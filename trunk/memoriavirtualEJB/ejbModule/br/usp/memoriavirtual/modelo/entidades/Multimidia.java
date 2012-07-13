package br.usp.memoriavirtual.modelo.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;


@Entity
@SequenceGenerator(name = "MULTIMIDIA_ID", sequenceName = "MULTIMIDIA_SEQ", allocationSize = 1)
public class Multimidia {

	public Multimidia() {
	}
	
	/**
	 * @param nome
	 * @param contentType
	 * @param content
	 * @param tamanho
	 */
	public Multimidia(String nome, String contentType, byte[] content,
			Integer tamanho) {
		super();
		this.nome = nome;
		this.contentType = contentType;
		this.content = content;
		this.tamanho = tamanho;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MULTIMIDIA_ID")
	private Integer id;
	private String nome;
	private String contentType;
	private byte[] content;
	private Integer tamanho;
	
	
	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}
	/**
	 * @param contentType the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	/**
	 * @return the content
	 */
	public byte[] getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(byte[] content) {
		this.content = content;
	}
	/**
	 * @return the tamanho
	 */
	public Integer getTamanho() {
		return tamanho;
	}
	/**
	 * @param tamanho the tamanho to set
	 */
	public void setTamanho(Integer tamanho) {
		this.tamanho = tamanho;
	}
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
}
