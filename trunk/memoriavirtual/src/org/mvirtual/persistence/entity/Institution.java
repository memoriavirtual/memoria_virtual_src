package org.mvirtual.persistence.entity;

import org.mvirtual.persistence.entity.relation.InstitutionTelephone;
import org.mvirtual.persistence.entity.relation.UserInstitution;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NaturalId;
//import org.hibernate.annotations.Type;

import org.hibernate.search.annotations.Field;
//import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.ContainedIn;

/**
 * Institution model bean
 * @author Kiyoshi de Brito Murata <kbmurata@gmail.com>
 * @author Gabriel de Faria Andery <gfandery@gmail.com>
 */
@Entity
@Table(name = "`institution`")
public class Institution
	extends AbstractPersistentObject
	implements java.io.Serializable
{
	private static final long serialVersionUID = 966198304027037872L;

        /**
         * Name of the institution.
         */
	private String name;

        /**
         * Nickname of the institution.
         */
	private String acronym;

        /**
         * Unit of the institution.
         */
	private String unit;

        /**
         * Address of the institution.
         */
	private String address;

        /**
         * Postal Code of the institution.
         */
	private String postalCode;

        /**
         * Post Office Box of the institution.
         */
	private String postOfficeBox;

        /**
         * City of the institution.
         */
	private String city;

        /**
         * State of the institution.
         */
	private String state;

        /**
         * Country of the institution.
         */
	private String country;

        /**
         * Locality of the institution.
         */
	private String locality;

        /**
         * Fax of the institution.
         */
	private String fax;

        /**
         * Url of the institution.
         */
	private String url;

        /**
         * E-mail for contact.
         */
	private String email;

        /**
         * Responsible for the unit of institution.
         */
	private String unitResponsible;

        /**
         * Function of the responsible of the unit of institution.
         */
	private String unitResponsibleFunction;

        // ESTA VARIÃ?VEL PROVAVELMENTE PODE SER RETIRADA
	private String registryNumber;

        /**
         * Set of phone numbers of this institution.
         */
	private Set<InstitutionTelephone> institutionTelephones = new HashSet<InstitutionTelephone>(0);

        /**
         * Set of users of this institution.
         */
	private Set<UserInstitution> userInstitutions = new HashSet<UserInstitution>(0);

        /**
         * Set of heritages that addresses this institution.
         */
	private Set<Heritage> heritages = new HashSet<Heritage>(0);

        /**
         * Only a void constructor.
         */
	public Institution() {}

        /**
         * A constructor that initializes the main properties of the institution.
         * @param name Name of the institution.
         * @param unit Unit of the institution.
         * @param address Address of the institution.
         * @param city City of the institution.
         * @param state State of the institution.
         * @param country Country of the institution.
         * @param unitresponsible Responsible for the unit.
         * @param unitresponsiblefunction Function of the responsible for the unit.
         */
	public Institution(String name, String unit, String address,
			String city, String state, String country, String unitResponsible,
			String unitResponsibleFunction) {
		this.name = name;
		this.unit = unit;
		this.address = address;
		this.city = city;
		this.state = state;
		this.country = country;
		this.unitResponsible = unitResponsible;
		this.unitResponsibleFunction = unitResponsibleFunction;
	}

        /**
         * Constructor with all properties of the institution.
         * @param name
         * @param acronym
         * @param unit
         * @param address
         * @param postalcode
         * @param postofficebox
         * @param city
         * @param state
         * @param country
         * @param locality
         * @param fax
         * @param url
         * @param email
         * @param unitresponsible
         * @param unitresponsiblefunction
         * @param registrynumber
         * @param institutionTelephones
         * @param userInstitutions
         * @param heritages
         */
	public Institution(String name, String acronym, String unit,
			String address, String postalCode, String postOfficeBox,
			String city, String state, String country, String locality,
			String fax, String url, String email, String unitResponsible,
			String unitResponsibleFunction, String registryNumber,
			Set<InstitutionTelephone> institutionTelephones,
			Set<UserInstitution> userInstitutions, Set<Heritage> heritages) {
		this.name = name;
		this.acronym = acronym;
		this.unit = unit;
		this.address = address;
		this.postalCode = postalCode;
		this.postOfficeBox = postOfficeBox;
		this.city = city;
		this.state = state;
		this.country = country;
		this.locality = locality;
		this.fax = fax;
		this.url = url;
		this.email = email;
		this.unitResponsible = unitResponsible;
		this.unitResponsibleFunction = unitResponsibleFunction;
		this.registryNumber = registryNumber;
		this.institutionTelephones = institutionTelephones;
		this.userInstitutions = userInstitutions;
		this.heritages = heritages;
	}

	/*
	@Transient
	@DocumentId
	public Long getID() {
		return super.getId();
	}
	*/

        /**
         * Gets the name of the institution.
         * @return String with the name of the institution.
         */
	@NaturalId
	@Column(name = "`name`", nullable = false)
	@Field
	public String getName() {
		return this.name;
	}

        /**
         * Sets the name of the institution.
         * @param name String with the name of the institution.
         */
	public void setName(String name) {
		this.name = name;
	}

        /**
         * Gets the nickname of the institution.
         * @return
         */
	@Column(name = "`acronym`")
	public String getAcronym() {
		return this.acronym;
	}

        /**
         * Sets the nickname for the institution.
         * @param acronym Nickname.
         */
	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}

        /**
         * Unit name of the institution.
         * @return String with the name of the unit.
         */
	@Column(name = "`unit`", nullable = false)
	public String getUnit() {
		return this.unit;
	}

        /**
         * Sets the unit name of the institution.
         * @param unit Unit name.
         */
	public void setUnit(String unit) {
		this.unit = unit;
	}

        /**
         * Gets the address of the institution.
         * @return Address of the institution.
         */
	@Column(name = "`address`", nullable = false)
	public String getAddress() {
		return this.address;
	}

        /**
         * Sets the address of the unit of the institution.
         * @param address Address.
         */
	public void setAddress(String address) {
		this.address = address;
	}

        /**
         * Gets the postal code of the institution unit.
         * @return Postal code.
         */
	@Column(name = "`postalcode`")
	public String getPostalCode() {
		return this.postalCode;
	}

        /**
         *
         * @param postalcode
         */
	public void setPostalCode(String postalcode) {
		this.postalCode = postalcode;
	}

	@Column(name = "`postofficebox`")
	public String getPostOfficeBox() {
		return this.postOfficeBox;
	}

	public void setPostOfficeBox(String postofficebox) {
		this.postOfficeBox = postofficebox;
	}

	@Column(name = "`city`", nullable = false)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "`state`", nullable = false)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "`country`", nullable = false)
	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name = "`locality`")
	public String getLocality() {
		return this.locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	@Column(name = "`fax`")
	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "`url`")
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "`email`")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "`unitresponsible`", nullable = false)
	public String getUnitResponsible() {
		return this.unitResponsible;
	}

	public void setUnitResponsible(String unitresponsible) {
		this.unitResponsible = unitresponsible;
	}

	@Column(name = "`unitresponsiblefunction`", nullable = false)
	public String getUnitResponsibleFunction() {
		return this.unitResponsibleFunction;
	}

	public void setUnitResponsibleFunction(String unitresponsiblefunction) {
		this.unitResponsibleFunction = unitresponsiblefunction;
	}

	@Column(name = "`registrynumber`")
	public String getRegistryNumber() {
		return this.registryNumber;
	}

	public void setRegistryNumber(String registryNumber) {
		this.registryNumber = registryNumber;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "institution")
	public Set<InstitutionTelephone> getInstitutionTelephones() {
		return this.institutionTelephones;
	}

	public void setInstitutionTelephones(Set<InstitutionTelephone> institutionTelephones) {
		this.institutionTelephones = institutionTelephones;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "institution")
	public Set<UserInstitution> getUserInstitutions() {
		return this.userInstitutions;
	}

	public void setUserInstitutions(Set<UserInstitution> userInstitutions) {
		this.userInstitutions = userInstitutions;
	}

	@ContainedIn
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "institution")
	public Set<Heritage> getHeritages() {
		return this.heritages;
	}

	public void setHeritages(Set<Heritage> heritages) {
		this.heritages = heritages;
	}

        @Override
	@Transient
	public String getRepr() {
		return String.format("%s", name);
	}
}
