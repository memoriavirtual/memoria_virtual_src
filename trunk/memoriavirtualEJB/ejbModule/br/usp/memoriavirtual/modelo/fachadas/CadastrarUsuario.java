package br.usp.memoriavirtual.modelo.fachadas;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.remoto.CadastrarUsuarioRemote;

@Stateless(mappedName = "CadastrarUsuario")
public class CadastrarUsuario implements CadastrarUsuarioRemote {

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;

	public void completarCadastro(Usuario usuario, String validacao)
			throws ModeloException {

		/* Busco pelo convite usando o campo ID na tabela */
		Query query = this.entityManager
				.createQuery("SELECT u FROM Usuario u WHERE u.id = :usuario");
		query.setParameter("usuario", validacao);

		Usuario convite = null;

		/*
		 * Insiro no banco de dados uma nova tupla com os dados cadastrados e
		 * deleto a tupla usada para enviar o convite
		 */
		try {
			convite = (Usuario) query.getSingleResult();
		} catch (NoResultException e) {
			throw new ModeloException("Convite invalido", e);
		}
		usuario.setAtivo(true);
		usuario.setAdministrador(convite.isAdministrador());
		entityManager.persist(usuario);
		entityManager.remove(usuario);
	}
}
