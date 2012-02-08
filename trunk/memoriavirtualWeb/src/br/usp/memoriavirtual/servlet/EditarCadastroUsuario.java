package br.usp.memoriavirtual.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;

public class EditarCadastroUsuario extends HttpServlet {

	@EJB
	private MemoriaVirtualRemote memoriaVirtualEJB;

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 2734229838622329992L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String data = request.getParameter("Data");
		String aprovador = request.getParameter("Aprovador");

		data = memoriaVirtualEJB.embaralhar(data);
		aprovador = memoriaVirtualEJB.embaralhar(aprovador);

		SimpleDateFormat formatoData = new SimpleDateFormat(
				"dd/MM/yyyy HH:mm:ss:SSS");
		try {
			Date d = formatoData.parse(data);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
