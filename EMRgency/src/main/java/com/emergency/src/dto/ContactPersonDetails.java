package com.emergency.src.dto;

import java.io.Serializable;

public class ContactPersonDetails implements Serializable {

	private static final long serialVersionUID = -6005039873686238296L;
	private String name;
	private String cellNo;
	private UserDetails userDetails;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cellNo == null) ? 0 : cellNo.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((userDetails == null) ? 0 : userDetails.hashCode());
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
		ContactPersonDetails other = (ContactPersonDetails) obj;
		if (cellNo == null) {
			if (other.cellNo != null)
				return false;
		} else if (!cellNo.equals(other.cellNo))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (userDetails == null) {
			if (other.userDetails != null)
				return false;
		} else if (!userDetails.equals(other.userDetails))
			return false;
		return true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCellNo() {
		return cellNo;
	}

	public void setCellNo(String cellNo) {
		this.cellNo = cellNo;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

}
