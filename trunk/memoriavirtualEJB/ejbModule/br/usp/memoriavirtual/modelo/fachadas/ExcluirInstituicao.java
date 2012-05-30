package br.usp.memoriavirtual.modelo.fachadas;


import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.Acesso;
import br.usp.memoriavirtual.modelo.entidades.Aprovacao;
import br.usp.memoriavirtual.modelo.entidades.EnumTipoAcao;
import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.ItemAuditoria;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fabricas.remoto.AuditoriaFabricaRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirInstituicaoRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;


/**
 * @author MAC
 */

@Stateless (mappedName = "ExcluirInstituicao")
public class ExcluirInstituicao implements ExcluirInstituicaoRemote {



	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;

	MemoriaVirtualRemote memoriaVirtualEJB;
	AuditoriaFabricaRemote autoriaFabricaEJB;
	/**
	 * Construtor Padrão, não leva parâmetros
	 */
	public ExcluirInstituicao() {

	}


	@SuppressWarnings("unchecked")
	public List<Usuario> listarAdministradores()
			throws ModeloException {

		List<Usuario> administradores;
		Query query;
		query = this.entityManager
				.createQuery("SELECT a FROM Usuario a WHERE a.administrador = TRUE ORDER BY a.id ");

		try {
			administradores = (List<Usuario>) query.getResultList();
			return administradores ;
		} catch (Exception e) {
			throw new ModeloException(e);
		}

	}
	/**
	 * Metodo auxiliar para recuperar usuario ligado a determinada institui��o
	 * 
	 * @param instituicao
	 *            instituicao
	 * @param grupo
	 *            Grupo ao qual o usuario pertence
	 * @return Usuario pertencente a referido grupo vinculado a referida institui��o
	 * @throws ModeloException
	 *             Em caso de erro
	 */
	@SuppressWarnings("unchecked")
	public List<Acesso> getGerentesdaInstituicao(Instituicao instituicao,boolean b)
			throws ModeloException {
		Grupo grupo = new Grupo("GERENTE") ;
		List<Acesso> objetosAcesso ;
		Query query;
		query = this.entityManager
				.createQuery("SELECT a FROM Acesso a WHERE  a.grupo = :grupo AND a.instituicao = :instituicao AND a.validade = :b");
		query.setParameter("grupo", grupo);
		query.setParameter("instituicao", instituicao);
		query.setParameter("b", b);

		try {
			objetosAcesso = (List<Acesso>) query.getResultList() ;			
			return objetosAcesso;
		} catch (Exception e) {
			throw new ModeloException(e);
		}

	}

	public Usuario getUsuario(String coluna, String parametro)
			throws ModeloException {
		Usuario usuario;
		Query query;
		query = this.entityManager
				.createQuery("SELECT a FROM Usuario a WHERE  a."+coluna+" = :parametro ");
		query.setParameter("parametro", parametro);
		try {
			usuario = (Usuario) query.getSingleResult() ;
			return usuario;
		} catch (Exception e) {
			throw new ModeloException(e);
		}

	}


	
	public Aprovacao getAprovacao(String chave)
			throws ModeloException {
		Aprovacao aprovacao;
		Query query;
		query = this.entityManager
				.createQuery("SELECT a FROM Aprovacao a WHERE  a.chaveEstrangeira = :chave   AND a.tabelaEstrangeira = :instituicao" );
		query.setParameter("chave", chave);
		query.setParameter("instituicao", "Instituicao");


		try {
			aprovacao = (Aprovacao) query.getSingleResult() ;
			return aprovacao;
		} catch (Exception e) {
			throw new ModeloException(e);
		}

	}

	public void excluirAprovacao(Aprovacao aprovacao){
		Query query;
		query = this.entityManager
				.createQuery("DELETE  FROM Aprovacao a WHERE a = :aprovacao ");
		query.setParameter("aprovacao", aprovacao);
		query.executeUpdate();	


	}
	

	public void validarExclusaoInstituicao(Instituicao instituicao,boolean acao,boolean flagAcesso)
			throws ModeloException {
		//Se acao verdadeira a instituicao e definitivamente excluida do sistema
		if(acao){
			instituicao = this.entityManager.find(Instituicao.class, instituicao.getId());
			if(flagAcesso){
				Query query;
				query = this.entityManager
						.createQuery("DELETE  FROM Acesso a WHERE a.instituicao = :instituicao ");
				query.setParameter("instituicao", instituicao);
				query.executeUpdate();		
			}
			try {
				this.entityManager.remove(instituicao);
			} catch (Exception e) {
				throw new ModeloException(e);
			}
		}else{//Caso Acao falso a instituicao volta a funcionar normalmente
			this.marcarInstituicaoExcluida(instituicao,! acao,flagAcesso);
		}
	}
	public void enviaremail(String Email,String assunto,String textoEmail){

		try {

			this.memoriaVirtualEJB.enviarEmail(Email, assunto, textoEmail);


		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	public ItemAuditoria getItemAuditoria(String nomeInstituicao,EnumTipoAcao enumTipoAcao) throws ModeloException {
		ItemAuditoria itemAuditoria;
		Query query;
		query = this.entityManager
				.createQuery("SELECT a FROM ItemAuditoria a WHERE a.atributoSignificativo = :nome AND a.tipoAcao = :acao");
		query.setParameter("nome", nomeInstituicao);
		query.setParameter("acao", enumTipoAcao);

		try {
			itemAuditoria = (ItemAuditoria) query.getSingleResult();
		} catch (Exception e) {
			throw new ModeloException(e);
		}
		return itemAuditoria;
	}

	public Instituicao getInstituicaoFalse(String pnome) throws ModeloException {
		Instituicao instituicao;
		Query query;
		query = this.entityManager
				.createQuery("SELECT a FROM Instituicao a WHERE a.nome = :nome AND a.validade = FALSE");
		query.setParameter("nome", pnome);
		try {
			instituicao = (Instituicao) query.getSingleResult();
		} catch (Exception e) {
			throw new ModeloException(e);
		}
		return instituicao;
	}

	public void marcarInstituicaoExcluida(Instituicao instituicao,boolean marca,boolean flagAcesso)
			throws ModeloException{
		Query query;
		query = this.entityManager
				.createQuery("UPDATE Instituicao a SET a.validade = :validade WHERE  a.id = :id");
		query.setParameter("id", instituicao.getId());
		query.setParameter("validade", marca );
		query.executeUpdate();
		if(flagAcesso){
			query = this.entityManager
					.createQuery("UPDATE Acesso a SET a.validade = :validade WHERE  a.instituicao = :id");
			query.setParameter("id", instituicao);
			query.setParameter("validade", marca );
			query.executeUpdate();
		}
	}
	public void registrarAprovacao(Usuario validador, Instituicao instituicao,
			Date dataValidade){
		Date data = new Date();
		instituicao = this.entityManager.find(Instituicao.class, instituicao.getId());
		Usuario u = entityManager.find(Usuario.class, validador.getId());
		Aprovacao aprovacao = new Aprovacao( data , u , dataValidade , instituicao.getNome() , Instituicao.class.getName());
		this.entityManager.persist(aprovacao);
	}
}