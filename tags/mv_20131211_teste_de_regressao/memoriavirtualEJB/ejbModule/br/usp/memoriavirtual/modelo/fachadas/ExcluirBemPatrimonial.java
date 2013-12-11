package br.usp.memoriavirtual.modelo.fachadas;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirBemPatrimonialRemote;

@Stateless(mappedName = "ExcluirAutor")
public class ExcluirBemPatrimonial implements ExcluirBemPatrimonialRemote {

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;
	
	@Override
	public BemPatrimonial recuperarDados(BemPatrimonial bem) throws ModeloException{
		try{
			return this.entityManager.find(BemPatrimonial.class, bem.getId());
		}
		catch(Exception e){
			throw new ModeloException(e);
		}
	}

	@Override
	public void excluirBem(BemPatrimonial bem) throws ModeloException {
		
		try{
			
			BemPatrimonial excluido = this.entityManager.find(BemPatrimonial.class, bem.getId());
			this.entityManager.remove(excluido);
		}
		catch(Exception e){
			throw new ModeloException(e);
		}
		
	}

}
