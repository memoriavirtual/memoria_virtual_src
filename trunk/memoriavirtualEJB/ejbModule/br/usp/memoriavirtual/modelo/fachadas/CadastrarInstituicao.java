package br.usp.memoriavirtual.modelo.fachadas;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.fachadas.remoto.CadastrarInstituicaoRemote;

@Stateless(mappedName = "CadastrarInstituicao")
public class CadastrarInstituicao implements CadastrarInstituicaoRemote{
	
	@PersistenceContext(unitName = "memoriavirtual")
    private EntityManager entityManager;
	
	public Instituicao cadastrarInstituicao(String Nome, String Localizacao, String Endereco, String Cidade, String Estado, String Cep, String Telefone){
		
		final Instituicao ins = new Instituicao(Nome, Localizacao, Endereco, Cidade, Estado, Cep, Telefone);

		if(entityManager.find(ins.getClass(), ins.getNome()) != null)
			return null;
		
		entityManager.persist(ins);
		return ins;
	}


}
