package br.usp.memoriavirtual.modelo.fachadas.remoto;

import java.util.List;

import javax.ejb.Remote;
import br.usp.memoriavirtual.modelo.entidades.Acesso;
import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Usuario;

@Remote
public interface EditarCadastroUsuarioRemote {

	public List<Usuario> listarUsuarios(String velhoNome);

	public List<Usuario> listarUsuarios(String nome, Usuario requerente,
			Grupo grupo);

	public List<Usuario> getAdministradores(Usuario usuario);

	public List<Acesso> getAcessos(Usuario usuario);

}
