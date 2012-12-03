package br.usp.memoriavirtual.modelo.fachadas;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.fachadas.remoto.RealizarBuscaSimplesRemote;

@Stateless(mappedName = "RealizarBuscaSimples")
public class RealizarBuscaSimples implements RealizarBuscaSimplesRemote {

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<BemPatrimonial> buscar(String busca) throws ModeloException {

		List<BemPatrimonial> bens = new ArrayList<BemPatrimonial>();
		List<BemPatrimonial> parcial = new ArrayList<BemPatrimonial>();
		List<String> stringsDeBusca = new ArrayList<String>();
		Query query;
		stringsDeBusca = obterStrings(busca);

		for (String s : stringsDeBusca) {
			try {
				query = entityManager
						.createQuery("SELECT b FROM BemPatrimonial b, "
								+ "BemPatrimonial_Titulos t WHERE "
								+ "t.bempatrimonial_id = b.id AND t.valor LIKE :padrao");
				query.setParameter("padrao", "%" + s + "%");
				parcial = (List<BemPatrimonial>) query.getResultList();
				for (BemPatrimonial b : parcial) {
					if (!bens.contains(b))
						bens.add(b);
				}

				query = entityManager
						.createQuery("SELECT b FROM BemPatrimonial b, "
								+ "BemPatrimonial_Descritores d WHERE "
								+ "d.bempatrimonial_id = b.id AND d.descritores LIKE :padrao");
				query.setParameter("padrao", "%" + s + "%");
				parcial = (List<BemPatrimonial>) query.getResultList();
				for (BemPatrimonial b : parcial) {
					if (!bens.contains(b))
						bens.add(b);
				}

				query = entityManager
						.createQuery("SELECT b FROM BemPatrimonial b, Autoria au, Autor a "
								+ "where b.id = au.bempatrimonial_id AND"
								+ "au.autor_id = a.id AND a.nome LIKE :padrao");
				query.setParameter("padrao", "%" + s + "%");
				parcial = (List<BemPatrimonial>) query.getResultList();
				for (BemPatrimonial b : parcial) {
					if (!bens.contains(b))
						bens.add(b);
				}
			} catch (Exception e) {
				throw new ModeloException(e);
			}
		}

		return bens;
	}

	public List<String> obterStrings(String busca) {

		List<String> lista = new ArrayList<String>();
		List<String> retorno = new ArrayList<String>();
		String[] strings = busca.split(" ");
		int nroEspacos = 0;

		for (String s : strings) {
			lista.add(s);
			nroEspacos++;
		}

		return lista;
	}

}
