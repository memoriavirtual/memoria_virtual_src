package br.usp.memoriavirtual.controle;

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
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fabricas.remoto.AuditoriaFabricaRemote;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirUsuarioRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;
import br.usp.memoriavirtual.utils.MensagensDeErro;

public class ExcluirUsuarioMB {

	@EJB
	private ExcluirUsuarioRemote excluirUsuarioEJB;
	@EJB
	private MemoriaVirtualRemote memoriaVirtualEJB;
	@EJB
	private AuditoriaFabricaRemote auditoriaFabricaEJB;

	private String nomeExcluir;
	private int prazoValidade;
	private String instuicaoPertencente;
	private String nivelPermissao;
	private String justificativa;
	private String excluir;
	private Usuario usuario;
	private Usuario eliminador;
	private String semelhante;
	private Usuario validador;

	private FacesContext context = FacesContext.getCurrentInstance();
	private String bundleName = "mensagens";
	private ResourceBundle bundle = context.getApplication().getResourceBundle(
			context, bundleName);

	private List<Usuario> usuarios = new ArrayList<Usuario>();
	private List<Usuario> semelhantes = new ArrayList<Usuario>();
	private List<String> nomeSemelhantes = new ArrayList<String>();

	public void setNomeExcluir(String nomeExcluir) {
		this.nomeExcluir = nomeExcluir;
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

	public String getNomeExcluir() {
		return this.nomeExcluir;
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
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		this.eliminador = (Usuario) request.getSession()
				.getAttribute("usuario");
		usuarios.clear();
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		listaUsuarios = this.excluirUsuarioEJB.listarUsuarios(this.nomeExcluir,this.eliminador,
				this.eliminador.isAdministrador());
		setUsuarios(listaUsuarios);
		usuario = null;
		return;
	}

	public String selecionarUsuario(Usuario usuario) {
		this.usuario = usuario;
		setNomeExcluir(usuario.getNomeCompleto());
		return null;
	}

	public String excluirEtapa1() {
		try {
			this.usuario = (Usuario) this.excluirUsuarioEJB
					.recuperarDadosUsuario(getNomeExcluir());
		} catch (Exception e) {
			MensagensDeErro.getErrorMessage(
					"excluiroUsuarioErroUsuarioNaoEncontrado", "resultado");
			return null;
		}
		setNomeExcluir(usuario.getNomeCompleto());
		try{
		setNivelPermissao(memoriaVirtualEJB.getURLServidor());}
		catch(ModeloException e){}
		if (usuario.isAdministrador()) {
			this.nivelPermissao = "Administrador";
		}
		return "etapa2";
	}

	public String excluirEtapa2() {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		this.eliminador = (Usuario) request.getSession()
				.getAttribute("usuario");
		usuarios.clear();
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		listaUsuarios = this.excluirUsuarioEJB.listarSemelhantes(
				this.eliminador.getId(), this.eliminador.isAdministrador());
		setSemelhantes(listaUsuarios);
		return "etapa3";
	}

	public String excluirEtapa3() {
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
			this.memoriaVirtualEJB
					.enviarEmail(
							"diegominatel@gmail.com",
							bundle.getString("excluirUsuarioEmailTitulo"),
							bundle.getString("excluirUsuarioEmailMensagem")
									+ "\n"
									+ "\n"
									+ bundle.getString("excluirUsuarioNome")
									+ ": "
									+ this.getNomeExcluir()
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
									+ this.eliminador.getNomeCompleto()
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
									+ "/excluir?"
									+ "chaveEstrangeira="
									+ this.usuario.getId()
									+ "\n\n"
									+ bundle.getString("excluirUsuarioEmailMensagemFim")
									+ "\n" + "\n");
			//registra a autoria do pedido de exclusão
			this.auditoriaFabricaEJB.auditarExcluirUsuario(this.eliminador, this.usuario.getId(),this.justificativa);
			//registra um objeto Aprovacao
			this.excluirUsuarioEJB.registrarAprovacao(this.validador,this.usuario.getId(),dataValidade);
			//marca a instituição a ser excluída para que a mesma não seja mais utilizada 
			this.excluirUsuarioEJB.marcarUsuarioExcluido(this.usuario,false,false);
			// mensagem de sucesso
			MensagensDeErro.getSucessMessage("excluirUsuarioEnviandoEmail",
					"resultado");
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (ModeloException e) {
			e.printStackTrace();
			e.getCause();
		} catch (NullPointerException e){
			e.printStackTrace();
		}
		return "true";

	}

	public String voltarEtapa1() {
		return "etapa1";
	}

	public String cancelar() {
		this.usuario = null;
		this.nomeExcluir = "";
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

}
