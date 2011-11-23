package br.usp.memoriavirtual.modelo.fachadas;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

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
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;

/**
 * EJB Sem estado e singleton que cont√©m m√©todos comuns ao sistema todo.
 */
@Singleton(mappedName = "MemoriaVirtual")
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
	 * Retorna o endere√ßo f√≠sico do servidor.
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
		} catch (Exception e) {
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
		} catch (Exception e) {
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

	public void enviarEmail(String destinatario, String assunto, String mensagem)
			throws MessagingException {
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

	/* MÔøΩtodo para embaralhar a validade e email do caso de uso Enviar Convite */
	public static String embaralhar(String msgOriginal) {

		String msgNova = "";

		if (msgOriginal.length() % 2 != 0) {
			for (int i = 0; i < msgOriginal.length(); i++) {
				if ((i + 1) % 2 != 0) {
					msgNova = msgNova.concat(""
							+ msgOriginal.charAt(msgOriginal.length() - 1 - i));
				} else {
					msgNova = msgNova.concat("" + msgOriginal.charAt(i));
				}
			}
		} else {
			int aux = 0;
			for (int i = 0; i < msgOriginal.length(); i++) {
				if (i + 1 != msgOriginal.length() - 1 - i) {
					if ((i + 1 - aux) % 2 != 0) {
						msgNova = msgNova.concat(""
								+ msgOriginal.charAt(msgOriginal.length() - 1
										- i));
					} else {
						msgNova = msgNova.concat("" + msgOriginal.charAt(i));
					}
				} else {
					if (msgOriginal.length() % 4 == 0) {
						msgNova = msgNova.concat("" + msgOriginal.charAt(i));
						msgNova = msgNova
								.concat("" + msgOriginal.charAt(i + 1));
						aux++;
						i++;
					} else {
						msgNova = msgNova
								.concat("" + msgOriginal.charAt(i + 1));
						msgNova = msgNova.concat("" + msgOriginal.charAt(i));
						aux++;
						i++;
					}
				}
			}
		}
		return msgNova;
	}

	

	/**
	 * MÈtodo de sugestıes para instituiÁ„o
	 * 
	 * @param pnome String para a qual o sistema gera sugestıes
	 * @return Lista de InstituiÁıes que comeÁam com a String de par‚metro
	 */
	@SuppressWarnings("unchecked")
	public List<Instituicao> listarInstuicoes(String pnome) {
		List<Instituicao> ins = new ArrayList<Instituicao>();
		Query query;

		query = entityManager
				.createQuery("SELECT a FROM Instituicao a WHERE a.nome LIKE padrao ORDER BY a.nome ");

		query.setParameter("padrao", pnome + "%");
		try {
			ins = (List<Instituicao>) query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ins;
	}

}
