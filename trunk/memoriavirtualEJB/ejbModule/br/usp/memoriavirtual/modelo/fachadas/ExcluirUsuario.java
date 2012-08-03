package br.usp.memoriavirtual.modelo.fachadas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
	public List<Usuario> listarUsuarios(String parteNome, Usuario requerente) throws ModeloException{

		List<Usuario> usuarios = new ArrayList<Usuario>();
		
		Query query = this.entityManager.createQuery("SELECT u FROM Usuario u WHERE " +
				"u.ativo = TRUE AND "  +
				"u.nomeCompleto LIKE :nome AND " +
				"u <> :requerente" + 
				" ORDER BY u.nomeCompleto ");
		query.setParameter("requerente", requerente);
		query.setParameter("nome", "%" + parteNome + "%");
		
		try{
			usuarios = (List<Usuario>)query.getResultList();
		}
		catch(Exception e){
			throw new ModeloException(e);
		}
		
		return usuarios;

	}

	public Usuario recuperarDadosUsuario(String nome) throws ModeloException {
		Query query = entityManager
				.createQuery("SELECT u FROM Usuario u WHERE u.nomeCompleto = :nome");
		query.setParameter("nome", nome);
		Usuario usuario = null;
		try {
			usuario = (Usuario) query.getSingleResult();
		} catch (NoResultException e) {
			throw new ModeloException("Erro ao carregar os dados!");
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
					.createQuery("SELECT a FROM Usuario a WHERE a.id <> :eliminador AND a.administrador = TRUE ORDER BY a.id ");
			query.setParameter("eliminador", eliminador);
		} else {
			query = this.entityManager
					.createQuery("SELECT a FROM Usuario a WHERE a.administrador = FALSE AND a.nomeCompleto LIKE :parteNome ORDER BY a.id ");
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

	public void registrarAprovacao(Usuario validador, String idExcluido,
			Date dataValidade) {
		Date data = new Date();
		Usuario u = entityManager.find(Usuario.class, validador.getId());
		Aprovacao aprovacao = new Aprovacao(data, u, dataValidade, idExcluido,
				"Usuario");
		this.entityManager.persist(aprovacao);
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
					.createQuery("UPDATE Acesso a SET a.validade = :validade WHERE  a.usuario = :id");
			query.setParameter("id", usuario.getId());
			query.setParameter("validade", marca);
			query.executeUpdate();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Acesso> listarAcessos(Usuario usuario)
			throws ModeloException {
		
		List<Acesso> acessos = new ArrayList<Acesso>();
		
		Query query = this.entityManager.createQuery("SELECT a FROM Acesso a WHERE a.usuario = :usuario");
		query.setParameter("usuario", usuario);
		
		try{
			acessos = (List<Acesso>)query.getResultList();
		}
		catch(Exception e){
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
		
		Query listarAcessos = entityManager.createQuery("SELECT a FROM Acesso a WHERE a.usuario = :usuario AND a.validade = TRUE");
		listarAcessos.setParameter("usuario", usuario);
		
		try{
			acessos = (List<Acesso>)listarAcessos.getResultList();
		}
		catch(Exception e){
			throw new ModeloException();
		}
		
		Query listarAdministradores = entityManager.createQuery("SELECT u FROM Usuario u WHERE " +
				"u.ativo = TRUE AND u.administrador = TRUE AND u <> :usuario");
		listarAdministradores.setParameter("usuario", requerente);
		
		try{
			aprovadores = listarAdministradores.getResultList();
		}
		catch(Exception e){
			throw new ModeloException();
		}
		
		for(Acesso a : acessos){
			Query listarAprovadores = entityManager.createQuery("SELECT a.usuario FROM Acesso a WHERE a.grupo = :grupo " +
					"AND a.instituicao = :instituicao AND a.validade = TRUE AND a.usuario <> :requerente");
			listarAprovadores.setParameter("grupo", new Grupo("gerente"));
			listarAprovadores.setParameter("instituicao", a.getInstituicao());
			listarAprovadores.setParameter("requerente", requerente);
			
			try{
				List<Usuario> gerentes = listarAprovadores.getResultList();
				System.out.println(gerentes.size());
				for(Usuario u : gerentes){
					aprovadores.add(u);
				}
			}
			catch(Exception e){
				throw new ModeloException();
			}
		}
			
		return aprovadores;
	}

}
