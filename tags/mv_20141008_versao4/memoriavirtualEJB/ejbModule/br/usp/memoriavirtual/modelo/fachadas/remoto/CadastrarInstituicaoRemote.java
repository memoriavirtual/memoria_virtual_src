package br.usp.memoriavirtual.modelo.fachadas.remoto;

import javax.ejb.Remote;

import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;

@Remote
public interface CadastrarInstituicaoRemote {
	public Instituicao cadastrarInstituicao(Instituicao instituicao)
			throws ModeloException;

}
