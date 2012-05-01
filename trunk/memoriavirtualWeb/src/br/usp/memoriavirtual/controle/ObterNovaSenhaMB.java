package br.usp.memoriavirtual.controle;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ObterNovaSenhaRemote;

public class ObterNovaSenhaMB {

	@EJB
	private ObterNovaSenhaRemote obterNovaSenhaEJB;
	
	private String email;
	private String token;
	private String novaSenha;

	public ObterNovaSenhaMB(){
		FacesContext ctx = FacesContext.getCurrentInstance(); 
        HttpServletRequest req = (HttpServletRequest) ctx.getExternalContext().getRequest();  
        
        String token = req.getParameter("validacao");  
        String email = req.getParameter("email");
        
        if(token != null) {  
             this.token = token;
        }
        if(email != null){
        	this.email = email;
        }

	}
	public String obterNovaSenha(){
		
		try {
			this.obterNovaSenhaEJB.obterNovaSenha(this.email);
		} catch (ModeloException e) {
			e.printStackTrace();
			return "falha";
		}
		return "sucesso";
	}

	public String cadastrarNovaSenha(){
		try {
			this.obterNovaSenhaEJB.cadastrarNovaSenha(this.email, this.token, this.novaSenha);
		} catch (ModeloException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
			return "falha";
		}
		return "sucesso";
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}
	
	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}
	
}
