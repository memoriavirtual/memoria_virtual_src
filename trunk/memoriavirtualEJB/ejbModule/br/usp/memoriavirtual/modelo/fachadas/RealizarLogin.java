package br.usp.memoriavirtual.modelo.fachadas;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.remoto.RealizarLoginRemote;

/**
 * Session Bean implementation class RealizarLogin
 */
@Stateless(mappedName = "RealizarLogin")
public class RealizarLogin implements RealizarLoginRemote {

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;

	/**
	 * Default constructor.
	 */
	public RealizarLogin() {

	}

	/**
	 * return resultado Resultado da valida��o do login
	 */
	public boolean autenticarUsuario(Usuario usuario) {
		boolean resultado = false;

		Query query;
		if (usuario.getEmail() == null) {   //caso o usuario esteja usando Id para logar
			query = this.entityManager
					.createQuery("SELECT u FROM Usuario u WHERE u.id = :id AND u.senha = :senha");
			query.setParameter("id", usuario.getId());
			query.setParameter("senha", usuario.getSenha());
		} else {                            //caso o usuario esteja usando Email para logar
			query = this.entityManager
					.createQuery("SELECT u FROM Usuario u WHERE u.email = :email AND u.senha = :senha");
			query.setParameter("email", usuario.getEmail());
			query.setParameter("senha", usuario.getSenha());
		}
		try {
			query.getSingleResult();
			resultado = true;
		} catch (NoResultException e) {
			resultado = false;
		}

		return resultado;
	}

}
