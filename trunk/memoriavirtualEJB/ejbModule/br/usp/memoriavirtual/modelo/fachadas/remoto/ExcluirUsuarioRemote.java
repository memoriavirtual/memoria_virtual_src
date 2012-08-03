package br.usp.memoriavirtual.modelo.fachadas.remoto;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;

@Remote
public interface ExcluirUsuarioRemote {

	public List<Usuario> listarUsuarios(String parteNome, Usuario requerente) throws ModeloException;
	public Usuario recuperarDadosUsuario(String nome) throws ModeloException;
	public List<Usuario> listarSemelhantes(String eliminador, Boolean isAdministrador);
	public void registrarAprovacao(Usuario validador, String idExcluido,
			Date dataValidade);
	public void marcarUsuarioExcluido(Usuario usuario,boolean marca,boolean flagAcesso) throws ModeloException;

}
