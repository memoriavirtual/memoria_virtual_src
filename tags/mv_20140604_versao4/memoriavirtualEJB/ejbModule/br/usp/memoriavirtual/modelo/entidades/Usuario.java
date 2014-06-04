package br.usp.memoriavirtual.modelo.entidades;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NamedQuery(name = "login", query = "SELECT u FROM Usuario u WHERE (u.identificacao = :usuario OR u.email = :usuario) AND u.senha = :senha AND u.ativo = true")
@Entity
@SequenceGenerator(name = "USUARIO_ID", sequenceName = "USUARIO_SEQ", allocationSize = 1)
public class Usuario implements Serializable, Cloneable {

	private static final long serialVersionUID = -2966677929188737645L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USUARIO_ID")
	private long id;

	private String identificacao;
	private String email;
	private String nomeCompleto;
	private String telefone;
	private String senha;

	@Temporal(TemporalType.DATE)
	private Date validade;

	private Boolean administrador;
	private Boolean ativo;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "usuario", cascade = CascadeType.ALL)
	private List<Acesso> acessos;

	public Usuario() {
		super();
	}

	public String getSenha() {
		return senha;
	}

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

	public Date getValidade() {
		return validade;
	}

	public void setValidade(Date validade) {
		this.validade = validade;
	}

	public Boolean isAdministrador() {
		if (this.administrador == null) {
			return false;
		}
		return administrador;
	}

	public void setAdministrador(Boolean administrador) {
		this.administrador = administrador;
	}

	public Boolean isAtivo() {
		return ativo;
	}

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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}

	public Boolean getAdministrador() {
		return administrador;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public List<Acesso> getAcessos() {
		return acessos;
	}

	public void setAcessos(List<Acesso> acessos) {
		this.acessos = acessos;
	}
}
