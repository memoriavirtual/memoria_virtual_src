package br.usp.memoriavirtual.modelo.fachadas;

import java.util.Date;
import java.util.Properties;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.mail.*;
import javax.mail.internet.*;
import javax.persistence.*;

import br.usp.memoriavirtual.modelo.entidades.Usuario;
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
	 */
	public String enviarConvite(String emails, String validade,
			String nivelAcesso) {
		String[] email = emails.split("[; ]+");
		String emailErro;
		
		emailErro = validarEmails(email);
		if(emailErro != null){
			return "email invalido: " + emailErro;
		}
		
		emailErro = procurarEmailsBD(email);
		if(emailErro != null){
			return "email existente: " + emailErro;
		}
		
		for(Integer i = 0; i < email.length; i++){
			cadastrarUsuarioBD(email[i], validade, nivelAcesso);
		}
		
		return "ok";
	}
	
	/** 
	 * Verifica se emails são validos.
	 * @return <code>email</code> O primeiro email invalido.
	 * @return <code>null</code> nenhum email invalido.
	 */

	private String validarEmails(String[] emails) {
		for (Integer i = 0; i < emails.length; i++) {
			if (!emails[i].contains("@") || !emails[i].contains(".")) {
				return emails[i];
			}
		}
		return null;
	}
	
	/** 
	 * Verifica se emails são validos.
	 * @return <code>email</code> O primeiro email encontrado na base.
	 * @return <code>null</code> nenhum email encontrado na base.
	 */
	
	private String procurarEmailsBD(String[] emails){
		Query query;		
		for(Integer i = 0; i > emails.length; i++){
			query = this.entityManager
				.createQuery("SELECT u FROM Usuario u WHERE (u.email = :usuario)");
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
	public boolean cadastrarUsuarioBD(String email, String validade, String nivelAcesso) {
		//TODO Criar niveis na base, processar validade e nivelAcesso
		Usuario user = new Usuario();
		user.setEmail(email);
		user.setId(user.gerarHash(email));
		
		if(entityManager.find(Usuario.class, email) == null && !entityManager.contains(user)){
			entityManager.persist(user);
		}
		return entityManager.contains(user);
	}
	
	private void enviarEmail(String destinatario, String assunto, String mensagem) {
	//TODO Arrumar remetente e host
        String remetente = "********";
        String host = "smtp.*******";
 
        Properties props = new Properties();
 
        props.put("mail.smtp.host", host);
        props.put("mail.debug", "true");
        Session session = Session.getInstance(props);
 
        try {
            Message msg = new MimeMessage(session);
 
            msg.setFrom(new InternetAddress(remetente));
            InternetAddress[] address = {new InternetAddress(destinatario)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(assunto);
            msg.setSentDate(new Date());
            msg.setText(mensagem);
 
            Transport.send(msg);
        }
        catch (MessagingException e) {
        	throw new EJBException(e.getMessage()); //É correto fazer isso?
        }
    }
}
