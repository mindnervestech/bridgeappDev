package com.mnt.dao.jpa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mnt.dao.InstClaimDetailDao;
import com.mnt.domain.InstClaimDetail;
import com.mnt.vm.AdmissionHeaderReportFileVM;
import com.mnt.vm.FilteredVM;
import com.mnt.vm.ReportResponseVM;
import com.mnt.vm.ReportVM;
import com.mnt.vm.SortedVM;

@Repository
public class InstClaimDetailDaoJpa extends BaseDaoJpa<InstClaimDetail> implements InstClaimDetailDao {

	public InstClaimDetailDaoJpa() {
		super(InstClaimDetail.class, "InstClaimDetail");
	}
	
	@Override
	@Transactional
	public void deleteOldRecords(String year, String month, String provider) {
		Query query = getEntityManager().createQuery("DELETE FROM InstClaimDetail icd where icd.year=:year and icd.month=:month and icd.provider=:provider");
		query.setParameter("year", year);
        query.setParameter("month", month);
        query.setParameter("provider", provider);
        query.executeUpdate();
	}
	
	@Override
	@Transactional
	public Double getSum(String year, List<String> providerArr) {
		String str = "";
			if(providerArr.contains("all")) {
				str = "select round(sum(paid_amount),2) from inst_claim_detail";
				if(!year.equals("all") && year != null && !year.equals("")) {
					str = str + " where year="+year;
				}
			} else {
				str = "select round(sum(paid_amount),2) from inst_claim_detail where ";
				for(int i=0; i<providerArr.size();i++) {
					if(i == providerArr.size()-1) {
						str = str + "provider="+'\''+providerArr.get(i)+'\'';
					} else {
						str = str + "provider="+'\''+providerArr.get(i)+'\''+" or ";
					}
				}	
				if(!year.equals("all") && year != null && !year.equals("")) {
					str = str + " and year="+year;
				}
			}
			
			System.out.println(str);
		Query query = getEntityManager().createNativeQuery(str);
		return query.getSingleResult() == null ? 0: (Double)query.getSingleResult();
	}
	
	public ReportResponseVM generateClaimDetailDataReport(ReportVM vm) {
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
				filterColumnName = "plan_name";
			}
			if(filteredList.get(i).getId().equals("providerName")) {
				filterColumnName = "pcp_name";
			}
			if(filteredList.get(i).getId().equals("medicareId")) {
				filterColumnName = "mbi";
			}
			if(filteredList.get(i).getId().equals("patientName")) {
				filterColumnName = "patientName";
			}
			if(filteredList.get(i).getId().equals("icdCode")) {
				filterColumnName = "icdCode";
			}
			if(filteredList.get(i).getId().equals("hccCode")) {
				filterColumnName = "hccCode";
			}
			if(filteredList.get(i).getId().equals("termedMonth")) {
				filterColumnName = "";
			}
			if(filteredList.get(i).getId().equals("eligibleMonth")) {
				filterColumnName = "eligibleMonth";
			}
			if(filteredList.get(i).getId().equals("cost")) {
				filterColumnName = "cost";
			}
			
			if(!filterColumnName.equals("") && !filterColumnName.equals("patientName") && !filterColumnName.equals("eligibleMonth") && !filterColumnName.equals("cost") && !filterColumnName.equals("icdCode") && !filterColumnName.equals("hccCode")) {
				filterStr += " and "+filterColumnName+" like "+'\''+"%"+filteredList.get(i).getValue()+"%"+'\''+" ";
			}
			
			if(filterColumnName.equals("patientName") || filterColumnName.equals("eligibleMonth") || filterColumnName.equals("cost") || filterColumnName.equals("icdCode") || filterColumnName.equals("hccCode")) {
				if(!havingStr.equals("")) {
					havingStr += " and ";
				} else {
					havingStr = " having ";
				}
				havingStr += filterColumnName+" like "+'\''+"%"+filteredList.get(i).getValue()+"%"+'\''+" ";
			}
				
		}
		
		if(vm.getCostRange() != null && !vm.getCostRange().equals("")) {
			if(!havingStr.equals("")) {
				havingStr += " and ";
			} else {
				havingStr = " having ";
			}
			if(vm.getCostRange().equals(">=10000")) {
				havingStr = havingStr + " total "+vm.getCostRange()+" ";
			} else {
				String arr[] = vm.getCostRange().split("-");
				havingStr = havingStr + " total between "+arr[0]+" and "+arr[1]+" ";
			}
		}
		
		String sortStr = "";
		String sortColName = "";
		
		if(!sortedList.isEmpty()) {
			if(sortedList.get(0).getId().equals("planName")) {
				sortColName = "plan_name";
			}
			if(sortedList.get(0).getId().equals("providerName")) {
				sortColName = "pcp_name";
			}
			if(sortedList.get(0).getId().equals("medicareId")) {
				sortColName = "mbi";
			}
			if(sortedList.get(0).getId().equals("patientName")) {
				sortColName = "patientName";
			}
			if(sortedList.get(0).getId().equals("icdCode")) {
				sortColName = "icdCode";
			}
			if(sortedList.get(0).getId().equals("hccCode")) {
				sortColName = "hccCode";
			}
			if(sortedList.get(0).getId().equals("termedMonth")) {
				sortColName = "";
			}
			if(sortedList.get(0).getId().equals("eligibleMonth")) {
				sortColName = "eligibleMonth";
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
		
		String sortQryStr = " limit "+start+","+end;
		String sortCountQryStr = "";
		if(!sortStr.equals("")) {
			sortQryStr = " order by "+sortStr+" limit "+start+","+end;
			sortCountQryStr = " order by "+sortStr;
		}
		
		String queryStr = "",countQueryStr = "",conditionStr = "",specialtyClaim="";
		
		if(!vm.getPlan().equals("all") && !vm.getPlan().equals("")) {
			conditionStr += " and dd.provider="+'\''+vm.getPlan()+'\'';
		}
		if(!vm.getLocation().equals("all") && !vm.getLocation().equals("")) {
			conditionStr += " and dd.pcp_location_code="+'\''+vm.getLocation()+'\'';
		}
		if(!vm.getProvider().equals("all") && !vm.getProvider().equals("")) {
			conditionStr += " and dd.pcp_name="+'\''+vm.getPcpName()+'\'';
		}
		if(vm.getGender() != null && !vm.getGender().equals("")) {
			conditionStr += " and dd.gender="+'\''+vm.getGender()+'\'';
		}
		
		if(vm.getAgeRange() != null && !vm.getAgeRange().equals("")) {
			if(vm.getAgeRange().equals(">=85")) {
				conditionStr += " and TIMESTAMPDIFF(YEAR, dd.birth_date, CURDATE()) "+vm.getAgeRange()+" ";
			} else {
				String arr[] = vm.getAgeRange().split("-");
				conditionStr += " and TIMESTAMPDIFF(YEAR, dd.birth_date, CURDATE()) between "+arr[0]+" and "+arr[1]+" ";
			}
		}
		
		if(vm.getZipCode() != null && !vm.getZipCode().equals("")) {
			conditionStr += " and dd.zip="+'\''+vm.getZipCode()+'\'';
		}
		if(vm.getHicn() != null && !vm.getHicn().equals("")) {
			conditionStr += " and dd.medicare_id="+'\''+vm.getHicn()+'\'';
		}
		
		if(vm.getSpeciality() != null && !vm.getSpeciality().equals("")) {
			specialtyClaim = " where pcd.claim_specialty="+'\''+vm.getSpeciality()+'\''+" ";
		}
		
		if(vm.getFromDate() != null && !vm.getFromDate().equals("Invalid date") && vm.getToDate() != null && !vm.getToDate().equals("Invalid date")) {
			conditionStr += " and dd.eligible_month between "+'\''+vm.getFromDate()+'\''+" and "+'\''+vm.getToDate()+'\'';	
		} 
			
		if(!conditionStr.equals("")) {
			conditionStr = " where "+conditionStr.substring(4);
		}
		if(!filterStr.equals(""))
			filterStr = " where "+filterStr.substring(4);
		
		if((vm.getClaimType().contains("inst claim") && vm.getClaimType().contains("prof claim") && vm.getClaimType().contains("rx claim") && vm.getClaimType().contains("pcp cap") && vm.getClaimType().contains("spec claim") && vm.getClaimType().contains("reinsurance prem")) || vm.getClaimType().isEmpty()) {
			
			queryStr = "select D.*, round((spec_cost + pcp_cap + reinsurance_prem + inst_claims + prof_claims + rx_claims),2) as total from (\n" + 
					"select C.*, COALESCE(round(sum(rd.paid_amount - lics_paid - rep_gap_dscnt),2),0) as rx_claims from  (\n" + 
					"select B.*, COALESCE(round(sum(pcd.paid_amount),2),0) as prof_claims from (\n" + 
					"select A.*, COALESCE(round(sum(icd.paid_amount),2),0) as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"COALESCE(round(sum(behavioral_health + chiropractic_cap + dental_cap + hearing_cap + lab + vision_ophthamalogy + vision_optometry + otc_cap + gym_cap + podiatry_cap + transportation + dermatology),2),0) as spec_cost,\n" + 
					"COALESCE(round(sum(pcp_cap),2),0) as pcp_cap, COALESCE(round(count(*)*min(constant_val),2),0) as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					" left join inst_claim_detail icd on icd.medicare_id = A.medicare_id  \n" + 
					"group by A.plan_name, A.mbi, A.patientName, A.eligibleMonth, A.pcp_name, A.medicare_id, A.spec_cost, A.pcp_cap, A.reinsurance_prem\n" + 
					") B \n" + 
					"left join prof_claim_detail pcd on pcd.medicare_id = B.medicare_id\n" + 
					specialtyClaim+
					"group by B.plan_name, B.mbi, B.patientName, B.eligibleMonth, B.pcp_name, B.medicare_id, B.spec_cost, B.pcp_cap, B.reinsurance_prem\n" + 
					") C\n" + 
					"left join rx_detail rd on rd.medicare_id = C.medicare_id\n" + 
					"group by C.plan_name, C.mbi, C.patientName, C.eligibleMonth, C.pcp_name, C.medicare_id, C.spec_cost, C.pcp_cap, C.reinsurance_prem\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("inst claim") && vm.getClaimType().contains("prof claim") && vm.getClaimType().contains("rx claim") && vm.getClaimType().contains("pcp cap") && vm.getClaimType().contains("spec claim") && vm.getClaimType().size() == 5) {
			
			queryStr = "select D.*, round((spec_cost + pcp_cap + inst_claims + prof_claims + rx_claims),2) as total from (\n" + 
					"select C.*, COALESCE(round(sum(rd.paid_amount - lics_paid - rep_gap_dscnt),2),0) as rx_claims from  (\n" + 
					"select B.*, COALESCE(round(sum(pcd.paid_amount),2),0) as prof_claims from (\n" + 
					"select A.*, COALESCE(round(sum(icd.paid_amount),2),0) as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"COALESCE(round(sum(behavioral_health + chiropractic_cap + dental_cap + hearing_cap + lab + vision_ophthamalogy + vision_optometry + otc_cap + gym_cap + podiatry_cap + transportation + dermatology),2),0) as spec_cost,\n" + 
					"COALESCE(round(sum(pcp_cap),2),0) as pcp_cap,' ' as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					" left join inst_claim_detail icd on icd.medicare_id = A.medicare_id  \n" + 
					"group by A.plan_name, A.mbi, A.patientName, A.eligibleMonth, A.pcp_name, A.medicare_id, A.spec_cost, A.pcp_cap, A.reinsurance_prem\n" + 
					") B \n" + 
					"left join prof_claim_detail pcd on pcd.medicare_id = B.medicare_id\n" + 
					specialtyClaim+
					"group by B.plan_name, B.mbi, B.patientName, B.eligibleMonth, B.pcp_name, B.medicare_id, B.spec_cost, B.pcp_cap, B.reinsurance_prem\n" + 
					") C\n" + 
					"left join rx_detail rd on rd.medicare_id = C.medicare_id\n" + 
					"group by C.plan_name, C.mbi, C.patientName, C.eligibleMonth, C.pcp_name, C.medicare_id, C.spec_cost, C.pcp_cap, C.reinsurance_prem\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("inst claim") && vm.getClaimType().contains("prof claim") && vm.getClaimType().contains("rx claim") && vm.getClaimType().contains("pcp cap") && vm.getClaimType().contains("reinsurance prem") && vm.getClaimType().size() == 5) {
			
			queryStr = "select D.*, round(( pcp_cap + reinsurance_prem + inst_claims + prof_claims + rx_claims),2) as total from (\n" + 
					"select C.*, COALESCE(round(sum(rd.paid_amount - lics_paid - rep_gap_dscnt),2),0) as rx_claims from  (\n" + 
					"select B.*, COALESCE(round(sum(pcd.paid_amount),2),0) as prof_claims from (\n" + 
					"select A.*, COALESCE(round(sum(icd.paid_amount),2),0) as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"' ' as spec_cost,\n" + 
					"COALESCE(round(sum(pcp_cap),2),0) as pcp_cap, COALESCE(round(count(*)*min(constant_val),2),0) as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					" left join inst_claim_detail icd on icd.medicare_id = A.medicare_id  \n" + 
					"group by A.plan_name, A.mbi, A.patientName, A.eligibleMonth, A.pcp_name, A.medicare_id, A.spec_cost, A.pcp_cap, A.reinsurance_prem\n" + 
					") B \n" + 
					"left join prof_claim_detail pcd on pcd.medicare_id = B.medicare_id\n" + 
					specialtyClaim+
					"group by B.plan_name, B.mbi, B.patientName, B.eligibleMonth, B.pcp_name, B.medicare_id, B.spec_cost, B.pcp_cap, B.reinsurance_prem\n" + 
					") C\n" + 
					"left join rx_detail rd on rd.medicare_id = C.medicare_id\n" + 
					"group by C.plan_name, C.mbi, C.patientName, C.eligibleMonth, C.pcp_name, C.medicare_id, C.spec_cost, C.pcp_cap, C.reinsurance_prem\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("inst claim") && vm.getClaimType().contains("prof claim") && vm.getClaimType().contains("rx claim") && vm.getClaimType().contains("pcp cap") && vm.getClaimType().size() == 4) {
			
			queryStr = "select D.*, round(( pcp_cap + inst_claims + prof_claims + rx_claims),2) as total from (\n" + 
					"select C.*, COALESCE(round(sum(rd.paid_amount - lics_paid - rep_gap_dscnt),2),0) as rx_claims from  (\n" + 
					"select B.*, COALESCE(round(sum(pcd.paid_amount),2),0) as prof_claims from (\n" + 
					"select A.*, COALESCE(round(sum(icd.paid_amount),2),0) as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"' ' as spec_cost,\n" + 
					"COALESCE(round(sum(pcp_cap),2),0) as pcp_cap, ' ' as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					" left join inst_claim_detail icd on icd.medicare_id = A.medicare_id  \n" + 
					"group by A.plan_name, A.mbi, A.patientName, A.eligibleMonth, A.pcp_name, A.medicare_id, A.spec_cost, A.pcp_cap, A.reinsurance_prem\n" + 
					") B \n" + 
					"left join prof_claim_detail pcd on pcd.medicare_id = B.medicare_id\n" +
					specialtyClaim+
					"group by B.plan_name, B.mbi, B.patientName, B.eligibleMonth, B.pcp_name, B.medicare_id, B.spec_cost, B.pcp_cap, B.reinsurance_prem\n" + 
					") C\n" + 
					"left join rx_detail rd on rd.medicare_id = C.medicare_id\n" + 
					"group by C.plan_name, C.mbi, C.patientName, C.eligibleMonth, C.pcp_name, C.medicare_id, C.spec_cost, C.pcp_cap, C.reinsurance_prem\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("inst claim") && vm.getClaimType().contains("prof claim") && vm.getClaimType().contains("rx claim") && vm.getClaimType().contains("spec claim") && vm.getClaimType().contains("reinsurance prem") && vm.getClaimType().size() == 5) {
			
			queryStr = "select D.*, round((spec_cost + reinsurance_prem + inst_claims + prof_claims + rx_claims),2) as total from (\n" + 
					"select C.*, COALESCE(round(sum(rd.paid_amount - lics_paid - rep_gap_dscnt),2),0) as rx_claims from  (\n" + 
					"select B.*, COALESCE(round(sum(pcd.paid_amount),2),0) as prof_claims from (\n" + 
					"select A.*, COALESCE(round(sum(icd.paid_amount),2),0) as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"COALESCE(round(sum(behavioral_health + chiropractic_cap + dental_cap + hearing_cap + lab + vision_ophthamalogy + vision_optometry + otc_cap + gym_cap + podiatry_cap + transportation + dermatology),2),0) as spec_cost,\n" + 
					"' ' as pcp_cap, COALESCE(round(count(*)*min(constant_val),2),0) as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					" left join inst_claim_detail icd on icd.medicare_id = A.medicare_id  \n" + 
					"group by A.plan_name, A.mbi, A.patientName, A.eligibleMonth, A.pcp_name, A.medicare_id, A.spec_cost, A.pcp_cap, A.reinsurance_prem\n" + 
					") B \n" + 
					"left join prof_claim_detail pcd on pcd.medicare_id = B.medicare_id\n" + 
					specialtyClaim+
					"group by B.plan_name, B.mbi, B.patientName, B.eligibleMonth, B.pcp_name, B.medicare_id, B.spec_cost, B.pcp_cap, B.reinsurance_prem\n" + 
					") C\n" + 
					"left join rx_detail rd on rd.medicare_id = C.medicare_id\n" + 
					"group by C.plan_name, C.mbi, C.patientName, C.eligibleMonth, C.pcp_name, C.medicare_id, C.spec_cost, C.pcp_cap, C.reinsurance_prem\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("inst claim") && vm.getClaimType().contains("prof claim") && vm.getClaimType().contains("rx claim") && vm.getClaimType().contains("spec claim") && vm.getClaimType().size() == 4) {
			
			queryStr = "select D.*, round((spec_cost + inst_claims + prof_claims + rx_claims),2) as total from (\n" + 
					"select C.*, COALESCE(round(sum(rd.paid_amount - lics_paid - rep_gap_dscnt),2),0) as rx_claims from  (\n" + 
					"select B.*, COALESCE(round(sum(pcd.paid_amount),2),0) as prof_claims from (\n" + 
					"select A.*, COALESCE(round(sum(icd.paid_amount),2),0) as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"COALESCE(round(sum(behavioral_health + chiropractic_cap + dental_cap + hearing_cap + lab + vision_ophthamalogy + vision_optometry + otc_cap + gym_cap + podiatry_cap + transportation + dermatology),2),0) as spec_cost,\n" + 
					"' ' as pcp_cap,' ' as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					" left join inst_claim_detail icd on icd.medicare_id = A.medicare_id  \n" + 
					"group by A.plan_name, A.mbi, A.patientName, A.eligibleMonth, A.pcp_name, A.medicare_id, A.spec_cost, A.pcp_cap, A.reinsurance_prem\n" + 
					") B \n" + 
					"left join prof_claim_detail pcd on pcd.medicare_id = B.medicare_id\n" + 
					specialtyClaim+
					"group by B.plan_name, B.mbi, B.patientName, B.eligibleMonth, B.pcp_name, B.medicare_id, B.spec_cost, B.pcp_cap, B.reinsurance_prem\n" + 
					") C\n" + 
					"left join rx_detail rd on rd.medicare_id = C.medicare_id\n" + 
					"group by C.plan_name, C.mbi, C.patientName, C.eligibleMonth, C.pcp_name, C.medicare_id, C.spec_cost, C.pcp_cap, C.reinsurance_prem\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("inst claim") && vm.getClaimType().contains("prof claim") && vm.getClaimType().contains("rx claim") && vm.getClaimType().contains("reinsurance prem") && vm.getClaimType().size() == 4) {
			
			queryStr = "select D.*, round((reinsurance_prem + inst_claims + prof_claims + rx_claims),2) as total from (\n" + 
					"select C.*, COALESCE(round(sum(rd.paid_amount - lics_paid - rep_gap_dscnt),2),0) as rx_claims from  (\n" + 
					"select B.*, COALESCE(round(sum(pcd.paid_amount),2),0) as prof_claims from (\n" + 
					"select A.*, COALESCE(round(sum(icd.paid_amount),2),0) as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"' ' as spec_cost,\n" + 
					"' ' as pcp_cap, COALESCE(round(count(*)*min(constant_val),2),0) as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					" left join inst_claim_detail icd on icd.medicare_id = A.medicare_id  \n" + 
					"group by A.plan_name, A.mbi, A.patientName, A.eligibleMonth, A.pcp_name, A.medicare_id, A.spec_cost, A.pcp_cap, A.reinsurance_prem\n" + 
					") B \n" + 
					"left join prof_claim_detail pcd on pcd.medicare_id = B.medicare_id\n" + 
					specialtyClaim+
					"group by B.plan_name, B.mbi, B.patientName, B.eligibleMonth, B.pcp_name, B.medicare_id, B.spec_cost, B.pcp_cap, B.reinsurance_prem\n" + 
					") C\n" + 
					"left join rx_detail rd on rd.medicare_id = C.medicare_id\n" + 
					"group by C.plan_name, C.mbi, C.patientName, C.eligibleMonth, C.pcp_name, C.medicare_id, C.spec_cost, C.pcp_cap, C.reinsurance_prem\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("inst claim") && vm.getClaimType().contains("prof claim") && vm.getClaimType().contains("rx claim") && vm.getClaimType().size() == 3) {
			
			queryStr = "select D.*, round((inst_claims + prof_claims + rx_claims),2) as total from (\n" + 
					"select C.*, COALESCE(round(sum(rd.paid_amount - lics_paid - rep_gap_dscnt),2),0) as rx_claims from  (\n" + 
					"select B.*, COALESCE(round(sum(pcd.paid_amount),2),0) as prof_claims from (\n" + 
					"select A.*, COALESCE(round(sum(icd.paid_amount),2),0) as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"' ' as spec_cost,\n" + 
					"' ' as pcp_cap, ' ' as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					" left join inst_claim_detail icd on icd.medicare_id = A.medicare_id  \n" + 
					"group by A.plan_name, A.mbi, A.patientName, A.eligibleMonth, A.pcp_name, A.medicare_id, A.spec_cost, A.pcp_cap, A.reinsurance_prem\n" + 
					") B \n" + 
					"left join prof_claim_detail pcd on pcd.medicare_id = B.medicare_id\n" + 
					specialtyClaim+
					"group by B.plan_name, B.mbi, B.patientName, B.eligibleMonth, B.pcp_name, B.medicare_id, B.spec_cost, B.pcp_cap, B.reinsurance_prem\n" + 
					") C\n" + 
					"left join rx_detail rd on rd.medicare_id = C.medicare_id\n" + 
					"group by C.plan_name, C.mbi, C.patientName, C.eligibleMonth, C.pcp_name, C.medicare_id, C.spec_cost, C.pcp_cap, C.reinsurance_prem\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("inst claim") && vm.getClaimType().contains("prof claim") && vm.getClaimType().contains("pcp cap") && vm.getClaimType().contains("spec claim") && vm.getClaimType().contains("reinsurance prem") && vm.getClaimType().size() == 5) {
			
			queryStr = "select D.*, round((spec_cost + pcp_cap + reinsurance_prem + inst_claims + prof_claims),2) as total from (\n" + 
					"select C.*, ' ' as rx_claims from  (\n" + 
					"select B.*, COALESCE(round(sum(pcd.paid_amount),2),0) as prof_claims from (\n" + 
					"select A.*, COALESCE(round(sum(icd.paid_amount),2),0) as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"COALESCE(round(sum(behavioral_health + chiropractic_cap + dental_cap + hearing_cap + lab + vision_ophthamalogy + vision_optometry + otc_cap + gym_cap + podiatry_cap + transportation + dermatology),2),0) as spec_cost,\n" + 
					"COALESCE(round(sum(pcp_cap),2),0) as pcp_cap, COALESCE(round(count(*)*min(constant_val),2),0) as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					" left join inst_claim_detail icd on icd.medicare_id = A.medicare_id  \n" + 
					"group by A.plan_name, A.mbi, A.patientName, A.eligibleMonth, A.pcp_name, A.medicare_id, A.spec_cost, A.pcp_cap, A.reinsurance_prem\n" + 
					") B \n" + 
					"left join prof_claim_detail pcd on pcd.medicare_id = B.medicare_id\n" + 
					specialtyClaim+
					"group by B.plan_name, B.mbi, B.patientName, B.eligibleMonth, B.pcp_name, B.medicare_id, B.spec_cost, B.pcp_cap, B.reinsurance_prem\n" + 
					") C\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("inst claim") && vm.getClaimType().contains("prof claim") && vm.getClaimType().contains("pcp cap") && vm.getClaimType().contains("spec claim") && vm.getClaimType().size() == 4) {
			
			queryStr = "select D.*, round((spec_cost + pcp_cap + inst_claims + prof_claims),2) as total from (\n" + 
					"select C.*, ' ' as rx_claims from  (\n" + 
					"select B.*, COALESCE(round(sum(pcd.paid_amount),2),0) as prof_claims from (\n" + 
					"select A.*, COALESCE(round(sum(icd.paid_amount),2),0) as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"COALESCE(round(sum(behavioral_health + chiropractic_cap + dental_cap + hearing_cap + lab + vision_ophthamalogy + vision_optometry + otc_cap + gym_cap + podiatry_cap + transportation + dermatology),2),0) as spec_cost,\n" + 
					"COALESCE(round(sum(pcp_cap),2),0) as pcp_cap, ' ' as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					" left join inst_claim_detail icd on icd.medicare_id = A.medicare_id  \n" + 
					"group by A.plan_name, A.mbi, A.patientName, A.eligibleMonth, A.pcp_name, A.medicare_id, A.spec_cost, A.pcp_cap, A.reinsurance_prem\n" + 
					") B \n" + 
					"left join prof_claim_detail pcd on pcd.medicare_id = B.medicare_id\n" + 
					specialtyClaim+
					"group by B.plan_name, B.mbi, B.patientName, B.eligibleMonth, B.pcp_name, B.medicare_id, B.spec_cost, B.pcp_cap, B.reinsurance_prem\n" + 
					") C\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("inst claim") && vm.getClaimType().contains("prof claim") && vm.getClaimType().contains("pcp cap") && vm.getClaimType().contains("reinsurance prem") && vm.getClaimType().size() == 4) {
			
			queryStr = "select D.*, round((pcp_cap + reinsurance_prem + inst_claims + prof_claims),2) as total from (\n" + 
					"select C.*, ' ' as rx_claims from  (\n" + 
					"select B.*, COALESCE(round(sum(pcd.paid_amount),2),0) as prof_claims from (\n" + 
					"select A.*, COALESCE(round(sum(icd.paid_amount),2),0) as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"' ' as spec_cost,\n" + 
					"COALESCE(round(sum(pcp_cap),2),0) as pcp_cap, COALESCE(round(count(*)*min(constant_val),2),0) as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					" left join inst_claim_detail icd on icd.medicare_id = A.medicare_id  \n" + 
					"group by A.plan_name, A.mbi, A.patientName, A.eligibleMonth, A.pcp_name, A.medicare_id, A.spec_cost, A.pcp_cap, A.reinsurance_prem\n" + 
					") B \n" + 
					"left join prof_claim_detail pcd on pcd.medicare_id = B.medicare_id\n" + 
					specialtyClaim+
					"group by B.plan_name, B.mbi, B.patientName, B.eligibleMonth, B.pcp_name, B.medicare_id, B.spec_cost, B.pcp_cap, B.reinsurance_prem\n" + 
					") C\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("inst claim") && vm.getClaimType().contains("prof claim") && vm.getClaimType().contains("pcp cap") && vm.getClaimType().size() == 3) {
			
			queryStr = "select D.*, round((pcp_cap + inst_claims + prof_claims),2) as total from (\n" + 
					"select C.*, ' ' as rx_claims from  (\n" + 
					"select B.*, COALESCE(round(sum(pcd.paid_amount),2),0) as prof_claims from (\n" + 
					"select A.*, COALESCE(round(sum(icd.paid_amount),2),0) as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"' ' as spec_cost,\n" + 
					"COALESCE(round(sum(pcp_cap),2),0) as pcp_cap, ' ' as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					" left join inst_claim_detail icd on icd.medicare_id = A.medicare_id  \n" + 
					"group by A.plan_name, A.mbi, A.patientName, A.eligibleMonth, A.pcp_name, A.medicare_id, A.spec_cost, A.pcp_cap, A.reinsurance_prem\n" + 
					") B \n" + 
					"left join prof_claim_detail pcd on pcd.medicare_id = B.medicare_id\n" + 
					specialtyClaim+
					"group by B.plan_name, B.mbi, B.patientName, B.eligibleMonth, B.pcp_name, B.medicare_id, B.spec_cost, B.pcp_cap, B.reinsurance_prem\n" + 
					") C\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("inst claim") && vm.getClaimType().contains("prof claim") && vm.getClaimType().contains("spec claim") && vm.getClaimType().contains("reinsurance prem") && vm.getClaimType().size() == 4) {
			
			queryStr = "select D.*, round((spec_cost + reinsurance_prem + inst_claims + prof_claims),2) as total from (\n" + 
					"select C.*, ' ' as rx_claims from  (\n" + 
					"select B.*, COALESCE(round(sum(pcd.paid_amount),2),0) as prof_claims from (\n" + 
					"select A.*, COALESCE(round(sum(icd.paid_amount),2),0) as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"COALESCE(round(sum(behavioral_health + chiropractic_cap + dental_cap + hearing_cap + lab + vision_ophthamalogy + vision_optometry + otc_cap + gym_cap + podiatry_cap + transportation + dermatology),2),0) as spec_cost,\n" + 
					"' ' as pcp_cap, COALESCE(round(count(*)*min(constant_val),2),0) as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					" left join inst_claim_detail icd on icd.medicare_id = A.medicare_id  \n" + 
					"group by A.plan_name, A.mbi, A.patientName, A.eligibleMonth, A.pcp_name, A.medicare_id, A.spec_cost, A.pcp_cap, A.reinsurance_prem\n" + 
					") B \n" + 
					"left join prof_claim_detail pcd on pcd.medicare_id = B.medicare_id\n" + 
					specialtyClaim+
					"group by B.plan_name, B.mbi, B.patientName, B.eligibleMonth, B.pcp_name, B.medicare_id, B.spec_cost, B.pcp_cap, B.reinsurance_prem\n" + 
					") C\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("inst claim") && vm.getClaimType().contains("prof claim") && vm.getClaimType().contains("spec claim") && vm.getClaimType().size() == 3) {
			
			queryStr = "select D.*, round((spec_cost + inst_claims + prof_claims),2) as total from (\n" + 
					"select C.*, ' ' as rx_claims from  (\n" + 
					"select B.*, COALESCE(round(sum(pcd.paid_amount),2),0) as prof_claims from (\n" + 
					"select A.*, COALESCE(round(sum(icd.paid_amount),2),0) as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"COALESCE(round(sum(behavioral_health + chiropractic_cap + dental_cap + hearing_cap + lab + vision_ophthamalogy + vision_optometry + otc_cap + gym_cap + podiatry_cap + transportation + dermatology),2),0) as spec_cost,\n" + 
					"' ' as pcp_cap, ' ' as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					" left join inst_claim_detail icd on icd.medicare_id = A.medicare_id  \n" + 
					"group by A.plan_name, A.mbi, A.patientName, A.eligibleMonth, A.pcp_name, A.medicare_id, A.spec_cost, A.pcp_cap, A.reinsurance_prem\n" + 
					") B \n" + 
					"left join prof_claim_detail pcd on pcd.medicare_id = B.medicare_id\n" + 
					specialtyClaim+
					"group by B.plan_name, B.mbi, B.patientName, B.eligibleMonth, B.pcp_name, B.medicare_id, B.spec_cost, B.pcp_cap, B.reinsurance_prem\n" + 
					") C\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("inst claim") && vm.getClaimType().contains("prof claim") && vm.getClaimType().contains("reinsurance prem") && vm.getClaimType().size() == 3) {
			
			queryStr = "select D.*, round((reinsurance_prem + inst_claims + prof_claims),2) as total from (\n" + 
					"select C.*, ' ' as rx_claims from  (\n" + 
					"select B.*, COALESCE(round(sum(pcd.paid_amount),2),0) as prof_claims from (\n" + 
					"select A.*, COALESCE(round(sum(icd.paid_amount),2),0) as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"' ' as spec_cost,\n" + 
					"' ' as pcp_cap, COALESCE(round(count(*)*min(constant_val),2),0) as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					" left join inst_claim_detail icd on icd.medicare_id = A.medicare_id  \n" + 
					"group by A.plan_name, A.mbi, A.patientName, A.eligibleMonth, A.pcp_name, A.medicare_id, A.spec_cost, A.pcp_cap, A.reinsurance_prem\n" + 
					") B \n" + 
					"left join prof_claim_detail pcd on pcd.medicare_id = B.medicare_id\n" + 
					specialtyClaim+
					"group by B.plan_name, B.mbi, B.patientName, B.eligibleMonth, B.pcp_name, B.medicare_id, B.spec_cost, B.pcp_cap, B.reinsurance_prem\n" + 
					") C\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("inst claim") && vm.getClaimType().contains("prof claim")  && vm.getClaimType().size() == 2) {
			
			queryStr = "select D.*, round((inst_claims + prof_claims),2) as total from (\n" + 
					"select C.*, ' ' as rx_claims from  (\n" + 
					"select B.*, COALESCE(round(sum(pcd.paid_amount),2),0) as prof_claims from (\n" + 
					"select A.*, COALESCE(round(sum(icd.paid_amount),2),0) as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"' ' as spec_cost,\n" + 
					"' ' as pcp_cap, ' ' as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					" left join inst_claim_detail icd on icd.medicare_id = A.medicare_id  \n" + 
					"group by A.plan_name, A.mbi, A.patientName, A.eligibleMonth, A.pcp_name, A.medicare_id, A.spec_cost, A.pcp_cap, A.reinsurance_prem\n" + 
					") B \n" + 
					"left join prof_claim_detail pcd on pcd.medicare_id = B.medicare_id\n" + 
					specialtyClaim+
					"group by B.plan_name, B.mbi, B.patientName, B.eligibleMonth, B.pcp_name, B.medicare_id, B.spec_cost, B.pcp_cap, B.reinsurance_prem\n" + 
					") C\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("inst claim") && vm.getClaimType().contains("rx claim") && vm.getClaimType().contains("pcp cap") && vm.getClaimType().contains("spec claim") && vm.getClaimType().contains("reinsurance prem") && vm.getClaimType().size() == 5) {
			
			queryStr = "select D.*, round((spec_cost + pcp_cap + reinsurance_prem + inst_claims + rx_claims),2) as total from (\n" + 
					"select C.*, COALESCE(round(sum(rd.paid_amount - lics_paid - rep_gap_dscnt),2),0) as rx_claims from  (\n" + 
					"select B.*, ' ' as prof_claims from (\n" + 
					"select A.*, COALESCE(round(sum(icd.paid_amount),2),0) as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"COALESCE(round(sum(behavioral_health + chiropractic_cap + dental_cap + hearing_cap + lab + vision_ophthamalogy + vision_optometry + otc_cap + gym_cap + podiatry_cap + transportation + dermatology),2),0) as spec_cost,\n" + 
					"COALESCE(round(sum(pcp_cap),2),0) as pcp_cap, COALESCE(round(count(*)*min(constant_val),2),0) as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					" left join inst_claim_detail icd on icd.medicare_id = A.medicare_id  \n" + 
					"group by A.plan_name, A.mbi, A.patientName, A.eligibleMonth, A.pcp_name, A.medicare_id, A.spec_cost, A.pcp_cap, A.reinsurance_prem\n" + 
					") B \n" + 
					") C\n" + 
					"left join rx_detail rd on rd.medicare_id = C.medicare_id\n" + 
					"group by C.plan_name, C.mbi, C.patientName, C.eligibleMonth, C.pcp_name, C.medicare_id, C.spec_cost, C.pcp_cap, C.reinsurance_prem\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("inst claim") && vm.getClaimType().contains("rx claim") && vm.getClaimType().contains("pcp cap") && vm.getClaimType().contains("spec claim") && vm.getClaimType().size() == 4) {
			
			queryStr = "select D.*, round((spec_cost + pcp_cap + inst_claims + rx_claims),2) as total from (\n" + 
					"select C.*, COALESCE(round(sum(rd.paid_amount - lics_paid - rep_gap_dscnt),2),0) as rx_claims from  (\n" + 
					"select B.*, ' ' as prof_claims from (\n" + 
					"select A.*, COALESCE(round(sum(icd.paid_amount),2),0) as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"COALESCE(round(sum(behavioral_health + chiropractic_cap + dental_cap + hearing_cap + lab + vision_ophthamalogy + vision_optometry + otc_cap + gym_cap + podiatry_cap + transportation + dermatology),2),0) as spec_cost,\n" + 
					"COALESCE(round(sum(pcp_cap),2),0) as pcp_cap, ' ' as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					" left join inst_claim_detail icd on icd.medicare_id = A.medicare_id  \n" + 
					"group by A.plan_name, A.mbi, A.patientName, A.eligibleMonth, A.pcp_name, A.medicare_id, A.spec_cost, A.pcp_cap, A.reinsurance_prem\n" + 
					") B \n" + 
					") C\n" + 
					"left join rx_detail rd on rd.medicare_id = C.medicare_id\n" + 
					"group by C.plan_name, C.mbi, C.patientName, C.eligibleMonth, C.pcp_name, C.medicare_id, C.spec_cost, C.pcp_cap, C.reinsurance_prem\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("inst claim") && vm.getClaimType().contains("rx claim") && vm.getClaimType().contains("pcp cap") && vm.getClaimType().contains("reinsurance prem") && vm.getClaimType().size() == 4) {
			
			queryStr = "select D.*, round((pcp_cap + reinsurance_prem + inst_claims + rx_claims),2) as total from (\n" + 
					"select C.*, COALESCE(round(sum(rd.paid_amount - lics_paid - rep_gap_dscnt),2),0) as rx_claims from  (\n" + 
					"select B.*, ' ' as prof_claims from (\n" + 
					"select A.*, COALESCE(round(sum(icd.paid_amount),2),0) as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"' ' as spec_cost,\n" + 
					"COALESCE(round(sum(pcp_cap),2),0) as pcp_cap, COALESCE(round(count(*)*min(constant_val),2),0) as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					" left join inst_claim_detail icd on icd.medicare_id = A.medicare_id  \n" + 
					"group by A.plan_name, A.mbi, A.patientName, A.eligibleMonth, A.pcp_name, A.medicare_id, A.spec_cost, A.pcp_cap, A.reinsurance_prem\n" + 
					") B \n" + 
					") C\n" + 
					"left join rx_detail rd on rd.medicare_id = C.medicare_id\n" + 
					"group by C.plan_name, C.mbi, C.patientName, C.eligibleMonth, C.pcp_name, C.medicare_id, C.spec_cost, C.pcp_cap, C.reinsurance_prem\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("inst claim") && vm.getClaimType().contains("rx claim") && vm.getClaimType().contains("pcp cap") && vm.getClaimType().size() == 3) {
			
			queryStr = "select D.*, round((pcp_cap + inst_claims + rx_claims),2) as total from (\n" + 
					"select C.*, COALESCE(round(sum(rd.paid_amount - lics_paid - rep_gap_dscnt),2),0) as rx_claims from  (\n" + 
					"select B.*, ' ' as prof_claims from (\n" + 
					"select A.*, COALESCE(round(sum(icd.paid_amount),2),0) as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"' ' as spec_cost,\n" + 
					"COALESCE(round(sum(pcp_cap),2),0) as pcp_cap, ' ' as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					" left join inst_claim_detail icd on icd.medicare_id = A.medicare_id  \n" + 
					"group by A.plan_name, A.mbi, A.patientName, A.eligibleMonth, A.pcp_name, A.medicare_id, A.spec_cost, A.pcp_cap, A.reinsurance_prem\n" + 
					") B \n" + 
					") C\n" + 
					"left join rx_detail rd on rd.medicare_id = C.medicare_id\n" + 
					"group by C.plan_name, C.mbi, C.patientName, C.eligibleMonth, C.pcp_name, C.medicare_id, C.spec_cost, C.pcp_cap, C.reinsurance_prem\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("inst claim") && vm.getClaimType().contains("rx claim") && vm.getClaimType().contains("spec claim") && vm.getClaimType().contains("reinsurance prem") && vm.getClaimType().size() == 4) {
			
			queryStr = "select D.*, round((spec_cost + reinsurance_prem + inst_claims + rx_claims),2) as total from (\n" + 
					"select C.*, COALESCE(round(sum(rd.paid_amount - lics_paid - rep_gap_dscnt),2),0) as rx_claims from  (\n" + 
					"select B.*, ' ' as prof_claims from (\n" + 
					"select A.*, COALESCE(round(sum(icd.paid_amount),2),0) as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"COALESCE(round(sum(behavioral_health + chiropractic_cap + dental_cap + hearing_cap + lab + vision_ophthamalogy + vision_optometry + otc_cap + gym_cap + podiatry_cap + transportation + dermatology),2),0) as spec_cost,\n" + 
					"' ' as pcp_cap, COALESCE(round(count(*)*min(constant_val),2),0) as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					" left join inst_claim_detail icd on icd.medicare_id = A.medicare_id  \n" + 
					"group by A.plan_name, A.mbi, A.patientName, A.eligibleMonth, A.pcp_name, A.medicare_id, A.spec_cost, A.pcp_cap, A.reinsurance_prem\n" + 
					") B \n" + 
					") C\n" + 
					"left join rx_detail rd on rd.medicare_id = C.medicare_id\n" + 
					"group by C.plan_name, C.mbi, C.patientName, C.eligibleMonth, C.pcp_name, C.medicare_id, C.spec_cost, C.pcp_cap, C.reinsurance_prem\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("inst claim") && vm.getClaimType().contains("rx claim") && vm.getClaimType().contains("spec claim") && vm.getClaimType().size() == 3) {
			
			queryStr = "select D.*, round((spec_cost + inst_claims + rx_claims),2) as total from (\n" + 
					"select C.*, COALESCE(round(sum(rd.paid_amount - lics_paid - rep_gap_dscnt),2),0) as rx_claims from  (\n" + 
					"select B.*, ' ' as prof_claims from (\n" + 
					"select A.*, COALESCE(round(sum(icd.paid_amount),2),0) as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"COALESCE(round(sum(behavioral_health + chiropractic_cap + dental_cap + hearing_cap + lab + vision_ophthamalogy + vision_optometry + otc_cap + gym_cap + podiatry_cap + transportation + dermatology),2),0) as spec_cost,\n" + 
					"' ' as pcp_cap, ' ' as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					" left join inst_claim_detail icd on icd.medicare_id = A.medicare_id  \n" + 
					"group by A.plan_name, A.mbi, A.patientName, A.eligibleMonth, A.pcp_name, A.medicare_id, A.spec_cost, A.pcp_cap, A.reinsurance_prem\n" + 
					") B \n" + 
					") C\n" + 
					"left join rx_detail rd on rd.medicare_id = C.medicare_id\n" + 
					"group by C.plan_name, C.mbi, C.patientName, C.eligibleMonth, C.pcp_name, C.medicare_id, C.spec_cost, C.pcp_cap, C.reinsurance_prem\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("inst claim") && vm.getClaimType().contains("rx claim") && vm.getClaimType().contains("reinsurance prem") && vm.getClaimType().size() == 3) {
			
			queryStr = "select D.*, round((reinsurance_prem + inst_claims + rx_claims),2) as total from (\n" + 
					"select C.*, COALESCE(round(sum(rd.paid_amount - lics_paid - rep_gap_dscnt),2),0) as rx_claims from  (\n" + 
					"select B.*, ' ' as prof_claims from (\n" + 
					"select A.*, COALESCE(round(sum(icd.paid_amount),2),0) as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"' ' as spec_cost,\n" + 
					"' ' as pcp_cap, COALESCE(round(count(*)*min(constant_val),2),0) as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					" left join inst_claim_detail icd on icd.medicare_id = A.medicare_id  \n" + 
					"group by A.plan_name, A.mbi, A.patientName, A.eligibleMonth, A.pcp_name, A.medicare_id, A.spec_cost, A.pcp_cap, A.reinsurance_prem\n" + 
					") B \n" + 
					") C\n" + 
					"left join rx_detail rd on rd.medicare_id = C.medicare_id\n" + 
					"group by C.plan_name, C.mbi, C.patientName, C.eligibleMonth, C.pcp_name, C.medicare_id, C.spec_cost, C.pcp_cap, C.reinsurance_prem\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("inst claim") && vm.getClaimType().contains("rx claim") && vm.getClaimType().size() == 2) {
			
			queryStr = "select D.*, round((inst_claims + rx_claims),2) as total from (\n" + 
					"select C.*, COALESCE(round(sum(rd.paid_amount - lics_paid - rep_gap_dscnt),2),0) as rx_claims from  (\n" + 
					"select B.*, ' ' as prof_claims from (\n" + 
					"select A.*, COALESCE(round(sum(icd.paid_amount),2),0) as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"' ' as spec_cost,\n" + 
					"' ' as pcp_cap, '' as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					" left join inst_claim_detail icd on icd.medicare_id = A.medicare_id  \n" + 
					"group by A.plan_name, A.mbi, A.patientName, A.eligibleMonth, A.pcp_name, A.medicare_id, A.spec_cost, A.pcp_cap, A.reinsurance_prem\n" + 
					") B \n" + 
					") C\n" + 
					"left join rx_detail rd on rd.medicare_id = C.medicare_id\n" + 
					"group by C.plan_name, C.mbi, C.patientName, C.eligibleMonth, C.pcp_name, C.medicare_id, C.spec_cost, C.pcp_cap, C.reinsurance_prem\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("inst claim") && vm.getClaimType().contains("pcp cap") && vm.getClaimType().contains("spec claim") && vm.getClaimType().contains("reinsurance prem") && vm.getClaimType().size() == 4) {
			
			queryStr = "select D.*, round((spec_cost + pcp_cap + reinsurance_prem + inst_claims),2) as total from (\n" + 
					"select C.*, ' ' as rx_claims from  (\n" + 
					"select B.*, ' ' as prof_claims from (\n" + 
					"select A.*, COALESCE(round(sum(icd.paid_amount),2),0) as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"COALESCE(round(sum(behavioral_health + chiropractic_cap + dental_cap + hearing_cap + lab + vision_ophthamalogy + vision_optometry + otc_cap + gym_cap + podiatry_cap + transportation + dermatology),2),0) as spec_cost,\n" + 
					"COALESCE(round(sum(pcp_cap),2),0) as pcp_cap, COALESCE(round(count(*)*min(constant_val),2),0) as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					" left join inst_claim_detail icd on icd.medicare_id = A.medicare_id  \n" + 
					"group by A.plan_name, A.mbi, A.patientName, A.eligibleMonth, A.pcp_name, A.medicare_id, A.spec_cost, A.pcp_cap, A.reinsurance_prem\n" + 
					") B \n" + 
					") C\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("inst claim") && vm.getClaimType().contains("pcp cap") && vm.getClaimType().contains("spec claim") && vm.getClaimType().size() == 3) {
			
			queryStr = "select D.*, round((spec_cost + pcp_cap + inst_claims),2) as total from (\n" + 
					"select C.*, ' ' as rx_claims from  (\n" + 
					"select B.*, ' ' as prof_claims from (\n" + 
					"select A.*, COALESCE(round(sum(icd.paid_amount),2),0) as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"COALESCE(round(sum(behavioral_health + chiropractic_cap + dental_cap + hearing_cap + lab + vision_ophthamalogy + vision_optometry + otc_cap + gym_cap + podiatry_cap + transportation + dermatology),2),0) as spec_cost,\n" + 
					"COALESCE(round(sum(pcp_cap),2),0) as pcp_cap, ' ' as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					" left join inst_claim_detail icd on icd.medicare_id = A.medicare_id  \n" + 
					"group by A.plan_name, A.mbi, A.patientName, A.eligibleMonth, A.pcp_name, A.medicare_id, A.spec_cost, A.pcp_cap, A.reinsurance_prem\n" + 
					") B \n" + 
					") C\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("inst claim") && vm.getClaimType().contains("pcp cap") && vm.getClaimType().contains("reinsurance prem") && vm.getClaimType().size() == 3) {
			
			queryStr = "select D.*, round((pcp_cap + reinsurance_prem + inst_claims),2) as total from (\n" + 
					"select C.*, ' ' as rx_claims from  (\n" + 
					"select B.*, ' ' as prof_claims from (\n" + 
					"select A.*, COALESCE(round(sum(icd.paid_amount),2),0) as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"' ' as spec_cost,\n" + 
					"COALESCE(round(sum(pcp_cap),2),0) as pcp_cap, COALESCE(round(count(*)*min(constant_val),2),0) as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					" left join inst_claim_detail icd on icd.medicare_id = A.medicare_id  \n" + 
					"group by A.plan_name, A.mbi, A.patientName, A.eligibleMonth, A.pcp_name, A.medicare_id, A.spec_cost, A.pcp_cap, A.reinsurance_prem\n" + 
					") B \n" + 
					") C\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("inst claim") && vm.getClaimType().contains("pcp cap") && vm.getClaimType().size() == 2) {
			
			queryStr = "select D.*, round((pcp_cap + inst_claims),2) as total from (\n" + 
					"select C.*, ' ' as rx_claims from  (\n" + 
					"select B.*, ' ' as prof_claims from (\n" + 
					"select A.*, COALESCE(round(sum(icd.paid_amount),2),0) as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"' ' as spec_cost,\n" + 
					"COALESCE(round(sum(pcp_cap),2),0) as pcp_cap, ' ' as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					" left join inst_claim_detail icd on icd.medicare_id = A.medicare_id  \n" + 
					"group by A.plan_name, A.mbi, A.patientName, A.eligibleMonth, A.pcp_name, A.medicare_id, A.spec_cost, A.pcp_cap, A.reinsurance_prem\n" + 
					") B \n" + 
					") C\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("inst claim") && vm.getClaimType().contains("spec claim") && vm.getClaimType().contains("reinsurance prem") && vm.getClaimType().size() == 3) {
			
			queryStr = "select D.*, round((spec_cost + reinsurance_prem + inst_claims),2) as total from (\n" + 
					"select C.*, ' ' as rx_claims from  (\n" + 
					"select B.*, ' ' as prof_claims from (\n" + 
					"select A.*, COALESCE(round(sum(icd.paid_amount),2),0) as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"COALESCE(round(sum(behavioral_health + chiropractic_cap + dental_cap + hearing_cap + lab + vision_ophthamalogy + vision_optometry + otc_cap + gym_cap + podiatry_cap + transportation + dermatology),2),0) as spec_cost,\n" + 
					"' ' as pcp_cap, COALESCE(round(count(*)*min(constant_val),2),0) as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					" left join inst_claim_detail icd on icd.medicare_id = A.medicare_id  \n" + 
					"group by A.plan_name, A.mbi, A.patientName, A.eligibleMonth, A.pcp_name, A.medicare_id, A.spec_cost, A.pcp_cap, A.reinsurance_prem\n" + 
					") B \n" + 
					") C\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("inst claim") && vm.getClaimType().contains("spec claim") && vm.getClaimType().size() == 2) {
			
			queryStr = "select D.*, round((spec_cost + inst_claims),2) as total from (\n" + 
					"select C.*, ' ' as rx_claims from  (\n" + 
					"select B.*, ' ' as prof_claims from (\n" + 
					"select A.*, COALESCE(round(sum(icd.paid_amount),2),0) as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"COALESCE(round(sum(behavioral_health + chiropractic_cap + dental_cap + hearing_cap + lab + vision_ophthamalogy + vision_optometry + otc_cap + gym_cap + podiatry_cap + transportation + dermatology),2),0) as spec_cost,\n" + 
					"' ' as pcp_cap, ' ' as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					" left join inst_claim_detail icd on icd.medicare_id = A.medicare_id  \n" + 
					"group by A.plan_name, A.mbi, A.patientName, A.eligibleMonth, A.pcp_name, A.medicare_id, A.spec_cost, A.pcp_cap, A.reinsurance_prem\n" + 
					") B \n" + 
					") C\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("inst claim") && vm.getClaimType().contains("reinsurance prem") && vm.getClaimType().size() == 2) {
		
			queryStr = "select D.*, round((reinsurance_prem + inst_claims),2) as total from (\n" + 
					"select C.*, ' ' as rx_claims from  (\n" + 
					"select B.*, ' ' as prof_claims from (\n" + 
					"select A.*, COALESCE(round(sum(icd.paid_amount),2),0) as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"' ' as spec_cost,\n" + 
					"' ' as pcp_cap, COALESCE(round(count(*)*min(constant_val),2),0) as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					" left join inst_claim_detail icd on icd.medicare_id = A.medicare_id  \n" + 
					"group by A.plan_name, A.mbi, A.patientName, A.eligibleMonth, A.pcp_name, A.medicare_id, A.spec_cost, A.pcp_cap, A.reinsurance_prem\n" + 
					") B \n" + 
					") C\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("inst claim") && vm.getClaimType().size() == 1) {
			
			queryStr = "select D.*, round((inst_claims),2) as total from (\n" + 
					"select C.*, ' ' as rx_claims from  (\n" + 
					"select B.*, ' ' as prof_claims from (\n" + 
					"select A.*, COALESCE(round(sum(icd.paid_amount),2),0) as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"' ' as spec_cost,\n" + 
					"' ' as pcp_cap, ' ' as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					" left join inst_claim_detail icd on icd.medicare_id = A.medicare_id  \n" + 
					"group by A.plan_name, A.mbi, A.patientName, A.eligibleMonth, A.pcp_name, A.medicare_id, A.spec_cost, A.pcp_cap, A.reinsurance_prem\n" + 
					") B \n" + 
					") C\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("prof claim") && vm.getClaimType().contains("rx claim") && vm.getClaimType().contains("pcp cap") && vm.getClaimType().contains("spec claim") && vm.getClaimType().contains("reinsurance prem") && vm.getClaimType().size() == 5) {
			
			queryStr = "select D.*, round((spec_cost + pcp_cap + reinsurance_prem + prof_claims + rx_claims),2) as total from (\n" + 
					"select C.*, COALESCE(round(sum(rd.paid_amount - lics_paid - rep_gap_dscnt),2),0) as rx_claims from  (\n" + 
					"select B.*, COALESCE(round(sum(pcd.paid_amount),2),0) as prof_claims from (\n" + 
					"select A.*, ' ' as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"COALESCE(round(sum(behavioral_health + chiropractic_cap + dental_cap + hearing_cap + lab + vision_ophthamalogy + vision_optometry + otc_cap + gym_cap + podiatry_cap + transportation + dermatology),2),0) as spec_cost,\n" + 
					"COALESCE(round(sum(pcp_cap),2),0) as pcp_cap, COALESCE(round(count(*)*min(constant_val),2),0) as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					") B \n" + 
					"left join prof_claim_detail pcd on pcd.medicare_id = B.medicare_id\n" + 
					specialtyClaim+
					"group by B.plan_name, B.mbi, B.patientName, B.eligibleMonth, B.pcp_name, B.medicare_id, B.spec_cost, B.pcp_cap, B.reinsurance_prem\n" + 
					") C\n" + 
					"left join rx_detail rd on rd.medicare_id = C.medicare_id\n" + 
					"group by C.plan_name, C.mbi, C.patientName, C.eligibleMonth, C.pcp_name, C.medicare_id, C.spec_cost, C.pcp_cap, C.reinsurance_prem\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("prof claim") && vm.getClaimType().contains("rx claim") && vm.getClaimType().contains("pcp cap") && vm.getClaimType().contains("spec claim") && vm.getClaimType().size() == 4) {
			
			queryStr = "select D.*, round((spec_cost + pcp_cap + prof_claims + rx_claims),2) as total from (\n" + 
					"select C.*, COALESCE(round(sum(rd.paid_amount - lics_paid - rep_gap_dscnt),2),0) as rx_claims from  (\n" + 
					"select B.*, COALESCE(round(sum(pcd.paid_amount),2),0) as prof_claims from (\n" + 
					"select A.*, ' ' as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"COALESCE(round(sum(behavioral_health + chiropractic_cap + dental_cap + hearing_cap + lab + vision_ophthamalogy + vision_optometry + otc_cap + gym_cap + podiatry_cap + transportation + dermatology),2),0) as spec_cost,\n" + 
					"COALESCE(round(sum(pcp_cap),2),0) as pcp_cap, ' ' as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					") B \n" + 
					"left join prof_claim_detail pcd on pcd.medicare_id = B.medicare_id\n" + 
					specialtyClaim+
					"group by B.plan_name, B.mbi, B.patientName, B.eligibleMonth, B.pcp_name, B.medicare_id, B.spec_cost, B.pcp_cap, B.reinsurance_prem\n" + 
					") C\n" + 
					"left join rx_detail rd on rd.medicare_id = C.medicare_id\n" + 
					"group by C.plan_name, C.mbi, C.patientName, C.eligibleMonth, C.pcp_name, C.medicare_id, C.spec_cost, C.pcp_cap, C.reinsurance_prem\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("prof claim") && vm.getClaimType().contains("rx claim") && vm.getClaimType().contains("pcp cap") && vm.getClaimType().contains("reinsurance prem") && vm.getClaimType().size() == 4) {
			
			queryStr = "select D.*, round((pcp_cap + reinsurance_prem + prof_claims + rx_claims),2) as total from (\n" + 
					"select C.*, COALESCE(round(sum(rd.paid_amount - lics_paid - rep_gap_dscnt),2),0) as rx_claims from  (\n" + 
					"select B.*, COALESCE(round(sum(pcd.paid_amount),2),0) as prof_claims from (\n" + 
					"select A.*, ' ' as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"' ' as spec_cost,\n" + 
					"COALESCE(round(sum(pcp_cap),2),0) as pcp_cap, COALESCE(round(count(*)*min(constant_val),2),0) as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					") B \n" + 
					"left join prof_claim_detail pcd on pcd.medicare_id = B.medicare_id\n" +
					specialtyClaim+
					"group by B.plan_name, B.mbi, B.patientName, B.eligibleMonth, B.pcp_name, B.medicare_id, B.spec_cost, B.pcp_cap, B.reinsurance_prem\n" + 
					") C\n" + 
					"left join rx_detail rd on rd.medicare_id = C.medicare_id\n" + 
					"group by C.plan_name, C.mbi, C.patientName, C.eligibleMonth, C.pcp_name, C.medicare_id, C.spec_cost, C.pcp_cap, C.reinsurance_prem\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("prof claim") && vm.getClaimType().contains("rx claim") && vm.getClaimType().contains("pcp cap") && vm.getClaimType().size() == 3) {
			
			queryStr = "select D.*, round((pcp_cap + prof_claims + rx_claims),2) as total from (\n" + 
					"select C.*, COALESCE(round(sum(rd.paid_amount - lics_paid - rep_gap_dscnt),2),0) as rx_claims from  (\n" + 
					"select B.*, COALESCE(round(sum(pcd.paid_amount),2),0) as prof_claims from (\n" + 
					"select A.*, ' ' as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"' ' as spec_cost,\n" + 
					"COALESCE(round(sum(pcp_cap),2),0) as pcp_cap, ' ' as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					") B \n" + 
					"left join prof_claim_detail pcd on pcd.medicare_id = B.medicare_id\n" + 
					specialtyClaim+
					"group by B.plan_name, B.mbi, B.patientName, B.eligibleMonth, B.pcp_name, B.medicare_id, B.spec_cost, B.pcp_cap, B.reinsurance_prem\n" + 
					") C\n" + 
					"left join rx_detail rd on rd.medicare_id = C.medicare_id\n" + 
					"group by C.plan_name, C.mbi, C.patientName, C.eligibleMonth, C.pcp_name, C.medicare_id, C.spec_cost, C.pcp_cap, C.reinsurance_prem\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("prof claim") && vm.getClaimType().contains("rx claim") && vm.getClaimType().contains("spec claim") && vm.getClaimType().contains("reinsurance prem") && vm.getClaimType().size() == 4) {
			
			queryStr = "select D.*, round((spec_cost + reinsurance_prem + prof_claims + rx_claims),2) as total from (\n" + 
					"select C.*, COALESCE(round(sum(rd.paid_amount - lics_paid - rep_gap_dscnt),2),0) as rx_claims from  (\n" + 
					"select B.*, COALESCE(round(sum(pcd.paid_amount),2),0) as prof_claims from (\n" + 
					"select A.*, ' ' as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"COALESCE(round(sum(behavioral_health + chiropractic_cap + dental_cap + hearing_cap + lab + vision_ophthamalogy + vision_optometry + otc_cap + gym_cap + podiatry_cap + transportation + dermatology),2),0) as spec_cost,\n" + 
					"' ' as pcp_cap, COALESCE(round(count(*)*min(constant_val),2),0) as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					") B \n" + 
					"left join prof_claim_detail pcd on pcd.medicare_id = B.medicare_id\n" + 
					specialtyClaim+
					"group by B.plan_name, B.mbi, B.patientName, B.eligibleMonth, B.pcp_name, B.medicare_id, B.spec_cost, B.pcp_cap, B.reinsurance_prem\n" + 
					") C\n" + 
					"left join rx_detail rd on rd.medicare_id = C.medicare_id\n" + 
					"group by C.plan_name, C.mbi, C.patientName, C.eligibleMonth, C.pcp_name, C.medicare_id, C.spec_cost, C.pcp_cap, C.reinsurance_prem\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("prof claim") && vm.getClaimType().contains("rx claim") && vm.getClaimType().contains("spec claim") && vm.getClaimType().size() == 3) {
			
			queryStr = "select D.*, round((spec_cost + prof_claims + rx_claims),2) as total from (\n" + 
					"select C.*, COALESCE(round(sum(rd.paid_amount - lics_paid - rep_gap_dscnt),2),0) as rx_claims from  (\n" + 
					"select B.*, COALESCE(round(sum(pcd.paid_amount),2),0) as prof_claims from (\n" + 
					"select A.*, ' ' as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"COALESCE(round(sum(behavioral_health + chiropractic_cap + dental_cap + hearing_cap + lab + vision_ophthamalogy + vision_optometry + otc_cap + gym_cap + podiatry_cap + transportation + dermatology),2),0) as spec_cost,\n" + 
					"' ' as pcp_cap, ' ' as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					") B \n" + 
					"left join prof_claim_detail pcd on pcd.medicare_id = B.medicare_id\n" + 
					specialtyClaim+
					"group by B.plan_name, B.mbi, B.patientName, B.eligibleMonth, B.pcp_name, B.medicare_id, B.spec_cost, B.pcp_cap, B.reinsurance_prem\n" + 
					") C\n" + 
					"left join rx_detail rd on rd.medicare_id = C.medicare_id\n" + 
					"group by C.plan_name, C.mbi, C.patientName, C.eligibleMonth, C.pcp_name, C.medicare_id, C.spec_cost, C.pcp_cap, C.reinsurance_prem\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("prof claim") && vm.getClaimType().contains("rx claim") && vm.getClaimType().contains("reinsurance prem") && vm.getClaimType().size() == 3) {
			
			queryStr = "select D.*, round((spec_cost + pcp_cap + reinsurance_prem + prof_claims + rx_claims),2) as total from (\n" + 
					"select C.*, COALESCE(round(sum(rd.paid_amount - lics_paid - rep_gap_dscnt),2),0) as rx_claims from  (\n" + 
					"select B.*, COALESCE(round(sum(pcd.paid_amount),2),0) as prof_claims from (\n" + 
					"select A.*, ' ' as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"' ' as spec_cost,\n" + 
					"' ' as pcp_cap, COALESCE(round(count(*)*min(constant_val),2),0) as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					") B \n" + 
					"left join prof_claim_detail pcd on pcd.medicare_id = B.medicare_id\n" + 
					specialtyClaim+
					"group by B.plan_name, B.mbi, B.patientName, B.eligibleMonth, B.pcp_name, B.medicare_id, B.spec_cost, B.pcp_cap, B.reinsurance_prem\n" + 
					") C\n" + 
					"left join rx_detail rd on rd.medicare_id = C.medicare_id\n" + 
					"group by C.plan_name, C.mbi, C.patientName, C.eligibleMonth, C.pcp_name, C.medicare_id, C.spec_cost, C.pcp_cap, C.reinsurance_prem\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("prof claim") && vm.getClaimType().contains("rx claim") && vm.getClaimType().size() == 2) {
			
			queryStr = "select D.*, round((prof_claims + rx_claims),2) as total from (\n" + 
					"select C.*, COALESCE(round(sum(rd.paid_amount - lics_paid - rep_gap_dscnt),2),0) as rx_claims from  (\n" + 
					"select B.*, COALESCE(round(sum(pcd.paid_amount),2),0) as prof_claims from (\n" + 
					"select A.*, ' ' as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"' ' as spec_cost,\n" + 
					"' ' as pcp_cap, ' ' as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					") B \n" + 
					"left join prof_claim_detail pcd on pcd.medicare_id = B.medicare_id\n" + 
					specialtyClaim+
					"group by B.plan_name, B.mbi, B.patientName, B.eligibleMonth, B.pcp_name, B.medicare_id, B.spec_cost, B.pcp_cap, B.reinsurance_prem\n" + 
					") C\n" + 
					"left join rx_detail rd on rd.medicare_id = C.medicare_id\n" + 
					"group by C.plan_name, C.mbi, C.patientName, C.eligibleMonth, C.pcp_name, C.medicare_id, C.spec_cost, C.pcp_cap, C.reinsurance_prem\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("prof claim") && vm.getClaimType().contains("pcp cap") && vm.getClaimType().contains("spec claim") && vm.getClaimType().contains("reinsurance prem") && vm.getClaimType().size() == 4) {
			
			queryStr = "select D.*, round((spec_cost + pcp_cap + reinsurance_prem + prof_claims),2) as total from (\n" + 
					"select C.*, ' ' as rx_claims from  (\n" + 
					"select B.*, COALESCE(round(sum(pcd.paid_amount),2),0) as prof_claims from (\n" + 
					"select A.*, ' ' as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"COALESCE(round(sum(behavioral_health + chiropractic_cap + dental_cap + hearing_cap + lab + vision_ophthamalogy + vision_optometry + otc_cap + gym_cap + podiatry_cap + transportation + dermatology),2),0) as spec_cost,\n" + 
					"COALESCE(round(sum(pcp_cap),2),0) as pcp_cap, COALESCE(round(count(*)*min(constant_val),2),0) as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					") B \n" + 
					"left join prof_claim_detail pcd on pcd.medicare_id = B.medicare_id\n" + 
					specialtyClaim+
					"group by B.plan_name, B.mbi, B.patientName, B.eligibleMonth, B.pcp_name, B.medicare_id, B.spec_cost, B.pcp_cap, B.reinsurance_prem\n" + 
					") C\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("prof claim") && vm.getClaimType().contains("pcp cap") && vm.getClaimType().contains("spec claim") && vm.getClaimType().size() == 3) {
			
			queryStr = "select D.*, round((spec_cost + pcp_cap + prof_claims),2) as total from (\n" + 
					"select C.*, ' ' as rx_claims from  (\n" + 
					"select B.*, COALESCE(round(sum(pcd.paid_amount),2),0) as prof_claims from (\n" + 
					"select A.*, ' ' as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"COALESCE(round(sum(behavioral_health + chiropractic_cap + dental_cap + hearing_cap + lab + vision_ophthamalogy + vision_optometry + otc_cap + gym_cap + podiatry_cap + transportation + dermatology),2),0) as spec_cost,\n" + 
					"COALESCE(round(sum(pcp_cap),2),0) as pcp_cap, ' ' as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					") B \n" + 
					"left join prof_claim_detail pcd on pcd.medicare_id = B.medicare_id\n" + 
					specialtyClaim+
					"group by B.plan_name, B.mbi, B.patientName, B.eligibleMonth, B.pcp_name, B.medicare_id, B.spec_cost, B.pcp_cap, B.reinsurance_prem\n" + 
					") C\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("prof claim") && vm.getClaimType().contains("pcp cap") && vm.getClaimType().contains("reinsurance prem") && vm.getClaimType().size() == 3) {
			
			queryStr = "select D.*, round((pcp_cap + reinsurance_prem + prof_claims),2) as total from (\n" + 
					"select C.*, ' ' as rx_claims from  (\n" + 
					"select B.*, COALESCE(round(sum(pcd.paid_amount),2),0) as prof_claims from (\n" + 
					"select A.*, ' ' as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"' ' as spec_cost,\n" + 
					"COALESCE(round(sum(pcp_cap),2),0) as pcp_cap, COALESCE(round(count(*)*min(constant_val),2),0) as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					") B \n" + 
					"left join prof_claim_detail pcd on pcd.medicare_id = B.medicare_id\n" + 
					specialtyClaim+
					"group by B.plan_name, B.mbi, B.patientName, B.eligibleMonth, B.pcp_name, B.medicare_id, B.spec_cost, B.pcp_cap, B.reinsurance_prem\n" + 
					") C\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("prof claim") && vm.getClaimType().contains("pcp cap") && vm.getClaimType().size() == 2) {
			
			queryStr = "select D.*, round((pcp_cap + prof_claims),2) as total from (\n" + 
					"select C.*, ' ' as rx_claims from  (\n" + 
					"select B.*, COALESCE(round(sum(pcd.paid_amount),2),0) as prof_claims from (\n" + 
					"select A.*, ' ' as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"' ' as spec_cost,\n" + 
					"COALESCE(round(sum(pcp_cap),2),0) as pcp_cap, ' ' as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					") B \n" + 
					"left join prof_claim_detail pcd on pcd.medicare_id = B.medicare_id\n" + 
					specialtyClaim+
					"group by B.plan_name, B.mbi, B.patientName, B.eligibleMonth, B.pcp_name, B.medicare_id, B.spec_cost, B.pcp_cap, B.reinsurance_prem\n" + 
					") C\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("prof claim") && vm.getClaimType().contains("spec claim") && vm.getClaimType().contains("reinsurance prem") && vm.getClaimType().size() == 3) {
		
			queryStr = "select D.*, round((spec_cost + reinsurance_prem + prof_claims),2) as total from (\n" + 
					"select C.*, ' ' as rx_claims from  (\n" + 
					"select B.*, COALESCE(round(sum(pcd.paid_amount),2),0) as prof_claims from (\n" + 
					"select A.*, ' ' as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"COALESCE(round(sum(behavioral_health + chiropractic_cap + dental_cap + hearing_cap + lab + vision_ophthamalogy + vision_optometry + otc_cap + gym_cap + podiatry_cap + transportation + dermatology),2),0) as spec_cost,\n" + 
					"' ' as pcp_cap, COALESCE(round(count(*)*min(constant_val),2),0) as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					") B \n" + 
					"left join prof_claim_detail pcd on pcd.medicare_id = B.medicare_id\n" + 
					specialtyClaim+
					"group by B.plan_name, B.mbi, B.patientName, B.eligibleMonth, B.pcp_name, B.medicare_id, B.spec_cost, B.pcp_cap, B.reinsurance_prem\n" + 
					") C\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("prof claim") && vm.getClaimType().contains("spec claim") && vm.getClaimType().size() == 2) {
			
			queryStr = "select D.*, round((spec_cost + prof_claims),2) as total from (\n" + 
					"select C.*, ' ' as rx_claims from  (\n" + 
					"select B.*, COALESCE(round(sum(pcd.paid_amount),2),0) as prof_claims from (\n" + 
					"select A.*, ' ' as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"COALESCE(round(sum(behavioral_health + chiropractic_cap + dental_cap + hearing_cap + lab + vision_ophthamalogy + vision_optometry + otc_cap + gym_cap + podiatry_cap + transportation + dermatology),2),0) as spec_cost,\n" + 
					"' ' as pcp_cap, ' ' as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					") B \n" + 
					"left join prof_claim_detail pcd on pcd.medicare_id = B.medicare_id\n" + 
					specialtyClaim+
					"group by B.plan_name, B.mbi, B.patientName, B.eligibleMonth, B.pcp_name, B.medicare_id, B.spec_cost, B.pcp_cap, B.reinsurance_prem\n" + 
					") C\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("prof claim") && vm.getClaimType().contains("reinsurance prem") && vm.getClaimType().size() == 2) {
			
			queryStr = "select D.*, round((reinsurance_prem + prof_claims),2) as total from (\n" + 
					"select C.*, ' ' as rx_claims from  (\n" + 
					"select B.*, COALESCE(round(sum(pcd.paid_amount),2),0) as prof_claims from (\n" + 
					"select A.*, ' ' as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"' ' as spec_cost,\n" + 
					"' ' as pcp_cap, COALESCE(round(count(*)*min(constant_val),2),0) as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					") B \n" + 
					"left join prof_claim_detail pcd on pcd.medicare_id = B.medicare_id\n" + 
					specialtyClaim+
					"group by B.plan_name, B.mbi, B.patientName, B.eligibleMonth, B.pcp_name, B.medicare_id, B.spec_cost, B.pcp_cap, B.reinsurance_prem\n" + 
					") C\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("prof claim") && vm.getClaimType().size() == 1) {
			
			queryStr = "select D.*, round((prof_claims),2) as total from (\n" + 
					"select C.*, ' ' as rx_claims from  (\n" + 
					"select B.*, COALESCE(round(sum(pcd.paid_amount),2),0) as prof_claims from (\n" + 
					"select A.*, ' ' as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"' ' as spec_cost,\n" + 
					"' ' as pcp_cap, ' ' as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					") B \n" + 
					"left join prof_claim_detail pcd on pcd.medicare_id = B.medicare_id\n" +
					specialtyClaim+
					"group by B.plan_name, B.mbi, B.patientName, B.eligibleMonth, B.pcp_name, B.medicare_id, B.spec_cost, B.pcp_cap, B.reinsurance_prem\n" + 
					") C\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("rx claim") && vm.getClaimType().contains("pcp cap") && vm.getClaimType().contains("spec claim") && vm.getClaimType().contains("reinsurance prem") && vm.getClaimType().size() == 4) {
			
			queryStr = "select D.*, round((spec_cost + pcp_cap + reinsurance_prem + rx_claims),2) as total from (\n" + 
					"select C.*, COALESCE(round(sum(rd.paid_amount - lics_paid - rep_gap_dscnt),2),0) as rx_claims from  (\n" + 
					"select B.*, ' ' as prof_claims from (\n" + 
					"select A.*, ' ' as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"COALESCE(round(sum(behavioral_health + chiropractic_cap + dental_cap + hearing_cap + lab + vision_ophthamalogy + vision_optometry + otc_cap + gym_cap + podiatry_cap + transportation + dermatology),2),0) as spec_cost,\n" + 
					"COALESCE(round(sum(pcp_cap),2),0) as pcp_cap, COALESCE(round(count(*)*min(constant_val),2),0) as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					") B \n" + 
					") C\n" + 
					"left join rx_detail rd on rd.medicare_id = C.medicare_id\n" + 
					"group by C.plan_name, C.mbi, C.patientName, C.eligibleMonth, C.pcp_name, C.medicare_id, C.spec_cost, C.pcp_cap, C.reinsurance_prem\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("rx claim") && vm.getClaimType().contains("pcp cap") && vm.getClaimType().contains("spec claim") && vm.getClaimType().size() == 3) {
		
			queryStr = "select D.*, round((spec_cost + pcp_cap + rx_claims),2) as total from (\n" + 
					"select C.*, COALESCE(round(sum(rd.paid_amount - lics_paid - rep_gap_dscnt),2),0) as rx_claims from  (\n" + 
					"select B.*, ' ' as prof_claims from (\n" + 
					"select A.*, ' ' as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"COALESCE(round(sum(behavioral_health + chiropractic_cap + dental_cap + hearing_cap + lab + vision_ophthamalogy + vision_optometry + otc_cap + gym_cap + podiatry_cap + transportation + dermatology),2),0) as spec_cost,\n" + 
					"COALESCE(round(sum(pcp_cap),2),0) as pcp_cap, ' ' as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					") B \n" + 
					") C\n" + 
					"left join rx_detail rd on rd.medicare_id = C.medicare_id\n" + 
					"group by C.plan_name, C.mbi, C.patientName, C.eligibleMonth, C.pcp_name, C.medicare_id, C.spec_cost, C.pcp_cap, C.reinsurance_prem\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("rx claim") && vm.getClaimType().contains("pcp cap") && vm.getClaimType().contains("reinsurance prem") && vm.getClaimType().size() == 3) {
			
			queryStr = "select D.*, round((pcp_cap + reinsurance_prem + rx_claims),2) as total from (\n" + 
					"select C.*, COALESCE(round(sum(rd.paid_amount - lics_paid - rep_gap_dscnt),2),0) as rx_claims from  (\n" + 
					"select B.*, ' ' as prof_claims from (\n" + 
					"select A.*, ' ' as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"' ' as spec_cost,\n" + 
					"COALESCE(round(sum(pcp_cap),2),0) as pcp_cap, COALESCE(round(count(*)*min(constant_val),2),0) as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					") B \n" + 
					") C\n" + 
					"left join rx_detail rd on rd.medicare_id = C.medicare_id\n" + 
					"group by C.plan_name, C.mbi, C.patientName, C.eligibleMonth, C.pcp_name, C.medicare_id, C.spec_cost, C.pcp_cap, C.reinsurance_prem\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("rx claim") && vm.getClaimType().contains("pcp cap") && vm.getClaimType().size() == 2) {
			
			queryStr = "select D.*, round((pcp_cap + rx_claims),2) as total from (\n" + 
					"select C.*, COALESCE(round(sum(rd.paid_amount - lics_paid - rep_gap_dscnt),2),0) as rx_claims from  (\n" + 
					"select B.*, ' ' as prof_claims from (\n" + 
					"select A.*, ' ' as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"' ' as spec_cost,\n" + 
					"COALESCE(round(sum(pcp_cap),2),0) as pcp_cap, ' ' as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					") B \n" + 
					") C\n" + 
					"left join rx_detail rd on rd.medicare_id = C.medicare_id\n" + 
					"group by C.plan_name, C.mbi, C.patientName, C.eligibleMonth, C.pcp_name, C.medicare_id, C.spec_cost, C.pcp_cap, C.reinsurance_prem\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("rx claim") && vm.getClaimType().contains("spec claim") && vm.getClaimType().contains("reinsurance prem") && vm.getClaimType().size() == 3) {
			
			queryStr = "select D.*, round((spec_cost + reinsurance_prem + rx_claims),2) as total from (\n" + 
					"select C.*, COALESCE(round(sum(rd.paid_amount - lics_paid - rep_gap_dscnt),2),0) as rx_claims from  (\n" + 
					"select B.*, ' ' as prof_claims from (\n" + 
					"select A.*, ' ' as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"COALESCE(round(sum(behavioral_health + chiropractic_cap + dental_cap + hearing_cap + lab + vision_ophthamalogy + vision_optometry + otc_cap + gym_cap + podiatry_cap + transportation + dermatology),2),0) as spec_cost,\n" + 
					"' ' as pcp_cap, COALESCE(round(count(*)*min(constant_val),2),0) as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					") B \n" + 
					") C\n" + 
					"left join rx_detail rd on rd.medicare_id = C.medicare_id\n" + 
					"group by C.plan_name, C.mbi, C.patientName, C.eligibleMonth, C.pcp_name, C.medicare_id, C.spec_cost, C.pcp_cap, C.reinsurance_prem\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("rx claim") && vm.getClaimType().contains("spec claim")  && vm.getClaimType().size() == 2) {
			
			queryStr = "select D.*, round((spec_cost + rx_claims),2) as total from (\n" + 
					"select C.*, COALESCE(round(sum(rd.paid_amount - lics_paid - rep_gap_dscnt),2),0) as rx_claims from  (\n" + 
					"select B.*, ' ' as prof_claims from (\n" + 
					"select A.*, ' ' as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"COALESCE(round(sum(behavioral_health + chiropractic_cap + dental_cap + hearing_cap + lab + vision_ophthamalogy + vision_optometry + otc_cap + gym_cap + podiatry_cap + transportation + dermatology),2),0) as spec_cost,\n" + 
					"' ' as pcp_cap, ' ' as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					") B \n" + 
					") C\n" + 
					"left join rx_detail rd on rd.medicare_id = C.medicare_id\n" + 
					"group by C.plan_name, C.mbi, C.patientName, C.eligibleMonth, C.pcp_name, C.medicare_id, C.spec_cost, C.pcp_cap, C.reinsurance_prem\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("rx claim") && vm.getClaimType().contains("reinsurance prem") && vm.getClaimType().size() == 2) {
			
			queryStr = "select D.*, round((reinsurance_prem + rx_claims),2) as total from (\n" + 
					"select C.*, COALESCE(round(sum(rd.paid_amount - lics_paid - rep_gap_dscnt),2),0) as rx_claims from  (\n" + 
					"select B.*, ' ' as prof_claims from (\n" + 
					"select A.*, ' ' as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"' ' as spec_cost,\n" + 
					"' ' as pcp_cap, COALESCE(round(count(*)*min(constant_val),2),0) as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					") B \n" + 
					") C\n" + 
					"left join rx_detail rd on rd.medicare_id = C.medicare_id\n" + 
					"group by C.plan_name, C.mbi, C.patientName, C.eligibleMonth, C.pcp_name, C.medicare_id, C.spec_cost, C.pcp_cap, C.reinsurance_prem\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("rx claim") && vm.getClaimType().size() == 1) {
			
			queryStr = "select D.*, round((rx_claims),2) as total from (\n" + 
					"select C.*, COALESCE(round(sum(rd.paid_amount - lics_paid - rep_gap_dscnt),2),0) as rx_claims from  (\n" + 
					"select B.*, ' ' as prof_claims from (\n" + 
					"select A.*, ' ' as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"' ' as spec_cost,\n" + 
					"' ' as pcp_cap, ' ' as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					") B \n" + 
					") C\n" + 
					"left join rx_detail rd on rd.medicare_id = C.medicare_id\n" + 
					"group by C.plan_name, C.mbi, C.patientName, C.eligibleMonth, C.pcp_name, C.medicare_id, C.spec_cost, C.pcp_cap, C.reinsurance_prem\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("pcp cap") && vm.getClaimType().contains("spec claim") && vm.getClaimType().contains("reinsurance prem") && vm.getClaimType().size() == 3) {
			
			queryStr = "select D.*, round((spec_cost + pcp_cap + reinsurance_prem),2) as total from (\n" + 
					"select C.*, ' ' as rx_claims from  (\n" + 
					"select B.*, ' ' as prof_claims from (\n" + 
					"select A.*, ' ' as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"COALESCE(round(sum(behavioral_health + chiropractic_cap + dental_cap + hearing_cap + lab + vision_ophthamalogy + vision_optometry + otc_cap + gym_cap + podiatry_cap + transportation + dermatology),2),0) as spec_cost,\n" + 
					"COALESCE(round(sum(pcp_cap),2),0) as pcp_cap, COALESCE(round(count(*)*min(constant_val),2),0) as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					") B \n" + 
					") C\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("pcp cap") && vm.getClaimType().contains("spec claim") && vm.getClaimType().size() == 2) {
			
			queryStr = "select D.*, round((spec_cost + pcp_cap),2) as total from (\n" + 
					"select C.*, ' ' as rx_claims from  (\n" + 
					"select B.*, ' ' as prof_claims from (\n" + 
					"select A.*, ' ' as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"COALESCE(round(sum(behavioral_health + chiropractic_cap + dental_cap + hearing_cap + lab + vision_ophthamalogy + vision_optometry + otc_cap + gym_cap + podiatry_cap + transportation + dermatology),2),0) as spec_cost,\n" + 
					"COALESCE(round(sum(pcp_cap),2),0) as pcp_cap, ' ' as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					") B \n" + 
					") C\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("pcp cap") && vm.getClaimType().contains("reinsurance prem") && vm.getClaimType().size() == 2) {
			
			queryStr = "select D.*, round((pcp_cap + reinsurance_prem),2) as total from (\n" + 
					"select C.*, ' ' as rx_claims from  (\n" + 
					"select B.*, ' ' as prof_claims from (\n" + 
					"select A.*, ' ' as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"' ' as spec_cost,\n" + 
					"COALESCE(round(sum(pcp_cap),2),0) as pcp_cap, COALESCE(round(count(*)*min(constant_val),2),0) as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					") B \n" + 
					") C\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("pcp cap") && vm.getClaimType().size() == 1) {
			
			queryStr = "select D.*, round((pcp_cap),2) as total from (\n" + 
					"select C.*, ' ' as rx_claims from  (\n" + 
					"select B.*, ' ' as prof_claims from (\n" + 
					"select A.*, ' ' as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"' ' as spec_cost,\n" + 
					"COALESCE(round(sum(pcp_cap),2),0) as pcp_cap, ' ' as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					") B \n" + 
					") C\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("spec claim") && vm.getClaimType().contains("reinsurance prem") && vm.getClaimType().size() == 2) {
			
			queryStr = "select D.*, round((spec_cost + reinsurance_prem),2) as total from (\n" + 
					"select C.*, ' ' as rx_claims from  (\n" + 
					"select B.*, ' ' as prof_claims from (\n" + 
					"select A.*, ' ' as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"COALESCE(round(sum(behavioral_health + chiropractic_cap + dental_cap + hearing_cap + lab + vision_ophthamalogy + vision_optometry + otc_cap + gym_cap + podiatry_cap + transportation + dermatology),2),0) as spec_cost,\n" + 
					"' ' as pcp_cap, COALESCE(round(count(*)*min(constant_val),2),0) as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					") B \n" + 
					") C\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("spec claim") && vm.getClaimType().size() == 1) {
			
			queryStr = "select D.*, round((spec_cost),2) as total from (\n" + 
					"select C.*, ' ' as rx_claims from  (\n" + 
					"select B.*, ' ' as prof_claims from (\n" + 
					"select A.*, ' ' as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"COALESCE(round(sum(behavioral_health + chiropractic_cap + dental_cap + hearing_cap + lab + vision_ophthamalogy + vision_optometry + otc_cap + gym_cap + podiatry_cap + transportation + dermatology),2),0) as spec_cost,\n" + 
					"' ' as pcp_cap, ' ' as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					") B \n" + 
					") C\n" + 
					") D "+filterStr+havingStr;
			
		}
		if(vm.getClaimType().contains("reinsurance prem") && vm.getClaimType().size() == 1) {
			
			queryStr = "select D.*, round((reinsurance_prem),2) as total from (\n" + 
					"select C.*, ' ' as rx_claims from  (\n" + 
					"select B.*, ' ' as prof_claims from (\n" + 
					"select A.*, ' ' as inst_claims from (\n" + 
					"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
					"' ' as spec_cost,\n" + 
					"' ' as pcp_cap, COALESCE(round(count(*)*min(constant_val),2),0) as reinsurance_prem\n" + 
					"from demographic_detail dd \n" + 
					conditionStr+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
					") A\n" + 
					") B \n" + 
					") C\n" + 
					") D "+filterStr+havingStr;
			
		}
		
		
		countQueryStr = "select count(*) from \n" + 
				"(\n" + queryStr;
		
		System.out.println(queryStr+sortQryStr);
		System.out.println(countQueryStr+sortCountQryStr);
		
		Query query = getEntityManager().createNativeQuery(queryStr+sortQryStr);
		queryResult = query.getResultList();
		
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
	
	public ReportResponseVM generateClaimDetailReport(ReportVM vm) {
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
				filterColumnName = "dd.plan_name";
			}
			if(filteredList.get(i).getId().equals("providerName")) {
				filterColumnName = "dd.pcp_name";
			}
			if(filteredList.get(i).getId().equals("medicareId")) {
				filterColumnName = "dd.mbi";
			}
			if(filteredList.get(i).getId().equals("patientName")) {
				filterColumnName = "patientName";
			}
			if(filteredList.get(i).getId().equals("icdCode")) {
				filterColumnName = "icdCode";
			}
			if(filteredList.get(i).getId().equals("hccCode")) {
				filterColumnName = "hccCode";
			}
			if(filteredList.get(i).getId().equals("termedMonth")) {
				filterColumnName = "";
			}
			if(filteredList.get(i).getId().equals("eligibleMonth")) {
				filterColumnName = "eligibleMonth";
			}
			if(filteredList.get(i).getId().equals("cost")) {
				filterColumnName = "cost";
			}
			
			if(!filterColumnName.equals("") && !filterColumnName.equals("patientName") && !filterColumnName.equals("eligibleMonth") && !filterColumnName.equals("cost") && !filterColumnName.equals("icdCode") && !filterColumnName.equals("hccCode")) {
				filterStr += " and "+filterColumnName+" like "+'\''+filteredList.get(i).getValue()+"%"+'\''+" ";
			}
			
			if(filterColumnName.equals("patientName") || filterColumnName.equals("eligibleMonth") || filterColumnName.equals("cost") || filterColumnName.equals("icdCode") || filterColumnName.equals("hccCode")) {
				if(!havingStr.equals("")) {
					havingStr += "and ";
				} else {
					havingStr = " having ";
				}
				havingStr += filterColumnName+" like "+'\''+filteredList.get(i).getValue()+"%"+'\''+" ";
			}
				
		}
		
		
		String sortStr = "";
		String sortColName = "";
		
		if(!sortedList.isEmpty()) {
			if(sortedList.get(0).getId().equals("planName")) {
				sortColName = "plan_name";
			}
			if(sortedList.get(0).getId().equals("providerName")) {
				sortColName = "pcp_name";
			}
			if(sortedList.get(0).getId().equals("medicareId")) {
				sortColName = "mbi";
			}
			if(sortedList.get(0).getId().equals("patientName")) {
				sortColName = "patientName";
			}
			if(sortedList.get(0).getId().equals("icdCode")) {
				sortColName = "icdCode";
			}
			if(sortedList.get(0).getId().equals("hccCode")) {
				sortColName = "hccCode";
			}
			if(sortedList.get(0).getId().equals("termedMonth")) {
				sortColName = "";
			}
			if(sortedList.get(0).getId().equals("eligibleMonth")) {
				sortColName = "eligibleMonth";
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
		
		String sortQryStr = " limit "+start+","+end;
		String sortCountQryStr = "";
		if(!sortStr.equals("")) {
			sortQryStr = " order by "+sortStr+" limit "+start+","+end;
			sortCountQryStr = " order by "+sortStr;
		}
		
		String queryStr = "",countQueryStr = "";
		
		
		if(!vm.getClaimType().isEmpty()) {
			
			if(vm.getFromDate() != null && !vm.getFromDate().equals("Invalid date") && vm.getToDate() != null && !vm.getToDate().equals("Invalid date")) {
				
				if(vm.getClaimType().contains("pcp cap")) {
					
					if(vm.getPlan().equals("all") || vm.getPlan().equals("")) {
						queryStr = "select dd.plan_name,dd.pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
								"COALESCE(round(sum(pcp_cap),2),0) as cost,'' as icdCode,'' as hccCode\n" + 
								"from demographic_detail dd  \n" + 
								"where dd.eligible_month between "+'\''+vm.getFromDate()+'\''+" and "+'\''+vm.getToDate()+'\''+filterStr+" group by dd.plan_name,dd.mbi,dd.pcp_name,dd.last_name,dd.first_name,dd.medicare_id "+havingStr;
						
					} else {
						queryStr = "select dd.plan_name,dd.pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
								"COALESCE(round(sum(pcp_cap),2),0) as cost,'' as icdCode,'' as hccCode\n" + 
								"from demographic_detail dd  \n" + 
								"where dd.provider="+'\''+vm.getPlan()+'\''+" and dd.eligible_month between "+'\''+vm.getFromDate()+'\''+" and "+'\''+vm.getToDate()+'\''+filterStr+" group by dd.plan_name,dd.mbi,dd.pcp_name,dd.last_name,dd.first_name,dd.medicare_id "+havingStr;
						
					}
					
				}
				if(vm.getClaimType().contains("spec claim")) {
					if(vm.getPlan().equals("all") || vm.getPlan().equals("")) {
						queryStr = "select dd.plan_name,dd.pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
							"COALESCE(round(sum(behavioral_health + chiropractic_cap + dental_cap + hearing_cap + lab + vision_ophthamalogy + vision_optometry + otc_cap + gym_cap + podiatry_cap + transportation + dermatology),2),0) as cost,'' as icdCode,'' as hccCode \n" + 
							"from demographic_detail dd  \n" + 
							"where dd.eligible_month between "+'\''+vm.getFromDate()+'\''+" and "+'\''+vm.getToDate()+'\''+filterStr+" group by dd.plan_name,dd.mbi,dd.pcp_name,dd.last_name,dd.first_name,dd.medicare_id "+havingStr;
					} else {
						queryStr = "select dd.plan_name,dd.pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
								"COALESCE(round(sum(behavioral_health + chiropractic_cap + dental_cap + hearing_cap + lab + vision_ophthamalogy + vision_optometry + otc_cap + gym_cap + podiatry_cap + transportation + dermatology),2),0) as cost,'' as icdCode,'' as hccCode \n" + 
								"from demographic_detail dd  \n" + 
								"where dd.provider="+'\''+vm.getPlan()+'\''+" and dd.eligible_month between "+'\''+vm.getFromDate()+'\''+" and "+'\''+vm.getToDate()+'\''+filterStr+" group by dd.plan_name,dd.mbi,dd.pcp_name,dd.last_name,dd.first_name,dd.medicare_id "+havingStr;
					}
				}
				if(vm.getClaimType().contains("reinsurance prem")) {
					if(vm.getPlan().equals("all") || vm.getPlan().equals("")) {
						queryStr = "select dd.plan_name,dd.pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
							"COALESCE(round(count(*)*min(constant_val),2),0) as cost,'' as icdCode,'' as hccCode \n" + 
							"from demographic_detail dd  \n" + 
							"where dd.eligible_month between "+'\''+vm.getFromDate()+'\''+" and "+'\''+vm.getToDate()+'\''+filterStr+" group by dd.plan_name,dd.mbi,dd.pcp_name,dd.last_name,dd.first_name,dd.medicare_id "+havingStr;
					} else {
						queryStr = "select dd.plan_name,dd.pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
								"COALESCE(round(count(*)*min(constant_val),2),0) as cost,'' as icdCode,'' as hccCode \n" + 
								"from demographic_detail dd  \n" + 
								"where dd.provider="+'\''+vm.getPlan()+'\''+" and dd.eligible_month between "+'\''+vm.getFromDate()+'\''+" and "+'\''+vm.getToDate()+'\''+filterStr+" group by dd.plan_name,dd.mbi,dd.pcp_name,dd.last_name,dd.first_name,dd.medicare_id "+havingStr;
					}
					
				}
				if(vm.getClaimType().contains("inst claim")) {
					
					if(!filterStr.equals(""))
						filterStr = " where "+filterStr.substring(4);
					if(!havingStr.equals(""))
						havingStr = " and "+havingStr.substring(6);
					
					if(vm.getPlan().equals("all") || vm.getPlan().equals("")) {
						
						queryStr = "select D.*, round((cost),2) as total from (\n" + 
								"select B.*, min(v_21) as hccCode from (\n" + 
								"select A.*, COALESCE(round(sum(icd.paid_amount),2),0) as cost,min(icd.principal_diagnosis) as icdCode from (\n" + 
								"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id \n" + 
								"from demographic_detail dd \n" + 
								" where dd.eligible_month between "+'\''+vm.getFromDate()+'\''+" and "+'\''+vm.getToDate()+'\''+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
								") A\n" + 
								" left join inst_claim_detail icd on icd.medicare_id = A.medicare_id  \n" + 
								"group by A.plan_name, A.mbi, A.patientName, A.eligibleMonth, A.pcp_name, A.medicare_id\n" + 
								") B \n" + 
								" left join hcc_code_values hcv on hcv.diagnosis_code = B.icdCode\n" + 
								"group by B.plan_name, B.mbi, B.patientName, B.eligibleMonth, B.pcp_name, B.medicare_id\n" + 
								") D "+filterStr+" having icdCode is not null "+havingStr;
						
					} else {
						
						queryStr = "select D.*, round((cost),2) as total from (\n" + 
								"select B.*, min(v_21) as hccCode from (\n" + 
								"select A.*, COALESCE(round(sum(icd.paid_amount),2),0) as cost,min(icd.principal_diagnosis) as icdCode from (\n" + 
								"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id \n" + 
								"from demographic_detail dd \n" + 
								" where dd.provider="+'\''+vm.getPlan()+'\''+" and dd.eligible_month between "+'\''+vm.getFromDate()+'\''+" and "+'\''+vm.getToDate()+'\''+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
								") A\n" + 
								" left join inst_claim_detail icd on icd.medicare_id = A.medicare_id  \n" + 
								"group by A.plan_name, A.mbi, A.patientName, A.eligibleMonth, A.pcp_name, A.medicare_id\n" + 
								") B \n" + 
								" left join hcc_code_values hcv on hcv.diagnosis_code = B.icdCode\n" + 
								"group by B.plan_name, B.mbi, B.patientName, B.eligibleMonth, B.pcp_name, B.medicare_id\n" + 
								") D "+filterStr+" having icdCode is not null "+havingStr;
					}
				}
				if(vm.getClaimType().contains("prof claim")) {
					
					if(!filterStr.equals(""))
					filterStr = " where "+filterStr.substring(4);
					if(!havingStr.equals(""))
					havingStr = " and "+havingStr.substring(6);
				
					if(vm.getPlan().equals("all") || vm.getPlan().equals("")) {
						
						queryStr = "select D.*, round((cost),2) as total from (\n" + 
								"select C.*, min(v_21) as hccCode from (\n" + 
								"select B.*, COALESCE(round(sum(pcd.paid_amount),2),0) as cost,min(pcd.principal_diagnosis) as icdCode from (\n" + 
								"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id\n" + 
								"from demographic_detail dd \n" + 
								" where dd.eligible_month between "+'\''+vm.getFromDate()+'\''+" and "+'\''+vm.getToDate()+'\''+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
								") B \n" + 
								"left join prof_claim_detail pcd on pcd.medicare_id = B.medicare_id\n" + 
								"group by B.plan_name, B.mbi, B.patientName, B.eligibleMonth, B.pcp_name, B.medicare_id\n" + 
								") C \n" + 
								"left join hcc_code_values hcv on hcv.diagnosis_code = C.icdCode\n" + 
								"group by C.plan_name, C.mbi, C.patientName, C.eligibleMonth, C.pcp_name, C.medicare_id\n" + 
								") D "+filterStr+" having icdCode is not null "+havingStr;
						
					} else {
						
						queryStr = "select D.*, round((cost),2) as total from (\n" + 
								"select C.*, min(v_21) as hccCode from (\n" + 
								"select B.*, COALESCE(round(sum(pcd.paid_amount),2),0) as cost,min(pcd.principal_diagnosis) as icdCode from (\n" + 
								"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id\n" + 
								"from demographic_detail dd \n" + 
								" where dd.provider="+'\''+vm.getPlan()+'\''+" and dd.eligible_month between "+'\''+vm.getFromDate()+'\''+" and "+'\''+vm.getToDate()+'\''+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
								") B \n" + 
								"left join prof_claim_detail pcd on pcd.medicare_id = B.medicare_id\n" + 
								"group by B.plan_name, B.mbi, B.patientName, B.eligibleMonth, B.pcp_name, B.medicare_id\n" + 
								") C \n" + 
								"left join hcc_code_values hcv on hcv.diagnosis_code = C.icdCode\n" + 
								"group by C.plan_name, C.mbi, C.patientName, C.eligibleMonth, C.pcp_name, C.medicare_id\n" + 
								") D "+filterStr+" having icdCode is not null "+havingStr;
						
					}
				}
				if(vm.getClaimType().contains("rx claim")) {
					if(vm.getPlan().equals("all") || vm.getPlan().equals("")) {
						queryStr = "select D.*, round((cost),2) as total from (\n" + 
								"select C.*, COALESCE(round(sum(rd.paid_amount - lics_paid - rep_gap_dscnt),2),0) as cost,'' as icdCode,'' as hccCode from  (\n" + 
								"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id\n" + 
								"from demographic_detail dd \n" + 
								" where dd.eligible_month between "+'\''+vm.getFromDate()+'\''+" and "+'\''+vm.getToDate()+'\''+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
								") C\n" + 
								"left join rx_detail rd on rd.medicare_id = C.medicare_id\n" + 
								"group by C.plan_name, C.mbi, C.patientName, C.eligibleMonth, C.pcp_name, C.medicare_id\n" + 
								") D "+filterStr+havingStr;
						
					} else {
						queryStr = "select D.*, round((cost),2) as total from (\n" + 
								"select C.*, COALESCE(round(sum(rd.paid_amount - lics_paid - rep_gap_dscnt),2),0) as cost,'' as icdCode,'' as hccCode from  (\n" + 
								"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id\n" + 
								"from demographic_detail dd \n" + 
								" where dd.provider="+'\''+vm.getPlan()+'\''+" and dd.eligible_month between "+'\''+vm.getFromDate()+'\''+" and "+'\''+vm.getToDate()+'\''+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
								") C\n" + 
								"left join rx_detail rd on rd.medicare_id = C.medicare_id\n" + 
								"group by C.plan_name, C.mbi, C.patientName, C.eligibleMonth, C.pcp_name, C.medicare_id\n" + 
								") D "+filterStr+havingStr;
					}
					
				}
			} else {
				
				if(vm.getClaimType().contains("pcp cap")) {
					if(vm.getPlan().equals("all") || vm.getPlan().equals("")) {
						if(!filterStr.equals(""))
							filterStr = " where "+filterStr.substring(4);
						queryStr = "select dd.plan_name,dd.pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
								"COALESCE(round(sum(pcp_cap),2),0) as cost,'' as icdCode,'' as hccCode\n" + 
								"from demographic_detail dd  \n" + 
								filterStr+" group by dd.plan_name,dd.mbi,dd.pcp_name,dd.last_name,dd.first_name,dd.medicare_id "+havingStr;
						
					} else {
						queryStr = "select dd.plan_name,dd.pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
								"COALESCE(round(sum(pcp_cap),2),0) as cost,'' as icdCode,'' as hccCode\n" + 
								"from demographic_detail dd  \n" + 
								"where dd.provider="+'\''+vm.getPlan()+'\''+filterStr+" group by dd.plan_name,dd.mbi,dd.pcp_name,dd.last_name,dd.first_name,dd.medicare_id "+havingStr;
						
					}
					
				}
				if(vm.getClaimType().contains("spec claim")) {
					if(vm.getPlan().equals("all") || vm.getPlan().equals("")) {
						if(!filterStr.equals(""))
							filterStr = " where "+filterStr.substring(4);
						queryStr = "select dd.plan_name,dd.pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
							"COALESCE(round(sum(behavioral_health + chiropractic_cap + dental_cap + hearing_cap + lab + vision_ophthamalogy + vision_optometry + otc_cap + gym_cap + podiatry_cap + transportation + dermatology),2),0) as cost,'' as icdCode,'' as hccCode \n" + 
							"from demographic_detail dd  \n" + 
							filterStr+" group by dd.plan_name,dd.mbi,dd.pcp_name,dd.last_name,dd.first_name,dd.medicare_id "+havingStr;
					} else {
						queryStr = "select dd.plan_name,dd.pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
								"COALESCE(round(sum(behavioral_health + chiropractic_cap + dental_cap + hearing_cap + lab + vision_ophthamalogy + vision_optometry + otc_cap + gym_cap + podiatry_cap + transportation + dermatology),2),0) as cost,'' as icdCode,'' as hccCode \n" + 
								"from demographic_detail dd  \n" + 
								"where dd.provider="+'\''+vm.getPlan()+'\''+filterStr+" group by dd.plan_name,dd.mbi,dd.pcp_name,dd.last_name,dd.first_name,dd.medicare_id "+havingStr;
					}
				}
				if(vm.getClaimType().contains("reinsurance prem")) {
					if(vm.getPlan().equals("all") || vm.getPlan().equals("")) {
						if(!filterStr.equals(""))
							filterStr = " where "+filterStr.substring(4);
						queryStr = "select dd.plan_name,dd.pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
							"COALESCE(round(count(*)*min(constant_val),2),0) as cost,'' as icdCode,'' as hccCode \n" + 
							"from demographic_detail dd  \n" + 
							filterStr+" group by dd.plan_name,dd.mbi,dd.pcp_name,dd.last_name,dd.first_name,dd.medicare_id "+havingStr;
					} else {
						queryStr = "select dd.plan_name,dd.pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id,\n" + 
								"COALESCE(round(count(*)*min(constant_val),2),0) as cost,'' as icdCode,'' as hccCode \n" + 
								"from demographic_detail dd  \n" + 
								"where dd.provider="+'\''+vm.getPlan()+'\''+filterStr+" group by dd.plan_name,dd.mbi,dd.pcp_name,dd.last_name,dd.first_name,dd.medicare_id "+havingStr;
					}
					
				}
				if(vm.getClaimType().contains("inst claim")) {
					
					if(!filterStr.equals(""))
						filterStr = " where "+filterStr.substring(4);
					if(!havingStr.equals(""))
						havingStr = " and "+havingStr.substring(6);
					
					if(vm.getPlan().equals("all") || vm.getPlan().equals("")) {
						
							queryStr = "select D.*, round((cost),2) as total from (\n" + 
									"select B.*, min(v_21) as hccCode from (\n" + 
									"select A.*, COALESCE(round(sum(icd.paid_amount),2),0) as cost,min(icd.principal_diagnosis) as icdCode from (\n" + 
									"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id \n" + 
									"from demographic_detail dd \n" + 
									" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
									") A\n" + 
									" left join inst_claim_detail icd on icd.medicare_id = A.medicare_id  \n" + 
									"group by A.plan_name, A.mbi, A.patientName, A.eligibleMonth, A.pcp_name, A.medicare_id\n" + 
									") B \n" + 
									" left join hcc_code_values hcv on hcv.diagnosis_code = B.icdCode\n" + 
									"group by B.plan_name, B.mbi, B.patientName, B.eligibleMonth, B.pcp_name, B.medicare_id\n" + 
									") D "+filterStr+" having icdCode is not null "+havingStr;
							
						} else {
							
							queryStr = "select D.*, round((cost),2) as total from (\n" + 
									"select B.*, min(v_21) as hccCode from (\n" + 
									"select A.*, COALESCE(round(sum(icd.paid_amount),2),0) as cost,min(icd.principal_diagnosis) as icdCode from (\n" + 
									"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id \n" + 
									"from demographic_detail dd \n" + 
									" where dd.provider="+'\''+vm.getPlan()+'\''+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
									") A\n" + 
									" left join inst_claim_detail icd on icd.medicare_id = A.medicare_id  \n" + 
									"group by A.plan_name, A.mbi, A.patientName, A.eligibleMonth, A.pcp_name, A.medicare_id\n" + 
									") B \n" + 
									" left join hcc_code_values hcv on hcv.diagnosis_code = B.icdCode\n" + 
									"group by B.plan_name, B.mbi, B.patientName, B.eligibleMonth, B.pcp_name, B.medicare_id\n" + 
									") D "+filterStr+" having icdCode is not null "+havingStr;
						}
				}
				if(vm.getClaimType().contains("prof claim")) {
					
					if(!filterStr.equals(""))
						filterStr = " where "+filterStr.substring(4);
					if(!havingStr.equals(""))
						havingStr = " and "+havingStr.substring(6);
					
						if(vm.getPlan().equals("all") || vm.getPlan().equals("")) {
							
							queryStr = "select D.*, round((cost),2) as total from (\n" + 
									"select C.*, min(v_21) as hccCode from (\n" + 
									"select B.*, COALESCE(round(sum(pcd.paid_amount),2),0) as cost,min(pcd.principal_diagnosis) as icdCode from (\n" + 
									"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id\n" + 
									"from demographic_detail dd \n" + 
									" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
									") B \n" + 
									"left join prof_claim_detail pcd on pcd.medicare_id = B.medicare_id\n" + 
									"group by B.plan_name, B.mbi, B.patientName, B.eligibleMonth, B.pcp_name, B.medicare_id\n" + 
									") C \n" + 
									"left join hcc_code_values hcv on hcv.diagnosis_code = C.icdCode\n" + 
									"group by C.plan_name, C.mbi, C.patientName, C.eligibleMonth, C.pcp_name, C.medicare_id\n" + 
									") D "+filterStr+" having icdCode is not null "+havingStr;
							
						} else {
							
							queryStr = "select D.*, round((cost),2) as total from (\n" + 
									"select C.*, min(v_21) as hccCode from (\n" + 
									"select B.*, COALESCE(round(sum(pcd.paid_amount),2),0) as cost,min(pcd.principal_diagnosis) as icdCode from (\n" + 
									"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id\n" + 
									"from demographic_detail dd \n" + 
									" where dd.provider="+'\''+vm.getPlan()+'\''+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
									") B \n" + 
									"left join prof_claim_detail pcd on pcd.medicare_id = B.medicare_id\n" + 
									"group by B.plan_name, B.mbi, B.patientName, B.eligibleMonth, B.pcp_name, B.medicare_id\n" + 
									") C \n" + 
									"left join hcc_code_values hcv on hcv.diagnosis_code = C.icdCode\n" + 
									"group by C.plan_name, C.mbi, C.patientName, C.eligibleMonth, C.pcp_name, C.medicare_id\n" + 
									") D "+filterStr+" having icdCode is not null "+havingStr;
							
						}
				}
				if(vm.getClaimType().contains("rx claim")) {
					if(vm.getPlan().equals("all") || vm.getPlan().equals("")) {
						if(!filterStr.equals(""))
							filterStr = " where "+filterStr.substring(4);
						queryStr = "select D.*, round((cost),2) as total from (\n" + 
								"select C.*, COALESCE(round(sum(rd.paid_amount - lics_paid - rep_gap_dscnt),2),0) as cost,'' as icdCode,'' as hccCode from  (\n" + 
								"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id\n" + 
								"from demographic_detail dd \n" + 
								" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
								") C\n" + 
								"left join rx_detail rd on rd.medicare_id = C.medicare_id\n" + 
								"group by C.plan_name, C.mbi, C.patientName, C.eligibleMonth, C.pcp_name, C.medicare_id\n" + 
								") D "+filterStr+havingStr;
						
					} else {
						queryStr = "select D.*, round((cost),2) as total from (\n" + 
								"select C.*, COALESCE(round(sum(rd.paid_amount - lics_paid - rep_gap_dscnt),2),0) as cost,'' as icdCode,'' as hccCode from  (\n" + 
								"select dd.plan_name,max(dd.pcp_name) as pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month) as eligibleMonth,dd.medicare_id\n" + 
								"from demographic_detail dd \n" + 
								" where dd.provider="+'\''+vm.getPlan()+'\''+" group by dd.plan_name,dd.mbi,dd.last_name,dd.first_name,dd.medicare_id \n" + 
								") C\n" + 
								"left join rx_detail rd on rd.medicare_id = C.medicare_id\n" + 
								"group by C.plan_name, C.mbi, C.patientName, C.eligibleMonth, C.pcp_name, C.medicare_id\n" + 
								") D "+filterStr+havingStr;
					}
					
				}
				
			}
			
			
			
		} else {
			
		}
			
		countQueryStr = "select count(*) from \n" + 
				"(\n" + queryStr;
		
		System.out.println(queryStr+sortQryStr);
		System.out.println(countQueryStr+sortCountQryStr);
		
		Query query = getEntityManager().createNativeQuery(queryStr+sortQryStr);
		queryResult = query.getResultList();
		
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
	
	public ReportResponseVM generateClaimReport(ReportVM vm) {
		
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
				filterColumnName = "plan_name";
			}
			if(filteredList.get(i).getId().equals("providerName")) {
				filterColumnName = "pcp_name";
			}
			if(filteredList.get(i).getId().equals("medicareId")) {
				filterColumnName = "mbi";
			}
			if(filteredList.get(i).getId().equals("patientName")) {
				filterColumnName = "patientName";
			}
			if(filteredList.get(i).getId().equals("icdCode")) {
				filterColumnName = "";
			}
			if(filteredList.get(i).getId().equals("hccCode")) {
				filterColumnName = "";
			}
			if(filteredList.get(i).getId().equals("termedMonth")) {
				filterColumnName = "";
			}
			if(filteredList.get(i).getId().equals("eligibleMonth")) {
				filterColumnName = "eligibleMonth";
			}
			if(filteredList.get(i).getId().equals("cost")) {
				filterColumnName = "cost";
			}
			
			if(!filterColumnName.equals("") && !filterColumnName.equals("patientName") && !filterColumnName.equals("eligibleMonth")) {
				filterStr += " and "+filterColumnName+" like "+'\''+filteredList.get(i).getValue()+"%"+'\''+" ";
			}
			
			if(filterColumnName.equals("patientName") || filterColumnName.equals("eligibleMonth")) {
				if(!havingStr.equals("")) {
					havingStr += "and ";
				} else {
					havingStr = " having ";
				}
				havingStr += filterColumnName+" like "+'\''+filteredList.get(i).getValue()+"%"+'\''+" ";
			}
				
		}
		
		
		String sortStr = "";
		String sortColName = "";
		
		if(!sortedList.isEmpty()) {
			if(sortedList.get(0).getId().equals("planName")) {
				sortColName = "plan_name";
			}
			if(sortedList.get(0).getId().equals("providerName")) {
				sortColName = "pcp_name";
			}
			if(sortedList.get(0).getId().equals("medicareId")) {
				sortColName = "mbi";
			}
			if(sortedList.get(0).getId().equals("patientName")) {
				sortColName = "patientName";
			}
			if(sortedList.get(0).getId().equals("icdCode")) {
				sortColName = "";
			}
			if(sortedList.get(0).getId().equals("hccCode")) {
				sortColName = "";
			}
			if(sortedList.get(0).getId().equals("termedMonth")) {
				sortColName = "";
			}
			if(sortedList.get(0).getId().equals("eligibleMonth")) {
				sortColName = "eligibleMonth";
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
		
		ReportResponseVM responseVM = new ReportResponseVM();
		String instStr = "",profStr = "",SpecStr = "",rxStr = "",instStrCount="",profStrCount="";
		String queryStr = "";
		List<Object[]> listFinal = new ArrayList<>();
		int start,end,noOfPages = 0,instCount = 0,profCount = 0,specCount = 0,rxCount = 0,totalCount = 0;
		end = vm.getPageSize() * vm.getPage();
		start = end - vm.getPageSize();
		end = vm.getPageSize();
		
		String sortQryStr = " limit "+start+","+end;
		String sortCountQryStr = "";
		if(!sortStr.equals("")) {
			sortQryStr = " order by "+sortStr+" limit "+start+","+end;
			sortCountQryStr = " order by "+sortStr;
		}
		
		String fileQuery = "";
		
		if(!vm.getClaimType().isEmpty()) {
			
			if(vm.getClaimType().contains("inst claim")) {
				
				instStr = "select distinct round(sum(paid_amount),2),member_name,provider_name,plan_name,medicare_id,service_month from inst_claim_detail";
				if( (vm.getPlan() != null && !vm.getPlan().equals("")) || (vm.getGender() != null && !vm.getGender().equals("")) || (vm.getProvider() != null && !vm.getProvider().equals("")) || (vm.getSpeciality() != null && !vm.getSpeciality().equals("")) || (vm.getCostRange() != null && !vm.getCostRange().equals("")) || ((vm.getFromDate() != null && !vm.getFromDate().equals("Invalid date") && vm.getToDate() != null && !vm.getToDate().equals("Invalid date")) ) || !filteredList.isEmpty()) {
					instStr = instStr + " where";
				}
				if(vm.getPlan() != null && !vm.getPlan().equals("")) {
					instStr = instStr + " provider="+'\''+vm.getPlan()+'\''+" and";
				}
				if(vm.getGender() != null && !vm.getGender().equals("")) {
					instStr = instStr + " gender="+'\''+vm.getGender()+'\''+" and";
				}
				if(vm.getProvider() != null && !vm.getProvider().equals("")) {
					instStr = instStr + " pcp_name="+'\''+vm.getProvider()+'\''+" and";
				}
				if(vm.getSpeciality() != null && !vm.getSpeciality().equals("")) {
					instStr = instStr + " attending_physician_spec="+'\''+vm.getSpeciality()+'\''+" and";
				}
				if(vm.getCostRange() != null && !vm.getCostRange().equals("")) {
					if(vm.getCostRange().contains("-")) {
						String arr[] = vm.getCostRange().split("-");
						instStr = instStr + " paid_amount between "+arr[0]+ " and "+ arr[1]+" and";
					} else {
						instStr = instStr + " paid_amount "+vm.getCostRange()+" and";
					}
				}
				
				if(vm.getFromDate() != null && !vm.getFromDate().equals("Invalid date") && vm.getToDate() != null && !vm.getToDate().equals("Invalid date")) {
					instStr += " service_month between "+'\''+vm.getFromDate()+'\''+" and "+'\''+vm.getToDate()+'\''+" and";
				}
				
				instStr += filterStr;
				
				if(instStr.charAt(instStr.length()-1) == 'd' && instStr.charAt(instStr.length()-2) == 'n' && instStr.charAt(instStr.length()-3) == 'a' && instStr.charAt(instStr.length()-4) == ' ') {
					instStr = instStr.substring(0, instStr.length()-4);
				}
				
				instStr += " group by member_name,provider_name,plan_name,medicare_id,service_month";
				
				instStr += sortStr;
				instStrCount = "select count(*) from ("+instStr+") A";
				
				Query countQuery = getEntityManager().createNativeQuery(instStrCount);
				instCount = Integer.parseInt(countQuery.getSingleResult().toString());
			}
			
			if(vm.getClaimType().contains("prof claim")) {
				profStr = "select distinct round(sum(paid_amount),2),member_name,provider_name,plan_name,medicare_id,service_month from prof_claim_detail";
				if( (vm.getPlan() != null && !vm.getPlan().equals("")) || (vm.getGender() != null && !vm.getGender().equals("")) || (vm.getProvider() != null && !vm.getProvider().equals("")) || (vm.getSpeciality() != null && !vm.getSpeciality().equals("")) || (vm.getCostRange() != null && !vm.getCostRange().equals("")) || (vm.getFromDate() != null && !vm.getFromDate().equals("Invalid date") && vm.getToDate() != null && !vm.getToDate().equals("Invalid date")) || !filteredList.isEmpty()) {
					profStr = profStr + " where";
				}
				if(vm.getPlan() != null && !vm.getPlan().equals("")) {
					profStr = profStr + " provider="+'\''+vm.getPlan()+'\''+" and";
				}
				if(vm.getGender() != null && !vm.getGender().equals("")) {
					profStr = profStr + " gender="+'\''+vm.getGender()+'\''+" and";
				}
				if(vm.getProvider() != null && !vm.getProvider().equals("")) {
					profStr = profStr + " pcp_name="+'\''+vm.getProvider()+'\''+" and";
				}
				if(vm.getSpeciality() != null && !vm.getSpeciality().equals("")) {
					profStr = profStr + " claim_specialty="+'\''+vm.getSpeciality()+'\''+" and";
				}
				if(vm.getCostRange() != null && !vm.getCostRange().equals("")) {
					if(vm.getCostRange().contains("-")) {
						String arr[] = vm.getCostRange().split("-");
						profStr = profStr + " paid_amount between "+arr[0]+ " and "+ arr[1]+" and";
					} else {
						profStr = profStr + " paid_amount "+vm.getCostRange()+" and";
					}
				}
				
				if(vm.getFromDate() != null && !vm.getFromDate().equals("Invalid date") && vm.getToDate() != null && !vm.getToDate().equals("Invalid date")) {
					profStr += " service_month between "+'\''+vm.getFromDate()+'\''+" and "+'\''+vm.getToDate()+'\''+" and";
				}
				
				profStr += filterStr;
				
				if(profStr.charAt(profStr.length()-1) == 'd' && profStr.charAt(profStr.length()-2) == 'n' && profStr.charAt(profStr.length()-3) == 'a' && profStr.charAt(profStr.length()-4) == ' ') {
					profStr = profStr.substring(0, profStr.length()-4);
				}
				
				profStr += " group by member_name,provider_name,plan_name,medicare_id,service_month";
				
				profStr += sortStr;
				
				profStrCount = "select count(*) from ("+profStr+") A";
				
				Query countQuery = getEntityManager().createNativeQuery(profStrCount);
				profCount = Integer.parseInt(countQuery.getSingleResult().toString());
			}
			
			if(vm.getClaimType().contains("spec claim")) {
				SpecStr = "select dd.plan_name,max(dd.pcp_name),dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month),dd.medicare_id,\n" + 
						"COALESCE(round(sum(behavioral_health + chiropractic_cap + dental_cap + hearing_cap + lab + vision_ophthamalogy + vision_optometry + otc_cap + gym_cap + podiatry_cap + transportation + dermatology),2),0) as spec_cost \n" + 
						"from demographic_detail dd \n";
				
				if( (vm.getPlan() != null && !vm.getPlan().equals("")) || (vm.getGender() != null && !vm.getGender().equals("")) || (vm.getProvider() != null && !vm.getProvider().equals("")) || (vm.getSpeciality() != null && !vm.getSpeciality().equals("")) || (vm.getCostRange() != null && !vm.getCostRange().equals("")) || (vm.getFromDate() != null && !vm.getFromDate().equals("Invalid date") && vm.getToDate() != null && !vm.getToDate().equals("Invalid date")) || !filteredList.isEmpty()) {
					SpecStr = SpecStr + " where";
				}
				if(vm.getPlan() != null && !vm.getPlan().equals("")) {
					SpecStr = SpecStr + " provider="+'\''+vm.getPlan()+'\''+" and";
				}
				if(vm.getGender() != null && !vm.getGender().equals("")) {
					SpecStr = SpecStr + " gender="+'\''+vm.getGender()+'\''+" and";
				}
				if(vm.getProvider() != null && !vm.getProvider().equals("")) {
					SpecStr = SpecStr + " pcp_name="+'\''+vm.getProvider()+'\''+" and";
				}
				if(vm.getSpeciality() != null && !vm.getSpeciality().equals("")) {
					SpecStr = SpecStr + " claim_specialty="+'\''+vm.getSpeciality()+'\''+" and";
				}
				if(vm.getCostRange() != null && !vm.getCostRange().equals("")) {
					if(vm.getCostRange().contains("-")) {
						String arr[] = vm.getCostRange().split("-");
						SpecStr = SpecStr + " paid_amount between "+arr[0]+ " and "+ arr[1]+" and";
					} else {
						SpecStr = SpecStr + " paid_amount "+vm.getCostRange()+" and";
					}
				}
				
				if(vm.getFromDate() != null && !vm.getFromDate().equals("Invalid date") && vm.getToDate() != null && !vm.getToDate().equals("Invalid date")) {
					SpecStr += " service_month between "+'\''+vm.getFromDate()+'\''+" and "+'\''+vm.getToDate()+'\''+" and";
				}
			}
			
			if(vm.getClaimType().contains("rx claim")) {
				rxStr = "select *, COALESCE(round(sum(rd.paid_amount - lics_paid - rep_gap_dscnt),2),0) as rx_claims from  (\n" + 
						"select dd.plan_name,dd.pcp_name,dd.mbi,concat(dd.last_name,' ',dd.first_name) as patientName,min(dd.eligible_month),dd.medicare_id \n" + 
						"from demographic_detail dd \n" + 
						" where dd.provider='OPT' and dd.eligible_month between '2018-05-05' and '2018-11-30' group by dd.plan_name,dd.mbi,dd.pcp_name,dd.last_name,dd.first_name,dd.medicare_id \n" + 
						") A\n" + 
						"left join rx_detail rd on rd.medicare_id = A.medicare_id\n" + 
						"group by A.plan_name, A.mbi, A.patientName, A.pcp_name, A.medicare_id \n" + 
						" order by patientName";
				
				if( (vm.getPlan() != null && !vm.getPlan().equals("")) || (vm.getGender() != null && !vm.getGender().equals("")) || (vm.getProvider() != null && !vm.getProvider().equals("")) || (vm.getSpeciality() != null && !vm.getSpeciality().equals("")) || (vm.getCostRange() != null && !vm.getCostRange().equals("")) || (vm.getFromDate() != null && !vm.getFromDate().equals("Invalid date") && vm.getToDate() != null && !vm.getToDate().equals("Invalid date")) || !filteredList.isEmpty()) {
					profStr = profStr + " where";
				}
				if(vm.getPlan() != null && !vm.getPlan().equals("")) {
					profStr = profStr + " provider="+'\''+vm.getPlan()+'\''+" and";
				}
				if(vm.getGender() != null && !vm.getGender().equals("")) {
					profStr = profStr + " gender="+'\''+vm.getGender()+'\''+" and";
				}
				if(vm.getProvider() != null && !vm.getProvider().equals("")) {
					profStr = profStr + " pcp_name="+'\''+vm.getProvider()+'\''+" and";
				}
				if(vm.getSpeciality() != null && !vm.getSpeciality().equals("")) {
					profStr = profStr + " claim_specialty="+'\''+vm.getSpeciality()+'\''+" and";
				}
				if(vm.getCostRange() != null && !vm.getCostRange().equals("")) {
					if(vm.getCostRange().contains("-")) {
						String arr[] = vm.getCostRange().split("-");
						profStr = profStr + " paid_amount between "+arr[0]+ " and "+ arr[1]+" and";
					} else {
						profStr = profStr + " paid_amount "+vm.getCostRange()+" and";
					}
				}
				
				if(vm.getFromDate() != null && !vm.getFromDate().equals("Invalid date") && vm.getToDate() != null && !vm.getToDate().equals("Invalid date")) {
					profStr += " service_month between "+'\''+vm.getFromDate()+'\''+" and "+'\''+vm.getToDate()+'\''+" and";
				}
				
			}
			
			if(vm.getClaimType().contains("pcp cap")) {
				queryStr = "";
			}
			
			if(vm.getClaimType().contains("reinsurance prem")) {
				queryStr = "";
			}
			
			String unionQuery = "";
			if(instStr != "") {
				if(profStr != "") {
					unionQuery = "("+instStr+") union ("+profStr+") limit "+start+","+end;
					fileQuery = "("+instStr+") union ("+profStr+")";
				} else {
					unionQuery = instStr+" limit "+start+","+end;
					fileQuery = instStr;
				}
			} else {
				unionQuery = profStr+" limit "+start+","+end;
				fileQuery = profStr;
			}
			
			
			System.out.println(unionQuery);
			Query query = getEntityManager().createNativeQuery(unionQuery);
			listFinal.addAll(query.getResultList());
			
			totalCount = instCount + profCount;
			noOfPages = totalCount/vm.getPageSize();
			if(totalCount % vm.getPageSize() > 0)
				noOfPages++;
			
		} else {
			instStr = "select distinct round(sum(paid_amount),2),member_name,provider_name,plan_name,medicare_id,service_month from inst_claim_detail";
			profStr = "select distinct round(sum(paid_amount),2),member_name,provider_name,plan_name,medicare_id,service_month from prof_claim_detail";
			if( (vm.getPlan() != null && !vm.getPlan().equals("")) || (vm.getGender() != null && !vm.getGender().equals("")) || (vm.getProvider() != null && !vm.getProvider().equals("")) || (vm.getSpeciality() != null && !vm.getSpeciality().equals("")) || (vm.getCostRange() != null && !vm.getCostRange().equals("")) || (vm.getFromDate() != null && !vm.getFromDate().equals("Invalid date") && vm.getToDate() != null && !vm.getToDate().equals("Invalid date")) || !filteredList.isEmpty() ) {
				instStr = instStr + " where";
				profStr = profStr + " where";
			}
			if(vm.getPlan() != null && !vm.getPlan().equals("")) {
				instStr = instStr + " provider="+'\''+vm.getPlan()+'\''+" and";
				profStr = profStr + " provider="+'\''+vm.getPlan()+'\''+" and";
			}
			if(vm.getGender() != null && !vm.getGender().equals("")) {
				instStr = instStr + " gender="+'\''+vm.getGender()+'\''+" and";
				profStr = profStr + " gender="+'\''+vm.getGender()+'\''+" and";
			}
			if(vm.getProvider() != null && !vm.getProvider().equals("")) {
				instStr = instStr + " pcp_name="+'\''+vm.getProvider()+'\''+" and";
				profStr = profStr + " pcp_name="+'\''+vm.getProvider()+'\''+" and";
			}
			if(vm.getSpeciality() != null && !vm.getSpeciality().equals("")) {
				instStr = instStr + " attending_physician_spec="+'\''+vm.getSpeciality()+'\''+" and";
				profStr = profStr + " claim_specialty="+'\''+vm.getSpeciality()+'\''+" and";
			}
			if(vm.getCostRange() != null && !vm.getCostRange().equals("")) {
				if(vm.getCostRange().contains("-")) {
					String arr[] = vm.getCostRange().split("-");
					instStr = instStr + " paid_amount between "+arr[0]+ " and "+ arr[1]+" and";
					profStr = profStr + " paid_amount between "+arr[0]+ " and "+ arr[1]+" and";
				} else {
					instStr = instStr + " paid_amount "+vm.getCostRange()+" and";
					profStr = profStr + " paid_amount "+vm.getCostRange()+" and";
				}
			}
			
			if(vm.getFromDate() != null && !vm.getFromDate().equals("Invalid date") && vm.getToDate() != null && !vm.getToDate().equals("Invalid date")) {
				instStr += " service_month between "+'\''+vm.getFromDate()+'\''+" and "+'\''+vm.getToDate()+'\''+" and";
				profStr += " service_month between "+'\''+vm.getFromDate()+'\''+" and "+'\''+vm.getToDate()+'\''+" and";
			}
			
			instStr += filterStr;
			profStr += filterStr;
			
			if(instStr.charAt(instStr.length()-1) == 'd' && instStr.charAt(instStr.length()-2) == 'n' && instStr.charAt(instStr.length()-3) == 'a' && instStr.charAt(instStr.length()-4) == ' ') {
				instStr = instStr.substring(0, instStr.length()-4);
			}
			if(profStr.charAt(profStr.length()-1) == 'd' && profStr.charAt(profStr.length()-2) == 'n' && profStr.charAt(profStr.length()-3) == 'a' && profStr.charAt(profStr.length()-4) == ' ') {
				profStr = profStr.substring(0, profStr.length()-4);
			}
			
			instStr += " group by member_name,provider_name,plan_name,medicare_id,service_month";
			profStr += " group by member_name,provider_name,plan_name,medicare_id,service_month";
			
			instStr += sortStr;
			profStr += sortStr;
			
			instStrCount = "select count(*) from ("+instStr+") A";
			profStrCount = "select count(*) from ("+profStr+") A";
			
			//System.out.println(instStr);
			//System.out.println(profStr);
			String unionQuery = "("+instStr+") union ("+profStr+") limit "+start+","+end;
			fileQuery = "("+instStr+") union ("+profStr+")";
			System.out.println(unionQuery);
			Query query = getEntityManager().createNativeQuery(unionQuery);
			listFinal.addAll(query.getResultList());
			
			Query countQuery = getEntityManager().createNativeQuery(instStrCount);
			profCount = Integer.parseInt(countQuery.getSingleResult().toString());
			countQuery = getEntityManager().createNativeQuery(profStrCount);
			profCount = Integer.parseInt(countQuery.getSingleResult().toString());
			
			totalCount = instCount + profCount;
			noOfPages = totalCount/vm.getPageSize();
			if(totalCount % vm.getPageSize() > 0)
				noOfPages++;
		}
		responseVM.setDataList(listFinal);
		responseVM.setNoOfPages(noOfPages);
		responseVM.setTotalCount(totalCount);
		responseVM.setFileQuery(fileQuery);
		return responseVM;
	}
	
	@Override
	@Transactional
	public List<Object[]> getDataForFile(String fileQuery) {
		System.out.println(fileQuery);
		Query query = getEntityManager().createNativeQuery(fileQuery);
		return query.getResultList();
	}
	
	@Override
	@Transactional
	public List<Object[]> getDuplicateClaimsAllData(String fileQuery) {
		System.out.println(fileQuery);
		Query query = getEntityManager().createNativeQuery(fileQuery);
		return query.getResultList();
	}
	
	public ReportResponseVM getDuplicateClaimsData(ReportVM vm) {
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
		String filterStr = "";
		String filterColumnName = "";
		for(int i=0;i<filteredList.size();i++) {
			if(filteredList.get(i).getId().equals("subscriberId")) {
				filterColumnName = "medicare_id";
			}
			if(filteredList.get(i).getId().equals("planName")) {
				filterColumnName = "plan_name";
			}
			if(filteredList.get(i).getId().equals("patientName")) {
				filterColumnName = "member_name";
			}
			if(filteredList.get(i).getId().equals("pcp")) {
				filterColumnName = "pcp_name";
			}
			if(filteredList.get(i).getId().equals("eligibleMonth")) {
				filterColumnName = "service_month";
			}
			if(filteredList.get(i).getId().equals("termedMonth")) {
				filterColumnName = "";
			}
			if(filteredList.get(i).getId().equals("claimDate")) {
				filterColumnName = "first_service_date";
			}
			if(filteredList.get(i).getId().equals("duplicativeCost")) {
				filterColumnName = "paid_amount";
			}
			
				if(!filterColumnName.equals("")) {
					filterStr += " and "+filterColumnName+" like "+'\''+"%"+filteredList.get(i).getValue()+"%"+'\'';
				}
			
		}
		
		String sortStr = "";
		String sortColName = "";
		if(!sortedList.isEmpty()) {
			if(sortedList.get(0).getId().equals("subscriberId")) {
				sortColName = "medicare_id";
			}
			if(sortedList.get(0).getId().equals("planName")) {
				sortColName = "plan_Name";
			}
			if(sortedList.get(0).getId().equals("patientName")) {
				sortColName = "member_name";
			}
			if(sortedList.get(0).getId().equals("pcp")) {
				sortColName = "pcp_name";
			}
			if(sortedList.get(0).getId().equals("eligibleMonth")) {
				sortColName = "service_month";
			}
			if(sortedList.get(0).getId().equals("termedMonth")) {
				sortColName = "";
			}
			if(sortedList.get(0).getId().equals("claimDate")) {
				sortColName = "first_service_date";
			}
			if(sortedList.get(0).getId().equals("duplicativeCost")) {
				sortColName = "paid_amount";
			}
			
			if(!sortedList.get(0).getId().equals("termedMonth")) {
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
		
		String sortQryStr = " order by paid_amount desc limit "+start+","+end;
		String sortCountQryStr = " order by paid_amount desc ";
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
		
			queryStr = "select * from (\n" + 
					"select medicare_id, plan_name, principal_diagnosis, member_name, pcp_name, service_month, first_service_date, paid_amount, count(*) as number,'PROF' as claimType from prof_claim_detail \n" + 
					conditionStr+
					" group by medicare_id, paid_amount, pcp_name, plan_name, member_name, principal_diagnosis, first_service_date, service_month \n" + 
					"union\n" + 
					"select medicare_id, plan_name, principal_diagnosis, member_name, pcp_name, service_month, first_service_date, paid_amount, count(*) as number,'INST' as claimType from inst_claim_detail \n" + 
					conditionStr+
					" group by medicare_id, paid_amount, pcp_name, plan_name, member_name, principal_diagnosis, first_service_date, service_month \n" + 
					") A where number > 1 and paid_amount > 0 "+filterStr;
			
			countQueryStr = "select count(*) from \n" + 
					"(\n" + 
					queryStr+ 
					") A ";
		
		System.out.println(queryStr+sortQryStr);
		System.out.println(countQueryStr+sortCountQryStr);
		
		Query query = getEntityManager().createNativeQuery(queryStr+sortQryStr);
		queryResult = query.getResultList();
		
		Query countQuery = getEntityManager().createNativeQuery(countQueryStr+sortCountQryStr);
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
	
	public ReportResponseVM getDuplicateClaimsExpandData(ReportVM vm) {
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
		String filterStr = "";
		String filterColumnName = "";
		for(int i=0;i<filteredList.size();i++) {
			if(filteredList.get(i).getId().equals("claimId")) {
				filterColumnName = "claim_id";
			}
			if(filteredList.get(i).getId().equals("claimDate")) {
				filterColumnName = "first_service_date";
			}
			if(filteredList.get(i).getId().equals("claimType")) {
				filterColumnName = "";
			}
			if(filteredList.get(i).getId().equals("clinicName")) {
				filterColumnName = "clinic_facility_name";
			}
			if(filteredList.get(i).getId().equals("providerName")) {
				filterColumnName = "pcp_name";
			}
			if(filteredList.get(i).getId().equals("betosCat")) {
				filterColumnName = "betos_cat";
			}
			if(filteredList.get(i).getId().equals("drgCode")) {
				filterColumnName = "drg_code";
			}
			if(filteredList.get(i).getId().equals("icdCodes")) {
				filterColumnName = "principal_diagnosis";
			}
			if(filteredList.get(i).getId().equals("hccCodes")) {
				filterColumnName = "";
			}
			if(filteredList.get(i).getId().equals("cost")) {
				filterColumnName = "paid_amount";
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
				sortColName = "first_service_date";
			}
			if(sortedList.get(0).getId().equals("claimType")) {
				sortColName = "";
			}
			if(sortedList.get(0).getId().equals("clinicName")) {
				sortColName = "clinic_facility_name";
			}
			if(sortedList.get(0).getId().equals("providerName")) {
				sortColName = "pcp_name";
			}
			if(sortedList.get(0).getId().equals("betosCat")) {
				sortColName = "betos_cat";
			}
			if(sortedList.get(0).getId().equals("drgCode")) {
				sortColName = "drg_code";
			}
			if(sortedList.get(0).getId().equals("icdCodes")) {
				sortColName = "principal_diagnosis";
			}
			if(sortedList.get(0).getId().equals("hccCodes")) {
				sortColName = "";
			}
			if(sortedList.get(0).getId().equals("cost")) {
				sortColName = "paid_amount";
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
		
		String sortQryStr = " order by pcp_name limit "+start+","+end;
		String sortCountQryStr = " order by pcp_name";
		if(!sortStr.equals("")) {
			sortQryStr = " order by "+sortStr+" limit "+start+","+end;
			sortCountQryStr = " order by "+sortStr;
		}
		
		String queryStr = "",countQueryStr = "",conditionStr = "";
		
		if(!vm.getProvider().equals("all")) {
			conditionStr = conditionStr + " and provider="+'\''+vm.getProvider()+'\''+" ";
		}
		if(!vm.getPcpName().equals("all")) {
			if(!vm.getProvider().equals("all"))
				conditionStr = conditionStr + " and pcp_id="+'\''+vm.getPcpName()+'\''+" ";
			else 
				conditionStr = conditionStr + " and pcp_name="+'\''+vm.getPcpName()+'\''+" ";
		}
		
			if(vm.getClaimTypeValue().equals("INST")) {
				queryStr = "select claim_id,first_service_date,'INST CLAIMS' as claimType,clinic_facility_name,pcp_name,betos_cat,drg_code,principal_diagnosis,paid_amount\n" + 
						" from inst_claim_detail where medicare_id="+'\''+vm.getMedicareId()+'\''+
						" and first_service_date="+'\''+vm.getFirstServiceDate()+'\''+" and service_month="+'\''+vm.getServiceMonth()+'\''+" and paid_amount="+'\''+vm.getPaidAmount()+'\''+conditionStr
						+filterStr;
			} else {
				queryStr = "select claim_id,first_service_date,'PROF CLAIMS' as claimType,clinic_facility_name,pcp_name,betos_cat,'' as drg_code,principal_diagnosis,paid_amount\n" + 
						" from prof_claim_detail where medicare_id="+'\''+vm.getMedicareId()+'\''+
						" and first_service_date="+'\''+vm.getFirstServiceDate()+'\''+" and service_month="+'\''+vm.getServiceMonth()+'\''+" and paid_amount="+'\''+vm.getPaidAmount()+'\''+conditionStr
						+filterStr;
			}
		
		
		countQueryStr = "select count(*) from \n" + 
				"(\n" + 
				queryStr+ 
				") A ";
		System.out.println(queryStr+sortQryStr);
		Query query = getEntityManager().createNativeQuery(queryStr+sortQryStr);
		queryResult = query.getResultList();
		
		System.out.println(queryStr+sortQryStr);
		System.out.println(countQueryStr+sortCountQryStr);
		Query countQuery = getEntityManager().createNativeQuery(countQueryStr+sortCountQryStr);
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
	
	public ReportResponseVM getAdmissionsReportData(ReportVM vm) {
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
			if(filteredList.get(i).getId().equals("patientName")) {
				filterColumnName = "member_name";
			}
			if(filteredList.get(i).getId().equals("subscriberId")) {
				filterColumnName = "subscriber_id";
			}
			if(filteredList.get(i).getId().equals("pcpName")) {
				filterColumnName = "pcp_name";
			}
			if(filteredList.get(i).getId().equals("eligibleMonth")) {
				filterColumnName = "eligibleMonth";
			}
			if(filteredList.get(i).getId().equals("totalNoOfAdmissions")) {
				filterColumnName = "admissions";
			}
			if(filteredList.get(i).getId().equals("totalCost")) {
				filterColumnName = "total";
			}
			
				if(!filterColumnName.equals("") && !filterColumnName.equals("admissions") && !filterColumnName.equals("total") && !filterColumnName.equals("eligibleMonth")) {
					filterStr += " and "+filterColumnName+" like "+'\''+"%"+filteredList.get(i).getValue()+"%"+'\''+" ";
				}
			
				if(!filterColumnName.equals("") && (filterColumnName.equals("admissions") || filterColumnName.equals("total") || filterColumnName.equals("eligibleMonth"))) {
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
			if(sortedList.get(0).getId().equals("subscriberId")) {
				sortColName = "subscriber_id";
			}
			if(sortedList.get(0).getId().equals("patientName")) {
				sortColName = "member_name";
			}
			if(sortedList.get(0).getId().equals("pcpName")) {
				sortColName = "pcp_name";
			}
			if(sortedList.get(0).getId().equals("eligibleMonth")) {
				sortColName = "eligibleMonth";
			}
			if(sortedList.get(0).getId().equals("totalNoOfAdmissions")) {
				sortColName = "admissions";
			}
			if(sortedList.get(0).getId().equals("totalCost")) {
				sortColName = "total";
			}
			if(!sortedList.get(0).getId().equals("totalCost")) {
				sortStr+= " "+sortColName+" ";
				if(sortedList.get(0).isDesc()) {
					sortStr += "desc";
				} else {
					sortStr += "asc";
				}
			} else {
				if(!sortedList.get(0).isDesc()) {
					sortStr+= " "+sortColName+" ";
					sortStr += "asc";
				}
			}
		}
		
		List<Object[]> queryResult = new ArrayList<>();
		int start,end,noOfPages = 0,totalCount = 0;
		end = vm.getPageSize() * vm.getPage();
		start = end - vm.getPageSize();
		end = vm.getPageSize();
		
		String sortQryStr = "order by total desc limit "+start+","+end;
		String sortCountQryStr = "order by total desc";
		if(!sortStr.equals("")) {
			sortQryStr = "order by "+sortStr+" limit "+start+","+end;
			sortCountQryStr = "order by "+sortStr;
		}
		
		String queryStr = "",countQueryStr = "",conditionStr  = "";
		
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
		
			queryStr = "select member_name,medicare_id,pcp_name,max(service_date) as eligibleMonth, round(sum(distinct paid_amount),0) as total,count(distinct first_service_date) as admissions from inst_claim_detail\n" + 
					"where admitting_diagnosis is not null "+conditionStr + filterStr +
					" group by member_name,medicare_id,pcp_name \n" + 
					havingStr;
			
			countQueryStr = "select count(*) from \n" + 
					"(\n" + 
					queryStr;
		
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
	
	public ReportResponseVM getPatientVisitReportData(ReportVM vm) {
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
			if(filteredList.get(i).getId().equals("patientName")) {
				filterColumnName = "member_name";
			}
			if(filteredList.get(i).getId().equals("hicn")) {
				filterColumnName = "medicare_id";
			}
			if(filteredList.get(i).getId().equals("pcpName")) {
				filterColumnName = "pcp_name";
			}
			if(filteredList.get(i).getId().equals("termedMonth")) {
				filterColumnName = "";
			}
			if(filteredList.get(i).getId().equals("ipaEffectiveDate")) {
				filterColumnName = "";
			}
			if(filteredList.get(i).getId().equals("totalErVisits")) {
				filterColumnName = "totalVisits";
			}
			if(filteredList.get(i).getId().equals("totalCost")) {
				filterColumnName = "totalCost";
			}
			
			if(!filterColumnName.equals("") && !filterColumnName.equals("totalVisits") && !filterColumnName.equals("totalCost")) {
				filterStr += " and "+filterColumnName+" like "+'\''+"%"+filteredList.get(i).getValue()+"%"+'\''+" ";
			}
		
			if(!filterColumnName.equals("") && (filterColumnName.equals("totalVisits") || filterColumnName.equals("totalCost") )) {
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
			if(sortedList.get(0).getId().equals("patientName")) {
				sortColName = "member_name";
			}
			if(sortedList.get(0).getId().equals("hicn")) {
				sortColName = "medicare_id";
			}
			if(sortedList.get(0).getId().equals("pcpName")) {
				sortColName = "pcp_name";
			}
			if(sortedList.get(0).getId().equals("termedMonth")) {
				sortColName = "";
			}
			if(sortedList.get(0).getId().equals("ipaEffectiveDate")) {
				sortColName = "";
			}
			if(sortedList.get(0).getId().equals("totalErVisits")) {
				sortColName = "totalVisits";
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
		
		String sortQryStr = " order by totalCost desc limit "+start+","+end;
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
		
			queryStr = "select  member_name,medicare_id,pcp_name,'' as termedMonth,'' as ipaDate,count(distinct first_service_date) as totalVisits,round(sum(total),0) as totalCost from \n" + 
					"(select member_name,medicare_id,pcp_name,round(sum(paid_amount),2) as total,first_service_date \n" + 
					"from inst_claim_detail where betos_cat like '%EMERGENCY ROOM%' "+conditionStr+" "+filterStr+" group by member_name,pcp_name,medicare_id,first_service_date \n" + 
					"union \n" + 
					"select member_name,medicare_id,pcp_name,round(sum(paid_amount),2) as total,first_service_date \n" + 
					"from prof_claim_detail where betos_cat like '%EMERGENCY ROOM%' "+conditionStr+" "+filterStr+" group by member_name,pcp_name,medicare_id,first_service_date) A\n" + 
					" group by member_name,pcp_name,medicare_id " + havingStr;
			
			countQueryStr = "select count(*) from \n" + 
					"(\n" + 
					queryStr;
		
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
	
	public ReportResponseVM getPatientVisitExpandReportData(ReportVM vm) {
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
		String filterStr = "";
		String filterColumnName = "";
		for(int i=0;i<filteredList.size();i++) {
			if(filteredList.get(i).getId().equals("claimId")) {
				filterColumnName = "claim_id";
			}
			if(filteredList.get(i).getId().equals("claimDate")) {
				filterColumnName = "first_service_date";
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
				filterColumnName = "paid_amount";
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
				sortColName = "first_service_date";
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
				sortColName = "paid_amount";
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
		
		String sortQryStr = " order by paid_amount desc limit "+start+","+end;
		String sortCountQryStr = " order by paid_amount desc ";
		if(!sortStr.equals("")) {
			sortQryStr = " order by "+sortStr+" limit "+start+","+end;
			sortCountQryStr = " order by "+sortStr;
		}
		
		String queryStr = "",countQueryStr = "";
		
		if(vm.getProvider().equals("all") && vm.getYear().equals("all")) {
			
			queryStr = "select claim_id,first_service_date,claim_type,clinic_facility_name,pcp_name,drg_code,betos_cat,paid_amount,principal_diagnosis from (\n"+
					"select distinct claim_id,first_service_date,claim_type,clinic_facility_name,pcp_name,drg_code,betos_cat,paid_amount,principal_diagnosis from inst_claim_detail where\n" + 
					" medicare_id="+'\''+vm.getMedicareId()+'\''+" and betos_cat like '%EMERGENCY ROOM%'\n" +filterStr+ 
					" union \n" + 
					"select distinct claim_id,first_service_date,claim_type,clinic_facility_name,pcp_name,'' as drg_code,betos_cat,paid_amount,principal_diagnosis from prof_claim_detail where\n" + 
					" medicare_id="+'\''+vm.getMedicareId()+'\''+" and betos_cat like '%EMERGENCY ROOM%'"+filterStr+") A";
			
			countQueryStr = "select count(*) from \n" + 
					"(\n" + 
					queryStr;
		} else {
			if(vm.getProvider().equals("all")) {
				queryStr = "select claim_id,first_service_date,claim_type,clinic_facility_name,pcp_name,drg_code,betos_cat,paid_amount,principal_diagnosis from (\n"+
						"select distinct claim_id,first_service_date,claim_type,clinic_facility_name,pcp_name,drg_code,betos_cat,paid_amount,principal_diagnosis from inst_claim_detail where first_service_date like "+'\''+vm.getYear()+"%"+'\''+"\n" + 
						" and medicare_id="+'\''+vm.getMedicareId()+'\''+" and betos_cat like '%EMERGENCY ROOM%'\n" +filterStr+ 
						" union \n" + 
						"select distinct claim_id,first_service_date,claim_type,clinic_facility_name,pcp_name,'' as drg_code,betos_cat,paid_amount,principal_diagnosis from prof_claim_detail where first_service_date like "+'\''+vm.getYear()+"%"+'\''+"\n" + 
						" and medicare_id="+'\''+vm.getMedicareId()+'\''+" and betos_cat like '%EMERGENCY ROOM%'"+filterStr+") A";
			}
			if(vm.getYear().equals("all")) {
				
				queryStr = "select claim_id,first_service_date,claim_type,clinic_facility_name,pcp_name,drg_code,betos_cat,paid_amount,principal_diagnosis from (\n"+
						"select distinct claim_id,first_service_date,claim_type,clinic_facility_name,pcp_name,drg_code,betos_cat,paid_amount,principal_diagnosis from inst_claim_detail where provider="+'\''+vm.getProvider()+'\''+"\n" + 
						" and medicare_id="+'\''+vm.getMedicareId()+'\''+" and betos_cat like '%EMERGENCY ROOM%'\n" +filterStr+ 
						" union \n" + 
						"select distinct claim_id,first_service_date,claim_type,clinic_facility_name,pcp_name,'' as drg_code,betos_cat,paid_amount,principal_diagnosis from prof_claim_detail where provider="+'\''+vm.getProvider()+'\''+"\n" + 
						" and medicare_id="+'\''+vm.getMedicareId()+'\''+" and betos_cat like '%EMERGENCY ROOM%'"+filterStr+") A";
			}
			if(!vm.getProvider().equals("all") && !vm.getYear().equals("all")) {
				
				queryStr = "select claim_id,first_service_date,claim_type,clinic_facility_name,pcp_name,drg_code,betos_cat,paid_amount,principal_diagnosis from (\n"+
						"select distinct claim_id,first_service_date,claim_type,clinic_facility_name,pcp_name,drg_code,betos_cat,paid_amount,principal_diagnosis from inst_claim_detail where provider="+'\''+vm.getProvider()+'\''+" and first_service_date like "+'\''+vm.getYear()+"%"+'\''+"\n" + 
						" and medicare_id="+'\''+vm.getMedicareId()+'\''+" and betos_cat like '%EMERGENCY ROOM%'\n" +filterStr+ 
						" union \n" + 
						"select distinct claim_id,first_service_date,claim_type,clinic_facility_name,pcp_name,'' as drg_code,betos_cat,paid_amount,principal_diagnosis from prof_claim_detail where provider="+'\''+vm.getProvider()+'\''+" and first_service_date like "+'\''+vm.getYear()+"%"+'\''+"\n" + 
						" and medicare_id="+'\''+vm.getMedicareId()+'\''+" and betos_cat like '%EMERGENCY ROOM%'"+filterStr+") A";
				
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
	
	public List<Object[]> getAdmissionsHeaderReportData(AdmissionHeaderReportFileVM vm) {
	
		String conditionStr="", countQueryStr="";
		
		if(!vm.getProvider().equals("all"))
			conditionStr+=" and provider = '"+vm.getProvider()+"' ";
		
		if(!vm.getPcpName().equals("all")) {
			if(!vm.getProvider().equals("all"))
				conditionStr+=" and pcp_id = '"+vm.getPcpName()+"' ";
			else
				conditionStr=" and pcp_name = '"+vm.getPcpName()+"' ";
		}
		if(!vm.getYear().equals("all"))
			conditionStr+=" and first_service_date like '"+vm.getYear()+"%' ";
		
		List<Object[]> queryResult = new ArrayList<>();
		
		String queryStr="select member_name,medicare_id,pcp_name,service_date as eligibleMonth, paid_amount as total,first_service_date \r\n" + 
						"from inst_claim_detail\r\n" + 
						"where admitting_diagnosis is not null "+conditionStr+"\r\n" + 
						"order by total desc";
	
			countQueryStr = "select count(*) from (\n" + queryStr;	
			System.out.println(queryStr);	
		Query query = getEntityManager().createNativeQuery(queryStr);
		queryResult = query.getResultList();
		
		
		System.out.println(countQueryStr+ ") A");
		Query countQuery = getEntityManager().createNativeQuery(countQueryStr+" ) A");
		return queryResult;
	}
	
	public ReportResponseVM getAdmissionsReportExpandData(ReportVM vm) {
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
				filterColumnName = "first_service_date";
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
				filterColumnName = "";
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
				filterColumnName = "paid_amount";
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
				sortColName = "first_service_date";
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
				sortColName = "";
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
				sortColName = "paid_amount";
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
		
		String sortQryStr = " order by paid_amount desc limit "+start+","+end;
		String sortCountQryStr = " order by paid_amount desc ";
		if(!sortStr.equals("")) {
			sortQryStr = " order by "+sortStr+" limit "+start+","+end;
			sortCountQryStr = " order by "+sortStr;
		}
		
		String queryStr = "",countQueryStr = "";
			
			if(vm.getProvider().equals("all") && vm.getYear().equals("all")) {
				queryStr = "select distinct claim_id,first_service_date,claim_type,clinic_facility_name,pcp_name,drg_code,betos_cat,paid_amount from inst_claim_detail \n" + 
						"where medicare_id="+'\''+vm.getSubscriberIdAdmissionsExpand()+'\''+" and admitting_diagnosis is not null" 
						+ filterStr ;
			} else {
				if(!vm.getYear().equals("all")) {
					queryStr = "select distinct claim_id,first_service_date,claim_type,clinic_facility_name,pcp_name,drg_code,betos_cat,paid_amount from inst_claim_detail \n" + 
							"where first_service_date like "+'\''+vm.getYear()+"%"+'\''+" and medicare_id="+'\''+vm.getSubscriberIdAdmissionsExpand()+'\''+" and admitting_diagnosis is not null" 
							+ filterStr ;
				}
				if(!vm.getProvider().equals("all")) {
					queryStr = "select distinct claim_id,first_service_date,claim_type,clinic_facility_name,pcp_name,drg_code,betos_cat,paid_amount from inst_claim_detail \n" + 
							"where provider="+'\''+vm.getProvider()+'\''+" and medicare_id="+'\''+vm.getSubscriberIdAdmissionsExpand()+'\''+" and admitting_diagnosis is not null" 
							+ filterStr ;
				}
				if(!vm.getProvider().equals("all") && !vm.getYear().equals("all")) {
					queryStr = "select distinct claim_id,first_service_date,claim_type,clinic_facility_name,pcp_name,drg_code,betos_cat,paid_amount from inst_claim_detail \n" + 
							"where provider="+'\''+vm.getProvider()+'\''+" and first_service_date like "+'\''+vm.getYear()+"%"+'\''+" and medicare_id="+'\''+vm.getSubscriberIdAdmissionsExpand()+'\''+" and admitting_diagnosis is not null" 
							+ filterStr ;
				}
			}
			
			countQueryStr = "select count(*) from (\n" + queryStr;	
		
		Query query = getEntityManager().createNativeQuery(queryStr+sortQryStr);
		queryResult = query.getResultList();
		
		System.out.println(queryStr+sortQryStr);
		System.out.println(countQueryStr+sortCountQryStr+ ") A");
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
	
	
	public ReportResponseVM getSpecialistComparisonReportData(ReportVM vm) {
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
		String filterStr = "";
		String filterColumnName = "";
		for(int i=0;i<filteredList.size();i++) {
			if(filteredList.get(i).getId().equals("specialityCode")) {
				filterColumnName = "claim_specialty";
			}
			
			if(!filterColumnName.equals("")) {
				filterStr = filterStr + " and "+ filterColumnName+" like "+'\''+"%"+filteredList.get(i).getValue()+"%"+'\''+" ";
			}
				
		}
		
		String sortStr = "";
		String sortColName = "";
		if(!sortedList.isEmpty()) {
			if(sortedList.get(0).getId().equals("specialityCode")) {
				sortColName = "claim_specialty";
			}
			if(sortedList.get(0).getId().equals("numberOfClaims")) {
				sortColName = "noOfClaims";
			}
			if(sortedList.get(0).getId().equals("numberOfBeneficiaries")) {
				sortColName = "noOfBenef";
			}
			if(sortedList.get(0).getId().equals("costPerClaim")) {
				sortColName = "costPerClaim";
			}
			if(sortedList.get(0).getId().equals("costPerBeneficiary")) {
				sortColName = "costPerBenef";
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
		
		
			if(!filterStr.equals("") && conditionStr.equals(""))
				filterStr = " where "+filterStr.substring(4);
			queryStr = "select claim_specialty,count(distinct claim_id) as noOfClaims,count(distinct member_name) as noOfBenef,round(sum(paid_amount)/count(distinct claim_id),2) as costPerClaim,\n" + 
					"round(sum(paid_amount)/count(distinct member_name),2) as costPerBenef,round(sum(paid_amount),2) as total,count(pcp_name) as noOfPcp\n" + 
					"from prof_claim_detail "+conditionStr+" "+filterStr+" group by claim_specialty having claim_specialty is not null";
			
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
	
	public ReportResponseVM getSpecialistComparisonExpandReportData(ReportVM vm) {
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
		String filterStr = "",havingStr = "";
		String filterColumnName = "";
		for(int i=0;i<filteredList.size();i++) {
			if(filteredList.get(i).getId().equals("practiceName")) {
				filterColumnName = "provider_name";
			}
			if(filteredList.get(i).getId().equals("specialityType")) {
				filterColumnName = "claim_specialty";
			}
			if(filteredList.get(i).getId().equals("numberOfClaims")) {
				filterColumnName = "noOfClaims";
			}
			if(filteredList.get(i).getId().equals("averageCostPerClaim")) {
				filterColumnName = "avgCost";
			}
			if(filteredList.get(i).getId().equals("cost")) {
				filterColumnName = "cost";
			}
			
			if(filterColumnName.equals("provider_name") || filterColumnName.equals("claim_specialty")) {
				filterStr += " and "+filterColumnName+" like "+'\''+"%"+filteredList.get(i).getValue()+"%"+'\''+" ";
			}
				
			if(!filterColumnName.equals("") && !filterColumnName.equals("provider_name") && !filterColumnName.equals("claim_specialty")) {
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
			if(sortedList.get(0).getId().equals("practiceName")) {
				sortColName = "provider_name";
			}
			if(sortedList.get(0).getId().equals("specialityType")) {
				sortColName = "claim_specialty";
			}
			if(sortedList.get(0).getId().equals("numberOfClaims")) {
				sortColName = "noOfClaims";
			}
			if(sortedList.get(0).getId().equals("averageCostPerClaim")) {
				sortColName = "avgCost";
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
		
		
		queryStr ="select provider_name,claim_specialty,sum(noOfClaims) as noOfClaims,round(sum(cost)/sum(noOfClaims),2) as avgCost,sum(cost) as cost from ("+ 
				"select provider_name,claim_specialty,count(distinct claim_id) as noOfClaims,member_name,round(round(sum(paid_amount),2)/count(distinct claim_id),2) as avgCost,round(sum(paid_amount),2) as cost from prof_claim_detail \n" + 
					"where claim_specialty="+'\''+vm.getSpeciality()+'\''+conditionStr+" "+filterStr+" group by provider_name,claim_specialty,member_name,provider_name "+havingStr
					+"\n ) A group by provider_name ";
		
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
	
	public ReportResponseVM getSpecialistComparisonExpandPracticeReportData(ReportVM vm) {
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
		String filterStr = "",havingStr = "";
		String filterColumnName = "";
		for(int i=0;i<filteredList.size();i++) {
			if(filteredList.get(i).getId().equals("practiceName")) {
				filterColumnName = "provider_name";
			}
			if(filteredList.get(i).getId().equals("specialityType")) {
				filterColumnName = "claim_specialty";
			}
			if(filteredList.get(i).getId().equals("patientName")) {
				filterColumnName = "member_name";
			}
			if(filteredList.get(i).getId().equals("pcpName")) {
				filterColumnName = "pcp_name";
			}
			if(filteredList.get(i).getId().equals("numberOfClaims")) {
				filterColumnName = "noOfClaims";
			}
			if(filteredList.get(i).getId().equals("averageCostPerClaim")) {
				filterColumnName = "avgCost";
			}
			if(filteredList.get(i).getId().equals("cost")) {
				filterColumnName = "cost";
			}
			
			if(filterColumnName.equals("provider_name") || filterColumnName.equals("claim_specialty") || filterColumnName.equals("member_name") || filterColumnName.equals("pcp_name")) {
				filterStr += " and "+filterColumnName+" like "+'\''+"%"+filteredList.get(i).getValue()+"%"+'\''+" ";
			}
				
			if(filterColumnName.equals("noOfClaims") || filterColumnName.equals("avgCost") || filterColumnName.equals("cost")) {
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
			if(sortedList.get(0).getId().equals("practiceName")) {
				sortColName = "provider_name";
			}
			if(sortedList.get(0).getId().equals("specialityType")) {
				sortColName = "claim_specialty";
			}
			if(sortedList.get(0).getId().equals("patientName")) {
				sortColName = "member_name";
			}
			if(sortedList.get(0).getId().equals("pcpName")) {
				sortColName = "pcp_name";
			}
			if(sortedList.get(0).getId().equals("numberOfClaims")) {
				sortColName = "noOfClaims";
			}
			if(sortedList.get(0).getId().equals("averageCostPerClaim")) {
				sortColName = "avgCost";
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
		
		
		queryStr = "select provider_name,claim_specialty,member_name,pcp_name,count(distinct claim_id) as noOfClaims,round(round(sum(paid_amount),2)/count(distinct claim_id),2) as avgCost,round(sum(paid_amount),2) as cost from prof_claim_detail \n" + 
					"where provider_name="+'\''+vm.getPracticeName()+'\''+conditionStr+" "+filterStr+" group by provider_name,claim_specialty,member_name,pcp_name "+havingStr
					;
		
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
		String fileQuery;
		
		if(sortCountQryStr!="") 
			fileQuery = queryStr+sortCountQryStr;
		else
			fileQuery=queryStr+sortQryStr;
	
	
		
		responseVM.setDataList(queryResult);
		responseVM.setNoOfPages(noOfPages);
		responseVM.setTotalCount(totalCount);
		responseVM.setFileQuery(fileQuery);
		return responseVM;
	}
	
	public ReportResponseVM getSpecialistComparisonExpandPatientReportData(ReportVM vm) {
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
		String filterStr = "",havingStr = "";
		String filterColumnName = "";
		for(int i=0;i<filteredList.size();i++) {
			if(filteredList.get(i).getId().equals("practiceName")) {
				filterColumnName = "provider_name";
			}
			if(filteredList.get(i).getId().equals("specialityType")) {
				filterColumnName = "claim_specialty";
			}
			if(filteredList.get(i).getId().equals("patientName")) {
				filterColumnName = "member_name";
			}
			if(filteredList.get(i).getId().equals("pcpName")) {
				filterColumnName = "pcp_name";
			}
			if(filteredList.get(i).getId().equals("cost")) {
				filterColumnName = "cost";
			}
			
			if(filterColumnName.equals("provider_name") || filterColumnName.equals("claim_specialty") || filterColumnName.equals("member_name") || filterColumnName.equals("pcp_name")) {
				filterStr += " and "+filterColumnName+" like "+'\''+"%"+filteredList.get(i).getValue()+"%"+'\''+" ";
			}
				
			if(filterColumnName.equals("cost")) {
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
			if(sortedList.get(0).getId().equals("practiceName")) {
				sortColName = "provider_name";
			}
			if(sortedList.get(0).getId().equals("specialityType")) {
				sortColName = "claim_specialty";
			}
			if(sortedList.get(0).getId().equals("patientName")) {
				sortColName = "member_name";
			}
			if(sortedList.get(0).getId().equals("pcpName")) {
				sortColName = "pcp_name";
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
		String sortCountQryStr = "";
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
		if(!vm.getMedicareId().equals(""))
			conditionStr= conditionStr+" and medicare_id = '"+vm.getMedicareId()+"' ";
		
		
		queryStr = "select provider_name,claim_specialty,member_name,pcp_name,round((paid_amount),2) as cost, medicare_id from prof_claim_detail "+
					"where provider_name='"+vm.getPracticeName()+"' "+conditionStr+filterStr+havingStr; 
		
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
		String fileQuery;
		
		if(sortCountQryStr!="") 
			fileQuery = queryStr+sortCountQryStr;
		else
			fileQuery=queryStr+sortQryStr;
	
	
		
		responseVM.setDataList(queryResult);
		responseVM.setNoOfPages(noOfPages);
		responseVM.setTotalCount(totalCount);
		responseVM.setFileQuery(fileQuery);
		return responseVM;
	}
	
}
