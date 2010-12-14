package br.usp.labes.memoriavirtual.busca;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.usp.labes.memoriavirtual.modelo.fachada.BemPatrimonial;
import br.usp.labes.memoriavirtual.modelo.fachada.RealizarBuscaSimples;

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
				
		List<BemPatrimonial> ListaBemPatrimonial =  null;
		
		int operation = Integer.parseInt(request.getParameter("operation"));
				
		switch (operation) {
		
			case SEARCH:
								
				String busca = request.getParameter("palavrachave");				
				
				try {
					ListaBemPatrimonial = RealizarBuscaSimples.realizarBusca(busca);
				} catch (Exception e) {
					//response.sendRedirect("jsp/message.jsp?id=1");
				}
				
				request.setAttribute("busca",ListaBemPatrimonial);	
				
				break;
				
			case INSERT:
				break;
				
			case REMOVE:
				break;
				
			case UPDATE:
				break;
							
		}
		
		try {
			request.getRequestDispatcher("jsp/displayResultList.jsp").forward(request, response);
		} 
		catch (Exception e) {
			//response.sendRedirect("jsp/message.jsp?id=3");
		}
		 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
