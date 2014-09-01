package br.usp.memoriavirtual.servlet.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import br.usp.memoriavirtual.modelo.entidades.Acesso;
import br.usp.memoriavirtual.modelo.entidades.Autor;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarAutorRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarInstituicaoRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.RealizarBuscaSimplesRemote;

@Stateless
@Path("/")
@Produces({ MediaType.APPLICATION_JSON })
public class Listar{

	@EJB
	EditarInstituicaoRemote editarInstituicaoEJB;
	
	@EJB
	EditarAutorRemote editarAutorEJB;
	
	@EJB
	RealizarBuscaSimplesRemote realizarBuscaSimplesEJB;
	
	@EJB
	MemoriaVirtualRemote memoriaVirtualEJB;
	
	public Listar() {
		super();
	}

	@GET
	@Path("/listarinstituicoes")
	public List<Instituicao> listarInstituicoes(@QueryParam("busca") String busca,@Context HttpServletRequest request){
		try {
			Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
			
			List<Instituicao> instituicoes = null;
			
			if (usuario.isAdministrador()) {
				instituicoes = editarInstituicaoEJB
						.listarInstituicoes(busca);
			}
			else {
				instituicoes = new ArrayList<Instituicao>();
				List<Instituicao> parcial = null;
				for(Acesso a : usuario.getAcessos()){
					parcial = editarInstituicaoEJB.listarInstituicoes(busca, a.getGrupo(), usuario);
					instituicoes.addAll(parcial);
				}
			}
			
			return instituicoes;

		} catch (ModeloException m) {
			m.printStackTrace();
		}
		  catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@GET
	@Path("/listarautores")
	public List<Autor> listarAutores(@QueryParam("busca") String busca,@Context HttpServletRequest request){
		try {
			List<Autor> autores = null;

			autores = editarAutorEJB.listarAutores(busca);
			return autores;

		} catch (ModeloException m) {
			m.printStackTrace();
		}
		return null;
	}
	
	@GET
	@Path("/listarbempatrimonial")
	public List<BemPatrimonial> listarBens(@QueryParam("busca") String busca,@Context HttpServletRequest request){

		try {
			List<BemPatrimonial> bensPatrimoniais = realizarBuscaSimplesEJB.buscar(busca, 1);

			return bensPatrimoniais;
			
		} catch (ModeloException m) {
			m.printStackTrace();
		}
		
		return null;
	}
	
	@GET
	@Path("/listarusuarios")
	public List<Usuario> listarUsuarios(@QueryParam("busca") String busca,@Context HttpServletRequest request){
		try {

			Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");

			List<Usuario> usuarios = memoriaVirtualEJB.listarUsuarios(busca,usuario);
			
			return usuarios;
			
		} catch (ModeloException m) {
			m.printStackTrace();
		}
		return null;
	}
}
