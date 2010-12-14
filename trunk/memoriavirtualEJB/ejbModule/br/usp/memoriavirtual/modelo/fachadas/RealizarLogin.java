package br.usp.memoriavirtual.modelo.fachadas;

import javax.ejb.Stateless;

import br.usp.memoriavirtual.modelo.fachadas.remoto.RealizarLoginRemote;

/**
 * Session Bean implementation class RealizarLogin
 */
@Stateless(mappedName = "RealizarLogin")
public class RealizarLogin implements RealizarLoginRemote {

    /**
     * Default constructor. 
     */
    public RealizarLogin() {
        // TODO Auto-generated constructor stub
    }

}
