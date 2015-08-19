package br.usp.memoriavirtual.servicos.rest;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.CadastrarBemPatrimonialRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarInstituicaoRemote;


@RequestScoped
@Path("/cadastrar")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class CadastrarBemPatrimonialRESTService extends BaseRESTService{
	
	@EJB
	private CadastrarBemPatrimonialRemote cadastrarBemPatrimonialEJB;
	@EJB
	private EditarInstituicaoRemote editarInstituicaolEJB;
	
	@POST
	@Path("/bemPatrimonial/{instituicao}")
	public Response cadastrar(
			BemPatrimonial bemPatrimonial,
			@PathParam("instituicao") Integer idInstituicao,
			@Context HttpServletRequest request) {
		
		if(validaCliente(request)){
		
			try {
				//Recupera instituicao pelo ID e salva no bemPatrimonial
				Instituicao instituicao = editarInstituicaolEJB.getInstituicao(idInstituicao);
				bemPatrimonial.setInstituicao(instituicao);
			
				this.cadastrarBemPatrimonialEJB.cadastrarBemPatrimonial(bemPatrimonial);
				return Response.status(Response.Status.CREATED).build();
			} catch (ModeloException e) {
				e.printStackTrace();
				return Response.serverError().entity(e.getMessage()).build();
			}
		}
		return null;		
	}
}
