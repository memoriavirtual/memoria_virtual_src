package br.usp.memoriavirtual.modelo.fachadas;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
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
	 * Metodo auxiliar para recuperar instituicao
	 * 
	 * @param pnome
	 *            String - Nome da instituicao
	 * @return Instituicao correspondente ao nome
	 * @throws ModeloException
	 *             Em caso de erro
	 */
	public Instituicao getInstituicao(String pnome) throws ModeloException {
		Instituicao instituicao;
		Query query;
		query = this.entityManager
				.createQuery("SELECT a FROM Instituicao a WHERE a.nome = :nome");
		query.setParameter("nome", pnome);
		try {
			instituicao = (Instituicao) query.getSingleResult();
		} catch (Exception e) {
			throw new ModeloException(e);
		}
		return instituicao;
	}

	/**
	 * Metodo auxiliar para recuperar instituicao
	 * 
	 * @param pnome
	 *            String - Nome da instituicao
	 * @param grupo
	 *            Grupo ao qual o usuario pertence
	 * @param usuario
	 *            Usuario que faz a requisicao
	 * @return Instituicao correspondente ao nome
	 * @throws ModeloException
	 *             Em caso de erro
	 */
	public Instituicao getInstituicao(String pnome, Grupo grupo, Usuario usuario)
			throws ModeloException {
		Instituicao instituicao;
		Query query;
		query = this.entityManager
				.createQuery("SELECT a FROM Acesso a WHERE a.grupo =: grupo AND a.usuario =: usuario");
		query.setParameter("grupo", grupo);
		query.setParameter("usuario", usuario);
		try {
			instituicao = (Instituicao) query.getSingleResult();
		} catch (Exception e) {
			throw new ModeloException(e);
		}
		return instituicao;
	}

	@Override
	public void editarInstituicao(Instituicao instituicao) throws ModeloException {

		Instituicao managedInstituicao;
		
		try {
			// Verifica a existencia da instituicao a ser alterada
			managedInstituicao = this.entityManager.find(Instituicao.class,
					instituicao.getId());
		} catch (Exception e) {
			throw new ModeloException(e);
		}
		if (managedInstituicao == null) {
			throw new ModeloException("Instituicao Inexistente");
		}

		if (instituicao != null) {
			managedInstituicao.setNome(instituicao.getNome());
			managedInstituicao.setLocalidade(instituicao.getLocalidade());
			managedInstituicao.setEndereco(instituicao.getEndereco());
			managedInstituicao.setCidade(instituicao.getCidade());
			managedInstituicao.setEstado(instituicao.getEstado());
			managedInstituicao.setCep(instituicao.getCep());
			managedInstituicao.setTelefone(instituicao.getTelefone());
			managedInstituicao.setCaixaPostal(instituicao.getCaixaPostal());
			managedInstituicao.setEmail(instituicao.getEmail());
			managedInstituicao.setUrl(instituicao.getUrl());
			managedInstituicao.setIdentificacaoProprietario(instituicao.getIdentificacaoProprietario());
			managedInstituicao.setAdministradorPropriedade(instituicao.getAdministradorPropriedade());
			managedInstituicao.setLatitude(instituicao.getLatitude());
			managedInstituicao.setLongitude(instituicao.getLongitude());
			managedInstituicao.setAltitude(instituicao.getAltitude());
			managedInstituicao.setTipoPropriedade(instituicao.getTipoPropriedade());
			managedInstituicao.setProtecaoExistente(instituicao.getProtecaoExistente());
		}
		
		try {
			entityManager.flush();
		} catch (Exception t) {
			throw new ModeloException(t);
		}
	}
}