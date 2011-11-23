package br.usp.memoriavirtual.modelo.fachadas;

import java.util.List;

import br.usp.memoriavirtual.modelo.entidades.Acesso;
import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarCadastroUsuarioRemote;

public class EditarCadatroUsuario implements EditarCadastroUsuarioRemote{

	@Override
	public void editarCadastroUsuario(String velhoNome, String novoNome,
			String novoTelefone, List<Acesso> acesso) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Acesso> getUsuariosSugeridos(String velhoNome, Grupo grupo,
			Usuario usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Acesso> getUsuariosSugeridos(String velhoNome) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
