package br.usp.memoriavirtual.modelo.entidades;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "AUTOR_ID", sequenceName = "AUTOR_SEQ", allocationSize = 1)
public class Autor implements Serializable {

	public static enum Atividade {
		adaptador, arquiteto, arranjador, classificador, comentador, compilador, cozinheiro, designer, engenheiro, escritor, escultor, fundador, fotografo, horticulturista, ilustrador, interprete, jardineiro, pintor, reporter, revisor, roteirista
	}

	private static final long serialVersionUID = 238135871232282769L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUTOR_ID")
	private long id;
	private String nome = "";
	private String sobrenome = "";
	private String codinome;
	private Atividade atividade;
	private String nascimento;
	private String obito;

	public Autor() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getCodinome() {
		return codinome;
	}

	public void setCodinome(String codinome) {
		this.codinome = codinome;
	}

	public Atividade getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}

	public String getNascimento() {
		return nascimento;
	}

	public void setNascimento(String nascimento) {
		this.nascimento = nascimento;
	}

	public String getObito() {
		return obito;
	}

	public void setObito(String obito) {
		this.obito = obito;
	}

}
