package br.usp.memoriavirtual.servlet;

import java.io.IOException;
import java.util.Date;

import javax.ejb.EJB;
import javax.el.ELResolver;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.usp.memoriavirtual.controle.EditarCadastroUsuarioMB;
import br.usp.memoriavirtual.modelo.entidades.Aprovacao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarCadastroUsuarioRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;
import br.usp.memoriavirtual.utils.FacesUtil;
import br.usp.memoriavirtual.utils.MVModeloAcao;
import br.usp.memoriavirtual.utils.MVModeloStatusAprovacao;

@WebServlet("/editarcadastrousuario")
public class EditarCadastroUsuario extends HttpServlet {

	@EJB
	private MemoriaVirtualRemote memoriaVirtualEJB;
	@EJB
	private EditarCadastroUsuarioRemote editarCadastroUsuarioEJB;

	private static final long serialVersionUID = 2734229838622329992L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		try {
			String id = request.getParameter("id");
			Aprovacao aprovacao = new Aprovacao();

			aprovacao = this.editarCadastroUsuarioEJB.getAprovacao(id);

			FacesContext facesContext = FacesUtil.getFacesContext(request,
					response);
			Usuario usuario = (Usuario) facesContext.getExternalContext()
					.getSessionMap().get("usuario");

			Date hoje = new Date();
			if (hoje.after(aprovacao.getExpiraEm())) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			} else if (usuario.getId() != aprovacao.getAnalista().getId()
					|| aprovacao.getStatus() != MVModeloStatusAprovacao.aguardando
					|| aprovacao.getAcao() != MVModeloAcao.editar_cadastro_usuario) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			} else {
				ELResolver resolver = facesContext.getApplication()
						.getELResolver();
				EditarCadastroUsuarioMB managedBean = (EditarCadastroUsuarioMB) resolver
						.getValue(facesContext.getELContext(), null,
								"editarCadastroUsuarioMB");
				managedBean.carregarAprovacao(aprovacao);
				response.sendRedirect("restrito/validaredicaousuario.jsf");

			}
		} catch (ModeloException m) {
			m.printStackTrace();
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}
}
