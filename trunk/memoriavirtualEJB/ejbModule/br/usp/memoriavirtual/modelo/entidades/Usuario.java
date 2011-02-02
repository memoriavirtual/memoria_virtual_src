package br.usp.memoriavirtual.modelo.entidades;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Entity
public class Usuario {

	@Id
	private String id;
	private String email;
	private String senha;

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;

	/**
	 * Construtor padrão
	 */
	public Usuario() {
		super();
	}


	public Usuario(String id, String email, String senha) {
		this();
		this.id = id;
		this.email = email;
		this.senha = senha;
	}
	
	
	/**
	 * return resultado Resultado da valida��o do login
	 */
	public Usuario realizarLogin(String usuario, String senha) {

		Query query;

		query = this.entityManager
				.createQuery("SELECT u FROM Usuario WHERE (id = :usuario OR email = :usuario) AND senha = :senha");
		query.setParameter("usuario", usuario);
		query.setParameter("senha", senha);

		Usuario resultado;
		try {
		    resultado = (Usuario) query.getSingleResult();
		    resultado.setSenha(null);
		} catch (NoResultException e) {
			resultado = null;
		}

		return resultado;
	}


	/**
	 * @return the login
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param login
	 *            the login to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the senha
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * @param senha
	 *            the senha to set
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
