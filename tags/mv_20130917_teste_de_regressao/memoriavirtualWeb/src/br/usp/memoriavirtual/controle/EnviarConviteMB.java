package br.usp.memoriavirtual.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import br.usp.memoriavirtual.modelo.entidades.Acesso;
import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EnviarConviteRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;
import br.usp.memoriavirtual.utils.Email;
import br.usp.memoriavirtual.utils.MensagensDeErro;
import br.usp.memoriavirtual.utils.ValidacoesDeCampos;

public class EnviarConviteMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4054888655663667241L;
	@EJB
	private MemoriaVirtualRemote memoriaVirtualEJB;
	@EJB
	private EnviarConviteRemote enviarConviteEJB;
	private String mensagem = "";
	private String validade = null;
	private List<Acesso> acessos = new ArrayList<Acesso>();
	private boolean renderizarInstituicao = true;
	private List<Email> listaEmails;
	private boolean administrador = false;

	public EnviarConviteMB() {
		listaEmails = new ArrayList<Email>();
		listaEmails.add(new Email());
	}

	public String enviarConvite() {

		/* Valida os campos preenchidos na tela. */
		this.validateEmail();
		this.validateValidade();
		/* Remove os emails em branco. */
		for (int i = 0; i < listaEmails.size(); i++) {
			if (listaEmails.get(i).getEmail().equals("")) {
				listaEmails.remove(i);
				i--;
			}
		}

		/* Verifica se há ao menos um email preenchido. */
		if (listaEmails.size() < 1) {
			MensagensDeErro.getErrorMessage(
					"enviarConviteNenhumEmailPreenchido", null);
			listaEmails.add(new Email());
			return "falha";
		}

		/*
		 * Se não há nenhuma mensagem de erro na tela tentamos enviar o
		 * convite
		 */
		if (!FacesContext.getCurrentInstance().getMessages().hasNext()) {

			/* Cria uma lista de strings para guardar os emails preenchidos. */
			List<String> stringsEmails = new ArrayList<String>();
			for (Email mail : listaEmails) {
				stringsEmails.add(mail.getEmail());
			}

			try {
				/*
				 * Envia o convite a todos emails atravez do método
				 * implementado no EJB.
				 */
				System.out.println(this.acessos.size());
				this.enviarConviteEJB.enviarConvite(stringsEmails, mensagem,
						validade, administrador, acessos);

				/* Exibe uma mensagem de sucesso. */
				MensagensDeErro.getSucessMessage("convite_enviado", null);

				/* Limpa a lista de emails na tela e adiciona um novo em branco. */
				listaEmails = new ArrayList<Email>();
				listaEmails.add(new Email());

			} catch (ModeloException e) {
				MensagensDeErro.getErrorMessage("erro_envio_convite", null);
				e.printStackTrace();
				return "falha";
			}
		}

		this.acessos.clear();
		this.listaEmails.clear();
		this.mensagem = "";
		
		return "sucesso";
	}

	public boolean mostrarAdministrador() {
		Usuario usuario = (Usuario) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("usuario");
		return usuario.isAdministrador();
	}

	public void alterarAdministrador(AjaxBehaviorEvent e) {

		if (this.administrador)
			this.acessos.clear();

	}

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

	public String deleteEmail() {

		return null;
	}

	public String deleteEmail(Email email) {
		listaEmails.remove(email);
		return null;
	}

	public List<SelectItem> getValidadeDias() {

		List<SelectItem> diasValidade = new ArrayList<SelectItem>();

		for (int i = 1; i <= 30; i++) {
			diasValidade.add(new SelectItem(i, i + " dias"));
		}
		return diasValidade;
	}

	public List<SelectItem> getNiveisPermitidos() {

		/* Pega-se o usuário da sessão para verificar se ele é administrador. */
		Usuario usuario = (Usuario) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("usuario");

		/*
		 * Cria a lista que guarda os níveis que o usuario pode utilizar para
		 * enviar o convite.
		 */
		List<SelectItem> niveisPermissao = new ArrayList<SelectItem>();

		/*
		 * Caso seja administrador pode convidar outros administradores,
		 * gerentes para qualquer instituição do sistema alem de catalogadores
		 * e revisores. Caso contrário pode convidar apenas níveis de acesso
		 * inferiores a Gerente.
		 */
		if (usuario.isAdministrador()) {
			niveisPermissao.add(new SelectItem("GERENTE", "Gerente"));
			niveisPermissao.add(new SelectItem("CATALOGADOR", "Catalogador"));
			niveisPermissao.add(new SelectItem("REVISOR", "Revisor"));

		} else {
			niveisPermissao.add(new SelectItem("CATALOGADOR", "Catalogador"));
			niveisPermissao.add(new SelectItem("REVISOR", "Revisor"));
		}

		return niveisPermissao;
	}

	public List<SelectItem> getInstituicoesPermitidas() {
		/*
		 * Pega o usuário da seção para procurar as instituição para o qual
		 * ele pode enviar convite.
		 */
		Usuario usuario = (Usuario) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("usuario");

		/*
		 * Se for um administrador passamos o grupo como null para recuperar
		 * todas instituições do sistema, se for um usuario normal criamos o
		 * grupo Gerente para recuperar apenas as instituições em que o
		 * usuário tem nivel de acesso de gerente (apenas gerente pode enviar
		 * convites).
		 */
		Grupo grupo;
		if (usuario.isAdministrador())
			grupo = null;
		else
			grupo = new Grupo("GERENTE");

		/*
		 * Criamos a lista onde será salva as instituições do usuário e em
		 * seguida recuperamos as instituições pelo método implementado no
		 * EJB.
		 */
		List<Instituicao> instituicoesUsuario = new ArrayList<Instituicao>();
		instituicoesUsuario = this.enviarConviteEJB.getInstituicoesPermitidas(
				usuario, grupo);

		/*
		 * Agora é populado a lista de SelectItem para exibir as opções ao
		 * usuário.
		 */
		List<SelectItem> listaInstituicoes = new ArrayList<SelectItem>();
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

	public String removerAcesso(Acesso a) {
		this.acessos.remove(a);
		return null;
	}

	public String adicionarAcesso() {
		if (!this.administrador) {
			Instituicao i = new Instituicao();
			Usuario u = new Usuario();
			Grupo g = new Grupo();
			Acesso a = new Acesso();
			a.setInstituicao(i);
			a.setGrupo(g);
			a.setUsuario(u);
			a.setValidade(true);
			this.acessos.add(a);
		}
		return null;
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

	public boolean isRenderizarInstituicao() {
		return renderizarInstituicao;
	}

	public void setRenderizarInstituicao(boolean renderizarInstituicao) {
		this.renderizarInstituicao = renderizarInstituicao;
	}

	public List<Acesso> getAcessos() {
		return acessos;
	}

	public void setAcessos(List<Acesso> acessos) {
		this.acessos = acessos;
	}

	public boolean isAdministrador() {
		return administrador;
	}

	public void setAdministrador(boolean administrador) {
		this.administrador = administrador;
	}

}
