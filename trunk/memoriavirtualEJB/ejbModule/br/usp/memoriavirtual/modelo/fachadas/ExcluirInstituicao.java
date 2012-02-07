package br.usp.memoriavirtual.modelo.fachadas;


import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.entidades.Acesso;

import br.usp.memoriavirtual.modelo.fabricas.remoto.AuditoriaFabricaRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirInstituicaoRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;


@Stateless (mappedName = "ExcluirInstituicao")
public class ExcluirInstituicao implements ExcluirInstituicaoRemote {

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;

	MemoriaVirtualRemote memoriaVirtualEJB;
	AuditoriaFabricaRemote autoriaFabricaEJB;
	/**
	 * Construtor Padr√£o, n√£o leva par√¢metros
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
	 * Metodo auxiliar para recuperar usuario ligado a determinada instituiÁ„o
	 * 
	 * @param instituicao
	 *            instituicao
	 * @param grupo
	 *            Grupo ao qual o usuario pertence
	 * @return Usuario pertencente a referido grupo vinculado a referida instituiÁ„o
	 * @throws ModeloException
	 *             Em caso de erro
	 */
	@SuppressWarnings("unchecked")
	public List<Acesso> getGerentesdaInstituicao(Instituicao instituicao)
	throws ModeloException {
		Grupo grupo = new Grupo("GERENTE") ;
		List<Acesso> objetosAcesso ;
		Query query;
		query = this.entityManager
		.createQuery("SELECT a FROM Acesso a WHERE  a.grupo = :grupo AND a.instituicao = :instituicao");
		query.setParameter("grupo", grupo);
		query.setParameter("instituicao", instituicao);
		try {
			objetosAcesso = (List<Acesso>) query.getResultList() ;			
			return objetosAcesso;
		} catch (Exception e) {
			throw new ModeloException(e);
		}

	}
	
	public Usuario getValidador(String nomeCompleto)
	throws ModeloException {
		Usuario usuario;
		Query query;
		query = this.entityManager
		.createQuery("SELECT a FROM Usuario a WHERE  a.nomeCompleto = :nomeCompleto ");
		query.setParameter("nomeCompleto", nomeCompleto);
		try {
			usuario = (Usuario) query.getSingleResult() ;
			return usuario;
		} catch (Exception e) {
			throw new ModeloException(e);
		}

	}
	
	
	public Usuario getRequisitor(String id)
	throws ModeloException {
		Usuario usuario;
		Query query;
		query = this.entityManager
		.createQuery("SELECT a FROM Usuario a WHERE  a.id = :id ");
		query.setParameter("id", id);
		try {
			usuario = (Usuario) query.getSingleResult() ;
			return usuario;
		} catch (Exception e) {
			throw new ModeloException(e);
		}

	}

 
	  public void validarExclusaoInstituicao(String nome,Usuario requisitor,Usuario validador)
    	throws ModeloException {
		Instituicao instituicao;
		Query query; 
		query = this.entityManager
		.createQuery("SELECT a FROM Instituicao a WHERE  a.nome = :nome ");
		query.setParameter("nome", nome);
		try {
			instituicao = (Instituicao) query.getSingleResult() ;
			autoriaFabricaEJB.auditarExcluirInstituicao(requisitor, instituicao.getNome());
			this.entityManager.remove(instituicao);
		} catch (Exception e) {
			throw new ModeloException(e);
		}

	}
	public String enviaremail(String Email,String assunto,String textoEmail){

		try {

			this.memoriaVirtualEJB.enviarEmail(Email, assunto, textoEmail);
			return "ok";

		} catch (Exception e) {

			e.printStackTrace();
			return "fail";
		}
	}
}