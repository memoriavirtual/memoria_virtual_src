package br.usp.memoriavirtual.modelo.fachadas;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.remoto.RealizarLoginRemote;

/**
 * EJB de Sessão sem estado responsável por implementar o caso de uso Realizar
 * Login.
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
     * @return resultado Resultado da validação do login
     */
    public Usuario realizarLogin(String usuario, String senha) {

	Query query;

	query = this.entityManager
		.createQuery("SELECT u FROM Usuario u WHERE (u.id = :usuario OR u.email = :usuario) AND u.senha = :senha");
	query.setParameter("usuario", usuario);
	query.setParameter("senha", senha);

	Usuario resultado;

	try {
	    resultado = (Usuario) query.getSingleResult();

	} catch (NoResultException e) {
		resultado = null;
	    e.printStackTrace();
	}

	return resultado;
    }

}
