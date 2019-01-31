package com.mnt.domain;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "permissions")
public class Permissions extends BaseDomain {

	private static final long serialVersionUID = 1L;
	@Column(name = "name")
	private String name;
	@Column(name = "module")
	private String module;
	@Column(name = "operations")
	private String operations;
	@Column(name = "visibility")
	private boolean visibility;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getOperations() {
		return operations;
	}
	public void setOperations(String operations) {
		this.operations = operations;
	}
	public boolean isVisibility() {
		return visibility;
	}
	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}
	
}
