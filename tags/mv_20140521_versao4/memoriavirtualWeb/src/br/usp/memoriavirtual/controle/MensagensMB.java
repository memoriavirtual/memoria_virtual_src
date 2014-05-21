package br.usp.memoriavirtual.controle;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.usp.memoriavirtual.utils.MVControleTiposDeMensagem;

@ManagedBean(name = "mensagensMB")
@SessionScoped
public class MensagensMB implements Serializable {

	private static final long serialVersionUID = -5751868477781188062L;
	private String mensagem = "";
	private String classe = "hidden";

	public MensagensMB() {

	}

	public void limpar() {
		this.mensagem = "";
		this.classe = "hidden";
	}

	public void mensagemErro(String mensagem) {
		this.mensagem = mensagem;
		this.classe = MVControleTiposDeMensagem.erro.toString();
	}

	public void mensagemSucesso(String mensagem) {
		this.mensagem = mensagem;
		this.classe = MVControleTiposDeMensagem.sucesso.toString();
	}

	public void mensagemAviso(String mensagem) {
		this.mensagem = mensagem;
		this.classe = MVControleTiposDeMensagem.aviso.toString();
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public void reset() {
		this.mensagem = "";
		this.classe = "hidden";
	}

}
