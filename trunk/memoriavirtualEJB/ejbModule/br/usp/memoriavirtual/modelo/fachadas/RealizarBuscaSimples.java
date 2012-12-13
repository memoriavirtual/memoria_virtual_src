package br.usp.memoriavirtual.modelo.fachadas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Comparator;
import java.util.Iterator;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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

	/**
	 * Método para gerar uma lista de strings a serem buscadas no banco a partir
	 * de uma busca do usuário
	 * 
	 * @param busca
	 *            String de busca inserida pelo usuário
	 * @return Lista de strings a serem buscadas no banco
	 */
	@SuppressWarnings("rawtypes")
	public List<String> obterStrings(String busca) {

		String[] tokens = busca.split(" ");
		List<SortedSet<Comparable>> conjuntos = gerarCombinacoes(tokens);
		List<String> buscas = new ArrayList<String>();
		for (SortedSet<Comparable> s : conjuntos) {
			String elemento = "";
			List<String> el = new ArrayList<String>();
			for(int i = 0 ; i < s.toArray().length; ++i){
				el.add(s.toArray()[i].toString());
			}
			System.out.println("el: " + el);
			for (String e : el) {
				elemento.concat(e);
			}
			buscas.add(elemento);

		}
		System.out.println(buscas);
		return buscas;
	}

	@SuppressWarnings("rawtypes")
	public List<SortedSet<Comparable>> gerarCombinacoes(String[] status) {

		List<SortedSet<Comparable>> allCombList = new ArrayList<SortedSet<Comparable>>();

		for (String nstatus : status) {
			allCombList.add(new TreeSet<Comparable>(Arrays.asList(nstatus)));
		}

		for (int nivel = 1; nivel < status.length; nivel++) {
			List<SortedSet<Comparable>> statusAntes = new ArrayList<SortedSet<Comparable>>(
					allCombList);
			for (Set<Comparable> antes : statusAntes) {
				SortedSet<Comparable> novo = new TreeSet<Comparable>(antes);
				novo.add(status[nivel]);
				if (!allCombList.contains(novo)) {
					allCombList.add(novo);
				}
			}
		}

		Collections.sort(allCombList, new Comparator<SortedSet<Comparable>>() {
			@SuppressWarnings("unchecked")
			@Override
			public int compare(SortedSet<Comparable> o1,
					SortedSet<Comparable> o2) {
				int sizeComp = o1.size() - o2.size();
				if (sizeComp == 0) {
					Iterator<Comparable> o1iIterator = o1.iterator();
					Iterator<Comparable> o2iIterator = o2.iterator();
					while (sizeComp == 0 && o1iIterator.hasNext()) {
						sizeComp = o1iIterator.next().compareTo(
								o2iIterator.next());
					}
				}
				return sizeComp;

			}
		});

		return allCombList;
	}

	/**
	 * Classe utilizada para estabelecer os critérios de comparação entre duas
	 * strings ponderadas.
	 * 
	 */
	class StringPonderada implements Comparable<StringPonderada> {
		String elemento;
		int peso;

		/**
		 * Construtor que popula os campos
		 * 
		 * @param elemento
		 *            String
		 * @param peso
		 *            Peso da string
		 */
		public StringPonderada(String elemento, int peso) {
			super();
			this.elemento = elemento;
			this.peso = peso;
		}

		@Override
		public int compareTo(StringPonderada outraString) {
			if (this.peso > outraString.peso)
				return -1;
			else if (this.peso < outraString.peso)
				return 1;
			else
				return 0;
		}

		public String getElemento() {
			return elemento;
		}

		public void setElemento(String elemento) {
			this.elemento = elemento;
		}

		public int getPeso() {
			return peso;
		}

		public void setPeso(int peso) {
			this.peso = peso;
		}

	}

}
