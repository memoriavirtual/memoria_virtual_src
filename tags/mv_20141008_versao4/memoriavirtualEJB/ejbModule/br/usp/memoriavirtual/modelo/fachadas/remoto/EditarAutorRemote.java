package br.usp.memoriavirtual.modelo.fachadas.remoto;

import java.util.List;

import br.usp.memoriavirtual.modelo.entidades.Autor;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;

public interface EditarAutorRemote {
	public List<Autor> listarAutores(String strDeBusca) throws ModeloException;

	public void editarAutor(Autor autor) throws ModeloException;

	public Autor getAutor(Long id) throws ModeloException;
}
