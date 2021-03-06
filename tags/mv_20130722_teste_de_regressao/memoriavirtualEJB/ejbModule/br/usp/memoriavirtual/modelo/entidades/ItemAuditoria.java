package br.usp.memoriavirtual.modelo.entidades;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@SequenceGenerator(name = "ITEMAUDITORIA_ID", sequenceName = "ITEMAUDITORIA_SEQ", allocationSize = 1)
public class ItemAuditoria implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3086869717610845286L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITEMAUDITORIA_ID")
	private Long id;
	
	@Temporal(TemporalType.DATE)
	Date data;
	String atributoSignificativo;
	String notas;
	Usuario autorAcao;
	EnumTipoAcao tipoAcao;
	/**
	 * @return the data
	 */
	public Date getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(Date data) {
		this.data = data;
	}

	/**
	 * @return the atributoSignificativo
	 */
	public String getAtributoSignificativo() {
		return atributoSignificativo;
	}

	/**
	 * @param atributoSignificativo
	 *            the atributoSignificativo to set
	 */
	public void setAtributoSignificativo(String atributoSignificativo) {
		this.atributoSignificativo = atributoSignificativo;
	}

	/**
	 * @return the notas
	 */
	public String getNotas() {
		return notas;
	}

	/**
	 * @param notas
	 *            the notas to set
	 */
	public void setNotas(String notas) {
		this.notas = notas;
	}

	/**
	 * @return the autorAcao
	 */
	public Usuario getAutorAcao() {
		return autorAcao;
	}

	/**
	 * @param autorAcao
	 *            the autorAcao to set
	 */
	public void setAutorAcao(Usuario autorAcao) {
		this.autorAcao = autorAcao;
	}

	/**
	 * @return the tipoAcao
	 */
	public EnumTipoAcao getTipoAcao() {
		return tipoAcao;
	}

	/**
	 * @param tipoAcao
	 *            the tipoAcao to set
	 */
	public void setTipoAcao(EnumTipoAcao tipoAcao) {
		this.tipoAcao = tipoAcao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
