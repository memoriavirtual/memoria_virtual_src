package br.usp.memoriavirtual.modelo.fachadas;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarInstituicaoRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;

@Stateless(mappedName = "EditarInstituicao")
public class EditarInstituicao implements EditarInstituicaoRemote {

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;
	MemoriaVirtualRemote memoriaVirtualEJB;

	/**
	 * Construtor Padrão, não leva parâmetros
	 */
	public EditarInstituicao() {
	}

	@Override
	public void editarInstituicao(Instituicao instituicao, String nome,
			String localizacao, String endereco, String cidade, String estado,
			String cep, String telefone) throws ModeloException {
		
		Instituicao managedInstituicao;
		
		try{
		//Verifica a existencia da instituicao a ser alterada
		managedInstituicao = this.entityManager.find(Instituicao.class, instituicao);
		}
		catch(Exception e){
			throw new ModeloException(e);
		}
		if(managedInstituicao == null){
			throw new ModeloException("Instituicao Inexistente");
		}

		if (instituicao != null) {
			instituicao.setNome(nome);
			instituicao.setLocalizacao(localizacao);
			instituicao.setEndereco(endereco);
			instituicao.setCidade(cidade);
			instituicao.setEstado(estado);
			instituicao.setCep(cep);
			instituicao.setTelefone(telefone);
		}
	}
}