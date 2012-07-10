package br.usp.memoriavirtual.modelo.entidades.bempatrimonial;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import br.usp.memoriavirtual.modelo.entidades.Autoria;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@SequenceGenerator(name = "BEMPATRIMONIAL_ID", sequenceName = "BEMPATRIMONIAL_SEQ", allocationSize = 1)
public class BemPatrimonial implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3790813768470746267L;
	
	/**
	 * 
	 */
	public BemPatrimonial() {
		super();
		this.descritores = new TreeSet <String>();
		this.fontesInformacao = new TreeSet <String>();
		this.titulos =   new TreeSet <Titulo>();
		this.autorias = new TreeSet <Autoria>();
		this.audioVisuals =   new TreeSet <AudioVisual>();
		this.intervencoes =  new TreeSet <Intervencao>();
		this.pesquisadores =  new TreeSet <Pesquisador>();
		this.bensrelacionados =  new TreeSet <BemPatrimonial>();
	}
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BEMPATRIMONIAL_ID")
	protected long id;
	protected boolean externo;
	protected String tipoDoBemPatrimonial;
	protected String numeroDeRegistro;
	protected String colecao;
	protected String localizacaoFisica;
	protected String latitude;
	protected String longitude;
	protected String caracteristicasFisTecExec;
	protected String conteudo;
	protected String meioDeAcesso;
	@ElementCollection
	protected Set<String> descritores;
	@ElementCollection
	protected Set<String> fontesInformacao;
	
	@ElementCollection
	@Embedded
	@OneToMany
	protected Set<Titulo> titulos;
	
	@ElementCollection
	@OneToMany
	protected Set<Autoria> autorias;
	
	
	@Embedded
	@OneToOne
	protected  Producao producao;
	
	@ElementCollection
	@Embedded
	@OneToMany
	protected Set<AudioVisual> audioVisuals;
	
	@Embedded
	protected  DisponibilidadeUsoProtecao disponibilidadeUsoProtecao;
	
	@Embedded
	@OneToOne
	protected  HistoricoProcedencia histtoricoProcedencia;
	
	@Embedded
	@OneToOne
	protected  Diagnostico diagnostico;
	
	@ElementCollection
	@Embedded
	@OneToMany
	protected Set<Intervencao> intervencoes;
	
	@ElementCollection
	@Embedded
	@OneToMany
	protected Set<Pesquisador> pesquisadores;
	
	@ElementCollection
	@Embedded
	@OneToMany
	protected Set<BemPatrimonial> bensrelacionados;
}


