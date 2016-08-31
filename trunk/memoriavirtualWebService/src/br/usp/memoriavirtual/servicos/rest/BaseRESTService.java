package br.usp.memoriavirtual.servicos.rest;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpServletRequest;

import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.RealizarLoginRemote;

import com.sun.org.apache.xml.internal.security.utils.Base64;

@RequestScoped
public class BaseRESTService {

	@EJB
	private RealizarLoginRemote realizarLoginEJB;

	protected boolean validaCliente(HttpServletRequest request) {
		String username = "";
		String password = "";
		try {
			String header = request.getHeader("authorization");
			String encodedText = header.substring(header.indexOf(" ") + 1);

			byte[] buf = null;
			buf = Base64.decode(encodedText.getBytes());

			String credentials = new String(buf);

			int posicaoDoCaracterSeparador = credentials.indexOf(":");

			if (posicaoDoCaracterSeparador > -1) {
				username = credentials.substring(0, posicaoDoCaracterSeparador);
				password = credentials.substring(posicaoDoCaracterSeparador + 1);
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return autentica(username, password);
	}

	protected boolean autentica(String usuario, String senha) {
		boolean autenticado = false;

		Usuario usuarioAutenticado = null;
		try {
			usuarioAutenticado = realizarLoginEJB.realizarLogin(usuario, senha);
		} catch (ModeloException e) {
			e.printStackTrace();
		}

		if (usuarioAutenticado != null) {
			autenticado = true;
		}

		return autenticado;
	}
}
