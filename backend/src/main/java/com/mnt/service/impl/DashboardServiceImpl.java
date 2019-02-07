package com.mnt.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mnt.dao.ClaimDetailsStoredResultsDao;
import com.mnt.dao.DemographicDetailDao;
import com.mnt.dao.InstClaimDetailDao;
import com.mnt.dao.MonthlyTotalsDataDao;
import com.mnt.dao.MonthlyTotalsReportDao;
import com.mnt.dao.PremiumDetailDao;
import com.mnt.dao.PrescDrugsDataDao;
import com.mnt.dao.ProfClaimDetailDao;
import com.mnt.dao.RxDetailDao;
import com.mnt.dao.SpecialtyClaimDetailDao;
import com.mnt.domain.ClaimDetailsStoredResults;
import com.mnt.domain.PrescDrugsData;
import com.mnt.service.DashboardService;
import com.mnt.vm.AdmissionsReportExpandFileVM;
import com.mnt.vm.AdmissionsReportExpandPrintDataVM;
import com.mnt.vm.AdmissionsReportExpandVM;
import com.mnt.vm.AdmissionsReportFileVM;
import com.mnt.vm.AdmissionsReportPrintDataVM;
import com.mnt.vm.AdmissionsReportVM;
import com.mnt.vm.ClaimDetailsVM;
import com.mnt.vm.DashboardReportsVM;
import com.mnt.vm.DrugsVM;
import com.mnt.vm.DuplicateClaimFileVM;
import com.mnt.vm.DuplicateClaimPrintDataVM;
import com.mnt.vm.DuplicateClaimsReportVM;
import com.mnt.vm.FileVM;
import com.mnt.vm.MembershipManagementFileVM;
import com.mnt.vm.MembershipManagementPatientTypeVM;
import com.mnt.vm.MembershipManagementPrintDataVM;
import com.mnt.vm.MembershipManagementVM;
import com.mnt.vm.OptionsVM;
import com.mnt.vm.PatientVisitReportFileVM;
import com.mnt.vm.PatientVisitReportPrintDataVM;
import com.mnt.vm.PatientVisitReportVM;
import com.mnt.vm.PrintDataVM;
import com.mnt.vm.ReportDataVM;
import com.mnt.vm.ReportGridVM;
import com.mnt.vm.ReportResponseVM;
import com.mnt.vm.ReportVM;
import com.mnt.vm.SpecialistComparisonPrintDataVM;
import com.mnt.vm.SpecialistComparisonReportFileVM;
import com.mnt.vm.SpecialistComparisonReportVM;
import com.mnt.vm.SummaryReportFileVM;
import com.mnt.vm.SummaryReportPrintDataVM;
import com.mnt.vm.SummaryReportVM;
import com.mnt.vm.reports.BeneficiariesManagementByClinicReportExpandFileVM;
import com.mnt.vm.reports.BeneficiariesManagementByClinicReportFileVM;
import com.mnt.vm.reports.BeneficiariesManagementByClinicReportPrintDataVM;
import com.mnt.vm.reports.BeneficiariesManagementByClinicReportVM;
import com.mnt.vm.reports.BeneficiariesManagementByDoctorFileVM;
import com.mnt.vm.reports.BeneficiariesManagementByDoctorPrintDataVM;
import com.mnt.vm.reports.BeneficiariesManagementByDoctorReportExpandFileVM;
import com.mnt.vm.reports.BeneficiariesManagementByDoctorReportVM;
import com.mnt.vm.reports.BeneficiariesManagementByLocationReportExpandFileVM;
import com.mnt.vm.reports.BeneficiariesManagementByLocationReportFileVM;
import com.mnt.vm.reports.BeneficiariesManagementByLocationReportPrintDataVM;
import com.mnt.vm.reports.BeneficiariesManagementByLocationReportVM;
import com.mnt.vm.reports.BeneficiariesManagementExpandReportPrintDataVM;
import com.mnt.vm.reports.BeneficiariesManagementExpandReportVM;
import com.mnt.vm.reports.BeneficiariesManagementReportExpandFileVM;
import com.mnt.vm.reports.BeneficiariesManagementReportFileVM;
import com.mnt.vm.reports.BeneficiariesManagementReportPrintDataVM;
import com.mnt.vm.reports.BeneficiariesManagementReportVM;
import com.mnt.vm.reports.DuplicateClaimsExpandFileVM;
import com.mnt.vm.reports.DuplicateClaimsExpandPrintDataVM;
import com.mnt.vm.reports.DuplicateClaimsExpandVM;
import com.mnt.vm.reports.PatientVisitExpandReportFileVM;
import com.mnt.vm.reports.PatientVisitExpandReportPrintDataVM;
import com.mnt.vm.reports.PatientVisitExpandReportVM;
import com.mnt.vm.reports.PmpmByPracticeExpandReportFileVM;
import com.mnt.vm.reports.PmpmByPracticeExpandReportPrintDataVM;
import com.mnt.vm.reports.PmpmByPracticeExpandReportVM;
import com.mnt.vm.reports.PmpmByPracticeReportFileVM;
import com.mnt.vm.reports.PmpmByPracticeReportPrintDataVM;
import com.mnt.vm.reports.PmpmByPracticeReportVM;
import com.mnt.vm.reports.ReinsuranceCostReportFileVM;
import com.mnt.vm.reports.ReinsuranceCostReportPrintDataVM;
import com.mnt.vm.reports.ReinsuranceCostReportVM;
import com.mnt.vm.reports.ReinsuranceManagementReportFileVM;
import com.mnt.vm.reports.ReinsuranceManagementReportPrintDataVM;
import com.mnt.vm.reports.ReinsuranceManagementReportVM;
import com.mnt.vm.reports.SettledMonthsExpandReportFileVM;
import com.mnt.vm.reports.SettledMonthsExpandReportPrintDataVM;
import com.mnt.vm.reports.SettledMonthsExpandReportVM;
import com.mnt.vm.reports.SettledMonthsReportFileVM;
import com.mnt.vm.reports.SettledMonthsReportPrintDataVM;
import com.mnt.vm.reports.SettledMonthsReportVM;
import com.mnt.vm.reports.SpecialistComparisonExpandPracticeReportVM;
import com.mnt.vm.reports.SpecialistComparisonExpandPrintDataVM;
import com.mnt.vm.reports.SpecialistComparisonExpandReportFileVM;
import com.mnt.vm.reports.SpecialistComparisonExpandReportVM;


@Repository
public class DashboardServiceImpl implements DashboardService {

	@Autowired
	RxDetailDao rxDetailDao;
	@Autowired
	ProfClaimDetailDao profClaimDetailDao;
	@Autowired
	SpecialtyClaimDetailDao specialtyClaimDetailDao;
	@Autowired
	InstClaimDetailDao instClaimDetailDao;
	@Autowired
	PremiumDetailDao premiumDetailDao;
	@Autowired
	ClaimDetailsStoredResultsDao claimDetailsStoredResultsDao;
	@Autowired
	PrescDrugsDataDao PrescDrugsDao;
	@Autowired
	MonthlyTotalsReportDao monthlyTotalsReportDao;
	@Autowired
	MonthlyTotalsDataDao monthlyTotalsDataDao;
	@Autowired
	DemographicDetailDao demographicDetailDao;
	
	@SuppressWarnings("deprecation")
	public DrugsVM getTop20PrescriptionDrugs(String type, String provider) {
		DrugsVM vm = new DrugsVM();
		List<String> drugNames = new ArrayList<>();
		List<Double> costList = new ArrayList<>();
		PrescDrugsData prescDrugsData = PrescDrugsDao.getTop20PrescriptionDrugs(type,provider);
		String names[] = null;
		if(prescDrugsData != null) {
			names = prescDrugsData.getDrugNames().split(",");
		}
		
		String costs[] = null;
		if(prescDrugsData != null) {
			costs = prescDrugsData.getCosts().split(",");
		}
		
		if(names != null) {
			for(String str: names) {
				drugNames.add(WordUtils.capitalizeFully(str).replaceAll(" ", ""));
			}
		}
		if(costs != null) {
			for(String cost: costs) {
				costList.add(Double.parseDouble(cost));
			}
		}
		vm.setDrugNames(drugNames);
		vm.setExpenditures(costList);
		return vm;
	}
	
	public DrugsVM getPlanAndPCPList() {
		DrugsVM vm = new DrugsVM();
		List<String> planList = rxDetailDao.getAllPlans();
		List<Object[]> pcpData = rxDetailDao.getAllPCP("all");
		List<String> yearsList = profClaimDetailDao.getAllYears();
		int currentYear = 0,currentMonth = 0;
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		currentYear = cal.get(Calendar.YEAR);
		currentMonth = cal.get(Calendar.MONTH);
		
		if(currentMonth == 0)
			currentYear--;
		
		String yearVal = currentYear+"";
		List<String> pcpLocationList = rxDetailDao.getAllPCPLocationCode(yearVal);
		
		List<OptionsVM> planVMList = new ArrayList<>();
		List<OptionsVM> pcpVMList = new ArrayList<>();
		List<OptionsVM> yearVMList = new ArrayList<>();
		List<OptionsVM> locationVMList = new ArrayList<>();
		
		for(String plan : planList) {
			OptionsVM planVM = new OptionsVM();
			planVM.setValue(plan);
			planVM.setLabel(plan);
			planVMList.add(planVM);
		}
		
		OptionsVM allVM = new OptionsVM();
		allVM.setValue("all");
		allVM.setLabel("All");
		pcpVMList.add(allVM);
		for(Object[] obj : pcpData) {
			OptionsVM pcpVM = new OptionsVM();
			pcpVM.setValue(obj[0].toString());
			pcpVM.setLabel(obj[0].toString());
			pcpVMList.add(pcpVM);
		}
		
		for(String year : yearsList) {
			OptionsVM yearVM = new OptionsVM();
			yearVM.setValue(year);
			yearVM.setLabel(year);
			yearVMList.add(yearVM);
		}
		
		OptionsVM locationallVM = new OptionsVM();
		locationallVM.setValue("all");
		locationallVM.setLabel("All");
		locationVMList.add(allVM);
		for(String location : pcpLocationList) {
			OptionsVM locationVM = new OptionsVM();
			locationVM.setValue(location);
			locationVM.setLabel(location);
			locationVMList.add(locationVM);
		}
		
		vm.setPlanList(planVMList);
		vm.setPcpList(pcpVMList);
		vm.setYearsList(yearVMList);
		vm.setLocationList(locationVMList);
		return vm;
	}
	
	public DrugsVM getMonthlyTotalsReportYears() {
		DrugsVM vm = new DrugsVM();
		List<OptionsVM> yearVMList = new ArrayList<>();
		List<String> yearsList = monthlyTotalsReportDao.getAllYears();
		
		for(String year : yearsList) {
			OptionsVM yearVM = new OptionsVM();
			yearVM.setValue(year);
			yearVM.setLabel(year);
			yearVMList.add(yearVM);
		}
		
		vm.setYearsList(yearVMList);
		return vm;
	}
	
	public DrugsVM getSpecialityList() {
		DrugsVM vm = new DrugsVM();
		List<String> specList = profClaimDetailDao.getAllSpeciality();
		List<OptionsVM> specialityVMList = new ArrayList<>();
		
		for(String spec : specList) {
			OptionsVM specVM = new OptionsVM();
			specVM.setValue(spec);
			specVM.setLabel(spec);
			specialityVMList.add(specVM);
		}
		
		vm.setSpecialityList(specialityVMList);
		return vm;
	}
	
	public List<OptionsVM> getPCPForAllProviders(String provider) {
		List<Object[]> pcpData = rxDetailDao.getAllPCP(provider);
		List<OptionsVM> pcpVMList = new ArrayList<>();
		
		if(provider.equals("all")) {
			for(Object[] obj : pcpData) {
				OptionsVM pcpVM = new OptionsVM();
				pcpVM.setValue(obj[0].toString());
				pcpVM.setLabel(obj[0].toString());
				pcpVMList.add(pcpVM);
			}
		} else {
			for(Object[] obj : pcpData) {
				OptionsVM pcpVM = new OptionsVM();
				pcpVM.setValue(obj[1].toString());
				pcpVM.setLabel(obj[0].toString());
				pcpVMList.add(pcpVM);
			}
		}
		return pcpVMList;
	}
	
	public List<OptionsVM> getLocationsByYear(String year) {
		List<String> locationData = rxDetailDao.getAllPCPLocationByYear(year);
		List<OptionsVM> pcpVMList = new ArrayList<>();
		
			for(String location : locationData) {
				OptionsVM pcpVM = new OptionsVM();
				pcpVM.setValue(location);
				pcpVM.setLabel(location);
				pcpVMList.add(pcpVM);
			}
		
		return pcpVMList;
	}
	
	public ClaimDetailsVM getAllClaimDetails(String year, List<String> providerArr, String pcpName, List<OptionsVM> locationList) {
		
		ClaimDetailsVM vm = new ClaimDetailsVM();
		
		List<Object[]> profInstPrescData = monthlyTotalsDataDao.getSumOfClaim(providerArr.get(0), pcpName, locationList, year);
			if(profInstPrescData.get(0)[0] != null) {
				vm.setProfessional(Double.parseDouble(profInstPrescData.get(0)[0].toString()));
			} else {
				vm.setProfessional(0.0);
			}
			if(profInstPrescData.get(0)[1] != null) {
				vm.setInstitutional(Double.parseDouble(profInstPrescData.get(0)[1].toString()));
			} else {
				vm.setInstitutional(0.0);
			}
			if(profInstPrescData.get(0)[2] != null) {
				vm.setPrescription(Double.parseDouble(profInstPrescData.get(0)[2].toString()));
			} else {
				vm.setPrescription(0.0);
			}
			
			Double specialistClaimData = monthlyTotalsDataDao.getSumOfSpecialistClaim(providerArr.get(0), pcpName, locationList, year);
			
			if(specialistClaimData != null) {
				vm.setSpeciality(specialistClaimData);
			} else {
				vm.setSpeciality(0.0);
			}
			
			Double currentMonthCount = monthlyTotalsDataDao.getSumOfCurrentMonth(providerArr.get(0), pcpName, locationList, year);
			vm.setCurrentMonth((int)Double.parseDouble(currentMonthCount.toString()));
		/*vm.setProfessional(profClaimDetailDao.getSum(year, providerArr));
		vm.setInstitutional(instClaimDetailDao.getSum(year, providerArr));
		vm.setSpeciality(specialtyClaimDetailDao.getSum(year, providerArr));*/
		return vm;
	}
	
	public ClaimDetailsVM getMonthlyTotalsReport(ClaimDetailsVM vm) {
		List<Object[]> totals = monthlyTotalsReportDao.getMonthlyTotalsReport(vm);
		Double ipaPremiumValue[] = {0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
		Double totalExpenses[] = {0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
		
		Double ipaPremiumValueQ[] = {0.0,0.0,0.0,0.0};
		Double totalExpensesQ[] = {0.0,0.0,0.0,0.0};
		
		Double ipaPremiumValueA[] = {0.0};
		Double totalExpensesA[] = {0.0};
		
		if(!totals.isEmpty()) {
			Integer monthNumber = 0;
			for(Object[] obj: totals) {
				String monthsArr[] = obj[2].toString().split("/");
				monthNumber = Integer.parseInt(monthsArr[1]);
				ipaPremiumValue[monthNumber-1] = Double.parseDouble(obj[0].toString());
				totalExpenses[monthNumber-1] = Double.parseDouble(obj[1].toString());
			}
			
		}
		
		ClaimDetailsVM vmObj = new ClaimDetailsVM();
		
		if(vm.getTotalsType().equals("monthly")) {
			vmObj.setIpaPremiumValue(ipaPremiumValue);
			vmObj.setTotalExpenses(totalExpenses);
			
		}
		
		if(vm.getTotalsType().equals("quarterly")) {
			
			ipaPremiumValueQ[0] = ipaPremiumValue[0]+ipaPremiumValue[1]+ipaPremiumValue[2];
			ipaPremiumValueQ[1] = ipaPremiumValue[3]+ipaPremiumValue[4]+ipaPremiumValue[5];
			ipaPremiumValueQ[2] = ipaPremiumValue[6]+ipaPremiumValue[7]+ipaPremiumValue[8];
			ipaPremiumValueQ[3] = ipaPremiumValue[9]+ipaPremiumValue[10]+ipaPremiumValue[11];
			
			totalExpensesQ[0] = totalExpenses[0]+totalExpenses[1]+totalExpenses[2];
			totalExpensesQ[1] = totalExpenses[3]+totalExpenses[4]+totalExpenses[5];
			totalExpensesQ[2] = totalExpenses[6]+totalExpenses[7]+totalExpenses[8];
			totalExpensesQ[3] = totalExpenses[9]+totalExpenses[10]+totalExpenses[11];
			
			vmObj.setIpaPremiumValue(ipaPremiumValueQ);
			vmObj.setTotalExpenses(totalExpensesQ);
		}
		
		if(vm.getTotalsType().equals("annual")) {
			Double totalExpensesSum = 0.0,ipaSum = 0.0;
			for(int i=0;i<12;i++) {
				totalExpensesSum += totalExpenses[i];
				ipaSum += ipaPremiumValue[i];
			}
			ipaPremiumValueA[0] = ipaSum;
			totalExpensesA[0] = totalExpensesSum;
			vmObj.setIpaPremiumValue(ipaPremiumValueA);
			vmObj.setTotalExpenses(totalExpensesA);
		}
		
		return vmObj;
	}
	
	public ReportGridVM generateClaimReport(ReportVM vm) {
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		List<ReportDataVM> list = new ArrayList<>();
		List<Object[]> resultData = null;
		Integer noOfPages = 0;
		ReportGridVM gridVM = new ReportGridVM();
		//ReportResponseVM responseVM = instClaimDetailDao.generateClaimReport(vm);
		//ReportResponseVM responseVM = instClaimDetailDao.generateClaimDetailReport(vm);
		ReportResponseVM responseVM = instClaimDetailDao.generateClaimDetailDataReport(vm);
		resultData = responseVM.getDataList();
		noOfPages = responseVM.getNoOfPages();
		
		for(Object[] obj: resultData) {
			ReportDataVM dataVM = new ReportDataVM();
			
			if(obj[0] != null)
				dataVM.setPlanName(obj[0].toString());
			
			if(obj[1] != null)
			dataVM.setProviderName(obj[1].toString());
			
			if(obj[2] != null)
			dataVM.setMedicareId(obj[2].toString());
			
			if(obj[3] != null)
			dataVM.setPatientName(obj[3].toString());
			
			if(obj[4] != null)
			dataVM.setEligibleMonth(obj[4].toString());
			
			if(obj[5] != null)
				dataVM.setMedicareValue(obj[5].toString());
			
			if(vm.getSpeciality() != null && !vm.getSpeciality().equals("") && vm.getClaimType().contains("prof claim")) {
				if(obj[10] != null)
					dataVM.setCost("$"+formatter.format(Double.parseDouble(obj[10].toString())));
			} else {
				if(obj[12] != null)
				dataVM.setCost("$"+formatter.format(Double.parseDouble(obj[12].toString())));
			}
			
			list.add(dataVM);
		}
		gridVM.setReportDataList(list);
		gridVM.setPages(noOfPages);
		gridVM.setTotalCount(responseVM.getTotalCount());
		gridVM.setFileQuery(responseVM.getFileQuery());
		return gridVM;
	}
	
	public DashboardReportsVM getDuplicateClaimsData(ReportVM vm) {
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		List<Object[]> resultData = null;
		Integer noOfPages = 0;
		List<DuplicateClaimsReportVM> list = new ArrayList<>();
		DashboardReportsVM gridVM = new DashboardReportsVM();
		
		ReportResponseVM responseVM = instClaimDetailDao.getDuplicateClaimsData(vm);
		resultData = responseVM.getDataList();
		noOfPages = responseVM.getNoOfPages();
		
		for(Object[] obj: resultData) {
			DuplicateClaimsReportVM dataVM = new DuplicateClaimsReportVM();
			
			if(obj[0] != null)
				dataVM.setSubscriberId(obj[0].toString());
			if(obj[1] != null)
				dataVM.setPlanName(obj[1].toString());
			if(obj[3] != null)
				dataVM.setPatientName(obj[3].toString());
			if(obj[4] != null)
				dataVM.setPcp(obj[4].toString());
			if(obj[5] != null)
				dataVM.setEligibleMonth(obj[5].toString());
			if(obj[6] != null)
				dataVM.setClaimDate(obj[6].toString());
			if(obj[7] != null) {
				if(obj[7].toString().equals("0.0")) {
					dataVM.setDuplicativeCost("$0");
				} else {
					dataVM.setDuplicativeCost("$"+formatter.format(Double.parseDouble(obj[7].toString())));
				}
			}
			if(obj[9] != null)
				dataVM.setClaimType(obj[9].toString());
			dataVM.setTermedMonth("");
			list.add(dataVM);
		}
		gridVM.setDuplicateClaimsReportData(list);
		gridVM.setPages(noOfPages);
		gridVM.setTotalCount(responseVM.getTotalCount());
		gridVM.setFileQuery(responseVM.getFileQuery());
		return gridVM;
	}
	
	public DashboardReportsVM getDuplicateClaimsExpandData(ReportVM vm) {
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		List<Object[]> resultData = null;
		Integer noOfPages = 0;
		List<DuplicateClaimsExpandVM> list = new ArrayList<>();
		DashboardReportsVM gridVM = new DashboardReportsVM();
		
		ReportResponseVM responseVM = instClaimDetailDao.getDuplicateClaimsExpandData(vm);
		resultData = responseVM.getDataList();
		noOfPages = responseVM.getNoOfPages();
		
		for(Object[] obj: resultData) {
			DuplicateClaimsExpandVM dataVM = new DuplicateClaimsExpandVM();
			
			if(obj[0] != null)
				dataVM.setClaimId(obj[0].toString());
			if(obj[1] != null)
				dataVM.setClaimDate(obj[1].toString());
			if(obj[2] != null)
				dataVM.setClaimType(obj[2].toString());
			if(obj[3] != null)
				dataVM.setClinicName(obj[3].toString());
			if(obj[4] != null)
				dataVM.setProviderName(obj[4].toString());
			if(obj[5] != null)
				dataVM.setBetosCat(obj[5].toString());
			if(obj[6] != null)
				dataVM.setDrgCode(obj[6].toString());
			if(obj[7] != null)
				dataVM.setIcdCodes(obj[7].toString());
			if(obj[8] != null) {
				if(obj[8].toString().equals("0.0")) {
					dataVM.setCost("$0");
				} else {
					dataVM.setCost("$"+formatter.format(Double.parseDouble(obj[8].toString())));
				}
			}
			list.add(dataVM);
		}
		gridVM.setDuplicateClaimsExpandData(list);
		gridVM.setPages(noOfPages);
		gridVM.setTotalCount(responseVM.getTotalCount());
		gridVM.setFileQuery(responseVM.getFileQuery());
		return gridVM;
	}
	
	public DashboardReportsVM getAdmissionsReportData(ReportVM vm) {
		DecimalFormat formatter = new DecimalFormat("#,###");
		List<Object[]> resultData = null;
		Integer noOfPages = 0;
		List<AdmissionsReportVM> list = new ArrayList<>();
		DashboardReportsVM gridVM = new DashboardReportsVM();
		ReportResponseVM responseVM = instClaimDetailDao.getAdmissionsReportData(vm);
		resultData = responseVM.getDataList();
		noOfPages = responseVM.getNoOfPages();
		
		for(Object[] obj: resultData) {
			AdmissionsReportVM dataVM = new AdmissionsReportVM();
			
			if(obj[0] != null)
				dataVM.setPatientName(obj[0].toString());
			if(obj[1] != null)
				dataVM.setSubscriberId(obj[1].toString());
			if(obj[2] != null)
				dataVM.setPcpName(obj[2].toString());
			if(obj[3] != null)
				dataVM.setEligibleMonth(obj[3].toString());
			if(obj[5] != null)
				dataVM.setTotalNoOfAdmissions(obj[5].toString());
			if(obj[4] != null)
				dataVM.setTotalCost("$"+formatter.format(Double.parseDouble(obj[4].toString())));
			list.add(dataVM);
		}
		gridVM.setAdmissionsReportData(list);
		gridVM.setPages(noOfPages);
		gridVM.setTotalCount(responseVM.getTotalCount());
		gridVM.setFileQuery(responseVM.getFileQuery());
		return gridVM;
	}
	
	public DashboardReportsVM getSummaryReportData(ReportVM vm) {
		DecimalFormat formatter = new DecimalFormat("#,###");
		List<Object[]> resultData = null;
		Integer noOfPages = 0;
		List<SummaryReportVM> list = new ArrayList<>();
		DashboardReportsVM gridVM = new DashboardReportsVM();
		ReportResponseVM responseVM = monthlyTotalsReportDao.getSummaryReportData(vm);
		resultData = responseVM.getDataList();
		noOfPages = responseVM.getNoOfPages();
		
		for(Object[] obj: resultData) {
			SummaryReportVM dataVM = new SummaryReportVM();
			
			if(obj[0] != null)
				dataVM.setMonth(obj[0].toString());
			if(obj[1] != null)
				dataVM.setMembers(obj[1].toString());
			if(obj[2] != null)
				dataVM.setMaPremium("$"+formatter.format(Double.parseDouble(obj[2].toString())));
			if(obj[3] != null)
				dataVM.setPartDPremium("$"+formatter.format(Double.parseDouble(obj[3].toString())));
			if(obj[4] != null)
				dataVM.setTotalPremium("$"+formatter.format(Double.parseDouble(obj[4].toString())));
			if(obj[5] != null)
				dataVM.setIpaPremium("$"+formatter.format(Double.parseDouble(obj[5].toString())));
			if(obj[6] != null)
				dataVM.setPcpCap("$"+formatter.format(Double.parseDouble(obj[6].toString())));
			if(obj[7] != null)
				dataVM.setSpecCost("$"+formatter.format(Double.parseDouble(obj[7].toString())));
			if(obj[8] != null)
				dataVM.setProfClaims("$"+formatter.format(Double.parseDouble(obj[8].toString())));
			if(obj[9] != null)
				dataVM.setInstClaims("$"+formatter.format(Double.parseDouble(obj[9].toString())));
			if(obj[10] != null)
				dataVM.setRxClaims("$"+formatter.format(Double.parseDouble(obj[10].toString())));
			if(obj[11] != null)
				dataVM.setIbnrDollars("$"+formatter.format(Double.parseDouble(obj[11].toString())));
			if(obj[12] != null)
				dataVM.setReinsurancePremium("$"+formatter.format(Double.parseDouble(obj[12].toString())));
			if(obj[13] != null)
				dataVM.setTotalExpenses("$"+formatter.format(Double.parseDouble(obj[13].toString())));
			
			dataVM.setSpecCap("$0");
			dataVM.setReinsuranceRecovered("$0");
			dataVM.setRxAdmin("$0");
			dataVM.setSilverSneakerUtilization("$0");
			dataVM.setPba("$0");
			dataVM.setHumanaAtHome("$0");
			dataVM.setDentalFFS("$0");
			list.add(dataVM);
		}
		gridVM.setSummaryReportData(list);
		gridVM.setPages(noOfPages);
		gridVM.setTotalCount(responseVM.getTotalCount());
		gridVM.setFileQuery(responseVM.getFileQuery());
		return gridVM;
	}
	
	public DashboardReportsVM getSettledMonthsReportData(ReportVM vm) {
		DecimalFormat formatter = new DecimalFormat("#,###");
		List<Object[]> resultData = null;
		Integer noOfPages = 0;
		List<SettledMonthsReportVM> list = new ArrayList<>();
		DashboardReportsVM gridVM = new DashboardReportsVM();
		ReportResponseVM responseVM = monthlyTotalsReportDao.getSettledMonthsReportData(vm);
		resultData = responseVM.getDataList();
		noOfPages = responseVM.getNoOfPages();
		
		for(Object[] obj: resultData) {
			SettledMonthsReportVM dataVM = new SettledMonthsReportVM();
			
			if(obj[0] != null)
				dataVM.setMonth(obj[0].toString());
			if(obj[1] != null)
				dataVM.setMembership(formatter.format(Double.parseDouble(obj[1].toString())));
			if(obj[2] != null)
				dataVM.setIpaPremium("$"+formatter.format(Double.parseDouble(obj[2].toString())));
			if(obj[3] != null)
				dataVM.setTotalExpenses("$"+formatter.format(Double.parseDouble(obj[3].toString())));
			if(obj[4] != null)
				dataVM.setStoploss("$"+formatter.format(Double.parseDouble(obj[4].toString())));
			if(obj[5] != null) {
				dataVM.setNetPremium("$"+formatter.format(Double.parseDouble(obj[5].toString())));
				dataVM.setSurplusDeficit("$"+formatter.format(Double.parseDouble(obj[5].toString())));
			}
			if(obj[6] != null)
				dataVM.setRiskSharing(formatter.format(Double.parseDouble(obj[6].toString())));
			
			list.add(dataVM);
		}
		gridVM.setSettledMonthsData(list);
		gridVM.setPages(noOfPages);
		gridVM.setTotalCount(responseVM.getTotalCount());
		gridVM.setFileQuery(responseVM.getFileQuery());
		return gridVM;
	}
	
	public DashboardReportsVM getSettledMonthsExpandReportData(ReportVM vm) {
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		List<Object[]> resultData = null;
		Integer noOfPages = 0;
		List<SettledMonthsExpandReportVM> list = new ArrayList<>();
		DashboardReportsVM gridVM = new DashboardReportsVM();
		ReportResponseVM responseVM = profClaimDetailDao.getSettledMonthsExpandReportData(vm);
		resultData = responseVM.getDataList();
		noOfPages = responseVM.getNoOfPages();
		
		for(Object[] obj: resultData) {
			SettledMonthsExpandReportVM dataVM = new SettledMonthsExpandReportVM();
			
			if(obj[0] != null)
				dataVM.setPatientName(obj[0].toString());
			if(obj[1] != null)
				dataVM.setPcpName(obj[1].toString());
			if(obj[2] != null)
				dataVM.setPcpLocation(obj[2].toString());
			if(obj[3] != null)
				dataVM.setCost("$"+formatter.format(Double.parseDouble(obj[3].toString())));
			if(obj[4] != null)
				dataVM.setClaimType(obj[4].toString());
			if(obj[5] != null)
				dataVM.setMra(obj[5].toString());
			
			list.add(dataVM);
		}
		gridVM.setSettledMonthsExpandData(list);
		gridVM.setPages(noOfPages);
		gridVM.setTotalCount(responseVM.getTotalCount());
		gridVM.setFileQuery(responseVM.getFileQuery());
		return gridVM;
	}
	public DashboardReportsVM getReinsuranceManagementData(ReportVM vm) {
	
		DecimalFormat formatter = new DecimalFormat("#,###");
		List<Object[]> resultData = null;
		Integer noOfPages = 0;
		List<ReinsuranceManagementReportVM> list = new ArrayList<>();
		DashboardReportsVM gridVM = new DashboardReportsVM();
		ReportResponseVM responseVM = monthlyTotalsReportDao.reinsuranceMangementReportData(vm);
		resultData = responseVM.getDataList();
		noOfPages = responseVM.getNoOfPages();
		
		for(Object[] obj: resultData) {
			ReinsuranceManagementReportVM dataVM = new ReinsuranceManagementReportVM();
			
			if(obj[0] != null)
				dataVM.setPlanName(obj[0].toString());
			if(obj[1] != null)
				dataVM.setPatientName(obj[1].toString());
			if(obj[2] != null)
				dataVM.setTermedMonth(obj[2].toString());
			if(obj[3] != null)
				dataVM.setPcpName(obj[3].toString());
			if(obj[4] != null)
				dataVM.setHicn(obj[4].toString());
			if(obj[5] != null)
				dataVM.setInstClaims("$"+formatter.format(Double.parseDouble(obj[5].toString())));
			if(obj[6] != null)
				dataVM.setProfClaims("$"+formatter.format(Double.parseDouble(obj[6].toString())));
			if(obj[7] != null)
				dataVM.setTotalCost("$"+formatter.format(Double.parseDouble(obj[7].toString())));
			list.add(dataVM);
		}
		gridVM.setReinsuranceManagementData(list);
		gridVM.setPages(noOfPages);
		gridVM.setTotalCount(responseVM.getTotalCount());
		gridVM.setFileQuery(responseVM.getFileQuery());
		return gridVM;
		
		
	}
	
	public DashboardReportsVM getReinsuranceCostReportData(ReportVM vm) {
		
		DecimalFormat formatter = new DecimalFormat("#,###");
		List<Object[]> resultData = null;
		Integer noOfPages = 0;
		List<ReinsuranceCostReportVM> list = new ArrayList<>();
		DashboardReportsVM gridVM = new DashboardReportsVM();
		ReportResponseVM responseVM = monthlyTotalsReportDao.reinsuranceCostReportData(vm);
		resultData = responseVM.getDataList();
		noOfPages = responseVM.getNoOfPages();
		
		for(Object[] obj: resultData) {
			ReinsuranceCostReportVM dataVM = new ReinsuranceCostReportVM();
			
			if(obj[0] != null)
				dataVM.setPlanName(obj[0].toString());
			if(obj[1] != null)
				dataVM.setPatientLastName(obj[1].toString());
			if(obj[2] != null)
				dataVM.setPatientFirstName(obj[2].toString());
			if(obj[3] != null)
				dataVM.setSubscriberID(obj[3].toString());
			if(obj[4] != null)
				dataVM.setEffectiveDate(obj[4].toString());
			if(obj[5] != null)
				dataVM.setDob(obj[5].toString());
			if(obj[6] != null)
				dataVM.setGender(obj[6].toString());
			if(obj[7] != null)
				dataVM.setPcpName(obj[7].toString());
			
			if(obj[10] != null)
			dataVM.setTotalClaimsCost("$"+formatter.format(Double.parseDouble(obj[10].toString())));
			list.add(dataVM);
		
		}
		gridVM.setReinsuranceCostReportData(list);
		gridVM.setPages(noOfPages);
		gridVM.setTotalCount(responseVM.getTotalCount());
		gridVM.setFileQuery(responseVM.getFileQuery());
		return gridVM;
		
		
	}
	

	
	public DashboardReportsVM getPmpmByPracticeReportData(ReportVM vm) {
		DecimalFormat formatter = new DecimalFormat("#,###");
		List<Object[]> resultData = null;
		Integer noOfPages = 0;
		List<PmpmByPracticeReportVM> list = new ArrayList<>();
		DashboardReportsVM gridVM = new DashboardReportsVM();
		ReportResponseVM responseVM = monthlyTotalsReportDao.getPmpmByPracticeReportData(vm);
		resultData = responseVM.getDataList();
		noOfPages = responseVM.getNoOfPages();
		
		for(Object[] obj: resultData) {
			PmpmByPracticeReportVM dataVM = new PmpmByPracticeReportVM();
			
			if(obj[0] != null)
				dataVM.setProviderName(obj[0].toString());
			if(obj[1] != null)
				dataVM.setTotalCost("$"+formatter.format(Double.parseDouble(obj[1].toString())));
			if(obj[2] != null)
				dataVM.setTotalNumberOfMemberMonth(formatter.format(Double.parseDouble(obj[2].toString())));
			if(obj[3] != null)
				dataVM.setPmpm("$"+formatter.format(Double.parseDouble(obj[3].toString())));
			if(obj[4] != null)
				dataVM.setPmpy("$"+formatter.format(Double.parseDouble(obj[4].toString())));
			if(obj[5] != null)
				dataVM.setTotalPremium("$"+formatter.format(Double.parseDouble(obj[5].toString())));
			if(obj[6] != null)
				dataVM.setIpaPremium("$"+formatter.format(Double.parseDouble(obj[6].toString())));
			if(obj[7] != null)
				dataVM.setDifference("$"+formatter.format(Double.parseDouble(obj[7].toString())));
			
			list.add(dataVM);
		}
		gridVM.setPmpmByPracticeData(list);
		gridVM.setPages(noOfPages);
		gridVM.setTotalCount(responseVM.getTotalCount());
		gridVM.setFileQuery(responseVM.getFileQuery());
		return gridVM;
	}
	
	public DashboardReportsVM getBeneficiariesManagementReportData(ReportVM vm) {
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		List<Object[]> resultData = null;
		Integer noOfPages = 0;
		List<BeneficiariesManagementReportVM> list = new ArrayList<>();
		DashboardReportsVM gridVM = new DashboardReportsVM();
		ReportResponseVM responseVM = demographicDetailDao.getBeneficiariesManagementReportData(vm);
		resultData = responseVM.getDataList();
		noOfPages = responseVM.getNoOfPages();
		
		for(Object[] obj: resultData) {
			BeneficiariesManagementReportVM dataVM = new BeneficiariesManagementReportVM();
			
			if(obj[0] != null)
				dataVM.setPlanName(obj[0].toString());
			if(obj[1] != null)
				dataVM.setHicn(obj[1].toString());
			if(obj[2] != null)
				dataVM.setPatientName(obj[2].toString()+",\n"+obj[3].toString());
			if(obj[4] != null)
				dataVM.setDob(obj[4].toString());
			if(obj[5] != null)
				dataVM.setEligibleMonth(obj[5].toString());
			if(obj[6] != null)
				dataVM.setPcpName(obj[6].toString());
			if(obj[7] != null)
				dataVM.setPcpLocation(obj[7].toString());
			if(obj[8] != null)
				dataVM.setMra(obj[8].toString());
			if(obj[9] != null)
				dataVM.setAddress(obj[9].toString());
			if(obj[10] != null)
				dataVM.setMedicareId(obj[10].toString());
			if(obj[11] != null)
				dataVM.setSpecCost(obj[11].toString());
			if(obj[12] != null)
				dataVM.setPcpCap(obj[12].toString());
			if(obj[13] != null)
				dataVM.setReinsurancePrem(obj[13].toString());
			if(obj[14] != null)
				dataVM.setInstClaims(obj[14].toString());
			if(obj[15] != null)
				dataVM.setProfClaims(obj[15].toString());
			if(obj[16] != null)
				dataVM.setRxClaims(obj[16].toString());
			if(obj[17] != null)
				dataVM.setTotalCost("$"+formatter.format(Double.parseDouble(obj[17].toString())));
			
			dataVM.setTermedMonth("");
			dataVM.setRecentAppointmentDate("");
			dataVM.setNextAppointmentDate("");
			dataVM.setFacilityLocation("");
			dataVM.setPhoneNumber("");
			dataVM.setLastClaimsDate("");
			dataVM.setIcdCode("");
			
			list.add(dataVM);
		}
		gridVM.setBeneficiariesManagementData(list);
		gridVM.setPages(noOfPages);
		gridVM.setTotalCount(responseVM.getTotalCount());
		gridVM.setFileQuery(responseVM.getFileQuery());
		return gridVM;
	}
	
	public DashboardReportsVM getBeneficiariesManagementByDoctorReportData(ReportVM vm) {
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		List<Object[]> resultData = null;
		Integer noOfPages = 0;
		List<BeneficiariesManagementByDoctorReportVM> list = new ArrayList<>();
		DashboardReportsVM gridVM = new DashboardReportsVM();
		ReportResponseVM responseVM = demographicDetailDao.getBeneficiariesManagementByDoctorReportData(vm);
		resultData = responseVM.getDataList();
		noOfPages = responseVM.getNoOfPages();
		
		for(Object[] obj: resultData) {
			BeneficiariesManagementByDoctorReportVM dataVM = new BeneficiariesManagementByDoctorReportVM();
			
			if(obj[0] != null)
				dataVM.setPcpName(obj[0].toString());
			if(obj[1] != null)
				dataVM.setPcpLocation(obj[1].toString());
			if(obj[2] != null)
				dataVM.setAverageMra(obj[2].toString());
			if(obj[3] != null)
				dataVM.setPcpId(obj[3].toString());
			if(obj[4] != null)
				dataVM.setSpecCost(obj[4].toString());
			if(obj[5] != null)
				dataVM.setPcpCap(obj[5].toString());
			if(obj[6] != null)
				dataVM.setReinsurancePrem(obj[6].toString());
			if(obj[7] != null)
				dataVM.setInstClaims(obj[7].toString());
			if(obj[8] != null)
				dataVM.setProfClaims(obj[8].toString());
			if(obj[9] != null)
				dataVM.setRxClaims(obj[9].toString());
			if(obj[10] != null)
				dataVM.setTotalCost("$"+formatter.format(Double.parseDouble(obj[10].toString())));
			
			list.add(dataVM);
		}
		gridVM.setBeneficiariesManagementByDoctorData(list);
		gridVM.setPages(noOfPages);
		gridVM.setTotalCount(responseVM.getTotalCount());
		gridVM.setFileQuery(responseVM.getFileQuery());
		return gridVM;
	}
	
	public DashboardReportsVM getBeneficiariesManagementExpandReportData(ReportVM vm) {
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		List<Object[]> resultData = null;
		Integer noOfPages = 0;
		List<BeneficiariesManagementExpandReportVM> list = new ArrayList<>();
		DashboardReportsVM gridVM = new DashboardReportsVM();
		ReportResponseVM responseVM = demographicDetailDao.getBeneficiariesManagementExpandReportData(vm);
		resultData = responseVM.getDataList();
		noOfPages = responseVM.getNoOfPages();
		
		for(Object[] obj: resultData) {
			BeneficiariesManagementExpandReportVM dataVM = new BeneficiariesManagementExpandReportVM();
			
			if(obj[0] != null)
				dataVM.setClaimId(obj[0].toString());
			if(obj[1] != null)
				dataVM.setClaimDate(obj[1].toString());
			if(obj[2] != null)
				dataVM.setClaimType(obj[2].toString());
			if(obj[3] != null)
				dataVM.setClinicName(obj[3].toString());
			if(obj[4] != null)
				dataVM.setPcpName(obj[4].toString());
			if(obj[5] != null)
				dataVM.setDrgCode(obj[5].toString());
			if(obj[6] != null)
				dataVM.setBetosCat(obj[6].toString());
			if(obj[7] != null)
				dataVM.setCost("$"+formatter.format(Double.parseDouble(obj[7].toString())));
			if(obj[8] != null)
				dataVM.setIcdCodes(obj[8].toString());
			dataVM.setHccCodes("");
			
			list.add(dataVM);
		}
		gridVM.setBeneficiariesManagementExpandData(list);
		gridVM.setPages(noOfPages);
		gridVM.setTotalCount(responseVM.getTotalCount());
		gridVM.setFileQuery(responseVM.getFileQuery());
		return gridVM;
	}
	
	public DashboardReportsVM getBeneficiariesManagementByLocationReportData(ReportVM vm) {
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		List<Object[]> resultData = null;
		Integer noOfPages = 0;
		List<BeneficiariesManagementByLocationReportVM> list = new ArrayList<>();
		DashboardReportsVM gridVM = new DashboardReportsVM();
		ReportResponseVM responseVM = demographicDetailDao.getBeneficiariesManagementByLocationReportData(vm);
		resultData = responseVM.getDataList();
		noOfPages = responseVM.getNoOfPages();
		
		for(Object[] obj: resultData) {
			BeneficiariesManagementByLocationReportVM dataVM = new BeneficiariesManagementByLocationReportVM();
			
			if(obj[0] != null)
				dataVM.setPcpLocation(obj[0].toString());
			if(obj[1] != null)
				dataVM.setMra(obj[1].toString());
			if(obj[2] != null)
				dataVM.setSpecCost(obj[2].toString());
			if(obj[3] != null)
				dataVM.setPcpCap(obj[3].toString());
			if(obj[4] != null)
				dataVM.setReinsurancePrem(obj[4].toString());
			if(obj[5] != null)
				dataVM.setInstClaim(obj[5].toString());
			if(obj[6] != null)
				dataVM.setProfClaims(obj[6].toString());
			if(obj[7] != null)
				dataVM.setRxClaims(obj[7].toString());
			if(obj[8] != null)
				dataVM.setTotalCost("$"+formatter.format(Double.parseDouble(obj[8].toString())));
		
			list.add(dataVM);
		}
		gridVM.setBeneficiariesManagementByLocationData(list);
		gridVM.setPages(noOfPages);
		gridVM.setTotalCount(responseVM.getTotalCount());
		gridVM.setFileQuery(responseVM.getFileQuery());
		return gridVM;
	}
	
	public DashboardReportsVM getBeneficiariesManagementByLocationExpandReportData(ReportVM vm) {
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		List<Object[]> resultData = null;
		Integer noOfPages = 0;
		List<BeneficiariesManagementExpandReportVM> list = new ArrayList<>();
		DashboardReportsVM gridVM = new DashboardReportsVM();
		ReportResponseVM responseVM = demographicDetailDao.getBeneficiariesManagementByLocationExpandReportData(vm);
		resultData = responseVM.getDataList();
		noOfPages = responseVM.getNoOfPages();
		
		for(Object[] obj: resultData) {
			BeneficiariesManagementExpandReportVM dataVM = new BeneficiariesManagementExpandReportVM();
			
			if(obj[0] != null)
				dataVM.setClaimId(obj[0].toString());
			if(obj[1] != null)
				dataVM.setClaimDate(obj[1].toString());
			if(obj[2] != null)
				dataVM.setClaimType(obj[2].toString());
			if(obj[3] != null)
				dataVM.setClinicName(obj[3].toString());
			if(obj[4] != null)
				dataVM.setPcpLocation(obj[4].toString());
			if(obj[5] != null)
				dataVM.setDrgCode(obj[5].toString());
			else
				dataVM.setDrgCode("");
			if(obj[6] != null)
				dataVM.setBetosCat(obj[6].toString());
			else
				dataVM.setBetosCat("");
			if(obj[7] != null)
				dataVM.setCost("$"+formatter.format(Double.parseDouble(obj[7].toString())));
			if(obj[8] != null)
				dataVM.setIcdCodes(obj[8].toString());
			dataVM.setHccCodes("");
			
			list.add(dataVM);
		}
		gridVM.setBeneficiariesManagementByLocationExpandData(list);
		gridVM.setPages(noOfPages);
		gridVM.setTotalCount(responseVM.getTotalCount());
		gridVM.setFileQuery(responseVM.getFileQuery());
		return gridVM;
	}
	
	public DashboardReportsVM getBeneficiariesManagementByClinicExpandReportData(ReportVM vm) {
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		List<Object[]> resultData = null;
		Integer noOfPages = 0;
		List<BeneficiariesManagementExpandReportVM> list = new ArrayList<>();
		DashboardReportsVM gridVM = new DashboardReportsVM();
		ReportResponseVM responseVM = demographicDetailDao.getBeneficiariesManagementByClinicExpandReportData(vm);
		resultData = responseVM.getDataList();
		noOfPages = responseVM.getNoOfPages();
		
		for(Object[] obj: resultData) {
			BeneficiariesManagementExpandReportVM dataVM = new BeneficiariesManagementExpandReportVM();
			
			if(obj[0] != null)
				dataVM.setClaimId(obj[0].toString());
			if(obj[1] != null)
				dataVM.setClaimDate(obj[1].toString());
			if(obj[2] != null)
				dataVM.setClaimType(obj[2].toString());
			if(obj[3] != null)
				dataVM.setClinicName(obj[3].toString());
			if(obj[4] != null)
				dataVM.setPcpName(obj[4].toString());
			if(obj[5] != null)
				dataVM.setDrgCode(obj[5].toString());
			else
				dataVM.setDrgCode("");
			if(obj[6] != null)
				dataVM.setBetosCat(obj[6].toString());
			else
				dataVM.setBetosCat("");
			if(obj[7] != null)
				dataVM.setCost("$"+formatter.format(Double.parseDouble(obj[7].toString())));
			if(obj[8] != null)
				dataVM.setIcdCodes(obj[8].toString());
			dataVM.setHccCodes("");
		
			
			
			list.add(dataVM);
		}
		gridVM.setBeneficiariesManagementByClinicExpandData(list);
		gridVM.setPages(noOfPages);
		gridVM.setTotalCount(responseVM.getTotalCount());
		gridVM.setFileQuery(responseVM.getFileQuery());
		return gridVM;
	}
	
	public DashboardReportsVM getBeneficiariesManagementByClinicReportData(ReportVM vm) {
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		List<Object[]> resultData = null;
		Integer noOfPages = 0;
		List<BeneficiariesManagementByClinicReportVM> list = new ArrayList<>();
		DashboardReportsVM gridVM = new DashboardReportsVM();
		ReportResponseVM responseVM = demographicDetailDao.getBeneficiariesManagementByClinicReportData(vm);
		resultData = responseVM.getDataList();
		noOfPages = responseVM.getNoOfPages();
		
		for(Object[] obj: resultData) {
			BeneficiariesManagementByClinicReportVM dataVM = new BeneficiariesManagementByClinicReportVM();
			
			if(obj[0] != null)
				dataVM.setClinicName(obj[0].toString());
			if(obj[1] != null)
				dataVM.setClinicId(obj[1].toString());
			if(obj[2] != null)
				dataVM.setTotalCost("$"+formatter.format(Double.parseDouble(obj[2].toString())));
		
			list.add(dataVM);
		}
		gridVM.setBeneficiariesManagementByClinicData(list);
		gridVM.setPages(noOfPages);
		gridVM.setTotalCount(responseVM.getTotalCount());
		gridVM.setFileQuery(responseVM.getFileQuery());
		return gridVM;
	}
	
	public DashboardReportsVM getBeneficiariesManagementByDoctorExpandReportData(ReportVM vm) {
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		List<Object[]> resultData = null;
		Integer noOfPages = 0;
		List<BeneficiariesManagementExpandReportVM> list = new ArrayList<>();
		DashboardReportsVM gridVM = new DashboardReportsVM();
		ReportResponseVM responseVM = demographicDetailDao.getBeneficiariesManagementByDoctorExpandReportData(vm);
		resultData = responseVM.getDataList();
		noOfPages = responseVM.getNoOfPages();
		
		for(Object[] obj: resultData) {
			BeneficiariesManagementExpandReportVM dataVM = new BeneficiariesManagementExpandReportVM();
			
			if(obj[0] != null)
				dataVM.setClaimId(obj[0].toString());
			if(obj[1] != null)
				dataVM.setClaimDate(obj[1].toString());
			if(obj[2] != null)
				dataVM.setClaimType(obj[2].toString());
			if(obj[3] != null)
				dataVM.setClinicName(obj[3].toString());
			if(obj[4] != null)
				dataVM.setPcpName(obj[4].toString());
			if(obj[5] != null)
				dataVM.setDrgCode(obj[5].toString());
			if(obj[6] != null)
				dataVM.setBetosCat(obj[6].toString());
			if(obj[7] != null)
				dataVM.setCost("$"+formatter.format(Double.parseDouble(obj[7].toString())));
			if(obj[8] != null)
				dataVM.setIcdCodes(obj[8].toString());
			dataVM.setHccCodes("");
			
			list.add(dataVM);
		}
		gridVM.setBeneficiariesManagementByDoctorExpandData(list);
		gridVM.setPages(noOfPages);
		gridVM.setTotalCount(responseVM.getTotalCount());
		gridVM.setFileQuery(responseVM.getFileQuery());
		return gridVM;
	}
	
	public DashboardReportsVM getPmpmByPracticeExpandReportData(ReportVM vm) {
		DecimalFormat formatter = new DecimalFormat("#,###");
		List<Object[]> resultData = null;
		Integer noOfPages = 0;
		List<PmpmByPracticeExpandReportVM> list = new ArrayList<>();
		DashboardReportsVM gridVM = new DashboardReportsVM();
		ReportResponseVM responseVM = monthlyTotalsReportDao.getPmpmByPracticeExpandReportData(vm);
		resultData = responseVM.getDataList();
		noOfPages = responseVM.getNoOfPages();
		
		for(Object[] obj: resultData) {
			PmpmByPracticeExpandReportVM dataVM = new PmpmByPracticeExpandReportVM();
			
			if(obj[0] != null)
				dataVM.setPatientName(obj[0].toString()+", "+obj[1].toString());
			if(obj[2] != null)
				dataVM.setPcpName(obj[2].toString());
			if(obj[3] != null)
				dataVM.setPcpLocation(obj[3].toString());
			if(obj[4] != null)
				dataVM.setMra(obj[4].toString());
			dataVM.setCost("");
			dataVM.setClaimType("");
			
			list.add(dataVM);
		}
		gridVM.setPmpmByPracticeExpandData(list);
		gridVM.setPages(noOfPages);
		gridVM.setTotalCount(responseVM.getTotalCount());
		gridVM.setFileQuery(responseVM.getFileQuery());
		return gridVM;
	}
	
	public DashboardReportsVM getMembershipManagementPatientsData(ReportVM vm) {
		DecimalFormat formatter = new DecimalFormat("#,###");
		List<Object[]> resultData = null;
		Integer noOfPages = 0;
		List<MembershipManagementPatientTypeVM> list = new ArrayList<>();
		DashboardReportsVM gridVM = new DashboardReportsVM();
		ReportResponseVM responseVM = demographicDetailDao.getMembershipManagementPatientTypeData(vm);
		resultData = responseVM.getDataList();
		noOfPages = responseVM.getNoOfPages();
		
		for(Object[] obj: resultData) {
			MembershipManagementPatientTypeVM dataVM = new MembershipManagementPatientTypeVM();
			
			if(obj[0] != null)
				dataVM.setPlanName(obj[0].toString());
			if(obj[1] != null)
				dataVM.setMedicareId(obj[1].toString());
			if(obj[2] != null)
				dataVM.setInsuranceId(obj[2].toString());
			if(obj[3] != null)
				dataVM.setPatientName(obj[3].toString());
			if(obj[4] != null)
				dataVM.setPatientDob(obj[4].toString());
			if(obj[5] != null)
				dataVM.setAssignedPcp(obj[5].toString());
			if(obj[6] != null)
				dataVM.setPcpLocation(obj[6].toString());
			
			if(vm.getPatientType().equals("Termed")) {
				/*SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date convertedDate = null;
				try {
					convertedDate = dateFormat.parse(responseVM.getPrevMonth());
				} catch (ParseException e) {e.printStackTrace();}
				
				Calendar c = Calendar.getInstance();
				c.setTime(convertedDate);
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
				dataVM.setIpaEffectiveDate(dateFormat.format(c.getTime()));*/
				dataVM.setIpaEffectiveDate(responseVM.getPrevMonth());
			} else {
				dataVM.setIpaEffectiveDate(obj[7].toString());
			}
			
			if(obj[8] != null && !obj[8].toString().equals(""))
				dataVM.setMra(obj[8].toString());
			else
				dataVM.setMra("0");
			if(obj[16] != null)
				dataVM.setTotalPatientCost("$"+formatter.format(Double.parseDouble(obj[16].toString())));
			
			list.add(dataVM);
		}
		gridVM.setMembershipManagementData(list);
		gridVM.setPages(noOfPages);
		gridVM.setTotalCount(responseVM.getTotalCount());
		gridVM.setFileQuery(responseVM.getFileQuery());
		return gridVM;
	}
	
	public DashboardReportsVM getPatientVisitReportData(ReportVM vm) {
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		List<Object[]> resultData = null;
		Integer noOfPages = 0;
		List<PatientVisitReportVM> list = new ArrayList<>();
		DashboardReportsVM gridVM = new DashboardReportsVM();
		ReportResponseVM responseVM = instClaimDetailDao.getPatientVisitReportData(vm);
		resultData = responseVM.getDataList();
		noOfPages = responseVM.getNoOfPages();
		
		for(Object[] obj: resultData) {
			PatientVisitReportVM dataVM = new PatientVisitReportVM();
			
			if(obj[0] != null)
				dataVM.setPatientName(obj[0].toString());
			if(obj[1] != null)
				dataVM.setHicn(obj[1].toString());
			if(obj[2] != null)
				dataVM.setPcpName(obj[2].toString());
			if(obj[3] != null)
				dataVM.setTermedMonth(obj[3].toString());
			if(obj[4] != null)
				dataVM.setIpaEffectiveDate(obj[4].toString());
			if(obj[5] != null)
				dataVM.setTotalErVisits(obj[5].toString());
			if(obj[6] != null)
				dataVM.setTotalCost("$"+formatter.format(Double.parseDouble(obj[6].toString())));
			list.add(dataVM);
		}
		gridVM.setPatientVisitReportData(list);
		gridVM.setPages(noOfPages);
		gridVM.setTotalCount(responseVM.getTotalCount());
		gridVM.setFileQuery(responseVM.getFileQuery());
		return gridVM;
	}
	
	public DashboardReportsVM getPatientVisitExpandReportData(ReportVM vm) {
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		List<Object[]> resultData = null;
		Integer noOfPages = 0;
		List<PatientVisitExpandReportVM> list = new ArrayList<>();
		DashboardReportsVM gridVM = new DashboardReportsVM();
		ReportResponseVM responseVM = instClaimDetailDao.getPatientVisitExpandReportData(vm);
		resultData = responseVM.getDataList();
		noOfPages = responseVM.getNoOfPages();
		
		for(Object[] obj: resultData) {
			PatientVisitExpandReportVM dataVM = new PatientVisitExpandReportVM();
			
			if(obj[0] != null)
				dataVM.setClaimId(obj[0].toString());
			if(obj[1] != null)
				dataVM.setClaimDate(obj[1].toString());
			if(obj[2] != null)
				dataVM.setClaimType(obj[2].toString());
			if(obj[3] != null)
				dataVM.setClinicName(obj[3].toString());
			if(obj[4] != null)
				dataVM.setPcpName(obj[4].toString());
			if(obj[5] != null)
				dataVM.setDrgCode(obj[5].toString());
			if(obj[6] != null)
				dataVM.setBetosCat(obj[6].toString());
			if(obj[7] != null)
				dataVM.setCost("$"+formatter.format(Double.parseDouble(obj[7].toString())));
			if(obj[8] != null)
				dataVM.setIcdCodes(obj[8].toString());
			
			dataVM.setHccCodes("");
			list.add(dataVM);
		}
		gridVM.setPatientVisitExpandReportData(list);
		gridVM.setPages(noOfPages);
		gridVM.setTotalCount(responseVM.getTotalCount());
		gridVM.setFileQuery(responseVM.getFileQuery());
		return gridVM;
	}
	
	public DashboardReportsVM getAdmissionsReportExpandData(ReportVM vm) {
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		List<Object[]> resultData = null;
		Integer noOfPages = 0;
		List<AdmissionsReportExpandVM> list = new ArrayList<>();
		DashboardReportsVM gridVM = new DashboardReportsVM();
		ReportResponseVM responseVM = instClaimDetailDao.getAdmissionsReportExpandData(vm);
		resultData = responseVM.getDataList();
		noOfPages = responseVM.getNoOfPages();
		
		for(Object[] obj: resultData) {
			AdmissionsReportExpandVM dataVM = new AdmissionsReportExpandVM();
			
			if(obj[0] != null)
				dataVM.setClaimId(obj[0].toString());
			if(obj[1] != null)
				dataVM.setClaimDate(obj[1].toString());
			if(obj[2] != null)
				dataVM.setClaimType(obj[2].toString());
			if(obj[3] != null)
				dataVM.setClinicName(obj[3].toString());
			if(obj[4] != null)
				dataVM.setPcpName(obj[4].toString());
			if(obj[5] != null)
				dataVM.setDrgCode(obj[5].toString());
			if(obj[6] != null)
				dataVM.setBetosCat(obj[6].toString());
			if(obj[7] != null)
				dataVM.setCost("$"+formatter.format(Double.parseDouble(obj[7].toString())));
			list.add(dataVM);
		}
		gridVM.setAdmissionsReportExpandData(list);
		gridVM.setPages(noOfPages);
		gridVM.setTotalCount(responseVM.getTotalCount());
		gridVM.setFileQuery(responseVM.getFileQuery());
		return gridVM;
	}
	
	public DashboardReportsVM getSpecialistComparisonReportData(ReportVM vm) {
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		List<Object[]> resultData = null;
		Integer noOfPages = 0;
		List<SpecialistComparisonReportVM> list = new ArrayList<>();
		DashboardReportsVM gridVM = new DashboardReportsVM();
		ReportResponseVM responseVM = instClaimDetailDao.getSpecialistComparisonReportData(vm);
		resultData = responseVM.getDataList();
		noOfPages = responseVM.getNoOfPages();
		
		for(Object[] obj: resultData) {
			SpecialistComparisonReportVM dataVM = new SpecialistComparisonReportVM();
			
			if(obj[0] != null)
				dataVM.setSpecialityCode(obj[0].toString());
			if(obj[1] != null)
				dataVM.setNumberOfClaims(obj[1].toString());
			if(obj[2] != null)
				dataVM.setNumberOfBeneficiaries(obj[2].toString());
			if(obj[3] != null)
				dataVM.setCostPerClaim("$"+formatter.format(Double.parseDouble(obj[3].toString())));
			if(obj[4] != null)
				dataVM.setCostPerBeneficiary("$"+formatter.format(Double.parseDouble(obj[4].toString())));
			if(obj[5] != null)
				dataVM.setTotalCost("$"+formatter.format(Double.parseDouble(obj[5].toString())));
			if(obj[6] != null)
				dataVM.setNumberOfPcp(obj[6].toString());
			list.add(dataVM);
		}
		gridVM.setSpecialistComparisonReportData(list);
		gridVM.setPages(noOfPages);
		gridVM.setTotalCount(responseVM.getTotalCount());
		gridVM.setFileQuery(responseVM.getFileQuery());
		return gridVM;
	}
	
	public DashboardReportsVM getSpecialistComparisonExpandReportData(ReportVM vm) {
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		List<Object[]> resultData = null;
		Integer noOfPages = 0;
		List<SpecialistComparisonExpandReportVM> list = new ArrayList<>();
		DashboardReportsVM gridVM = new DashboardReportsVM();
		ReportResponseVM responseVM = instClaimDetailDao.getSpecialistComparisonExpandReportData(vm);
		resultData = responseVM.getDataList();
		noOfPages = responseVM.getNoOfPages();
		
		for(Object[] obj: resultData) {
			SpecialistComparisonExpandReportVM dataVM = new SpecialistComparisonExpandReportVM();
			
			if(obj[0] != null)
				dataVM.setPracticeName(obj[0].toString());
			if(obj[1] != null)
				dataVM.setSpecialityType(obj[1].toString());
			if(obj[2] != null)
				dataVM.setNumberOfClaims(obj[2].toString());
			if(obj[3] != null)
				dataVM.setAverageCostPerClaim("$"+formatter.format(Double.parseDouble(obj[3].toString())));
			if(obj[4] != null)
				dataVM.setCost("$"+formatter.format(Double.parseDouble(obj[4].toString())));
			
			list.add(dataVM);
		}
		gridVM.setSpecialistComparisonExpandReportData(list);
		gridVM.setPages(noOfPages);
		gridVM.setTotalCount(responseVM.getTotalCount());
		gridVM.setFileQuery(responseVM.getFileQuery());
		return gridVM;
	}
	
	public DashboardReportsVM getSpecialistComparisonExpandPracticeReportData(ReportVM vm) {
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		List<Object[]> resultData = null;
		Integer noOfPages = 0;
		List<SpecialistComparisonExpandPracticeReportVM> list = new ArrayList<>();
		DashboardReportsVM gridVM = new DashboardReportsVM();
		ReportResponseVM responseVM = instClaimDetailDao.getSpecialistComparisonExpandPracticeReportData(vm);
		resultData = responseVM.getDataList();
		noOfPages = responseVM.getNoOfPages();
		
		for(Object[] obj: resultData) {
			SpecialistComparisonExpandPracticeReportVM dataVM = new SpecialistComparisonExpandPracticeReportVM();
			
			if(obj[0] != null)
				dataVM.setPracticeName(obj[0].toString());
			if(obj[1] != null)
				dataVM.setSpecialityType(obj[1].toString());
			if(obj[2] != null)
				dataVM.setPatientName(obj[2].toString());
			if(obj[3] != null)
				dataVM.setPcpName(obj[3].toString());
			if(obj[4] != null)
				dataVM.setNumberOfClaims(obj[4].toString());
			if(obj[5] != null)
				dataVM.setAverageCostPerClaim("$"+formatter.format(Double.parseDouble(obj[5].toString())));
			if(obj[6] != null)
				dataVM.setCost("$"+formatter.format(Double.parseDouble(obj[6].toString())));
			
			list.add(dataVM);
		}
		gridVM.setSpecialistComparisonExpandPracticeReportData(list);
		gridVM.setPages(noOfPages);
		gridVM.setTotalCount(responseVM.getTotalCount());
		gridVM.setFileQuery(responseVM.getFileQuery());
		return gridVM;
	}
	
	public List<DuplicateClaimsReportVM> getDataForDuplicateClaimsXLFile(DuplicateClaimFileVM fileVM) {
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		List<Object[]> resultData = null;
		List<DuplicateClaimsReportVM> list = new ArrayList<>();
		resultData = instClaimDetailDao.getDuplicateClaimsAllData(fileVM.getFileQuery());
		
		for(Object[] obj: resultData) {
			DuplicateClaimsReportVM dataVM = new DuplicateClaimsReportVM();
			
			if(obj[1] != null)
				dataVM.setSubscriberId(obj[1].toString());
			if(obj[2] != null)
				dataVM.setPlanName(obj[2].toString());
			if(obj[3] != null)
				dataVM.setPatientName(obj[3].toString());
			if(obj[4] != null)
				dataVM.setPcp(obj[4].toString());
			if(obj[5] != null)
				dataVM.setEligibleMonth(obj[5].toString());
			if(obj[6] != null)
				dataVM.setClaimDate(obj[6].toString());
			if(obj[7] != null)
				dataVM.setDuplicativeCost("$"+formatter.format(Double.parseDouble(obj[7].toString())));
			list.add(dataVM);
		}
		return list;
	}
	
	public List<AdmissionsReportVM> getDataForAdmissionsReportXL(AdmissionsReportFileVM vm) {
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		List<Object[]> resultData = null;
		List<AdmissionsReportVM> list = new ArrayList<>();
		resultData = instClaimDetailDao.getDataForFile(vm.getFileQuery());
		
		for(Object[] obj: resultData) {
			AdmissionsReportVM dataVM = new AdmissionsReportVM();
			
			if(obj[0] != null)
				dataVM.setPatientName(obj[0].toString());
			if(obj[1] != null)
				dataVM.setSubscriberId(obj[1].toString());
			if(obj[2] != null)
				dataVM.setPcpName(obj[2].toString());
			if(obj[3] != null)
				dataVM.setEligibleMonth(obj[3].toString());
			if(obj[5] != null)
				dataVM.setTotalNoOfAdmissions(obj[5].toString());
			if(obj[4] != null)
				dataVM.setTotalCost("$"+formatter.format(Double.parseDouble(obj[4].toString())));
			list.add(dataVM);
		}
		
		return list;
	}
	
	public List<AdmissionsReportExpandVM> getDataForAdmissionsReportExpandXL(AdmissionsReportExpandFileVM vm) {
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		List<Object[]> resultData = null;
		List<AdmissionsReportExpandVM> list = new ArrayList<>();
		resultData = instClaimDetailDao.getDataForFile(vm.getFileQuery());
		
		for(Object[] obj: resultData) {
			AdmissionsReportExpandVM dataVM = new AdmissionsReportExpandVM();
			
			if(obj[0] != null)
				dataVM.setClaimId(obj[0].toString());
			if(obj[1] != null)
				dataVM.setClaimDate(obj[1].toString());
			if(obj[2] != null)
				dataVM.setClaimType(obj[2].toString());
			if(obj[3] != null)
				dataVM.setClinicName(obj[3].toString());
			if(obj[4] != null)
				dataVM.setPcpName(obj[4].toString());
			if(obj[5] != null)
				dataVM.setDrgCode(obj[5].toString());
			if(obj[6] != null)
				dataVM.setBetosCat(obj[6].toString());
			if(obj[7] != null)
				dataVM.setCost("$"+formatter.format(Double.parseDouble(obj[7].toString())));
			list.add(dataVM);
		}
		
		return list;
	}
	
	public List<ReportDataVM> getDataForFile(FileVM fileVM) {
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		List<Object[]> resultData = instClaimDetailDao.getDataForFile(fileVM.getFileQuery());
		List<ReportDataVM> list = new ArrayList<>();
		
		for(Object[] obj: resultData) {
			ReportDataVM dataVM = new ReportDataVM();
			
			if(obj[0] != null)
				dataVM.setPlanName(obj[0].toString());
			
			if(obj[1] != null)
			dataVM.setProviderName(obj[1].toString());
			
			if(obj[2] != null)
			dataVM.setMedicareId(obj[2].toString());
			
			if(obj[3] != null)
			dataVM.setPatientName(obj[3].toString());
			
			if(obj[4] != null)
			dataVM.setEligibleMonth(obj[4].toString());
			
			if(fileVM.getSpeciality() != null && !fileVM.getSpeciality().equals("") && fileVM.getClaimType().contains("prof claim")) {
				if(obj[10] != null)
					dataVM.setCost("$"+formatter.format(Double.parseDouble(obj[10].toString())));
			} else {
				if(obj[12] != null)
				dataVM.setCost("$"+formatter.format(Double.parseDouble(obj[12].toString())));
			}
			
			dataVM.setHccCodes("");
			dataVM.setIcdCode("");
			dataVM.setTermedMonth("");
			
			list.add(dataVM);
		}
		
		return list;
	}
	
	public List<PrintDataVM> getDataForPrint(FileVM fileVM) {
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		List<Object[]> resultData = instClaimDetailDao.getDataForFile(fileVM.getFileQuery());
		List<PrintDataVM> list = new ArrayList<>();
		
		for(Object[] obj: resultData) {
			PrintDataVM dataVM = new PrintDataVM();
			
			if(obj[0] != null)
				dataVM.setPlanName(obj[0].toString());
			
			if(obj[1] != null)
			dataVM.setProviderName(obj[1].toString());
			
			if(obj[2] != null)
			dataVM.setMedicareId(obj[2].toString());
			
			if(obj[3] != null)
			dataVM.setPatientName(obj[3].toString());
			
			if(obj[4] != null)
			dataVM.setEligibleMonth(obj[4].toString());
			
			if(fileVM.getSpeciality() != null && !fileVM.getSpeciality().equals("") && fileVM.getClaimType().contains("prof claim")) {
				if(obj[10] != null)
					dataVM.setCost("$"+formatter.format(Double.parseDouble(obj[10].toString())));
			} else {
				if(obj[12] != null)
				dataVM.setCost("$"+formatter.format(Double.parseDouble(obj[12].toString())));
			}
			
			dataVM.setHccCodes("");
			dataVM.setIcdCode("");
			dataVM.setTermedMonth("");
			
			list.add(dataVM);
		}
		
		return list;
	}
	
	public List<DuplicateClaimPrintDataVM> getDataForDuplicateClaimPrint(DuplicateClaimFileVM fileVM) {
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		List<Object[]> resultData = instClaimDetailDao.getDataForFile(fileVM.getFileQuery());
		List<DuplicateClaimPrintDataVM> list = new ArrayList<>();
		
		for(Object[] obj: resultData) {
			DuplicateClaimPrintDataVM dataVM = new DuplicateClaimPrintDataVM();
			
			if(obj[1] != null)
				dataVM.setSubscriberId(obj[1].toString());
			if(obj[2] != null)
				dataVM.setPlanName(obj[2].toString());
			if(obj[3] != null)
				dataVM.setPatientName(obj[3].toString());
			if(obj[4] != null)
				dataVM.setPcp(obj[4].toString());
			if(obj[5] != null)
				dataVM.setEligibleMonth(obj[5].toString());
			if(obj[6] != null)
				dataVM.setClaimDate(obj[6].toString());
			if(obj[7] != null)
				dataVM.setDuplicativeCost("$"+formatter.format(Double.parseDouble(obj[7].toString())));
			
			dataVM.setTermedMonth("");
			list.add(dataVM);
		}
		
		return list;
	}
	
	public List<AdmissionsReportPrintDataVM> getDataForAdmissionsReportPrint(AdmissionsReportFileVM fileVM) {
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		List<Object[]> resultData = instClaimDetailDao.getDataForFile(fileVM.getFileQuery());
		List<AdmissionsReportPrintDataVM> list = new ArrayList<>();
		
		for(Object[] obj: resultData) {
			AdmissionsReportPrintDataVM dataVM = new AdmissionsReportPrintDataVM();
			
			if(obj[0] != null)
				dataVM.setPatientName(obj[0].toString());
			if(obj[1] != null)
				dataVM.setSubscriberId(obj[1].toString());
			if(obj[2] != null)
				dataVM.setPcpName(obj[2].toString());
			if(obj[3] != null)
				dataVM.setEligibleMonth(obj[3].toString());
			if(obj[5] != null)
				dataVM.setTotalNumberOfAdmissions(obj[5].toString());
			if(obj[4] != null)
				dataVM.setTotalCost("$"+formatter.format(Double.parseDouble(obj[4].toString())));
			
			list.add(dataVM);
		}
		
		return list;
	}
	
	public List<PatientVisitReportPrintDataVM> getDataForPatientVisitReportPrint(PatientVisitReportFileVM fileVM) {
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		List<Object[]> resultData = instClaimDetailDao.getDataForFile(fileVM.getFileQuery());
		List<PatientVisitReportPrintDataVM> list = new ArrayList<>();
		
		for(Object[] obj: resultData) {
			PatientVisitReportPrintDataVM dataVM = new PatientVisitReportPrintDataVM();
			
			if(obj[0] != null)
				dataVM.setPatientName(obj[0].toString());
			if(obj[1] != null)
				dataVM.setHicn(obj[1].toString());
			if(obj[2] != null)
				dataVM.setPcpName(obj[2].toString());
			if(obj[3] != null)
				dataVM.setTermedMonth(obj[3].toString());
			if(obj[4] != null)
				dataVM.setIpaEffectiveDate(obj[4].toString());
			if(obj[5] != null)
				dataVM.setTotalErVisits(obj[5].toString());
			if(obj[6] != null)
				dataVM.setTotalCost("$"+formatter.format(Double.parseDouble(obj[6].toString())));
			
			list.add(dataVM);
		}
		
		return list;
	}
	
	public List<PatientVisitExpandReportPrintDataVM> getDataForPatientVisitExpandReportPrint(PatientVisitExpandReportFileVM fileVM) {
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		List<Object[]> resultData = instClaimDetailDao.getDataForFile(fileVM.getFileQuery());
		List<PatientVisitExpandReportPrintDataVM> list = new ArrayList<>();
		
		for(Object[] obj: resultData) {
			PatientVisitExpandReportPrintDataVM dataVM = new PatientVisitExpandReportPrintDataVM();
			
			if(obj[0] != null)
				dataVM.setClaimId(obj[0].toString());
			if(obj[1] != null)
				dataVM.setClaimDate(obj[1].toString());
			if(obj[2] != null)
				dataVM.setClaimType(obj[2].toString());
			if(obj[3] != null)
				dataVM.setClinicName(obj[3].toString());
			if(obj[4] != null)
				dataVM.setPcpName(obj[4].toString());
			if(obj[5] != null)
				dataVM.setDrgCode(obj[5].toString());
			if(obj[6] != null)
				dataVM.setBetosCat(obj[6].toString());
			if(obj[7] != null)
				dataVM.setCost("$"+formatter.format(Double.parseDouble(obj[7].toString())));
			if(obj[8] != null)
				dataVM.setIcdCodes(obj[8].toString());
			dataVM.setHccCodes("");
			
			list.add(dataVM);
		}
		
		return list;
	}
	
	public List<SettledMonthsReportPrintDataVM> getDataForSettledMonthsReportPrint(SettledMonthsReportFileVM fileVM) {
		DecimalFormat formatter = new DecimalFormat("#,###");
		List<Object[]> resultData = instClaimDetailDao.getDataForFile(fileVM.getFileQuery());
		List<SettledMonthsReportPrintDataVM> list = new ArrayList<>();
		
		for(Object[] obj: resultData) {
			SettledMonthsReportPrintDataVM dataVM = new SettledMonthsReportPrintDataVM();
			
			if(obj[0] != null)
				dataVM.setMonth(obj[0].toString());
			if(obj[1] != null)
				dataVM.setMembership(formatter.format(Double.parseDouble(obj[1].toString())));
			if(obj[2] != null)
				dataVM.setIpaPremium("$"+formatter.format(Double.parseDouble(obj[2].toString())));
			if(obj[3] != null)
				dataVM.setTotalExpenses("$"+formatter.format(Double.parseDouble(obj[3].toString())));
			if(obj[4] != null)
				dataVM.setStoploss("$"+formatter.format(Double.parseDouble(obj[4].toString())));
			if(obj[5] != null) {
				dataVM.setNetPremium("$"+formatter.format(Double.parseDouble(obj[5].toString())));
				dataVM.setSurplusDeficit("$"+formatter.format(Double.parseDouble(obj[5].toString())));
			}
			if(obj[6] != null)
				dataVM.setRiskSharing(formatter.format(Double.parseDouble(obj[6].toString())));
			
			list.add(dataVM);
		}
		
		return list;
	}
	
	public List<SettledMonthsExpandReportPrintDataVM> getDataForSettledMonthsExpandReportPrint(SettledMonthsExpandReportFileVM fileVM) {
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		List<Object[]> resultData = instClaimDetailDao.getDataForFile(fileVM.getFileQuery());
		List<SettledMonthsExpandReportPrintDataVM> list = new ArrayList<>();
		
		for(Object[] obj: resultData) {
			SettledMonthsExpandReportPrintDataVM dataVM = new SettledMonthsExpandReportPrintDataVM();
			
			if(obj[0] != null)
				dataVM.setPatientName(obj[0].toString());
			if(obj[1] != null)
				dataVM.setPcpName(obj[1].toString());
			if(obj[2] != null)
				dataVM.setPcpLocation(obj[2].toString());
			if(obj[3] != null)
				dataVM.setCost("$"+formatter.format(Double.parseDouble(obj[3].toString())));
			if(obj[4] != null)
				dataVM.setClaimType(obj[4].toString());
			if(obj[5] != null)
				dataVM.setMra(obj[5].toString());
			
			list.add(dataVM);
		}
		
		return list;
	}
	
	public List<ReinsuranceCostReportPrintDataVM> getDataForReinsuranceCostReportPrint(ReinsuranceCostReportFileVM fileVM) {
		DecimalFormat formatter = new DecimalFormat("#,###");
		List<Object[]> resultData = instClaimDetailDao.getDataForFile(fileVM.getFileQuery());
		List<ReinsuranceCostReportPrintDataVM> list = new ArrayList<>();
		
		for(Object[] obj: resultData) {
			ReinsuranceCostReportPrintDataVM dataVM = new ReinsuranceCostReportPrintDataVM();
			
			if(obj[0] != null)
				dataVM.setPlanName(obj[0].toString());
			if(obj[1] != null)
				dataVM.setPatientLastName(obj[1].toString());
			if(obj[2] != null)
				dataVM.setPatientFirstName(obj[2].toString());
			if(obj[3] != null)
				dataVM.setSubscriberID(obj[3].toString());
			if(obj[4] != null)
				dataVM.setEffectiveDate(obj[4].toString());
			if(obj[5] != null)
				dataVM.setDob(obj[5].toString());
			if(obj[6] != null)
				dataVM.setGender(obj[6].toString());
			if(obj[7] != null)
				dataVM.setPcpName(obj[7].toString());
			
			if(obj[10] != null)
			dataVM.setTotalClaimsCost("$"+formatter.format(Double.parseDouble(obj[10].toString())));
			
			dataVM.setTermedMonth("");
			dataVM.setPolicyPeriod("N/A");
			dataVM.setStatus("N/A");
			
			
			
			list.add(dataVM);
		}
		return list;
	}
	
	public List<PmpmByPracticeReportPrintDataVM> getDataForPmpmByPracticeReportPrint(PmpmByPracticeReportFileVM fileVM) {
		DecimalFormat formatter = new DecimalFormat("#,###");
		List<Object[]> resultData = instClaimDetailDao.getDataForFile(fileVM.getFileQuery());
		List<PmpmByPracticeReportPrintDataVM> list = new ArrayList<>();
		
		for(Object[] obj: resultData) {
			PmpmByPracticeReportPrintDataVM dataVM = new PmpmByPracticeReportPrintDataVM();
			
			if(obj[0] != null)
				dataVM.setProviderName(obj[0].toString());
			if(obj[1] != null)
				dataVM.setTotalCost("$"+formatter.format(Double.parseDouble(obj[1].toString())));
			if(obj[2] != null)
				dataVM.setTotalNumberOfMemberMonth(formatter.format(Double.parseDouble(obj[2].toString())));
			if(obj[3] != null)
				dataVM.setPmpm("$"+formatter.format(Double.parseDouble(obj[3].toString())));
			if(obj[4] != null)
				dataVM.setPmpy("$"+formatter.format(Double.parseDouble(obj[4].toString())));
			if(obj[5] != null)
				dataVM.setTotalPremium("$"+formatter.format(Double.parseDouble(obj[5].toString())));
			if(obj[6] != null)
				dataVM.setIpaPremium("$"+formatter.format(Double.parseDouble(obj[6].toString())));
			if(obj[7] != null)
				dataVM.setDifference("$"+formatter.format(Double.parseDouble(obj[7].toString())));
			
			list.add(dataVM);
		}
		
		return list;
	}
	
	public List<BeneficiariesManagementReportPrintDataVM> getDataForBeneficiariesManagementReportPrint(BeneficiariesManagementReportFileVM fileVM) {
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		List<Object[]> resultData = instClaimDetailDao.getDataForFile(fileVM.getFileQuery());
		List<BeneficiariesManagementReportPrintDataVM> list = new ArrayList<>();
		
		for(Object[] obj: resultData) {
			BeneficiariesManagementReportPrintDataVM dataVM = new BeneficiariesManagementReportPrintDataVM();
			
			if(obj[0] != null)
				dataVM.setPlanName(obj[0].toString());
			if(obj[1] != null)
				dataVM.setHicn(obj[1].toString());
			if(obj[2] != null)
				dataVM.setPatientName(obj[2].toString()+",\n"+obj[3].toString());
			if(obj[4] != null)
				dataVM.setDob(obj[4].toString());
			if(obj[5] != null)
				dataVM.setEligibleMonth(obj[5].toString());
			if(obj[6] != null)
				dataVM.setPcpName(obj[6].toString());
			if(obj[7] != null)
				dataVM.setPcpLocation(obj[7].toString());
			if(obj[8] != null)
				dataVM.setMra(obj[8].toString());
			if(obj[9] != null)
				dataVM.setAddress(obj[9].toString());
			if(obj[17] != null) {
				dataVM.setTotalCost("$"+formatter.format(Double.parseDouble(obj[17].toString())));
			}
			
			dataVM.setTermedMonth("");
			dataVM.setRecentAppointmentDate("");
			dataVM.setNextAppointmentDate("");
			dataVM.setFacilityLocation("");
			dataVM.setPhoneNumber("");
			dataVM.setLastClaimsDate("");
			dataVM.setIcdCode("");
			
			list.add(dataVM);
		}
		
		return list;
	}
	
	
	public List<BeneficiariesManagementByLocationReportPrintDataVM> getDataForBeneficiariesManagementByLocationReportPrint(BeneficiariesManagementByLocationReportFileVM fileVM) {
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		List<Object[]> resultData = instClaimDetailDao.getDataForFile(fileVM.getFileQuery());
		List<BeneficiariesManagementByLocationReportPrintDataVM> list = new ArrayList<>();
		
		for(Object[] obj: resultData) {
			BeneficiariesManagementByLocationReportPrintDataVM dataVM = new BeneficiariesManagementByLocationReportPrintDataVM();
			
			if(obj[0] != null)
				dataVM.setPcpLocation(obj[0].toString());
			if(obj[1] != null)
				dataVM.setMra(obj[1].toString());
			if(obj[2] != null) {
				dataVM.setTotalCost("$"+formatter.format(Double.parseDouble(obj[2].toString())));
			}			
			list.add(dataVM);
		}
		
		return list;
	}
	
	public List<BeneficiariesManagementByClinicReportPrintDataVM> getDataForBeneficiariesManagementByClinicReportPrint(BeneficiariesManagementByClinicReportFileVM fileVM) {
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		List<Object[]> resultData = instClaimDetailDao.getDataForFile(fileVM.getFileQuery());
		List<BeneficiariesManagementByClinicReportPrintDataVM> list = new ArrayList<>();
		
		for(Object[] obj: resultData) {
			BeneficiariesManagementByClinicReportPrintDataVM dataVM = new BeneficiariesManagementByClinicReportPrintDataVM();
			
			if(obj[0] != null)
				dataVM.setClinicName(obj[0].toString());
			if(obj[2] != null) {
				dataVM.setTotalCost("$"+formatter.format(Double.parseDouble(obj[2].toString())));
			}			
			list.add(dataVM);
		}
		
		return list;
	}
	
	public List<BeneficiariesManagementExpandReportPrintDataVM> getDataForBeneficiariesManagementExpandReportPrint(BeneficiariesManagementReportExpandFileVM fileVM) {
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		List<Object[]> resultData = instClaimDetailDao.getDataForFile(fileVM.getFileQuery());
		List<BeneficiariesManagementExpandReportPrintDataVM> list = new ArrayList<>();
		
		for(Object[] obj: resultData) {
			BeneficiariesManagementExpandReportPrintDataVM dataVM = new BeneficiariesManagementExpandReportPrintDataVM();
			
			if(obj[0] != null)
				dataVM.setClaimId(obj[0].toString());
			if(obj[1] != null)
				dataVM.setClaimDate(obj[1].toString());
			if(obj[2] != null)
				dataVM.setClaimType(obj[2].toString());
			if(obj[3] != null)
				dataVM.setClinicName(obj[3].toString());
			if(obj[4] != null)
				dataVM.setPcpName(obj[4].toString());
			if(obj[5] != null)
				dataVM.setDrgCode(obj[5].toString());
			if(obj[6] != null)
				dataVM.setBetosCat(obj[6].toString());
			if(obj[7] != null)
				dataVM.setCost("$"+formatter.format(Double.parseDouble(obj[7].toString())));
			if(obj[8] != null)
				dataVM.setIcdCodes(obj[8].toString());
			dataVM.setHccCodes("");
			
			list.add(dataVM);
		}
		
		return list;
	}
	
	public List<BeneficiariesManagementExpandReportPrintDataVM> getDataForBeneficiariesManagementByDoctorExpandReportPrint(BeneficiariesManagementByDoctorReportExpandFileVM fileVM) {
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		List<Object[]> resultData = instClaimDetailDao.getDataForFile(fileVM.getFileQuery());
		List<BeneficiariesManagementExpandReportPrintDataVM> list = new ArrayList<>();
		
		for(Object[] obj: resultData) {
			BeneficiariesManagementExpandReportPrintDataVM dataVM = new BeneficiariesManagementExpandReportPrintDataVM();
			
			if(obj[0] != null)
				dataVM.setClaimId(obj[0].toString());
			if(obj[1] != null)
				dataVM.setClaimDate(obj[1].toString());
			if(obj[2] != null)
				dataVM.setClaimType(obj[2].toString());
			if(obj[3] != null)
				dataVM.setClinicName(obj[3].toString());
			if(obj[4] != null)
				dataVM.setPcpName(obj[4].toString());
			if(obj[5] != null)
				dataVM.setDrgCode(obj[5].toString());
			if(obj[6] != null)
				dataVM.setBetosCat(obj[6].toString());
			if(obj[7] != null)
				dataVM.setCost("$"+formatter.format(Double.parseDouble(obj[7].toString())));
			if(obj[8] != null)
				dataVM.setIcdCodes(obj[8].toString());
			dataVM.setHccCodes("");
			
			list.add(dataVM);
		}
		
		return list;
	}
	
	public List<BeneficiariesManagementExpandReportPrintDataVM> getDataForBeneficiariesManagementByClinicExpandReportPrint(BeneficiariesManagementByClinicReportExpandFileVM fileVM) {
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		List<Object[]> resultData = instClaimDetailDao.getDataForFile(fileVM.getFileQuery());
		List<BeneficiariesManagementExpandReportPrintDataVM> list = new ArrayList<>();
		
		for(Object[] obj: resultData) {
			BeneficiariesManagementExpandReportPrintDataVM dataVM = new BeneficiariesManagementExpandReportPrintDataVM();
			
			if(obj[0] != null)
				dataVM.setClaimId(obj[0].toString());
			if(obj[1] != null)
				dataVM.setClaimDate(obj[1].toString());
			if(obj[2] != null)
				dataVM.setClaimType(obj[2].toString());
			if(obj[3] != null)
				dataVM.setClinicName(obj[3].toString());
			if(obj[4] != null)
				dataVM.setPcpName(obj[4].toString());
			if(obj[5] != null)
				dataVM.setDrgCode(obj[5].toString());
			else
				dataVM.setDrgCode("");
			if(obj[6] != null)
				dataVM.setBetosCat(obj[6].toString());
			else
				dataVM.setBetosCat("");
			if(obj[7] != null)
				dataVM.setCost("$"+formatter.format(Double.parseDouble(obj[7].toString())));
			if(obj[8] != null)
				dataVM.setIcdCodes(obj[8].toString());
			dataVM.setHccCodes("");
			
			
			list.add(dataVM);
		}
		
		return list;
	}
	
	public List<BeneficiariesManagementExpandReportPrintDataVM> getDataForBeneficiariesManagementByLocationExpandReportPrint(BeneficiariesManagementByLocationReportExpandFileVM fileVM) {
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		List<Object[]> resultData = instClaimDetailDao.getDataForFile(fileVM.getFileQuery());
		List<BeneficiariesManagementExpandReportPrintDataVM> list = new ArrayList<>();
		
		for(Object[] obj: resultData) {
			BeneficiariesManagementExpandReportPrintDataVM dataVM = new BeneficiariesManagementExpandReportPrintDataVM();
			
			if(obj[0] != null)
				dataVM.setClaimId(obj[0].toString());
			if(obj[1] != null)
				dataVM.setClaimDate(obj[1].toString());
			if(obj[2] != null)
				dataVM.setClaimType(obj[2].toString());
			if(obj[3] != null)
				dataVM.setClinicName(obj[3].toString());
			if(obj[4] != null)
				dataVM.setPcpLocation(obj[4].toString());
			if(obj[5] != null)
				dataVM.setDrgCode(obj[5].toString());
			else
				dataVM.setDrgCode("");
			if(obj[6] != null)
				dataVM.setBetosCat(obj[6].toString());
			else
				dataVM.setBetosCat("");
			if(obj[7] != null)
				dataVM.setCost("$"+formatter.format(Double.parseDouble(obj[7].toString())));
			if(obj[8] != null)
				dataVM.setIcdCodes(obj[8].toString());
			dataVM.setHccCodes("");
		
			
			list.add(dataVM);
		}
		
		return list;
	}
	
	public List<BeneficiariesManagementByDoctorPrintDataVM> getDataForBeneficiariesManagementByDoctorPrint(BeneficiariesManagementByDoctorFileVM fileVM) {
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		List<Object[]> resultData = instClaimDetailDao.getDataForFile(fileVM.getFileQuery());
		List<BeneficiariesManagementByDoctorPrintDataVM> list = new ArrayList<>();
		
		for(Object[] obj: resultData) {
			BeneficiariesManagementByDoctorPrintDataVM dataVM = new BeneficiariesManagementByDoctorPrintDataVM();
			
			if(obj[0] != null)
				dataVM.setPcpName(obj[0].toString());
			if(obj[1] != null)
				dataVM.setPcpLocation(obj[1].toString());
			if(obj[2] != null)
				dataVM.setAverageMra(obj[2].toString());
			if(obj[3] != null)
				dataVM.setPcpId(obj[3].toString());
			if(obj[4] != null)
				dataVM.setSpecCost(obj[4].toString());
			if(obj[5] != null)
				dataVM.setPcpCap(obj[5].toString());
			if(obj[6] != null)
				dataVM.setReinsurancePrem(obj[6].toString());
			if(obj[7] != null)
				dataVM.setInstClaims(obj[7].toString());
			if(obj[8] != null)
				dataVM.setProfClaims(obj[8].toString());
			if(obj[9] != null)
				dataVM.setRxClaims(obj[9].toString());
			if(obj[10] != null)
				dataVM.setTotalCost("$"+formatter.format(Double.parseDouble(obj[10].toString())));
			
			list.add(dataVM);
		}
		
		return list;
	}
	
	public List<PmpmByPracticeExpandReportPrintDataVM> getDataForPmpmByPracticeExpandReportPrint(PmpmByPracticeExpandReportFileVM fileVM) {
		DecimalFormat formatter = new DecimalFormat("#,###");
		List<Object[]> resultData = instClaimDetailDao.getDataForFile(fileVM.getFileQuery());
		List<PmpmByPracticeExpandReportPrintDataVM> list = new ArrayList<>();
		
		for(Object[] obj: resultData) {
			PmpmByPracticeExpandReportPrintDataVM dataVM = new PmpmByPracticeExpandReportPrintDataVM();
			
			if(obj[0] != null)
				dataVM.setPatientName(obj[0].toString()+", "+obj[1].toString());
			if(obj[2] != null)
				dataVM.setPcpName(obj[2].toString());
			if(obj[3] != null)
				dataVM.setPcpLocation(obj[3].toString());
			if(obj[4] != null)
				dataVM.setMra(obj[4].toString());
			dataVM.setCost("");
			dataVM.setClaimType("");
			
			list.add(dataVM);
		}
		
		return list;
	}
	
	public List<MembershipManagementPrintDataVM> getMembershipManagementPrint(MembershipManagementFileVM fileVM) {
		DecimalFormat formatter = new DecimalFormat("#,###");
		List<Object[]> resultData = instClaimDetailDao.getDataForFile(fileVM.getFileQuery());
		List<MembershipManagementPrintDataVM> list = new ArrayList<>();
		
		for(Object[] obj: resultData) {
			MembershipManagementPrintDataVM dataVM = new MembershipManagementPrintDataVM();
			
			if(obj[0] != null)
				dataVM.setPlanName(obj[0].toString());
			if(obj[1] != null)
				dataVM.setMedicareId(obj[1].toString());
			if(obj[2] != null)
				dataVM.setInsuranceId(obj[2].toString());
			if(obj[3] != null)
				dataVM.setPatientName(obj[3].toString());
			if(obj[4] != null)
				dataVM.setPatientDob(obj[4].toString());
			if(obj[5] != null)
				dataVM.setAssignedPcp(obj[5].toString());
			if(obj[6] != null)
				dataVM.setPcpLocation(obj[6].toString());
			
			if(fileVM.getPatientType().equals("Termed")) {
				int currentYear = 0,prevYear = 0,currentMonth = 0, prevMonth = 0;
				Date date = new Date();
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				currentYear = cal.get(Calendar.YEAR);
				prevYear = cal.get(Calendar.YEAR);
				currentMonth = cal.get(Calendar.MONTH);
				prevMonth = cal.get(Calendar.MONTH)-1;
				
				if(currentMonth == 0) {
					currentYear--;
					prevYear--;
					currentMonth = 12;
					prevMonth = 11;
				}
				if(currentMonth == 1) {
					prevYear--;
					currentMonth = 1;
					prevMonth = 12;
				}
				String currentMonthStr = "",prevMonthStr = "";
				if(currentMonth > 9) {
					currentMonthStr = currentYear+"-"+currentMonth+"-01";
					prevMonthStr = prevYear+"-"+prevMonth+"-01";
				} else {
					currentMonthStr = currentYear+"-0"+currentMonth+"-01";
					prevMonthStr = prevYear+"-0"+prevMonth+"-01";
				}
				
				/*SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date convertedDate = null;
				try {
					convertedDate = dateFormat.parse(prevMonthStr);
				} catch (ParseException e) {e.printStackTrace();}
				
				Calendar c = Calendar.getInstance();
				c.setTime(convertedDate);
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
				dataVM.setIpaEffectiveDate(dateFormat.format(c.getTime()));*/
				dataVM.setIpaEffectiveDate(prevMonthStr);
			} else {
				dataVM.setIpaEffectiveDate(obj[7].toString());
			}
			
			if(obj[8] != null && !obj[8].toString().equals(""))
				dataVM.setMra(obj[8].toString());
			else
				dataVM.setMra("0");
			if(obj[16] != null)
				dataVM.setTotalPatientCost("$"+formatter.format(Double.parseDouble(obj[16].toString())));
			
			list.add(dataVM);
		}
		
		return list;
	}
	
	public List<AdmissionsReportExpandPrintDataVM> getDataForAdmissionsReportExpandPrint(AdmissionsReportExpandFileVM fileVM) {
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		List<Object[]> resultData = instClaimDetailDao.getDataForFile(fileVM.getFileQuery());
		List<AdmissionsReportExpandPrintDataVM> list = new ArrayList<>();
		
		for(Object[] obj: resultData) {
			AdmissionsReportExpandPrintDataVM dataVM = new AdmissionsReportExpandPrintDataVM();
			
			if(obj[0] != null)
				dataVM.setClaimId(obj[0].toString());
			if(obj[1] != null)
				dataVM.setClaimDate(obj[1].toString());
			if(obj[2] != null)
				dataVM.setClaimType(obj[2].toString());
			if(obj[3] != null)
				dataVM.setClinicName(obj[3].toString());
			if(obj[4] != null)
				dataVM.setPcpName(obj[4].toString());
			if(obj[5] != null)
				dataVM.setDrgCode(obj[5].toString());
			if(obj[6] != null)
				dataVM.setBetosCat(obj[6].toString());
			if(obj[7] != null)
				dataVM.setCost("$"+formatter.format(Double.parseDouble(obj[7].toString())));
			dataVM.setIcdCodes("");
			dataVM.setHccCodes("");
			list.add(dataVM);
		}
		
		return list;
	}
	
	public List<DuplicateClaimsExpandPrintDataVM> getDataForDuplicateClaimsExpandPrint(DuplicateClaimsExpandFileVM fileVM) {
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		List<Object[]> resultData = instClaimDetailDao.getDataForFile(fileVM.getFileQuery());
		List<DuplicateClaimsExpandPrintDataVM> list = new ArrayList<>();
		
		for(Object[] obj: resultData) {
			DuplicateClaimsExpandPrintDataVM dataVM = new DuplicateClaimsExpandPrintDataVM();
			
			if(obj[0] != null)
				dataVM.setClaimId(obj[0].toString());
			if(obj[1] != null)
				dataVM.setClaimDate(obj[1].toString());
			if(obj[2] != null)
				dataVM.setClaimType(obj[2].toString());
			if(obj[3] != null)
				dataVM.setClinicName(obj[3].toString());
			if(obj[4] != null)
				dataVM.setProviderName(obj[4].toString());
			if(obj[5] != null)
				dataVM.setBetosCat(obj[5].toString());
			if(obj[6] != null)
				dataVM.setDrgCode(obj[6].toString());
			if(obj[7] != null) {
				if(obj[7].toString().equals("null"))
					dataVM.setIcdCodes("");
				else
				dataVM.setIcdCodes(obj[7].toString());
			}
			if(obj[8] != null) {
				if(obj[8].toString().equals("0.0")) {
					dataVM.setCost("$0");
				} else {
					dataVM.setCost("$"+formatter.format(Double.parseDouble(obj[8].toString())));
				}
			}
			dataVM.setHccCodes("");
			list.add(dataVM);
		}
		
		return list;
	}
	
	public List<SpecialistComparisonPrintDataVM> getDataForSpecialistComparisonPrint(SpecialistComparisonReportFileVM fileVM) {
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		List<Object[]> resultData = instClaimDetailDao.getDataForFile(fileVM.getFileQuery());
		List<SpecialistComparisonPrintDataVM> list = new ArrayList<>();
		
		for(Object[] obj: resultData) {
			SpecialistComparisonPrintDataVM dataVM = new SpecialistComparisonPrintDataVM();
			
			if(obj[0] != null)
				dataVM.setSpecialityCode(obj[0].toString());
			if(obj[1] != null)
				dataVM.setNumberOfClaims(obj[1].toString());
			if(obj[2] != null)
				dataVM.setNumberOfBeneficiaries(obj[2].toString());
			if(obj[3] != null)
				dataVM.setCostPerClaim("$"+formatter.format(Double.parseDouble(obj[3].toString())));
			if(obj[4] != null)
				dataVM.setCostPerBeneficiary("$"+formatter.format(Double.parseDouble(obj[4].toString())));
			if(obj[5] != null)
				dataVM.setTotalCost("$"+formatter.format(Double.parseDouble(obj[5].toString())));
			if(obj[6] != null)
				dataVM.setNumberOfPcp(obj[6].toString());
			list.add(dataVM);
		}
		
		return list;
	}
	
	public List<SpecialistComparisonExpandPrintDataVM> getDataForSpecialistComparisonExpandPrint(SpecialistComparisonExpandReportFileVM fileVM) {
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		List<Object[]> resultData = instClaimDetailDao.getDataForFile(fileVM.getFileQuery());
		List<SpecialistComparisonExpandPrintDataVM> list = new ArrayList<>();
		
		for(Object[] obj: resultData) {
			SpecialistComparisonExpandPrintDataVM dataVM = new SpecialistComparisonExpandPrintDataVM();
			
			if(obj[0] != null)
				dataVM.setPracticeName(obj[0].toString());
			if(obj[1] != null)
				dataVM.setSpecialityType(obj[1].toString());
			if(obj[2] != null)
				dataVM.setNumberOfClaims(obj[2].toString());
			if(obj[4] != null)
				dataVM.setAverageCostPerClaim("$"+formatter.format(Double.parseDouble(obj[4].toString())));
			if(obj[5] != null)
				dataVM.setCost("$"+formatter.format(Double.parseDouble(obj[5].toString())));
			list.add(dataVM);
		}
		
		return list;
	}
	
	public List<SummaryReportPrintDataVM> getDataForSummaryReportPrint(SummaryReportFileVM fileVM) {
		DecimalFormat formatter = new DecimalFormat("#,###");
		List<Object[]> resultData = instClaimDetailDao.getDataForFile(fileVM.getFileQuery());
		List<SummaryReportPrintDataVM> list = new ArrayList<>();
		
		for(Object[] obj: resultData) {
			SummaryReportPrintDataVM dataVM = new SummaryReportPrintDataVM();
			
			if(obj[0] != null)
				dataVM.setMonth(obj[0].toString());
			if(obj[1] != null)
				dataVM.setMembers(obj[1].toString());
			if(obj[2] != null)
				dataVM.setMaPremium("$"+formatter.format(Double.parseDouble(obj[2].toString())));
			if(obj[3] != null)
				dataVM.setPartDPremium("$"+formatter.format(Double.parseDouble(obj[3].toString())));
			if(obj[4] != null)
				dataVM.setTotalPremium("$"+formatter.format(Double.parseDouble(obj[4].toString())));
			if(obj[5] != null)
				dataVM.setIpaPremium("$"+formatter.format(Double.parseDouble(obj[5].toString())));
			if(obj[6] != null)
				dataVM.setPcpCap("$"+formatter.format(Double.parseDouble(obj[6].toString())));
			if(obj[7] != null)
				dataVM.setSpecCost("$"+formatter.format(Double.parseDouble(obj[7].toString())));
			if(obj[8] != null)
				dataVM.setProfClaims("$"+formatter.format(Double.parseDouble(obj[8].toString())));
			if(obj[9] != null)
				dataVM.setInstClaims("$"+formatter.format(Double.parseDouble(obj[9].toString())));
			if(obj[10] != null)
				dataVM.setRxClaims("$"+formatter.format(Double.parseDouble(obj[10].toString())));
			if(obj[11] != null)
				dataVM.setIbnrDollars("$"+formatter.format(Double.parseDouble(obj[11].toString())));
			if(obj[12] != null)
				dataVM.setReinsurancePremium("$"+formatter.format(Double.parseDouble(obj[12].toString())));
			if(obj[13] != null)
				dataVM.setTotalExpenses("$"+formatter.format(Double.parseDouble(obj[13].toString())));
			
			list.add(dataVM);
		}
		
		return list;
	}
	
	public void generatePDF(FileVM fileVM, OutputStream os) throws SQLException, IOException, DocumentException {
		
		List<ReportDataVM> list = getDataForFile(fileVM);
		
		File file = new File("Data export-Claims Search.pdf");
		 Document document = new Document();
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();
	        
	        int tableColumnSize = 1;
	        if(fileVM.showProviderName)
	        	tableColumnSize++;
	        if(fileVM.showMedicareId)
	        	tableColumnSize++;
	        if(fileVM.showPatientName)
	        	tableColumnSize++;
	        if(fileVM.showICDCode)
	        	tableColumnSize++;
	        if(fileVM.showHCCCodes)
	        	tableColumnSize++;
	        if(fileVM.showTermedMonth)
	        	tableColumnSize++;
	        if(fileVM.showEligibleMonth)
	        	tableColumnSize++;
	        if(fileVM.showCost)
	        	tableColumnSize++;
	        
	        PdfPTable table = new PdfPTable(tableColumnSize);//columns
	        
	        @SuppressWarnings("deprecation")
			BaseColor myColor = WebColors.getRGBColor("#2D4154");
	        
	        Font font = new Font(FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
	        PdfPCell cell1 = new PdfPCell(new Paragraph("Plan Name", font));
	        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
	        cell1.setVerticalAlignment(Element.ALIGN_TOP);
	        cell1.setBackgroundColor(myColor);
	        cell1.setBorderColor(BaseColor.WHITE);
	        cell1.setBorderWidth(0.1f);
	        table.addCell(cell1);
	        if(fileVM.showProviderName) {
	        	PdfPCell cell2 = new PdfPCell(new Paragraph("Provider Name", font));
	        	cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell2.setVerticalAlignment(Element.ALIGN_TOP);
		        cell2.setBackgroundColor(myColor);
		        cell2.setBorderColor(BaseColor.WHITE);
		        cell2.setBorderWidth(0.1f);
	        	table.addCell(cell2);
	        }
	        if(fileVM.showMedicareId) {
	        	PdfPCell cell3 = new PdfPCell(new Paragraph("Medicare ID", font));
	        	cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell3.setVerticalAlignment(Element.ALIGN_TOP);
		        cell3.setBackgroundColor(myColor);
		        cell3.setBorderColor(BaseColor.WHITE);
		        cell3.setBorderWidth(0.1f);
	        	table.addCell(cell3);
	        }
	        if(fileVM.showPatientName) {
	        	PdfPCell cell4 = new PdfPCell(new Paragraph("Patient Name", font));
	        	cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell4.setVerticalAlignment(Element.ALIGN_TOP);
		        cell4.setBackgroundColor(myColor);
		        cell4.setBorderColor(BaseColor.WHITE);
		        cell4.setBorderWidth(0.1f);
	        	table.addCell(cell4);
	        }
	        if(fileVM.showICDCode) {
	        	PdfPCell cell5 = new PdfPCell(new Paragraph("ICD9/10 Code", font));
	        	cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell5.setVerticalAlignment(Element.ALIGN_TOP);
		        cell5.setBackgroundColor(myColor);
		        cell5.setBorderColor(BaseColor.WHITE);
		        cell5.setBorderWidth(0.1f);
	        	table.addCell(cell5);
	        }
	        if(fileVM.showHCCCodes) {
	        	PdfPCell cell6 = new PdfPCell(new Paragraph("HCC Codes", font));
	        	cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell6.setVerticalAlignment(Element.ALIGN_TOP);
		        cell6.setBackgroundColor(myColor);
		        cell6.setBorderColor(BaseColor.WHITE);
		        cell6.setBorderWidth(0.1f);
	        	table.addCell(cell6);
	        }
	        if(fileVM.showTermedMonth) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("Termed Month", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        if(fileVM.showEligibleMonth) {
	        	PdfPCell cell8 = new PdfPCell(new Paragraph("Eligible Month", font));
	        	cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell8.setVerticalAlignment(Element.ALIGN_TOP);
		        cell8.setBackgroundColor(myColor);
		        cell8.setBorderColor(BaseColor.WHITE);
		        cell8.setBorderWidth(0.1f);
	        	table.addCell(cell8);
	        }
	        if(fileVM.showCost) {
	        	PdfPCell cell9 = new PdfPCell(new Paragraph("Cost", font));
	        	cell9.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell9.setVerticalAlignment(Element.ALIGN_TOP);
		        cell9.setBackgroundColor(myColor);
		        cell9.setBorderColor(BaseColor.WHITE);
		        cell9.setBorderWidth(0.1f);
	        	table.addCell(cell9);
	        }
	        table.setHeaderRows(1);
	        
	        BaseColor oddRowColor = WebColors.getRGBColor("#F3F3F3");
	        
	        int count = 1; //table rows
	        for(ReportDataVM vm: list) {
	        	Font rowFont = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
		        PdfPCell rowCell1 = new PdfPCell(new Paragraph(vm.getPlanName(), rowFont));
		        rowCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
		        rowCell1.setVerticalAlignment(Element.ALIGN_TOP);
		        if(count%2 > 0)
		        rowCell1.setBackgroundColor(oddRowColor);
		        rowCell1.setBorderColor(BaseColor.WHITE);
		        rowCell1.setBorderWidth(0.1f);
		        table.addCell(rowCell1);
		        
		        if(fileVM.showProviderName) {
		        	PdfPCell rowCell2 = new PdfPCell(new Paragraph(vm.getProviderName(), rowFont));
			        rowCell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell2.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell2.setBackgroundColor(oddRowColor);
			        rowCell2.setBorderColor(BaseColor.WHITE);
			        rowCell2.setBorderWidth(0.1f);
			        table.addCell(rowCell2);
		        }
		        if(fileVM.showMedicareId) {
		        	PdfPCell rowCell3 = new PdfPCell(new Paragraph(vm.getMedicareId(), rowFont));
			        rowCell3.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell3.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell3.setBackgroundColor(oddRowColor);
			        rowCell3.setBorderColor(BaseColor.WHITE);
			        rowCell3.setBorderWidth(0.1f);
			        table.addCell(rowCell3);
		        }
		        if(fileVM.showPatientName) {
		        	PdfPCell rowCell4 = new PdfPCell(new Paragraph(vm.getPatientName(), rowFont));
			        rowCell4.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell4.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell4.setBackgroundColor(oddRowColor);
			        rowCell4.setBorderColor(BaseColor.WHITE);
			        rowCell4.setBorderWidth(0.1f);
			        table.addCell(rowCell4);
		        }
		        if(fileVM.showICDCode) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getIcdCode(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showHCCCodes) {
		        	PdfPCell rowCell6 = new PdfPCell(new Paragraph(vm.getHccCodes(), rowFont));
			        rowCell6.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell6.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell6.setBackgroundColor(oddRowColor);
			        rowCell6.setBorderColor(BaseColor.WHITE);
			        rowCell6.setBorderWidth(0.1f);
			        table.addCell(rowCell6);
		        }
		        if(fileVM.showTermedMonth) {
		        	PdfPCell rowCell7 = new PdfPCell(new Paragraph(vm.getTermedMonth(), rowFont));
			        rowCell7.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell7.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell7.setBackgroundColor(oddRowColor);
			        rowCell7.setBorderColor(BaseColor.WHITE);
			        rowCell7.setBorderWidth(0.1f);
			        table.addCell(rowCell7);
		        }
		        if(fileVM.showEligibleMonth) {
		        	PdfPCell rowCell8 = new PdfPCell(new Paragraph(vm.getEligibleMonth(), rowFont));
			        rowCell8.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell8.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell8.setBackgroundColor(oddRowColor);
			        rowCell8.setBorderColor(BaseColor.WHITE);
			        rowCell8.setBorderWidth(0.1f);
			        table.addCell(rowCell8);
		        }
		        if(fileVM.showCost) {
		        	PdfPCell rowCell9 = new PdfPCell(new Paragraph(vm.getCost(), rowFont));
			        rowCell9.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell9.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell9.setBackgroundColor(oddRowColor);
			        rowCell9.setBorderColor(BaseColor.WHITE);
			        rowCell9.setBorderWidth(0.1f);
			        table.addCell(rowCell9);
		        }
		        count++;
	        }
	        
	        table.setWidthPercentage(100);
	        document.add(table);
	        document.close();
	        InputStream is = new FileInputStream(file);
	        IOUtils.copy(is, os);
	}
	

	public void generateDuplicateClaimPDF(DuplicateClaimFileVM fileVM, OutputStream os) throws SQLException, IOException, DocumentException {
		
		List<DuplicateClaimsReportVM> list = getDataForDuplicateClaimsXLFile(fileVM);
		
		File file = new File("Data export-Duplicate Claims.pdf");
		 Document document = new Document();
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();
	        
	        int tableColumnSize = 0;
	        if(fileVM.showSubscriberId_duplicate)
	        	tableColumnSize++;
	        if(fileVM.showPlanName_duplicate)
	        	tableColumnSize++;
	        if(fileVM.showPatientName_duplicate)
	        	tableColumnSize++;
	        if(fileVM.showPcp_duplicate)
	        	tableColumnSize++;
	        if(fileVM.showEligibleMonth_duplicate)
	        	tableColumnSize++;
	        if(fileVM.showTermedMonth_duplicate)
	        	tableColumnSize++;
	        if(fileVM.showClaimDate_duplicate)
	        	tableColumnSize++;
	        if(fileVM.showDuplicativeCost_duplicate)
	        	tableColumnSize++;
	        
	        PdfPTable table = new PdfPTable(tableColumnSize);//columns
	        
	        @SuppressWarnings("deprecation")
			BaseColor myColor = WebColors.getRGBColor("#2D4154");
	        
	        Font font = new Font(FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
	        if(fileVM.showSubscriberId_duplicate) {
		        PdfPCell cell1 = new PdfPCell(new Paragraph("HICN/Subscriber ID", font));
		        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell1.setVerticalAlignment(Element.ALIGN_TOP);
		        cell1.setBackgroundColor(myColor);
		        cell1.setBorderColor(BaseColor.WHITE);
		        cell1.setBorderWidth(0.1f);
		        table.addCell(cell1);
	        }
	        if(fileVM.showPlanName_duplicate) {
	        	PdfPCell cell2 = new PdfPCell(new Paragraph("Plan Name", font));
	        	cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell2.setVerticalAlignment(Element.ALIGN_TOP);
		        cell2.setBackgroundColor(myColor);
		        cell2.setBorderColor(BaseColor.WHITE);
		        cell2.setBorderWidth(0.1f);
	        	table.addCell(cell2);
	        }
	        if(fileVM.showPatientName_duplicate) {
	        	PdfPCell cell3 = new PdfPCell(new Paragraph("Patient Name", font));
	        	cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell3.setVerticalAlignment(Element.ALIGN_TOP);
		        cell3.setBackgroundColor(myColor);
		        cell3.setBorderColor(BaseColor.WHITE);
		        cell3.setBorderWidth(0.1f);
	        	table.addCell(cell3);
	        }
	        if(fileVM.showPcp_duplicate) {
	        	PdfPCell cell4 = new PdfPCell(new Paragraph("PCP", font));
	        	cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell4.setVerticalAlignment(Element.ALIGN_TOP);
		        cell4.setBackgroundColor(myColor);
		        cell4.setBorderColor(BaseColor.WHITE);
		        cell4.setBorderWidth(0.1f);
	        	table.addCell(cell4);
	        }
	        if(fileVM.showEligibleMonth_duplicate) {
	        	PdfPCell cell5 = new PdfPCell(new Paragraph("Eligible Month", font));
	        	cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell5.setVerticalAlignment(Element.ALIGN_TOP);
		        cell5.setBackgroundColor(myColor);
		        cell5.setBorderColor(BaseColor.WHITE);
		        cell5.setBorderWidth(0.1f);
	        	table.addCell(cell5);
	        }
	        if(fileVM.showTermedMonth_duplicate) {
	        	PdfPCell cell6 = new PdfPCell(new Paragraph("Termed Month", font));
	        	cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell6.setVerticalAlignment(Element.ALIGN_TOP);
		        cell6.setBackgroundColor(myColor);
		        cell6.setBorderColor(BaseColor.WHITE);
		        cell6.setBorderWidth(0.1f);
	        	table.addCell(cell6);
	        }
	        if(fileVM.showClaimDate_duplicate) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("Claim Date", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        if(fileVM.showDuplicativeCost_duplicate) {
	        	PdfPCell cell8 = new PdfPCell(new Paragraph("Duplicative Cost", font));
	        	cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell8.setVerticalAlignment(Element.ALIGN_TOP);
		        cell8.setBackgroundColor(myColor);
		        cell8.setBorderColor(BaseColor.WHITE);
		        cell8.setBorderWidth(0.1f);
	        	table.addCell(cell8);
	        }
	        
	        table.setHeaderRows(1);
	        
	        BaseColor oddRowColor = WebColors.getRGBColor("#F3F3F3");
	        
	        int count = 1; //table rows
	        for(DuplicateClaimsReportVM vm: list) {
	        	Font rowFont = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
	        	if(fileVM.showSubscriberId_duplicate) {
			        PdfPCell rowCell1 = new PdfPCell(new Paragraph(vm.getSubscriberId(), rowFont));
			        rowCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell1.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell1.setBackgroundColor(oddRowColor);
			        rowCell1.setBorderColor(BaseColor.WHITE);
			        rowCell1.setBorderWidth(0.1f);
			        table.addCell(rowCell1);
	        	}
	        	
		        if(fileVM.showPlanName_duplicate) {
		        	PdfPCell rowCell2 = new PdfPCell(new Paragraph(vm.getPlanName(), rowFont));
			        rowCell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell2.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell2.setBackgroundColor(oddRowColor);
			        rowCell2.setBorderColor(BaseColor.WHITE);
			        rowCell2.setBorderWidth(0.1f);
			        table.addCell(rowCell2);
		        }
		        if(fileVM.showPatientName_duplicate) {
		        	PdfPCell rowCell3 = new PdfPCell(new Paragraph(vm.getPatientName(), rowFont));
			        rowCell3.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell3.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell3.setBackgroundColor(oddRowColor);
			        rowCell3.setBorderColor(BaseColor.WHITE);
			        rowCell3.setBorderWidth(0.1f);
			        table.addCell(rowCell3);
		        }
		        if(fileVM.showPcp_duplicate) {
		        	PdfPCell rowCell4 = new PdfPCell(new Paragraph(vm.getPcp(), rowFont));
			        rowCell4.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell4.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell4.setBackgroundColor(oddRowColor);
			        rowCell4.setBorderColor(BaseColor.WHITE);
			        rowCell4.setBorderWidth(0.1f);
			        table.addCell(rowCell4);
		        }
		        if(fileVM.showEligibleMonth_duplicate) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getEligibleMonth(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showTermedMonth_duplicate) {
		        	PdfPCell rowCell6 = new PdfPCell(new Paragraph(vm.getTermedMonth(), rowFont));
			        rowCell6.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell6.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell6.setBackgroundColor(oddRowColor);
			        rowCell6.setBorderColor(BaseColor.WHITE);
			        rowCell6.setBorderWidth(0.1f);
			        table.addCell(rowCell6);
		        }
		        if(fileVM.showClaimDate_duplicate) {
		        	PdfPCell rowCell7 = new PdfPCell(new Paragraph(vm.getClaimDate(), rowFont));
			        rowCell7.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell7.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell7.setBackgroundColor(oddRowColor);
			        rowCell7.setBorderColor(BaseColor.WHITE);
			        rowCell7.setBorderWidth(0.1f);
			        table.addCell(rowCell7);
		        }
		        if(fileVM.showDuplicativeCost_duplicate) {
		        	PdfPCell rowCell8 = new PdfPCell(new Paragraph(vm.getDuplicativeCost(), rowFont));
			        rowCell8.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell8.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell8.setBackgroundColor(oddRowColor);
			        rowCell8.setBorderColor(BaseColor.WHITE);
			        rowCell8.setBorderWidth(0.1f);
			        table.addCell(rowCell8);
		        }
		        
		        count++;
	        }
	        
	        table.setWidthPercentage(100);
	        document.add(table);
	        document.close();
	        InputStream is = new FileInputStream(file);
	        IOUtils.copy(is, os);
	}
	
	public void generateAdmissionsReportPDF(AdmissionsReportFileVM fileVM, OutputStream os) throws SQLException, IOException, DocumentException {
		
		List<AdmissionsReportVM> list = getDataForAdmissionsReportXL(fileVM);
		
		File file = new File("Data export-Admissions Report.pdf");
		 Document document = new Document();
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();
	        
	        int tableColumnSize = 0;
	        if(fileVM.showPatientName_admissions)
	        	tableColumnSize++;
	        if(fileVM.showSubscriberId_admissions)
	        	tableColumnSize++;
	        if(fileVM.showPcpName_admissions)
	        	tableColumnSize++;
	        if(fileVM.showEligibleMonth_admissions)
	        	tableColumnSize++;
	        if(fileVM.showTotalNoOfAdmissions_admissions)
	        	tableColumnSize++;
	        if(fileVM.showTotalCost_admissions)
	        	tableColumnSize++;
	        
	        PdfPTable table = new PdfPTable(tableColumnSize);//columns
	        
	        @SuppressWarnings("deprecation")
			BaseColor myColor = WebColors.getRGBColor("#2D4154");
	        
	        Font font = new Font(FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
	        if(fileVM.showPatientName_admissions) {
	        	PdfPCell cell3 = new PdfPCell(new Paragraph("Patient Name", font));
	        	cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell3.setVerticalAlignment(Element.ALIGN_TOP);
		        cell3.setBackgroundColor(myColor);
		        cell3.setBorderColor(BaseColor.WHITE);
		        cell3.setBorderWidth(0.1f);
	        	table.addCell(cell3);
	        }
	        if(fileVM.showSubscriberId_admissions) {
		        PdfPCell cell1 = new PdfPCell(new Paragraph("HICN/Subscriber ID", font));
		        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell1.setVerticalAlignment(Element.ALIGN_TOP);
		        cell1.setBackgroundColor(myColor);
		        cell1.setBorderColor(BaseColor.WHITE);
		        cell1.setBorderWidth(0.1f);
		        table.addCell(cell1);
	        }
	        if(fileVM.showPcpName_admissions) {
	        	PdfPCell cell4 = new PdfPCell(new Paragraph("PCP Name", font));
	        	cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell4.setVerticalAlignment(Element.ALIGN_TOP);
		        cell4.setBackgroundColor(myColor);
		        cell4.setBorderColor(BaseColor.WHITE);
		        cell4.setBorderWidth(0.1f);
	        	table.addCell(cell4);
	        }
	        if(fileVM.showEligibleMonth_admissions) {
	        	PdfPCell cell5 = new PdfPCell(new Paragraph("Eligible Month", font));
	        	cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell5.setVerticalAlignment(Element.ALIGN_TOP);
		        cell5.setBackgroundColor(myColor);
		        cell5.setBorderColor(BaseColor.WHITE);
		        cell5.setBorderWidth(0.1f);
	        	table.addCell(cell5);
	        }
	        if(fileVM.showTotalNoOfAdmissions_admissions) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("Total Number Of Admissions", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        if(fileVM.showTotalCost_admissions) {
	        	PdfPCell cell8 = new PdfPCell(new Paragraph("Total Cost", font));
	        	cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell8.setVerticalAlignment(Element.ALIGN_TOP);
		        cell8.setBackgroundColor(myColor);
		        cell8.setBorderColor(BaseColor.WHITE);
		        cell8.setBorderWidth(0.1f);
	        	table.addCell(cell8);
	        }
	        
	        table.setHeaderRows(1);
	        
	        BaseColor oddRowColor = WebColors.getRGBColor("#F3F3F3");
	        
	        int count = 1; //table rows
	        for(AdmissionsReportVM vm: list) {
	        	Font rowFont = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
	        	if(fileVM.showPatientName_admissions) {
		        	PdfPCell rowCell3 = new PdfPCell(new Paragraph(vm.getPatientName(), rowFont));
			        rowCell3.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell3.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell3.setBackgroundColor(oddRowColor);
			        rowCell3.setBorderColor(BaseColor.WHITE);
			        rowCell3.setBorderWidth(0.1f);
			        table.addCell(rowCell3);
		        }
	        	if(fileVM.showSubscriberId_admissions) {
			        PdfPCell rowCell1 = new PdfPCell(new Paragraph(vm.getSubscriberId(), rowFont));
			        rowCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell1.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell1.setBackgroundColor(oddRowColor);
			        rowCell1.setBorderColor(BaseColor.WHITE);
			        rowCell1.setBorderWidth(0.1f);
			        table.addCell(rowCell1);
	        	}
		        if(fileVM.showPcpName_admissions) {
		        	PdfPCell rowCell4 = new PdfPCell(new Paragraph(vm.getPcpName(), rowFont));
			        rowCell4.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell4.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell4.setBackgroundColor(oddRowColor);
			        rowCell4.setBorderColor(BaseColor.WHITE);
			        rowCell4.setBorderWidth(0.1f);
			        table.addCell(rowCell4);
		        }
		        if(fileVM.showEligibleMonth_admissions) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getEligibleMonth(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showTotalNoOfAdmissions_admissions) {
		        	PdfPCell rowCell6 = new PdfPCell(new Paragraph(vm.getTotalNoOfAdmissions(), rowFont));
			        rowCell6.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell6.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell6.setBackgroundColor(oddRowColor);
			        rowCell6.setBorderColor(BaseColor.WHITE);
			        rowCell6.setBorderWidth(0.1f);
			        table.addCell(rowCell6);
		        }
		        if(fileVM.showTotalCost_admissions) {
		        	PdfPCell rowCell8 = new PdfPCell(new Paragraph(vm.getTotalCost(), rowFont));
			        rowCell8.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell8.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell8.setBackgroundColor(oddRowColor);
			        rowCell8.setBorderColor(BaseColor.WHITE);
			        rowCell8.setBorderWidth(0.1f);
			        table.addCell(rowCell8);
		        }
		        
		        count++;
	        }
	        
	        table.setWidthPercentage(100);
	        document.add(table);
	        document.close();
	        InputStream is = new FileInputStream(file);
	        IOUtils.copy(is, os);
	}
	
	public void generatePatientVisitReportPDF(PatientVisitReportFileVM fileVM, OutputStream os) throws SQLException, IOException, DocumentException {
		
		List<PatientVisitReportPrintDataVM> list = getDataForPatientVisitReportPrint(fileVM);
		
		File file = new File("Data export-Patient Visit Report.pdf");
		 Document document = new Document();
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();
	        
	        int tableColumnSize = 0;
	        if(fileVM.showPatientName_patientVisit)
	        	tableColumnSize++;
	        if(fileVM.showHicn_patientVisit)
	        	tableColumnSize++;
	        if(fileVM.showPcpName_patientVisit)
	        	tableColumnSize++;
	        if(fileVM.showTermedMonth_patientVisit)
	        	tableColumnSize++;
	        if(fileVM.showIpaEffectiveDate_patientVisit)
	        	tableColumnSize++;
	        if(fileVM.showTotalErVisits_patientVisit)
	        	tableColumnSize++;
	        if(fileVM.showTotalCost_patientVisit)
	        	tableColumnSize++;
	        
	        PdfPTable table = new PdfPTable(tableColumnSize);//columns
	        
	        @SuppressWarnings("deprecation")
			BaseColor myColor = WebColors.getRGBColor("#2D4154");
	        
	        Font font = new Font(FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
	        if(fileVM.showPatientName_patientVisit) {
	        	PdfPCell cell3 = new PdfPCell(new Paragraph("Patient Name", font));
	        	cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell3.setVerticalAlignment(Element.ALIGN_TOP);
		        cell3.setBackgroundColor(myColor);
		        cell3.setBorderColor(BaseColor.WHITE);
		        cell3.setBorderWidth(0.1f);
	        	table.addCell(cell3);
	        }
	        if(fileVM.showHicn_patientVisit) {
		        PdfPCell cell1 = new PdfPCell(new Paragraph("HICN", font));
		        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell1.setVerticalAlignment(Element.ALIGN_TOP);
		        cell1.setBackgroundColor(myColor);
		        cell1.setBorderColor(BaseColor.WHITE);
		        cell1.setBorderWidth(0.1f);
		        table.addCell(cell1);
	        }
	        if(fileVM.showPcpName_patientVisit) {
	        	PdfPCell cell4 = new PdfPCell(new Paragraph("PCP Name", font));
	        	cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell4.setVerticalAlignment(Element.ALIGN_TOP);
		        cell4.setBackgroundColor(myColor);
		        cell4.setBorderColor(BaseColor.WHITE);
		        cell4.setBorderWidth(0.1f);
	        	table.addCell(cell4);
	        }
	        if(fileVM.showTermedMonth_patientVisit) {
	        	PdfPCell cell5 = new PdfPCell(new Paragraph("Termed Month", font));
	        	cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell5.setVerticalAlignment(Element.ALIGN_TOP);
		        cell5.setBackgroundColor(myColor);
		        cell5.setBorderColor(BaseColor.WHITE);
		        cell5.setBorderWidth(0.1f);
	        	table.addCell(cell5);
	        }
	        if(fileVM.showIpaEffectiveDate_patientVisit) {
	        	PdfPCell cell5 = new PdfPCell(new Paragraph("IPA Effective Date", font));
	        	cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell5.setVerticalAlignment(Element.ALIGN_TOP);
		        cell5.setBackgroundColor(myColor);
		        cell5.setBorderColor(BaseColor.WHITE);
		        cell5.setBorderWidth(0.1f);
	        	table.addCell(cell5);
	        }
	        if(fileVM.showTotalErVisits_patientVisit) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("Total Number Of ER Visits", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        if(fileVM.showTotalCost_patientVisit) {
	        	PdfPCell cell8 = new PdfPCell(new Paragraph("Total Cost", font));
	        	cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell8.setVerticalAlignment(Element.ALIGN_TOP);
		        cell8.setBackgroundColor(myColor);
		        cell8.setBorderColor(BaseColor.WHITE);
		        cell8.setBorderWidth(0.1f);
	        	table.addCell(cell8);
	        }
	        
	        table.setHeaderRows(1);
	        
	        BaseColor oddRowColor = WebColors.getRGBColor("#F3F3F3");
	        
	        int count = 1; //table rows
	        for(PatientVisitReportPrintDataVM vm: list) {
	        	Font rowFont = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
	        	if(fileVM.showPatientName_patientVisit) {
		        	PdfPCell rowCell3 = new PdfPCell(new Paragraph(vm.getPatientName(), rowFont));
			        rowCell3.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell3.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell3.setBackgroundColor(oddRowColor);
			        rowCell3.setBorderColor(BaseColor.WHITE);
			        rowCell3.setBorderWidth(0.1f);
			        table.addCell(rowCell3);
		        }
	        	if(fileVM.showHicn_patientVisit) {
			        PdfPCell rowCell1 = new PdfPCell(new Paragraph(vm.getHicn(), rowFont));
			        rowCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell1.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell1.setBackgroundColor(oddRowColor);
			        rowCell1.setBorderColor(BaseColor.WHITE);
			        rowCell1.setBorderWidth(0.1f);
			        table.addCell(rowCell1);
	        	}
		        if(fileVM.showPcpName_patientVisit) {
		        	PdfPCell rowCell4 = new PdfPCell(new Paragraph(vm.getPcpName(), rowFont));
			        rowCell4.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell4.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell4.setBackgroundColor(oddRowColor);
			        rowCell4.setBorderColor(BaseColor.WHITE);
			        rowCell4.setBorderWidth(0.1f);
			        table.addCell(rowCell4);
		        }
		        if(fileVM.showTermedMonth_patientVisit) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getTermedMonth(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showIpaEffectiveDate_patientVisit) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getIpaEffectiveDate(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showTotalErVisits_patientVisit) {
		        	PdfPCell rowCell6 = new PdfPCell(new Paragraph(vm.getTotalErVisits(), rowFont));
			        rowCell6.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell6.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell6.setBackgroundColor(oddRowColor);
			        rowCell6.setBorderColor(BaseColor.WHITE);
			        rowCell6.setBorderWidth(0.1f);
			        table.addCell(rowCell6);
		        }
		        if(fileVM.showTotalCost_patientVisit) {
		        	PdfPCell rowCell8 = new PdfPCell(new Paragraph(vm.getTotalCost(), rowFont));
			        rowCell8.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell8.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell8.setBackgroundColor(oddRowColor);
			        rowCell8.setBorderColor(BaseColor.WHITE);
			        rowCell8.setBorderWidth(0.1f);
			        table.addCell(rowCell8);
		        }
		        
		        count++;
	        }
	        
	        table.setWidthPercentage(100);
	        document.add(table);
	        document.close();
	        InputStream is = new FileInputStream(file);
	        IOUtils.copy(is, os);
	}
	
    public void generatePatientVisitExpandReportPDF(PatientVisitExpandReportFileVM fileVM, OutputStream os) throws SQLException, IOException, DocumentException {
		
		List<PatientVisitExpandReportPrintDataVM> list = getDataForPatientVisitExpandReportPrint(fileVM);
		
		File file = new File("Data export-Patient Visit Report Details.pdf");
		 Document document = new Document();
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();
	        
	        int tableColumnSize = 0;
	        if(fileVM.showClaimId_patientVisitExpand)
	        	tableColumnSize++;
	        if(fileVM.showClaimDate_patientVisitExpand)
	        	tableColumnSize++;
	        if(fileVM.showClaimType_patientVisitExpand)
	        	tableColumnSize++;
	        if(fileVM.showClinicName_patientVisitExpand)
	        	tableColumnSize++;
	        if(fileVM.showPcpName_patientVisitExpand)
	        	tableColumnSize++;
	        if(fileVM.showIcdCodes_patientVisitExpand)
	        	tableColumnSize++;
	        if(fileVM.showHccCodes_patientVisitExpand)
	        	tableColumnSize++;
	        if(fileVM.showDrgCode_patientVisitExpand)
	        	tableColumnSize++;
	        if(fileVM.showBetosCat_patientVisitExpand)
	        	tableColumnSize++;
	        if(fileVM.showCost_patientVisitExpand)
	        	tableColumnSize++;
	        
	        PdfPTable table = new PdfPTable(tableColumnSize);//columns
	        
	        @SuppressWarnings("deprecation")
			BaseColor myColor = WebColors.getRGBColor("#2D4154");
	        
	        Font font = new Font(FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
	        if(fileVM.showClaimId_patientVisitExpand) {
	        	PdfPCell cell3 = new PdfPCell(new Paragraph("Claim Id", font));
	        	cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell3.setVerticalAlignment(Element.ALIGN_TOP);
		        cell3.setBackgroundColor(myColor);
		        cell3.setBorderColor(BaseColor.WHITE);
		        cell3.setBorderWidth(0.1f);
	        	table.addCell(cell3);
	        }
	        if(fileVM.showClaimDate_patientVisitExpand) {
		        PdfPCell cell1 = new PdfPCell(new Paragraph("Claim Date", font));
		        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell1.setVerticalAlignment(Element.ALIGN_TOP);
		        cell1.setBackgroundColor(myColor);
		        cell1.setBorderColor(BaseColor.WHITE);
		        cell1.setBorderWidth(0.1f);
		        table.addCell(cell1);
	        }
	        if(fileVM.showClaimType_patientVisitExpand) {
	        	PdfPCell cell4 = new PdfPCell(new Paragraph("Claim Type", font));
	        	cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell4.setVerticalAlignment(Element.ALIGN_TOP);
		        cell4.setBackgroundColor(myColor);
		        cell4.setBorderColor(BaseColor.WHITE);
		        cell4.setBorderWidth(0.1f);
	        	table.addCell(cell4);
	        }
	        if(fileVM.showClinicName_patientVisitExpand) {
	        	PdfPCell cell5 = new PdfPCell(new Paragraph("Clinic Name", font));
	        	cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell5.setVerticalAlignment(Element.ALIGN_TOP);
		        cell5.setBackgroundColor(myColor);
		        cell5.setBorderColor(BaseColor.WHITE);
		        cell5.setBorderWidth(0.1f);
	        	table.addCell(cell5);
	        }
	        if(fileVM.showPcpName_patientVisitExpand) {
	        	PdfPCell cell5 = new PdfPCell(new Paragraph("PCP Name", font));
	        	cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell5.setVerticalAlignment(Element.ALIGN_TOP);
		        cell5.setBackgroundColor(myColor);
		        cell5.setBorderColor(BaseColor.WHITE);
		        cell5.setBorderWidth(0.1f);
	        	table.addCell(cell5);
	        }
	        if(fileVM.showIcdCodes_patientVisitExpand) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("ICD Codes", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        if(fileVM.showHccCodes_patientVisitExpand) {
	        	PdfPCell cell8 = new PdfPCell(new Paragraph("HCC Codes", font));
	        	cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell8.setVerticalAlignment(Element.ALIGN_TOP);
		        cell8.setBackgroundColor(myColor);
		        cell8.setBorderColor(BaseColor.WHITE);
		        cell8.setBorderWidth(0.1f);
	        	table.addCell(cell8);
	        }
	        
	        if(fileVM.showDrgCode_patientVisitExpand) {
	        	PdfPCell cell8 = new PdfPCell(new Paragraph("DRG Code", font));
	        	cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell8.setVerticalAlignment(Element.ALIGN_TOP);
		        cell8.setBackgroundColor(myColor);
		        cell8.setBorderColor(BaseColor.WHITE);
		        cell8.setBorderWidth(0.1f);
	        	table.addCell(cell8);
	        }
	        
	        if(fileVM.showBetosCat_patientVisitExpand) {
	        	PdfPCell cell8 = new PdfPCell(new Paragraph("Betos Cat", font));
	        	cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell8.setVerticalAlignment(Element.ALIGN_TOP);
		        cell8.setBackgroundColor(myColor);
		        cell8.setBorderColor(BaseColor.WHITE);
		        cell8.setBorderWidth(0.1f);
	        	table.addCell(cell8);
	        }
	        
	        if(fileVM.showCost_patientVisitExpand) {
	        	PdfPCell cell8 = new PdfPCell(new Paragraph("Cost", font));
	        	cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell8.setVerticalAlignment(Element.ALIGN_TOP);
		        cell8.setBackgroundColor(myColor);
		        cell8.setBorderColor(BaseColor.WHITE);
		        cell8.setBorderWidth(0.1f);
	        	table.addCell(cell8);
	        }
	        
	        table.setHeaderRows(1);
	        
	        BaseColor oddRowColor = WebColors.getRGBColor("#F3F3F3");
	        
	        int count = 1; //table rows
	        for(PatientVisitExpandReportPrintDataVM vm: list) {
	        	Font rowFont = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
	        	if(fileVM.showClaimId_patientVisitExpand) {
		        	PdfPCell rowCell3 = new PdfPCell(new Paragraph(vm.getClaimId(), rowFont));
			        rowCell3.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell3.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell3.setBackgroundColor(oddRowColor);
			        rowCell3.setBorderColor(BaseColor.WHITE);
			        rowCell3.setBorderWidth(0.1f);
			        table.addCell(rowCell3);
		        }
	        	if(fileVM.showClaimDate_patientVisitExpand) {
			        PdfPCell rowCell1 = new PdfPCell(new Paragraph(vm.getClaimDate(), rowFont));
			        rowCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell1.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell1.setBackgroundColor(oddRowColor);
			        rowCell1.setBorderColor(BaseColor.WHITE);
			        rowCell1.setBorderWidth(0.1f);
			        table.addCell(rowCell1);
	        	}
		        if(fileVM.showClaimType_patientVisitExpand) {
		        	PdfPCell rowCell4 = new PdfPCell(new Paragraph(vm.getClaimType(), rowFont));
			        rowCell4.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell4.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell4.setBackgroundColor(oddRowColor);
			        rowCell4.setBorderColor(BaseColor.WHITE);
			        rowCell4.setBorderWidth(0.1f);
			        table.addCell(rowCell4);
		        }
		        if(fileVM.showClinicName_patientVisitExpand) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getClinicName(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showPcpName_patientVisitExpand) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getPcpName(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showIcdCodes_patientVisitExpand) {
		        	PdfPCell rowCell6 = new PdfPCell(new Paragraph(vm.getIcdCodes(), rowFont));
			        rowCell6.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell6.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell6.setBackgroundColor(oddRowColor);
			        rowCell6.setBorderColor(BaseColor.WHITE);
			        rowCell6.setBorderWidth(0.1f);
			        table.addCell(rowCell6);
		        }
		        if(fileVM.showHccCodes_patientVisitExpand) {
		        	PdfPCell rowCell8 = new PdfPCell(new Paragraph(vm.getHccCodes(), rowFont));
			        rowCell8.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell8.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell8.setBackgroundColor(oddRowColor);
			        rowCell8.setBorderColor(BaseColor.WHITE);
			        rowCell8.setBorderWidth(0.1f);
			        table.addCell(rowCell8);
		        }
		        
		        if(fileVM.showDrgCode_patientVisitExpand) {
		        	PdfPCell rowCell8 = new PdfPCell(new Paragraph(vm.getDrgCode(), rowFont));
			        rowCell8.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell8.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell8.setBackgroundColor(oddRowColor);
			        rowCell8.setBorderColor(BaseColor.WHITE);
			        rowCell8.setBorderWidth(0.1f);
			        table.addCell(rowCell8);
		        }
		        
		        if(fileVM.showBetosCat_patientVisitExpand) {
		        	PdfPCell rowCell8 = new PdfPCell(new Paragraph(vm.getBetosCat(), rowFont));
			        rowCell8.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell8.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell8.setBackgroundColor(oddRowColor);
			        rowCell8.setBorderColor(BaseColor.WHITE);
			        rowCell8.setBorderWidth(0.1f);
			        table.addCell(rowCell8);
		        }
		        
		        if(fileVM.showCost_patientVisitExpand) {
		        	PdfPCell rowCell8 = new PdfPCell(new Paragraph(vm.getCost(), rowFont));
			        rowCell8.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell8.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell8.setBackgroundColor(oddRowColor);
			        rowCell8.setBorderColor(BaseColor.WHITE);
			        rowCell8.setBorderWidth(0.1f);
			        table.addCell(rowCell8);
		        }
		        
		        count++;
	        }
	        
	        table.setWidthPercentage(100);
	        document.add(table);
	        document.close();
	        InputStream is = new FileInputStream(file);
	        IOUtils.copy(is, os);
	}
	
	public void generateSettledMonthsReportPDF(SettledMonthsReportFileVM fileVM, OutputStream os) throws SQLException, IOException, DocumentException {
		
		List<SettledMonthsReportPrintDataVM> list = getDataForSettledMonthsReportPrint(fileVM);
		
		File file = new File("Data export-Settled Months Report.pdf");
		 Document document = new Document();
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();
	        
	        int tableColumnSize = 0;
	        if(fileVM.showPcpLocation_settledMonths)
	        	tableColumnSize++;
	        if(fileVM.showMonth_settledMonths)
	        	tableColumnSize++;
	        if(fileVM.showMembership_settledMonths)
	        	tableColumnSize++;
	        if(fileVM.showIpaPremium_settledMonths)
	        	tableColumnSize++;
	        if(fileVM.showTotalExpenses_settledMonths)
	        	tableColumnSize++;
	        if(fileVM.showStopLoss_settledMonths)
	        	tableColumnSize++;
	        if(fileVM.showNetPremium_settledMonths)
	        	tableColumnSize++;
	        if(fileVM.showRiskSharing_settledMonths)
	        	tableColumnSize++;
	        if(fileVM.showSurplusDeficit_settledMonths)
	        	tableColumnSize++;
	        
	        PdfPTable table = new PdfPTable(tableColumnSize);//columns
	        
	        @SuppressWarnings("deprecation")
			BaseColor myColor = WebColors.getRGBColor("#2D4154");
	        
	        Font font = new Font(FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
	        if(fileVM.showPcpLocation_settledMonths) {
	        	PdfPCell cell3 = new PdfPCell(new Paragraph("PCP Location", font));
	        	cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell3.setVerticalAlignment(Element.ALIGN_TOP);
		        cell3.setBackgroundColor(myColor);
		        cell3.setBorderColor(BaseColor.WHITE);
		        cell3.setBorderWidth(0.1f);
	        	table.addCell(cell3);
	        }
	        if(fileVM.showMonth_settledMonths) {
		        PdfPCell cell1 = new PdfPCell(new Paragraph("Month", font));
		        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell1.setVerticalAlignment(Element.ALIGN_TOP);
		        cell1.setBackgroundColor(myColor);
		        cell1.setBorderColor(BaseColor.WHITE);
		        cell1.setBorderWidth(0.1f);
		        table.addCell(cell1);
	        }
	        if(fileVM.showMembership_settledMonths) {
	        	PdfPCell cell4 = new PdfPCell(new Paragraph("Membership", font));
	        	cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell4.setVerticalAlignment(Element.ALIGN_TOP);
		        cell4.setBackgroundColor(myColor);
		        cell4.setBorderColor(BaseColor.WHITE);
		        cell4.setBorderWidth(0.1f);
	        	table.addCell(cell4);
	        }
	        if(fileVM.showIpaPremium_settledMonths) {
	        	PdfPCell cell5 = new PdfPCell(new Paragraph("Ipa Premium", font));
	        	cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell5.setVerticalAlignment(Element.ALIGN_TOP);
		        cell5.setBackgroundColor(myColor);
		        cell5.setBorderColor(BaseColor.WHITE);
		        cell5.setBorderWidth(0.1f);
	        	table.addCell(cell5);
	        }
	        if(fileVM.showTotalExpenses_settledMonths) {
	        	PdfPCell cell5 = new PdfPCell(new Paragraph("Total Expenses", font));
	        	cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell5.setVerticalAlignment(Element.ALIGN_TOP);
		        cell5.setBackgroundColor(myColor);
		        cell5.setBorderColor(BaseColor.WHITE);
		        cell5.setBorderWidth(0.1f);
	        	table.addCell(cell5);
	        }
	        if(fileVM.showStopLoss_settledMonths) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("StopLoss", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        if(fileVM.showNetPremium_settledMonths) {
	        	PdfPCell cell8 = new PdfPCell(new Paragraph("Net Premium", font));
	        	cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell8.setVerticalAlignment(Element.ALIGN_TOP);
		        cell8.setBackgroundColor(myColor);
		        cell8.setBorderColor(BaseColor.WHITE);
		        cell8.setBorderWidth(0.1f);
	        	table.addCell(cell8);
	        }
	        if(fileVM.showRiskSharing_settledMonths) {
	        	PdfPCell cell8 = new PdfPCell(new Paragraph("Risk Sharing", font));
	        	cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell8.setVerticalAlignment(Element.ALIGN_TOP);
		        cell8.setBackgroundColor(myColor);
		        cell8.setBorderColor(BaseColor.WHITE);
		        cell8.setBorderWidth(0.1f);
	        	table.addCell(cell8);
	        }
	        if(fileVM.showSurplusDeficit_settledMonths) {
	        	PdfPCell cell8 = new PdfPCell(new Paragraph("Surplus/Deficit", font));
	        	cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell8.setVerticalAlignment(Element.ALIGN_TOP);
		        cell8.setBackgroundColor(myColor);
		        cell8.setBorderColor(BaseColor.WHITE);
		        cell8.setBorderWidth(0.1f);
	        	table.addCell(cell8);
	        }
	        
	        table.setHeaderRows(1);
	        
	        BaseColor oddRowColor = WebColors.getRGBColor("#F3F3F3");
	        
	        int count = 1; //table rows
	        for(SettledMonthsReportPrintDataVM vm: list) {
	        	Font rowFont = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
	        	if(fileVM.showPcpLocation_settledMonths) {
		        	PdfPCell rowCell3 = new PdfPCell(new Paragraph(vm.getPcpLocation(), rowFont));
			        rowCell3.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell3.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell3.setBackgroundColor(oddRowColor);
			        rowCell3.setBorderColor(BaseColor.WHITE);
			        rowCell3.setBorderWidth(0.1f);
			        table.addCell(rowCell3);
		        }
	        	if(fileVM.showMonth_settledMonths) {
			        PdfPCell rowCell1 = new PdfPCell(new Paragraph(vm.getMonth(), rowFont));
			        rowCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell1.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell1.setBackgroundColor(oddRowColor);
			        rowCell1.setBorderColor(BaseColor.WHITE);
			        rowCell1.setBorderWidth(0.1f);
			        table.addCell(rowCell1);
	        	}
		        if(fileVM.showMembership_settledMonths) {
		        	PdfPCell rowCell4 = new PdfPCell(new Paragraph(vm.getMembership(), rowFont));
			        rowCell4.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell4.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell4.setBackgroundColor(oddRowColor);
			        rowCell4.setBorderColor(BaseColor.WHITE);
			        rowCell4.setBorderWidth(0.1f);
			        table.addCell(rowCell4);
		        }
		        if(fileVM.showIpaPremium_settledMonths) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getIpaPremium(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showTotalExpenses_settledMonths) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getTotalExpenses(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showStopLoss_settledMonths) {
		        	PdfPCell rowCell6 = new PdfPCell(new Paragraph(vm.getStoploss(), rowFont));
			        rowCell6.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell6.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell6.setBackgroundColor(oddRowColor);
			        rowCell6.setBorderColor(BaseColor.WHITE);
			        rowCell6.setBorderWidth(0.1f);
			        table.addCell(rowCell6);
		        }
		        if(fileVM.showNetPremium_settledMonths) {
		        	PdfPCell rowCell8 = new PdfPCell(new Paragraph(vm.getNetPremium(), rowFont));
			        rowCell8.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell8.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell8.setBackgroundColor(oddRowColor);
			        rowCell8.setBorderColor(BaseColor.WHITE);
			        rowCell8.setBorderWidth(0.1f);
			        table.addCell(rowCell8);
		        }
		        if(fileVM.showRiskSharing_settledMonths) {
		        	PdfPCell rowCell8 = new PdfPCell(new Paragraph(vm.getRiskSharing(), rowFont));
			        rowCell8.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell8.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell8.setBackgroundColor(oddRowColor);
			        rowCell8.setBorderColor(BaseColor.WHITE);
			        rowCell8.setBorderWidth(0.1f);
			        table.addCell(rowCell8);
		        }
		        if(fileVM.showSurplusDeficit_settledMonths) {
		        	PdfPCell rowCell8 = new PdfPCell(new Paragraph(vm.getSurplusDeficit(), rowFont));
			        rowCell8.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell8.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell8.setBackgroundColor(oddRowColor);
			        rowCell8.setBorderColor(BaseColor.WHITE);
			        rowCell8.setBorderWidth(0.1f);
			        table.addCell(rowCell8);
		        }
		        
		        count++;
	        }
	        
	        table.setWidthPercentage(100);
	        document.add(table);
	        document.close();
	        InputStream is = new FileInputStream(file);
	        IOUtils.copy(is, os);
	}
	
	public void generateSettledMonthsExpandReportPDF(SettledMonthsExpandReportFileVM fileVM, OutputStream os) throws SQLException, IOException, DocumentException {
		
		List<SettledMonthsExpandReportPrintDataVM> list = getDataForSettledMonthsExpandReportPrint(fileVM);
		
		File file = new File("Data export-Settled Months Expand Report.pdf");
		 Document document = new Document();
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();
	        
	        int tableColumnSize = 0;
	        if(fileVM.showPatientName_settledMonthsExpand)
	        	tableColumnSize++;
	        if(fileVM.showPcpName_settledMonthsExpand)
	        	tableColumnSize++;
	        if(fileVM.showPcpLocation_settledMonthsExpand)
	        	tableColumnSize++;
	        if(fileVM.showCost_settledMonthsExpand)
	        	tableColumnSize++;
	        if(fileVM.showClaimType_settledMonthsExpand)
	        	tableColumnSize++;
	        if(fileVM.showMra_settledMonthsExpand)
	        	tableColumnSize++;
	        
	        PdfPTable table = new PdfPTable(tableColumnSize);//columns
	        
	        @SuppressWarnings("deprecation")
			BaseColor myColor = WebColors.getRGBColor("#2D4154");
	        
	        Font font = new Font(FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
	        if(fileVM.showPatientName_settledMonthsExpand) {
	        	PdfPCell cell3 = new PdfPCell(new Paragraph("Patient Name", font));
	        	cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell3.setVerticalAlignment(Element.ALIGN_TOP);
		        cell3.setBackgroundColor(myColor);
		        cell3.setBorderColor(BaseColor.WHITE);
		        cell3.setBorderWidth(0.1f);
	        	table.addCell(cell3);
	        }
	        if(fileVM.showPcpName_settledMonthsExpand) {
		        PdfPCell cell1 = new PdfPCell(new Paragraph("PCP Name", font));
		        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell1.setVerticalAlignment(Element.ALIGN_TOP);
		        cell1.setBackgroundColor(myColor);
		        cell1.setBorderColor(BaseColor.WHITE);
		        cell1.setBorderWidth(0.1f);
		        table.addCell(cell1);
	        }
	        if(fileVM.showPcpLocation_settledMonthsExpand) {
	        	PdfPCell cell4 = new PdfPCell(new Paragraph("PCP Location", font));
	        	cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell4.setVerticalAlignment(Element.ALIGN_TOP);
		        cell4.setBackgroundColor(myColor);
		        cell4.setBorderColor(BaseColor.WHITE);
		        cell4.setBorderWidth(0.1f);
	        	table.addCell(cell4);
	        }
	        if(fileVM.showCost_settledMonthsExpand) {
	        	PdfPCell cell5 = new PdfPCell(new Paragraph("Cost", font));
	        	cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell5.setVerticalAlignment(Element.ALIGN_TOP);
		        cell5.setBackgroundColor(myColor);
		        cell5.setBorderColor(BaseColor.WHITE);
		        cell5.setBorderWidth(0.1f);
	        	table.addCell(cell5);
	        }
	        if(fileVM.showClaimType_settledMonthsExpand) {
	        	PdfPCell cell5 = new PdfPCell(new Paragraph("Claim Type", font));
	        	cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell5.setVerticalAlignment(Element.ALIGN_TOP);
		        cell5.setBackgroundColor(myColor);
		        cell5.setBorderColor(BaseColor.WHITE);
		        cell5.setBorderWidth(0.1f);
	        	table.addCell(cell5);
	        }
	        if(fileVM.showMra_settledMonthsExpand) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("MRA", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        
	        table.setHeaderRows(1);
	        
	        BaseColor oddRowColor = WebColors.getRGBColor("#F3F3F3");
	        
	        int count = 1; //table rows
	        for(SettledMonthsExpandReportPrintDataVM vm: list) {
	        	Font rowFont = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
	        	if(fileVM.showPatientName_settledMonthsExpand) {
		        	PdfPCell rowCell3 = new PdfPCell(new Paragraph(vm.getPatientName(), rowFont));
			        rowCell3.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell3.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell3.setBackgroundColor(oddRowColor);
			        rowCell3.setBorderColor(BaseColor.WHITE);
			        rowCell3.setBorderWidth(0.1f);
			        table.addCell(rowCell3);
		        }
	        	if(fileVM.showPcpName_settledMonthsExpand) {
			        PdfPCell rowCell1 = new PdfPCell(new Paragraph(vm.getPcpName(), rowFont));
			        rowCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell1.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell1.setBackgroundColor(oddRowColor);
			        rowCell1.setBorderColor(BaseColor.WHITE);
			        rowCell1.setBorderWidth(0.1f);
			        table.addCell(rowCell1);
	        	}
		        if(fileVM.showPcpLocation_settledMonthsExpand) {
		        	PdfPCell rowCell4 = new PdfPCell(new Paragraph(vm.getPcpLocation(), rowFont));
			        rowCell4.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell4.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell4.setBackgroundColor(oddRowColor);
			        rowCell4.setBorderColor(BaseColor.WHITE);
			        rowCell4.setBorderWidth(0.1f);
			        table.addCell(rowCell4);
		        }
		        if(fileVM.showCost_settledMonthsExpand) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getCost(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showClaimType_settledMonthsExpand) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getClaimType(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showMra_settledMonthsExpand) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getMra(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        
		        count++;
	        }
	        
	        table.setWidthPercentage(100);
	        document.add(table);
	        document.close();
	        InputStream is = new FileInputStream(file);
	        IOUtils.copy(is, os);
	}
	
	public void generateReisuranceManagementReportPDF(ReinsuranceManagementReportFileVM fileVM, OutputStream os) throws SQLException, IOException, DocumentException {
		
		List<ReinsuranceManagementReportPrintDataVM> list = getDataForReinsuranceManagementReportPrint(fileVM);
		
		File file = new File("Data export-Reinsurance Management Report.pdf");
		 Document document = new Document();
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();
	        
	        int tableColumnSize = 0;
	        if(fileVM.showSubscriberID_reinsuranceManagement)
	        	tableColumnSize++;
	        if(fileVM.showPlanName_reinsuranceManagement)
	        	tableColumnSize++;
	        if(fileVM.showPatientName_reinsuranceManagement)
	        	tableColumnSize++;
	        if(fileVM.showPcpName_reinsuranceManagement)
	        	tableColumnSize++;
	        if(fileVM.showTermedMonth_reinsuranceManagement)
	        	tableColumnSize++;
	        if(fileVM.showInstClaims_reinsuranceManagement)
	        	tableColumnSize++;
	        if(fileVM.showProfClaims_reinsuranceManagement)
	        	tableColumnSize++;
	        if(fileVM.showTotalCost_reinsuranceManagement)
	        	tableColumnSize++;
	        
	        PdfPTable table = new PdfPTable(tableColumnSize);//columns
	        
	        @SuppressWarnings("deprecation")
			BaseColor myColor = WebColors.getRGBColor("#2D4154");
	        
	        Font font = new Font(FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
	        if(fileVM.showSubscriberID_reinsuranceManagement) {
	        	PdfPCell cell3 = new PdfPCell(new Paragraph("SusbcriberID", font));
	        	cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell3.setVerticalAlignment(Element.ALIGN_TOP);
		        cell3.setBackgroundColor(myColor);
		        cell3.setBorderColor(BaseColor.WHITE);
		        cell3.setBorderWidth(0.1f);
	        	table.addCell(cell3);
	        }
	        if(fileVM.showPlanName_reinsuranceManagement) {
		        PdfPCell cell1 = new PdfPCell(new Paragraph("Plan Name Name", font));
		        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell1.setVerticalAlignment(Element.ALIGN_TOP);
		        cell1.setBackgroundColor(myColor);
		        cell1.setBorderColor(BaseColor.WHITE);
		        cell1.setBorderWidth(0.1f);
		        table.addCell(cell1);
	        }
	        if(fileVM.showPatientName_reinsuranceManagement) {
	        	PdfPCell cell4 = new PdfPCell(new Paragraph("Patient Name", font));
	        	cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell4.setVerticalAlignment(Element.ALIGN_TOP);
		        cell4.setBackgroundColor(myColor);
		        cell4.setBorderColor(BaseColor.WHITE);
		        cell4.setBorderWidth(0.1f);
	        	table.addCell(cell4);
	        }
	        if(fileVM.showPcpName_reinsuranceManagement) {
	        	PdfPCell cell5 = new PdfPCell(new Paragraph("PCP Name", font));
	        	cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell5.setVerticalAlignment(Element.ALIGN_TOP);
		        cell5.setBackgroundColor(myColor);
		        cell5.setBorderColor(BaseColor.WHITE);
		        cell5.setBorderWidth(0.1f);
	        	table.addCell(cell5);
	        }
	        if(fileVM.showTermedMonth_reinsuranceManagement) {
	        	PdfPCell cell5 = new PdfPCell(new Paragraph("Termed Month", font));
	        	cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell5.setVerticalAlignment(Element.ALIGN_TOP);
		        cell5.setBackgroundColor(myColor);
		        cell5.setBorderColor(BaseColor.WHITE);
		        cell5.setBorderWidth(0.1f);
	        	table.addCell(cell5);
	        }
	        if(fileVM.showInstClaims_reinsuranceManagement) {
	        	PdfPCell cell5 = new PdfPCell(new Paragraph("INST Claims", font));
	        	cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell5.setVerticalAlignment(Element.ALIGN_TOP);
		        cell5.setBackgroundColor(myColor);
		        cell5.setBorderColor(BaseColor.WHITE);
		        cell5.setBorderWidth(0.1f);
	        	table.addCell(cell5);
	        }
	        if(fileVM.showProfClaims_reinsuranceManagement) {
	        	PdfPCell cell5 = new PdfPCell(new Paragraph("PROF Claims", font));
	        	cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell5.setVerticalAlignment(Element.ALIGN_TOP);
		        cell5.setBackgroundColor(myColor);
		        cell5.setBorderColor(BaseColor.WHITE);
		        cell5.setBorderWidth(0.1f);
	        	table.addCell(cell5);
	        }
	        if(fileVM.showTotalCost_reinsuranceManagement) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("Total Cost", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        
	        table.setHeaderRows(1);
	        
	        BaseColor oddRowColor = WebColors.getRGBColor("#F3F3F3");
	        
	        int count = 1; //table rows
	        for(ReinsuranceManagementReportPrintDataVM vm: list) {
	        	Font rowFont = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
	        	if(fileVM.showSubscriberID_reinsuranceManagement) {
		        	PdfPCell rowCell3 = new PdfPCell(new Paragraph(vm.getHicn(), rowFont));
			        rowCell3.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell3.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell3.setBackgroundColor(oddRowColor);
			        rowCell3.setBorderColor(BaseColor.WHITE);
			        rowCell3.setBorderWidth(0.1f);
			        table.addCell(rowCell3);
		        }
	        	if(fileVM.showPlanName_reinsuranceManagement) {
			        PdfPCell rowCell1 = new PdfPCell(new Paragraph(vm.getPlanName(), rowFont));
			        rowCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell1.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell1.setBackgroundColor(oddRowColor);
			        rowCell1.setBorderColor(BaseColor.WHITE);
			        rowCell1.setBorderWidth(0.1f);
			        table.addCell(rowCell1);
	        	}
		        if(fileVM.showPatientName_reinsuranceManagement) {
		        	PdfPCell rowCell4 = new PdfPCell(new Paragraph(vm.getPatientName(), rowFont));
			        rowCell4.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell4.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell4.setBackgroundColor(oddRowColor);
			        rowCell4.setBorderColor(BaseColor.WHITE);
			        rowCell4.setBorderWidth(0.1f);
			        table.addCell(rowCell4);
		        }
		        if(fileVM.showPcpName_reinsuranceManagement) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getPcpName(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showTermedMonth_reinsuranceManagement) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getTermedMonth(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showInstClaims_reinsuranceManagement) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getInstClaims(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showProfClaims_reinsuranceManagement) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getProfClaims(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showTotalCost_reinsuranceManagement) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getTotalCost(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        
		        count++;
	        }
	        
	        table.setWidthPercentage(100);
	        document.add(table);
	        document.close();
	        InputStream is = new FileInputStream(file);
	        IOUtils.copy(is, os);
	}

public void generateCostManagementReportPDF(ReinsuranceCostReportFileVM fileVM, OutputStream os) throws SQLException, IOException, DocumentException {
		
		List<ReinsuranceCostReportPrintDataVM> list = getDataForReinsuranceCostReportPrint(fileVM);
		
		File file = new File("Data export-Reinsurance Cost Report.pdf");
		 Document document = new Document();
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();
	        
	        int tableColumnSize = 0;
	        if(fileVM.showPlanName_reinsuranceCostReport)
	        	tableColumnSize++;
	        if(fileVM.showPolicyPeriod_reinsuranceCostReport)
	        	tableColumnSize++;
	        if(fileVM.showPatientLastName_reinsuranceCostReport)
	        	tableColumnSize++;
	        if(fileVM.showPatientFirstName_reinsuranceCostReport)
	        	tableColumnSize++;
	        if(fileVM.showSubscriberID_reinsuranceCostReport)
	        	tableColumnSize++;
	        if(fileVM.showEffectiveDate_reinsuranceCostReport)
	        	tableColumnSize++;
	        if(fileVM.showTermedMonth_reinsuranceCostReport)
	        	tableColumnSize++;
	        if(fileVM.showDateOfBirth_reinsuranceCostReport)
	        	tableColumnSize++;
	        if(fileVM.showStatus_reinsuranceCostReport)
	        	tableColumnSize++;
	        if(fileVM.showGender_reinsuranceCostReport)
	        	tableColumnSize++;
	        if(fileVM.showPcpName_reinsuranceCostReport)
	        	tableColumnSize++;
	        if(fileVM.showTotalClaimsCost_reinsuranceCostReport)
	        	tableColumnSize++;
	        
	        PdfPTable table = new PdfPTable(tableColumnSize);//columns
	        @SuppressWarnings("deprecation")
			BaseColor myColor = WebColors.getRGBColor("#2D4154");
	        
	        Font font = new Font(FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
	        
	        if(fileVM.showPlanName_reinsuranceCostReport) {
	        	PdfPCell cell3 = new PdfPCell(new Paragraph("Plan Name", font));
	        	cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell3.setVerticalAlignment(Element.ALIGN_TOP);
		        cell3.setBackgroundColor(myColor);
		        cell3.setBorderColor(BaseColor.WHITE);
		        cell3.setBorderWidth(0.1f);
	        	table.addCell(cell3);
	        }
	        if(fileVM.showPolicyPeriod_reinsuranceCostReport) {
		        PdfPCell cell1 = new PdfPCell(new Paragraph("Policy Period", font));
		        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell1.setVerticalAlignment(Element.ALIGN_TOP);
		        cell1.setBackgroundColor(myColor);
		        cell1.setBorderColor(BaseColor.WHITE);
		        cell1.setBorderWidth(0.1f);
		        table.addCell(cell1);
	        }
	        if(fileVM.showPatientLastName_reinsuranceCostReport) {
	        	PdfPCell cell4 = new PdfPCell(new Paragraph("Patient Last Name", font));
	        	cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell4.setVerticalAlignment(Element.ALIGN_TOP);
		        cell4.setBackgroundColor(myColor);
		        cell4.setBorderColor(BaseColor.WHITE);
		        cell4.setBorderWidth(0.1f);
	        	table.addCell(cell4);
	        }
	        if(fileVM.showPatientFirstName_reinsuranceCostReport) {
	        	PdfPCell cell5 = new PdfPCell(new Paragraph("Patient First Name", font));
	        	cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell5.setVerticalAlignment(Element.ALIGN_TOP);
		        cell5.setBackgroundColor(myColor);
		        cell5.setBorderColor(BaseColor.WHITE);
		        cell5.setBorderWidth(0.1f);
	        	table.addCell(cell5);
	        }
	        if(fileVM.showSubscriberID_reinsuranceCostReport) {
	        	PdfPCell cell5 = new PdfPCell(new Paragraph("HICN/SubscriberID", font));
	        	cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell5.setVerticalAlignment(Element.ALIGN_TOP);
		        cell5.setBackgroundColor(myColor);
		        cell5.setBorderColor(BaseColor.WHITE);
		        cell5.setBorderWidth(0.1f);
	        	table.addCell(cell5);
	        }
	        if(fileVM.showEffectiveDate_reinsuranceCostReport) {
	        	PdfPCell cell5 = new PdfPCell(new Paragraph("Effective Date", font));
	        	cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell5.setVerticalAlignment(Element.ALIGN_TOP);
		        cell5.setBackgroundColor(myColor);
		        cell5.setBorderColor(BaseColor.WHITE);
		        cell5.setBorderWidth(0.1f);
	        	table.addCell(cell5);
	        }
	        if(fileVM.showTermedMonth_reinsuranceCostReport) {
	        	PdfPCell cell5 = new PdfPCell(new Paragraph("Termed Month", font));
	        	cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell5.setVerticalAlignment(Element.ALIGN_TOP);
		        cell5.setBackgroundColor(myColor);
		        cell5.setBorderColor(BaseColor.WHITE);
		        cell5.setBorderWidth(0.1f);
	        	table.addCell(cell5);
	        }
	        if(fileVM.showDateOfBirth_reinsuranceCostReport) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("DOB", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        if(fileVM.showStatus_reinsuranceCostReport) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("Status", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        if(fileVM.showGender_reinsuranceCostReport) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("Gender", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        if(fileVM.showPcpName_reinsuranceCostReport) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("PCP Name", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        if(fileVM.showTotalClaimsCost_reinsuranceCostReport) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("Total Claims Cost", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        table.setHeaderRows(1);
	        
	        BaseColor oddRowColor = WebColors.getRGBColor("#F3F3F3");
	        
	        int count = 1; //table rows
	        for(ReinsuranceCostReportPrintDataVM vm: list) 
	        {
	        	Font rowFont = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
	        	if(fileVM.showPlanName_reinsuranceCostReport) {
		        	PdfPCell rowCell3 = new PdfPCell(new Paragraph(vm.getPlanName(), rowFont));
			        rowCell3.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell3.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell3.setBackgroundColor(oddRowColor);
			        rowCell3.setBorderColor(BaseColor.WHITE);
			        rowCell3.setBorderWidth(0.1f);
			        table.addCell(rowCell3);
		        }
	        	if(fileVM.showPolicyPeriod_reinsuranceCostReport) {
			        PdfPCell rowCell1 = new PdfPCell(new Paragraph(vm.getPolicyPeriod(), rowFont));
			        rowCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell1.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell1.setBackgroundColor(oddRowColor);
			        rowCell1.setBorderColor(BaseColor.WHITE);
			        rowCell1.setBorderWidth(0.1f);
			        table.addCell(rowCell1);
	        	}
		        if(fileVM.showPatientLastName_reinsuranceCostReport) {
		        	PdfPCell rowCell4 = new PdfPCell(new Paragraph(vm.getPatientLastName(), rowFont));
			        rowCell4.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell4.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell4.setBackgroundColor(oddRowColor);
			        rowCell4.setBorderColor(BaseColor.WHITE);
			        rowCell4.setBorderWidth(0.1f);
			        table.addCell(rowCell4);
		        }

		        if(fileVM.showPatientFirstName_reinsuranceCostReport) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getPatientFirstName(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showSubscriberID_reinsuranceCostReport) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getSubscriberID(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showEffectiveDate_reinsuranceCostReport) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getEffectiveDate(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showTermedMonth_reinsuranceCostReport) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getTermedMonth(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showDateOfBirth_reinsuranceCostReport) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getDob(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showStatus_reinsuranceCostReport) {
		        	PdfPCell rowCell7 = new PdfPCell(new Paragraph(vm.getStatus(), rowFont));
		        	rowCell7.setHorizontalAlignment(Element.ALIGN_LEFT);
		        	rowCell7.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell7.setBackgroundColor(oddRowColor);
			        rowCell7.setBorderColor(BaseColor.WHITE);
			        rowCell7.setBorderWidth(0.1f);
			        table.addCell(rowCell7);
		        }
		        if(fileVM.showGender_reinsuranceCostReport) {
		        	PdfPCell rowCell7 = new PdfPCell(new Paragraph(vm.getGender(), rowFont));
		        	rowCell7.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell7.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell7.setBackgroundColor(oddRowColor);
			        rowCell7.setBorderColor(BaseColor.WHITE);
			        rowCell7.setBorderWidth(0.1f);
			        table.addCell(rowCell7);
		        }
		        if(fileVM.showPcpName_reinsuranceCostReport) {
		        	PdfPCell rowCell7 = new PdfPCell(new Paragraph(vm.getPcpName(), rowFont));
		        	rowCell7.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell7.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell7.setBackgroundColor(oddRowColor);
			        rowCell7.setBorderColor(BaseColor.WHITE);
			        rowCell7.setBorderWidth(0.1f);
			        table.addCell(rowCell7);
		        }
		        if(fileVM.showTotalClaimsCost_reinsuranceCostReport) {
		        	PdfPCell rowCell7 = new PdfPCell(new Paragraph(vm.getTotalClaimsCost(), rowFont));
			        rowCell7.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell7.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell7.setBackgroundColor(oddRowColor);
			        rowCell7.setBorderColor(BaseColor.WHITE);
			        rowCell7.setBorderWidth(0.1f);
			        table.addCell(rowCell7);
		        }
		        count++;
	        }
	        
	        table.setWidthPercentage(100);
	        document.add(table);
	        document.close();
	        InputStream is = new FileInputStream(file);
	        IOUtils.copy(is, os);
	        
	}

	
	public void generatePmpmByPracticeReportPDF(PmpmByPracticeReportFileVM fileVM, OutputStream os) throws SQLException, IOException, DocumentException {
		
		List<PmpmByPracticeReportPrintDataVM> list = getDataForPmpmByPracticeReportPrint(fileVM);
		
		File file = new File("Data export-PMPM By Practice Report.pdf");
		 Document document = new Document();
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();
	        
	        int tableColumnSize = 0;
	        if(fileVM.showFacilityLocationName_pmpmByPractice)
	        	tableColumnSize++;
	        if(fileVM.showProviderName_pmpmByPractice)
	        	tableColumnSize++;
	        if(fileVM.showTotalCost_pmpmByPractice)
	        	tableColumnSize++;
	        if(fileVM.showTotalNumberOfMemberMonth_pmpmByPractice)
	        	tableColumnSize++;
	        if(fileVM.showPMPM_pmpmByPractice)
	        	tableColumnSize++;
	        if(fileVM.showPMPY_pmpmByPractice)
	        	tableColumnSize++;
	        if(fileVM.showTotalPremium_pmpmByPractice)
	        	tableColumnSize++;
	        if(fileVM.showIpaPremium_pmpmByPractice)
	        	tableColumnSize++;
	        if(fileVM.showDifference_pmpmByPractice)
	        	tableColumnSize++;
	        
	        PdfPTable table = new PdfPTable(tableColumnSize);//columns
	        
	        @SuppressWarnings("deprecation")
			BaseColor myColor = WebColors.getRGBColor("#2D4154");
	        
	        Font font = new Font(FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
	        if(fileVM.showFacilityLocationName_pmpmByPractice) {
	        	PdfPCell cell3 = new PdfPCell(new Paragraph("Facility Location Name", font));
	        	cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell3.setVerticalAlignment(Element.ALIGN_TOP);
		        cell3.setBackgroundColor(myColor);
		        cell3.setBorderColor(BaseColor.WHITE);
		        cell3.setBorderWidth(0.1f);
	        	table.addCell(cell3);
	        }
	        if(fileVM.showProviderName_pmpmByPractice) {
		        PdfPCell cell1 = new PdfPCell(new Paragraph("Provider Name", font));
		        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell1.setVerticalAlignment(Element.ALIGN_TOP);
		        cell1.setBackgroundColor(myColor);
		        cell1.setBorderColor(BaseColor.WHITE);
		        cell1.setBorderWidth(0.1f);
		        table.addCell(cell1);
	        }
	        if(fileVM.showTotalCost_pmpmByPractice) {
	        	PdfPCell cell4 = new PdfPCell(new Paragraph("Total Cost", font));
	        	cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell4.setVerticalAlignment(Element.ALIGN_TOP);
		        cell4.setBackgroundColor(myColor);
		        cell4.setBorderColor(BaseColor.WHITE);
		        cell4.setBorderWidth(0.1f);
	        	table.addCell(cell4);
	        }
	        if(fileVM.showTotalNumberOfMemberMonth_pmpmByPractice) {
	        	PdfPCell cell5 = new PdfPCell(new Paragraph("Total Number Of Member Month", font));
	        	cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell5.setVerticalAlignment(Element.ALIGN_TOP);
		        cell5.setBackgroundColor(myColor);
		        cell5.setBorderColor(BaseColor.WHITE);
		        cell5.setBorderWidth(0.1f);
	        	table.addCell(cell5);
	        }
	        if(fileVM.showPMPM_pmpmByPractice) {
	        	PdfPCell cell5 = new PdfPCell(new Paragraph("PMPM", font));
	        	cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell5.setVerticalAlignment(Element.ALIGN_TOP);
		        cell5.setBackgroundColor(myColor);
		        cell5.setBorderColor(BaseColor.WHITE);
		        cell5.setBorderWidth(0.1f);
	        	table.addCell(cell5);
	        }
	        if(fileVM.showPMPY_pmpmByPractice) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("PMPY", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        if(fileVM.showTotalPremium_pmpmByPractice) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("Total Premium", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        if(fileVM.showIpaPremium_pmpmByPractice) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("IPA Premium", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        if(fileVM.showDifference_pmpmByPractice) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("Total Premium - IPA Premium", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        
	        table.setHeaderRows(1);
	        
	        BaseColor oddRowColor = WebColors.getRGBColor("#F3F3F3");
	        
	        int count = 1; //table rows
	        for(PmpmByPracticeReportPrintDataVM vm: list) {
	        	Font rowFont = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
	        	if(fileVM.showFacilityLocationName_pmpmByPractice) {
		        	PdfPCell rowCell3 = new PdfPCell(new Paragraph(vm.getFacilityLocationName(), rowFont));
			        rowCell3.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell3.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell3.setBackgroundColor(oddRowColor);
			        rowCell3.setBorderColor(BaseColor.WHITE);
			        rowCell3.setBorderWidth(0.1f);
			        table.addCell(rowCell3);
		        }
	        	if(fileVM.showProviderName_pmpmByPractice) {
			        PdfPCell rowCell1 = new PdfPCell(new Paragraph(vm.getProviderName(), rowFont));
			        rowCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell1.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell1.setBackgroundColor(oddRowColor);
			        rowCell1.setBorderColor(BaseColor.WHITE);
			        rowCell1.setBorderWidth(0.1f);
			        table.addCell(rowCell1);
	        	}
		        if(fileVM.showTotalCost_pmpmByPractice) {
		        	PdfPCell rowCell4 = new PdfPCell(new Paragraph(vm.getTotalCost(), rowFont));
			        rowCell4.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell4.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell4.setBackgroundColor(oddRowColor);
			        rowCell4.setBorderColor(BaseColor.WHITE);
			        rowCell4.setBorderWidth(0.1f);
			        table.addCell(rowCell4);
		        }
		        if(fileVM.showTotalNumberOfMemberMonth_pmpmByPractice) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getTotalNumberOfMemberMonth(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showPMPM_pmpmByPractice) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getPmpm(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showPMPY_pmpmByPractice) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getPmpy(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showTotalPremium_pmpmByPractice) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getTotalPremium(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showIpaPremium_pmpmByPractice) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getIpaPremium(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showDifference_pmpmByPractice) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getDifference(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        
		        count++;
	        }
	        
	        table.setWidthPercentage(100);
	        document.add(table);
	        document.close();
	        InputStream is = new FileInputStream(file);
	        IOUtils.copy(is, os);
	}
	
	public void generatePmpmByPracticeExpandReportPDF(PmpmByPracticeExpandReportFileVM fileVM, OutputStream os) throws SQLException, IOException, DocumentException {
		
		List<PmpmByPracticeExpandReportPrintDataVM> list = getDataForPmpmByPracticeExpandReportPrint(fileVM);
		
		File file = new File("Data export-PMPM By Practice Expand Report.pdf");
		 Document document = new Document();
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();
	        
	        int tableColumnSize = 0;
	        if(fileVM.showPatientName_pmpmByPracticeExpand)
	        	tableColumnSize++;
	        if(fileVM.showPcpName_pmpmByPracticeExpand)
	        	tableColumnSize++;
	        if(fileVM.showPcpLocation_pmpmByPracticeExpand)
	        	tableColumnSize++;
	        if(fileVM.showMra_pmpmByPracticeExpand)
	        	tableColumnSize++;
	        if(fileVM.showCost_pmpmByPracticeExpand)
	        	tableColumnSize++;
	        if(fileVM.showClaimType_pmpmByPracticeExpand)
	        	tableColumnSize++;
	        
	        PdfPTable table = new PdfPTable(tableColumnSize);//columns
	        
	        @SuppressWarnings("deprecation")
			BaseColor myColor = WebColors.getRGBColor("#2D4154");
	        
	        Font font = new Font(FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
	        if(fileVM.showPatientName_pmpmByPracticeExpand) {
	        	PdfPCell cell3 = new PdfPCell(new Paragraph("Patient Name", font));
	        	cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell3.setVerticalAlignment(Element.ALIGN_TOP);
		        cell3.setBackgroundColor(myColor);
		        cell3.setBorderColor(BaseColor.WHITE);
		        cell3.setBorderWidth(0.1f);
	        	table.addCell(cell3);
	        }
	        if(fileVM.showPcpName_pmpmByPracticeExpand) {
		        PdfPCell cell1 = new PdfPCell(new Paragraph("PCP Name", font));
		        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell1.setVerticalAlignment(Element.ALIGN_TOP);
		        cell1.setBackgroundColor(myColor);
		        cell1.setBorderColor(BaseColor.WHITE);
		        cell1.setBorderWidth(0.1f);
		        table.addCell(cell1);
	        }
	        if(fileVM.showPcpLocation_pmpmByPracticeExpand) {
	        	PdfPCell cell4 = new PdfPCell(new Paragraph("PCP Location", font));
	        	cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell4.setVerticalAlignment(Element.ALIGN_TOP);
		        cell4.setBackgroundColor(myColor);
		        cell4.setBorderColor(BaseColor.WHITE);
		        cell4.setBorderWidth(0.1f);
	        	table.addCell(cell4);
	        }
	        if(fileVM.showMra_pmpmByPracticeExpand) {
	        	PdfPCell cell5 = new PdfPCell(new Paragraph("MRA", font));
	        	cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell5.setVerticalAlignment(Element.ALIGN_TOP);
		        cell5.setBackgroundColor(myColor);
		        cell5.setBorderColor(BaseColor.WHITE);
		        cell5.setBorderWidth(0.1f);
	        	table.addCell(cell5);
	        }
	        if(fileVM.showCost_pmpmByPracticeExpand) {
	        	PdfPCell cell5 = new PdfPCell(new Paragraph("Cost", font));
	        	cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell5.setVerticalAlignment(Element.ALIGN_TOP);
		        cell5.setBackgroundColor(myColor);
		        cell5.setBorderColor(BaseColor.WHITE);
		        cell5.setBorderWidth(0.1f);
	        	table.addCell(cell5);
	        }
	        if(fileVM.showClaimType_pmpmByPracticeExpand) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("Claim Type", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        
	        table.setHeaderRows(1);
	        
	        BaseColor oddRowColor = WebColors.getRGBColor("#F3F3F3");
	        
	        int count = 1; //table rows
	        for(PmpmByPracticeExpandReportPrintDataVM vm: list) {
	        	Font rowFont = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
	        	if(fileVM.showPatientName_pmpmByPracticeExpand) {
		        	PdfPCell rowCell3 = new PdfPCell(new Paragraph(vm.getPatientName(), rowFont));
			        rowCell3.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell3.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell3.setBackgroundColor(oddRowColor);
			        rowCell3.setBorderColor(BaseColor.WHITE);
			        rowCell3.setBorderWidth(0.1f);
			        table.addCell(rowCell3);
		        }
	        	if(fileVM.showPcpName_pmpmByPracticeExpand) {
			        PdfPCell rowCell1 = new PdfPCell(new Paragraph(vm.getPcpName(), rowFont));
			        rowCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell1.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell1.setBackgroundColor(oddRowColor);
			        rowCell1.setBorderColor(BaseColor.WHITE);
			        rowCell1.setBorderWidth(0.1f);
			        table.addCell(rowCell1);
	        	}
		        if(fileVM.showPcpLocation_pmpmByPracticeExpand) {
		        	PdfPCell rowCell4 = new PdfPCell(new Paragraph(vm.getPcpLocation(), rowFont));
			        rowCell4.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell4.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell4.setBackgroundColor(oddRowColor);
			        rowCell4.setBorderColor(BaseColor.WHITE);
			        rowCell4.setBorderWidth(0.1f);
			        table.addCell(rowCell4);
		        }
		        if(fileVM.showMra_pmpmByPracticeExpand) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getMra(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showCost_pmpmByPracticeExpand) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getCost(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showClaimType_pmpmByPracticeExpand) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getClaimType(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        
		        count++;
	        }
	        
	        table.setWidthPercentage(100);
	        document.add(table);
	        document.close();
	        InputStream is = new FileInputStream(file);
	        IOUtils.copy(is, os);
	}
	
	
	public void generateBeneficiariesManagementByLocationReportPDF(BeneficiariesManagementByLocationReportFileVM fileVM, OutputStream os) throws SQLException, IOException, DocumentException {
		
		List<BeneficiariesManagementByLocationReportPrintDataVM> list = getDataForBeneficiariesManagementByLocationReportPrint(fileVM);
		
			File file = new File("Data export-Beneficiaries Management By Location.pdf");
		 	Document document = new Document();
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();
	        
	        int tableColumnSize = 0;
	        if(fileVM.showPcpLocation_beneficiariesManagementByLocation)
	        	tableColumnSize++;
	        if(fileVM.showMra_beneficiariesManagementByLocation)
	        	tableColumnSize++;
	        if(fileVM.showTotalCost_beneficiariesManagementByLocation)
	        	tableColumnSize++;
	        PdfPTable table = new PdfPTable(tableColumnSize);//columns
	        
	        @SuppressWarnings("deprecation")
			BaseColor myColor = WebColors.getRGBColor("#2D4154");
	        
	        Font font = new Font(FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
	        if(fileVM.showPcpLocation_beneficiariesManagementByLocation) {
	        	PdfPCell cell3 = new PdfPCell(new Paragraph("PCP Location", font));
	        	cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell3.setVerticalAlignment(Element.ALIGN_TOP);
		        cell3.setBackgroundColor(myColor);
		        cell3.setBorderColor(BaseColor.WHITE);
		        cell3.setBorderWidth(0.1f);
	        	table.addCell(cell3);
	        }
	        if(fileVM.showMra_beneficiariesManagementByLocation) {
		        PdfPCell cell1 = new PdfPCell(new Paragraph("Average MRA", font));
		        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell1.setVerticalAlignment(Element.ALIGN_TOP);
		        cell1.setBackgroundColor(myColor);
		        cell1.setBorderColor(BaseColor.WHITE);
		        cell1.setBorderWidth(0.1f);
		        table.addCell(cell1);
	        }
	        if(fileVM.showTotalCost_beneficiariesManagementByLocation) {
	        	PdfPCell cell4 = new PdfPCell(new Paragraph("Total Cost", font));
	        	cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell4.setVerticalAlignment(Element.ALIGN_TOP);
		        cell4.setBackgroundColor(myColor);
		        cell4.setBorderColor(BaseColor.WHITE);
		        cell4.setBorderWidth(0.1f);
	        	table.addCell(cell4);
	        }
	        
	        
	        table.setHeaderRows(1);
	        
	        BaseColor oddRowColor = WebColors.getRGBColor("#F3F3F3");
	        
	        int count = 1; //table rows
	        for(BeneficiariesManagementByLocationReportPrintDataVM vm: list) {
	        	Font rowFont = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
	        	if(fileVM.showPcpLocation_beneficiariesManagementByLocation) {
		        	PdfPCell rowCell3 = new PdfPCell(new Paragraph(vm.getPcpLocation(), rowFont));
			        rowCell3.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell3.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell3.setBackgroundColor(oddRowColor);
			        rowCell3.setBorderColor(BaseColor.WHITE);
			        rowCell3.setBorderWidth(0.1f);
			        table.addCell(rowCell3);
		        }
	        	if(fileVM.showMra_beneficiariesManagementByLocation) {
			        PdfPCell rowCell1 = new PdfPCell(new Paragraph(vm.getMra(), rowFont));
			        rowCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell1.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell1.setBackgroundColor(oddRowColor);
			        rowCell1.setBorderColor(BaseColor.WHITE);
			        rowCell1.setBorderWidth(0.1f);
			        table.addCell(rowCell1);
	        	}
		        if(fileVM.showTotalCost_beneficiariesManagementByLocation) {
		        	PdfPCell rowCell4 = new PdfPCell(new Paragraph(vm.getTotalCost(), rowFont));
			        rowCell4.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell4.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell4.setBackgroundColor(oddRowColor);
			        rowCell4.setBorderColor(BaseColor.WHITE);
			        rowCell4.setBorderWidth(0.1f);
			        table.addCell(rowCell4);
		        }
		    
		        
		        count++;
	        }
	        
	        table.setWidthPercentage(100);
	        document.add(table);
	        document.close();
	        InputStream is = new FileInputStream(file);
	        IOUtils.copy(is, os);
	}
	
	public void generateBeneficiariesManagementByClinicReportPDF(BeneficiariesManagementByClinicReportFileVM fileVM, OutputStream os) throws SQLException, IOException, DocumentException {
		
		List<BeneficiariesManagementByClinicReportPrintDataVM> list = getDataForBeneficiariesManagementByClinicReportPrint(fileVM);
		
			File file = new File("Data export-Beneficiaries Management By Clinic.pdf");
		 	Document document = new Document();
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();
	        
	        int tableColumnSize = 0;
	        if(fileVM.showClinicName_beneficiariesManagementByClinic)
	        	tableColumnSize++;
	        if(fileVM.showTotalCost_beneficiariesManagementByClinic)
	        	tableColumnSize++;
	        PdfPTable table = new PdfPTable(tableColumnSize);//columns
	        
	        @SuppressWarnings("deprecation")
			BaseColor myColor = WebColors.getRGBColor("#2D4154");
	        
	        Font font = new Font(FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
	        if(fileVM.showClinicName_beneficiariesManagementByClinic) {
	        	PdfPCell cell3 = new PdfPCell(new Paragraph("Clinic Name", font));
	        	cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell3.setVerticalAlignment(Element.ALIGN_TOP);
		        cell3.setBackgroundColor(myColor);
		        cell3.setBorderColor(BaseColor.WHITE);
		        cell3.setBorderWidth(0.1f);
	        	table.addCell(cell3);
	        }
	        if(fileVM.showTotalCost_beneficiariesManagementByClinic) {
	        	PdfPCell cell4 = new PdfPCell(new Paragraph("Total Cost", font));
	        	cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell4.setVerticalAlignment(Element.ALIGN_TOP);
		        cell4.setBackgroundColor(myColor);
		        cell4.setBorderColor(BaseColor.WHITE);
		        cell4.setBorderWidth(0.1f);
	        	table.addCell(cell4);
	        }
	        
	        
	        table.setHeaderRows(1);
	        
	        BaseColor oddRowColor = WebColors.getRGBColor("#F3F3F3");
	        
	        int count = 1; //table rows
	        for(BeneficiariesManagementByClinicReportPrintDataVM vm: list) {
	        	Font rowFont = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
	        	if(fileVM.showClinicName_beneficiariesManagementByClinic) {
		        	PdfPCell rowCell3 = new PdfPCell(new Paragraph(vm.getClinicName(), rowFont));
			        rowCell3.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell3.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell3.setBackgroundColor(oddRowColor);
			        rowCell3.setBorderColor(BaseColor.WHITE);
			        rowCell3.setBorderWidth(0.1f);
			        table.addCell(rowCell3);
		        }
		        if(fileVM.showTotalCost_beneficiariesManagementByClinic) {
		        	PdfPCell rowCell4 = new PdfPCell(new Paragraph(vm.getTotalCost(), rowFont));
			        rowCell4.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell4.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell4.setBackgroundColor(oddRowColor);
			        rowCell4.setBorderColor(BaseColor.WHITE);
			        rowCell4.setBorderWidth(0.1f);
			        table.addCell(rowCell4);
		        }
		    
		        
		        count++;
	        }
	        
	        table.setWidthPercentage(100);
	        document.add(table);
	        document.close();
	        InputStream is = new FileInputStream(file);
	        IOUtils.copy(is, os);
	}
	
	public void generateBeneficiariesManagementReportPDF(BeneficiariesManagementReportFileVM fileVM, OutputStream os) throws SQLException, IOException, DocumentException {
		
		List<BeneficiariesManagementReportPrintDataVM> list = getDataForBeneficiariesManagementReportPrint(fileVM);
		
		File file = new File("Data export-Beneficiaries Management Report.pdf");
		 Document document = new Document();
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();
	        
	        int tableColumnSize = 0;
	        if(fileVM.showPlanName_beneficiariesManagement)
	        	tableColumnSize++;
	        if(fileVM.showHicn_beneficiariesManagement)
	        	tableColumnSize++;
	        if(fileVM.showPatientName_beneficiariesManagement)
	        	tableColumnSize++;
	        if(fileVM.showDob_beneficiariesManagement)
	        	tableColumnSize++;
	        if(fileVM.showEligibleMonth_beneficiariesManagement)
	        	tableColumnSize++;
	        if(fileVM.showTermedMonth_beneficiariesManagement)
	        	tableColumnSize++;
	        if(fileVM.showPcpName_beneficiariesManagement)
	        	tableColumnSize++;
	        if(fileVM.showPcpLocation_beneficiariesManagement)
	        	tableColumnSize++;
	        if(fileVM.showMra_beneficiariesManagement)
	        	tableColumnSize++;
	        if(fileVM.showTotalCost_beneficiariesManagement)
	        	tableColumnSize++;
	        if(fileVM.showAddress_beneficiariesManagement)
	        	tableColumnSize++;
	        if(fileVM.showRecentAppointmentDate_beneficiariesManagement)
	        	tableColumnSize++;
	        if(fileVM.showNextAppointmentDate_beneficiariesManagement)
	        	tableColumnSize++;
	        if(fileVM.showFacilityLocation_beneficiariesManagement)
	        	tableColumnSize++;
	        if(fileVM.showPhoneNumber_beneficiariesManagement)
	        	tableColumnSize++;
	        if(fileVM.showLastClaimsDate_beneficiariesManagement)
	        	tableColumnSize++;
	        if(fileVM.showIcdCode_beneficiariesManagement)
	        	tableColumnSize++;
	        
	        PdfPTable table = new PdfPTable(tableColumnSize);//columns
	        
	        @SuppressWarnings("deprecation")
			BaseColor myColor = WebColors.getRGBColor("#2D4154");
	        
	        Font font = new Font(FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
	        if(fileVM.showPlanName_beneficiariesManagement) {
	        	PdfPCell cell3 = new PdfPCell(new Paragraph("Plan Name", font));
	        	cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell3.setVerticalAlignment(Element.ALIGN_TOP);
		        cell3.setBackgroundColor(myColor);
		        cell3.setBorderColor(BaseColor.WHITE);
		        cell3.setBorderWidth(0.1f);
	        	table.addCell(cell3);
	        }
	        if(fileVM.showHicn_beneficiariesManagement) {
		        PdfPCell cell1 = new PdfPCell(new Paragraph("HICN/Subscriber ID", font));
		        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell1.setVerticalAlignment(Element.ALIGN_TOP);
		        cell1.setBackgroundColor(myColor);
		        cell1.setBorderColor(BaseColor.WHITE);
		        cell1.setBorderWidth(0.1f);
		        table.addCell(cell1);
	        }
	        if(fileVM.showPatientName_beneficiariesManagement) {
	        	PdfPCell cell4 = new PdfPCell(new Paragraph("Patient Name", font));
	        	cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell4.setVerticalAlignment(Element.ALIGN_TOP);
		        cell4.setBackgroundColor(myColor);
		        cell4.setBorderColor(BaseColor.WHITE);
		        cell4.setBorderWidth(0.1f);
	        	table.addCell(cell4);
	        }
	        if(fileVM.showDob_beneficiariesManagement) {
	        	PdfPCell cell5 = new PdfPCell(new Paragraph("DOB", font));
	        	cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell5.setVerticalAlignment(Element.ALIGN_TOP);
		        cell5.setBackgroundColor(myColor);
		        cell5.setBorderColor(BaseColor.WHITE);
		        cell5.setBorderWidth(0.1f);
	        	table.addCell(cell5);
	        }
	        if(fileVM.showEligibleMonth_beneficiariesManagement) {
	        	PdfPCell cell5 = new PdfPCell(new Paragraph("Eligible Month", font));
	        	cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell5.setVerticalAlignment(Element.ALIGN_TOP);
		        cell5.setBackgroundColor(myColor);
		        cell5.setBorderColor(BaseColor.WHITE);
		        cell5.setBorderWidth(0.1f);
	        	table.addCell(cell5);
	        }
	        if(fileVM.showTermedMonth_beneficiariesManagement) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("Termed Month", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        
	        if(fileVM.showPcpName_beneficiariesManagement) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("PCP Name", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        
	        if(fileVM.showPcpLocation_beneficiariesManagement) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("PCP Location", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        
	        if(fileVM.showMra_beneficiariesManagement) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("MRA", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        
	        if(fileVM.showTotalCost_beneficiariesManagement) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("Total Cost", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        
	        if(fileVM.showAddress_beneficiariesManagement) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("Address", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        
	        if(fileVM.showRecentAppointmentDate_beneficiariesManagement) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("Recent Appointment Date", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        
	        if(fileVM.showNextAppointmentDate_beneficiariesManagement) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("Next Appointment Date", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        
	        if(fileVM.showFacilityLocation_beneficiariesManagement) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("Facility Location", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        
	        if(fileVM.showPhoneNumber_beneficiariesManagement) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("Phone Number", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        
	        if(fileVM.showLastClaimsDate_beneficiariesManagement) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("Last Claims Date", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        
	        if(fileVM.showIcdCode_beneficiariesManagement) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("ICD Code", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        
	        table.setHeaderRows(1);
	        
	        BaseColor oddRowColor = WebColors.getRGBColor("#F3F3F3");
	        
	        int count = 1; //table rows
	        for(BeneficiariesManagementReportPrintDataVM vm: list) {
	        	Font rowFont = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
	        	if(fileVM.showPlanName_beneficiariesManagement) {
		        	PdfPCell rowCell3 = new PdfPCell(new Paragraph(vm.getPlanName(), rowFont));
			        rowCell3.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell3.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell3.setBackgroundColor(oddRowColor);
			        rowCell3.setBorderColor(BaseColor.WHITE);
			        rowCell3.setBorderWidth(0.1f);
			        table.addCell(rowCell3);
		        }
	        	if(fileVM.showHicn_beneficiariesManagement) {
			        PdfPCell rowCell1 = new PdfPCell(new Paragraph(vm.getHicn(), rowFont));
			        rowCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell1.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell1.setBackgroundColor(oddRowColor);
			        rowCell1.setBorderColor(BaseColor.WHITE);
			        rowCell1.setBorderWidth(0.1f);
			        table.addCell(rowCell1);
	        	}
		        if(fileVM.showPatientName_beneficiariesManagement) {
		        	PdfPCell rowCell4 = new PdfPCell(new Paragraph(vm.getPatientName(), rowFont));
			        rowCell4.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell4.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell4.setBackgroundColor(oddRowColor);
			        rowCell4.setBorderColor(BaseColor.WHITE);
			        rowCell4.setBorderWidth(0.1f);
			        table.addCell(rowCell4);
		        }
		        if(fileVM.showDob_beneficiariesManagement) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getDob(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showEligibleMonth_beneficiariesManagement) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getEligibleMonth(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showTermedMonth_beneficiariesManagement) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getTermedMonth(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showPcpName_beneficiariesManagement) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getPcpName(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showPcpLocation_beneficiariesManagement) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getPcpLocation(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showMra_beneficiariesManagement) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getMra(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showTotalCost_beneficiariesManagement) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getTotalCost(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showAddress_beneficiariesManagement) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getAddress(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showRecentAppointmentDate_beneficiariesManagement) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getRecentAppointmentDate(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showNextAppointmentDate_beneficiariesManagement) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getNextAppointmentDate(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showFacilityLocation_beneficiariesManagement) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getFacilityLocation(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showPhoneNumber_beneficiariesManagement) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getPhoneNumber(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showLastClaimsDate_beneficiariesManagement) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getLastClaimsDate(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showIcdCode_beneficiariesManagement) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getIcdCode(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        
		        count++;
	        }
	        
	        table.setWidthPercentage(100);
	        document.add(table);
	        document.close();
	        InputStream is = new FileInputStream(file);
	        IOUtils.copy(is, os);
	}
	
	public void generateBeneficiariesManagementExpandReportPDF(BeneficiariesManagementReportExpandFileVM fileVM, OutputStream os) throws SQLException, IOException, DocumentException {
		
		List<BeneficiariesManagementExpandReportPrintDataVM> list = getDataForBeneficiariesManagementExpandReportPrint(fileVM);
		
		File file = new File("Data export-Beneficiaries Management Details.pdf");
		 Document document = new Document();
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();
	        
	        int tableColumnSize = 0;
	        if(fileVM.showClaimId_beneficiariesManagementExpand)
	        	tableColumnSize++;
	        if(fileVM.showClaimDate_beneficiariesManagementExpand)
	        	tableColumnSize++;
	        if(fileVM.showClaimType_beneficiariesManagementExpand)
	        	tableColumnSize++;
	        if(fileVM.showClinicName_beneficiariesManagementExpand)
	        	tableColumnSize++;
	        if(fileVM.showPcpName_beneficiariesManagementExpand)
	        	tableColumnSize++;
	        if(fileVM.showIcdCodes_beneficiariesManagementExpand)
	        	tableColumnSize++;
	        if(fileVM.showHccCodes_beneficiariesManagementExpand)
	        	tableColumnSize++;
	        if(fileVM.showDrgCode_beneficiariesManagementExpand)
	        	tableColumnSize++;
	        if(fileVM.showBetosCat_beneficiariesManagementExpand)
	        	tableColumnSize++;
	        if(fileVM.showCost_beneficiariesManagementExpand)
	        	tableColumnSize++;
	        
	        PdfPTable table = new PdfPTable(tableColumnSize);//columns
	        
	        @SuppressWarnings("deprecation")
			BaseColor myColor = WebColors.getRGBColor("#2D4154");
	        
	        Font font = new Font(FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
	        if(fileVM.showClaimId_beneficiariesManagementExpand) {
	        	PdfPCell cell3 = new PdfPCell(new Paragraph("Claim Id", font));
	        	cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell3.setVerticalAlignment(Element.ALIGN_TOP);
		        cell3.setBackgroundColor(myColor);
		        cell3.setBorderColor(BaseColor.WHITE);
		        cell3.setBorderWidth(0.1f);
	        	table.addCell(cell3);
	        }
	        if(fileVM.showClaimDate_beneficiariesManagementExpand) {
		        PdfPCell cell1 = new PdfPCell(new Paragraph("Claim Date", font));
		        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell1.setVerticalAlignment(Element.ALIGN_TOP);
		        cell1.setBackgroundColor(myColor);
		        cell1.setBorderColor(BaseColor.WHITE);
		        cell1.setBorderWidth(0.1f);
		        table.addCell(cell1);
	        }
	        if(fileVM.showClaimType_beneficiariesManagementExpand) {
	        	PdfPCell cell4 = new PdfPCell(new Paragraph("Claim Type", font));
	        	cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell4.setVerticalAlignment(Element.ALIGN_TOP);
		        cell4.setBackgroundColor(myColor);
		        cell4.setBorderColor(BaseColor.WHITE);
		        cell4.setBorderWidth(0.1f);
	        	table.addCell(cell4);
	        }
	        if(fileVM.showClinicName_beneficiariesManagementExpand) {
	        	PdfPCell cell5 = new PdfPCell(new Paragraph("Clinic Name", font));
	        	cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell5.setVerticalAlignment(Element.ALIGN_TOP);
		        cell5.setBackgroundColor(myColor);
		        cell5.setBorderColor(BaseColor.WHITE);
		        cell5.setBorderWidth(0.1f);
	        	table.addCell(cell5);
	        }
	        if(fileVM.showPcpName_beneficiariesManagementExpand) {
	        	PdfPCell cell5 = new PdfPCell(new Paragraph("PCP Name", font));
	        	cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell5.setVerticalAlignment(Element.ALIGN_TOP);
		        cell5.setBackgroundColor(myColor);
		        cell5.setBorderColor(BaseColor.WHITE);
		        cell5.setBorderWidth(0.1f);
	        	table.addCell(cell5);
	        }
	        if(fileVM.showIcdCodes_beneficiariesManagementExpand) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("ICD Codes", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        
	        if(fileVM.showHccCodes_beneficiariesManagementExpand) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("HCC Codes", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        
	        if(fileVM.showDrgCode_beneficiariesManagementExpand) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("DRG Code", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        
	        if(fileVM.showBetosCat_beneficiariesManagementExpand) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("Betos Cat", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        
	        if(fileVM.showCost_beneficiariesManagementExpand) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("Cost", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        
	        table.setHeaderRows(1);
	        
	        BaseColor oddRowColor = WebColors.getRGBColor("#F3F3F3");
	        
	        int count = 1; //table rows
	        for(BeneficiariesManagementExpandReportPrintDataVM vm: list) {
	        	Font rowFont = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
	        	if(fileVM.showClaimId_beneficiariesManagementExpand) {
		        	PdfPCell rowCell3 = new PdfPCell(new Paragraph(vm.getClaimId(), rowFont));
			        rowCell3.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell3.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell3.setBackgroundColor(oddRowColor);
			        rowCell3.setBorderColor(BaseColor.WHITE);
			        rowCell3.setBorderWidth(0.1f);
			        table.addCell(rowCell3);
		        }
	        	if(fileVM.showClaimDate_beneficiariesManagementExpand) {
			        PdfPCell rowCell1 = new PdfPCell(new Paragraph(vm.getClaimDate(), rowFont));
			        rowCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell1.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell1.setBackgroundColor(oddRowColor);
			        rowCell1.setBorderColor(BaseColor.WHITE);
			        rowCell1.setBorderWidth(0.1f);
			        table.addCell(rowCell1);
	        	}
		        if(fileVM.showClaimType_beneficiariesManagementExpand) {
		        	PdfPCell rowCell4 = new PdfPCell(new Paragraph(vm.getClaimType(), rowFont));
			        rowCell4.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell4.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell4.setBackgroundColor(oddRowColor);
			        rowCell4.setBorderColor(BaseColor.WHITE);
			        rowCell4.setBorderWidth(0.1f);
			        table.addCell(rowCell4);
		        }
		        if(fileVM.showClinicName_beneficiariesManagementExpand) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getClinicName(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showPcpName_beneficiariesManagementExpand) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getPcpName(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showIcdCodes_beneficiariesManagementExpand) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getIcdCodes(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showHccCodes_beneficiariesManagementExpand) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getHccCodes(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showDrgCode_beneficiariesManagementExpand) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getDrgCode(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showBetosCat_beneficiariesManagementExpand) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getBetosCat(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showCost_beneficiariesManagementExpand) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getCost(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        
		        count++;
	        }
	        
	        table.setWidthPercentage(100);
	        document.add(table);
	        document.close();
	        InputStream is = new FileInputStream(file);
	        IOUtils.copy(is, os);
	}
	
	public void generateBeneficiariesManagementByDoctorExpandReportPDF(BeneficiariesManagementByDoctorReportExpandFileVM fileVM, OutputStream os) throws SQLException, IOException, DocumentException {
		
		List<BeneficiariesManagementExpandReportPrintDataVM> list = getDataForBeneficiariesManagementByDoctorExpandReportPrint(fileVM);
		
		File file = new File("Data export-Beneficiaries Management By Doctor Details.pdf");
		 Document document = new Document();
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();
	        
	        int tableColumnSize = 0;
	        if(fileVM.showClaimId_beneficiariesManagementByDoctorExpand)
	        	tableColumnSize++;
	        if(fileVM.showClaimDate_beneficiariesManagementByDoctorExpand)
	        	tableColumnSize++;
	        if(fileVM.showClaimType_beneficiariesManagementByDoctorExpand)
	        	tableColumnSize++;
	        if(fileVM.showClinicName_beneficiariesManagementByDoctorExpand)
	        	tableColumnSize++;
	        if(fileVM.showPcpName_beneficiariesManagementByDoctorExpand)
	        	tableColumnSize++;
	        if(fileVM.showIcdCodes_beneficiariesManagementByDoctorExpand)
	        	tableColumnSize++;
	        if(fileVM.showHccCodes_beneficiariesManagementByDoctorExpand)
	        	tableColumnSize++;
	        if(fileVM.showDrgCode_beneficiariesManagementByDoctorExpand)
	        	tableColumnSize++;
	        if(fileVM.showBetosCat_beneficiariesManagementByDoctorExpand)
	        	tableColumnSize++;
	        if(fileVM.showCost_beneficiariesManagementByDoctorExpand)
	        	tableColumnSize++;
	        
	        PdfPTable table = new PdfPTable(tableColumnSize);//columns
	        
	        @SuppressWarnings("deprecation")
			BaseColor myColor = WebColors.getRGBColor("#2D4154");
	        
	        Font font = new Font(FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
	        if(fileVM.showClaimId_beneficiariesManagementByDoctorExpand) {
	        	PdfPCell cell3 = new PdfPCell(new Paragraph("Claim Id", font));
	        	cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell3.setVerticalAlignment(Element.ALIGN_TOP);
		        cell3.setBackgroundColor(myColor);
		        cell3.setBorderColor(BaseColor.WHITE);
		        cell3.setBorderWidth(0.1f);
	        	table.addCell(cell3);
	        }
	        if(fileVM.showClaimDate_beneficiariesManagementByDoctorExpand) {
		        PdfPCell cell1 = new PdfPCell(new Paragraph("Claim Date", font));
		        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell1.setVerticalAlignment(Element.ALIGN_TOP);
		        cell1.setBackgroundColor(myColor);
		        cell1.setBorderColor(BaseColor.WHITE);
		        cell1.setBorderWidth(0.1f);
		        table.addCell(cell1);
	        }
	        if(fileVM.showClaimType_beneficiariesManagementByDoctorExpand) {
	        	PdfPCell cell4 = new PdfPCell(new Paragraph("Claim Type", font));
	        	cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell4.setVerticalAlignment(Element.ALIGN_TOP);
		        cell4.setBackgroundColor(myColor);
		        cell4.setBorderColor(BaseColor.WHITE);
		        cell4.setBorderWidth(0.1f);
	        	table.addCell(cell4);
	        }
	        if(fileVM.showClinicName_beneficiariesManagementByDoctorExpand) {
	        	PdfPCell cell5 = new PdfPCell(new Paragraph("Clinic Name", font));
	        	cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell5.setVerticalAlignment(Element.ALIGN_TOP);
		        cell5.setBackgroundColor(myColor);
		        cell5.setBorderColor(BaseColor.WHITE);
		        cell5.setBorderWidth(0.1f);
	        	table.addCell(cell5);
	        }
	        if(fileVM.showPcpName_beneficiariesManagementByDoctorExpand) {
	        	PdfPCell cell5 = new PdfPCell(new Paragraph("PCP Name", font));
	        	cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell5.setVerticalAlignment(Element.ALIGN_TOP);
		        cell5.setBackgroundColor(myColor);
		        cell5.setBorderColor(BaseColor.WHITE);
		        cell5.setBorderWidth(0.1f);
	        	table.addCell(cell5);
	        }
	        if(fileVM.showIcdCodes_beneficiariesManagementByDoctorExpand) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("ICD Codes", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        
	        if(fileVM.showHccCodes_beneficiariesManagementByDoctorExpand) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("HCC Codes", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        
	        if(fileVM.showDrgCode_beneficiariesManagementByDoctorExpand) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("DRG Code", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        
	        if(fileVM.showBetosCat_beneficiariesManagementByDoctorExpand) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("Betos Cat", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        
	        if(fileVM.showCost_beneficiariesManagementByDoctorExpand) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("Cost", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        
	        table.setHeaderRows(1);
	        
	        BaseColor oddRowColor = WebColors.getRGBColor("#F3F3F3");
	        
	        int count = 1; //table rows
	        for(BeneficiariesManagementExpandReportPrintDataVM vm: list) {
	        	Font rowFont = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
	        	if(fileVM.showClaimId_beneficiariesManagementByDoctorExpand) {
		        	PdfPCell rowCell3 = new PdfPCell(new Paragraph(vm.getClaimId(), rowFont));
			        rowCell3.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell3.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell3.setBackgroundColor(oddRowColor);
			        rowCell3.setBorderColor(BaseColor.WHITE);
			        rowCell3.setBorderWidth(0.1f);
			        table.addCell(rowCell3);
		        }
	        	if(fileVM.showClaimDate_beneficiariesManagementByDoctorExpand) {
			        PdfPCell rowCell1 = new PdfPCell(new Paragraph(vm.getClaimDate(), rowFont));
			        rowCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell1.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell1.setBackgroundColor(oddRowColor);
			        rowCell1.setBorderColor(BaseColor.WHITE);
			        rowCell1.setBorderWidth(0.1f);
			        table.addCell(rowCell1);
	        	}
		        if(fileVM.showClaimType_beneficiariesManagementByDoctorExpand) {
		        	PdfPCell rowCell4 = new PdfPCell(new Paragraph(vm.getClaimType(), rowFont));
			        rowCell4.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell4.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell4.setBackgroundColor(oddRowColor);
			        rowCell4.setBorderColor(BaseColor.WHITE);
			        rowCell4.setBorderWidth(0.1f);
			        table.addCell(rowCell4);
		        }
		        if(fileVM.showClinicName_beneficiariesManagementByDoctorExpand) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getClinicName(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showPcpName_beneficiariesManagementByDoctorExpand) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getPcpName(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showIcdCodes_beneficiariesManagementByDoctorExpand) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getIcdCodes(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showHccCodes_beneficiariesManagementByDoctorExpand) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getHccCodes(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showDrgCode_beneficiariesManagementByDoctorExpand) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getDrgCode(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showBetosCat_beneficiariesManagementByDoctorExpand) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getBetosCat(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showCost_beneficiariesManagementByDoctorExpand) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getCost(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        
		        count++;
	        }
	        
	        table.setWidthPercentage(100);
	        document.add(table);
	        document.close();
	        InputStream is = new FileInputStream(file);
	        IOUtils.copy(is, os);
	}
	
public void generateBeneficiariesManagementByLocationExpandReportPDF(BeneficiariesManagementByLocationReportExpandFileVM fileVM, OutputStream os) throws SQLException, IOException, DocumentException {
		
		List<BeneficiariesManagementExpandReportPrintDataVM> list = getDataForBeneficiariesManagementByLocationExpandReportPrint(fileVM);
		
		File file = new File("Data export-Beneficiaries Management By Doctor Details.pdf");
		 Document document = new Document();
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();
	        
	        int tableColumnSize = 0;
	        if(fileVM.showClaimId_beneficiariesManagementByLocationExpand)
	        	tableColumnSize++;
	        if(fileVM.showClaimDate_beneficiariesManagementByLocationExpand)
	        	tableColumnSize++;
	        if(fileVM.showClaimType_beneficiariesManagementByLocationExpand)
	        	tableColumnSize++;
	        if(fileVM.showClinicName_beneficiariesManagementByLocationExpand)
	        	tableColumnSize++;
	        if(fileVM.showPcpLocation_beneficiariesManagementByLocationExpand)
	        	tableColumnSize++;
	        if(fileVM.showIcdCodes_beneficiariesManagementByLocationExpand)
	        	tableColumnSize++;
	        if(fileVM.showHccCodes_beneficiariesManagementByLocationExpand)
	        	tableColumnSize++;
	        if(fileVM.showDrgCode_beneficiariesManagementByLocationExpand)
	        	tableColumnSize++;
	        if(fileVM.showBetosCat_beneficiariesManagementByLocationExpand)
	        	tableColumnSize++;
	        if(fileVM.showCost_beneficiariesManagementByLocationExpand)
	        	tableColumnSize++;
	        
	        PdfPTable table = new PdfPTable(tableColumnSize);//columns
	        
	        @SuppressWarnings("deprecation")
			BaseColor myColor = WebColors.getRGBColor("#2D4154");
	        
	        Font font = new Font(FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
	        if(fileVM.showClaimId_beneficiariesManagementByLocationExpand) {
	        	PdfPCell cell3 = new PdfPCell(new Paragraph("Claim Id", font));
	        	cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell3.setVerticalAlignment(Element.ALIGN_TOP);
		        cell3.setBackgroundColor(myColor);
		        cell3.setBorderColor(BaseColor.WHITE);
		        cell3.setBorderWidth(0.1f);
	        	table.addCell(cell3);
	        }
	        if(fileVM.showClaimDate_beneficiariesManagementByLocationExpand) {
		        PdfPCell cell1 = new PdfPCell(new Paragraph("Claim Date", font));
		        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell1.setVerticalAlignment(Element.ALIGN_TOP);
		        cell1.setBackgroundColor(myColor);
		        cell1.setBorderColor(BaseColor.WHITE);
		        cell1.setBorderWidth(0.1f);
		        table.addCell(cell1);
	        }
	        if(fileVM.showClaimType_beneficiariesManagementByLocationExpand) {
	        	PdfPCell cell4 = new PdfPCell(new Paragraph("Claim Type", font));
	        	cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell4.setVerticalAlignment(Element.ALIGN_TOP);
		        cell4.setBackgroundColor(myColor);
		        cell4.setBorderColor(BaseColor.WHITE);
		        cell4.setBorderWidth(0.1f);
	        	table.addCell(cell4);
	        }
	        if(fileVM.showClinicName_beneficiariesManagementByLocationExpand) {
	        	PdfPCell cell5 = new PdfPCell(new Paragraph("Clinic Name", font));
	        	cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell5.setVerticalAlignment(Element.ALIGN_TOP);
		        cell5.setBackgroundColor(myColor);
		        cell5.setBorderColor(BaseColor.WHITE);
		        cell5.setBorderWidth(0.1f);
	        	table.addCell(cell5);
	        }
	        if(fileVM.showPcpLocation_beneficiariesManagementByLocationExpand) {
	        	PdfPCell cell5 = new PdfPCell(new Paragraph("PCP Location", font));
	        	cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell5.setVerticalAlignment(Element.ALIGN_TOP);
		        cell5.setBackgroundColor(myColor);
		        cell5.setBorderColor(BaseColor.WHITE);
		        cell5.setBorderWidth(0.1f);
	        	table.addCell(cell5);
	        }
	        if(fileVM.showIcdCodes_beneficiariesManagementByLocationExpand) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("ICD Codes", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        
	        if(fileVM.showHccCodes_beneficiariesManagementByLocationExpand) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("HCC Codes", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        
	        if(fileVM.showDrgCode_beneficiariesManagementByLocationExpand) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("DRG Code", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        
	        if(fileVM.showBetosCat_beneficiariesManagementByLocationExpand) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("Betos Cat", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        
	        if(fileVM.showCost_beneficiariesManagementByLocationExpand) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("Cost", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        
	        table.setHeaderRows(1);
	        
	        BaseColor oddRowColor = WebColors.getRGBColor("#F3F3F3");
	        
	        int count = 1; //table rows
	        for(BeneficiariesManagementExpandReportPrintDataVM vm: list) {
	        	Font rowFont = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
	        	if(fileVM.showClaimId_beneficiariesManagementByLocationExpand) {
		        	PdfPCell rowCell3 = new PdfPCell(new Paragraph(vm.getClaimId(), rowFont));
			        rowCell3.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell3.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell3.setBackgroundColor(oddRowColor);
			        rowCell3.setBorderColor(BaseColor.WHITE);
			        rowCell3.setBorderWidth(0.1f);
			        table.addCell(rowCell3);
		        }
	        	if(fileVM.showClaimDate_beneficiariesManagementByLocationExpand) {
			        PdfPCell rowCell1 = new PdfPCell(new Paragraph(vm.getClaimDate(), rowFont));
			        rowCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell1.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell1.setBackgroundColor(oddRowColor);
			        rowCell1.setBorderColor(BaseColor.WHITE);
			        rowCell1.setBorderWidth(0.1f);
			        table.addCell(rowCell1);
	        	}
		        if(fileVM.showClaimType_beneficiariesManagementByLocationExpand) {
		        	PdfPCell rowCell4 = new PdfPCell(new Paragraph(vm.getClaimType(), rowFont));
			        rowCell4.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell4.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell4.setBackgroundColor(oddRowColor);
			        rowCell4.setBorderColor(BaseColor.WHITE);
			        rowCell4.setBorderWidth(0.1f);
			        table.addCell(rowCell4);
		        }
		        if(fileVM.showClinicName_beneficiariesManagementByLocationExpand) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getClinicName(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showPcpLocation_beneficiariesManagementByLocationExpand) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getPcpLocation(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showIcdCodes_beneficiariesManagementByLocationExpand) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getIcdCodes(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showHccCodes_beneficiariesManagementByLocationExpand) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getHccCodes(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showDrgCode_beneficiariesManagementByLocationExpand) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getDrgCode(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showBetosCat_beneficiariesManagementByLocationExpand) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getBetosCat(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showCost_beneficiariesManagementByLocationExpand) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getCost(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        
		        count++;
	        }
	        
	        table.setWidthPercentage(100);
	        document.add(table);
	        document.close();
	        InputStream is = new FileInputStream(file);
	        IOUtils.copy(is, os);
	}

public void generateBeneficiariesManagementByClinicExpandReportPDF(BeneficiariesManagementByClinicReportExpandFileVM fileVM, OutputStream os) throws SQLException, IOException, DocumentException {
	
	List<BeneficiariesManagementExpandReportPrintDataVM> list = getDataForBeneficiariesManagementByClinicExpandReportPrint(fileVM);
	
	File file = new File("Data export-Beneficiaries Management By Clinic Details.pdf");
	 Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();
        
        int tableColumnSize = 0;
        if(fileVM.showClaimId_beneficiariesManagementByClinicExpand)
        	tableColumnSize++;
        if(fileVM.showClaimDate_beneficiariesManagementByClinicExpand)
        	tableColumnSize++;
        if(fileVM.showClaimType_beneficiariesManagementByClinicExpand)
        	tableColumnSize++;
        if(fileVM.showClinicName_beneficiariesManagementByClinicExpand)
        	tableColumnSize++;
        if(fileVM.showPcpName_beneficiariesManagementByClinicExpand)
        	tableColumnSize++;
        if(fileVM.showIcdCodes_beneficiariesManagementByClinicExpand)
        	tableColumnSize++;
        if(fileVM.showHccCodes_beneficiariesManagementByClinicExpand)
        	tableColumnSize++;
        if(fileVM.showDrgCode_beneficiariesManagementByClinicExpand)
        	tableColumnSize++;
        if(fileVM.showBetosCat_beneficiariesManagementByClinicExpand)
        	tableColumnSize++;
        if(fileVM.showCost_beneficiariesManagementByClinicExpand)
        	tableColumnSize++;
        
        PdfPTable table = new PdfPTable(tableColumnSize);//columns
        
        @SuppressWarnings("deprecation")
		BaseColor myColor = WebColors.getRGBColor("#2D4154");
        
        Font font = new Font(FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
        if(fileVM.showClaimId_beneficiariesManagementByClinicExpand) {
        	PdfPCell cell3 = new PdfPCell(new Paragraph("Claim Id", font));
        	cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
	        cell3.setVerticalAlignment(Element.ALIGN_TOP);
	        cell3.setBackgroundColor(myColor);
	        cell3.setBorderColor(BaseColor.WHITE);
	        cell3.setBorderWidth(0.1f);
        	table.addCell(cell3);
        }
        if(fileVM.showClaimDate_beneficiariesManagementByClinicExpand) {
	        PdfPCell cell1 = new PdfPCell(new Paragraph("Claim Date", font));
	        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
	        cell1.setVerticalAlignment(Element.ALIGN_TOP);
	        cell1.setBackgroundColor(myColor);
	        cell1.setBorderColor(BaseColor.WHITE);
	        cell1.setBorderWidth(0.1f);
	        table.addCell(cell1);
        }
        if(fileVM.showClaimType_beneficiariesManagementByClinicExpand) {
        	PdfPCell cell4 = new PdfPCell(new Paragraph("Claim Type", font));
        	cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
	        cell4.setVerticalAlignment(Element.ALIGN_TOP);
	        cell4.setBackgroundColor(myColor);
	        cell4.setBorderColor(BaseColor.WHITE);
	        cell4.setBorderWidth(0.1f);
        	table.addCell(cell4);
        }
        if(fileVM.showClinicName_beneficiariesManagementByClinicExpand) {
        	PdfPCell cell5 = new PdfPCell(new Paragraph("Clinic Name", font));
        	cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
	        cell5.setVerticalAlignment(Element.ALIGN_TOP);
	        cell5.setBackgroundColor(myColor);
	        cell5.setBorderColor(BaseColor.WHITE);
	        cell5.setBorderWidth(0.1f);
        	table.addCell(cell5);
        }
        if(fileVM.showPcpName_beneficiariesManagementByClinicExpand) {
        	PdfPCell cell5 = new PdfPCell(new Paragraph("PCP Name", font));
        	cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
	        cell5.setVerticalAlignment(Element.ALIGN_TOP);
	        cell5.setBackgroundColor(myColor);
	        cell5.setBorderColor(BaseColor.WHITE);
	        cell5.setBorderWidth(0.1f);
        	table.addCell(cell5);
        }
        if(fileVM.showIcdCodes_beneficiariesManagementByClinicExpand) {
        	PdfPCell cell7 = new PdfPCell(new Paragraph("ICD Codes", font));
        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
	        cell7.setVerticalAlignment(Element.ALIGN_TOP);
	        cell7.setBackgroundColor(myColor);
	        cell7.setBorderColor(BaseColor.WHITE);
	        cell7.setBorderWidth(0.1f);
        	table.addCell(cell7);
        }
        
        if(fileVM.showHccCodes_beneficiariesManagementByClinicExpand) {
        	PdfPCell cell7 = new PdfPCell(new Paragraph("HCC Codes", font));
        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
	        cell7.setVerticalAlignment(Element.ALIGN_TOP);
	        cell7.setBackgroundColor(myColor);
	        cell7.setBorderColor(BaseColor.WHITE);
	        cell7.setBorderWidth(0.1f);
        	table.addCell(cell7);
        }
        
        if(fileVM.showDrgCode_beneficiariesManagementByClinicExpand) {
        	PdfPCell cell7 = new PdfPCell(new Paragraph("DRG Code", font));
        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
	        cell7.setVerticalAlignment(Element.ALIGN_TOP);
	        cell7.setBackgroundColor(myColor);
	        cell7.setBorderColor(BaseColor.WHITE);
	        cell7.setBorderWidth(0.1f);
        	table.addCell(cell7);
        }
        
        if(fileVM.showBetosCat_beneficiariesManagementByClinicExpand) {
        	PdfPCell cell7 = new PdfPCell(new Paragraph("Betos Cat", font));
        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
	        cell7.setVerticalAlignment(Element.ALIGN_TOP);
	        cell7.setBackgroundColor(myColor);
	        cell7.setBorderColor(BaseColor.WHITE);
	        cell7.setBorderWidth(0.1f);
        	table.addCell(cell7);
        }
        
        if(fileVM.showCost_beneficiariesManagementByClinicExpand) {
        	PdfPCell cell7 = new PdfPCell(new Paragraph("Cost", font));
        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
	        cell7.setVerticalAlignment(Element.ALIGN_TOP);
	        cell7.setBackgroundColor(myColor);
	        cell7.setBorderColor(BaseColor.WHITE);
	        cell7.setBorderWidth(0.1f);
        	table.addCell(cell7);
        }
        
        table.setHeaderRows(1);
        
        BaseColor oddRowColor = WebColors.getRGBColor("#F3F3F3");
        
        int count = 1; //table rows
        for(BeneficiariesManagementExpandReportPrintDataVM vm: list) {
        	Font rowFont = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
        	if(fileVM.showClaimId_beneficiariesManagementByClinicExpand) {
	        	PdfPCell rowCell3 = new PdfPCell(new Paragraph(vm.getClaimId(), rowFont));
		        rowCell3.setHorizontalAlignment(Element.ALIGN_LEFT);
		        rowCell3.setVerticalAlignment(Element.ALIGN_TOP);
		        if(count%2 > 0)
		        rowCell3.setBackgroundColor(oddRowColor);
		        rowCell3.setBorderColor(BaseColor.WHITE);
		        rowCell3.setBorderWidth(0.1f);
		        table.addCell(rowCell3);
	        }
        	if(fileVM.showClaimDate_beneficiariesManagementByClinicExpand) {
		        PdfPCell rowCell1 = new PdfPCell(new Paragraph(vm.getClaimDate(), rowFont));
		        rowCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
		        rowCell1.setVerticalAlignment(Element.ALIGN_TOP);
		        if(count%2 > 0)
		        rowCell1.setBackgroundColor(oddRowColor);
		        rowCell1.setBorderColor(BaseColor.WHITE);
		        rowCell1.setBorderWidth(0.1f);
		        table.addCell(rowCell1);
        	}
	        if(fileVM.showClaimType_beneficiariesManagementByClinicExpand) {
	        	PdfPCell rowCell4 = new PdfPCell(new Paragraph(vm.getClaimType(), rowFont));
		        rowCell4.setHorizontalAlignment(Element.ALIGN_LEFT);
		        rowCell4.setVerticalAlignment(Element.ALIGN_TOP);
		        if(count%2 > 0)
		        rowCell4.setBackgroundColor(oddRowColor);
		        rowCell4.setBorderColor(BaseColor.WHITE);
		        rowCell4.setBorderWidth(0.1f);
		        table.addCell(rowCell4);
	        }
	        if(fileVM.showClinicName_beneficiariesManagementByClinicExpand) {
	        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getClinicName(), rowFont));
		        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
		        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
		        if(count%2 > 0)
		        rowCell5.setBackgroundColor(oddRowColor);
		        rowCell5.setBorderColor(BaseColor.WHITE);
		        rowCell5.setBorderWidth(0.1f);
		        table.addCell(rowCell5);
	        }
	        if(fileVM.showPcpName_beneficiariesManagementByClinicExpand) {
	        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getPcpName(), rowFont));
		        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
		        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
		        if(count%2 > 0)
		        rowCell5.setBackgroundColor(oddRowColor);
		        rowCell5.setBorderColor(BaseColor.WHITE);
		        rowCell5.setBorderWidth(0.1f);
		        table.addCell(rowCell5);
	        }
	        if(fileVM.showIcdCodes_beneficiariesManagementByClinicExpand) {
	        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getIcdCodes(), rowFont));
		        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
		        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
		        if(count%2 > 0)
		        rowCell5.setBackgroundColor(oddRowColor);
		        rowCell5.setBorderColor(BaseColor.WHITE);
		        rowCell5.setBorderWidth(0.1f);
		        table.addCell(rowCell5);
	        }
	        if(fileVM.showHccCodes_beneficiariesManagementByClinicExpand) {
	        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getHccCodes(), rowFont));
		        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
		        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
		        if(count%2 > 0)
		        rowCell5.setBackgroundColor(oddRowColor);
		        rowCell5.setBorderColor(BaseColor.WHITE);
		        rowCell5.setBorderWidth(0.1f);
		        table.addCell(rowCell5);
	        }
	        if(fileVM.showDrgCode_beneficiariesManagementByClinicExpand) {
	        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getDrgCode(), rowFont));
		        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
		        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
		        if(count%2 > 0)
		        rowCell5.setBackgroundColor(oddRowColor);
		        rowCell5.setBorderColor(BaseColor.WHITE);
		        rowCell5.setBorderWidth(0.1f);
		        table.addCell(rowCell5);
	        }
	        if(fileVM.showBetosCat_beneficiariesManagementByClinicExpand) {
	        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getBetosCat(), rowFont));
		        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
		        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
		        if(count%2 > 0)
		        rowCell5.setBackgroundColor(oddRowColor);
		        rowCell5.setBorderColor(BaseColor.WHITE);
		        rowCell5.setBorderWidth(0.1f);
		        table.addCell(rowCell5);
	        }
	        if(fileVM.showCost_beneficiariesManagementByClinicExpand) {
	        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getCost(), rowFont));
		        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
		        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
		        if(count%2 > 0)
		        rowCell5.setBackgroundColor(oddRowColor);
		        rowCell5.setBorderColor(BaseColor.WHITE);
		        rowCell5.setBorderWidth(0.1f);
		        table.addCell(rowCell5);
	        }
	        
	        count++;
        }
        
        table.setWidthPercentage(100);
        document.add(table);
        document.close();
        InputStream is = new FileInputStream(file);
        IOUtils.copy(is, os);
}


	public void generateBeneficiariesManagementByDoctorPDF(BeneficiariesManagementByDoctorFileVM fileVM, OutputStream os) throws SQLException, IOException, DocumentException {
		
		List<BeneficiariesManagementByDoctorPrintDataVM> list = getDataForBeneficiariesManagementByDoctorPrint(fileVM);
		
		File file = new File("Data export-Beneficiaries Management By Doctor.pdf");
		 Document document = new Document();
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();
	        
	        int tableColumnSize = 0;
	        if(fileVM.showPcpName_beneficiariesManagementByDoctor)
	        	tableColumnSize++;
	        if(fileVM.showPcpLocation_beneficiariesManagementByDoctor)
	        	tableColumnSize++;
	        if(fileVM.showAverageMra_beneficiariesManagementByDoctor)
	        	tableColumnSize++;
	        if(fileVM.showTotalCost_beneficiariesManagementByDoctor)
	        	tableColumnSize++;
	        
	        PdfPTable table = new PdfPTable(tableColumnSize);//columns
	        
	        @SuppressWarnings("deprecation")
			BaseColor myColor = WebColors.getRGBColor("#2D4154");
	        
	        Font font = new Font(FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
	        if(fileVM.showPcpName_beneficiariesManagementByDoctor) {
	        	PdfPCell cell3 = new PdfPCell(new Paragraph("PCP Name", font));
	        	cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell3.setVerticalAlignment(Element.ALIGN_TOP);
		        cell3.setBackgroundColor(myColor);
		        cell3.setBorderColor(BaseColor.WHITE);
		        cell3.setBorderWidth(0.1f);
	        	table.addCell(cell3);
	        }
	        if(fileVM.showPcpLocation_beneficiariesManagementByDoctor) {
		        PdfPCell cell1 = new PdfPCell(new Paragraph("PCP Location", font));
		        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell1.setVerticalAlignment(Element.ALIGN_TOP);
		        cell1.setBackgroundColor(myColor);
		        cell1.setBorderColor(BaseColor.WHITE);
		        cell1.setBorderWidth(0.1f);
		        table.addCell(cell1);
	        }
	        if(fileVM.showAverageMra_beneficiariesManagementByDoctor) {
	        	PdfPCell cell4 = new PdfPCell(new Paragraph("Average MRA", font));
	        	cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell4.setVerticalAlignment(Element.ALIGN_TOP);
		        cell4.setBackgroundColor(myColor);
		        cell4.setBorderColor(BaseColor.WHITE);
		        cell4.setBorderWidth(0.1f);
	        	table.addCell(cell4);
	        }
	        if(fileVM.showTotalCost_beneficiariesManagementByDoctor) {
	        	PdfPCell cell5 = new PdfPCell(new Paragraph("Total Cost", font));
	        	cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell5.setVerticalAlignment(Element.ALIGN_TOP);
		        cell5.setBackgroundColor(myColor);
		        cell5.setBorderColor(BaseColor.WHITE);
		        cell5.setBorderWidth(0.1f);
	        	table.addCell(cell5);
	        }
	        
	        table.setHeaderRows(1);
	        
	        BaseColor oddRowColor = WebColors.getRGBColor("#F3F3F3");
	        
	        int count = 1; //table rows
	        for(BeneficiariesManagementByDoctorPrintDataVM vm: list) {
	        	Font rowFont = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
	        	if(fileVM.showPcpName_beneficiariesManagementByDoctor) {
		        	PdfPCell rowCell3 = new PdfPCell(new Paragraph(vm.getPcpName(), rowFont));
			        rowCell3.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell3.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell3.setBackgroundColor(oddRowColor);
			        rowCell3.setBorderColor(BaseColor.WHITE);
			        rowCell3.setBorderWidth(0.1f);
			        table.addCell(rowCell3);
		        }
	        	if(fileVM.showPcpLocation_beneficiariesManagementByDoctor) {
			        PdfPCell rowCell1 = new PdfPCell(new Paragraph(vm.getPcpLocation(), rowFont));
			        rowCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell1.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell1.setBackgroundColor(oddRowColor);
			        rowCell1.setBorderColor(BaseColor.WHITE);
			        rowCell1.setBorderWidth(0.1f);
			        table.addCell(rowCell1);
	        	}
		        if(fileVM.showAverageMra_beneficiariesManagementByDoctor) {
		        	PdfPCell rowCell4 = new PdfPCell(new Paragraph(vm.getAverageMra(), rowFont));
			        rowCell4.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell4.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell4.setBackgroundColor(oddRowColor);
			        rowCell4.setBorderColor(BaseColor.WHITE);
			        rowCell4.setBorderWidth(0.1f);
			        table.addCell(rowCell4);
		        }
		        if(fileVM.showTotalCost_beneficiariesManagementByDoctor) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getTotalCost(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        
		        count++;
	        }
	        
	        table.setWidthPercentage(100);
	        document.add(table);
	        document.close();
	        InputStream is = new FileInputStream(file);
	        IOUtils.copy(is, os);
	}
	
	public void generateDuplicateClaimsExpandPDF(DuplicateClaimsExpandFileVM fileVM, OutputStream os) throws SQLException, IOException, DocumentException {
		
		List<DuplicateClaimsExpandPrintDataVM> list = getDataForDuplicateClaimsExpandPrint(fileVM);
		
		File file = new File("Data export-Duplicate Claims Details.pdf");
		 Document document = new Document();
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();
	        
	        int tableColumnSize = 0;
	        if(fileVM.showClaimId_duplicateClaimsExpand)
	        	tableColumnSize++;
	        if(fileVM.showClaimDate_duplicateClaimsExpand)
	        	tableColumnSize++;
	        if(fileVM.showClaimType_duplicateClaimsExpand)
	        	tableColumnSize++;
	        if(fileVM.showClinicName_duplicateClaimsExpand)
	        	tableColumnSize++;
	        if(fileVM.showProviderName_duplicateClaimsExpand)
	        	tableColumnSize++;
	        if(fileVM.showBetosCat_duplicateClaimsExpand)
	        	tableColumnSize++;
	        if(fileVM.showDrgCode_duplicateClaimsExpand)
	        	tableColumnSize++;
	        if(fileVM.showIcdCodes_duplicateClaimsExpand)
	        	tableColumnSize++;
	        if(fileVM.showHccCodes_duplicateClaimsExpand)
	        	tableColumnSize++;
	        if(fileVM.showCost_duplicateClaimsExpand)
	        	tableColumnSize++;
	        
	        PdfPTable table = new PdfPTable(tableColumnSize);//columns
	        
	        @SuppressWarnings("deprecation")
			BaseColor myColor = WebColors.getRGBColor("#2D4154");
	        
	        Font font = new Font(FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
	        if(fileVM.showClaimId_duplicateClaimsExpand) {
	        	PdfPCell cell3 = new PdfPCell(new Paragraph("Claim Id", font));
	        	cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell3.setVerticalAlignment(Element.ALIGN_TOP);
		        cell3.setBackgroundColor(myColor);
		        cell3.setBorderColor(BaseColor.WHITE);
		        cell3.setBorderWidth(0.1f);
	        	table.addCell(cell3);
	        }
	        if(fileVM.showClaimDate_duplicateClaimsExpand) {
		        PdfPCell cell1 = new PdfPCell(new Paragraph("Claim Date", font));
		        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell1.setVerticalAlignment(Element.ALIGN_TOP);
		        cell1.setBackgroundColor(myColor);
		        cell1.setBorderColor(BaseColor.WHITE);
		        cell1.setBorderWidth(0.1f);
		        table.addCell(cell1);
	        }
	        if(fileVM.showClaimType_duplicateClaimsExpand) {
	        	PdfPCell cell4 = new PdfPCell(new Paragraph("Claim Type", font));
	        	cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell4.setVerticalAlignment(Element.ALIGN_TOP);
		        cell4.setBackgroundColor(myColor);
		        cell4.setBorderColor(BaseColor.WHITE);
		        cell4.setBorderWidth(0.1f);
	        	table.addCell(cell4);
	        }
	        if(fileVM.showClinicName_duplicateClaimsExpand) {
	        	PdfPCell cell5 = new PdfPCell(new Paragraph("Clinic Name", font));
	        	cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell5.setVerticalAlignment(Element.ALIGN_TOP);
		        cell5.setBackgroundColor(myColor);
		        cell5.setBorderColor(BaseColor.WHITE);
		        cell5.setBorderWidth(0.1f);
	        	table.addCell(cell5);
	        }
	        if(fileVM.showProviderName_duplicateClaimsExpand) {
	        	PdfPCell cell5 = new PdfPCell(new Paragraph("Provider Name", font));
	        	cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell5.setVerticalAlignment(Element.ALIGN_TOP);
		        cell5.setBackgroundColor(myColor);
		        cell5.setBorderColor(BaseColor.WHITE);
		        cell5.setBorderWidth(0.1f);
	        	table.addCell(cell5);
	        }
	        if(fileVM.showBetosCat_duplicateClaimsExpand) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("Betos Cat", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        
	        if(fileVM.showDrgCode_duplicateClaimsExpand) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("DRG Code", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        
	        if(fileVM.showIcdCodes_duplicateClaimsExpand) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("ICD 9/10 Code(s)", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        
	        if(fileVM.showHccCodes_duplicateClaimsExpand) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("HCC Code(s)", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        
	        if(fileVM.showCost_duplicateClaimsExpand) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("Cost", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        
	        table.setHeaderRows(1);
	        
	        BaseColor oddRowColor = WebColors.getRGBColor("#F3F3F3");
	        
	        int count = 1; //table rows
	        for(DuplicateClaimsExpandPrintDataVM vm: list) {
	        	Font rowFont = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
	        	if(fileVM.showClaimId_duplicateClaimsExpand) {
		        	PdfPCell rowCell3 = new PdfPCell(new Paragraph(vm.getClaimId(), rowFont));
			        rowCell3.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell3.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell3.setBackgroundColor(oddRowColor);
			        rowCell3.setBorderColor(BaseColor.WHITE);
			        rowCell3.setBorderWidth(0.1f);
			        table.addCell(rowCell3);
		        }
	        	if(fileVM.showClaimDate_duplicateClaimsExpand) {
			        PdfPCell rowCell1 = new PdfPCell(new Paragraph(vm.getClaimDate(), rowFont));
			        rowCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell1.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell1.setBackgroundColor(oddRowColor);
			        rowCell1.setBorderColor(BaseColor.WHITE);
			        rowCell1.setBorderWidth(0.1f);
			        table.addCell(rowCell1);
	        	}
		        if(fileVM.showClaimType_duplicateClaimsExpand) {
		        	PdfPCell rowCell4 = new PdfPCell(new Paragraph(vm.getClaimType(), rowFont));
			        rowCell4.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell4.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell4.setBackgroundColor(oddRowColor);
			        rowCell4.setBorderColor(BaseColor.WHITE);
			        rowCell4.setBorderWidth(0.1f);
			        table.addCell(rowCell4);
		        }
		        if(fileVM.showClinicName_duplicateClaimsExpand) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getClinicName(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showProviderName_duplicateClaimsExpand) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getProviderName(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showBetosCat_duplicateClaimsExpand) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getBetosCat(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showDrgCode_duplicateClaimsExpand) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getDrgCode(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showIcdCodes_duplicateClaimsExpand) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getIcdCodes(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showHccCodes_duplicateClaimsExpand) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getHccCodes(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showCost_duplicateClaimsExpand) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getCost(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        
		        count++;
	        }
	        
	        table.setWidthPercentage(100);
	        document.add(table);
	        document.close();
	        InputStream is = new FileInputStream(file);
	        IOUtils.copy(is, os);
	}
	
	public void generateMembershipManagementPDF(MembershipManagementFileVM fileVM, OutputStream os) throws SQLException, IOException, DocumentException {
		
		List<MembershipManagementPrintDataVM> list = getMembershipManagementPrint(fileVM);
		
		File file = new File("Data export-MembershipManagement.pdf");
		 Document document = new Document();
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();
	        
	        int tableColumnSize = 0;
	        if(fileVM.showPlanName_membershipManagement)
	        	tableColumnSize++;
	        if(fileVM.showMedicareId_membershipManagement)
	        	tableColumnSize++;
	        if(fileVM.showInsuranceId_membershipManagement)
	        	tableColumnSize++;
	        if(fileVM.showPatientName_membershipManagement)
	        	tableColumnSize++;
	        if(fileVM.showPatientDob_membershipManagement)
	        	tableColumnSize++;
	        if(fileVM.showAssignedPcp_membershipManagement)
	        	tableColumnSize++;
	        if(fileVM.showPcpLocation_membershipManagement)
	        	tableColumnSize++;
	        if(fileVM.showIpaEffectiveDate_membershipManagement)
	        	tableColumnSize++;
	        if(fileVM.showMra_membershipManagement)
	        	tableColumnSize++;
	        if(fileVM.showTotalPatientCost_membershipManagement)
	        	tableColumnSize++;
	        
	        PdfPTable table = new PdfPTable(tableColumnSize);//columns
	        
	        @SuppressWarnings("deprecation")
			BaseColor myColor = WebColors.getRGBColor("#2D4154");
	        
	        Font font = new Font(FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
	        if(fileVM.showPlanName_membershipManagement) {
	        	PdfPCell cell3 = new PdfPCell(new Paragraph("Plan Name", font));
	        	cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell3.setVerticalAlignment(Element.ALIGN_TOP);
		        cell3.setBackgroundColor(myColor);
		        cell3.setBorderColor(BaseColor.WHITE);
		        cell3.setBorderWidth(0.1f);
	        	table.addCell(cell3);
	        }
	        if(fileVM.showMedicareId_membershipManagement) {
		        PdfPCell cell1 = new PdfPCell(new Paragraph("Medicare ID", font));
		        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell1.setVerticalAlignment(Element.ALIGN_TOP);
		        cell1.setBackgroundColor(myColor);
		        cell1.setBorderColor(BaseColor.WHITE);
		        cell1.setBorderWidth(0.1f);
		        table.addCell(cell1);
	        }
	        if(fileVM.showInsuranceId_membershipManagement) {
	        	PdfPCell cell4 = new PdfPCell(new Paragraph("Insurance ID", font));
	        	cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell4.setVerticalAlignment(Element.ALIGN_TOP);
		        cell4.setBackgroundColor(myColor);
		        cell4.setBorderColor(BaseColor.WHITE);
		        cell4.setBorderWidth(0.1f);
	        	table.addCell(cell4);
	        }
	        if(fileVM.showPatientName_membershipManagement) {
	        	PdfPCell cell5 = new PdfPCell(new Paragraph("Patient Name", font));
	        	cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell5.setVerticalAlignment(Element.ALIGN_TOP);
		        cell5.setBackgroundColor(myColor);
		        cell5.setBorderColor(BaseColor.WHITE);
		        cell5.setBorderWidth(0.1f);
	        	table.addCell(cell5);
	        }
	        if(fileVM.showPatientDob_membershipManagement) {
	        	PdfPCell cell5 = new PdfPCell(new Paragraph("Patient DOB", font));
	        	cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell5.setVerticalAlignment(Element.ALIGN_TOP);
		        cell5.setBackgroundColor(myColor);
		        cell5.setBorderColor(BaseColor.WHITE);
		        cell5.setBorderWidth(0.1f);
	        	table.addCell(cell5);
	        }
	        if(fileVM.showAssignedPcp_membershipManagement) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("Assigned PCP", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        if(fileVM.showPcpLocation_membershipManagement) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("PCP Location", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        if(fileVM.showIpaEffectiveDate_membershipManagement) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("IPA Effective Date", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        if(fileVM.showMra_membershipManagement) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("MRA", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        if(fileVM.showTotalPatientCost_membershipManagement) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("Total Patient Cost", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        
	        
	        table.setHeaderRows(1);
	        
	        BaseColor oddRowColor = WebColors.getRGBColor("#F3F3F3");
	        
	        int count = 1; //table rows
	        for(MembershipManagementPrintDataVM vm: list) {
	        	Font rowFont = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
	        	if(fileVM.showPlanName_membershipManagement) {
		        	PdfPCell rowCell3 = new PdfPCell(new Paragraph(vm.getPlanName(), rowFont));
			        rowCell3.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell3.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell3.setBackgroundColor(oddRowColor);
			        rowCell3.setBorderColor(BaseColor.WHITE);
			        rowCell3.setBorderWidth(0.1f);
			        table.addCell(rowCell3);
		        }
	        	if(fileVM.showMedicareId_membershipManagement) {
			        PdfPCell rowCell1 = new PdfPCell(new Paragraph(vm.getMedicareId(), rowFont));
			        rowCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell1.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell1.setBackgroundColor(oddRowColor);
			        rowCell1.setBorderColor(BaseColor.WHITE);
			        rowCell1.setBorderWidth(0.1f);
			        table.addCell(rowCell1);
	        	}
		        if(fileVM.showInsuranceId_membershipManagement) {
		        	PdfPCell rowCell4 = new PdfPCell(new Paragraph(vm.getInsuranceId(), rowFont));
			        rowCell4.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell4.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell4.setBackgroundColor(oddRowColor);
			        rowCell4.setBorderColor(BaseColor.WHITE);
			        rowCell4.setBorderWidth(0.1f);
			        table.addCell(rowCell4);
		        }
		        if(fileVM.showPatientName_membershipManagement) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getPatientName(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showPatientDob_membershipManagement) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getPatientDob(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showAssignedPcp_membershipManagement) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getAssignedPcp(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showPcpLocation_membershipManagement) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getPcpLocation(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showIpaEffectiveDate_membershipManagement) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getIpaEffectiveDate(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showMra_membershipManagement) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getMra(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showTotalPatientCost_membershipManagement) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getTotalPatientCost(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        
		        count++;
	        }
	        
	        table.setWidthPercentage(100);
	        document.add(table);
	        document.close();
	        InputStream is = new FileInputStream(file);
	        IOUtils.copy(is, os);
	}
	
	public void generateSummaryReportPDF(SummaryReportFileVM fileVM, OutputStream os) throws SQLException, IOException, DocumentException {
		
		List<SummaryReportPrintDataVM> list = getDataForSummaryReportPrint(fileVM);
		
		File file = new File("Data export-Summary Report.pdf");
		 Document document = new Document();
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();
	        
	        int tableColumnSize = 0;
	        
	          if(fileVM.showPcpLocation_summary)
	        	tableColumnSize++;
	    	  if(fileVM.showMonth_summary)
	    		tableColumnSize++;
		      if(fileVM.showMembers_summary)
		    	  tableColumnSize++;
		      if(fileVM.showMaPremium_summary)
		    	  tableColumnSize++;
		      if(fileVM.showPartDPremium_summary)
		    	  tableColumnSize++;
		      if(fileVM.showTotalPremium_summary)
		    	  tableColumnSize++;
		      if(fileVM.showIpaPremium_summary)
		    	  tableColumnSize++;
		      if(fileVM.showPcpCap_summary)
		    	  tableColumnSize++;
		      if(fileVM.showSpecCost_summary)
		    	  tableColumnSize++;
		      if(fileVM.showProfClaims_summary)
		    	  tableColumnSize++;
		      if(fileVM.showInstClaims_summary)
		    	  tableColumnSize++;
		      if(fileVM.showRxClaims_summary)
		    	  tableColumnSize++;
		      if(fileVM.showIbnrDollars_summary)
		    	  tableColumnSize++;
		      if(fileVM.showReinsurancePremium_summary)
		    	  tableColumnSize++;
		      if(fileVM.showSpecCap_summary)
		    	  tableColumnSize++;
		      if(fileVM.showTotalExpenses_summary)
		    	  tableColumnSize++;
		      if(fileVM.showReinsuranceRecovered_summary)
		    	  tableColumnSize++;
		      if(fileVM.showRxAdmin_summary)
		    	  tableColumnSize++;
		      if(fileVM.showSilverSneakerUtilization_summary)
		    	  tableColumnSize++;
		      if(fileVM.showPba_summary)
		    	  tableColumnSize++;
		      if(fileVM.showHumanaAtHome_summary)
		    	  tableColumnSize++;
		      if(fileVM.showDentalFFS_summary)
		    	  tableColumnSize++;
		      
	        PdfPTable table = new PdfPTable(tableColumnSize);//columns
	        
	        @SuppressWarnings("deprecation")
			BaseColor myColor = WebColors.getRGBColor("#2D4154");
	        
	        Font font = new Font(FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
	        if(fileVM.showPcpLocation_summary) {
	        	PdfPCell cell3 = new PdfPCell(new Paragraph("PCP Location", font));
	        	cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell3.setVerticalAlignment(Element.ALIGN_TOP);
		        cell3.setBackgroundColor(myColor);
		        cell3.setBorderColor(BaseColor.WHITE);
		        cell3.setBorderWidth(0.1f);
	        	table.addCell(cell3);
	        }
	        if(fileVM.showMonth_summary) {
		        PdfPCell cell1 = new PdfPCell(new Paragraph("Month", font));
		        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell1.setVerticalAlignment(Element.ALIGN_TOP);
		        cell1.setBackgroundColor(myColor);
		        cell1.setBorderColor(BaseColor.WHITE);
		        cell1.setBorderWidth(0.1f);
		        table.addCell(cell1);
	        }
	        if(fileVM.showMembers_summary) {
	        	PdfPCell cell4 = new PdfPCell(new Paragraph("Members", font));
	        	cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell4.setVerticalAlignment(Element.ALIGN_TOP);
		        cell4.setBackgroundColor(myColor);
		        cell4.setBorderColor(BaseColor.WHITE);
		        cell4.setBorderWidth(0.1f);
	        	table.addCell(cell4);
	        }
	        if(fileVM.showMaPremium_summary) {
	        	PdfPCell cell5 = new PdfPCell(new Paragraph("Ma Premium", font));
	        	cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell5.setVerticalAlignment(Element.ALIGN_TOP);
		        cell5.setBackgroundColor(myColor);
		        cell5.setBorderColor(BaseColor.WHITE);
		        cell5.setBorderWidth(0.1f);
	        	table.addCell(cell5);
	        }
	        if(fileVM.showPartDPremium_summary) {
	        	PdfPCell cell5 = new PdfPCell(new Paragraph("Part D Premium", font));
	        	cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell5.setVerticalAlignment(Element.ALIGN_TOP);
		        cell5.setBackgroundColor(myColor);
		        cell5.setBorderColor(BaseColor.WHITE);
		        cell5.setBorderWidth(0.1f);
	        	table.addCell(cell5);
	        }
	        if(fileVM.showTotalPremium_summary) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("Total Premium", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        if(fileVM.showIpaPremium_summary) {
	        	PdfPCell cell8 = new PdfPCell(new Paragraph("IPA Premium", font));
	        	cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell8.setVerticalAlignment(Element.ALIGN_TOP);
		        cell8.setBackgroundColor(myColor);
		        cell8.setBorderColor(BaseColor.WHITE);
		        cell8.setBorderWidth(0.1f);
	        	table.addCell(cell8);
	        }
	        if(fileVM.showPcpCap_summary) {
	        	PdfPCell cell8 = new PdfPCell(new Paragraph("PCP Cap", font));
	        	cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell8.setVerticalAlignment(Element.ALIGN_TOP);
		        cell8.setBackgroundColor(myColor);
		        cell8.setBorderColor(BaseColor.WHITE);
		        cell8.setBorderWidth(0.1f);
	        	table.addCell(cell8);
	        }
	        if(fileVM.showSpecCost_summary) {
	        	PdfPCell cell8 = new PdfPCell(new Paragraph("Spec Cost", font));
	        	cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell8.setVerticalAlignment(Element.ALIGN_TOP);
		        cell8.setBackgroundColor(myColor);
		        cell8.setBorderColor(BaseColor.WHITE);
		        cell8.setBorderWidth(0.1f);
	        	table.addCell(cell8);
	        }
	        
	        if(fileVM.showProfClaims_summary) {
	        	PdfPCell cell8 = new PdfPCell(new Paragraph("Prof Claims", font));
	        	cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell8.setVerticalAlignment(Element.ALIGN_TOP);
		        cell8.setBackgroundColor(myColor);
		        cell8.setBorderColor(BaseColor.WHITE);
		        cell8.setBorderWidth(0.1f);
	        	table.addCell(cell8);
	        }
	        
	        if(fileVM.showInstClaims_summary) {
	        	PdfPCell cell8 = new PdfPCell(new Paragraph("Inst Claims", font));
	        	cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell8.setVerticalAlignment(Element.ALIGN_TOP);
		        cell8.setBackgroundColor(myColor);
		        cell8.setBorderColor(BaseColor.WHITE);
		        cell8.setBorderWidth(0.1f);
	        	table.addCell(cell8);
	        }
	        
	        if(fileVM.showRxClaims_summary) {
	        	PdfPCell cell8 = new PdfPCell(new Paragraph("Rx Claims", font));
	        	cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell8.setVerticalAlignment(Element.ALIGN_TOP);
		        cell8.setBackgroundColor(myColor);
		        cell8.setBorderColor(BaseColor.WHITE);
		        cell8.setBorderWidth(0.1f);
	        	table.addCell(cell8);
	        }
	        
	        if(fileVM.showIbnrDollars_summary) {
	        	PdfPCell cell8 = new PdfPCell(new Paragraph("IBNR Dollars", font));
	        	cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell8.setVerticalAlignment(Element.ALIGN_TOP);
		        cell8.setBackgroundColor(myColor);
		        cell8.setBorderColor(BaseColor.WHITE);
		        cell8.setBorderWidth(0.1f);
	        	table.addCell(cell8);
	        }
	        
	        if(fileVM.showReinsurancePremium_summary) {
	        	PdfPCell cell8 = new PdfPCell(new Paragraph("Reinsurance Premium", font));
	        	cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell8.setVerticalAlignment(Element.ALIGN_TOP);
		        cell8.setBackgroundColor(myColor);
		        cell8.setBorderColor(BaseColor.WHITE);
		        cell8.setBorderWidth(0.1f);
	        	table.addCell(cell8);
	        }
	        
	        if(fileVM.showSpecCap_summary) {
	        	PdfPCell cell8 = new PdfPCell(new Paragraph("Spec Cap", font));
	        	cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell8.setVerticalAlignment(Element.ALIGN_TOP);
		        cell8.setBackgroundColor(myColor);
		        cell8.setBorderColor(BaseColor.WHITE);
		        cell8.setBorderWidth(0.1f);
	        	table.addCell(cell8);
	        }
	        
	        if(fileVM.showTotalExpenses_summary) {
	        	PdfPCell cell8 = new PdfPCell(new Paragraph("Total Expenses", font));
	        	cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell8.setVerticalAlignment(Element.ALIGN_TOP);
		        cell8.setBackgroundColor(myColor);
		        cell8.setBorderColor(BaseColor.WHITE);
		        cell8.setBorderWidth(0.1f);
	        	table.addCell(cell8);
	        }
	        
	        if(fileVM.showReinsuranceRecovered_summary) {
	        	PdfPCell cell8 = new PdfPCell(new Paragraph("Reinsurance Recovered", font));
	        	cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell8.setVerticalAlignment(Element.ALIGN_TOP);
		        cell8.setBackgroundColor(myColor);
		        cell8.setBorderColor(BaseColor.WHITE);
		        cell8.setBorderWidth(0.1f);
	        	table.addCell(cell8);
	        }
	        
	        if(fileVM.showRxAdmin_summary) {
	        	PdfPCell cell8 = new PdfPCell(new Paragraph("Rx Admin", font));
	        	cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell8.setVerticalAlignment(Element.ALIGN_TOP);
		        cell8.setBackgroundColor(myColor);
		        cell8.setBorderColor(BaseColor.WHITE);
		        cell8.setBorderWidth(0.1f);
	        	table.addCell(cell8);
	        }
	        
	        if(fileVM.showSilverSneakerUtilization_summary) {
	        	PdfPCell cell8 = new PdfPCell(new Paragraph("Silver Sneaker Utilization", font));
	        	cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell8.setVerticalAlignment(Element.ALIGN_TOP);
		        cell8.setBackgroundColor(myColor);
		        cell8.setBorderColor(BaseColor.WHITE);
		        cell8.setBorderWidth(0.1f);
	        	table.addCell(cell8);
	        }
	        
	        if(fileVM.showPba_summary) {
	        	PdfPCell cell8 = new PdfPCell(new Paragraph("PBA", font));
	        	cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell8.setVerticalAlignment(Element.ALIGN_TOP);
		        cell8.setBackgroundColor(myColor);
		        cell8.setBorderColor(BaseColor.WHITE);
		        cell8.setBorderWidth(0.1f);
	        	table.addCell(cell8);
	        }
	        
	        if(fileVM.showHumanaAtHome_summary) {
	        	PdfPCell cell8 = new PdfPCell(new Paragraph("Humana At Home", font));
	        	cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell8.setVerticalAlignment(Element.ALIGN_TOP);
		        cell8.setBackgroundColor(myColor);
		        cell8.setBorderColor(BaseColor.WHITE);
		        cell8.setBorderWidth(0.1f);
	        	table.addCell(cell8);
	        }
	        
	        if(fileVM.showDentalFFS_summary) {
	        	PdfPCell cell8 = new PdfPCell(new Paragraph("Dental FFS", font));
	        	cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell8.setVerticalAlignment(Element.ALIGN_TOP);
		        cell8.setBackgroundColor(myColor);
		        cell8.setBorderColor(BaseColor.WHITE);
		        cell8.setBorderWidth(0.1f);
	        	table.addCell(cell8);
	        }
	        
	        table.setHeaderRows(1);
	        
	        BaseColor oddRowColor = WebColors.getRGBColor("#F3F3F3");
	        
	        int count = 1; //table rows
	        for(SummaryReportPrintDataVM vm: list) {
	        	Font rowFont = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
	        	if(fileVM.showPcpLocation_summary) {
		        	PdfPCell rowCell3 = new PdfPCell(new Paragraph(vm.getPcpLocation(), rowFont));
			        rowCell3.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell3.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell3.setBackgroundColor(oddRowColor);
			        rowCell3.setBorderColor(BaseColor.WHITE);
			        rowCell3.setBorderWidth(0.1f);
			        table.addCell(rowCell3);
		        }
	        	if(fileVM.showMonth_summary) {
			        PdfPCell rowCell1 = new PdfPCell(new Paragraph(vm.getMonth(), rowFont));
			        rowCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell1.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell1.setBackgroundColor(oddRowColor);
			        rowCell1.setBorderColor(BaseColor.WHITE);
			        rowCell1.setBorderWidth(0.1f);
			        table.addCell(rowCell1);
	        	}
		        if(fileVM.showMembers_summary) {
		        	PdfPCell rowCell4 = new PdfPCell(new Paragraph(vm.getMembers(), rowFont));
			        rowCell4.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell4.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell4.setBackgroundColor(oddRowColor);
			        rowCell4.setBorderColor(BaseColor.WHITE);
			        rowCell4.setBorderWidth(0.1f);
			        table.addCell(rowCell4);
		        }
		        if(fileVM.showMaPremium_summary) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getMaPremium(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showPartDPremium_summary) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getPartDPremium(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showTotalPremium_summary) {
		        	PdfPCell rowCell6 = new PdfPCell(new Paragraph(vm.getTotalPremium(), rowFont));
			        rowCell6.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell6.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell6.setBackgroundColor(oddRowColor);
			        rowCell6.setBorderColor(BaseColor.WHITE);
			        rowCell6.setBorderWidth(0.1f);
			        table.addCell(rowCell6);
		        }
		        if(fileVM.showIpaPremium_summary) {
		        	PdfPCell rowCell8 = new PdfPCell(new Paragraph(vm.getIpaPremium(), rowFont));
			        rowCell8.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell8.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell8.setBackgroundColor(oddRowColor);
			        rowCell8.setBorderColor(BaseColor.WHITE);
			        rowCell8.setBorderWidth(0.1f);
			        table.addCell(rowCell8);
		        }
		        if(fileVM.showPcpCap_summary) {
		        	PdfPCell rowCell8 = new PdfPCell(new Paragraph(vm.getPcpCap(), rowFont));
			        rowCell8.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell8.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell8.setBackgroundColor(oddRowColor);
			        rowCell8.setBorderColor(BaseColor.WHITE);
			        rowCell8.setBorderWidth(0.1f);
			        table.addCell(rowCell8);
		        }
		        if(fileVM.showSpecCost_summary) {
		        	PdfPCell rowCell8 = new PdfPCell(new Paragraph(vm.getSpecCost(), rowFont));
			        rowCell8.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell8.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell8.setBackgroundColor(oddRowColor);
			        rowCell8.setBorderColor(BaseColor.WHITE);
			        rowCell8.setBorderWidth(0.1f);
			        table.addCell(rowCell8);
		        }
		        if(fileVM.showProfClaims_summary) {
		        	PdfPCell rowCell8 = new PdfPCell(new Paragraph(vm.getProfClaims(), rowFont));
			        rowCell8.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell8.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell8.setBackgroundColor(oddRowColor);
			        rowCell8.setBorderColor(BaseColor.WHITE);
			        rowCell8.setBorderWidth(0.1f);
			        table.addCell(rowCell8);
		        }
		        if(fileVM.showInstClaims_summary) {
		        	PdfPCell rowCell8 = new PdfPCell(new Paragraph(vm.getInstClaims(), rowFont));
			        rowCell8.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell8.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell8.setBackgroundColor(oddRowColor);
			        rowCell8.setBorderColor(BaseColor.WHITE);
			        rowCell8.setBorderWidth(0.1f);
			        table.addCell(rowCell8);
		        }
		        if(fileVM.showRxClaims_summary) {
		        	PdfPCell rowCell8 = new PdfPCell(new Paragraph(vm.getRxClaims(), rowFont));
			        rowCell8.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell8.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell8.setBackgroundColor(oddRowColor);
			        rowCell8.setBorderColor(BaseColor.WHITE);
			        rowCell8.setBorderWidth(0.1f);
			        table.addCell(rowCell8);
		        }
		        if(fileVM.showIbnrDollars_summary) {
		        	PdfPCell rowCell8 = new PdfPCell(new Paragraph(vm.getIbnrDollars(), rowFont));
			        rowCell8.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell8.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell8.setBackgroundColor(oddRowColor);
			        rowCell8.setBorderColor(BaseColor.WHITE);
			        rowCell8.setBorderWidth(0.1f);
			        table.addCell(rowCell8);
		        }
		        if(fileVM.showReinsurancePremium_summary) {
		        	PdfPCell rowCell8 = new PdfPCell(new Paragraph(vm.getReinsurancePremium(), rowFont));
			        rowCell8.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell8.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell8.setBackgroundColor(oddRowColor);
			        rowCell8.setBorderColor(BaseColor.WHITE);
			        rowCell8.setBorderWidth(0.1f);
			        table.addCell(rowCell8);
		        }
		        if(fileVM.showSpecCap_summary) {
		        	PdfPCell rowCell8 = new PdfPCell(new Paragraph(vm.getSpecCap(), rowFont));
			        rowCell8.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell8.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell8.setBackgroundColor(oddRowColor);
			        rowCell8.setBorderColor(BaseColor.WHITE);
			        rowCell8.setBorderWidth(0.1f);
			        table.addCell(rowCell8);
		        }
		        if(fileVM.showTotalExpenses_summary) {
		        	PdfPCell rowCell8 = new PdfPCell(new Paragraph(vm.getTotalExpenses(), rowFont));
			        rowCell8.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell8.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell8.setBackgroundColor(oddRowColor);
			        rowCell8.setBorderColor(BaseColor.WHITE);
			        rowCell8.setBorderWidth(0.1f);
			        table.addCell(rowCell8);
		        }
		        if(fileVM.showReinsuranceRecovered_summary) {
		        	PdfPCell rowCell8 = new PdfPCell(new Paragraph(vm.getReinsuranceRecovered(), rowFont));
			        rowCell8.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell8.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell8.setBackgroundColor(oddRowColor);
			        rowCell8.setBorderColor(BaseColor.WHITE);
			        rowCell8.setBorderWidth(0.1f);
			        table.addCell(rowCell8);
		        }
		        if(fileVM.showRxAdmin_summary) {
		        	PdfPCell rowCell8 = new PdfPCell(new Paragraph(vm.getRxAdmin(), rowFont));
			        rowCell8.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell8.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell8.setBackgroundColor(oddRowColor);
			        rowCell8.setBorderColor(BaseColor.WHITE);
			        rowCell8.setBorderWidth(0.1f);
			        table.addCell(rowCell8);
		        }
		        if(fileVM.showSilverSneakerUtilization_summary) {
		        	PdfPCell rowCell8 = new PdfPCell(new Paragraph(vm.getSilverSneakerUtilization(), rowFont));
			        rowCell8.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell8.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell8.setBackgroundColor(oddRowColor);
			        rowCell8.setBorderColor(BaseColor.WHITE);
			        rowCell8.setBorderWidth(0.1f);
			        table.addCell(rowCell8);
		        }
		        if(fileVM.showPba_summary) {
		        	PdfPCell rowCell8 = new PdfPCell(new Paragraph(vm.getPba(), rowFont));
			        rowCell8.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell8.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell8.setBackgroundColor(oddRowColor);
			        rowCell8.setBorderColor(BaseColor.WHITE);
			        rowCell8.setBorderWidth(0.1f);
			        table.addCell(rowCell8);
		        }
		        if(fileVM.showHumanaAtHome_summary) {
		        	PdfPCell rowCell8 = new PdfPCell(new Paragraph(vm.getHumanaAtHome(), rowFont));
			        rowCell8.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell8.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell8.setBackgroundColor(oddRowColor);
			        rowCell8.setBorderColor(BaseColor.WHITE);
			        rowCell8.setBorderWidth(0.1f);
			        table.addCell(rowCell8);
		        }
		        if(fileVM.showDentalFFS_summary) {
		        	PdfPCell rowCell8 = new PdfPCell(new Paragraph(vm.getDentalFFS(), rowFont));
			        rowCell8.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell8.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell8.setBackgroundColor(oddRowColor);
			        rowCell8.setBorderColor(BaseColor.WHITE);
			        rowCell8.setBorderWidth(0.1f);
			        table.addCell(rowCell8);
		        }
		        count++;
	        }
	        
	        table.setWidthPercentage(100);
	        document.add(table);
	        document.close();
	        InputStream is = new FileInputStream(file);
	        IOUtils.copy(is, os);
	}
	
	public void generateAdmissionsReportExpandPDF(AdmissionsReportExpandFileVM fileVM, OutputStream os) throws SQLException, IOException, DocumentException {
		
		List<AdmissionsReportExpandVM> list = getDataForAdmissionsReportExpandXL(fileVM);
		
		File file = new File("Data export-Admissions Report.pdf");
		 Document document = new Document();
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();
	        
	        int tableColumnSize = 0;
	        if(fileVM.showClaimId_admissionsExpand)
	        	tableColumnSize++;
	        if(fileVM.showClaimDate_admissionsExpand)
	        	tableColumnSize++;
	        if(fileVM.showClaimType_admissionsExpand)
	        	tableColumnSize++;
	        if(fileVM.showClinicName_admissionsExpand)
	        	tableColumnSize++;
	        if(fileVM.showPcpName_admissionsExpand)
	        	tableColumnSize++;
	        if(fileVM.showIcdCodes_admissionsExpand)
	        	tableColumnSize++;
	        if(fileVM.showHccCodes_admissionsExpand)
	        	tableColumnSize++;
	        if(fileVM.showDrgCode_admissionsExpand)
	        	tableColumnSize++;
	        if(fileVM.showBetosCat_admissionsExpand)
	        	tableColumnSize++;
	        if(fileVM.showCost_admissionsExpand)
	        	tableColumnSize++;
	        
	        PdfPTable table = new PdfPTable(tableColumnSize);//columns
	        
	        @SuppressWarnings("deprecation")
			BaseColor myColor = WebColors.getRGBColor("#2D4154");
	        
	        Font font = new Font(FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
	        if(fileVM.showClaimId_admissionsExpand) {
	        	PdfPCell cell3 = new PdfPCell(new Paragraph("Claim Id", font));
	        	cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell3.setVerticalAlignment(Element.ALIGN_TOP);
		        cell3.setBackgroundColor(myColor);
		        cell3.setBorderColor(BaseColor.WHITE);
		        cell3.setBorderWidth(0.1f);
	        	table.addCell(cell3);
	        }
	        if(fileVM.showClaimDate_admissionsExpand) {
		        PdfPCell cell1 = new PdfPCell(new Paragraph("Claim Date", font));
		        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell1.setVerticalAlignment(Element.ALIGN_TOP);
		        cell1.setBackgroundColor(myColor);
		        cell1.setBorderColor(BaseColor.WHITE);
		        cell1.setBorderWidth(0.1f);
		        table.addCell(cell1);
	        }
	        if(fileVM.showClaimType_admissionsExpand) {
	        	PdfPCell cell4 = new PdfPCell(new Paragraph("Claim Type", font));
	        	cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell4.setVerticalAlignment(Element.ALIGN_TOP);
		        cell4.setBackgroundColor(myColor);
		        cell4.setBorderColor(BaseColor.WHITE);
		        cell4.setBorderWidth(0.1f);
	        	table.addCell(cell4);
	        }
	        if(fileVM.showClinicName_admissionsExpand) {
	        	PdfPCell cell5 = new PdfPCell(new Paragraph("Clinic Name", font));
	        	cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell5.setVerticalAlignment(Element.ALIGN_TOP);
		        cell5.setBackgroundColor(myColor);
		        cell5.setBorderColor(BaseColor.WHITE);
		        cell5.setBorderWidth(0.1f);
	        	table.addCell(cell5);
	        }
	        if(fileVM.showPcpName_admissionsExpand) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("PCP Name", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        if(fileVM.showIcdCodes_admissionsExpand) {
	        	PdfPCell cell8 = new PdfPCell(new Paragraph("ICD Codes", font));
	        	cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell8.setVerticalAlignment(Element.ALIGN_TOP);
		        cell8.setBackgroundColor(myColor);
		        cell8.setBorderColor(BaseColor.WHITE);
		        cell8.setBorderWidth(0.1f);
	        	table.addCell(cell8);
	        }
	        if(fileVM.showHccCodes_admissionsExpand) {
	        	PdfPCell cell8 = new PdfPCell(new Paragraph("HCC Codes", font));
	        	cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell8.setVerticalAlignment(Element.ALIGN_TOP);
		        cell8.setBackgroundColor(myColor);
		        cell8.setBorderColor(BaseColor.WHITE);
		        cell8.setBorderWidth(0.1f);
	        	table.addCell(cell8);
	        }
	        if(fileVM.showDrgCode_admissionsExpand) {
	        	PdfPCell cell8 = new PdfPCell(new Paragraph("DRG Code", font));
	        	cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell8.setVerticalAlignment(Element.ALIGN_TOP);
		        cell8.setBackgroundColor(myColor);
		        cell8.setBorderColor(BaseColor.WHITE);
		        cell8.setBorderWidth(0.1f);
	        	table.addCell(cell8);
	        }
	        if(fileVM.showBetosCat_admissionsExpand) {
	        	PdfPCell cell8 = new PdfPCell(new Paragraph("Betos Cat", font));
	        	cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell8.setVerticalAlignment(Element.ALIGN_TOP);
		        cell8.setBackgroundColor(myColor);
		        cell8.setBorderColor(BaseColor.WHITE);
		        cell8.setBorderWidth(0.1f);
	        	table.addCell(cell8);
	        }
	        if(fileVM.showCost_admissionsExpand) {
	        	PdfPCell cell8 = new PdfPCell(new Paragraph("Cost", font));
	        	cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell8.setVerticalAlignment(Element.ALIGN_TOP);
		        cell8.setBackgroundColor(myColor);
		        cell8.setBorderColor(BaseColor.WHITE);
		        cell8.setBorderWidth(0.1f);
	        	table.addCell(cell8);
	        }
	        
	        table.setHeaderRows(1);
	        
	        BaseColor oddRowColor = WebColors.getRGBColor("#F3F3F3");
	        
	        int count = 1; //table rows
	        for(AdmissionsReportExpandVM vm: list) {
	        	Font rowFont = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
	        	if(fileVM.showClaimId_admissionsExpand) {
		        	PdfPCell rowCell3 = new PdfPCell(new Paragraph(vm.getClaimId(), rowFont));
			        rowCell3.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell3.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell3.setBackgroundColor(oddRowColor);
			        rowCell3.setBorderColor(BaseColor.WHITE);
			        rowCell3.setBorderWidth(0.1f);
			        table.addCell(rowCell3);
		        }
	        	if(fileVM.showClaimDate_admissionsExpand) {
			        PdfPCell rowCell1 = new PdfPCell(new Paragraph(vm.getClaimDate(), rowFont));
			        rowCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell1.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell1.setBackgroundColor(oddRowColor);
			        rowCell1.setBorderColor(BaseColor.WHITE);
			        rowCell1.setBorderWidth(0.1f);
			        table.addCell(rowCell1);
	        	}
		        if(fileVM.showClaimType_admissionsExpand) {
		        	PdfPCell rowCell4 = new PdfPCell(new Paragraph(vm.getClaimType(), rowFont));
			        rowCell4.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell4.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell4.setBackgroundColor(oddRowColor);
			        rowCell4.setBorderColor(BaseColor.WHITE);
			        rowCell4.setBorderWidth(0.1f);
			        table.addCell(rowCell4);
		        }
		        if(fileVM.showClinicName_admissionsExpand) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getClinicName(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showPcpName_admissionsExpand) {
		        	PdfPCell rowCell6 = new PdfPCell(new Paragraph(vm.getPcpName(), rowFont));
			        rowCell6.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell6.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell6.setBackgroundColor(oddRowColor);
			        rowCell6.setBorderColor(BaseColor.WHITE);
			        rowCell6.setBorderWidth(0.1f);
			        table.addCell(rowCell6);
		        }
		        if(fileVM.showIcdCodes_admissionsExpand) {
		        	PdfPCell rowCell8 = new PdfPCell(new Paragraph(vm.getIcdCodes(), rowFont));
			        rowCell8.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell8.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell8.setBackgroundColor(oddRowColor);
			        rowCell8.setBorderColor(BaseColor.WHITE);
			        rowCell8.setBorderWidth(0.1f);
			        table.addCell(rowCell8);
		        }
		        if(fileVM.showHccCodes_admissionsExpand) {
		        	PdfPCell rowCell8 = new PdfPCell(new Paragraph(vm.getHccCodes(), rowFont));
			        rowCell8.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell8.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell8.setBackgroundColor(oddRowColor);
			        rowCell8.setBorderColor(BaseColor.WHITE);
			        rowCell8.setBorderWidth(0.1f);
			        table.addCell(rowCell8);
		        }
		        if(fileVM.showDrgCode_admissionsExpand) {
		        	PdfPCell rowCell8 = new PdfPCell(new Paragraph(vm.getDrgCode(), rowFont));
			        rowCell8.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell8.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell8.setBackgroundColor(oddRowColor);
			        rowCell8.setBorderColor(BaseColor.WHITE);
			        rowCell8.setBorderWidth(0.1f);
			        table.addCell(rowCell8);
		        }
		        if(fileVM.showBetosCat_admissionsExpand) {
		        	PdfPCell rowCell8 = new PdfPCell(new Paragraph(vm.getBetosCat(), rowFont));
			        rowCell8.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell8.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell8.setBackgroundColor(oddRowColor);
			        rowCell8.setBorderColor(BaseColor.WHITE);
			        rowCell8.setBorderWidth(0.1f);
			        table.addCell(rowCell8);
		        }
		        if(fileVM.showCost_admissionsExpand) {
		        	PdfPCell rowCell8 = new PdfPCell(new Paragraph(vm.getCost(), rowFont));
			        rowCell8.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell8.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell8.setBackgroundColor(oddRowColor);
			        rowCell8.setBorderColor(BaseColor.WHITE);
			        rowCell8.setBorderWidth(0.1f);
			        table.addCell(rowCell8);
		        }
		        
		        count++;
	        }
	        
	        table.setWidthPercentage(100);
	        document.add(table);
	        document.close();
	        InputStream is = new FileInputStream(file);
	        IOUtils.copy(is, os);
	}
	
	public void generateSpecialistComparisonReportPDF(SpecialistComparisonReportFileVM fileVM, OutputStream os) throws SQLException, IOException, DocumentException {

		List<SpecialistComparisonPrintDataVM> list = getDataForSpecialistComparisonPrint(fileVM);
		
		File file = new File("Data export-Specialist Comparison Report.pdf");
		 Document document = new Document();
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();
	        
	        int tableColumnSize = 0;
	        if(fileVM.showSpecialityCode_specialistComparison)
	        	tableColumnSize++;
	        if(fileVM.showNoOfPcp_specialistComparison)
	        	tableColumnSize++;
	        if(fileVM.showNoOfClaims_specialistComparison)
	        	tableColumnSize++;
	        if(fileVM.showNoOfBeneficiaries_specialistComparison)
	        	tableColumnSize++;
	        if(fileVM.showCostPerClaim_specialistComparison)
	        	tableColumnSize++;
	        if(fileVM.showCostPerBeneficiary_specialistComparison)
	        	tableColumnSize++;
	        if(fileVM.showTotalCost_specialistComparison)
	        	tableColumnSize++;
	        
	        PdfPTable table = new PdfPTable(tableColumnSize);//columns
	        
	        @SuppressWarnings("deprecation")
			BaseColor myColor = WebColors.getRGBColor("#2D4154");
	        
	        Font font = new Font(FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
	        if(fileVM.showSpecialityCode_specialistComparison) {
	        	PdfPCell cell3 = new PdfPCell(new Paragraph("Speciality Code", font));
	        	cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell3.setVerticalAlignment(Element.ALIGN_TOP);
		        cell3.setBackgroundColor(myColor);
		        cell3.setBorderColor(BaseColor.WHITE);
		        cell3.setBorderWidth(0.1f);
	        	table.addCell(cell3);
	        }
	        if(fileVM.showNoOfPcp_specialistComparison) {
		        PdfPCell cell1 = new PdfPCell(new Paragraph("Number Of PCP", font));
		        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell1.setVerticalAlignment(Element.ALIGN_TOP);
		        cell1.setBackgroundColor(myColor);
		        cell1.setBorderColor(BaseColor.WHITE);
		        cell1.setBorderWidth(0.1f);
		        table.addCell(cell1);
	        }
	        if(fileVM.showNoOfClaims_specialistComparison) {
		        PdfPCell cell1 = new PdfPCell(new Paragraph("Number Of Claims", font));
		        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell1.setVerticalAlignment(Element.ALIGN_TOP);
		        cell1.setBackgroundColor(myColor);
		        cell1.setBorderColor(BaseColor.WHITE);
		        cell1.setBorderWidth(0.1f);
		        table.addCell(cell1);
	        }
	        if(fileVM.showNoOfBeneficiaries_specialistComparison) {
	        	PdfPCell cell4 = new PdfPCell(new Paragraph("Number Of Beneficiaries", font));
	        	cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell4.setVerticalAlignment(Element.ALIGN_TOP);
		        cell4.setBackgroundColor(myColor);
		        cell4.setBorderColor(BaseColor.WHITE);
		        cell4.setBorderWidth(0.1f);
	        	table.addCell(cell4);
	        }
	        if(fileVM.showCostPerClaim_specialistComparison) {
	        	PdfPCell cell5 = new PdfPCell(new Paragraph("Cost Per Claim", font));
	        	cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell5.setVerticalAlignment(Element.ALIGN_TOP);
		        cell5.setBackgroundColor(myColor);
		        cell5.setBorderColor(BaseColor.WHITE);
		        cell5.setBorderWidth(0.1f);
	        	table.addCell(cell5);
	        }
	        if(fileVM.showCostPerBeneficiary_specialistComparison) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("Cost Per Beneficiary", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        if(fileVM.showTotalCost_specialistComparison) {
	        	PdfPCell cell8 = new PdfPCell(new Paragraph("Total Cost", font));
	        	cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell8.setVerticalAlignment(Element.ALIGN_TOP);
		        cell8.setBackgroundColor(myColor);
		        cell8.setBorderColor(BaseColor.WHITE);
		        cell8.setBorderWidth(0.1f);
	        	table.addCell(cell8);
	        }
	       
	        
	        table.setHeaderRows(1);
	        
	        BaseColor oddRowColor = WebColors.getRGBColor("#F3F3F3");
	        
	        int count = 1; //table rows
	        for(SpecialistComparisonPrintDataVM vm: list) {
	        	Font rowFont = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
	        	if(fileVM.showSpecialityCode_specialistComparison) {
		        	PdfPCell rowCell3 = new PdfPCell(new Paragraph(vm.getSpecialityCode(), rowFont));
			        rowCell3.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell3.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell3.setBackgroundColor(oddRowColor);
			        rowCell3.setBorderColor(BaseColor.WHITE);
			        rowCell3.setBorderWidth(0.1f);
			        table.addCell(rowCell3);
		        }
	        	if(fileVM.showNoOfPcp_specialistComparison) {
			        PdfPCell rowCell1 = new PdfPCell(new Paragraph(vm.getNumberOfPcp(), rowFont));
			        rowCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell1.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell1.setBackgroundColor(oddRowColor);
			        rowCell1.setBorderColor(BaseColor.WHITE);
			        rowCell1.setBorderWidth(0.1f);
			        table.addCell(rowCell1);
	        	}
	        	if(fileVM.showNoOfClaims_specialistComparison) {
			        PdfPCell rowCell1 = new PdfPCell(new Paragraph(vm.getNumberOfClaims(), rowFont));
			        rowCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell1.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell1.setBackgroundColor(oddRowColor);
			        rowCell1.setBorderColor(BaseColor.WHITE);
			        rowCell1.setBorderWidth(0.1f);
			        table.addCell(rowCell1);
	        	}
		        if(fileVM.showNoOfBeneficiaries_specialistComparison) {
		        	PdfPCell rowCell4 = new PdfPCell(new Paragraph(vm.getNumberOfBeneficiaries(), rowFont));
			        rowCell4.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell4.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell4.setBackgroundColor(oddRowColor);
			        rowCell4.setBorderColor(BaseColor.WHITE);
			        rowCell4.setBorderWidth(0.1f);
			        table.addCell(rowCell4);
		        }
		        if(fileVM.showCostPerClaim_specialistComparison) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getCostPerClaim(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showCostPerBeneficiary_specialistComparison) {
		        	PdfPCell rowCell6 = new PdfPCell(new Paragraph(vm.getCostPerBeneficiary(), rowFont));
			        rowCell6.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell6.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell6.setBackgroundColor(oddRowColor);
			        rowCell6.setBorderColor(BaseColor.WHITE);
			        rowCell6.setBorderWidth(0.1f);
			        table.addCell(rowCell6);
		        }
		        if(fileVM.showTotalCost_specialistComparison) {
		        	PdfPCell rowCell8 = new PdfPCell(new Paragraph(vm.getTotalCost(), rowFont));
			        rowCell8.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell8.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell8.setBackgroundColor(oddRowColor);
			        rowCell8.setBorderColor(BaseColor.WHITE);
			        rowCell8.setBorderWidth(0.1f);
			        table.addCell(rowCell8);
		        }
		        
		        count++;
	        }
	        
	        table.setWidthPercentage(100);
	        document.add(table);
	        document.close();
	        InputStream is = new FileInputStream(file);
	        IOUtils.copy(is, os);
	}
	
	public void generateSpecialistComparisonExpandReportPDF(SpecialistComparisonExpandReportFileVM fileVM, OutputStream os) throws SQLException, IOException, DocumentException {

		List<SpecialistComparisonExpandPrintDataVM> list = getDataForSpecialistComparisonExpandPrint(fileVM);
		
		File file = new File("Data export-Specialist Comparison Expand Report.pdf");
		 Document document = new Document();
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();
	        
	        int tableColumnSize = 0;
	        if(fileVM.showPracticeName_specialistComparisonExpand)
	        	tableColumnSize++;
	        if(fileVM.showSpecialityType_specialistComparisonExpand)
	        	tableColumnSize++;
	        if(fileVM.showNoOfClaims_specialistComparisonExpand)
	        	tableColumnSize++;
	        if(fileVM.showAverageCostPerClaim_specialistComparisonExpand)
	        	tableColumnSize++;
	        if(fileVM.showCost_specialistComparisonExpand)
	        	tableColumnSize++;
	        
	        PdfPTable table = new PdfPTable(tableColumnSize);//columns
	        
	        @SuppressWarnings("deprecation")
			BaseColor myColor = WebColors.getRGBColor("#2D4154");
	        
	        Font font = new Font(FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
	        if(fileVM.showPracticeName_specialistComparisonExpand) {
	        	PdfPCell cell3 = new PdfPCell(new Paragraph("Practice Name", font));
	        	cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell3.setVerticalAlignment(Element.ALIGN_TOP);
		        cell3.setBackgroundColor(myColor);
		        cell3.setBorderColor(BaseColor.WHITE);
		        cell3.setBorderWidth(0.1f);
	        	table.addCell(cell3);
	        }
	        if(fileVM.showSpecialityType_specialistComparisonExpand) {
		        PdfPCell cell1 = new PdfPCell(new Paragraph("Speciality Type", font));
		        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell1.setVerticalAlignment(Element.ALIGN_TOP);
		        cell1.setBackgroundColor(myColor);
		        cell1.setBorderColor(BaseColor.WHITE);
		        cell1.setBorderWidth(0.1f);
		        table.addCell(cell1);
	        }
	        if(fileVM.showNoOfClaims_specialistComparisonExpand) {
	        	PdfPCell cell4 = new PdfPCell(new Paragraph("Number Of Claims", font));
	        	cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell4.setVerticalAlignment(Element.ALIGN_TOP);
		        cell4.setBackgroundColor(myColor);
		        cell4.setBorderColor(BaseColor.WHITE);
		        cell4.setBorderWidth(0.1f);
	        	table.addCell(cell4);
	        }
	        if(fileVM.showAverageCostPerClaim_specialistComparisonExpand) {
	        	PdfPCell cell5 = new PdfPCell(new Paragraph("Average Cost Per Claim", font));
	        	cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell5.setVerticalAlignment(Element.ALIGN_TOP);
		        cell5.setBackgroundColor(myColor);
		        cell5.setBorderColor(BaseColor.WHITE);
		        cell5.setBorderWidth(0.1f);
	        	table.addCell(cell5);
	        }
	        if(fileVM.showCost_specialistComparisonExpand) {
	        	PdfPCell cell7 = new PdfPCell(new Paragraph("Cost", font));
	        	cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		        cell7.setVerticalAlignment(Element.ALIGN_TOP);
		        cell7.setBackgroundColor(myColor);
		        cell7.setBorderColor(BaseColor.WHITE);
		        cell7.setBorderWidth(0.1f);
	        	table.addCell(cell7);
	        }
	        
	        table.setHeaderRows(1);
	        
	        BaseColor oddRowColor = WebColors.getRGBColor("#F3F3F3");
	        
	        int count = 1; //table rows
	        for(SpecialistComparisonExpandPrintDataVM vm: list) {
	        	Font rowFont = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
	        	if(fileVM.showPracticeName_specialistComparisonExpand) {
		        	PdfPCell rowCell3 = new PdfPCell(new Paragraph(vm.getPracticeName(), rowFont));
			        rowCell3.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell3.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell3.setBackgroundColor(oddRowColor);
			        rowCell3.setBorderColor(BaseColor.WHITE);
			        rowCell3.setBorderWidth(0.1f);
			        table.addCell(rowCell3);
		        }
	        	if(fileVM.showSpecialityType_specialistComparisonExpand) {
			        PdfPCell rowCell1 = new PdfPCell(new Paragraph(vm.getSpecialityType(), rowFont));
			        rowCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell1.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell1.setBackgroundColor(oddRowColor);
			        rowCell1.setBorderColor(BaseColor.WHITE);
			        rowCell1.setBorderWidth(0.1f);
			        table.addCell(rowCell1);
	        	}
		        if(fileVM.showNoOfClaims_specialistComparisonExpand) {
		        	PdfPCell rowCell4 = new PdfPCell(new Paragraph(vm.getNumberOfClaims(), rowFont));
			        rowCell4.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell4.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell4.setBackgroundColor(oddRowColor);
			        rowCell4.setBorderColor(BaseColor.WHITE);
			        rowCell4.setBorderWidth(0.1f);
			        table.addCell(rowCell4);
		        }
		        if(fileVM.showAverageCostPerClaim_specialistComparisonExpand) {
		        	PdfPCell rowCell5 = new PdfPCell(new Paragraph(vm.getAverageCostPerClaim(), rowFont));
			        rowCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell5.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell5.setBackgroundColor(oddRowColor);
			        rowCell5.setBorderColor(BaseColor.WHITE);
			        rowCell5.setBorderWidth(0.1f);
			        table.addCell(rowCell5);
		        }
		        if(fileVM.showCost_specialistComparisonExpand) {
		        	PdfPCell rowCell6 = new PdfPCell(new Paragraph(vm.getCost(), rowFont));
			        rowCell6.setHorizontalAlignment(Element.ALIGN_LEFT);
			        rowCell6.setVerticalAlignment(Element.ALIGN_TOP);
			        if(count%2 > 0)
			        rowCell6.setBackgroundColor(oddRowColor);
			        rowCell6.setBorderColor(BaseColor.WHITE);
			        rowCell6.setBorderWidth(0.1f);
			        table.addCell(rowCell6);
		        }
		        
		        count++;
	        }
	        
	        table.setWidthPercentage(100);
	        document.add(table);
	        document.close();
	        InputStream is = new FileInputStream(file);
	        IOUtils.copy(is, os);
	}
	
	public MembershipManagementVM getMembershipManagementData(ReportVM vm) {
		return demographicDetailDao.getMembershipManagementData(vm);
	}
	
	public void generateXLSX(FileVM fileVM, OutputStream os) throws IOException {
		
		Workbook workbook = new XSSFWorkbook();
	    Sheet sheet = workbook.createSheet("Claims");

	    org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
	    headerFont.setBold(true);
	    headerFont.setFontHeightInPoints((short) 12);

	    CellStyle headerCellStyle = workbook.createCellStyle();
	    headerCellStyle.setFont(headerFont);
	    
	 // Create a Row
	    Row headerRow = sheet.createRow(0);
	    int headerIndex = 0;
	    Cell cell0 = headerRow.createCell(headerIndex);
	      cell0.setCellValue("Plan Name");
	      cell0.setCellStyle(headerCellStyle);
	      
	      if(fileVM.showProviderName) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Provider Name");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	    
	      if(fileVM.showMedicareId) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Medicare ID");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showPatientName) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Patient Name");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showICDCode) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("ICD9/10 Code");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showHCCCodes) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("HCC Codes");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showTermedMonth) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Termed Month");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showEligibleMonth) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Eligible Month");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showCost) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Cost");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	   // Create Other rows and cells
	      int rowNum = 1;
	      int rowIndex = 0;
	      List<ReportDataVM> list = getDataForFile(fileVM);
	      for (ReportDataVM vm : list) {
	    	  rowIndex = 0;
	          Row row = sheet.createRow(rowNum);
	          	  row.createCell(rowIndex).setCellValue(vm.getPlanName());
	          
	          if(fileVM.showProviderName) {
	        	  row.createCell(++rowIndex).setCellValue(vm.getProviderName());
		      }
		    
		      if(fileVM.showMedicareId) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getMedicareId());
		      }
		      
		      if(fileVM.showPatientName) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getPatientName());
		      }
		      
		      if(fileVM.showICDCode) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getIcdCode());
		      }
		      
		      if(fileVM.showHCCCodes) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getHccCodes());
		      }
		      
		      if(fileVM.showTermedMonth) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getTermedMonth());
		      }
		      
		      if(fileVM.showEligibleMonth) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getEligibleMonth());
		      }
		      
		      if(fileVM.showCost) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getCost());
		      }
		      
		      rowNum = rowNum + 1;
		      
	        }
	      
	   // Resize all columns to fit the content size
	      for(int i=0;i<headerIndex;i++) {
	    	  sheet.autoSizeColumn(i);
	      }
	      FileOutputStream fileOut = new FileOutputStream("Data export-Claims Search.xlsx");
	      workbook.write(fileOut);
	      InputStream is = new FileInputStream(new File("Data export-Claims Search.xlsx"));
	      IOUtils.copy(is, os);
	      //Files.copy(new File("Data export-Claims Search.xlsx").toPath(), os);
	      fileOut.close();
	}
	
	public void generateDuplicateClaimsXLSX(DuplicateClaimFileVM fileVM, OutputStream os) throws IOException {
		
		Workbook workbook = new XSSFWorkbook();
	    Sheet sheet = workbook.createSheet("Duplicate Claims Report");

	    org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
	    headerFont.setBold(true);
	    headerFont.setFontHeightInPoints((short) 12);

	    CellStyle headerCellStyle = workbook.createCellStyle();
	    headerCellStyle.setFont(headerFont);
	    
	 // Create a Row
	    Row headerRow = sheet.createRow(0);
	    int headerIndex = 0;
	    if(fileVM.showSubscriberId_duplicate) {
	      Cell cell0 = headerRow.createCell(headerIndex);
	      cell0.setCellValue("HICN/Subscriber ID");
	      cell0.setCellStyle(headerCellStyle);
	    }
	      
	      if(fileVM.showPlanName_duplicate) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Plan Name");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	    
	      if(fileVM.showPatientName_duplicate) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Patient Name");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showPcp_duplicate) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("PCP");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showEligibleMonth_duplicate) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Eligible Month");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showTermedMonth_duplicate) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Termed Month");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showClaimDate_duplicate) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Claim Date");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showDuplicativeCost_duplicate) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Duplicative Cost");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	   // Create Other rows and cells
	      int rowNum = 1;
	      int rowIndex = 0;
	      List<DuplicateClaimsReportVM> list = getDataForDuplicateClaimsXLFile(fileVM);
	      for (DuplicateClaimsReportVM vm : list) {
	    	  rowIndex = 0;
	    	  Row row = sheet.createRow(rowNum);
	    	  
	    	  if(fileVM.showSubscriberId_duplicate) {
	          	  row.createCell(rowIndex).setCellValue(vm.getSubscriberId());
	    	  }
	          if(fileVM.showPlanName_duplicate) {
	        	  row.createCell(++rowIndex).setCellValue(vm.getPlanName());
		      }
		    
		      if(fileVM.showPatientName_duplicate) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getPatientName());
		      }
		      
		      if(fileVM.showPcp_duplicate) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getPcp());
		      }
		      
		      if(fileVM.showEligibleMonth_duplicate) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getEligibleMonth());
		      }
		      
		      if(fileVM.showTermedMonth_duplicate) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getTermedMonth());
		      }
		      
		      if(fileVM.showClaimDate_duplicate) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getClaimDate());
		      }
		      
		      if(fileVM.showDuplicativeCost_duplicate) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getDuplicativeCost());
		      }
		      
		      rowNum = rowNum + 1;
		      
	        }
	      
	   // Resize all columns to fit the content size
	      for(int i=0;i<headerIndex;i++) {
	    	  sheet.autoSizeColumn(i);
	      }
	      FileOutputStream fileOut = new FileOutputStream("Data export-Claims Search.xlsx");
	      workbook.write(fileOut);
	      InputStream is = new FileInputStream(new File("Data export-Claims Search.xlsx"));
	      IOUtils.copy(is, os);
	      //Files.copy(new File("Data export-Claims Search.xlsx").toPath(), os);
	      fileOut.close();
	}
	
	public void generateAdmissionsReportXLSX(AdmissionsReportFileVM fileVM, OutputStream os)  throws IOException {

		Workbook workbook = new XSSFWorkbook();
	    Sheet sheet = workbook.createSheet("Admissions Report");

	    org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
	    headerFont.setBold(true);
	    headerFont.setFontHeightInPoints((short) 12);

	    CellStyle headerCellStyle = workbook.createCellStyle();
	    headerCellStyle.setFont(headerFont);
	    
	 // Create a Row
	    Row headerRow = sheet.createRow(0);
	    int headerIndex = 0;
	    

	    if(fileVM.showPatientName_admissions) {
	    	Cell cell1 = headerRow.createCell(headerIndex);
	    	cell1.setCellValue("Patient Name");
	    	cell1.setCellStyle(headerCellStyle);
	    }
	      
	    if(fileVM.showSubscriberId_admissions) {
	      Cell cell0 = headerRow.createCell(++headerIndex);
	      cell0.setCellValue("HICN/Subscriber ID");
	      cell0.setCellStyle(headerCellStyle);
	    }
	      
	      if(fileVM.showPcpName_admissions) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("PCP Name");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showEligibleMonth_admissions) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Eligible Month");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showTotalNoOfAdmissions_admissions) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Total Number Of Admissions");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showTotalCost_admissions) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Total Cost");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	   // Create Other rows and cells
	      int rowNum = 1;
	      int rowIndex = 0;
	      List<AdmissionsReportVM> list = getDataForAdmissionsReportXL(fileVM);
	      for (AdmissionsReportVM vm : list) {
	    	  rowIndex = 0;
	    	  Row row = sheet.createRow(rowNum);
	    	  
	    	  if(fileVM.showPatientName_admissions) {
		    	  row.createCell(rowIndex).setCellValue(vm.getPatientName());
		      }
		      
	    	  if(fileVM.showSubscriberId_admissions) {
	          	  row.createCell(++rowIndex).setCellValue(vm.getSubscriberId());
	    	  }
		    
		      if(fileVM.showPcpName_admissions) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getPcpName());
		      }
		      
		      if(fileVM.showEligibleMonth_admissions) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getEligibleMonth());
		      }
		      
		      if(fileVM.showTotalNoOfAdmissions_admissions) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getTotalNoOfAdmissions());
		      }
		      
		      if(fileVM.showTotalCost_admissions) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getTotalCost());
		      }
		      
		      rowNum = rowNum + 1;
		      
	        }
	      
	   // Resize all columns to fit the content size
	      for(int i=0;i<headerIndex;i++) {
	    	  sheet.autoSizeColumn(i);
	      }
	      FileOutputStream fileOut = new FileOutputStream("Data export-Admissions Report Search.xlsx");
	      workbook.write(fileOut);
	      InputStream is = new FileInputStream(new File("Data export-Admissions Report Search.xlsx"));
	      IOUtils.copy(is, os);
	      //Files.copy(new File("Data export-Claims Search.xlsx").toPath(), os);
	      fileOut.close();
	}
	
	public void generatePatientVisitReportXLSX(PatientVisitReportFileVM fileVM, OutputStream os)  throws IOException {

		Workbook workbook = new XSSFWorkbook();
	    Sheet sheet = workbook.createSheet("Patient Visit Report");

	    org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
	    headerFont.setBold(true);
	    headerFont.setFontHeightInPoints((short) 12);

	    CellStyle headerCellStyle = workbook.createCellStyle();
	    headerCellStyle.setFont(headerFont);
	    
	 // Create a Row
	    Row headerRow = sheet.createRow(0);
	    int headerIndex = 0;
	    

	    if(fileVM.showPatientName_patientVisit) {
	    	Cell cell1 = headerRow.createCell(headerIndex);
	    	cell1.setCellValue("Patient Name");
	    	cell1.setCellStyle(headerCellStyle);
	    }
	      
	    if(fileVM.showHicn_patientVisit) {
	      Cell cell0 = headerRow.createCell(++headerIndex);
	      cell0.setCellValue("HICN");
	      cell0.setCellStyle(headerCellStyle);
	    }
	      
	      if(fileVM.showPcpName_patientVisit) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("PCP Name");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showTermedMonth_patientVisit) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Termed Month");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showIpaEffectiveDate_patientVisit) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("IPA Effective Date");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showTotalErVisits_patientVisit) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Total Number Of ER Visits");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showTotalCost_patientVisit) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Total Cost");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	   // Create Other rows and cells
	      int rowNum = 1;
	      int rowIndex = 0;
	      List<PatientVisitReportPrintDataVM> list = getDataForPatientVisitReportPrint(fileVM);
	      for (PatientVisitReportPrintDataVM vm : list) {
	    	  rowIndex = 0;
	    	  Row row = sheet.createRow(rowNum);
	    	  
	    	  if(fileVM.showPatientName_patientVisit) {
		    	  row.createCell(rowIndex).setCellValue(vm.getPatientName());
		      }
		      
	    	  if(fileVM.showHicn_patientVisit) {
	          	  row.createCell(++rowIndex).setCellValue(vm.getHicn());
	    	  }
		    
		      if(fileVM.showPcpName_patientVisit) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getPcpName());
		      }
		      
		      if(fileVM.showTermedMonth_patientVisit) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getTermedMonth());
		      }
		      
		      if(fileVM.showIpaEffectiveDate_patientVisit) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getIpaEffectiveDate());
		      }
		      
		      if(fileVM.showTotalErVisits_patientVisit) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getTotalErVisits());
		      }
		      
		      if(fileVM.showTotalCost_patientVisit) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getTotalCost());
		      }
		      
		      rowNum = rowNum + 1;
		      
	        }
	      
	   // Resize all columns to fit the content size
	      for(int i=0;i<headerIndex;i++) {
	    	  sheet.autoSizeColumn(i);
	      }
	      FileOutputStream fileOut = new FileOutputStream("Data export-Patient Visit Report Search.xlsx");
	      workbook.write(fileOut);
	      InputStream is = new FileInputStream(new File("Data export-Patient Visit Report Search.xlsx"));
	      IOUtils.copy(is, os);
	      //Files.copy(new File("Data export-Claims Search.xlsx").toPath(), os);
	      fileOut.close();
	}
	
	public void generatePatientVisitExpandReportXLSX(PatientVisitExpandReportFileVM fileVM, OutputStream os)  throws IOException {

		Workbook workbook = new XSSFWorkbook();
	    Sheet sheet = workbook.createSheet("Patient Visit Report Details");

	    org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
	    headerFont.setBold(true);
	    headerFont.setFontHeightInPoints((short) 12);

	    CellStyle headerCellStyle = workbook.createCellStyle();
	    headerCellStyle.setFont(headerFont);
	    
	 // Create a Row
	    Row headerRow = sheet.createRow(0);
	    int headerIndex = 0;
	    

	    if(fileVM.showClaimId_patientVisitExpand) {
	    	Cell cell1 = headerRow.createCell(headerIndex);
	    	cell1.setCellValue("Claim Id");
	    	cell1.setCellStyle(headerCellStyle);
	    }
	      
	    if(fileVM.showClaimDate_patientVisitExpand) {
	      Cell cell0 = headerRow.createCell(++headerIndex);
	      cell0.setCellValue("Claim Date");
	      cell0.setCellStyle(headerCellStyle);
	    }
	      
	      if(fileVM.showClaimType_patientVisitExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Claim Type");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showClinicName_patientVisitExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Clinic Name");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showPcpName_patientVisitExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("PCP Name");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showIcdCodes_patientVisitExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("ICD Codes");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showHccCodes_patientVisitExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("HCC Codes");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showDrgCode_patientVisitExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("DRG Code");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showBetosCat_patientVisitExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Betos Cat");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showCost_patientVisitExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Cost");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	   // Create Other rows and cells
	      int rowNum = 1;
	      int rowIndex = 0;
	      List<PatientVisitExpandReportPrintDataVM> list = getDataForPatientVisitExpandReportPrint(fileVM);
	      for (PatientVisitExpandReportPrintDataVM vm : list) {
	    	  rowIndex = 0;
	    	  Row row = sheet.createRow(rowNum);
	    	  
	    	  if(fileVM.showClaimId_patientVisitExpand) {
		    	  row.createCell(rowIndex).setCellValue(vm.getClaimId());
		      }
		      
	    	  if(fileVM.showClaimDate_patientVisitExpand) {
	          	  row.createCell(++rowIndex).setCellValue(vm.getClaimDate());
	    	  }
		    
		      if(fileVM.showClaimType_patientVisitExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getClaimType());
		      }
		      
		      if(fileVM.showClinicName_patientVisitExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getClinicName());
		      }
		      
		      if(fileVM.showPcpName_patientVisitExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getPcpName());
		      }
		      
		      if(fileVM.showIcdCodes_patientVisitExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getIcdCodes());
		      }
		      
		      if(fileVM.showHccCodes_patientVisitExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getHccCodes());
		      }
		      
		      if(fileVM.showDrgCode_patientVisitExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getDrgCode());
		      }
		      
		      if(fileVM.showBetosCat_patientVisitExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getBetosCat());
		      }
		      
		      if(fileVM.showCost_patientVisitExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getCost());
		      }
		      
		      rowNum = rowNum + 1;
		      
	        }
	      
	   // Resize all columns to fit the content size
	      for(int i=0;i<headerIndex;i++) {
	    	  sheet.autoSizeColumn(i);
	      }
	      FileOutputStream fileOut = new FileOutputStream("Data export-Patient Visit Report Details.xlsx");
	      workbook.write(fileOut);
	      InputStream is = new FileInputStream(new File("Data export-Patient Visit Report Details.xlsx"));
	      IOUtils.copy(is, os);
	      //Files.copy(new File("Data export-Claims Search.xlsx").toPath(), os);
	      fileOut.close();
	}
	
	public void generateSettledMonthsReportXLSX(SettledMonthsReportFileVM fileVM, OutputStream os)  throws IOException {

		Workbook workbook = new XSSFWorkbook();
	    Sheet sheet = workbook.createSheet("Settled Months Report");

	    org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
	    headerFont.setBold(true);
	    headerFont.setFontHeightInPoints((short) 12);

	    CellStyle headerCellStyle = workbook.createCellStyle();
	    headerCellStyle.setFont(headerFont);
	    
	 // Create a Row
	    Row headerRow = sheet.createRow(0);
	    int headerIndex = 0;
	    

	    if(fileVM.showPcpLocation_settledMonths) {
	    	Cell cell1 = headerRow.createCell(headerIndex);
	    	cell1.setCellValue("PCP Location");
	    	cell1.setCellStyle(headerCellStyle);
	    }
	      
	    if(fileVM.showMonth_settledMonths) {
	      Cell cell0 = headerRow.createCell(++headerIndex);
	      cell0.setCellValue("Month");
	      cell0.setCellStyle(headerCellStyle);
	    }
	      
	      if(fileVM.showMembership_settledMonths) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Membership");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showIpaPremium_settledMonths) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Ipa Premium");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showTotalExpenses_settledMonths) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Total Expenses");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showStopLoss_settledMonths) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("StopLoss");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showNetPremium_settledMonths) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Net Premium");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showRiskSharing_settledMonths) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Risk Sharing");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showSurplusDeficit_settledMonths) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Surplus/Deficit");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	   // Create Other rows and cells
	      int rowNum = 1;
	      int rowIndex = 0;
	      List<SettledMonthsReportPrintDataVM> list = getDataForSettledMonthsReportPrint(fileVM);
	      for (SettledMonthsReportPrintDataVM vm : list) {
	    	  rowIndex = 0;
	    	  Row row = sheet.createRow(rowNum);
	    	  
	    	  if(fileVM.showPcpLocation_settledMonths) {
		    	  row.createCell(rowIndex).setCellValue(vm.getPcpLocation());
		      }
		      
	    	  if(fileVM.showMonth_settledMonths) {
	          	  row.createCell(++rowIndex).setCellValue(vm.getMonth());
	    	  }
		    
		      if(fileVM.showMembership_settledMonths) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getMembership());
		      }
		      
		      if(fileVM.showIpaPremium_settledMonths) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getIpaPremium());
		      }
		      
		      if(fileVM.showTotalExpenses_settledMonths) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getTotalExpenses());
		      }
		      
		      if(fileVM.showStopLoss_settledMonths) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getStoploss());
		      }
		      
		      if(fileVM.showNetPremium_settledMonths) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getNetPremium());
		      }
		      
		      if(fileVM.showRiskSharing_settledMonths) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getRiskSharing());
		      }
		      
		      if(fileVM.showSurplusDeficit_settledMonths) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getSurplusDeficit());
		      }
		      rowNum = rowNum + 1;
		      
	        }
	      
	   // Resize all columns to fit the content size
	      for(int i=0;i<headerIndex;i++) {
	    	  sheet.autoSizeColumn(i);
	      }
	      FileOutputStream fileOut = new FileOutputStream("Data export-Settled Months Report Search.xlsx");
	      workbook.write(fileOut);
	      InputStream is = new FileInputStream(new File("Data export-Settled Months Report Search.xlsx"));
	      IOUtils.copy(is, os);
	      //Files.copy(new File("Data export-Claims Search.xlsx").toPath(), os);
	      fileOut.close();
	}
	
	public void generateSettledMonthsExpandReportXLSX(SettledMonthsExpandReportFileVM fileVM, OutputStream os)  throws IOException {

		Workbook workbook = new XSSFWorkbook();
	    Sheet sheet = workbook.createSheet("Settled Months Expand Report");

	    org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
	    headerFont.setBold(true);
	    headerFont.setFontHeightInPoints((short) 12);

	    CellStyle headerCellStyle = workbook.createCellStyle();
	    headerCellStyle.setFont(headerFont);
	    
	 // Create a Row
	    Row headerRow = sheet.createRow(0);
	    int headerIndex = 0;
	    

	    if(fileVM.showPatientName_settledMonthsExpand) {
	    	Cell cell1 = headerRow.createCell(headerIndex);
	    	cell1.setCellValue("Patient Name");
	    	cell1.setCellStyle(headerCellStyle);
	    }
	      
	    if(fileVM.showPcpName_settledMonthsExpand) {
	      Cell cell0 = headerRow.createCell(++headerIndex);
	      cell0.setCellValue("PCP Name");
	      cell0.setCellStyle(headerCellStyle);
	    }
	      
	      if(fileVM.showPcpLocation_settledMonthsExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("PCP Location");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showCost_settledMonthsExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Cost");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showClaimType_settledMonthsExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Claim Type");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showMra_settledMonthsExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("MRA");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	   // Create Other rows and cells
	      int rowNum = 1;
	      int rowIndex = 0;
	      List<SettledMonthsExpandReportPrintDataVM> list = getDataForSettledMonthsExpandReportPrint(fileVM);
	      for (SettledMonthsExpandReportPrintDataVM vm : list) {
	    	  rowIndex = 0;
	    	  Row row = sheet.createRow(rowNum);
	    	  
	    	  if(fileVM.showPatientName_settledMonthsExpand) {
		    	  row.createCell(rowIndex).setCellValue(vm.getPatientName());
		      }
		      
	    	  if(fileVM.showPcpName_settledMonthsExpand) {
	          	  row.createCell(++rowIndex).setCellValue(vm.getPcpName());
	    	  }
		    
		      if(fileVM.showPcpLocation_settledMonthsExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getPcpLocation());
		      }
		      
		      if(fileVM.showCost_settledMonthsExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getCost());
		      }
		      
		      if(fileVM.showClaimType_settledMonthsExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getClaimType());
		      }
		      
		      if(fileVM.showMra_settledMonthsExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getMra());
		      }
		      
		      rowNum = rowNum + 1;
		      
	        }
	      
	   // Resize all columns to fit the content size
	      for(int i=0;i<headerIndex;i++) {
	    	  sheet.autoSizeColumn(i);
	      }
	      FileOutputStream fileOut = new FileOutputStream("Data export-Settled Months Expand Report Search.xlsx");
	      workbook.write(fileOut);
	      InputStream is = new FileInputStream(new File("Data export-Settled Months Expand Report Search.xlsx"));
	      IOUtils.copy(is, os);
	      //Files.copy(new File("Data export-Claims Search.xlsx").toPath(), os);
	      fileOut.close();
	}
	
	public void generateReinsuranceManagementReportXLSX(ReinsuranceManagementReportFileVM fileVM, OutputStream os)  throws IOException {

		Workbook workbook = new XSSFWorkbook();
	    Sheet sheet = workbook.createSheet("Reinsurance Management Report");

	    org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
	    headerFont.setBold(true);
	    headerFont.setFontHeightInPoints((short) 12);

	    CellStyle headerCellStyle = workbook.createCellStyle();
	    headerCellStyle.setFont(headerFont);
	    
	 // Create a Row
	    Row headerRow = sheet.createRow(0);
	    int headerIndex = 0;
	    

	    	if(fileVM.showSubscriberID_reinsuranceManagement) {
	    		Cell cell1 = headerRow.createCell(headerIndex);
	    		cell1.setCellValue("SubscriberID");
	    		cell1.setCellStyle(headerCellStyle);
	    	}
	      
	    	if(fileVM.showPlanName_reinsuranceManagement) {
	    		Cell cell0 = headerRow.createCell(++headerIndex);
	    		cell0.setCellValue("Plan Name");
	    		cell0.setCellStyle(headerCellStyle);
	    	}
	      
	      if(fileVM.showPatientName_reinsuranceManagement) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Patient Name");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showPcpName_reinsuranceManagement) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("PCP Name");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      if(fileVM.showTermedMonth_reinsuranceManagement) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Termed Month");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      if(fileVM.showInstClaims_reinsuranceManagement) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("INST Claims");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      if(fileVM.showProfClaims_reinsuranceManagement) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("PROF Claims");
	    	  cell1.setCellStyle(headerCellStyle);
	      }	      
	      if(fileVM.showTotalCost_reinsuranceManagement) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Total Cost");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      
	   // Create Other rows and cells
	      int rowNum = 1;
	      int rowIndex = 0;
	      List<ReinsuranceManagementReportPrintDataVM> list = getDataForReinsuranceManagementReportPrint(fileVM);
	      for (ReinsuranceManagementReportPrintDataVM vm : list) {
	    	  rowIndex = 0;
	    	  Row row = sheet.createRow(rowNum);
	    	  
	    	  if(fileVM.showSubscriberID_reinsuranceManagement) {
		    	  row.createCell(rowIndex).setCellValue(vm.getHicn());
		      }
		      
	    	  if(fileVM.showPlanName_reinsuranceManagement) {
	          	  row.createCell(++rowIndex).setCellValue(vm.getPlanName());
	    	  }
		    
		      if(fileVM.showPatientName_reinsuranceManagement) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getPatientName());
		      }
		      
		      if(fileVM.showPcpName_reinsuranceManagement) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getPcpName());
		      }
		      
		      if(fileVM.showTermedMonth_reinsuranceManagement) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getTermedMonth());
		      }
		      if(fileVM.showInstClaims_reinsuranceManagement) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getInstClaims());
		      }
		      if(fileVM.showProfClaims_reinsuranceManagement) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getProfClaims());
		      }
		      
		      if(fileVM.showTotalCost_reinsuranceManagement) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getTotalCost());
		      }
		      
		      rowNum = rowNum + 1;
		      
	        }
	      
	   // Resize all columns to fit the content size
	      for(int i=0;i<headerIndex;i++) {
	    	  sheet.autoSizeColumn(i);
	      }
	      FileOutputStream fileOut = new FileOutputStream("Data export-Reinsurance Management Report.xlsx");
	      workbook.write(fileOut);
	      InputStream is = new FileInputStream(new File("Data export-Reinsurance Management Report.xlsx"));
	      IOUtils.copy(is, os);
	      //Files.copy(new File("Data export-Claims Search.xlsx").toPath(), os);
	      fileOut.close();
	}
	
	
	public void generateReinsuranceCostReportXLSX(ReinsuranceCostReportFileVM fileVM, OutputStream os)  throws IOException {

		Workbook workbook = new XSSFWorkbook();
	    Sheet sheet = workbook.createSheet("Reinsurance Cost Report");

	    org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
	    headerFont.setBold(true);
	    headerFont.setFontHeightInPoints((short) 12);

	    CellStyle headerCellStyle = workbook.createCellStyle();
	    headerCellStyle.setFont(headerFont);
	    
	 // Create a Row
	    Row headerRow = sheet.createRow(0);
	    int headerIndex = 0;
	    

	    	if(fileVM.showPlanName_reinsuranceCostReport) {
	    		Cell cell1 = headerRow.createCell(headerIndex);
	    		cell1.setCellValue("Plan Name");
	    		cell1.setCellStyle(headerCellStyle);
	    	}
	      
	    	if(fileVM.showPolicyPeriod_reinsuranceCostReport) {
	    		Cell cell0 = headerRow.createCell(++headerIndex);
	    		cell0.setCellValue("Policy Period");
	    		cell0.setCellStyle(headerCellStyle);
	    	}
	      
	      if(fileVM.showPatientLastName_reinsuranceCostReport) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Patient Last Name");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showPatientFirstName_reinsuranceCostReport) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Patient First Name");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      if(fileVM.showSubscriberID_reinsuranceCostReport) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("HICN/SubscriberID");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      if(fileVM.showEffectiveDate_reinsuranceCostReport) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Effective Date");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      if(fileVM.showTermedMonth_reinsuranceCostReport) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Termed Month");
	    	  cell1.setCellStyle(headerCellStyle);
	      }	      
	      if(fileVM.showDateOfBirth_reinsuranceCostReport) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("DOB");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      if(fileVM.showStatus_reinsuranceCostReport) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Status");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      if(fileVM.showGender_reinsuranceCostReport) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Gender");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      if(fileVM.showPcpName_reinsuranceCostReport) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("PCP Name");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      if(fileVM.showTotalClaimsCost_reinsuranceCostReport) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Total Claims Cost");
	    	  cell1.setCellStyle(headerCellStyle);
	      } 
	      
	   // Create Other rows and cells
	      int rowNum = 1;
	      int rowIndex = 0;
	      List<ReinsuranceCostReportPrintDataVM> list = getDataForReinsuranceCostReportPrint(fileVM);
	      for (ReinsuranceCostReportPrintDataVM vm : list) {
	    	  rowIndex = 0;
	    	  Row row = sheet.createRow(rowNum);
	    	  
	    	  if(fileVM.showPlanName_reinsuranceCostReport) {
		    	  row.createCell(rowIndex).setCellValue(vm.getPlanName());
		      }
		      
	    	  if(fileVM.showPolicyPeriod_reinsuranceCostReport) {
	          	  row.createCell(++rowIndex).setCellValue(vm.getPolicyPeriod());
	    	  }
		    
		      if(fileVM.showPatientLastName_reinsuranceCostReport	) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getPatientLastName());
		      }
		      
		      if(fileVM.showPatientFirstName_reinsuranceCostReport) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getPatientFirstName());
		      }
		      
		      if(fileVM.showSubscriberID_reinsuranceCostReport) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getSubscriberID());
		      }
		      if(fileVM.showEffectiveDate_reinsuranceCostReport) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getEffectiveDate());
		      }
		      if(fileVM.showTermedMonth_reinsuranceCostReport) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getTermedMonth());
		      }
		      
		      if(fileVM.showDateOfBirth_reinsuranceCostReport) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getDob());
		      }
		      if(fileVM.showStatus_reinsuranceCostReport) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getStatus());
		      }
		      if(fileVM.showGender_reinsuranceCostReport) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getGender());
		      }
		      if(fileVM.showPcpName_reinsuranceCostReport) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getPcpName());
		      }
		      if(fileVM.showTotalClaimsCost_reinsuranceCostReport) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getTotalClaimsCost());
		      }
		      rowNum = rowNum + 1;
		      
	        }
	      
	   // Resize all columns to fit the content size
	      for(int i=0;i<headerIndex;i++) {
	    	  sheet.autoSizeColumn(i);
	      }
	      FileOutputStream fileOut = new FileOutputStream("Data export-Reinsurance Cost Report.xlsx");
	      workbook.write(fileOut);
	      InputStream is = new FileInputStream(new File("Data export-Reinsurance Cost Report.xlsx"));
	      IOUtils.copy(is, os);
	      //Files.copy(new File("Data export-Claims Search.xlsx").toPath(), os);
	      fileOut.close();
	}


	

	public void generatePmpmByPracticeReportXLSX(PmpmByPracticeReportFileVM fileVM, OutputStream os)  throws IOException {

		Workbook workbook = new XSSFWorkbook();
	    Sheet sheet = workbook.createSheet("PMPM By Practice Report");

	    org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
	    headerFont.setBold(true);
	    headerFont.setFontHeightInPoints((short) 12);

	    CellStyle headerCellStyle = workbook.createCellStyle();
	    headerCellStyle.setFont(headerFont);
	    
	 // Create a Row
	    Row headerRow = sheet.createRow(0);
	    int headerIndex = 0;
	    

	    if(fileVM.showFacilityLocationName_pmpmByPractice) {
	    	Cell cell1 = headerRow.createCell(headerIndex);
	    	cell1.setCellValue("Facility Location Name");
	    	cell1.setCellStyle(headerCellStyle);
	    }
	      
	    if(fileVM.showProviderName_pmpmByPractice) {
	      Cell cell0 = headerRow.createCell(++headerIndex);
	      cell0.setCellValue("Provider Name");
	      cell0.setCellStyle(headerCellStyle);
	    }
	      
	      if(fileVM.showTotalCost_pmpmByPractice) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Total Cost");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showTotalNumberOfMemberMonth_pmpmByPractice) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Total Number Of Member Month");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showPMPM_pmpmByPractice) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("PMPM");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showPMPY_pmpmByPractice) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("PMPY");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showTotalPremium_pmpmByPractice) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Total Premium");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showIpaPremium_pmpmByPractice) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("IPA Premium");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showDifference_pmpmByPractice) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Total Premium - IPA Premium");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	   // Create Other rows and cells
	      int rowNum = 1;
	      int rowIndex = 0;
	      List<PmpmByPracticeReportPrintDataVM> list = getDataForPmpmByPracticeReportPrint(fileVM);
	      for (PmpmByPracticeReportPrintDataVM vm : list) {
	    	  rowIndex = 0;
	    	  Row row = sheet.createRow(rowNum);
	    	  
	    	  if(fileVM.showFacilityLocationName_pmpmByPractice) {
		    	  row.createCell(rowIndex).setCellValue(vm.getFacilityLocationName());
		      }
		      
	    	  if(fileVM.showProviderName_pmpmByPractice) {
	          	  row.createCell(++rowIndex).setCellValue(vm.getProviderName());
	    	  }
		    
		      if(fileVM.showTotalCost_pmpmByPractice) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getTotalCost());
		      }
		      
		      if(fileVM.showTotalNumberOfMemberMonth_pmpmByPractice) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getTotalNumberOfMemberMonth());
		      }
		      
		      if(fileVM.showPMPM_pmpmByPractice) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getPmpm());
		      }
		      
		      if(fileVM.showPMPY_pmpmByPractice) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getPmpy());
		      }
		      
		      if(fileVM.showTotalPremium_pmpmByPractice) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getTotalPremium());
		      }
		      
		      if(fileVM.showIpaPremium_pmpmByPractice) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getIpaPremium());
		      }
		      
		      if(fileVM.showDifference_pmpmByPractice) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getDifference());
		      }
		      
		      rowNum = rowNum + 1;
		      
	        }
	      
	   // Resize all columns to fit the content size
	      for(int i=0;i<headerIndex;i++) {
	    	  sheet.autoSizeColumn(i);
	      }
	      FileOutputStream fileOut = new FileOutputStream("Data export-PMPM By Practice Report Search.xlsx");
	      workbook.write(fileOut);
	      InputStream is = new FileInputStream(new File("Data export-PMPM By Practice Report Search.xlsx"));
	      IOUtils.copy(is, os);
	      //Files.copy(new File("Data export-Claims Search.xlsx").toPath(), os);
	      fileOut.close();
	}
	
	public void generatePmpmByPracticeExpandReportXLSX(PmpmByPracticeExpandReportFileVM fileVM, OutputStream os)  throws IOException {

		Workbook workbook = new XSSFWorkbook();
	    Sheet sheet = workbook.createSheet("PMPM By Practice Expand Report");

	    org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
	    headerFont.setBold(true);
	    headerFont.setFontHeightInPoints((short) 12);

	    CellStyle headerCellStyle = workbook.createCellStyle();
	    headerCellStyle.setFont(headerFont);
	    
	 // Create a Row
	    Row headerRow = sheet.createRow(0);
	    int headerIndex = 0;
	    

	    if(fileVM.showPatientName_pmpmByPracticeExpand) {
	    	Cell cell1 = headerRow.createCell(headerIndex);
	    	cell1.setCellValue("Patient Name");
	    	cell1.setCellStyle(headerCellStyle);
	    }
	      
	    if(fileVM.showPcpName_pmpmByPracticeExpand) {
	      Cell cell0 = headerRow.createCell(++headerIndex);
	      cell0.setCellValue("PCP Name");
	      cell0.setCellStyle(headerCellStyle);
	    }
	      
	      if(fileVM.showPcpLocation_pmpmByPracticeExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("PCP Location");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showMra_pmpmByPracticeExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("MRA");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showCost_pmpmByPracticeExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Cost");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showClaimType_pmpmByPracticeExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Claim Type");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	   // Create Other rows and cells
	      int rowNum = 1;
	      int rowIndex = 0;
	      List<PmpmByPracticeExpandReportPrintDataVM> list = getDataForPmpmByPracticeExpandReportPrint(fileVM);
	      for (PmpmByPracticeExpandReportPrintDataVM vm : list) {
	    	  rowIndex = 0;
	    	  Row row = sheet.createRow(rowNum);
	    	  
	    	  if(fileVM.showPatientName_pmpmByPracticeExpand) {
		    	  row.createCell(rowIndex).setCellValue(vm.getPatientName());
		      }
		      
	    	  if(fileVM.showPcpName_pmpmByPracticeExpand) {
	          	  row.createCell(++rowIndex).setCellValue(vm.getPcpName());
	    	  }
		    
		      if(fileVM.showPcpLocation_pmpmByPracticeExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getPcpLocation());
		      }
		      
		      if(fileVM.showMra_pmpmByPracticeExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getMra());
		      }
		      
		      if(fileVM.showCost_pmpmByPracticeExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getCost());
		      }
		      
		      if(fileVM.showClaimType_pmpmByPracticeExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getClaimType());
		      }
		      
		      rowNum = rowNum + 1;
		      
	        }
	      
	   // Resize all columns to fit the content size
	      for(int i=0;i<headerIndex;i++) {
	    	  sheet.autoSizeColumn(i);
	      }
	      FileOutputStream fileOut = new FileOutputStream("Data export-PMPM By Practice Expand Report Search.xlsx");
	      workbook.write(fileOut);
	      InputStream is = new FileInputStream(new File("Data export-PMPM By Practice Expand Report Search.xlsx"));
	      IOUtils.copy(is, os);
	      //Files.copy(new File("Data export-Claims Search.xlsx").toPath(), os);
	      fileOut.close();
	}
	
	public void generateBeneficiariesManagementByLocationReportXLSX(BeneficiariesManagementByLocationReportFileVM fileVM, OutputStream os)  throws IOException {

		Workbook workbook = new XSSFWorkbook();
	    Sheet sheet = workbook.createSheet("Beneficiaries Management Report By Location");

	    org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
	    headerFont.setBold(true);
	    headerFont.setFontHeightInPoints((short) 12);

	    CellStyle headerCellStyle = workbook.createCellStyle();
	    headerCellStyle.setFont(headerFont);
	    
	 // Create a Row
	    Row headerRow = sheet.createRow(0);
	    int headerIndex = 0;
	    

	    if(fileVM.showPcpLocation_beneficiariesManagementByLocation) {
	    	Cell cell1 = headerRow.createCell(headerIndex);
	    	cell1.setCellValue("PCP Location");
	    	cell1.setCellStyle(headerCellStyle);
	    }
	      
	    if(fileVM.showMra_beneficiariesManagementByLocation) {
	      Cell cell0 = headerRow.createCell(++headerIndex);
	      cell0.setCellValue("Average MRA");
	      cell0.setCellStyle(headerCellStyle);
	    }
	      
	      if(fileVM.showTotalCost_beneficiariesManagementByLocation) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Total Cost");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	   // Create Other rows and cells
	      int rowNum = 1;
	      int rowIndex = 0;
	      List<BeneficiariesManagementByLocationReportPrintDataVM> list = getDataForBeneficiariesManagementByLocationReportPrint(fileVM);
	      for (BeneficiariesManagementByLocationReportPrintDataVM vm : list) {
	    	  rowIndex = 0;
	    	  Row row = sheet.createRow(rowNum);
	    	  
	    	  if(fileVM.showPcpLocation_beneficiariesManagementByLocation) {
		    	  row.createCell(rowIndex).setCellValue(vm.getPcpLocation());
		      }
		      
	    	  if(fileVM.showMra_beneficiariesManagementByLocation) {
	          	  row.createCell(++rowIndex).setCellValue(vm.getMra());
	    	  }
		    
		      if(fileVM.showTotalCost_beneficiariesManagementByLocation) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getTotalCost());
		      }
		      
		      rowNum = rowNum + 1;
		      
	        }
	      
	   // Resize all columns to fit the content size
	      for(int i=0;i<headerIndex;i++) {
	    	  sheet.autoSizeColumn(i);
	      }
	      FileOutputStream fileOut = new FileOutputStream("Data export-Beneficiaries Management Report By Location.xlsx");
	      workbook.write(fileOut);
	      InputStream is = new FileInputStream(new File("Data export-Beneficiaries Management Report By Location.xlsx"));
	      IOUtils.copy(is, os);
	      //Files.copy(new File("Data export-Claims Search.xlsx").toPath(), os);
	      fileOut.close();
	}
	
	public void generateBeneficiariesManagementByClinicReportXLSX(BeneficiariesManagementByClinicReportFileVM fileVM, OutputStream os)  throws IOException {

		Workbook workbook = new XSSFWorkbook();
	    Sheet sheet = workbook.createSheet("Beneficiaries Management Report By Clinic");

	    org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
	    headerFont.setBold(true);
	    headerFont.setFontHeightInPoints((short) 12);

	    CellStyle headerCellStyle = workbook.createCellStyle();
	    headerCellStyle.setFont(headerFont);
	    
	 // Create a Row
	    Row headerRow = sheet.createRow(0);
	    int headerIndex = 0;
	    

	    if(fileVM.showClinicName_beneficiariesManagementByClinic) {
	    	Cell cell1 = headerRow.createCell(headerIndex);
	    	cell1.setCellValue("Clinic Name");
	    	cell1.setCellStyle(headerCellStyle);
	    }
	      
	      if(fileVM.showTotalCost_beneficiariesManagementByClinic) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Total Cost");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	   // Create Other rows and cells
	      int rowNum = 1;
	      int rowIndex = 0;
	      List<BeneficiariesManagementByClinicReportPrintDataVM> list = getDataForBeneficiariesManagementByClinicReportPrint(fileVM);
	      for (BeneficiariesManagementByClinicReportPrintDataVM vm : list) {
	    	  rowIndex = 0;
	    	  Row row = sheet.createRow(rowNum);
	    	  
	    	  if(fileVM.showClinicName_beneficiariesManagementByClinic) {
		    	  row.createCell(rowIndex).setCellValue(vm.getClinicName());
		      }
	    	  
		      if(fileVM.showTotalCost_beneficiariesManagementByClinic) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getTotalCost());
		      }
		      
		      rowNum = rowNum + 1;
		      
	        }
	      
	   // Resize all columns to fit the content size
	      for(int i=0;i<headerIndex;i++) {
	    	  sheet.autoSizeColumn(i);
	      }
	      FileOutputStream fileOut = new FileOutputStream("Data export-Beneficiaries Management Report By Clinic.xlsx");
	      workbook.write(fileOut);
	      InputStream is = new FileInputStream(new File("Data export-Beneficiaries Management Report By Clinic.xlsx"));
	      IOUtils.copy(is, os);
	      //Files.copy(new File("Data export-Claims Search.xlsx").toPath(), os);
	      fileOut.close();
	}
	
	public void generateBeneficiariesManagementReportXLSX(BeneficiariesManagementReportFileVM fileVM, OutputStream os)  throws IOException {

		Workbook workbook = new XSSFWorkbook();
	    Sheet sheet = workbook.createSheet("Beneficiaries Management Report");

	    org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
	    headerFont.setBold(true);
	    headerFont.setFontHeightInPoints((short) 12);

	    CellStyle headerCellStyle = workbook.createCellStyle();
	    headerCellStyle.setFont(headerFont);
	    
	 // Create a Row
	    Row headerRow = sheet.createRow(0);
	    int headerIndex = 0;
	    

	    if(fileVM.showPlanName_beneficiariesManagement) {
	    	Cell cell1 = headerRow.createCell(headerIndex);
	    	cell1.setCellValue("Plan Name");
	    	cell1.setCellStyle(headerCellStyle);
	    }
	      
	    if(fileVM.showHicn_beneficiariesManagement) {
	      Cell cell0 = headerRow.createCell(++headerIndex);
	      cell0.setCellValue("HICN/ Subscriber ID");
	      cell0.setCellStyle(headerCellStyle);
	    }
	      
	      if(fileVM.showPatientName_beneficiariesManagement) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Patient Name");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showDob_beneficiariesManagement) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("DOB");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showEligibleMonth_beneficiariesManagement) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Eligible Month");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showTermedMonth_beneficiariesManagement) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Termed Month");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showPcpName_beneficiariesManagement) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("PCP Name");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showPcpLocation_beneficiariesManagement) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("PCP Location");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showMra_beneficiariesManagement) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("MRA");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showTotalCost_beneficiariesManagement) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Total Cost");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showAddress_beneficiariesManagement) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Address");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showRecentAppointmentDate_beneficiariesManagement) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Recent Appointment Date");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showNextAppointmentDate_beneficiariesManagement) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Next Appointment Date");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showFacilityLocation_beneficiariesManagement) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Facility Location");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showPhoneNumber_beneficiariesManagement) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Phone Number");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showLastClaimsDate_beneficiariesManagement) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Last Claims Date");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showIcdCode_beneficiariesManagement) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("ICD Code");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	   // Create Other rows and cells
	      int rowNum = 1;
	      int rowIndex = 0;
	      List<BeneficiariesManagementReportPrintDataVM> list = getDataForBeneficiariesManagementReportPrint(fileVM);
	      for (BeneficiariesManagementReportPrintDataVM vm : list) {
	    	  rowIndex = 0;
	    	  Row row = sheet.createRow(rowNum);
	    	  
	    	  if(fileVM.showPlanName_beneficiariesManagement) {
		    	  row.createCell(rowIndex).setCellValue(vm.getPlanName());
		      }
		      
	    	  if(fileVM.showHicn_beneficiariesManagement) {
	          	  row.createCell(++rowIndex).setCellValue(vm.getHicn());
	    	  }
		    
		      if(fileVM.showPatientName_beneficiariesManagement) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getPatientName());
		      }
		      
		      if(fileVM.showDob_beneficiariesManagement) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getDob());
		      }
		      
		      if(fileVM.showEligibleMonth_beneficiariesManagement) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getEligibleMonth());
		      }
		      
		      if(fileVM.showTermedMonth_beneficiariesManagement) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getTermedMonth());
		      }
		      
		      if(fileVM.showPcpName_beneficiariesManagement) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getPcpName());
		      }
		      
		      if(fileVM.showPcpLocation_beneficiariesManagement) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getPcpLocation());
		      }
		      
		      if(fileVM.showMra_beneficiariesManagement) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getMra());
		      }
		      
		      if(fileVM.showTotalCost_beneficiariesManagement) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getTotalCost());
		      }
		      
		      if(fileVM.showAddress_beneficiariesManagement) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getAddress());
		      }
		      
		      if(fileVM.showRecentAppointmentDate_beneficiariesManagement) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getRecentAppointmentDate());
		      }
		      
		      if(fileVM.showNextAppointmentDate_beneficiariesManagement) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getNextAppointmentDate());
		      }
		      
		      if(fileVM.showFacilityLocation_beneficiariesManagement) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getFacilityLocation());
		      }
		      
		      if(fileVM.showPhoneNumber_beneficiariesManagement) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getPhoneNumber());
		      }
		      
		      if(fileVM.showLastClaimsDate_beneficiariesManagement) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getLastClaimsDate());
		      }
		      
		      if(fileVM.showIcdCode_beneficiariesManagement) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getIcdCode());
		      }
		      
		      rowNum = rowNum + 1;
		      
	        }
	      
	   // Resize all columns to fit the content size
	      for(int i=0;i<headerIndex;i++) {
	    	  sheet.autoSizeColumn(i);
	      }
	      FileOutputStream fileOut = new FileOutputStream("Data export-Beneficiaries Management Report.xlsx");
	      workbook.write(fileOut);
	      InputStream is = new FileInputStream(new File("Data export-Beneficiaries Management Report.xlsx"));
	      IOUtils.copy(is, os);
	      //Files.copy(new File("Data export-Claims Search.xlsx").toPath(), os);
	      fileOut.close();
	}
	
	public void generateBeneficiariesManagementExpandReportXLSX(BeneficiariesManagementReportExpandFileVM fileVM, OutputStream os)  throws IOException {

		Workbook workbook = new XSSFWorkbook();
	    Sheet sheet = workbook.createSheet("Beneficiaries Management Details");

	    org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
	    headerFont.setBold(true);
	    headerFont.setFontHeightInPoints((short) 12);

	    CellStyle headerCellStyle = workbook.createCellStyle();
	    headerCellStyle.setFont(headerFont);
	    
	 // Create a Row
	    Row headerRow = sheet.createRow(0);
	    int headerIndex = 0;
	    

	    if(fileVM.showClaimId_beneficiariesManagementExpand) {
	    	Cell cell1 = headerRow.createCell(headerIndex);
	    	cell1.setCellValue("Claim Id");
	    	cell1.setCellStyle(headerCellStyle);
	    }
	      
	    if(fileVM.showClaimDate_beneficiariesManagementExpand) {
	      Cell cell0 = headerRow.createCell(++headerIndex);
	      cell0.setCellValue("Claim Date");
	      cell0.setCellStyle(headerCellStyle);
	    }
	      
	      if(fileVM.showClaimType_beneficiariesManagementExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Claim Type");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showClinicName_beneficiariesManagementExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Clinic Name");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showPcpName_beneficiariesManagementExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("PCP Name");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showIcdCodes_beneficiariesManagementExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("ICD Codes");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showHccCodes_beneficiariesManagementExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("HCC Codes");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showDrgCode_beneficiariesManagementExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("DRG Code");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showBetosCat_beneficiariesManagementExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Betos Cat");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showCost_beneficiariesManagementExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Cost");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      
	   // Create Other rows and cells
	      int rowNum = 1;
	      int rowIndex = 0;
	      List<BeneficiariesManagementExpandReportPrintDataVM> list = getDataForBeneficiariesManagementExpandReportPrint(fileVM);
	      for (BeneficiariesManagementExpandReportPrintDataVM vm : list) {
	    	  rowIndex = 0;
	    	  Row row = sheet.createRow(rowNum);
	    	  
	    	  if(fileVM.showClaimId_beneficiariesManagementExpand) {
		    	  row.createCell(rowIndex).setCellValue(vm.getClaimId());
		      }
		      
	    	  if(fileVM.showClaimDate_beneficiariesManagementExpand) {
	          	  row.createCell(++rowIndex).setCellValue(vm.getClaimDate());
	    	  }
		    
		      if(fileVM.showClaimType_beneficiariesManagementExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getClaimType());
		      }
		      
		      if(fileVM.showClinicName_beneficiariesManagementExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getClinicName());
		      }
		      
		      if(fileVM.showPcpName_beneficiariesManagementExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getPcpName());
		      }
		      
		      if(fileVM.showIcdCodes_beneficiariesManagementExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getIcdCodes());
		      }
		      
		      if(fileVM.showHccCodes_beneficiariesManagementExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getHccCodes());
		      }
		      
		      if(fileVM.showDrgCode_beneficiariesManagementExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getDrgCode());
		      }
		      
		      if(fileVM.showBetosCat_beneficiariesManagementExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getBetosCat());
		      }
		      
		      if(fileVM.showCost_beneficiariesManagementExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getCost());
		      }
		      
		      rowNum = rowNum + 1;
		      
	        }
	      
	   // Resize all columns to fit the content size
	      for(int i=0;i<headerIndex;i++) {
	    	  sheet.autoSizeColumn(i);
	      }
	      FileOutputStream fileOut = new FileOutputStream("Data export-Beneficiaries Management Details.xlsx");
	      workbook.write(fileOut);
	      InputStream is = new FileInputStream(new File("Data export-Beneficiaries Management Details.xlsx"));
	      IOUtils.copy(is, os);
	      //Files.copy(new File("Data export-Claims Search.xlsx").toPath(), os);
	      fileOut.close();
	}
	
	public void generateBeneficiariesManagementByDoctorExpandReportXLSX(BeneficiariesManagementByDoctorReportExpandFileVM fileVM, OutputStream os)  throws IOException {

		Workbook workbook = new XSSFWorkbook();
	    Sheet sheet = workbook.createSheet("Beneficiaries Management Details");

	    org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
	    headerFont.setBold(true);
	    headerFont.setFontHeightInPoints((short) 12);

	    CellStyle headerCellStyle = workbook.createCellStyle();
	    headerCellStyle.setFont(headerFont);
	    
	 // Create a Row
	    Row headerRow = sheet.createRow(0);
	    int headerIndex = 0;
	    

	    if(fileVM.showClaimId_beneficiariesManagementByDoctorExpand) {
	    	Cell cell1 = headerRow.createCell(headerIndex);
	    	cell1.setCellValue("Claim Id");
	    	cell1.setCellStyle(headerCellStyle);
	    }
	      
	    if(fileVM.showClaimDate_beneficiariesManagementByDoctorExpand) {
	      Cell cell0 = headerRow.createCell(++headerIndex);
	      cell0.setCellValue("Claim Date");
	      cell0.setCellStyle(headerCellStyle);
	    }
	      
	      if(fileVM.showClaimType_beneficiariesManagementByDoctorExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Claim Type");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showClinicName_beneficiariesManagementByDoctorExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Clinic Name");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showPcpName_beneficiariesManagementByDoctorExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("PCP Name");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showIcdCodes_beneficiariesManagementByDoctorExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("ICD Codes");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showHccCodes_beneficiariesManagementByDoctorExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("HCC Codes");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showDrgCode_beneficiariesManagementByDoctorExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("DRG Code");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showBetosCat_beneficiariesManagementByDoctorExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Betos Cat");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showCost_beneficiariesManagementByDoctorExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Cost");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      
	   // Create Other rows and cells
	      int rowNum = 1;
	      int rowIndex = 0;
	      List<BeneficiariesManagementExpandReportPrintDataVM> list = getDataForBeneficiariesManagementByDoctorExpandReportPrint(fileVM);
	      for (BeneficiariesManagementExpandReportPrintDataVM vm : list) {
	    	  rowIndex = 0;
	    	  Row row = sheet.createRow(rowNum);
	    	  
	    	  if(fileVM.showClaimId_beneficiariesManagementByDoctorExpand) {
		    	  row.createCell(rowIndex).setCellValue(vm.getClaimId());
		      }
		      
	    	  if(fileVM.showClaimDate_beneficiariesManagementByDoctorExpand) {
	          	  row.createCell(++rowIndex).setCellValue(vm.getClaimDate());
	    	  }
		    
		      if(fileVM.showClaimType_beneficiariesManagementByDoctorExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getClaimType());
		      }
		      
		      if(fileVM.showClinicName_beneficiariesManagementByDoctorExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getClinicName());
		      }
		      
		      if(fileVM.showPcpName_beneficiariesManagementByDoctorExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getPcpName());
		      }
		      
		      if(fileVM.showIcdCodes_beneficiariesManagementByDoctorExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getIcdCodes());
		      }
		      
		      if(fileVM.showHccCodes_beneficiariesManagementByDoctorExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getHccCodes());
		      }
		      
		      if(fileVM.showDrgCode_beneficiariesManagementByDoctorExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getDrgCode());
		      }
		      
		      if(fileVM.showBetosCat_beneficiariesManagementByDoctorExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getBetosCat());
		      }
		      
		      if(fileVM.showCost_beneficiariesManagementByDoctorExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getCost());
		      }
		      
		      rowNum = rowNum + 1;
		      
	        }
	      
	   // Resize all columns to fit the content size
	      for(int i=0;i<headerIndex;i++) {
	    	  sheet.autoSizeColumn(i);
	      }
	      FileOutputStream fileOut = new FileOutputStream("Data export-Beneficiaries Management By Doctor Details.xlsx");
	      workbook.write(fileOut);
	      InputStream is = new FileInputStream(new File("Data export-Beneficiaries Management By Doctor Details.xlsx"));
	      IOUtils.copy(is, os);
	      //Files.copy(new File("Data export-Claims Search.xlsx").toPath(), os);
	      fileOut.close();
	}
	
	public void generateBeneficiariesManagementByLocationExpandReportXLSX(BeneficiariesManagementByLocationReportExpandFileVM fileVM, OutputStream os)  throws IOException {

		Workbook workbook = new XSSFWorkbook();
	    Sheet sheet = workbook.createSheet("Beneficiaries Management Details");

	    org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
	    headerFont.setBold(true);
	    headerFont.setFontHeightInPoints((short) 12);

	    CellStyle headerCellStyle = workbook.createCellStyle();
	    headerCellStyle.setFont(headerFont);
	    
	 // Create a Row
	    Row headerRow = sheet.createRow(0);
	    int headerIndex = 0;
	    

	    if(fileVM.showClaimId_beneficiariesManagementByLocationExpand) {
	    	Cell cell1 = headerRow.createCell(headerIndex);
	    	cell1.setCellValue("Claim Id");
	    	cell1.setCellStyle(headerCellStyle);
	    }
	      
	    if(fileVM.showClaimDate_beneficiariesManagementByLocationExpand) {
	      Cell cell0 = headerRow.createCell(++headerIndex);
	      cell0.setCellValue("Claim Date");
	      cell0.setCellStyle(headerCellStyle);
	    }
	      
	      if(fileVM.showClaimType_beneficiariesManagementByLocationExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Claim Type");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showClinicName_beneficiariesManagementByLocationExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Clinic Name");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showPcpLocation_beneficiariesManagementByLocationExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("PCP Location");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showIcdCodes_beneficiariesManagementByLocationExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("ICD Codes");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showHccCodes_beneficiariesManagementByLocationExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("HCC Codes");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showDrgCode_beneficiariesManagementByLocationExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("DRG Code");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showBetosCat_beneficiariesManagementByLocationExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Betos Cat");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showCost_beneficiariesManagementByLocationExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Cost");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      
	   // Create Other rows and cells
	      int rowNum = 1;
	      int rowIndex = 0;
	      List<BeneficiariesManagementExpandReportPrintDataVM> list = getDataForBeneficiariesManagementByLocationExpandReportPrint(fileVM);
	      for (BeneficiariesManagementExpandReportPrintDataVM vm : list) {
	    	  rowIndex = 0;
	    	  Row row = sheet.createRow(rowNum);
	    	  
	    	  if(fileVM.showClaimId_beneficiariesManagementByLocationExpand) {
		    	  row.createCell(rowIndex).setCellValue(vm.getClaimId());
		      }
		      
	    	  if(fileVM.showClaimDate_beneficiariesManagementByLocationExpand) {
	          	  row.createCell(++rowIndex).setCellValue(vm.getClaimDate());
	    	  }
		    
		      if(fileVM.showClaimType_beneficiariesManagementByLocationExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getClaimType());
		      }
		      
		      if(fileVM.showClinicName_beneficiariesManagementByLocationExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getClinicName());
		      }
		      
		      if(fileVM.showPcpLocation_beneficiariesManagementByLocationExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getPcpLocation());
		      }
		      
		      if(fileVM.showIcdCodes_beneficiariesManagementByLocationExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getIcdCodes());
		      }
		      
		      if(fileVM.showHccCodes_beneficiariesManagementByLocationExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getHccCodes());
		      }
		      
		      if(fileVM.showDrgCode_beneficiariesManagementByLocationExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getDrgCode());
		      }
		      
		      if(fileVM.showBetosCat_beneficiariesManagementByLocationExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getBetosCat());
		      }
		      
		      if(fileVM.showCost_beneficiariesManagementByLocationExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getCost());
		      }
		      
		      rowNum = rowNum + 1;
		      
	        }
	      
	   // Resize all columns to fit the content size
	      for(int i=0;i<headerIndex;i++) {
	    	  sheet.autoSizeColumn(i);
	      }
	      FileOutputStream fileOut = new FileOutputStream("Data export-Beneficiaries Management By Location Details.xlsx");
	      workbook.write(fileOut);
	      InputStream is = new FileInputStream(new File("Data export-Beneficiaries Management By Location Details.xlsx"));
	      IOUtils.copy(is, os);
	      //Files.copy(new File("Data export-Claims Search.xlsx").toPath(), os);
	      fileOut.close();
	}
	
	public void generateBeneficiariesManagementByClinicExpandReportXLSX(BeneficiariesManagementByClinicReportExpandFileVM fileVM, OutputStream os)  throws IOException {

		Workbook workbook = new XSSFWorkbook();
	    Sheet sheet = workbook.createSheet("Beneficiaries Management Details");

	    org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
	    headerFont.setBold(true);
	    headerFont.setFontHeightInPoints((short) 12);

	    CellStyle headerCellStyle = workbook.createCellStyle();
	    headerCellStyle.setFont(headerFont);
	    
	 // Create a Row
	    Row headerRow = sheet.createRow(0);
	    int headerIndex = 0;
	    

	    if(fileVM.showClaimId_beneficiariesManagementByClinicExpand) {
	    	Cell cell1 = headerRow.createCell(headerIndex);
	    	cell1.setCellValue("Claim Id");
	    	cell1.setCellStyle(headerCellStyle);
	    }
	      
	    if(fileVM.showClaimDate_beneficiariesManagementByClinicExpand) {
	      Cell cell0 = headerRow.createCell(++headerIndex);
	      cell0.setCellValue("Claim Date");
	      cell0.setCellStyle(headerCellStyle);
	    }
	      
	      if(fileVM.showClaimType_beneficiariesManagementByClinicExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Claim Type");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showClinicName_beneficiariesManagementByClinicExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Clinic Name");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showPcpName_beneficiariesManagementByClinicExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("PCP Name");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showIcdCodes_beneficiariesManagementByClinicExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("ICD Codes");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showHccCodes_beneficiariesManagementByClinicExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("HCC Codes");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showDrgCode_beneficiariesManagementByClinicExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("DRG Code");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showBetosCat_beneficiariesManagementByClinicExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Betos Cat");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showCost_beneficiariesManagementByClinicExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Cost");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      
	   // Create Other rows and cells
	      int rowNum = 1;
	      int rowIndex = 0;
	      List<BeneficiariesManagementExpandReportPrintDataVM> list = getDataForBeneficiariesManagementByClinicExpandReportPrint(fileVM);
	      for (BeneficiariesManagementExpandReportPrintDataVM vm : list) {
	    	  rowIndex = 0;
	    	  Row row = sheet.createRow(rowNum);
	    	  
	    	  if(fileVM.showClaimId_beneficiariesManagementByClinicExpand) {
		    	  row.createCell(rowIndex).setCellValue(vm.getClaimId());
		      }
		      
	    	  if(fileVM.showClaimDate_beneficiariesManagementByClinicExpand) {
	          	  row.createCell(++rowIndex).setCellValue(vm.getClaimDate());
	    	  }
		    
		      if(fileVM.showClaimType_beneficiariesManagementByClinicExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getClaimType());
		      }
		      
		      if(fileVM.showClinicName_beneficiariesManagementByClinicExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getClinicName());
		      }
		      
		      if(fileVM.showPcpName_beneficiariesManagementByClinicExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getPcpName());
		      }
		      
		      if(fileVM.showIcdCodes_beneficiariesManagementByClinicExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getIcdCodes());
		      }
		      
		      if(fileVM.showHccCodes_beneficiariesManagementByClinicExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getHccCodes());
		      }
		      
		      if(fileVM.showDrgCode_beneficiariesManagementByClinicExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getDrgCode());
		      }
		      
		      if(fileVM.showBetosCat_beneficiariesManagementByClinicExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getBetosCat());
		      }
		      
		      if(fileVM.showCost_beneficiariesManagementByClinicExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getCost());
		      }
		      
		      rowNum = rowNum + 1;
		      
	        }
	      
	   // Resize all columns to fit the content size
	      for(int i=0;i<headerIndex;i++) {
	    	  sheet.autoSizeColumn(i);
	      }
	      FileOutputStream fileOut = new FileOutputStream("Data export-Beneficiaries Management By Location Details.xlsx");
	      workbook.write(fileOut);
	      InputStream is = new FileInputStream(new File("Data export-Beneficiaries Management By Location Details.xlsx"));
	      IOUtils.copy(is, os);
	      //Files.copy(new File("Data export-Claims Search.xlsx").toPath(), os);
	      fileOut.close();
	}

	
	public void generateBeneficiariesManagementByDoctorXLSX(BeneficiariesManagementByDoctorFileVM fileVM, OutputStream os)  throws IOException {

		Workbook workbook = new XSSFWorkbook();
	    Sheet sheet = workbook.createSheet("Beneficiaries Management By Doctor");

	    org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
	    headerFont.setBold(true);
	    headerFont.setFontHeightInPoints((short) 12);

	    CellStyle headerCellStyle = workbook.createCellStyle();
	    headerCellStyle.setFont(headerFont);
	    
	 // Create a Row
	    Row headerRow = sheet.createRow(0);
	    int headerIndex = 0;
	    

	    if(fileVM.showPcpName_beneficiariesManagementByDoctor) {
	    	Cell cell1 = headerRow.createCell(headerIndex);
	    	cell1.setCellValue("PCP Name");
	    	cell1.setCellStyle(headerCellStyle);
	    }
	      
	    if(fileVM.showPcpLocation_beneficiariesManagementByDoctor) {
	      Cell cell0 = headerRow.createCell(++headerIndex);
	      cell0.setCellValue("PCP Location");
	      cell0.setCellStyle(headerCellStyle);
	    }
	      
	      if(fileVM.showAverageMra_beneficiariesManagementByDoctor) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Average MRA");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showTotalCost_beneficiariesManagementByDoctor) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Total Cost");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      
	   // Create Other rows and cells
	      int rowNum = 1;
	      int rowIndex = 0;
	      List<BeneficiariesManagementByDoctorPrintDataVM> list = getDataForBeneficiariesManagementByDoctorPrint(fileVM);
	      for (BeneficiariesManagementByDoctorPrintDataVM vm : list) {
	    	  rowIndex = 0;
	    	  Row row = sheet.createRow(rowNum);
	    	  
	    	  if(fileVM.showPcpName_beneficiariesManagementByDoctor) {
		    	  row.createCell(rowIndex).setCellValue(vm.getPcpName());
		      }
		      
	    	  if(fileVM.showPcpLocation_beneficiariesManagementByDoctor) {
	          	  row.createCell(++rowIndex).setCellValue(vm.getPcpLocation());
	    	  }
		    
		      if(fileVM.showAverageMra_beneficiariesManagementByDoctor) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getAverageMra());
		      }
		      
		      if(fileVM.showTotalCost_beneficiariesManagementByDoctor) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getTotalCost());
		      }
		      
		      rowNum = rowNum + 1;
		      
	        }
	      
	   // Resize all columns to fit the content size
	      for(int i=0;i<headerIndex;i++) {
	    	  sheet.autoSizeColumn(i);
	      }
	      FileOutputStream fileOut = new FileOutputStream("Data export-Beneficiaries Management By Doctor.xlsx");
	      workbook.write(fileOut);
	      InputStream is = new FileInputStream(new File("Data export-Beneficiaries Management By Doctor.xlsx"));
	      IOUtils.copy(is, os);
	      //Files.copy(new File("Data export-Claims Search.xlsx").toPath(), os);
	      fileOut.close();
	}
	
	public void generateDuplicateClaimsExpandReportXLSX(DuplicateClaimsExpandFileVM fileVM, OutputStream os)  throws IOException {

		Workbook workbook = new XSSFWorkbook();
	    Sheet sheet = workbook.createSheet("Duplicate Claims Details");

	    org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
	    headerFont.setBold(true);
	    headerFont.setFontHeightInPoints((short) 12);

	    CellStyle headerCellStyle = workbook.createCellStyle();
	    headerCellStyle.setFont(headerFont);
	    
	 // Create a Row
	    Row headerRow = sheet.createRow(0);
	    int headerIndex = 0;
	    

	    if(fileVM.showClaimId_duplicateClaimsExpand) {
	    	Cell cell1 = headerRow.createCell(headerIndex);
	    	cell1.setCellValue("Claim Id");
	    	cell1.setCellStyle(headerCellStyle);
	    }
	      
	    if(fileVM.showClaimDate_duplicateClaimsExpand) {
	      Cell cell0 = headerRow.createCell(++headerIndex);
	      cell0.setCellValue("Claim Date");
	      cell0.setCellStyle(headerCellStyle);
	    }
	      
	      if(fileVM.showClaimType_duplicateClaimsExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Claim Type");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showClinicName_duplicateClaimsExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Clinic Name");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showProviderName_duplicateClaimsExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Provider Name");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showBetosCat_duplicateClaimsExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Betos Cat");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showDrgCode_duplicateClaimsExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("DRG Code");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showIcdCodes_duplicateClaimsExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("ICD Code 9/10 Code(s)");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showHccCodes_duplicateClaimsExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("HCC Code(s)");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showCost_duplicateClaimsExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Cost");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      
	   // Create Other rows and cells
	      int rowNum = 1;
	      int rowIndex = 0;
	      List<DuplicateClaimsExpandPrintDataVM> list = getDataForDuplicateClaimsExpandPrint(fileVM);
	      for (DuplicateClaimsExpandPrintDataVM vm : list) {
	    	  rowIndex = 0;
	    	  Row row = sheet.createRow(rowNum);
	    	  
	    	  if(fileVM.showClaimId_duplicateClaimsExpand) {
		    	  row.createCell(rowIndex).setCellValue(vm.getClaimId());
		      }
		      
	    	  if(fileVM.showClaimDate_duplicateClaimsExpand) {
	          	  row.createCell(++rowIndex).setCellValue(vm.getClaimDate());
	    	  }
		    
		      if(fileVM.showClaimType_duplicateClaimsExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getClaimType());
		      }
		      
		      if(fileVM.showClinicName_duplicateClaimsExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getClinicName());
		      }
		      
		      if(fileVM.showProviderName_duplicateClaimsExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getProviderName());
		      }
		      
		      if(fileVM.showBetosCat_duplicateClaimsExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getBetosCat());
		      }
		      
		      if(fileVM.showDrgCode_duplicateClaimsExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getDrgCode());
		      }
		      
		      if(fileVM.showIcdCodes_duplicateClaimsExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getIcdCodes());
		      }
		      
		      if(fileVM.showHccCodes_duplicateClaimsExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getHccCodes());
		      }
		      
		      if(fileVM.showCost_duplicateClaimsExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getCost());
		      }
		      
		      rowNum = rowNum + 1;
		      
	        }
	      
	   // Resize all columns to fit the content size
	      for(int i=0;i<headerIndex;i++) {
	    	  sheet.autoSizeColumn(i);
	      }
	      FileOutputStream fileOut = new FileOutputStream("Data export-Duplicate Claims Details.xlsx");
	      workbook.write(fileOut);
	      InputStream is = new FileInputStream(new File("Data export-Duplicate Claims Details.xlsx"));
	      IOUtils.copy(is, os);
	      //Files.copy(new File("Data export-Claims Search.xlsx").toPath(), os);
	      fileOut.close();
	}
	
	public void generateMembershipManagementXLSX(MembershipManagementFileVM fileVM, OutputStream os)  throws IOException {

		Workbook workbook = new XSSFWorkbook();
	    Sheet sheet = workbook.createSheet("Membership Management");

	    org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
	    headerFont.setBold(true);
	    headerFont.setFontHeightInPoints((short) 12);

	    CellStyle headerCellStyle = workbook.createCellStyle();
	    headerCellStyle.setFont(headerFont);
	    
	 // Create a Row
	    Row headerRow = sheet.createRow(0);
	    int headerIndex = 0;
	    

	    if(fileVM.showPlanName_membershipManagement) {
	    	Cell cell1 = headerRow.createCell(headerIndex);
	    	cell1.setCellValue("Plan Name");
	    	cell1.setCellStyle(headerCellStyle);
	    }
	      
	    if(fileVM.showMedicareId_membershipManagement) {
	      Cell cell0 = headerRow.createCell(++headerIndex);
	      cell0.setCellValue("Medicare ID");
	      cell0.setCellStyle(headerCellStyle);
	    }
	      
	      if(fileVM.showInsuranceId_membershipManagement) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Insurance ID");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showPatientName_membershipManagement) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Patient Name");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showPatientDob_membershipManagement) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Patient DOB");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showAssignedPcp_membershipManagement) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Assigned PCP");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showPcpLocation_membershipManagement) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("PCP Location");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showIpaEffectiveDate_membershipManagement) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("IPA Effective Date");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showMra_membershipManagement) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("MRA");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showTotalPatientCost_membershipManagement) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Total Patient Cost");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	   // Create Other rows and cells
	      int rowNum = 1;
	      int rowIndex = 0;
	      List<MembershipManagementPrintDataVM> list = getMembershipManagementPrint(fileVM);
	      for (MembershipManagementPrintDataVM vm : list) {
	    	  rowIndex = 0;
	    	  Row row = sheet.createRow(rowNum);
	    	  
	    	  if(fileVM.showPlanName_membershipManagement) {
		    	  row.createCell(rowIndex).setCellValue(vm.getPlanName());
		      }
		      
	    	  if(fileVM.showMedicareId_membershipManagement) {
	          	  row.createCell(++rowIndex).setCellValue(vm.getMedicareId());
	    	  }
		    
		      if(fileVM.showInsuranceId_membershipManagement) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getInsuranceId());
		      }
		      
		      if(fileVM.showPatientName_membershipManagement) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getPatientName());
		      }
		      
		      if(fileVM.showPatientDob_membershipManagement) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getPatientDob());
		      }
		      
		      if(fileVM.showAssignedPcp_membershipManagement) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getAssignedPcp());
		      }
		      
		      if(fileVM.showPcpLocation_membershipManagement) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getPcpLocation());
		      }
		      
		      if(fileVM.showIpaEffectiveDate_membershipManagement) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getIpaEffectiveDate());
		      }
		      
		      if(fileVM.showMra_membershipManagement) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getMra());
		      }
		      
		      if(fileVM.showTotalPatientCost_membershipManagement) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getTotalPatientCost());
		      }
		      
		      rowNum = rowNum + 1;
		      
	        }
	      
	   // Resize all columns to fit the content size
	      for(int i=0;i<headerIndex;i++) {
	    	  sheet.autoSizeColumn(i);
	      }
	      FileOutputStream fileOut = new FileOutputStream("Data export-Membership Management.xlsx");
	      workbook.write(fileOut);
	      InputStream is = new FileInputStream(new File("Data export-Membership Management.xlsx"));
	      IOUtils.copy(is, os);
	      //Files.copy(new File("Data export-Claims Search.xlsx").toPath(), os);
	      fileOut.close();
	}
	
	public void generateSummaryReportXLSX(SummaryReportFileVM fileVM, OutputStream os)  throws IOException {

		Workbook workbook = new XSSFWorkbook();
	    Sheet sheet = workbook.createSheet("Summary Report");

	    org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
	    headerFont.setBold(true);
	    headerFont.setFontHeightInPoints((short) 12);

	    CellStyle headerCellStyle = workbook.createCellStyle();
	    headerCellStyle.setFont(headerFont);
	    
	 // Create a Row
	    Row headerRow = sheet.createRow(0);
	    int headerIndex = 0;
	    

	    if(fileVM.showPcpLocation_summary) {
	    	Cell cell1 = headerRow.createCell(headerIndex);
	    	cell1.setCellValue("PCP Location");
	    	cell1.setCellStyle(headerCellStyle);
	    }
	      
	    if(fileVM.showMonth_summary) {
	      Cell cell0 = headerRow.createCell(++headerIndex);
	      cell0.setCellValue("Month");
	      cell0.setCellStyle(headerCellStyle);
	    }
	      
	      if(fileVM.showMembers_summary) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Members");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showMaPremium_summary) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Ma Premium");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showPartDPremium_summary) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Part D Premium");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showTotalPremium_summary) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Total Premium");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showIpaPremium_summary) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("IPA Premium");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showPcpCap_summary) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("PCP Cap");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showSpecCost_summary) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Spec Cost");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showProfClaims_summary) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Prof Claims");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showInstClaims_summary) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Inst Claims");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showRxClaims_summary) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Rx Claims");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showIbnrDollars_summary) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("IBNR Dollars");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showReinsurancePremium_summary) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Reinsurance Premium");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showSpecCap_summary) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Spec Cap");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showTotalExpenses_summary) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Total Expenses");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showReinsuranceRecovered_summary) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Reinsurance Recovered");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showRxAdmin_summary) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Rx Admin");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showSilverSneakerUtilization_summary) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Silver Sneaker Utilization");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showPba_summary) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("PBA");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showHumanaAtHome_summary) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Humana At Home");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showDentalFFS_summary) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Dental FFS");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	   // Create Other rows and cells
	      int rowNum = 1;
	      int rowIndex = 0;
	      List<SummaryReportPrintDataVM> list = getDataForSummaryReportPrint(fileVM);
	      for (SummaryReportPrintDataVM vm : list) {
	    	  rowIndex = 0;
	    	  Row row = sheet.createRow(rowNum);
	    	  
	    	  if(fileVM.showPcpLocation_summary) {
		    	  row.createCell(rowIndex).setCellValue(vm.getPcpLocation());
		      }
		      
	    	  if(fileVM.showMonth_summary) {
	          	  row.createCell(++rowIndex).setCellValue(vm.getMonth());
	    	  }
		    
		      if(fileVM.showMembers_summary) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getMembers());
		      }
		      
		      if(fileVM.showMaPremium_summary) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getMaPremium());
		      }
		      
		      if(fileVM.showPartDPremium_summary) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getPartDPremium());
		      }
		      
		      if(fileVM.showTotalPremium_summary) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getTotalPremium());
		      }
		      
		      if(fileVM.showIpaPremium_summary) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getIpaPremium());
		      }
		      
		      if(fileVM.showPcpCap_summary) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getPcpCap());
		      }
		      
		      if(fileVM.showSpecCost_summary) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getSpecCost());
		      }
		      
		      if(fileVM.showProfClaims_summary) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getProfClaims());
		      }
		      
		      if(fileVM.showInstClaims_summary) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getInstClaims());
		      }
		      
		      if(fileVM.showRxClaims_summary) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getRxClaims());
		      }
		      
		      if(fileVM.showIbnrDollars_summary) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getIbnrDollars());
		      }
		      
		      if(fileVM.showReinsurancePremium_summary) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getReinsurancePremium());
		      }
		      
		      if(fileVM.showSpecCap_summary) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getSpecCap());
		      }
		      
		      if(fileVM.showTotalExpenses_summary) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getTotalExpenses());
		      }
		      
		      if(fileVM.showReinsuranceRecovered_summary) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getReinsuranceRecovered());
		      }
		      
		      if(fileVM.showRxAdmin_summary) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getRxAdmin());
		      }
		      
		      if(fileVM.showSilverSneakerUtilization_summary) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getSilverSneakerUtilization());
		      }
		      
		      if(fileVM.showPba_summary) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getPba());
		      }
		      
		      if(fileVM.showHumanaAtHome_summary) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getHumanaAtHome());
		      }
		      
		      if(fileVM.showDentalFFS_summary) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getDentalFFS());
		      }
		      rowNum = rowNum + 1;
		      
	        }
	      
	   // Resize all columns to fit the content size
	      for(int i=0;i<headerIndex;i++) {
	    	  sheet.autoSizeColumn(i);
	      }
	      FileOutputStream fileOut = new FileOutputStream("Data export-Summary Report Search.xlsx");
	      workbook.write(fileOut);
	      InputStream is = new FileInputStream(new File("Data export-Summary Report Search.xlsx"));
	      IOUtils.copy(is, os);
	      //Files.copy(new File("Data export-Claims Search.xlsx").toPath(), os);
	      fileOut.close();
	}
	
	public void generateAdmissionsReportExpandXLSX(AdmissionsReportExpandFileVM fileVM, OutputStream os)  throws IOException {

		Workbook workbook = new XSSFWorkbook();
	    Sheet sheet = workbook.createSheet("Admissions Report");

	    org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
	    headerFont.setBold(true);
	    headerFont.setFontHeightInPoints((short) 12);

	    CellStyle headerCellStyle = workbook.createCellStyle();
	    headerCellStyle.setFont(headerFont);
	    
	 // Create a Row
	    Row headerRow = sheet.createRow(0);
	    int headerIndex = 0;
	    

	    if(fileVM.showClaimId_admissionsExpand) {
	    	Cell cell1 = headerRow.createCell(headerIndex);
	    	cell1.setCellValue("Claim Id");
	    	cell1.setCellStyle(headerCellStyle);
	    }
	      
	    if(fileVM.showClaimDate_admissionsExpand) {
	      Cell cell0 = headerRow.createCell(++headerIndex);
	      cell0.setCellValue("Claim Date");
	      cell0.setCellStyle(headerCellStyle);
	    }
	      
	      if(fileVM.showClaimType_admissionsExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Claim Type");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showClinicName_admissionsExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Clinic Name");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showPcpName_admissionsExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("PCP Name");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showIcdCodes_admissionsExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("ICD Codes");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      if(fileVM.showHccCodes_admissionsExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("HCC Codes");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      if(fileVM.showDrgCode_admissionsExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("DRG Code");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      if(fileVM.showBetosCat_admissionsExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Betos Cat");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      if(fileVM.showCost_admissionsExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Cost");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	   // Create Other rows and cells
	      int rowNum = 1;
	      int rowIndex = 0;
	      List<AdmissionsReportExpandVM> list = getDataForAdmissionsReportExpandXL(fileVM);
	      for (AdmissionsReportExpandVM vm : list) {
	    	  rowIndex = 0;
	    	  Row row = sheet.createRow(rowNum);
	    	  
	    	  if(fileVM.showClaimId_admissionsExpand) {
		    	  row.createCell(rowIndex).setCellValue(vm.getClaimId());
		      }
		      
	    	  if(fileVM.showClaimDate_admissionsExpand) {
	          	  row.createCell(++rowIndex).setCellValue(vm.getClaimDate());
	    	  }
		    
		      if(fileVM.showClaimType_admissionsExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getClaimType());
		      }
		      
		      if(fileVM.showClinicName_admissionsExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getClinicName());
		      }
		      
		      if(fileVM.showPcpName_admissionsExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getPcpName());
		      }
		      
		      if(fileVM.showIcdCodes_admissionsExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getIcdCodes());
		      }
		      
		      if(fileVM.showHccCodes_admissionsExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getHccCodes());
		      }
		      
		      if(fileVM.showDrgCode_admissionsExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getDrgCode());
		      }
		      
		      if(fileVM.showBetosCat_admissionsExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getBetosCat());
		      }
		      
		      if(fileVM.showCost_admissionsExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getCost());
		      }
		      
		      rowNum = rowNum + 1;
		      
	        }
	      
	   // Resize all columns to fit the content size
	      for(int i=0;i<headerIndex;i++) {
	    	  sheet.autoSizeColumn(i);
	      }
	      FileOutputStream fileOut = new FileOutputStream("Data export-Admissions Report Search.xlsx");
	      workbook.write(fileOut);
	      InputStream is = new FileInputStream(new File("Data export-Admissions Report Search.xlsx"));
	      IOUtils.copy(is, os);
	      fileOut.close();
	}
	
	public void generateSpecialistComparisonReportXLSX(SpecialistComparisonReportFileVM fileVM, OutputStream os)  throws IOException {

		Workbook workbook = new XSSFWorkbook();
	    Sheet sheet = workbook.createSheet("Specialist Comparison Report");

	    org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
	    headerFont.setBold(true);
	    headerFont.setFontHeightInPoints((short) 12);

	    CellStyle headerCellStyle = workbook.createCellStyle();
	    headerCellStyle.setFont(headerFont);
	    
	 // Create a Row
	    Row headerRow = sheet.createRow(0);
	    int headerIndex = 0;
	    

	    if(fileVM.showSpecialityCode_specialistComparison) {
	    	Cell cell1 = headerRow.createCell(headerIndex);
	    	cell1.setCellValue("Speciality Code");
	    	cell1.setCellStyle(headerCellStyle);
	    }
	      
	    if(fileVM.showNoOfPcp_specialistComparison) {
		      Cell cell0 = headerRow.createCell(++headerIndex);
		      cell0.setCellValue("Number Of PCP");
		      cell0.setCellStyle(headerCellStyle);
		    }
	    
	    if(fileVM.showNoOfClaims_specialistComparison) {
	      Cell cell0 = headerRow.createCell(++headerIndex);
	      cell0.setCellValue("Number Of Claims");
	      cell0.setCellStyle(headerCellStyle);
	    }
	      
	      if(fileVM.showNoOfBeneficiaries_specialistComparison) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Number Of Beneficiaries");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showCostPerClaim_specialistComparison) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Cost Per Claim");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showCostPerBeneficiary_specialistComparison) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Cost Per Beneficiary");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showTotalCost_specialistComparison) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Total Cost");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	     
	      
	   // Create Other rows and cells
	      int rowNum = 1;
	      int rowIndex = 0;
	      List<SpecialistComparisonPrintDataVM> list = getDataForSpecialistComparisonPrint(fileVM);
	      for (SpecialistComparisonPrintDataVM vm : list) {
	    	  rowIndex = 0;
	    	  Row row = sheet.createRow(rowNum);
	    	  
	    	  if(fileVM.showSpecialityCode_specialistComparison) {
		    	  row.createCell(rowIndex).setCellValue(vm.getSpecialityCode());
		      }
		      
	    	  if(fileVM.showNoOfPcp_specialistComparison) {
	          	  row.createCell(++rowIndex).setCellValue(vm.getNumberOfPcp());
	    	  }
	    	  
	    	  if(fileVM.showNoOfClaims_specialistComparison) {
	          	  row.createCell(++rowIndex).setCellValue(vm.getNumberOfClaims());
	    	  }
		    
		      if(fileVM.showNoOfBeneficiaries_specialistComparison) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getNumberOfBeneficiaries());
		      }
		      
		      if(fileVM.showCostPerClaim_specialistComparison) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getCostPerClaim());
		      }
		      
		      if(fileVM.showCostPerBeneficiary_specialistComparison) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getCostPerBeneficiary());
		      }
		      
		      if(fileVM.showTotalCost_specialistComparison) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getTotalCost());
		      }
		      
		      rowNum = rowNum + 1;
		      
	        }
	      
	   // Resize all columns to fit the content size
	      for(int i=0;i<headerIndex;i++) {
	    	  sheet.autoSizeColumn(i);
	      }
	      FileOutputStream fileOut = new FileOutputStream("Data export-Specialist Comparison Report Search.xlsx");
	      workbook.write(fileOut);
	      InputStream is = new FileInputStream(new File("Data export-Specialist Comparison Report Search.xlsx"));
	      IOUtils.copy(is, os);
	      fileOut.close();
	}
	
	public void generateSpecialistComparisonExpandReportXLSX(SpecialistComparisonExpandReportFileVM fileVM, OutputStream os)  throws IOException {

		Workbook workbook = new XSSFWorkbook();
	    Sheet sheet = workbook.createSheet("Specialist Comparison Expand Report");

	    org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
	    headerFont.setBold(true);
	    headerFont.setFontHeightInPoints((short) 12);

	    CellStyle headerCellStyle = workbook.createCellStyle();
	    headerCellStyle.setFont(headerFont);
	    
	 // Create a Row
	    Row headerRow = sheet.createRow(0);
	    int headerIndex = 0;
	    

	    if(fileVM.showPracticeName_specialistComparisonExpand) {
	    	Cell cell1 = headerRow.createCell(headerIndex);
	    	cell1.setCellValue("Practice Name");
	    	cell1.setCellStyle(headerCellStyle);
	    }
	      
	    if(fileVM.showSpecialityType_specialistComparisonExpand) {
	      Cell cell0 = headerRow.createCell(++headerIndex);
	      cell0.setCellValue("Speciality Type");
	      cell0.setCellStyle(headerCellStyle);
	    }
	      
	      if(fileVM.showNoOfClaims_specialistComparisonExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Number Of Claims");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showAverageCostPerClaim_specialistComparisonExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Average Cost Per Claim");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	      if(fileVM.showCost_specialistComparisonExpand) {
	    	  Cell cell1 = headerRow.createCell(++headerIndex);
	    	  cell1.setCellValue("Cost");
	    	  cell1.setCellStyle(headerCellStyle);
	      }
	      
	   // Create Other rows and cells
	      int rowNum = 1;
	      int rowIndex = 0;
	      List<SpecialistComparisonExpandPrintDataVM> list = getDataForSpecialistComparisonExpandPrint(fileVM);
	      for (SpecialistComparisonExpandPrintDataVM vm : list) {
	    	  rowIndex = 0;
	    	  Row row = sheet.createRow(rowNum);
	    	  
	    	  if(fileVM.showPracticeName_specialistComparisonExpand) {
		    	  row.createCell(rowIndex).setCellValue(vm.getPracticeName());
		      }
		      
	    	  if(fileVM.showSpecialityType_specialistComparisonExpand) {
	          	  row.createCell(++rowIndex).setCellValue(vm.getSpecialityType());
	    	  }
		    
		      if(fileVM.showNoOfClaims_specialistComparisonExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getNumberOfClaims());
		      }
		      
		      if(fileVM.showAverageCostPerClaim_specialistComparisonExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getAverageCostPerClaim());
		      }
		      
		      if(fileVM.showCost_specialistComparisonExpand) {
		    	  row.createCell(++rowIndex).setCellValue(vm.getCost());
		      }
		      
		      rowNum = rowNum + 1;
		      
	        }
	      
	   // Resize all columns to fit the content size
	      for(int i=0;i<headerIndex;i++) {
	    	  sheet.autoSizeColumn(i);
	      }
	      FileOutputStream fileOut = new FileOutputStream("Data export-Specialist Comparison Expand Report Search.xlsx");
	      workbook.write(fileOut);
	      InputStream is = new FileInputStream(new File("Data export-Specialist Comparison Expand Report Search.xlsx"));
	      IOUtils.copy(is, os);
	      fileOut.close();
	}

	@Override
	public List<ReinsuranceManagementReportPrintDataVM> getDataForReinsuranceManagementReportPrint(ReinsuranceManagementReportFileVM fileVM) {
		DecimalFormat formatter = new DecimalFormat("#,###");
		List<Object[]> resultData = instClaimDetailDao.getDataForFile(fileVM.getFileQuery());
		List<ReinsuranceManagementReportPrintDataVM> list = new ArrayList<>();
		
		for(Object[] obj: resultData) {
			ReinsuranceManagementReportPrintDataVM dataVM = new ReinsuranceManagementReportPrintDataVM();
			
			if(obj[0] != null)
				dataVM.setPlanName(obj[0].toString());
			if(obj[1] != null)
				dataVM.setPatientName(obj[1].toString());
			if(obj[2] != null)
				dataVM.setTermedMonth(obj[2].toString());
			if(obj[3] != null)
				dataVM.setPcpName(obj[3].toString());			
			if(obj[4] != null)
				dataVM.setHicn(obj[4].toString());
			if(obj[5] != null)
				dataVM.setInstClaims("$"+formatter.format(Double.parseDouble(obj[5].toString())));
			if(obj[6] != null)
				dataVM.setProfClaims("$"+formatter.format(Double.parseDouble(obj[6].toString())));
			if(obj[7] != null)
				dataVM.setTotalCost("$"+formatter.format(Double.parseDouble(obj[7].toString())));
			
			list.add(dataVM);
		}
		
		return list;
	}
	
	
}
