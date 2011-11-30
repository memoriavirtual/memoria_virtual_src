package br.usp.memoriavirtual.modelo.fachadas;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
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
	
	/**
	 * listarInstituicoes(pnome, grupo, usuario)
	 * @param String pnome todo ou parte do nome da instituicao a ser procurada
	 * @param Grupo grupo Grupo do usuario que faz o pedido
	 * @param Usuario usuario usuario que faz o pedido
	 * @return List<Instituicao>  Lista de instituicoes cujo nome comeca com pnome, e podem ser acessadas pelo 
	 */
	public List<Instituicao> listarInstituicoes(String pnome,
			Grupo grupo, Usuario usuario) {
		List<Instituicao> instituicoesLista = new ArrayList<Instituicao>();
		instituicoesLista = memoriaVirtualEJB.listarInstituicoes(pnome, grupo, usuario);
		return instituicoesLista;
	}

	

	/**
	 * listarInstituicoes(pnome)
	 * @param String pnome Parte ou todo do nome da instituição a ser procurada
	 * @return List<Instituicao> Lista das Instituições cujo nome começa com a string de parâmetro 
	 */
	public List<Instituicao> listarInstituicoes(String pnome) {
		List<Instituicao> ins = new ArrayList<Instituicao>();
		ins = memoriaVirtualEJB.listarInstituicoes(pnome);
		return ins;
	}
	

	@Override
	public void editarInstituicao(String velhoNome, String novoNome,
			String novoLocalizacao, String novoEndereco, String novoCidade,
			String novoEstado, String novoCep, String novoTelefone)
			throws ModeloException {
		Instituicao instituicao;

		Query query = this.entityManager
				.createQuery("SELECT a FROM Instituicao a WHERE a.nome =:nome");
		query.setParameter("nome", velhoNome);

		try {
			instituicao = (Instituicao) query.getSingleResult();
		} catch (NoResultException e) {
			ModeloException excessao = new ModeloException(e);
			throw excessao;
		} catch (Exception e) {
			RuntimeException excessao = new RuntimeException(e);
			throw excessao;
		}
		if (instituicao != null) {
			instituicao.setNome(novoNome);
			instituicao.setLocalizacao(novoLocalizacao);
			instituicao.setEndereco(novoEndereco);
			instituicao.setCidade(novoCidade);
			instituicao.setEstado(novoEstado);
			instituicao.setCep(novoCep);
			instituicao.setTelefone(novoTelefone);
		}
	}
}