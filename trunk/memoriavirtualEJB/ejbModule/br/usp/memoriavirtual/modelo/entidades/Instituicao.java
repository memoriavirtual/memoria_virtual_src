package br.usp.memoriavirtual.modelo.entidades;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;

@Entity
@SequenceGenerator(name = "INSTITUICAO_ID", sequenceName = "INSTITUICAO_SEQ", allocationSize = 1)
public class Instituicao implements Serializable {

	public static enum TiposProtecaoExistente {
		mundial, federalIndividual, federalConjunto, estadualIndividual, estadualConjunto, municipalIndividual, municipalConjunto, decreto, entorno, nenhuma
	}

	public static enum TipoPropriedade {
		publica, privada, mista, outro
	}

	private static final long serialVersionUID = -5996690587044446292L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "INSTITUICAO_ID")
	private long id;
	private String nome;
	private String localidade;
	private String endereco;
	private String cidade;
	private String estado;
	private String cep;
	private String telefone;
	private String caixaPostal;
	private String pais;
	private String email;
	private String url;
	private String identificacaoProprietario;
	private String administradorPropriedade;
	private String latitude;
	private String longitude;
	private String altitude;
	private TipoPropriedade tipoPropriedade;
	private TiposProtecaoExistente protecaoExistente;
	private String legislacaoIncidente;
	private String sinteseHistorica;
	private Boolean validade;
	private Boolean revisao;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private ContainerMultimidia containerMultimidia;

	@OneToMany(mappedBy = "instituicao", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<BemPatrimonial> bens;

	public Instituicao() {
		super();

	}

	@Override
	public boolean equals(Object obj) {
		Instituicao i = (Instituicao) obj;
		if(this.id == i.getId())
			return true;
		else
			return false;
	};
	
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

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCaixaPostal() {
		return caixaPostal;
	}

	public void setCaixaPostal(String caixaPostal) {
		this.caixaPostal = caixaPostal;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIdentificacaoProprietario() {
		return identificacaoProprietario;
	}

	public void setIdentificacaoProprietario(String identificacaoProprietario) {
		this.identificacaoProprietario = identificacaoProprietario;
	}

	public String getAdministradorPropriedade() {
		return administradorPropriedade;
	}

	public void setAdministradorPropriedade(String administradorPropriedade) {
		this.administradorPropriedade = administradorPropriedade;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getAltitude() {
		return altitude;
	}

	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}

	public TipoPropriedade getTipoPropriedade() {
		return tipoPropriedade;
	}

	public void setTipoPropriedade(TipoPropriedade tipoPropriedade) {
		this.tipoPropriedade = tipoPropriedade;
	}

	public TiposProtecaoExistente getProtecaoExistente() {
		return protecaoExistente;
	}

	public void setProtecaoExistente(TiposProtecaoExistente protecaoExistente) {
		this.protecaoExistente = protecaoExistente;
	}

	public String getLegislacaoIncidente() {
		return legislacaoIncidente;
	}

	public void setLegislacaoIncidente(String legislacaoIncidente) {
		this.legislacaoIncidente = legislacaoIncidente;
	}

	public String getSinteseHistorica() {
		return sinteseHistorica;
	}

	public void setSinteseHistorica(String sinteseHistorica) {
		this.sinteseHistorica = sinteseHistorica;
	}

	public Boolean getValidade() {
		return validade;
	}

	public void setValidade(Boolean validade) {
		this.validade = validade;
	}

	public ContainerMultimidia getContainerMultimidia() {
		return containerMultimidia;
	}

	public void setContainerMultimidia(ContainerMultimidia containerMultimidia) {
		this.containerMultimidia = containerMultimidia;
	}

	public Boolean getRevisao() {
		return revisao;
	}

	public void setRevisao(Boolean revisao) {
		this.revisao = revisao;
	}
}
