package br.usp.memoriavirtual.servicos.soap;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.jws.WebService;

import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.RealizarBuscaSimplesRemote;

@WebService(serviceName="buscar")
public class RealizarBuscaSOAPService {

	@EJB
	private RealizarBuscaSimplesRemote realizarBuscaEJB;

	public ArrayList<BemPatrimonial> buscar(String stringDeBusca) {
		ArrayList<BemPatrimonial> bensPatrimoniais = null;
		try {
			bensPatrimoniais = this.realizarBuscaEJB.buscar(stringDeBusca);
		} catch (ModeloException e) {
			e.printStackTrace();
		}
		return bensPatrimoniais;
	}

}
