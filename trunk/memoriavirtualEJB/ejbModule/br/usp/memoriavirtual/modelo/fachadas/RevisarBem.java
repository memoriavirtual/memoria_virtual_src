package br.usp.memoriavirtual.modelo.fachadas;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Revisao;
import br.usp.memoriavirtual.modelo.fachadas.remoto.RevisarBemRemote;

@Stateless(mappedName="RevisarBem")
public class RevisarBem implements RevisarBemRemote{

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;
	
	@Override
	public BemPatrimonial getBemPatrimonial(Long id) throws ModeloException {
		Query q = entityManager.createQuery("SELECT b FROM BemPatrimonial b WHERE b.id=:identificacao");
		q.setParameter("identificacao", id);
		
		BemPatrimonial b = (BemPatrimonial) q.getResultList().get(0);
		
		entityManager.refresh(b);
		return b;
	}

	@Override
	public void inserirRevisao(Revisao r) {
		entityManager.persist(r);		
	}

}
