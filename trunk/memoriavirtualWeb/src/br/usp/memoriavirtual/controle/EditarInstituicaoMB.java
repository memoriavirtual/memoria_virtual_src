package br.usp.memoriavirtual.controle;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.el.ELResolver;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarInstituicaoRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.UtilMultimidiaRemote;
import br.usp.memoriavirtual.utils.MensagensDeErro;
import br.usp.memoriavirtual.utils.ValidacoesDeCampos;

@ManagedBean(name = "editarInstituicaoMB")
@SessionScoped
public class EditarInstituicaoMB extends CadastrarInstituicaoMB implements
		Serializable {

	private static final long serialVersionUID = -2609697377310497761L;
	@EJB
	private EditarInstituicaoRemote editarInstituicaoEJB;

	@EJB
	private MemoriaVirtualRemote memoriaVirtualEJB;

	@EJB
	private UtilMultimidiaRemote utilMultimidiaEJB;
	protected long id = -1;

	private MensagensMB mensagens;

	public EditarInstituicaoMB() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ELResolver resolver = facesContext.getApplication().getELResolver();
		this.mensagens = (MensagensMB) resolver.getValue(
				facesContext.getELContext(), null, "mensagensMB");
	}

	public String editarInstituicao() {

		if (this.validar()) {
			try {
				this.editarInstituicaoEJB.editarInstituicao(instituicao);
				this.getMensagens().mensagemSucesso(this.traduzir("sucesso"));
				return this.redirecionar(
						"/restrito/editarinstituicaomultimidia.jsf", true);
			} catch (ModeloException e) {
				this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
				e.printStackTrace();
				return null;
			}

		} else {
			this.getMensagens().mensagemErro(this.traduzir("erroFormulario"));
		}
		return null;
	}

	public String voltar(){
		return "selecionarinstituicaoedicao.jsf";
	}
	
	public String selecionarInstituicao() {
		if (this.id != -1) {
			try {
				this.instituicao = this.editarInstituicaoEJB
						.getInstituicao(this.id);
				this.campos = this.utilMultimidiaEJB
						.listarCampos(this.instituicao.getContainerMultimidia());
				return this.redirecionar("/restrito/editarinstituicao.jsf",
						true);
			} catch (ModeloException m) {
				this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
				m.printStackTrace();
				return null;
			}
		} else {
			String args[] = { this.traduzir("nome") };
			MensagensDeErro.getErrorMessage("erroCampoVazio", args,
					"validacao-nome");
			this.getMensagens().mensagemErro(this.traduzir("erroFormulario"));
			return null;
		}
	}

	@Override
	public boolean validarNome() {

		if (this.instituicao.getNome() == null
				|| this.instituicao.getNome().equals("")) {
			String args[] = { this.traduzir("nome") };
			MensagensDeErro.getErrorMessage("erroCampoVazio", args,
					"validacao-nome");
			return false;
		}
		return true;
	}
	
	@Override
	public boolean validarCep(){
		if(ValidacoesDeCampos.validarFormatoCep(this.instituicao.getCep())){
			return true;
		}
		else{
			MensagensDeErro.getErrorMessage("erroFormatoCep",
					"validacao-cep");
			return false;
		}
	}

	@Override
	public boolean validar() {
		return this.validarNome() && this.validarCep();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public MensagensMB getMensagens() {
		return mensagens;
	}

	public void setMensagens(MensagensMB mensagens) {
		this.mensagens = mensagens;
	}
}