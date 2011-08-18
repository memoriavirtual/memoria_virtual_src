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

    public Instituicao cadastrarInstituicao(String Nome, String Localizacao, String Endereco, String Cidade,
	    String Estado, String Cep, String Telefone) {

	Instituicao instituicao = new Instituicao(Nome, Localizacao, Endereco, Cidade, Estado, Cep, Telefone);

	// verifica se ja existe uma instituicao com esse nome
	if (entityManager.find(instituicao.getClass(), instituicao.getNome()) != null)
	    return null;
	// tenta persistir no banco
	try {
	    entityManager.persist(instituicao);
	} catch (Exception e) {
	    e.printStackTrace();
	    return null;
	}

	// certifica que a persistencia ocorreu com sucesso
	if (!entityManager.contains(instituicao))
	    return null;

	return instituicao;
    }
}
