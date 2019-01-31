package com.mnt.dao.jpa;

import org.springframework.stereotype.Repository;

import com.mnt.dao.RoleDao;
import com.mnt.domain.Role;

@Repository
public class RoleDaoJpa extends BaseDaoJpa<Role>  implements RoleDao {

	public RoleDaoJpa() {
		super(Role.class, "Role");
	}
	
}
