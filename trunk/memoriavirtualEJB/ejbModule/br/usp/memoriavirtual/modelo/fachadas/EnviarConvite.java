package br.usp.memoriavirtual.modelo.fachadas;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.management.timer.Timer;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.Acesso;
import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EnviarConviteRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;

@Stateless(mappedName = "EnviarConvite")
public class EnviarConvite implements EnviarConviteRemote {

	@EJB
	private MemoriaVirtualRemote memoriaVirtual;
	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;
	@Resource(name = "mail/memoriavirtual")
	private javax.mail.Session mailSession;

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
	public void enviarConvite(List<String> emails, String mensagem,
			String validade, String instituicao, String nivelAcesso)
			throws ModeloException {

		/* Crio um Date que guarda a data de vencimento do convite. */
		Date dataAtual = new Date();
		Date vencimento = new Date(dataAtual.getTime()
				+ (long) Integer.parseInt(validade) * Timer.ONE_DAY);

		/* Para cada email na lista cadastramos no banco e enviamos o email. */
		for (String mail : emails) {

			/* String para guardar o ID a ser gerado. */
			String id;
			Usuario user = new Usuario();
			/*
			 * Gerador de numeros random para adicionar ao email e gerar um hash
			 * unico no banco.
			 */
			Random generator = new Random();

			/*
			 * Gera o ID do convite calculando o hash com base no email + um
			 * numero aleatório. Se o ID já existir geramos outro numero
			 * aleatório para calcular o hash.
			 */
			do {
				id = Usuario.gerarHash(mail
						+ Integer.toString(generator.nextInt()));
			} while (entityManager.find(Usuario.class, id) != null);

			/*
			 * Seta os valores do convite e coloca como inativo até o convite
			 * ser cadastrado.
			 */
			user.setId(id);
			user.setValidade(vencimento);
			user.setEmail(mail);
			user.setAtivo(false);

			try {
				/* Classe que formata o Date para exibir na mensagem do convite. */
				DateFormat formatoData = new SimpleDateFormat("dd/MM/yy");
				String assunto = "Convite para o Memória Virtual";

				/* Agora formamos a mensagem de corpo do email que será enviado. */
				String textoEmail = "Você foi convidado(a) para participar do memoria virtual como "
						+ nivelAcesso.toLowerCase();
				if (!nivelAcesso.equalsIgnoreCase("Administrador"))
					textoEmail = textoEmail + "na instituição: " + instituicao;
				textoEmail = textoEmail
						+ ".\n Para concluir seu cadastro acesse o link a seguir :"
						+ memoriaVirtual.getURLServidor()
						+ "/fazercadastro?Validacao="
						+ memoriaVirtual.embaralhar(user.getId()) + "&email="
						+ memoriaVirtual.embaralhar(user.getEmail());
				textoEmail = textoEmail + ".\n Seu convite é valido ate "
						+ formatoData.format(user.getValidade());
				if (!mensagem.equals(""))
					textoEmail = textoEmail
							+ ".\n Voce recebeu a seguinte mensagem junto ao convite: "
							+ mensagem;

				/* Envia o email com a mensagem e cadastra no banco de dados. */
				enviarEmail(mail, assunto, textoEmail);
				cadastrarUsuarioBD(user, instituicao, nivelAcesso);
			} catch (ModeloException e) {
				throw e;
			}
		}
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
	 * @throws ModeloException
	 */

	private void cadastrarUsuarioBD(Usuario usuario, String instituicaoNome,
			String nivelAcesso) throws ModeloException {

		/* Verifica se é um novo administrador. */
		if (nivelAcesso.equalsIgnoreCase("ADMINISTRADOR"))
			usuario.setAdministrador(true);
		else
			usuario.setAdministrador(false);

		/* Persiste o novo usuario no banco de dados. */
		try {
			this.entityManager.persist(usuario);
		} catch (Exception e) {
			throw new ModeloException(
					"Erro ao persistir o novo usuário no banco de dados.", e);
		}

		/*
		 * Se não for um administrador devemos inserir um novo acesso no banco
		 * de dados.
		 */
		if (!nivelAcesso.equalsIgnoreCase("ADMINISTRADOR")) {
			Acesso acesso = new Acesso();
			Grupo grupo = null;

			/* Recupera-se a instituição ao qual o acesso pertence. */
			Query query1 = this.entityManager
					.createQuery("select i from Instituicao i where i.nome = :nome");
			query1.setParameter("nome", instituicaoNome);

			@SuppressWarnings("unchecked")
			List<Instituicao> instList = (List<Instituicao>) query1
					.getResultList();

			Instituicao instituicao = null;

			if (!instList.isEmpty())
				instituicao = instList.get(0);
			else {
				this.entityManager.remove(usuario);
				throw new ModeloException(
						"Instituicao selecionada nao existe no banco de dados.",
						null);
			}

			/* Recupera o grupo baseado no nivel de acesso inserido. */
			grupo = (Grupo) entityManager.find(Grupo.class,
					nivelAcesso.toUpperCase());
			if (grupo == null) {

				this.entityManager.remove(usuario);
				throw new ModeloException("Grupo nao encontrado", null);
			}
			acesso.setInstituicao(instituicao);
			acesso.setUsuario(usuario);
			acesso.setGrupo(grupo);

			/* Persiste o novo acesso no banco. */
			try {
				this.entityManager.persist(acesso);
			} catch (Exception e) {
				this.entityManager.remove(usuario);
				throw new ModeloException(
						"Falha ao inserir o novo acesso no banco de dados.", e);
			}

		}

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

	@SuppressWarnings("unchecked")
	public List<Instituicao> getInstituicoesPermitidas(Usuario usuario,
			Grupo grupo) {

		/* Lista com as instituições que será retornada. */
		List<Instituicao> instituicoes = null;

		Query query;

		/*
		 * Se ambos os parametros forem diferentes de null a pesquisa é
		 * realizada baseada na tabela de acessos do banco de dados.
		 */
		if (usuario != null && grupo != null) {
			/*
			 * Recupera os acessos que o usuário possui como o grupo de acesso
			 * passado por parametro.
			 */
			query = this.entityManager
					.createQuery("select a from Acesso a where a.usuario = :usuario and a.grupo = :grupo");
			query.setParameter("usuario", usuario);
			query.setParameter("grupo", grupo);

			List<Acesso> acessos = (List<Acesso>) query.getResultList();

			/* Recupera as instituições utilizando a lista de acessos. */
			instituicoes = new ArrayList<Instituicao>();
			for (Acesso acesso : acessos) {
				instituicoes.add(acesso.getInstituicao());
			}

		}
		/*
		 * Se o grupo for null e o usuario for um administrador é retornado
		 * todas as instituições do banco de dados
		 */
		else if (usuario != null && usuario.isAdministrador()) {
			query = this.entityManager
					.createQuery("select i from Instituicao i");

			instituicoes = (List<Instituicao>) query.getResultList();
		}

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

	public void enviarEmail(String destinatario, String assunto, String mensagem)
			throws ModeloException {

		Message message = new MimeMessage(mailSession);

		try {
			/* Preenche os dados para enviar a mensagem(email). */
			message.setFrom();
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(destinatario, false));
			message.setSubject(assunto);
			Date timeStamp = new Date();
			message.setText(mensagem);
			message.setHeader("X-Mailer", "Memoria virtual mailer");
			message.setSentDate(timeStamp);
			/* Envia o email. */
			Transport.send(message);
		} catch (MessagingException e) {
			throw new ModeloException();
		}
	}
}