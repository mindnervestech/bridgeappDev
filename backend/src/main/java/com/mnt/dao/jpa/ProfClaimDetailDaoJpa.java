package com.mnt.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mnt.dao.ProfClaimDetailDao;
import com.mnt.domain.ProfClaimDetail;
import com.mnt.vm.FilteredVM;
import com.mnt.vm.ReportResponseVM;
import com.mnt.vm.ReportVM;
import com.mnt.vm.SortedVM;

@Repository
public class ProfClaimDetailDaoJpa extends BaseDaoJpa<ProfClaimDetail> implements ProfClaimDetailDao {

	public ProfClaimDetailDaoJpa() {
		super(ProfClaimDetail.class, "ProfClaimDetail");
	}
	
	@Override
	@Transactional
	public void deleteOldRecords(String year, String month, String provider) {
		Query query = getEntityManager().createQuery("DELETE FROM ProfClaimDetail pcd where pcd.year=:year and pcd.month=:month and pcd.provider=:provider");
		query.setParameter("year", year);
        query.setParameter("month", month);
        query.setParameter("provider", provider);
        query.executeUpdate();
	}
	
	@Override
	@Transactional
	public List<String> getAllYears() {
		Query query = getEntityManager().createNativeQuery("select distinct year from monthly_totals_data order by year asc");
		return query.getResultList();
	}
	
	@Override
	@Transactional
	public List<String> getAllSpeciality() {
		Query query = getEntityManager().createNativeQuery("select distinct claim_specialty from prof_claim_detail where claim_specialty is not null order by claim_specialty asc");
		return query.getResultList();
	}
	
	@Override
	@Transactional
	public Double getSum(String year, List<String> providerArr) {
		String str = "";
			if(providerArr.contains("all")) {
				str = "select round(sum(paid_amount),2) from prof_claim_detail";
				if(!year.equals("all") && year != null && !year.equals("")) {
					str = str + " where year="+year;
				}
			} else {
				str = "select round(sum(paid_amount),2) from prof_claim_detail where ";
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
		return  query.getSingleResult() == null ? 0: (Double)query.getSingleResult();
	}
	
	@Override
	@Transactional
	public Double getClaimDetails(String qry) {
		Query query = getEntityManager().createNativeQuery(qry);
		return  query.getSingleResult() == null ? 0: (Double)query.getSingleResult();
	}
	
	@Override
	@Transactional
	public ReportResponseVM getSettledMonthsExpandReportData(ReportVM vm) {
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
				filterColumnName = "patientName";
			}
			if(filteredList.get(i).getId().equals("pcpName")) {
				filterColumnName = "pcp_name";
			}
			if(filteredList.get(i).getId().equals("pcpLocation")) {
				filterColumnName = "pcp_location_code";
			}
			if(filteredList.get(i).getId().equals("cost")) {
				filterColumnName = "cost";
			}
			if(filteredList.get(i).getId().equals("claimType")) {
				filterColumnName = "claim_type";
			}
			if(filteredList.get(i).getId().equals("mra")) {
				filterColumnName = "mra";
			}
			
			if(filterColumnName.equals("pcp_name") || filterColumnName.equals("pcp_location_code") || filterColumnName.equals("claim_type")) {
				filterStr += " and "+filterColumnName+" like "+'\''+"%"+filteredList.get(i).getValue()+"%"+'\''+" ";
			}
			
			if(!filterColumnName.equals("") && !filterColumnName.equals("pcp_name") && !filterColumnName.equals("pcp_location_code") && !filterColumnName.equals("claim_type")) {
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
				sortColName = "patientName";
			}
			if(sortedList.get(0).getId().equals("pcpName")) {
				sortColName = "pcp_name";
			}
			if(sortedList.get(0).getId().equals("pcpLocation")) {
				sortColName = "pcp_location_code";
			}
			if(sortedList.get(0).getId().equals("cost")) {
				sortColName = "cost";
			}
			if(sortedList.get(0).getId().equals("claimType")) {
				sortColName = "claim_type";
			}
			if(sortedList.get(0).getId().equals("mra")) {
				sortColName = "mra";
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
		
		String queryStr = "",countQueryStr = "",conditionStr = "",icdCondStr = "",pcdCondStr = "",rdCondStr = "";
		
		if(!vm.getProvider().equals("all")) {
			conditionStr = conditionStr + " and provider="+'\''+vm.getProvider()+'\'';
			icdCondStr = icdCondStr + " and icd.provider="+'\''+vm.getProvider()+'\'';
			pcdCondStr = pcdCondStr + " and pcd.provider="+'\''+vm.getProvider()+'\'';
			rdCondStr = rdCondStr + " and rd.provider="+'\''+vm.getProvider()+'\'';
		}
		if(!vm.getPcpName().equals("all")) {
			if(!vm.getProvider().equals("all")) {
				conditionStr = conditionStr + " and pcp_id="+'\''+vm.getPcpName()+'\'';
				icdCondStr = icdCondStr + " and icd.pcp_id="+'\''+vm.getPcpName()+'\'';
				pcdCondStr = pcdCondStr + " and pcd.pcp_id="+'\''+vm.getPcpName()+'\'';
				rdCondStr = rdCondStr + " and rd.pcp_id="+'\''+vm.getPcpName()+'\'';
				
			} else {
				conditionStr = conditionStr + " and pcp_name="+'\''+vm.getPcpName()+'\'';
				icdCondStr = icdCondStr + " and icd.pcp_name="+'\''+vm.getPcpName()+'\'';
				pcdCondStr = pcdCondStr + " and pcd.pcp_name="+'\''+vm.getPcpName()+'\'';
				rdCondStr = rdCondStr + " and rd.pcp_name="+'\''+vm.getPcpName()+'\'';
			}
				
		}
		
		if(!filterStr.equals(""))
			filterStr = " where "+filterStr.substring(4);
		
			queryStr = "select * from (select concat(last_name,',',first_name) as patientName,pcp_name,pcp_location_code,pcp_cap as cost,'PCP CAP' as claim_type,round(risk_score_partc,2) as mra,'' from demographic_detail \n" + 
					"where eligible_month="+'\''+vm.getSelectedMonth()+'\''+conditionStr+" \n" + 
					"union\n" + 
					"select concat(last_name,',',first_name) as patientName,pcp_name,pcp_location_code,reinsurance_premium as cost,'REINSURANCE PREM' as claim_type,round(risk_score_partc,2) as mra,'' from demographic_detail \n" + 
					"where eligible_month="+'\''+vm.getSelectedMonth()+'\''+conditionStr+" \n" +
					"union \n" + 
					"select concat(last_name,',',first_name) as patientName,pcp_name,pcp_location_code,\n" + 
					"behavioral_health+chiropractic_cap+dental_cap+hearing_cap+lab+vision_ophthamalogy+vision_optometry+otc_cap+gym_cap+podiatry_cap+transportation\n" + 
					"+dermatology as cost,'SPEC CLAIMS' as claim_type,round(risk_score_partc,2) as mra,'' from demographic_detail \n" + 
					"where eligible_month="+'\''+vm.getSelectedMonth()+'\''+conditionStr+" \n" + 
					"union\n" + 
					"select icd.member_name as patientName,icd.pcp_name,icd.pcp_location_code,icd.paid_amount as cost,'INST CLAIMS' as claim_type,round(dd.risk_score_partc,2) as mra,icd.medicare_id from inst_claim_detail icd \n" + 
					"left join demographic_detail dd on dd.medicare_id = icd.medicare_id\n" + 
					"where icd.first_service_date="+'\''+vm.getSelectedMonth()+'\''+icdCondStr+" \n" + 
					"union\n" + 
					" select pcd.member_name as patientName,pcd.pcp_name,pcd.pcp_location_code,pcd.paid_amount as cost,'PROF CLAIMS' as claim_type,round(dd.risk_score_partc,2) as mra,pcd.medicare_id from prof_claim_detail pcd \n" + 
					" left join demographic_detail dd on dd.medicare_id = pcd.medicare_id\n" + 
					" where pcd.first_service_date="+'\''+vm.getSelectedMonth()+'\''+pcdCondStr+" \n" + 
					"union\n" + 
					"select concat(rd.last_name,',',rd.first_name) as patientName,rd.pcp_name,rd.pcp_location_code,rd.paid_amount as cost,'RX CLAIMS' as claim_type,round(dd.risk_score_partc,2) as mra,rd.medicare_id from rx_detail rd \n" + 
					"left join demographic_detail dd on dd.medicare_id = rd.medicare_id\n" + 
					"where rd.date_filled="+'\''+vm.getSelectedMonth()+'\''+rdCondStr+") A" +filterStr+havingStr;
					
		
		
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
}
