package org.mvirtual.persistence.container;

import org.mvirtual.persistence.entity.User;
import org.mvirtual.persistence.entity.Institution;
import org.mvirtual.persistence.entity.relation.UserInstitution;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserInstitutionRelation
{
	private static final Logger log = LoggerFactory.getLogger(UserInstitutionRelation.class);

	private User user;
	private Institution institution;
	private UserInstitution userInstitution;
	
	public UserInstitutionRelation() {}

	public UserInstitutionRelation(User u, Institution i, UserInstitution ui)
	{
		user = u;
		institution = i;
		userInstitution = ui;
	}

	/**
	  * Setter for User.
	  * @param newUser new value for User
	  */
	public void setUser(User newUser) {
		user = newUser;
	}

	/**
	 * Getter for User.
	 * @return User
	 */
	public User getUser() {
		return user;
	}
	
	/**
	 * Setter for institution.
	 * @param newInstitution new value for institution
	 */
	public void setInstitution(Institution newInstitution) {
		institution = newInstitution;
	}

	/**
	 * Getter for institution.
	 * @return institution
	 */
	public Institution getInstitution() {
		return institution;
	}

	/**
	 * Setter for userInstitution.
	 * @param newUserInstitution new value for userInstitution
	 */
	public void setUserInstitution(UserInstitution newUserInstitution) {
		userInstitution = newUserInstitution;
	}

	/**
	 * Getter for userInstitution.
	 * @return userInstitution
	 */
	public UserInstitution getUserInstitution() {
		return userInstitution;
	}

        @Override
	public String toString() {
		String u = (user != null ? user.toString() : "unbound");
		String i = (institution != null ? institution.toString() : "unbound");
		String ui = (userInstitution != null ? userInstitution.toString() : "unbound");
		return String.format("UserInstitutionRelation <u: %s, i: %s, ui: %s>", u, i, ui);
	}
}
