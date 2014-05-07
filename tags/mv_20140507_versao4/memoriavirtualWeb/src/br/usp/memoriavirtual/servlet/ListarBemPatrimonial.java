package br.usp.memoriavirtual.servlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.RealizarBuscaSimplesRemote;

@WebServlet("/listarbempatrimonial")
public class ListarBemPatrimonial extends HttpServlet {

	private static final long serialVersionUID = -3455546327228843100L;

	@EJB
	RealizarBuscaSimplesRemote realizarBuscaSimplesEJB;

	public ListarBemPatrimonial() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String busca = request.getParameter("busca");

		try {
			List<BemPatrimonial> bensPatrimoniais = realizarBuscaSimplesEJB.buscar(busca, 1);

			String lista = "[";
			for (int i = 0; i < bensPatrimoniais.size(); ++i) {
				lista += ("{\"id\":" + "\"" + bensPatrimoniais.get(i).getId()
						+ "\"," + "\"text\":" + "\""
						+ bensPatrimoniais.get(i).getTituloPrincipal() + "\"}");
				if (i < bensPatrimoniais.size() - 1) {
					lista += ",";
				}
			}
			lista += "]";
			response.setHeader("Cache-Control",
					"no-store, no-cache, must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Connection", "Keep-Alive");
			response.setHeader("Content-Length", Integer
					.valueOf(lista.length()).toString());
			response.setHeader("Keep-Alive", "timeout=5, max=100");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("text/plain");

			InputStream is = new ByteArrayInputStream(lista.getBytes());
			OutputStream os = response.getOutputStream();
			byte[] buffer = new byte[32];

			int read = is.read(buffer);
			while (read >= 0) {
				if (read > 0)
					os.write(buffer, 0, read);
				read = is.read(buffer);
			}

			is.close();
			os.close();
			response.setStatus(HttpServletResponse.SC_OK);
			response.flushBuffer();

		} catch (ModeloException m) {
			m.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

}
