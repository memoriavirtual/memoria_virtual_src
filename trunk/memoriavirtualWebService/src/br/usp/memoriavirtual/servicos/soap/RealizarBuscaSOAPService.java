package br.usp.memoriavirtual.servicos.soap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import br.usp.memoriavirtual.modelo.entidades.Multimidia;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.RealizarBuscaSimplesRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.RealizarLoginRemote;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

@WebService(serviceName = "buscar")
public class RealizarBuscaSOAPService {

	@Resource
	WebServiceContext webServiceContext;
	
	@EJB
	private RealizarBuscaSimplesRemote realizarBuscaEJB;

	@EJB
	private RealizarLoginRemote realizarLoginEJB;
	
	@WebMethod(operationName = "testarAutenticacao")
	public String testarAutenticacao(){
		if(validaClient()){
			return "Cliente Validado com sucesso";
		}else{
			return "Falha na autentica&ccedil;&atilde;o do cliente: credenciais incorretas";
		}
		
	}
	
	@WebMethod(operationName = "buscar")
	public ArrayList<BemPatrimonial> buscar(@WebParam(name = "stringDeBusca") String stringDeBusca,@WebParam(name = "numeroDaPagina") int pagina) {
		if(validaClient()){
			ArrayList<BemPatrimonial> bensPatrimoniais = null;
			try {
				bensPatrimoniais = this.realizarBuscaEJB.buscar(stringDeBusca,pagina);
			} catch (ModeloException e) {
				e.printStackTrace();
			}
			return bensPatrimoniais;
		}
		return null;
	}
	
	@WebMethod(operationName = "buscarInstituicao")
	public ArrayList<BemPatrimonial> buscaPorInstituicao(@WebParam(name = "stringDeBusca") String stringDeBusca,@WebParam(name = "numeroDaPagina") int pagina,
			@WebParam(name = "tamanhoPagina") int tamanhoPagina,@WebParam(name = "nomeInstituicao") String nomeInstituicao) {
		if(validaClient()){
			ArrayList<BemPatrimonial> bensPatrimoniais = null;
			try {
				bensPatrimoniais = this.realizarBuscaEJB.buscarPorInstituicao(stringDeBusca,pagina,tamanhoPagina,nomeInstituicao);
			} catch (ModeloException e) {
				e.printStackTrace();
			}
			return bensPatrimoniais;
		}
		return null;
	}
	
	@WebMethod(operationName = "buscarMidias")
	public List<Multimidia> getMidias(@WebParam(name = "idBemPatrimonial") String idBemPatrimonial) {
		if(validaClient()){
			List<Multimidia> midias = null;
			midias = this.realizarBuscaEJB.getMidias(Long.parseLong(idBemPatrimonial));
			return midias;
		}
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	private boolean validaClient(){
		MessageContext mc = webServiceContext.getMessageContext();
		Map http_headers = (Map) mc.get(MessageContext.HTTP_REQUEST_HEADERS);
		
		String username = null;
		String password = null;
		
		try{
		
			List t = (List)http_headers.get("Authorization");
			if(t == null || t.size() == 0) {
				throw new RuntimeException("Cliente Validado com sucesso");
			}
			
			String encodedText = ((String) t.get(0)).substring(5);		
			
			byte[] buf = null;
			try {
				buf = Base64.decode(encodedText.getBytes());
			} catch (Base64DecodingException e) {
				e.printStackTrace();
			}
			String credentials = new String(buf);
			
			int p = credentials.indexOf(":");
			
			if(p > -1){
				username = credentials.substring(0,p);
				password = credentials.substring(p+1);
			} else {
				throw new RuntimeException("Erro na decodifica��o");
			}
		
		}catch(Exception e){
			throw new RuntimeException("Falha na autentica&ccedil;&atilde;o do cliente: faltando cabe&ccedil;alho HTTP &l;tAuthorization&gt;");
		}
		return autentica(username, password);
	}
	
	private boolean autentica(String usuario,String senha){
		boolean autenticado = false;
		
		Usuario usuarioAutenticado = null;
		try {
			usuarioAutenticado = realizarLoginEJB.realizarLogin(usuario,senha);
		} catch (ModeloException e) {
			e.printStackTrace();
		}
		
		if (usuarioAutenticado != null) {
			autenticado = true;
		}
		
		return autenticado;
	}

}
