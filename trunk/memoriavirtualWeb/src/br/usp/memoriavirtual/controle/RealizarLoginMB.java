package br.usp.memoriavirtual.controle;

import java.net.SocketException;
import java.net.UnknownHostException;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.remoto.RealizarLoginRemote;

public class RealizarLoginMB {

    @EJB
    private RealizarLoginRemote realizarLoginEJB;
    private String usuario = "";
    private String senha = "";

    /**
     * html> <head> <meta http-equiv="Content-Type"
     * content="text/html; charset=ISO-8859-1"> <title>Logado com
     * sucesso</title> </head> <body>
     * 
     * <h1>Logado com sucesso</h1>
     * 
     * </body> </html> Verifica as informacões de usuário e senha na base de
     * dados.
     * 
     * @return <code>true</code> se o usuário foi autenticado com sucesso ou
     *         <code>false</code> caso contrário.
     * @throws CloneNotSupportedException
     * @throws UnknownHostException
     * @throws SocketException
     */
    public String autenticarUsuario() {
	boolean autenticado = false;

	Usuario usuarioAutenticado = null;
	try {
	    usuarioAutenticado = realizarLoginEJB.realizarLogin(this.getUsuario(), this.getSenha());
	} catch (CloneNotSupportedException e) {
	    e.printStackTrace();
	}

	if (usuarioAutenticado != null) {
	    autenticado = true;
	    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usuarioAutenticado);
	} else {
	    FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	}

	if (!autenticado)
	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuário ou Senha incorretos."));
	
	this.setSenha(null);

	return autenticado ? "sucesso" : "falha";
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
