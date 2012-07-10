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

import br.usp.memoriavirtual.modelo.entidades.Acesso;
import br.usp.memoriavirtual.modelo.entidades.Aprovacao;
import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.ItemAuditoria;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fabricas.remoto.AuditoriaFabricaRemote;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirInstituicaoRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;
import br.usp.memoriavirtual.utils.MensagensDeErro;

/**
 * @author MAC
 * 
 */
@ManagedBean(name = "excluirInstituicaoMB")
@SessionScoped
public class ExcluirInstituicaoMB implements Serializable {

	private static final long serialVersionUID = 1147425267036231710L;

	// objetos ejb
	@EJB
	private ExcluirInstituicaoRemote excluirInstituicaoEJB;
	@EJB
	private MemoriaVirtualRemote memoriaVirtualEJB;

	@EJB
	private AuditoriaFabricaRemote auditoriaFabricaEJB;

	// objetos gerais

	// para construir as mensagens
	private FacesContext context = FacesContext.getCurrentInstance();
	private String bundleName = "mensagens";
	private ResourceBundle bundle = context.getApplication().getResourceBundle(
			context, bundleName);

	// da instituição a ser excluída
	private Instituicao instituicao = null;
	private String nome = null;
	private List<Usuario> gerentesInstituicao = new ArrayList<Usuario>();
	private Usuario gerente = null;

	// do pedido de exclusão
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
	 * Método é invocado a cada evento Ajax KeyUp no campo Nome da Instituição
	 * do formulário, tal campo está associado ao atributo this.nome, este
	 * atributo é utilizado para fazer uma busca no banco de dados, que retorna
	 * uma lista em ordem alfabética, em relação ao nome, de todas as
	 * instituições cujo o nome corresponde em parte com o atributo this.nome.
	 * 
	 * @param event
	 *            Ajax KeyUp
	 */
	public void listarInstituicoes(AjaxBehaviorEvent event) {
		this.instituicoes.clear();
		if (this.nome != "") {

			List<Instituicao> listaInstituicoes = new ArrayList<Instituicao>();
			listaInstituicoes = this.memoriaVirtualEJB.listarInstituicoes(
					this.nome, new Grupo("GERENTE"), requisitor);
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
					.getString("excluirInstituicaoSugestaoTodas"));
			this.instituicoes.add(inst);
		}
		return;
	}

	/**
	 * Método é invocado quando é pressionado o botão Selecionar Instituição,
	 * Ele invoca os métodos de validação do campos dos formulários, se estes
	 * método considerarem válidas as informações nos campos do formulário, o
	 * método this.listarGerentes() é invocado, e depois é retornado a String
	 * Instselecionada que será interpretada pelo facesConfig
	 * 
	 * @return Se todas as validações retornarem true retorna a Sting
	 *         Instselecionada Que é interpretada pelo facesConfig chamando a
	 *         próxima página do CDU ExcluirInstituicao
	 */
	public String selecionarInstituicoes() {
		if (this.validateNome() && this.validateValidade()) {
			this.listarGerentes(true);
			return "Instselecionada";
		}
		return null;
	}

	/**
	 * Método é invocado quando ocorre um clique uma linha do DataTable que
	 * contém a lista de Instituições cujo nome corresponde em parte ao nome que
	 * está sendo digitado. A instituição selecionada é passada como parâmetro,
	 * e utilizada para setar os argumentos this.nome com o nome da instituição
	 * e o this.instituicao com o próprio objeto Instituição.
	 * 
	 * @param instituicao
	 * 
	 */
	public String selecionarInstituicoes(Instituicao instituicao) {
		if (!instituicao.getNome().equals(
				this.bundle.getString("excluirInstituicaoSugestaoTodas"))) {
			if (!instituicao.getNome().equals(
					this.bundle.getString("excluirInstituicaoErrolistavazia"))) {
				this.setInstituicao(instituicao);
				this.setNome(instituicao.getNome());
				this.instituicoes.clear();
			}
		} else {
			try {
				this.instituicoes = this.excluirInstituicaoEJB
						.listarTodasInstituicoes(new Grupo("GERENTE"),
								requisitor);
			} catch (ModeloException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Método invocado quando uma instituição é selecionada, seja na página do
	 * CDU ou no servlet, o método faz uma requisição no banco de dados de
	 * objetos Acesso relacionados com a instituição selecionada, caso exista
	 * uma lista de objetos Acesso, ele transforma a lista em uma lista de
	 * objetos Usuario e a atribui a this.gerentesInstituicao e seta o atributo
	 * this.gerente com o primeiro usuário da lista. caso não exista uma lista
	 * de objetos Acesso relacionados a Instituição, um objeto Usuario é criado
	 * mas este serve apenas para mostrar a mensagem na página informando que a
	 * instituição não tem gerentes.
	 * 
	 */
	public void listarGerentes(boolean b) {
		List<Acesso> acessos = new ArrayList<Acesso>();
		if (this.gerentesInstituicao.isEmpty()) {
			try {
				acessos = excluirInstituicaoEJB.recuperarGerentesdaInstituicao(
						this.instituicao, b);
				for (Acesso a : acessos) {
					this.gerentesInstituicao.add(a.getUsuario());
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

			} catch (ModeloException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Método invocado quando ocorre o evento Ajax KeyUp na caixa de texto, onde
	 * o usuário preenche a justificativa.Este método apenas invoca o método
	 * this.contarJustificativa
	 * 
	 * @param event
	 */
	public void contagemJustificativa(AjaxBehaviorEvent event) {
		contarJustificativa();
	}

	/**
	 * Método faz analisa o tamanho da String exibe mensagens com o número de
	 * caracteres na página do CDU, caso o número de caracteres exceda 300, a
	 * mensagem de erro também é exibida.
	 * 
	 * @return True caso o número de caracteres da justificativa seja inferior a
	 *         300 caracteres, do contrário retorna falso.
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
	 * Método trata o pressionar do botão Confirmar na página do CDU
	 * 
	 * @return String confirmarexcluir, caso as validações dos campos sejam
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
	 * Método é utilizado pela página do CDU para exibir a lista de
	 * administradores e/ou gerentes que estão apitos a validarem o pedido de
	 * exclusão. o método também trabalha para que a lista esteja lexamente
	 * ordenada, para que o usuario da seção não esteja na lista e para que não
	 * contenha nomes repetidos
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
																// instituição
				validadoreslista.put(gerentes.getId(),
						gerentes.getNomeCompleto());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		validadoreslista.remove(requisitor.getId());
		List<String> listaTemp = new ArrayList<String>();

		listaTemp.addAll(validadoreslista.values());

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
	 * Método monta o texto do email, bem como chama o método responsável por
	 * enviar o mesmo. Ainda registra um objeto itemAuditoria, registra um
	 * objeto aprovação e marca a instituição a ser excluída e os objetos Acesso
	 * relacionados a ela com o campo validade = false também trabalha com o
	 * objeto Date afim de encontrar a data de expiração que fara parte do
	 * objeto Aprovação
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
											+ this.instituicao.getNome()
											+ "\n\n"
											+ bundle.getString("excluirInstituicaoEmailMensagemFim")
											+ "\n" + "\n");
					// registra a autoria do pedido de exclusão
					this.auditoriaFabricaEJB.auditarExcluirInstituicao(
							this.requisitor, this.instituicao.getNome(),
							this.justificativa);
					// registra um objeto Aprovacao
					this.excluirInstituicaoEJB.registrarAprovacao(
							this.administradorValidador, this.instituicao,
							dataValidade);
					// marca a instituição a ser excluída para que a mesma não
					// seja mais utilizada
					this.excluirInstituicaoEJB.marcarInstituicaoExcluida(
							this.instituicao, false, this.gerente != null);

					// mensagem de sucesso
					MensagensDeErro.getSucessMessage(
							"excluirInstituicaoEnviandoEmail", "resultado");
				} catch (MessagingException e) {
					e.printStackTrace();
				} catch (ModeloException e) {
					e.printStackTrace();
					e.getCause();
				}

			}
			this.zerarManegedBean();
		} else {
			MensagensDeErro.getErrorMessage("excluirInstituicaoErrolistavazia",
					"resultado");
		}
		return null;
	}

	/* Métodos que tratam do Pedido de Exclusão */

	/**
	 * Método trata ação do botão Negar Pedido da Página do caso de uso Nega o
	 * pedido, reativa a Instituição e todas as suas dependências
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
	 * Método exclui a instituição do sistema Memória Virtual,bem como todos o
	 * objetos Acesso relacionados a ela , além disso, salva no bando de dados,
	 * na tabela auditoria os responsáveis pela exclusão
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
	 * Método reinicializa todos os atributos da classe ExcluirInstiuicaoMB. É
	 * utilizado nos métodos relacionados com os botões voltar das páginas do
	 * CDU Excluir Instituição
	 */
	private void zerarManegedBean() {
		// para construir as mensagens
		this.context = FacesContext.getCurrentInstance();
		;
		this.bundleName = "mensagens";
		this.bundle = context.getApplication().getResourceBundle(context,
				bundleName);

		// da instituição a ser excluída
		this.instituicao = null;
		this.nome = null;
		this.gerentesInstituicao = new ArrayList<Usuario>();
		this.gerente = null;

		// do pedido de exclusão
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

	/* Métodos dos botões voltar e cancelar */

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

	/* seção de métodos validadores de formulário */

	/**
	 * @param event
	 *            Método de validação do imput validade do pedido de exclusão
	 */
	public void validadeValidade(AjaxBehaviorEvent event) {
		validateValidade();
		return;
	}

	/**
	 * Método de validação do imput validade do pedido de exclusão Utilizado
	 * pelo validador do evento e pelo validador do botão
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
	 *            keyup Método de validação do imput validade do pedido de
	 *            exclusão
	 */
	public void validadeNome(AjaxBehaviorEvent event) {
		validateNome();
		return;
	}

	/**
	 * Método de validação do imput validade do pedido de exclusão Utilizado
	 * pelo validador do evento e pelo validador do botão
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
	 *            Método de validação do imput gerente validador
	 */
	public void validadeValidador(AjaxBehaviorEvent event) {
		booleanValidador();
		return;
	}

	/**
	 * Método de validação do imput validador do pedido de exclusão Utilizado
	 * pelo vallidador do evento e pelo validador do botão
	 */
	public boolean booleanValidador() {

		if (this.nomeValidador == null) {
			MensagensDeErro.getErrorMessage("excluirInstituicaoErroValidador",
					"validacaoValidador");
			return false;
		}
		return true;
	}

	/**
	 * Métodos de validação da justificativa
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