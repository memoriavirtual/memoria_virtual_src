package br.usp.memoriavirtual.servicos.soap;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.RealizarBuscaSimplesRemote;

@WebService(serviceName = "buscar")
public class RealizarBuscaSOAPService {

	@EJB
	private RealizarBuscaSimplesRemote realizarBuscaEJB;

	@WebMethod(operationName = "buscar")
	public ArrayList<BemPatrimonial> buscar(@WebParam(name = "stringDeBusca") String stringDeBusca,@WebParam(name = "numeroDaPagina") int pagina) {
		ArrayList<BemPatrimonial> bensPatrimoniais = null;
		try {
			bensPatrimoniais = this.realizarBuscaEJB.buscar(stringDeBusca,pagina);
		} catch (ModeloException e) {
			e.printStackTrace();
		}
		return bensPatrimoniais;
	}

}
