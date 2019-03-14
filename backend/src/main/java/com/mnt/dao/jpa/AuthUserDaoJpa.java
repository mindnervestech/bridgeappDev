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

	@Override
	public AuthUser findByUserName(String username) {
		Query query = getEntityManager().createQuery("SELECT au FROM AuthUser AS au WHERE au.email=:email");
	     query.setParameter("email", username);
	     try {
	    	 return (AuthUser) query.getSingleResult();
	     } catch(NoResultException e) {
	    	 return null;
	     }
	}
	

	@Override
	@Transactional
	public boolean ChangePassword(String email,String newPassword) {
		try {
		Query query = getEntityManager().createNativeQuery("UPDATE auth_user SET password = '"+newPassword+"' WHERE email = '"+email+"'");
		query.executeUpdate();
		return true;
		}
		catch(Exception e) {
			return false;
		}
	}

	@Override
	@Transactional
	public boolean ChangePasswordFirstTime(String email, String newPassword) {
		try {
		Query query = getEntityManager().createNativeQuery("UPDATE auth_user SET password = '"+newPassword+"' WHERE email = '"+email+"'");
		query.executeUpdate();
		query = getEntityManager().createNativeQuery("UPDATE auth_user SET ACTIVE = 1 WHERE email='"+email+"' AND PASSWORD = '"+newPassword+"'");
		query.executeUpdate();
		return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
			
		}
	}
}
