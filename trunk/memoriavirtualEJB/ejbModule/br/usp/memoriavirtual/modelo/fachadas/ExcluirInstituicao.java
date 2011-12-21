package br.usp.memoriavirtual.modelo.fachadas;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.entidades.Acesso;

import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirInstituicaoRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;


@Stateless (mappedName = "ExcluirInstituicao")
public class ExcluirInstituicao implements ExcluirInstituicaoRemote {

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;
	MemoriaVirtualRemote memoriaVirtualEJB;
	/**
	 * Construtor Padr√£o, n√£o leva par√¢metros
	 */
	public ExcluirInstituicao() {

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
	public Usuario getUsuarioInstituicao(Instituicao instituicao, Grupo grupo)
	throws ModeloException {

		Acesso acesso;
		Query query;
		query = this.entityManager
		.createQuery("SELECT a FROM Acesso a WHERE  a.grupo = :grupo AND a.instituicao = :instituicao");
		query.setParameter("grupo", grupo);
		query.setParameter("instituicao", instituicao);
		try {
			acesso = (Acesso) query.getSingleResult() ;
			return acesso.getUsuario();
		} catch (Exception e) {
			throw new ModeloException(e);
		}

	}
	@SuppressWarnings("unchecked")
	public List<Usuario> listarAdministradores()
	throws ModeloException {

		List<Usuario> administradores;
		Query query;
		query = this.entityManager
		.createQuery("SELECT a FROM Usuario a ORDER BY a.id ");
		
		try {
			administradores = (List<Usuario>) query.getResultList();
			return administradores ;
		} catch (Exception e) {
			throw new ModeloException(e);
		}

	}


}