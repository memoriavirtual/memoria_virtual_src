package br.usp.memoriavirtual.modelo.fachadas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.ContainerMultimidia;
import br.usp.memoriavirtual.modelo.entidades.Multimidia;
import br.usp.memoriavirtual.modelo.fachadas.remoto.UtilMultimidiaRemote;
import br.usp.memoriavirtual.utils.MVModeloCamposMultimidia;

@Stateless(mappedName = "cadastrarMultimidia")
public class UtilMultimidia implements UtilMultimidiaRemote, Serializable {

	private static final long serialVersionUID = -4994278707531407765L;

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager em;

	@Override
	public Long cadastrarMultimidia(Multimidia m) throws ModeloException {
		try {
			ContainerMultimidia c = em.find(ContainerMultimidia.class, m.getContainerMultimidia().getId());
			m.setContainerMultimidia(c);
			em.persist(m);
			return Long.valueOf(m.getId());
		} catch (Exception e) {
			throw new ModeloException(e);
		}
	}

	@Override
	public void excluirMultimidia(long id) throws ModeloException {
		try {
			Multimidia m = em.find(Multimidia.class, id);
			em.remove(m);
			em.flush();
		} catch (Exception e) {
			throw new ModeloException(e);
		}

	}

	@Override
	public Multimidia getMultimidia(long id) throws ModeloException {
		try {
			Multimidia m = em.find(Multimidia.class, id);
			return m;
		} catch (Exception e) {
			throw new ModeloException(e);
		}
	}

	@Override
	public String getContentType(long id) throws ModeloException {
		try {
			Query q = em
					.createQuery("SELECT m.contentType FROM Multimidia m WHERE m.id = :id");
			q.setParameter("id", id);
			return (String) q.getSingleResult();
		} catch (Exception e) {
			throw new ModeloException(e);
		}
	}

	@Override
	public void atualizarMidia(long id, String nome, String descricao)
			throws ModeloException {
		try {
			Multimidia m = em.find(Multimidia.class, id);
			m.setNome(nome);
			m.setDescricao(descricao);
			em.merge(m);
		} catch (Exception e) {
			throw new ModeloException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MVModeloCamposMultimidia> listarCampos(
			ContainerMultimidia container) throws ModeloException {
		try {
			List<MVModeloCamposMultimidia> campos = new ArrayList<MVModeloCamposMultimidia>();
			Query q = em
					.createQuery("SELECT m.id, m.nome, m.descricao FROM Multimidia m WHERE m.containerMultimidia = :container");
			q.setParameter("container", container);
			List<Object[]> rows = q.getResultList();
			for (Object[] row : rows) {
				MVModeloCamposMultimidia campo = new MVModeloCamposMultimidia();
				Long id = (Long) row[0];
				campo.setId(id.longValue());
				campo.setNome((String) row[1]);
				campo.setDescricao((String) row[2]);
				campos.add(campo);
			}
			return campos;
		} catch (Exception e) {
			throw new ModeloException(e);
		}
	}

}
