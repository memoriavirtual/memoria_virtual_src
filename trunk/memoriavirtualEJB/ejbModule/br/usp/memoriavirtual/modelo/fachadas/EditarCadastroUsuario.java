package br.usp.memoriavirtual.modelo.fachadas;

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
	public List<Usuario> getAdministradores(Usuario usuario)
			throws ModeloException {
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
	public List<Usuario> listarUsuarios(String nome, Usuario requerente,
			Grupo grupo) throws ModeloException {

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
					if (!a.getUsuario().isAdministrador()
							&& !usuarios.contains(a.getUsuario())
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
	public void editarCadastro(Usuario usuario, String nomeCompleto,
			String telefone) throws ModeloException {

		Usuario usuarioAlterado = entityManager.find(usuario.getClass(),
				usuario.getId());

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
	public void editarAcessos(String aprovadorId, List<Acesso> acessos,
			List<String> situacoes, Date data, Date expiracao)
			throws ModeloException {

		try {
			Usuario aprovador = entityManager.find(Usuario.class, aprovadorId);

			for (Acesso a : acessos) {

				System.out.println(a.getInstituicao().getNome()
						+ a.getGrupo().getId()
						+ a.getUsuario().getNomeCompleto());

				// Buscar no banco os objetos corretos para instanciar o aceso
				Query q = entityManager
						.createQuery("SELECT i FROM Instituicao i WHERE i.nome = :nome");
				q.setParameter("nome", a.getInstituicao().getNome());
				Instituicao i = (Instituicao) q.getSingleResult();
				Grupo g = entityManager.find(Grupo.class, a.getGrupo().getId());
				Usuario u = entityManager.find(Usuario.class, a.getUsuario()
						.getId());

				// Preparar o objeto acesso para ser salvo na tabela aprovacao
				String grupo = g.getId();
				String usuario = u.getId();
				long instituicao = i.getId();
				String acesso = grupo + ";" + usuario + ";" + instituicao + ";"
						+ situacoes.get(0);

				situacoes.remove(0);

				// Persistir a aprovacao
				Aprovacao aprovacao = new Aprovacao();
				aprovacao.setAprovador(aprovador);
				aprovacao.setChaveEstrangeira(acesso);
				aprovacao.setData(data);
				aprovacao.setExpiracao(expiracao);
				aprovacao.setTabelaEstrangeira("Acesso");

				entityManager.persist(aprovacao);

				// Preparar o link
				String idEmbaralhado = memoriaVirtualEJB.embaralhar(aprovacao
						.getId().toString());

				String link = memoriaVirtualEJB.getURLServidor()
						+ "/editarcadastrousuario?Id=" + idEmbaralhado;

				// this.memoriaVirtualEJB.enviarEmail(aprovador.getEmail(),
				// "mv", mensagem);

			}
		} catch (Exception e) {
			throw new ModeloException(e);
		}

	}

	@Override
	public boolean isAprovacaoExpirada(String aprovacaoString)
			throws ModeloException {
		try {
			long id = new Long(aprovacaoString).longValue();
			Aprovacao aprovacao = entityManager.find(Aprovacao.class, id);
			Date data = new Date();
			if (data.after(aprovacao.getExpiracao())) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new ModeloException(e);
		}

	}

	@Override
	public Acesso getAcesso(String aprovacaoString) throws ModeloException {
		try {

			long id = new Long(aprovacaoString).longValue();

			Aprovacao aprovacao = entityManager.find(Aprovacao.class, id);

			String acessoString[] = aprovacao.getChaveEstrangeira().split(";");

			Grupo g = entityManager.find(Grupo.class, acessoString[0]);
			Usuario u = entityManager.find(Usuario.class, acessoString[1]);
			long instituicaoId = new Long(acessoString[2]).longValue();
			Instituicao i = entityManager
					.find(Instituicao.class, instituicaoId);

			Acesso acesso = new Acesso();

			acesso.setGrupo(g);
			acesso.setInstituicao(i);
			acesso.setUsuario(u);

			return acesso;

		} catch (Exception e) {
			throw new ModeloException(e);
		}

	}

	@Override
	public Aprovacao getAprovacao(String aprovacaoString)
			throws ModeloException {

		try {
			long id = new Long(aprovacaoString).longValue();
			Aprovacao aprovacao = this.entityManager.find(Aprovacao.class, id);
			return aprovacao;
		} catch (Exception e) {
			throw new ModeloException(e);
		}
	}

	@Override
	public void persistir(long id) throws ModeloException {

		try {
			Aprovacao aprovacao = entityManager.find(Aprovacao.class, id);
			String acessoString[] = aprovacao.getChaveEstrangeira().split(";");

			long instituicaoId = new Long(acessoString[2]).longValue();
			Grupo g = entityManager.find(Grupo.class, acessoString[0]);
			Usuario u = entityManager.find(Usuario.class, acessoString[1]);
			Instituicao i = entityManager
					.find(Instituicao.class, instituicaoId);

			Acesso a = new Acesso();
			a.setGrupo(g);
			a.setUsuario(u);
			a.setInstituicao(i);
			a.setValidade(true);

			entityManager.remove(aprovacao);
			entityManager.persist(a);

		} catch (Exception e) {
			throw new ModeloException(e);
		}
	}

	@Override
	public void remover(long id) throws ModeloException {

		try {

			Aprovacao aprovacao = entityManager.find(Aprovacao.class, id);
			entityManager.remove(aprovacao);

		} catch (Exception e) {
			throw new ModeloException(e);
		}
	}

	@Override
	public void removerAprovacao(String aprovacaoString) throws ModeloException {
		try {

			long id = new Long(aprovacaoString).longValue();
			Aprovacao aprovacao = entityManager.find(Aprovacao.class, id);
			entityManager.remove(aprovacao);

		} catch (Exception e) {
			throw new ModeloException(e);
		}
	}
}
