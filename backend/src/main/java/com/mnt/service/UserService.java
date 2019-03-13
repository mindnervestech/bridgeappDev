package com.mnt.service;

import java.util.List;

import com.mnt.vm.RoleVM;
import com.mnt.vm.UserVM;

public interface UserService {

	void saveUser(UserVM userVM);
	void updateUser(Long id, UserVM userVM);
	UserVM loginUser(String email, String password);
	List<RoleVM> getAllRoles();
	List<UserVM> getAllUsers();
	UserVM getUserById(Long userId);
	void deleteUserById(Long userId);
	void deleteGroupById(Long groupId);
	public boolean sentOTP(String email);
	public boolean validateOTP(String email, String otp);
	public boolean changeForgottenPassword(String email, String newPassword);


}
