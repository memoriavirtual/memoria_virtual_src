package br.usp.memoriavirtual.servicos.rest;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.RealizarBuscaSimplesRemote;

@Stateless
@Path("/buscar")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class RealizarBuscaService {

	@EJB
	private RealizarBuscaSimplesRemote realizarBuscaEJB;

	@GET
	@Path("/{stringDeBusca}")
	public ArrayList<BemPatrimonial> buscar(@PathParam("stringDeBusca") String stringDeBusca) {
		try {
			return realizarBuscaEJB.buscar(stringDeBusca);
		} catch (ModeloException e) {
			e.printStackTrace();
		}
		return null;

	}
}
