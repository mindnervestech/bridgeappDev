package com.mnt.vm;

import java.util.List;

import com.mnt.vm.reports.BeneficiariesManagementByClinicReportVM;
import com.mnt.vm.reports.BeneficiariesManagementByDoctorReportVM;
import com.mnt.vm.reports.BeneficiariesManagementByLocationReportVM;
import com.mnt.vm.reports.BeneficiariesManagementExpandReportVM;
import com.mnt.vm.reports.BeneficiariesManagementReportVM;
import com.mnt.vm.reports.DuplicateClaimsExpandVM;
import com.mnt.vm.reports.PatientVisitExpandReportVM;
import com.mnt.vm.reports.PmpmByPracticeExpandReportVM;
import com.mnt.vm.reports.PmpmByPracticeReportVM;
import com.mnt.vm.reports.ReinsuranceCostReportVM;
import com.mnt.vm.reports.ReinsuranceManagementReportVM;
import com.mnt.vm.reports.SettledMonthsExpandReportVM;
import com.mnt.vm.reports.SettledMonthsReportVM;
import com.mnt.vm.reports.SpecialistComparisonExpandPatientReportVM;
import com.mnt.vm.reports.SpecialistComparisonExpandPracticeReportVM;
import com.mnt.vm.reports.SpecialistComparisonExpandReportVM;

public class DashboardReportsVM {

	public List<DuplicateClaimsReportVM> duplicateClaimsReportData;
	public List<AdmissionsReportVM> admissionsReportData;
	public List<AdmissionsReportExpandVM> admissionsReportExpandData;
	public List<SpecialistComparisonReportVM> specialistComparisonReportData;
	public List<SpecialistComparisonExpandReportVM> specialistComparisonExpandReportData;
	public List<SpecialistComparisonExpandPracticeReportVM> specialistComparisonExpandPracticeReportData;
	public List<SpecialistComparisonExpandPatientReportVM> specialistComparisonExpandPatientReportData;
	
	public List<PatientVisitReportVM> patientVisitReportData;
	public List<SummaryReportVM> summaryReportData;
	public List<SettledMonthsReportVM> settledMonthsData;
	public List<SettledMonthsExpandReportVM> settledMonthsExpandData;
	public List<PmpmByPracticeReportVM> pmpmByPracticeData;
	public List<ReinsuranceManagementReportVM> reinsuranceManagementData;
	public List<ReinsuranceCostReportVM> reinsuranceCostReportData;
	public List<MembershipManagementPatientTypeVM> membershipManagementData;
	public List<PmpmByPracticeExpandReportVM> pmpmByPracticeExpandData;
	public List<PatientVisitExpandReportVM> patientVisitExpandReportData;
	public List<BeneficiariesManagementReportVM> beneficiariesManagementData;
	public List<BeneficiariesManagementByLocationReportVM>BeneficiariesManagementByLocationData;
	public List<BeneficiariesManagementByClinicReportVM> BeneficiariesManagementByClinicData;
	public List<BeneficiariesManagementExpandReportVM> beneficiariesManagementExpandData;
	public List<DuplicateClaimsExpandVM> duplicateClaimsExpandData;
	public List<BeneficiariesManagementByDoctorReportVM> beneficiariesManagementByDoctorData;
	public List<BeneficiariesManagementExpandReportVM> beneficiariesManagementByLocationExpandData;
	public List<BeneficiariesManagementExpandReportVM> beneficiariesManagementByClinicExpandData;
	public List<BeneficiariesManagementExpandReportVM> beneficiariesManagementByDoctorExpandData;
	public Integer pages;
	public Integer totalCount;
	public String fileQuery;
	
	
	public List<ReinsuranceCostReportVM> getReinsuranceCostReportData() {
		return reinsuranceCostReportData;
	}
	public void setReinsuranceCostReportData(List<ReinsuranceCostReportVM> reinsuranceCostReportData) {
		this.reinsuranceCostReportData = reinsuranceCostReportData;
	}
	public List<BeneficiariesManagementExpandReportVM> getBeneficiariesManagementByClinicExpandData() {
		return beneficiariesManagementByClinicExpandData;
	}
	public void setBeneficiariesManagementByClinicExpandData(
			List<BeneficiariesManagementExpandReportVM> beneficiariesManagementByClinicExpandData) {
		this.beneficiariesManagementByClinicExpandData = beneficiariesManagementByClinicExpandData;
	}
	public List<BeneficiariesManagementExpandReportVM> getBeneficiariesManagementByLocationExpandData() {
		return beneficiariesManagementByLocationExpandData;
	}
	public void setBeneficiariesManagementByLocationExpandData(
			List<BeneficiariesManagementExpandReportVM> beneficiariesManagementByLocationExpandData) {
		this.beneficiariesManagementByLocationExpandData = beneficiariesManagementByLocationExpandData;
	}
	
	public List<DuplicateClaimsReportVM> getDuplicateClaimsReportData() {
		return duplicateClaimsReportData;
	}
	public void setDuplicateClaimsReportData(List<DuplicateClaimsReportVM> duplicateClaimsReportData) {
		this.duplicateClaimsReportData = duplicateClaimsReportData;
	}
	public Integer getPages() {
		return pages;
	}
	public void setPages(Integer pages) {
		this.pages = pages;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public List<BeneficiariesManagementByClinicReportVM> getBeneficiariesManagementByClinicData() {
		return BeneficiariesManagementByClinicData;
	}
	public void setBeneficiariesManagementByClinicData(
			List<BeneficiariesManagementByClinicReportVM> beneficiariesManagementByClinicData) {
		BeneficiariesManagementByClinicData = beneficiariesManagementByClinicData;
	}
	public String getFileQuery() {
		return fileQuery;
	}
	public void setFileQuery(String fileQuery) {
		this.fileQuery = fileQuery;
	}
	public List<AdmissionsReportVM> getAdmissionsReportData() {
		return admissionsReportData;
	}
	public void setAdmissionsReportData(List<AdmissionsReportVM> admissionsReportData) {
		this.admissionsReportData = admissionsReportData;
	}
	public List<AdmissionsReportExpandVM> getAdmissionsReportExpandData() {
		return admissionsReportExpandData;
	}
	public void setAdmissionsReportExpandData(List<AdmissionsReportExpandVM> admissionsReportExpandData) {
		this.admissionsReportExpandData = admissionsReportExpandData;
	}
	public List<SpecialistComparisonReportVM> getSpecialistComparisonReportData() {
		return specialistComparisonReportData;
	}
	public void setSpecialistComparisonReportData(List<SpecialistComparisonReportVM> specialistComparisonReportData) {
		this.specialistComparisonReportData = specialistComparisonReportData;
	}
	public List<PatientVisitReportVM> getPatientVisitReportData() {
		return patientVisitReportData;
	}
	public void setPatientVisitReportData(List<PatientVisitReportVM> patientVisitReportData) {
		this.patientVisitReportData = patientVisitReportData;
	}
	public List<SummaryReportVM> getSummaryReportData() {
		return summaryReportData;
	}
	public void setSummaryReportData(List<SummaryReportVM> summaryReportData) {
		this.summaryReportData = summaryReportData;
	}
	public List<SettledMonthsReportVM> getSettledMonthsData() {
		return settledMonthsData;
	}
	public void setSettledMonthsData(List<SettledMonthsReportVM> settledMonthsData) {
		this.settledMonthsData = settledMonthsData;
	}
	public List<SettledMonthsExpandReportVM> getSettledMonthsExpandData() {
		return settledMonthsExpandData;
	}
	public void setSettledMonthsExpandData(List<SettledMonthsExpandReportVM> settledMonthsExpandData) {
		this.settledMonthsExpandData = settledMonthsExpandData;
	}
	public List<PmpmByPracticeReportVM> getPmpmByPracticeData() {
		return pmpmByPracticeData;
	}
	public void setPmpmByPracticeData(List<PmpmByPracticeReportVM> pmpmByPracticeData) {
		this.pmpmByPracticeData = pmpmByPracticeData;
	}
	public void setReinsuranceManagementData(List<ReinsuranceManagementReportVM> reinsuranceManagementData) {
		this.reinsuranceManagementData=reinsuranceManagementData;
	}
	public List<MembershipManagementPatientTypeVM> getMembershipManagementData() {
		return membershipManagementData;
	}
	public void setMembershipManagementData(List<MembershipManagementPatientTypeVM> membershipManagementData) {
		this.membershipManagementData = membershipManagementData;
	}
	public List<PmpmByPracticeExpandReportVM> getPmpmByPracticeExpandData() {
		return pmpmByPracticeExpandData;
	}
	public void setPmpmByPracticeExpandData(List<PmpmByPracticeExpandReportVM> pmpmByPracticeExpandData) {
		this.pmpmByPracticeExpandData = pmpmByPracticeExpandData;
	}
	public List<PatientVisitExpandReportVM> getPatientVisitExpandReportData() {
		return patientVisitExpandReportData;
	}
	public void setPatientVisitExpandReportData(List<PatientVisitExpandReportVM> patientVisitExpandReportData) {
		this.patientVisitExpandReportData = patientVisitExpandReportData;
	}
	public List<BeneficiariesManagementReportVM> getBeneficiariesManagementData() {
		return beneficiariesManagementData;
	}
	public void setBeneficiariesManagementData(List<BeneficiariesManagementReportVM> beneficiariesManagementData) {
		this.beneficiariesManagementData = beneficiariesManagementData;
	}
	public List<BeneficiariesManagementExpandReportVM> getBeneficiariesManagementExpandData() {
		return beneficiariesManagementExpandData;
	}
	public void setBeneficiariesManagementExpandData(
			List<BeneficiariesManagementExpandReportVM> beneficiariesManagementExpandData) {
		this.beneficiariesManagementExpandData = beneficiariesManagementExpandData;
	}
	public List<DuplicateClaimsExpandVM> getDuplicateClaimsExpandData() {
		return duplicateClaimsExpandData;
	}
	public void setDuplicateClaimsExpandData(List<DuplicateClaimsExpandVM> duplicateClaimsExpandData) {
		this.duplicateClaimsExpandData = duplicateClaimsExpandData;
	}
	public List<SpecialistComparisonExpandReportVM> getSpecialistComparisonExpandReportData() {
		return specialistComparisonExpandReportData;
	}
	public void setSpecialistComparisonExpandReportData(
			List<SpecialistComparisonExpandReportVM> specialistComparisonExpandReportData) {
		this.specialistComparisonExpandReportData = specialistComparisonExpandReportData;
	}
	public List<BeneficiariesManagementByDoctorReportVM> getBeneficiariesManagementByDoctorData() {
		return beneficiariesManagementByDoctorData;
	}
	public void setBeneficiariesManagementByDoctorData(
			List<BeneficiariesManagementByDoctorReportVM> beneficiariesManagementByDoctorData) {
		this.beneficiariesManagementByDoctorData = beneficiariesManagementByDoctorData;
	}
	public List<BeneficiariesManagementExpandReportVM> getBeneficiariesManagementByDoctorExpandData() {
		return beneficiariesManagementByDoctorExpandData;
	}
	public void setBeneficiariesManagementByDoctorExpandData(
			List<BeneficiariesManagementExpandReportVM> beneficiariesManagementByDoctorExpandData) {
		this.beneficiariesManagementByDoctorExpandData = beneficiariesManagementByDoctorExpandData;
	}
	public List<SpecialistComparisonExpandPracticeReportVM> getSpecialistComparisonExpandPracticeReportData() {
		return specialistComparisonExpandPracticeReportData;
	}
	public void setSpecialistComparisonExpandPracticeReportData(
			List<SpecialistComparisonExpandPracticeReportVM> specialistComparisonExpandPracticeReportData) {
		this.specialistComparisonExpandPracticeReportData = specialistComparisonExpandPracticeReportData;
	}
	public List<ReinsuranceManagementReportVM> getReinsuranceManagementData() {
		return reinsuranceManagementData;
	}
	public List<BeneficiariesManagementByLocationReportVM> getBeneficiariesManagementByLocationData() {
		return BeneficiariesManagementByLocationData;
	}
	public void setBeneficiariesManagementByLocationData(
			List<BeneficiariesManagementByLocationReportVM> beneficiariesManagementBtLocationData) {
		BeneficiariesManagementByLocationData = beneficiariesManagementBtLocationData;
	}
	
	public List<SpecialistComparisonExpandPatientReportVM> getSpecialistComparisonExpandPatientReportData() {
		return specialistComparisonExpandPatientReportData;
	}
	public void setSpecialistComparisonExpandPatientReportData(
			List<SpecialistComparisonExpandPatientReportVM> specialistComparisonExpandPatientReportData) {
		this.specialistComparisonExpandPatientReportData = specialistComparisonExpandPatientReportData;
	}
}
