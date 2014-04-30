package br.usp.memoriavirtual.modelo.fachadas;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.Autor;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarAutorRemote;

@Stateless(mappedName = "EditarAutor")
public class EditarAutor implements EditarAutorRemote {

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<Autor> listarAutores(String strDeBusca) throws ModeloException {
		List<Autor> lista;

		Query query;
		query = this.entityManager
				.createQuery("SELECT a FROM Autor a WHERE LOWER(a.nome) LIKE LOWER(:busca) OR "
						+ "LOWER(a.sobrenome) LIKE LOWER(:busca) OR "
						+ "LOWER(a.codinome) LIKE LOWER(:busca) ORDER BY a.nome");
		query.setParameter("busca", "%" + strDeBusca + "%");
		try {
			lista = (List<Autor>) query.getResultList();
			return lista;
		} catch (Exception e) {
			throw new ModeloException(e);
		}
	}

	@Override
	public void editarAutor(Autor autor) throws ModeloException {

		try {
			entityManager.merge(autor);
		} catch (Exception t) {
			throw new ModeloException(t);
		}
	}

	@Override
	public Autor getAutor(Long id) throws ModeloException {
		try {
			return (Autor) entityManager.find(Autor.class, id);
		} catch (Exception e) {
			throw new ModeloException(e);
		}
	}
}
