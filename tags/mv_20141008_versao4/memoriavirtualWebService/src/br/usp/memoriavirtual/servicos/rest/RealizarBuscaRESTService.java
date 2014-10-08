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

import com.sun.org.apache.xml.internal.security.utils.Base64;

@Stateless
@Path("/buscar")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class RealizarBuscaRESTService {

	@EJB
	private RealizarLoginRemote realizarLoginEJB;

	@EJB
	private RealizarBuscaSimplesRemote realizarBuscaEJB;

	@GET
	@Produces("text/html")
	public String buscar(@Context HttpServletRequest request) {
		if(validaCliente(request)){
			System.out.println("eita");
			return "<html><head></head><body>"
					+ "Cliente Validado Com Sucesso!<br/>"
					+ "Servi&ccedil;os Disponiveis:<br/>"
					+ "<table style='border: solid 1px;'>"
					+ "	<tr>"
					+ "		<td>buscar</td>"
					+ "		<td>path: /{stringDeBusca}/{numeroDaPagina}</td>"
					+"	</tr>"
					+ "	<tr>"
					+ "		<td>buscarPorInstituicao</td>"
					+ "		<td>path: /{stringDeBusca}/{numeroDaPagina}/{tamanhoPagina}/{nomeInstituicao}</td>"
					+ " </tr>"
					+ "	<tr>"
					+ "		<td>buscarMidiaPorContainer</td>"
					+ "		<td>path: /{container}</td>"
					+ "	</tr>"
					+ "</table>"
					+ "</body></html>";
		}else{
			return "<html><head></head><body>"
					+ "Falha na Valida&ccedil;&atilde;o do Cliente<br/>"
					+ "Use um header de &lt;Authorization&gt; HTTP"
					+ "</body></html>";
		}
	}

	@GET
	@Path("/{stringDeBusca}/{numeroDaPagina}")
	public ArrayList<BemPatrimonial> buscar(
			@PathParam("stringDeBusca") String stringDeBusca,
			@PathParam("numeroDaPagina") int pagina,
			@Context HttpServletRequest request) {
		if (validaCliente(request)) {
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
	@Path("/{stringDeBusca}/{numeroDaPagina}/{tamanhoPagina}/{nomeInstituicao}")
	public ArrayList<BemPatrimonial> buscarPorInstituicao(
			@PathParam("stringDeBusca") String stringDeBusca,
			@PathParam("numeroDaPagina") int pagina,
			@PathParam("tamanhoPagina") int tamanhoPagina,
			@PathParam("nomeInstituicao") String nomeInstituicao,
			@Context HttpServletRequest request) {
		if (validaCliente(request)) {
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
			@PathParam("container") String idContainer,
			@Context HttpServletRequest request) {
		if (validaCliente(request)) {
			List<Multimidia> midias = null;
			midias = this.realizarBuscaEJB.getMidias(Long
					.parseLong(idContainer));
			return midias;
		}
		return null;
	}

	private boolean validaCliente(HttpServletRequest request) {
		String username = "";
		String password = "";
		try {
			String header = request.getHeader("authorization");
			String encodedText = header.substring(header.indexOf(" ") + 1);
			
			byte[] buf = null;
			buf = Base64.decode(encodedText.getBytes());
			
			String credentials = new String(buf);

			int p = credentials.indexOf(":");

			if (p > -1) {
				username = credentials.substring(0, p);
				password = credentials.substring(p + 1);
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
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
