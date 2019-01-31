package com.mnt.dao.jpa;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.dao.PremissionsDao;
import com.mnt.domain.GroupSet;
import com.mnt.domain.PermissionMatrix;
import com.mnt.domain.Permissions;

@Repository
public class PermissionsDaoJpa extends BaseDaoJpa<Permissions> implements PremissionsDao {

	public PermissionsDaoJpa() {
		super(Permissions.class, "Permissions");
	}
	
	@Override
	@Transactional
	public Permissions findByNameAndModule(String module, String name) {
		 Query query = getEntityManager().createQuery("SELECT p FROM Permissions AS p WHERE p.module=:module AND p.name=:name");
	     query.setParameter("module", module);
	     query.setParameter("name", name);
	     return (Permissions) query.getSingleResult();
	}
}
