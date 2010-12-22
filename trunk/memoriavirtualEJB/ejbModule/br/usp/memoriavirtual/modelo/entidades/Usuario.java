package br.usp.memoriavirtual.modelo.entidades;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Usuario {

	@Id
	private String login;
	private String senha;

	/**
	 * Construtor padrão
	 */
	public Usuario() {

	}

	/**
	 * Construtor com login
	 * 
	 * @param login
	 *            Login do usuário
	 */
	public Usuario(String login) {
		super();
		this.login = login;
	}
	

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login
	 *            the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
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
}
