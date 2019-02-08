package com.mnt.dao;

import java.util.HashMap;
import java.util.List;

import com.mnt.domain.InstClaimDetail;
import com.mnt.vm.AdmissionHeaderReportFileVM;
import com.mnt.vm.ReportResponseVM;
import com.mnt.vm.ReportVM;

public interface InstClaimDetailDao extends BaseDao<InstClaimDetail> {
	public void deleteOldRecords(String year, String month, String provider);
	public Double getSum(String year, List<String> providerArr);
	public ReportResponseVM generateClaimReport(ReportVM vm);
	public ReportResponseVM generateClaimDetailReport(ReportVM vm);
	public ReportResponseVM generateClaimDetailDataReport(ReportVM vm);
	public ReportResponseVM getDuplicateClaimsData(ReportVM vm);
	public ReportResponseVM getDuplicateClaimsExpandData(ReportVM vm);
	public ReportResponseVM getAdmissionsReportData(ReportVM vm);
	public ReportResponseVM getPatientVisitReportData(ReportVM vm);
	public ReportResponseVM getPatientVisitExpandReportData(ReportVM vm);
	public ReportResponseVM getAdmissionsReportExpandData(ReportVM vm);
	public ReportResponseVM getSpecialistComparisonReportData(ReportVM vm);
	public ReportResponseVM getSpecialistComparisonExpandReportData(ReportVM vm);
	public ReportResponseVM getSpecialistComparisonExpandPracticeReportData(ReportVM vm);
	public List<Object[]> getDataForFile(String fileQuery);
	public List<Object[]> getDuplicateClaimsAllData(String fileQuery);
	public List<Object[]> getAdmissionsHeaderReportData(AdmissionHeaderReportFileVM vm);
}
