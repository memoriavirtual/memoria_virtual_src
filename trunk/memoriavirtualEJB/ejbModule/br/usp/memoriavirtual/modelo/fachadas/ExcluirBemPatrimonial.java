package br.usp.memoriavirtual.modelo.fachadas;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirBemPatrimonialRemote;

@Stateless(mappedName = "ExcluirAutor")
public class ExcluirBemPatrimonial implements ExcluirBemPatrimonialRemote {

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<BemPatrimonial> listarBensPatrimoniais(String nome)
			throws ModeloException {

		List<BemPatrimonial> bens = new ArrayList<BemPatrimonial>();

		Query query = this.entityManager
				.createQuery("SELECT t.bemPatrimonial FROM Titulo t WHERE  t.valor LIKE :nome");
		query.setParameter("nome", "%" + nome + "%");

		try {
			bens = (List<BemPatrimonial>) query.getResultList();
		} catch (Exception e) {
			throw new ModeloException(e);
		}

		return bens;

	}

}
