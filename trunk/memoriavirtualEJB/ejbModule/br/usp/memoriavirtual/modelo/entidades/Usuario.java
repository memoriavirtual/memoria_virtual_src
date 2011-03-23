package br.usp.memoriavirtual.modelo.entidades;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Usuario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private String email;
	private String senha;



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
