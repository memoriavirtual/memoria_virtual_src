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

	private List<Usuario> usuarios = new ArrayList<Usuario>();

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
	
	public void setUsuario(Usuario usuario){
		this.usuario = usuario;
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
	
	public Usuario getUsuario(){
		return this.usuario;
	}

	public void listarUsuarios(AjaxBehaviorEvent event) {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		this.eliminador = (Usuario) request.getSession().getAttribute("usuario");
		usuarios.clear();
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		listaUsuarios = this.excluirUsuarioEJB.listarUsuarios(this.nomeExcluir, this.eliminador.isAdministrador());
		setUsuarios(listaUsuarios);
		usuario = null;
		return;
	}
	
	public String selecionarUsuario(Usuario usuario){ 
		this.usuario = usuario;
		setNomeExcluir(usuario.getNomeCompleto());
		return null;
	}
	
	public String excluirEtapa1(){
		this.nivelPermissao = this.usuario.getEmail();
		return "etapa2";
	}

}
