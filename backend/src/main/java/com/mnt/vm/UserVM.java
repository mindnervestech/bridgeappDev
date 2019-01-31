package com.mnt.vm;

import java.util.List;

public class UserVM {

	public Long id;
	public String userName;
	public String email;
	public String firstName;
	public String lastName;
	public String phone;
	public String password;
	public Long roleId;
	public boolean isActive;
	public String role;
	public List<Long> groupIdList;
	public List<PermissionsVM> permissions;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public List<Long> getGroupIdList() {
		return groupIdList;
	}
	public void setGroupIdList(List<Long> groupIdList) {
		this.groupIdList = groupIdList;
	}
	public List<PermissionsVM> getPermissions() {
		return permissions;
	}
	public void setPermissions(List<PermissionsVM> permissions) {
		this.permissions = permissions;
	}
	
}
