package br.usp.memoriavirtual.controle;

import javax.ejb.EJB;

import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.remoto.RealizarLoginRemote;



public class RealizarLoginMB {

	@EJB
	private RealizarLoginRemote realizarLogin;
	private Usuario usuario = new Usuario();
	private boolean logado = false;
	
	
	public String login() {
        logado = realizarLogin.validarLogin(usuario);
        return logado ? "sucesso" : "falha";
    }

	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
}
