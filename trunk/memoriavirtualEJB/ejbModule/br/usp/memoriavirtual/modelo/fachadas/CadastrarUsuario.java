package br.usp.memoriavirtual.modelo.fachadas;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.Acesso;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.remoto.CadastrarUsuarioRemote;

@Stateless(mappedName = "CadastrarUsuario")
public class CadastrarUsuario implements CadastrarUsuarioRemote {

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;

	public Usuario verificarConvite(String convite) throws ModeloException {
		Query query = this.entityManager.createQuery("SELECT u FROM Usuario u WHERE u.id = :usuario");
		query.setParameter("usuario", convite);

		Usuario usuarioAutenticado = null;

		try {
			usuarioAutenticado = (Usuario) query.getSingleResult();
		} catch (NoResultException e) {
			usuarioAutenticado = null;
			throw new ModeloException(e);
		}

		return usuarioAutenticado;
	}

	
	@SuppressWarnings("unchecked")
	public void cadastrarUsuario(Usuario usuario, String validacao) throws ModeloException {

		/* Busco pelo convite usando o campo ID na tabela */
		Query queryUsuario = this.entityManager.createQuery("SELECT u FROM Usuario u WHERE u.id = :usuario");
		queryUsuario.setParameter("usuario", validacao);
		
		Usuario convite = null;
		/*
		 * Insiro no banco de dados uma nova tupla com os dados cadastrados e deleto a tupla usada para enviar o convite
		 */
		try {
			convite = (Usuario) queryUsuario.getSingleResult();
		} catch (NoResultException e) {
			throw new ModeloException("Convite invalido", e);
		}
		List<Acesso> acessos;
		
		Query queryAcesso = this.entityManager.createQuery("SELECT a FROM Acesso a Where a.usuario = :convite");
		queryAcesso.setParameter("convite", convite);
		
		acessos = (List<Acesso>) queryAcesso.getResultList();
		
		for(Acesso acesso:acessos){
			entityManager.remove(acesso);
		}
		
		usuario.setAtivo(true);
		usuario.setAdministrador(convite.isAdministrador());
		convite.setEmail("");
		entityManager.remove(convite);
		entityManager.persist(usuario);

		
		for(Acesso acesso:acessos){
			Acesso novoAcesso = new Acesso(usuario, acesso.getInstituicao(), acesso.getGrupo());
			entityManager.persist(novoAcesso);
		}
	}
}
