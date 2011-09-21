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

	public void cadastrarInstituicao(String Nome, String Localizacao, String Endereco, String Cidade, String Estado,
			String Cep, String Telefone) throws ModeloException {

		Instituicao instituicao = new Instituicao(Nome, Localizacao, Endereco, Cidade, Estado, Cep, Telefone);

		// verifica se ja existe uma instituicao com esse nome
		if (entityManager.find(instituicao.getClass(), instituicao.getNome()) != null)
			throw new ModeloException("Já existe uma Intituição com este nome");
		// tenta persistir no banco

		entityManager.persist(instituicao);

		// certifica que a persistencia ocorreu com sucesso
		if (!entityManager.contains(instituicao))
			throw new ModeloException("A inserção da nova Instituição foi mal sucedida!");
	}
}
