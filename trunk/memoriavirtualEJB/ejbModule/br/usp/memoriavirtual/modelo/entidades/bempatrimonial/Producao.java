package br.usp.memoriavirtual.modelo.entidades.bempatrimonial;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Lob;

@Embeddable
public class Producao implements Serializable {

	private static final long serialVersionUID = 1L;
	private String local = "";
	private String ano = "";
	private String edicao = "";
	@Lob
	private String outrasResponsabilidades = "";

	public Producao() {
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public String getEdicao() {
		return edicao;
	}

	public void setEdicao(String edicao) {
		this.edicao = edicao;
	}

	public String getOutrasResponsabilidades() {
		return outrasResponsabilidades;
	}

	public void setOutrasResponsabilidades(String outrasResponsabilidades) {
		this.outrasResponsabilidades = outrasResponsabilidades;
	}

}
