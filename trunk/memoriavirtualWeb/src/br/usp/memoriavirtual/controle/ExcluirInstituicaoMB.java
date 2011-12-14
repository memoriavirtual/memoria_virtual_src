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
import br.usp.memoriavirtual.utils.MensagensDeErro;

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
	@EJB
	private EditarInstituicaoRemote editarInstituicaoEJB;

	private String nome;
	private List<Instituicao> instituicoes;
	private Instituicao instituicao;




	public void listarInstituicoes(AjaxBehaviorEvent event) {
		List<Instituicao> auxinstituicoes;
		auxinstituicoes = this.memoriaVirtualEJB.listarInstituicoes(this.nome);
		this.setInstituicoes(auxinstituicoes);
		return;
	}

	public void selecionarInstituicoes ( Instituicao pinstituicao ){
		this.setNome(pinstituicao.getNome());
		this.setInstituicao(pinstituicao);
		this.instituicoes.clear();
		return ;
	}
	public String selecionarInstituicoes (){

		try {
			this.setInstituicao ( editarInstituicaoEJB
					.getInstituicao(this.nome));
			this.instituicoes.clear();
			return "Instselecionada";
		} catch (ModeloException e) {
			e.printStackTrace();
		}

		return "erro";
	}
	public String sim() {
		return "sim";
	}
	public String cancelar() {
		return "cancelar";
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


	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

	public Instituicao getInstituicao() {
		return instituicao;
	}

}