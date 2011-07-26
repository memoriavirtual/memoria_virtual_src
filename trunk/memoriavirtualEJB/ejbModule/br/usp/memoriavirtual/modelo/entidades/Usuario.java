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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
public class Usuario implements Serializable, Cloneable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    @NotNull
    @Column(unique = true)
    @Pattern(regexp = "[a-z0-9!#$%&’*+/=?^_‘{|}~-]+(?:\\." + "[a-z0-9!#$%&’*+/=?^_‘{|}~-]+)*@"
	    + "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "Email invalido")
    private String email;
    private String nomeCompleto;
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
     *            se o usuario � ou n�o administrador do sistema
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

	/**
     * @param input
     *            a senha para ser criptografada ou o email para criar o id
     *            usando no convite
     */
    public String gerarHash(String input) {

    	MessageDigest md = null;
    	byte messageDigest[] = null;
    	try {
    		md = MessageDigest.getInstance("SHA-512");
    		messageDigest = md.digest(input.getBytes("UTF-8"));
    	} catch (NoSuchAlgorithmException ex) {
    		Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
    	} catch (UnsupportedEncodingException ex) {
    		Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
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
    public Object clone() throws CloneNotSupportedException {
	Usuario clone = (Usuario) super.clone();
	clone.setSenha(null);
        return clone;
    }

}
