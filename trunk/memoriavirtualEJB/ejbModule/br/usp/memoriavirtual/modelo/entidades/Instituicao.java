package br.usp.memoriavirtual.modelo.entidades;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class Instituicao  extends EntidadeComMidia implements Serializable {
	
	
	private static final long serialVersionUID = -5996690587044446292L;

	
	/**
	 * Serial Version UID
	 */
   
	
	private String nome;
	private String localidade;
	private String endereco;
	private String cidade;
	//@Pattern(regexp = "[A-Z]{2}", message = "Estado no formato incorreto (ex: SP e não sp)")
	private String estado;
	// @Pattern(regexp = "[0-9]{11}")
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
	private String tipoPropriedade;
	private String protecaoExistente;
	private String legislacaoIncidente;
	private String sinteseHistorica;
	private Boolean validade;
	

	/**
	 * Construtor padrão
	 */
	public Instituicao() {
		super();

	}

	/**
	 * Construtor conveniente
	 * 
	 * @param Nome
	 *            Nome da insituição
	 * @param Local
	 *            Localização
	 * @param End
	 *            Endereço
	 * @param Cidade
	 * @param Est
	 *            Estado
	 * @param Cep
	 * @param Tel
	 *            Telefone
	 */
	public Instituicao(String Nome, String Local, String End, String Cidade, String Est,String pais, String Cep, String Tel,
			String caixaPostal, String email, String url,String identificacaoProprietario, String administradorPropriedade,
			String latitude, String longitude, String altitude,String tipoPropriedade, String protecaoExistente, String legislacao , String sintese) {
		super();
		this.nome = Nome;
		this.localidade = Local;
		this.endereco = End;
		this.cidade = Cidade;
		this.estado = Est;
		this.pais = pais;
		this.cep = Cep;
		this.telefone = Tel;
		this.caixaPostal = caixaPostal;
		this.email=email;
		this.url=url;
		this.identificacaoProprietario=identificacaoProprietario;
		this.administradorPropriedade = administradorPropriedade;
		this.latitude=latitude;
		this.longitude=longitude;
		this.altitude=altitude;
		this.tipoPropriedade=tipoPropriedade;
		this.protecaoExistente=protecaoExistente;
		this.legislacaoIncidente = legislacao;
		this.sinteseHistorica = sintese;
	}
	/**
	 * Cosntrutor com id.
	 * 
	 */
	public Instituicao(long id, String Nome, String Local, String End, String Cidade, String Est, String pais,String Cep, String Tel,
			String caixaPostal, String email, String url,String identificacaoProprietario, String administradorPropriedade,
			String latitude, String longitude, String altitude,String tipoPropriedade, String protecaoExistente ,String legislacao , String sintese) {
		super();
		this.id = id;
		this.nome = Nome;
		this.localidade = Local;
		this.endereco = End;
		this.cidade = Cidade;
		this.estado = Est;
		this.pais = pais;
		this.cep = Cep;
		this.telefone = Tel;
		this.caixaPostal = caixaPostal;
		this.email=email;
		this.url=url;
		this.identificacaoProprietario=identificacaoProprietario;
		this.administradorPropriedade = administradorPropriedade;
		this.latitude=latitude;
		this.longitude=longitude;
		this.altitude=altitude;
		this.tipoPropriedade=tipoPropriedade;
		this.protecaoExistente=protecaoExistente;
		this.legislacaoIncidente = legislacao;
		this.sinteseHistorica = sintese;

	}
	
	/**
	 * @return the legislacaoIncidente
	 */
	public String getLegislacaoIncidente() {
		return legislacaoIncidente;
	}

	/**
	 * @param legislacaoIncidente the legislacaoIncidente to set
	 */
	public void setLegislacaoIncidente(String legislacaoIncidente) {
		this.legislacaoIncidente = legislacaoIncidente;
	}

	/**
	 * @return O nome da instituição
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            Define o nome da instituição
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}


	/**
	 * @return O endereco da instituição
	 */
	public String getEndereco() {
		return endereco;
	}

	/**
	 * @param endereco
	 *            Define o endereco da instituição
	 */
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	/**
	 * @return A cidade da instituição
	 */
	public String getCidade() {
		return cidade;
	}

	/**
	 * @param cidade
	 *            Define a cidade da instituição
	 */
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	/**
	 * @return O estado(UF) da instituição
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            Define o estado(UF) da instituição
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return O cep da instituição
	 */
	public String getCep() {
		return cep;
	}

	/**
	 * @param cep
	 *            Define o cep da instituição
	 */
	public void setCep(String cep) {
		this.cep = cep;
	}

	/**
	 * @return O telefone da instituição
	 */
	public String getTelefone() {
		return telefone;
	}

	/**
	 * @param telefone
	 *            Define o telefone da instituição
	 */
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	/**
	 * @return the validade
	 */
	public Boolean getValidade() {
		return validade;
	}

	/**
	 * @param validade
	 *            the validade to set
	 */
	public void setValidade(Boolean validade) {
		this.validade = validade;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return the caixaPostal
	 */
	public String getCaixaPostal() {
		return caixaPostal;
	}

	/**
	 * @param caixaPostal the caixaPostal to set
	 */
	public void setCaixaPostal(String caixaPostal) {
		this.caixaPostal = caixaPostal;
	}

	/**
	 * @return the pais
	 */
	public String getPais() {
		return pais;
	}

	/**
	 * @param pais the pais to set
	 */
	public void setPais(String pais) {
		this.pais = pais;
	}
	/**
	 * @return the altitude
	 */
	public String getAltitude() {
		return altitude;
	}

	/**
	 * @param altitude the altitude to set
	 */
	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}

	/**
	 * @return the tipoPropriedade
	 */
	public String getTipoPropriedade() {
		return tipoPropriedade;
	}

	/**
	 * @param tipoPropriedade the tipoPropriedade to set
	 */
	public void setTipoPropriedade(String tipoPropriedade) {
		this.tipoPropriedade = tipoPropriedade;
	}

	/**
	 * @return the protecaoExistente
	 */
	public String getProtecaoExistente() {
		return protecaoExistente;
	}

	/**
	 * @param protecaoExistente the protecaoExistente to set
	 */
	public void setProtecaoExistente(String protecaoExistente) {
		this.protecaoExistente = protecaoExistente;
	}


	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the identificacaoProprietario
	 */
	public String getIdentificacaoProprietario() {
		return identificacaoProprietario;
	}

	/**
	 * @param identificacaoProprietario the identificacaoProprietario to set
	 */
	public void setIdentificacaoProprietario(String identificacaoProprietario) {
		this.identificacaoProprietario = identificacaoProprietario;
	}

	/**
	 * @return the administradorPropriedade
	 */
	public String getAdministradorPropriedade() {
		return administradorPropriedade;
	}

	/**
	 * @param administradorPropriedade the administradorPropriedade to set
	 */
	public void setAdministradorPropriedade(String administradorPropriedade) {
		this.administradorPropriedade = administradorPropriedade;
	}

	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
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
	 * @param longitude the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the localidade
	 */
	public String getLocalidade() {
		return localidade;
	}

	/**
	 * @param localidade the localidade to set
	 */
	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	/**
	 * @return the legislacaoExistente
	 */
	public String getLegislacaoExistente() {
		return legislacaoIncidente;
	}

	/**
	 * @param legislacaoExistente the legislacaoExistente to set
	 */
	public void setLegislacaoExistente(String legislacaoExistente) {
		this.legislacaoIncidente = legislacaoExistente;
	}

	/**
	 * @return the sinteseHistorica
	 */
	public String getSinteseHistorica() {
		return sinteseHistorica;
	}

	/**
	 * @param sinteseHistorica the sinteseHistorica to set
	 */
	public void setSinteseHistorica(String sinteseHistorica) {
		this.sinteseHistorica = sinteseHistorica;
	}

	

	
}
