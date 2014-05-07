package br.usp.memoriavirtual.servlet;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.el.ELResolver;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.usp.memoriavirtual.controle.CadastrarUsuarioMB;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.remoto.CadastrarUsuarioRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;
import br.usp.memoriavirtual.utils.FacesUtil;

@WebServlet("/cadastrarusuario")
public class CadastrarUsuario extends HttpServlet implements Serializable {

	private static final long serialVersionUID = 2734229838622329992L;

	@EJB
	private CadastrarUsuarioRemote cadastrarUsuarioEJB;

	@EJB
	private MemoriaVirtualRemote memoriaVirtualEJB;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		FacesContext facesContext = FacesUtil
				.getFacesContext(request, response);

		Usuario usuario = (Usuario) facesContext.getExternalContext()
				.getSessionMap().get("usuario");

		if (usuario != null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		} else {
			String validacao = request.getParameter("id");
			ELResolver resolver = facesContext.getApplication().getELResolver();

			CadastrarUsuarioMB cadastrarUsuarioMB = (CadastrarUsuarioMB) resolver
					.getValue(facesContext.getELContext(), null,
							"cadastrarUsuarioMB");

			try {
				validacao = memoriaVirtualEJB.embaralhar(validacao);

				Usuario usuarioAutenticado = null;

				usuarioAutenticado = cadastrarUsuarioEJB
						.verificarConvite(validacao);

				if ((usuarioAutenticado != null)
						&& (!usuarioAutenticado.isAtivo())) {
					cadastrarUsuarioMB.setUsuario(usuarioAutenticado);
					response.sendRedirect("cadastrarusuario.jsf");
				} else {
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		}
	}
}
