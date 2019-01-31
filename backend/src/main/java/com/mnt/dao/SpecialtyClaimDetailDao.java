package com.mnt.dao;

import java.util.List;

import com.mnt.domain.SpecialtyClaimDetail;

public interface SpecialtyClaimDetailDao extends BaseDao<SpecialtyClaimDetail> {
	public void deleteOldRecords(String year, String month, String provider);
	public Double getSum(String year, List<String> providerArr);
}
