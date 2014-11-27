package br.usp.memoriavirtual.modelo.fachadas;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.Aprovacao;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarCadastroUsuarioRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;

@Stateless(mappedName = "EditarCadastroUsuario")
public class EditarCadastroUsuario implements EditarCadastroUsuarioRemote {

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;

	@EJB
	private MemoriaVirtualRemote memoriaVirtualEJB;

	public EditarCadastroUsuario() {

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> listarAprovadores(Usuario requerente, Usuario usuario)
			throws ModeloException {
		List<Usuario> administradores = new ArrayList<Usuario>();

		Query query = entityManager
				.createQuery("SELECT a FROM Usuario a WHERE a <> :requerente "
						+ "AND a <> :u AND a.administrador = true AND a.ativo = true");
		query.setParameter("requerente", requerente);
		query.setParameter("u", usuario);
		try {
			administradores = (List<Usuario>) query.getResultList();
		} catch (Exception e) {
			throw new ModeloException(e);
		}
		return administradores;
	}

	@Override
	public Aprovacao getAprovacao(String aprovacaoString)
			throws ModeloException {

		try {
			long id = new Long(aprovacaoString).longValue();
			Aprovacao aprovacao = this.entityManager.find(Aprovacao.class, id);
			return aprovacao;
		} catch (Exception e) {
			throw new ModeloException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Instituicao> listarInstituicoes(String instituicao)
			throws ModeloException {
		List<Instituicao> instituicoes = new ArrayList<Instituicao>();

		try {
			Query query = entityManager
					.createQuery("SELECT i FROM Instituicao i WHERE LOWER(i.nome) LIKE LOWER(:padrao) AND i.validade = true ORDER BY i.nome");
			query.setParameter("padrao", "%" + instituicao + "%");
			instituicoes = (List<Instituicao>) query.getResultList();

			return instituicoes;
		} catch (Exception e) {
			throw new ModeloException(e);
		}
	}

	@Override
	public long solicitarEdicao(Aprovacao aprovacao) throws ModeloException {
		try {
			entityManager.persist(aprovacao);
			entityManager.flush();
			return aprovacao.getId();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ModeloException(e);
		}
	}

	@Override
	public void aprovar(Usuario usuario, Aprovacao aprovacao)
			throws ModeloException {
		try {
			entityManager.merge(usuario);
			entityManager.merge(aprovacao);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ModeloException(e);
		}
	}

	@Override
	public void negar(Aprovacao aprovacao) throws ModeloException {
		try {
			entityManager.merge(aprovacao);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ModeloException(e);
		}
	}
}
