/**
 * 
 */
package br.usp.memoriavirtual.modelo.fachadas;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.Autor;
import br.usp.memoriavirtual.modelo.fachadas.remoto.CadastrarAutorRemote;

/**
 * @author bigmac
 * 
 */
@Stateless(mappedName = "CadastrarAutor")
public class CadastrarAutor implements CadastrarAutorRemote {

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public void cadastrarAutor(Autor autor) throws ModeloException {
		List<Autor> ins = new ArrayList<Autor>();
		Query query;

		query = entityManager
				.createQuery("SELECT a FROM Autor a WHERE a.nome =:nome ");
		query.setParameter("nome", autor.getNome());
		try {
			ins = (List<Autor>) query.getResultList();
		} catch (Exception e) {

		}
		if (!ins.isEmpty()) {
			query = entityManager
					.createQuery("SELECT a FROM Autor a WHERE a.nome =:nome AND a.sobrenome =:sobrenome");
			query.setParameter("nome", autor.getNome());
			query.setParameter("sobrenome", autor.getSobrenome());
			try {
				ins = (List<Autor>) query.getResultList();
			} catch (Exception e) {

			}
			if (!ins.isEmpty()) {
				query = entityManager
						.createQuery("SELECT a FROM Autor a WHERE a.nome =:nome AND a.sobrenome =:sobrenome AND a.codinome =:codinome");
				query.setParameter("nome", autor.getNome());
				query.setParameter("sobrenome", autor.getSobrenome());
				query.setParameter("codinome", autor.getCodinome());
				try {
					ins = (List<Autor>) query.getResultList();
				} catch (Exception e) {

				}
				if (!ins.isEmpty()) {
					query = entityManager
							.createQuery("SELECT a FROM Autor a WHERE a.nome =:nome AND a.sobrenome =:sobrenome AND a.codinome =:codinome AND a.nascimento =:nascimento");
					query.setParameter("nome", autor.getNome());
					query.setParameter("sobrenome", autor.getSobrenome());
					query.setParameter("codinome", autor.getCodinome());
					query.setParameter("nascimento", autor.getNascimento());
					try {
						ins = (List<Autor>) query.getResultList();
					} catch (Exception e) {

					}
					if (!ins.isEmpty()) {
						query = entityManager
								.createQuery("SELECT a FROM Autor a WHERE a.nome =:nome AND a.sobrenome =:sobrenome AND a.codinome =:codinome AND a.nascimento =:nascimento AND a.obito =:obito");
						query.setParameter("nome", autor.getNome());
						query.setParameter("sobrenome", autor.getSobrenome());
						query.setParameter("codinome", autor.getCodinome());
						query.setParameter("nascimento", autor.getNascimento());
						query.setParameter("obito", autor.getObito());
						try {
							ins = (List<Autor>) query.getResultList();
						} catch (Exception e) {

						}
						if (!ins.isEmpty()) {
							query = entityManager
									.createQuery("SELECT a FROM Autor a WHERE a.nome =:nome AND a.sobrenome =:sobrenome AND a.codinome =:codinome AND a.nascimento =:nascimento AND a.obito =:obito AND a.atividade =:atividade");
							query.setParameter("nome", autor.getNome());
							query.setParameter("sobrenome",
									autor.getSobrenome());
							query.setParameter("codinome", autor.getCodinome());
							query.setParameter("nascimento",
									autor.getNascimento());
							query.setParameter("obito", autor.getObito());
							query.setParameter("atividade", autor.getAtividade());
							try {
								ins = (List<Autor>) query.getResultList();
							} catch (Exception e) {

							}
							if (!ins.isEmpty()) {
								throw new ModeloException("Autor idêntico");
							}
						}
					}
				}
			}
		}
		entityManager.persist(autor);
	}

}
