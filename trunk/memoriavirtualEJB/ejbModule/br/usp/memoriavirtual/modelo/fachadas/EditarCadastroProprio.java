package br.usp.memoriavirtual.modelo.fachadas;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarCadastroProprioRemote;

@Stateless(mappedName = "EditarCadastroProprio")
public class EditarCadastroProprio implements EditarCadastroProprioRemote {

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;

	public void editar(Usuario usuario) throws ModeloException {
		try {
			entityManager.merge(usuario);
			entityManager.flush();
		} catch (Exception e) {
			throw new ModeloException(e);
		}
	}

}
