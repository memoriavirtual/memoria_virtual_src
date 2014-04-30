package br.usp.memoriavirtual.modelo.fachadas;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.usp.memoriavirtual.modelo.entidades.Aprovacao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirUsuarioRemote;

@Stateless(mappedName = "ExcluirUsuario")
public class ExcluirUsuario implements ExcluirUsuarioRemote {

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;

	@Override
	public long solicitarExclusao(Usuario usuario, Aprovacao aprovacao)
			throws ModeloException {
		try {
			entityManager.persist(aprovacao);
			usuario = entityManager.find(Usuario.class, usuario.getId());
			usuario.setAtivo(false);
			entityManager.merge(usuario);
			entityManager.flush();
			return aprovacao.getId().longValue();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ModeloException(e);
		}
	}

	@Override
	public void aprovar(Usuario usuario, Aprovacao aprovacao)
			throws ModeloException {
		try {
			entityManager.merge(aprovacao);
			usuario = entityManager.find(Usuario.class, usuario.getId());
			entityManager.remove(usuario);
			entityManager.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ModeloException(e);
		}
	}

	@Override
	public void negar(Usuario usuario, Aprovacao aprovacao)
			throws ModeloException {
		try {
			entityManager.persist(aprovacao);
			usuario = entityManager.find(Usuario.class, usuario.getId());
			usuario.setAtivo(true);
			entityManager.merge(usuario);
			entityManager.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ModeloException(e);
		}
	}
}
