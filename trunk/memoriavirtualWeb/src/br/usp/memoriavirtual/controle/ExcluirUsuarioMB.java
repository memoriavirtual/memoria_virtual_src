package br.usp.memoriavirtual.controle;

public class ExcluirUsuarioMB {
	
	private String nomeExcluir;
	private int prazoValidade;
	
	public void setNomeExcluir(String nomeExcluir){
		this.nomeExcluir = nomeExcluir;
	}
	
	public void setPrazoValidade(int prazoValidade){
		this.prazoValidade = prazoValidade;
	}
	
	public String getNomeExcluir(){
		return this.nomeExcluir;
	}
	
	public int getPrazoValidade(){
		return this.prazoValidade;
	}

}
