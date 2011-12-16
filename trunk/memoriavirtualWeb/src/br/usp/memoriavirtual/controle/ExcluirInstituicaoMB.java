package br.usp.memoriavirtual.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarInstituicaoRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;
import br.usp.memoriavirtual.utils.MensagensDeErro;

@ManagedBean(name = "excluirInstituicaoMB")
@SessionScoped
public class ExcluirInstituicaoMB implements Serializable {

	//@EJB
	//private ExcluirInstituicaoRemote excluirInstituicaoEJB;


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB
	private MemoriaVirtualRemote memoriaVirtualEJB;
	@EJB
	private EditarInstituicaoRemote editarInstituicaoEJB;
	private String nome;
	private List<Instituicao> instituicoes = new ArrayList<Instituicao>();
	private Instituicao instituicao = null;
	private String validade = null;
	private String justificativa = null;
	private boolean flagInstituicao = false;

	@SuppressWarnings("unused")
	private List<SelectItem> ValidadeDias;


	public void listarInstituicoes(AjaxBehaviorEvent event) {
		this.instituicoes.clear();
		List<Instituicao> listaInstituicoes = new ArrayList<Instituicao>();
		listaInstituicoes = this.memoriaVirtualEJB.listarInstituicoes(this.nome);
		this.setInstituicoes(listaInstituicoes);
		this.validacaolista();
		return;
	}
	private void validacaolista(){
		if(this.instituicoes.isEmpty() )
			if(this.nome != ""){
				MensagensDeErro.getErrorMessage("excluirInstituicaoErrolistavazia",
				"validacaoNome");
				this.flagInstituicao = false;
			}
		if( this.nome == ""){
			MensagensDeErro.getErrorMessage("excluirInstituicaoInstituicaoVazia",
			"validacaoNome");	
			this.flagInstituicao = false;
		}else
			this.flagInstituicao = true;
	}
	public void selecionarInstituicoes ( Instituicao pinstituicao ){
		this.setNome(pinstituicao.getNome());
		this.setInstituicao(pinstituicao);
		this.flagInstituicao = true;
		this.instituicoes.clear();
		return ;
	}
	public String selecionarInstituicoes (){
		if(this.instituicao == null && this.flagInstituicao){
			try {this.setInstituicao ( editarInstituicaoEJB.getInstituicao(this.nome));
			} catch (ModeloException e) {
				e.printStackTrace();
				this.flagInstituicao = false;
			}
		}
		if ( validateValidade() && this.flagInstituicao){
			return "Instselecionada";
		}else{	
			if(!this.flagInstituicao){
				MensagensDeErro.getErrorMessage("excluirInstituicaoInstituicaoErro","validacaoNome");	
			}
		}

		return "erro";
	}
	/**
	 * @param event
	 * Método de validação do imput validade do pedido de exclusão 
	 */
	public void validadeValidade(AjaxBehaviorEvent event){
		validateValidade();
		return;
	}
	/**
	 * Método de validação do imput validade do pedido de exclusão
	 * Utilizado pelo vallidador do evento e pelo validador do botão
	 */
	public boolean validateValidade(){

		if (this.validade == null) {
			MensagensDeErro.getErrorMessage("enviarconvite_validadevazia",
			"validacaoValidade");
			return false;
		} 
		return true;
	}
	public String confirmarexcluirInstituicao() {
		return "confirmarexcluir";
	}
	public String voltar() {
		this.nome= "";
		this.instituicoes.clear();
		this.instituicao = null;
		this.validade = "";
		this.justificativa = "";
		this.flagInstituicao = false;
		return "voltar";
	}
	public String cancelar() {
		this.nome= "";
		this.instituicoes.clear();
		this.instituicao = null;
		this.validade = "";
		this.justificativa = "";
		this.flagInstituicao = false;
		return "cancelar";
	}




	public String getNome() {
		return nome;
	}

	public void setNome(String pnome) {
		nome = pnome;
	}

	public List<Instituicao> getInstituicoes() {
		return instituicoes;
	}


	public void setInstituicoes(List<Instituicao> instituicoes) {
		this.instituicoes = instituicoes;
	}


	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

	public Instituicao getInstituicao() {
		return instituicao;
	}

	/**
	 * @param validade the validade to set
	 */
	public void setValidade(String validade) {
		this.validade = validade;
	}

	/**
	 * @return the validade
	 */
	public String getValidade() {
		return validade;
	}
	/**
	 * @return the validade + dias
	 */
	public String getValidadecomdias() {
		return validade + " dias.";
	}

	/**
	 * @param validadeDias the validadeDias to set
	 */
	public void setValidadeDias(List<SelectItem> validadeDias) {
		ValidadeDias = validadeDias;
	}
	/**
	 * @return the validadeDias
	 */
	public List<SelectItem> getValidadeDias() {

		List<SelectItem> diasValidade = new ArrayList<SelectItem>();

		diasValidade.add(new SelectItem(null, ""));
		for (int i = 1; i<= 30; i++) {
			diasValidade.add(new SelectItem(i, i + " dias"));
		}
		return diasValidade;
	}

	/**
	 * @param justificativa the justificativa to set
	 */
	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	/**
	 * @return the justificativa
	 */
	public String getJustificativa() {
		return justificativa;
	}
	/**
	 * @param flagInstituicao the flagInstituicao to set
	 */
	public void setFlagInstituicao(boolean flagInstituicao) {
		this.flagInstituicao = flagInstituicao;
	}
	/**
	 * @return the flagInstituicao
	 */
	public boolean isFlagInstituicao() {
		return flagInstituicao;
	}
}