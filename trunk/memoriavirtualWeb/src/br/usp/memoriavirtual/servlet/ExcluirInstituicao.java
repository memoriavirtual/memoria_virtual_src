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
import br.usp.memoriavirtual.modelo.entidades.EnumTipoAcao;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.ItemAuditoria;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirInstituicaoRemote;
import br.usp.memoriavirtual.utils.FacesUtil;

public class ExcluirInstituicao extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1529968971817032604L;
	@EJB
	private ExcluirInstituicaoRemote excluirInstituicaoEJB;
	public void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException, ServletException {
		
		//Objeto Aprova��o sera utilizado para testar a data de validade do pedido de exclus�o
		Aprovacao aprovacao = new Aprovacao();
		//Objeto ItemAuditoria sera utilizado para recuperar o requerente do pedido de exclus�o
		ItemAuditoria itemAuditoria = new ItemAuditoria();
		Instituicao instituicao = new Instituicao();
		//parametros recuperados da URL, como chave estrangeira est� embaralhado sera desenbaralhado
		String chaveEstrangeira = req.getParameter("chaveEstrangeira");
		
		
		
		//Antecipando a instancia do ManegedBean antes mesmo que a p�gina esteja carregada
		FacesContext facesContext = FacesUtil.getFacesContext(req, response);
		ELResolver resolver = facesContext.getApplication().getELResolver();   
		ExcluirInstituicaoMB  bean = (ExcluirInstituicaoMB) resolver.getValue(facesContext.getELContext(), null, "excluirInstituicaoMB"); 
		
		
		//Recuperando Objetos Aprova��o, Instituicao, ItemAuditoria
		try {
			instituicao = this.excluirInstituicaoEJB.getInstituicaoFalse(chaveEstrangeira);
		} catch (ModeloException e) {
			e.printStackTrace();
		}try {
			 aprovacao = this.excluirInstituicaoEJB.getAprovacao(instituicao.getNome());
		} catch (ModeloException e) {
			e.printStackTrace();
		}
		
		try {
			itemAuditoria = this.excluirInstituicaoEJB.getItemAuditoria(aprovacao.getChaveEstrangeira(),EnumTipoAcao.EXCLUIR_INSTITUICAO);
		} catch (ModeloException e) {
			e.printStackTrace();
		}
		
		//setando valores no ManegedBean
		bean.setInstituicao(instituicao);
		bean.setItemAuditoria(itemAuditoria);
		bean.setAprovacao(aprovacao);
		bean.setRequisitor(itemAuditoria.getAutorAcao());
		bean.setJustificativa(itemAuditoria.getNotas());
		bean.Gerentes(false);	
		response.sendRedirect("restrito/validarExclusao.jsf");
		
	}
}
