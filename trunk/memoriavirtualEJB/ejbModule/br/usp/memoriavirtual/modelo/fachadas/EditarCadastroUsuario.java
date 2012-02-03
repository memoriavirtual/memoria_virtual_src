package br.usp.memoriavirtual.modelo.fachadas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import javax.validation.ConstraintViolationException;

import com.sun.xml.ws.rx.rm.runtime.sequence.persistent.PersistenceException;

import br.usp.memoriavirtual.modelo.entidades.Acesso;
import br.usp.memoriavirtual.modelo.entidades.Aprovacao;
import br.usp.memoriavirtual.modelo.entidades.Grupo;
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
				.createQuery("SELECT a FROM Acesso a WHERE a.usuario = :usuario AND a.validade = true");
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
				.createQuery("SELECT a FROM Usuario a WHERE a.ativo = true AND a.administrador IS NULL AND a.nomeCompleto LIKE :padrao ORDER BY a.nomeCompleto");
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
	public List<Usuario> getAdministradores(Usuario usuario) {
		List<Usuario> administradores = new ArrayList<Usuario>();

		Query query = entityManager
				.createQuery("SELECT a FROM Usuario a WHERE a <> :requerente AND a.administrador = true AND a.ativo = true");
		query.setParameter("requerente", usuario);
		try {
			administradores = (List<Usuario>) query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return administradores;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> listarUsuarios(String nome, Usuario requerente,
			Grupo grupo) {

		List<Acesso> acessos = new ArrayList<Acesso>();
		List<Instituicao> instituicoes = new ArrayList<Instituicao>();
		List<Usuario> usuarios = new ArrayList<Usuario>();

		Query query = entityManager
				.createQuery("SELECT a FROM Acesso a WHERE a.usuario = :usuario AND a.grupo = :grupo AND a.validade = true");
		query.setParameter("usuario", requerente);
		query.setParameter("grupo", grupo);

		try {

			acessos = (List<Acesso>) query.getResultList();
			for (Acesso a : acessos) {
				instituicoes.add(a.getInstituicao());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Instituicao i : instituicoes) {
			Query q = entityManager
					.createQuery("SELECT a FROM Acesso a WHERE a.instituicao = :instituicao AND a.validade = true");
			q.setParameter("instituicao", i);
			try {
				acessos = q.getResultList();
				for (Acesso a : acessos) {
					// Os usuarios listados não podem ser administradores, não
					// podem ser repetidos, nem podem ser o proprio usuario que
					// faz a requisicao
					if (!a.getUsuario().isAdministrador()
							&& !usuarios.contains(a.getUsuario())
							&& (a.getUsuario().getId() != requerente.getId())) {
						usuarios.add(a.getUsuario());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return usuarios;
	}

	@Override
	public void editarCadastro(Usuario usuario, String nomeCompleto, String telefone){

		Usuario usuarioAlterado = entityManager.find(usuario.getClass(), usuario.getId());

		usuarioAlterado.setNomeCompleto(nomeCompleto);
		usuarioAlterado.setTelefone(telefone);
		
		//problemas com constraints - administrador nao pode ser null
		if(!usuarioAlterado.isAdministrador()){
			usuarioAlterado.setAdministrador(false);
		}
		
		try{
		entityManager.flush();
		}
		catch(Exception t){
			t.printStackTrace();
		}
		
	}
	
	@Override
	public void editarAcessos(Usuario aprovador, List<Acesso> acessos, Date data, Date expiracao){
		
//		for(Acesso a : acessos){
//			Aprovacao aprovacao = new Aprovacao();
//			aprovacao.setAprovador(aprovador);
//			aprovacao.setChaveEstrangeira(a.);
//			aprovacao.setData(data);
//			aprovacao.setExpiracao(expiracao);
//			aprovacao.setTabelaEstrangeira("Acesso");
//		}
		
	}
}
