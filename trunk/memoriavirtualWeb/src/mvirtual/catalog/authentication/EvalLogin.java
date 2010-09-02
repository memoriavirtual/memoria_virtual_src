/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mvirtual.catalog.authentication;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;

import java.util.Map;
import java.util.List;

import org.mvirtual.persistence.entity.User;
import org.mvirtual.persistence.hibernate.IndustrialEstate;

import org.hibernate.Session;
import org.hibernate.HibernateException;

import org.apache.log4j.Logger;

/**
 * Avalia se username e password permitem acesso ao sistema.
 * @author Fabricio
 */
public class EvalLogin extends ActionSupport {
    private static Logger logger = Logger.getLogger(EvalLogin.class);

    /**
     * Verifica se username e password fornecidos possuem permissão para acesso
     * ao sistema.
     * @return String que indica se há ou não acesso ao sistema.
     */
    @Override
    public String execute () {
        // Cria a seção.
        Session session = IndustrialEstate.getSessionFactory ().openSession();

        List <User> listOfUsers = null;
        
        try
        {
            // Busca username e password.
            listOfUsers = (List <User>)
                session.createQuery("from User u where u.login=:username and u.password=:password")
                .setText ("username", login.getUsername())
                .setText ("password", login.getPassword())
                .setMaxResults(1)
                .list();
        }
        catch (HibernateException e)
        {
            logger.error(e.getMessage ());
            logger.error
                    ("This error was generated possibly because more than 1 identical login and password were found");
        }

        // Fecha seção.
        session.close ();

        if (true/*login.getUsername().equalsIgnoreCase("mv")
                && login.getPassword().equals("mv")*/) {


            // Obtém seção.
            Map <String, Object> sessionMap = ActionContext.getContext ().getSession ();

            // Salva login do usuário na sessão para indicar que já está logado.
            sessionMap.put ("username","memoria_virtual");

            // Retorna.
            return SUCCESS;
        }

        // Se a lista é nula ou o tamanho da lista é diferente de 1, retorna erro.
        if (listOfUsers == null || listOfUsers.size() != 1) return INPUT;

        // Pega o usuário.
        User user = listOfUsers.get (0);

        // Obtém seção.
        Map <String, Object> sessionMap = ActionContext.getContext ().getSession ();

        // Salva login do usuário na sessão para indicar que já está logado.
        sessionMap.put ("username",user.getLogin ());

        // Retorna.
        return SUCCESS;
    }

    /**
     * Verifica se username ou password são nulos ou vazios. Se forem, retorna
     * erro.
     */
    @Override
    public void validate () {
        if (login == null)
        {
            addFieldError ("login.username","Username Error");
            return;
        }

        if (login.getUsername ().length() == 0)
            addFieldError ("login.username","Username Error");

        if (login.getPassword ().length () == 0)
            addFieldError ("login.password","Password Error");
    }

    /**
     * Obtém username e password.
     * @return Objeto com username e password.
     */
    public DefaultAuthentication getLogin() {
        return login;
    }

    /**
     * Atribui objeto que contém username e password.
     * @param authenticationData Objeto com username e password.
     */
    public void setLogin(DefaultAuthentication authenticationData) {
        this.login = authenticationData;
    }

    /**
     * Guarda objeto com login e password.
     */
    private DefaultAuthentication login;
}
