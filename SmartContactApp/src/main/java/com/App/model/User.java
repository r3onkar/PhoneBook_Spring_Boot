package com.App.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@NotBlank(message = "field should be not empty")
	private String name;
	
	@NotBlank(message = "field should be not empty")
	@Column(unique = true)
	private String email;
	
	@NotBlank(message = "field should be not empty")
	@Size(min=3,max=1000,message ="Password size should be between 3-12 charecters")
	private String pass;
	
	@Column(length = 500)
	private String about;
	private String imageUrl;
	private String role;
	private boolean enabled;
	
	@OneToMany(cascade = CascadeType.ALL ,fetch = FetchType.EAGER,mappedBy = "user",orphanRemoval = true)
	private List<Contact> contacts = new ArrayList<>();
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public List<Contact> getContacts() {
		return contacts;
	}
	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
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
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public User(Integer id, String name, String email, String pass, String about, String imageUrl, String role,
			boolean enabled) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.pass = pass;
		this.about = about;
		this.imageUrl = imageUrl;
		this.role = role;
		this.enabled = enabled;
	}
	
	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", pass=" + pass + ", about=" + about
				+ ", imageUrl=" + imageUrl + ", role=" + role + ", enabled=" + enabled + "]";
	}
	
	
	
	
	
	
	

}
