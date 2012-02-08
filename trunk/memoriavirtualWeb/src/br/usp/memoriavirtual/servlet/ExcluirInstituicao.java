package br.usp.memoriavirtual.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.usp.memoriavirtual.modelo.fachadas.MemoriaVirtual;

public class ExcluirInstituicao extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1529968971817032604L;
	
	
	private MemoriaVirtual memoriaVirtualEJB;
	public void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException, ServletException {

		String chaveEstrngeira = memoriaVirtualEJB.embaralhar(req.getParameter("chaveEstrangeira"));
		

	
			response.sendRedirect("restrito/selecionar.jsf");
		
	}
}
