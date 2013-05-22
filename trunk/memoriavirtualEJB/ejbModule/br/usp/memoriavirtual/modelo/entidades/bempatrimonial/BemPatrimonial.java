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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import br.usp.memoriavirtual.modelo.entidades.Autoria;
import br.usp.memoriavirtual.modelo.entidades.ContainerMultimidia;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;

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
	public enum TipoDoBemPatrimonial{
		NORMAL,ARQUEOLOGICO,ARQUITETONICO,NATURAL;
	}
	
	
	public BemPatrimonial() {
		super();
		this.descritores = new TreeSet<Descritor>();
		this.assuntos = new TreeSet<Assunto>(); 
		this.fontesInformacao = new ArrayList<String>();
		this.titulos = new ArrayList<Titulo>();
		this.autorias = new ArrayList<Autoria>();
		this.intervencoes = new ArrayList<Intervencao>();
		this.pesquisadores = new ArrayList<Pesquisador>();
		this.bensrelacionados = new ArrayList<BemPatrimonial>();

	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BEMPATRIMONIAL_ID")
	protected long id;
	@ManyToOne
	protected Instituicao instituicao = new Instituicao();
	protected boolean externo = false;
	protected String tipoDoBemPatrimonial = "";
	protected String numeroDeRegistro = "";
	protected String colecao = "";
	protected String localizacaoFisica = "";
	protected String latitude = "";
	protected String longitude = "";
	protected String tituloPrincipal = "";
	protected String caracteristicasFisTecExec = "";
	protected String conteudo = "";
	protected String meioDeAcesso = "";
	protected String idMidia = "";
	protected String Complemento = "";

	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER )
	@JoinTable(name="BEMPATRIMONIAL_DESCRITOR",
    joinColumns=
        @JoinColumn(name="BEMPATRIMONIAL_ID"),
    inverseJoinColumns=
        @JoinColumn(name="DESCRITOR_ID"))
	protected Set<Descritor> descritores;
	
	
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(name="BEMPATRIMONIAL_ASSUNTO",
    joinColumns=
        @JoinColumn(name="BEMPATRIMONIAL_ID"),
    inverseJoinColumns=
        @JoinColumn(name="ASSUNTO_ID"))
	protected Set<Assunto> assuntos;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "BEMPATRIMONIAL_FONTESINFORMACAO", joinColumns = @JoinColumn(name = "BEMPATRIMONIAL_ID"))
	protected List<String> fontesInformacao;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "bemPatrimonial", cascade = CascadeType.ALL)
	// @ElementCollection(fetch = FetchType.EAGER)
	// @CollectionTable(name = "BEMPATRIMONIAL_TITULOS", joinColumns =
	// @JoinColumn(name = "BEMPATRIMONIAL_ID"))
	protected List<Titulo> titulos;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "bemPatrimonial", cascade = CascadeType.ALL)
	protected List<Autoria> autorias;

	@Embedded
	protected Producao producao = new Producao("", "", "", "");

	@Embedded
	protected DisponibilidadeUsoProtecao disponibilidadeUsoProtecao = new DisponibilidadeUsoProtecao(
			"", "", "", "", "", "", "", "");

	@Embedded
	protected HistoricoProcedencia historicoProcedencia = new HistoricoProcedencia(
			"", "", "", "", "", "");

	@Embedded
	protected Diagnostico diagnostico = new Diagnostico("", "", "");

	@ElementCollection( fetch = FetchType.EAGER)
	@CollectionTable(name = "BEMPATRIMONIAL_INTERVENCOES", joinColumns = @JoinColumn(name = "BEMPATRIMONIAL_ID"))
	protected List<Intervencao> intervencoes;

	@ElementCollection( fetch = FetchType.EAGER)
	@CollectionTable(name = "BEMPATRIMONIAL_PESQUISADORES", joinColumns = @JoinColumn(name = "BEMPATRIMONIAL_ID"))
	protected List<Pesquisador> pesquisadores;

	@OneToMany( fetch = FetchType.EAGER)
	@JoinTable(name = "BEMPATRIMONIAL_BENSRELACIONADOS", inverseJoinColumns = @JoinColumn(name = "BENSRELACIONADOS_ID"), joinColumns = @JoinColumn(name = "BEMPATRIMONIAL_ID"))
	protected List<BemPatrimonial> bensrelacionados;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	protected ContainerMultimidia containerMultimidia = new ContainerMultimidia();

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the externo
	 */
	public boolean isExterno() {
		return externo;
	}

	/**
	 * @param externo
	 *            the externo to set
	 */
	public void setExterno(boolean externo) {
		this.externo = externo;
	}

	/**
	 * @return the tipoDoBemPatrimonial
	 */
	public String getTipoDoBemPatrimonial() {
		return tipoDoBemPatrimonial;
	}

	/**
	 * @param tipoDoBemPatrimonial
	 *            the tipoDoBemPatrimonial to set
	 */
	public void setTipoDoBemPatrimonial(String tipoDoBemPatrimonial) {
		this.tipoDoBemPatrimonial = tipoDoBemPatrimonial;
	}

	/**
	 * @return the numeroDeRegistro
	 */
	public String getNumeroDeRegistro() {
		return numeroDeRegistro;
	}

	/**
	 * @param numeroDeRegistro
	 *            the numeroDeRegistro to set
	 */
	public void setNumeroDeRegistro(String numeroDeRegistro) {
		this.numeroDeRegistro = numeroDeRegistro;
	}

	/**
	 * @return the colecao
	 */
	public String getColecao() {
		return colecao;
	}

	/**
	 * @param colecao
	 *            the colecao to set
	 */
	public void setColecao(String colecao) {
		this.colecao = colecao;
	}

	/**
	 * @return the localizacaoFisica
	 */
	public String getLocalizacaoFisica() {
		return localizacaoFisica;
	}

	/**
	 * @param localizacaoFisica
	 *            the localizacaoFisica to set
	 */
	public void setLocalizacaoFisica(String localizacaoFisica) {
		this.localizacaoFisica = localizacaoFisica;
	}

	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the caracteristicasFisTecExec
	 */
	public String getCaracteristicasFisTecExec() {
		return caracteristicasFisTecExec;
	}

	/**
	 * @param caracteristicasFisTecExec
	 *            the caracteristicasFisTecExec to set
	 */
	public void setCaracteristicasFisTecExec(String caracteristicasFisTecExec) {
		this.caracteristicasFisTecExec = caracteristicasFisTecExec;
	}

	/**
	 * @return the conteudo
	 */
	public String getConteudo() {
		return conteudo;
	}

	/**
	 * @param conteudo
	 *            the conteudo to set
	 */
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	/**
	 * @return the meioDeAcesso
	 */
	public String getMeioDeAcesso() {
		return meioDeAcesso;
	}

	/**
	 * @param meioDeAcesso
	 *            the meioDeAcesso to set
	 */
	public void setMeioDeAcesso(String meioDeAcesso) {
		this.meioDeAcesso = meioDeAcesso;
	}

	/**
	 * @return the fontesInformacao
	 */
	public List<String> getFontesInformacao() {
		return fontesInformacao;
	}

	/**
	 * @return the autorias
	 */
	public List<Autoria> getAutorias() {
		return autorias;
	}

	/**
	 * @param autorias
	 *            the autorias to set
	 */
	public void setAutorias(List<Autoria> autorias) {
		this.autorias = autorias;
	}

	/**
	 * @return the producao
	 */
	public Producao getProducao() {
		return producao;
	}

	/**
	 * @param producao
	 *            the producao to set
	 */
	public void setProducao(Producao producao) {
		this.producao = producao;
	}

	public String getComplemento() {
		return Complemento;
	}

	public void setComplemento(String complemento) {
		Complemento = complemento;
	}

	/**
	 * @return the disponibilidadeUsoProtecao
	 */
	public DisponibilidadeUsoProtecao getDisponibilidadeUsoProtecao() {
		return disponibilidadeUsoProtecao;
	}

	/**
	 * @param disponibilidadeUsoProtecao
	 *            the disponibilidadeUsoProtecao to set
	 */
	public void setDisponibilidadeUsoProtecao(
			DisponibilidadeUsoProtecao disponibilidadeUsoProtecao) {
		this.disponibilidadeUsoProtecao = disponibilidadeUsoProtecao;
	}

	/**
	 * @return the historicoProcedencia
	 */
	public HistoricoProcedencia getHistoricoProcedencia() {
		return historicoProcedencia;
	}

	/**
	 * @param historicoProcedencia
	 *            the historicoProcedencia to set
	 */
	public void setHistoricoProcedencia(
			HistoricoProcedencia historicoProcedencia) {
		this.historicoProcedencia = historicoProcedencia;
	}

	/**
	 * @return the diagnostico
	 */
	public Diagnostico getDiagnostico() {
		return diagnostico;
	}

	/**
	 * @param diagnostico
	 *            the diagnostico to set
	 */
	public void setDiagnostico(Diagnostico diagnostico) {
		this.diagnostico = diagnostico;
	}

	/**
	 * @return the descritores
	 */
	public Set<Descritor> getDescritores() {
		return descritores;
	}

	/**
	 * @return the titulos
	 */
	public List<Titulo> getTitulos() {
		return titulos;
	}

	/**
	 * @return the intervencoes
	 */
	public List<Intervencao> getIntervencoes() {
		return intervencoes;
	}

	/**
	 * @return the pesquisadores
	 */
	public List<Pesquisador> getPesquisadores() {
		return pesquisadores;
	}

	/**
	 * @return the bensrelacionados
	 */
	public List<BemPatrimonial> getBensrelacionados() {
		return bensrelacionados;
	}

	/**
	 * @return the containerMultimidia
	 */
	public ContainerMultimidia getContainerMultimidia() {
		return containerMultimidia;
	}

	/**
	 * @param containerMultimidia
	 *            the containerMultimidia to set
	 */
	public void setContainerMultimidia(ContainerMultimidia containerMultimidia) {
		this.containerMultimidia = containerMultimidia;
	}

	public Instituicao getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

	public String getIdMidia() {
		return idMidia;
	}

	public void setIdMidia(String idMidia) {
		this.idMidia = idMidia;
	}

	public void adicionarTitulo(Titulo titulo) {
		this.titulos.add(titulo);
	}

	public void setDescritores(Set<Descritor> descritores) {
		this.descritores = descritores;
	}

	public void setFontesInformacao(List<String> fontesInformacao) {
		this.fontesInformacao = fontesInformacao;
	}

	public void setTitulos(List<Titulo> titulos) {
		this.titulos = titulos;
	}

	public void setIntervencoes(List<Intervencao> intervencoes) {
		this.intervencoes = intervencoes;
	}

	public void setPesquisadores(List<Pesquisador> pesquisadores) {
		this.pesquisadores = pesquisadores;
	}

	public void setBensrelacionados(List<BemPatrimonial> bensrelacionados) {
		this.bensrelacionados = bensrelacionados;
	}

	public Set<Assunto> getAssuntos() {
		return assuntos;
	}

	public void setAssuntos(Set<Assunto> assuntos) {
		this.assuntos = assuntos;
	}

	public String getTituloPrincipal() {
		return tituloPrincipal;
	}

	public void setTituloPrincipal(String tituloPrincipal) {
		this.tituloPrincipal = tituloPrincipal;
	}

}
