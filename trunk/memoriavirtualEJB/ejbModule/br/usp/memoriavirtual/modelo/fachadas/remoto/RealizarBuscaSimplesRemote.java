package br.usp.memoriavirtual.modelo.fachadas.remoto;

import java.util.List;

import javax.ejb.Remote;

import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;


@Remote
public interface RealizarBuscaSimplesRemote {
	
	public List<BemPatrimonial> buscar(String busca) throws ModeloException;

}
