package br.usp.memoriavirtual.modelo.entidades.bempatrimonial;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class Diagnostico implements Serializable {

	public static enum EstadoConservacao {
		bom, precario, emArruinamento, arruinado
	}

	public static enum EstadoPreservacao {
		integro, poucoAlterado, muitoAlterado, descaracterizado
	}

	private static final long serialVersionUID = -2256116542157135280L;
	private EstadoPreservacao estadoPreservacao = Diagnostico.EstadoPreservacao.integro;
	private EstadoConservacao estadoConservacao = Diagnostico.EstadoConservacao.bom;
	private String notasEstadoConservacao = "";

	public Diagnostico() {
		super();
	}

	public EstadoPreservacao getEstadoPreservacao() {
		return estadoPreservacao;
	}

	public void setEstadoPreservacao(EstadoPreservacao estadoPreservacao) {
		this.estadoPreservacao = estadoPreservacao;
	}

	public EstadoConservacao getEstadoConservacao() {
		return estadoConservacao;
	}

	public void setEstadoConservacao(EstadoConservacao estadoConservacao) {
		this.estadoConservacao = estadoConservacao;
	}

	public String getNotasEstadoConservacao() {
		return notasEstadoConservacao;
	}

	public void setNotasEstadoConservacao(String notasEstadoConservacao) {
		this.notasEstadoConservacao = notasEstadoConservacao;
	}
}
