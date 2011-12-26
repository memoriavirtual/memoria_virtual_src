package br.usp.memoriavirtual.modelo.fachadas;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.Acesso;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarCadastroUsuarioRemote;

@Stateless(mappedName = "EditarCadastroUsuario")
public class EditarCadastroUsuario implements EditarCadastroUsuarioRemote {

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;

	public EditarCadastroUsuario() {

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Acesso> getAcessos(Usuario usuario) {
		List<Acesso> acessos = new ArrayList<Acesso>();
		Query query = entityManager
				.createQuery("SELECT a FROM Acesso a WHERE a.usuario = :usuario");
		query.setParameter("usuario", usuario);
		try {

			acessos = (List<Acesso>) query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return acessos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> listarUsuarios(String nome) {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		Query query = entityManager
				.createQuery("SELECT a FROM Usuario a WHERE a.nomeCompleto LIKE :padrao ORDER BY a.nomeCompleto");
		query.setParameter("padrao", "%" + nome + "%");
		try {
			usuarios = (List<Usuario>) query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usuarios;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> getAdministradores(Usuario usuario){
		List<Usuario> administradores = new ArrayList<Usuario>();
		
		Query query = entityManager.createQuery("SELECT a FROM Usuario a WHERE a.administrador =: true");
		try{
			administradores = (List<Usuario>)query.getResultList();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return administradores;
	}

}
