package br.usp.memoriavirtual.modelo.entidades.bempatrimonial;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class BemNatural extends BemPatrimonial implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3260274553621739342L;

	public BemNatural() {
	}
	
	/**
	 * @param relevo
	 * @param meioAntropico
	 * @param caracteristicasAmbientais
	 */
	public BemNatural(String relevo, String meioAntropico,
			String caracteristicasAmbientais) {
		super();
		this.relevo = relevo;
		this.meioAntropico = meioAntropico;
		this.caracteristicasAmbientais = caracteristicasAmbientais;
		this.tipoDoBemPatrimonial = new String(BemPatrimonial.TipoDoBemPatrimonial.NATURAL.name());
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
