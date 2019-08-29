/*
 * DomainEntity.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package forms;

import java.util.HashSet;

import javax.validation.constraints.Size;

import security.Authority;
import security.UserAccount;
import domain.Author;

public class RegistrationFormAuthor extends Author {

	// Constructors -----------------------------------------------------------

	public RegistrationFormAuthor() {
		super();
	}


	// Properties -------------------------------------------------------------

	private String	password;

	private Boolean	patternPhone;


	public Boolean getPatternPhone() {
		return this.patternPhone;
	}

	public void setPatternPhone(final Boolean patternPhone) {
		this.patternPhone = patternPhone;
	}

	@Size(min = 5, max = 32)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	// Business methods -------------------------------------------------------

	public RegistrationFormAuthor createToAuthor() {

		final RegistrationFormAuthor registrationForm = new RegistrationFormAuthor();

		registrationForm.setPatternPhone(false);
		registrationForm.setAddress("");
		registrationForm.setEmail("");
		registrationForm.setName("");
		registrationForm.setPhoto("");
		registrationForm.setAddress("");
		registrationForm.setPassword("");
		registrationForm.setMiddleName("");
		registrationForm.setSurname("");

		//PREGUNTAR
		final UserAccount user = new UserAccount();
		user.setAuthorities(new HashSet<Authority>());
		final Authority ad = new Authority();
		ad.setAuthority(Authority.AUTHOR);
		user.getAuthorities().add(ad);

		//NUEVO
		user.setUsername("");
		user.setPassword("");

		registrationForm.setUserAccount(user);

		return registrationForm;
	}

}
