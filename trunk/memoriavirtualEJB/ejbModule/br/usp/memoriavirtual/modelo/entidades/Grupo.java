package br.usp.memoriavirtual.modelo.entidades;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Grupo implements Serializable {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 9000706222936792545L;
	@Id
	private String id;

	/**
	 * Construtor padrão
	 */
	public Grupo() {
		super();
	}

	public Grupo(String id) {
		super();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
