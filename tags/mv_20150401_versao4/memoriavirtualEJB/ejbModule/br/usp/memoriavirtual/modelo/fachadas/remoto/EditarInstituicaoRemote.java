package br.usp.memoriavirtual.modelo.fachadas.remoto;

import java.util.List;

import javax.ejb.Remote;

import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;

@Remote
public interface EditarInstituicaoRemote {

	public void editarInstituicao(Instituicao instituicao)
			throws ModeloException;

	public Instituicao getInstituicao(long id) throws ModeloException;

	public Instituicao getInstituicao(long id, Grupo grupo, Usuario usuario)
			throws ModeloException;

	public List<Instituicao> listarInstituicoes(String pnome)
			throws ModeloException;

	public List<Instituicao> listarInstituicoes(String pnome, Grupo grupo,
			Usuario usuario) throws ModeloException;

	public List<Usuario> getGerentes(Instituicao i);
	
}