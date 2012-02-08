package br.usp.memoriavirtual.servlet;

import java.io.IOException;

import javax.el.ELResolver;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.usp.memoriavirtual.controle.ExcluirInstituicaoMB;
import br.usp.memoriavirtual.modelo.entidades.Aprovacao;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarInstituicaoRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirInstituicaoRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;
import br.usp.memoriavirtual.utils.FacesUtil;

public class ExcluirInstituicao extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1529968971817032604L;
	
	private EditarInstituicaoRemote editarInstituicaoEJB;
	private MemoriaVirtualRemote memoriaVirtualEJB;
	private ExcluirInstituicaoRemote excluirInstituicaoEJB;
	public void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException, ServletException {
		
		
		Aprovacao aprovacao = new Aprovacao();
		FacesContext facesContext = FacesUtil.getFacesContext(req, response);
		String chaveEstrangeira = memoriaVirtualEJB.embaralhar(req.getParameter("chaveEstrangeira"));
		String justificativa = memoriaVirtualEJB.embaralhar(req.getParameter("justificativa"));
		ELResolver resolver = facesContext.getApplication().getELResolver();   
		ExcluirInstituicaoMB  bean = (ExcluirInstituicaoMB) resolver.getValue(facesContext.getELContext(), null, "excluirInstituicaoMB");  

		try {
			 aprovacao = this.excluirInstituicaoEJB.getAprovacao("Instituicao", chaveEstrangeira);
		} catch (ModeloException e) {
			e.printStackTrace();
		}
		try {
			bean.setInstituicao(editarInstituicaoEJB.getInstituicao(aprovacao.getChaveEstrangeira()));
		} catch (ModeloException e) {
			e.printStackTrace();
		}
		try {
			bean.setRequisitor(excluirInstituicaoEJB.getRequisitor(memoriaVirtualEJB.embaralhar(req.getParameter("chaveEstrangeira"))));
		} catch (ModeloException e) {
			e.printStackTrace();
		}
		bean.setJustificativa(justificativa);
		bean.Gerentes();
		response.sendRedirect("restrito/validarExclusao.jsf");
		
	}
}
