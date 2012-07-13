package br.usp.memoriavirtual.modelo.fachadas.remoto;

import java.util.ArrayList;

import javax.ejb.Remote;


import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Multimidia;


@Remote
public interface CadastrarInstituicaoRemote {
	public void cadastrarInstituicao(Instituicao instituicao) ;
	public void vincularArquivos(Instituicao instituicao , ArrayList<Multimidia> arquivos);

}
