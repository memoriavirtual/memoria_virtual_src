package br.usp.memoriavirtual.modelo.fachadas;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.Acesso;
import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarCadastroUsuarioRemote;

@Stateless(mappedName = "EditarCadastroUsuario")
public class EditarCadatroUsuario implements EditarCadastroUsuarioRemote {

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;

	@Override
	public void editarCadastroUsuario(String velhoNome, String novoNome,
			String novoTelefone, List<Acesso> acesso) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Acesso> getUsuariosSugeridos(String velhoNome, Grupo grupo,
			Usuario usuario) {
		List<Acesso> usuarios = new ArrayList<Acesso>();
		List<Acesso> instituicoes = new ArrayList<Acesso>();

		Query query = entityManager
				.createQuery("SELECT a FROM Acesso a WHERE a.grupo := grupo AND a.usuario");
		query.setParameter("grupo", grupo);
		query.setParameter("usuario", usuario);
		try{
			instituicoes = (List<Acesso>)query.getResultList();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		for(Acesso i : instituicoes){
			query = entityManager.createQuery("SELECT a FROM Acesso a WHERE ");
		}
		return usuarios;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Acesso> getUsuariosSugeridos(String velhoNome) {
		List<Acesso> usuarios = new ArrayList<Acesso>();
		Query query = entityManager
				.createQuery("SELECT a FROM Acesso a WHERE a.usuario LIKE padrao ORDER BY a.usuario");
		query.setParameter("padrao", velhoNome + "%");
		try{
			usuarios = (List<Acesso>)query.getResultList();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return usuarios;
	}

}
