package org.mvirtual.persistence.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;
import org.mvirtual.persistence.entity.relation.AuthorityHeritage;
import org.mvirtual.persistence.entity.relation.HeritageResearcherResponsible;
import org.mvirtual.persistence.entity.relation.HeritageSubject;
import org.mvirtual.persistence.entity.relation.UserHeritageCatalogue;
import org.mvirtual.persistence.entity.relation.UserHeritageReview;
import org.mvirtual.persistence.entity.relation.embedded.AuthorityHeritageId;

/**
 * Heritage model bean
 * 
 * @author Kiyoshi de Brito Murata <kbmurata@gmail.com>
 * @author Gabriel de Faria Andery <gfandery@gmail.com>
 */
@Entity
@Table(name = "`heritage`")
@Indexed
public class Heritage extends AbstractPersistentObject implements
		java.io.Serializable {
	private static final long serialVersionUID = 27895880527820405L;

	/**
	 * Número atribuído pela instituição ao bem patrimonial.
	 */
	private String controlNumber;

	/**
	 * Título ou nome da obra, documento, bem arquitetônico, objeto museológico.
	 * Para os bens naturais a equivalência para nome popular mais utilizado.
	 * Pode estar previamente estabelecido para o bem patrimonial ou será
	 * construído pelo pesquisador/catalogador ou ainda complementado de acordo
	 * com o plano de organização do acervo da instituição. Ex.: Título já
	 * estabelecido: "Revista Moderna". Título construído: "Xícara do Conde do
	 * Pinhal". Título complementado: "Revista Moderna Fasciculo 2".
	 */
	private String title;

	/**
	 * Obra ou documento traduzido para informar título na língua original, para
	 * bens naturais informar nome científico.
	 */
	private String originalTitle;

	/**
	 * Outro nome da obra ou documento que geralmente aparece acompanhado da
	 * conjunção “ou�? após vírgula; para os bens naturais equivalência para
	 * outros nomes também populares ou regionais.
	 */
	private String alternativeTitle;

	/**
	 * Nome da coleção a que pertence o item inventariado conforme organização
	 * da instituição.
	 */
	private String collection;

	/**
	 * Conjunto de informações que descreve a localização física do bem
	 * patrimonial no acervo.
	 */
	private String physicalLocation;

	/**
	 * Pode ser usado para subtítulos, nomes de séries monográficas,
	 * subcoleções, entre outras situações não previstas nos campos anteriores.
	 */
	private String complementTitle;

	/**
	 * Situação de disponibilidade do bem patrimonial (acervo, evento ou
	 * manutenção). 0 - NÃO SE APLICA 1 - ACERVO (estante, reserva técnica,
	 * etc); 2 - EVENTO (exposição, feira, etc); 3 - MANUTENÇÃO (restauro,
	 * encadernação, pequenos consertos, etc). 4 - BAIXA DO PATRIMÔNIO Obs.:
	 * Considerar bens naturais, imateriais e arquitetônicos como parte do
	 * acervo.
	 */
	private Long situation;

	private String local;

	/**
	 * Latitude GPS.
	 */
	private BigDecimal gpsLatitude;

	/**
	 * Longitude GPS.
	 */
	private BigDecimal gpsLongitude;

	private String date;
	private String editionNumber;
	private String reissueNumber;
	private String otherResponsibilities;
	private String content;
	private String note;
	private String dimensions;
	private String physicalFeatures;
	private String support;
	private String condition;
	private String conditionNotes;
	private String accessConditions;
	private String reproductionConditions;
	private String usage;
	private String protection;
	private String heritageProtectionInstitution;
	private String legislation;
	private String acquisitionType;
	private String acquisitionOrigin;
	private String acquisitionValue;
	private String acquisitionDate;
	private String acquisitionCurrentOwner;
	private String historic;
	private String findingAid;
	private String registryNumber;
	private Institution institution;
	private Boolean reviewed;

	private Set<UserHeritageReview> userHeritageReviews = new HashSet<UserHeritageReview>(
			0);

	private Set<AuthorityHeritage> authorityHeritages = new HashSet<AuthorityHeritage>(
			0);
	private Set<Multimedia> multimedias = new HashSet<Multimedia>(0);
	private Set<Intervention> interventions = new HashSet<Intervention>(0);

	/**
         * 
         */
	private Set<UserHeritageCatalogue> userHeritageCatalogues = new HashSet<UserHeritageCatalogue>(
			0);
	private Set<HeritageResearcherResponsible> heritageResearcherResponsibles = new HashSet<HeritageResearcherResponsible>(
			0);
	private Set<InformationSource> informationSources = new HashSet<InformationSource>(
			0);
	private Set<Descriptor> descriptors = new HashSet<Descriptor>(0);
	private Set<HeritageType> heritageTypes = new HashSet<HeritageType>(0);
	private Set<Heritage> heritagesForIdHeritage = new HashSet<Heritage>(0);
	private Set<Heritage> heritagesForIdRelatedHeritage = new HashSet<Heritage>(
			0);
	private Set<HeritageSubject> heritageSubjects = new HashSet<HeritageSubject>(
			0);

	public Heritage() {
	}

	public Heritage(Institution institution, String title) {
		this.institution = institution;
		this.title = title;
	}

	public Heritage(Institution institution, String controlnumber,
			String title, String originaltitle, String alternativetitle,
			String collection, String physicallocation, String complementtitle,
			Long situation, String local, BigDecimal gpsLatitude,
			BigDecimal gpsLongitude, String date, String editionnumber,
			String reissuenumber, String otherresponsibilities, String content,
			String note, String dimensions, String physicalfeatures,
			String support, String condition, String conditionnotes,
			String accessconditions, String reproductionconditions,
			String usage, String protection,
			String heritageprotectioninstitution, String legislation,
			String acquisitiontype, String acquisitionorigin,
			String acquisitionvalue, String acquisitiondate,
			String acquisitioncurrentowner, String historic, String findingaid,
			String registrynumber) {
		this.institution = institution;
		this.controlNumber = controlnumber;
		this.title = title;
		this.originalTitle = originaltitle;
		this.alternativeTitle = alternativetitle;
		this.collection = collection;
		this.physicalLocation = physicallocation;
		this.complementTitle = complementtitle;
		this.situation = situation;
		this.local = local;
		this.gpsLatitude = gpsLatitude;
		this.gpsLongitude = gpsLongitude;
		this.date = date;
		this.editionNumber = editionnumber;
		this.reissueNumber = reissuenumber;
		this.otherResponsibilities = otherresponsibilities;
		this.content = content;
		this.note = note;
		this.dimensions = dimensions;
		this.physicalFeatures = physicalfeatures;
		this.support = support;
		this.condition = condition;
		this.conditionNotes = conditionnotes;
		this.accessConditions = accessconditions;
		this.reproductionConditions = reproductionconditions;
		this.usage = usage;
		this.protection = protection;
		this.heritageProtectionInstitution = heritageprotectioninstitution;
		this.legislation = legislation;
		this.acquisitionType = acquisitiontype;
		this.acquisitionOrigin = acquisitionorigin;
		this.acquisitionValue = acquisitionvalue;
		this.acquisitionDate = acquisitiondate;
		this.acquisitionCurrentOwner = acquisitioncurrentowner;
		this.historic = historic;
		this.findingAid = findingaid;
		this.registryNumber = registrynumber;
	}

	public Heritage(Institution institution, String controlnumber,
			String title, String originaltitle, String alternativetitle,
			String collection, String physicallocation, String complementtitle,
			Long situation, String local, BigDecimal gpsLatitude,
			BigDecimal gpsLongitude, String date, String editionnumber,
			String reissuenumber, String otherresponsibilities, String content,
			String note, String dimensions, String physicalfeatures,
			String support, String condition, String conditionnotes,
			String accessconditions, String reproductionconditions,
			String usage, String protection,
			String heritageprotectioninstitution, String legislation,
			String acquisitiontype, String acquisitionorigin,
			String acquisitionvalue, String acquisitiondate,
			String acquisitioncurrentowner, String historic, String findingaid,
			String registrynumber, Set<UserHeritageReview> userHeritageReviews,
			Set<AuthorityHeritage> authorityHeritages,
			Set<Multimedia> multimedias, Set<Intervention> interventions,
			Set<UserHeritageCatalogue> userHeritageCatalogues,
			Set<HeritageResearcherResponsible> heritageResearcherresponsibles,
			Set<InformationSource> informationsources,
			Set<Descriptor> descriptors, Set<HeritageType> heritagetypes,
			Set<Heritage> heritagesForIdHeritage,
			Set<Heritage> heritagesForIdRelatedHeritage,
			Set<HeritageSubject> heritageSubjects) {
		this.institution = institution;
		this.controlNumber = controlnumber;
		this.title = title;
		this.originalTitle = originaltitle;
		this.alternativeTitle = alternativetitle;
		this.collection = collection;
		this.physicalLocation = physicallocation;
		this.complementTitle = complementtitle;
		this.situation = situation;
		this.local = local;
		this.gpsLatitude = gpsLatitude;
		this.gpsLongitude = gpsLongitude;
		this.date = date;
		this.editionNumber = editionnumber;
		this.reissueNumber = reissuenumber;
		this.otherResponsibilities = otherresponsibilities;
		this.content = content;
		this.note = note;
		this.dimensions = dimensions;
		this.physicalFeatures = physicalfeatures;
		this.support = support;
		this.condition = condition;
		this.conditionNotes = conditionnotes;
		this.accessConditions = accessconditions;
		this.reproductionConditions = reproductionconditions;
		this.usage = usage;
		this.protection = protection;
		this.heritageProtectionInstitution = heritageprotectioninstitution;
		this.legislation = legislation;
		this.acquisitionType = acquisitiontype;
		this.acquisitionOrigin = acquisitionorigin;
		this.acquisitionValue = acquisitionvalue;
		this.acquisitionDate = acquisitiondate;
		this.acquisitionCurrentOwner = acquisitioncurrentowner;
		this.historic = historic;
		this.findingAid = findingaid;
		this.registryNumber = registrynumber;
		this.userHeritageReviews = userHeritageReviews;
		this.authorityHeritages = authorityHeritages;
		this.multimedias = multimedias;
		this.interventions = interventions;
		this.userHeritageCatalogues = userHeritageCatalogues;
		this.heritageResearcherResponsibles = heritageResearcherresponsibles;
		this.informationSources = informationsources;
		this.descriptors = descriptors;
		this.heritageTypes = heritagetypes;
		this.heritagesForIdHeritage = heritagesForIdHeritage;
		this.heritagesForIdRelatedHeritage = heritagesForIdRelatedHeritage;
		this.heritageSubjects = heritageSubjects;
	}

	/*
	 * @Transient
	 * 
	 * @DocumentId public Long getID() { return super.getId(); }
	 */

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "`id_institution`", nullable = false)
	@IndexedEmbedded(depth = 1)
	public Institution getInstitution() {
		return this.institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	@NaturalId(mutable = true)
	@Column(name = "`controlnumber`", unique = true)
	@Field
	@Type(type = "text")
	public String getControlNumber() {
		return this.controlNumber;
	}

	public void setControlNumber(String controlnumber) {
		this.controlNumber = controlnumber;
	}

	@NaturalId(mutable = true)
	@Column(name = "`title`", nullable = false)
	@Fields( {
			@Field(index = Index.TOKENIZED),
			@Field(name = "heritageTitle_forSort", index = Index.UN_TOKENIZED, store = Store.YES) })
	@Type(type = "text")
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "`originaltitle`")
	@Field
	@Type(type = "text")
	public String getOriginalTitle() {
		return this.originalTitle;
	}

	public void setOriginalTitle(String originaltitle) {
		this.originalTitle = originaltitle;
	}

	@Column(name = "`alternativetitle`")
	@Field
	@Type(type = "text")
	public String getAlternativeTitle() {
		return this.alternativeTitle;
	}

	public void setAlternativeTitle(String alternativetitle) {
		this.alternativeTitle = alternativetitle;
	}

	@Column(name = "`collection`")
	@Field
	@Type(type = "text")
	public String getCollection() {
		return this.collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	@Column(name = "`physicallocation`")
	@Field
	@Type(type = "text")
	public String getPhysicalLocation() {
		return this.physicalLocation;
	}

	public void setPhysicalLocation(String physicallocation) {
		this.physicalLocation = physicallocation;
	}

	@Column(name = "`complementtitle`")
	@Field
	@Type(type = "text")
	public String getComplementTitle() {
		return this.complementTitle;
	}

	public void setComplementTitle(String complementtitle) {
		this.complementTitle = complementtitle;
	}

	@Column(name = "`situation`")
	@Field
	@Type(type = "long")
	public Long getSituation() {
		return this.situation;
	}

	public void setSituation(Long situation) {
		this.situation = situation;
	}

	@Column(name = "`local`")
	@Field
	@Type(type = "text")
	public String getLocal() {
		return this.local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	/**
	 * Coordenada GPS - Latitude.
	 * 
	 * @return Latitude.
	 */
	@Column(name = "GPS_LATITUDE")
	@Field
	@Type(type = "big_decimal")
	public BigDecimal getGpsLatitude() {
		return gpsLatitude;
	}

	/**
	 * Coordenada GPS - Latitude.
	 * 
	 * @param gpsLatitude
	 *            Latitude.
	 */
	public void setGpsLatitude(BigDecimal gpsLatitude) {
		this.gpsLatitude = gpsLatitude;
	}

	/**
	 * Coordenada GPS - Longitude
	 * 
	 * @return Longitude.
	 */
	@Column(name = "GPS_LONGITUDE")
	@Field
	@Type(type = "big_decimal")
	public BigDecimal getGpsLongitude() {
		return gpsLongitude;
	}

	/**
	 * Coordenada GPS - Longitude.
	 * 
	 * @param gpsLongitude
	 *            Longitude.
	 */
	public void setGpsLongitude(BigDecimal gpsLongitude) {
		this.gpsLongitude = gpsLongitude;
	}

	@Column(name = "`date`")
	@Field
	@Type(type = "text")
	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Column(name = "`editionnumber`")
	@Field
	@Type(type = "text")
	public String getEditionNumber() {
		return this.editionNumber;
	}

	public void setEditionNumber(String editionnumber) {
		this.editionNumber = editionnumber;
	}

	@Column(name = "`reissuenumber`")
	@Field
	@Type(type = "text")
	public String getReissueNumber() {
		return this.reissueNumber;
	}

	public void setReissueNumber(String reissuenumber) {
		this.reissueNumber = reissuenumber;
	}

	@Column(name = "`otherresponsibilities`")
	@Field
	@Type(type = "text")
	public String getOtherResponsibilities() {
		return this.otherResponsibilities;
	}

	public void setOtherResponsibilities(String otherresponsibilities) {
		this.otherResponsibilities = otherresponsibilities;
	}

	@Column(name = "`content`")
	@Field
	@Type(type = "text")
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "`note`")
	@Field
	@Type(type = "text")
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "`dimensions`")
	@Field
	@Type(type = "text")
	public String getDimensions() {
		return this.dimensions;
	}

	public void setDimensions(String dimensions) {
		this.dimensions = dimensions;
	}

	@Column(name = "`physicalfeatures`")
	@Field
	@Type(type = "text")
	public String getPhysicalFeatures() {
		return this.physicalFeatures;
	}

	public void setPhysicalFeatures(String physicalfeatures) {
		this.physicalFeatures = physicalfeatures;
	}

	@Column(name = "`support`")
	@Field
	@Type(type = "text")
	public String getSupport() {
		return this.support;
	}

	public void setSupport(String support) {
		this.support = support;
	}

	@Column(name = "`condition`")
	@Field
	@Type(type = "text")
	public String getCondition() {
		return this.condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	@Column(name = "`conditionnotes`")
	@Field
	@Type(type = "text")
	public String getConditionNotes() {
		return this.conditionNotes;
	}

	public void setConditionNotes(String conditionnotes) {
		this.conditionNotes = conditionnotes;
	}

	@Column(name = "`accessconditions`")
	@Field
	@Type(type = "text")
	public String getAccessConditions() {
		return this.accessConditions;
	}

	public void setAccessConditions(String accessconditions) {
		this.accessConditions = accessconditions;
	}

	@Column(name = "`reproductionconditions`")
	@Field
	@Type(type = "text")
	public String getReproductionConditions() {
		return this.reproductionConditions;
	}

	public void setReproductionConditions(String reproductionconditions) {
		this.reproductionConditions = reproductionconditions;
	}

	@Column(name = "`usage`")
	@Field
	@Type(type = "text")
	public String getUsage() {
		return this.usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	@Column(name = "`protection`")
	@Field
	@Type(type = "text")
	public String getProtection() {
		return this.protection;
	}

	public void setProtection(String protection) {
		this.protection = protection;
	}

	@Column(name = "`heritageprotectioninstitution`")
	@Field
	@Type(type = "text")
	public String getHeritageProtectionInstitution() {
		return this.heritageProtectionInstitution;
	}

	public void setHeritageProtectionInstitution(
			String heritageprotectioninstitution) {
		this.heritageProtectionInstitution = heritageprotectioninstitution;
	}

	@Column(name = "`legislation`")
	@Field
	@Type(type = "text")
	public String getLegislation() {
		return this.legislation;
	}

	public void setLegislation(String legislation) {
		this.legislation = legislation;
	}

	@Column(name = "`acquisitiontype`")
	@Field
	@Type(type = "text")
	public String getAcquisitionType() {
		return this.acquisitionType;
	}

	public void setAcquisitionType(String acquisitiontype) {
		this.acquisitionType = acquisitiontype;
	}

	@Column(name = "`acquisitionorigin`")
	@Field
	@Type(type = "text")
	public String getAcquisitionOrigin() {
		return this.acquisitionOrigin;
	}

	public void setAcquisitionOrigin(String acquisitionorigin) {
		this.acquisitionOrigin = acquisitionorigin;
	}

	@Column(name = "`acquisitionvalue`")
	@Type(type = "text")
	public String getAcquisitionValue() {
		return this.acquisitionValue;
	}

	public void setAcquisitionValue(String acquisitionvalue) {
		this.acquisitionValue = acquisitionvalue;
	}

	@Column(name = "`acquisitiondate`")
	@Field
	@Type(type = "text")
	public String getAcquisitionDate() {
		return this.acquisitionDate;
	}

	public void setAcquisitionDate(String acquisitiondate) {
		this.acquisitionDate = acquisitiondate;
	}

	@Column(name = "`acquisitioncurrentowner`")
	@Field
	@Type(type = "text")
	public String getAcquisitionCurrentOwner() {
		return this.acquisitionCurrentOwner;
	}

	public void setAcquisitionCurrentOwner(String acquisitioncurrentowner) {
		this.acquisitionCurrentOwner = acquisitioncurrentowner;
	}

	@Column(name = "`historic`")
	@Field
	@Type(type = "text")
	public String getHistoric() {
		return this.historic;
	}

	public void setHistoric(String historic) {
		this.historic = historic;
	}

	@Column(name = "`findingaid`")
	@Type(type = "text")
	public String getFindingAid() {
		return this.findingAid;
	}

	public void setFindingAid(String findingaid) {
		this.findingAid = findingaid;
	}

	@Column(name = "`registrynumber`")
	@Type(type = "text")
	public String getRegistryNumber() {
		return this.registryNumber;
	}

	public void setRegistryNumber(String registrynumber) {
		this.registryNumber = registrynumber;
	}

	@Column(name = "`reviewed`")
	public Boolean isReviewed() {
		return this.reviewed;
	}

	public void setReviewed(Boolean reviewed) {
		this.reviewed = reviewed;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "heritage")
	public Set<UserHeritageReview> getUserHeritageReviews() {
		return this.userHeritageReviews;
	}

	public void setUserHeritageReviews(
			Set<UserHeritageReview> userHeritageReviews) {
		this.userHeritageReviews = userHeritageReviews;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "heritage")
	@IndexedEmbedded(depth = 2)
	public Set<AuthorityHeritage> getAuthorityHeritages() {
		return this.authorityHeritages;
	}

	public void setAuthorityHeritages(Set<AuthorityHeritage> authorityHeritages) {
		this.authorityHeritages = authorityHeritages;
	}

	@ContainedIn
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "`multimedia_heritage`", joinColumns = { @JoinColumn(name = "`id_heritage`", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "`id_multimedia`", nullable = false, updatable = false) })
	public Set<Multimedia> getMultimedias() {
		return this.multimedias;
	}

	public void setMultimedias(Set<Multimedia> multimedias) {
		this.multimedias = multimedias;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "`intervention_heritage`", joinColumns = { @JoinColumn(name = "`id_heritage`", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "`id_intervention`", nullable = false, updatable = false) })
	@IndexedEmbedded(depth = 1)
	public Set<Intervention> getInterventions() {
		return this.interventions;
	}

	public void setInterventions(Set<Intervention> interventions) {
		this.interventions = interventions;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "heritage")
	public Set<UserHeritageCatalogue> getUserHeritageCatalogues() {
		return this.userHeritageCatalogues;
	}

	public void setUserHeritageCatalogues(
			Set<UserHeritageCatalogue> userHeritageCatalogues) {
		this.userHeritageCatalogues = userHeritageCatalogues;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "heritage")
	public Set<HeritageResearcherResponsible> getHeritageResearcherResponsibles() {
		return this.heritageResearcherResponsibles;
	}

	public void setHeritageResearcherResponsibles(
			Set<HeritageResearcherResponsible> heritageResearcherresponsibles) {
		this.heritageResearcherResponsibles = heritageResearcherresponsibles;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "`informationsource_heritage`", joinColumns = { @JoinColumn(name = "`id_heritage`", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "`id_informationsource`", nullable = false, updatable = false) })
	public Set<InformationSource> getInformationSources() {
		return this.informationSources;
	}

	public void setInformationSources(Set<InformationSource> informationsources) {
		this.informationSources = informationsources;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "`descriptor_heritage`", joinColumns = { @JoinColumn(name = "`id_heritage`", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "`id_descriptor`", nullable = false, updatable = false) })
	@IndexedEmbedded(depth = 1)
	public Set<Descriptor> getDescriptors() {
		return this.descriptors;
	}

	public void setDescriptors(Set<Descriptor> descriptors) {
		this.descriptors = descriptors;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "`heritagetype_heritage`", joinColumns = { @JoinColumn(name = "`id_heritage`", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "`id_heritagetype`", nullable = false, updatable = false) })
	@IndexedEmbedded(depth = 1)
	public Set<HeritageType> getHeritageTypes() {
		return this.heritageTypes;
	}

	public void setHeritageTypes(Set<HeritageType> heritagetypes) {
		this.heritageTypes = heritagetypes;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "`heritage_heritage`", joinColumns = { @JoinColumn(name = "`id_related_heritage`", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "`id_heritage`", nullable = false, updatable = false) })
	public Set<Heritage> getRelatedHeritagesFromThis() {
		return this.heritagesForIdHeritage;
	}

	public void setRelatedHeritagesFromThis(Set<Heritage> heritagesForIdHeritage) {
		this.heritagesForIdHeritage = heritagesForIdHeritage;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "`heritage_heritage`", joinColumns = { @JoinColumn(name = "`id_heritage`", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "`id_related_heritage`", nullable = false, updatable = false) })
	public Set<Heritage> getRelatedHeritagesToThis() {
		return this.heritagesForIdRelatedHeritage;
	}

	public void setRelatedHeritagesToThis(
			Set<Heritage> heritagesForIdRelatedHeritage) {
		this.heritagesForIdRelatedHeritage = heritagesForIdRelatedHeritage;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "heritage")
	@IndexedEmbedded(depth = 2)
	public Set<HeritageSubject> getHeritageSubjects() {
		return this.heritageSubjects;
	}

	public void setHeritageSubjects(Set<HeritageSubject> heritageSubjects) {
		this.heritageSubjects = heritageSubjects;
	}

	/**
	 * Adds a authority and cares all about relation table.
	 * 
	 * @param authority
	 *            Authority Data
	 * @param authorshipType
	 *            Type of authorship
	 * @param function
	 *            Function of author related to heritage.
	 */
	public void addAuthority(Authority authority, String authorshipType,
			String function) {

		AuthorityHeritageId authorityHeritageId = new AuthorityHeritageId(this
				.getId(), authority.getId(), authorshipType);

		AuthorityHeritage authorityHeritage = new AuthorityHeritage(
				authorityHeritageId, this, authority, function);

		this.authorityHeritages.add(authorityHeritage);
	}

	public void addDescriptor(Descriptor descriptor) {
		this.descriptors.add(descriptor);
	}

	@Override
	@Transient
	public String getRepr() {
		return String.format("%s", title);
	}
}
