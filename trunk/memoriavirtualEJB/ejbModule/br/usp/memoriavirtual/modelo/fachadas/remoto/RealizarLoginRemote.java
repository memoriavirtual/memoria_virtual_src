package br.usp.memoriavirtual.modelo.fachadas.remoto;
import javax.ejb.Remote;

import br.usp.memoriavirtual.modelo.entidades.Usuario;

@Remote
public interface RealizarLoginRemote {

	public boolean autenticarUsuario(Usuario usuario);
	
}
