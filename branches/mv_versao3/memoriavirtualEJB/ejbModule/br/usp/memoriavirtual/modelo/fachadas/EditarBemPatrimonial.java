package br.usp.memoriavirtual.modelo.fachadas;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.Autoria;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Assunto;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Descritor;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Titulo;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarBemPatrimonialRemote;

@Stateless(mappedName = "EditarBemPatrimonial")
public class EditarBemPatrimonial implements EditarBemPatrimonialRemote {
	public EditarBemPatrimonial() {

	}

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<BemPatrimonial> listarBensPatrimoniais(String strDeBusca)
			throws ModeloException {
		List<BemPatrimonial> lista;

		Query query;
		query = this.entityManager
				.createQuery("SELECT a FROM BemPatrimonial a ");
		try {
			lista = (List<BemPatrimonial>) query.getResultList();
			return lista;
		} catch (Exception e) {
			throw new ModeloException(e);
		}
	}

	@Override
	public void editarBemPatrimonial(BemPatrimonial bem) throws ModeloException {

		try {
			Query query;

			query = entityManager
					.createQuery("DELETE FROM Autoria a WHERE a.bemPatrimonial = :bem");
			query.setParameter("bem", bem);
			query.executeUpdate();

			for (Assunto a : bem.getAssuntos()) {
				query = this.entityManager
						.createQuery("SELECT a FROM Assunto a WHERE  a.assunto = :assunto ");
				query.setParameter("assunto", a.getAssunto());
				try {
					a = (Assunto) query.getSingleResult();

				} catch (Exception e1) {
					entityManager.persist(a);
				}
			}
			
			query = entityManager.createQuery("UPDATE BemPatrimonial b SET b.containerMultimidia = NULL WHERE b = :bem");
			query.setParameter("bem", bem);
			query.executeUpdate();

			query = entityManager
					.createQuery("DELETE FROM Multimidia m WHERE m.containerMultimidia = :container");
			query.setParameter("container", bem.getContainerMultimidia());
			query.executeUpdate();

			query = entityManager
					.createQuery("DELETE FROM ContainerMultimidia c WHERE c = :container");
			query.setParameter("container", bem.getContainerMultimidia());
			query.executeUpdate();

			for (Descritor a : bem.getDescritores()) {
				query = this.entityManager
						.createQuery("SELECT a FROM Descritor a WHERE  a.descritor = :descritor ");
				query.setParameter("descritor", a.getDescritor());
				try {
					a = (Descritor) query.getSingleResult();

				} catch (Exception e1) {
					entityManager.persist(a);
				}
			}

			for (Titulo a : bem.getTitulos())
				a.setBempatrimonial(bem);
			for (Autoria a : bem.getAutorias())
				a.setBemPatrimonial(bem);

			entityManager.merge(bem);

		} catch (Exception e) {
			throw new ModeloException(e);
		}

	}
}
