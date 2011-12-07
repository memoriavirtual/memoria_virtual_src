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
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;

@ManagedBean(name = "excluirInstituicaoMB")
@SessionScoped
public class ExcluirInstituicaoMB implements Serializable {

	@EJB
	private EditarInstituicaoRemote excluirInstituicaoEJB;
	

	@EJB
	private MemoriaVirtualRemote memoriaVirtualEJB;
	
	private String nome;
	private List<Instituicao> instituicoes;
	
	public void listarInstituicoes(AjaxBehaviorEvent event) {

		Usuario usuario = (Usuario) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("usuario");
		
		if(usuario.isAdministrador()){
			
			this.setInstituicoes ( this.memoriaVirtualEJB.listarInstituicoes(this.nome));
		}else{
			MensagensDeErro.getErrorMessage("cadastrarInstituicaoErrocadastramento", "resultado");
		}
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
}