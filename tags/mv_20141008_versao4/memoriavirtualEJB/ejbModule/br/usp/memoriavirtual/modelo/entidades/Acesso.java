package br.usp.memoriavirtual.modelo.entidades;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.xml.bind.annotation.XmlRootElement;

@NamedQuery(name = "acessos", query = "SELECT a FROM Acesso a WHERE a.usuario = :usuario AND a.validade = true")
@Entity
@SequenceGenerator(name = "ACESSO_ID", sequenceName = "ACESSO_SEQ", allocationSize = 1)
@XmlRootElement
public class Acesso implements Serializable {
	
	private static final long serialVersionUID = 7511773311010053091L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACESSO_ID")
	private long id;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "USUARIO")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "INSTITUICAO")
	private Instituicao instituicao;

	@ManyToOne
	@JoinColumn(name = "GRUPO")
	private Grupo grupo;

	private Boolean validade;

	public Acesso() {
		super();
	}

	public Acesso(Usuario usuario, Instituicao instituicao, Grupo grupo) {
		super();
		this.usuario = usuario;
		this.instituicao = instituicao;
		this.grupo = grupo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Instituicao getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Boolean getValidade() {
		return validade;
	}

	public void setValidade(Boolean validade) {
		this.validade = validade;
	}

	@Override
	public String toString() {
		return (this.getUsuario().getId() + ", "
				+ this.getInstituicao().getNome() + ", "
				+ this.getGrupo().getId() + ", " + this.getValidade());
	}
}
