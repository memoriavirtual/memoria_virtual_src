package br.usp.memoriavirtual.controle;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.mail.MessagingException;

import br.usp.memoriavirtual.modelo.entidades.Aprovacao;
import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.ItemAuditoria;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fabricas.remoto.AuditoriaFabricaRemote;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarInstituicaoRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirInstituicaoRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;
import br.usp.memoriavirtual.utils.MensagensDeErro;

/**
 * @author MAC
 * 
 */

public class ExcluirInstituicaoMB implements Serializable {

	private static final long serialVersionUID = 1147425267036231710L;

	// objetos ejb
	@EJB
	private ExcluirInstituicaoRemote excluirInstituicaoEJB;
	@EJB
	private MemoriaVirtualRemote memoriaVirtualEJB;

	@EJB
	private EditarInstituicaoRemote editarInstituicaoEJB;

	@EJB
	private AuditoriaFabricaRemote auditoriaFabricaEJB;

	// objetos gerais

	// para construir as mensagens
	private FacesContext context = FacesContext.getCurrentInstance();
	private String bundleName = "mensagens";
	private ResourceBundle bundle = context.getApplication().getResourceBundle(
			context, bundleName);

	// da institui��o a ser exclu�da
	private Instituicao instituicao = null;
	private String nome = null;
	private List<Usuario> gerentesInstituicao = new ArrayList<Usuario>();
	private Usuario gerente = null;

	// do pedido de exclus�o
	private Usuario requisitor = (Usuario) FacesContext.getCurrentInstance()
			.getExternalContext().getSessionMap().get("usuario");
	private String nomeValidador = null;
	private String validade = null;
	private String justificativa = null;
	private Usuario administradorValidador = null;
	private ItemAuditoria itemAuditoria = null;
	private Aprovacao aprovacao = null;

	// da infraestrutura para as paginas
	private String justificativa1 = null;
	private List<Instituicao> instituicoes = new ArrayList<Instituicao>();

	/**
	 * M�todo � invocado a cada evento Ajax KeyUp no campo Nome da
	 * Institui��o do formul�rio, tal campo est� associado ao atributo
	 * this.nome, este atributo � utilizado para fazer uma busca no banco de
	 * dados, que retorna uma lista em ordem alfab�tica, em rela��o ao
	 * nome, de todas as institui��es cujo o nome corresponde em parte com o
	 * atributo this.nome.
	 * 
	 * @param event
	 *            Ajax KeyUp
	 */
	public void listarInstituicoes(AjaxBehaviorEvent event) {
		this.instituicoes.clear();
		if (this.nome != "") {

			List<Instituicao> listaInstituicoes = null;
			if (requisitor.isAdministrador()) {
				try {
					listaInstituicoes = this.editarInstituicaoEJB
							.listarInstituicoes(this.nome);
				} catch (ModeloException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				try {
					listaInstituicoes = this.editarInstituicaoEJB
							.listarInstituicoes(this.nome,
									new Grupo("GERENTE"), requisitor);
				} catch (ModeloException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			this.setInstituicoes(listaInstituicoes);
			if (this.instituicoes.isEmpty()) {
				Instituicao inst = new Instituicao();
				inst.setNome(this.bundle
						.getString("excluirInstituicaoErrolistavazia"));
				this.instituicoes.add(inst);
			}
		} else {
			Instituicao inst = new Instituicao();
			inst.setNome(this.bundle
					.getString("listarTodos"));
			this.instituicoes.add(inst);
		}
		return;
	}

	/**
	 * M�todo � invocado quando � pressionado o bot�o Selecionar
	 * Institui��o, Ele invoca os m�todos de valida��o do campos dos
	 * formul�rios, se estes m�todo considerarem v�lidas as
	 * informa��es nos campos do formul�rio, o m�todo
	 * this.listarGerentes() � invocado, e depois � retornado a String
	 * Instselecionada que ser� interpretada pelo facesConfig
	 * 
	 * @return Se todas as valida��es retornarem true retorna a Sting
	 *         Instselecionada Que � interpretada pelo facesConfig chamando a
	 *         pr�xima p�gina do CDU ExcluirInstituicao
	 */
	public String selecionarInstituicoes() {
		if (this.validateNome() && this.validateValidade()) {
			this.listarGerentes(true);
			return "Instselecionada";
		}
		return null;
	}

	/**
	 * M�todo � invocado quando ocorre um clique uma linha do DataTable que
	 * cont�m a lista de Institui��es cujo nome corresponde em parte ao
	 * nome que est� sendo digitado. A institui��o selecionada � passada
	 * como par�metro, e utilizada para setar os argumentos this.nome com o
	 * nome da institui��o e o this.instituicao com o pr�prio objeto
	 * Institui��o.
	 * 
	 * @param instituicao
	 * 
	 */
	public String selecionarInstituicoes(Instituicao instituicao) {
		if (!instituicao.getNome().equals(
				this.bundle.getString("listarTodos"))) {
			if (!instituicao.getNome().equals(
					this.bundle.getString("excluirInstituicaoErrolistavazia"))) {
				this.setInstituicao(instituicao);
				this.setNome(instituicao.getNome());
				// System.out.println(instituicao.getNome() +
				// instituicao.getId() );
				this.instituicoes.clear();
			}
		} else {
			try {
				if (requisitor.isAdministrador()) {
					this.instituicoes = this.excluirInstituicaoEJB
							.listarTodasInstituicoes();
				} else {
					this.instituicoes = this.excluirInstituicaoEJB
							.listarTodasInstituicoes(new Grupo("GERENTE"),
									requisitor);
				}
			} catch (ModeloException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * M�todo invocado quando uma institui��o � selecionada, seja na
	 * p�gina do CDU ou no servlet, o m�todo faz uma requisi��o no banco
	 * de dados de objetos Acesso relacionados com a institui��o
	 * selecionada, caso exista uma lista de objetos Acesso, ele transforma a
	 * lista em uma lista de objetos Usuario e a atribui a
	 * this.gerentesInstituicao e seta o atributo this.gerente com o primeiro
	 * usu�rio da lista. caso n�o exista uma lista de objetos Acesso
	 * relacionados a Institui��o, um objeto Usuario � criado mas este
	 * serve apenas para mostrar a mensagem na p�gina informando que a
	 * institui��o n�o tem gerentes.
	 * 
	 */
	public void listarGerentes(boolean b) {

		if (this.gerentesInstituicao.isEmpty()) {
			try {
				this.gerentesInstituicao = excluirInstituicaoEJB
						.recuperarGerentesdaInstituicao(this.instituicao, b);
			} catch (ModeloException e) {
				e.printStackTrace();
			}
			if (this.gerentesInstituicao.isEmpty()) {
				Usuario e = new Usuario();
				e.setNomeCompleto(this.bundle
						.getString("excluirInstituicaoSemGerente"));
				this.gerentesInstituicao.add(e);
				this.gerente = null;
			} else {
				this.gerente = this.gerentesInstituicao.get(0);
			}

		}

	}

	/**
	 * M�todo invocado quando ocorre o evento Ajax KeyUp na caixa de texto,
	 * onde o usu�rio preenche a justificativa.Este m�todo apenas invoca o
	 * m�todo this.contarJustificativa
	 * 
	 * @param event
	 */
	public void contagemJustificativa(AjaxBehaviorEvent event) {
		contarJustificativa();
	}

	/**
	 * M�todo faz analisa o tamanho da String exibe mensagens com o n�mero
	 * de caracteres na p�gina do CDU, caso o n�mero de caracteres exceda
	 * 300, a mensagem de erro tamb�m � exibida.
	 * 
	 * @return True caso o n�mero de caracteres da justificativa seja inferior
	 *         a 300 caracteres, do contr�rio retorna falso.
	 */
	public boolean contarJustificativa() {

		if (this.justificativa.length() > 300) {
			MensagensDeErro.getErrorMessage(
					"excluirInstituicaoInstituicaoJustificativa300",
					"validacaoJustificativa");
			this.justificativa = this.justificativa1;
			return false;
		} else {
			this.justificativa1 = this.justificativa;
			FacesContext.getCurrentInstance().addMessage(
					"validacaoJustificativa",
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							this.justificativa.length() + "/300", null));
			return true;
		}

	}

	/**
	 * M�todo trata o pressionar do bot�o Confirmar na p�gina do CDU
	 * 
	 * @return String confirmarexcluir, caso as valida��es dos campos sejam
	 *         verdadeiras
	 */
	public String confirmarexcluirInstituicao() {
		if (this.instituicao != null) {
			if (validateJustificativa()) {
				return "confirmarexcluir";
			}
		} else {
			MensagensDeErro.getErrorMessage("excluirInstituicaoErrolistavazia",
					"resultado");
		}
		return null;
	}

	/**
	 * M�todo � utilizado pela p�gina do CDU para exibir a lista de
	 * administradores e/ou gerentes que est�o apitos a validarem o pedido de
	 * exclus�o. o m�todo tamb�m trabalha para que a lista esteja
	 * lexamente ordenada, para que o usuario da se��o n�o esteja na lista
	 * e para que n�o contenha nomes repetidos
	 * 
	 * @return lista de validadores.
	 */
	public List<SelectItem> recuperarPossiveisAprovadores() {

		Map<String, String> validadoreslista = new HashMap<String, String>();
		List<SelectItem> lista = new ArrayList<SelectItem>();

		try {
			for (Usuario administradores : excluirInstituicaoEJB
					.listarAdministradores()) {// para cada administrador do
												// sistema
				validadoreslista.put(administradores.getId(),
						administradores.getNomeCompleto());
			}
			for (Usuario gerentes : this.gerentesInstituicao) {// para cada
																// gerente da
																// institui��o
				validadoreslista.put(gerentes.getId(),
						gerentes.getNomeCompleto());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		validadoreslista.remove(requisitor.getId());
		List<String> listaTemp = new ArrayList<String>();

		for (String i : validadoreslista.values()) {
			listaTemp.add(i);
		}

		MyStringComparable c = new MyStringComparable();
		Collections.sort(listaTemp, c);

		// carregando lista select itens
		lista.add(new SelectItem(null, bundle
				.getString("excluirInstituicaoSelecioneValidadOr")));
		for (String i : listaTemp) {
			lista.add(new SelectItem(i, i));
		}
		return lista;

	}

	/**
	 * M�todo monta o texto do email, bem como chama o m�todo respons�vel
	 * por enviar o mesmo. Ainda registra um objeto itemAuditoria, registra um
	 * objeto aprova��o e marca a institui��o a ser exclu�da e os
	 * objetos Acesso relacionados a ela com o campo validade = false tamb�m
	 * trabalha com o objeto Date afim de encontrar a data de expira��o que
	 * fara parte do objeto Aprova��o
	 * 
	 * @throws IOException
	 */
	public String enviarPedidoExclusao() throws IOException {
		if (this.instituicao != null) {

			if (booleanValidador()) {

				Date dataValidade = new Date();
				int numeroDieas = Integer.parseInt(this.validade, 10);

				DateFormat formatoData = DateFormat.getDateInstance();
				GregorianCalendar gc = new GregorianCalendar();
				gc.add(GregorianCalendar.HOUR, 24 * numeroDieas);
				dataValidade = gc.getTime();
				String gerente = (this.gerente == null) ? this.bundle
						.getString("excluirInstituicaoSemGerente")
						: this.gerente.getNomeCompleto();

				try {
					this.administradorValidador = this.excluirInstituicaoEJB
							.getUsuario("nomeCompleto", this.nomeValidador);
				} catch (ModeloException e2) {
					e2.printStackTrace();
				}
				// registra um objeto Aprovacao
				Aprovacao aprovacao = this.excluirInstituicaoEJB
						.registrarAprovacao(this.administradorValidador,
								this.instituicao, dataValidade);
				// marca a institui��o a ser exclu�da para que a mesma
				// n�o
				// seja mais utilizada

				// registra a autoria do pedido de exclus�o
				ItemAuditoria i = this.auditoriaFabricaEJB
						.auditarExcluirInstituicao(this.requisitor,
								this.instituicao.getNome(), this.justificativa);

				try {

					this.memoriaVirtualEJB
							.enviarEmail(
									this.administradorValidador.getEmail(),
									bundle.getString("excluirInstituicaoEmailTitulo"),
									bundle.getString("excluirInstituicaoEmailMensagem")
											+ "\n"
											+ "\n"
											+ bundle.getString("excluirInstituicaoNome")
											+ this.instituicao.getNome()
											+ "\n"
											+ bundle.getString("excluirInstituicaoGerentes")
											+ gerente
											+ "\n"
											+ bundle.getString("excluirInstituicaoJustificativa")
											+ this.getJustificativa()
											+ "\n"
											+ bundle.getString("excluirInstituicaoRequisitor")
											+ this.getRequisitor()
													.getNomeCompleto()
											+ "\n"
											+ bundle.getString("excluirInstituicaoValidade")
											+ formatoData.format(dataValidade)
											+ "\n"
											+ bundle.getString("excluirInstituicaoEmilMensagemURL")
											+ "\n"
											+ "\n"
											+ "http://"
											+ memoriaVirtualEJB
													.getURLServidor()
											+ "/excluir?"
											+ "chaveEstrangeira="
											+ aprovacao.getId()
											+ "&auditoria="
											+ i.getId()
											+ "\n\n"
											+ bundle.getString("excluirInstituicaoEmailMensagemFim")
											+ "\n" + "\n");

					// registra a autoria do pedido de exclus�o
					this.auditoriaFabricaEJB.auditarExcluirInstituicao(
							this.requisitor, this.instituicao.getNome(),
							this.justificativa);

					// marca a institui��o a ser exclu�da para que a mesma
					// n�o
					// seja mais utilizada

					this.excluirInstituicaoEJB.marcarInstituicaoExcluida(
							this.instituicao, false, this.gerente != null);

					// mensagem de sucesso
					MensagensDeErro.getSucessMessage(
							"excluirInstituicaoEnviandoEmail", "resultado");
				} catch (MessagingException e) {
					e.printStackTrace();
					this.excluirInstituicaoEJB.excluirAprovacao(aprovacao);
				} catch (ModeloException e) {
					e.printStackTrace();
					e.getCause();
					this.excluirInstituicaoEJB.excluirAprovacao(aprovacao);
				}

			}
			this.zerarManegedBean();
		} else {
			MensagensDeErro.getErrorMessage("excluirInstituicaoErrolistavazia",
					"resultado");
		}
		return null;
	}

	/* M�todos que tratam do Pedido de Exclus�o */

	/**
	 * M�todo trata a��o do bot�o Negar Pedido da P�gina do caso de
	 * uso Nega o pedido, reativa a Institui��o e todas as suas
	 * depend�ncias
	 * 
	 * @return
	 */
	public String negarExclusaoInstituicao() {
		if (this.instituicao != null) {
			this.administradorValidador = (Usuario) FacesContext
					.getCurrentInstance().getExternalContext().getSessionMap()
					.get("usuario");

			if (this.administradorValidador.getId().equals(
					this.aprovacao.getAprovador().getId())) {
				if (this.aprovacao.getExpiracao().after(new Date())) {
					try {
						this.excluirInstituicaoEJB.validarExclusaoInstituicao(
								this.instituicao, false, this.gerente != null);
						this.excluirInstituicaoEJB
								.excluirAprovacao(this.aprovacao);
						this.auditoriaFabricaEJB
								.auditarNegarExcluirInstituicao(
										this.aprovacao.getAprovador(),
										this.instituicao.getNome(),
										this.justificativa);
						MensagensDeErro.getSucessMessage(
								"excluirInstituicaoNegado", "resultado");
					} catch (ModeloException e) {
						e.printStackTrace();
					}
				} else {
					MensagensDeErro.getErrorMessage(
							"excluirInstituicaoErroData", "resultado");
				}
			} else {
				MensagensDeErro.getErrorMessage(
						"excluirInstituicaoErroUsuarioValidador", "resultado");
			}
			this.zerarManegedBean();
		} else {
			MensagensDeErro.getErrorMessage("excluirInstituicaoErrolistavazia",
					"resultado");
		}
		return null;
	}

	/**
	 * M�todo exclui a institui��o do sistema Mem�ria Virtual,bem como
	 * todos o objetos Acesso relacionados a ela , al�m disso, salva no bando
	 * de dados, na tabela auditoria os respons�veis pela exclus�o
	 * 
	 */
	public String validarExclusaoInstituicao() {
		if (this.instituicao != null) {

			this.administradorValidador = (Usuario) FacesContext
					.getCurrentInstance().getExternalContext().getSessionMap()
					.get("usuario");

			if (this.administradorValidador.getId().equals(
					this.aprovacao.getAprovador().getId())) {
				if (this.aprovacao.getExpiracao().after(new Date())) {
					try {
						this.excluirInstituicaoEJB.validarExclusaoInstituicao(
								this.instituicao, true, this.gerente != null);
						this.auditoriaFabricaEJB
								.auditarAutorizarExcluirInstituicao(
										this.aprovacao.getAprovador(),
										this.instituicao.getNome(),
										this.justificativa);
						this.excluirInstituicaoEJB.excluirAprovacao(aprovacao);
						System.out.print("A instituicao " + this.instituicao
								+ "foi excluida ..");
						MensagensDeErro.getSucessMessage(
								"excluirInstituicaoExcluida", "resultado");
					} catch (ModeloException e) {
						e.printStackTrace();
					}
				} else {
					MensagensDeErro.getErrorMessage(
							"excluirInstituicaoErroData", "resultado");
				}
			} else {
				MensagensDeErro.getErrorMessage(
						"excluirInstituicaoErroUsuarioValidador", "resultado");
			}
			this.zerarManegedBean();
		} else {
			MensagensDeErro.getErrorMessage("excluirInstituicaoErrolistavazia",
					"resultado");
		}
		return null;
	}

	/**
	 * M�todo reinicializa todos os atributos da classe ExcluirInstiuicaoMB.
	 * � utilizado nos m�todos relacionados com os bot�es voltar das
	 * p�ginas do CDU Excluir Institui��o
	 */
	private void zerarManegedBean() {
		// para construir as mensagens
		this.context = FacesContext.getCurrentInstance();
		;
		this.bundleName = "mensagens";
		this.bundle = context.getApplication().getResourceBundle(context,
				bundleName);

		// da institui��o a ser exclu�da
		this.instituicao = null;
		this.nome = null;
		this.gerentesInstituicao = new ArrayList<Usuario>();
		this.gerente = null;

		// do pedido de exclus�o
		this.requisitor = (Usuario) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("usuario");
		this.nomeValidador = null;
		this.validade = null;
		this.justificativa = null;
		this.administradorValidador = null;
		this.itemAuditoria = null;
		this.aprovacao = null;

		// da infraestrutura para as paginas
		this.justificativa1 = null;
		this.instituicoes = new ArrayList<Instituicao>();

	}

	/* M�todos dos bot�es voltar e cancelar */

	public String voltar() {
		this.zerarManegedBean();
		return "voltar";
	}

	public String voltar1() {
		this.administradorValidador = new Usuario();
		return "voltar1";
	}

	public String cancelar() {
		this.zerarManegedBean();
		return "cancelar";
	}

	/* se��o de m�todos validadores de formul�rio */

	/**
	 * @param event
	 *            M�todo de valida��o do imput validade do pedido de
	 *            exclus�o
	 */
	public void validadeValidade(AjaxBehaviorEvent event) {
		validateValidade();
		return;
	}

	/**
	 * M�todo de valida��o do imput validade do pedido de exclus�o
	 * Utilizado pelo validador do evento e pelo validador do bot�o
	 */
	public boolean validateValidade() {

		if (this.validade == null) {
			MensagensDeErro.getErrorMessage("enviarconvite_validadevazia",
					"validacaoValidade");
			return false;
		}
		return true;
	}

	/**
	 * @param event
	 *            keyup M�todo de valida��o do imput validade do pedido de
	 *            exclus�o
	 */
	public void validadeNome(AjaxBehaviorEvent event) {
		validateNome();
		return;
	}

	/**
	 * M�todo de valida��o do imput validade do pedido de exclus�o
	 * Utilizado pelo validador do evento e pelo validador do bot�o
	 */
	public boolean validateNome() {

		if (!this.memoriaVirtualEJB
				.verificarDisponibilidadeNomeInstituicao(this.nome))
			return true;
		else {
			MensagensDeErro.getErrorMessage(
					"excluirInstituicaoDescricaocampo1", "resultado");
			return false;
		}
	}

	/**
	 * @param event
	 *            M�todo de valida��o do imput gerente validador
	 */
	public void validadeValidador(AjaxBehaviorEvent event) {
		booleanValidador();
		return;
	}

	/**
	 * M�todo de valida��o do imput validador do pedido de exclus�o
	 * Utilizado pelo vallidador do evento e pelo validador do bot�o
	 */
	public boolean booleanValidador() {

		if (this.nomeValidador == null
				|| this.nomeValidador.contains(this.bundle
						.getString("excluirInstituicaoSemGerente"))) {
			MensagensDeErro.getErrorMessage("excluirInstituicaoErroValidador",
					"validacaoValidador");
			return false;
		}
		return true;
	}

	/**
	 * M�todos de valida��o da justificativa
	 * 
	 * @param event
	 */
	public void validateJustificativa(AjaxBehaviorEvent event) {
		validateJustificativa();
	}

	public boolean validateJustificativa() {
		if (this.justificativa.length() == 0) {
			MensagensDeErro.getErrorMessage(
					"excluirInstituicaoInstituicaoJustificativa",
					"validacaoJustificativa");
			return false;
		} else {
			if (contarJustificativa())
				return true;
			else
				return false;
		}

	}

	/* Getters e Setters */

	public String getNomeValidador() {
		return nomeValidador;
	}

	public void setNomeValidador(String nomeValidador) {
		this.nomeValidador = nomeValidador;
	}

	/**
	 * @return the itemAuditoria
	 */
	public ItemAuditoria getItemAuditoria() {
		return itemAuditoria;
	}

	/**
	 * @param itemAuditoria
	 *            the itemAuditoria to set
	 */
	public void setItemAuditoria(ItemAuditoria itemAuditoria) {
		this.itemAuditoria = itemAuditoria;
	}

	/**
	 * @return the aprovacao
	 */
	public Aprovacao getAprovacao() {
		return aprovacao;
	}

	/**
	 * @param aprovacao
	 *            the aprovacao to set
	 */
	public void setAprovacao(Aprovacao aprovacao) {
		this.aprovacao = aprovacao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String pnome) {
		nome = pnome;
	}

	public List<Instituicao> getInstituicoes() {
		return instituicoes;
	}

	public void setInstituicoes(List<Instituicao> instituicoes) {
		this.instituicoes = instituicoes;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

	public Instituicao getInstituicao() {
		return instituicao;
	}

	/**
	 * @param validade
	 *            the validade to set
	 */
	public void setValidade(String validade) {
		this.validade = validade;
	}

	/**
	 * @return the validade
	 */
	public String getValidade() {
		return validade;
	}

	/**
	 * @return the validade + dias
	 */
	public String getValidadecomdias() {
		return validade + " dias.";
	}

	/**
	 * @return the requisitor
	 */
	public Usuario getRequisitor() {
		return requisitor;
	}

	/**
	 * @param requisitor
	 *            the requisitor to set
	 */
	public void setRequisitor(Usuario requisitor) {
		this.requisitor = requisitor;
	}

	/**
	 * @return the validadeDias
	 */
	public List<SelectItem> getValidadeDias() {

		List<SelectItem> diasValidade = new ArrayList<SelectItem>();

		diasValidade.add(new SelectItem(null, bundle
				.getString("excluirInstituicaoSelecioneValidadE")));

		for (int i = 1; i <= 30; i++) {
			diasValidade.add(new SelectItem(i, i + "   dias"));
		}
		return diasValidade;
	}

	private class MyStringComparable implements Comparator<String> {

		@Override
		public int compare(String o1, String o2) {

			return o1.compareToIgnoreCase(o2);
		}

	}

	/**
	 * @param justificativa
	 *            the justificativa to set
	 */
	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	/**
	 * @return the justificativa
	 */
	public String getJustificativa() {
		return justificativa;
	}

	/**
	 * @param administradorValidador
	 *            the administradorValidador to set
	 */
	public void setAdministradorValidador(Usuario administradorValidador) {
		this.administradorValidador = administradorValidador;
	}

	/**
	 * @return the administradorValidador
	 */
	public Usuario getAdministradorValidador() {
		return administradorValidador;
	}

	/**
	 * @param gerentesInstituicao
	 *            the gerentesInstituicao to set
	 */
	public void setGerentesInstituicao(ArrayList<Usuario> gerentesInstituicao) {
		this.gerentesInstituicao = gerentesInstituicao;
	}

	/**
	 * @return the gerentesInstituicao
	 */
	public List<Usuario> getGerentesInstituicao() {
		return gerentesInstituicao;
	}
}