package com.mnt.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.DocumentException;
import com.mnt.dao.HccCodeValuesDao;
import com.mnt.dao.MonthlyTotalsDataDao;
import com.mnt.dao.MonthlyTotalsReportDao;
import com.mnt.domain.HccCodeValues;
import com.mnt.domain.MonthlyTotalsData;
import com.mnt.domain.MonthlyTotalsReport;
import com.mnt.service.DashboardService;
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
import com.mnt.vm.reports.BeneficiariesManagementByClinicReportFileVM;
import com.mnt.vm.reports.BeneficiariesManagementByClinicReportPrintDataVM;
import com.mnt.vm.reports.BeneficiariesManagementByDoctorFileVM;
import com.mnt.vm.reports.BeneficiariesManagementByDoctorPrintDataVM;
import com.mnt.vm.reports.BeneficiariesManagementByDoctorReportExpandFileVM;
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
import com.mnt.vm.reports.SettledMonthsExpandReportFileVM;
import com.mnt.vm.reports.SettledMonthsExpandReportPrintDataVM;
import com.mnt.vm.reports.SettledMonthsReportFileVM;
import com.mnt.vm.reports.SettledMonthsReportPrintDataVM;
import com.mnt.vm.reports.SpecialistComparisonExpandPrintDataVM;
import com.mnt.vm.reports.SpecialistComparisonExpandReportFileVM;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@RestController
public class DashboardController {

	@Autowired
	DashboardService dashboardService;
	
	@Autowired
	MonthlyTotalsReportDao monthlyTotalsReportDao;
	@Autowired
	MonthlyTotalsDataDao monthlyTotalsDataDao;
	@Autowired
	HccCodeValuesDao hccCodeValuesDao;
	
	@RequestMapping(value="/getTopPrescriptionDrugs/{type}/{provider}",method = RequestMethod.GET)
	@ResponseBody
    public DrugsVM getTopPrescriptionDrugs(@PathVariable("type") String type, @PathVariable("provider") String provider) {
		return dashboardService.getTop20PrescriptionDrugs(type.toLowerCase(), provider);
    }
	
	@RequestMapping(value="/getAllPlanAndPCP",method = RequestMethod.GET)
	@ResponseBody
    public DrugsVM getAllPlanAndPCP() {
		return dashboardService.getPlanAndPCPList();
    }
	
	@RequestMapping(value="/getLocationsByYear",method = RequestMethod.POST)
	@ResponseBody
    public List<OptionsVM> getLocationsByYear(@RequestParam(value="year") String year) {
		return dashboardService.getLocationsByYear(year);
    }
	
	@RequestMapping(value="/getPCPForAllProviders",method = RequestMethod.POST)
	@ResponseBody
    public List<OptionsVM> getPCPForAllProviders(@RequestParam(value="providerArr") List<String> providerArr) {
		return dashboardService.getPCPForAllProviders(providerArr.get(0));
    }
	
	@RequestMapping(value="/getMonthlyTotalsReportYears",method = RequestMethod.GET)
	@ResponseBody
    public DrugsVM getMonthlyTotalsReportYears() {
		return dashboardService.getMonthlyTotalsReportYears();
    }
	
	@RequestMapping(value="/getSpeciality",method = RequestMethod.GET)
	@ResponseBody
    public DrugsVM getSpeciality() {
		return dashboardService.getSpecialityList();
    }
	
	@RequestMapping(value="/getAllClaimDetails",method = RequestMethod.POST)
	@ResponseBody
    public ClaimDetailsVM getAllClaimDetails(@RequestParam(value="year") String year, 
    		@RequestParam(value="providerArr") List<String> providerArr,
    		@RequestParam(value="optionName") String optionName,@RequestParam(value="locationName") String locationlist) throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		List<OptionsVM> optionsList = null;
		try {
			optionsList = mapper.readValue(locationlist, new TypeReference<List<OptionsVM>>(){});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dashboardService.getAllClaimDetails(year, providerArr, optionName, optionsList);
    }
	
	@RequestMapping(value="/getMonthlyTotalsReport",method = RequestMethod.POST)
	@ResponseBody
    public ClaimDetailsVM getMonthlyTotalsReport(ClaimDetailsVM vm) {
		
		return dashboardService.getMonthlyTotalsReport(vm);
    }
	
	@RequestMapping(value="/generateClaimReport",method = RequestMethod.POST)
	@ResponseBody
    public ReportGridVM generateClaimReport(ReportVM vm) {
		return dashboardService.generateClaimReport(vm);
    }
	
	@RequestMapping(value="/getDuplicateClaimsData",method = RequestMethod.POST)
	@ResponseBody
    public DashboardReportsVM getDuplicateClaimsData(ReportVM vm) {
		return dashboardService.getDuplicateClaimsData(vm);
    }
	
	@RequestMapping(value="/getDuplicateClaimsExpandData",method = RequestMethod.POST)
	@ResponseBody
    public DashboardReportsVM getDuplicateClaimsExpandData(ReportVM vm) {
		return dashboardService.getDuplicateClaimsExpandData(vm);
    }
	
	@RequestMapping(value="/getAdmissionsReportData",method = RequestMethod.POST)
	@ResponseBody
    public DashboardReportsVM getAdmissionsReportData(ReportVM vm) {
		return dashboardService.getAdmissionsReportData(vm);
    }
	
	@RequestMapping(value="/getSummaryReportData",method = RequestMethod.POST)
	@ResponseBody
    public DashboardReportsVM getSummaryReportData(ReportVM vm) {
		return dashboardService.getSummaryReportData(vm);
    }
	
	@RequestMapping(value="/getPatientVisitReportData",method = RequestMethod.POST)
	@ResponseBody
    public DashboardReportsVM getPatientVisitReportData(ReportVM vm) {
		return dashboardService.getPatientVisitReportData(vm);
    }
	
	@RequestMapping(value="/getPatientVisitExpandReportData",method = RequestMethod.POST)
	@ResponseBody
    public DashboardReportsVM getPatientVisitExpandReportData(ReportVM vm) {
		return dashboardService.getPatientVisitExpandReportData(vm);
    }
	
	@RequestMapping(value="/getSettledMonthsData",method = RequestMethod.POST)
	@ResponseBody
    public DashboardReportsVM getSettledMonthsData(ReportVM vm) {
		return dashboardService.getSettledMonthsReportData(vm);
    }
	
	@RequestMapping(value="/getSettledMonthsExpandData",method = RequestMethod.POST)
	@ResponseBody
    public DashboardReportsVM getSettledMonthsExpandData(ReportVM vm) {
		return dashboardService.getSettledMonthsExpandReportData(vm);
    }
	
	@RequestMapping(value="/getPmpmByPracticeData",method = RequestMethod.POST)
	@ResponseBody
    public DashboardReportsVM getPmpmByPracticeData(ReportVM vm) {
		return dashboardService.getPmpmByPracticeReportData(vm);
    }
	
	@RequestMapping(value="/getBeneficiariesManagementData",method = RequestMethod.POST)
	@ResponseBody
    public DashboardReportsVM getBeneficiariesManagementData(ReportVM vm) {
		return dashboardService.getBeneficiariesManagementReportData(vm);
    }
	
	@RequestMapping(value="/getBeneficiariesManagementByLocationData",method = RequestMethod.POST)
	@ResponseBody
    public DashboardReportsVM getBeneficiariesManagementByLocationData(ReportVM vm) {
		return dashboardService.getBeneficiariesManagementByLocationReportData(vm);
    }
	
	@RequestMapping(value="/getBeneficiariesManagementByClinicData",method = RequestMethod.POST)
	@ResponseBody
    public DashboardReportsVM getBeneficiariesManagementByClinicData(ReportVM vm) {
		return dashboardService.getBeneficiariesManagementByClinicReportData(vm);
    }
	
	@RequestMapping(value="/getBeneficiariesManagementByDoctorData",method = RequestMethod.POST)
	@ResponseBody
    public DashboardReportsVM getBeneficiariesManagementByDoctorData(ReportVM vm) {
		return dashboardService.getBeneficiariesManagementByDoctorReportData(vm);
    }
	
	@RequestMapping(value="/getBeneficiariesManagementExpandData",method = RequestMethod.POST)
	@ResponseBody
    public DashboardReportsVM getBeneficiariesManagementExpandData(ReportVM vm) {
		return dashboardService.getBeneficiariesManagementExpandReportData(vm);
    }
	
	@RequestMapping(value="/getBeneficiariesManagementByDoctorExpandData",method = RequestMethod.POST)
	@ResponseBody
    public DashboardReportsVM getBeneficiariesManagementByDoctorExpandData(ReportVM vm) {
		return dashboardService.getBeneficiariesManagementByDoctorExpandReportData(vm);
    }
	
	@RequestMapping(value="/getPmpmByPracticeExpandData",method = RequestMethod.POST)
	@ResponseBody
    public DashboardReportsVM getPmpmByPracticeExpandData(ReportVM vm) {
		return dashboardService.getPmpmByPracticeExpandReportData(vm);
    }
	
	@RequestMapping(value="/getMembershipManagementPatientsData",method = RequestMethod.POST)
	@ResponseBody
    public DashboardReportsVM getMembershipManagementPatientsData(ReportVM vm) {
		return dashboardService.getMembershipManagementPatientsData(vm);
    }
	
	@RequestMapping(value="/getAdmissionsReportExpandData",method = RequestMethod.POST)
	@ResponseBody
    public DashboardReportsVM getAdmissionsReportExpandData(ReportVM vm) {
		return dashboardService.getAdmissionsReportExpandData(vm);
    }
	
	@RequestMapping(value="/getSpecialistComparisonReportData",method = RequestMethod.POST)
	@ResponseBody
    public DashboardReportsVM getSpecialistComparisonReportData(ReportVM vm) {
		return dashboardService.getSpecialistComparisonReportData(vm);
    }
	
	@RequestMapping(value="/getSpecialistComparisonExpandReportData",method = RequestMethod.POST)
	@ResponseBody
    public DashboardReportsVM getSpecialistComparisonExpandReportData(ReportVM vm) {
		return dashboardService.getSpecialistComparisonExpandReportData(vm);
    }
	
	@RequestMapping(value="/getDataForPrint",method = RequestMethod.POST)
	@ResponseBody
    public List<PrintDataVM> getDataForPrint(FileVM vm) {
		return dashboardService.getDataForPrint(vm);
    }
	
	@RequestMapping(value="/getDuplicateClaimDataForPrint",method = RequestMethod.POST)
	@ResponseBody
    public List<DuplicateClaimPrintDataVM> getDuplicateClaimDataForPrint(DuplicateClaimFileVM vm) {
		return dashboardService.getDataForDuplicateClaimPrint(vm);
    }
	
	@RequestMapping(value="/getDuplicateClaimsExpandDataForPrint",method = RequestMethod.POST)
	@ResponseBody
    public List<DuplicateClaimsExpandPrintDataVM> getDuplicateClaimsExpandDataForPrint(DuplicateClaimsExpandFileVM vm) {
		return dashboardService.getDataForDuplicateClaimsExpandPrint(vm);
    }
	
	@RequestMapping(value="/getAdmissionsReportDataForPrint",method = RequestMethod.POST)
	@ResponseBody
    public List<AdmissionsReportPrintDataVM> getAdmissionsReportDataForPrint(AdmissionsReportFileVM vm) {
		return dashboardService.getDataForAdmissionsReportPrint(vm);
    }
	
	@RequestMapping(value="/getPatientVisitReportDataForPrint",method = RequestMethod.POST)
	@ResponseBody
    public List<PatientVisitReportPrintDataVM> getPatientVisitReportDataForPrint(PatientVisitReportFileVM vm) {
		return dashboardService.getDataForPatientVisitReportPrint(vm);
    }
	
	@RequestMapping(value="/getPatientVisitExpandReportDataForPrint",method = RequestMethod.POST)
	@ResponseBody
    public List<PatientVisitExpandReportPrintDataVM> getPatientVisitExpandReportDataForPrint(PatientVisitExpandReportFileVM vm) {
		return dashboardService.getDataForPatientVisitExpandReportPrint(vm);
    }
	
	@RequestMapping(value="/getSettledMonthsReportDataForPrint",method = RequestMethod.POST)
	@ResponseBody
    public List<SettledMonthsReportPrintDataVM> getSettledMonthsReportDataForPrint(SettledMonthsReportFileVM vm) {
		return dashboardService.getDataForSettledMonthsReportPrint(vm);
    }
	
	@RequestMapping(value="/getSettledMonthsExpandReportDataForPrint",method = RequestMethod.POST)
	@ResponseBody
    public List<SettledMonthsExpandReportPrintDataVM> getSettledMonthsExpandReportDataForPrint(SettledMonthsExpandReportFileVM vm) {
		return dashboardService.getDataForSettledMonthsExpandReportPrint(vm);
    }
	
	@RequestMapping(value="/getPmpmByPracticeReportDataForPrint",method = RequestMethod.POST)
	@ResponseBody
    public List<PmpmByPracticeReportPrintDataVM> getPmpmByPracticeReportDataForPrint(PmpmByPracticeReportFileVM vm) {
		return dashboardService.getDataForPmpmByPracticeReportPrint(vm);
    }
	
	@RequestMapping(value="/getBeneficiariesManagementDataForPrint",method = RequestMethod.POST)
	@ResponseBody
    public List<BeneficiariesManagementReportPrintDataVM> getBeneficiariesManagementDataForPrint(BeneficiariesManagementReportFileVM vm) {
		return dashboardService.getDataForBeneficiariesManagementReportPrint(vm);
    }
	
	@RequestMapping(value="/getBeneficiariesManagementExpandDataForPrint",method = RequestMethod.POST)
	@ResponseBody
    public List<BeneficiariesManagementExpandReportPrintDataVM> getBeneficiariesManagementExpandDataForPrint(BeneficiariesManagementReportExpandFileVM vm) {
		return dashboardService.getDataForBeneficiariesManagementExpandReportPrint(vm);
    }
	
	@RequestMapping(value="/getBeneficiariesManagementByDoctorExpandDataForPrint",method = RequestMethod.POST)
	@ResponseBody
    public List<BeneficiariesManagementExpandReportPrintDataVM> getBeneficiariesManagementByDoctorExpandDataForPrint(BeneficiariesManagementByDoctorReportExpandFileVM vm) {
		return dashboardService.getDataForBeneficiariesManagementByDoctorExpandReportPrint(vm);
    }
	
	@RequestMapping(value="/getBeneficiariesManagementByLocationDataForPrint",method = RequestMethod.POST)
	@ResponseBody
    public List<BeneficiariesManagementByLocationReportPrintDataVM> getBeneficiariesManagementByLocationDataForPrint(BeneficiariesManagementByLocationReportFileVM vm) {
		return dashboardService.getDataForBeneficiariesManagementByLocationReportPrint(vm);
    }
	
	@RequestMapping(value="/getBeneficiariesManagementByClinicDataForPrint",method = RequestMethod.POST)
	@ResponseBody
    public List<BeneficiariesManagementByClinicReportPrintDataVM> getBeneficiariesManagementByClinicDataForPrint(BeneficiariesManagementByClinicReportFileVM vm) {
		return dashboardService.getDataForBeneficiariesManagementByClinicReportPrint(vm);
    }
	
	@RequestMapping(value="/getBeneficiariesManagementByDoctorDataForPrint",method = RequestMethod.POST)
	@ResponseBody
    public List<BeneficiariesManagementByDoctorPrintDataVM> getBeneficiariesManagementByDoctorDataForPrint(BeneficiariesManagementByDoctorFileVM vm) {
		return dashboardService.getDataForBeneficiariesManagementByDoctorPrint(vm);
    }
	
	@RequestMapping(value="/getPmpmByPracticeExpandReportDataForPrint",method = RequestMethod.POST)
	@ResponseBody
    public List<PmpmByPracticeExpandReportPrintDataVM> getPmpmByPracticeExpandReportDataForPrint(PmpmByPracticeExpandReportFileVM vm) {
		return dashboardService.getDataForPmpmByPracticeExpandReportPrint(vm);
    }
	
	@RequestMapping(value="/getMembershipManagementDataForPrint",method = RequestMethod.POST)
	@ResponseBody
    public List<MembershipManagementPrintDataVM> getMembershipManagementDataForPrint(MembershipManagementFileVM vm) {
		return dashboardService.getMembershipManagementPrint(vm);
    }
	
	@RequestMapping(value="/getAdmissionsReportExpandDataForPrint",method = RequestMethod.POST)
	@ResponseBody
    public List<AdmissionsReportExpandPrintDataVM> getAdmissionsReportExpandDataForPrint(AdmissionsReportExpandFileVM vm) {
		return dashboardService.getDataForAdmissionsReportExpandPrint(vm);
    }
	
	@RequestMapping(value="/getSpecialistComparisonReportDataForPrint",method = RequestMethod.POST)
	@ResponseBody
    public List<SpecialistComparisonPrintDataVM> getSpecialistComparisonReportDataForPrint(SpecialistComparisonReportFileVM vm) {
		return dashboardService.getDataForSpecialistComparisonPrint(vm);
    }
	
	@RequestMapping(value="/getSpecialistComparisonExpandReportDataForPrint",method = RequestMethod.POST)
	@ResponseBody
    public List<SpecialistComparisonExpandPrintDataVM> getSpecialistComparisonExpandReportDataForPrint(SpecialistComparisonExpandReportFileVM vm) {
		return dashboardService.getDataForSpecialistComparisonExpandPrint(vm);
    }
	
	@RequestMapping(value="/getSummaryReportDataForPrint",method = RequestMethod.POST)
	@ResponseBody
    public List<SummaryReportPrintDataVM> getSummaryReportDataForPrint(SummaryReportFileVM vm) {
		return dashboardService.getDataForSummaryReportPrint(vm);
    }
	
	@RequestMapping(value="/getMembershipManagementData",method = RequestMethod.POST)
	@ResponseBody
    public MembershipManagementVM getMembershipManagementData(ReportVM vm) {
		return dashboardService.getMembershipManagementData(vm);
    }
	
	@RequestMapping(value="/renderPDF/{json}",method = RequestMethod.GET)
	@ResponseBody
	public String renderPDF(@PathVariable String json, HttpServletResponse response) {
		String csv = "";
		byte[] decodedBytes = Base64.getDecoder().decode(json);
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		FileVM fileVM = null;
		
		try {
			fileVM = mapper.readValue(new String(decodedBytes), FileVM.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		response.setContentType("application/pdf");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + "Data export-Claims Search" + ".pdf\"");
		try {
			OutputStream outputStream = response.getOutputStream();
			dashboardService.generatePDF(fileVM,outputStream);
			outputStream.close();
			response.flushBuffer();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return csv;
	}
	
	@RequestMapping(value="/renderXLSX/{json}",method = RequestMethod.GET)
	@ResponseBody
	public String renderXLSX(@PathVariable String json, HttpServletResponse response) {
		String csv = "";
		byte[] decodedBytes = Base64.getDecoder().decode(json);
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		FileVM fileVM = null;
		
		try {
			fileVM = mapper.readValue(new String(decodedBytes), FileVM.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + "Data export-Claims Search" + ".xlsx\"");
		try {
			OutputStream outputStream = response.getOutputStream();
			dashboardService.generateXLSX(fileVM,outputStream);
			outputStream.close();
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return csv;
	}
	
	@RequestMapping(value="/renderDuplicateClaimsXLSX/{json}",method = RequestMethod.GET)
	@ResponseBody
	public String renderDuplicateClaimsXLSX(@PathVariable String json, HttpServletResponse response) {
		String csv = "";
		
		byte[] decodedBytes = Base64.getDecoder().decode(json);
		ObjectMapper mapper = new ObjectMapper();
		DuplicateClaimFileVM fileVM = null;
		try {
			fileVM = mapper.readValue(new String(decodedBytes), DuplicateClaimFileVM.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + "Data export-Duplicate Claims" + ".xlsx\"");
		try {
			OutputStream outputStream = response.getOutputStream();
			dashboardService.generateDuplicateClaimsXLSX(fileVM,outputStream);
			outputStream.close();
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return csv;
	}
	
	@RequestMapping(value="/renderDuplicateClaimsPDF/{json}",method = RequestMethod.GET)
	@ResponseBody
	public String renderDuplicateClaimsPDF(@PathVariable String json, HttpServletResponse response) {
		String csv = "";
		ObjectMapper mapper = new ObjectMapper();
		byte[] decodedBytes = Base64.getDecoder().decode(json);
		DuplicateClaimFileVM fileVM = null;
		
		try {
			fileVM = mapper.readValue(new String(decodedBytes), DuplicateClaimFileVM.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		response.setContentType("application/pdf");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + "Data export-Duplicate Claims" + ".pdf\"");
		try {
			OutputStream outputStream = response.getOutputStream();
			dashboardService.generateDuplicateClaimPDF(fileVM,outputStream);
			outputStream.close();
			response.flushBuffer();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return csv;
	}
	
	@RequestMapping(value="/renderAdmissionsReportXLSX/{json}",method = RequestMethod.GET)
	@ResponseBody
	public String renderAdmissionsReportXLSX(@PathVariable String json, HttpServletResponse response) {
		String csv = "";
		byte[] decodedBytes = Base64.getDecoder().decode(json);
		ObjectMapper mapper = new ObjectMapper();
		AdmissionsReportFileVM fileVM = null;
		try {
			fileVM = mapper.readValue(new String(decodedBytes), AdmissionsReportFileVM.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + "Data export- Admissions Report" + ".xlsx\"");
		try {
			OutputStream outputStream = response.getOutputStream();
			dashboardService.generateAdmissionsReportXLSX(fileVM,outputStream);
			outputStream.close();
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return csv;
	}
	
	@RequestMapping(value="/renderAdmissionsReportPDF/{json}",method = RequestMethod.GET)
	@ResponseBody
	public String renderAdmissionsReportPDF(@PathVariable String json, HttpServletResponse response) {
		String csv = "";
		ObjectMapper mapper = new ObjectMapper();
		byte[] decodedBytes = Base64.getDecoder().decode(json);
		AdmissionsReportFileVM fileVM = null;
		
		try {
			fileVM = mapper.readValue(new String(decodedBytes), AdmissionsReportFileVM.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		response.setContentType("application/pdf");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + "Data export- Admissions Report" + ".pdf\"");
		try {
			OutputStream outputStream = response.getOutputStream();
			dashboardService.generateAdmissionsReportPDF(fileVM,outputStream);
			outputStream.close();
			response.flushBuffer();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return csv;
	}
	
	@RequestMapping(value="/renderAdmissionsReportExpandXLSX/{json}",method = RequestMethod.GET)
	@ResponseBody
	public String renderAdmissionsReportExpandXLSX(@PathVariable String json, HttpServletResponse response) {
		String csv = "";
		byte[] decodedBytes = Base64.getDecoder().decode(json);
		ObjectMapper mapper = new ObjectMapper();
		AdmissionsReportExpandFileVM fileVM = null;
		try {
			fileVM = mapper.readValue(new String(decodedBytes), AdmissionsReportExpandFileVM.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + "Data export- Admissions Report" + ".xlsx\"");
		try {
			OutputStream outputStream = response.getOutputStream();
			dashboardService.generateAdmissionsReportExpandXLSX(fileVM,outputStream);
			outputStream.close();
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return csv;
	}
	
	@RequestMapping(value="/renderAdmissionsReportExpandPDF/{json}",method = RequestMethod.GET)
	@ResponseBody
	public String renderAdmissionsReportExpandPDF(@PathVariable String json, HttpServletResponse response) {
		String csv = "";
		ObjectMapper mapper = new ObjectMapper();
		byte[] decodedBytes = Base64.getDecoder().decode(json);
		AdmissionsReportExpandFileVM fileVM = null;
		
		try {
			fileVM = mapper.readValue(new String(decodedBytes), AdmissionsReportExpandFileVM.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		response.setContentType("application/pdf");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + "Data export- Admissions Report" + ".pdf\"");
		try {
			OutputStream outputStream = response.getOutputStream();
			dashboardService.generateAdmissionsReportExpandPDF(fileVM,outputStream);
			outputStream.close();
			response.flushBuffer();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return csv;
	}
	
	@RequestMapping(value="/renderSpecialistComparisonReportXLSX/{json}",method = RequestMethod.GET)
	@ResponseBody
	public String renderSpecialistComparisonReportXLSX(@PathVariable String json, HttpServletResponse response) {
		String csv = "";
		byte[] decodedBytes = Base64.getDecoder().decode(json);
		ObjectMapper mapper = new ObjectMapper();
		SpecialistComparisonReportFileVM fileVM = null;
		try {
			fileVM = mapper.readValue(new String(decodedBytes), SpecialistComparisonReportFileVM.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + "Data export- Admissions Report" + ".xlsx\"");
		try {
			OutputStream outputStream = response.getOutputStream();
			dashboardService.generateSpecialistComparisonReportXLSX(fileVM,outputStream);
			outputStream.close();
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return csv;
	}
	
	@RequestMapping(value="/renderSpecialistComparisonReportPDF/{json}",method = RequestMethod.GET)
	@ResponseBody
	public String renderSpecialistComparisonReportPDF(@PathVariable String json, HttpServletResponse response) {
		String csv = "";
		ObjectMapper mapper = new ObjectMapper();
		byte[] decodedBytes = Base64.getDecoder().decode(json);
		SpecialistComparisonReportFileVM fileVM = null;
		
		try {
			fileVM = mapper.readValue(new String(decodedBytes), SpecialistComparisonReportFileVM.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		response.setContentType("application/pdf");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + "Data export- Admissions Report" + ".pdf\"");
		try {
			OutputStream outputStream = response.getOutputStream();
			dashboardService.generateSpecialistComparisonReportPDF(fileVM,outputStream);
			outputStream.close();
			response.flushBuffer();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return csv;
	}
	
	@RequestMapping(value="/renderSpecialistComparisonExpandReportXLSX/{json}",method = RequestMethod.GET)
	@ResponseBody
	public String renderSpecialistComparisonExpandReportXLSX(@PathVariable String json, HttpServletResponse response) {
		String csv = "";
		byte[] decodedBytes = Base64.getDecoder().decode(json);
		ObjectMapper mapper = new ObjectMapper();
		SpecialistComparisonExpandReportFileVM fileVM = null;
		try {
			fileVM = mapper.readValue(new String(decodedBytes), SpecialistComparisonExpandReportFileVM.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + "Data export- Admissions Report" + ".xlsx\"");
		try {
			OutputStream outputStream = response.getOutputStream();
			dashboardService.generateSpecialistComparisonExpandReportXLSX(fileVM,outputStream);
			outputStream.close();
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return csv;
	}
	
	@RequestMapping(value="/renderSpecialistComparisonExpandReportPDF/{json}",method = RequestMethod.GET)
	@ResponseBody
	public String renderSpecialistComparisonExpandReportPDF(@PathVariable String json, HttpServletResponse response) {
		String csv = "";
		ObjectMapper mapper = new ObjectMapper();
		byte[] decodedBytes = Base64.getDecoder().decode(json);
		SpecialistComparisonExpandReportFileVM fileVM = null;
		
		try {
			fileVM = mapper.readValue(new String(decodedBytes), SpecialistComparisonExpandReportFileVM.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		response.setContentType("application/pdf");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + "Data export- Admissions Report" + ".pdf\"");
		try {
			OutputStream outputStream = response.getOutputStream();
			dashboardService.generateSpecialistComparisonExpandReportPDF(fileVM,outputStream);
			outputStream.close();
			response.flushBuffer();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return csv;
	}
	
	@RequestMapping(value="/renderPatientVisitReportXLSX/{json}",method = RequestMethod.GET)
	@ResponseBody
	public String renderPatientVisitReportXLSX(@PathVariable String json, HttpServletResponse response) {
		String csv = "";
		byte[] decodedBytes = Base64.getDecoder().decode(json);
		ObjectMapper mapper = new ObjectMapper();
		PatientVisitReportFileVM fileVM = null;
		try {
			fileVM = mapper.readValue(new String(decodedBytes), PatientVisitReportFileVM.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + "Data export- ER Patient Visit Report" + ".xlsx\"");
		try {
			OutputStream outputStream = response.getOutputStream();
			dashboardService.generatePatientVisitReportXLSX(fileVM,outputStream);
			outputStream.close();
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return csv;
	}
	
	@RequestMapping(value="/renderPatientVisitExpandReportXLSX/{json}",method = RequestMethod.GET)
	@ResponseBody
	public String renderPatientVisitExpandReportXLSX(@PathVariable String json, HttpServletResponse response) {
		String csv = "";
		byte[] decodedBytes = Base64.getDecoder().decode(json);
		ObjectMapper mapper = new ObjectMapper();
		PatientVisitExpandReportFileVM fileVM = null;
		try {
			fileVM = mapper.readValue(new String(decodedBytes), PatientVisitExpandReportFileVM.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + "Data export- ER Patient Visit Report Details" + ".xlsx\"");
		try {
			OutputStream outputStream = response.getOutputStream();
			dashboardService.generatePatientVisitExpandReportXLSX(fileVM,outputStream);
			outputStream.close();
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return csv;
	}
	
	@RequestMapping(value="/renderPatientVisitReportPDF/{json}",method = RequestMethod.GET)
	@ResponseBody
	public String renderPatientVisitReportPDF(@PathVariable String json, HttpServletResponse response) {
		String csv = "";
		ObjectMapper mapper = new ObjectMapper();
		byte[] decodedBytes = Base64.getDecoder().decode(json);
		PatientVisitReportFileVM fileVM = null;
		
		try {
			fileVM = mapper.readValue(new String(decodedBytes), PatientVisitReportFileVM.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		response.setContentType("application/pdf");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + "Data export- Patient Visit Report" + ".pdf\"");
		try {
			OutputStream outputStream = response.getOutputStream();
			dashboardService.generatePatientVisitReportPDF(fileVM,outputStream);
			outputStream.close();
			response.flushBuffer();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return csv;
	}
	
	@RequestMapping(value="/renderPatientVisitExpandReportPDF/{json}",method = RequestMethod.GET)
	@ResponseBody
	public String renderPatientVisitExpandReportPDF(@PathVariable String json, HttpServletResponse response) {
		String csv = "";
		ObjectMapper mapper = new ObjectMapper();
		byte[] decodedBytes = Base64.getDecoder().decode(json);
		PatientVisitExpandReportFileVM fileVM = null;
		
		try {
			fileVM = mapper.readValue(new String(decodedBytes), PatientVisitExpandReportFileVM.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		response.setContentType("application/pdf");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + "Data export- Patient Visit Report Details" + ".pdf\"");
		try {
			OutputStream outputStream = response.getOutputStream();
			dashboardService.generatePatientVisitExpandReportPDF(fileVM,outputStream);
			outputStream.close();
			response.flushBuffer();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return csv;
	}
	
	@RequestMapping(value="/renderSummaryReportXLSX/{json}",method = RequestMethod.GET)
	@ResponseBody
	public String renderSummaryReportXLSX(@PathVariable String json, HttpServletResponse response) {
		String csv = "";
		byte[] decodedBytes = Base64.getDecoder().decode(json);
		ObjectMapper mapper = new ObjectMapper();
		SummaryReportFileVM fileVM = null;
		try {
			fileVM = mapper.readValue(new String(decodedBytes), SummaryReportFileVM.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + "Data export-Summary Report" + ".xlsx\"");
		try {
			OutputStream outputStream = response.getOutputStream();
			dashboardService.generateSummaryReportXLSX(fileVM,outputStream);
			outputStream.close();
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return csv;
	}
	
	@RequestMapping(value="/renderSummaryReportPDF/{json}",method = RequestMethod.GET)
	@ResponseBody
	public String renderSummaryReportPDF(@PathVariable String json, HttpServletResponse response) {
		String csv = "";
		ObjectMapper mapper = new ObjectMapper();
		byte[] decodedBytes = Base64.getDecoder().decode(json);
		SummaryReportFileVM fileVM = null;
		
		try {
			fileVM = mapper.readValue(new String(decodedBytes), SummaryReportFileVM.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		response.setContentType("application/pdf");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + "Data export- Summary Report" + ".pdf\"");
		try {
			OutputStream outputStream = response.getOutputStream();
			dashboardService.generateSummaryReportPDF(fileVM,outputStream);
			outputStream.close();
			response.flushBuffer();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return csv;
	}
	
	@RequestMapping(value="/renderSettledMonthsReportXLSX/{json}",method = RequestMethod.GET)
	@ResponseBody
	public String renderSettledMonthsReportXLSX(@PathVariable String json, HttpServletResponse response) {
		String csv = "";
		byte[] decodedBytes = Base64.getDecoder().decode(json);
		ObjectMapper mapper = new ObjectMapper();
		SettledMonthsReportFileVM fileVM = null;
		try {
			fileVM = mapper.readValue(new String(decodedBytes), SettledMonthsReportFileVM.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + "Data export-Settled Months Report" + ".xlsx\"");
		try {
			OutputStream outputStream = response.getOutputStream();
			dashboardService.generateSettledMonthsReportXLSX(fileVM,outputStream);
			outputStream.close();
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return csv;
	}
	
	@RequestMapping(value="/renderSettledMonthsReportPDF/{json}",method = RequestMethod.GET)
	@ResponseBody
	public String renderSettledMonthsReportPDF(@PathVariable String json, HttpServletResponse response) {
		String csv = "";
		ObjectMapper mapper = new ObjectMapper();
		byte[] decodedBytes = Base64.getDecoder().decode(json);
		SettledMonthsReportFileVM fileVM = null;
		
		try {
			fileVM = mapper.readValue(new String(decodedBytes), SettledMonthsReportFileVM.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		response.setContentType("application/pdf");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + "Data export- Settled Months Report" + ".pdf\"");
		try {
			OutputStream outputStream = response.getOutputStream();
			dashboardService.generateSettledMonthsReportPDF(fileVM,outputStream);
			outputStream.close();
			response.flushBuffer();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return csv;
	}
	
	@RequestMapping(value="/renderSettledMonthsExpandReportXLSX/{json}",method = RequestMethod.GET)
	@ResponseBody
	public String renderSettledMonthsExpandReportXLSX(@PathVariable String json, HttpServletResponse response) {
		String csv = "";
		byte[] decodedBytes = Base64.getDecoder().decode(json);
		ObjectMapper mapper = new ObjectMapper();
		SettledMonthsExpandReportFileVM fileVM = null;
		try {
			fileVM = mapper.readValue(new String(decodedBytes), SettledMonthsExpandReportFileVM.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + "Data export-Settled Months Expand Report" + ".xlsx\"");
		try {
			OutputStream outputStream = response.getOutputStream();
			dashboardService.generateSettledMonthsExpandReportXLSX(fileVM,outputStream);
			outputStream.close();
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return csv;
	}
	
	@RequestMapping(value="/renderSettledMonthsExpandReportPDF/{json}",method = RequestMethod.GET)
	@ResponseBody
	public String renderSettledMonthsExpandReportPDF(@PathVariable String json, HttpServletResponse response) {
		String csv = "";
		ObjectMapper mapper = new ObjectMapper();
		byte[] decodedBytes = Base64.getDecoder().decode(json);
		SettledMonthsExpandReportFileVM fileVM = null;
		
		try {
			fileVM = mapper.readValue(new String(decodedBytes), SettledMonthsExpandReportFileVM.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		response.setContentType("application/pdf");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + "Data export- Settled Months Expand Report" + ".pdf\"");
		try {
			OutputStream outputStream = response.getOutputStream();
			dashboardService.generateSettledMonthsExpandReportPDF(fileVM,outputStream);
			outputStream.close();
			response.flushBuffer();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return csv;
	}
	
	@RequestMapping(value="/renderPmpmByPracticeReportXLSX/{json}",method = RequestMethod.GET)
	@ResponseBody
	public String renderPmpmByPracticeReportXLSX(@PathVariable String json, HttpServletResponse response) {
		String csv = "";
		byte[] decodedBytes = Base64.getDecoder().decode(json);
		ObjectMapper mapper = new ObjectMapper();
		PmpmByPracticeReportFileVM fileVM = null;
		try {
			fileVM = mapper.readValue(new String(decodedBytes), PmpmByPracticeReportFileVM.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + "Data export-PMPM By Practice Report" + ".xlsx\"");
		try {
			OutputStream outputStream = response.getOutputStream();
			dashboardService.generatePmpmByPracticeReportXLSX(fileVM,outputStream);
			outputStream.close();
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return csv;
	}
	
	@RequestMapping(value="/renderPmpmByPracticeReportPDF/{json}",method = RequestMethod.GET)
	@ResponseBody
	public String renderPmpmByPracticeReportPDF(@PathVariable String json, HttpServletResponse response) {
		String csv = "";
		ObjectMapper mapper = new ObjectMapper();
		byte[] decodedBytes = Base64.getDecoder().decode(json);
		PmpmByPracticeReportFileVM fileVM = null;
		
		try {
			fileVM = mapper.readValue(new String(decodedBytes), PmpmByPracticeReportFileVM.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		response.setContentType("application/pdf");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + "Data export- PMPM By Practice Report" + ".pdf\"");
		try {
			OutputStream outputStream = response.getOutputStream();
			dashboardService.generatePmpmByPracticeReportPDF(fileVM,outputStream);
			outputStream.close();
			response.flushBuffer();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return csv;
	}
	
	@RequestMapping(value="/renderMembershipManagementXLSX/{json}",method = RequestMethod.GET)
	@ResponseBody
	public String renderMembershipManagementXLSX(@PathVariable String json, HttpServletResponse response) {
		String csv = "";
		byte[] decodedBytes = Base64.getDecoder().decode(json);
		ObjectMapper mapper = new ObjectMapper();
		MembershipManagementFileVM fileVM = null;
		try {
			fileVM = mapper.readValue(new String(decodedBytes), MembershipManagementFileVM.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + "Data export-"+fileVM.getPatientType()+" Patient List" + ".xlsx\"");
		try {
			OutputStream outputStream = response.getOutputStream();
			dashboardService.generateMembershipManagementXLSX(fileVM,outputStream);
			outputStream.close();
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return csv;
	}
	
	@RequestMapping(value="/renderMembershipManagementPDF/{json}",method = RequestMethod.GET)
	@ResponseBody
	public String renderMembershipManagementPDF(@PathVariable String json, HttpServletResponse response) {
		String csv = "";
		ObjectMapper mapper = new ObjectMapper();
		byte[] decodedBytes = Base64.getDecoder().decode(json);
		MembershipManagementFileVM fileVM = null;
		
		try {
			fileVM = mapper.readValue(new String(decodedBytes), MembershipManagementFileVM.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		response.setContentType("application/pdf");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + "Data export- "+fileVM.getPatientType()+" Patient List" + ".pdf\"");
		try {
			OutputStream outputStream = response.getOutputStream();
			dashboardService.generateMembershipManagementPDF(fileVM,outputStream);
			outputStream.close();
			response.flushBuffer();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return csv;
	}
	
	@RequestMapping(value="/renderPmpmByPracticeExpandReportXLSX/{json}",method = RequestMethod.GET)
	@ResponseBody
	public String renderPmpmByPracticeExpandReportXLSX(@PathVariable String json, HttpServletResponse response) {
		String csv = "";
		byte[] decodedBytes = Base64.getDecoder().decode(json);
		ObjectMapper mapper = new ObjectMapper();
		PmpmByPracticeExpandReportFileVM fileVM = null;
		try {
			fileVM = mapper.readValue(new String(decodedBytes), PmpmByPracticeExpandReportFileVM.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + "Data export-PMPM By Practice Details " + ".xlsx\"");
		try {
			OutputStream outputStream = response.getOutputStream();
			dashboardService.generatePmpmByPracticeExpandReportXLSX(fileVM,outputStream);
			outputStream.close();
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return csv;
	}
	
	@RequestMapping(value="/renderPmpmByPracticeExpandReportPDF/{json}",method = RequestMethod.GET)
	@ResponseBody
	public String renderPmpmByPracticeExpandReportPDF(@PathVariable String json, HttpServletResponse response) {
		String csv = "";
		ObjectMapper mapper = new ObjectMapper();
		byte[] decodedBytes = Base64.getDecoder().decode(json);
		PmpmByPracticeExpandReportFileVM fileVM = null;
		
		try {
			fileVM = mapper.readValue(new String(decodedBytes), PmpmByPracticeExpandReportFileVM.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		response.setContentType("application/pdf");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + "Data export- PMPM By Practice Report Details" + ".pdf\"");
		try {
			OutputStream outputStream = response.getOutputStream();
			dashboardService.generatePmpmByPracticeExpandReportPDF(fileVM,outputStream);
			outputStream.close();
			response.flushBuffer();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return csv;
	}
	
	@RequestMapping(value="/renderBeneficiariesManagementXLSX/{json}",method = RequestMethod.GET)
	@ResponseBody
	public String renderBeneficiariesManagementXLSX(@PathVariable String json, HttpServletResponse response) {
		String csv = "";
		byte[] decodedBytes = Base64.getDecoder().decode(json);
		ObjectMapper mapper = new ObjectMapper();
		BeneficiariesManagementReportFileVM fileVM = null;
		try {
			fileVM = mapper.readValue(new String(decodedBytes), BeneficiariesManagementReportFileVM.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + "Data export-Beneficiaries Management Report " + ".xlsx\"");
		try {
			OutputStream outputStream = response.getOutputStream();
			dashboardService.generateBeneficiariesManagementReportXLSX(fileVM,outputStream);
			outputStream.close();
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return csv;
	}
	
	@RequestMapping(value="/renderBeneficiariesManagementByLocationXLSX/{json}",method = RequestMethod.GET)
	@ResponseBody
	public String renderBeneficiariesManagementByLocationXLSX(@PathVariable String json, HttpServletResponse response) {
		String csv = "";
		byte[] decodedBytes = Base64.getDecoder().decode(json);
		ObjectMapper mapper = new ObjectMapper();
		BeneficiariesManagementByLocationReportFileVM fileVM = null;
		try {
			fileVM = mapper.readValue(new String(decodedBytes), BeneficiariesManagementByLocationReportFileVM.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + "Data export-Beneficiaries Management Report By Location " + ".xlsx\"");
		try {
			OutputStream outputStream = response.getOutputStream();
			dashboardService.generateBeneficiariesManagementByLocationReportXLSX(fileVM,outputStream);
			outputStream.close();
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return csv;
	}
	
	@RequestMapping(value="/renderBeneficiariesManagementPDF/{json}",method = RequestMethod.GET)
	@ResponseBody
	public String renderBeneficiariesManagementReportPDF(@PathVariable String json, HttpServletResponse response) {
		String csv = "";
		ObjectMapper mapper = new ObjectMapper();
		byte[] decodedBytes = Base64.getDecoder().decode(json);
		BeneficiariesManagementReportFileVM fileVM = null;
		
		try {
			fileVM = mapper.readValue(new String(decodedBytes), BeneficiariesManagementReportFileVM.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		response.setContentType("application/pdf");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + "Data export- Beneficiaries Management Report" + ".pdf\"");
		try {
			OutputStream outputStream = response.getOutputStream();
			dashboardService.generateBeneficiariesManagementReportPDF(fileVM,outputStream);
			outputStream.close();
			response.flushBuffer();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return csv;
	}
	
	@RequestMapping(value="/renderBeneficiariesManagementByLocationPDF/{json}",method = RequestMethod.GET)
	@ResponseBody
	public String renderBeneficiariesManagementByLocationPDF(@PathVariable String json, HttpServletResponse response) {
		String csv = "";
		ObjectMapper mapper = new ObjectMapper();
		byte[] decodedBytes = Base64.getDecoder().decode(json);
		BeneficiariesManagementByLocationReportFileVM fileVM = null;
		
		try {
			fileVM = mapper.readValue(new String(decodedBytes), BeneficiariesManagementByLocationReportFileVM.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		response.setContentType("application/pdf");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + "Data export- Beneficiaries Management Report By Location" + ".pdf\"");
		try {
			OutputStream outputStream = response.getOutputStream();
			dashboardService.generateBeneficiariesManagementByLocationReportPDF(fileVM,outputStream);
			outputStream.close();
			response.flushBuffer();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return csv;
	}
	
	@RequestMapping(value="/renderBeneficiariesManagementByClinicXLSX/{json}",method = RequestMethod.GET)
	@ResponseBody
	public String renderBeneficiariesManagementByClinicXLSX(@PathVariable String json, HttpServletResponse response) {
		String csv = "";
		byte[] decodedBytes = Base64.getDecoder().decode(json);
		ObjectMapper mapper = new ObjectMapper();
		BeneficiariesManagementByClinicReportFileVM fileVM = null;
		try {
			fileVM = mapper.readValue(new String(decodedBytes), BeneficiariesManagementByClinicReportFileVM.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + "Data export-Beneficiaries Management Report By Clinic " + ".xlsx\"");
		try {
			OutputStream outputStream = response.getOutputStream();
			dashboardService.generateBeneficiariesManagementByClinicReportXLSX(fileVM,outputStream);
			outputStream.close();
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return csv;
	}
	
	@RequestMapping(value="/renderBeneficiariesManagementByClinicPDF/{json}",method = RequestMethod.GET)
	@ResponseBody
	public String renderBeneficiariesManagementByClinicPDF(@PathVariable String json, HttpServletResponse response) {
		String csv = "";
		ObjectMapper mapper = new ObjectMapper();
		byte[] decodedBytes = Base64.getDecoder().decode(json);
		BeneficiariesManagementByClinicReportFileVM fileVM = null;
		
		try {
			fileVM = mapper.readValue(new String(decodedBytes), BeneficiariesManagementByClinicReportFileVM.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		response.setContentType("application/pdf");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + "Data export- Beneficiaries Management Report By Clinic" + ".pdf\"");
		try {
			OutputStream outputStream = response.getOutputStream();
			dashboardService.generateBeneficiariesManagementByClinicReportPDF(fileVM,outputStream);
			outputStream.close();
			response.flushBuffer();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return csv;
	}
	
	@RequestMapping(value="/renderBeneficiariesManagementExpandXLSX/{json}",method = RequestMethod.GET)
	@ResponseBody
	public String renderBeneficiariesManagementExpandXLSX(@PathVariable String json, HttpServletResponse response) {
		String csv = "";
		byte[] decodedBytes = Base64.getDecoder().decode(json);
		ObjectMapper mapper = new ObjectMapper();
		BeneficiariesManagementReportExpandFileVM fileVM = null;
		try {
			fileVM = mapper.readValue(new String(decodedBytes), BeneficiariesManagementReportExpandFileVM.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + "Beneficiaries Management Details " + ".xlsx\"");
		try {
			OutputStream outputStream = response.getOutputStream();
			dashboardService.generateBeneficiariesManagementExpandReportXLSX(fileVM,outputStream);
			outputStream.close();
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return csv;
	}
	
	@RequestMapping(value="/renderBeneficiariesManagementExpandPDF/{json}",method = RequestMethod.GET)
	@ResponseBody
	public String renderBeneficiariesManagementExpandPDF(@PathVariable String json, HttpServletResponse response) {
		String csv = "";
		ObjectMapper mapper = new ObjectMapper();
		byte[] decodedBytes = Base64.getDecoder().decode(json);
		BeneficiariesManagementReportExpandFileVM fileVM = null;
		
		try {
			fileVM = mapper.readValue(new String(decodedBytes), BeneficiariesManagementReportExpandFileVM.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		response.setContentType("application/pdf");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + "Data export- Beneficiaries Management Details" + ".pdf\"");
		try {
			OutputStream outputStream = response.getOutputStream();
			dashboardService.generateBeneficiariesManagementExpandReportPDF(fileVM,outputStream);
			outputStream.close();
			response.flushBuffer();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return csv;
	}
	
	@RequestMapping(value="/renderBeneficiariesManagementByDoctorXLSX/{json}",method = RequestMethod.GET)
	@ResponseBody
	public String renderBeneficiariesManagementByDoctorXLSX(@PathVariable String json, HttpServletResponse response) {
		String csv = "";
		byte[] decodedBytes = Base64.getDecoder().decode(json);
		ObjectMapper mapper = new ObjectMapper();
		BeneficiariesManagementByDoctorFileVM fileVM = null;
		try {
			fileVM = mapper.readValue(new String(decodedBytes), BeneficiariesManagementByDoctorFileVM.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + "Beneficiaries Management By Doctor " + ".xlsx\"");
		try {
			OutputStream outputStream = response.getOutputStream();
			dashboardService.generateBeneficiariesManagementByDoctorXLSX(fileVM,outputStream);
			outputStream.close();
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return csv;
	}
	
	@RequestMapping(value="/renderBeneficiariesManagementByDoctorPDF/{json}",method = RequestMethod.GET)
	@ResponseBody
	public String renderBeneficiariesManagementByDoctorPDF(@PathVariable String json, HttpServletResponse response) {
		String csv = "";
		ObjectMapper mapper = new ObjectMapper();
		byte[] decodedBytes = Base64.getDecoder().decode(json);
		BeneficiariesManagementByDoctorFileVM fileVM = null;
		
		try {
			fileVM = mapper.readValue(new String(decodedBytes), BeneficiariesManagementByDoctorFileVM.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		response.setContentType("application/pdf");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + "Data export- Beneficiaries Management By Doctor" + ".pdf\"");
		try {
			OutputStream outputStream = response.getOutputStream();
			dashboardService.generateBeneficiariesManagementByDoctorPDF(fileVM,outputStream);
			outputStream.close();
			response.flushBuffer();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return csv;
	}
	
	@RequestMapping(value="/renderBeneficiariesManagementByDoctorExpandXLSX/{json}",method = RequestMethod.GET)
	@ResponseBody
	public String renderBeneficiariesManagementByDoctorExpandXLSX(@PathVariable String json, HttpServletResponse response) {
		String csv = "";
		byte[] decodedBytes = Base64.getDecoder().decode(json);
		ObjectMapper mapper = new ObjectMapper();
		BeneficiariesManagementByDoctorReportExpandFileVM fileVM = null;
		try {
			fileVM = mapper.readValue(new String(decodedBytes), BeneficiariesManagementByDoctorReportExpandFileVM.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + "Beneficiaries Management By Doctor Details " + ".xlsx\"");
		try {
			OutputStream outputStream = response.getOutputStream();
			dashboardService.generateBeneficiariesManagementByDoctorExpandReportXLSX(fileVM,outputStream);
			outputStream.close();
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return csv;
	}
	
	@RequestMapping(value="/renderBeneficiariesManagementByDoctorExpandPDF/{json}",method = RequestMethod.GET)
	@ResponseBody
	public String renderBeneficiariesManagementByDoctorExpandPDF(@PathVariable String json, HttpServletResponse response) {
		String csv = "";
		ObjectMapper mapper = new ObjectMapper();
		byte[] decodedBytes = Base64.getDecoder().decode(json);
		BeneficiariesManagementByDoctorReportExpandFileVM fileVM = null;
		
		try {
			fileVM = mapper.readValue(new String(decodedBytes), BeneficiariesManagementByDoctorReportExpandFileVM.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		response.setContentType("application/pdf");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + "Data export- Beneficiaries Management By Doctor Details" + ".pdf\"");
		try {
			OutputStream outputStream = response.getOutputStream();
			dashboardService.generateBeneficiariesManagementByDoctorExpandReportPDF(fileVM,outputStream);
			outputStream.close();
			response.flushBuffer();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return csv;
	}
	
	@RequestMapping(value="/renderDuplicateClaimsExpandXLSX/{json}",method = RequestMethod.GET)
	@ResponseBody
	public String renderDuplicateClaimsExpandXLSX(@PathVariable String json, HttpServletResponse response) {
		String csv = "";
		byte[] decodedBytes = Base64.getDecoder().decode(json);
		ObjectMapper mapper = new ObjectMapper();
		DuplicateClaimsExpandFileVM fileVM = null;
		try {
			fileVM = mapper.readValue(new String(decodedBytes), DuplicateClaimsExpandFileVM.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + "Duplicate Claims Details " + ".xlsx\"");
		try {
			OutputStream outputStream = response.getOutputStream();
			dashboardService.generateDuplicateClaimsExpandReportXLSX(fileVM,outputStream);
			outputStream.close();
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return csv;
	}
	
	@RequestMapping(value="/renderDuplicateClaimsExpandPDF/{json}",method = RequestMethod.GET)
	@ResponseBody
	public String renderDuplicateClaimsExpandPDF(@PathVariable String json, HttpServletResponse response) {
		String csv = "";
		ObjectMapper mapper = new ObjectMapper();
		byte[] decodedBytes = Base64.getDecoder().decode(json);
		DuplicateClaimsExpandFileVM fileVM = null;
		
		try {
			fileVM = mapper.readValue(new String(decodedBytes), DuplicateClaimsExpandFileVM.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		response.setContentType("application/pdf");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + "Data export- Duplicate Claims Details" + ".pdf\"");
		try {
			OutputStream outputStream = response.getOutputStream();
			dashboardService.generateDuplicateClaimsExpandPDF(fileVM,outputStream);
			outputStream.close();
			response.flushBuffer();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return csv;
	}
	
	@RequestMapping(value="/readSummaryFile",method = RequestMethod.GET)
	public void readSummaryFile() {
		final String SAMPLE_CSV_FILE_PATH = "/home/lubuntu02/Downloads/DEC_2018/FRH/report_data.csv";
		Reader reader = null;
		try {
			reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
		} catch (IOException e) {e.printStackTrace();}
		
		/*CsvToBean<MonthlyTotalsReport> csvToBean = new CsvToBeanBuilder(reader)
                .withType(MonthlyTotalsReport.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
		
		List<MonthlyTotalsReport> list = csvToBean.parse();
		
		for(MonthlyTotalsReport obj: list) {
			monthlyTotalsReportDao.save(obj);
		}*/
		
		/*CsvToBean<HccCodeValues> csvToBean = new CsvToBeanBuilder(reader)
                .withType(HccCodeValues.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
		
		List<HccCodeValues> list = csvToBean.parse();
		
		for(HccCodeValues obj: list) {
			hccCodeValuesDao.save(obj);
		}*/
		
		/*CsvToBean<MonthlyTotalsData> csvToBean = new CsvToBeanBuilder(reader)
                .withType(MonthlyTotalsData.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
		
		List<MonthlyTotalsData> list = csvToBean.parse();
		
		for(MonthlyTotalsData obj: list) {
			monthlyTotalsDataDao.save(obj);
		}*/
	}
	
}
