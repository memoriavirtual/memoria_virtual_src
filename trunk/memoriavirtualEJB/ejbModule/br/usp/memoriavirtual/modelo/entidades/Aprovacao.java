package br.usp.memoriavirtual.modelo.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@SequenceGenerator(name = "APROVACAO_ID", sequenceName = "APROVACAO_SEQ", allocationSize = 1)
public class Aprovacao implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7916023027694966362L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APROVACAO_ID")
	private Long id;

	@Temporal(TemporalType.DATE)
	private Date data;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "APROVADOR")
	private Usuario aprovador;

	@Temporal(TemporalType.DATE)
	private Date expiracao;

	private String chaveEstrangeira; //para Instituição, será seu Nome

	private String tabelaEstrangeira; //Nome da Classe

	public Aprovacao() {
		super();
	}

	/**
	 * Construtor
	 * 
	 * @param data
	 * @param aprovador
	 * @param expiracao
	 * @param chaveEstrangeira
	 * @param tabelaEstrangeira
	 */
	public Aprovacao(Date data, Usuario aprovador, Date expiracao, String chaveEstrangeira, String tabelaEstrangeira) {
		super();
		this.data = data;
		this.aprovador = aprovador;
		this.expiracao = expiracao;
		this.chaveEstrangeira = chaveEstrangeira;
		this.tabelaEstrangeira = tabelaEstrangeira;
	}

	/**
	 * @return the data
	 */
	public Date getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(Date data) {
		this.data = data;
	}

	/**
	 * @return the aprovador
	 */
	public Usuario getAprovador() {
		return aprovador;
	}

	/**
	 * @param aprovador
	 *            the aprovador to set
	 */
	public void setAprovador(Usuario aprovador) {
		this.aprovador = aprovador;
	}

	/**
	 * @return the expiracao
	 */
	public Date getExpiracao() {
		return expiracao;
	}

	/**
	 * @param expiracao
	 *            the expiracao to set
	 */
	public void setExpiracao(Date expiracao) {
		this.expiracao = expiracao;
	}

	/**
	 * @return the chaveEstrangeira
	 */
	public String getChaveEstrangeira() {
		return chaveEstrangeira;
	}

	/**
	 * @param chaveEstrangeira
	 *            the chaveEstrangeira to set
	 */
	public void setChaveEstrangeira(String chaveEstrangeira) {
		this.chaveEstrangeira = chaveEstrangeira;
	}

	/**
	 * @return the tabelaEstrangeira
	 */
	public String getTabelaEstrangeira() {
		return tabelaEstrangeira;
	}

	/**
	 * @param tabelaEstrangeira
	 *            the tabelaEstrangeira to set
	 */
	public void setTabelaEstrangeira(String tabelaEstrangeira) {
		this.tabelaEstrangeira = tabelaEstrangeira;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

}
