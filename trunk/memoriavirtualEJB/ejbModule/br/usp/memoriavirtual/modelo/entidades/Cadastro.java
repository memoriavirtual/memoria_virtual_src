package br.usp.memoriavirtual.modelo.entidades;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Cadastro implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
	private Usuario usuario;
	@Id
	private Instituicao instituicao;
	@Id
	private Grupo grupo;
	
	/**
	 * Construtor padrão
	 */
	public Cadastro() {
		super();
	}

	public Cadastro(Usuario usuario, Instituicao instituicao, Grupo grupo) {
		super();
		this.usuario = usuario;
		this.instituicao = instituicao;
		this.grupo = grupo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Instituicao getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}
	
	
}
