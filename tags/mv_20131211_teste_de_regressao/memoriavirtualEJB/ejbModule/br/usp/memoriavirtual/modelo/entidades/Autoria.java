package br.usp.memoriavirtual.modelo.entidades;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlTransient;

import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;

@Entity
public class Autoria implements Serializable {

	public enum TipoAutoria {
		COAUTOR, ORGANIZADOR, AGENCIA, AUTOR, AUTOR_INSTITUCIONAL, COMPILADOR, COORDENADOR, DIRETOR, EDITOR, ENTIDADE_PRODUTORA, ENTREVISTADO, ESTUDIO, FABRICANTE, FIGURINISTA, FOTOGRAFO, ILUSTRADOR, PALESTRANTE, PREFACIADOR, TRADUTOR;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2352775687092568864L;

	public Autoria() {

	}

	/**
	 * @param tipoAutoria
	 * @param autor
	 * @param bemPatrimonial
	 */
	public Autoria(TipoAutoria tipoAutoria, Autor autor, BemPatrimonial bemPatrimonial) {
		this.tipoAutoria = tipoAutoria;
		this.autor = autor;
		this.bemPatrimonial = bemPatrimonial;
	}

	@Id
	private TipoAutoria tipoAutoria;
	@Id
	private Autor autor;
	@Id
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BEMPATRIMONIAL")
	@XmlTransient
	private BemPatrimonial bemPatrimonial;

	public String getNomeAutor() {
		return autor.getNome() + " " + autor.getSobrenome();
	}

	/**
	 * @return the tipoAutoria
	 */
	public TipoAutoria getTipoAutoria() {
		return tipoAutoria;
	}

	/**
	 * @param tipoAutoria
	 *            the tipoAutoria to set
	 */
	public void setTipoAutoria(TipoAutoria tipoAutoria) {
		this.tipoAutoria = tipoAutoria;
	}

	/**
	 * @return the autor
	 */
	public Autor getAutor() {
		return autor;
	}

	/**
	 * @param autor
	 *            the autor to set
	 */
	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	/**
	 * @return the bemPatrimonial
	 */
	public BemPatrimonial getBemPatrimonial() {
		return bemPatrimonial;
	}

	/**
	 * @param bemPatrimonial
	 *            the bemPatrimonial to set
	 */
	public void setBemPatrimonial(BemPatrimonial bemPatrimonial) {
		this.bemPatrimonial = bemPatrimonial;
	}

}
