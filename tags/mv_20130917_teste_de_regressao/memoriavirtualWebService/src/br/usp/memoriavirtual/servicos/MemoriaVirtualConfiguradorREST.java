package br.usp.memoriavirtual.servicos;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import br.usp.memoriavirtual.servicos.rest.RealizarBuscaService;

public class MemoriaVirtualConfiguradorREST extends Application {
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();

		classes.add(RealizarBuscaService.class);

		return classes;
	}

}
