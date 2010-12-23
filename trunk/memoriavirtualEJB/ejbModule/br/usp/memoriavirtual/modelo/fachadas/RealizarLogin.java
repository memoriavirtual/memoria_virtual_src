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


	@PersistenceContext (unitName = "memoriavirtual")
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
	
		Query query = this.entityManager.createQuery("SELECT u FROM Usuario u WHERE u.login = :login AND u.senha = :senha");
        query.setParameter("login", usuario.getLogin());
        query.setParameter("senha", usuario.getSenha());
		
        try{
        	query.getSingleResult();
			resultado = true;
        }catch (NoResultException e) {
        	resultado = false;
		}
		
		return resultado;
	}

}
