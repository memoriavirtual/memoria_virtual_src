package br.usp.memoriavirtual.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.usp.memoriavirtual.modelo.entidades.Multimidia;
import br.usp.memoriavirtual.modelo.fachadas.remoto.UtilMultimidiaRemote;

public class MultiMidiaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final int TAMANHO_PADRAO_BUFFER = 8192;

	@EJB
	private UtilMultimidiaRemote utilMultimidiaEJB;

	public MultiMidiaServlet() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Boolean type = new Boolean(request.getParameter("type"));
		Boolean thumb = new Boolean(request.getParameter("thumb"));

		String id = request.getParameter("id");

		if (id != null && !id.equals("")) {
			Long idLong = new Long(id);

			try {
				Multimidia m = utilMultimidiaEJB.getMultimidia(idLong);
				if (type)
					this.enviarType(response, m);
				else
					this.enviarStream(response, m, thumb);
			} catch (Exception e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_NOT_FOUND);// 404
				return;
			}

		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);// 404
			return;
		}

	}

	protected void enviarType(HttpServletResponse response, Multimidia midia)
			throws IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		out.println(midia.getContentType());
		out.flush();
		close(out);
	}

	protected void enviarStream(HttpServletResponse response, Multimidia m,
			Boolean thumb) throws IOException {

		Multimidia midia = (thumb) ? m.getThumb() : m;

		response.reset();
		response.setBufferSize(TAMANHO_PADRAO_BUFFER);
		response.setContentType(getServletContext()
				.getMimeType(midia.getNome()));
		response.setHeader("Content-Length",
				String.valueOf(midia.getContent().length));
		response.setHeader("Content-Disposition",
				"inline; filename=\"" + midia.getNome() + "\"");

		BufferedInputStream in = null;
		BufferedOutputStream out = null;

		try {
			InputStream byteInputStream = new ByteArrayInputStream(
					midia.getContent(), 0, midia.getContent().length);

			in = new BufferedInputStream(byteInputStream, TAMANHO_PADRAO_BUFFER);
			out = new BufferedOutputStream(response.getOutputStream(),
					TAMANHO_PADRAO_BUFFER);

			byte[] buffer = new byte[TAMANHO_PADRAO_BUFFER];
			int tamanho;
			while ((tamanho = in.read(buffer)) > 0) {
				out.write(buffer, 0, tamanho);
			}
			out.flush();
		} finally {
			close(out);
			close(in);
		}

	}

	private static void close(Closeable resource) {
		if (resource != null) {
			try {
				resource.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
