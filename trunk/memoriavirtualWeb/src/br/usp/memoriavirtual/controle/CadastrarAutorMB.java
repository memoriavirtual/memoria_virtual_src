/**
 * 
 */
package br.usp.memoriavirtual.controle;

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
public class CadastrarAutorMB {

	@EJB
	private CadastrarAutorRemote cadastrarAutorEJB;

	private String nome = "";
	private String sobrenome = "";
	private String codinome = "";
	private String nascimento = "";
	private String obito = "";
	private String atividade = "";
	private String tipoAutoria = "";

	private boolean outroTipoAutoria = false;

	private boolean normalTipoAutoria = true;

	private boolean outroAtividade = false;

	private boolean normalAtividade = true;
	
	

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

		if (this.validateNome() && this.validateSobrenome()
				&& this.validateNascimento() && this.validateObito()
				&& this.validateTipoAutoria() && this.validateAtividade()) {
			

			this.cadastrarAutorEJB.cadastrarAutor(new Autor(
					this.tipoAutoria, this.nome, this.sobrenome,
					this.codinome, this.atividade, this.nascimento, this.obito));
			MensagensDeErro.getSucessMessage("cadastrarAutorSucesso",
					"resultado");
			this.nome = "";
			this.sobrenome = "";
			this.codinome = "";
			this.nascimento = "";
			this.obito = "";
			this.atividade = "";
			this.tipoAutoria = "";
			this.outroTipoAutoria = false;
			this.normalTipoAutoria = true;
			this.outroAtividade = false;
			this.normalAtividade = true;

		} else {
			MensagensDeErro.getErrorMessage("cadastrarAutorFalha", "resultado");
		}
		return retorno;
	}

	public List<SelectItem> getListaTipoAutoria() {
		List<SelectItem> tiposAutoria = new ArrayList<SelectItem>();
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);
		tiposAutoria.add(new SelectItem(bundle
				.getString("cadastrarAutorListaTipoAutoria0")));
		tiposAutoria.add(new SelectItem(bundle
				.getString("cadastrarAutorListaTipoAutoria1")));
		tiposAutoria.add(new SelectItem(bundle
				.getString("cadastrarAutorListaTipoAutoria2")));
		tiposAutoria.add(new SelectItem(bundle
				.getString("cadastrarAutorListaTipoAutoria3")));
		tiposAutoria.add(new SelectItem(bundle
				.getString("cadastrarAutorListaTipoAutoria4")));
		tiposAutoria.add(new SelectItem(bundle
				.getString("cadastrarAutorListaTipoAutoria5")));
		tiposAutoria.add(new SelectItem(bundle
				.getString("cadastrarAutorListaTipoAutoria6")));
		tiposAutoria.add(new SelectItem(bundle
				.getString("cadastrarAutorListaTipoAutoria7")));
		tiposAutoria.add(new SelectItem(bundle
				.getString("cadastrarAutorListaTipoAutoria8")));
		tiposAutoria.add(new SelectItem(bundle
				.getString("cadastrarAutorListaTipoAutoria9")));
		tiposAutoria.add(new SelectItem(bundle
				.getString("cadastrarAutorListaTipoAutoria10")));
		tiposAutoria.add(new SelectItem(bundle
				.getString("cadastrarAutorListaTipoAutoria11")));
		tiposAutoria.add(new SelectItem(bundle
				.getString("cadastrarAutorListaTipoAutoria12")));
		tiposAutoria.add(new SelectItem(bundle
				.getString("cadastrarAutorListaTipoAutoria13")));
		tiposAutoria.add(new SelectItem(bundle
				.getString("cadastrarAutorListaTipoAutoria14")));
		tiposAutoria.add(new SelectItem(bundle
				.getString("cadastrarAutorListaTipoAutoria15")));
		tiposAutoria.add(new SelectItem(bundle
				.getString("cadastrarAutorListaTipoAutoria16")));
		tiposAutoria.add(new SelectItem(bundle
				.getString("cadastrarAutorListaTipoAutoria17")));
		tiposAutoria.add(new SelectItem(bundle
				.getString("cadastrarAutorListaTipoAutoria18")));

		return tiposAutoria;
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
		this.atividade = "";
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
	public String abrirAutoriaOutro() {
		this.tipoAutoria = "";
		this.outroTipoAutoria = !this.outroTipoAutoria;
		this.normalTipoAutoria = !this.normalTipoAutoria;
		return null;
	}

	public String getOutroLink() {
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);
		if (this.normalTipoAutoria) {
			return bundle.getString("cadastrarAutorEspecificar");
		} else {
			return bundle.getString("cadastrarAutorSelecionar");
		}

	}
	
	public String resetCadastrarAutor() {
		this.nome = "";
		this.sobrenome = "";
		this.codinome = "";
		this.nascimento = "";
		this.obito = "";
		this.atividade = "";
		this.tipoAutoria = "";
		this.outroTipoAutoria = false;
		this.normalTipoAutoria = true;
		this.outroAtividade = false;
		this.normalAtividade = true;
		return "reset";
	}

	public void validateTipoAutoria(AjaxBehaviorEvent event) {
		this.validateTipoAutoria();
	}

	public boolean validateTipoAutoria() {
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);
		if (this.tipoAutoria.equals(bundle
				.getString("cadastrarAutorListaTipoAutoria" + 0))) {
			this.tipoAutoria = "";
		}
		return true;
	}

	public void validateNome(AjaxBehaviorEvent event) {
		this.validateNome();
	}

	public boolean validateNome() {
		if (this.nome.equals("")) {
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
		if (this.sobrenome.equals("")) {
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
		if (this.atividade.equals(bundle
				.getString("cadastrarAutorListaAtividade" + 0))) {
			this.atividade = "";

		}
		return true;
	}

	public void validateNascimento(AjaxBehaviorEvent event) {
		this.validateNascimento();
	}

	public boolean validateNascimento() {
		if (this.nascimento.equals("")) {
			this.nascimento = "01/01/0001";
		}
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		try {
			format.setLenient(false);
			format.parse(this.nascimento);
		} catch (ParseException e) {
			MensagensDeErro.getErrorMessage(
					"cadastrarAutorNascimentoIncorreto", "validacaoNascimento");
			return false;
		}
		return true;
	}

	public void validateObito(AjaxBehaviorEvent event) {
		this.validateObito();
	}

	public boolean validateObito() {
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		try {
			format.setLenient(false);
			format.parse(this.obito);
		} catch (ParseException e) {
			MensagensDeErro.getErrorMessage(
					"cadastrarAutorNascimentoIncorreto", "validacaoObito");
			return false;
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
	 * @param outroAtividade the outroAtividade to set
	 */
	public void setOutroAtividade(boolean outroAtividade) {
		this.outroAtividade = outroAtividade;
	}

	/**
	 * @param normalAtividade the normalAtividade to set
	 */
	public void setNormalAtividade(boolean normalAtividade) {
		this.normalAtividade = normalAtividade;
	}

	/**
	 * @return the outroTipoAutoria
	 */
	public boolean isOutroTipoAutoria() {
		return outroTipoAutoria;
	}

	/**
	 * @param outroTipoAutoria
	 *            the outroTipoAutoria to set
	 */
	public void setOutroTipoAutoria(boolean outroTipoAutoria) {
		this.outroTipoAutoria = outroTipoAutoria;
	}

	/**
	 * @return the tipoAutoria
	 */
	public String getTipoAutoria() {
		return tipoAutoria;
	}

	/**
	 * @param tipoAutoria
	 *            the tipoAutoria to set
	 */
	public void setTipoAutoria(String tipoAutoria) {
		this.tipoAutoria = tipoAutoria;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @return the sobrenome
	 */
	public String getSobrenome() {
		return sobrenome;
	}

	/**
	 * @return the codinome
	 */
	public String getCodinome() {
		return codinome;
	}

	/**
	 * @return the nascimento
	 */
	public String getNascimento() {
		return nascimento;
	}

	/**
	 * @return the obito
	 */
	public String getObito() {
		return obito;
	}

	/**
	 * @return the atividade
	 */
	public String getAtividade() {
		return atividade;
	}

	/**
	 * @param nome
	 *            the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @param sobrenome
	 *            the sobrenome to set
	 */
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	/**
	 * @param codinome
	 *            the codinome to set
	 */
	public void setCodinome(String codinome) {
		this.codinome = codinome;
	}

	/**
	 * @param nascimento
	 *            the nascimento to set
	 */
	public void setNascimento(String nascimento) {
		this.nascimento = nascimento;
	}

	/**
	 * @param obito
	 *            the obito to set
	 */
	public void setObito(String obito) {
		this.obito = obito;
	}

	/**
	 * @param atividade
	 *            the atividade to set
	 */
	public void setAtividade(String atividade) {
		this.atividade = atividade;
	}

	/**
	 * @return the normaltipoAutoria
	 */
	public boolean isNormalTipoAutoria() {
		return normalTipoAutoria;
	}

	/**
	 * @param normaltipoAutoria
	 *            the normaltipoAutoria to set
	 */
	public void setNormalTipoAutoria(boolean normalTipoAutoria) {
		this.normalTipoAutoria = normalTipoAutoria;
	}


}
