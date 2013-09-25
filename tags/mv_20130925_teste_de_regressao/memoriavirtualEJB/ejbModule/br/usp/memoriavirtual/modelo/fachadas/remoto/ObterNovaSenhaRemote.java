package br.usp.memoriavirtual.modelo.fachadas.remoto;

import javax.ejb.Remote;

import br.usp.memoriavirtual.modelo.fachadas.ModeloException;

@Remote
public interface ObterNovaSenhaRemote {

	public void obterNovaSenha(String email) throws ModeloException;
	public void cadastrarNovaSenha(String email, String token, String novaSenha) throws ModeloException;

}
