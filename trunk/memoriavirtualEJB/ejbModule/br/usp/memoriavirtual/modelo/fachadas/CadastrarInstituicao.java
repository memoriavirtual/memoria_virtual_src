package br.usp.memoriavirtual.modelo.fachadas;

import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Multimidia;
import br.usp.memoriavirtual.modelo.fachadas.remoto.CadastrarInstituicaoRemote;

@Stateless(mappedName = "CadastrarInstituicao")
public class CadastrarInstituicao implements CadastrarInstituicaoRemote {

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;

	
	
	public void cadastrarInstituicao(Instituicao instituicao)
	 {
		//System.out.println(instituicao.getNome());
		entityManager.persist(instituicao); 

	}



	@Override
	public void vincularArquivos(Instituicao instituicao,
			ArrayList<Multimidia> arquivos) {
		
		
		for(Multimidia i : arquivos){
			entityManager.persist(i);
			System.out.println(i.getId());
			instituicao.getImagens().add(i.getId());
		}
	}
}
