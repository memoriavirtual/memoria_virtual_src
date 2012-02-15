package br.usp.memoriavirtual.modelo.fachadas;

import java.util.Random;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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

		usuario = recuperarUsuario(email);
		if (usuario != null) {

			usuario.setSenha(email + new Random().nextInt());
			this.entityManager.flush();

			try {
				String assunto = "Recuperação de senha do Memoria Virtual";
				String mensagem = "Você foi solicitou uma nova senha do Memoria Virtual.  "
						+ "Para cadastrar uma nova senha, entre no link a seguir: "
						+ this.memoriaVirtual.getURLServidor() + "/cadastrarnovasenha.jsf?email=" + email
						+ "&validacao=" + usuario.getSenha();
				this.memoriaVirtual.enviarEmail(email, assunto, mensagem);
			} catch (MessagingException e) {
				e.printStackTrace();
				throw new ModeloException("Erro ao enviar o email!");
			}

			throw new ModeloException("Email enviado com sucesso!");

		} else
			throw new ModeloException("Email não encontrado!");
	}

	public void cadastrarNovaSenha(String email, String token, String novaSenha) throws ModeloException {
		Usuario usuario = null;

		usuario = recuperarUsuario(email, token);
		if (usuario != null) {
			usuario.setSenha(novaSenha);
			this.entityManager.flush();
		} else
			throw new ModeloException("Erro ao processar sua solicitação.");
	}

	/**
	 * 
	 * @param email
	 * @return Usuario com o email fornecido
	 * @throws ModeloException
	 */
	private Usuario recuperarUsuario(String email) throws ModeloException {
		Usuario usuario = null;

		Query query = this.entityManager.createQuery("SELECT u FROM Usuario u WHERE u.email = :email");
		query.setParameter("email", email);
		try {
			usuario = (Usuario) query.getSingleResult();
		} catch (NoResultException e) {
			throw new ModeloException("Email não cadastrado!");
		}
		return usuario;
	}

	/**
	 * 
	 * @param email
	 * @param senha
	 * @return Usuario com o email e senha fornecidos (A senha sera comparada com a senha armazenada no banco, sem
	 *         calcular hash, pois é apenas um token para a recuperçao da senha)
	 * @throws ModeloException
	 * 
	 */
	private Usuario recuperarUsuario(String email, String senha) throws ModeloException {
		Usuario usuario = null;

		Query query = this.entityManager
				.createQuery("SELECT u FROM Usuario u WHERE u.email = :email AND u.senha = :senha");
		query.setParameter("email", email);
		query.setParameter("senha", senha);
		try {
			usuario = (Usuario) query.getSingleResult();
		} catch (NoResultException e) {
			throw new ModeloException("Erro processar sua solicitação!");
		}

		return usuario;
	}
}
