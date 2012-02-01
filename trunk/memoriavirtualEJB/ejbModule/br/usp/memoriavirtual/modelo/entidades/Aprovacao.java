package br.usp.memoriavirtual.modelo.entidades;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Aprovacao {

	@Id
	@Temporal(TemporalType.DATE)
	private Date data;
	@Id
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "APROVADOR")
	private Usuario aprovador;
	@Temporal(TemporalType.DATE)
	private Date expiracao;
	@Id
	private String chaveEstrangeira;
	@Id
	private String tabelaEstrangeira;

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

}
