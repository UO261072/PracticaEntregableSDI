package com.uniovi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Friends {

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="user1_id")
	private User user1;
	@ManyToOne
	@JoinColumn(name="user2_id")
	private User user2;
	
	
	public Friends() {}
	public Friends(User user1, User user2) {
		super();
		this.user1 = user1;
		this.user2 = user2;
	}
	public Friends(Long id, User user1, User user2) {
		super();
		this.id = id;
		this.user1 = user1;
		this.user2 = user2;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getUser1() {
		return user1;
	}
	public void setUser1(User user1) {
		this.user1 = user1;
	}
	public User getUser2() {
		return user2;
	}
	public void setUser2(User user2) {
		this.user2 = user2;
	}
	@Override
	public String toString() {
		return "Friends ["+user1 +" " + user2+"]";
	}
	
	
	
}
