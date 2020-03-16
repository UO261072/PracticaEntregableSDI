package com.uniovi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;


@Entity
public class Invite {

	@Id
	@GeneratedValue
	private Long id;
	@Transient
	private String sendString;
	@Transient
	private String recieveString;
	@ManyToOne
	@JoinColumn(name="sender_id")
	private User sender;
	
	@ManyToOne
	@JoinColumn(name="reciever_id")
	private User reciever;
	
	public Invite() {}
	public Invite(User sender, User reciever) {
		super();
		this.sender = sender;
		this.reciever = reciever;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getSender() {
		return sender;
	}
	public void setSender(User sender) {
		this.sender = sender;
	}
	public User getReciever() {
		return reciever;
	}
	public void setReciever(User reciever) {
		this.reciever = reciever;
	}
	
	public String getSendString() {
		return sendString;
	}
	public void setSendString(String sendString) {
		this.sendString = sendString;
	}
	public String getRecieveString() {
		return recieveString;
	}
	public void setRecieveString(String recieveString) {
		this.recieveString = recieveString;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((reciever == null) ? 0 : reciever.hashCode());
		result = prime * result + ((sender == null) ? 0 : sender.hashCode());
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
		Invite other = (Invite) obj;
		if (reciever == null) {
			if (other.reciever != null)
				return false;
		} else if (!reciever.equals(other.reciever))
			return false;
		if (sender == null) {
			if (other.sender != null)
				return false;
		} else if (!sender.equals(other.sender))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Invite [id=" + id + ", sender=" + sender.getName()+" "+sender.getSurname() + ", reciever=" + reciever.getName()+" "+reciever.getSurname() + "]";
	}
	
	
	
	
	
	
}
