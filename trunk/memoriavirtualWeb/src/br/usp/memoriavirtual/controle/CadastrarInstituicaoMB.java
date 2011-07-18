package br.usp.memoriavirtual.controle;


import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
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
	private List<String> estados;
	
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
	public String CadatrarInstituicao(){
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
	 * @return (List<String>) todos os estados do Brasil (somente this.estados)
	 */
	public List<String> getEstados(){
		
		this.estados = new ArrayList<String>();
		
		this.estados.add("AC");
		this.estados.add("AL");
		this.estados.add("AM");
		this.estados.add("AP");
		this.estados.add("BA");
		this.estados.add("CE");
		this.estados.add("DF");
		this.estados.add("ES");
		this.estados.add("GO");
		this.estados.add("MA");
		this.estados.add("MG");
		this.estados.add("MS");
		this.estados.add("MT");
		this.estados.add("PA");
		this.estados.add("PB");
		this.estados.add("PE");
		this.estados.add("PI");
		this.estados.add("PR");
		this.estados.add("RJ");
		this.estados.add("RN");
		this.estados.add("RO");
		this.estados.add("RR");
		this.estados.add("RS");
		this.estados.add("SC");
		this.estados.add("SP");
		this.estados.add("SE");
		this.estados.add("TO");
		
		return this.estados;
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
	
	/**
	 * Setter do atributo estados
	 */
	public void setEstados(List<String> pEstados){
		this.estados = pEstados;
	}

	
}
