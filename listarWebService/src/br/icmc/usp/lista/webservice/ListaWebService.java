package br.icmc.usp.lista.webservice;

import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class ListaWebService {
	@WebMethod(operationName = "Lista")
	public ArrayList<String> Lista(String recebe) {

		ArrayList<String> nomes = new ArrayList<String>();
		nomes.add(recebe);
		nomes.add("fim");
		nomes.add("webservice");

		return nomes;

	}

}
