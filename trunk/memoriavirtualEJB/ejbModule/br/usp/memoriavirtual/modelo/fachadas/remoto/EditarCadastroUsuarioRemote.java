package br.usp.memoriavirtual.modelo.fachadas.remoto;

import java.util.List;

import br.usp.memoriavirtual.modelo.entidades.Acesso;
import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;

public interface EditarCadastroUsuarioRemote {

	public void editarCadastroUsuario(String velhoNome, String novoNome,
			String novoTelefone, List<Acesso> acesso) throws ModeloException;

	public List<Acesso> getUsuariosSugeridos(String velhoNome, Grupo grupo,
			Usuario usuario);
	
	public List<Acesso> getUsuariosSugeridos(String velhoNome);

}
