package br.usp.memoriavirtual.controle;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.usp.memoriavirtual.modelo.fachadas.remoto.CadastrarUsuarioRemote;


public class CadastrarUsuarioMB {
	
	@EJB
	private CadastrarUsuarioRemote cadastrarUsuarioEJB;
	private String email = "";
	private String nomeCompleto = "";
	private String telefone = "";
	private String senha = "";
	private String confirmacaoSenha = "";
	private String validacao = "";
	

	public CadastrarUsuarioMB(){
			
	}
		
	public String completarCadastro(){
		
		boolean erro = false;
		
		String erroEmail = cadastrarUsuarioEJB.validarEmail(email);
		if(erroEmail != null){
			erro = true;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(erroEmail));
		}
		
		//Validar nome
		//Validar telefone
		
		if(!senha.equals(confirmacaoSenha)){
			erro = true;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Confirmacao de senha não confere."));
		}
		
		if(erro)
			return "falhou";
		
		String resultado = cadastrarUsuarioEJB.completarCadastro(email, nomeCompleto, telefone, senha, validacao);
	
		return resultado;
	}
		
    public String getEmail() {
		return email;
	}
    
	public void setEmail(String email) {
		this.email = email;
	}
    
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getValidacao() {
		return validacao;
	}

	public void setValidacao(String validacao) {
		this.validacao = validacao;
	}

	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}

	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
	}	

}
