package br.usp.memoriavirtual.modelo.fachadas;

import javax.ejb.Stateless;

import br.usp.memoriavirtual.modelo.entidades.Usuario;

import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarCadastroProprioRemote;

@Stateless(mappedName = "EditarCadastroProprio")
public class EditarCadastroProprio implements EditarCadastroProprioRemote {

	private Usuario usuario;

	public void atualizarDadosUsuario(String id, String email, String nome,
			String telefone, String senha, String validacao) {
	}

	public boolean disponibilidadeEmail(String email) {
		return true;
	}

	public Usuario recuperarDadosUsuario(String id) {
		return usuario;
	}

}
