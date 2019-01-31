package com.mnt.dao;

import java.util.List;

import com.mnt.domain.ProfClaimDetail;
import com.mnt.vm.ReportResponseVM;
import com.mnt.vm.ReportVM;

public interface ProfClaimDetailDao extends BaseDao<ProfClaimDetail> {
	public void deleteOldRecords(String year, String month, String provider);
	public List<String> getAllYears();
	public List<String> getAllSpeciality();
	public Double getSum(String year, List<String> providerArr);
	public Double getClaimDetails(String qry);
	public ReportResponseVM getSettledMonthsExpandReportData(ReportVM vm);
}
