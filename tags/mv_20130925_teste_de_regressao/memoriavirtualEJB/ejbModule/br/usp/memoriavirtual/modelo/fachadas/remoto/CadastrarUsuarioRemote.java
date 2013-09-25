package br.usp.memoriavirtual.modelo.fachadas.remoto;

import javax.ejb.Remote;

import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;

@Remote
public interface CadastrarUsuarioRemote {

	public void cadastrarUsuario(Usuario usuario, String validacao) throws ModeloException;

	public Usuario verificarConvite(String convite) throws ModeloException;

}
