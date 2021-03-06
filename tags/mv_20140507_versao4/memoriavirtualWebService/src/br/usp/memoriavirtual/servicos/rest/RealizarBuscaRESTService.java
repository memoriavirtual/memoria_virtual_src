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
public class RealizarBuscaRESTService {

	@EJB
	private RealizarBuscaSimplesRemote realizarBuscaEJB;

	@GET
	public ArrayList<BemPatrimonial> buscar() {
		ArrayList<BemPatrimonial> bensPatrimoniais = null;
		try {
			bensPatrimoniais = this.realizarBuscaEJB.buscar("",1);
		} catch (ModeloException e) {
			e.printStackTrace();
		}
		return bensPatrimoniais;
	}

	@GET
	@Path("/{stringDeBusca}/{numeroDaPagina}")
	public ArrayList<BemPatrimonial> buscar(@PathParam("stringDeBusca") String stringDeBusca,
			@PathParam("numeroDaPagina") int pagina) {
		ArrayList<BemPatrimonial> bensPatrimoniais = null;
		try {
			bensPatrimoniais = this.realizarBuscaEJB.buscar(stringDeBusca,pagina);
		} catch (ModeloException e) {
			e.printStackTrace();
		}
		return bensPatrimoniais;

	}
}
