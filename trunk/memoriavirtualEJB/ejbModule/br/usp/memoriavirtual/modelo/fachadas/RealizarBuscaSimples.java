package br.usp.memoriavirtual.modelo.fachadas;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sun.tools.ws.processor.modeler.ModelerException;

import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.fachadas.remoto.RealizarBuscaSimplesRemote;

@Stateless(mappedName = "RealizarBuscaSimples")
public class RealizarBuscaSimples implements RealizarBuscaSimplesRemote {

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;

	@Override
	public List<BemPatrimonial> buscar(String busca) throws ModeloException {

		List<BemPatrimonial> bens = new ArrayList<BemPatrimonial>();
		List<String> stringsDeBusca = new ArrayList<String>();
		Query query;
		stringsDeBusca = obterStrings(busca);

		/*for (String s : stringsDeBusca) {
			try {
				query = (Query) entityManager
						.createQuery("SELECT b FROM BemPatrimonial b");
				bens = (List<BemPatrimonial>) query.getResultList();
			} catch (Exception e) {
				throw new ModeloException(e);
			}
		}*/

		return bens;
	}

	public List<String> obterStrings(String busca) {

		List<String> lista = new ArrayList<String>();
		List<String> retorno = new ArrayList<String>();
		String[] strings = busca.split(" ");
		int nroEspacos = 0;

		for (String s : strings){
			lista.add(s);
			nroEspacos++;
		}
		
		
		
		

		return lista;
	}

}
