package br.usp.memoriavirtual.modelo.fachadas;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import br.usp.memoriavirtual.modelo.entidades.Acesso;
import br.usp.memoriavirtual.modelo.entidades.Aprovacao;
import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarCadastroUsuarioRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;

@Stateless(mappedName = "EditarCadastroUsuario")
public class EditarCadastroUsuario implements EditarCadastroUsuarioRemote {

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;

	@EJB
	private MemoriaVirtualRemote memoriaVirtualEJB;

	public EditarCadastroUsuario() {

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Acesso> getAcessos(Usuario usuario) throws ModeloException {
		List<Acesso> acessos = new ArrayList<Acesso>();
		Query query = entityManager
				.createQuery("SELECT a FROM Acesso a WHERE a.usuario = :usuario AND a.validade = true");
		query.setParameter("usuario", usuario);
		try {

			acessos = (List<Acesso>) query.getResultList();

		} catch (Exception e) {
			throw new ModeloException(e);
		}
		return acessos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> listarUsuarios(String nome) throws ModeloException {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		Query query = entityManager
				.createQuery("SELECT a FROM Usuario a WHERE a.ativo = true AND a.administrador = false AND a.nomeCompleto LIKE :padrao ORDER BY a.nomeCompleto");
		query.setParameter("padrao", "%" + nome + "%");
		try {
			usuarios = (List<Usuario>) query.getResultList();
		} catch (Exception e) {
			throw new ModeloException(e);
		}
		return usuarios;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> getAdministradores(Usuario usuario) throws ModeloException {
		List<Usuario> administradores = new ArrayList<Usuario>();

		Query query = entityManager
				.createQuery("SELECT a FROM Usuario a WHERE a <> :requerente AND a.administrador = true AND a.ativo = true");
		query.setParameter("requerente", usuario);
		try {
			administradores = (List<Usuario>) query.getResultList();
		} catch (Exception e) {
			throw new ModeloException(e);
		}
		return administradores;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> listarUsuarios(String nome, Usuario requerente, Grupo grupo) throws ModeloException {

		List<Acesso> acessos = new ArrayList<Acesso>();
		List<Instituicao> instituicoes = new ArrayList<Instituicao>();
		List<Usuario> usuarios = new ArrayList<Usuario>();

		Query query = entityManager
				.createQuery("SELECT a FROM Acesso a WHERE a.usuario = :usuario AND a.grupo = :grupo AND a.validade = true");
		query.setParameter("usuario", requerente);
		query.setParameter("grupo", grupo);

		try {

			acessos = (List<Acesso>) query.getResultList();
			for (Acesso a : acessos) {
				instituicoes.add(a.getInstituicao());

			}
		} catch (Exception e) {
			throw new ModeloException(e);
		}
		for (Instituicao i : instituicoes) {
			Query q = entityManager
					.createQuery("SELECT a FROM Acesso a WHERE a.instituicao = :instituicao AND a.validade = true");
			q.setParameter("instituicao", i);
			try {
				acessos = q.getResultList();
				for (Acesso a : acessos) {
					// Os usuarios listados não podem ser administradores, não
					// podem ser repetidos, nem podem ser o proprio usuario que
					// faz a requisicao
					if (!a.getUsuario().isAdministrador() && !usuarios.contains(a.getUsuario())
							&& (a.getUsuario().getId() != requerente.getId())) {
						usuarios.add(a.getUsuario());
					}
				}
			} catch (Exception e) {
				throw new ModeloException(e);
			}
		}
		return usuarios;
	}

	@Override
	public void editarCadastro(Usuario usuario, String nomeCompleto, String telefone) throws ModeloException {

		Usuario usuarioAlterado = entityManager.find(usuario.getClass(), usuario.getId());

		usuarioAlterado.setNomeCompleto(nomeCompleto);
		usuarioAlterado.setTelefone(telefone);

		// problemas com constraints - administrador nao pode ser null
		if (!usuarioAlterado.isAdministrador()) {
			usuarioAlterado.setAdministrador(false);
		}

		try {
			entityManager.flush();
		} catch (Exception t) {
			throw new ModeloException(t);
		}

	}

	@Override
	public void editarAcessos(String aprovadorId, List<Acesso> acessos, Date data, Date expiracao)
			throws ModeloException {

		try {
			Usuario aprovador = entityManager.find(Usuario.class, aprovadorId);

			for (Acesso a : acessos) {

				SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SSS");

				String dataString = formatoData.format(data);
				data = formatoData.parse(dataString);

				String expiracaoString = formatoData.format(expiracao);
				expiracao = formatoData.parse(expiracaoString);

				String grupo = a.getGrupo().getId();
				String usuario = a.getUsuario().getId();
				String instituicao = a.getInstituicao().getNome();
				String acesso = grupo + ";" + usuario + ";" + instituicao;

				Aprovacao aprovacao = new Aprovacao();
				aprovacao.setAprovador(aprovador);
				aprovacao.setChaveEstrangeira(acesso);
				aprovacao.setData(data);
				aprovacao.setExpiracao(expiracao);
				aprovacao.setTabelaEstrangeira("Acesso");

				Instituicao i = entityManager.find(Instituicao.class, a.getInstituicao().getNome());
				Grupo g = entityManager.find(Grupo.class, a.getGrupo().getId());
				Usuario u = entityManager.find(Usuario.class, a.getUsuario().getId());

				Acesso b = new Acesso();
				b.setGrupo(g);
				b.setInstituicao(i);
				b.setUsuario(u);
				b.setValidade(false);

				entityManager.persist(b);
				entityManager.persist(aprovacao);

				String dataEmbaralhado = memoriaVirtualEJB.embaralhar(dataString);
				String aprovadorEmbaralhado = memoriaVirtualEJB.embaralhar(aprovadorId);
				String acessoEmbaralhado = memoriaVirtualEJB.embaralhar(acesso);

				String link = memoriaVirtualEJB.getURLServidor()

				+ "/editarcadastrousuario.jsf?Data=" + dataEmbaralhado + "&Aprovador=" + aprovadorEmbaralhado
						+ "&Acesso=" + acessoEmbaralhado;

				System.out.println(link);

			}
		} catch (Exception e) {
			throw new ModeloException(e);
		}

	}

}
