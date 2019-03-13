package com.mnt.dao;

import com.mnt.domain.AuthUser;

public interface AuthUserDao  extends BaseDao<AuthUser> {
	
	AuthUser loginUser(String email, String password);
	void deleteUsersGroup(Long groupId);
	AuthUser findByUserName(String username);
	public boolean ChangePassword(String email, String newPassword);
}
