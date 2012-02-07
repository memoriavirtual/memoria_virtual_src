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
import javax.servlet.http.HttpServletRequest;

import br.usp.memoriavirtual.modelo.entidades.Acesso;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
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
	@EJB
	private ExcluirInstituicaoRemote excluirInstituicaoEJB;
	@EJB
	private MemoriaVirtualRemote memoriaVirtualEJB;
	@EJB
	private EditarInstituicaoRemote editarInstituicaoEJB;
	private FacesContext context = FacesContext.getCurrentInstance();;
	private String bundleName = "mensagens";
	ResourceBundle bundle = context.getApplication().getResourceBundle(context, bundleName);
	private String nome = null;
	private List<Instituicao> instituicoes = new ArrayList<Instituicao>();
	private Instituicao instituicao = null;
	private Usuario administradorValidador = null;
	private String nomeValidador = null;
	
	private Usuario gerente = null;
	private Usuario requisitor = (Usuario) FacesContext.getCurrentInstance()
	.getExternalContext().getSessionMap().get("usuario");
	private String validade = null;
	private String justificativa = null;
	private String menssagemValidade = null ;
	private String mensagemValidador = null;
	private String justificativa1 = null;
	private boolean flagInstituicao = false;
	@SuppressWarnings("unused")
	private List<SelectItem> ValidadeDias;
	private List<Usuario> listaAdministradores = new ArrayList<Usuario>();
	private List<Usuario> gerentesInstituicao = new ArrayList<Usuario>();
	
	
	/**
	 * Método recupera os parametros da url e exclui a instiuição do sistema
	 * Memoria Virtual, alem disso, salva no bando de dados, na tabela auditoria
	 * os responsaveis pela exclusão
	 * @param event - Evento onLoad da página alcançada pelo email de confirmação
	 */
	public void excluirInstituicao(AjaxBehaviorEvent event) {
		Usuario requisitor = null;
		Usuario validador  = (Usuario) FacesContext.getCurrentInstance()
		.getExternalContext().getSessionMap().get("usuario");
		
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
			requisitor = excluirInstituicaoEJB.getRequisitor( memoriaVirtualEJB.embaralhar(req.getParameter("requisitor")));
		} catch (ModeloException e) {
			e.printStackTrace();
		}
        //aqui deve ser inserido um teste condicional a respeito da validade do pedido de exclusão
        try {
			excluirInstituicaoEJB.excluirInstituicao(memoriaVirtualEJB.embaralhar(req.getParameter("instituicao")),requisitor,validador);
		} catch (ModeloException e) {
			e.printStackTrace();
		}
		
    }

	
	public String enviarPedidoExclusao() throws IOException   {
		if(validateValidador()){
			
			
			SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yy");
			Date dataValidade = new Date();   
			try {
					
					this.memoriaVirtualEJB.enviarEmail(this.getEmailAdminstrador(),bundle.getString("excluirInstituicaoEmailTitulo"),
							bundle.getString("excluirInstituicaoEmailMensagem")+"\n"+"\n"
							+ bundle.getString("excluirInstituicaoNome") + this.instituicao.getNome()+"\n"
							+ bundle.getString("excluirInstituicaoGerentes") + this.gerente.getNomeCompleto()+"\n"
							+ bundle.getString("excluirInstituicaoJustificativa") + this.getJustificativa()+"\n"
							+ bundle.getString("excluirInstituicaoValidade") + formatoData.format(dataValidade)+"\n"
							+ bundle.getString("excluirInstituicaoEmilMensagemURL")+"\n"+"\n"
							+ "http://"
							+ memoriaVirtualEJB.getEnderecoServidor()
							+ "/restrito/validarExclusao.jsf?requisitor="
							+ memoriaVirtualEJB.embaralhar(this.requisitor.getId())
							+ "&instituicao="
							+ memoriaVirtualEJB.embaralhar(this.instituicao.getNome())
							+ "&validade="
							+ memoriaVirtualEJB.embaralhar(formatoData.format(dataValidade))+"\n"+"\n"
							+ bundle.getString("excluirInstituicaoEmailMensagemFim")+"\n"+"\n");
							MensagensDeErro.getSucessMessage("excluirInstituicaoEnviandoEmail",
							"resultado");
			} catch (MessagingException e) {
					e.printStackTrace();
				}
						
		}
		return "ooo";
	}

	
	
	

	private String getEmailAdminstrador() {
		try {
			Usuario validador = this.excluirInstituicaoEJB.getValidador(nomeValidador);

			return validador.getEmail();
		} catch (ModeloException e) {
			e.printStackTrace();
		}
		return null;
		
		
	}





	public void Gerentes(){
		List<Acesso> acessos = new ArrayList<Acesso>();
		try{
			acessos = excluirInstituicaoEJB.getGerentesdaInstituicao
			(instituicao);
			for (Acesso a: acessos)
			{
			this.gerentesInstituicao.add(a.getUsuario());
			}
			
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
	public void selecionarInstituicoes ( Instituicao pinstituicao ){
		this.setNome(pinstituicao.getNome());
		this.setInstituicao(pinstituicao);
		this.flagInstituicao = true;
		this.instituicoes.clear();
		return ;
	}
	/**
	 * @return
	 * Trata das validações quando o botão confirmar é pressionado
	 * Se todas as validações retornarem true return a Sting Instselecionada
	 * Que é interpretada pelo faces-config chamando a nova página.
	 */
	public String selecionarInstituicoes (){
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
		validateValidador();
		return;
	}
	/**
	 * Método de validação do imput validador do pedido de exclusão
	 * Utilizado pelo vallidador do evento e pelo validador do botão
	 */
	public boolean validateValidador(){

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
	 * @param validadeDias the validadeDias to set
	 */
	public void setValidadeDias(List<SelectItem> validadeDias) {
		ValidadeDias = validadeDias;
	}
	/**
	 * @return the validadeDias
	 */
	public List<SelectItem> getValidadeDias() {

		List<SelectItem> diasValidade = new ArrayList<SelectItem>();
		this.menssagemValidade = bundle.getString("excluirInstituicaoSelecioneValidadE");

		diasValidade.add(new SelectItem(null, this.menssagemValidade));

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

		this.mensagemValidador = bundle.getString("excluirInstituicaoSelecioneValidadOr");

		
		try {
			this.listaAdministradores = excluirInstituicaoEJB.listarAdministradores();
			for(Usuario c : this.listaAdministradores) {   
				validadoreslista.add(new String(c.getNomeCompleto()));   
			}
			for(Usuario c : this.gerentesInstituicao) {   
				validadoreslista.add(new String(c.getNomeCompleto()));   
			}
			MyStringComparable c = new MyStringComparable();
			Collections.sort(validadoreslista,c);
			for(int i=0 ; i != validadoreslista.size( ) - 1; i ++ ){
				if(validadoreslista.get(i).compareTo(validadoreslista.get(i+1)) == 0){
					validadoreslista.remove(i);
				}
				
			}
			//carregando lista select itens
			lista.add(new SelectItem(null, this.mensagemValidador));
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