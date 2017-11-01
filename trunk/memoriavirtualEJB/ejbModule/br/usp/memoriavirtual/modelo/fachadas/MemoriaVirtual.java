package br.usp.memoriavirtual.modelo.fachadas;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.Acesso;
import br.usp.memoriavirtual.modelo.entidades.Aprovacao;
import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;
import br.usp.memoriavirtual.utils.MVModeloMapeamentoUrl;

@Singleton(mappedName = "MemoriaVirtual")
public class MemoriaVirtual implements MemoriaVirtualRemote {

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;

	@Resource(name = "mail/memoriavirtual")
	private javax.mail.Session mailSession;

	public MemoriaVirtual() {

	}

	public Integer getTamanhoPagina() {
		Context context = null;
		try {
			context = new InitialContext();
		} catch (NamingException e) {
			new ModeloException(e);
			return tamanhoPaginaDefault;
		}
		Properties propriedades = null;
		try {
			propriedades = (Properties) context.lookup("memoriavirtual.propriedades");
		} catch (NamingException e) {
			return tamanhoPaginaDefault;
		}
		if (propriedades.get("searchResultPerPage") == null)
			return tamanhoPaginaDefault;

		Integer result = new Integer((String) propriedades.get("searchResultPerPage"));
		return result;
	}

	public String getURLServidor() throws ModeloException {

		Context context = null;
		try {
			context = new InitialContext();
		} catch (NamingException e) {
			new ModeloException(e);
			return null;
		}
		Properties propriedades = null;
		try {
			propriedades = (Properties) context.lookup("memoriavirtual.propriedades");
		} catch (NamingException e) {
			new ModeloException(e);
			return null;
		}
		return (String) propriedades.get("hostname");
	}

	public String getCaptchaPublicKey() throws ModeloException {

		Context context = null;
		try {
			context = new InitialContext();
		} catch (NamingException e) {
			new ModeloException(e);
			return null;
		}
		Properties propriedades = null;
		try {
			propriedades = (Properties) context.lookup("memoriavirtual.propriedades");
		} catch (NamingException e) {
			new ModeloException(e);
			return null;
		}
		return (String) propriedades.get("publicKey");
	}

	public String getCaptchaPrivateKey() throws ModeloException {

		Context context = null;
		try {
			context = new InitialContext();
		} catch (NamingException e) {
			new ModeloException(e);
			return null;
		}
		Properties propriedades = null;
		try {
			propriedades = (Properties) context.lookup("memoriavirtual.propriedades");
		} catch (NamingException e) {
			new ModeloException(e);
			return null;
		}
		return (String) propriedades.get("privateKey");
	}

	public String getIntervaloTimer() throws ModeloException {

		Context context = null;
		try {
			context = new InitialContext();
		} catch (NamingException e) {
			new ModeloException(e);
			return null;
		}
		Properties propriedades = null;
		try {
			propriedades = (Properties) context.lookup("memoriavirtual.propriedades");
		} catch (NamingException e) {
			new ModeloException(e);
			return null;
		}
		return (String) propriedades.get("timer");

	}

	public boolean verificarDisponibilidadeIdUsuario(String id) {

		try {
			Query query = entityManager.createQuery("SELECT u FROM Usuario u WHERE u.identificacao = :id");
			query.setParameter("id", id);

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

	public boolean verificarDisponibilidadeEmail(String email, Usuario usuario) throws ModeloException {

		try {
			Query query;
			if (usuario != null) {
				query = entityManager.createQuery("SELECT u FROM Usuario u WHERE u.email = :email AND u <> :usuario");
				query.setParameter("email", email);
				query.setParameter("usuario", usuario);
			} else {
				query = entityManager.createQuery("SELECT u FROM Usuario u WHERE u.email = :email");
				query.setParameter("email", email);
			}

			Usuario usuarioMemoria = (Usuario) query.getSingleResult();

			return (usuarioMemoria == null);
		} catch (NoResultException e) {
			return true;
		} catch (Exception e) {
			throw new ModeloException(e);
		}
	}

	public void enviarEmail(String destinatario, String assunto, String mensagem) throws MessagingException {
		Message message = new MimeMessage(this.mailSession);
		message.setFrom();
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario, false));
		message.setSubject(assunto);
		Date timeStamp = new Date();

		Multipart multipart = new MimeMultipart("alternative");

		MimeBodyPart htmlPart = new MimeBodyPart();
		htmlPart.setContent(mensagem, "text/html");
		
		multipart.addBodyPart(htmlPart);
		message.setContent(multipart);
		message.setHeader("X-Mailer", "Memoria virtual mailer");
		message.setSentDate(timeStamp);
		
		// Enviar mensagem
		Transport.send(message);
	}

	public String embaralhar(String mensagemOriginal) {

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

	@Override
	@SuppressWarnings("unchecked")
	public List<Usuario> listarUsuarios(String busca, Usuario usuario) throws ModeloException {

		List<Usuario> usuarios = new ArrayList<Usuario>();

		if (busca == null) {
			return usuarios;
		}

		try {

			Query query;
			if (usuario.isAdministrador()) {
				query = this.entityManager.createQuery("SELECT u FROM Usuario u " + "WHERE u.ativo = TRUE "
						+ "AND LOWER(u.nomeCompleto) LIKE LOWER(:padrao) AND u.id <> :id " + "ORDER BY u.nomeCompleto");
				query.setParameter("padrao", "%" + busca + "%");
				query.setParameter("id", usuario.getId());
			} else {
				Grupo grupo = new Grupo("GERENTE");
				query = entityManager.createQuery(
						"SELECT b.usuario FROM Acesso a, Acesso b WHERE a.usuario = :usuario AND a.grupo = :grupo AND a.instituicao = b.instituicao AND LOWER(b.usuario.nomeCompleto) LIKE LOWER(:padrao) AND b.usuario <> :usuario ORDER BY b.usuario.nomeCompleto");
				query.setParameter("usuario", usuario);
				query.setParameter("grupo", grupo);
				query.setParameter("padrao", "%" + busca + "%");
			}

			return (List<Usuario>) query.getResultList();
		} catch (Exception e) {
			throw new ModeloException(e);
		}
	}

	@SuppressWarnings("rawtypes")
	public List<Acesso> listarAcessos(Usuario usuario) {

		Query query;

		query = this.entityManager.createNamedQuery("acessos", Acesso.class);
		query.setParameter("usuario", usuario);

		List lista = (List) query.getResultList();

		List<Acesso> listaAcessos = new ArrayList<Acesso>();

		for (Object o : lista) {
			listaAcessos.add((Acesso) o);
		}

		return listaAcessos;
	}

	@Override
	public Usuario getUsuario(String id) throws ModeloException {
		try {
			return (Usuario) entityManager.find(Usuario.class, new Long(id).longValue());
		} catch (Exception e) {
			throw new ModeloException(e);
		}
	}

	@Override
	public String getUrl(MVModeloMapeamentoUrl url, Map<String, String> mapaParametros) throws ModeloException {
		try {
			String parametros = "?";

			boolean primeiro = true;
			Iterator<Entry<String, String>> it = mapaParametros.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, String> par = (Map.Entry<String, String>) it.next();

				if (primeiro) {
					parametros = parametros + par.getKey() + "=" + par.getValue();
					primeiro = false;
				} else {
					parametros = "&" + parametros + par.getKey() + "=" + par.getValue();
				}
			}

			return this.getURLServidor() + url.toString() + parametros;
		} catch (Exception e) {
			throw new ModeloException(e);
		}
	}

	@Override
	public Aprovacao getAprovacao(Long id) throws ModeloException {
		try {
			return entityManager.find(Aprovacao.class, id);
		} catch (Exception e) {
			throw new ModeloException(e);
		}
	}

}
