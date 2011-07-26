package br.usp.memoriavirtual.modelo.fachadas.remoto;

import javax.ejb.Remote;

@Remote
public interface CadastrarUsuarioRemote {

	public String completarCadastro(String email, String nomeCompleto, String telefone, String senha, String validacao);
	public String validarEmail(String email); 
}
