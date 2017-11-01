package br.usp.memoriavirtual.servlet.rest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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

@Path("/")
@Produces({ MediaType.APPLICATION_JSON })
@javax.enterprise.context.RequestScoped
public class Listar implements Serializable{

	private static final long serialVersionUID = 6983171202115368169L;

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
				instituicoes = editarInstituicaoEJB.listarInstituicoes(busca);
			}
			else {
				instituicoes = new ArrayList<Instituicao>();
				List<Instituicao> parcial = null;
				for(Acesso a : usuario.getAcessos()){
					parcial = editarInstituicaoEJB.listarInstituicoes(busca, a.getGrupo(), usuario);
					for(Instituicao i: parcial){
						if(!instituicoes.contains(i)){
							instituicoes.add(i);
						}
					}
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
	@Path("/listarbempatrimonial/{inst}")
	public List<BemPatrimonial> listarBensInstituicao(@PathParam("inst") String inst, @QueryParam("busca") String busca, 
			@Context HttpServletRequest request) {
		
		//Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
		Integer instituicaoId = Integer.parseInt(inst);

		try {
			List<BemPatrimonial> bensPatrimoniais = realizarBuscaSimplesEJB.buscar(busca);
			List<BemPatrimonial> resultado = new ArrayList<BemPatrimonial>();
			
			for (BemPatrimonial b : bensPatrimoniais) {
				if (b.getInstituicao().getId() == instituicaoId) {
					resultado.add(b);
				}
			}

			return resultado;

		} catch (ModeloException m) {
			m.printStackTrace();
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
	
	boolean verificaAcesso(BemPatrimonial b, List<Acesso> acessos){
		boolean temAcesso = false;
		for(Acesso a : acessos){
			if(a.getInstituicao().getId()==b.getInstituicao().getId()){
				temAcesso = true;
			}
		}
		return temAcesso;
	}
	
	@GET
	@Path("/listarbempatrimonial")
	public List<BemPatrimonial> listarBens(@QueryParam("busca") String busca,@Context HttpServletRequest request){
			Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
		try {
			List<BemPatrimonial> bensPatrimoniais = realizarBuscaSimplesEJB.buscar(busca);
			List<BemPatrimonial> excluir = new ArrayList<BemPatrimonial>();
			if(!usuario.isAdministrador()){
				for(BemPatrimonial b : bensPatrimoniais){
					if(!verificaAcesso(b, usuario.getAcessos())){
						excluir.add(b);
					}
				}
				bensPatrimoniais.removeAll(excluir);
			}
			
			return bensPatrimoniais;
			
		} catch (ModeloException m) {
			m.printStackTrace();
		}		
		return null;
	}
	
	@GET
	@Path("/listarbempatrimonialrevisao")
	public List<BemPatrimonial> listarBensRevisao(@QueryParam("busca") String busca,@Context HttpServletRequest request){
			Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
			try {
			List<BemPatrimonial> bensPatrimoniais = realizarBuscaSimplesEJB.buscar(busca);
			List<BemPatrimonial> excluir = new ArrayList<BemPatrimonial>();
			if(!usuario.isAdministrador()){
				for(BemPatrimonial b : bensPatrimoniais){
					if(!verificaAcesso(b, usuario.getAcessos())){
						excluir.add(b);
					}
				}
			}
			for(BemPatrimonial b : bensPatrimoniais){
				if(b.getInstituicao().getRevisao() == false)
					excluir.add(b);
			}
			bensPatrimoniais.removeAll(excluir);
			return bensPatrimoniais;
		} catch (Exception e) {
			e.printStackTrace();
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
