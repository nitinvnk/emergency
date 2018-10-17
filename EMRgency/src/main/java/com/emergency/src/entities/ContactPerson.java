package com.emergency.src.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "contactperson")
public class ContactPerson implements Serializable {

	private static final long serialVersionUID = 3280981447696446973L;

	@Id
	@GeneratedValue
	@Column(name = "contact_id")
	private long id;

	@Column(name = "name")
	private String name;

	@Column(name = "cellno")
	private String cellno;

	@Column(name = "pemail")
	private String pemail;

	@Column(name = "semail")
	private String semail;

	@Column(name = "created")
	private Timestamp created = new Timestamp(System.currentTimeMillis());

	@Column(name = "lastupdated")
	private Timestamp lastUpdated;

	@ManyToMany(mappedBy = "contactPersons")
	private Set<User> associatedUsers = new HashSet<>();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cellno == null) ? 0 : cellno.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pemail == null) ? 0 : pemail.hashCode());
		result = prime * result + ((semail == null) ? 0 : semail.hashCode());
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
		ContactPerson other = (ContactPerson) obj;
		if (cellno == null) {
			if (other.cellno != null)
				return false;
		} else if (!cellno.equals(other.cellno))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pemail == null) {
			if (other.pemail != null)
				return false;
		} else if (!pemail.equals(other.pemail))
			return false;
		if (semail == null) {
			if (other.semail != null)
				return false;
		} else if (!semail.equals(other.semail))
			return false;
		return true;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCellno() {
		return cellno;
	}

	public void setCellno(String cellno) {
		this.cellno = cellno;
	}

	public String getPemail() {
		return pemail;
	}

	public void setPemail(String pemail) {
		this.pemail = pemail;
	}

	public String getSemail() {
		return semail;
	}

	public void setSemail(String semail) {
		this.semail = semail;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public Timestamp getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Set<User> getAssociatedUsers() {
		return associatedUsers;
	}

	public void setAssociatedUsers(Set<User> associatedUsers) {
		this.associatedUsers = associatedUsers;
	}

}
