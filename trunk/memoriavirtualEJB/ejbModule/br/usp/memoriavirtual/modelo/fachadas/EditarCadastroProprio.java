package br.usp.memoriavirtual.modelo.fachadas;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.Usuario;

import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarCadastroProprioRemote;

@Stateless(mappedName = "EditarCadastroProprio")
public class EditarCadastroProprio implements EditarCadastroProprioRemote {

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;


	public void atualizarDadosUsuario(String id, String novoEmail,
			String novoNomeCompleto, String novoTelefone, String novaSenha)
			throws ModeloException {
		Usuario usuario = null;
		usuario = this.entityManager.find(Usuario.class, id);
		if (usuario != null) {
			usuario.setEmail(novoEmail);
			usuario.setNomeCompleto(novoNomeCompleto);
			usuario.setTelefone(novoTelefone);
			usuario.setSenha(novaSenha);
			entityManager.flush();
		} else
			throw new ModeloException("Usuario não encontrado");
	}

	public Usuario recuperarDadosUsuario(String id) {

		Query query = entityManager
				.createQuery("SELECT u FROM Usuario u WHERE u.id = :id");
		query.setParameter("id", id);
		Usuario usuario = null;
		usuario = (Usuario) query.getSingleResult();
		return usuario;
	}

}
