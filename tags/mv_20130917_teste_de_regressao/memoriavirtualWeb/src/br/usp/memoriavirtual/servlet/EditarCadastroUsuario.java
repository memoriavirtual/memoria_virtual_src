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
import br.usp.memoriavirtual.modelo.entidades.Aprovacao;
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
		Aprovacao aprovacao = new Aprovacao();
		try {
			aprovacao = this.editarCadastroUsuarioEJB
					.getAprovacao(aprovacaoString);
		} catch (ModeloException m) {
			m.printStackTrace();
		}

		try {
			if (editarCadastroUsuarioEJB.isAprovacaoExpirada(aprovacaoString)) {
				editarCadastroUsuarioEJB.removerAprovacao(aprovacaoString);
				response.sendRedirect("restrito/dataexpirada.jsf");
			} else {

				// Inicializando Managed Bean
				FacesContext facesContext = FacesUtil.getFacesContext(request,
						response);
				ELResolver resolver = facesContext.getApplication()
						.getELResolver();
				EditarCadastroUsuarioMB managedBean = (EditarCadastroUsuarioMB) resolver
						.getValue(facesContext.getELContext(), null,
								"editarCadastroUsuarioMB");

				managedBean.setAcesso(aprovacaoString);
				managedBean.setAprovacao(aprovacao);
				response.sendRedirect("restrito/validaredicao.jsf");
			}
		} catch (ModeloException e) {
			e.printStackTrace();
		}

	}
}
