package br.usp.memoriavirtual.modelo.entidades.bempatrimonial;

import java.io.Serializable;

import javax.persistence.Entity;
@Entity
public class BemArqueologico extends BemPatrimonial implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4718742198160645902L;
	
	

	/**
	 * @param condicaoTopografica
	 * @param sitioDaPaisagem
	 * @param aguaProximo
	 * @param possuiVegetacao
	 * @param exposicao
	 * @param usoAtual
	 * @param outros
	 * @param notas
	 * @param areaTotal2
	 * @param comprimento2
	 * @param altura2
	 * @param largura2
	 * @param profundidade2
	 */
	public BemArqueologico( String condicaoTopografica, String sitioDaPaisagem,
			String aguaProximo, String possuiVegetacao, String exposicao,
			String usoAtual, String outros, String notas, String areaTotal2,
			String comprimento2, String altura2, String largura2,
			String profundidade2) {
		super();
		this.condicaoTopografica = condicaoTopografica;
		this.sitioDaPaisagem = sitioDaPaisagem;
		this.aguaProximo = aguaProximo;
		this.possuiVegetacao = possuiVegetacao;
		this.exposicao = exposicao;
		this.usoAtual = usoAtual;
		this.outros = outros;
		this.notas = notas;
		this.areaTotal = areaTotal2;
		this.comprimento = comprimento2;
		this.altura = altura2;
		this.largura = largura2;
		this.profundidade = profundidade2;
		this.tipoDoBemPatrimonial = new String(BemPatrimonial.TipoDoBemPatrimonial.ARQUEOLOGICO.name());
		
	}
	public BemArqueologico() {
	}
	
	private String condicaoTopografica;
	private String sitioDaPaisagem;
	private String aguaProximo;
	private String possuiVegetacao;
	private String exposicao;
	private String usoAtual;
	private String outros;
	private String notas;
	private String areaTotal;
	private String comprimento;
	private String altura;
	private String largura;
	private String profundidade;

	/**
	 * @return the condicaoTopografica
	 */
	public String getCondicaoTopografica() {
		return condicaoTopografica;
	}
	/**
	 * @param condicaoTopografica the condicaoTopografica to set
	 */
	public void setCondicaoTopografica(String condicaoTopografica) {
		this.condicaoTopografica = condicaoTopografica;
	}
	/**
	 * @return the sitioDaPaisagem
	 */
	public String getSitioDaPaisagem() {
		return sitioDaPaisagem;
	}
	/**
	 * @param sitioDaPaisagem the sitioDaPaisagem to set
	 */
	public void setSitioDaPaisagem(String sitioDaPaisagem) {
		this.sitioDaPaisagem = sitioDaPaisagem;
	}
	/**
	 * @return the aguaProximo
	 */
	public String getAguaProximo() {
		return aguaProximo;
	}
	/**
	 * @param aguaProximo the aguaProximo to set
	 */
	public void setAguaProximo(String aguaProximo) {
		this.aguaProximo = aguaProximo;
	}
	/**
	 * @return the possuiVegetacao
	 */
	public String getPossuiVegetacao() {
		return possuiVegetacao;
	}
	/**
	 * @param possuiVegetacao the possuiVegetacao to set
	 */
	public void setPossuiVegetacao(String possuiVegetacao) {
		this.possuiVegetacao = possuiVegetacao;
	}
	/**
	 * @return the exposicao
	 */
	public String getExposicao() {
		return exposicao;
	}
	/**
	 * @param exposicao the exposicao to set
	 */
	public void setExposicao(String exposicao) {
		this.exposicao = exposicao;
	}
	/**
	 * @return the usoAtual
	 */
	public String getUsoAtual() {
		return usoAtual;
	}
	/**
	 * @param usoAtual the usoAtual to set
	 */
	public void setUsoAtual(String usoAtual) {
		this.usoAtual = usoAtual;
	}
	/**
	 * @return the outros
	 */
	public String getOutros() {
		return outros;
	}
	/**
	 * @param outros the outros to set
	 */
	public void setOutros(String outros) {
		this.outros = outros;
	}
	/**
	 * @return the notas
	 */
	public String getNotas() {
		return notas;
	}
	/**
	 * @param notas the notas to set
	 */
	public void setNotas(String notas) {
		this.notas = notas;
	}
	/**
	 * @return the areaTotal
	 */
	public String getAreaTotal() {
		return areaTotal;
	}
	/**
	 * @param areaTotal the areaTotal to set
	 */
	public void setAreaTotal(String areaTotal) {
		this.areaTotal = areaTotal;
	}
	/**
	 * @return the comprimento
	 */
	public String getComprimento() {
		return comprimento;
	}
	/**
	 * @param comprimento the comprimento to set
	 */
	public void setComprimento(String comprimento) {
		this.comprimento = comprimento;
	}
	/**
	 * @return the altura
	 */
	public String getAltura() {
		return altura;
	}
	/**
	 * @param altura the altura to set
	 */
	public void setAltura(String altura) {
		this.altura = altura;
	}
	/**
	 * @return the largura
	 */
	public String getLargura() {
		return largura;
	}
	/**
	 * @param largura the largura to set
	 */
	public void setLargura(String largura) {
		this.largura = largura;
	}
	/**
	 * @return the profundidade
	 */
	public String getProfundidade() {
		return profundidade;
	}
	/**
	 * @param profundidade the profundidade to set
	 */
	public void setProfundidade(String profundidade) {
		this.profundidade = profundidade;
	}
	
	
}
