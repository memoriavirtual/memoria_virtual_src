package br.usp.memoriavirtual.modelo.entidades.bempatrimonial;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Lob;
 
@Embeddable
public class Pesquisador implements Serializable {

	private static final long serialVersionUID = -5418736654663671679L;
	private String nome = "";
	private String dataPesquisa = "";
	@Lob
	private String notasPesquisador = "";

	public Pesquisador() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDataPesquisa() {
		return dataPesquisa;
	}

	public void setDataPesquisa(String dataPesquisa) {
		this.dataPesquisa = dataPesquisa;
	}

	public String getNotasPesquisador() {
		return notasPesquisador;
	}

	public void setNotasPesquisador(String notasPesquisador) {
		this.notasPesquisador = notasPesquisador;
	}
}
