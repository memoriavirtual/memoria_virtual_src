package br.usp.memoriavirtual.controle;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "mensagensMB")
@SessionScoped
public class MensagensMB implements Serializable {

	private static final long serialVersionUID = -5751868477781188062L;
	private String mensagem = "qwerwqerwq";
	private String nomeClasse = "";

	public MensagensMB() {

	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
		System.out.println(mensagem);
	}

	public String getNomeClasse() {
		return nomeClasse;
	}

	public void setNomeClasse(String nomeClasse) {
		this.nomeClasse = nomeClasse;
	}

}
