package br.usp.memoriavirtual.servlet;

import java.io.IOException;

import javax.el.ELResolver;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.usp.memoriavirtual.controle.MensagensMB;
import br.usp.memoriavirtual.utils.FacesUtil;

@WebServlet("/limparmensagens")
public class LimparMensagens extends HttpServlet{

	private static final long serialVersionUID = 2862443141464332215L;

	public LimparMensagens() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		FacesContext facesContext = FacesUtil
				.getFacesContext(request, response);
		ELResolver resolver = facesContext.getApplication().getELResolver();
		MensagensMB bean = (MensagensMB) resolver.getValue(
				facesContext.getELContext(), null, "mensagensMB");
		bean.limpar();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

}
