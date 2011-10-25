package br.usp.memoriavirtual.modelo.fachadas;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Date;
import java.util.Enumeration;

import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;

/**
 * EJB Sem estado e singleton que contém métodos comuns ao sistema todo.
 */
@Singleton
public class MemoriaVirtual implements MemoriaVirtualRemote {

	private static InetAddress enderecoServidor = null;
	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;
	@Resource(name = "mail/memoriavirtual")
	private javax.mail.Session mailSession;

	/**
	 * Default constructor.
	 */
	public MemoriaVirtual() {

	}

	/**
	 * Retorna o endereço físico do servidor.
	 * 
	 * @throws IOException
	 * 
	 */
	public InetAddress getEnderecoServidor() throws IOException {

		if (enderecoServidor == null) {
			Enumeration<NetworkInterface> interfaces = NetworkInterface
					.getNetworkInterfaces();

			while (interfaces.hasMoreElements()) {
				NetworkInterface interfaceRede = (NetworkInterface) interfaces
						.nextElement();
				if (interfaceRede.isUp()) {
					Enumeration<InetAddress> enderecosRede = interfaceRede
							.getInetAddresses();
					while (enderecosRede.hasMoreElements()) {
						InetAddress enderecoRede = (InetAddress) enderecosRede
								.nextElement();
						if (enderecoRede.isReachable(10)
								&& !enderecoRede.isLoopbackAddress()
								&& !enderecoRede.isAnyLocalAddress()) {
							enderecoServidor = enderecoRede;
							return enderecoServidor;
						}
					}
				}
			}
		}
		return enderecoServidor;
	}

	public boolean disponibilidadeId(String id) {

		Query query = entityManager
				.createQuery("SELECT u FROM Usuario u WHERE u.id = :id");
		query.setParameter("id", id);

		try {
			query.getSingleResult();
			return false;
		} catch (NoResultException e) {
			return true;
		} catch (Exception e){
			return false;
		}
	}

	public boolean disponibilidadeNomeInstituicao(String Nome) {

		Query query = entityManager
				.createQuery("SELECT u FROM Instituicao u WHERE u.nome = :nome");
		query.setParameter("nome", Nome);

		try {
			query.getSingleResult();
			return false;
		} catch (NoResultException e) {
			return true;
		} catch (Exception e){
			return false;
		}
	}
	public boolean disponibilidadeEmail(String email) {

		Query query = entityManager
				.createQuery("SELECT u FROM Usuario u WHERE u.email = :email");
		query.setParameter("email", email);

		try {
			query.getSingleResult();
			return false;
		} catch (NoResultException e) {

		}
		return true;
	}

	public void enviarEmail(String destinatario, String assunto, String mensagem) throws MessagingException{
		Message message = new MimeMessage(this.mailSession);
		message.setFrom();
		message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(destinatario, false));
		message.setSubject(assunto);
		Date timeStamp = new Date();
		message.setText(mensagem);
		message.setHeader("X-Mailer", "Memoria virtual mailer");
		message.setSentDate(timeStamp);
		// Enviar mensagem
		Transport.send(message);
	}

}
