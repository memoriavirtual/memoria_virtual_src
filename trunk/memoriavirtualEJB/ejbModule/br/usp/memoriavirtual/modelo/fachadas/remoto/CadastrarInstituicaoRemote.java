package br.usp.memoriavirtual.modelo.fachadas.remoto;

import javax.ejb.Remote;


import br.usp.memoriavirtual.modelo.entidades.Instituicao;


@Remote
public interface CadastrarInstituicaoRemote {
	public void cadastrarInstituicao(Instituicao instituicao) ;


}
