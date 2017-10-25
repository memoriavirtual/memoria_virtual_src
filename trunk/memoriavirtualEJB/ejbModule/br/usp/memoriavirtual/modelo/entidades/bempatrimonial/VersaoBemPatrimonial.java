package br.usp.memoriavirtual.modelo.entidades.bempatrimonial;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.xml.bind.annotation.XmlTransient;

import br.usp.memoriavirtual.modelo.entidades.Usuario;

@Entity
@SequenceGenerator(name = "VERSAOBEMPATRIMONIAL_ID", sequenceName = "VERSAOBEMPATRIMONIAL_SEQ", allocationSize = 1)
public class VersaoBemPatrimonial implements Serializable{

	private static final long serialVersionUID = -2573458818398098042L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VERSAOBEMPATRIMONIAL_ID")
	private long id;
	
	@XmlTransient
	@ManyToOne(fetch=FetchType.EAGER)
	private BemPatrimonial bemPatrimonial = null;
	
	private Timestamp timestamp = null;
	
	@Lob
	private byte[] bemPatrimonialBytes = null;
	
	@OneToOne(fetch=FetchType.EAGER)
	private Usuario usuario;
	
	public VersaoBemPatrimonial(){
		super();
		this.timestamp = new Timestamp(System.currentTimeMillis());
		this.bemPatrimonial = new BemPatrimonial();
		this.usuario = new Usuario();
	}

	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the bemPatrimonial
	 */
	@XmlTransient
	public BemPatrimonial getBemPatrimonial() {
		return bemPatrimonial;
	}

	/**
	 * @param bemPatrimonial the bemPatrimonial to set
	 */
	public void setBemPatrimonial(BemPatrimonial bemPatrimonial) {
		this.bemPatrimonial = bemPatrimonial;
	}

	/**
	 * @return the timestamp
	 */
	public Timestamp getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the bemPatrimonialBytes
	 */
	public byte[] getBemPatrimonialBytes() {
		return bemPatrimonialBytes;
	}

	/**
	 * @param bemPatrimonialBytes the bemPatrimonialBytes to set
	 */
	public void setBemPatrimonialBytes(byte[] bemPatrimonialBytes) {
		this.bemPatrimonialBytes = bemPatrimonialBytes;
	}

	
	
}
