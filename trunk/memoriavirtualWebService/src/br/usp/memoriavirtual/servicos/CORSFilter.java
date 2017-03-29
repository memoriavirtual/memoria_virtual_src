package br.usp.memoriavirtual.servicos;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

@Provider
@PreMatching
public class CORSFilter implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		final MultivaluedMap<String, Object> headers = responseContext.getHeaders();

		headers.add("Access-Control-Allow-Origin", "*");
		headers.add("Access-Control-Allow-Headers", "Authorization, Origin, X-Requested-With, Content-Type");
		headers.add("Access-Control-Expose-Headers", "Location, Content-Disposition");
		headers.add("Access-Control-Allow-Methods", "POST, PUT, GET, DELETE, HEAD, OPTIONS");
	}

}
