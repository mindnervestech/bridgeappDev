package com.mnt.service;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

import com.itextpdf.text.DocumentException;
import com.mnt.vm.AdmissionsReportExpandFileVM;
import com.mnt.vm.AdmissionsReportExpandPrintDataVM;
import com.mnt.vm.AdmissionsReportFileVM;
import com.mnt.vm.AdmissionsReportPrintDataVM;
import com.mnt.vm.ClaimDetailsVM;
import com.mnt.vm.DashboardReportsVM;
import com.mnt.vm.DrugsVM;
import com.mnt.vm.DuplicateClaimFileVM;
import com.mnt.vm.DuplicateClaimPrintDataVM;
import com.mnt.vm.FileVM;
import com.mnt.vm.MembershipManagementFileVM;
import com.mnt.vm.MembershipManagementPrintDataVM;
import com.mnt.vm.MembershipManagementVM;
import com.mnt.vm.OptionsVM;
import com.mnt.vm.PatientVisitReportFileVM;
import com.mnt.vm.PatientVisitReportPrintDataVM;
import com.mnt.vm.PrintDataVM;
import com.mnt.vm.ReportGridVM;
import com.mnt.vm.ReportVM;
import com.mnt.vm.SpecialistComparisonPrintDataVM;
import com.mnt.vm.SpecialistComparisonReportFileVM;
import com.mnt.vm.SummaryReportFileVM;
import com.mnt.vm.SummaryReportPrintDataVM;
import com.mnt.vm.reports.BeneficiariesManagementByClinicReportExpandFileVM;
import com.mnt.vm.reports.BeneficiariesManagementByClinicReportFileVM;
import com.mnt.vm.reports.BeneficiariesManagementByClinicReportPrintDataVM;
import com.mnt.vm.reports.BeneficiariesManagementByDoctorFileVM;
import com.mnt.vm.reports.BeneficiariesManagementByDoctorPrintDataVM;
import com.mnt.vm.reports.BeneficiariesManagementByDoctorReportExpandFileVM;
import com.mnt.vm.reports.BeneficiariesManagementByLocationReportExpandFileVM;
import com.mnt.vm.reports.BeneficiariesManagementByLocationReportFileVM;
import com.mnt.vm.reports.BeneficiariesManagementByLocationReportPrintDataVM;
import com.mnt.vm.reports.BeneficiariesManagementExpandReportPrintDataVM;
import com.mnt.vm.reports.BeneficiariesManagementReportExpandFileVM;
import com.mnt.vm.reports.BeneficiariesManagementReportFileVM;
import com.mnt.vm.reports.BeneficiariesManagementReportPrintDataVM;
import com.mnt.vm.reports.DuplicateClaimsExpandFileVM;
import com.mnt.vm.reports.DuplicateClaimsExpandPrintDataVM;
import com.mnt.vm.reports.PatientVisitExpandReportFileVM;
import com.mnt.vm.reports.PatientVisitExpandReportPrintDataVM;
import com.mnt.vm.reports.PmpmByPracticeExpandReportFileVM;
import com.mnt.vm.reports.PmpmByPracticeExpandReportPrintDataVM;
import com.mnt.vm.reports.PmpmByPracticeReportFileVM;
import com.mnt.vm.reports.PmpmByPracticeReportPrintDataVM;
import com.mnt.vm.reports.ReinsuranceCostReportFileVM;
import com.mnt.vm.reports.ReinsuranceCostReportPrintDataVM;
import com.mnt.vm.reports.ReinsuranceManagementReportFileVM;
import com.mnt.vm.reports.ReinsuranceManagementReportPrintDataVM;
import com.mnt.vm.reports.SettledMonthsExpandReportFileVM;
import com.mnt.vm.reports.SettledMonthsExpandReportPrintDataVM;
import com.mnt.vm.reports.SettledMonthsReportFileVM;
import com.mnt.vm.reports.SettledMonthsReportPrintDataVM;
import com.mnt.vm.reports.SpecialistComparisonExpandPatientPrintDataVM;
import com.mnt.vm.reports.SpecialistComparisonExpandPatientReportFileVM;
import com.mnt.vm.reports.SpecialistComparisonExpandPrintDataVM;
import com.mnt.vm.reports.SpecialistComparisonExpandReportFileVM;

public interface DashboardService {

	DrugsVM getTop20PrescriptionDrugs(String type, String provider);
	DrugsVM getPlanAndPCPList();
	List<OptionsVM> getPCPForAllProviders(String provider);
	List<OptionsVM> getLocationsByYear(String year);
	DrugsVM getMonthlyTotalsReportYears();
	DrugsVM getSpecialityList();
	ClaimDetailsVM getAllClaimDetails(String year, List<String> providerArr, String optionName, List<OptionsVM> locationList);
	ClaimDetailsVM getMonthlyTotalsReport(ClaimDetailsVM vm);
	ReportGridVM generateClaimReport(ReportVM vm);
	DashboardReportsVM getDuplicateClaimsData(ReportVM vm);
	DashboardReportsVM getDuplicateClaimsExpandData(ReportVM vm);
	DashboardReportsVM getAdmissionsReportData(ReportVM vm);
	DashboardReportsVM getSummaryReportData(ReportVM vm);
	DashboardReportsVM getPatientVisitReportData(ReportVM vm);
	DashboardReportsVM getPatientVisitExpandReportData(ReportVM vm);
	DashboardReportsVM getSettledMonthsReportData(ReportVM vm);
	DashboardReportsVM getSettledMonthsExpandReportData(ReportVM vm);
	DashboardReportsVM getPmpmByPracticeReportData(ReportVM vm);
	DashboardReportsVM getBeneficiariesManagementReportData(ReportVM vm);
	DashboardReportsVM getBeneficiariesManagementByDoctorReportData(ReportVM vm);
	DashboardReportsVM getBeneficiariesManagementExpandReportData(ReportVM vm);
	DashboardReportsVM getBeneficiariesManagementByDoctorExpandReportData(ReportVM vm);
	DashboardReportsVM getPmpmByPracticeExpandReportData(ReportVM vm);
	DashboardReportsVM getMembershipManagementPatientsData(ReportVM vm);
	DashboardReportsVM getAdmissionsReportExpandData(ReportVM vm);
	DashboardReportsVM getSpecialistComparisonReportData(ReportVM vm);
	DashboardReportsVM getSpecialistComparisonExpandReportData(ReportVM vm);
	DashboardReportsVM getSpecialistComparisonExpandPracticeReportData(ReportVM vm);
	
	MembershipManagementVM getMembershipManagementData(ReportVM vm);
	List<PrintDataVM> getDataForPrint(FileVM fileVM);
	List<DuplicateClaimPrintDataVM> getDataForDuplicateClaimPrint(DuplicateClaimFileVM vm);
	List<AdmissionsReportPrintDataVM> getDataForAdmissionsReportPrint(AdmissionsReportFileVM vm);
	List<DuplicateClaimsExpandPrintDataVM> getDataForDuplicateClaimsExpandPrint(DuplicateClaimsExpandFileVM vm);
	List<PatientVisitReportPrintDataVM> getDataForPatientVisitReportPrint(PatientVisitReportFileVM vm);
	List<PatientVisitExpandReportPrintDataVM> getDataForPatientVisitExpandReportPrint(PatientVisitExpandReportFileVM vm);
	List<SettledMonthsReportPrintDataVM> getDataForSettledMonthsReportPrint(SettledMonthsReportFileVM vm);
	List<SettledMonthsExpandReportPrintDataVM> getDataForSettledMonthsExpandReportPrint(SettledMonthsExpandReportFileVM vm);
	List<PmpmByPracticeReportPrintDataVM> getDataForPmpmByPracticeReportPrint(PmpmByPracticeReportFileVM vm);
	List<BeneficiariesManagementReportPrintDataVM> getDataForBeneficiariesManagementReportPrint(BeneficiariesManagementReportFileVM vm);
	List<BeneficiariesManagementExpandReportPrintDataVM> getDataForBeneficiariesManagementExpandReportPrint(BeneficiariesManagementReportExpandFileVM vm);
	List<BeneficiariesManagementExpandReportPrintDataVM> getDataForBeneficiariesManagementByDoctorExpandReportPrint(BeneficiariesManagementByDoctorReportExpandFileVM vm);
	List<BeneficiariesManagementByDoctorPrintDataVM> getDataForBeneficiariesManagementByDoctorPrint(BeneficiariesManagementByDoctorFileVM vm);
	List<PmpmByPracticeExpandReportPrintDataVM> getDataForPmpmByPracticeExpandReportPrint(PmpmByPracticeExpandReportFileVM vm);
	List<MembershipManagementPrintDataVM> getMembershipManagementPrint(MembershipManagementFileVM vm);
	List<AdmissionsReportExpandPrintDataVM> getDataForAdmissionsReportExpandPrint(AdmissionsReportExpandFileVM vm);
	List<SpecialistComparisonPrintDataVM> getDataForSpecialistComparisonPrint(SpecialistComparisonReportFileVM vm);
	List<SpecialistComparisonExpandPrintDataVM> getDataForSpecialistComparisonExpandPrint(SpecialistComparisonExpandReportFileVM vm);
	List<SummaryReportPrintDataVM> getDataForSummaryReportPrint(SummaryReportFileVM vm);
	void generatePDF(FileVM fileVM, OutputStream os) throws SQLException, IOException, DocumentException;
	void generateDuplicateClaimPDF(DuplicateClaimFileVM fileVM, OutputStream os) throws SQLException, IOException, DocumentException;
	void generateAdmissionsReportPDF(AdmissionsReportFileVM fileVM, OutputStream os) throws SQLException, IOException, DocumentException;
	void generatePatientVisitReportPDF(PatientVisitReportFileVM fileVM, OutputStream os) throws SQLException, IOException, DocumentException;
	void generatePatientVisitExpandReportPDF(PatientVisitExpandReportFileVM fileVM, OutputStream os) throws SQLException, IOException, DocumentException;
	void generateSettledMonthsReportPDF(SettledMonthsReportFileVM fileVM, OutputStream os) throws SQLException, IOException, DocumentException;
	void generateSettledMonthsExpandReportPDF(SettledMonthsExpandReportFileVM fileVM, OutputStream os) throws SQLException, IOException, DocumentException;
	void generatePmpmByPracticeReportPDF(PmpmByPracticeReportFileVM fileVM, OutputStream os) throws SQLException, IOException, DocumentException;
	void generatePmpmByPracticeExpandReportPDF(PmpmByPracticeExpandReportFileVM fileVM, OutputStream os) throws SQLException, IOException, DocumentException;
	void generateBeneficiariesManagementReportPDF(BeneficiariesManagementReportFileVM fileVM, OutputStream os) throws SQLException, IOException, DocumentException;
	void generateBeneficiariesManagementExpandReportPDF(BeneficiariesManagementReportExpandFileVM fileVM, OutputStream os) throws SQLException, IOException, DocumentException;
	void generateBeneficiariesManagementByDoctorExpandReportPDF(BeneficiariesManagementByDoctorReportExpandFileVM fileVM, OutputStream os) throws SQLException, IOException, DocumentException;
	void generateBeneficiariesManagementByDoctorPDF(BeneficiariesManagementByDoctorFileVM fileVM, OutputStream os) throws SQLException, IOException, DocumentException;
	void generateDuplicateClaimsExpandPDF(DuplicateClaimsExpandFileVM fileVM, OutputStream os) throws SQLException, IOException, DocumentException;
	void generateMembershipManagementPDF(MembershipManagementFileVM fileVM, OutputStream os) throws SQLException, IOException, DocumentException;
	void generateAdmissionsReportExpandPDF(AdmissionsReportExpandFileVM fileVM, OutputStream os) throws SQLException, IOException, DocumentException;
	void generateSpecialistComparisonReportPDF(SpecialistComparisonReportFileVM fileVM, OutputStream os) throws SQLException, IOException, DocumentException;
	void generateSpecialistComparisonExpandReportPDF(SpecialistComparisonExpandReportFileVM fileVM, OutputStream os) throws SQLException, IOException, DocumentException;
	void generateSummaryReportPDF(SummaryReportFileVM fileVM, OutputStream os) throws SQLException, IOException, DocumentException;
	public void generateXLSX(FileVM fileVM, OutputStream os)  throws IOException;
	public void generateDuplicateClaimsXLSX(DuplicateClaimFileVM fileVM, OutputStream os)  throws IOException;
	public void generateAdmissionsReportXLSX(AdmissionsReportFileVM fileVM, OutputStream os)  throws IOException;
	public void generatePatientVisitReportXLSX(PatientVisitReportFileVM fileVM, OutputStream os)  throws IOException;
	public void generatePatientVisitExpandReportXLSX(PatientVisitExpandReportFileVM fileVM, OutputStream os)  throws IOException;
	public void generateSettledMonthsReportXLSX(SettledMonthsReportFileVM fileVM, OutputStream os)  throws IOException;
	public void generateSettledMonthsExpandReportXLSX(SettledMonthsExpandReportFileVM fileVM, OutputStream os)  throws IOException;
	public void generatePmpmByPracticeReportXLSX(PmpmByPracticeReportFileVM fileVM, OutputStream os)  throws IOException;
	public void generatePmpmByPracticeExpandReportXLSX(PmpmByPracticeExpandReportFileVM fileVM, OutputStream os)  throws IOException;
	public void generateBeneficiariesManagementReportXLSX(BeneficiariesManagementReportFileVM fileVM, OutputStream os)  throws IOException;
	public void generateBeneficiariesManagementExpandReportXLSX(BeneficiariesManagementReportExpandFileVM fileVM, OutputStream os)  throws IOException;
	public void generateBeneficiariesManagementByDoctorExpandReportXLSX(BeneficiariesManagementByDoctorReportExpandFileVM fileVM, OutputStream os)  throws IOException;
	public void generateBeneficiariesManagementByDoctorXLSX(BeneficiariesManagementByDoctorFileVM fileVM, OutputStream os)  throws IOException;
	public void generateDuplicateClaimsExpandReportXLSX(DuplicateClaimsExpandFileVM fileVM, OutputStream os)  throws IOException;
	public void generateMembershipManagementXLSX(MembershipManagementFileVM fileVM, OutputStream os)  throws IOException;
	public void generateAdmissionsReportExpandXLSX(AdmissionsReportExpandFileVM fileVM, OutputStream os)  throws IOException;
	public void generateSpecialistComparisonReportXLSX(SpecialistComparisonReportFileVM fileVM, OutputStream os)  throws IOException;
	public void generateSpecialistComparisonExpandReportXLSX(SpecialistComparisonExpandReportFileVM fileVM, OutputStream os)  throws IOException;
	public void generateSummaryReportXLSX(SummaryReportFileVM fileVM, OutputStream os)  throws IOException;
	public DashboardReportsVM getReinsuranceManagementData(ReportVM vm);
	public void generateReinsuranceManagementReportXLSX(ReinsuranceManagementReportFileVM fileVM, OutputStream outputStream) throws IOException;
	public void generateReisuranceManagementReportPDF(ReinsuranceManagementReportFileVM fileVM, OutputStream outputStream) throws SQLException, IOException, DocumentException;
	public List<ReinsuranceManagementReportPrintDataVM> getDataForReinsuranceManagementReportPrint(
			ReinsuranceManagementReportFileVM vm);
	public DashboardReportsVM getBeneficiariesManagementByLocationReportData(ReportVM vm);
	public DashboardReportsVM getBeneficiariesManagementByClinicReportData(ReportVM vm);
	public List<BeneficiariesManagementByLocationReportPrintDataVM> getDataForBeneficiariesManagementByLocationReportPrint(
			BeneficiariesManagementByLocationReportFileVM vm);
	public List<BeneficiariesManagementByClinicReportPrintDataVM> getDataForBeneficiariesManagementByClinicReportPrint(
			BeneficiariesManagementByClinicReportFileVM vm);
	public void generateBeneficiariesManagementByLocationReportXLSX(BeneficiariesManagementByLocationReportFileVM fileVM,
			OutputStream outputStream) throws IOException;
	public void generateBeneficiariesManagementByClinicReportXLSX(BeneficiariesManagementByClinicReportFileVM fileVM,
			OutputStream outputStream) throws IOException;
	public void generateBeneficiariesManagementByLocationReportPDF(BeneficiariesManagementByLocationReportFileVM fileVM,
			OutputStream outputStream) throws SQLException, IOException, DocumentException;
	public DashboardReportsVM getBeneficiariesManagementByLocationExpandReportData(ReportVM vm);
	
	public List<BeneficiariesManagementExpandReportPrintDataVM> getDataForBeneficiariesManagementByLocationExpandReportPrint(
			BeneficiariesManagementByLocationReportExpandFileVM vm);
	public void generateBeneficiariesManagementByLocationExpandReportXLSX(
			BeneficiariesManagementByLocationReportExpandFileVM fileVM, OutputStream outputStream) throws IOException;
	public void generateBeneficiariesManagementByLocationExpandReportPDF(
			BeneficiariesManagementByLocationReportExpandFileVM fileVM, OutputStream outputStream) throws SQLException, IOException, DocumentException;
	public void generateBeneficiariesManagementByClinicReportPDF(BeneficiariesManagementByClinicReportFileVM fileVM,	OutputStream outputStream) throws SQLException, IOException, DocumentException;
	public DashboardReportsVM getBeneficiariesManagementByClinicExpandReportData(ReportVM vm);
	public List<BeneficiariesManagementExpandReportPrintDataVM> getDataForBeneficiariesManagementByClinicExpandReportPrint(
			BeneficiariesManagementByClinicReportExpandFileVM vm);
	public void generateBeneficiariesManagementByClinicExpandReportXLSX(
			BeneficiariesManagementByClinicReportExpandFileVM fileVM, OutputStream outputStream) throws IOException;
	public void generateBeneficiariesManagementByClinicExpandReportPDF(
			BeneficiariesManagementByClinicReportExpandFileVM fileVM, OutputStream outputStream) throws SQLException, IOException, DocumentException;
	public DashboardReportsVM getReinsuranceCostReportData(ReportVM vm);
	public List<ReinsuranceCostReportPrintDataVM> getDataForReinsuranceCostReportPrint(ReinsuranceCostReportFileVM vm);
	public void generateReinsuranceCostReportXLSX(ReinsuranceCostReportFileVM fileVM, OutputStream outputStream) throws IOException;
	public void generateCostManagementReportPDF(ReinsuranceCostReportFileVM fileVM, OutputStream outputStream) throws SQLException, IOException, DocumentException;
	public void generateAdmissionsHeaderReportPDF(AdmissionHeaderReportFileVM fileVM, OutputStream outputStream) throws SQLException, IOException, DocumentException;
	public void generateAdmissionsHeaderReportXLSX(AdmissionHeaderReportFileVM fileVM, OutputStream outputStream) throws IOException;
	public DashboardReportsVM getSpecialistComparisonExpandPatientReportData(ReportVM vm);
	public List<SpecialistComparisonExpandPatientPrintDataVM> getDataForSpecialistComparisonPatientExpandPrint(
			SpecialistComparisonExpandPatientReportFileVM vm);
	public void generateSpecialistComparisonExpandPatientReportPDF(SpecialistComparisonExpandPatientReportFileVM fileVM,
			OutputStream outputStream) throws SQLException, IOException, DocumentException;
	public void generateSpecialistComparisonExpandPatientReportXLSX(SpecialistComparisonExpandPatientReportFileVM fileVM,
			OutputStream outputStream) throws IOException;




}
