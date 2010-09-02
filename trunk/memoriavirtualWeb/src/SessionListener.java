/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSession;

import org.mvirtual.persistence.entity.Institution;
import org.mvirtual.persistence.hibernate.IndustrialEstate;

import org.hibernate.Session;
import org.hibernate.Transaction;

import mvirtual.catalog.SessionNames;

/**
 *
 * @author Fabricio
 */
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated (HttpSessionEvent event) {
        HttpSession httpSession = event.getSession();

        httpSession.setAttribute ("version", "0.0.0.1");
    }


    @Override
    public void sessionDestroyed (HttpSessionEvent event) {
    }
}
