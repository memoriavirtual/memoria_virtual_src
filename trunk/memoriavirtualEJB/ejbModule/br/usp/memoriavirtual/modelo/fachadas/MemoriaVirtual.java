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

import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;

/**
 * EJB Sem estado e singleton que contém métodos comuns ao sistema todo.
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
	 * Retorna o endereço físico do servidor.
	 * 
	 * @throws IOException
	 * 
	 */
	public InetAddress getEnderecoServidor() throws IOException {

		if (enderecoServidor == null) {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

			while (interfaces.hasMoreElements()) {
				NetworkInterface interfaceRede = (NetworkInterface) interfaces.nextElement();
				if (interfaceRede.isUp()) {
					Enumeration<InetAddress> enderecosRede = interfaceRede.getInetAddresses();
					while (enderecosRede.hasMoreElements()) {
						InetAddress enderecoRede = (InetAddress) enderecosRede.nextElement();
						if (enderecoRede.isReachable(10) && !enderecoRede.isLoopbackAddress()
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

	public boolean verificarDisponibilidadeIdUsuario(String id) {

		Query query = entityManager.createQuery("SELECT u FROM Usuario u WHERE u.id = :id");
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

	public boolean verificarDisponibilidadeNomeInstituicao(String Nome) {

		Query query = entityManager.createQuery("SELECT u FROM Instituicao u WHERE u.nome = :nome");
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

	public boolean verificarDisponibilidadeEmail(String email) {

		Query query = entityManager.createQuery("SELECT u FROM Usuario u WHERE u.email = :email");
		query.setParameter("email", email);

		try {
			query.getSingleResult();
			return false;
		} catch (NoResultException e) {

		}
		return true;
	}

	public void enviarEmail(String destinatario, String assunto, String mensagem) throws MessagingException {
		Message message = new MimeMessage(this.mailSession);
		message.setFrom();
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario, false));
		message.setSubject(assunto);
		Date timeStamp = new Date();
		message.setText(mensagem);
		message.setHeader("X-Mailer", "Memoria virtual mailer");
		message.setSentDate(timeStamp);
		// Enviar mensagem
		Transport.send(message);
	}

	/**
	 * Método para embaralhar a validade e email do caso de uso Enviar Convite
	 * 
	 * @param mensagemOriginal
	 * @return
	 */
	public static String embaralhar(String mensagemOriginal) {

		String msgNova = "";

		if (mensagemOriginal.length() % 2 != 0) {
			for (int i = 0; i < mensagemOriginal.length(); i++) {
				if ((i + 1) % 2 != 0) {
					msgNova = msgNova.concat("" + mensagemOriginal.charAt(mensagemOriginal.length() - 1 - i));
				} else {
					msgNova = msgNova.concat("" + mensagemOriginal.charAt(i));
				}
			}
		} else {
			int aux = 0;
			for (int i = 0; i < mensagemOriginal.length(); i++) {
				if (i + 1 != mensagemOriginal.length() - 1 - i) {
					if ((i + 1 - aux) % 2 != 0) {
						msgNova = msgNova.concat("" + mensagemOriginal.charAt(mensagemOriginal.length() - 1 - i));
					} else {
						msgNova = msgNova.concat("" + mensagemOriginal.charAt(i));
					}
				} else {
					if (mensagemOriginal.length() % 4 == 0) {
						msgNova = msgNova.concat("" + mensagemOriginal.charAt(i));
						msgNova = msgNova.concat("" + mensagemOriginal.charAt(i + 1));
						aux++;
						i++;
					} else {
						msgNova = msgNova.concat("" + mensagemOriginal.charAt(i + 1));
						msgNova = msgNova.concat("" + mensagemOriginal.charAt(i));
						aux++;
						i++;
					}
				}
			}
		}
		return msgNova;
	}

	/**
	 * listarInstituicoes(pnome, grupo, usuario)
	 * @param String pnome todo ou parte do nome da instituicao a ser procurada
	 * @param Grupo grupo Grupo do usuario que faz o pedido
	 * @param Usuario usuario usuario que faz o pedido
	 * @return List<Instituicao>  Lista de instituicoes cujo nome comeca com pnome, e podem ser acessadas pelo 
	 */
	@SuppressWarnings("unchecked")
	public List<Instituicao> listarInstituicoes(String pnome,
			Grupo grupo, Usuario usuario) {
		List<Instituicao> ins = new ArrayList<Instituicao>();
		Query query;

		query = entityManager
		.createQuery("SELECT a FROM Acesso a WHERE a.grupo =:grupo AND a.usuario =:usuario AND a.instituicao like nome% ");
		query.setParameter("nome", pnome);
		query.setParameter("grupo", grupo);
		query.setParameter("usuario", usuario);
		try {
			ins = (List<Instituicao>) query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ins;
	}

	/**
	 * Metodo de sugestoes para instituicao
	 * 
	 * @param pnome
	 *            String para a qual o sistema gera sugestoes
	 * @return Lista de Instituicoes que comecam com a String de parametro
	 */
	@SuppressWarnings("unchecked")
	public List<Instituicao> listarInstituicoes(String pnome) {
		List<Instituicao> instituicoes = new ArrayList<Instituicao>();
		Query query;
		if(!(pnome =="")){
			query = entityManager.createQuery("SELECT a FROM Instituicao a  WHERE a.nome LIKE :padrao ORDER BY a.nome");

			query.setParameter("padrao", "%" + pnome + "%");
			try {
				instituicoes = (List<Instituicao>) query.getResultList();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return instituicoes;
	}

}
