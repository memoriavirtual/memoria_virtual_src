package br.usp.memoriavirtual.modelo.entidades;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class UsuarioInstituicao implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id private String numRegistroInstituicao;
	@Id private String idUsuario;
	@NotNull private String tipo;

	/**
	 * Construtor padrão
	 */
	public UsuarioInstituicao() {
		super();
	}


	public UsuarioInstituicao(String numRegistroInstituicao, String idUsuario, String tipo) {
		this();
		this.numRegistroInstituicao = numRegistroInstituicao;
		this.idUsuario = idUsuario;
		this.tipo = tipo;
	}


	/**
	 * @return O numero de registro da instituição
	 */
	public String getNumRegistroInstituicao() {
		return numRegistroInstituicao;
	}


	/**
	 * @param numRegistroInstituicao O numero de registro da instituição a ser definido
	 */
	public void setNumRegistroInstituicao(String numRegistroInstituicao) {
		this.numRegistroInstituicao = numRegistroInstituicao;
	}


	/**
	 * @return O id do usuario
	 */
	public String getIdUsuario() {
		return idUsuario;
	}


	/**
	 * @param idUsuario O id do usuario a ser definido
	 */
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}


	/**
	 * @return A categoria do usuario
	 */
	public String getTipo() {
		return tipo;
	}


	/**
	 * @param A categoria do usuario a ser definida
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
}