/**
 * 
 */
package br.usp.memoriavirtual.controle;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import br.usp.memoriavirtual.modelo.entidades.Autor;
import br.usp.memoriavirtual.modelo.fachadas.remoto.CadastrarAutorRemote;
import br.usp.memoriavirtual.utils.MensagensDeErro;

/**
 * @author bigmac
 * 
 */
public class CadastrarAutorMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5784819830194641882L;

	@EJB
	private CadastrarAutorRemote cadastrarAutorEJB;

	protected Autor autor = new Autor("", "", "", "", "", "");

	private boolean outroAtividade = false;
	private boolean normalAtividade = true;
	private boolean outroNascimento = false;
	private boolean normalNascimento = true;
	private boolean outroObito = false;
	private boolean normalObito = true;

	/**
	 * Construtor Padr�o
	 */
	public CadastrarAutorMB() {
		super();
	}

	/**
	 * 
	 * @return String a ser interpretada pelo faces-config
	 */
	public String cadastrarAutor() {
		String retorno = null;

		this.validateNome();
		this.validateSobrenome();
		this.validateNascimento();
		this.validateObito();
		this.validateAtividade();
		
		if(!FacesContext.getCurrentInstance().getMessages().hasNext()){

			this.cadastrarAutorEJB.cadastrarAutor(new Autor(this.autor
					.getNome(), this.autor.getSobrenome(), this.autor
					.getCodinome(), this.autor.getAtividade(), this.autor
					.getNascimento(), this.autor.getObito()));
			MensagensDeErro.getSucessMessage("cadastrarAutorSucesso",
					"resultado");
			this.autor.setNome("");
			this.autor.setSobrenome("");
			this.autor.setCodinome("");
			this.autor.setNascimento("");
			this.autor.setObito("");
			this.autor.setAtividade("");
			this.outroAtividade = false;
			this.normalAtividade = true;
			outroNascimento = false;
			normalNascimento = true;
			outroObito = false;
			normalObito = true;

		} else {
			MensagensDeErro.getErrorMessage("cadastrarAutorFalha", "resultado");
		}
		return retorno;
	}

	public List<SelectItem> getListaAtividade() {
		List<SelectItem> atividades = new ArrayList<SelectItem>();
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);
		atividades.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade0")));
		atividades.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade1")));
		atividades.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade2")));
		atividades.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade3")));
		atividades.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade4")));
		atividades.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade5")));
		atividades.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade6")));
		atividades.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade7")));
		atividades.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade8")));
		atividades.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade9")));
		atividades.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade10")));
		atividades.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade11")));
		atividades.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade12")));
		atividades.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade13")));
		atividades.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade14")));
		atividades.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade15")));
		atividades.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade16")));
		atividades.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade17")));
		atividades.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade18")));
		atividades.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade19")));
		atividades.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade20")));
		atividades.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade21")));
		atividades.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade22")));

		return atividades;
	}

	public String abrirAtividadeOutro() {
		this.autor.setAtividade("");
		this.outroAtividade = !this.outroAtividade;
		this.normalAtividade = !this.normalAtividade;
		return null;
	}

	public String getOutroLinkAtividade() {
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);
		if (this.normalAtividade) {
			return bundle.getString("cadastrarAutorEspecificar");
		} else {
			return bundle.getString("cadastrarAutorSelecionar");
		}

	}

	public String abrirNascimentoOutro() {
		this.autor.setNascimento("");
		this.outroNascimento = (this.outroNascimento) ? false : true;
		this.normalNascimento = (this.normalNascimento) ? false : true;
		return null;
	}

	public String getOutroLinkNascimento() {
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);
		if (!this.normalNascimento) {
			return bundle.getString("cadastrarAutorDataEspecifica");
		} else {
			return bundle.getString("cadastrarAutorDataImprecisa");
		}

	}

	public String abrirObitoOutro() {
		this.setObito("");
		this.outroObito = !this.outroObito;
		this.normalObito = !this.normalObito;
		return null;
	}

	public String getOutroLinkObito() {
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);
		if (!this.normalObito) {
			return bundle.getString("cadastrarAutorDataEspecifica");
		} else {
			return bundle.getString("cadastrarAutorDataImprecisa");
		}

	}

	public String resetCadastrarAutor() {
		this.autor.setNome("");
		this.autor.setSobrenome("");
		this.autor.setCodinome("");
		this.autor.setNascimento("");
		this.autor.setObito("");
		this.autor.setAtividade("");
		this.outroAtividade = false;
		this.normalAtividade = true;
		outroNascimento = false;
		normalNascimento = true;
		outroObito = false;
		normalObito = true;
		return "reset";
	}

	public void validateNome(AjaxBehaviorEvent event) {
		this.validateNome();
	}

	public boolean validateNome() {
		if (this.autor.getNome().equals("")) {
			MensagensDeErro.getErrorMessage("cadastrarAutorNomeVazia",
					"validacaoNome");
			return false;
		}
		return true;
	}

	public void validateSobrenome(AjaxBehaviorEvent event) {
		this.validateSobrenome();
	}

	public boolean validateSobrenome() {
		if (this.autor.getSobrenome().equals("")) {
			MensagensDeErro.getErrorMessage("cadastrarAutorSobrenomeVazia",
					"validacaoSobrenome");
			return false;
		}
		return true;
	}

	public void validateAtividade(AjaxBehaviorEvent event) {
		this.validateAtividade();
	}

	public boolean validateAtividade() {
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);
		if (this.autor.getAtividade().equals(
				bundle.getString("cadastrarAutorListaAtividade" + 0))) {
			this.autor.setAtividade("");

		}
		return true;
	}

	public void validateNascimento(AjaxBehaviorEvent event) {
		this.validateNascimento();
	}

	public boolean validateNascimento() {
		if ((!this.autor.getNascimento().equals("")))
			if (this.normalNascimento == true) {
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				try {
					format.setLenient(false);
					format.parse(this.autor.getNascimento());
				} catch (ParseException e) {
					MensagensDeErro.getErrorMessage(
							"cadastrarAutorNascimentoIncorreto",
							"validacaoNascimento");
					return false;
				}
			}
		return true;
	}

	public void validateObito(AjaxBehaviorEvent event) {
		this.validateObito();
	}

	public boolean validateObito() {
		if ((!this.autor.getObito().equals(""))) {
			if (this.normalObito == true) {
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				try {
					format.setLenient(false);
					format.parse(this.autor.getObito());
				} catch (ParseException e) {
					MensagensDeErro.getErrorMessage(
							"cadastrarAutorNascimentoIncorreto",
							"validacaoObito");
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * @return the outroAtividade
	 */
	public boolean isOutroAtividade() {
		return outroAtividade;
	}

	/**
	 * @return the normalAtividade
	 */
	public boolean isNormalAtividade() {
		return normalAtividade;
	}

	/**
	 * @param outroAtividade
	 *            the outroAtividade to set
	 */
	public void setOutroAtividade(boolean outroAtividade) {
		this.outroAtividade = outroAtividade;
	}

	/**
	 * @param normalAtividade
	 *            the normalAtividade to set
	 */
	public void setNormalAtividade(boolean normalAtividade) {
		this.normalAtividade = normalAtividade;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return this.autor.getNome();
	}

	/**
	 * @return the sobrenome
	 */
	public String getSobrenome() {
		return this.autor.getSobrenome();
	}

	/**
	 * @return the codinome
	 */
	public String getCodinome() {
		return this.autor.getCodinome();
	}

	/**
	 * @return the nascimento
	 */
	public String getNascimento() {
		return this.autor.getNascimento();
	}

	/**
	 * @return the obito
	 */
	public String getObito() {
		return this.autor.getObito();
	}

	/**
	 * @return the atividade
	 */
	public String getAtividade() {
		return this.autor.getAtividade();
	}

	/**
	 * @param nome
	 *            the nome to set
	 */
	public void setNome(String nome) {
		this.autor.setNome(nome);
	}

	/**
	 * @param sobrenome
	 *            the sobrenome to set
	 */
	public void setSobrenome(String sobrenome) {
		this.autor.setSobrenome(sobrenome);
	}

	/**
	 * @param codinome
	 *            the codinome to set
	 */
	public void setCodinome(String codinome) {
		this.autor.setCodinome(codinome);
	}

	/**
	 * @param nascimento
	 *            the nascimento to set
	 */
	public void setNascimento(String nascimento) {
		this.autor.setNascimento(nascimento);
	}

	/**
	 * @param obito
	 *            the obito to set
	 */
	public void setObito(String obito) {
		this.autor.setObito(obito);
	}

	/**
	 * @param atividade
	 *            the atividade to set
	 */
	public void setAtividade(String atividade) {
		this.autor.setAtividade(atividade);
	}

	/**
	 * @return the normaltipoAutoria
	 */

	/**
	 * @return the outroNascimento
	 */
	public boolean isOutroNascimento() {
		return outroNascimento;
	}

	/**
	 * @param outroNascimento
	 *            the outroNascimento to set
	 */
	public void setOutroNascimento(boolean outroNascimento) {
		this.outroNascimento = outroNascimento;
	}

	/**
	 * @return the normalNascimento
	 */
	public boolean isNormalNascimento() {
		return normalNascimento;
	}

	/**
	 * @param normalNascimento
	 *            the normalNascimento to set
	 */
	public void setNormalNascimento(boolean normalNascimento) {
		this.normalNascimento = normalNascimento;
	}

	/**
	 * @return the outroObito
	 */
	public boolean isOutroObito() {
		return outroObito;
	}

	/**
	 * @param outroObito
	 *            the outroObito to set
	 */
	public void setOutroObito(boolean outroObito) {
		this.outroObito = outroObito;
	}

	/**
	 * @return the normalObito
	 */
	public boolean isNormalObito() {
		return normalObito;
	}

	/**
	 * @param normalObito
	 *            the normalObito to set
	 */
	public void setNormalObito(boolean normalObito) {
		this.normalObito = normalObito;
	}

}
