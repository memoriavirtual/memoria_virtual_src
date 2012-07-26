/**
 * 
 */
package br.usp.memoriavirtual.controle;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.usp.memoriavirtual.modelo.entidades.Multimidia;

/**
 * @author bigmac
 *
 */
@ManagedBean(name = "cadastrarBemPatrimonialMB")
@SessionScoped
public class CadastrarBemPatrimonialMB implements BeanComMidia, Serializable {

	
	
	
	
	
	
	
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7413170360811077491L;
	
	
	
	
	
	
	
	protected boolean externo;
	
	protected String naturezaBem;
	
	
	
	

	

	@Override
	public List<Multimidia> recuperaColecaoMidia() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void adicionarMidia(Multimidia midia) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String removeMidia(Multimidia midia) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	/**
	 * @return the naturezaBem
	 */
	public String getNaturezaBem() {
		return naturezaBem;
	}

	/**
	 * @param naturezaBem the naturezaBem to set
	 */
	public void setNaturezaBem(String naturezaBem) {
		this.naturezaBem = naturezaBem;
	}
	/**
	 * @return the externo
	 */
	public boolean isExterno() {
		return externo;
	}

	/**
	 * @param externo the externo to set
	 */
	public void setExterno(boolean externo) {
		this.externo = externo;
	}

		
	
}
