package br.usp.memoriavirtual.modelo.entidades.bempatrimonial;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.sun.jmx.snmp.Timestamp;

@Entity
@SequenceGenerator(name = "VERSAOBEMPATRIMONIAL_ID", sequenceName = "VERSAOBEMPATRIMONIAL_SEQ", allocationSize = 1)
public class VersaoBemPatrimonial implements Serializable{

	private static final long serialVersionUID = -2573458818398098042L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VERSAOBEMPATRIMONIAL_ID")
	private long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private BemPatrimonial bemPatrimonial = null;
	
	private Timestamp timestamp = null;
	
	@Lob
	private byte[] bem = null;
	
	public VersaoBemPatrimonial(){
		super();
		this.timestamp = new Timestamp(System.currentTimeMillis());
		this.bemPatrimonial = new BemPatrimonial();
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
	 * @return the bem
	 */
	public byte[] getBem() {
		return bem;
	}

	/**
	 * @param bem the bem to set
	 */
	public void setBem(byte[] bem) {
		this.bem = bem;
	}
	
}
