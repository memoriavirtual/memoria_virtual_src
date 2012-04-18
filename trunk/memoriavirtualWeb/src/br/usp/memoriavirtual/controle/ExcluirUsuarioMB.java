package br.usp.memoriavirtual.controle;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;

import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirUsuarioRemote;

public class ExcluirUsuarioMB {

	@EJB
	private ExcluirUsuarioRemote excluirUsuarioEJB;

	private String nomeExcluir;
	private int prazoValidade;
	private String instuicaoPertencente;
	private String nivelPermissao;
	private String justificativa;
	private String excluir;
	private Usuario usuario;
	private Usuario eliminador;
	private String semelhante;

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
		listaUsuarios = this.excluirUsuarioEJB.listarUsuarios(this.nomeExcluir,
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
			e.printStackTrace();
			return null;
		}
		setNomeExcluir(usuario.getNomeCompleto());
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
		usuario = null;
		return "etapa3";
	}

	public String excluirEtapa3() {
		return null;
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

}
