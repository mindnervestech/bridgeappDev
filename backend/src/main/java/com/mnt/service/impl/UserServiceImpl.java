package com.mnt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mnt.dao.AuthUserDao;
import com.mnt.dao.GroupSetDao;
import com.mnt.dao.PermissionMatrixDao;
import com.mnt.dao.RoleDao;
import com.mnt.domain.AuthUser;
import com.mnt.domain.GroupSet;
import com.mnt.domain.PermissionMatrix;
import com.mnt.domain.Role;
import com.mnt.service.UserService;
import com.mnt.vm.PermissionsVM;
import com.mnt.vm.RoleVM;
import com.mnt.vm.UserVM;

@Repository
public class UserServiceImpl implements UserService {

	@Autowired
	AuthUserDao authUserDao;
	@Autowired
	RoleDao roleDao;
	@Autowired
	GroupSetDao groupSetDao;
	@Autowired
	PermissionMatrixDao permissionMatrixDao;
	
	
	public void saveUser(UserVM userVM) {
		AuthUser authUser = new AuthUser();
		authUser.setUsername(userVM.getUserName());
		authUser.setEmail(userVM.getEmail());
		authUser.setPassword(userVM.getPassword());
		authUser.setFirstName(userVM.getFirstName());
		authUser.setLastName(userVM.getLastName());
		authUser.setPhone(userVM.getPhone());
		authUser.setActive(true);
		/*Role role = roleDao.loadById(userVM.getRoleId());
		List<Role> roles = new ArrayList<>();
		roles.add(role);
		authUser.setRoles(roles);*/
		List<GroupSet> groups = new ArrayList<>();
		for(Long groupId: userVM.getGroupIdList()) {
			GroupSet group = groupSetDao.loadById(groupId);
			groups.add(group);
		}
		authUser.setGroups(groups);
		authUserDao.save(authUser);
	}
	
	public void updateUser(Long id, UserVM userVM) {
		AuthUser authUser = authUserDao.loadById(id);
		authUser.setUsername(userVM.getUserName());
		authUser.setEmail(userVM.getEmail());
		authUser.setPassword(userVM.getPassword());
		authUser.setFirstName(userVM.getFirstName());
		authUser.setLastName(userVM.getLastName());
		authUser.setPhone(userVM.getPhone());
		authUser.setActive(true);
		/*Role role = roleDao.loadById(userVM.getRoleId());
		List<Role> roles = new ArrayList<>();
		roles.add(role);
		authUser.setRoles(roles);*/
		List<GroupSet> groups = new ArrayList<>();
		for(Long groupId: userVM.getGroupIdList()) {
			GroupSet group = groupSetDao.loadById(groupId);
			groups.add(group);
		}
		authUser.setGroups(groups);
		authUserDao.save(authUser);
	}
	
	public UserVM loginUser(String email, String password) {
		AuthUser user = authUserDao.loginUser(email, password);
		UserVM vm = new UserVM();
		if(user == null) {
			vm.setId(-1L);
		} else {
			vm.setId(user.getId());
			vm.setEmail(user.getEmail());
			//vm.setPassword(user.getPassword());
			vm.setUserName(user.getUsername());
			vm.setFirstName(user.getFirstName());
			vm.setLastName(user.getLastName());
			vm.setPhone(user.getPhone());
			List<PermissionsVM> permissionsList = new ArrayList<>();
			for(GroupSet group: user.getGroups()) {
				List<PermissionMatrix> list = permissionMatrixDao.getPermissionsByGroup(group);
				for(PermissionMatrix matrix: list) {
					PermissionsVM permissionsVM = new PermissionsVM();
					permissionsVM.setId(matrix.getId());
					permissionsVM.setName(matrix.getPermissionObj().getName());
					permissionsVM.setModule(matrix.getPermissionObj().getModule());
					permissionsList.add(permissionsVM);
				}
			}
			vm.setPermissions(permissionsList);
		}
		return vm;
	}
	
	public List<RoleVM> getAllRoles() {
		List<RoleVM> vmList = new ArrayList<>();
		List<Role> roleList = roleDao.loadAll();
		for(Role role: roleList) {
			RoleVM vm = new RoleVM();
			vm.setId(role.getId());
			vm.setName(role.getName());
			vmList.add(vm);
		}
		return vmList;
	}
	
	public List<UserVM> getAllUsers() {
		List<UserVM> userVMList = new ArrayList<>();
		List<AuthUser> users = authUserDao.loadAll();
		for(AuthUser user: users) {
			UserVM vm = new UserVM();
			vm.setId(user.getId());
			vm.setActive(user.isActive());
			vm.setEmail(user.getEmail());
			vm.setFirstName(user.getFirstName());
			vm.setLastName(user.getLastName());
			vm.setPassword(user.getPassword());
			vm.setPhone(user.getPhone());
			vm.setUserName(user.getUsername());
			if(!user.getRoles().isEmpty())
			vm.setRole(user.getRoles().get(0).getName());
			userVMList.add(vm);
		}
		return userVMList;
	}
	
	public UserVM getUserById(Long id) {
		AuthUser user = authUserDao.loadById(id);
		UserVM vm = new UserVM();
		vm.setId(user.getId());
		vm.setActive(user.isActive());
		vm.setEmail(user.getEmail());
		vm.setFirstName(user.getFirstName());
		vm.setLastName(user.getLastName());
		vm.setPassword(user.getPassword());
		vm.setPhone(user.getPhone());
		vm.setUserName(user.getUsername());
		if(!user.getRoles().isEmpty()) {
			vm.setRole(user.getRoles().get(0).getName());
			vm.setRoleId(user.getRoles().get(0).getId());
		}
		List<Long> groupIds = new ArrayList<>();
		for(GroupSet group: user.getGroups()) {
			groupIds.add(group.getId());
		}
		vm.setGroupIdList(groupIds);
		return vm;
	}
	
	public void deleteUserById(Long userId) {
		AuthUser user = authUserDao.loadById(userId);
		authUserDao.delete(user);
	}
	
	public void deleteGroupById(Long groupId) {
		GroupSet group = groupSetDao.loadById(groupId);
		permissionMatrixDao.deleteGroupPermissions(groupId);
		authUserDao.deleteUsersGroup(groupId);
		groupSetDao.delete(group);
	}
	
}
