package br.usp.memoriavirtual.modelo.fachadas;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.fachadas.remoto.ValidacaoRemote;

@Stateless(mappedName = "Validacao")
public class Validacao implements ValidacaoRemote {

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager em;

	@Override
	public boolean validacaoUnico(String query, Object o,
			Map<String, Object> parametros) throws ModeloException {
		try {
			Query q = em.createNamedQuery(query, o.getClass());
			Iterator<Entry<String, Object>> it = parametros.entrySet()
					.iterator();
			while (it.hasNext()) {
				Map.Entry<String, Object> par = (Map.Entry<String, Object>) it
						.next();
				q.setParameter(par.getKey(), par.getValue());
			}
			q.getSingleResult();
			return true;
		} catch (NonUniqueResultException n) {
			return false;
		} catch (Exception e) {
			throw new ModeloException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean validacaoNaoExiste(String query, Object o,
			Map<String, Object> parametros) throws ModeloException {
		try {
			Query q = em.createNamedQuery(query, o.getClass());
			Iterator<Entry<String, Object>> it = parametros.entrySet()
					.iterator();
			while (it.hasNext()) {
				Map.Entry<String, Object> par = (Map.Entry<String, Object>) it
						.next();
				q.setParameter(par.getKey(), par.getValue());
			}
			List<Object> list = q.getResultList();
			if (list.size() == 0) {
				return true;
			}
			return false;
		} catch (NoResultException n) {
			return true;
		} catch (Exception e) {
			throw new ModeloException(e);
		}
	}

}
