package br.usp.memoriavirtual.modelo.fachadas.remoto;

import java.util.List;

import javax.ejb.Remote;

import br.usp.memoriavirtual.modelo.entidades.Aprovacao;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;

@Remote
public interface ExcluirInstituicaoRemote {

	public List<Usuario> listarAnalistas(Instituicao instituicao,
			Usuario usuario) throws ModeloException;

	public void negar(Instituicao instituicao, Aprovacao aprovacao)
			throws ModeloException;

	public void aprovar(Instituicao instituicao, Aprovacao aprovacao)
			throws ModeloException;

	public Long solicitarExclusao(Instituicao instituicao, Aprovacao aprovacao)
			throws ModeloException;

}