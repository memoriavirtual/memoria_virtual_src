package br.usp.memoriavirtual.controle;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
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
	private ResourceBundle bundle = context.getApplication().getResourceBundle(context, bundleName);

	//da instituicao a ser excluida
	private Instituicao instituicao = new Instituicao();
	private String nome = new String();
	private List<Usuario> gerentesInstituicao = new ArrayList<Usuario>();
	private Usuario gerente = new Usuario();
	private boolean flagGerente = false;

	//do pedido de exclus�o
	private Usuario requisitor = (Usuario) FacesContext.getCurrentInstance()
			.getExternalContext().getSessionMap().get("usuario");
	private String nomeValidador = new String();
	private String validade = new String();
	private String justificativa = new String();
	private Usuario administradorValidador = new Usuario();
	private ItemAuditoria itemAuditoria = new ItemAuditoria();
	private Aprovacao aprovacao = new Aprovacao();

	//da infraestrutura para as paginas
	private String justificativa1 = new String();
	private boolean flagInstituicao = false;
	private List<Instituicao> instituicoes = new ArrayList<Instituicao>();
	private List<Usuario> listaAdministradores = new ArrayList<Usuario>();









	private void zerarManegedBean ( ){
		//para construir as mensagens
		this.context = FacesContext.getCurrentInstance();;
		this.bundleName = "mensagens";
		this.bundle = context.getApplication().getResourceBundle(context, bundleName);

		//da instituicao a ser excluida
		this.instituicao = new Instituicao();
		this.nome = new String();
		this.gerentesInstituicao = new ArrayList<Usuario>();
		this.gerente = new Usuario();
		this.flagGerente = false;

		//do pedido de exclus�o
		this.requisitor = (Usuario) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("usuario");
		this.nomeValidador = new String();
		this.validade = new String();
		this.justificativa = new String();
		this.administradorValidador = new Usuario();
		this.itemAuditoria = new ItemAuditoria();
		this.aprovacao = new Aprovacao();

		//da infraestrutura para as paginas
		this.justificativa1 = new String();
		this.flagInstituicao = false;
		this.instituicoes = new ArrayList<Instituicao>();
		this.listaAdministradores = new ArrayList<Usuario>();
	}



	public String negarExclusaoInstituicao(){
		if(this.instituicao.getNome() != null){
			this.administradorValidador =  (Usuario) FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().get("usuario");

			if(this.administradorValidador.getId().equals(this.aprovacao.getAprovador().getId())){
				if(this.aprovacao.getExpiracao().after(new Date())){

					try {
						excluirInstituicaoEJB.validarExclusaoInstituicao(this.instituicao,false);
						excluirInstituicaoEJB.excluirAprovacaoItemAuditoria(this.aprovacao,this.itemAuditoria);
						MensagensDeErro.getSucessMessage("excluirInstituicaoNegado", "resultado");
					} catch (ModeloException e) {
						e.printStackTrace();
					}
				}else{ 
					MensagensDeErro.getErrorMessage("excluirInstituicaoErroData", "resultado");
				}
			}else{ 
				MensagensDeErro.getErrorMessage("excluirInstituicaoErroUsuarioValidador", "resultado");
			}
			this.zerarManegedBean();
		}else{
			MensagensDeErro.getErrorMessage("excluirInstituicaoErrolistavazia", "resultado");
		}
		return null;
	}
	/**
	 * M�todo  exclui a instiui��o do sistema Memoria Virtual,bem como todos o objetos Acesso relacionados a ela
	 * , alem disso, salva no bando de dados, na tabela auditoria
	 * os responsaveis pela exclus�o
	 * 
	 */
	public String validarExclusaoInstituicao() {
		if(this.instituicao.getNome() != null){

			this.administradorValidador =  (Usuario) FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().get("usuario");

			if(this.administradorValidador.getId().equals(this.aprovacao.getAprovador().getId())){
				if(this.aprovacao.getExpiracao().after(new Date())){
					try {
						this.excluirInstituicaoEJB.validarExclusaoInstituicao(this.instituicao,true);
						this.auditoriaFabricaEJB.auditarAutorizarExcluirInstituicao(this.aprovacao.getAprovador(), this.instituicao.getNome(),this.justificativa);
						this.excluirInstituicaoEJB.excluirAprovacaoItemAuditoria(aprovacao);
						System.out.print("A instituicao "+this.instituicao+"foi excluida ..");
						MensagensDeErro.getSucessMessage("excluirInstituicaoExcluida", "resultado");
					} catch (ModeloException e) {
						e.printStackTrace();
					}
				}else{ 
					MensagensDeErro.getErrorMessage("excluirInstituicaoErroData", "resultado");
				}
			}else{
				MensagensDeErro.getErrorMessage("excluirInstituicaoErroUsuarioValidador", "resultado");
			}
			this.zerarManegedBean();
		}else{
			MensagensDeErro.getErrorMessage("excluirInstituicaoErrolistavazia", "resultado");
		}
		return null;
	}

	/**
	 * M�todo constr�i o texto do email, bem como chama o m�todo responsavel
	 * por enviar o mesmo.
	 * Ainda registra um objeto Itemauditoria, registra um objeto aprova��o e 
	 * marca a institui��o a ser excluida e os objetos Acesso relacionados a ela com o campo validade = false
	 * @throws IOException
	 */
	public String enviarPedidoExclusao() throws IOException   {
		if(this.instituicao != new Instituicao()){

			if(booleanValidador()){

				Date dataValidade = new Date();   
				int a = Integer.parseInt(this.validade,10);

				DateFormat formatoData = DateFormat.getDateInstance();
				GregorianCalendar gc=new GregorianCalendar();
				gc.add(GregorianCalendar.HOUR, 24*a);
				dataValidade = gc.getTime();
				try {
					
					//this.getEmailAdminstrador(this.nomeValidador);
					this.memoriaVirtualEJB.enviarEmail(this.getEmailAdminstrador(this.nomeValidador),bundle.getString("excluirInstituicaoEmailTitulo"),
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
						+ bundle.getString("excluirInstituicaoEmailMensagemFim")+"\n"+"\n");
					//registra a autoria do pedido de exclus�o
					this.auditoriaFabricaEJB.auditarExcluirInstituicao(this.requisitor, this.instituicao.getNome(),this.justificativa);
					//registra um objeto Aprova��o
					this.excluirInstituicaoEJB.registrarAprovacao(this.administradorValidador,this.instituicao,dataValidade);
					//marca a institui��o a ser excluida para que a mesma n�o seja mais utilizada 
					this.excluirInstituicaoEJB.marcarInstituicaoExcluida(this.instituicao,false);

					//mensagem de sucesso 
					MensagensDeErro.getSucessMessage("excluirInstituicaoEnviandoEmail",
							"resultado");
					} catch (MessagingException e) {
				e.printStackTrace();
					 } catch (ModeloException e) {
						 e.printStackTrace();
					 }

			}
			this.zerarManegedBean();
		}else{
			MensagensDeErro.getErrorMessage("excluirInstituicaoErrolistavazia", "resultado");
		}
		return null;
	}





	/**
	 * M�todo retorna um administrador do banco de dados.
	 * neste caso, o gerente a ser o Avaliador do pedido de exclus�o
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
	 * M�todo apenas transforma um lista de acessos em uma lista de usuarios
	 * e seta como gerente da instituicao o primeiro usuario da lista
	 */
	public void Gerentes(boolean b){
		List<Acesso> acessos = new ArrayList<Acesso>();
		if(!this.flagGerente){
			try{
				acessos = excluirInstituicaoEJB.getGerentesdaInstituicao
						(this.instituicao,b);
				for (Acesso a: acessos)
				{
					this.gerentesInstituicao.add(a.getUsuario());
				}
				this.gerente = this.gerentesInstituicao.get(0);
				this.flagGerente = true;
			}
			catch (ModeloException e){
				e.printStackTrace();
			}
		}

	}
	/**
	 * @param event
	 * Encontra uma lista de institui��es que correspondem ao nome
	 * que est� sendo digitado no campo
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
	 * Testa a se lista � vazia  e envia as mensagens corretas
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
		}
	}
	/**
	 * @param pinstituicao
	 * Seleciona uma institui��o escolhida na lista
	 */
	public String selecionarInstituicoes ( Instituicao pinstituicao ){ 
		this.flagGerente = false;
		this.gerentesInstituicao.clear();
		this.setNome(pinstituicao.getNome());
		this.setInstituicao(pinstituicao);
		this.flagInstituicao = true;
		this.instituicoes.clear();
		return null;
	}
	/**
	 * @return
	 * Trata das valida��es quando o bot�o confirmar � pressionado
	 * Se todas as valida��es retornarem true return a Sting Instselecionada
	 * Que � interpretada pelo faces-config chamando a nova p�gina.
	 */
	public String selecionarInstituicoe (){
		if(this.instituicao == null && this.flagInstituicao){
			try {this.setInstituicao ( editarInstituicaoEJB.getInstituicao(this.nome));

			} catch (ModeloException e) {
				e.printStackTrace();
				this.flagInstituicao = false;
			}
		}
		if ( validateValidade() && this.flagInstituicao){
			this.Gerentes(true);
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
	 * M�todo de valida��o do imput validade do pedido de exclus�o 
	 */
	public void validadeValidade(AjaxBehaviorEvent event){
		validateValidade();
		return;
	}
	/**
	 * M�todo de valida��o do imput validade do pedido de exclus�o
	 * Utilizado pelo vallidador do evento e pelo validador do bot�o
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
	 * M�todo de valida��o do imput gerente validador 
	 */
	public void validadeValidador(AjaxBehaviorEvent event){
		booleanValidador();
		return;
	}
	/**
	 * M�todo de valida��o do imput validador do pedido de exclus�o
	 * Utilizado pelo vallidador do evento e pelo validador do bot�o
	 */
	public boolean booleanValidador(){

		if (this.nomeValidador == null ) {
			MensagensDeErro.getErrorMessage("excluirInstituicaoErroValidador",
					"validacaoValidador");
			return false;
		} 
		return true;
	}
	/**
	 * M�todos de valida��o da justificativa
	 * @param event
	 */
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
	/**
	 * M�todos para contagem don numero de caracteres no campo justificativa 
	 * @param event
	 */
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
		if(this.instituicao.getNome() != null){
			if(validateJustificativa ()){
				return "confirmarexcluir";
			}
		}else{
			MensagensDeErro.getErrorMessage("excluirInstituicaoErrolistavazia", "resultado");
		}
		return null;
	}
	public String voltar() {
		this.instituicoes.clear();
		this.justificativa = "";
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
			diasValidade.add(new SelectItem(i, i + "   dias"));
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
				if(flag && (c.getId().compareTo( this.requisitor.getId()) !=  0)){
					validadoreslista.add(new String(c.getNomeCompleto()));
				}
				flag = true;

			}
			for(Usuario x : this.gerentesInstituicao) {   
				if(x.getId().compareTo( this.requisitor.getId()) !=  0){
					validadoreslista.add(new String(x.getNomeCompleto()));  
				}
			}
			this.listaAdministradores.clear();
			//this.gerentesInstituicao.clear();
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