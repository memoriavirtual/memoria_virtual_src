package br.usp.memoriavirtual.modelo.fachadas.remoto;

import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;

public interface EditarBemPatrimonialRemote {
	public void editarBemPatrimonial(BemPatrimonial bem) throws ModeloException;
}
