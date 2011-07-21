package br.usp.memoriavirtual.controle;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EnviarConviteRemote;

public class EnviarConviteMB {

	@EJB
	private EnviarConviteRemote enviarConviteEJB;
	private String emails = "";
	private String mensagem = "";
	private String validade = "";
	private String instituicao = "";
	private String nivelAcesso = "";
	private String erro = "";

	public String enviarConvite() {
		Usuario usuario = (Usuario) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("usuario");
		List<Instituicao> instituicoesUsuario = new ArrayList<Instituicao>();
		boolean sucesso = true;
		boolean consistente = false;

		// Faz verificacao para ver se os dados retornados pela pagina sao
		// consistentes
		if (usuario.isAdministrador()) {
			consistente = true;
		} else if (!nivelAcesso.toUpperCase().equals("ADMINISTRADOR")) {
			Grupo grupo = new Grupo("Gerente");
			instituicoesUsuario = this.enviarConviteEJB.getInstituicoes(grupo,
					usuario);
			for (Instituicao instituicao : instituicoesUsuario) {
				if (instituicao.getNome().equals(this.instituicao)) {
					consistente = true;
					break;
				}
			}
		}

		if (Integer.parseInt(validade) < 31 && consistente) {
			this.erro = this.enviarConviteEJB.enviarConvite(emails, mensagem,
					validade, instituicao, nivelAcesso);
			if (erro != "sucesso") {
				sucesso = false;
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(erro));
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Dados inconsistentes recebidos"));
		}
		return sucesso ? "sucesso" : "falha";
	}

	public List<SelectItem> getNiveisPermitidos() {
		Usuario usuario = (Usuario) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("usuario");

		List<SelectItem> niveisPermissao = new ArrayList<SelectItem>();
		List<Grupo> grupos = null;

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

	/**
	 * @return O erro ocorrido
	 */
	public String getErro() {
		return erro;
	}

}
