package br.usp.memoriavirtual.modelo.entidades;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;


@Entity
@SequenceGenerator(name = "MULTIMIDIA_ID", sequenceName = "MULTIMIDIA_SEQ", allocationSize = 1)
public class Multimidia implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1246434877940087730L;
	public Multimidia() {
	}
	
	/**
	 * @param nome
	 * @param contentType
	 * @param content
	 * @param tamanho
	 */
	public Multimidia(String nome, byte[] content, String contentType , String descricao ) {
		super();
		this.nome = nome;
		this.content = content;
		this.descricao = descricao;
		this.contentType = contentType;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MULTIMIDIA_ID")
	private long id;
	@ManyToOne(cascade=CascadeType.ALL , fetch=FetchType.EAGER)
	@JoinColumn(name="CONTAINERMULTIMIDIA_ID")
	private ContainerMultimidia containerMultimidia;
	private String nome; 
	private String contentType;
	private String descricao;
	@Lob  
    @Column(columnDefinition = "BYTEA")
	private byte[] content;
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
	 * @return the entidadeComMidia
	 */
	public ContainerMultimidia getEntidadeComMidia() {
		return containerMultimidia;
	}

	/**
	 * @param entidadeComMidia the entidadeComMidia to set
	 */
	public void setEntidadeComMidia(ContainerMultimidia entidadeComMidia) {
		this.containerMultimidia = entidadeComMidia;
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
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return the containerMultimidia
	 */
	public ContainerMultimidia getContainerMultimidia() {
		return containerMultimidia;
	}

	/**
	 * @param containerMultimidia the containerMultimidia to set
	 */
	public void setContainerMultimidia(ContainerMultimidia containerMultimidia) {
		this.containerMultimidia = containerMultimidia;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	
	
}
