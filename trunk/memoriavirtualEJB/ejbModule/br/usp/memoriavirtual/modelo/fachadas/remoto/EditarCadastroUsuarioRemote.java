package br.usp.memoriavirtual.modelo.fachadas.remoto;

import java.util.List;

import javax.ejb.Remote;

import br.usp.memoriavirtual.modelo.entidades.Aprovacao;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;

@Remote
public interface EditarCadastroUsuarioRemote {

	public List<Usuario> listarAprovadores(Usuario requerente, Usuario usuario)
			throws ModeloException;

	public Aprovacao getAprovacao(String aprovacao) throws ModeloException;

	public List<Instituicao> listarInstituicoes(String instituicao)
			throws ModeloException;

	public long solicitarEdicao(Aprovacao aprovacao) throws ModeloException;

	public void aprovar(Usuario usuario, Aprovacao aprovacao)
			throws ModeloException;

	public void negar(Aprovacao aprovacao) throws ModeloException;
}
