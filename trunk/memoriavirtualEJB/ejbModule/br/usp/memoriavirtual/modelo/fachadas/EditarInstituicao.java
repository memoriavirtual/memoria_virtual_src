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
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarInstituicaoRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;

@Stateless(mappedName = "EditarInstituicao")
public class EditarInstituicao implements EditarInstituicaoRemote {

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;
	MemoriaVirtualRemote memoriaVirtualEJB;

	public EditarInstituicao() {
	}

	public Instituicao getInstituicao(long id) throws ModeloException {
		try {
			return entityManager.find(Instituicao.class, id);
		} catch (Exception e) {
			throw new ModeloException(e);
		}
	}

	public Instituicao getInstituicao(long id, Grupo grupo, Usuario usuario)
			throws ModeloException {
		try {
			Query  query = this.entityManager
					.createQuery("SELECT a.instituicao FROM Acesso a WHERE a.grupo =: grupo AND a.usuario =: usuario");
			query.setParameter("grupo", grupo);
			query.setParameter("usuario", usuario);

			return (Instituicao) query.getSingleResult();
		} catch (Exception e) {
			throw new ModeloException(e);
		}
	}

	@Override
	public void editarInstituicao(Instituicao instituicao)
			throws ModeloException {

		try {
			this.entityManager.merge(instituicao);
		} catch (Exception e) {
			throw new ModeloException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Instituicao> listarInstituicoes(String pnome, Grupo grupo,
			Usuario usuario) throws ModeloException {
		List<Instituicao> ins = new ArrayList<Instituicao>();
		Query query;

		query = entityManager.createQuery("SELECT a.instituicao "
				+ "FROM Acesso a " + "WHERE a.grupo = :grupo "
				+ "AND a.usuario = :usuario "
				+ "AND LOWER(a.instituicao.nome) LIKE LOWER(:nome) ");
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

	@SuppressWarnings("unchecked")
	public List<Instituicao> listarInstituicoes(String pnome)
			throws ModeloException {
		List<Instituicao> instituicoes = new ArrayList<Instituicao>();
		Query query;
		query = entityManager.createQuery("SELECT a FROM Instituicao a  "
				+ "WHERE a.validade = TRUE AND LOWER(a.nome) "
				+ "LIKE LOWER(:padrao) ORDER BY a.nome");

		query.setParameter("padrao", "%" + pnome + "%");
		try {
			instituicoes = (List<Instituicao>) query.getResultList();
		} catch (Exception e) {
			throw new ModeloException(e);
		}

		return instituicoes;
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario> getGerentes(Instituicao i){
		Grupo g = new Grupo("GERENTE");
		Query q = entityManager.createQuery("SELECT a.usuario FROM Acesso a where a.grupo = :grupo AND a.instituicao = :ins");
		q.setParameter("ins", i);
		q.setParameter("grupo", g);
		return q.getResultList();
	}
}