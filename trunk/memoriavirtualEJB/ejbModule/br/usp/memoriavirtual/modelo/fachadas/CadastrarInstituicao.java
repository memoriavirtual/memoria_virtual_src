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

	/**
	 * Este método cadastra uma nova instituição no banco de dados.
	 * 
	 * @param nome
	 *            Nome da Instituição
	 * @param localizacao
	 *            Localização da Instituição
	 * @param endereco
	 *            Endereço da Instituição
	 * @param cidade
	 *            Cidade da Instituição
	 * @param estado
	 *            Estado da Instituição
	 * @param cep
	 *            CEP da Instituição
	 * @param telefone
	 *            Telefone da Instituição
	 * @throws ModeloException
	 *             A exceção é lançada caso uma instituição já exista com este nome
	 */
	public void cadastrarInstituicao(String nome, String localizacao, String endereco, String cidade, String estado,
			String cep, String telefone) throws ModeloException {

		Instituicao instituicao = new Instituicao(nome, localizacao, endereco, cidade, estado, cep, telefone);

		// verifica se ja existe uma instituicao com esse nome
		if (entityManager.find(instituicao.getClass(), instituicao.getNome()) != null)
			throw new ModeloException("Já existe uma Intituição com este nome");
		// tenta persistir no banco

		entityManager.persist(instituicao);
	}
}
