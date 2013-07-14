/**
 * 
 */
package br.usp.memoriavirtual.modelo.fachadas;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarBemPatrimonialRemote;

/**
 * @author mac
 *
 */
@Stateless (mappedName = "EditarBemPatrimonial")
public class EditarBemPatrimonial implements EditarBemPatrimonialRemote {
	public EditarBemPatrimonial() {
		
	}
	
	
	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BemPatrimonial> listarBensPatrimoniais (String strDeBusca) throws ModeloException{
		List<BemPatrimonial> lista ;

		Query query;
		query = this.entityManager
				.createQuery("SELECT a FROM BemPatrimonial a ");
		try {
			lista = ( List<BemPatrimonial> ) query.getResultList();
			return  lista ;
		} catch (Exception e) {
			throw new ModeloException(e);
		}
	}

	@Override
	public void salvarBemPatrimonial(BemPatrimonial bem) throws ModeloException {
		
		//Merge do bem patrimonial
		entityManager.merge(bem); 
		
	}
	

}
