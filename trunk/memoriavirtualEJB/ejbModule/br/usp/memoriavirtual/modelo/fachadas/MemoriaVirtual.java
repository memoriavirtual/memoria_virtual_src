package br.usp.memoriavirtual.modelo.fachadas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.Acesso;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;

/**
 * EJB Sem estado e singleton que cont�m m�todos comuns ao sistema todo.
 */
@Singleton(mappedName = "MemoriaVirtual")
public class MemoriaVirtual implements MemoriaVirtualRemote {

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
	 * Retorna o endereco do servidor.
	 * 
	 */
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
			propriedades = (Properties) context
					.lookup("memoriavirtual.propriedades");
		} catch (NamingException e) {
			new ModeloException(e);
			return null;
		}
		return (String) propriedades.get("hostname");
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
			propriedades = (Properties) context
					.lookup("memoriavirtual.propriedades");
		} catch (NamingException e) {
			new ModeloException(e);
			return null;
		}
		return (String) propriedades.get("timer");

	}

	public boolean verificarDisponibilidadeIdUsuario(String id) {

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

	public boolean verificarDisponibilidadeNomeInstituicao(String Nome) {

		Query query = entityManager
				.createQuery("SELECT u FROM Instituicao u WHERE u.nome = :nome");
		query.setParameter("nome", Nome);

		try {
			query.getSingleResult();
			System.out.println("resultado");
			return false;
		} catch (NoResultException e) {
			System.out.println("noresult");
			return true;
		} catch (Exception e) {
			System.out.println("outro");
			return false;
		}
	}

	public boolean verificarDisponibilidadeEmail(String email) {

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

	/**
	 * Metodo para embaralhar a validade e email do caso de uso Enviar Convite
	 * 
	 * @param mensagemOriginal
	 * @return
	 */
	public String embaralhar(String mensagemOriginal) {

		String msgNova = "";

		if (mensagemOriginal.length() % 2 != 0) {
			for (int i = 0; i < mensagemOriginal.length(); i++) {
				if ((i + 1) % 2 != 0) {
					msgNova = msgNova.concat(""
							+ mensagemOriginal.charAt(mensagemOriginal.length()
									- 1 - i));
				} else {
					msgNova = msgNova.concat("" + mensagemOriginal.charAt(i));
				}
			}
		} else {
			int aux = 0;
			for (int i = 0; i < mensagemOriginal.length(); i++) {
				if (i + 1 != mensagemOriginal.length() - 1 - i) {
					if ((i + 1 - aux) % 2 != 0) {
						msgNova = msgNova.concat(""
								+ mensagemOriginal.charAt(mensagemOriginal
										.length() - 1 - i));
					} else {
						msgNova = msgNova.concat(""
								+ mensagemOriginal.charAt(i));
					}
				} else {
					if (mensagemOriginal.length() % 4 == 0) {
						msgNova = msgNova.concat(""
								+ mensagemOriginal.charAt(i));
						msgNova = msgNova.concat(""
								+ mensagemOriginal.charAt(i + 1));
						aux++;
						i++;
					} else {
						msgNova = msgNova.concat(""
								+ mensagemOriginal.charAt(i + 1));
						msgNova = msgNova.concat(""
								+ mensagemOriginal.charAt(i));
						aux++;
						i++;
					}
				}
			}
		}
		return msgNova;
	}



	/**
	 * Método para sugestão de usuários retorna todos os usuários do banco cujo
	 * nome contém o padrão
	 * 
	 * @param pnome
	 *            String a ser comparada com os nomes
	 * @return Lista de usuários
	 * @throws ModeloException
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Usuario> listarUsuarios(String pnome) throws ModeloException {

		// Lista de usuarios a ser retornada
		List<Usuario> usuarios = new ArrayList<Usuario>();
		// Query reposavel pela busca no banco
		Query query = null;

		// Só busca se o nome não for nulo
		if (!pnome.equals(null)) {

			// query busca todos os usuarios ativos, não administradores
			// cujo nome contém a string fornecida em ordem alfabética
			query = this.entityManager
					.createQuery("SELECT u FROM Usuario u WHERE u.ativo = TRUE "
							+ "AND u.administrador = FALSE "
							+ "AND u.nomeCompleto LIKE :padrao "
							+ "ORDER BY u.nomeCompleto");
			query.setParameter("padrao", "%" + pnome + "%");
		}
		try {

			// tenta pegar a lista de resultados
			usuarios = (List<Usuario>) query.getResultList();

			// Cria um usuario para a opcao de listar todos os usuários
			Usuario opcaoListarTodos = new Usuario();
			opcaoListarTodos.setNomeCompleto("Listar Todos");
			opcaoListarTodos.setId("listartodos");
			usuarios.add(0, opcaoListarTodos);

		} catch (Exception e) {
			// em caso de erro, joga uma exceção
			throw new ModeloException(e);
		}
		return usuarios;
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
	
}
