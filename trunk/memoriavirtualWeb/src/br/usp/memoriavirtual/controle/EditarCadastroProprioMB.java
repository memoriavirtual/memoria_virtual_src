package br.usp.memoriavirtual.controle;

import javax.ejb.EJB;

import br.usp.memoriavirtual.modelo.entidades.Usuario;

public class EditarCadastroProprioMB {

	@EJB
	private Usuario usuario;
	private String novoEmail;
	private String novoNomeCompleto;
	private String novaSenha;

	public String getNomeCompleto() {
		return this.usuario.getNomeCompleto();
	}

	public String getEmail() {
		return this.usuario.getEmail();
	}

	public String getSenha() {
		return this.usuario.getSenha();
	}

	public void setNovoEmail(String novoEmail) {
		this.novoEmail = novoEmail;
	}

	public void setNovoNomeCompleto(String novoNomeCompleto) {
		this.novoNomeCompleto = novoNomeCompleto;
	}

	public boolean setNovaSenha(String novaSenha, String confirmaNovaSenha) {
		if (novaSenha == confirmaNovaSenha) {
			this.novaSenha = novaSenha;
			return true;
		} else
			return false;

	}

	public String getNovoNomeCompleto() {
		return this.novoNomeCompleto;
	}

	public String getNovoEmail() {
		return this.novoEmail;
	}

	public String getNovaSenha() {
		return this.novaSenha;
	}

}
