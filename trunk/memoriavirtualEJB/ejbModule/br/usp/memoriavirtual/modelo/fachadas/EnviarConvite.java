package br.usp.memoriavirtual.modelo.fachadas;

import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.usp.memoriavirtual.modelo.fachadas.remoto.EnviarConviteRemote;

@Stateless(mappedName = "EnviarConvite")
public class EnviarConvite implements EnviarConviteRemote {

    @SuppressWarnings("unused")
	@PersistenceContext(unitName = "memoriavirtual")
    private EntityManager entityManager;

    /**
     * Default constructor.
     */
    public EnviarConvite() {

    }

    /**
     * @return <code>"ok"</code> 
     * 				se emails são validos e ainda não foram cadastrados
     * @return <code>"email invalido: email"</code> 
     * 				se existe erro no email
     * 
     * @return <code>"email existente: email"</code>
     * 				se o email ja esta cadastrado
     */
    public String enviarConvite(ArrayList<String> emails, String validade, String nivelAcesso) {

    	return "ok";
    }

}
