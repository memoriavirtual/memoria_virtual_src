package br.usp.memoriavirtual.modelo.fachadas.remoto;

import javax.ejb.Remote;

import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;

@Remote
public interface RealizarLoginRemote {

	public Usuario realizarLogin(String usuario, String senha)
			throws ModeloException;

}
