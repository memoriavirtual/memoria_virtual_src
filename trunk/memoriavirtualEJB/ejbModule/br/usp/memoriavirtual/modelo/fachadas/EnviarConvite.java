package br.usp.memoriavirtual.modelo.fachadas;

import java.util.Date;
import java.util.Random;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.management.timer.Timer;
import javax.persistence.*;

import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EnviarConviteRemote;

@Stateless(mappedName = "EnviarConvite")
public class EnviarConvite implements EnviarConviteRemote {

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;
	@Resource(name="mail/myMailSession")
	private javax.mail.Session session;


	/**
	 * Default constructor.
	 */
	public EnviarConvite() {

	}

	/**
	 * @return <code>"sucesso"</code> se convites foram enviados com sucesso
	 *         cadastrados
	 * @return <code>"Formato de email invalido: email"</code> se existe erro no
	 *         email
	 * 
	 * @return <code>"Email ja existente no sistema: email"</code> se o email ja
	 *         esta cadastrado
	 * @return <code>"Erro ao cadastrar no banco de dados: email"</code> Se
	 *         houve algum erro ao incluir o email no banco de dados
	 */
	public String enviarConvite(String emails, String mensagem,
			String validade, String instituicao, String nivelAcesso) {
		String[] email = emails.split("[; ]+");
		String erro;
		erro = procurarEmailsBD(email);
		if (erro != null) {
			return "Email ja existente no sistema: " + erro;
		}

		for (Integer i = 0; i < email.length; i++) {
			erro = cadastrarUsuarioBD(email[i], validade, instituicao,
					nivelAcesso);
			if (erro.equals("falhaBD"))
				return "Erro ao cadastrar no banco de dados: " + email[i]; // Se houve algum erro desconhecido ao inserir no banco
			else if (erro.equals("falhaEmail"))
				return "Formato de email invalido: " + email[i];

			// TODO:mandar email
		}

		return "sucesso";
	}

	/**
	 * Verifica se emails ja existem na base.
	 * 
	 * @return <code>email</code> O primeiro email encontrado na base.
	 * @return <code>null</code> nenhum email encontrado na base.
	 */

	public String procurarEmailsBD(String[] emails) {
		Query query;
		for (Integer i = 0; i < emails.length; i++) {
			query = this.entityManager
					.createQuery("SELECT u FROM Usuario u WHERE u.email = :email");
			query.setParameter("email", emails[i]);
			try {
				query.getSingleResult();
				return emails[i];
			} catch (NoResultException e) {
			}
		}
		return null;
	}

	/**
	 * @return falhaEmail Se formato do email esta incorreto
	 * @return falhaBD Se ocorreu algum erro ao incluir no banco de dados
	 * @return sucesso Se o usuario foi incluido no banco de dados com sucesso
	 */
	private String cadastrarUsuarioBD(String email, String validade,
			String instituicaoId, String nivelAcesso) {

		Boolean usuarioCadastrado = false;
		Date dataAtual = new Date();
		Date vencimento = new Date(dataAtual.getTime()
				+ (long) Integer.parseInt(validade) * Timer.ONE_DAY);
		String id;
		Usuario user = new Usuario();
		Instituicao instituicao = null;
		Random generator = new Random(); // Usado para gerar o hash do convite

		// Gera o id do usuario calculando o hash com base no email + um numero
		// aleatorio.
		// Se ja existir o id, calcula outro numero aleatorio ate que o id seja
		// unico
		// id serÃ¡ usado para verificaÃ§Ã£o do convite enviado via email
		do {
			id = user.gerarHash(email + Integer.toString(generator.nextInt()));
		} while (entityManager.find(Usuario.class, id) != null);
		user.setId(id);
		user.setValidade(vencimento);
		user.setEmail(email);
		instituicao = (Instituicao) entityManager.find(Instituicao.class, instituicaoId);
		if(instituicao == null)
			return "falhaBD";
		
		if (!this.entityManager.contains(user)) {
			// Persiste usuario na base
			try {
				this.entityManager.persist(user);
			} catch (Exception e) {
				return "falhaEmail"; // Na hora de persisitir o usuario, se o email estiver errado é gerada uma exceçao
			}
			if (this.entityManager.contains(user)) {
				usuarioCadastrado = true;
			}
		}
		return (usuarioCadastrado) ? "sucesso" : "falhaBD";
	}
	
	public void enviarEmail(String destinatario, String assunto, String mensagem) {
	    try {
	        Message message = new MimeMessage(session);
	        message.setFrom();
	        message.setRecipients(Message.RecipientType.TO,
	                InternetAddress.parse(destinatario, false));
	        message.setSubject(assunto);
	        Date timeStamp = new Date();
	        message.setText(mensagem);
			message.setHeader("X-Mailer", "Memoria virtual mailer");
	        message.setSentDate(timeStamp);

	        // Send message
	        Transport.send(message);
	    } catch (MessagingException ex) {
	        ex.printStackTrace();
	    }
	}

	
}
