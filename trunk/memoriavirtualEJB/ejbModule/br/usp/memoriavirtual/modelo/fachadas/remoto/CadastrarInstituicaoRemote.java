package br.usp.memoriavirtual.modelo.fachadas.remoto;

import javax.ejb.Remote;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;

@Remote
public interface CadastrarInstituicaoRemote {
	public Instituicao cadastrarInstituicao(String Nome, String Localizacao, String Endereco, String Cidade, String Estado, String Cep, String Telefone);
	
}
