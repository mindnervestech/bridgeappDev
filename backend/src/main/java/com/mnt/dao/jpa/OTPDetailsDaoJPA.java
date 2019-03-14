package com.mnt.dao.jpa;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.mnt.dao.OTPDetailsDao;
import com.mnt.domain.AuthUser;
import com.mnt.domain.OTPDetails;

@Repository
public class OTPDetailsDaoJPA extends BaseDaoJpa<OTPDetails> implements OTPDetailsDao {

	public OTPDetailsDaoJPA() {
		super(OTPDetails.class, "OTPDetails");
	}
	
	
	@Override
	@Transactional
	public boolean setOTPandEmail(String email, String otp) {
		Query query = getEntityManager().createNativeQuery("DELETE FROM otp_details WHERE email = '"+email+"'");
		query.executeUpdate();
		 query = getEntityManager().createNativeQuery("INSERT INTO `otp_details`(email,otp) VALUES ('"+email+"',"+otp+")");
		 query.executeUpdate();
		return true;
	}
	
	@Override
	@Transactional
	public boolean validateEmailAndOTP(String email,String otp) {
		
		Query query = getEntityManager().createNativeQuery("select * from otp_details");
		List<Object[]> result =query.getResultList();
		
		for(Object[] obj : result)
			if(obj[0].equals(email) && obj[1].equals(otp)) {
				query = getEntityManager().createNativeQuery("DELETE FROM otp_details WHERE email = '"+email+"'");
				query.executeUpdate();
				return true;	
			}
		return false;
	}


}
