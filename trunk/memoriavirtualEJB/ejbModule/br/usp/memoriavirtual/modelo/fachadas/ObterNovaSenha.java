package br.usp.memoriavirtual.modelo.fachadas;

import java.io.IOException;
import java.util.Random;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ObterNovaSenhaRemote;

@Stateless(mappedName = "ObterNovaSenha")
public class ObterNovaSenha implements ObterNovaSenhaRemote {

	@EJB
	private MemoriaVirtualRemote memoriaVirtual;
	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;

	/**
	 * Default constructor.
	 */
	public ObterNovaSenha() {

	}

	@Override
	public void obterNovaSenha(String email) throws ModeloException {
		
		Usuario usuario = null;
		String token = null;

		usuario = recuperarUsuario(email);
		if (usuario != null) {
			token = Usuario.gerarHash(email
					+ Integer.toString(new Random().nextInt()));

			usuario.setSenha(token);
			this.entityManager.flush();
			
			try {
				String assunto = "Recuperação de senha do Memoria Virtual";
				String mensagem = "Você foi solicitou uma nova senha do Memoria Virtual.  "
						+ "Para cadastrar uma nova senha, entre no link a seguir: "
						+ this.memoriaVirtual.getEnderecoServidor()
								.getCanonicalHostName()
						+ "/ObterNovaSenha.jsf?Validacao="
						+ token
						+ "&email="
						+ email;
				this.memoriaVirtual.enviarEmail(email, assunto, mensagem);
			} catch (IOException e) {
				e.printStackTrace();
				throw new ModeloException("Erro no servidor");
			} catch (MessagingException e) {
				e.printStackTrace();
				throw new ModeloException("Erro ao enviar o email");
			}

		} else
			throw new ModeloException("Email não encontrado");
	}
	
	public void cadastrarNovaSenha(String email, String token, String novaSenha) throws ModeloException{
		Usuario usuario = null;
		
		usuario = recuperarUsuario(email, token);
		if (usuario != null) {
			usuario.setSenha(Usuario.gerarHash(novaSenha));
			this.entityManager.flush();
		} else
			throw new ModeloException("Erro ao processar sua solicitação.");
	}

	private Usuario recuperarUsuario(String email) {
		Usuario usuario = null;

		Query query = this.entityManager
				.createQuery("SELECT u FROM Usuario u WHERE u.email = :email");
		query.setParameter("email", email);
		usuario = (Usuario) query.getSingleResult();

		return usuario;
	}
	
	private Usuario recuperarUsuario(String email, String senha) {
		Usuario usuario = null;

		Query query = this.entityManager
				.createQuery("SELECT u FROM Usuario u WHERE u.email = :email AND u.senha = :senha");
		query.setParameter("email", email);
		query.setParameter("senha", senha);
		usuario = (Usuario) query.getSingleResult();

		return usuario;
	}
}
