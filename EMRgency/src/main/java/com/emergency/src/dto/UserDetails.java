package com.emergency.src.dto;

import java.io.Serializable;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

public class UserDetails implements Serializable {

	private static final long serialVersionUID = 7713916203402273119L;
	
	@NotEmpty(message = " firstname must not be empty")
	private String firstname;
	
	@NotEmpty(message = " lastname must not be empty")
	private String lastname;
	
	@NotEmpty(message = "cellno is Mandatory")
	private String cellno;
	
	private String email;
	private String imei1;
	private String imei2;
	
	@Valid
	private ContactPersonDetails[] cpDetails = new ContactPersonDetails[3];

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cellno == null) ? 0 : cellno.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result + ((imei1 == null) ? 0 : imei1.hashCode());
		result = prime * result + ((imei2 == null) ? 0 : imei2.hashCode());
		result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDetails other = (UserDetails) obj;
		if (cellno == null) {
			if (other.cellno != null)
				return false;
		} else if (!cellno.equals(other.cellno))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstname == null) {
			if (other.firstname != null)
				return false;
		} else if (!firstname.equals(other.firstname))
			return false;
		if (imei1 == null) {
			if (other.imei1 != null)
				return false;
		} else if (!imei1.equals(other.imei1))
			return false;
		if (imei2 == null) {
			if (other.imei2 != null)
				return false;
		} else if (!imei2.equals(other.imei2))
			return false;
		if (lastname == null) {
			if (other.lastname != null)
				return false;
		} else if (!lastname.equals(other.lastname))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserDetails [firstname=" + firstname + ", lastname=" + lastname + ", cellNo=" + cellno + ", email="
				+ email + ", imei1=" + imei1 + ", imei2=" + imei2 + "]";
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getCellno() {
		return cellno;
	}

	public void setCellno(String cellno) {
		this.cellno = cellno;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImei1() {
		return imei1;
	}

	public void setImei1(String imei1) {
		this.imei1 = imei1;
	}

	public String getImei2() {
		return imei2;
	}

	public void setImei2(String imei2) {
		this.imei2 = imei2;
	}

	public ContactPersonDetails[] getCpDetails() {
		return cpDetails;
	}

	public void setCpDetails(ContactPersonDetails[] cpDetails) {
		this.cpDetails = cpDetails;
	}

}
