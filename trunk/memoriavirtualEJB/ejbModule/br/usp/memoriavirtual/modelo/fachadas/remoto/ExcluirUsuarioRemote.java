package br.usp.memoriavirtual.modelo.fachadas.remoto;

import java.util.List;

import javax.ejb.Remote;

import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;

@Remote
public interface ExcluirUsuarioRemote {

	public List<Usuario> listarUsuarios(String parteNome, Boolean isAdministrador);
	public Usuario recuperarDadosUsuario(String nome) throws ModeloException;
	public List<Usuario> listarSemelhantes(String eliminador, Boolean isAdministrador);

}
