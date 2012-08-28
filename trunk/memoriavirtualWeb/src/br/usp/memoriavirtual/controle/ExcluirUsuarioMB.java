package br.usp.memoriavirtual.controle;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import br.usp.memoriavirtual.modelo.entidades.Acesso;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fabricas.remoto.AuditoriaFabricaRemote;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarCadastroUsuarioRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirUsuarioRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;
import br.usp.memoriavirtual.utils.MensagensDeErro;

public class ExcluirUsuarioMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6957046653709643226L;
	@EJB
	private ExcluirUsuarioRemote excluirUsuarioEJB;
	@EJB
	private MemoriaVirtualRemote memoriaVirtualEJB;
	@EJB
	private AuditoriaFabricaRemote auditoriaFabricaEJB;
	@EJB
	private EditarCadastroUsuarioRemote editarCadastroUsuarioEJB;

	private String nome;
	private int prazoValidade;
	private String instuicaoPertencente;
	private String nivelPermissao;
	private String justificativa;
	private String excluir;
	private Usuario usuario;
	private Usuario requerente;
	private String semelhante;
	private Usuario validador;

	private FacesContext context = FacesContext.getCurrentInstance();
	private String bundleName = "mensagens";
	private ResourceBundle bundle = context.getApplication().getResourceBundle(
			context, bundleName);

	private List<Usuario> usuarios = new ArrayList<Usuario>();
	private List<Usuario> semelhantes = new ArrayList<Usuario>();
	private List<String> nomeSemelhantes = new ArrayList<String>();
	private List<Acesso> acessos;

	private String aprovacao;

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setPrazoValidade(int prazoValidade) {
		this.prazoValidade = prazoValidade;
	}

	public void setInstituicaoPertencente(String instituicaoPertencente) {
		this.instuicaoPertencente = instituicaoPertencente;
	}

	public void setNivelPermissao(String nivelPermissao) {
		this.nivelPermissao = nivelPermissao;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	public void setExcluir(String excluir) {
		this.excluir = excluir;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setSemelhantes(List<Usuario> semelhantes) {
		this.semelhantes = semelhantes;
		List<String> aux = new ArrayList<String>();
		for (Usuario u : semelhantes) {
			aux.add(u.getNomeCompleto());
		}
		Collections.sort(aux);
		setNomeSemelhantes(aux);
	}

	public void setSemelhante(String semelhante) {
		this.semelhante = semelhante;
	}

	public void setNomeSemelhantes(List<String> nomeSemelhantes) {
		this.nomeSemelhantes = nomeSemelhantes;
	}

	public String getNome() {
		return this.nome;
	}

	public int getPrazoValidade() {
		return this.prazoValidade;
	}

	public String getInstituicaoPertencente() {
		return this.instuicaoPertencente;
	}

	public String getNivelPermissao() {
		return this.nivelPermissao;
	}

	public String getJustificativa() {
		return this.justificativa;
	}

	public String getExcluir() {
		return this.excluir;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public List<Usuario> getSemelhantes() {
		return this.semelhantes;
	}

	public String getSemelhante() {
		return this.semelhante;
	}

	public List<String> getNomeSemelhantes() {
		return this.nomeSemelhantes;
	}

	public void listarUsuarios(AjaxBehaviorEvent event) {

		this.listarUsuarios();

	}

	public void listarUsuarios() {

		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		this.requerente = (Usuario) request.getSession()
				.getAttribute("usuario");

		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);

		this.usuarios = new ArrayList<Usuario>();

		try {
			this.usuarios = this.excluirUsuarioEJB.listarUsuarios(this.nome,
					this.requerente, this.requerente.isAdministrador());
		} catch (ModeloException m) {
			MensagensDeErro.getErrorMessage(
					"excluiroUsuarioErroUsuarioNaoEncontrado", "resultado");
			m.printStackTrace();
		}

		Usuario todos = new Usuario();
		todos.setId(bundle.getString("listarTodos"));
		todos.setNomeCompleto(bundle.getString("listarTodos"));
		this.usuarios.add(0, todos);

	}

	public String selecionarUsuario(Usuario usuario) {

		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);

		if (usuario.getNomeCompleto().equals(bundle.getObject("listarTodos"))) {
			this.nome = "";
			this.listarUsuarios();
			this.usuarios.remove(0);
			return null;
		}

		this.usuario = usuario;
		setNome(usuario.getNomeCompleto());
		this.usuarios.clear();
		this.listarAcessos();
		return "etapa2";
	}

	public String selecionarUsuario() {
		Usuario usuario = new Usuario();
		try {
			usuario = excluirUsuarioEJB.recuperarDadosUsuario(nome);
		} catch (ModeloException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return this.selecionarUsuario(usuario);

	}

	public void listarAcessos() {

		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		this.requerente = (Usuario) request.getSession()
				.getAttribute("usuario");

		this.acessos = new ArrayList<Acesso>();

		if (this.usuario == null) {
			try {
				this.usuario = this.excluirUsuarioEJB
						.recuperarDadosUsuario(nome);
			} catch (ModeloException e) {
				MensagensDeErro.getErrorMessage(
						"excluirUsuarioErroRecuperacao", "resultado");
			}

		}

		if (requerente.isAdministrador()) {
			try {
				this.acessos = excluirUsuarioEJB.listarAcessos(this.usuario);
			} catch (ModeloException m) {
				m.printStackTrace();
			}
		}

	}

	public List<SelectItem> getAprovadores() {

		List<SelectItem> itens = new ArrayList<SelectItem>();
		List<Usuario> aprovadores = new ArrayList<Usuario>();

		try {
			aprovadores = excluirUsuarioEJB.listarAprovadores(this.requerente,
					this.usuario);
		} catch (ModeloException m) {
			m.printStackTrace();
		}

		for (Usuario u : aprovadores) {
			itens.add(new SelectItem(u.getNomeCompleto(), u.getNomeCompleto()));
		}

		return itens;
	}

	public String marcarExclusao() {

		Date dataValidade = new Date();
		DateFormat formatoData = DateFormat.getDateInstance();
		GregorianCalendar gc = new GregorianCalendar();
		gc.add(GregorianCalendar.HOUR, 24 * this.prazoValidade);
		dataValidade = gc.getTime();

		try {
			this.validador = (Usuario) this.excluirUsuarioEJB
					.recuperarDadosUsuario(getSemelhante());
		} catch (Exception e) {
			MensagensDeErro.getErrorMessage(
					"excluiroUsuarioErroUsuarioNaoEncontrado", "resultado");
			return null;
		}

		try {

			// registra a autoria do pedido de exclus�o
			this.auditoriaFabricaEJB.auditarExcluirUsuario(this.requerente,
					this.usuario.getId(), this.justificativa);
			// registra um objeto Aprovacao
			Long apr = this.excluirUsuarioEJB.registrarAprovacao(this.validador,
					this.usuario.getId(), dataValidade);
			// marca a institui��o a ser exclu�da para que a mesma n�o seja mais
			// utilizada
			this.excluirUsuarioEJB.marcarUsuarioExcluido(this.usuario, false,
					false);
			
			String ap = this.memoriaVirtualEJB.embaralhar(String.valueOf(apr));
			String us = this.memoriaVirtualEJB.embaralhar(this.usuario.getId());

			this.memoriaVirtualEJB
					.enviarEmail(
							this.validador.getEmail(),
							bundle.getString("excluirUsuarioEmailAssunto"),
							bundle.getString("excluirUsuarioEmailMensagem")
									+ "\n"
									+ "\n"
									+ bundle.getString("excluirUsuarioNome")
									+ ": "
									+ this.getNome()
									+ "\n"
									+ bundle.getString("excluirUsuarioInstituicao")
									+ ": "
									+ this.getInstituicaoPertencente()
									+ "\n"
									+ bundle.getString("excluirUsuarioNivelPermissao")
									+ ": "
									+ this.getNivelPermissao()
									+ "\n"
									+ bundle.getString("excluirUsuarioJustificativa")
									+ ": "
									+ this.getJustificativa()
									+ "\n"
									+ bundle.getString("excluirUsuarioRequisitor")
									+ ": "
									+ this.requerente.getNomeCompleto()
									+ "\n"
									+ bundle.getString("excluirUsuarioPrazoValidade")
									+ ": "
									+ formatoData.format(dataValidade)
									+ "\n\n"
									+ bundle.getString("excluirUsuarioEmailMensagemURL")
									+ "\n"
									+ "\n"
									+ "http://"
									+ memoriaVirtualEJB.getURLServidor()
									+ "/excluirusuario?"
									+ "aprovacao="
									+ ap
									+ "&usuario="
									+ us
									+ "\n\n"
									+ bundle.getString("excluirUsuarioEmailMensagemFim")
									+ "\n" + "\n");

			// mensagem de sucesso
			MensagensDeErro.getSucessMessage("excluirUsuarioEnviandoEmail",
					"resultado");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "true";

	}

	public String voltarEtapa1() {
		return "etapa1";
	}

	public String cancelar() {
		this.usuario = null;
		this.nome = "";
		this.nivelPermissao = "";
		this.justificativa = "";
		this.semelhante = "";
		this.instuicaoPertencente = "";
		return "cancelar";
	}

	public void validateJustificativa(AjaxBehaviorEvent e) {
		this.validateJustificativa();
	}

	public boolean validateJustificativa() {
		if (this.justificativa.equals("")) {
			MensagensDeErro.getErrorMessage(
					"excluirUsuarioErroJustificativaVazia",
					"validacaoJustificativa");
			return false;
		} else if (this.justificativa.length() < 10) {
			MensagensDeErro.getErrorMessage(
					"excluiroUsuarioErroJustificativaCurta",
					"validacaoJustificativa");
			return false;
		}
		return true;
	}

	public List<SelectItem> getPrazos() {

		List<SelectItem> prazos = new ArrayList<SelectItem>();

		for (int i = 1; i <= 30; ++i) {
			if (i == 1)
				prazos.add(new SelectItem(i, String.valueOf(i) + " dia"));
			else
				prazos.add(new SelectItem(i, String.valueOf(i) + " dias"));
		}

		return prazos;

	}

	public List<Acesso> getAcessos() {
		return acessos;
	}

	public void setAcessos(List<Acesso> acessos) {
		this.acessos = acessos;
	}

	public String getAprovacao() {
		return aprovacao;
	}

	public void setAprovacao(String aprovacao) {
		this.aprovacao = aprovacao;
	}

	public String excluirUsuario() {
		try {
			this.excluirUsuarioEJB.excluirUsuario(this.aprovacao);
			MensagensDeErro.getSucessMessage("excluirUsuarioSucessoOperacao",
					"resultado");
			// this.auditoriaFabricaEJB.auditarAutorizarExcluirUsuario(autorAcao,
			// atributoSignificativo, justificativa);
		} catch (ModeloException m) {
			m.printStackTrace();
			MensagensDeErro.getErrorMessage("excluirUsuarioErroOperacao",
					"resultado");
		}

		return null;
	}

}
