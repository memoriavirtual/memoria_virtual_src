/**
 * 
 */
package br.usp.memoriavirtual.modelo.fachadas;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.Autoria;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Assunto;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Descritor;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Titulo;
import br.usp.memoriavirtual.modelo.fachadas.remoto.CadastrarBemPatrimonialRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarBemPatrimonialRemote;

/**
 * @author bigmac
 * 
 */
@Stateless(mappedName = "CadastrarBemPatrimonial")
public class CadastrarBemPatrimonial implements CadastrarBemPatrimonialRemote {

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;
	
	@EJB
	private EditarBemPatrimonialRemote editarBemPatrimonialEJB;

	@SuppressWarnings("unchecked")
	@Override
	public List<Instituicao> listarInstituicao(Usuario usuario)
			throws ModeloException {
		List<Instituicao> lista;
		Query query;
		query = this.entityManager
				.createQuery("SELECT a.instituicao FROM Acesso a WHERE  a.usuario = :usuario AND a.validade = TRUE");
		query.setParameter("usuario", usuario);
		try {
			lista = (List<Instituicao>) query.getResultList();
			return lista;
		} catch (Exception e) {
			throw new ModeloException(e);
		}
	}

	@Override
	public void cadastrarBemPatrimonial(BemPatrimonial bem)
			throws ModeloException {
		for (Assunto a : bem.getAssuntos()) {
			Query query;
			query = this.entityManager
					.createQuery("SELECT a FROM Assunto a WHERE  a.assunto = :assunto ");
			query.setParameter("assunto", a.getAssunto());
			try {
				a = (Assunto) query.getSingleResult();

			} catch (Exception e1) {
				entityManager.persist(a);
			}
		}
		// entityManager.persist(bem);
		for (Descritor a : bem.getDescritores()) {
			Query query;
			query = this.entityManager
					.createQuery("SELECT a FROM Descritor a WHERE  a.descritor = :descritor ");
			query.setParameter("descritor", a.getDescritor());
			try {
				a = (Descritor) query.getSingleResult();

			} catch (Exception e1) {
				entityManager.persist(a);
			}
		}
		try {
			entityManager.persist(bem);
		} catch (Exception e) {
			throw new ModeloException(e);
		}
		for (Titulo a : bem.getTitulos())
			a.setBempatrimonial(bem); 
		for (Autoria a : bem.getAutorias())
			a.setBemPatrimonial(bem);

	}

	@Override
	public void salvarBemPatrimonial(BemPatrimonial bem) throws ModeloException {
		
		editarBemPatrimonialEJB.salvarBemPatrimonial(bem);
		
	}

}
