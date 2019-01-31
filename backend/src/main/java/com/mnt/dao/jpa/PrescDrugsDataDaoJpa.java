package com.mnt.dao.jpa;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.dao.PrescDrugsDataDao;
import com.mnt.domain.PrescDrugsData;

@Repository
public class PrescDrugsDataDaoJpa extends BaseDaoJpa<PrescDrugsData> implements PrescDrugsDataDao {

	public PrescDrugsDataDaoJpa() {
		super(PrescDrugsData.class, "PrescDrugsData");
	}
	
	@Override
	@Transactional
	public void deleteAllData() {
		Query query = getEntityManager().createNativeQuery("delete from presc_drugs_data");
	    query.executeUpdate();
	}
	
	@Override
	@Transactional
	public PrescDrugsData getTop20PrescriptionDrugs(String type, String provider) {
		try {
			Query query = getEntityManager().createQuery("SELECT pd FROM PrescDrugsData AS pd WHERE pd.type=:type and pd.provider=:provider");
	        query.setParameter("type", type);
	        query.setParameter("provider", provider);
	        return (PrescDrugsData) query.getSingleResult();
			} catch(NoResultException nre) {
				return null;
			}
	}
}
