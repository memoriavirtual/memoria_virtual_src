package br.usp.memoriavirtual.modelo.fachadas;

import java.util.Date;
import java.util.Properties;
import java.util.Random;

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
	 * @return <code>"sucesso"</code> se convites foram enviados com sucesso
	 *         cadastrados
	 * @return <code>"Formato de email invalido: email"</code> se existe erro no email
	 * 
	 * @return <code>"Email ja existente no sistema: email"</code> se o email ja esta
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
			return "Email ja existente no sistema: " + emailErro;
		}

		for (Integer i = 0; i < email.length; i++) {
			emailErro = cadastrarUsuarioBD(email[i], validade, instituicao, nivelAcesso);
			if (emailErro.equals("falhaBD"))
				return "Erro ao cadastrar no banco de dados: " + email[i];
			else if(emailErro.equals("falhaEmail"))
				return "Formato de email invalido: " + email[i];
		}

		return "sucesso";
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
	 * @return falhaEmail Se formato do email esta incorreto
	 * @return falhaBD Se ocorreu algum erro ao incluir no banco de dados
	 * @return sucesso Se o usuario foi incluido no banco de dados com sucesso
	 */
	public String cadastrarUsuarioBD(String email, String validade,
			String instituicao, String nivelAcesso) {
		Boolean usuarioCadastrado = false;
		Boolean usuarioInstituicaoCadastrado = false;
		Date dataAtual = new Date();
		Date vencimento = new Date(dataAtual.getTime()
				+ (long) Integer.parseInt(validade) * Timer.ONE_DAY);
		String id;
		Usuario user = new Usuario();
		Random generator = new Random();
		
		user.setEmail(email);
		//Gera o id do usuario calculando o hash com base no email + um numero aleatorio.
		//Se ja existir o id, calcula outro numero aleatorio ate que o id seja unico
		//id será usado para verificação do convite enviado via email
		do{
			id = user.gerarHash(email + Integer.toString(generator.nextInt()));
		}while(entityManager.find(Usuario.class, id) != null);
		user.setId(id);
		user.setValidade(vencimento);

		UsuarioInstituicao usuarioInstituicao = new UsuarioInstituicao();
		usuarioInstituicao.setIdUsuario(user.getId());
		usuarioInstituicao.setNumRegistroInstituicao(instituicao);
		usuarioInstituicao.setTipo(nivelAcesso);
		if (!this.entityManager.contains(user)) {
			// Persiste usuario na base
			try{
				this.entityManager.persist(user);
			}catch(Exception e){
				return "falhaEmail";
			}
			if (this.entityManager.contains(user))
				usuarioCadastrado = true;

			// Persiste relacao entre usuario e instituicao na base
			this.entityManager.persist(usuarioInstituicao);
			if (this.entityManager.contains(usuarioInstituicao))
				usuarioInstituicaoCadastrado = true;
		}
		return (usuarioCadastrado && usuarioInstituicaoCadastrado)?"sucesso":"falhaBD";
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
