package com.mnt.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role extends BaseDomain {

	private static final long serialVersionUID = 1L;
	@Column(name = "name")
	private String name;
	
	@ManyToMany(mappedBy = "roles")
    private List<AuthUser> users = new ArrayList<>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<AuthUser> getUsers() {
		return users;
	}
	public void setUsers(List<AuthUser> users) {
		this.users = users;
	}
	
}
