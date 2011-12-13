package br.usp.memoriavirtual.controle;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarInstituicaoRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;

@ManagedBean(name = "excluirInstituicaoMB")
@SessionScoped
public class ExcluirInstituicaoMB implements Serializable {

	//@EJB
	//private ExcluirInstituicaoRemote excluirInstituicaoEJB;
		

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB
	private MemoriaVirtualRemote memoriaVirtualEJB;
	private EditarInstituicaoRemote editarInstituicaoEJB;
	
	private String nome;
	private List<Instituicao> instituicoes;

	private String localizacao;
	private String endereco;
	private String cidade;
	private String estado;
	private String cep;
	private String telefone;
	private Instituicao instituicao;

	
	public void listarInstituicoes(AjaxBehaviorEvent event) {
	
			this.setInstituicoes ( this.memoriaVirtualEJB.listarInstituicoes(this.nome));
		
			//MensagensDeErro.getErrorMessage("cadastrarInstituicaoErrocadastramento", "resultado");
		
		return;
	}
	
	public void listarInstituicoes (Instituicao instituicao){
		this.setNome(instituicao.getNome());
		this.setCep(instituicao.getCep());
		this.setCidade(instituicao.getCidade());
		this.setEndereco(instituicao.getEndereco());
		this.setEstado(instituicao.getEstado());
		this.setLocalizacao(instituicao.getLocalizacao());
		this.setTelefone(instituicao.getTelefone());
		this.setInstituicao(instituicao);
		this.instituicoes.clear();
		return;
	}
		

	//get e set
	
	//nome
	public String getNome() {
		return nome;
	}

	public void setNome(String pnome) {
		nome = pnome;
	}
	
	public List<Instituicao> getInstituicoes() {
		return instituicoes;
	}

	//listadeinstituicao
	public void setInstituicoes(List<Instituicao> instituicoes) {
		this.instituicoes = instituicoes;
	}
	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}
	public String getLocalizacao() {
		return localizacao;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getCidade() {
		return cidade;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getEstado() {
		return estado;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getCep() {
		return cep;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getTelefone() {
		return telefone;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

	public Instituicao getInstituicao() {
		return instituicao;
	}

	public void setEditarInstituicaoEJB(EditarInstituicaoRemote editarInstituicaoEJB) {
		this.editarInstituicaoEJB = editarInstituicaoEJB;
	}

	public EditarInstituicaoRemote getEditarInstituicaoEJB() {
		return editarInstituicaoEJB;
	}
}