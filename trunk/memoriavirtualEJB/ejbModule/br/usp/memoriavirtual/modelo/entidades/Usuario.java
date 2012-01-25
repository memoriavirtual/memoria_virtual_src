package br.usp.memoriavirtual.modelo.entidades;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@NamedQuery(name = "login", query = "SELECT u FROM Usuario u WHERE (u.id = :usuario OR u.email = :usuario) AND u.senha = :senha AND u.ativo = true")
@Entity
public class Usuario implements Serializable, Cloneable {
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -2966677929188737645L;
	@Id
	private String id;
	@NotNull
	@Column(unique = true)
	@Pattern(regexp = "[a-z0-9!#$%&’*+/=?^_‘{|}~-]+(?:\\."
			+ "[a-z0-9!#$%&’*+/=?^_‘{|}~-]+)*@"
			+ "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "Email invalido")
	private String email;
	private String nomeCompleto;
	private String telefone;
	private String senha;
	private Date validade;
	@NotNull
	private Boolean administrador;
	@NotNull
	private Boolean ativo;

	/**
	 * Construtor padrão
	 */
	public Usuario() {
		super();
	}

	public Usuario(String id, String email, String nomeCompleto, String telefone, String senha){
		this.id = id;
		this.email = email;
		this.nomeCompleto = nomeCompleto;
		this.telefone = telefone;
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
		String senhaCriptografada = gerarHash(senha);
		this.senha = senhaCriptografada;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return validade do cadastro
	 */
	public Date getValidade() {
		return validade;
	}

	/**
	 * @param validade
	 *            a data limite da validade
	 */
	public void setValidade(java.util.Date validade) {
		java.sql.Date temp = new Date(validade.getTime());
		this.validade = temp;
	}

	public Boolean isAdministrador() {
		return administrador;
	}

	/**
	 * @param administrador
	 *            se o usuario é ou não administrador do sistema
	 */

	public void setAdministrador(Boolean administrador) {
		this.administrador = administrador;
	}

	public Boolean isAtivo() {
		return ativo;
	}

	/**
	 * @param ativo
	 *            Se o usuario ja fez cadastro
	 */

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	
	
	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	/**
	 * @param input
	 *            a senha para ser criptografada ou o email para criar o id
	 *            usando no convite
	 */
	public static String gerarHash(String input) {

		MessageDigest md = null;
		byte messageDigest[] = null;
		try {
			md = MessageDigest.getInstance("SHA-512");
			messageDigest = md.digest(input.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException ex) {
			Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null,
					ex);
		} catch (UnsupportedEncodingException ex) {
			Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null,
					ex);
		} catch (NullPointerException ex) {
			return null;
		}
		// Converte para hexa antes de utilizar a senha criptografada
		StringBuilder hexString = new StringBuilder();
		for (byte b : messageDigest) {
			hexString.append(String.format("%02X", 0xFF & b));
		}
		String inputCifrado = hexString.toString();
		return inputCifrado;
	}

	@Override
	public Usuario clone() {

		Usuario clone = null;
		try {
			clone = (Usuario) super.clone();
			clone.setSenha(null);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return clone;
	}
}
