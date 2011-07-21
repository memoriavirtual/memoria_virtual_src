package br.usp.memoriavirtual.modelo.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Instituicao implements Serializable{

	
	private static final long serialVersionUID = 1L;
	@Id
	@NotNull 
	@Column(unique = true)
	private String nome;
	@Column(unique = true)
	private String email;
	@Column(unique = true)
	private String localizacao;
	@Column(unique = true)
	private String endereco;
	@Column(unique = true)
	private String cidade;
	@Column(unique = true)
	private String estado;
	@Column(unique = true)
	private String cep;
	@Column(unique = true)
	private String telefone;

	/**
	 * Construtor padrão
	 */
	public Instituicao() {
		super();
	}
	
	/**
	 * @return O nome da instituição
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            Define o nome da instituição
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return O email da instituição
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * @param email
	 *            Define o email da instituição
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * @return A localizacao da instituição
	 */
	public String getLocalizacao() {
		return localizacao;
	}
	
	/**
	 * @param localizacao
	 *            Define a localizacao da instituição
	 */
	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}
	
	/**
	 * @return O endereco da instituição
	 */
	public String getEndereco() {
		return endereco;
	}
	
	/**
	 * @param endereco
	 *            Define o endereco da instituição
	 */
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	/**
	 * @return A cidade da instituição
	 */
	public String getCidade() {
		return cidade;
	}
	
	/**
	 * @param cidade
	 *            Define a cidade da instituição
	 */
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	/**
	 * @return O estado(UF) da instituição
	 */
	public String getEstado() {
		return estado;
	}
	
	/**
	 * @param estado
	 *            Define o estado(UF) da instituição
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	/**
	 * @return O cep da instituição
	 */
	public String getCep() {
		return cep;
	}
	
	/**
	 * @param cep
	 *            Define o cep da instituição
	 */
	public void setCep(String cep) {
		this.cep = cep;
	}
	
	/**
	 * @return O telefone da instituição
	 */
	public String getTelefone() {
		return telefone;
	}
	
	/**
	 * @param telefone
	 *            Define o telefone da instituição
	 */
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
}
