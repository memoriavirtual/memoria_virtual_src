/**
 * 
 */
package br.usp.memoriavirtual.modelo.fachadas;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.fachadas.remoto.CadastrarBemPatrimonialRemote;

/**
 * @author bigmac
 *
 */
@Stateless(mappedName = "CadastrarBemPatrimonial")
public class CadastrarBemPatrimonial implements CadastrarBemPatrimonialRemote {

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Instituicao> listarInstituicao(Usuario usuario) throws ModeloException {
		List<Instituicao> lista;
		Query query;
		query = this.entityManager
				.createQuery("SELECT a.instituicao FROM Acesso a WHERE  a.usuario = :usuario AND a.validade = TRUE" );
		query.setParameter("usuario", usuario);
		try {
			lista = (List<Instituicao>) query.getResultList();
			return lista;
		} catch (Exception e) {
			throw new ModeloException(e);
		}
	}


	@Override
	public void cadastrarBemPatrimonial(BemPatrimonial bem) {
		
		entityManager.persist(bem);
		bem.getTitulos().get(0).setBempatrimonial(bem);
	}
	
}
