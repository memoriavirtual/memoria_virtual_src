package br.usp.memoriavirtual.modelo.entidades.bempatrimonial;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@SequenceGenerator(name = "REVISAO_ID", sequenceName = "REVISAO_SEQ", allocationSize = 1)
public class Revisao implements Serializable{

	private static final long serialVersionUID = 3733542902798023334L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REVISAO_ID")
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private BemPatrimonial bemPatrimonial;
	
	private Date dataRevisao;
	
	private String anotacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataRevisao() {
		return dataRevisao;
	}

	public void setDataRevisao(Date dataRevisao) {
		this.dataRevisao = dataRevisao;
	}

	public String getAnotacao() {
		return anotacao;
	}

	public void setAnotacao(String anotacao) {
		this.anotacao = anotacao;
	}

	@XmlTransient
	public BemPatrimonial getBemPatrimonial() {
		return bemPatrimonial;
	}

	public void setBemPatrimonial(BemPatrimonial bemPatrimonial) {
		this.bemPatrimonial = bemPatrimonial;
	}
	}
