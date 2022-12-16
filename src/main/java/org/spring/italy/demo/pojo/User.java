package org.spring.italy.demo.pojo;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	@NotBlank
	@Column(unique = true)
	private String username;
	
	@NotNull
	@NotBlank
	private String password;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Role> roles;
	
	
	public User() {}
	public User(String name, String password, Role role) {
		
		setUsername(name);
		setPassword(password);
		addRole(role);
	}
	public User(String name, String password, Set<Role> roles) {
		
		setUsername(name);
		setPassword(password);
		setRoles(roles);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String name) {
		this.username = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public void addRole(Role role) {
		
		if(getRoles() == null)
			roles = new HashSet<>();
		
		getRoles().add(role);
	}
	
	@Override
	public String toString() {
		
		return "\n" + getId() 
				+ ": " + getUsername()
				+ " - " + getPassword()
				+ " |-| " + getRoles();
	}
	
}
