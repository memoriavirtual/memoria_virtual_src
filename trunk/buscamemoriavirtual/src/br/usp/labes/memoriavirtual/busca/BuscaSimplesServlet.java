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
		String page = null;
		String pageRS = null;
		PrintWriter out = response.getWriter();
		ResultSet rs = null;
		
		String pattern = request.getParameter("pattern");
		int operation = Integer.parseInt(request.getParameter("operation"));
		BuscarSimples buscarSimples = null;

		switch (operation) {
			case SEARCH:
				buscarSimples = new BuscarSimples();
				
				int id = Integer.parseInt(request.getParameter("id"));
				String title = request.getParameter("title");
				String body = request.getParameter("body");
				String date = request.getParameter("date");
				String author = request.getParameter("author");
				
				try {
					Interceptor.filter(id);
					Interceptor.filter(title);
					Interceptor.filter(body);
					Interceptor.filter(author);
					Interceptor.filterDate(date,pattern);
				} catch (Exception e) {
					response.sendRedirect("message.jsp?id=1");
				}
				
				buscarSimples.setId(id);
				buscarSimples.setTitle(title);
				buscarSimples.setBody(body);
				buscarSimples.setDate(date);
				buscarSimples.setAuthor(author);
				
				try {
					rs = buscarSimples.Buscar();
					pageRS = "displayResultList.jsp?rs=rs";
				} catch (Exception e) {
					page = "message.jsp?id=1";
				}
				
				page = "message.jsp?id=0";
				break;
			case INSERT:
				page = "message.jsp?id=2";
				try {
				} catch (Exception e) {
					page = "message.jsp?id=3";
				}
				break;
			case REMOVE:
				page = "message.jsp?id=2";
				try {
				} catch (Exception e) {
					page = "message.jsp?id=3";
				}
				break;
			case UPDATE:
				page = "message.jsp?id=2";
				try {
				} catch (Exception e) {
					page = "message.jsp?id=3";
				}
				break;
		}

		response.sendRedirect(page);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
