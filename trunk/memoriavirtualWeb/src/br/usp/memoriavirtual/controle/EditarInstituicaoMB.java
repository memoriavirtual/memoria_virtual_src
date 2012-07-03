package br.usp.memoriavirtual.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import br.usp.memoriavirtual.utils.ValidacoesDeCampos;
import br.usp.memoriavirtual.utils.MensagensDeErro;
import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarInstituicaoRemote;


@ManagedBean(name = "editarInstituicaoMB")
@SessionScoped
public class EditarInstituicaoMB extends CadastrarInstituicaoMB implements Serializable {

	private static final long serialVersionUID = -2609697377310497761L;
	@EJB
	private EditarInstituicaoRemote editarInstituicaoEJB;
	private List<Instituicao> instituicoes;
	private Instituicao instituicao;

	public String editarInstituicao() {

		if (this.validateLocalizacao()
				&& this.validateNome() ) {

			try {
				Instituicao novaInstituicao = new Instituicao(this.nome,
						this.localizacao, this.endereco, this.cidade, this.estado,
						this.cep, this.telefone, this.caixaPostal, this.email, this.URL,
						this.identificacaoProprietario, this.administradorPropriedade,
						this.latitude, this.longitude, this.altitude,this.tipoPropriedade, this.protecaoExistente);
				this.editarInstituicaoEJB.editarInstituicao(novaInstituicao);
			} catch (ModeloException e) {
				MensagensDeErro.getErrorMessage(
						"editarInstituicaoErroEditarFalha", "resutado");
			} catch (RuntimeException e) {
				MensagensDeErro.getErrorMessage(
						"editarInstituicaoErroEditarFalha", "resultado");
			}

			this.cep = "";
			this.cidade = "";
			this.endereco = "";
			this.estado = "";
			this.localizacao = "";
			this.nome = "";
			this.telefone = "";

			MensagensDeErro.getSucessMessage("editarInstituicaoSucessoEditar",
					"resultado");
		}
		return "falha";

	}
	

	public String cancelar() {
		return "cancelar";
	}

	public void instituicoesSugeridas(AjaxBehaviorEvent event) {

		Usuario usuario = (Usuario) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("usuario");

		// Lista de instituicoes que o usuario pertence
		List<Instituicao> instituicoesUsuario = new ArrayList<Instituicao>();

		if (usuario.isAdministrador()) {
			instituicoesUsuario = this.memoriaVirtualEJB
					.listarInstituicoes(this.nome);
		} else {
			Grupo grupo = new Grupo("Gerente");
			instituicoesUsuario = this.memoriaVirtualEJB.listarInstituicoes(
					this.nome, grupo, usuario);
		}

		this.instituicoes = instituicoesUsuario;
	}

	public String selecionarInstituicao(Instituicao instituicao) {

		this.nome = instituicao.getNome();
		this.cep = instituicao.getCep();
		this.cidade = instituicao.getCidade();
		this.endereco = instituicao.getEndereco();
		this.estado = instituicao.getEstado();
		this.localizacao = instituicao.getLocalidade();
		this.telefone = instituicao.getTelefone();
		this.instituicao = instituicao;
		this.instituicoes.clear();
		return "sucesso";
	}

	public String selecionarInstituicao() {

		if (this.validateNome()) {
			Instituicao instituicao;
			Usuario usuario = (Usuario) FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().get("usuario");

			if (usuario.isAdministrador()) {
				try {
					instituicao = editarInstituicaoEJB
							.getInstituicao(this.nome);
					this.cep = instituicao.getCep();
					this.cidade = instituicao.getCidade();
					this.endereco = instituicao.getEndereco();
					this.estado = instituicao.getEstado();
					this.localizacao = instituicao.getLocalidade();
					this.telefone = instituicao.getTelefone();
					this.instituicao = instituicao;
					this.instituicoes.clear();
					System.out.println("akie");
					return "sucesso";
				} catch (ModeloException e) {
					e.printStackTrace();
				}
			} else {
				Grupo grupo = new Grupo("Gerente");
				try {
					instituicao = editarInstituicaoEJB.getInstituicao(
							this.nome, grupo, usuario);
					this.cep = instituicao.getCep();
					this.cidade = instituicao.getCidade();
					this.endereco = instituicao.getEndereco();
					this.estado = instituicao.getEstado();
					this.localizacao = instituicao.getLocalidade();
					this.telefone = instituicao.getTelefone();
					this.instituicao = instituicao;
					this.instituicoes.clear();
					return "sucesso";
				} catch (ModeloException e) {
					e.printStackTrace();
				}
			}
		}
		return "erro";

	}

	public void validateNome(AjaxBehaviorEvent event) {
		this.validateNome();
	}

	public boolean validateNome() {
		if ((this.memoriaVirtualEJB
				.verificarDisponibilidadeNomeInstituicao(this.nome))) {
			MensagensDeErro.getErrorMessage(
					"editarInstituicaoErroNomeExistente", "validacaoNome");
			return false;
		} else if (this.nome.length() < 4) {
			MensagensDeErro.getErrorMessage("editarInstituicaoErroNomeCurto",
					"validacaoNome");
			return false;
		}
		return true;
	}

	

	public void validateendereco(AjaxBehaviorEvent event) {
		this.validateendereco();
	}

	public boolean validateendereco() {
		if (this.endereco.equals("")) {
			MensagensDeErro.getErrorMessage(
					"editarInstituicaoErroenderecoVazio", "validacaoendereco");
			return false;
		}
		return true;
	}

	public void validateCidade(AjaxBehaviorEvent event) {
		this.validateCidade();
	}

	public boolean validateCidade() {
		if (this.cidade.equals("")) {
			MensagensDeErro.getErrorMessage("editarInstituicaoErroCidadeVazio",
					"validacaoCidade");
			return false;
		}
		return true;
	}

	public void validateCep(AjaxBehaviorEvent event) {
		this.validateCep();
	}

	public boolean validateCep() {
		if (this.cep.equals("")) {
			MensagensDeErro.getErrorMessage("editarInstituicaoErroCepVazio",
					"validacaoCep");
			return false;
		} else if (!ValidacoesDeCampos.validarFormatoCep(this.cep)) {
			MensagensDeErro.getErrorMessage("editarInstituicaoErroCepInvalido",
					"validacaoCep");
			return false;
		}
		return true;
	}

	public void validateTelefone(AjaxBehaviorEvent event) {
		this.validateCep();
	}

	public boolean validateTelefone() {
		if (this.telefone.equals("")) {
			MensagensDeErro.getErrorMessage(
					"editarInstituicaoErroTelefoneVazio", "validacaoTelefone");
			return false;
		} else if (!ValidacoesDeCampos.validarFormatoTelefone(this.telefone)) {
			MensagensDeErro.getErrorMessage(
					"editarInstituicaoErroTelefoneInvalido",
					"validacaoTelefone");
			return false;
		}
		return true;
	}


	public List<SelectItem> getEstadoSigla() {


		List<SelectItem> estados = new ArrayList<SelectItem>();

		estados.add(new SelectItem(null, this.estado));
		estados.add(new SelectItem("AL", "AL"));
		estados.add(new SelectItem("AM", "AM"));
		estados.add(new SelectItem("AP", "AP"));
		estados.add(new SelectItem("BA", "BA"));
		estados.add(new SelectItem("CE", "CE"));
		estados.add(new SelectItem("DF", "DF"));
		estados.add(new SelectItem("ES", "ES"));
		estados.add(new SelectItem("GO", "GO"));
		estados.add(new SelectItem("MA", "MA"));
		estados.add(new SelectItem("MG", "MG"));
		estados.add(new SelectItem("MS", "MS"));
		estados.add(new SelectItem("MT", "MT"));
		estados.add(new SelectItem("PA", "PA"));
		estados.add(new SelectItem("PB", "PB"));
		estados.add(new SelectItem("PE", "PE"));
		estados.add(new SelectItem("PI", "PI"));
		estados.add(new SelectItem("PR", "PR"));
		estados.add(new SelectItem("RJ", "RJ"));
		estados.add(new SelectItem("RN", "RN"));
		estados.add(new SelectItem("RO", "RO"));
		estados.add(new SelectItem("RR", "RR"));
		estados.add(new SelectItem("RS", "RS"));
		estados.add(new SelectItem("SC", "SC"));
		estados.add(new SelectItem("SP", "SP"));
		estados.add(new SelectItem("SE", "SE"));
		estados.add(new SelectItem("TO", "TO"));

		return estados;
	}

	/**
	 * @return the novoNome
	 */

	public String getNome() {
		return this.nome;
	}

	/**
	 * @param novoNome
	 *            the novoNome to set
	 */
	public void setNome(String novoNome) {
		this.nome = novoNome;
	}

	/**
	 * @return the localizacao
	 */
	public String getlocalizacao() {
		return localizacao;
	}

	/**
	 * @param localizacao
	 *            the localizacao to set
	 */
	public void setlocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	/**
	 * @return the endereco
	 */
	public String getendereco() {
		return endereco;
	}

	/**
	 * @param endereco
	 *            the endereco to set
	 */
	public void setendereco(String endereco) {
		this.endereco = endereco;
	}

	/**
	 * @return the cidade
	 */
	public String getcidade() {
		return cidade;
	}

	/**
	 * @param cidade
	 *            the cidade to set
	 */
	public void setcidade(String cidade) {
		this.cidade = cidade;
	}

	/**
	 * @return the estado
	 */
	public String getestado() {
		return estado;
	}

	/**
	 * @param estado
	 *            the estado to set
	 */
	public void setestado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return the cep
	 */
	public String getcep() {
		return cep;
	}

	/**
	 * @param cep
	 *            the cep to set
	 */
	public void setcep(String cep) {
		this.cep = cep;
	}

	/**
	 * @return the telefone
	 */
	public String gettelefone() {
		return telefone;
	}

	/**
	 * @param telefone
	 *            the telefone to set
	 */
	public void settelefone(String telefone) {
		this.telefone = telefone;
	}

	/**
	 * @return the instituicoes
	 */
	public List<Instituicao> getInstituicoes() {
		return this.instituicoes;
	}

	/**
	 * @param telefone
	 *            the instituicoes to set
	 */
	public void setInstituicoes(List<Instituicao> novoInstituicoes) {
		this.instituicoes = novoInstituicoes;
	}

}