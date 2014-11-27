package br.usp.memoriavirtual.servlet.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/listar")
public class ConfigurarListagemRest extends Application{
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();

		classes.add(Listar.class);

		return classes;
	}

}
