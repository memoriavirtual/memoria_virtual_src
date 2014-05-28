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

import br.usp.memoriavirtual.controle.ExcluirInstituicaoMB;
import br.usp.memoriavirtual.modelo.entidades.Aprovacao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirInstituicaoRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;
import br.usp.memoriavirtual.utils.FacesUtil;
import br.usp.memoriavirtual.utils.MVModeloStatusAprovacao;

@WebServlet("/excluirinstituicao")
public class ExcluirInstituicao extends HttpServlet implements Serializable {

	private static final long serialVersionUID = 1529968971817032604L;
	@EJB
	private ExcluirInstituicaoRemote excluirInstituicaoEJB;

	@EJB
	private MemoriaVirtualRemote memoriaVirtualEJB;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		try {
			FacesContext facesContext = FacesUtil.getFacesContext(request,
					response);

			Usuario usuario = (Usuario) facesContext.getExternalContext()
					.getSessionMap().get("usuario");

			String id = request.getParameter("id");

			if (!id.equals(null) && id != null && id.length() > 0) {
				Aprovacao aprovacao = memoriaVirtualEJB.getAprovacao(new Long(
						id));
				if ((aprovacao.getAnalista().getId() == usuario.getId())
						&& (aprovacao.getStatus() == MVModeloStatusAprovacao.aguardando)) {
					ELResolver resolver = facesContext.getApplication()
							.getELResolver();
					ExcluirInstituicaoMB bean = (ExcluirInstituicaoMB) resolver
							.getValue(facesContext.getELContext(), null,
									"excluirInstituicaoMB");

					bean.setAprovacao(aprovacao);
					bean.carregar();
					response.sendRedirect("restrito/validarexclusaoinstituicao.jsf");
				} else {
					System.out.println(aprovacao.getAnalista().getId() + " "
							+ usuario.getId());
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
				}
			} else {
				System.out.println(id);
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}
}