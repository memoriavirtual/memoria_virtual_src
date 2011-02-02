package br.usp.memoriavirtual.modelo.entidades;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Usuario {

	@Id
	private String id;
	private String email;
	private String senha;

	/**
	 * Construtor padr√£o
	 */
	public Usuario() {
		super();
	}

	/**
	 * Construtor com login
	 * 
	 * @param login
	 *            Login do usu√°rio
	 */
	public Usuario(String login) {
		this();

		if (login.contains("@") != true) {   //Verifica se a autentificaÁ„o possui @ ou nao...
			this.id = login;				 //Se nao possuir È id.. se possuir È email
			this.email = null;
		} else {
			this.id = null;
			this.email = login;
		}
	}

	public Usuario(String login, String senha) {
		this(login);
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
