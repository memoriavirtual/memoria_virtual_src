package br.usp.memoriavirtual.modelo.fachadas;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.Acesso;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.remoto.RealizarLoginRemote;

/**
 * EJB de Sessão sem estado responsável por implementar o caso de uso Realizar
 * Login.
 */
@Stateless(mappedName = "RealizarLogin")
public class RealizarLogin implements RealizarLoginRemote {

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;

	/**
	 * Default constructor.
	 */
	public RealizarLogin() {

	}

	/**
	 * @return resultado Resultado da validação do login
	 * 
	 */
	public Usuario realizarLogin(String usuario, String senha)
			throws ModeloException {

		Usuario usuarioAutenticado = null;
		Usuario usuarioClone = null;

		Query query;

		query = this.entityManager.createNamedQuery("login", Usuario.class);
		query.setParameter("usuario", usuario);
		query.setParameter("senha", Usuario.gerarHash(senha));


		try {
			usuarioAutenticado = (Usuario) query.getSingleResult();
			if (usuarioAutenticado.isAtivo()) {
				usuarioClone = usuarioAutenticado.clone();
			}
		} catch (NoResultException e) {
			usuarioAutenticado = null;
			throw new ModeloException(e);
		}
		return usuarioClone;
	}

	@SuppressWarnings("rawtypes")
	public List<Acesso> getAcessos(Usuario usuario) {

		Query query;

		query = this.entityManager.createNamedQuery("acessos", Acesso.class);
		query.setParameter("usuario", usuario);

		List lista = (List) query.getResultList();

		List<Acesso> listaAcessos = new ArrayList<Acesso>();

		for (Object o : lista) {
			listaAcessos.add((Acesso) o);
		}

		return listaAcessos;
	}

}
