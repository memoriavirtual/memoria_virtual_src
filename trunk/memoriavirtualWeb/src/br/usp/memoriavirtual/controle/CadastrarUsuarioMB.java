package br.usp.memoriavirtual.controle;

public class CadastrarUsuarioMB {

	
	private String email = "";
	private String senha = "";
	
	
	 public CadastrarUsuarioMB(){
			
	}
		
	public String completarCadastro(){
			
		return "true";
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

	
}
