package com.mnt.service;

import java.util.List;
import java.util.Map;

import com.mnt.vm.GroupVM;

public interface PermissionService {

	Map<String,List<String>> getAllPermissions();
	void saveGroupPermissions(String groupName, String description, List<String> permissionArray);
	List<GroupVM> getAllGroups();
	GroupVM getGroupById(Long id);
	void updateGroupById(Long id, String groupName, String description, List<String> permissionArray);
}
