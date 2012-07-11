package br.usp.memoriavirtual.modelo.entidades.bempatrimonial;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class BemArquitetonico extends BemPatrimonial implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7652111334209358223L;

	public BemArquitetonico() {
	}
	
	private String condicaoTopografia;
	private String sudo;
	private String numeroDePavimentos;
	private String numeroDeAmbientes;
	private String alcova;
	private String porao;
	private String sotao;
	private String outros;
	private String areaTotal;
	private String alturaFachFrontal;
	private String alturaFachPosterior;
	private String largura;
	private String profundidade;
	private String alturaTotal;
	private String peDireitoTerreo;
	private String tipoPeDireito;

	/**
	 * @return the condicaoTopografia
	 */
	public String getCondicaoTopografia() {
		return condicaoTopografia;
	}
	/**
	 * @param condicaoTopografia the condicaoTopografia to set
	 */
	public void setCondicaoTopografia(String condicaoTopografia) {
		this.condicaoTopografia = condicaoTopografia;
	}
	/**
	 * @return the sudo
	 */
	public String getSudo() {
		return sudo;
	}
	/**
	 * @param sudo the sudo to set
	 */
	public void setSudo(String sudo) {
		this.sudo = sudo;
	}
	/**
	 * @return the numeroDePavimentos
	 */
	public String getNumeroDePavimentos() {
		return numeroDePavimentos;
	}
	/**
	 * @param numeroDePavimentos the numeroDePavimentos to set
	 */
	public void setNumeroDePavimentos(String numeroDePavimentos) {
		this.numeroDePavimentos = numeroDePavimentos;
	}
	/**
	 * @return the numeroDeAmbientes
	 */
	public String getNumeroDeAmbientes() {
		return numeroDeAmbientes;
	}
	/**
	 * @param numeroDeAmbientes the numeroDeAmbientes to set
	 */
	public void setNumeroDeAmbientes(String numeroDeAmbientes) {
		this.numeroDeAmbientes = numeroDeAmbientes;
	}
	/**
	 * @return the alcova
	 */
	public String getAlcova() {
		return alcova;
	}
	/**
	 * @param alcova the alcova to set
	 */
	public void setAlcova(String alcova) {
		this.alcova = alcova;
	}
	/**
	 * @return the porao
	 */
	public String getPorao() {
		return porao;
	}
	/**
	 * @param porao the porao to set
	 */
	public void setPorao(String porao) {
		this.porao = porao;
	}
	/**
	 * @return the sotao
	 */
	public String getSotao() {
		return sotao;
	}
	/**
	 * @param sotao the sotao to set
	 */
	public void setSotao(String sotao) {
		this.sotao = sotao;
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
	 * @return the alturaFachFrontal
	 */
	public String getAlturaFachFrontal() {
		return alturaFachFrontal;
	}
	/**
	 * @param alturaFachFrontal the alturaFachFrontal to set
	 */
	public void setAlturaFachFrontal(String alturaFachFrontal) {
		this.alturaFachFrontal = alturaFachFrontal;
	}
	/**
	 * @return the alturaFachPosterior
	 */
	public String getAlturaFachPosterior() {
		return alturaFachPosterior;
	}
	/**
	 * @param alturaFachPosterior the alturaFachPosterior to set
	 */
	public void setAlturaFachPosterior(String alturaFachPosterior) {
		this.alturaFachPosterior = alturaFachPosterior;
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
	/**
	 * @return the alturaTotal
	 */
	public String getAlturaTotal() {
		return alturaTotal;
	}
	/**
	 * @param alturaTotal the alturaTotal to set
	 */
	public void setAlturaTotal(String alturaTotal) {
		this.alturaTotal = alturaTotal;
	}
	/**
	 * @return the peDireitoTerreo
	 */
	public String getPeDireitoTerreo() {
		return peDireitoTerreo;
	}
	/**
	 * @param peDireitoTerreo the peDireitoTerreo to set
	 */
	public void setPeDireitoTerreo(String peDireitoTerreo) {
		this.peDireitoTerreo = peDireitoTerreo;
	}
	/**
	 * @return the tipoPeDireito
	 */
	public String getTipoPeDireito() {
		return tipoPeDireito;
	}
	/**
	 * @param tipoPeDireito the tipoPeDireito to set
	 */
	public void setTipoPeDireito(String tipoPeDireito) {
		this.tipoPeDireito = tipoPeDireito;
	}
	
	
}
