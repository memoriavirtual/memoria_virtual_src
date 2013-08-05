package br.usp.memoriavirtual.modelo.fachadas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.Acesso;
import br.usp.memoriavirtual.modelo.entidades.Aprovacao;
import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirUsuarioRemote;

@Stateless(mappedName = "ExcluirUsuario")
public class ExcluirUsuario implements ExcluirUsuarioRemote {

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<Usuario> listarUsuarios(String parteNome, Usuario requerente,
			boolean isAdministrador) throws ModeloException {

		List<Usuario> usuarios = new ArrayList<Usuario>();

		if (isAdministrador) {
			Query query = this.entityManager
					.createQuery("SELECT u FROM Usuario u WHERE "
							+ "u.ativo = TRUE AND "
							+ "LOWER(u.nomeCompleto) LIKE LOWER(:nome) AND "
							+ "u <> :requerente" + " ORDER BY u.nomeCompleto ");
			query.setParameter("requerente", requerente);
			query.setParameter("nome", "%" + parteNome + "%");
			try {
				usuarios = (List<Usuario>) query.getResultList();
			} catch (Exception e) {
				throw new ModeloException(e);
			}
		} else {
			Query query = this.entityManager
					.createQuery("SELECT b.usuario from Acesso a, Acesso b "
							+ "WHERE a.usuario = :requerente AND a.grupo = :grupo "
							+ "AND b.instituicao = a.instituicao AND b.usuario <> :requerente");

			query.setParameter("grupo", new Grupo("GERENTE"));
			query.setParameter("requerente", requerente);

			try {
				usuarios = query.getResultList();
			} catch (Exception e) {
				throw new ModeloException(e);
			}

		}

		return usuarios;

	}

	public Usuario recuperarDadosUsuario(String nome) throws ModeloException {

		Usuario usuario = new Usuario();

		try {
			Query query = entityManager
					.createQuery("SELECT u FROM Usuario u WHERE u.nomeCompleto = :nome");
			query.setParameter("nome", nome);

			usuario = (Usuario) query.getSingleResult();
		} catch (Exception e) {
			throw new ModeloException(e);
		}

		return usuario;
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> listarSemelhantes(String eliminador,
			Boolean isAdministrador) {
		List<Usuario> usuarios;
		Query query;
		if (isAdministrador) {
			query = this.entityManager
					.createQuery("SELECT a FROM Usuario a WHERE a.id <> :eliminador "
							+ "AND a.administrador = TRUE ORDER BY a.id ");
			query.setParameter("eliminador", eliminador);
		} else {
			query = this.entityManager
					.createQuery("SELECT a FROM Usuario a WHERE a.administrador = FALSE AND "
							+ "LOWER(a.nomeCompleto) LIKE LOWER(:parteNome) "
							+ "ORDER BY a.id ");
			query.setParameter("eliminador", eliminador);
		}
		try {
			usuarios = (List<Usuario>) query.getResultList();
			return usuarios;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public Long registrarAprovacao(Usuario validador, String idExcluido,
			Date dataValidade) throws ModeloException {
		Date data = new Date();
		Usuario u = entityManager.find(Usuario.class, validador.getId());
		Aprovacao aprovacao = new Aprovacao(data, u, dataValidade, idExcluido,
				Usuario.class.getCanonicalName());
		try {
			this.entityManager.persist(aprovacao);
		} catch (Exception e) {
			throw new ModeloException(e);
		}

		return aprovacao.getId();
	}

	public void marcarUsuarioExcluido(Usuario usuario, boolean marca,
			boolean flagAcesso) throws ModeloException {
		Query query;
		query = this.entityManager
				.createQuery("UPDATE Usuario a SET a.ativo = :validade WHERE  a.id = :id");
		query.setParameter("id", usuario.getId());
		query.setParameter("validade", marca);
		query.executeUpdate();
		if (flagAcesso) {
			query = this.entityManager
					.createQuery("UPDATE Acesso a SET a.validade = :validade WHERE  a.usuario = :usuario");
			query.setParameter("usuario", usuario);
			query.setParameter("validade", marca);
			query.executeUpdate();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Acesso> listarAcessos(Usuario usuario) throws ModeloException {

		List<Acesso> acessos = new ArrayList<Acesso>();

		Query query = this.entityManager
				.createQuery("SELECT a FROM Acesso a WHERE a.usuario = :usuario");
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
	public List<Usuario> listarAprovadores(Usuario requerente, Usuario usuario)
			throws ModeloException {

		List<Acesso> acessos = new ArrayList<Acesso>();
		List<Usuario> aprovadores = new ArrayList<Usuario>();

		Query listarAcessos = entityManager
				.createQuery("SELECT a FROM Acesso a WHERE a.usuario = :usuario AND a.validade = TRUE");
		listarAcessos.setParameter("usuario", usuario);

		try {
			acessos = (List<Acesso>) listarAcessos.getResultList();
		} catch (Exception e) {
			throw new ModeloException();
		}

		Query listarAdministradores = entityManager
				.createQuery("SELECT u FROM Usuario u WHERE "
						+ "u.ativo = TRUE AND u.administrador = TRUE AND u <> :requerente AND u <> :usuario");
		listarAdministradores.setParameter("usuario", usuario);
		listarAdministradores.setParameter("requerente", requerente);

		try {
			aprovadores = listarAdministradores.getResultList();
		} catch (Exception e) {
			throw new ModeloException();
		}

		for (Acesso a : acessos) {
			Query listarAprovadores = entityManager
					.createQuery("SELECT a.usuario FROM Acesso a WHERE a.grupo = :grupo "
							+ "AND a.instituicao = :instituicao AND a.validade = TRUE AND a.usuario <> :requerente AND a.usuario <> :usuario");
			listarAprovadores.setParameter("grupo", new Grupo("gerente"));
			listarAprovadores.setParameter("instituicao", a.getInstituicao());
			listarAprovadores.setParameter("requerente", requerente);
			listarAprovadores.setParameter("usuario", usuario);

			try {
				List<Usuario> gerentes = listarAprovadores.getResultList();
				for (Usuario u : gerentes) {
					aprovadores.add(u);
				}
			} catch (Exception e) {
				throw new ModeloException();
			}
		}

		return aprovadores;
	}

	@Override
	public Aprovacao recuperarDadosAprovacao(String id) throws ModeloException {
		Aprovacao aprovacao = new Aprovacao();

		Query query = this.entityManager
				.createQuery("SELECT a FROM Aprovacao a WHERE a.id = :id");
		query.setParameter("id", id);

		try {
			aprovacao = (Aprovacao) query.getSingleResult();
		} catch (Exception e) {
			throw new ModeloException();
		}
		return aprovacao;
	}

	@Override
	public void excluirUsuario(String id) throws ModeloException {
		try {
			Aprovacao aprovacao = this.entityManager.find(Aprovacao.class,
					Long.valueOf(id));
			Usuario usuario = this.entityManager.find(Usuario.class,
					aprovacao.getChaveEstrangeira());

			Query removerAcessos = this.entityManager
					.createQuery("DELETE FROM Acesso a WHERE a.usuario = :usuario");
			removerAcessos.setParameter("usuario", usuario);
			removerAcessos.executeUpdate();

			Query query = entityManager
					.createQuery("DELETE FROM ItemAuditoria i WHERE i.autorAcao = :usuario");
			query.setParameter("usuario", usuario);
			query.executeUpdate();

			this.entityManager.remove(usuario);

			Query removerUsuario = this.entityManager
					.createQuery("DELETE FROM Aprovacao a WHERE a.id = :id");
			removerUsuario.setParameter("id", Long.valueOf(id));
			removerUsuario.executeUpdate();

		} catch (Exception e) {
			throw new ModeloException(e);
		}
	}

	public Usuario recuperarUsuario(String id) {

		Query query1 = this.entityManager
				.createQuery("SELECT u FROM Usuario u WHERE u.id = :usuario");
		query1.setParameter("usuario", id);

		Usuario usuario = null;

		try {
			usuario = (Usuario) query1.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return usuario;
	}

	public void excluirAprovacao(Aprovacao aprovacao) {
		Query query;
		query = this.entityManager
				.createQuery("DELETE  FROM Aprovacao a WHERE a = :aprovacao ");
		query.setParameter("aprovacao", aprovacao);
		query.executeUpdate();
	}
}
