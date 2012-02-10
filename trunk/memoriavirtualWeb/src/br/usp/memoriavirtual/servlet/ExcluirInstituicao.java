package br.usp.memoriavirtual.servlet;

import java.io.IOException;

import javax.ejb.EJB;
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
	@EJB
	private EditarInstituicaoRemote editarInstituicaoEJB;
	@EJB
	private MemoriaVirtualRemote memoriaVirtualEJB;
	@EJB
	private ExcluirInstituicaoRemote excluirInstituicaoEJB;
	public void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException, ServletException {
		
		//Objeto Aprovação sera utilizado para testar a data de validade do pedido de exclusão
		Aprovacao aprovacao = new Aprovacao();
		
		//parametros recuperados da URL 
		String chaveEstrangeira = req.getParameter("chaveEstrangeira");
		String justificativa = req.getParameter("justificativa");
		String requisitor = req.getParameter("requisitor");
		
		
		//Antecipando a instancia do ManegedBean antes mesmo que a página esteja carregada
		FacesContext facesContext = FacesUtil.getFacesContext(req, response);
		ELResolver resolver = facesContext.getApplication().getELResolver();   
		ExcluirInstituicaoMB  bean = (ExcluirInstituicaoMB) resolver.getValue(facesContext.getELContext(), null, "excluirInstituicaoMB"); 
		
		//Objeto Aprovação
		try {
			 aprovacao = this.excluirInstituicaoEJB.getAprovacao(chaveEstrangeira);
		} catch (ModeloException e) {
			e.printStackTrace();
		}
		
		//setando valores no ManegedBean
		try {
			bean.setInstituicao(editarInstituicaoEJB.getInstituicao(chaveEstrangeira));
		} catch (ModeloException e) {
			e.printStackTrace();
		}
		try {
			bean.setRequisitor(excluirInstituicaoEJB.getRequisitor(requisitor));
		} catch (ModeloException e) {
			e.printStackTrace();
		}
		
		bean.setJustificativa(justificativa);
		bean.Gerentes();	
		response.sendRedirect("restrito/validarExclusao.jsf");
		
	}
}
