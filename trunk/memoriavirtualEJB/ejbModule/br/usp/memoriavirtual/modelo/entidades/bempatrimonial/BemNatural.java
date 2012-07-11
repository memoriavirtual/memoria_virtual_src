package br.usp.memoriavirtual.modelo.entidades.bempatrimonial;

import java.io.Serializable;

public class BemNatural extends BemPatrimonial implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3260274553621739342L;

	public BemNatural() {
	}
	
	private String relevo;
	private String meioAntropico;
	private String caracteristicasAmbientais;

	/**
	 * @return the relevo
	 */
	public String getRelevo() {
		return relevo;
	}
	/**
	 * @param relevo the relevo to set
	 */
	public void setRelevo(String relevo) {
		this.relevo = relevo;
	}
	/**
	 * @return the meioAntropico
	 */
	public String getMeioAntropico() {
		return meioAntropico;
	}
	/**
	 * @param meioAntropico the meioAntropico to set
	 */
	public void setMeioAntropico(String meioAntropico) {
		this.meioAntropico = meioAntropico;
	}
	/**
	 * @return the caracteristicasAmbientais
	 */
	public String getCaracteristicasAmbientais() {
		return caracteristicasAmbientais;
	}
	/**
	 * @param caracteristicasAmbientais the caracteristicasAmbientais to set
	 */
	public void setCaracteristicasAmbientais(String caracteristicasAmbientais) {
		this.caracteristicasAmbientais = caracteristicasAmbientais;
	}
	
	
}
