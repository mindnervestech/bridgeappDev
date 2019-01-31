package com.mnt.dao;

import java.util.List;

import com.mnt.domain.GroupSet;
import com.mnt.domain.PermissionMatrix;

public interface PermissionMatrixDao  extends BaseDao<PermissionMatrix> {

	List<PermissionMatrix> getPermissionsByGroup(GroupSet group);
	void deleteGroupPermissions(Long id);
}
