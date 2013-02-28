package br.usp.memoriavirtual.modelo.fachadas;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Multimidia;
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
	public void editarInstituicao(Instituicao instituicao)
			throws ModeloException {

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
			
			Multimidia b =null;
			
			for(Multimidia a: instituicao.getContainerMultimidia().getMultimidia()){
				
				b = this.entityManager.find(Multimidia.class, a.getId());
				if(b != null){
					b.setDescricao(a.getDescricao());
				}else{
					a.setId(0);
					a.setContainerMultimidia(managedInstituicao.getContainerMultimidia());
					managedInstituicao.getContainerMultimidia().addMultimidia(a);
				}
				
			}	
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
			managedInstituicao.setIdentificacaoProprietario(instituicao
					.getIdentificacaoProprietario());
			managedInstituicao.setAdministradorPropriedade(instituicao
					.getAdministradorPropriedade());
			managedInstituicao.setLatitude(instituicao.getLatitude());
			managedInstituicao.setLongitude(instituicao.getLongitude());
			managedInstituicao.setAltitude(instituicao.getAltitude());
			managedInstituicao.setTipoPropriedade(instituicao
					.getTipoPropriedade());
			managedInstituicao.setProtecaoExistente(instituicao
					.getProtecaoExistente());
			
		
		}

		try {
			entityManager.flush();
		} catch (Exception t) {
			throw new ModeloException(t);
		}
	}

	/**
	 * listarInstituicoes(pnome, grupo, usuario)
	 * 
	 * @param String
	 *            pnome todo ou parte do nome da instituicao a ser procurada
	 * @param Grupo
	 *            grupo Grupo do usuario que faz o pedido
	 * @param Usuario
	 *            usuario usuario que faz o pedido
	 * @return List<Instituicao> Lista de instituicoes cujo nome comeca com
	 *         pnome, e podem ser acessadas pelo
	 * @throws ModeloException
	 */
	@SuppressWarnings("unchecked")
	public List<Instituicao> listarInstituicoes(String pnome, Grupo grupo,
			Usuario usuario) throws ModeloException {
		List<Instituicao> ins = new ArrayList<Instituicao>();
		Query query;

		query = entityManager
				.createQuery("SELECT a.instituicao FROM Acesso a WHERE a.grupo =:grupo AND a.usuario =:usuario AND a.instituicao.nome LIKE :nome ");
		query.setParameter("nome", "%" + pnome + "%");

		query.setParameter("grupo", grupo);
		query.setParameter("usuario", usuario);
		try {
			ins = (List<Instituicao>) query.getResultList();
		} catch (Exception e) {
			throw new ModeloException(e);
		}

		return ins;
	}

	/**
	 * Metodo de sugestoes para instituicao
	 * 
	 * @param pnome
	 *            String para a qual o sistema gera sugestoes
	 * @return Lista de Instituicoes que comecam com a String de parametro
	 * @throws ModeloException
	 */
	@SuppressWarnings("unchecked")
	public List<Instituicao> listarInstituicoes(String pnome)
			throws ModeloException {
		List<Instituicao> instituicoes = new ArrayList<Instituicao>();
		Query query;
		query = entityManager
				.createQuery("SELECT a FROM Instituicao a  WHERE a.validade = TRUE AND a.nome LIKE :padrao ORDER BY a.nome");

		query.setParameter("padrao", "%" + pnome + "%");
		try {
			instituicoes = (List<Instituicao>) query.getResultList();
		} catch (Exception e) {
			throw new ModeloException(e);
		}

		return instituicoes;
	}
}