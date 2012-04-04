/**
 * 
 */
package br.usp.memoriavirtual.modelo.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author bigmac
 *
 */
@Entity
@SequenceGenerator(name = "AUTOR_ID", sequenceName = "AUTOR_SEQ", allocationSize = 1)
public class Autor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 238135871232282769L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUTOR_ID")
	private long id;
	private String tipoAutoria;
	private String nome;
	private String sobrenome;
	private String codinome;
	private String atividade;
	@Temporal(TemporalType.DATE)
	private Date nascimento;
	@Temporal(TemporalType.DATE)
	private Date obito;
	/**
	 * 
	 */
	public Autor() {
		
	}
	public Autor(String tipoAutoria, String nome, String sobrenome,
			String codinome, String atividade, Date nascimento, Date obito) {
		super();
		this.tipoAutoria = tipoAutoria;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.codinome = codinome;
		this.atividade = atividade;
		this.nascimento = nascimento;
		this.obito = obito;
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @return the tipoAuditoria
	 */
	public String getTipoAutoria() {
		return tipoAutoria;
	}
	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * @return the sobrenome
	 */
	public String getSobrenome() {
		return sobrenome;
	}
	/**
	 * @return the codinome
	 */
	public String getCodinome() {
		return codinome;
	}
	/**
	 * @return the atividade
	 */
	public String getAtividade() {
		return atividade;
	}
	/**
	 * @return the nascimento
	 */
	public Date getNascimento() {
		return nascimento;
	}
	/**
	 * @return the obito
	 */
	public Date getObito() {
		return obito;
	}
	/**
	 * @param tipoAuditoria the tipoAuditoria to set
	 */
	public void setTipoAuditoria(String tipoAutoria) {
		this.tipoAutoria = tipoAutoria;
	}
	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	/**
	 * @param sobrenome the sobrenome to set
	 */
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	/**
	 * @param codinome the codinome to set
	 */
	public void setCodinome(String codinome) {
		this.codinome = codinome;
	}
	/**
	 * @param atividade the atividade to set
	 */
	public void setAtividade(String atividade) {
		this.atividade = atividade;
	}
	/**
	 * @param nascimento the nascimento to set
	 */
	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}
	/**
	 * @param obito the obito to set
	 */
	public void setObito(Date obito) {
		this.obito = obito;
	}

}
