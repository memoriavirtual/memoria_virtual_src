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
	private String mensagem = "";
	private String validade = null;
	private String instituicao = null;
	private String nivelAcesso = null;

	private List<Email> listaEmails;

	public List<Email> getListaEmails() {
		return listaEmails;
	}

	public void setListaEmails(List<Email> listaEmails) {
		this.listaEmails = listaEmails;
	}

	public String addEmail() {
		Email email = new Email();
		listaEmails.add(email);
		return null;
	}

	public String deleteEmail(Email email) {
		listaEmails.remove(email);
		return null;
	}

	public EnviarConviteMB() {
		listaEmails = new ArrayList<Email>();
		Email mail = new Email();
		listaEmails.add(mail);
	}

	public String enviarConvite() {

		this.validateEmail();
		this.validateInstituicao();
		this.validateNivelAcesso();
		this.validateValidade();

		
		// Verifica se tem ao menos um email preenchido
		if (listaEmails.size() < 1) {
			MensagensDeErro.getErrorMessage(
					"enviarConviteNenhumEmailPreenchido", null);
			return "falha";
		} else {
			int count = 0;
			for (Email mail : listaEmails) {
				if (!mail.getEmail().equals("")) {
					count++;
				}
			}
			if (count == 0) {
				MensagensDeErro.getErrorMessage(
						"enviarConviteNenhumEmailPreenchido", null);
				return "falha";
			}
		}

		if (!FacesContext.getCurrentInstance().getMessages().hasNext()) {

			List<String> stringsEmails = new ArrayList<String>();
			for (Email mail : listaEmails) {
				if (!mail.getEmail().equals(""))
					stringsEmails.add(mail.getEmail());
			}

			try {
				this.enviarConviteEJB.enviarConvite(stringsEmails, mensagem,
						validade, instituicao, nivelAcesso);
				MensagensDeErro.getSucessMessage("convite_enviado", null);
				listaEmails = new ArrayList<Email>();
				listaEmails.add(new Email());
			} catch (Exception e) {
				MensagensDeErro.getErrorMessage("erro_envio_convite", null);
				e.printStackTrace();
				return "falha";
			}
		}

		return "sucesso";
	}

	public List<SelectItem> getValidadeDias() {

		List<SelectItem> diasValidade = new ArrayList<SelectItem>();

		diasValidade.add(new SelectItem(null, ""));
		for (int i = 1; i <= 30; i++) {
			diasValidade.add(new SelectItem(i, i + " dias"));
		}
		return diasValidade;
	}

	public List<SelectItem> getNiveisPermitidos() {
		Usuario usuario = (Usuario) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("usuario");

		List<SelectItem> niveisPermissao = new ArrayList<SelectItem>();
		List<Grupo> grupos = null;

		niveisPermissao.add(new SelectItem(null, "----- Escolha o nivel ----"));

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
		listaInstituicoes
				.add(new SelectItem(null, "--Escolha a instituicao--"));
		for (Instituicao instituicao : instituicoesUsuario) {
			listaInstituicoes.add(new SelectItem(instituicao.getNome(),
					instituicao.getNome()));
		}
		return listaInstituicoes;
	}

	public void validateEmail(AjaxBehaviorEvent event) {
		this.validateEmail();
	}

	public void validateEmail() {
		for (Email email : this.listaEmails) {
			if (!ValidacoesDeCampos.validarFormatoEmail(email.getEmail())
					&& !email.getEmail().equals("")) {
				String[] argumentos = { email.getEmail() };
				MensagensDeErro.getErrorMessage(
						"enviarConviteFormatoInvalidoEmail", argumentos, null);
			} else if (!memoriaVirtualEJB.verificarDisponibilidadeEmail(email
					.getEmail())) {
				String[] argumentos = { email.getEmail() };
				MensagensDeErro.getErrorMessage(
						"enviarConviteEmailJaCadastrado", argumentos, null);
			}
		}
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

	public void validateValidade(AjaxBehaviorEvent event) {
		this.validateValidade();
	}

	public void validateValidade() {

		if (this.validade == null) {
			MensagensDeErro.getErrorMessage("enviarconvite_validadevazia",
					"validacaoValidade");
		}
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

	public void validateNivelAcesso(AjaxBehaviorEvent event) {
		this.validateNivelAcesso();
	}

	public void validateNivelAcesso() {
		if (this.nivelAcesso == null) {
			MensagensDeErro.getErrorMessage("enviarconvite_nivelacessovazio",
					"validacaoNivelAcesso");
		}
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

	public void validateInstituicao(AjaxBehaviorEvent event) {
		this.validateInstituicao();
	}

	public void validateInstituicao() {
		if (this.instituicao == null) {
			MensagensDeErro.getErrorMessage("enviarconvite_instituicaovazia",
					"validacaoInstituicao");
		} 
	}

}
