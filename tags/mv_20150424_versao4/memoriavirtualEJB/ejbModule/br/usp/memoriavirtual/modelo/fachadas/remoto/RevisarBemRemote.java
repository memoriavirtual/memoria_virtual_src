package br.usp.memoriavirtual.modelo.fachadas.remoto;

import javax.ejb.Remote;

import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Revisao;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;

@Remote
public interface RevisarBemRemote {
	public BemPatrimonial getBemPatrimonial(Long id) throws ModeloException ;
	public void inserirRevisao(Revisao r);	
}
