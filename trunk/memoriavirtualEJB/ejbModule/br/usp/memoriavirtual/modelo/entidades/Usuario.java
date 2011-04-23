package br.usp.memoriavirtual.modelo.entidades;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Date;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;

@Entity
public class Usuario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id private String id;
	@Id
	@Pattern(regexp="[a-z0-9!#$%&’*+/=?^_‘{|}~-]+(?:\\."
		+"[a-z0-9!#$%&’*+/=?^_‘{|}~-]+)*@"
		+"(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
		message="{invalid.email}")
	private String email;
	private String senha;
	private Date validade;


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
		this.senha = gerarHash(senha);
	
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
	public void setValidade(java.util.Date validade){
		java.sql.Date temp = new Date(validade.getTime());
		this.validade = temp;
	}

	/**
	 * @param input
	 * 				a senha para ser criptografada ou o email para criar o id usando no convite
	 */
	public String gerarHash(String input){
		
		MessageDigest md = null;
        byte messageDigest[] = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
            messageDigest = md.digest(input.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Converte para hexa antes de utilizar a senha criptografada
        StringBuilder hexString = new StringBuilder();
        for (byte b : messageDigest) {
            hexString.append(String.format("%02X", 0xFF & b));
        }
        
        String inputCifrado = hexString.toString();
        
		return inputCifrado;
	}
	
}
