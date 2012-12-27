package br.usp.memoriavirtual.controle;

import java.util.List;

import br.usp.memoriavirtual.modelo.entidades.Multimidia;

public interface BeanComMidia {
	
	/**
	 * 
	 * @return Coleção de objetos Multimídia
	 */
	public List<Multimidia> recuperaColecaoMidia();
	/**
	 *Adicionar Objetos Multimidia 
	 */
	public void adicionarMidia (Multimidia midia);
	/**
	 *Remover Objetos Multimidia 
	 */
	public String removeMidia(Multimidia midia);
	public String removeMidia(int index);
	
}
