package br.usp.memoriavirtual.controle;

import javax.ejb.EJB;

import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarCadastroProprioRemote;

public class EditarCadastroProprioMB {

	@EJB
	private EditarCadastroProprioRemote editarCadastroProprioEJB;
	private Usuario usuario;
	private String novoEmail;
	private String novoNomeCompleto;
	private String novaSenha;

	public String editarCadastroProprio() {
		if (this.novoEmail == null || this.novoNomeCompleto == null
				|| this.novaSenha == null)
			return "Incompleto";
		usuario = this.editarCadastroProprioEJB.recuperarDadosUsuario("1");
		try {
			this.editarCadastroProprioEJB.atualizarDadosUsuario(
					usuario.getId(), this.novoEmail, this.novoNomeCompleto,
					this.novaSenha);
			return "Sucesso";
		} catch (Exception e) {
			return "Falha";
		}
	}

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
