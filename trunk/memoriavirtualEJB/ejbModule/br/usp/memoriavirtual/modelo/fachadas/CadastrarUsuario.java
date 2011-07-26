package br.usp.memoriavirtual.modelo.fachadas;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.remoto.CadastrarUsuarioRemote;

@Stateless(mappedName = "CadastrarUsuario")
public class CadastrarUsuario implements CadastrarUsuarioRemote{
	
	@PersistenceContext(unitName = "memoriavirtual")
    private EntityManager entityManager;
	
	public String completarCadastro(String email, String nomeCompleto, String telefone, String senha, String validacao){
		
		/*Busco pelo convite usando o campo ID na tabela*/
		Query query = this.entityManager
			.createQuery("SELECT u FROM Usuario u WHERE u.id = :usuario");
		query.setParameter("usuario", validacao);

		Usuario usuario = null;
		
		/*Atualizo o banco com os dados cadastrados*/
		try {
		    usuario =  (Usuario) query.getSingleResult();
		    usuario.setEmail(email);
		    usuario.setNomeCompleto(nomeCompleto);
		    usuario.setSenha(senha);
		    usuario.setAtivo(true);
		} catch (NoResultException e) {
		    usuario = null;
		    e.printStackTrace();
		    return "Convite n�o encontrado";
		}

	    
		return "sucesso";
	}
	
	public String validarEmail(String email) {

			String regexp = "[a-z0-9!#$%&’*+/=?^_‘{|}~-]+(?:\\."
					+ "[a-z0-9!#$%&’*+/=?^_‘{|}~-]+)*@"
					+ "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
			Pattern pattern = Pattern.compile(regexp);
			Matcher matcher = pattern.matcher(email);

			if (!matcher.matches())
				return "Formato invalido de email";
			
			Query query = this.entityManager
						.createQuery("SELECT u FROM Usuario u WHERE u.email = :email");
			query.setParameter("email", email);
				
			try {
				query.getSingleResult();
				return "Email ja cadastrado.";
			} catch (NoResultException e) {
				
			}
			return null;
	}

}
