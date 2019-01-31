package com.mnt.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mnt.dao.AuthUserDao;
import com.mnt.dao.GroupSetDao;
import com.mnt.dao.PermissionMatrixDao;
import com.mnt.dao.PremissionsDao;
import com.mnt.domain.AuthUser;
import com.mnt.domain.GroupSet;
import com.mnt.domain.PermissionMatrix;
import com.mnt.domain.Permissions;
import com.mnt.service.PermissionService;
import com.mnt.vm.GroupVM;
import com.mnt.vm.PermissionsVM;

@Repository
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	PremissionsDao permissionsDao;
	@Autowired
	GroupSetDao groupSetDao;
	@Autowired
	PermissionMatrixDao permissionMatrixDao;
	@Autowired
	AuthUserDao authUserDao;
	
	public Map<String,List<String>> getAllPermissions() {
		Map<String,List<String>> permissionsMap = new HashMap<String, List<String>>();
		List<Permissions> list = permissionsDao.loadAll();
		String moduleName = "";
		for(int i=0;i<list.size();i++) {
			if(!moduleName.equals(list.get(i).getModule())) {
				moduleName = list.get(i).getModule();
			}
			List<String> nameList = new ArrayList<>();
			for(int j=0;j<list.size();j++) {
				if(moduleName.equals(list.get(j).getModule())) {
					nameList.add(list.get(j).getName());
				}
			}
			permissionsMap.put(moduleName, nameList);
		}
		return permissionsMap;
	}
	
	public void saveGroupPermissions(String groupName, String description, List<String> permissionArray) {
		GroupSet groupSet = new GroupSet();
		groupSet.setName(groupName);
		groupSet.setDescription(description);
		groupSet.setCreatedDate(new Date());
		GroupSet savedGroupSetObj = groupSetDao.save(groupSet);
		for(String module: permissionArray) {
			String arr[] = module.split("=");
			Permissions permission = permissionsDao.findByNameAndModule(arr[0], arr[1]);
			PermissionMatrix permissionMatrix = new PermissionMatrix();
			permissionMatrix.setAccessLevel(1);
			permissionMatrix.setPermissionObj(permission);
			permissionMatrix.setGroup(savedGroupSetObj);
			permissionMatrixDao.save(permissionMatrix);
		}
	}
	
	public List<GroupVM> getAllGroups() {
		List<GroupVM> vmList = new ArrayList<>();
		List<GroupSet> groupSetList = groupSetDao.loadAll();
		for(GroupSet group: groupSetList) {
			GroupVM vm = new GroupVM();
			vm.setId(group.getId());
			vm.setName(group.getName());
			vm.setDescription(group.getDescription());
			vmList.add(vm);
		}
		return vmList;
	}
	
	public GroupVM getGroupById(Long id) {
		GroupVM vm = new GroupVM();
		GroupSet groupSet = groupSetDao.loadById(id);
		vm.setId(id);
		vm.setName(groupSet.getName());
		vm.setDescription(groupSet.getDescription());
		List<PermissionMatrix> list = permissionMatrixDao.getPermissionsByGroup(groupSet);
		List<PermissionsVM> vmList = new ArrayList<>();
		for(PermissionMatrix matrix: list) {
			PermissionsVM permissionsVM = new PermissionsVM();
			permissionsVM.setId(matrix.getPermissionObj().getId());
			permissionsVM.setName(matrix.getPermissionObj().getName());
			permissionsVM.setModule(matrix.getPermissionObj().getModule());
			vmList.add(permissionsVM);
		}
		vm.setPermissionsList(vmList);
		return vm;
	}
	
	public void updateGroupById(Long id, String groupName, String description, List<String> permissionArray) {
		GroupSet groupSet = groupSetDao.loadById(id);
		groupSet.setName(groupName);
		groupSet.setDescription(description);
		groupSetDao.save(groupSet);
		permissionMatrixDao.deleteGroupPermissions(id);
		for(String module: permissionArray) {
			String arr[] = module.split("=");
			Permissions permission = permissionsDao.findByNameAndModule(arr[0], arr[1]);
			PermissionMatrix permissionMatrix = new PermissionMatrix();
			permissionMatrix.setAccessLevel(1);
			permissionMatrix.setPermissionObj(permission);
			permissionMatrix.setGroup(groupSet);
			permissionMatrixDao.save(permissionMatrix);
		}
	}
}
