package com.mnt.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "group_set")
public class GroupSet extends BaseDomain {

	private static final long serialVersionUID = 1L;
	@Column(name = "name")
	private String name;
	@Column(name = "description")
	private String description;
	@Column(name = "created_date")
	private Date createdDate;
	
	@ManyToMany(mappedBy = "groups")
    private List<AuthUser> users = new ArrayList<>();
	
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
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public List<AuthUser> getUsers() {
		return users;
	}
	public void setUsers(List<AuthUser> users) {
		this.users = users;
	}
	
}
