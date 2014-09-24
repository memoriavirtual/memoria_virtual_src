package br.usp.memoriavirtual.modelo.fachadas;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.Acesso;
import br.usp.memoriavirtual.modelo.entidades.Aprovacao;
import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirInstituicaoRemote;
import br.usp.memoriavirtual.utils.MVModeloStatusAprovacao;

@Stateless(mappedName = "ExcluirInstituicao")
public class ExcluirInstituicao implements ExcluirInstituicaoRemote {

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;
	
	public ExcluirInstituicao() {

	}

	@SuppressWarnings("unchecked")
	public List<Usuario> listarAnalistas(Instituicao instituicao, Usuario usuario) throws ModeloException {

		try {
			Grupo grupo = new Grupo("GERENTE");
			List<Usuario> usuarios;
			Query query;
			query = this.entityManager.createQuery("SELECT a.usuario FROM Acesso a WHERE "
					+ "a.grupo = :grupo AND a.instituicao = :instituicao AND a.validade = true AND "
					+ "a.usuario <> :usuario UNION SELECT u FROM Usuario u WHERE "
					+ "u.administrador = true AND u <> :usuario");
			query.setParameter("grupo", grupo);
			query.setParameter("instituicao", instituicao);
			query.setParameter("usuario", usuario);

			usuarios = (List<Usuario>) query.getResultList();
			return usuarios;
		} catch (Exception e) {
			throw new ModeloException(e);
		}

	}

	@Override
	public void negar(Instituicao instituicao, Aprovacao aprovacao) throws ModeloException {
		try {
			aprovacao.setStatus(MVModeloStatusAprovacao.negada);
			aprovacao.setAlteradaEm(new Date());
			entityManager.merge(aprovacao);

			instituicao.setValidade(true);
			entityManager.merge(instituicao);
		} catch (Exception e) {
			throw new ModeloException(e);
		}
	}

	@Override
	public void aprovar(Instituicao instituicao, Aprovacao aprovacao) throws ModeloException {
		try {
			aprovacao.setStatus(MVModeloStatusAprovacao.aprovada);
			aprovacao.setAlteradaEm(new Date());
			entityManager.merge(aprovacao);

			instituicao = entityManager.find(Instituicao.class, instituicao.getId());
			
			Query query;
			query = this.entityManager.createQuery("Select a from Acesso a where a.instituicao.id = :i");
			query.setParameter("i", instituicao.getId());
			
			@SuppressWarnings("unchecked")
			List<Acesso> acessos = query.getResultList();
			
			for(Acesso a: acessos){
				entityManager.remove(a);
			}
			
			entityManager.remove(instituicao);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ModeloException(e);
		}

	}

	@Override
	public Long solicitarExclusao(Instituicao instituicao, Aprovacao aprovacao) throws ModeloException {
		try {
			instituicao.setValidade(false);
			entityManager.merge(instituicao);
			entityManager.persist(aprovacao);
			return aprovacao.getId();
		} catch (Exception e) {
			throw new ModeloException(e);
		}
	}

}