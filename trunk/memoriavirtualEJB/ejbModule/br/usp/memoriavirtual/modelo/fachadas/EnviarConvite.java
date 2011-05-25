package br.usp.memoriavirtual.modelo.fachadas;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.management.timer.Timer;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.Acesso;
import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EnviarConviteRemote;

@Stateless(mappedName = "EnviarConvite")
public class EnviarConvite implements EnviarConviteRemote {

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;
	@Resource(name = "mail/memoriavirtual")
	private javax.mail.Session mailSession;
	private Usuario usuario;
	private Instituicao instituicao;


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
		
		erro = validacaoEmail(email);
		if(erro != null){
			return "Formato de email invalido: " + erro;
		}

		for (Integer i = 0; i < email.length; i++) {
			erro = cadastrarUsuarioBD(email[i], validade, instituicao,
					nivelAcesso);
			if (erro.equals("falhaBD"))
				return "Erro desconhecido ao cadastrar no banco de dados: "
						+ email[i]; // Se ouve alguma excessao nao tratada ao
									// inserir no banco
			else if (erro.equals("falhaEmail"))
				return "Formato de email invalido: " + email[i];
			
			//Enviar o email
			
			else if(erro.equals("sucesso")){
				DateFormat formatoData =  new SimpleDateFormat("dd/MM/yy"); 
				String assunto =  "Convite para o Memoria Virtual";
				String textoEmail = "Voc� foi convidado(a) para participar do memoria virtual como "+ nivelAcesso
				+ "na instituicao: " + this.instituicao.getNome() + ". Para concluir seu cadastro entre no link a seguir: .../fazerCadastro.jsf?Validacao="
					+this.usuario.getId()+"&email="+this.usuario.getEmail() + ".... Seu convite � valido ate " + formatoData.format(this.usuario.getValidade()) + " ... Voce recebeu a seguinte mensagem: " + mensagem; 
				
					enviarEmail(email[i], assunto, textoEmail);
			}
		}

		return "sucesso";
	}
	
	/**
	 * Verifica se emails sao validos.
	 * 
	 * @return <code>email</code> O primeiro email invalido.
	 * @return <code>null</code> nenhum email invalido.
	 */
	
	public String validacaoEmail(String[] emails){
		for (Integer i = 0; i < emails.length; i++) {
			String regexp="[a-z0-9!#$%&’*+/=?^_‘{|}~-]+(?:\\."
				+"[a-z0-9!#$%&’*+/=?^_‘{|}~-]+)*@"
				+"(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
			Pattern pattern = Pattern.compile(regexp);
			Matcher matcher = pattern.matcher(emails[i]);
			//Verifica se email esta de acorda com a expressao regular e se nao tem 2 email juntos
			if(!matcher.matches())
				//if(matcher.matches())
					return emails[i];
		}
		return null;
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
	 * @param email
	 *            O email do usuario a ser inserido no banco
	 * @param validade
	 *            A validade do convite
	 * @param instituicaoId
	 *            O id da institui�ao a ser vinculada com o usuario
	 * @param nivelAcesso
	 *            O nivel de acesso do usuario na institui�ao
	 * 
	 * @return falhaEmail Se formato do email esta incorreto
	 * 
	 * @return falhaBD Se ocorreu algum erro ao incluir no banco de dados
	 * 
	 * @return sucesso Se o usuario foi incluido no banco de dados com sucesso
	 */
	
	private String cadastrarUsuarioBD(String email, String validade,
			String instituicaoId, String nivelAcesso) {

		Boolean usuarioCadastrado = false;
		Date dataAtual = new Date();
		Date vencimento = new Date(dataAtual.getTime()
				+ (long) Integer.parseInt(validade) * Timer.ONE_DAY);
		String id;
		this.usuario = new Usuario();
		Random generator = new Random(); // Usado para gerar o hash do convite

		// Gera o id do usuario calculando o hash com base no email + um numero
		// aleatorio.
		// Se ja existir o id, calcula outro numero aleatorio ate que o id seja
		// unico
		// id será usado para verificação do convite enviado via email
		do {
			id = this.usuario.gerarHash(email
					+ Integer.toString(generator.nextInt()));
		} while (entityManager.find(Usuario.class, id) != null);
		
		this.usuario.setId(id);
		this.usuario.setValidade(vencimento);
		this.usuario.setEmail(email);
		this.usuario.setAtivo(false);
		
		if (nivelAcesso.toUpperCase().equals("ADMINISTRADOR"))
			this.usuario.setAdministrador(true);
		else
			this.usuario.setAdministrador(false);
		
		if (!this.entityManager.contains(this.usuario)) {
			// Persiste usuario na base
			try {
				this.entityManager.persist(this.usuario);
			} catch (Exception e) {
				return "falhaEmail"; // Na hora de persisitir o usuario, se o
				// email estiver errado � gerada uma
				// exce�ao
			}
			if (this.entityManager.contains(this.usuario)) {
				usuarioCadastrado = true;
			}
			//Se nao foi convidado para ser Administrador, inclui em um grupo
			if (!nivelAcesso.toUpperCase().equals("ADMINISTRADOR")) {
				Acesso acesso = new Acesso();
				Grupo grupo = null;

				this.instituicao = (Instituicao) entityManager.find(Instituicao.class,
						instituicaoId);
				if (this.instituicao == null)
					return "falhaBD";

				grupo = (Grupo) entityManager.find(Grupo.class,
						nivelAcesso);
				if (grupo == null)
					return "falhaBD";
				
				acesso.setInstituicao(this.instituicao);
				acesso.setUsuario(this.usuario);
				acesso.setGrupo(grupo);
				try {
					this.entityManager.persist(acesso);
				} catch (Exception e) {
					return "falhaBD";
				}
				if (!this.entityManager.contains(acesso)) {
					usuarioCadastrado = false;
				}
			}

		}
		return (usuarioCadastrado) ? "sucesso" : "falhaBD";
	}

	/**
	 * @param grupo
	 *            Um grupo cadastrado no banco de dados
	 * 
	 * @param usuario
	 * 
	 * @return List<Instituicao> Institui��es que o usuario faz parte e pertence
	 *         ao grupo passado como parametro
	 */

	public List<Instituicao> getInstituicoes(Grupo grupo, Usuario usuario) {
		List<Instituicao> instituicoes = new ArrayList<Instituicao>();
		Query query = this.entityManager
				.createQuery("select a from Acesso a where a.usuario = :usuario and a.grupo = :grupo");
		query.setParameter("usuario", usuario);
		query.setParameter("grupo", grupo);

		@SuppressWarnings("unchecked")
		List<Acesso> acessos = (List<Acesso>) query.getResultList();
		for (Acesso acesso : acessos) {
			instituicoes.add(acesso.getInstituicao());
		}

		return instituicoes;
	}

	/**
	 * @return List<Grupo> Retorna todos os grupos
	 */
	@SuppressWarnings("unchecked")
	public List<Grupo> getGrupos() {
		List<Grupo> grupos = null;
		Query query = this.entityManager.createQuery("Select g from Grupo g");
		grupos = (List<Grupo>) query.getResultList();
		return grupos;
	}

	/**
	 * 
	 * @return List<Instituicao> Todas instituicoes
	 */

	@SuppressWarnings("unchecked")
	public List<Instituicao> getInstituicoes() {
		List<Instituicao> instituicoes = null;
		Query query = this.entityManager
				.createQuery("select i from Instituicao i");
		instituicoes = (List<Instituicao>) query.getResultList();

		return instituicoes;
	}

	/**
	 * Envia emails
	 * 
	 * @param destinatario
	 *            o email do destinatario
	 * 
	 * @param assunto
	 *            O assunto do email
	 * 
	 * @param mensagem
	 *            O texto do corpo do email
	 * 
	 */

	public void enviarEmail(String destinatario, String assunto, String mensagem) {
		try {
			Message message = new MimeMessage(mailSession);
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