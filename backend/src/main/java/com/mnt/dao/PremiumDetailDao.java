package com.mnt.dao;

import java.util.List;

import com.mnt.domain.PremiumDetail;
import com.mnt.vm.ClaimDetailsVM;

public interface PremiumDetailDao extends BaseDao<PremiumDetail> {
	public void deleteOldRecords(String year, String month, String provider);
	public List<Object[]> getMonthlyTotalsReport(ClaimDetailsVM vm);
}
