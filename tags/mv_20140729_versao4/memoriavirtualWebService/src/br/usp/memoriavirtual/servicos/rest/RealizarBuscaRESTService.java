package br.usp.memoriavirtual.servicos.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import br.usp.memoriavirtual.modelo.entidades.Multimidia;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.RealizarBuscaSimplesRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.RealizarLoginRemote;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

@Stateless
@Path("/buscar")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class RealizarBuscaRESTService {

	@EJB
	private RealizarLoginRemote realizarLoginEJB;

	@EJB
	private RealizarBuscaSimplesRemote realizarBuscaEJB;

	@Context
	private HttpServletRequest request;

	@GET
	public String buscar() {
		if(validaClient()){
			return "cliente Validado com sucesso";
		}else{
			return "falha na validação do cliente";
		}
	}

	@GET
	@Path("/{stringDeBusca}/{numeroDaPagina}")
	public ArrayList<BemPatrimonial> buscar(
			@PathParam("stringDeBusca") String stringDeBusca,
			@PathParam("numeroDaPagina") int pagina) {
		if (validaClient()) {
			ArrayList<BemPatrimonial> bensPatrimoniais = null;
			try {
				bensPatrimoniais = this.realizarBuscaEJB.buscar(stringDeBusca,
						pagina);
			} catch (ModeloException e) {
				e.printStackTrace();
			}
			for (BemPatrimonial bem : bensPatrimoniais) {
				bem.getContainerMultimidia().setMultimidia(null);
			}
			return bensPatrimoniais;
		}
		return null;

	}

	@GET
	@Path("/{stringDeBusca}/{numeroDaPagina}/{tamanhoPagina}/{nomeInstituicao}/")
	public ArrayList<BemPatrimonial> buscarPorInstituicao(
			@PathParam("stringDeBusca") String stringDeBusca,
			@PathParam("numeroDaPagina") int pagina,
			@PathParam("tamanhoPagina") int tamanhoPagina,
			@PathParam("nomeInstituicao") String nomeInstituicao) {
		if (validaClient()) {
			ArrayList<BemPatrimonial> bensPatrimoniais = null;

			try {
				bensPatrimoniais = this.realizarBuscaEJB.buscarPorInstituicao(
						stringDeBusca, pagina, tamanhoPagina, nomeInstituicao);
			} catch (ModeloException e) {
				e.printStackTrace();
			}
			return bensPatrimoniais;
		}
		return null;

	}

	@GET
	@Path("/{container}")
	public List<Multimidia> buscarMidiaPorContainer(
			@PathParam("container") String idContainer) {
		if (validaClient()) {
			List<Multimidia> midias = null;
			midias = this.realizarBuscaEJB.getMidias(Long
					.parseLong(idContainer));
			return midias;
		}
		return null;
	}

	private boolean validaClient() {

		String username = "";
		String password = "";
		try {
			String header = request.getHeader("authorization");

			String encodedText = header.substring(header.indexOf(" ") + 1);

			byte[] buf = null;
			try {
				buf = Base64.decode(encodedText.getBytes());
			} catch (Base64DecodingException e) {
				e.printStackTrace();
			}
			String credentials = new String(buf);
			System.out.println("decoded text: " + credentials);

			int p = credentials.indexOf(":");

			if (p > -1) {
				username = credentials.substring(0, p);
				password = credentials.substring(p + 1);
			} else {
				throw new RuntimeException("Error in decoding");
			}
		} catch (Exception e) {
			
		}
		return autentica(username, password);
	}

	public boolean autentica(String usuario, String senha) {
		boolean autenticado = false;

		Usuario usuarioAutenticado = null;
		try {
			usuarioAutenticado = realizarLoginEJB.realizarLogin(usuario, senha);
		} catch (ModeloException e) {
			e.printStackTrace();
		}

		if (usuarioAutenticado != null) {
			autenticado = true;
		}

		return autenticado;
	}

}
