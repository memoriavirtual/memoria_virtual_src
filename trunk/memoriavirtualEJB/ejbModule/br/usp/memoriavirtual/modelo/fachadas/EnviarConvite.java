package br.usp.memoriavirtual.modelo.fachadas;

import java.util.Date;
import java.util.Properties;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.mail.*;
import javax.mail.internet.*;
import javax.management.timer.Timer;
import javax.persistence.*;

import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.entidades.UsuarioInstituicao;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EnviarConviteRemote;

@Stateless(mappedName = "EnviarConvite")
public class EnviarConvite implements EnviarConviteRemote {

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;

	/**
	 * Default constructor.
	 */
	public EnviarConvite() {

	}

	/**
	 * @return <code>"ok"</code> se emails s�o validos e ainda n�o foram
	 *         cadastrados
	 * @return <code>"email invalido: email"</code> se existe erro no email
	 * 
	 * @return <code>"email existente: email"</code> se o email ja esta
	 *         cadastrado
	 * @return <code>"Erro ao cadastrar no banco de dados: email"</code> Se
	 *         houve algum erro ao incluir o email no banco de dados
	 */
	public String enviarConvite(String emails, String mensagem,
			String validade, String instituicao, String nivelAcesso) {
		String[] email = emails.split("[; ]+");
		String emailErro;

		emailErro = procurarEmailsBD(email);
		if (emailErro != null) {
			return "email existente: " + emailErro;
		}

		for (Integer i = 0; i < email.length; i++) {
			if (!cadastrarUsuarioBD(email[i], validade, instituicao, nivelAcesso))
				return "Erro ao cadastrar no banco de dados: " + email[i];
		}

		return "ok";
	}

	/**
	 * Verifica se emails ja existem na base.
	 * 
	 * @return <code>email</code> O primeiro email encontrado na base.
	 * @return <code>null</code> nenhum email encontrado na base.
	 */

	private String procurarEmailsBD(String[] emails) {
		Query query;
		for (Integer i = 0; i < emails.length; i++) {
			query = this.entityManager
					.createQuery("SELECT u FROM Usuario u WHERE u.email = :email");
			query.setParameter("email", emails[i]);
			
			try {
				query.getSingleResult();
				return emails[i];
			} catch (NoResultException e) {}
		}
		return null;
	}

	/**
	 * @return true Se teve sucesso
	 * @return false Se aconteceu algum erro
	 */
	public boolean cadastrarUsuarioBD(String email, String validade,
			String instituicao, String nivelAcesso) {
		Boolean usuarioCadastrado = false;
		Boolean usuarioInstituicaoCadastrado = false;
		Date dataAtual = new Date();
		Date vencimento = new Date(dataAtual.getTime()
				+ (long) Integer.parseInt(validade) * Timer.ONE_DAY);

		Usuario user = new Usuario();
		user.setEmail(email);
		user.setId(user.gerarHash(email + "Memoria"));
		user.setValidade(vencimento);

		UsuarioInstituicao usuarioInstituicao = new UsuarioInstituicao();
		usuarioInstituicao.setIdUsuario(user.getId());
		usuarioInstituicao.setNumRegistroInstituicao(instituicao);
		usuarioInstituicao.setTipo(nivelAcesso);
		if (!this.entityManager.contains(user)) {
			// Persiste usuario na base
			this.entityManager.persist(user);
			if (this.entityManager.contains(user))
				usuarioCadastrado = true;

			// Persiste relacao entre usuario e instituicao na base
			this.entityManager.persist(usuarioInstituicao);
			if (this.entityManager.contains(usuarioInstituicao))
				usuarioInstituicaoCadastrado = true;
		}
		return usuarioCadastrado && usuarioInstituicaoCadastrado;
	}

	private void enviarEmail(String destinatario, String assunto, String mensagem) {
		// TODO Arrumar remetente e host
		String remetente = "********";
		String host = "smtp.*******";

		Properties props = new Properties();

		props.put("mail.smtp.host", host);
		props.put("mail.debug", "true");
		Session session = Session.getInstance(props);

		try {
			Message msg = new MimeMessage(session);

			msg.setFrom(new InternetAddress(remetente));
			InternetAddress[] address = { new InternetAddress(destinatario) };
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject(assunto);
			msg.setSentDate(new Date());
			msg.setText(mensagem);

			Transport.send(msg);
		} catch (MessagingException e) {
			throw new EJBException(e.getMessage()); // É correto fazer isso?
		}
	}
}
