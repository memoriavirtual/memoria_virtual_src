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
	public Usuario realizarLogin(String usuario, String senha) {
		
		Query query;
		
		query = this.entityManager
				.createQuery("SELECT u FROM Usuario u WHERE (u.id = :usuario OR u.email = :usuario) AND u.senha = :senha");
		query.setParameter("usuario", usuario);
		query.setParameter("senha", senha);

		
		//query = this.entityManager
		//.createQuery("SELECT u FROM Usuario WHERE (id = :usuario OR email = :usuario) AND senha = :senha");
		
		Usuario resultado;
		Usuario resultadoClone;
		
		try {
		    resultado = (Usuario) query.getSingleResult();
		    resultadoClone = new Usuario(resultado.getId(), resultado.getEmail(), null);
		    
		} catch (NoResultException e) {
			resultadoClone = null;
			e.printStackTrace();
		}

		return resultadoClone;
	}

}
