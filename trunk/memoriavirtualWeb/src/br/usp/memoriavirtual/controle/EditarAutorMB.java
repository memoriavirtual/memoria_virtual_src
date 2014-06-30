package br.usp.memoriavirtual.controle;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.el.ELResolver;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.usp.memoriavirtual.modelo.entidades.Autor;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarAutorRemote;
import br.usp.memoriavirtual.utils.MensagensDeErro;

@ManagedBean(name = "editarAutorMB")
@SessionScoped
public class EditarAutorMB extends CadastrarAutorMB implements Serializable {

	private static final long serialVersionUID = 6035894025134227970L;

	@EJB
	private EditarAutorRemote editarAutorEJB;

	protected String id = "";
	protected Autor autor = new Autor();
	private MensagensMB mensagens;

	public EditarAutorMB() {
		super();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ELResolver resolver = facesContext.getApplication().getELResolver();
		this.mensagens = (MensagensMB) resolver.getValue(
				facesContext.getELContext(), null, "mensagensMB");
	}

	public String editar() {
		if (this.validar()) {
			try {
				this.editarAutorEJB.editarAutor(this.autor);
				this.getMensagens().mensagemSucesso(this.traduzir("sucesso"));
				return this.redirecionar("/restrito/index.jsf", true);
			} catch (Exception e) {
				this.getMensagens().mensagemErro(this.traduzir("erro"));
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	public String voltar(){
		return "selecionarautoredicao.jsf";
	}
	
	public String selecionarAutor() {
		if(id.isEmpty()){
			this.getMensagens().mensagemErro(this.traduzir("mensagemErroExcluirAutorEmBranco"));
			return null;
		}
		try {
			this.autor = this.editarAutorEJB.getAutor(new Long(this.id));
			return this.redirecionar("/restrito/editarautor.jsf", false);
		} catch (ModeloException m) {
			this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
			m.printStackTrace();
			return null;
		}
	}

	public String cancelarEditarAutor() {
		return "cancel";
	}

	public String limpar() {
		this.id = "";
		this.autor = new Autor();
		super.limpar();
		return null;
	}

	@Override
	public boolean validar() {
		boolean a = this.validarNome();
		boolean b = this.validarSobrenome();
		return (a && b);
	}

	@Override
	public boolean validarNome() {
		if (this.autor.getNome() == null || this.autor.getNome().equals("")) {
			String args[] = { this.traduzir("nome") };
			MensagensDeErro.getErrorMessage("erroCampoVazio", args,
					"validacao-nome");
			return false;
		}
		return true;
	}

	@Override
	public boolean validarSobrenome() {
		if (this.autor.getSobrenome() == null
				|| this.autor.getSobrenome().equals("")) {
			String args[] = { this.traduzir("sobrenome") };
			MensagensDeErro.getErrorMessage("erroCampoVazio", args,
					"validacao-sobrenome");
			return false;
		}
		return true;
	}

	// getters e setters

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MensagensMB getMensagens() {
		return mensagens;
	}

	public void setMensagens(MensagensMB mensagens) {
		this.mensagens = mensagens;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}
}
