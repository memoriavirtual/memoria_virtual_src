package br.usp.memoriavirtual.servicos;

import java.io.IOException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import br.usp.memoriavirtual.modelo.fachadas.ModeloException;

@Provider
@PreMatching
public class CORSFilter implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		final MultivaluedMap<String, Object> headers = responseContext.getHeaders();

		Context context = null;
		try {
			context = new InitialContext();
		} catch (NamingException e) {
			new ModeloException(e);
		}
		Properties propriedades = null;
		try {
			propriedades = (Properties) context.lookup("memoriavirtual.propriedades");
		} catch (NamingException e) {
			new ModeloException(e);
		}

		String dominiosPermitidos = propriedades.getProperty("dominiosPermitidos") != null
				? propriedades.getProperty("dominiosPermitidos") : "";

		headers.add("Access-Control-Allow-Origin", dominiosPermitidos);
		headers.add("Access-Control-Allow-Headers", "Authorization, Origin, X-Requested-With, Content-Type");
		headers.add("Access-Control-Expose-Headers", "Location, Content-Disposition");
		headers.add("Access-Control-Allow-Methods", "POST, PUT, GET, DELETE, HEAD, OPTIONS");
	}

}
