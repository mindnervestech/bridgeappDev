package com.mnt.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "permission_matrix")
public class PermissionMatrix extends BaseDomain {

	private static final long serialVersionUID = 1L;
	
	@Column(name="accesslevel")
	 private int accessLevel;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "permissions_id")
	private Permissions permissionObj;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private AuthUser user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id")
	private GroupSet group;

	public int getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(int accessLevel) {
		this.accessLevel = accessLevel;
	}

	public Permissions getPermissionObj() {
		return permissionObj;
	}

	public void setPermissionObj(Permissions permissionObj) {
		this.permissionObj = permissionObj;
	}

	public AuthUser getUser() {
		return user;
	}

	public void setUser(AuthUser user) {
		this.user = user;
	}

	public GroupSet getGroup() {
		return group;
	}

	public void setGroup(GroupSet group) {
		this.group = group;
	}

	
}
