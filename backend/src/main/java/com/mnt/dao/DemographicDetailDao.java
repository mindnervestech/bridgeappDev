package com.mnt.dao;

import com.mnt.domain.DemographicDetail;
import com.mnt.vm.MembershipManagementVM;
import com.mnt.vm.ReportResponseVM;
import com.mnt.vm.ReportVM;

public interface DemographicDetailDao extends BaseDao<DemographicDetail>{
	public void deleteOldRecords(String year, String month, String provider);
	public MembershipManagementVM getMembershipManagementData(ReportVM vm);
	public ReportResponseVM getMembershipManagementPatientTypeData(ReportVM vm);
	public ReportResponseVM getBeneficiariesManagementReportData(ReportVM vm);
	public ReportResponseVM getBeneficiariesManagementByDoctorReportData(ReportVM vm);
	public ReportResponseVM getBeneficiariesManagementExpandReportData(ReportVM vm);
	public ReportResponseVM getBeneficiariesManagementByDoctorExpandReportData(ReportVM vm);
	public ReportResponseVM getBeneficiariesManagementByLocationReportData(ReportVM vm);
	public ReportResponseVM getBeneficiariesManagementByClinicReportData(ReportVM vm);
}
