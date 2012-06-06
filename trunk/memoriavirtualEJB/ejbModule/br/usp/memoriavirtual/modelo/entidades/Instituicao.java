package br.usp.memoriavirtual.modelo.entidades;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Pattern;

@Entity
@SequenceGenerator(name = "INSTITUICAO_ID", sequenceName = "INSTITUICAO_SEQ", allocationSize = 1)
public class Instituicao implements Serializable {

	/**
	 * Serial Version UID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "INSTITUICAO_ID")
	private long id;
	private static final long serialVersionUID = -5996690587044446292L;
	private String nome;
	private String localidade;
	private String endereco;
	private String cidade;
	@Pattern(regexp = "[A-Z]{2}", message = "Estado no formato incorreto (ex: SP e não sp)")
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
	public Instituicao(String Nome, String Local, String End, String Cidade, String Est, String Cep, String Tel) {
		super();
		this.nome = Nome;
		this.localidade = Local;
		this.endereco = End;
		this.cidade = Cidade;
		this.estado = Est;
		this.cep = Cep;
		this.telefone = Tel;

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

}
