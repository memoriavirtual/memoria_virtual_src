package br.usp.memoriavirtual.controle;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EnviarConviteRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;
import br.usp.memoriavirtual.utils.Email;
import br.usp.memoriavirtual.utils.MensagensDeErro;
import br.usp.memoriavirtual.utils.ValidacoesDeCampos;

public class EnviarConviteMB {

	@EJB
	private MemoriaVirtualRemote memoriaVirtualEJB;
	@EJB
	private EnviarConviteRemote enviarConviteEJB;
	private String emails = "";
	private String mensagem = "";
	private String validade = null;
	private String instituicao = null;
	private String nivelAcesso = null;

	
	private ArrayList<Email> inputList;
	
	public EnviarConviteMB(){
		inputList = new ArrayList<Email>();
	}
	public void addInput() {
		inputList.add(new Email());
	}

	public ArrayList<Email> getInputList() {
		return inputList;
	}

	public void setInputList(ArrayList<Email> inputList) {
		this.inputList = inputList;
	}

	public String deleteValue(Email element) {
		inputList.remove(element);
		return null;
	}
	
	public String enviarConvite(int i){
		
		this.validateEmail();
		this.validateInstituicao();
		this.validateNivelAcesso();
		this.validateValidade();
		
		
		ArrayList<String> emails = new ArrayList<String>();
		for(Email m:inputList){
			emails.add(m.getEmail());
		}
		
		if (!FacesContext.getCurrentInstance().getMessages().hasNext()) {
			try{
				this.enviarConviteEJB.enviarConvite(emails, mensagem,
					validade, instituicao, nivelAcesso);
				MensagensDeErro.getSucessMessage("convite_enviado", "resultado");
			}catch(Exception e){
				MensagensDeErro.getErrorMessage("erro_envio_convite", "resultado");
			}
		}		
		return "falha";
	}
	
	public List<SelectItem> getValidadeDias() {

		List<SelectItem> diasValidade = new ArrayList<SelectItem>();

		diasValidade.add(new SelectItem(null, ""));
		for (int i = 1; i<= 30; i++) {
			diasValidade.add(new SelectItem(i, i + " dias"));
		}
		return diasValidade;
	}

	public List<SelectItem> getNiveisPermitidos() {
		Usuario usuario = (Usuario) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("usuario");

		List<SelectItem> niveisPermissao = new ArrayList<SelectItem>();
		List<Grupo> grupos = null;

		niveisPermissao.add(new SelectItem(null,"----- Escolha o nivel ----"));
		
		grupos = enviarConviteEJB.getGrupos();
		for (Grupo grupo : grupos) {
			niveisPermissao.add(new SelectItem(grupo.getId()));
		}
		if (usuario.isAdministrador()) {
			niveisPermissao.add(new SelectItem("Administrador"));
		}

		return niveisPermissao;
	}

	public List<SelectItem> getInstituicoesPermitidas() {
		Usuario usuario = (Usuario) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("usuario");

		// Lista de SelectItem que sera exibida na pagina
		List<SelectItem> listaInstituicoes = new ArrayList<SelectItem>();

		// Lista de instituicoes que o usuario pertence
		List<Instituicao> instituicoesUsuario = new ArrayList<Instituicao>();
		if (usuario.isAdministrador()) {
			instituicoesUsuario = this.enviarConviteEJB.getInstituicoes();
		} else {
			Grupo grupo = new Grupo("Gerente");
			instituicoesUsuario = this.enviarConviteEJB.getInstituicoes(grupo,
					usuario);
		}
		listaInstituicoes.add(new SelectItem(null,"--Escolha a instituicao--"));
		for (Instituicao instituicao : instituicoesUsuario) {
			listaInstituicoes.add(new SelectItem(instituicao.getNome(),
					instituicao.getNome()));
		}
		return listaInstituicoes;
	}

	/**
	 * @return String contendo a lista de emails
	 */
	public String getEmails() {
		return emails;
	}

	/**
	 * @param Define
	 *            String contendo a lista de emails a enviar convite
	 */
	public void setEmails(String emails) {
		this.emails = emails;
	}
	
	public void validateEmail(){
		if (this.emails.equals("")) {
			MensagensDeErro.getErrorMessage("cadastrarUsuarioErroEmailVazio",
					"validacaoEmail");
		} else if (!ValidacoesDeCampos.validarFormatoEmail(this.emails)) {
			MensagensDeErro.getErrorMessage("cadastrarUsuarioErroEmailInvalido",
					"validacaoEmail");
		} else if (!memoriaVirtualEJB.disponibilidadeEmail(this.emails)) {
			MensagensDeErro.getErrorMessage(
					"cadastrarUsuarioErroEmailJaCadastrado", "validacaoEmail");
		} else {
			
		}
	}
	
	public void validateEmail(AjaxBehaviorEvent event){
		this.validateEmail();
	}

	/**
	 * @return A mensagem personalizada a ser enviada junto com os convites
	 */
	public String getMensagem() {
		return mensagem;
	}

	/**
	 * @param mensagem
	 *            Define a mensagem personalizada a ser enviada junto com os
	 *            convites
	 */
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	/**
	 * @return A validade do convite
	 */
	public String getValidade() {
		return validade;
	}

	/**
	 * @param validade
	 *            Define a validade do convite
	 */
	public void setValidade(String validade) {
		this.validade = validade;
	}
	
	public void validateValidade(){
		
		if (this.validade == null) {
			MensagensDeErro.getErrorMessage("enviarconvite_validadevazia",
					"validacaoValidade");
		} 
	}
	
	public void validateValidade(AjaxBehaviorEvent event){
		this.validateValidade();
	}

	/**
	 * @return O nivel de acesso do usuario
	 */
	public String getNivelAcesso() {
		return nivelAcesso;
	}

	/**
	 * @param nivelAcesso
	 *            Define o nivel de acesso do usuario
	 */
	public void setNivelAcesso(String nivelAcesso) {
		this.nivelAcesso = nivelAcesso;
	}

	public void validateNivelAcesso(){
		if (this.nivelAcesso == null) {
			MensagensDeErro.getErrorMessage("enviarconvite_nivelacessovazio",
					"validacaoNivelAcesso");
		} 
	}
	
	public void validateNivelAcesso(AjaxBehaviorEvent event){
		this.validateNivelAcesso();
	}
	
	/**
	 * @return A instituição
	 */
	public String getInstituicao() {
		return instituicao;
	}

	/**
	 * @param instituicao
	 *            Define a instituição
	 */
	public void setInstituicao(String instituicao) {
		this.instituicao = instituicao;
	}

	public void validateInstituicao(){
		if (this.instituicao == null) {
			MensagensDeErro.getErrorMessage("enviarconvite_instituicaovazia",
					"validacaoInstituicao");
		} else {
			MensagensDeErro.getErrorMessage("enviarconvite_title",
			"validacaoInstituicao");
		}
	}
	
	public void validateInstituicao(AjaxBehaviorEvent event){
		this.validateInstituicao();
	}
	
}
