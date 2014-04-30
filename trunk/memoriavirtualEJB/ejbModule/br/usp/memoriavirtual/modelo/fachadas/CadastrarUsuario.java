package br.usp.memoriavirtual.modelo.fachadas;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.Acesso;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.remoto.CadastrarUsuarioRemote;

@Stateless(mappedName = "CadastrarUsuario")
public class CadastrarUsuario implements CadastrarUsuarioRemote, Serializable {

	private static final long serialVersionUID = -1389418898478564440L;

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;

	public Usuario verificarConvite(String id) throws ModeloException {
		try {
			return (Usuario) entityManager.find(Usuario.class,
					new Long(id).longValue());
		} catch (NoResultException e) {
			throw new ModeloException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public void cadastrarUsuario(Usuario usuario, String senha)
			throws ModeloException {

		try {
			Usuario managed = entityManager
					.find(Usuario.class, usuario.getId());
			managed.setIdentificacao(usuario.getIdentificacao());
			managed.setAtivo(true);
			managed.setNomeCompleto(usuario.getNomeCompleto());
			managed.setEmail(usuario.getEmail());
			managed.setSenha(senha);
			managed.setTelefone(usuario.getTelefone());
			managed.setValidade(null);
			entityManager.merge(managed);

			Query queryAcesso = this.entityManager
					.createQuery("SELECT a FROM Acesso a Where a.usuario = :usuario");
			queryAcesso.setParameter("usuario", managed);

			List<Acesso> acessos = (List<Acesso>) queryAcesso.getResultList();

			for (Acesso acesso : acessos) {
				acesso.setUsuario(managed);
				entityManager.merge(acesso);
			}

			entityManager.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ModeloException(e);
		}
	}
}
