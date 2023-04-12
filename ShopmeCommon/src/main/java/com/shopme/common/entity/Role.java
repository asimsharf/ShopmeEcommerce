package com.shopme.common.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 40, nullable = false, unique = true)
	private String name;
	
	@Column(length = 150, nullable = false)
	private String description;

	@ManyToMany(mappedBy = "roles")
	private Set<User> users = new HashSet<>();

	public void addUser(User user) {
		users.add(user);
	}

	public void removeUser(User user) {
		users.remove(user);
	}

	public Role() {
	}

	public Role(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public Role(Integer id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o){
			return true;
		}
		if(o == null){
			return false;
		}
		if(getClass() != o.getClass()){
			return false;
		}
		Role role = (Role) o;
		if (id == null){
			return role.id == null;
		} else return id.equals(role.id);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
