package br.usp.memoriavirtual.servlet;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.el.ELResolver;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.usp.memoriavirtual.controle.ExcluirInstituicaoMB;
import br.usp.memoriavirtual.modelo.entidades.Aprovacao;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.ItemAuditoria;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirInstituicaoRemote;
import br.usp.memoriavirtual.utils.FacesUtil;

public class ExcluirInstituicao extends HttpServlet implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1529968971817032604L;
	@EJB
	private ExcluirInstituicaoRemote excluirInstituicaoEJB;
	public void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException, ServletException {
		
		//Objeto Aprovação sera utilizado para testar a data de validade do pedido de exclusão
		Aprovacao aprovacao = new Aprovacao();
		//Objeto ItemAuditoria sera utilizado para recuperar o requerente do pedido de exclusão
		ItemAuditoria itemAuditoria = new ItemAuditoria();
		Instituicao instituicao = new Instituicao();
		Integer chaveEstrangeira = new Integer (req.getParameter("chaveEstrangeira"));
		Integer auditoria = new Integer (req.getParameter("auditoria"));
		System.out.println(auditoria);
		
		//Referência no managed bean
		FacesContext facesContext = FacesUtil.getFacesContext(req, response);
		ELResolver resolver = facesContext.getApplication().getELResolver();   
		ExcluirInstituicaoMB  bean = (ExcluirInstituicaoMB) resolver.getValue(facesContext.getELContext(), null, "excluirInstituicaoMB"); 
		
		
		//Recuperando Objetos Aprovação, Instituição, ItemAuditoria
		
		try {
			 aprovacao = this.excluirInstituicaoEJB.recuperarAprovacao( (long)chaveEstrangeira);
		} catch (ModeloException e) { 
			e.printStackTrace();
		}
		
		try {
			itemAuditoria = this.excluirInstituicaoEJB.recuperarItemAuditoria((long)auditoria);
		} catch (ModeloException e) {
			e.printStackTrace();
		}
		try {
			instituicao = this.excluirInstituicaoEJB.recuperarInstituicaoFalse(Long.parseLong(aprovacao.getChaveEstrangeira()));
		} catch (ModeloException e) {
			e.printStackTrace();
			return;
		}
		//setando valores no ManegedBean
		bean.setInstituicao(instituicao);
		bean.setItemAuditoria(itemAuditoria);
		bean.setAprovacao(aprovacao);
		bean.setRequisitor(itemAuditoria.getAutorAcao());
		bean.setJustificativa(itemAuditoria.getNotas());
		bean.listarGerentes(false);	
		response.sendRedirect("restrito/validarExclusao.jsf");
		
	}
}
