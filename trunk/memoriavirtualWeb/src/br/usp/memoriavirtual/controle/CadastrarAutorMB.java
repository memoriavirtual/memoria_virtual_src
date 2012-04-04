/**
 * 
 */
package br.usp.memoriavirtual.controle;

import java.util.ResourceBundle;

import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import br.usp.memoriavirtual.utils.MensagensDeErro;

/**
 * @author bigmac
 *
 */
public class CadastrarAutorMB {
	
	private String nome;
	private String sobrenome;
	private String codinome;
	private String nascimento;
	private String obito;
	private String atividade;
	

	/**
	 * Construtor Padrão 
	 */
	public CadastrarAutorMB() {
		super();
	}
	/**
	 * 
	 * @return String a ser interpretada pelo faces-config
	 */
	public String cadastraAutor (){
		String retorno = null;
		if (!FacesContext.getCurrentInstance().getMessages().hasNext()){
			
		}
		return retorno;
	}
	public void validateTipoAutoria(AjaxBehaviorEvent event) {
		this.validateTipoAutoria();
	}

	public void validateTipoAutoria() {
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(context, bundleName);
		if (this.atividade.equals(bundle.getString("cadastrarAutorListaTipoAutoria"+ 0 ))) {
			MensagensDeErro.getErrorMessage("cadastrarAutorListaAtividadeVazia",
					"validacaoSenha");
		} 
	}public void validateNome(AjaxBehaviorEvent event) {
		this.validateNome();
	}

	public void validateNome() {
		if (this.nome.equals("")) {
			MensagensDeErro.getErrorMessage("cadastrarAutorListaNomeVazia",
					"validacaoSenha");
		} 
		
	}public void validateSobrenome(AjaxBehaviorEvent event) {
		this.validateSobrenome();
	}

	public void validateSobrenome() {
		if (this.sobrenome.equals("")) {
			MensagensDeErro.getErrorMessage("cadastrarAutorListaSobrenomeVazia",
					"validacaoSenha");
		} 
	}public void validateAtividade(AjaxBehaviorEvent event) {
		this.validateAtividade();
	}

	public void validateAtividade() {
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(context, bundleName);
		if (this.atividade.equals(bundle.getString("cadastrarAutorListaAtividade"+ 0 ))) {
			String[] argumentos = { "senha" };
			MensagensDeErro.getErrorMessage("campo_vazio", argumentos,
					"validacaoSenha");
		}
	}
	public void validateNascimento(AjaxBehaviorEvent event) {
		this.validateNascimento();
	}

	public void validateNascimento() {
		
	}
	public void validateObito(AjaxBehaviorEvent event) {
		this.validateObito();
	}

	public void validateObito() {
		
	}
	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}


	/**
	 * @return the sobrenome
	 */
	public String getSobrenome() {
		return sobrenome;
	}


	/**
	 * @return the codinome
	 */
	public String getCodinome() {
		return codinome;
	}


	/**
	 * @return the nascimento
	 */
	public String getNascimento() {
		return nascimento;
	}


	/**
	 * @return the obito
	 */
	public String getObito() {
		return obito;
	}


	/**
	 * @return the atividade
	 */
	public String getAtividade() {
		return atividade;
	}


	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}


	/**
	 * @param sobrenome the sobrenome to set
	 */
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}


	/**
	 * @param codinome the codinome to set
	 */
	public void setCodinome(String codinome) {
		this.codinome = codinome;
	}


	/**
	 * @param nascimento the nascimento to set
	 */
	public void setNascimento(String nascimento) {
		this.nascimento = nascimento;
	}


	/**
	 * @param obito the obito to set
	 */
	public void setObito(String obito) {
		this.obito = obito;
	}


	/**
	 * @param atividade the atividade to set
	 */
	public void setAtividade(String atividade) {
		this.atividade = atividade;
	}
	
	

}
