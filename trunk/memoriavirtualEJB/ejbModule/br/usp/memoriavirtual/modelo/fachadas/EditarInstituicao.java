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
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarInstituicaoRemote;

@Stateless(mappedName = "EditarInstituicao")
public class EditarInstituicao implements EditarInstituicaoRemote {

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<Instituicao> getInstituicoesSugeridas(String pnome) {
		List<Instituicao> ins = new ArrayList<Instituicao>();
		Query query;

		query = entityManager
				.createQuery("SELECT a FROM Instituicao WHERE a.nome like nome%");
		query.setParameter("nome", pnome);
		try {
			ins = (List<Instituicao>) query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();

		}

		return ins;
	}

	@SuppressWarnings("unchecked")
	public List<Instituicao> getInstituicoesSugeridas(String pnome,
			Grupo grupo, Usuario usuario) {
		List<Instituicao> ins = new ArrayList<Instituicao>();
		Query query;

		query = entityManager
				.createQuery("SELECT a FROM Acesso WHERE a.grupo =:grupo AND a.usuario =:usuario AND a.instituicao like pnome% ");
		query.setParameter("nome", pnome);
		query.setParameter("grupos", grupo);
		query.setParameter("usuario", usuario);
		try {
			ins = (List<Instituicao>) query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();

		}

		return ins;
	}

	@Override
	public String editarInstituicao(String velhoNome, String novoNome,
			String novoEmail, String novoLocalizacao, String novoEndereco,
			String novoCidade, String novoEstado, String novoCep,
			String novoTelefone) {
		Instituicao instituicao;

		instituicao = this.entityManager.find(Instituicao.class, velhoNome);
		if (instituicao != null) {
			instituicao.setNome(novoNome);
			instituicao.setEmail(novoEmail);
			instituicao.setLocalizacao(novoLocalizacao);
			instituicao.setEndereco(novoEndereco);
			instituicao.setCidade(novoCidade);
			instituicao.setEstado(novoEstado);
			instituicao.setCep(novoCep);
			instituicao.setTelefone(novoTelefone);
			entityManager.flush();
			return "sucesso";
		}

		return "erro";
	}

	@Override
	public List<Instituicao> getInstituicoes(Grupo grupo, Usuario usuario) {

		List<Instituicao> instituicoes = new ArrayList<Instituicao>();
		Query query = this.entityManager
				.createQuery("select a from Acesso a where a.usuario = :usuario and a.grupo = :grupo");
		query.setParameter("usuario", usuario);
		query.setParameter("grupo", grupo);

		@SuppressWarnings("unchecked")
		List<Acesso> acessos = (List<Acesso>) query.getResultList();
		for (Acesso acesso : acessos) {
			instituicoes.add(acesso.getInstituicao());
		}

		return instituicoes;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Instituicao> getInstituicoes() {

		List<Instituicao> instituicoes = null;
		Query query = this.entityManager
				.createQuery("select i from Instituicao i");
		instituicoes = (List<Instituicao>) query.getResultList();

		return instituicoes;
	}

}