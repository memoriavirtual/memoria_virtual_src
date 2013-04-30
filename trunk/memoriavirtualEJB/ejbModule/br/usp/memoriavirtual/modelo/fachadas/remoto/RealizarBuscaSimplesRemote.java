package br.usp.memoriavirtual.modelo.fachadas.remoto;

import java.util.List;

import javax.ejb.Remote;

import br.usp.memoriavirtual.modelo.entidades.Multimidia;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemArqueologico;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemArquitetonico;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemNatural;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;

@Remote
public interface RealizarBuscaSimplesRemote {

	public List<BemPatrimonial> buscar(String busca) throws ModeloException;

	public BemArqueologico buscarBemArqueologico(BemPatrimonial bem)
			throws ModeloException;

	public BemArquitetonico buscarBemArquitetonico(BemPatrimonial bem)
			throws ModeloException;

	public BemNatural buscarBemNatural(BemPatrimonial bem)
			throws ModeloException;

	public List<Multimidia> buscarVideos(BemPatrimonial bem)
			throws ModeloException;

	public List<Multimidia> buscarImagens(BemPatrimonial bem)
			throws ModeloException;

	public List<Multimidia> buscarAudio(BemPatrimonial bem)
			throws ModeloException;

}
