package br.usp.labes.memoriavirtual.busca;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import br.usp.labes.memoriavirtual.util.*;

/**
 * Servlet implementation class BuscaSimplesServlet
 */
public class BuscaSimplesServlet extends HttpServlet implements ConfigServlet{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuscaSimplesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String pageRS = null;
		PrintWriter out = response.getWriter();
		ResultSet rs = null;		
		int operation = Integer.parseInt(request.getParameter("operation"));
		BuscarSimples buscarSimples = null;
		
		
		switch (operation) {
			case SEARCH:
				buscarSimples = new BuscarSimples();				
				String busca = request.getParameter("busca");
				
				try {
					Interceptor.filter(busca);
				} catch (Exception e) {
					response.sendRedirect("message.jsp?id=1");
				}
				
				buscarSimples.setBusca(busca);
				try {
					rs = buscarSimples.Buscar();
					pageRS = "displayResultList.jsp?rs="+rs;
				} catch (Exception e) {
					pageRS = "message.jsp?id=1";
				}
				
				pageRS = "message.jsp?id=0";
				
				break;
				
			case INSERT:
				pageRS = "message.jsp?id=2";
				try {
				} catch (Exception e) {
					pageRS = "message.jsp?id=3";
				}
				break;
				
			case REMOVE:
				pageRS = "message.jsp?id=2";
				try {
				} catch (Exception e) {
					pageRS = "message.jsp?id=3";
				}
				break;
				
			case UPDATE:
				pageRS = "message.jsp?id=2";
				try {
				} catch (Exception e) {
					pageRS = "message.jsp?id=3";
				}
				break;
				
		}

		request.getRequestDispatcher("../jsp/displayResultList.jsp").forward(request, response); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
