package br.usp.memoriavirtual.modelo.entidades.bempatrimonial;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn; 
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import br.usp.memoriavirtual.modelo.entidades.Autoria;
import br.usp.memoriavirtual.modelo.entidades.EntidadeComMidia;



@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class BemPatrimonial extends EntidadeComMidia implements Serializable {

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
		this.titulos =   new ArrayList <Titulo>();
		this.autorias = new ArrayList <Autoria>();
		this.intervencoes =  new ArrayList <Intervencao>();
		this.pesquisadores =  new ArrayList <Pesquisador>();
		this.bensrelacionados =  new ArrayList <BemPatrimonial>();
	}
	
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
	
	
	@ElementCollection(fetch=FetchType.EAGER )
	@CollectionTable(name="BEMPATRIMONIAL_DESCRITORES" , joinColumns=@JoinColumn(name="BEMPATRIMONIAL_ID"))
	protected Set<String> descritores;
	
	@ElementCollection(fetch=FetchType.EAGER )
	@CollectionTable(name="BEMPATRIMONIAL_FONTESINFORMACAO", joinColumns=@JoinColumn(name="BEMPATRIMONIAL_ID"))
	protected Set<String> fontesInformacao;
	
	@ElementCollection(fetch=FetchType.EAGER )
	@Embedded
	@CollectionTable(name="BEMPATRIMONIAL_TITULOS", joinColumns=@JoinColumn(name="BEMPATRIMONIAL_ID"))
	protected List<Titulo> titulos;
	
	
	@OneToMany(fetch = FetchType.EAGER , mappedBy="bemPatrimonial", cascade=CascadeType.ALL )
	protected List<Autoria> autorias;
	
	
	@Embedded
	protected  Producao producao;
	
	
	
	@Embedded
	protected  DisponibilidadeUsoProtecao disponibilidadeUsoProtecao;
	
	@Embedded
	protected  HistoricoProcedencia histtoricoProcedencia;
	
	@Embedded
	protected  Diagnostico diagnostico;
	
	@ElementCollection
	@Embedded
	@CollectionTable(name="BEMPATRIMONIAL_INTERVENCOES", joinColumns=@JoinColumn(name="BEMPATRIMONIAL_ID"))
	protected List<Intervencao> intervencoes;
	
	@ElementCollection
	@Embedded
	@CollectionTable(name="BEMPATRIMONIAL_PESQUISADORES", joinColumns=@JoinColumn(name="BEMPATRIMONIAL_ID"))
	protected List<Pesquisador> pesquisadores;
	
	@OneToMany
	@JoinTable(name="BEMPATRIMONIAL_BENSRELACIONADOS",inverseJoinColumns=@JoinColumn(name="BENSRELACIONADOS_ID"), joinColumns=@JoinColumn(name="BEMPATRIMONIAL_ID"))
	protected List<BemPatrimonial> bensrelacionados;
}


