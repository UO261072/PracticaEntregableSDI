package com.uniovi.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Post {

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="owner_id")
	private User owner;
	
	private Date date;
	
	private String text;
	
	public Post() {}

	public Post(Long id, User owner, Date date, String text) {
		super();
		this.id = id;
		this.owner = owner;
		this.date = date;
		this.text = text;
	}

	public Post(User owner, Date date, String text) {
		super();
		this.owner = owner;
		this.date = date;
		this.text = text;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
}
