package br.usp.memoriavirtual.modelo.fachadas.remoto;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;

import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Multimidia;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;

@Remote
public interface RealizarBuscaSimplesRemote {

	public ArrayList<BemPatrimonial> buscar(String busca, Integer pagina) throws ModeloException;

	public ArrayList<BemPatrimonial> buscarExterno(String busca, Integer pagina) throws ModeloException;
	
	public ArrayList<BemPatrimonial> buscar(String busca) throws ModeloException;

	public Integer getNumeroDePaginasBusca();

	public boolean possuiAcesso(Usuario usuario, Instituicao instituicao) throws ModeloException;

	public ArrayList<BemPatrimonial> buscarPorInstituicao(String busca, Integer pagina, Integer tamanhoPagina,
			String nomeInstituicao) throws ModeloException;

	public List<Multimidia> getMidias(Long container);

}
