package br.usp.memoriavirtual.controle;

public class ExcluirUsuarioMB {
	
	private String nomeExcluir;
	private int prazoValidade;
	private String instuicaoPertencente;
	private String nivelPermissao;
	private String justificativa;
	private String excluir;
	
	
	public void setNomeExcluir(String nomeExcluir){
		this.nomeExcluir = nomeExcluir;
	}
	
	public void setPrazoValidade(int prazoValidade){
		this.prazoValidade = prazoValidade;
	}
	
	public void setInstituicaoPertencente(String instituicaoPertencente){
		this.instuicaoPertencente = instituicaoPertencente;
	}

	public void setNivelPermissao(String nivelPermissao){
		this.nivelPermissao = nivelPermissao;
	}
	
	public void setJustificativa(String justificativa){
		this.justificativa = justificativa;
	}
	
	public void setExcluir(String excluir){
		this.excluir = excluir;
	}
	
	public String getNomeExcluir(){
		return this.nomeExcluir;
	}
	
	public int getPrazoValidade(){
		return this.prazoValidade;
	}
	
	public String getInstituicaoPertencente(){
		return this.instuicaoPertencente;
	}
	
	public String getNivelPermissao(){
		return this.nivelPermissao;
	}
	
	public String getJustificativa(){
		return this.justificativa;
	}
	
	public String getExcluir(){
		return this.excluir;
	}
	
}
