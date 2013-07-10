package br.usp.memoriavirtual.servlet;

import java.io.IOException;

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

/**
 * Servlet implementation class ExcluirUsuario
 */
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
		
		String aprovacaoId = request.getParameter("aprovacao");
		aprovacaoId = this.memoriaVirtualEJB.embaralhar(aprovacaoId);

		String usuarioNome =  request.getParameter("usuario");
		usuarioNome = this.memoriaVirtualEJB.embaralhar(usuarioNome);
		
		
		
		Usuario usuario = (Usuario) request.getSession()
				.getAttribute("usuario");
		
		if(!this.editarCadastroUsuarioEJB.isAprovador(usuario, aprovacaoId)){
			response.sendRedirect("restrito/excluirusuario_etapa1.jsf");
			return;
		}

		boolean expiracao = false;

		try {
			expiracao = editarCadastroUsuarioEJB
					.isAprovacaoExpirada(aprovacaoId);
		} catch (ModeloException m) {
			m.printStackTrace();
		}

		if (expiracao) {

			try {
				editarCadastroUsuarioEJB.removerAprovacao(aprovacaoId);

				Usuario pendente = excluirUsuarioEJB
						.recuperarDadosUsuario(usuarioNome);

				excluirUsuarioEJB.marcarUsuarioExcluido(pendente, true, true);
			} catch (ModeloException m) {
				m.printStackTrace();
			}

		}

		else {
			// Inicializando Managed Bean
			FacesContext facesContext = FacesUtil.getFacesContext(request,
					response);
			ELResolver resolver = facesContext.getApplication().getELResolver();
			ExcluirUsuarioMB managedBean = (ExcluirUsuarioMB) resolver
					.getValue(facesContext.getELContext(), null,
							"excluirUsuarioMB");

			managedBean.setAprovacao(aprovacaoId);
			managedBean.setNome(usuarioNome);
			managedBean.setUsuario(null);
			managedBean.listarAcessos();
			
			response.sendRedirect("restrito/confirmarexclusao.jsf");
		}

	}

}
