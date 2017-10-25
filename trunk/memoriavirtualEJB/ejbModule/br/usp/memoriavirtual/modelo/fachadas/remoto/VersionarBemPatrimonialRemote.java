package br.usp.memoriavirtual.modelo.fachadas.remoto;

import java.util.ArrayList;

import javax.ejb.Remote;

import java.sql.Timestamp;

import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;

@Remote
public interface VersionarBemPatrimonialRemote {
	public void salvarVersaoBemPatrimonial(BemPatrimonial bem) throws ModeloException;
	public ArrayList<Long> listarIdsDeVersoes(BemPatrimonial bem) throws ModeloException;
	public ArrayList<Timestamp> listarDatasDeVersoes(BemPatrimonial bem);
}
