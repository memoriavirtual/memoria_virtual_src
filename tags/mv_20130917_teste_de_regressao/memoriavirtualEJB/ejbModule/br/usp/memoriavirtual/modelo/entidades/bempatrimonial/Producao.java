package br.usp.memoriavirtual.modelo.entidades.bempatrimonial;

import java.io.Serializable;

import javax.persistence.Embeddable;


@Embeddable
public class Producao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public Producao() {
	}

	
	/**
	 * @param local
	 * @param ano
	 * @param edicao
	 * @param outrasResponsabilidades
	 */
	public Producao(String local, String ano, String edicao,
			String outrasResponsabilidades) {
		super();
		this.local = local;
		this.ano = ano;
		this.edicao = edicao;
		this.outrasResponsabilidades = outrasResponsabilidades;
	}


	private String local; 
	private String ano;
	private String edicao;
	private String outrasResponsabilidades;
	/**
	 * @return the local
	 */
	public String getLocal() {
		return local;
	}


	/**
	 * @param local the local to set
	 */
	public void setLocal(String local) {
		this.local = local;
	}


	/**
	 * @return the ano
	 */
	public String getAno() {
		return ano;
	}


	/**
	 * @param ano the ano to set
	 */
	public void setAno(String ano) {
		this.ano = ano;
	}


	/**
	 * @return the edicao
	 */
	public String getEdicao() {
		return edicao;
	}


	/**
	 * @param edicao the edicao to set
	 */
	public void setEdicao(String edicao) {
		this.edicao = edicao;
	}


	/**
	 * @return the outrasResponsabilidades
	 */
	public String getOutrasResponsabilidades() {
		return outrasResponsabilidades;
	}


	/**
	 * @param outrasResponsabilidades the outrasResponsabilidades to set
	 */
	public void setOutrasResponsabilidades(String outrasResponsabilidades) {
		this.outrasResponsabilidades = outrasResponsabilidades;
	}
}
