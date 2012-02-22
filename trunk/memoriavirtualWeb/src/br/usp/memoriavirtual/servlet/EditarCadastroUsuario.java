package br.usp.memoriavirtual.servlet;

import java.io.IOException;
import javax.ejb.EJB;
import javax.el.ELResolver;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import br.usp.memoriavirtual.controle.EditarCadastroUsuarioMB;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarCadastroUsuarioRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;
import br.usp.memoriavirtual.utils.FacesUtil;

public class EditarCadastroUsuario extends HttpServlet {

	@EJB
	private MemoriaVirtualRemote memoriaVirtualEJB;
	@EJB
	private EditarCadastroUsuarioRemote editarCadastroUsuarioEJB;

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 2734229838622329992L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String aprovacaoString = request.getParameter("Id");
		aprovacaoString = this.memoriaVirtualEJB.embaralhar(aprovacaoString);


		try {
			if (editarCadastroUsuarioEJB.isAprovacaoExpirada(aprovacaoString)) {
				editarCadastroUsuarioEJB.remover(aprovacaoString);
				response.sendRedirect("restrito/dataexpirada.jsf");
			} else {
				// Antecipando a instancia do ManegedBean antes mesmo que a p�gina
				// esteja carregada
				FacesContext facesContext = FacesUtil.getFacesContext(request,
						response);
				ELResolver resolver = facesContext.getApplication().getELResolver();
				EditarCadastroUsuarioMB managedBean = (EditarCadastroUsuarioMB) resolver
						.getValue(facesContext.getELContext(), null,
								"editarCadatroUsuarioMB");
				
				//managedBean.setAcesso(aprovacaoString);
				response.sendRedirect("restrito/validaredicao.jsf");
			}
		} catch (ModeloException e) {
			e.printStackTrace();
		}

	}
}
