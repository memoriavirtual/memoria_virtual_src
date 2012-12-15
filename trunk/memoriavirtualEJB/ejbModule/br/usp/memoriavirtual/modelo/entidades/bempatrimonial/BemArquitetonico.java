package br.usp.memoriavirtual.modelo.entidades.bempatrimonial;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class BemArquitetonico extends BemPatrimonial implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7652111334209358223L;
	
	/**
	 * @param condicaoTopografia
	 * @param sudo
	 * @param numPavimentos
	 * @param numAmbientes
	 * @param alcova2
	 * @param porao2
	 * @param sotao2
	 * @param outros
	 * @param areaTotal2
	 * @param alturaFachadaFrontal
	 * @param alturaFachadaSuperior
	 * @param largura2
	 * @param profundidade2
	 * @param alturaTotal2
	 * @param peDireitoTerreo2
	 * @param tipoPeDireito
	 */
	public BemArquitetonico(String condicaoTopografia, String sudo,
			Integer numPavimentos, Integer numAmbientes, Boolean alcova2,
			Boolean porao2, Boolean sotao2, String outros, String areaTotal2,
			String alturaFachadaFrontal, String alturaFachadaSuperior,
			String largura2, String profundidade2, String alturaTotal2,
			String peDireitoTerreo2, String tipoPeDireito) {
		super();
		this.condicaoTopografia = condicaoTopografia;
		this.uso = sudo;
		this.numeroDePavimentos = numPavimentos;
		this.numeroDeAmbientes = numAmbientes;
		this.alcova = alcova2;
		this.porao = porao2;
		this.sotao = sotao2;
		this.outros = outros;
		this.areaTotal = areaTotal2;
		this.alturaFachFrontal = alturaFachadaFrontal;
		this.alturaFachPosterior = alturaFachadaSuperior;
		this.largura = largura2;
		this.profundidade = profundidade2;
		this.alturaTotal = alturaTotal2;
		this.peDireitoTerreo = peDireitoTerreo2;
		this.tipoPeDireito = tipoPeDireito;
	}
	public BemArquitetonico() {
	}
	
	private String condicaoTopografia;
	private String uso;
	private Integer numeroDePavimentos; 
	private Integer numeroDeAmbientes;
	private Boolean alcova;
	private Boolean porao;
	private Boolean sotao;
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
	 * @return the uso
	 */
	public String getUso() {
		return uso;
	}
	/**
	 * @param uso the uso to set
	 */
	public void setUso(String uso) {
		this.uso = uso;
	}
	/**
	 * @return the numeroDePavimentos
	 */
	public Integer getNumeroDePavimentos() {
		return numeroDePavimentos;
	}
	/**
	 * @param numeroDePavimentos the numeroDePavimentos to set
	 */
	public void setNumeroDePavimentos(Integer numeroDePavimentos) {
		this.numeroDePavimentos = numeroDePavimentos;
	}
	/**
	 * @return the numeroDeAmbientes
	 */
	public Integer getNumeroDeAmbientes() {
		return numeroDeAmbientes;
	}
	/**
	 * @param numeroDeAmbientes the numeroDeAmbientes to set
	 */
	public void setNumeroDeAmbientes(Integer numeroDeAmbientes) {
		this.numeroDeAmbientes = numeroDeAmbientes;
	}
	/**
	 * @return the alcova
	 */
	public Boolean getAlcova() {
		return alcova;
	}
	/**
	 * @param alcova the alcova to set
	 */
	public void setAlcova(Boolean alcova) {
		this.alcova = alcova;
	}
	/**
	 * @return the porao
	 */
	public Boolean getPorao() {
		return porao;
	}
	/**
	 * @param porao the porao to set
	 */
	public void setPorao(Boolean porao) {
		this.porao = porao;
	}
	/**
	 * @return the sotao
	 */
	public Boolean getSotao() {
		return sotao;
	}
	/**
	 * @param sotao the sotao to set
	 */
	public void setSotao(Boolean sotao) {
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
