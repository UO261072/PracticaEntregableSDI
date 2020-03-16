package com.uniovi.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class User {
	
	@Id
	@GeneratedValue
	private long id;
	private String name;
	private String surname;
	@Column(unique=true)
	private String email;
	private String password;
	private String passwordConfirm;
	private String role;
	
	@OneToMany(mappedBy="reciever",cascade=CascadeType.ALL)
	private Set<Invite> recievedInvites;
	
	@OneToMany(mappedBy="sender",cascade=CascadeType.ALL)
	private Set<Invite> sendedInvites;
	

	
	public User() {}

	public User(String name, String surname, String email) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Set<Invite> getSendedInvites() {
		return sendedInvites;
	}

	public void setSendedInvites(Set<Invite> sendedInvites) {
		this.sendedInvites = sendedInvites;
	}

	public Set<Invite> getRecievedInvites() {
		return recievedInvites;
	}

	public void setRecievedInvites(Set<Invite> recievedInvites) {
		this.recievedInvites = recievedInvites;
	}
	public void addSendInvite(Invite invite) {
		this.sendedInvites.add(invite);
	}
	public void addRecieveInvite(Invite invite) {
		this.recievedInvites.add(invite);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return name+" "+surname+" "+email;
	}

	
	
	
	
	

}
