package br.usp.memoriavirtual.modelo.fachadas.remoto;



import br.usp.memoriavirtual.modelo.entidades.Autor;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;

public interface ExcluirAutorRemote {
	public void excluirAutor(Autor autor) throws ModeloException ;
}
