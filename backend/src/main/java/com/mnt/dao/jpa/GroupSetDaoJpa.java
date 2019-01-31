package com.mnt.dao.jpa;

import org.springframework.stereotype.Repository;

import com.mnt.dao.GroupSetDao;
import com.mnt.domain.GroupSet;

@Repository
public class GroupSetDaoJpa extends BaseDaoJpa<GroupSet> implements GroupSetDao {

	public GroupSetDaoJpa() {
		super(GroupSet.class, "GroupSet");
	}
}
