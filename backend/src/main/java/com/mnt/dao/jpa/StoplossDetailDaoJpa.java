package com.mnt.dao.jpa;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.dao.StoplossDetailDao;
import com.mnt.domain.StoplossDetail;

@Repository
public class StoplossDetailDaoJpa extends BaseDaoJpa<StoplossDetail> implements StoplossDetailDao {
	
	public StoplossDetailDaoJpa() {
		super(StoplossDetail.class, "StoplossDetail");
	}
	
	@Override
	@Transactional
	public void deleteOldRecords(String year, String month, String provider) {
		Query query = getEntityManager().createQuery("DELETE FROM StoplossDetail sd where sd.year=:year and sd.month=:month and sd.provider=:provider");
		query.setParameter("year", year);
        query.setParameter("month", month);
        query.setParameter("provider", provider);
        query.executeUpdate();
	}
}
