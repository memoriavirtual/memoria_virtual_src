package br.usp.memoriavirtual.modelo.fachadas;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.usp.memoriavirtual.modelo.entidades.Autoria;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Titulo;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarBemPatrimonialRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.VersionarBemPatrimonialRemote;

@Stateless(mappedName = "EditarBemPatrimonial")
public class EditarBemPatrimonial implements EditarBemPatrimonialRemote {
	
	public EditarBemPatrimonial() {
		
	}

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;
	
	@EJB
	private VersionarBemPatrimonialRemote novaVersao;
	
	@Override
	public void editarBemPatrimonial(BemPatrimonial bem) throws ModeloException {

		try {
			entityManager.merge(bem);
			
			for (Titulo a : bem.getTitulos())
				a.setBempatrimonial(bem);
			for (Autoria a : bem.getAutorias())
				a.setBemPatrimonial(bem);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ModeloException(e);
		}
		
		novaVersao.salvarVersaoBemPatrimonial(bem);

	}
}
