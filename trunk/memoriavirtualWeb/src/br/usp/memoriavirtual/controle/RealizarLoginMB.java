package br.usp.memoriavirtual.controle;

import java.io.IOException;
import java.io.Serializable;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.usp.memoriavirtual.modelo.entidades.Acesso;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.RealizarLoginRemote;
import br.usp.memoriavirtual.utils.MensagensDeErro;

public class RealizarLoginMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1005061522826383091L;
	@EJB
	private RealizarLoginRemote realizarLoginEJB;
	@EJB
	private MemoriaVirtualRemote memoriaVirtualEJB;
	private String usuario = "";
	private String senha = "";

	public RealizarLoginMB() {
		super();
		this.redirecionarUsuarioLogado();
	}

	/**
	 * Verifica as informacões de usuário e senha na base de dados.
	 * 
	 * @return true se o usuário foi autenticado com sucesso ou false caso
	 *         contrário.
	 * @throws CloneNotSupportedException
	 * @throws UnknownHostException
	 * @throws SocketException
	 */
	public String autenticarUsuario() {
		boolean autenticado = false;

		Usuario usuarioAutenticado = null;
		try {
			usuarioAutenticado = realizarLoginEJB.realizarLogin(
					this.getUsuario(), this.getSenha());
		} catch (ModeloException e) {
			e.printStackTrace();
		}

		if (usuarioAutenticado != null) {
			autenticado = true;

			/*
			 * Coloca o usuario autenticado no sessao.
			 */
			HttpServletRequest request = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();
			request.getSession().setAttribute("usuario", usuarioAutenticado);

			/*
			 * Coloca a lista de acessos do usuario no sessao.
			 */
			List<Acesso> listaAcessos = memoriaVirtualEJB
					.listarAcessos(usuarioAutenticado);
			request.getSession().setAttribute("acessos", listaAcessos);

		} else {
			FacesContext.getCurrentInstance().getExternalContext()
					.invalidateSession();
		}

		if (!autenticado)
			MensagensDeErro.getErrorMessage("realizarLoginErro", "resultado");

		this.setSenha(null);

		return autenticado ? "sucesso" : "falha";
	}

	public String redirecionarUsuarioLogado() {

		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		Usuario u = (Usuario) request.getSession().getAttribute("usuario");

		if (u != null) {
			ExternalContext context = FacesContext.getCurrentInstance()
					.getExternalContext();
			try {
				context.redirect("restrito/index.jsf");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;

	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 *            the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the senha
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * @param senha
	 *            the senha to set
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}

}
