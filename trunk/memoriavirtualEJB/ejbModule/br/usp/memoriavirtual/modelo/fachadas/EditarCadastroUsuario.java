package br.usp.memoriavirtual.modelo.fachadas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
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
	public List<Grupo> listarGrupos() throws ModeloException {

		List<Grupo> grupos = new ArrayList<Grupo>();

		try {
			Query query = this.entityManager
					.createQuery("SELECT g FROM Grupo g");
			grupos = (List<Grupo>) query.getResultList();
			return grupos;
		} catch (Exception e) {
			throw new ModeloException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Acesso> listarAcessos(Usuario usuario) throws ModeloException {
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

	/**
	 * Método usado para listar os possíveis aprovadores de uma edição de
	 * acessos de usuário. Esses aprovadores são os administradores do
	 * sistema..
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> listarAprovadores(Usuario usuario)
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

	@Override
	public void editarCadastro(Usuario usuario, String nomeCompleto,
			String telefone) throws ModeloException {

		Usuario usuarioAlterado = entityManager.find(usuario.getClass(),
				usuario.getId());

		usuarioAlterado.setNomeCompleto(nomeCompleto);
		usuarioAlterado.setTelefone(telefone);

		try {
			entityManager.flush();
		} catch (Exception t) {
			throw new ModeloException(t);
		}

	}

	@Override
	public void editarAcessos(Usuario usuario, String aprovadorId,
			List<Acesso> acessos, List<String> situacoes, Date data,
			Date expiracao, String justificativa, boolean administrador)
			throws ModeloException {

		try {
			Usuario aprovador = entityManager.find(Usuario.class, aprovadorId);
			usuario = entityManager.find(Usuario.class, usuario.getId());

			if (administrador == true) {

				Aprovacao aprovacao = new Aprovacao();
				aprovacao.setAprovador(aprovador);
				aprovacao.setChaveEstrangeira(new Boolean(administrador).toString());
				aprovacao.setData(data);
				aprovacao.setExpiracao(expiracao);
				aprovacao.setTabelaEstrangeira("Usuario");

				String idEmbaralhado = memoriaVirtualEJB.embaralhar(aprovacao
						.getId().toString());

				String link = memoriaVirtualEJB.getURLServidor()
						+ "/editarcadastrousuario?Id=" + idEmbaralhado;

				FacesContext context = FacesContext.getCurrentInstance();
				String bundleName = "mensagens";
				ResourceBundle bundle = context.getApplication()
						.getResourceBundle(context, bundleName);

				String message = bundle
						.getString("editarCadastroUsuarioMensagem");

				message = message + link;
				message = message
						+ bundle.getString("editarCadastroUsuarioMensagemJustificativa");
				message = message + justificativa;

				this.memoriaVirtualEJB.enviarEmail(aprovador.getEmail(), "mv",
						message);

				entityManager.persist(aprovacao);

				return;
			} else {

				for (Acesso a : acessos) {

					// Buscar no banco os objetos corretos para instanciar o
					// aceso
					Query q = entityManager
							.createQuery("SELECT i FROM Instituicao i WHERE i.nome = :nome");
					q.setParameter("nome", a.getInstituicao().getNome());
					Instituicao i = (Instituicao) q.getSingleResult();
					Grupo g = entityManager.find(Grupo.class, a.getGrupo()
							.getId());
					Usuario u = entityManager.find(Usuario.class, a
							.getUsuario().getId());

					// Preparar o objeto acesso para ser salvo na tabela
					// aprovacao
					String grupo = g.getId();
					String usuarioId = u.getId();
					long instituicao = i.getId();
					String acesso = grupo + ";" + usuarioId + ";" + instituicao
							+ ";" + situacoes.get(0) + ";" + justificativa;

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
					String idEmbaralhado = memoriaVirtualEJB
							.embaralhar(aprovacao.getId().toString());

					String link = memoriaVirtualEJB.getURLServidor()
							+ "/editarcadastrousuario?Id=" + idEmbaralhado;

					FacesContext context = FacesContext.getCurrentInstance();
					String bundleName = "mensagens";
					ResourceBundle bundle = context.getApplication()
							.getResourceBundle(context, bundleName);

					String message = bundle
							.getString("editarCadastroUsuarioMensagem");

					message = message + link;
					message = message
							+ bundle.getString("editarCadastroUsuarioMensagemJustificativa");
					message = message + justificativa;

					this.memoriaVirtualEJB.enviarEmail(aprovador.getEmail(),
							"mv", message);

				}
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

			entityManager.persist(a);

			Query query = this.entityManager
					.createQuery("DELETE  FROM Aprovacao a WHERE a = :aprovacao ");
			query.setParameter("aprovacao", aprovacao);
			query.executeUpdate();

		} catch (Exception e) {
			throw new ModeloException(e);
		}
	}

	@Override
	public void remover(long id) throws ModeloException {

		try {

			Aprovacao aprovacao = entityManager.find(Aprovacao.class, id);
			String acessoString[] = aprovacao.getChaveEstrangeira().split(";");

			long instituicaoId = new Long(acessoString[2]).longValue();
			Grupo g = entityManager.find(Grupo.class, acessoString[0]);
			Usuario u = entityManager.find(Usuario.class, acessoString[1]);
			Instituicao i = entityManager
					.find(Instituicao.class, instituicaoId);

			Query removeAcesso = this.entityManager
					.createQuery("DELETE FROM Acesso a WHERE a.grupo = :grupo AND a.instituicao = :instituicao AND a.usuario = :usuario AND a.validade = true");
			removeAcesso.setParameter("instituicao", i);
			removeAcesso.setParameter("usuario", u);
			removeAcesso.setParameter("grupo", g);
			removeAcesso.executeUpdate();

			Query query = this.entityManager
					.createQuery("DELETE FROM Aprovacao a WHERE a.id = :id");
			query.setParameter("id", aprovacao.getId());
			query.executeUpdate();

		} catch (Exception e) {
			throw new ModeloException(e);
		}
	}

	@Override
	public void removerAprovacao(String aprovacaoString) throws ModeloException {
		try {

			long id = new Long(aprovacaoString).longValue();
			Aprovacao aprovacao = entityManager.find(Aprovacao.class, id);

			Query query = this.entityManager
					.createQuery("DELETE FROM Aprovacao a WHERE a = :parametro");
			query.setParameter("parametro", aprovacao);
			query.executeUpdate();

		} catch (Exception e) {
			throw new ModeloException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Instituicao> listarInstituicoes(String instituicao)
			throws ModeloException {
		List<Instituicao> instituicoes = new ArrayList<Instituicao>();

		try {
			Query query = entityManager
					.createQuery("SELECT i FROM Instituicao i WHERE i.nome LIKE :padrao ORDER BY i.nome");
			query.setParameter("padrao", "%" + instituicao + "%");
			instituicoes = (List<Instituicao>) query.getResultList();

			if (instituicoes.size() == 0) {
				Instituicao listarTodos = new Instituicao();
				listarTodos.setNome("Listar Todos");
				instituicoes.add(0, listarTodos);
			}

			return instituicoes;
		} catch (Exception e) {
			throw new ModeloException(e);
		}
	}

	@Override
	public boolean isAprovador(Usuario usuario, String aprovacaoId) {
		try {

			Aprovacao aprovacao = this.entityManager.find(Aprovacao.class,
					Long.valueOf(aprovacaoId));
			if (aprovacao.getAprovador().getId().equals(usuario.getId()))
				return true;
			else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
