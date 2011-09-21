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

	public Usuario completarCadastro(String id, String email, String nome,
			String telefone, String senha, String validacao) {

		/* Busco pelo convite usando o campo ID na tabela */
		Query query = this.entityManager
				.createQuery("SELECT u FROM Usuario u WHERE u.id = :usuario");
		query.setParameter("usuario", validacao);

		Usuario usuario = null;
		Usuario usuarioCadastrado = new Usuario();

		/*
		 * Insiro no banco de dados uma nova tupla com os dados cadastrados e
		 * deleto a tupla usada para enviar o convite
		 */
		try {
			usuario = (Usuario) query.getSingleResult();
			usuarioCadastrado.setId(id);
			usuarioCadastrado.setEmail(email);
			usuarioCadastrado.setNomeCompleto(nome);
			usuarioCadastrado.setSenha(senha);
			usuarioCadastrado.setAtivo(true);
			usuarioCadastrado.setAdministrador(usuario.isAdministrador());
			entityManager.persist(usuarioCadastrado);
			entityManager.remove(usuario);
			return usuarioCadastrado.clone();

		} catch (NoResultException e) {
			usuario = null;
			e.printStackTrace();
			return null;
		}

	}

	public boolean disponibilidadeEmail(String email) {

		Query query = entityManager
				.createQuery("SELECT u FROM Usuario u WHERE u.email = :email");
		query.setParameter("email", email);

		try {
			query.getSingleResult();
			return false;
		} catch (NoResultException e) {

		}
		return true;

	}
}
