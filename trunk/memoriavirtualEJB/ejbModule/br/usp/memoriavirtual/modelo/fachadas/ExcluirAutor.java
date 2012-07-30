package br.usp.memoriavirtual.modelo.fachadas;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.usp.memoriavirtual.modelo.entidades.Autor;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirAutorRemote;

@Stateless(mappedName = "ExcluirAutor")
public class ExcluirAutor implements ExcluirAutorRemote {

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;

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
