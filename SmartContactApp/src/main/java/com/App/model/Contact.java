package com.App.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;

@Entity
@Table(name = "contacts")
public class Contact {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer cid;
	private String name; 
	@Column(unique = true)
	private String email;
	private String nickname;
	private String work;
	private String phone;
	@Column(length = 500)
	private String description;
	private String photo;
	
	@ManyToOne
	private User user;
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getWork() {
		return work;
	}
	public void setWork(String work) {
		this.work = work;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	
	public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Contact(Integer cid, String name, String email, String nickname, String work, String phone,
			String description, String photo) {
		super();
		this.cid = cid;
		this.name = name;
		this.email = email;
		this.nickname = nickname;
		this.work = work;
		this.phone = phone;
		this.description = description;
		this.photo = photo;
	}
	@Override
	public String toString() {
		return "Contact [cid=" + cid + ", name=" + name + ", email=" + email + ", nickname=" + nickname + ", work="
				+ work + ", phone=" + phone + ", description=" + description + ", photo=" + photo + ", user=" + user
				+ "]";
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return this.cid==((Contact)obj).getCid();
	}
	

}
