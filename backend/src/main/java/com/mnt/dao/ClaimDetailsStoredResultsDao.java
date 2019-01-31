package com.mnt.dao;

import com.mnt.domain.ClaimDetailsStoredResults;;

public interface ClaimDetailsStoredResultsDao  extends BaseDao<ClaimDetailsStoredResults> {

	public ClaimDetailsStoredResults getSumOfClaim(String provider, String year, String claimType, String optionName, String locationName);
	public void deleteAllData();
}
