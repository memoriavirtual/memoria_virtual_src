package br.usp.memoriavirtual.modelo.fachadas.remoto;

import javax.ejb.Remote;

import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;

@Remote
public interface EditarCadastroProprioRemote {

	public void atualizarDadosUsuario(String id, String novoEmail, String novoNomeCompleto,
			String novoTelefone, String novaSenha, String validacao) throws ModeloException;
	
	public boolean disponibilidadeEmail(String email);
	
	public Usuario recuperarDadosUsuario(String id);
	
}
