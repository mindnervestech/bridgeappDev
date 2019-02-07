package com.mnt.dao;

import java.util.List;

import com.mnt.domain.MonthlyTotalsReport;
import com.mnt.vm.ClaimDetailsVM;
import com.mnt.vm.ReportResponseVM;
import com.mnt.vm.ReportVM;

public interface MonthlyTotalsReportDao extends BaseDao<MonthlyTotalsReport> {
	
	public List<String> getAllYears();
	public List<Object[]> getMonthlyTotalsReport(ClaimDetailsVM vm);
	public ReportResponseVM getSummaryReportData(ReportVM vm);
	public ReportResponseVM getSettledMonthsReportData(ReportVM vm);
	public ReportResponseVM getPmpmByPracticeReportData(ReportVM vm);
	public ReportResponseVM getPmpmByPracticeExpandReportData(ReportVM vm);
	public ReportResponseVM reinsuranceMangementReportData(ReportVM vm);
	public ReportResponseVM reinsuranceCostReportData(ReportVM vm);
}
