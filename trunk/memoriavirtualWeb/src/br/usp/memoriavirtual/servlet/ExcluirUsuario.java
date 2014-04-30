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

import br.usp.memoriavirtual.controle.ExcluirUsuarioMB;
import br.usp.memoriavirtual.modelo.entidades.Aprovacao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarCadastroUsuarioRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirUsuarioRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;
import br.usp.memoriavirtual.utils.FacesUtil;
import br.usp.memoriavirtual.utils.MVModeloAcao;
import br.usp.memoriavirtual.utils.MVModeloStatusAprovacao;

@WebServlet("/excluirusuario")
public class ExcluirUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private MemoriaVirtualRemote memoriaVirtualEJB;

	@EJB
	private ExcluirUsuarioRemote excluirUsuarioEJB;

	@EJB
	private EditarCadastroUsuarioRemote editarCadastroUsuarioEJB;

	public ExcluirUsuario() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

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
					|| aprovacao.getAcao() != MVModeloAcao.excluir_usuario) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			} else {
				ELResolver resolver = facesContext.getApplication()
						.getELResolver();
				ExcluirUsuarioMB managedBean = (ExcluirUsuarioMB) resolver
						.getValue(facesContext.getELContext(), null,
								"excluirUsuarioMB");
				managedBean.carregarAprovacao(aprovacao);
				response.sendRedirect("restrito/validarexclusaousuario.jsf");

			}
		} catch (ModeloException m) {
			m.printStackTrace();
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}
}
