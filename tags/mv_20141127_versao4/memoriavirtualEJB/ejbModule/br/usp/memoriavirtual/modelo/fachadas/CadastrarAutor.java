package br.usp.memoriavirtual.modelo.fachadas;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.usp.memoriavirtual.modelo.entidades.Autor;
import br.usp.memoriavirtual.modelo.fachadas.remoto.CadastrarAutorRemote;

@Stateless(mappedName = "CadastrarAutor")
public class CadastrarAutor implements CadastrarAutorRemote {

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;

	public void cadastrarAutor(Autor autor) throws ModeloException {
		try {
			entityManager.persist(autor);
		} catch (Exception e) {
			throw new ModeloException(e);
		}
	}
}
