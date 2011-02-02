package br.usp.memoriavirtual.modelo.fachadas;

import javax.ejb.Stateless;

import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.remoto.RealizarLoginRemote;

/**
 * Session Bean implementation class RealizarLogin
 */
@Stateless(mappedName = "RealizarLogin")
public class RealizarLogin implements RealizarLoginRemote {



	/**
	 * Default constructor.
	 */
	public RealizarLogin() {

	}

	/**
	 * return resultado Resultado da valida��o do login
	 */
	public Usuario realizarLogin(String usuario, String senha) {
		
		return (new Usuario()).realizarLogin(usuario, senha);
	}

}
