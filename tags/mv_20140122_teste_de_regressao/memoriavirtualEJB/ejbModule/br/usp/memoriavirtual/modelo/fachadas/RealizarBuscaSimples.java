package br.usp.memoriavirtual.modelo.fachadas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.Acesso;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Multimidia;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.fachadas.remoto.RealizarBuscaSimplesRemote;

@Stateless(mappedName = "RealizarBuscaSimples")
public class RealizarBuscaSimples implements RealizarBuscaSimplesRemote {

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<BemPatrimonial> buscar(String busca)
			throws ModeloException {

		ArrayList<BemPatrimonial> bens = new ArrayList<BemPatrimonial>();
		List<BemPatrimonial> parcial = new ArrayList<BemPatrimonial>();
		List<String> stringsDeBusca = new ArrayList<String>();
		Query query;
		stringsDeBusca = (List<String>) obterStrings(busca);

		for (String s : stringsDeBusca) {
			s = s.trim();
			try {
				query = entityManager
						.createQuery("SELECT b FROM BemPatrimonial b WHERE LOWER(b.tituloPrincipal) LIKE LOWER(:padrao)");
				query.setParameter("padrao", "%" + s + "%");
				parcial = (List<BemPatrimonial>) query.getResultList();

				for (BemPatrimonial b : parcial) {
					if (!bens.contains(b)) {
						bens.add(b);
					}
				}
				parcial.clear();
			} catch (Exception e) {
				throw new ModeloException(e);
			}
		}

		for (String s : stringsDeBusca) {
			s = s.trim();
			try {
				query = entityManager
						.createQuery("SELECT b FROM BemPatrimonial b, BEMPATRIMONIAL_TITULOS t "
								+ "WHERE t MEMBER OF b.titulos "
								+ "AND LOWER(t.valor) LIKE LOWER(:padrao)");
				query.setParameter("padrao", "%" + s + "%");
				parcial = (List<BemPatrimonial>) query.getResultList();
				for (BemPatrimonial b : parcial) {
					if (!bens.contains(b))
						bens.add(b);
				}
				parcial.clear();

			} catch (Exception e) {
				throw new ModeloException(e);
			}
		}

		bens.trimToSize();
		return bens;
	}

	/**
	 * M�todo para gerar uma lista de strings a serem buscadas no banco a partir
	 * de uma busca do usu�rio
	 * 
	 * @param busca
	 *            String de busca inserida pelo usu�rio
	 * @return Lista de strings a serem buscadas no banco
	 */
	@SuppressWarnings("rawtypes")
	public List<String> obterStrings(String busca) {

		String[] tokens = busca.split(" ");
		List<SortedSet<Comparable>> conjuntos = gerarCombinacoes(tokens);
		List<String> buscas = new ArrayList<String>();
		List<String> tokensLista = new ArrayList<String>();
		for (int i = 0; i < tokens.length; ++i) {
			tokensLista.add(tokens[i]);
		}
		List<StringPonderada> buscasPonderadas = new ArrayList<RealizarBuscaSimples.StringPonderada>();
		List<String> elementos = new ArrayList<String>();
		for (SortedSet<Comparable> s : conjuntos) {

			StringPonderada strPonderada = new StringPonderada();
			for (int i = 0; i < s.toArray().length; ++i) {
				elementos.add(s.toArray()[i].toString());
			}
			for (String e : elementos) {

				strPonderada = strPonderada.concat(new StringPonderada(e,
						(tokensLista.size() - tokensLista.indexOf(e)) * 10));

				strPonderada = strPonderada.concat(new StringPonderada(" ", 0));

			}
			elementos.clear();
			buscasPonderadas.add(strPonderada);
			Collections.sort(buscasPonderadas);
			buscas.clear();
			for (StringPonderada p : buscasPonderadas) {
				buscas.add(p.getElemento());
			}

		}

		return buscas;
	}

	@SuppressWarnings("rawtypes")
	public List<SortedSet<Comparable>> gerarCombinacoes(String[] gerador) {

		List<SortedSet<Comparable>> combinacoes = new ArrayList<SortedSet<Comparable>>();

		for (String s : gerador) {
			combinacoes.add(new TreeSet<Comparable>(Arrays.asList(s)));
		}

		for (int nivel = 1; nivel < gerador.length; nivel++) {
			List<SortedSet<Comparable>> statusAnterior = new ArrayList<SortedSet<Comparable>>(
					combinacoes);
			for (Set<Comparable> antes : statusAnterior) {
				SortedSet<Comparable> novo = new TreeSet<Comparable>(antes);
				novo.add(gerador[nivel]);
				if (!combinacoes.contains(novo)) {
					combinacoes.add(novo);
				}
			}
		}

		return combinacoes;
	}

	@Override
	public List<Multimidia> buscarVideos(BemPatrimonial bem)
			throws ModeloException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Multimidia> buscarImagens(BemPatrimonial bem)
			throws ModeloException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Multimidia> buscarAudio(BemPatrimonial bem)
			throws ModeloException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Classe utilizada para estabelecer os crit�rios de compara��o entre duas
	 * strings ponderadas.
	 * 
	 */
	class StringPonderada implements Comparable<StringPonderada> {
		private String elemento;
		private int peso;

		/**
		 * Construtor padr�o
		 */
		public StringPonderada() {
			super();
			this.elemento = new String();
			this.peso = 0;
		}

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

		/**
		 * Concatena o elemento e soma os pesos
		 * 
		 * @param str
		 * @return
		 */
		public StringPonderada concat(StringPonderada str) {
			return new StringPonderada(this.elemento.concat(str.getElemento()),
					this.peso + str.peso);
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

	@SuppressWarnings("unchecked")
	@Override
	public boolean possuiAcesso(Usuario usuario, Instituicao instituicao)
			throws ModeloException {

		Query query;
		List<Acesso> acessos = new ArrayList<Acesso>();

		try {
			query = entityManager
					.createQuery("SELECT a FROM Acesso a WHERE a.usuario = :usuario AND a.instituicao = :instituicao");
			acessos = (List<Acesso>) query.getResultList();
		} catch (Exception e) {
			throw new ModeloException(e);
		}

		if (!acessos.isEmpty())
			return true;

		return false;
	}

}
