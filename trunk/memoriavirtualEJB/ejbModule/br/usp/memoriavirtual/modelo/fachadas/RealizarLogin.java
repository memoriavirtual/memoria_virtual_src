package br.usp.memoriavirtual.modelo.fachadas;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
        // TODO Auto-generated constructor stub
    }

}
