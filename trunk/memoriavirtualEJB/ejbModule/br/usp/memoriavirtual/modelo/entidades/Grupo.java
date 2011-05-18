package br.usp.memoriavirtual.modelo.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

public class Grupo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id private String id;
	@NotNull
	@Column(unique = true)
	private String nome;
	
	/**
	 * Construtor padrão
	 */
	public Grupo() {
		super();
	}

	public Grupo(String id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	

}
