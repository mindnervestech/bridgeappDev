package com.mnt.dao.jpa;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.dao.ClaimDetailsStoredResultsDao;
import com.mnt.domain.ClaimDetailsStoredResults;
import com.mnt.domain.FileInfo;


@Repository
public class ClaimDetailsStoredResultsDaoJpa extends BaseDaoJpa<ClaimDetailsStoredResults> implements ClaimDetailsStoredResultsDao {

	public ClaimDetailsStoredResultsDaoJpa() {
		super(ClaimDetailsStoredResults.class, "ClaimDetailsStoredResults");
	}
	
	@Override
	@Transactional
	public ClaimDetailsStoredResults getSumOfClaim(String provider, String year, String claimType, String optionName, String locationName) {
		try {
			Query query = getEntityManager().createQuery("SELECT cd FROM ClaimDetailsStoredResults AS cd WHERE cd.provider=:provider and cd.year=:year and cd.claimType=:claimType and cd.optionName=:optionName and locationName=:locationName");
	        query.setParameter("provider", provider);
	        query.setParameter("year", year);
	        query.setParameter("claimType", claimType);
	        query.setParameter("optionName", optionName);
	        query.setParameter("locationName", locationName);
	        return (ClaimDetailsStoredResults) query.getSingleResult();
			} catch(NoResultException nre) {
				return null;
			}
	}
	
	@Override
	@Transactional
	public void deleteAllData() {
		Query query = getEntityManager().createNativeQuery("delete from claim_details_stored_results");
	    query.executeUpdate();
	}
	
}
