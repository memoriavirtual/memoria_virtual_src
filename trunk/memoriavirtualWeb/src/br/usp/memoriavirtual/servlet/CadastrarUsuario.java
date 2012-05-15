package br.usp.memoriavirtual.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.CadastrarUsuarioRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;

public class CadastrarUsuario extends HttpServlet {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 2734229838622329992L;
	@EJB
	private CadastrarUsuarioRemote cadastrarUsuarioEJB;
	@EJB
	private MemoriaVirtualRemote memoriaVirtualEJB;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		String validacao = request.getParameter("Validacao");
		String email = request.getParameter("email");
		
		/*Como a validacao e email ja estão embaralhados eles serão desembaralhados.*/
		validacao = memoriaVirtualEJB.embaralhar(validacao);
		email = memoriaVirtualEJB.embaralhar(email);

		Usuario usuarioAutenticado = null;
		try {
			usuarioAutenticado = cadastrarUsuarioEJB.verificarConvite(validacao);
		} catch (ModeloException e) {
			e.printStackTrace();
		}

		if ((usuarioAutenticado != null) && (!usuarioAutenticado.isAtivo())) {
			request.getSession().setAttribute("usuario", usuarioAutenticado);
		} else {
			usuarioAutenticado = null;
		}

		if (usuarioAutenticado != null) {
			response.sendRedirect("restrito/cadastrarusuario.jsf");
			// Caso queria fazer por foward para nï¿½o mudar a url usar cï¿½digo
			// abaixo:
			// javax.servlet.RequestDispatcher dispatcher =
			// request.getRequestDispatcher("cadastrarusuario.jsf?validacao="+validacao+"&email="+email);
			// dispatcher.forward(request, response);
		} else {
			response.sendRedirect("conviteinvalido.jsf");
		}
	}
}
