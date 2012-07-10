package br.usp.memoriavirtual.modelo.entidades.bempatrimonial;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

@Embeddable
public class AudioVisual implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1201570747232923797L;
	public AudioVisual() {
	}
	
	
	private String titulo;
	private String tipo;
	private String descricao;
	@Lob  
    @Column(columnDefinition = "BYTEA")  
    private byte[] imagemVideo;
	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}
	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}
	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	/**
	 * @return the imagemVideo
	 */
	public byte[] getImagemVideo() {
		return imagemVideo;
	}
	/**
	 * @param imagemVideo the imagemVideo to set
	 */
	public void setImagemVideo(byte[] imagemVideo) {
		this.imagemVideo = imagemVideo;
	}
}
