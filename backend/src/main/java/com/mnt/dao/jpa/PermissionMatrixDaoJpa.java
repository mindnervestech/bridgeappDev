package com.mnt.dao.jpa;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.dao.PermissionMatrixDao;
import com.mnt.domain.GroupSet;
import com.mnt.domain.PermissionMatrix;

@Repository
public class PermissionMatrixDaoJpa  extends BaseDaoJpa<PermissionMatrix> implements PermissionMatrixDao {

	public PermissionMatrixDaoJpa() {
		super(PermissionMatrix.class, "PermissionMatrix");
	}
	
	@Override
	@Transactional
	public List<PermissionMatrix> getPermissionsByGroup(GroupSet group) {
		Query query = getEntityManager().createQuery("SELECT p FROM PermissionMatrix AS p WHERE p.group.id=:id");
	     query.setParameter("id", group.getId());
	     return query.getResultList();
	}
	
	@Override
	@Transactional
	public void deleteGroupPermissions(Long id) {
		Query query = getEntityManager().createNativeQuery("delete from permission_matrix where group_id=:id");
	     query.setParameter("id", id);
	     query.executeUpdate();
	}
}
