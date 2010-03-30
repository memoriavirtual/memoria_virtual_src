package org.mvirtual.persistence.entity;

import org.mvirtual.persistence.entity.relation.UserHeritageCatalogue;
import org.mvirtual.persistence.entity.relation.UserHeritageReview;
import org.mvirtual.persistence.entity.relation.UserInstitution;
import org.mvirtual.persistence.entity.relation.embedded.UserInstitutionId;
import org.mvirtual.persistence.entity.Institution;
import org.mvirtual.persistence.entity.Heritage;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * User model bean.
 * @author Kiyoshi de Brito Murata <kbmurata@gmail.com>
 */
@Entity
@Table(name = "`user`")
public class User
	extends AbstractPersistentObject
	implements java.io.Serializable
{
	private static final long serialVersionUID = -2369991900664893595L;

	private String login;
	private String password;
	private String name;
	private String userIdentificationNumber;
	private String address;
	private Boolean active;
	private String registryDate;
	private String dismissDate;
	private String telephone;
	private String email;

	private Set<UserHeritageReview> userHeritageReviews = new HashSet<UserHeritageReview>(0);
	private Set<UserHeritageCatalogue> userHeritageCatalogues = new HashSet<UserHeritageCatalogue>(0);
	private Set<UserInstitution> userInstitutions = new HashSet<UserInstitution>(0);

        public void addHeritage () {
            
        }

        public void addInstitution (Institution institution, Integer userType) {
            UserInstitutionId userInstitutionId =
                    new UserInstitutionId(institution.getId (),this.getId(), userType);

            UserInstitution userInstitution = 
                    new UserInstitution(userInstitutionId,
                                        institution,
			                this);

            userInstitution.equals(userInstitution);
        }

	public User() {}

	public User(String login, String password, String name,
			String userIdentificationNumber, String address, Boolean active,
			String registryDate, String telephone) {
		this.login = login;
		this.password = password;
		this.name = name;
		this.userIdentificationNumber = userIdentificationNumber;
		this.address = address;
		this.active = active;
		this.registryDate = registryDate;
		this.telephone = telephone;
	}

	public User(String login, String password, String name,
			String userIdentificationNumber, String address, Boolean active,
			String registryDate, String dismissDate, String telephone,
			String email, Set<UserHeritageReview> userHeritageReviews,
			Set<UserHeritageCatalogue> userHeritageCatalogues,
			Set<UserInstitution> userInstitutions) {
		this.login = login;
		this.password = password;
		this.name = name;
		this.userIdentificationNumber = userIdentificationNumber;
		this.address = address;
		this.active = active;
		this.registryDate = registryDate;
		this.dismissDate = dismissDate;
		this.telephone = telephone;
		this.email = email;
		this.userHeritageReviews = userHeritageReviews;
		this.userHeritageCatalogues = userHeritageCatalogues;
		this.userInstitutions = userInstitutions;
	}

	@Column(name = "`login`", unique = true, nullable = false)
	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Column(name = "`password`", nullable = false)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "`name`", nullable = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "`useridentificationnumber`", nullable = false)
	public String getUserIdentificationNumber() {
		return this.userIdentificationNumber;
	}

	public void setUserIdentificationNumber(String useridentificationnumber) {
		this.userIdentificationNumber = useridentificationnumber;
	}

	@Column(name = "`address`", nullable = false)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "`active`", nullable = false)
	public Boolean isActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Column(name = "`registrydate`", nullable = false)
	public String getRegistryDate() {
		return this.registryDate;
	}

	public void setRegistryDate(String registryDate) {
		this.registryDate = registryDate;
	}

	@Column(name = "`dismissdate`")
	public String getDismissDate() {
		return this.dismissDate;
	}

	public void setDismissDate(String dismissDate) {
		this.dismissDate = dismissDate;
	}

	@Column(name = "`telephone`", nullable = false)
	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Column(name = "`email`")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	public Set<UserHeritageReview> getUserHeritageReviews() {
		return this.userHeritageReviews;
	}

	public void setUserHeritageReviews(Set<UserHeritageReview> userHeritageReviews) {
		this.userHeritageReviews = userHeritageReviews;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	public Set<UserHeritageCatalogue> getUserHeritageCatalogues() {
		return this.userHeritageCatalogues;
	}

	public void setUserHeritageCatalogues(Set<UserHeritageCatalogue> userHeritageCatalogues) {
		this.userHeritageCatalogues = userHeritageCatalogues;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	public Set<UserInstitution> getUserInstitutions() {
		return this.userInstitutions;
	}

	public void setUserInstitutions(Set<UserInstitution> userInstitutions) {
		this.userInstitutions = userInstitutions;
	}

	@Transient
        @Override
	public String getRepr() {
		return String.format("%s", login);
	}
}
