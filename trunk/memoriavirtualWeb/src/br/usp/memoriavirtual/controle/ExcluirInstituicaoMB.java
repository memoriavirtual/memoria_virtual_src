package br.usp.memoriavirtual.controle;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
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
@ManagedBean(name = "excluirInstituicaoMB")
@SessionScoped
public class ExcluirInstituicaoMB implements Serializable {


	private static final long serialVersionUID = 1147425267036231710L;
	
	
	//objetos ejb
	@EJB
	private ExcluirInstituicaoRemote excluirInstituicaoEJB;
	@EJB
	private MemoriaVirtualRemote memoriaVirtualEJB;
	@EJB
	private EditarInstituicaoRemote editarInstituicaoEJB;// para utilizar o metodo getinstituicao()
	@EJB
	private AuditoriaFabricaRemote auditoriaFabricaEJB;
	
	
	//objetos gerais
	
	//para construir as mensagens
	private FacesContext context = FacesContext.getCurrentInstance();;
	private String bundleName = "mensagens";
	ResourceBundle bundle = context.getApplication().getResourceBundle(context, bundleName);
	
	//da instituicao a ser excluida
	private Instituicao instituicao = null;
	private String nome = null;
	private Usuario gerente = null;

	//do pedido de exclusão
	private Usuario requisitor = (Usuario) FacesContext.getCurrentInstance()
	.getExternalContext().getSessionMap().get("usuario");
	private String nomeValidador = null;
	private String validade = null;
	private String justificativa = null;
	private Usuario administradorValidador = null;
	private ItemAuditoria itemAuditoria = null;
	private Aprovacao aprovacao = null;
	
	//da infraestrutura para as paginas
	private String justificativa1 = null;
	private boolean flagInstituicao = false;
	private List<Instituicao> instituicoes = new ArrayList<Instituicao>();
	private List<Usuario> listaAdministradores = new ArrayList<Usuario>();
	private List<Usuario> gerentesInstituicao = new ArrayList<Usuario>();


	


	/**
	 * Método recupera os parametros da url e exclui a instiuição do sistema
	 * Memoria Virtual, alem disso, salva no bando de dados, na tabela auditoria
	 * os responsaveis pela exclusão
	 * @param event - Evento onLoad da página alcançada pelo email de confirmação
	 */
	public String validarExclusaoInstituicao() {

	
		//aqui deve ser inserido um teste condicional a respeito da validade do pedido de exclusão
		/*try {
			excluirInstituicaoEJB.validarExclusaoInstituicao(this.instituicao,this.requisitor,this.administradorValidador);
		} catch (ModeloException e) {
			e.printStackTrace();
		}*/
		return null;
	}

	/**
	 * Método constrói o texto do email, bem como chama o método responsavel
	 * por enviar o mesmo.
	 * Ainda registra um objeto Itemauditoria, registra um objeto aprovação e 
	 * marca a instituição a ser excluida com o campo validade = false
	 * @throws IOException
	 */
	public String enviarPedidoExclusao() throws IOException   {
		if(booleanValidador()){


			SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yy");
			Date dataValidade = new Date();   
			try {
				this.getEmailAdminstrador(this.nomeValidador);
				/*this.memoriaVirtualEJB.enviarEmail(this.getEmailAdminstrador(this.nomeValidador),bundle.getString("excluirInstituicaoEmailTitulo"),
						bundle.getString("excluirInstituicaoEmailMensagem")+"\n"+"\n"
						+ bundle.getString("excluirInstituicaoNome") + this.instituicao.getNome()+"\n"
						+ bundle.getString("excluirInstituicaoGerentes") + this.gerente.getNomeCompleto()+"\n"
						+ bundle.getString("excluirInstituicaoJustificativa") + this.getJustificativa()+"\n"
						+ bundle.getString("excluirInstituicaoRequisitor") + this.getRequisitor()+"\n"
						+ bundle.getString("excluirInstituicaoValidade") + formatoData.format(dataValidade)+"\n"
						+ bundle.getString("excluirInstituicaoEmilMensagemURL")+"\n"+"\n"
						+ "http://"
						+ memoriaVirtualEJB.getEnderecoServidor()
						+ "/excluir?"
						+ "chaveEstrangeira="
						+ this.instituicao.getNome()
						+"\n\n"
						+ bundle.getString("excluirInstituicaoEmailMensagemFim")+"\n"+"\n");*/
				//registra a autoria do pedido de exclusão
				this.auditoriaFabricaEJB.auditarExcluirInstituicao(this.requisitor, this.instituicao.getNome(),this.justificativa);
				//registra um objeto Aprovação
				this.excluirInstituicaoEJB.registrarAprovacao(this.administradorValidador,this.instituicao,dataValidade);
				//marca a instituição a ser excluida para que a mesma não seja mais utilizada 
				this.excluirInstituicaoEJB.marcarInstituicaoExcluida(this.instituicao);
				
				//mensagem de sucesso 
				MensagensDeErro.getSucessMessage("excluirInstituicaoEnviandoEmail",
				"resultado");
			/*} catch (MessagingException e) {
				e.printStackTrace();
			*/} catch (ModeloException e) {
				e.printStackTrace();
			}

		}
		return null;
	}

	public void servetService(String chaveEstrangeira){
		
		
	}


	

	/**
	 * Método retorna um administrador do banco de dados.
	 * neste caso, o gerente a ser o Avaliador do pedido de exclusão
	 * @param nome
	 * @return
	 */
	private String getEmailAdminstrador(String nome) {
		try {
			this.administradorValidador = this.excluirInstituicaoEJB.getValidador(nome);

			return this.administradorValidador.getEmail();
		} catch (ModeloException e) {
			e.printStackTrace();
		}
		return null;


	}




	/**
	 * Método apenas transforma um lista de acessos em uma lista de usuarios
	 * e seta como gerente da instituicao o primeiro usuario da lista
	 */
	public void Gerentes(){
		List<Acesso> acessos = new ArrayList<Acesso>();
		try{
			acessos = excluirInstituicaoEJB.getGerentesdaInstituicao
			(this.instituicao);
			for (Acesso a: acessos)
			{
				this.gerentesInstituicao.add(a.getUsuario());
			}
			this.gerente = this.gerentesInstituicao.get(0);
		}
		catch (ModeloException e){
			e.printStackTrace();
		}


	}
	/**
	 * @param event
	 * Encontra uma lista de instituições que correspondem ao nome
	 * que está sendo digitado no campo
	 */
	public void listarInstituicoes(AjaxBehaviorEvent event) {
		this.instituicoes.clear();
		List<Instituicao> listaInstituicoes = new ArrayList<Instituicao>();
		listaInstituicoes = this.memoriaVirtualEJB.listarInstituicoes(this.nome);
		this.setInstituicoes(listaInstituicoes);
		this.validacaolista();
		return;
	}
	/**
	 * Testa a se lista é vazia  e envia as mensagens corretas
	 */
	private void validacaolista(){
		if(this.instituicoes.isEmpty() )
			if(this.nome != ""){
				MensagensDeErro.getErrorMessage("excluirInstituicaoErrolistavazia",
				"validacaoNome");
				this.flagInstituicao = false;
			}
		if( this.nome == ""){
			MensagensDeErro.getErrorMessage("excluirInstituicaoInstituicaoVazia",
			"validacaoNome");	
			this.flagInstituicao = false;
		}else
			this.flagInstituicao = true;
	}
	/**
	 * @param pinstituicao
	 * Seleciona uma instituição escolhida na lista
	 */
	public String selecionarInstituicoes ( Instituicao pinstituicao ){
		this.setNome(pinstituicao.getNome());
		this.setInstituicao(pinstituicao);
		this.flagInstituicao = true;
		this.instituicoes.clear();
		return null;
	}
	/**
	 * @return
	 * Trata das validações quando o botão confirmar é pressionado
	 * Se todas as validações retornarem true return a Sting Instselecionada
	 * Que é interpretada pelo faces-config chamando a nova página.
	 */
	public String selecionarInstituicoe (){
		this.Gerentes();
		if(this.instituicao == null && this.flagInstituicao){
			try {this.setInstituicao ( editarInstituicaoEJB.getInstituicao(this.nome));

			} catch (ModeloException e) {
				e.printStackTrace();
				this.flagInstituicao = false;
			}
		}
		if ( validateValidade() && this.flagInstituicao){
			return "Instselecionada";
		}else{	
			if(!this.flagInstituicao){
				MensagensDeErro.getErrorMessage("excluirInstituicaoErroNaoSelecionou","validacaoNome");	
			}
		}

		return "erro";
	}
	/**
	 * @param event
	 * Método de validação do imput validade do pedido de exclusão 
	 */
	public void validadeValidade(AjaxBehaviorEvent event){
		validateValidade();
		return;
	}
	/**
	 * Método de validação do imput validade do pedido de exclusão
	 * Utilizado pelo vallidador do evento e pelo validador do botão
	 */
	public boolean validateValidade(){

		if (this.validade == null ) {
			MensagensDeErro.getErrorMessage("enviarconvite_validadevazia",
			"validacaoValidade");
			return false;
		} 
		return true;
	}
	/**
	 * @param event
	 * Método de validação do imput gerente validador 
	 */
	public void validadeValidador(AjaxBehaviorEvent event){
		booleanValidador();
		return;
	}
	/**
	 * Método de validação do imput validador do pedido de exclusão
	 * Utilizado pelo vallidador do evento e pelo validador do botão
	 */
	public boolean booleanValidador(){

		if (this.nomeValidador == null ) {
			MensagensDeErro.getErrorMessage("excluirInstituicaoErroValidador",
			"validacaoValidador");
			return false;
		} 
		return true;
	}
	public void validateJustificativa (AjaxBehaviorEvent event) {
		validateJustificativa ();		
	}
	public boolean validateJustificativa () {
		if(this.justificativa.length() == 0 ){
			MensagensDeErro.getErrorMessage("excluirInstituicaoInstituicaoJustificativa",
			"validacaoJustificativa");
			return false;
		}else{if(contagemJustificativa ())
			return true;
		else return false;
		}

	}
	public void contagemJustificativa (AjaxBehaviorEvent event) {
		contagemJustificativa ();
	}
	public boolean contagemJustificativa (){

		if(this.justificativa.length() > 300){
			MensagensDeErro.getErrorMessage("excluirInstituicaoInstituicaoJustificativa300",
			"validacaoJustificativa");
			this.justificativa = this.justificativa1;
			return false;
		}else{
			this.justificativa1 = this.justificativa;
			FacesContext.getCurrentInstance().addMessage("validacaoJustificativa",
					new FacesMessage(FacesMessage.SEVERITY_WARN, this.justificativa.length() + "/300", null));
			return true;
		}

	}

	public String confirmarexcluirInstituicao() {
		if(validateJustificativa ()){
			return "confirmarexcluir";
		}
		return null;
	}
	public String voltar() {
		this.nome= "";
		this.instituicoes.clear();
		this.instituicao = null;
		this.validade = "";
		this.justificativa = "";
		this.flagInstituicao = false;
		return "voltar";
	}
	public String cancelar() {
		this.nome= "";
		this.instituicoes.clear();
		this.instituicao = null;
		this.validade = "";
		this.justificativa = "";
		this.flagInstituicao = false;
		return "cancelar";
	}



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
	 * @param itemAuditoria the itemAuditoria to set
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
	 * @param aprovacao the aprovacao to set
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
	 * @param validade the validade to set
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
	 * @param requisitor the requisitor to set
	 */
	public void setRequisitor(Usuario requisitor) {
		this.requisitor = requisitor;
	}

	/**
	 * @return the validadeDias
	 */
	public List<SelectItem> getValidadeDias() {

		List<SelectItem> diasValidade = new ArrayList<SelectItem>();

		diasValidade.add(new SelectItem(null, bundle.getString("excluirInstituicaoSelecioneValidadE")));

		for (int i = 1; i<= 30; i++) {
			diasValidade.add(new SelectItem(i, i + " dias"));
		}
		return diasValidade;
	}
	/**
	 * @return the validadeDias
	 */
	public List<SelectItem> getListaValidadores() {

		List<String> validadoreslista = new ArrayList<String>();
		List<SelectItem> lista = new ArrayList<SelectItem>();



		try {
			boolean flag = true;
			this.listaAdministradores = excluirInstituicaoEJB.listarAdministradores();
			for(Usuario c : this.listaAdministradores) {   

				for(Usuario x : this.gerentesInstituicao) {   
					if(c.getId().compareTo( x.getId()) ==  0){
						flag = false;
					}
				}
				if(flag){
					validadoreslista.add(new String(c.getNomeCompleto()));
				}
				flag = true;

			}
			for(Usuario x : this.gerentesInstituicao) {   
				validadoreslista.add(new String(x.getNomeCompleto()));   
			}
			this.listaAdministradores.clear();
			this.gerentesInstituicao.clear();
			MyStringComparable c = new MyStringComparable();
			Collections.sort(validadoreslista,c);

			//carregando lista select itens
			lista.add(new SelectItem(null,bundle.getString("excluirInstituicaoSelecioneValidadOr")));
			for(String i : validadoreslista) {   
				lista.add(new SelectItem(i,i));   
			}
			return lista;
		} catch (ModeloException e) {
			e.printStackTrace();
		}


		return lista;
	}


	private class MyStringComparable implements Comparator<String>{  

		@Override    
		public int compare(String o1, String o2) {

			return o1.compareToIgnoreCase(o2);    
		}

	}
	/**
	 * @param justificativa the justificativa to set
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
	 * @param flagInstituicao the flagInstituicao to set
	 */
	public void setFlagInstituicao(boolean flagInstituicao) {
		this.flagInstituicao = flagInstituicao;
	}
	/**
	 * @return the flagInstituicao
	 */
	public boolean isFlagInstituicao() {
		return flagInstituicao;
	}
	/**
	 * @param administradorValidador the administradorValidador to set
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
	 * @param gerentesInstituicao the gerentesInstituicao to set
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