package br.usp.memoriavirtual.modelo.fachadas;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.fachadas.remoto.CadastrarInstituicaoRemote;

@Stateless(mappedName = "RealizarLogin")
public class CadastrarInstituicao implements CadastrarInstituicaoRemote{
	
	@PersistenceContext(unitName = "memoriavirtual")
    private EntityManager entityManager;
	
	public Instituicao cadastrarInstituicao(String Nome, String Localizacao, String Endereco, String Cidade, String Estado, String Cep, String Telefone){
		
		Query query;
		query = this.entityManager.createQuery("INSERT INTO Instituicao VALUES()");
		
	}


}
