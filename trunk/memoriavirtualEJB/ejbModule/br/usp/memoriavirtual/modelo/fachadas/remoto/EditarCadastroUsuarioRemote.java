package br.usp.memoriavirtual.modelo.fachadas.remoto;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;
import br.usp.memoriavirtual.modelo.entidades.Acesso;
import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Usuario;

@Remote
public interface EditarCadastroUsuarioRemote {

	public List<Usuario> listarUsuarios(String nome);

	public List<Usuario> listarUsuarios(String nome, Usuario requerente,
			Grupo grupo);

	public List<Usuario> getAdministradores(Usuario usuario);

	public List<Acesso> getAcessos(Usuario usuario);
	
	public void editarCadastro(Usuario usuario, String nomeCompleto, String telefone);
	
	public void editarAcessos(Usuario usuario, List<Acesso> acessos, Date data, Date expiracao);


}
