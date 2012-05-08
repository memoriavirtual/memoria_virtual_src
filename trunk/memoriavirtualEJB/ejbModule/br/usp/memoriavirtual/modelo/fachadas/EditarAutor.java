/**
 * 
 */
package br.usp.memoriavirtual.modelo.fachadas;

import java.util.List;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.Autor;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarAutorRemote;

/**
 * @author bigmac
 *
 */

@Stateless (mappedName = "EditarAutor")
public class EditarAutor implements EditarAutorRemote {
	
	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public List<Autor> listarAutores (String strDeBusca) throws ModeloException {
		List<Autor> lista ;

		Query query;
		query = this.entityManager
				.createQuery("SELECT a FROM Autor a WHERE a.nome like :busca ");
		query.setParameter("busca", strDeBusca + "%");
		try {
			lista = ( List<Autor> ) query.getResultList();
			return  lista ;
		} catch (Exception e) {
			throw new ModeloException(e);
		}
	}
}
