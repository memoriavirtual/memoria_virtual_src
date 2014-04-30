package br.usp.memoriavirtual.modelo.entidades;

import java.io.Serializable;

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
import javax.xml.bind.annotation.XmlTransient;

@Entity
@SequenceGenerator(name = "MULTIMIDIA_ID", sequenceName = "MULTIMIDIA_SEQ", allocationSize = 1)
public class Multimidia implements Serializable {

	private static final long serialVersionUID = -1246434877940087730L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MULTIMIDIA_ID")
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CONTAINERMULTIMIDIA_ID")
	@XmlTransient
	private ContainerMultimidia containerMultimidia;

	@Lob
	@Column(columnDefinition = "BYTEA")
	private byte[] content;

	@Lob
	@Column(columnDefinition = "BYTEA")
	private Multimidia thumb;

	private String nome;
	private String contentType;
	private String descricao;

	public Multimidia() {
		super();
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@XmlTransient
	public ContainerMultimidia getEntidadeComMidia() {
		return containerMultimidia;
	}

	public void setEntidadeComMidia(ContainerMultimidia entidadeComMidia) {
		this.containerMultimidia = entidadeComMidia;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isImagem() {
		return this.getContentType().contains("image");
	}

	public boolean isVideo() {
		return this.getContentType().contains("video");
	}

	public boolean isAudio() {
		return this.getContentType().contains("audio");
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public long getId() {
		return id;
	}

	@XmlTransient
	public ContainerMultimidia getContainerMultimidia() {
		return containerMultimidia;
	}

	public void setContainerMultimidia(ContainerMultimidia containerMultimidia) {
		this.containerMultimidia = containerMultimidia;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Multimidia getThumb() {
		return thumb;
	}

	public void setThumb(Multimidia thumb) {
		this.thumb = thumb;
	}
}
