package br.usp.memoriavirtual.modelo.fachadas.remoto;

import javax.ejb.Remote;

import br.usp.memoriavirtual.modelo.fachadas.ModeloException;

@Remote
public interface ExcluirBemPatrimonialRemote {

	public void excluir(long id) throws ModeloException;

}
