package br.usp.memoriavirtual.modelo.fachadas.remoto;

import java.util.List;

import br.usp.memoriavirtual.modelo.entidades.Autor;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;

public interface ExcluirAutorRemote {
	public List<Autor> listarAutores(String nome) throws ModeloException ;
	public void excluirAutor(Autor autor) throws ModeloException ;
}
