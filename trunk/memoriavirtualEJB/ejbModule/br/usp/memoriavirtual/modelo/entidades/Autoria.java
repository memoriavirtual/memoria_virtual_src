package br.usp.memoriavirtual.modelo.entidades;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;

@Entity
@SequenceGenerator(name = "AUTORIA_ID", sequenceName = "AUTORIA_SEQ", allocationSize = 1)
@XmlRootElement
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Autoria implements Serializable {

	public static enum TipoAutoria {
		coautor, organizador, agencia, autor, autorInstitucional, compilador, coordenador, diretor, editor, entidadeProdutora, entrevistado, estudio, fabricante, figurinista, fotografo, ilustrador, palestrante, prefaciador, tradutor;
	}

	private static final long serialVersionUID = -2352775687092568864L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUTORIA_ID")
	private long id;

	private TipoAutoria tipo = Autoria.TipoAutoria.autor;
	private Autor autor = new Autor();

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BEMPATRIMONIAL")
	@XmlTransient
	private BemPatrimonial bemPatrimonial;

	public Autoria() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public TipoAutoria getTipo() {
		return tipo;
	}

	public void setTipo(TipoAutoria tipoAutoria) {
		this.tipo = tipoAutoria;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public BemPatrimonial getBemPatrimonial() {
		return bemPatrimonial;
	}

	public void setBemPatrimonial(BemPatrimonial bemPatrimonial) {
		this.bemPatrimonial = bemPatrimonial;
	}

}
