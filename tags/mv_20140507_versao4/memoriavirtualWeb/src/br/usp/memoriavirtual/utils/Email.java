package br.usp.memoriavirtual.utils;

import java.io.Serializable;

public class Email implements Serializable {

	private static final long serialVersionUID = -2040472548939340419L;
	private String email;

	public Email() {
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
