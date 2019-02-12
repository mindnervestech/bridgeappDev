package com.mnt.dao.jpa;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mnt.dao.DemographicDetailDao;
import com.mnt.domain.DemographicDetail;
import com.mnt.vm.FilteredVM;
import com.mnt.vm.MembershipManagementVM;
import com.mnt.vm.OptionsVM;
import com.mnt.vm.ReportResponseVM;
import com.mnt.vm.ReportVM;
import com.mnt.vm.SortedVM;

@Repository
public class DemographicDetailDaoJpa extends BaseDaoJpa<DemographicDetail> implements DemographicDetailDao{

	public DemographicDetailDaoJpa() {
		super(DemographicDetail.class, "DemographicDetail");
	}
	
	@Override
	@Transactional
	public void deleteOldRecords(String year, String month, String provider) {
		Query query = getEntityManager().createQuery("DELETE FROM DemographicDetail icd where icd.year=:year and icd.month=:month and icd.provider=:provider");
		query.setParameter("year", year);
        query.setParameter("month", month);
        query.setParameter("provider", provider);
        query.executeUpdate();
	}
	
	@SuppressWarnings("deprecation")
	@Override
	@Transactional
	public MembershipManagementVM getMembershipManagementData(ReportVM vm) {
		MembershipManagementVM dataVM = new MembershipManagementVM();
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		
		int currentPatients = 0,newPatients = 0,termedPatients = 0,currentYear = 0,prevYear = 0,currentMonth = 0, prevMonth = 0,total = 0;
		Double currentPercentage = 0.0,newPercentage = 0.0,termedPercentage = 0.0;
		String currentPatientsQryStr = "",newPatientsQryStr = "",termedPatientsQryStr = "",conditionStr = "";
		
		Query currentMonthQuery = getEntityManager().createNativeQuery("select distinct month,year from demographic_detail");
		List<Object[]> resultList = currentMonthQuery.getResultList();
		
		if(!resultList.isEmpty()) {
			
			if(resultList.get(0)[0].toString().contains("JAN"))
				currentMonth = 1;
			if(resultList.get(0)[0].toString().contains("FEB"))
				currentMonth = 2;
			if(resultList.get(0)[0].toString().contains("MAR"))
				currentMonth = 3;
			if(resultList.get(0)[0].toString().contains("APR"))
				currentMonth = 4;
			if(resultList.get(0)[0].toString().contains("MAY"))
				currentMonth = 5;
			if(resultList.get(0)[0].toString().contains("JUN"))
				currentMonth = 6;
			if(resultList.get(0)[0].toString().contains("JUL"))
				currentMonth = 7;
			if(resultList.get(0)[0].toString().contains("AUG"))
				currentMonth = 8;
			if(resultList.get(0)[0].toString().contains("SEP"))
				currentMonth = 9;
			if(resultList.get(0)[0].toString().contains("OCT"))
				currentMonth = 10;
			if(resultList.get(0)[0].toString().contains("NOV"))
				currentMonth = 11;
			if(resultList.get(0)[0].toString().contains("DEC"))
				currentMonth = 12;
		
			currentYear = Integer.parseInt(resultList.get(0)[1].toString());
			prevYear = Integer.parseInt(resultList.get(0)[1].toString());
			prevMonth = currentMonth - 1;
		}
		/*Date date = new Date();
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
		}*/
		
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
		
		
		
		if(!vm.getProvider().equals("all")) {
			conditionStr = conditionStr + " and provider="+'\''+vm.getProvider()+'\'';
		}
		if(!vm.getPcpName().equals("all")) {
			if(vm.getProvider().equals("all"))
				conditionStr = conditionStr + " and pcp_name="+'\''+vm.getPcpName()+'\'';
			else
				conditionStr = conditionStr + " and pcp_id="+'\''+vm.getPcpName()+'\'';
		}
		String locationCondition = "";
		
		ObjectMapper mapper = new ObjectMapper();
		List<OptionsVM> locationList = null;
		try {
			locationList = mapper.readValue(vm.getLocation(), new TypeReference<List<OptionsVM>>(){});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(!locationList.isEmpty()) {
			for(int i=0;i<locationList.size();i++) {
					if(!locationList.get(i).getValue().equals("all")) {
						if(i != locationList.size()-1)
							locationCondition = locationCondition + " pcp_location_code="+'\''+locationList.get(i).getValue()+'\''+" or ";
						else
							locationCondition = locationCondition + " pcp_location_code="+'\''+locationList.get(i).getValue()+'\'';
					}
				}
		}
		
		if(!locationCondition.equals(""))
			conditionStr = conditionStr + " and ("+locationCondition+")";
		
		
		currentPatientsQryStr = "select count(*) from demographic_detail where eligible_month="+'\''+currentMonthStr+'\''+conditionStr;
		newPatientsQryStr = "select count(*) from demographic_detail where eligible_month="+'\''+currentMonthStr+'\''+conditionStr+" and subscriber_id not in (select subscriber_id from demographic_detail where eligible_month="+'\''+prevMonthStr+'\''+conditionStr+")";
		termedPatientsQryStr = "select count(*) from demographic_detail where eligible_month="+'\''+prevMonthStr+'\''+conditionStr+" and subscriber_id not in (select subscriber_id from demographic_detail where eligible_month="+'\''+currentMonthStr+'\''+conditionStr+")";
		
		System.out.println(currentPatientsQryStr);
		System.out.println(newPatientsQryStr);
		System.out.println(termedPatientsQryStr);
		Query currentPatientsQuery = getEntityManager().createNativeQuery(currentPatientsQryStr);
		currentPatients = ((BigInteger)currentPatientsQuery.getSingleResult()).intValue();
		
		Query newPatientsQuery = getEntityManager().createNativeQuery(newPatientsQryStr);
		newPatients = ((BigInteger)newPatientsQuery.getSingleResult()).intValue();
		
		Query termedPatientsQuery = getEntityManager().createNativeQuery(termedPatientsQryStr);
		termedPatients = ((BigInteger)termedPatientsQuery.getSingleResult()).intValue();
		
		total = currentPatients+newPatients+termedPatients;
		currentPercentage = ((double)currentPatients/total)*100;
		newPercentage = ((double)newPatients/total)*100;
		termedPercentage = ((double)termedPatients/total)*100;
		
		dataVM.setCurrentPatients(currentPatients);
		dataVM.setNewPatients(newPatients);
		dataVM.setTermedPatients(termedPatients);
		dataVM.setNetImpact(newPatients - termedPatients);
		dataVM.setCurrentPatientsPercent(formatter.format(currentPercentage)+"%");
		dataVM.setNewPatientsPercent(formatter.format(newPercentage)+"%");
		dataVM.setTermedPatientsPercent(formatter.format(termedPercentage)+"%");
		return dataVM;
	}
	
	@Override
	@Transactional
	public ReportResponseVM getMembershipManagementPatientTypeData(ReportVM vm) {
		ReportResponseVM responseVM = new ReportResponseVM();
		
		ObjectMapper mapper = new ObjectMapper();
		List<SortedVM> sortedList = null;
		List<FilteredVM> filteredList = null;
		try {
			sortedList = mapper.readValue(vm.getSortedColumns(), new TypeReference<List<SortedVM>>(){});
			filteredList = mapper.readValue(vm.getFilteredColumns(), new TypeReference<List<FilteredVM>>(){});
		} catch (Exception e) {
			e.printStackTrace();
		}
		String filterStr = "",havingStr="";
		String filterColumnName = "";
		for(int i=0;i<filteredList.size();i++) {
			if(filteredList.get(i).getId().equals("planName")) {
				filterColumnName = "dd.plan_name";
			}
			if(filteredList.get(i).getId().equals("medicareId")) {
				filterColumnName = "dd.mbi";
			}
			if(filteredList.get(i).getId().equals("insuranceId")) {
				filterColumnName = "dd.subscriber_id";
			}
			if(filteredList.get(i).getId().equals("patientName")) {
				filterColumnName = "patientName";
			}
			if(filteredList.get(i).getId().equals("patientDob")) {
				filterColumnName = "dd.birth_date";
			}
			if(filteredList.get(i).getId().equals("assignedPcp")) {
				filterColumnName = "dd.pcp_name";
			}
			if(filteredList.get(i).getId().equals("pcpLocation")) {
				filterColumnName = "dd.pcp_location_code";
			}
			if(filteredList.get(i).getId().equals("ipaEffectiveDate")) {
				filterColumnName = "dd.eligible_month";
			}
			if(filteredList.get(i).getId().equals("mra")) {
				filterColumnName = "mra";
			}
			if(filteredList.get(i).getId().equals("totalPatientCost")) {
				filterColumnName = "total";
			}
			
			if(!filterColumnName.equals("") && !filterColumnName.equals("patientName") && !filterColumnName.equals("mra") && !filterColumnName.equals("total")) {
				filterStr += " and "+filterColumnName+" like "+'\''+"%"+filteredList.get(i).getValue()+"%"+'\''+" ";
			}
			
			if(filterColumnName.equals("patientName") || filterColumnName.equals("mra") || filterColumnName.equals("total")) {
				if(!havingStr.equals("")) {
					havingStr += "and ";
				} else {
					havingStr = " having ";
				}
				havingStr += filterColumnName+" like "+'\''+"%"+filteredList.get(i).getValue()+"%"+'\''+" ";
			}
		}
		
		String sortStr = "";
		String sortColName = "";
		if(!sortedList.isEmpty()) {
			if(sortedList.get(0).getId().equals("planName")) {
				sortColName = "plan_name";
			}
			if(sortedList.get(0).getId().equals("medicareId")) {
				sortColName = "mbi";
			}
			if(sortedList.get(0).getId().equals("insuranceId")) {
				sortColName = "subscriber_id";
			}
			if(sortedList.get(0).getId().equals("patientName")) {
				sortColName = "patientName";
			}
			if(sortedList.get(0).getId().equals("patientDob")) {
				sortColName = "birth_date";
			}
			if(sortedList.get(0).getId().equals("assignedPcp")) {
				sortColName = "pcp_name";
			}
			if(sortedList.get(0).getId().equals("pcpLocation")) {
				sortColName = "pcp_location_code";
			}
			if(sortedList.get(0).getId().equals("ipaEffectiveDate")) {
				sortColName = "eligible_month";
			}
			if(sortedList.get(0).getId().equals("mra")) {
				sortColName = "mra";
			}
			if(sortedList.get(0).getId().equals("totalPatientCost")) {
				sortColName = "total";
			}
			
			if(!sortColName.equals("")) {
				sortStr+= " "+sortColName+" ";
				if(sortedList.get(0).isDesc()) {
					sortStr += "desc";
				} else {
					sortStr += "asc";
				}
			}
		}
		
		List<Object[]> queryResult = new ArrayList<>();
		int start,end,noOfPages = 0,totalCount = 0;
		end = vm.getPageSize() * vm.getPage();
		start = end - vm.getPageSize();
		end = vm.getPageSize();
		
		String sortQryStr = " order by total desc limit "+start+","+end;
		String sortCountQryStr = " order by total desc ";
		if(!sortStr.equals("")) {
			sortQryStr = " order by "+sortStr+" limit "+start+","+end;
			sortCountQryStr = " order by "+sortStr;
		}
		
		int currentYear = 0,prevYear = 0,currentMonth = 0, prevMonth = 0;
		
		Query currentMonthQuery = getEntityManager().createNativeQuery("select distinct month,year from demographic_detail");
		List<Object[]> resultList = currentMonthQuery.getResultList();
		
		if(!resultList.isEmpty()) {
			
			if(resultList.get(0)[0].toString().contains("JAN"))
				currentMonth = 1;
			if(resultList.get(0)[0].toString().contains("FEB"))
				currentMonth = 2;
			if(resultList.get(0)[0].toString().contains("MAR"))
				currentMonth = 3;
			if(resultList.get(0)[0].toString().contains("APR"))
				currentMonth = 4;
			if(resultList.get(0)[0].toString().contains("MAY"))
				currentMonth = 5;
			if(resultList.get(0)[0].toString().contains("JUN"))
				currentMonth = 6;
			if(resultList.get(0)[0].toString().contains("JUL"))
				currentMonth = 7;
			if(resultList.get(0)[0].toString().contains("AUG"))
				currentMonth = 8;
			if(resultList.get(0)[0].toString().contains("SEP"))
				currentMonth = 9;
			if(resultList.get(0)[0].toString().contains("OCT"))
				currentMonth = 10;
			if(resultList.get(0)[0].toString().contains("NOV"))
				currentMonth = 11;
			if(resultList.get(0)[0].toString().contains("DEC"))
				currentMonth = 12;
		
			currentYear = Integer.parseInt(resultList.get(0)[1].toString());
			prevYear = Integer.parseInt(resultList.get(0)[1].toString());
			prevMonth = currentMonth - 1;
		}
		
		/*Date date = new Date();
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
		}*/
		
		
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
		
		String queryStr = "",countQueryStr = "",whereStr = "",conditionStr = "",subConditionStr = "";
		
		if(!vm.getProvider().equals("all")) {
			conditionStr = conditionStr + " and dd.provider="+'\''+vm.getProvider()+'\'';
			subConditionStr = subConditionStr + " and provider="+'\''+vm.getProvider()+'\'';
		}
		if(!vm.getPcpName().equals("all")) {
			if(vm.getProvider().equals("all")) {
				conditionStr = conditionStr + " and dd.pcp_name="+'\''+vm.getPcpName()+'\'';
				subConditionStr = subConditionStr + " and pcp_name="+'\''+vm.getPcpName()+'\'';
			}
			else {
				conditionStr = conditionStr + " and dd.pcp_id="+'\''+vm.getPcpName()+'\'';
				subConditionStr = subConditionStr + " and pcp_id="+'\''+vm.getPcpName()+'\'';
			}
		}
		String locationCondition = "",subLocationCondition = "";
		
		ObjectMapper locationMapper = new ObjectMapper();
		List<OptionsVM> locationList = null;
		try {
			locationList = locationMapper.readValue(vm.getLocation(), new TypeReference<List<OptionsVM>>(){});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(!locationList.isEmpty()) {
			for(int i=0;i<locationList.size();i++) {
					if(!locationList.get(i).getValue().equals("all")) {
						if(i != locationList.size()-1) {
							locationCondition = locationCondition + " dd.pcp_location_code="+'\''+locationList.get(i).getValue()+'\''+" or ";
							subLocationCondition = subLocationCondition + " pcp_location_code="+'\''+locationList.get(i).getValue()+'\''+" or ";
						}
						else {
							locationCondition = locationCondition + " dd.pcp_location_code="+'\''+locationList.get(i).getValue()+'\'';
							subLocationCondition = subLocationCondition + " pcp_location_code="+'\''+locationList.get(i).getValue()+'\'';
						}
					}
				}
		}
		
		if(!locationCondition.equals("")) {
			conditionStr = conditionStr + " and ("+locationCondition+")";
			subConditionStr = subConditionStr + " and ("+subLocationCondition+")";
		}
		
		if(vm.getPatientType().equals("Current")) {
			whereStr = " where dd.eligible_month="+'\''+currentMonthStr+'\''+conditionStr;
		}
		
		if(vm.getPatientType().equals("New")) {
			whereStr = " where dd.eligible_month="+'\''+currentMonthStr+'\''+conditionStr+" and dd.subscriber_id not in (select subscriber_id from demographic_detail where eligible_month="+'\''+prevMonthStr+'\''+subConditionStr+")";
		}
		
		if(vm.getPatientType().equals("Termed")) {
			whereStr = " where dd.eligible_month="+'\''+prevMonthStr+'\''+conditionStr+" and dd.subscriber_id not in (select subscriber_id from demographic_detail where eligible_month="+'\''+currentMonthStr+'\''+subConditionStr+")";
		}

		queryStr = "select D.*, round((spec_cost + pcp_cap + reinsurance_prem + inst_claims + prof_claims + rx_claims),0) as total from (\n" + 
				"select C.*, COALESCE(round(sum(rd.paid_amount - lics_paid - rep_gap_dscnt),2),0) as rx_claims from  (\n" + 
				"select B.*, COALESCE(round(sum(pcd.paid_amount),2),0) as prof_claims from (\n" + 
				"select A.*, COALESCE(round(sum(icd.paid_amount),2),0) as inst_claims from (\n" + 
				"select dd.plan_name,dd.mbi,dd.subscriber_id,concat(dd.last_name,' ',dd.first_name) as patientName,dd.birth_date,dd.pcp_name,dd.pcp_location_code,dd.eligible_month,\n" + 
				"max(round(dd.risk_score_partc,3)) as mra,dd.medicare_id,\n" + 
				"COALESCE(round(sum(behavioral_health + chiropractic_cap + dental_cap + hearing_cap + lab + vision_ophthamalogy + vision_optometry + otc_cap + gym_cap + podiatry_cap + transportation + dermatology),2),0) as spec_cost,\n" + 
				"COALESCE(round(sum(pcp_cap),2),0) as pcp_cap, COALESCE(round(count(*)*min(constant_val),2),0) as reinsurance_prem\n" + 
				"from demographic_detail dd \n" +whereStr+ 
				filterStr+" group by dd.plan_name,dd.mbi,dd.subscriber_id,dd.last_name,dd.first_name,dd.pcp_name,dd.pcp_location_code,dd.eligible_month,dd.birth_date,dd.medicare_id \n" + 
				") A\n" + 
				" left join inst_claim_detail icd on icd.medicare_id = A.medicare_id  \n" + 
				"group by A.plan_name, A.mbi, A.subscriber_id, A.patientName, A.birth_date, A.eligible_month, A.pcp_name, A.pcp_location_code, A.mra, A.medicare_id, A.spec_cost, A.pcp_cap, A.reinsurance_prem\n" + 
				") B \n" + 
				"left join prof_claim_detail pcd on pcd.medicare_id = B.medicare_id\n" + 
				"group by B.plan_name,B.mbi,B.subscriber_id,B.patientName,B.birth_date, B.eligible_month, B.pcp_name, B.pcp_location_code, B.mra, B.medicare_id, B.spec_cost, B.pcp_cap, B.reinsurance_prem\n" + 
				") C\n" + 
				"left join rx_detail rd on rd.medicare_id = C.medicare_id\n" + 
				"group by C.plan_name,C.mbi,C.subscriber_id,C.patientName,C.birth_date, C.eligible_month, C.pcp_name, C.pcp_location_code, C.mra, C.medicare_id, C.spec_cost, C.pcp_cap, C.reinsurance_prem\n" + 
				") D "+havingStr+ 
				"";
		
		countQueryStr = "select count(*) from \n" + 
				"(\n" + queryStr;
		
		System.out.println(queryStr+sortQryStr);
		
		Query query = getEntityManager().createNativeQuery(queryStr+sortQryStr);
		queryResult = query.getResultList();
		
		System.out.println(countQueryStr+sortCountQryStr);
		Query countQuery = getEntityManager().createNativeQuery(countQueryStr+sortCountQryStr+" ) A");
		totalCount = Integer.parseInt(countQuery.getSingleResult().toString());
		noOfPages = totalCount/vm.getPageSize();
		if(totalCount % vm.getPageSize() > 0)
			noOfPages++;
		
		String fileQuery = queryStr+sortCountQryStr;
		
		responseVM.setDataList(queryResult);
		responseVM.setNoOfPages(noOfPages);
		responseVM.setTotalCount(totalCount);
		responseVM.setFileQuery(fileQuery);
		responseVM.setPrevMonth(prevMonthStr);
		return responseVM;
	}
	
	public ReportResponseVM getBeneficiariesManagementReportData(ReportVM vm) {
		ReportResponseVM responseVM = new ReportResponseVM();
		
		ObjectMapper mapper = new ObjectMapper();
		List<SortedVM> sortedList = null;
		List<FilteredVM> filteredList = null;
		try {
			sortedList = mapper.readValue(vm.getSortedColumns(), new TypeReference<List<SortedVM>>(){});
			filteredList = mapper.readValue(vm.getFilteredColumns(), new TypeReference<List<FilteredVM>>(){});
		} catch (Exception e) {
			e.printStackTrace();
		}
		String filterStr = "", havingStr = "";
		String filterColumnName = "";
		for(int i=0;i<filteredList.size();i++) {
			if(filteredList.get(i).getId().equals("planName")) {
				filterColumnName = "planName";
			}
			if(filteredList.get(i).getId().equals("hicn")) {
				filterColumnName = "mbi";
			}
			if(filteredList.get(i).getId().equals("patientName")) {
				filterColumnName = "last_name";
			}
			if(filteredList.get(i).getId().equals("dob")) {
				filterColumnName = "birth_date";
			}
			if(filteredList.get(i).getId().equals("eligibleMonth")) {
				filterColumnName = "eligibleMonth";
			}
			if(filteredList.get(i).getId().equals("termedMonth")) {
				filterColumnName = "";
			}
			if(filteredList.get(i).getId().equals("pcpName")) {
				filterColumnName = "pcpName";
			}
			if(filteredList.get(i).getId().equals("pcpLocation")) {
				filterColumnName = "pcpLocation";
			}
			if(filteredList.get(i).getId().equals("mra")) {
				filterColumnName = "mra";
			}
			if(filteredList.get(i).getId().equals("totalCost")) {
				filterColumnName = "total";
			}
			if(filteredList.get(i).getId().equals("address")) {
				filterColumnName = "address";
			}
			
			if(filterColumnName.equals("mbi") || filterColumnName.equals("last_name") || filterColumnName.equals("birth_date")) {
				filterStr += " and "+filterColumnName+" like "+'\''+"%"+filteredList.get(i).getValue()+"%"+'\''+" ";
			}
		
			if(!filterColumnName.equals("") && !filterColumnName.equals("mbi") && !filterColumnName.equals("last_name") && !filterColumnName.equals("birth_date")) {
				if(!havingStr.equals("")) {
					havingStr += "and ";
				} else {
					havingStr = " having ";
				}
				havingStr += filterColumnName+" like "+'\''+"%"+filteredList.get(i).getValue()+"%"+'\''+" ";
			}
		}
		
		String sortStr = "";
		String sortColName = "";
		if(!sortedList.isEmpty()) {
			if(sortedList.get(0).getId().equals("planName")) {
				sortColName = "planName";
			}
			if(sortedList.get(0).getId().equals("hicn")) {
				sortColName = "mbi";
			}
			if(sortedList.get(0).getId().equals("patientName")) {
				sortColName = "last_name";
			}
			if(sortedList.get(0).getId().equals("dob")) {
				sortColName = "birth_date";
			}
			if(sortedList.get(0).getId().equals("eligibleMonth")) {
				sortColName = "eligibleMonth";
			}
			if(sortedList.get(0).getId().equals("termedMonth")) {
				sortColName = "";
			}
			if(sortedList.get(0).getId().equals("pcpName")) {
				sortColName = "pcpName";
			}
			if(sortedList.get(0).getId().equals("pcpLocation")) {
				sortColName = "pcpLocation";
			}
			if(sortedList.get(0).getId().equals("mra")) {
				sortColName = "mra";
			}
			if(sortedList.get(0).getId().equals("totalCost")) {
				sortColName = "total";
			}
			if(!sortColName.equals("")) {
				sortStr+= " "+sortColName+" ";
				if(sortedList.get(0).isDesc()) {
					sortStr += "desc";
				} else {
					sortStr += "asc";
				}
			}
		}
		
		List<Object[]> queryResult = new ArrayList<>();
		int start,end,noOfPages = 0,totalCount = 0;
		end = vm.getPageSize() * vm.getPage();
		start = end - vm.getPageSize();
		end = vm.getPageSize();
		
		String sortQryStr = " order by total desc limit "+start+","+end;
		String sortCountQryStr = " order by total ";
		if(!sortStr.equals("")) {
			sortQryStr = " order by "+sortStr+" limit "+start+","+end;
			sortCountQryStr = " order by "+sortStr;
		}
		
		String queryStr = "",countQueryStr = "",conditionStr = "";
		
		if(!vm.getProvider().equals("all")) {
			conditionStr = conditionStr + " and dd.provider="+'\''+vm.getProvider()+'\'';
		}
		if(!vm.getPcpName().equals("all")) {
			if(!vm.getProvider().equals("all"))
				conditionStr = conditionStr + " and dd.pcp_id="+'\''+vm.getPcpName()+'\'';
			else 
				conditionStr = conditionStr + " and dd.pcp_name="+'\''+vm.getPcpName()+'\'';
		}
		if(!vm.getYear().equals("all")) {
			conditionStr = conditionStr + " and dd.eligible_month like "+'\''+vm.getYear()+"%"+'\'';
		}
		
		if(!conditionStr.equals("")) {
			conditionStr = " where "+conditionStr.substring(4);
		}
		
			if(!filterStr.equals("") && conditionStr.equals(""))
				filterStr = " where "+filterStr.substring(4);
				
			queryStr = "select D.*, (spec_cost + pcp_cap + reinsurance_prem + inst_claims + prof_claims + rx_claims) as total from (\n" + 
					"select C.*, COALESCE(round(sum(rd.paid_amount - lics_paid - rep_gap_dscnt),2),0) as rx_claims from  (\n" + 
					"select B.*, COALESCE(round(sum(pcd.paid_amount),2),0) as prof_claims from (\n" + 
					"select A.*, COALESCE(round(sum(icd.paid_amount),2),0) as inst_claims from (\n" + 
					"select distinct max(dd.plan_name) planName,dd.mbi,dd.last_name,dd.first_name,dd.birth_date,min(dd.eligible_month) as eligibleMonth,min(dd.pcp_name) as pcpName,min(dd.pcp_location_code) as pcpLocation,\n" + 
					"COALESCE(max(round(dd.risk_score_partc,2)),0) as mra,max(concat(dd.member_addr1,' ',dd.city,' ',dd.state,' ',dd.zip)) as address,dd.medicare_id,\n" + 
					"COALESCE(round(sum(behavioral_health + chiropractic_cap + dental_cap + hearing_cap + lab + vision_ophthamalogy + vision_optometry + otc_cap + gym_cap + podiatry_cap + transportation + dermatology),2),0) as spec_cost,\n" + 
					"COALESCE(round(sum(pcp_cap),2),0) as pcp_cap, COALESCE(round(count(*)*min(constant_val),2),0) as reinsurance_prem\n" + 
					"from demographic_detail dd \n" +conditionStr+" "+filterStr+" group by dd.mbi,dd.last_name,dd.first_name,dd.birth_date,dd.medicare_id \n" + 
					") A\n" + 
					" left join inst_claim_detail icd on icd.medicare_id = A.medicare_id\n" + 
					"group by A.mbi,A.last_name,A.first_name,A.birth_date, A.eligibleMonth, A.pcpName, A.pcpLocation, A.mra, A.medicare_id order by A.last_name\n" + 
					") B \n" + 
					"left join prof_claim_detail pcd on pcd.medicare_id = B.medicare_id\n" + 
					"group by B.mbi,B.last_name,B.first_name,B.birth_date, B.eligibleMonth, B.pcpName, B.pcpLocation, B.mra, B.medicare_id order by B.last_name\n" + 
					") C\n" + 
					"left join rx_detail rd on rd.medicare_id = C.medicare_id\n" + 
					"group by C.mbi,C.last_name,C.first_name,C.birth_date, C.eligibleMonth, C.pcpName, C.pcpLocation, C.mra, C.medicare_id order by C.last_name\n" + 
					") D " +havingStr;
			
			countQueryStr = "select count(*) from \n" + 
					"(\n" + 
					queryStr;
		
		System.out.println(queryStr+sortQryStr);
		
		Query query = getEntityManager().createNativeQuery(queryStr+sortQryStr);
		queryResult = query.getResultList();
		
		System.out.println(countQueryStr+sortCountQryStr);
		Query countQuery = getEntityManager().createNativeQuery(countQueryStr+sortCountQryStr+" ) A");
		totalCount = Integer.parseInt(countQuery.getSingleResult().toString());
		noOfPages = totalCount/vm.getPageSize();
		if(totalCount % vm.getPageSize() > 0)
			noOfPages++;
		
		String fileQuery = queryStr+sortCountQryStr;
		
		responseVM.setDataList(queryResult);
		responseVM.setNoOfPages(noOfPages);
		responseVM.setTotalCount(totalCount);
		responseVM.setFileQuery(fileQuery);
		return responseVM;
	}
	
	
	public ReportResponseVM getBeneficiariesManagementByLocationReportData(ReportVM vm) {
		ReportResponseVM responseVM = new ReportResponseVM();
		
		ObjectMapper mapper = new ObjectMapper();
		List<SortedVM> sortedList = null;
		List<FilteredVM> filteredList = null;
		try {
			sortedList = mapper.readValue(vm.getSortedColumns(), new TypeReference<List<SortedVM>>(){});
			filteredList = mapper.readValue(vm.getFilteredColumns(), new TypeReference<List<FilteredVM>>(){});
		} catch (Exception e) {
			e.printStackTrace();
		}
		String filterStr = "", havingStr = "";
		String filterColumnName = "";
		for(int i=0;i<filteredList.size();i++) {
			
			if(filteredList.get(i).getId().equals("pcpLocation")) {
				filterColumnName = "pcp_location_code";
			}
			if(filteredList.get(i).getId().equals("mra")) {
				filterColumnName = "mra";
			}
			if(filteredList.get(i).getId().equals("totalCost")) {
				filterColumnName = "total";
			}
			
			if(filterColumnName.equals("pcp_location_code")) {
				filterStr += " and "+filterColumnName+" like "+'\''+"%"+filteredList.get(i).getValue()+"%"+'\''+" ";
			}
		
			if(!filterColumnName.equals("") && !filterColumnName.equals("pcp_location_code")) {
				if(!havingStr.equals("")) {
					havingStr += "and ";
				} else {
					havingStr = " having ";
				}
				havingStr += filterColumnName+" like "+'\''+"%"+filteredList.get(i).getValue()+"%"+'\''+" ";
			}
		}
		
		String sortStr = "";
		String sortColName = "";
		if(!sortedList.isEmpty()) {
			if(sortedList.get(0).getId().equals("pcpLocation")) {
				sortColName = "pcp_location_code";
			}
			if(sortedList.get(0).getId().equals("mra")) {
				sortColName = "mra";
			}
			if(sortedList.get(0).getId().equals("totalCost")) {
				sortColName = "total";
			}
			if(!sortColName.equals("")) {
				sortStr+= " "+sortColName+" ";
				if(sortedList.get(0).isDesc()) {
					sortStr += "desc";
				} else {
					sortStr += "asc";
				}
			}
		}
		
		List<Object[]> queryResult = new ArrayList<>();
		int start,end,noOfPages = 0,totalCount = 0;
		end = vm.getPageSize() * vm.getPage();
		start = end - vm.getPageSize();
		end = vm.getPageSize();
		
		String sortQryStr = " order by total desc limit "+start+","+end;
		String sortCountQryStr = " order by total desc ";
		if(!sortStr.equals("")) {
			sortQryStr = " order by "+sortStr+" limit "+start+","+end;
			sortCountQryStr = " order by "+sortStr;
		}
		
		String queryStr = "",countQueryStr = "",conditionStr = "";
		
		if(!vm.getProvider().equals("all")) {
			conditionStr = conditionStr + " and dd.provider="+'\''+vm.getProvider()+'\'';
		}
		if(!vm.getPcpName().equals("all")) {
			if(!vm.getProvider().equals("all"))
				conditionStr = conditionStr + " and dd.pcp_id="+'\''+vm.getPcpName()+'\'';
			else 
				conditionStr = conditionStr + " and dd.pcp_name="+'\''+vm.getPcpName()+'\'';
		}
		if(!vm.getYear().equals("all")) {
			conditionStr = conditionStr + " and dd.eligible_month like "+'\''+vm.getYear()+"%"+'\'';
		}
		
		if(!conditionStr.equals("")) {
			conditionStr = " where "+conditionStr.substring(4);
		}
		
			if(!filterStr.equals("") && conditionStr.equals(""))
				filterStr = " where "+filterStr.substring(4);
				
			
			queryStr="select D.*, (spec_cost + pcp_cap + reinsurance_prem + inst_claims + prof_claims + rx_claims) as total from (\r\n" + 
					"select C.*, COALESCE(round(sum(rd.paid_amount - lics_paid - rep_gap_dscnt),2),0) as rx_claims from  (\r\n" + 
					"select B.*, COALESCE(round(sum(pcd.paid_amount),2),0) as prof_claims from (\r\n" + 
					"select A.*, COALESCE(round(sum(icd.paid_amount),2),0) as inst_claims from (\r\n" + 
					"select dd.pcp_location_code,\r\n" + 
					"COALESCE(round(avg(dd.risk_score_partc),2),0) as mra,\r\n" + 
					"COALESCE(round(sum(behavioral_health + chiropractic_cap + dental_cap + hearing_cap + lab + vision_ophthamalogy + vision_optometry + otc_cap + gym_cap + podiatry_cap + transportation + dermatology),2),0) as spec_cost,\r\n" + 
					"COALESCE(round(sum(pcp_cap),2),0) as pcp_cap, COALESCE(round(count(*)*min(constant_val),2),0) as reinsurance_prem\r\n" + 
					"from demographic_detail dd \r\n" + 
					conditionStr+" "+filterStr+" group by dd.pcp_location_code\r\n" + 
					") A\r\n" + 
					" left join inst_claim_detail icd on icd.pcp_location_code = A.pcp_location_code\r\n" + 
					"group by A.pcp_location_code\r\n" + 
					") B \r\n" + 
					"left join prof_claim_detail pcd on pcd.pcp_location_code = B.pcp_location_code\r\n" + 
					"group by B.pcp_location_code\r\n" + 
					") C\r\n" + 
					"left join rx_detail rd on rd.pcp_location_code = C.pcp_location_code\r\n" + 
					"group by C.pcp_location_code\r\n" + 
					") D"+havingStr;
		/*
		 * queryStr =
		 * "select D.*, (spec_cost + pcp_cap + reinsurance_prem + inst_claims + prof_claims + rx_claims) as total from (\n"
		 * +
		 * "select C.*, COALESCE(round(sum(rd.paid_amount - lics_paid - rep_gap_dscnt),2),0) as rx_claims from  (\n"
		 * +
		 * "select B.*, COALESCE(round(sum(pcd.paid_amount),2),0) as prof_claims from (\n"
		 * +
		 * "select A.*, COALESCE(round(sum(icd.paid_amount),2),0) as inst_claims from (\n"
		 * +
		 * "select distinct max(dd.plan_name) planName,dd.mbi,dd.last_name,dd.first_name,dd.birth_date,min(dd.eligible_month) as eligibleMonth,min(dd.pcp_name) as pcpName,min(dd.pcp_location_code) as pcpLocation,\n"
		 * +
		 * "max(round(dd.risk_score_partc,2)) as mra,max(concat(dd.member_addr1,' ',dd.city,' ',dd.state,' ',dd.zip)) as address,dd.medicare_id,\n"
		 * +
		 * "COALESCE(round(sum(behavioral_health + chiropractic_cap + dental_cap + hearing_cap + lab + vision_ophthamalogy + vision_optometry + otc_cap + gym_cap + podiatry_cap + transportation + dermatology),2),0) as spec_cost,\n"
		 * +
		 * "COALESCE(round(sum(pcp_cap),2),0) as pcp_cap, COALESCE(round(count(*)*min(constant_val),2),0) as reinsurance_prem\n"
		 * + "from demographic_detail dd \n" +conditionStr+" "
		 * +filterStr+" group by dd.mbi,dd.last_name,dd.first_name,dd.birth_date,dd.medicare_id \n"
		 * + ") A\n" +
		 * " left join inst_claim_detail icd on icd.medicare_id = A.medicare_id\n" +
		 * "group by A.mbi,A.last_name,A.first_name,A.birth_date, A.eligibleMonth, A.pcpName, A.pcpLocation, A.mra, A.medicare_id order by A.last_name\n"
		 * + ") B \n" +
		 * "left join prof_claim_detail pcd on pcd.medicare_id = B.medicare_id\n" +
		 * "group by B.mbi,B.last_name,B.first_name,B.birth_date, B.eligibleMonth, B.pcpName, B.pcpLocation, B.mra, B.medicare_id order by B.last_name\n"
		 * + ") C\n" + "left join rx_detail rd on rd.medicare_id = C.medicare_id\n" +
		 * "group by C.mbi,C.last_name,C.first_name,C.birth_date, C.eligibleMonth, C.pcpName, C.pcpLocation, C.mra, C.medicare_id order by C.last_name\n"
		 * + ") D " +havingStr;
		 */
			
			countQueryStr = "select count(*) from \n" + 
					"(\n" + 
					queryStr;
		
		System.out.println(queryStr+sortQryStr);
		
		Query query = getEntityManager().createNativeQuery(queryStr+sortQryStr);
		queryResult = query.getResultList();
		
		System.out.println(countQueryStr+sortCountQryStr);
		Query countQuery = getEntityManager().createNativeQuery(countQueryStr+sortCountQryStr+" ) A");
		totalCount = Integer.parseInt(countQuery.getSingleResult().toString());
		noOfPages = totalCount/vm.getPageSize();
		if(totalCount % vm.getPageSize() > 0)
			noOfPages++;
		
		String fileQuery = queryStr+sortCountQryStr;
		
		responseVM.setDataList(queryResult);
		responseVM.setNoOfPages(noOfPages);
		responseVM.setTotalCount(totalCount);
		responseVM.setFileQuery(fileQuery);
		return responseVM;
	}
	
	
	public ReportResponseVM getBeneficiariesManagementByClinicReportData(ReportVM vm) {
		ReportResponseVM responseVM = new ReportResponseVM();
		
		ObjectMapper mapper = new ObjectMapper();
		List<SortedVM> sortedList = null;
		List<FilteredVM> filteredList = null;
		try {
			sortedList = mapper.readValue(vm.getSortedColumns(), new TypeReference<List<SortedVM>>(){});
			filteredList = mapper.readValue(vm.getFilteredColumns(), new TypeReference<List<FilteredVM>>(){});
		} catch (Exception e) {
			e.printStackTrace();
		}
		String filterStr = "", havingStr = "";
		String filterColumnName = "";
		for(int i=0;i<filteredList.size();i++) {
			
			if(filteredList.get(i).getId().equals("clinicName")) {
				filterColumnName = "clinic_facility_name";
			}
			if(filteredList.get(i).getId().equals("totalCost")) {
				filterColumnName = "totalCost";
			}
			
				filterStr += " and "+filterColumnName+" like "+'\''+"%"+filteredList.get(i).getValue()+"%"+'\''+" ";
			
		}
		
		String sortStr = "";
		String sortColName = "";
		if(!sortedList.isEmpty()) {
			if(sortedList.get(0).getId().equals("clinicName")) {
				sortColName = "clinic_facility_name";
			}
			if(sortedList.get(0).getId().equals("totalCost")) {
				sortColName = "totalCost";
			}
			
			if(!sortColName.equals("")) {
				sortStr+= " "+sortColName+" ";
				if(sortedList.get(0).isDesc()) {
					sortStr += "desc";
				} else {
					sortStr += "asc";
				}
			}
		}
		
		List<Object[]> queryResult = new ArrayList<>();
		int start,end,noOfPages = 0,totalCount = 0;
		end = vm.getPageSize() * vm.getPage();
		start = end - vm.getPageSize();
		end = vm.getPageSize();
		
		String sortQryStr = "  order by totalCost desc limit "+start+","+end;
		String sortCountQryStr = " order by totalCost desc ";
		if(!sortStr.equals("")) {
			sortQryStr = " order by "+sortStr+" limit "+start+","+end;
			sortCountQryStr = " order by "+sortStr;
		}
		
		String queryStr = "",countQueryStr = "",conditionStr = "";
		
		if(!vm.getProvider().equals("all")) {
			conditionStr = conditionStr + " and provider="+'\''+vm.getProvider()+'\'';
		}
		if(!vm.getPcpName().equals("all")) {
			if(!vm.getProvider().equals("all"))
				conditionStr = conditionStr + " and pcp_id="+'\''+vm.getPcpName()+'\'';
			else 
				conditionStr = conditionStr + " and pcp_name="+'\''+vm.getPcpName()+'\'';
		}
		if(!vm.getYear().equals("all")) {
			conditionStr = conditionStr + " and first_service_date like "+'\''+vm.getYear()+"%"+'\'';
		}
		
		if(!conditionStr.equals("")) {
			conditionStr = " where "+conditionStr.substring(4);
		}
		
			if(!filterStr.equals(""))
				filterStr = " where "+filterStr.substring(4);
				
			
			queryStr="select clinic_facility_name,clinic_facility_id,totalCost from (select clinic_facility_name,clinic_facility_id,round(sum(paid_amount),2) as totalCost from inst_claim_detail "+conditionStr+"\n" + 
					" group by clinic_facility_name,clinic_facility_id \n" + 
					"union \n" + 
					"select clinic_facility_name,clinic_facility_id,round(sum(paid_amount),2) as totalCost from prof_claim_detail "+conditionStr+"\n" + 
					" group by clinic_facility_name,clinic_facility_id ) A "+filterStr;
			
			countQueryStr = "select count(*) from \n" + 
					"(\n" + 
					queryStr;
		
		System.out.println(queryStr+sortQryStr);
		
		Query query = getEntityManager().createNativeQuery(queryStr+sortQryStr);
		queryResult = query.getResultList();
		
		System.out.println(countQueryStr+sortCountQryStr);
		Query countQuery = getEntityManager().createNativeQuery(countQueryStr+sortCountQryStr+" ) A");
		totalCount = Integer.parseInt(countQuery.getSingleResult().toString());
		noOfPages = totalCount/vm.getPageSize();
		if(totalCount % vm.getPageSize() > 0)
			noOfPages++;
		
		String fileQuery = queryStr+sortCountQryStr;
		
		responseVM.setDataList(queryResult);
		responseVM.setNoOfPages(noOfPages);
		responseVM.setTotalCount(totalCount);
		responseVM.setFileQuery(fileQuery);
		return responseVM;
	}
	
	
	
	public ReportResponseVM getBeneficiariesManagementByDoctorReportData(ReportVM vm) {
		ReportResponseVM responseVM = new ReportResponseVM();
		
		ObjectMapper mapper = new ObjectMapper();
		List<SortedVM> sortedList = null;
		List<FilteredVM> filteredList = null;
		try {
			sortedList = mapper.readValue(vm.getSortedColumns(), new TypeReference<List<SortedVM>>(){});
			filteredList = mapper.readValue(vm.getFilteredColumns(), new TypeReference<List<FilteredVM>>(){});
		} catch (Exception e) {
			e.printStackTrace();
		}
		String filterStr = "", havingStr = "";
		String filterColumnName = "";
		for(int i=0;i<filteredList.size();i++) {
			if(filteredList.get(i).getId().equals("pcpName")) {
				filterColumnName = "pcp_name";
			}
			if(filteredList.get(i).getId().equals("pcpLocation")) {
				filterColumnName = "pcpLocation";
			}
			if(filteredList.get(i).getId().equals("averageMra")) {
				filterColumnName = "mra";
			}
			if(filteredList.get(i).getId().equals("totalCost")) {
				filterColumnName = "total";
			}
			
			/*if(filterColumnName.equals("mbi") || filterColumnName.equals("last_name") || filterColumnName.equals("birth_date")) {
				filterStr += " and "+filterColumnName+" like "+'\''+"%"+filteredList.get(i).getValue()+"%"+'\''+" ";
			}*/
		
			if(!filterColumnName.equals("")) {
				if(!havingStr.equals("")) {
					havingStr += "and ";
				} else {
					havingStr = " having ";
				}
				havingStr += filterColumnName+" like "+'\''+"%"+filteredList.get(i).getValue()+"%"+'\''+" ";
			}
		}
		
		String sortStr = "";
		String sortColName = "";
		if(!sortedList.isEmpty()) {
			if(sortedList.get(0).getId().equals("pcpName")) {
				sortColName = "pcp_name";
			}
			if(sortedList.get(0).getId().equals("pcpLocation")) {
				sortColName = "pcpLocation";
			}
			if(sortedList.get(0).getId().equals("averageMra")) {
				sortColName = "mra";
			}
			if(sortedList.get(0).getId().equals("totalCost")) {
				sortColName = "total";
			}
			
			if(!sortColName.equals("")) {
				sortStr+= " "+sortColName+" ";
				if(sortedList.get(0).isDesc()) {
					sortStr += "desc";
				} else {
					sortStr += "asc";
				}
			}
		}
		
		List<Object[]> queryResult = new ArrayList<>();
		int start,end,noOfPages = 0,totalCount = 0;
		end = vm.getPageSize() * vm.getPage();
		start = end - vm.getPageSize();
		end = vm.getPageSize();
		
		String sortQryStr = " order by total desc limit "+start+","+end;
		String sortCountQryStr = " order by total desc ";
		if(!sortStr.equals("")) {
			sortQryStr = " order by "+sortStr+" limit "+start+","+end;
			sortCountQryStr = " order by "+sortStr;
		}
		
		String queryStr = "",countQueryStr = "",conditionStr = "";
		
		if(!vm.getProvider().equals("all")) {
			conditionStr = conditionStr + " and dd.provider="+'\''+vm.getProvider()+'\'';
		}
		if(!vm.getPcpName().equals("all")) {
			if(!vm.getProvider().equals("all"))
				conditionStr = conditionStr + " and dd.pcp_id="+'\''+vm.getPcpName()+'\'';
			else 
				conditionStr = conditionStr + " and dd.pcp_name="+'\''+vm.getPcpName()+'\'';
		}
		if(!vm.getYear().equals("all")) {
			conditionStr = conditionStr + " and dd.eligible_month like "+'\''+vm.getYear()+"%"+'\'';
		}
		
		if(!conditionStr.equals("")) {
			conditionStr = " where "+conditionStr.substring(4);
		}
		
			if(!vm.getProvider().equals("all")) {	
				
			queryStr = "select D.*, (spec_cost + pcp_cap + reinsurance_prem + inst_claims + prof_claims + rx_claims) as total from (\n" + 
					"select C.*, COALESCE(round(sum(rd.paid_amount - lics_paid - rep_gap_dscnt),2),0) as rx_claims from  (\n" + 
					"select B.*, COALESCE(round(sum(pcd.paid_amount),2),0) as prof_claims from (\n" + 
					"select A.*, COALESCE(round(sum(icd.paid_amount),2),0) as inst_claims from (\n" + 
					"select dd.pcp_name,min(dd.pcp_location_code) as pcpLocation,\n" + 
					"COALESCE(round(avg(dd.risk_score_partc),2),0) as mra,dd.pcp_id,\n" + 
					"COALESCE(round(sum(behavioral_health + chiropractic_cap + dental_cap + hearing_cap + lab + vision_ophthamalogy + vision_optometry + otc_cap + gym_cap + podiatry_cap + transportation + dermatology),2),0) as spec_cost,\n" + 
					"COALESCE(round(sum(pcp_cap),2),0) as pcp_cap, COALESCE(round(count(*)*min(constant_val),2),0) as reinsurance_prem\n" + 
					"from demographic_detail dd \n" +conditionStr+"  group by dd.pcp_name,dd.pcp_id \n" + 
					") A\n" + 
					" left join inst_claim_detail icd on icd.pcp_id = A.pcp_id\n" + 
					"group by A.pcp_name,A.pcp_id\n" + 
					") B \n" + 
					"left join prof_claim_detail pcd on pcd.pcp_id = B.pcp_id\n" + 
					"group by B.pcp_name,B.pcp_id\n" + 
					") C\n" + 
					"left join rx_detail rd on rd.pcp_id = C.pcp_id\n" + 
					"group by C.pcp_name,C.pcp_id\n" + 
					") D "+havingStr;
			}
			
			if(vm.getProvider().equals("all")) {	
				
				queryStr = "select pcp_name,min(pcpLocation) as pcpLocation,COALESCE(round(avg(mra),2),0) as mra,pcp_name as pcpId,sum(spec_cost) as spec_cost,sum(pcp_cap) as pcp_cap,sum(reinsurance_prem) as reinsurance_prem,sum(inst_claims) as inst_claims,sum(prof_claims) as prof_claims,sum(rx_claims) as rx_claims,sum(total) as total from("+
						"select D.*, (spec_cost + pcp_cap + reinsurance_prem + inst_claims + prof_claims + rx_claims) as total from (\n" + 
						"select C.*, COALESCE(round(sum(rd.paid_amount - lics_paid - rep_gap_dscnt),2),0) as rx_claims from  (\n" + 
						"select B.*, COALESCE(round(sum(pcd.paid_amount),2),0) as prof_claims from (\n" + 
						"select A.*, COALESCE(round(sum(icd.paid_amount),2),0) as inst_claims from (\n" + 
						"select dd.pcp_name,min(dd.pcp_location_code) as pcpLocation,\n" + 
						"round(avg(dd.risk_score_partc),2) as mra,dd.pcp_id,\n" + 
						"COALESCE(round(sum(behavioral_health + chiropractic_cap + dental_cap + hearing_cap + lab + vision_ophthamalogy + vision_optometry + otc_cap + gym_cap + podiatry_cap + transportation + dermatology),2),0) as spec_cost,\n" + 
						"COALESCE(round(sum(pcp_cap),2),0) as pcp_cap, COALESCE(round(count(*)*min(constant_val),2),0) as reinsurance_prem\n" + 
						"from demographic_detail dd \n" +conditionStr+"  group by dd.pcp_name,dd.pcp_id \n" + 
						") A\n" + 
						" left join inst_claim_detail icd on icd.pcp_id = A.pcp_id\n" + 
						"group by A.pcp_name,A.pcp_id\n" + 
						") B \n" + 
						"left join prof_claim_detail pcd on pcd.pcp_id = B.pcp_id\n" + 
						"group by B.pcp_name,B.pcp_id\n" + 
						") C\n" + 
						"left join rx_detail rd on rd.pcp_id = C.pcp_id\n" + 
						"group by C.pcp_name,C.pcp_id\n" + 
						") D ) E group by pcp_name "+havingStr;
			}
			
			countQueryStr = "select count(*) from \n" + 
					"(\n" + 
					queryStr;
		
		System.out.println(queryStr+sortQryStr);
		
		Query query = getEntityManager().createNativeQuery(queryStr+sortQryStr);
		queryResult = query.getResultList();
		
		System.out.println(countQueryStr+sortCountQryStr);
		Query countQuery = getEntityManager().createNativeQuery(countQueryStr+sortCountQryStr+" ) A");
		totalCount = Integer.parseInt(countQuery.getSingleResult().toString());
		noOfPages = totalCount/vm.getPageSize();
		if(totalCount % vm.getPageSize() > 0)
			noOfPages++;
		
		String fileQuery = queryStr+sortCountQryStr;
		
		responseVM.setDataList(queryResult);
		responseVM.setNoOfPages(noOfPages);
		responseVM.setTotalCount(totalCount);
		responseVM.setFileQuery(fileQuery);
		return responseVM;
	}
	
	public ReportResponseVM getBeneficiariesManagementExpandReportData(ReportVM vm) {
		ReportResponseVM responseVM = new ReportResponseVM();
		
		ObjectMapper mapper = new ObjectMapper();
		List<SortedVM> sortedList = null;
		List<FilteredVM> filteredList = null;
		try {
			sortedList = mapper.readValue(vm.getSortedColumns(), new TypeReference<List<SortedVM>>(){});
			filteredList = mapper.readValue(vm.getFilteredColumns(), new TypeReference<List<FilteredVM>>(){});
		} catch (Exception e) {
			e.printStackTrace();
		}
		String filterStr = "", havingStr = "";
		String filterColumnName = "";
		for(int i=0;i<filteredList.size();i++) {
			if(filteredList.get(i).getId().equals("claimId")) {
				filterColumnName = "claim_id";
			}
			if(filteredList.get(i).getId().equals("claimDate")) {
				filterColumnName = "claim_date";
			}
			if(filteredList.get(i).getId().equals("claimType")) {
				filterColumnName = "claim_type";
			}
			if(filteredList.get(i).getId().equals("clinicName")) {
				filterColumnName = "clinic_facility_name";
			}
			if(filteredList.get(i).getId().equals("pcpName")) {
				filterColumnName = "pcp_name";
			}
			if(filteredList.get(i).getId().equals("icdCodes")) {
				filterColumnName = "principal_diagnosis";
			}
			if(filteredList.get(i).getId().equals("hccCodes")) {
				filterColumnName = "";
			}
			if(filteredList.get(i).getId().equals("drgCode")) {
				filterColumnName = "drg_code";
			}
			if(filteredList.get(i).getId().equals("betosCat")) {
				filterColumnName = "betos_cat";
			}
			if(filteredList.get(i).getId().equals("cost")) {
				filterColumnName = "cost";
			}
			
			if(!filterColumnName.equals("")) {
				filterStr += " and "+filterColumnName+" like "+'\''+"%"+filteredList.get(i).getValue()+"%"+'\''+" ";
			}
			
		}
		
		String sortStr = "";
		String sortColName = "";
		if(!sortedList.isEmpty()) {
			if(sortedList.get(0).getId().equals("claimId")) {
				sortColName = "claim_id";
			}
			if(sortedList.get(0).getId().equals("claimDate")) {
				sortColName = "claim_date";
			}
			if(sortedList.get(0).getId().equals("claimType")) {
				sortColName = "claim_type";
			}
			if(sortedList.get(0).getId().equals("clinicName")) {
				sortColName = "clinic_facility_name";
			}
			if(sortedList.get(0).getId().equals("pcpName")) {
				sortColName = "pcp_name";
			}
			if(sortedList.get(0).getId().equals("icdCodes")) {
				sortColName = "principal_diagnosis";
			}
			if(sortedList.get(0).getId().equals("hccCodes")) {
				sortColName = "";
			}
			if(sortedList.get(0).getId().equals("drgCode")) {
				sortColName = "drg_code";
			}
			if(sortedList.get(0).getId().equals("betosCat")) {
				sortColName = "betos_cat";
			}
			if(sortedList.get(0).getId().equals("cost")) {
				sortColName = "cost";
			}
			if(!sortColName.equals("")) {
				sortStr+= " "+sortColName+" ";
				if(sortedList.get(0).isDesc()) {
					sortStr += "desc";
				} else {
					sortStr += "asc";
				}
			}
		}
		
		List<Object[]> queryResult = new ArrayList<>();
		int start,end,noOfPages = 0,totalCount = 0;
		end = vm.getPageSize() * vm.getPage();
		start = end - vm.getPageSize();
		end = vm.getPageSize();
		
		String sortQryStr = " order by cost desc limit "+start+","+end;
		String sortCountQryStr = " order by cost ";
		if(!sortStr.equals("")) {
			sortQryStr = " order by "+sortStr+" limit "+start+","+end;
			sortCountQryStr = " order by "+sortStr;
		}
		
		String queryStr = "",countQueryStr = "";
		
		if(!filterStr.equals(""))
			filterStr = " where "+filterStr.substring(4);	
		
		if(vm.getProvider().equals("all") && vm.getYear().equals("all")) {
				
			queryStr = "select * from (select claim_id,first_service_date as claim_date,'INST CLAIMS' as claim_type,clinic_facility_name,pcp_name,drg_code,betos_cat,paid_amount as cost,principal_diagnosis from inst_claim_detail where medicare_id="+'\''+vm.getMedicareId()+'\''+"\n" + 
					"union\n" + 
					"select claim_id,first_service_date as claim_date,'PROF CLAIMS' as claim_type,clinic_facility_name,pcp_name,'' as drg_code,betos_cat,paid_amount as cost,principal_diagnosis from prof_claim_detail where medicare_id="+'\''+vm.getMedicareId()+'\''+"\n" + 
					"union\n" + 
					"select claim_id,date_filled as claim_date,'RX CLAIMS' as claim_type,'' as clinic_facility_name,pcp_name,'' as drg_code,'' as betos_cat,paid_amount as cost,'' as principal_diagnosis from rx_detail where medicare_id="+'\''+vm.getMedicareId()+'\''+"\n" + 
					"union\n" + 
					"select 'N/A' as claim_id,eligible_month as claim_date,'PCP CAP' as claim_type,'' as clinic_facility_name,pcp_name,'' as drg_code,'' as betos_cat,pcp_cap as cost,'' as principal_diagnosis from demographic_detail where medicare_id="+'\''+vm.getMedicareId()+'\''+"\n" + 
					"union\n" + 
					"select 'N/A' as claim_id,eligible_month as claim_date,'REINSURANCE PREM' as claim_type,'' as clinic_facility_name,pcp_name,'' as drg_code,'' as betos_cat,constant_val as cost,'' as principal_diagnosis from demographic_detail where medicare_id="+'\''+vm.getMedicareId()+'\''+"\n" + 
					"union\n" + 
					"select 'N/A' as claim_id,eligible_month as claim_date,'SPEC CLAIMS' as claim_type,'' as clinic_facility_name,pcp_name,'' as drg_code,'' as betos_cat,\n" + 
					"behavioral_health+chiropractic_cap+dental_cap+hearing_cap+lab+vision_ophthamalogy+vision_optometry+otc_cap+gym_cap+podiatry_cap+transportation\n" + 
					"+dermatology as cost,'' as principal_diagnosis from demographic_detail where medicare_id="+'\''+vm.getMedicareId()+'\''+") A "+filterStr;
			
			countQueryStr = "select count(*) from \n" + 
					"(\n" + 
					queryStr;
		} else {
			if(vm.getProvider().equals("all")) {
				queryStr = "select * from (select claim_id,first_service_date as claim_date,'INST CLAIMS' as claim_type,clinic_facility_name,pcp_name,drg_code,betos_cat,paid_amount as cost,principal_diagnosis from inst_claim_detail where first_service_date like "+'\''+vm.getYear()+"%"+'\''+" and medicare_id="+'\''+vm.getMedicareId()+'\''+"\n" + 
						"union\n" + 
						"select claim_id,first_service_date as claim_date,'PROF CLAIMS' as claim_type,clinic_facility_name,pcp_name,'' as drg_code,betos_cat,paid_amount as cost,principal_diagnosis from prof_claim_detail where first_service_date like "+'\''+vm.getYear()+"%"+'\''+" and medicare_id="+'\''+vm.getMedicareId()+'\''+"\n" + 
						"union\n" + 
						"select claim_id,date_filled as claim_date,'RX CLAIMS' as claim_type,'' as clinic_facility_name,pcp_name,'' as drg_code,'' as betos_cat,paid_amount as cost,'' as principal_diagnosis from rx_detail where date_filled like "+'\''+vm.getYear()+"%"+'\''+" and medicare_id="+'\''+vm.getMedicareId()+'\''+"\n" + 
						"union\n" + 
						"select 'N/A' as claim_id,eligible_month as claim_date,'PCP CAP' as claim_type,'' as clinic_facility_name,pcp_name,'' as drg_code,'' as betos_cat,pcp_cap as cost,'' as principal_diagnosis from demographic_detail where eligible_month like "+'\''+vm.getYear()+"%"+'\''+" and medicare_id="+'\''+vm.getMedicareId()+'\''+"\n" + 
						"union\n" + 
						"select 'N/A' as claim_id,eligible_month as claim_date,'REINSURANCE PREM' as claim_type,'' as clinic_facility_name,pcp_name,'' as drg_code,'' as betos_cat,constant_val as cost,'' as principal_diagnosis from demographic_detail where eligible_month like "+'\''+vm.getYear()+"%"+'\''+" and medicare_id="+'\''+vm.getMedicareId()+'\''+"\n" + 
						"union\n" + 
						"select 'N/A' as claim_id,eligible_month as claim_date,'SPEC CLAIMS' as claim_type,'' as clinic_facility_name,pcp_name,'' as drg_code,'' as betos_cat,\n" + 
						"behavioral_health+chiropractic_cap+dental_cap+hearing_cap+lab+vision_ophthamalogy+vision_optometry+otc_cap+gym_cap+podiatry_cap+transportation\n" + 
						"+dermatology as cost,'' as principal_diagnosis from demographic_detail where eligible_month like "+'\''+vm.getYear()+"%"+'\''+" and medicare_id="+'\''+vm.getMedicareId()+'\''+") A "+filterStr;
			}
			if(vm.getYear().equals("all")) {
				
				queryStr = "select * from (select claim_id,first_service_date as claim_date,'INST CLAIMS' as claim_type,clinic_facility_name,pcp_name,drg_code,betos_cat,paid_amount as cost,principal_diagnosis from inst_claim_detail where provider="+'\''+vm.getProvider()+'\''+" and medicare_id="+'\''+vm.getMedicareId()+'\''+"\n" + 
						"union\n" + 
						"select claim_id,first_service_date as claim_date,'PROF CLAIMS' as claim_type,clinic_facility_name,pcp_name,'' as drg_code,betos_cat,paid_amount as cost,principal_diagnosis from prof_claim_detail where provider="+'\''+vm.getProvider()+'\''+" and medicare_id="+'\''+vm.getMedicareId()+'\''+"\n" + 
						"union\n" + 
						"select claim_id,date_filled as claim_date,'RX CLAIMS' as claim_type,'' as clinic_facility_name,pcp_name,'' as drg_code,'' as betos_cat,paid_amount as cost,'' as principal_diagnosis from rx_detail where provider="+'\''+vm.getProvider()+'\''+" and medicare_id="+'\''+vm.getMedicareId()+'\''+"\n" + 
						"union\n" + 
						"select 'N/A' as claim_id,eligible_month as claim_date,'PCP CAP' as claim_type,'' as clinic_facility_name,pcp_name,'' as drg_code,'' as betos_cat,pcp_cap as cost,'' as principal_diagnosis from demographic_detail where provider="+'\''+vm.getProvider()+'\''+" and medicare_id="+'\''+vm.getMedicareId()+'\''+"\n" + 
						"union\n" + 
						"select 'N/A' as claim_id,eligible_month as claim_date,'REINSURANCE PREM' as claim_type,'' as clinic_facility_name,pcp_name,'' as drg_code,'' as betos_cat,constant_val as cost,'' as principal_diagnosis from demographic_detail where provider="+'\''+vm.getProvider()+'\''+" and medicare_id="+'\''+vm.getMedicareId()+'\''+"\n" + 
						"union\n" + 
						"select 'N/A' as claim_id,eligible_month as claim_date,'SPEC CLAIMS' as claim_type,'' as clinic_facility_name,pcp_name,'' as drg_code,'' as betos_cat,\n" + 
						"behavioral_health+chiropractic_cap+dental_cap+hearing_cap+lab+vision_ophthamalogy+vision_optometry+otc_cap+gym_cap+podiatry_cap+transportation\n" + 
						"+dermatology as cost,'' as principal_diagnosis from demographic_detail where provider="+'\''+vm.getProvider()+'\''+" and medicare_id="+'\''+vm.getMedicareId()+'\''+") A "+filterStr;
			}
			if(!vm.getProvider().equals("all") && !vm.getYear().equals("all")) {
				
				queryStr = "select * from (select claim_id,first_service_date as claim_date,'INST CLAIMS' as claim_type,clinic_facility_name,pcp_name,drg_code,betos_cat,paid_amount as cost,principal_diagnosis from inst_claim_detail where provider="+'\''+vm.getProvider()+'\''+" and first_service_date like "+'\''+vm.getYear()+"%"+'\''+" and medicare_id="+'\''+vm.getMedicareId()+'\''+"\n" + 
						"union\n" + 
						"select claim_id,first_service_date as claim_date,'PROF CLAIMS' as claim_type,clinic_facility_name,pcp_name,'' as drg_code,betos_cat,paid_amount as cost,principal_diagnosis from prof_claim_detail where provider="+'\''+vm.getProvider()+'\''+" and first_service_date like "+'\''+vm.getYear()+"%"+'\''+" and medicare_id="+'\''+vm.getMedicareId()+'\''+"\n" + 
						"union\n" + 
						"select claim_id,date_filled as claim_date,'RX CLAIMS' as claim_type,'' as clinic_facility_name,pcp_name,'' as drg_code,'' as betos_cat,paid_amount as cost,'' as principal_diagnosis from rx_detail where provider="+'\''+vm.getProvider()+'\''+" and date_filled like "+'\''+vm.getYear()+"%"+'\''+" and medicare_id="+'\''+vm.getMedicareId()+'\''+"\n" + 
						"union\n" + 
						"select 'N/A' as claim_id,eligible_month as claim_date,'PCP CAP' as claim_type,'' as clinic_facility_name,pcp_name,'' as drg_code,'' as betos_cat,pcp_cap as cost,'' as principal_diagnosis from demographic_detail where provider="+'\''+vm.getProvider()+'\''+" and eligible_month like "+'\''+vm.getYear()+"%"+'\''+" and medicare_id="+'\''+vm.getMedicareId()+'\''+"\n" + 
						"union\n" + 
						"select 'N/A' as claim_id,eligible_month as claim_date,'REINSURANCE PREM' as claim_type,'' as clinic_facility_name,pcp_name,'' as drg_code,'' as betos_cat,constant_val as cost,'' as principal_diagnosis from demographic_detail where provider="+'\''+vm.getProvider()+'\''+" and eligible_month like "+'\''+vm.getYear()+"%"+'\''+" and medicare_id="+'\''+vm.getMedicareId()+'\''+"\n" + 
						"union\n" + 
						"select 'N/A' as claim_id,eligible_month as claim_date,'SPEC CLAIMS' as claim_type,'' as clinic_facility_name,pcp_name,'' as drg_code,'' as betos_cat,\n" + 
						"behavioral_health+chiropractic_cap+dental_cap+hearing_cap+lab+vision_ophthamalogy+vision_optometry+otc_cap+gym_cap+podiatry_cap+transportation\n" + 
						"+dermatology as cost,'' as principal_diagnosis from demographic_detail where provider="+'\''+vm.getProvider()+'\''+" and eligible_month like "+'\''+vm.getYear()+"%"+'\''+" and medicare_id="+'\''+vm.getMedicareId()+'\''+") A "+filterStr;
				
			}
			countQueryStr = "select count(*) from \n" + 
					"(\n" + 
					queryStr;
		}
		
		System.out.println(queryStr+sortQryStr);
		
		Query query = getEntityManager().createNativeQuery(queryStr+sortQryStr);
		queryResult = query.getResultList();
		
		System.out.println(queryStr+sortQryStr);
		System.out.println(countQueryStr+sortCountQryStr);
		Query countQuery = getEntityManager().createNativeQuery(countQueryStr+sortCountQryStr+" ) A");
		totalCount = Integer.parseInt(countQuery.getSingleResult().toString());
		noOfPages = totalCount/vm.getPageSize();
		if(totalCount % vm.getPageSize() > 0)
			noOfPages++;
		
		String fileQuery = queryStr+sortCountQryStr;
		
		responseVM.setDataList(queryResult);
		responseVM.setNoOfPages(noOfPages);
		responseVM.setTotalCount(totalCount);
		responseVM.setFileQuery(fileQuery);
		return responseVM;
	}
	
	public ReportResponseVM getBeneficiariesManagementByLocationExpandReportData(ReportVM vm) {
		ReportResponseVM responseVM = new ReportResponseVM();
		
		ObjectMapper mapper = new ObjectMapper();
		List<SortedVM> sortedList = null;
		List<FilteredVM> filteredList = null;
		try {
			sortedList = mapper.readValue(vm.getSortedColumns(), new TypeReference<List<SortedVM>>(){});
			filteredList = mapper.readValue(vm.getFilteredColumns(), new TypeReference<List<FilteredVM>>(){});
		} catch (Exception e) {
			e.printStackTrace();
		}
		String filterStr = "", havingStr = "";
		String filterColumnName = "";
		for(int i=0;i<filteredList.size();i++) {
			if(filteredList.get(i).getId().equals("claimId")) {
				filterColumnName = "claim_id";
			}
			if(filteredList.get(i).getId().equals("claimDate")) {
				filterColumnName = "claim_date";
			}
			if(filteredList.get(i).getId().equals("claimType")) {
				filterColumnName = "claim_type";
			}
			if(filteredList.get(i).getId().equals("clinicName")) {
				filterColumnName = "clinic_facility_name";
			}
		
			if(filteredList.get(i).getId().equals("pcpLocation"))
			{
				filterColumnName="pcp_location_code";
			}
			if(filteredList.get(i).getId().equals("icdCodes")) {
				filterColumnName = "principal_diagnosis";
			}
			if(filteredList.get(i).getId().equals("hccCodes")) {
				filterColumnName = "";
			}
			if(filteredList.get(i).getId().equals("drgCode")) {
				filterColumnName = "drg_code";
			}
			if(filteredList.get(i).getId().equals("betosCat")) {
				filterColumnName = "betos_cat";
			}
			if(filteredList.get(i).getId().equals("cost")) {
				filterColumnName = "cost";
			}
			
			if(!filterColumnName.equals("")) {
				filterStr += " and "+filterColumnName+" like "+'\''+"%"+filteredList.get(i).getValue()+"%"+'\''+" ";
			}
			
		}
		
		String sortStr = "";
		String sortColName = "";
		if(!sortedList.isEmpty()) {
			if(sortedList.get(0).getId().equals("claimId")) {
				sortColName = "claim_id";
			}
			if(sortedList.get(0).getId().equals("claimDate")) {
				sortColName = "claim_date";
			}
			if(sortedList.get(0).getId().equals("claimType")) {
				sortColName = "claim_type";
			}
			if(sortedList.get(0).getId().equals("clinicName")) {
				sortColName = "clinic_facility_name";
			}
			
			if(sortedList.get(0).getId().equals("pcpLocation")) {
				sortColName = "pcp_location_code";
			}
			
			if(sortedList.get(0).getId().equals("icdCodes")) {
				sortColName = "principal_diagnosis";
			}
			if(sortedList.get(0).getId().equals("hccCodes")) {
				sortColName = "";
			}
			if(sortedList.get(0).getId().equals("drgCode")) {
				sortColName = "drg_code";
			}
			if(sortedList.get(0).getId().equals("betosCat")) {
				sortColName = "betos_cat";
			}
			if(sortedList.get(0).getId().equals("cost")) {
				sortColName = "cost";
			}
			if(!sortColName.equals("")) {
				sortStr+= " "+sortColName+" ";
				if(sortedList.get(0).isDesc()) {
					sortStr += "desc";
				} else {
					sortStr += "asc";
				}
			}
		}
		
		List<Object[]> queryResult = new ArrayList<>();
		int start,end,noOfPages = 0,totalCount = 0;
		end = vm.getPageSize() * vm.getPage();
		start = end - vm.getPageSize();
		end = vm.getPageSize();
		
		String sortQryStr = "  order by cost desc limit "+start+","+end;
		String sortCountQryStr = " order by cost desc ";
		if(!sortStr.equals("")) {
			sortQryStr = " order by "+sortStr+" limit "+start+","+end;
			sortCountQryStr = " order by "+sortStr;
		}
		
		String queryStr = "",countQueryStr = "";
		
		if(!filterStr.equals(""))
			filterStr = " where "+filterStr.substring(4);	
		
		if(vm.getProvider().equals("all") && vm.getYear().equals("all")) {
				
			queryStr = "select * from (select claim_id,first_service_date as claim_date,'INST CLAIMS' as claim_type,clinic_facility_name,pcp_location_code,drg_code,betos_cat,paid_amount as cost,principal_diagnosis from inst_claim_detail where pcp_location_code="+'\''+vm.getPcpLocation()+'\''+"\n" + 
					"union\n" + 
					"select claim_id,first_service_date as claim_date,'PROF CLAIMS' as claim_type,clinic_facility_name,pcp_location_code,'' as drg_code,betos_cat,paid_amount as cost,principal_diagnosis from prof_claim_detail where pcp_location_code="+'\''+vm.getPcpLocation()+'\''+"\n" + 
					"union\n" + 
					"select claim_id,date_filled as claim_date,'RX CLAIMS' as claim_type,'' as clinic_facility_name,pcp_location_code,'' as drg_code,'' as betos_cat,paid_amount as cost,'' as principal_diagnosis from rx_detail where pcp_location_code="+'\''+vm.getPcpLocation()+'\''+"\n" + 
					"union\n" + 
					"select 'N/A' as claim_id,eligible_month as claim_date,'PCP CAP' as claim_type,'' as clinic_facility_name,pcp_location_code,'' as drg_code,'' as betos_cat,pcp_cap as cost,'' as principal_diagnosis from demographic_detail where pcp_location_code="+'\''+vm.getPcpLocation()+'\''+"\n" + 
					"union\n" + 
					"select 'N/A' as claim_id,eligible_month as claim_date,'REINSURANCE PREM' as claim_type,'' as clinic_facility_name,pcp_location_code,'' as drg_code,'' as betos_cat,constant_val as cost,'' as principal_diagnosis from demographic_detail where pcp_location_code="+'\''+vm.getPcpLocation()+'\''+"\n" + 
					"union\n" + 
					"select 'N/A' as claim_id,eligible_month as claim_date,'SPEC CLAIMS' as claim_type,'' as clinic_facility_name,pcp_location_code,'' as drg_code,'' as betos_cat,\n" + 
					"behavioral_health+chiropractic_cap+dental_cap+hearing_cap+lab+vision_ophthamalogy+vision_optometry+otc_cap+gym_cap+podiatry_cap+transportation\n" + 
					"+dermatology as cost,'' as principal_diagnosis from demographic_detail where pcp_location_code="+'\''+vm.getPcpLocation()+'\''+") A "+filterStr;
			
			countQueryStr = "select count(*) from \n" + 
					"(\n" + 
					queryStr;
		} else {
			if(vm.getProvider().equals("all")) {
				queryStr = "select * from (select claim_id,first_service_date as claim_date,'INST CLAIMS' as claim_type,clinic_facility_name,pcp_location_code,drg_code,betos_cat,paid_amount as cost,principal_diagnosis from inst_claim_detail where first_service_date like "+'\''+vm.getYear()+"%"+'\''+" and pcp_location_code="+'\''+vm.getPcpLocation()+'\''+"\n" + 
						"union\n" + 
						"select claim_id,first_service_date as claim_date,'PROF CLAIMS' as claim_type,clinic_facility_name,pcp_location_code,'' as drg_code,betos_cat,paid_amount as cost,principal_diagnosis from prof_claim_detail where first_service_date like "+'\''+vm.getYear()+"%"+'\''+" and pcp_location_code="+'\''+vm.getPcpLocation()+'\''+"\n" + 
						"union\n" + 
						"select claim_id,date_filled as claim_date,'RX CLAIMS' as claim_type,'' as clinic_facility_name,pcp_location_code,'' as drg_code,'' as betos_cat,paid_amount as cost,'' as principal_diagnosis from rx_detail where date_filled like "+'\''+vm.getYear()+"%"+'\''+" and pcp_location_code="+'\''+vm.getPcpLocation()+'\''+"\n" + 
						"union\n" + 
						"select 'N/A' as claim_id,eligible_month as claim_date,'PCP CAP' as claim_type,'' as clinic_facility_name,pcp_location_code,'' as drg_code,'' as betos_cat,pcp_cap as cost,'' as principal_diagnosis from demographic_detail where eligible_month like "+'\''+vm.getYear()+"%"+'\''+" and pcp_location_code="+'\''+vm.getPcpLocation()+'\''+"\n" + 
						"union\n" + 
						"select 'N/A' as claim_id,eligible_month as claim_date,'REINSURANCE PREM' as claim_type,'' as clinic_facility_name,pcp_location_code,'' as drg_code,'' as betos_cat,constant_val as cost,'' as principal_diagnosis from demographic_detail where eligible_month like "+'\''+vm.getYear()+"%"+'\''+" and pcp_location_code="+'\''+vm.getPcpLocation()+'\''+"\n" + 
						"union\n" + 
						"select 'N/A' as claim_id,eligible_month as claim_date,'SPEC CLAIMS' as claim_type,'' as clinic_facility_name,pcp_location_code,'' as drg_code,'' as betos_cat,\n" + 
						"behavioral_health+chiropractic_cap+dental_cap+hearing_cap+lab+vision_ophthamalogy+vision_optometry+otc_cap+gym_cap+podiatry_cap+transportation\n" + 
						"+dermatology as cost,'' as principal_diagnosis from demographic_detail where eligible_month like "+'\''+vm.getYear()+"%"+'\''+" and pcp_location_code="+'\''+vm.getPcpLocation()+'\''+") A "+filterStr;
			}
			if(vm.getYear().equals("all")) {
				
				queryStr = "select * from (select claim_id,first_service_date as claim_date,'INST CLAIMS' as claim_type,clinic_facility_name,pcp_location_code,drg_code,betos_cat,paid_amount as cost,principal_diagnosis from inst_claim_detail where provider="+'\''+vm.getProvider()+'\''+" and pcp_location_code="+'\''+vm.getPcpLocation()+'\''+"\n" + 
						"union\n" + 
						"select claim_id,first_service_date as claim_date,'PROF CLAIMS' as claim_type,clinic_facility_name,pcp_location_code,'' as drg_code,betos_cat,paid_amount as cost,principal_diagnosis from prof_claim_detail where provider="+'\''+vm.getProvider()+'\''+" and pcp_location_code="+'\''+vm.getPcpLocation()+'\''+"\n" + 
						"union\n" + 
						"select claim_id,date_filled as claim_date,'RX CLAIMS' as claim_type,'' as clinic_facility_name,pcp_location_code,'' as drg_code,'' as betos_cat,paid_amount as cost,'' as principal_diagnosis from rx_detail where provider="+'\''+vm.getProvider()+'\''+" and pcp_location_code="+'\''+vm.getPcpLocation()+'\''+"\n" + 
						"union\n" + 
						"select 'N/A' as claim_id,eligible_month as claim_date,'PCP CAP' as claim_type,'' as clinic_facility_name,pcp_location_code,'' as drg_code,'' as betos_cat,pcp_cap as cost,'' as principal_diagnosis from demographic_detail where provider="+'\''+vm.getProvider()+'\''+" and pcp_location_code="+'\''+vm.getPcpLocation()+'\''+"\n" + 
						"union\n" + 
						"select 'N/A' as claim_id,eligible_month as claim_date,'REINSURANCE PREM' as claim_type,'' as clinic_facility_name,pcp_location_code,'' as drg_code,'' as betos_cat,constant_val as cost,'' as principal_diagnosis from demographic_detail where provider="+'\''+vm.getProvider()+'\''+" and pcp_location_code="+'\''+vm.getPcpLocation()+'\''+"\n" + 
						"union\n" + 
						"select 'N/A' as claim_id,eligible_month as claim_date,'SPEC CLAIMS' as claim_type,'' as clinic_facility_name,pcp_location_code,'' as drg_code,'' as betos_cat,\n" + 
						"behavioral_health+chiropractic_cap+dental_cap+hearing_cap+lab+vision_ophthamalogy+vision_optometry+otc_cap+gym_cap+podiatry_cap+transportation\n" + 
						"+dermatology as cost,'' as principal_diagnosis from demographic_detail where provider="+'\''+vm.getProvider()+'\''+" and pcp_location_code="+'\''+vm.getPcpLocation()+'\''+") A "+filterStr;
			}
			if(!vm.getProvider().equals("all") && !vm.getYear().equals("all")) {
				
				queryStr = "select * from (select claim_id,first_service_date as claim_date,'INST CLAIMS' as claim_type,clinic_facility_name,pcp_location_code,drg_code,betos_cat,paid_amount as cost,principal_diagnosis from inst_claim_detail where provider="+'\''+vm.getProvider()+'\''+" and first_service_date like "+'\''+vm.getYear()+"%"+'\''+" and pcp_location_code="+'\''+vm.getPcpLocation()+'\''+"\n" + 
						"union\n" + 
						"select claim_id,first_service_date as claim_date,'PROF CLAIMS' as claim_type,clinic_facility_name,pcp_location_code,'' as drg_code,betos_cat,paid_amount as cost,principal_diagnosis from prof_claim_detail where provider="+'\''+vm.getProvider()+'\''+" and first_service_date like "+'\''+vm.getYear()+"%"+'\''+" and pcp_location_code="+'\''+vm.getPcpLocation()+'\''+"\n" + 
						"union\n" + 
						"select claim_id,date_filled as claim_date,'RX CLAIMS' as claim_type,'' as clinic_facility_name,pcp_location_code,'' as drg_code,'' as betos_cat,paid_amount as cost,'' as principal_diagnosis from rx_detail where provider="+'\''+vm.getProvider()+'\''+" and date_filled like "+'\''+vm.getYear()+"%"+'\''+" and pcp_location_code="+'\''+vm.getPcpLocation()+'\''+"\n" + 
						"union\n" + 
						"select 'N/A' as claim_id,eligible_month as claim_date,'PCP CAP' as claim_type,'' as clinic_facility_name,pcp_location_code,'' as drg_code,'' as betos_cat,pcp_cap as cost,'' as principal_diagnosis from demographic_detail where provider="+'\''+vm.getProvider()+'\''+" and eligible_month like "+'\''+vm.getYear()+"%"+'\''+" and pcp_location_code="+'\''+vm.getPcpLocation()+'\''+"\n" + 
						"union\n" + 
						"select 'N/A' as claim_id,eligible_month as claim_date,'REINSURANCE PREM' as claim_type,'' as clinic_facility_name,pcp_location_code,'' as drg_code,'' as betos_cat,constant_val as cost,'' as principal_diagnosis from demographic_detail where provider="+'\''+vm.getProvider()+'\''+" and eligible_month like "+'\''+vm.getYear()+"%"+'\''+" and pcp_location_code="+'\''+vm.getPcpLocation()+'\''+"\n" + 
						"union\n" + 
						"select 'N/A' as claim_id,eligible_month as claim_date,'SPEC CLAIMS' as claim_type,'' as clinic_facility_name,pcp_location_code,'' as drg_code,'' as betos_cat,\n" + 
						"behavioral_health+chiropractic_cap+dental_cap+hearing_cap+lab+vision_ophthamalogy+vision_optometry+otc_cap+gym_cap+podiatry_cap+transportation\n" + 
						"+dermatology as cost,'' as principal_diagnosis from demographic_detail where provider="+'\''+vm.getProvider()+'\''+" and eligible_month like "+'\''+vm.getYear()+"%"+'\''+" and pcp_location_code="+'\''+vm.getPcpLocation()+'\''+") A "+filterStr;
				
			}
			countQueryStr = "select count(*) from \n" + 
					"(\n" + 
					queryStr;
		}
		
		System.out.println(queryStr+sortQryStr);
		
		Query query = getEntityManager().createNativeQuery(queryStr+sortQryStr);
		queryResult = query.getResultList();
		
		System.out.println(queryStr+sortQryStr);
		System.out.println(countQueryStr+sortCountQryStr);
		Query countQuery = getEntityManager().createNativeQuery(countQueryStr+sortCountQryStr+" ) A");
		totalCount = Integer.parseInt(countQuery.getSingleResult().toString());
		noOfPages = totalCount/vm.getPageSize();
		if(totalCount % vm.getPageSize() > 0)
			noOfPages++;
		
		String fileQuery = queryStr+sortCountQryStr;
		

		responseVM.setDataList(queryResult);
		responseVM.setNoOfPages(noOfPages);
		responseVM.setTotalCount(totalCount);
		responseVM.setFileQuery(fileQuery);
		return responseVM;
	}
	
	public ReportResponseVM getBeneficiariesManagementByClinicExpandReportData(ReportVM vm) {
		ReportResponseVM responseVM = new ReportResponseVM();
		
		ObjectMapper mapper = new ObjectMapper();
		List<SortedVM> sortedList = null;
		List<FilteredVM> filteredList = null;
		try {
			sortedList = mapper.readValue(vm.getSortedColumns(), new TypeReference<List<SortedVM>>(){});
			filteredList = mapper.readValue(vm.getFilteredColumns(), new TypeReference<List<FilteredVM>>(){});
		} catch (Exception e) {
			e.printStackTrace();
		}
		String filterStr = "", havingStr = "";
		String filterColumnName = "";
		for(int i=0;i<filteredList.size();i++) {
			if(filteredList.get(i).getId().equals("claimId")) {
				filterColumnName = "claim_id";
			}
			if(filteredList.get(i).getId().equals("claimDate")) {
				filterColumnName = "claim_date";
			}
			if(filteredList.get(i).getId().equals("claimType")) {
				filterColumnName = "claim_type";
			}
			if(filteredList.get(i).getId().equals("clinicName")) {
				filterColumnName = "clinic_facility_name";
			}
		
			if(filteredList.get(i).getId().equals("pcpName"))
			{
				filterColumnName="pcp_name";
			}
			if(filteredList.get(i).getId().equals("icdCodes")) {
				filterColumnName = "principal_diagnosis";
			}
			if(filteredList.get(i).getId().equals("hccCodes")) {
				filterColumnName = "";
			}
			if(filteredList.get(i).getId().equals("drgCode")) {
				filterColumnName = "drg_code";
			}
			if(filteredList.get(i).getId().equals("betosCat")) {
				filterColumnName = "betos_cat";
			}
			if(filteredList.get(i).getId().equals("cost")) {
				filterColumnName = "cost";
			}
			
			if(!filterColumnName.equals("")) {
				filterStr += " and "+filterColumnName+" like "+'\''+"%"+filteredList.get(i).getValue()+"%"+'\''+" ";
			}
			
		}
		
		String sortStr = "";
		String sortColName = "";
		if(!sortedList.isEmpty()) {
			if(sortedList.get(0).getId().equals("claimId")) {
				sortColName = "claim_id";
			}
			if(sortedList.get(0).getId().equals("claimDate")) {
				sortColName = "claim_date";
			}
			if(sortedList.get(0).getId().equals("claimType")) {
				sortColName = "claim_type";
			}
			if(sortedList.get(0).getId().equals("clinicName")) {
				sortColName = "clinic_facility_name";
			}
			
			if(sortedList.get(0).getId().equals("pcpName")) {
				sortColName = "pcp_name";
			}
			
			if(sortedList.get(0).getId().equals("icdCodes")) {
				sortColName = "principal_diagnosis";
			}
			if(sortedList.get(0).getId().equals("hccCodes")) {
				sortColName = "";
			}
			if(sortedList.get(0).getId().equals("drgCode")) {
				sortColName = "drg_code";
			}
			if(sortedList.get(0).getId().equals("betosCat")) {
				sortColName = "betos_cat";
			}
			if(sortedList.get(0).getId().equals("cost")) {
				sortColName = "cost";
			}
			if(!sortColName.equals("")) {
				sortStr+= " "+sortColName+" ";
				if(sortedList.get(0).isDesc()) {
					sortStr += "desc";
				} else {
					sortStr += "asc";
				}
			}
		}
		
		List<Object[]> queryResult = new ArrayList<>();
		int start,end,noOfPages = 0,totalCount = 0;
		end = vm.getPageSize() * vm.getPage();
		start = end - vm.getPageSize();
		end = vm.getPageSize();
		
		String sortQryStr = "  order by cost desc limit "+start+","+end;
		String sortCountQryStr = " order by cost desc ";
		if(!sortStr.equals("")) {
			sortQryStr = " order by "+sortStr+" limit "+start+","+end;
			sortCountQryStr = " order by "+sortStr;
		}
		
		String queryStr = "",countQueryStr = "";
		
		if(!filterStr.equals(""))
			filterStr = " where "+filterStr.substring(4);	
		
		if(vm.getProvider().equals("all") && vm.getYear().equals("all")) {
				
			queryStr = "select * from (select claim_id,first_service_date as claim_date,'INST CLAIMS' as claim_type,clinic_facility_name,pcp_name,drg_code,betos_cat,paid_amount as cost,principal_diagnosis from inst_claim_detail where clinic_facility_name="+'\''+vm.getClinicName()+'\''+"\n" + 
					"union\n" + 
					"select claim_id,first_service_date as claim_date,'PROF CLAIMS' as claim_type,clinic_facility_name,pcp_name,'' as drg_code,betos_cat,paid_amount as cost,principal_diagnosis from prof_claim_detail where clinic_facility_name="+'\''+vm.getClinicName()+'\''+") A "+filterStr;
			
			countQueryStr = "select count(*) from \n" + 
					"(\n" + 
					queryStr;
		} else {
			if(vm.getProvider().equals("all")) {
				queryStr = "select * from (select claim_id,first_service_date as claim_date,'INST CLAIMS' as claim_type,clinic_facility_name,pcp_name,drg_code,betos_cat,paid_amount as cost,principal_diagnosis from inst_claim_detail where first_service_date like "+'\''+vm.getYear()+"%"+'\''+" and clinic_facility_name="+'\''+vm.getClinicName()+'\''+"\n" + 
						"union\n" + 
						"select claim_id,first_service_date as claim_date,'PROF CLAIMS' as claim_type,clinic_facility_name,pcp_name,'' as drg_code,betos_cat,paid_amount as cost,principal_diagnosis from prof_claim_detail where first_service_date like "+'\''+vm.getYear()+"%"+'\''+" and clinic_facility_name="+'\''+vm.getClinicName()+'\''+") A "+filterStr;
			}
			if(vm.getYear().equals("all")) {
				
				queryStr = "select * from (select claim_id,first_service_date as claim_date,'INST CLAIMS' as claim_type,clinic_facility_name,pcp_name,drg_code,betos_cat,paid_amount as cost,principal_diagnosis from inst_claim_detail where provider="+'\''+vm.getProvider()+'\''+" and clinic_facility_name="+'\''+vm.getClinicName()+'\''+"\n" + 
						"union\n" + 
						"select claim_id,first_service_date as claim_date,'PROF CLAIMS' as claim_type,clinic_facility_name,pcp_location_code,'' as drg_code,betos_cat,paid_amount as cost,principal_diagnosis from prof_claim_detail where provider="+'\''+vm.getProvider()+'\''+" and clinic_facility_name="+'\''+vm.getClinicName()+'\''+") A "+filterStr;
			}
			if(!vm.getProvider().equals("all") && !vm.getYear().equals("all")) {
				
				queryStr = "select * from (select claim_id,first_service_date as claim_date,'INST CLAIMS' as claim_type,clinic_facility_name,pcp_name,drg_code,betos_cat,paid_amount as cost,principal_diagnosis from inst_claim_detail where provider="+'\''+vm.getProvider()+'\''+" and first_service_date like "+'\''+vm.getYear()+"%"+'\''+" and clinic_facility_name="+'\''+vm.getClinicName()+'\''+"\n" + 
						"union\n" + 
						"select claim_id,first_service_date as claim_date,'PROF CLAIMS' as claim_type,clinic_facility_name,pcp_name,'' as drg_code,betos_cat,paid_amount as cost,principal_diagnosis from prof_claim_detail where provider="+'\''+vm.getProvider()+'\''+" and first_service_date like "+'\''+vm.getYear()+"%"+'\''+" and clinic_facility_name="+'\''+vm.getClinicName()+'\''+") A "+filterStr;
				
			}
			countQueryStr = "select count(*) from \n" + 
					"(\n" + 
					queryStr;
		}
		
		System.out.println(queryStr+sortQryStr);
		
		Query query = getEntityManager().createNativeQuery(queryStr+sortQryStr);
		queryResult = query.getResultList();
		
		System.out.println(queryStr+sortQryStr);
		System.out.println(countQueryStr+sortCountQryStr);
		Query countQuery = getEntityManager().createNativeQuery(countQueryStr+sortCountQryStr+" ) A");
		totalCount = Integer.parseInt(countQuery.getSingleResult().toString());
		noOfPages = totalCount/vm.getPageSize();
		if(totalCount % vm.getPageSize() > 0)
			noOfPages++;
		
		String fileQuery = queryStr+sortCountQryStr;
		

		responseVM.setDataList(queryResult);
		responseVM.setNoOfPages(noOfPages);
		responseVM.setTotalCount(totalCount);
		responseVM.setFileQuery(fileQuery);
		return responseVM;
	}
	

	public ReportResponseVM getBeneficiariesManagementByDoctorExpandReportData(ReportVM vm) {
		ReportResponseVM responseVM = new ReportResponseVM();
		
		ObjectMapper mapper = new ObjectMapper();
		List<SortedVM> sortedList = null;
		List<FilteredVM> filteredList = null;
		try {
			sortedList = mapper.readValue(vm.getSortedColumns(), new TypeReference<List<SortedVM>>(){});
			filteredList = mapper.readValue(vm.getFilteredColumns(), new TypeReference<List<FilteredVM>>(){});
		} catch (Exception e) {
			e.printStackTrace();
		}
		String filterStr = "", havingStr = "";
		String filterColumnName = "";
		for(int i=0;i<filteredList.size();i++) {
			if(filteredList.get(i).getId().equals("claimId")) {
				filterColumnName = "claim_id";
			}
			if(filteredList.get(i).getId().equals("claimDate")) {
				filterColumnName = "claim_date";
			}
			if(filteredList.get(i).getId().equals("claimType")) {
				filterColumnName = "claim_type";
			}
			if(filteredList.get(i).getId().equals("clinicName")) {
				filterColumnName = "clinic_facility_name";
			}
			if(filteredList.get(i).getId().equals("pcpName")) {
				filterColumnName = "pcp_name";
			}
			if(filteredList.get(i).getId().equals("icdCodes")) {
				filterColumnName = "principal_diagnosis";
			}
			if(filteredList.get(i).getId().equals("hccCodes")) {
				filterColumnName = "";
			}
			if(filteredList.get(i).getId().equals("drgCode")) {
				filterColumnName = "drg_code";
			}
			if(filteredList.get(i).getId().equals("betosCat")) {
				filterColumnName = "betos_cat";
			}
			if(filteredList.get(i).getId().equals("cost")) {
				filterColumnName = "cost";
			}
			
			if(!filterColumnName.equals("")) {
				filterStr += " and "+filterColumnName+" like "+'\''+"%"+filteredList.get(i).getValue()+"%"+'\''+" ";
			}
			
		}
		
		String sortStr = "";
		String sortColName = "";
		if(!sortedList.isEmpty()) {
			if(sortedList.get(0).getId().equals("claimId")) {
				sortColName = "claim_id";
			}
			if(sortedList.get(0).getId().equals("claimDate")) {
				sortColName = "claim_date";
			}
			if(sortedList.get(0).getId().equals("claimType")) {
				sortColName = "claim_type";
			}
			if(sortedList.get(0).getId().equals("clinicName")) {
				sortColName = "clinic_facility_name";
			}
			if(sortedList.get(0).getId().equals("pcpName")) {
				sortColName = "pcp_name";
			}
			if(sortedList.get(0).getId().equals("icdCodes")) {
				sortColName = "principal_diagnosis";
			}
			if(sortedList.get(0).getId().equals("hccCodes")) {
				sortColName = "";
			}
			if(sortedList.get(0).getId().equals("drgCode")) {
				sortColName = "drg_code";
			}
			if(sortedList.get(0).getId().equals("betosCat")) {
				sortColName = "betos_cat";
			}
			if(sortedList.get(0).getId().equals("cost")) {
				sortColName = "cost";
			}
			if(!sortColName.equals("")) {
				sortStr+= " "+sortColName+" ";
				if(sortedList.get(0).isDesc()) {
					sortStr += "desc";
				} else {
					sortStr += "asc";
				}
			}
		}
		
		List<Object[]> queryResult = new ArrayList<>();
		int start,end,noOfPages = 0,totalCount = 0;
		end = vm.getPageSize() * vm.getPage();
		start = end - vm.getPageSize();
		end = vm.getPageSize();
		
		String sortQryStr = " order by cost desc limit "+start+","+end;
		String sortCountQryStr = " order by cost desc ";
		if(!sortStr.equals("")) {
			sortQryStr = " order by "+sortStr+" limit "+start+","+end;
			sortCountQryStr = " order by "+sortStr;
		}
		
		String queryStr = "",countQueryStr = "";
		
		if(!filterStr.equals(""))
			filterStr = " where "+filterStr.substring(4);	
		
		if(vm.getProvider().equals("all") && vm.getYear().equals("all")) {
				
			queryStr = "select * from (select claim_id,first_service_date as claim_date,'INST CLAIMS' as claim_type,clinic_facility_name,pcp_name,drg_code,betos_cat,paid_amount as cost,principal_diagnosis from inst_claim_detail where pcp_name="+'\''+vm.getPcpId()+'\''+"\n" + 
					"union\n" + 
					"select claim_id,first_service_date as claim_date,'PROF CLAIMS' as claim_type,clinic_facility_name,pcp_name,'' as drg_code,betos_cat,paid_amount as cost,principal_diagnosis from prof_claim_detail where pcp_name="+'\''+vm.getPcpId()+'\''+"\n" + 
					"union\n" + 
					"select claim_id,date_filled as claim_date,'RX CLAIMS' as claim_type,'' as clinic_facility_name,pcp_name,'' as drg_code,'' as betos_cat,paid_amount as cost,'' as principal_diagnosis from rx_detail where pcp_name="+'\''+vm.getPcpId()+'\''+"\n" + 
					"union\n" + 
					"select 'N/A' as claim_id,eligible_month as claim_date,'PCP CAP' as claim_type,'' as clinic_facility_name,pcp_name,'' as drg_code,'' as betos_cat,pcp_cap as cost,'' as principal_diagnosis from demographic_detail where pcp_name="+'\''+vm.getPcpId()+'\''+"\n" + 
					"union\n" + 
					"select 'N/A' as claim_id,eligible_month as claim_date,'REINSURANCE PREM' as claim_type,'' as clinic_facility_name,pcp_name,'' as drg_code,'' as betos_cat,constant_val as cost,'' as principal_diagnosis from demographic_detail where pcp_name="+'\''+vm.getPcpId()+'\''+"\n" + 
					"union\n" + 
					"select 'N/A' as claim_id,eligible_month as claim_date,'SPEC CLAIMS' as claim_type,'' as clinic_facility_name,pcp_name,'' as drg_code,'' as betos_cat,\n" + 
					"behavioral_health+chiropractic_cap+dental_cap+hearing_cap+lab+vision_ophthamalogy+vision_optometry+otc_cap+gym_cap+podiatry_cap+transportation\n" + 
					"+dermatology as cost,'' as principal_diagnosis from demographic_detail where pcp_name="+'\''+vm.getPcpId()+'\''+") A "+filterStr;
			
			countQueryStr = "select count(*) from \n" + 
					"(\n" + 
					queryStr;
		} else {
			if(vm.getProvider().equals("all")) {
				queryStr = "select * from (select claim_id,first_service_date as claim_date,'INST CLAIMS' as claim_type,clinic_facility_name,pcp_name,drg_code,betos_cat,paid_amount as cost,principal_diagnosis from inst_claim_detail where first_service_date like "+'\''+vm.getYear()+"%"+'\''+" and pcp_name="+'\''+vm.getPcpId()+'\''+"\n" + 
						"union\n" + 
						"select claim_id,first_service_date as claim_date,'PROF CLAIMS' as claim_type,clinic_facility_name,pcp_name,'' as drg_code,betos_cat,paid_amount as cost,principal_diagnosis from prof_claim_detail where first_service_date like "+'\''+vm.getYear()+"%"+'\''+" and pcp_name="+'\''+vm.getPcpId()+'\''+"\n" + 
						"union\n" + 
						"select claim_id,date_filled as claim_date,'RX CLAIMS' as claim_type,'' as clinic_facility_name,pcp_name,'' as drg_code,'' as betos_cat,paid_amount as cost,'' as principal_diagnosis from rx_detail where date_filled like "+'\''+vm.getYear()+"%"+'\''+" and pcp_name="+'\''+vm.getPcpId()+'\''+"\n" + 
						"union\n" + 
						"select 'N/A' as claim_id,eligible_month as claim_date,'PCP CAP' as claim_type,'' as clinic_facility_name,pcp_name,'' as drg_code,'' as betos_cat,pcp_cap as cost,'' as principal_diagnosis from demographic_detail where eligible_month like "+'\''+vm.getYear()+"%"+'\''+" and pcp_name="+'\''+vm.getPcpId()+'\''+"\n" + 
						"union\n" + 
						"select 'N/A' as claim_id,eligible_month as claim_date,'REINSURANCE PREM' as claim_type,'' as clinic_facility_name,pcp_name,'' as drg_code,'' as betos_cat,constant_val as cost,'' as principal_diagnosis from demographic_detail where eligible_month like "+'\''+vm.getYear()+"%"+'\''+" and pcp_name="+'\''+vm.getPcpId()+'\''+"\n" + 
						"union\n" + 
						"select 'N/A' as claim_id,eligible_month as claim_date,'SPEC CLAIMS' as claim_type,'' as clinic_facility_name,pcp_name,'' as drg_code,'' as betos_cat,\n" + 
						"behavioral_health+chiropractic_cap+dental_cap+hearing_cap+lab+vision_ophthamalogy+vision_optometry+otc_cap+gym_cap+podiatry_cap+transportation\n" + 
						"+dermatology as cost,'' as principal_diagnosis from demographic_detail where eligible_month like "+'\''+vm.getYear()+"%"+'\''+" and pcp_name="+'\''+vm.getPcpId()+'\''+") A "+filterStr;
			}
			if(vm.getYear().equals("all")) {
				
				queryStr = "select * from (select claim_id,first_service_date as claim_date,'INST CLAIMS' as claim_type,clinic_facility_name,pcp_name,drg_code,betos_cat,paid_amount as cost,principal_diagnosis from inst_claim_detail where provider="+'\''+vm.getProvider()+'\''+" and pcp_id="+'\''+vm.getPcpId()+'\''+"\n" + 
						"union\n" + 
						"select claim_id,first_service_date as claim_date,'PROF CLAIMS' as claim_type,clinic_facility_name,pcp_name,'' as drg_code,betos_cat,paid_amount as cost,principal_diagnosis from prof_claim_detail where provider="+'\''+vm.getProvider()+'\''+" and pcp_id="+'\''+vm.getPcpId()+'\''+"\n" + 
						"union\n" + 
						"select claim_id,date_filled as claim_date,'RX CLAIMS' as claim_type,'' as clinic_facility_name,pcp_name,'' as drg_code,'' as betos_cat,paid_amount as cost,'' as principal_diagnosis from rx_detail where provider="+'\''+vm.getProvider()+'\''+" and pcp_id="+'\''+vm.getPcpId()+'\''+"\n" + 
						"union\n" + 
						"select 'N/A' as claim_id,eligible_month as claim_date,'PCP CAP' as claim_type,'' as clinic_facility_name,pcp_name,'' as drg_code,'' as betos_cat,pcp_cap as cost,'' as principal_diagnosis from demographic_detail where provider="+'\''+vm.getProvider()+'\''+" and pcp_id="+'\''+vm.getPcpId()+'\''+"\n" + 
						"union\n" + 
						"select 'N/A' as claim_id,eligible_month as claim_date,'REINSURANCE PREM' as claim_type,'' as clinic_facility_name,pcp_name,'' as drg_code,'' as betos_cat,constant_val as cost,'' as principal_diagnosis from demographic_detail where provider="+'\''+vm.getProvider()+'\''+" and pcp_id="+'\''+vm.getPcpId()+'\''+"\n" + 
						"union\n" + 
						"select 'N/A' as claim_id,eligible_month as claim_date,'SPEC CLAIMS' as claim_type,'' as clinic_facility_name,pcp_name,'' as drg_code,'' as betos_cat,\n" + 
						"behavioral_health+chiropractic_cap+dental_cap+hearing_cap+lab+vision_ophthamalogy+vision_optometry+otc_cap+gym_cap+podiatry_cap+transportation\n" + 
						"+dermatology as cost,'' as principal_diagnosis from demographic_detail where provider="+'\''+vm.getProvider()+'\''+" and pcp_id="+'\''+vm.getPcpId()+'\''+") A "+filterStr;
			}
			if(!vm.getProvider().equals("all") && !vm.getYear().equals("all")) {
				
				queryStr = "select * from (select claim_id,first_service_date as claim_date,'INST CLAIMS' as claim_type,clinic_facility_name,pcp_name,drg_code,betos_cat,paid_amount as cost,principal_diagnosis from inst_claim_detail where provider="+'\''+vm.getProvider()+'\''+" and first_service_date like "+'\''+vm.getYear()+"%"+'\''+" and pcp_id="+'\''+vm.getPcpId()+'\''+"\n" + 
						"union\n" + 
						"select claim_id,first_service_date as claim_date,'PROF CLAIMS' as claim_type,clinic_facility_name,pcp_name,'' as drg_code,betos_cat,paid_amount as cost,principal_diagnosis from prof_claim_detail where provider="+'\''+vm.getProvider()+'\''+" and first_service_date like "+'\''+vm.getYear()+"%"+'\''+" and pcp_id="+'\''+vm.getPcpId()+'\''+"\n" + 
						"union\n" + 
						"select claim_id,date_filled as claim_date,'RX CLAIMS' as claim_type,'' as clinic_facility_name,pcp_name,'' as drg_code,'' as betos_cat,paid_amount as cost,'' as principal_diagnosis from rx_detail where provider="+'\''+vm.getProvider()+'\''+" and date_filled like "+'\''+vm.getYear()+"%"+'\''+" and pcp_id="+'\''+vm.getPcpId()+'\''+"\n" + 
						"union\n" + 
						"select 'N/A' as claim_id,eligible_month as claim_date,'PCP CAP' as claim_type,'' as clinic_facility_name,pcp_name,'' as drg_code,'' as betos_cat,pcp_cap as cost,'' as principal_diagnosis from demographic_detail where provider="+'\''+vm.getProvider()+'\''+" and eligible_month like "+'\''+vm.getYear()+"%"+'\''+" and pcp_id="+'\''+vm.getPcpId()+'\''+"\n" + 
						"union\n" + 
						"select 'N/A' as claim_id,eligible_month as claim_date,'REINSURANCE PREM' as claim_type,'' as clinic_facility_name,pcp_name,'' as drg_code,'' as betos_cat,constant_val as cost,'' as principal_diagnosis from demographic_detail where provider="+'\''+vm.getProvider()+'\''+" and eligible_month like "+'\''+vm.getYear()+"%"+'\''+" and pcp_id="+'\''+vm.getPcpId()+'\''+"\n" + 
						"union\n" + 
						"select 'N/A' as claim_id,eligible_month as claim_date,'SPEC CLAIMS' as claim_type,'' as clinic_facility_name,pcp_name,'' as drg_code,'' as betos_cat,\n" + 
						"behavioral_health+chiropractic_cap+dental_cap+hearing_cap+lab+vision_ophthamalogy+vision_optometry+otc_cap+gym_cap+podiatry_cap+transportation\n" + 
						"+dermatology as cost,'' as principal_diagnosis from demographic_detail where provider="+'\''+vm.getProvider()+'\''+" and eligible_month like "+'\''+vm.getYear()+"%"+'\''+" and pcp_id="+'\''+vm.getPcpId()+'\''+") A "+filterStr;
				
			}
			countQueryStr = "select count(*) from \n" + 
					"(\n" + 
					queryStr;
		}
		
		System.out.println(queryStr+sortQryStr);
		
		Query query = getEntityManager().createNativeQuery(queryStr+sortQryStr);
		queryResult = query.getResultList();
		
		System.out.println(queryStr+sortQryStr);
		System.out.println(countQueryStr+sortCountQryStr);
		Query countQuery = getEntityManager().createNativeQuery(countQueryStr+sortCountQryStr+" ) A");
		totalCount = Integer.parseInt(countQuery.getSingleResult().toString());
		noOfPages = totalCount/vm.getPageSize();
		if(totalCount % vm.getPageSize() > 0)
			noOfPages++;
		
		String fileQuery = queryStr+sortCountQryStr;
		
		responseVM.setDataList(queryResult);
		responseVM.setNoOfPages(noOfPages);
		responseVM.setTotalCount(totalCount);
		responseVM.setFileQuery(fileQuery);
		return responseVM;
	}
	
}
