package br.usp.memoriavirtual.modelo.fachadas.remoto;

import javax.ejb.Remote;

import br.usp.memoriavirtual.modelo.entidades.Usuario;

@Remote
public interface CadastrarUsuarioRemote {

	public Usuario completarCadastro(String id, String email, String nome,
			String telefone, String senha, String validacao);

	public boolean disponibilidadeEmail(String email);
}
