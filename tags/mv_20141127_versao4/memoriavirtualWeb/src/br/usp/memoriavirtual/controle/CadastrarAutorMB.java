package br.usp.memoriavirtual.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.el.ELResolver;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import br.usp.memoriavirtual.modelo.entidades.Autor;
import br.usp.memoriavirtual.modelo.entidades.Autor.Atividade;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.CadastrarAutorRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ValidacaoRemote;
import br.usp.memoriavirtual.utils.MVControleMemoriaVirtual;
import br.usp.memoriavirtual.utils.MensagensDeErro;
import br.usp.memoriavirtual.utils.ValidacoesDeCampos;

@ManagedBean(name = "cadastrarAutorMB")
@SessionScoped
public class CadastrarAutorMB implements Serializable, BeanMemoriaVirtual {

	private static final long serialVersionUID = 5784819830194641882L;

	@EJB
	private CadastrarAutorRemote cadastrarAutorEJB;
	
	@EJB
	private ValidacaoRemote validacao;

	protected String nome = "";
	protected String sobrenome = "";
	protected String codinome = "";
	protected Atividade atividade = Autor.Atividade.adaptador;
	protected String nascimento = "";
	protected String obito = "";
	private MensagensMB mensagens;

	public CadastrarAutorMB() {
		super();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ELResolver resolver = facesContext.getApplication().getELResolver();
		this.mensagens = (MensagensMB) resolver.getValue(
				facesContext.getELContext(), null, "mensagensMB");
	}

	public String cadastrar() {
		if (this.validar()) {
			try {
				Autor autor = new Autor();
				autor.setAtividade(this.atividade);
				autor.setCodinome(this.codinome);
				autor.setNascimento(this.nascimento);
				autor.setNome(this.nome);
				autor.setObito(this.obito);
				autor.setSobrenome(this.sobrenome);
				
				this.cadastrarAutorEJB.cadastrarAutor(autor);
				this.getMensagens().mensagemSucesso(this.traduzir("sucesso"));
				this.limpar();
				return this.redirecionar("/restrito/index.jsf", true);
			} catch (Exception e) {
				this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	public String cadastroRapido(){
		String resultado = this.cadastrar();
		if(resultado != null && resultado.equals(this.redirecionar("/restrito/index.jsf", true))){
			MensagensDeErro.getSucessMessage("sucesso", "resultado");
		}
		return null;
	}

	public String limpar() {
		this.nome = "";
		this.sobrenome = "";
		this.codinome = "";
		this.atividade = Autor.Atividade.adaptador;
		this.nascimento = "";
		this.obito = "";
		return null;
	}

	public String cancelar() {
		this.limpar();
		return this.redirecionar("/restrito/index.jsf", true);
	}

	@Override
	public String traduzir(String chave) {
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, MVControleMemoriaVirtual.bundleName);
		return bundle.getString(chave);
	}

	@Override
	public String redirecionar(String pagina, boolean redirect) {
		return redirect ? pagina + "?faces-redirect=true" : pagina;
	}

	@Override
	public boolean validar() {
		boolean a = this.validarNome();
		boolean b = this.validarSobrenome();
		boolean c = this.validarUnico();
		return (a && b && c);
	}

	public boolean validarUnico(){
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("nome", this.nome);
		parametros.put("sobre", this.sobrenome);
		parametros.put("atividade", this.atividade);
		
		try {
			if(validacao.validacaoNaoExiste("unicidadeAutor", Autor.class, parametros)){
				return true;
			}else{
				this.getMensagens().mensagemErro(this.traduzir("autorJaCadastrado"));
				return false;
			}
		} catch (ModeloException e) {
			this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean validarNome() {
		if (this.nome == null || this.nome.equals("")) {
			String args[] = { this.traduzir("nome") };
			MensagensDeErro.getErrorMessage("erroCampoVazio", args,
					"validacao-nome");
			return false;
		}
		return true;
	}

	public boolean validarSobrenome() {
		if (this.sobrenome == null || this.sobrenome.equals("")) {
			String args[] = { this.traduzir("sobrenome") };
			MensagensDeErro.getErrorMessage("erroCampoVazio", args,
					"validacao-sobrenome");
			return false;
		}
		return true;
	}

	public List<SelectItem> getListaAtividade() {

		List<SelectItem> opcoes = new ArrayList<SelectItem>();

		for (Autor.Atividade t : Autor.Atividade.values()) {
			opcoes.add(new SelectItem(t, this.traduzir(t.toString())));
		}

		return opcoes;
	}

	// getters e setters

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getCodinome() {
		return codinome;
	}

	public void setCodinome(String codinome) {
		this.codinome = codinome;
	}

	public Atividade getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}

	public String getNascimento() {
		return nascimento;
	}

	public void setNascimento(String nascimento) {
		this.nascimento = nascimento;
	}

	public String getObito() {
		return obito;
	}

	public void setObito(String obito) {
		this.obito = obito;
	}

	public MensagensMB getMensagens() {
		return mensagens;
	}

	public void setMensagens(MensagensMB mensagens) {
		this.mensagens = mensagens;
	}

	@Override
	public void validarCampo(String nomeCampoMensagem, String nomeCampo,String campo) {
		if(ValidacoesDeCampos.validarComprimento(campo, 255)){
			String args[] = {"255"};
			MensagensDeErro.getWarningMessage("erroMaximoCaracteres", args, nomeCampoMensagem);
		}		
	}

}
