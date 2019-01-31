package com.mnt.dao.jpa;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.dao.AuthUserDao;
import com.mnt.domain.AuthUser;

@Repository
public class AuthUserDaoJpa extends BaseDaoJpa<AuthUser> implements AuthUserDao {

	public AuthUserDaoJpa() {
		super(AuthUser.class, "AuthUser");
	}
	
	@Override
	@Transactional
	public AuthUser loginUser(String email, String password) {
		Query query = getEntityManager().createQuery("SELECT au FROM AuthUser AS au WHERE au.email=:email AND au.password=:password");
	     query.setParameter("email", email);
	     query.setParameter("password", password);
	     try {
	    	 return (AuthUser) query.getSingleResult();
	     } catch(NoResultException e) {
	    	 return null;
	     }
	     
	}
	
	@Override
	@Transactional
	public void deleteUsersGroup(Long groupId) {
		Query query = getEntityManager().createNativeQuery("delete from user_group where group_id=:id");
	     query.setParameter("id", groupId);
	     query.executeUpdate();
	}
}
