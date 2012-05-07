/**
 * 
 */
package br.usp.memoriavirtual.controle;

import br.usp.memoriavirtual.modelo.entidades.Autor;

/**
 * @author bigmac
 *
 */
public class EditarAutorMB {
	
	private Autor autor;
	
	private boolean etapa1 = true;
	private boolean etapa2= false;
	
	/**
	 * Construtor 
	 */
	public EditarAutorMB(){
		super();
	}
		
	/**
	 * @return the nome
	 */
	public String getNome() {
		return autor.getNome();
	}

	/**
	 * @return the sobrenome
	 */
	public String getSobrenome() {
		return autor.getSobrenome();
	}

	/**
	 * @return the codinome
	 */
	public String getCodinome() {
		return autor.getCodinome();
	}

	/**
	 * @return the nascimento
	 */
	public String getNascimento() {
		return autor.getNascimento();
	}

	/**
	 * @return the obito
	 */
	public String getObito() {
		return autor.getObito();
	}

	/**
	 * @return the atividade
	 */
	public String getAtividade() {
		return autor.getAtividade();
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
	
	
	public boolean isEtapa1() {
		return etapa1;
	}

	public boolean isEtapa2() {
		return etapa2;
	}


}
