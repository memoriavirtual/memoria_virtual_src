package br.usp.memoriavirtual.modelo.fachadas;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.remoto.RealizarLoginRemote;

@Stateless(mappedName = "RealizarLogin")
public class RealizarLogin implements RealizarLoginRemote {

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;

	public RealizarLogin() {

	}

	public Usuario realizarLogin(String usuario, String senha)
			throws ModeloException {

		Usuario usuarioAutenticado = null;
		Usuario usuarioClone = null;

		Query query;

		query = this.entityManager.createNamedQuery("login", Usuario.class);
		query.setParameter("usuario", usuario);
		query.setParameter("senha", Usuario.gerarHash(senha));

		try {
			usuarioAutenticado = (Usuario) query.getSingleResult();
			entityManager.refresh(usuarioAutenticado);
			
			if (usuarioAutenticado.isAtivo()) {
				usuarioClone = usuarioAutenticado.clone();
			}
		} catch (NoResultException e) {
			usuarioAutenticado = null;
			throw new ModeloException(e);
		}
		return usuarioClone;
	}

}
