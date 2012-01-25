package br.usp.memoriavirtual.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditarCadastroUsuario extends HttpServlet {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 2734229838622329992L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		String validacao = request.getParameter("String");
		System.out.println(validacao);
		

		if (validacao != "valido") {
			response.sendRedirect("restrito/index.jsf");
			// Caso queria fazer por foward para n�o mudar a url usar c�digo
			// abaixo:
			// javax.servlet.RequestDispatcher dispatcher =
			// request.getRequestDispatcher("cadastrarusuario.jsf?validacao="+validacao+"&email="+email);
			// dispatcher.forward(request, response);
		} else {
			response.sendRedirect("restrito/selecionar.jsf");
		}
	}
}
