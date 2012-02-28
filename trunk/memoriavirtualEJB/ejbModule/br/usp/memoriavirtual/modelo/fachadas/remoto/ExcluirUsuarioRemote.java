package br.usp.memoriavirtual.modelo.fachadas.remoto;

import java.util.List;

import javax.ejb.Remote;

import br.usp.memoriavirtual.modelo.entidades.Usuario;

@Remote
public interface ExcluirUsuarioRemote {

	public List<Usuario> listarUsuarios(String parteNome);

}
