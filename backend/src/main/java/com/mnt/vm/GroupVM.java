package com.mnt.vm;

import java.util.List;

public class GroupVM {

	public Long id;
	public String name;
	public String description;
	public List<PermissionsVM> permissionsList;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	public List<PermissionsVM> getPermissionsList() {
		return permissionsList;
	}
	public void setPermissionsList(List<PermissionsVM> permissionsList) {
		this.permissionsList = permissionsList;
	}
	
}
