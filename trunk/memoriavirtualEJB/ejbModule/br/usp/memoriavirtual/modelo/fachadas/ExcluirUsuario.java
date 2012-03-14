package br.usp.memoriavirtual.modelo.fachadas;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirUsuarioRemote;

@Stateless(mappedName = "ExcluirUsuario")
public class ExcluirUsuario implements ExcluirUsuarioRemote {

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<Usuario> listarUsuarios(String parteNome,
			Boolean isAdministrador) {

		List<Usuario> usuarios;
		Query query;
		if (isAdministrador) {
			query = this.entityManager
					.createQuery("SELECT a FROM Usuario a WHERE a.nomeCompleto LIKE :parteNome ORDER BY a.id ");
			query.setParameter("parteNome", "%" + parteNome + "%");
		} else {
			query = this.entityManager
					.createQuery("SELECT a FROM Usuario a WHERE a.administrador = FALSE AND a.nomeCompleto LIKE :parteNome ORDER BY a.id ");
			query.setParameter("parteNome", "%" + parteNome + "%");
		}
		try {
			usuarios = (List<Usuario>) query.getResultList();
			return usuarios;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}
