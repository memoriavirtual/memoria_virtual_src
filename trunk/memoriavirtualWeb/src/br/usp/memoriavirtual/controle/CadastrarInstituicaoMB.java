package br.usp.memoriavirtual.controle;


import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.model.SelectItem;

import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.fachadas.remoto.CadastrarInstituicaoRemote;

/**
 * Mananged Bean responsável pelo controle do cadatro de uma nova instituição
 */

public class CadastrarInstituicaoMB {
	
	@EJB
	private CadastrarInstituicaoRemote CadastrarInstituicaoEJB;
	private String nome;
	private String localizacao;
	private String endereco;
	private String cidade;
	private String estado;
	private String cep;
	private String telefone;
	
	/**
	 * Construtor padrão
	 */
	public CadastrarInstituicaoMB(){
		
	}
	
	/**
	 * Método responsável por verificar a formatação dos campos e passar o comando para o EJB realizar a operação no banco de dados
	 * 
	 * @return "sucesso" ou "falha" dependendo resultado das operações
	 */
	public String CadastrarInstituicao(){
		Instituicao instituicaoCadastrada = null;
		boolean cadastrada = false;
		
		try{
			instituicaoCadastrada = CadastrarInstituicaoEJB.cadastrarInstituicao(this.nome, this.localizacao, this.endereco, this.cidade, this.estado, this.cep, this.telefone);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		if(instituicaoCadastrada != null)
			cadastrada = true;
		
		return cadastrada ? "sucesso" : "falha";
		
	}
	
	/**
	 * Getter do nome
	 * @return (String) Nome da instituicao  
	 */
	public String getNome(){
		return this.nome;
	}
	
	/**
	 * Getter da localização
	 * @return (String) Localização da instituicao  
	 */
	public String getLocalizacao(){
		return this.localizacao;
	}
	
	/**
	 * Getter do endereco
	 * @return (String) Endereco da instituicao  
	 */
	public String getEndereco(){
		return this.endereco;
	}
	
	/**
	 * Getter da cidade
	 * @return (String) Cidade da instituicao  
	 */
	public String getCidade(){
		return this.cidade;
	}
	
	/**
	 * Getter do estado(unidade da federação)
	 * @return (String) Estado da instituicao  
	 */
	public String getEstado(){
		return this.estado;
	}
	
	/**
	 * Getter do CEP 
	 * @return (String) CEP da instituicao  
	 */
	public String getCep(){
		return this.cep;
	}
	
	/**
	 * Getter do telefone
	 * @return (String) telefone da instituicao  
	 */
	public String getTelefone(){
		return this.telefone;
	}
	
	/**
	 * Getter dos estados (Usado no form de cadastro)
	 * @return (List<String>) todos os estados do Brasil (somente estados)
	 */
	public List<SelectItem> getEstados(){

		List<SelectItem> estados = new ArrayList<SelectItem>();
		
		estados.add(new SelectItem("al", "AL"));
		estados.add(new SelectItem("am", "AM"));
		estados.add(new SelectItem("ap", "AP"));
		estados.add(new SelectItem("ba", "BA"));
		estados.add(new SelectItem("ce", "CE"));
		estados.add(new SelectItem("df", "DF"));
		estados.add(new SelectItem("es", "ES"));
		estados.add(new SelectItem("go", "GO"));
		estados.add(new SelectItem("ma", "MA"));
		estados.add(new SelectItem("mg", "MG"));
		estados.add(new SelectItem("ms", "MS"));
		estados.add(new SelectItem("mt", "MT"));
		estados.add(new SelectItem("pa", "PA"));
		estados.add(new SelectItem("pb", "PB"));
		estados.add(new SelectItem("pe", "PE"));
		estados.add(new SelectItem("pi", "PI"));
		estados.add(new SelectItem("pr", "PR"));
		estados.add(new SelectItem("rj", "RJ"));
		estados.add(new SelectItem("rn", "RN"));
		estados.add(new SelectItem("ro", "RO"));
		estados.add(new SelectItem("rr", "RR"));
		estados.add(new SelectItem("rs", "RS"));
		estados.add(new SelectItem("sc", "SC"));
		estados.add(new SelectItem("sp", "SP"));
		estados.add(new SelectItem("se", "SE"));
		estados.add(new SelectItem("to", "TO"));
		
		return estados;
	}
	
	/**
	 * Setter do nome da instituição
	 */
	public void setNome(String pNome){
		this.nome = pNome;
	}
	
	/**
	 * Setter da localização da instituicao
	 */
	public void setLocalizacao(String pLocalizacao){
		this.localizacao = pLocalizacao;
	}
	
	/**
	 * Setter do endereco da instituicao
	 */
	public void setEndereco(String pEndereco){
		this.endereco = pEndereco;
	}

	/**
	 * Setter da cidade da instituicao
	 */
	public void setCidade(String pCidade){
		this.cidade = pCidade;
	}
	
	/**
	 * Setter do estado (UF) da instituicao
	 */
	public void setEstado(String pEstado){
		this.estado = pEstado;
	}
	
	/**
	 * Setter do CEP da instituicao
	 */
	public void setCep(String pCep){
		this.cep = pCep;
	}
	
	/**
	 * Setter do telefone da instituicao
	 */
	public void setTelefone(String pTelefone){
		this.telefone = pTelefone;
	}

	
}
