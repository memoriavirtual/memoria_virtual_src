package br.usp.memoriavirtual.modelo.fachadas;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.usp.memoriavirtual.modelo.entidades.Multimidia;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirBemPatrimonialRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.UtilMultimidiaRemote;
import br.usp.memoriavirtual.utils.MVModeloCamposMultimidia;

@Stateless(mappedName = "ExcluirBemPatrimonial")
public class ExcluirBemPatrimonial implements ExcluirBemPatrimonialRemote {

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;

	@EJB
	private UtilMultimidiaRemote utilMultimidiaEJB;
	
	@Override
	public void excluir(long id) throws ModeloException {

		try {
			
			BemPatrimonial bemPatrimonial = this.entityManager.find(BemPatrimonial.class, id);
			
			for(MVModeloCamposMultimidia midiaGenerica : utilMultimidiaEJB.listarCampos(bemPatrimonial.getContainerMultimidia())){
				Multimidia midia = this.entityManager.find(Multimidia.class, midiaGenerica.getId());
				this.entityManager.remove(midia);
			}
			
			this.entityManager.remove(bemPatrimonial.getContainerMultimidia());
			
			this.entityManager.remove(bemPatrimonial);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ModeloException(e);
		}
	}
}
