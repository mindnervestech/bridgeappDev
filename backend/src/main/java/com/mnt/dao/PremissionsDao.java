package com.mnt.dao;


import com.mnt.domain.Permissions;

public interface PremissionsDao  extends BaseDao<Permissions> {

	public Permissions findByNameAndModule(String module, String name);
}
