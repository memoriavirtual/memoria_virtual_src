package br.usp.memoriavirtual.modelo.fachadas;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirBemPatrimonialRemote;

public class ExcluirBemPatrimonial implements ExcluirBemPatrimonialRemote{
	
	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;


	@Override
	public List<BemPatrimonial> listarBensPatrimoniais(String nome) throws ModeloException{
		
		//Query query = this.entityManager.createQuery("SELECT b FROM Bem")
		return null;
		
	}

}
