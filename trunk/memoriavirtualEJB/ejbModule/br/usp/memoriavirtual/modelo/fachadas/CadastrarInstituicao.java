package br.usp.memoriavirtual.modelo.fachadas;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.fachadas.remoto.CadastrarInstituicaoRemote;

@Stateless(mappedName = "CadastrarInstituicao")
public class CadastrarInstituicao implements CadastrarInstituicaoRemote {

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;

	
	
	public void cadastrarInstituicao(Instituicao instituicao)
	 {
		entityManager.persist(instituicao); 
		entityManager.flush();

	}

}
