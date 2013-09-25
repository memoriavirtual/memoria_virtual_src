package br.usp.memoriavirtual.modelo.fachadas.remoto;

import javax.ejb.Remote;

import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;

@Remote
public interface ExcluirBemPatrimonialRemote {
	
	public BemPatrimonial recuperarDados(BemPatrimonial bem) throws ModeloException;
	public void excluirBem(BemPatrimonial bem) throws ModeloException;

}
