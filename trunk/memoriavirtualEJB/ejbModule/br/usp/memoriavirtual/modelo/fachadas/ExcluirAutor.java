package br.usp.memoriavirtual.modelo.fachadas;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.Autor;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirAutorRemote;

@Stateless(mappedName = "ExcluirAutor")
public class ExcluirAutor implements ExcluirAutorRemote {

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<Autor> listarAutores(String nome) throws ModeloException {

		List<Autor> autores = new ArrayList<Autor>();

		try {
			Query query = this.entityManager
					.createQuery("SELECT a FROM Autor a WHERE a.nome LIKE :nome ORDER BY a.nome");

			query.setParameter("nome", "%" + nome + "%");
			autores = query.getResultList();
		}

		catch (Exception e) {
			throw new ModeloException(e);
		}

		return autores;
	}

	@Override
	public void excluirAutor(Autor autor) throws ModeloException {
		try {
			
			Autor autorManaged = entityManager.find(Autor.class, autor.getId());
			entityManager.remove(autorManaged);
		} catch (Exception e) {
			throw new ModeloException(e);
		}

	}

}
