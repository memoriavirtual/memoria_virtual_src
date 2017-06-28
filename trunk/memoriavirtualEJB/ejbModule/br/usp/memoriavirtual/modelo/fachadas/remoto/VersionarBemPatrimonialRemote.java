package br.usp.memoriavirtual.modelo.fachadas.remoto;

import javax.ejb.Remote;

import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;

@Remote
public interface VersionarBemPatrimonialRemote {
	public void SalvarVersaoBemPatrimonial(BemPatrimonial bem) throws ModeloException;
}
