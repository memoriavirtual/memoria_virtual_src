package br.usp.memoriavirtual.modelo.fachadas.remoto;

import javax.ejb.Remote;

import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;

@Remote
public interface CadastrarBemPatrimonialRemote {
	BemPatrimonial cadastrarBemPatrimonial(BemPatrimonial bem)
			throws ModeloException;

	BemPatrimonial salvarBemPatrimonial(BemPatrimonial bem)
			throws ModeloException;

	BemPatrimonial getBemPatrimonial(long id) throws ModeloException;
}
