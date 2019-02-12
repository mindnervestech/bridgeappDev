package com.mnt.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mnt.dao.MonthlyTotalsReportDao;
import com.mnt.domain.MonthlyTotalsReport;
import com.mnt.vm.ClaimDetailsVM;
import com.mnt.vm.FilteredVM;
import com.mnt.vm.OptionsVM;
import com.mnt.vm.ReportResponseVM;
import com.mnt.vm.ReportVM;
import com.mnt.vm.SortedVM;

@Repository
public class MonthlyTotalsReportDaoJpa extends BaseDaoJpa<MonthlyTotalsReport> implements MonthlyTotalsReportDao {

	public MonthlyTotalsReportDaoJpa() {
		super(MonthlyTotalsReport.class, "MonthlyTotalsReport");
	}
	
	@Override
	@Transactional
	public List<String> getAllYears() {
		Query query = getEntityManager().createNativeQuery("select distinct year from monthly_totals_report");
		return query.getResultList();
	}
	
	@Override
	@Transactional
	public List<Object[]> getMonthlyTotalsReport(ClaimDetailsVM vm) {
		String str = "",conditionStr = "";
		
		/*if(vm.getProvider().equals("all") && vm.getYear().equals("all")) {
			str = "select round(sum(ipa_premium),2),round(sum(total_expenses),2),month_number from monthly_totals_report group by month_number order by month_number";
		} else {
			if(vm.getProvider().equals("all")) {
				str = "select round(sum(ipa_premium),2),round(sum(total_expenses),2),month_number from monthly_totals_report where year="+'\''+vm.getYear()+'\''+" group by month_number order by month_number";
			}
			if(vm.getYear().equals("all")) {
				str = "select round(sum(ipa_premium),2),round(sum(total_expenses),2),month_number from monthly_totals_report where provider="+'\''+vm.getProvider()+'\''+" group by month_number order by month_number";
			}
			if(!vm.getProvider().equals("all") && !vm.getYear().equals("all")) {
				str = "select round(sum(ipa_premium),2),round(sum(total_expenses),2),month_number from monthly_totals_report where provider="+'\''+vm.getProvider()+'\''+" and year="+'\''+vm.getYear()+'\''+" group by month_number order by month_number";
			}
		}*/
		
		/*if(vm.getProvider().equals("all") && vm.getPcpName().equals("all") && vm.getPcpLocation().equals("all")) {
			str="select round(sum(ipa_premium),2),round(sum(total_expenses+(membership*constant_val)),2),month from monthly_totals_data where year="+'\''+vm.getYear()+'\''+" and month like "+'\''+vm.getYear()+"%"+'\''+" group by month order by month";
		} else {
			if(vm.getProvider().equals("all") && vm.getPcpName().equals("all") && !vm.getPcpLocation().equals("all")) {
				str="select round(sum(ipa_premium),2),round(sum(total_expenses+(membership*constant_val)),2),month from monthly_totals_data where year="+'\''+vm.getYear()+'\''+" and month like "+'\''+vm.getYear()+"%"+'\''+" and pcp_location="+'\''+vm.getPcpLocation()+'\''+" group by month order by month";
			}
			if(vm.getProvider().equals("all") && !vm.getPcpName().equals("all") && vm.getPcpLocation().equals("all")) {
				str="select round(sum(ipa_premium),2),round(sum(total_expenses+(membership*constant_val)),2),month from monthly_totals_data where year="+'\''+vm.getYear()+'\''+" and month like "+'\''+vm.getYear()+"%"+'\''+" and pcp_name="+'\''+vm.getPcpName()+'\''+" group by month order by month";
			}
			if(!vm.getProvider().equals("all") && vm.getPcpName().equals("all") && vm.getPcpLocation().equals("all")) {
				str="select round(sum(ipa_premium),2),round(sum(total_expenses+(membership*constant_val)),2),month from monthly_totals_data where year="+'\''+vm.getYear()+'\''+" and month like "+'\''+vm.getYear()+"%"+'\''+" and provider="+'\''+vm.getProvider()+'\''+" group by month order by month";
			}
			if(!vm.getProvider().equals("all") && !vm.getPcpName().equals("all") && vm.getPcpLocation().equals("all")) {
				str="select round(sum(ipa_premium),2),round(sum(total_expenses+(membership*constant_val)),2),month from monthly_totals_data where year="+'\''+vm.getYear()+'\''+" and month like "+'\''+vm.getYear()+"%"+'\''+" and pcp_id="+'\''+vm.getPcpName()+'\''+" and provider="+'\''+vm.getProvider()+'\''+" group by month order by month";
			}
			if(vm.getProvider().equals("all") && !vm.getPcpName().equals("all") && !vm.getPcpLocation().equals("all")) {
				str="select round(sum(ipa_premium),2),round(sum(total_expenses+(membership*constant_val)),2),month from monthly_totals_data where year="+'\''+vm.getYear()+'\''+" and month like "+'\''+vm.getYear()+"%"+'\''+" and pcp_name="+'\''+vm.getPcpName()+'\''+" and pcp_location="+'\''+vm.getPcpLocation()+'\''+" group by month order by month";
			}
			if(!vm.getProvider().equals("all") && vm.getPcpName().equals("all") && !vm.getPcpLocation().equals("all")) {
				str="select round(sum(ipa_premium),2),round(sum(total_expenses+(membership*constant_val)),2),month from monthly_totals_data where year="+'\''+vm.getYear()+'\''+" and month like "+'\''+vm.getYear()+"%"+'\''+" and provider="+'\''+vm.getProvider()+'\''+" and pcp_location="+'\''+vm.getPcpLocation()+'\''+" group by month order by month";
			}
			if(!vm.getProvider().equals("all") && !vm.getPcpName().equals("all") && !vm.getPcpLocation().equals("all")) {
				str="select round(sum(ipa_premium),2),round(sum(total_expenses+(membership*constant_val)),2),month from monthly_totals_data where year="+'\''+vm.getYear()+'\''+" and month like "+'\''+vm.getYear()+"%"+'\''+" and provider="+'\''+vm.getProvider()+'\''+" and pcp_id="+'\''+vm.getPcpName()+'\''+" and pcp_location="+'\''+vm.getPcpLocation()+'\''+" group by month order by month";
			}
			
		}*/
		
		/*if(vm.getProvider().equals("all") && vm.getPcpName().equals("all") && vm.getPcpLocation().equals("all") && vm.getYear().equals("all")) {
			str="select round(sum(ipa_premium),2),round(sum(total_expenses+(membership*constant_val)),2),month from monthly_totals_data group by month order by month";
		}
		if(vm.getProvider().equals("all") && vm.getPcpName().equals("all") && vm.getPcpLocation().equals("all") && !vm.getYear().equals("all")) {
			str="select round(sum(ipa_premium),2),round(sum(total_expenses+(membership*constant_val)),2),month from monthly_totals_data where year="+'\''+vm.getYear()+'\''+" and month like "+'\''+vm.getYear()+"%"+'\''+" group by month order by month";
		}
		if(vm.getProvider().equals("all") && vm.getPcpName().equals("all") && !vm.getPcpLocation().equals("all") && vm.getYear().equals("all")) {
			str="select round(sum(ipa_premium),2),round(sum(total_expenses+(membership*constant_val)),2),month from monthly_totals_data where pcp_location="+'\''+vm.getPcpLocation()+'\''+" group by month order by month";
		}
		if(vm.getProvider().equals("all") && vm.getPcpName().equals("all") && !vm.getPcpLocation().equals("all") && !vm.getYear().equals("all")) {
			str="select round(sum(ipa_premium),2),round(sum(total_expenses+(membership*constant_val)),2),month from monthly_totals_data where year="+'\''+vm.getYear()+'\''+" and month like "+'\''+vm.getYear()+"%"+'\''+" and pcp_location="+'\''+vm.getPcpLocation()+'\''+" group by month order by month";
		}
		if(vm.getProvider().equals("all") && !vm.getPcpName().equals("all") && vm.getPcpLocation().equals("all") && vm.getYear().equals("all")) {
			str="select round(sum(ipa_premium),2),round(sum(total_expenses+(membership*constant_val)),2),month from monthly_totals_data where pcp_name="+'\''+vm.getPcpName()+'\''+" group by month order by month";
		}
		if(vm.getProvider().equals("all") && !vm.getPcpName().equals("all") && vm.getPcpLocation().equals("all") && !vm.getYear().equals("all")) {
			str="select round(sum(ipa_premium),2),round(sum(total_expenses+(membership*constant_val)),2),month from monthly_totals_data where year="+'\''+vm.getYear()+'\''+" and month like "+'\''+vm.getYear()+"%"+'\''+" and pcp_name="+'\''+vm.getPcpName()+'\''+" group by month order by month";
		}
		if(vm.getProvider().equals("all") && !vm.getPcpName().equals("all") && !vm.getPcpLocation().equals("all") && vm.getYear().equals("all")) {
			str="select round(sum(ipa_premium),2),round(sum(total_expenses+(membership*constant_val)),2),month from monthly_totals_data where pcp_name="+'\''+vm.getPcpName()+'\''+" and pcp_location="+'\''+vm.getPcpLocation()+'\''+" group by month order by month";
		}
		if(vm.getProvider().equals("all") && !vm.getPcpName().equals("all") && !vm.getPcpLocation().equals("all") && !vm.getYear().equals("all")) {
			str="select round(sum(ipa_premium),2),round(sum(total_expenses+(membership*constant_val)),2),month from monthly_totals_data where year="+'\''+vm.getYear()+'\''+" and month like "+'\''+vm.getYear()+"%"+'\''+" and pcp_name="+'\''+vm.getPcpName()+'\''+" and pcp_location="+'\''+vm.getPcpLocation()+'\''+" group by month order by month";
		}
		if(!vm.getProvider().equals("all") && vm.getPcpName().equals("all") && vm.getPcpLocation().equals("all") && vm.getYear().equals("all")) {
			str="select round(sum(ipa_premium),2),round(sum(total_expenses+(membership*constant_val)),2),month from monthly_totals_data where provider="+'\''+vm.getProvider()+'\''+" group by month order by month";
		}
		if(!vm.getProvider().equals("all") && vm.getPcpName().equals("all") && vm.getPcpLocation().equals("all") && !vm.getYear().equals("all")) {
			str="select round(sum(ipa_premium),2),round(sum(total_expenses+(membership*constant_val)),2),month from monthly_totals_data where year="+'\''+vm.getYear()+'\''+" and month like "+'\''+vm.getYear()+"%"+'\''+" and provider="+'\''+vm.getProvider()+'\''+" group by month order by month";
		}
		if(!vm.getProvider().equals("all") && vm.getPcpName().equals("all") && !vm.getPcpLocation().equals("all") && vm.getYear().equals("all")) {
			str="select round(sum(ipa_premium),2),round(sum(total_expenses+(membership*constant_val)),2),month from monthly_totals_data where provider="+'\''+vm.getProvider()+'\''+" and pcp_location="+'\''+vm.getPcpLocation()+'\''+" group by month order by month";
		}
		if(!vm.getProvider().equals("all") && vm.getPcpName().equals("all") && !vm.getPcpLocation().equals("all") && !vm.getYear().equals("all")) {
			str="select round(sum(ipa_premium),2),round(sum(total_expenses+(membership*constant_val)),2),month from monthly_totals_data where year="+'\''+vm.getYear()+'\''+" and month like "+'\''+vm.getYear()+"%"+'\''+" and provider="+'\''+vm.getProvider()+'\''+" and pcp_location="+'\''+vm.getPcpLocation()+'\''+" group by month order by month";
		}
		if(!vm.getProvider().equals("all") && !vm.getPcpName().equals("all") && vm.getPcpLocation().equals("all") && vm.getYear().equals("all")) {
			str="select round(sum(ipa_premium),2),round(sum(total_expenses+(membership*constant_val)),2),month from monthly_totals_data where provider="+'\''+vm.getProvider()+'\''+" and pcp_id="+'\''+vm.getPcpName()+'\''+" group by month order by month";
		}
		if(!vm.getProvider().equals("all") && !vm.getPcpName().equals("all") && vm.getPcpLocation().equals("all") && !vm.getYear().equals("all")) {
			str="select round(sum(ipa_premium),2),round(sum(total_expenses+(membership*constant_val)),2),month from monthly_totals_data where year="+'\''+vm.getYear()+'\''+" and month like "+'\''+vm.getYear()+"%"+'\''+" and provider="+'\''+vm.getProvider()+'\''+" and pcp_id="+'\''+vm.getPcpName()+'\''+" group by month order by month";
		}
		if(!vm.getProvider().equals("all") && !vm.getPcpName().equals("all") && !vm.getPcpLocation().equals("all") && vm.getYear().equals("all")) {
			str="select round(sum(ipa_premium),2),round(sum(total_expenses+(membership*constant_val)),2),month from monthly_totals_data where provider="+'\''+vm.getProvider()+'\''+" and pcp_id="+'\''+vm.getPcpName()+'\''+" and pcp_location="+'\''+vm.getPcpLocation()+'\''+" group by month order by month";
		}
		if(!vm.getProvider().equals("all") && !vm.getPcpName().equals("all") && !vm.getPcpLocation().equals("all") && !vm.getYear().equals("all")) {
			str="select round(sum(ipa_premium),2),round(sum(total_expenses+(membership*constant_val)),2),month from monthly_totals_data where year="+'\''+vm.getYear()+'\''+" and month like "+'\''+vm.getYear()+"%"+'\''+" and provider="+'\''+vm.getProvider()+'\''+" and pcp_id="+'\''+vm.getPcpName()+'\''+" and pcp_location="+'\''+vm.getPcpLocation()+'\''+" group by month order by month";
		}*/
		
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
			locationList = mapper.readValue(vm.getPcpLocation(), new TypeReference<List<OptionsVM>>(){});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(!locationList.isEmpty()) {
			for(int i=0;i<locationList.size();i++) {
					if(!locationList.get(i).getValue().equals("all")) {
						if(i != locationList.size()-1)
							locationCondition = locationCondition + " pcp_location="+'\''+locationList.get(i).getValue()+'\''+" or ";
						else
							locationCondition = locationCondition + " pcp_location="+'\''+locationList.get(i).getValue()+'\'';
					}
				}
		}
		
		if(!locationCondition.equals(""))
			conditionStr = conditionStr + " and ("+locationCondition+")";
		
		if(!vm.getYear().equals("all")) {
			conditionStr = conditionStr + " and year="+'\''+vm.getYear()+'\''+" and month like "+'\''+vm.getYear()+"%"+'\'';
		}
		
		if(!conditionStr.equals(""))
			conditionStr = " where "+conditionStr.substring(4);
		
		str = "select round(sum(ipa_premium),2),round(sum(total_expenses+(membership*constant_val)),2),month from monthly_totals_data "+conditionStr+" group by month order by month";
		System.out.println("Condition string : ................"+conditionStr);
		System.out.println(str);
		Query query = getEntityManager().createNativeQuery(str);
		return  query.getResultList();
	}
	
	public ReportResponseVM getSummaryReportData(ReportVM vm) {
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
			if(filteredList.get(i).getId().equals("month")) {
				filterColumnName = "month";
			}
			if(filteredList.get(i).getId().equals("members")) {
				filterColumnName = "membership";
			}
			if(filteredList.get(i).getId().equals("maPremium")) {
				filterColumnName = "premium_ma";
			}
			if(filteredList.get(i).getId().equals("partDPremium")) {
				filterColumnName = "premium_partd";
			}
			if(filteredList.get(i).getId().equals("totalPremium")) {
				filterColumnName = "total_premium";
			}
			if(filteredList.get(i).getId().equals("ipaPremium")) {
				filterColumnName = "ipa_premium";
			}
			if(filteredList.get(i).getId().equals("pcpCap")) {
				filterColumnName = "pcp_cap";
			}
			if(filteredList.get(i).getId().equals("specCost")) {
				filterColumnName = "spec_cost";
			}
			if(filteredList.get(i).getId().equals("profClaims")) {
				filterColumnName = "prof_claims";
			}
			if(filteredList.get(i).getId().equals("instClaims")) {
				filterColumnName = "inst_claims";
			}
			if(filteredList.get(i).getId().equals("rxClaims")) {
				filterColumnName = "rx_claims";
			}
			if(filteredList.get(i).getId().equals("ibnrDollars")) {
				filterColumnName = "ibnr_dollars";
			}
			if(filteredList.get(i).getId().equals("reinsurancePremium")) {
				filterColumnName = "reinsurance";
			}
			if(filteredList.get(i).getId().equals("totalExpenses")) {
				filterColumnName = "total_expenses";
			}
			
				if(!filterColumnName.equals("")) {
					filterStr += " and "+filterColumnName+" like "+'\''+"%"+filteredList.get(i).getValue()+"%"+'\''+" ";
				}
				
		}
		
		String sortStr = "";
		String sortColName = "";
		if(!sortedList.isEmpty()) {
			if(sortedList.get(0).getId().equals("month")) {
				sortColName = "month";
			}
			if(sortedList.get(0).getId().equals("members")) {
				sortColName = "membership";
			}
			if(sortedList.get(0).getId().equals("maPremium")) {
				sortColName = "premium_ma";
			}
			if(sortedList.get(0).getId().equals("partDPremium")) {
				sortColName = "premium_partd";
			}
			if(sortedList.get(0).getId().equals("totalPremium")) {
				sortColName = "total_premium";
			}
			if(sortedList.get(0).getId().equals("ipaPremium")) {
				sortColName = "ipa_premium";
			}
			if(sortedList.get(0).getId().equals("pcpCap")) {
				sortColName = "pcp_cap";
			}
			if(sortedList.get(0).getId().equals("specCost")) {
				sortColName = "spec_cost";
			}
			if(sortedList.get(0).getId().equals("profClaims")) {
				sortColName = "prof_claims";
			}
			if(sortedList.get(0).getId().equals("instClaims")) {
				sortColName = "inst_claims";
			}
			if(sortedList.get(0).getId().equals("rxClaims")) {
				sortColName = "rx_claims";
			}
			if(sortedList.get(0).getId().equals("ibnrDollars")) {
				sortColName = "ibnr_dollars";
			}
			if(sortedList.get(0).getId().equals("reinsurancePremium")) {
				sortColName = "reinsurance";
			}
			if(sortedList.get(0).getId().equals("totalExpenses")) {
				sortColName = "total_expenses";
			}
			
			
			if(!sortedList.get(0).getId().equals("month")) {
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
		
		String sortQryStr = " order by month limit "+start+","+end;
		String sortCountQryStr = " order by month";
		if(!sortStr.equals("")) {
			sortQryStr = " order by "+sortStr+" limit "+start+","+end;
			sortCountQryStr = " order by "+sortStr;
		}
		
		String queryStr = "",countQueryStr = "";
		
		
		if(vm.getProvider().equals("all") && vm.getYear().equals("all")) {
			if(!filterStr.equals(""))
				filterStr = " where "+filterStr.substring(4);
			queryStr = "select month,sum(membership),sum(premium_ma),sum(premium_partd),sum(total_premium),sum(ipa_premium),sum(pcp_cap),sum(spec_cost),sum(prof_claims),\n" + 
					"sum(inst_claims),sum(rx_claims),sum(ibnr_dollars),sum(round(membership*reinsurance,0)) as reinsurancePremium,sum(total_expenses+reinsurance*membership)\n" + 
					" from monthly_totals_report "+filterStr+" group by month";
			
			countQueryStr = "select count(*) from \n" + 
					"(\n" + 
					queryStr;
		} else {
			if(vm.getProvider().equals("all")) {
				queryStr = "select month,sum(membership),sum(premium_ma),sum(premium_partd),sum(total_premium),sum(ipa_premium),sum(pcp_cap),sum(spec_cost),sum(prof_claims),\n" + 
						"sum(inst_claims),sum(rx_claims),sum(ibnr_dollars),sum(round(membership*reinsurance,0)) as reinsurancePremium,sum(total_expenses+reinsurance*membership)\n" + 
						" from monthly_totals_report where year="+'\''+vm.getYear()+'\''+" "+filterStr+" group by month";
				
				countQueryStr = "select count(*) from \n" + 
						"(\n" + 
						queryStr;
			}
			if(vm.getYear().equals("all")) {
				queryStr = "select month,sum(membership),sum(premium_ma),sum(premium_partd),sum(total_premium),sum(ipa_premium),sum(pcp_cap),sum(spec_cost),sum(prof_claims),\n" + 
						"sum(inst_claims),sum(rx_claims),sum(ibnr_dollars),sum(round(membership*reinsurance,0)) as reinsurancePremium,sum(total_expenses+reinsurance*membership)\n" + 
						" from monthly_totals_report where provider="+'\''+vm.getProvider()+'\''+" "+filterStr+" group by month";
				
				countQueryStr = "select count(*) from \n" + 
						"(\n" + 
						queryStr;
			}
			if(!vm.getProvider().equals("all") && !vm.getYear().equals("all")) {
				queryStr = "select month,membership,premium_ma,premium_partd,total_premium,ipa_premium,pcp_cap,spec_cost,prof_claims,\n" + 
						"inst_claims,rx_claims,ibnr_dollars,round(membership*reinsurance,0) as reinsurancePremium,total_expenses+reinsurance*membership"+ 
						" from monthly_totals_report where provider="+'\''+vm.getProvider()+'\''+" and year="+'\''+vm.getYear()+'\''+filterStr;
				
				countQueryStr = "select count(*) from \n" + 
						"(\n" + 
						queryStr;
				
			}
		}
		
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
	
	public ReportResponseVM getSettledMonthsReportData(ReportVM vm) {
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
			filterColumnName = filteredList.get(i).getId();
			if(filterColumnName.equals("surplusDeficit")) {
				filterColumnName = "netPremium";
			}
			
				if(!filterColumnName.equals("") && !filterColumnName.equals("month")) {
					if(!havingStr.equals("")) {
						havingStr += "and ";
					} else {
						havingStr = " having ";
					}
					havingStr += filterColumnName+" like "+'\''+"%"+filteredList.get(i).getValue()+"%"+'\''+" ";
				} else {
					if(!filterColumnName.equals(""))
					filterStr += " and "+filterColumnName+" like "+'\''+"%"+filteredList.get(i).getValue()+"%"+'\''+" ";
				}
		}
		
		String sortStr = "";
		String sortColName = "";
		if(!sortedList.isEmpty()) {
			
			sortColName = sortedList.get(0).getId();
			if(sortColName.equals("surplusDeficit"))
				sortColName = "netPremium";
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
		
		String sortQryStr = " order by month limit "+start+","+end;
		String sortCountQryStr = " order by month";
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
			conditionStr = conditionStr + " and year="+'\''+vm.getYear()+'\'';
		}
		
		if(!conditionStr.equals("")) {
			conditionStr = " where "+conditionStr.substring(4);
		}
		
		
			if(!filterStr.equals("") && conditionStr.equals(""))
				filterStr = " where "+filterStr.substring(4);
			queryStr = "select month,sum(membership) as membership,round(sum(ipa_premium),0) as ipaPremium,round(sum(total_expenses)+sum(membership)*constant_val,0) as totalExpenses,\n" + 
					"round(sum(stop_loss),0) as stoploss,round(sum(net_premium)-sum(membership)*constant_val,0) as netPremium,sum(risk_sharing) as riskSharing\n" + 
					" from monthly_totals_data "+conditionStr+" "+filterStr+" group by month,constant_val "+havingStr;
			
			countQueryStr = "select count(*) from \n" + 
					"(\n" + queryStr;
		
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

	public ReportResponseVM reinsuranceMangementReportData(ReportVM vm) {
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
			filterColumnName = filteredList.get(i).getId();
			
			if(filterColumnName.equals("hicn")) {
				filterColumnName = "medicare_id";
			}
			if(filterColumnName.equals("planName")) {
				filterColumnName = "planName";
			}
			if(filterColumnName.equals("patientName")) {
				filterColumnName = "memberName";
			}
			if(filterColumnName.equals("pcpName")) {
				filterColumnName = "pcpName";
			}
			if(filterColumnName.equals("instClaims")) {
				filterColumnName = "inst_claims";
			}
			if(filterColumnName.equals("profClaims")) {
				filterColumnName = "prof_claims";
			}
			
			if(filterColumnName.equals("termedMonth")) {
				filterColumnName = "eligibleMonth";
			}
			if(filterColumnName.equals("totalCost")) {
				filterColumnName = "total";
			}
			
				if(!filterColumnName.equals("")) {
					
					havingStr += " and "+filterColumnName+" like "+'\''+"%"+filteredList.get(i).getValue()+"%"+'\''+" ";
				}
		}
		
		String sortStr = "";
		String sortColName = "";
		if(!sortedList.isEmpty()) {
			
			sortColName = sortedList.get(0).getId();
			if(sortColName.equals("hicn"))
				sortColName = "medicare_id";
			if(sortColName.equals("planName"))
				sortColName = "planName";
			if(sortColName.equals("patientName"))
				sortColName = "memberName";
			if(sortColName.equals("pcpName"))
				sortColName = "pcpName";
			if(sortColName.equals("termedMonth"))
				sortColName = "eligibleMonth";
			if(sortColName.equals("instClaims"))
					sortColName="inst_claims";
			if(sortColName.equals("profClaims"))
				sortColName="prof_claims";
		
			if(sortColName.equals("totalCost"))
				sortColName = "total";
			
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
		String sortCountQryStr = " order by total desc";
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
		System.out.println(vm.getYear());
		if(!vm.getYear().equals("all")) {
			conditionStr = conditionStr + " and eligible_month like "+'\''+vm.getYear()+"%"+'\'';
		}
		
		if(!conditionStr.equals("")) {
			conditionStr = " where "+conditionStr.substring(4);
		}
		
			if(!filterStr.equals("") && conditionStr.equals(""))
				filterStr = " where "+filterStr.substring(4);
			
			queryStr = "select C.*, COALESCE(round((inst_claims + prof_claims),0),0) as total from (\r\n" + 
					"select B.*, COALESCE(round(sum(pcd.paid_amount),0),0) as prof_claims from (\r\n" + 
					"select A.*, COALESCE(round(sum(icd.paid_amount),0),0) as inst_claims  from (\r\n" + 
					"select distinct max(dd.plan_name) planName,concat(dd.last_name,' ',dd.first_name) as memberName,min(dd.eligible_month) as eligibleMonth,min(dd.pcp_name) as pcpName,\r\n" + 
					"dd.medicare_id \r\n" + 
					"from demographic_detail dd \r\n" + 
					conditionStr+" group by dd.medicare_id,memberName\r\n" + 
					") A\r\n" + 
					" left join inst_claim_detail icd on icd.medicare_id = A.medicare_id \r\n" + 
					"group by A.eligibleMonth, A.pcpName, A.medicare_id, A.memberName 	\r\n" + 
					") B \r\n" + 
					"left join prof_claim_detail pcd on pcd.medicare_id = B.medicare_id\r\n" + 
					"group by B.eligibleMonth, B.pcpName, B.medicare_id,B.memberName \r\n" + 
					") C having total > (select reinsurance_threshold from settings_table limit 1) "+havingStr;
			
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
	
	public ReportResponseVM reinsuranceCostReportData(ReportVM vm) {
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
		String filterStr = "", havingStr = "", filterString="";
		String filterColumnName = "";
		for(int i=0;i<filteredList.size();i++) {
			filterColumnName = filteredList.get(i).getId();
			
			if(filterColumnName.equals("planName")) {
				filterColumnName = "planName";
			}if(filterColumnName.equals("policyPeriod")) {
				filterColumnName = "";
				
			}if(filterColumnName.equals("patientName")) {
				filterColumnName = "patientName";
				
			}if(filterColumnName.equals("subscriberID")) {
				filterColumnName = "medicare_id";
			}if(filterColumnName.equals("effectiveDate")) {
				filterColumnName = "eligibleMonth";
			}if(filterColumnName.equals("termedMonth")) {
				filterColumnName = "";
			}if(filterColumnName.equals("dob")) {	
				filterColumnName="birth_date";
			}if(filterColumnName.equals("status")) {	
				filterColumnName="";
			}if(filterColumnName.equals("gender")) {	
				filterColumnName="gender";
			}if(filterColumnName.equals("pcpName")) {	
				filterColumnName="pcpName";
			}if(filterColumnName.equals("totalClaimsCost")) {	
				filterColumnName="total";
			}
				
			
			if(!filterColumnName.equals("")) {	
					
				havingStr += " and "+ filterColumnName+" like "+'\''+"%"+filteredList.get(i).getValue()+"%"+'\''+" ";
				} 	else {
					if(!filterColumnName.equals(""))
					filterStr += " and "+filterColumnName+" like "+'\''+"%"+filteredList.get(i).getValue()+"%"+'\''+" ";
				}
			
				
				
		}
		
		String sortStr = "";
		String sortColName = "";
		if(!sortedList.isEmpty()) {
			
			sortColName = sortedList.get(0).getId();
			if(sortColName.equals("planName"))
				sortColName = "planName";
			
			if(sortColName.equals("policyPeriod"))
				sortColName = "";
			
			if(sortColName.equals("patientName"))
				sortColName = "patientName";
			
			if(sortColName.equals("subscriberID"))
				sortColName = "medicare_id";
			
			if(sortColName.equals("effectiveDate"))
				sortColName = "eligibleMonth";
			
			if(sortColName.equals("termedMonth"))
				sortColName = "";
			
			if(sortColName.equals("dob"))
				sortColName="birth_date";
			
			if(sortColName.equals("status"))
				sortColName = "";
			
			if(sortColName.equals("gender"))
				sortColName="gender";
		
			if(sortColName.equals("pcpName"))
				sortColName = "pcpName";
			
			if(sortColName.equals("totalClaimsCost"))
				sortColName = "total";
			
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
		String sortCountQryStr = " order by total desc";
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
			conditionStr = conditionStr + " and eligible_month like "+'\''+vm.getYear()+"%"+'\'';
		}
		
		if(!conditionStr.equals("")) {
			conditionStr = " where "+conditionStr.substring(4);
		}
		
			if(!filterStr.equals("") && conditionStr.equals(""))
				filterStr = " where "+filterStr.substring(4);
			
			queryStr = "select C.*, (inst_claims + prof_claims) as total from (\r\n" + 
					"select B.*, COALESCE(round(sum(pcd.paid_amount),2),0) as prof_claims from (\r\n" + 
					"select A.*, COALESCE(round(sum(icd.paid_amount),2),0) as inst_claims from (\r\n" + 
					"select distinct max(dd.plan_name) planName,concat(dd.last_name,' ',dd.first_name) as patientName,dd.medicare_id,min(dd.eligible_month) as eligibleMonth,dd.birth_date, dd.gender ,min(dd.pcp_name) as pcpName\r\n" + 
					"from demographic_detail dd \r\n" + 
					conditionStr+filterStr+" group by dd.birth_date,dd.medicare_id,dd.gender,patientName \r\n" + 
					") A\r\n" + 
					" left join inst_claim_detail icd on icd.medicare_id = A.medicare_id\r\n" + 
					"group by A.birth_date, A.eligibleMonth, A.pcpName, A.medicare_id, A.planName, A.gender, A.patientName  \r\n" + 
					") B \r\n" + 
					"left join prof_claim_detail pcd on pcd.medicare_id = B.medicare_id\r\n" + 
					"group by B.birth_date, B.eligibleMonth, B.pcpName,B.medicare_id, B.planName, B.gender, B.patientName \r\n" + 
					") C\r\n" + 
					" having total > (select reinsurance_cost_threshold from settings_table limit 1)" + havingStr;
			
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
	
	
	
	public ReportResponseVM getPmpmByPracticeReportData(ReportVM vm) {
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
			filterColumnName = filteredList.get(i).getId();
			if(filterColumnName.equals("facilityLocationName")) {
				filterColumnName = "";
			}
			if(filterColumnName.equals("providerName")) {
				filterColumnName = "pcp_name";
			}
			
				if(!filterColumnName.equals("") && !filterColumnName.equals("pcp_name")) {
					if(!havingStr.equals("")) {
						havingStr += "and ";
					} else {
						havingStr = " having ";
					}
					havingStr += filterColumnName+" like "+'\''+"%"+filteredList.get(i).getValue()+"%"+'\''+" ";
				} else {
					if(!filterColumnName.equals(""))
					filterStr += " and "+filterColumnName+" like "+'\''+"%"+filteredList.get(i).getValue()+"%"+'\''+" ";
				}
		}
		
		String sortStr = "";
		String sortColName = "";
		if(!sortedList.isEmpty()) {
			
			sortColName = sortedList.get(0).getId();
			if(sortColName.equals("facilityLocationName"))
				sortColName = "";
			if(sortColName.equals("providerName"))
				sortColName = "pcp_name";
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
			conditionStr = conditionStr + " and month like "+'\''+vm.getYear()+"%"+'\'';
		}
		
		if(!conditionStr.equals("")) {
			conditionStr = " where "+conditionStr.substring(4);
		}
		
			if(!filterStr.equals("") && conditionStr.equals(""))
				filterStr = " where "+filterStr.substring(4);
			queryStr = "select pcp_name,sum(totalCost) as totalCost,sum(totalNumberOfMemberMonth) as totalNumberOfMemberMonth,sum(pmpm) as pmpm,sum(pmpy) as pmpy,round(sum(total_premium),2) as totalPremium,round(sum(ipa_premium),2) as ipaPremium,round(sum(total_premium-ipa_premium),2) as difference from (\n"+
					"select pcp_name,round(sum(total_expenses)+constant_val*sum(membership),0) as totalCost,sum(membership) as totalNumberOfMemberMonth,\n" + 
					"round((sum(total_expenses)+constant_val*sum(membership))/sum(membership),0) as pmpm,\n" + 
					"round(((sum(total_expenses)+constant_val*sum(membership))/sum(membership))*12,0) as pmpy,pcp_id,sum(ipa_premium) as ipa_premium,sum(total_premium) as total_premium from monthly_totals_data \n" + 
					conditionStr+" "+filterStr+" group by pcp_name,constant_val,pcp_id) A group by pcp_name "+havingStr;
			
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
	
	public ReportResponseVM getPmpmByPracticeExpandReportData(ReportVM vm) {
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
			filterColumnName = filteredList.get(i).getId();
			if(filterColumnName.equals("patientName")) {
				filterColumnName = "concat(first_name,' ',last_name)";
			}
			if(filterColumnName.equals("pcpName")) {
				filterColumnName = "pcp_name";
			}
			if(filterColumnName.equals("pcpLocation")) {
				filterColumnName = "pcp_location_code";
			}
			if(filterColumnName.equals("mra")) {
				filterColumnName = "mra";
			}
			if(filterColumnName.equals("cost")) {
				filterColumnName = "total";
			}
			if(filterColumnName.equals("claimType")) {
				filterColumnName = "claimType";
			}
			
				if(filterColumnName.equals("claimType") || filterColumnName.equals("total") || filterColumnName.equals("mra")) {
					if(!havingStr.equals("")) {
						havingStr += "and ";
					} else {
						havingStr = " having ";
					}
					havingStr += filterColumnName+" like "+'\''+"%"+filteredList.get(i).getValue()+"%"+'\''+" ";
				} else {
					if(!filterColumnName.equals(""))
					filterStr += " and "+filterColumnName+" like "+'\''+"%"+filteredList.get(i).getValue()+"%"+'\''+" ";
				}
		}
		
		String sortStr = "";
		String sortColName = "";
		if(!sortedList.isEmpty()) {
			
			sortColName = sortedList.get(0).getId();
			if(sortColName.equals("patientName"))
				sortColName = "concat(last_name,' ',first_name)";
			if(sortColName.equals("pcpName"))
				sortColName = "pcpName";
			if(sortColName.equals("pcpLocation"))
				sortColName = "pcpLocation";
			if(sortColName.equals("mra"))
				sortColName = "mra";
			if(sortColName.equals("cost"))
				sortColName = "total";
			if(sortColName.equals("claimType"))
				sortColName = "claimType";
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
		
		String sortQryStr = " order by mra desc limit "+start+","+end;
		String sortCountQryStr = " order by mra desc";
		if(!sortStr.equals("")) {
			sortQryStr = " order by "+sortStr+" limit "+start+","+end;
			sortCountQryStr = " order by "+sortStr;
		}
		
		String queryStr = "",countQueryStr = "", conditionStr ="";
		
		if(!vm.getProvider().equals("all")) {
			conditionStr+=" and dd.provider = '" +vm.getProvider()+"' ";
		}
		
		if(!vm.getYear().equals("all")) {
			conditionStr+=" and dd.eligible_month like '"+vm.getYear() +"%' ";
		}
		

		
		queryStr = "select D.*, (spec_cost + pcp_cap + reinsurance_prem + inst_claims + prof_claims + rx_claims) as total,concat(if(inst_claims !=0,'INST_CLAIM',''),if(inst_claims!=0,', ',' '),if(prof_claims!=0,'PROF_CLAIMS',''),if(prof_claims!=0,', ',' '),if(rx_claims !=0,'RX_CLAIM',''),if(rx_claims!=0,', ',' '),if(reinsurance_prem!=0,'REINSURANCE_PREM',''),if(reinsurance_prem!=0,', ',' '),if(pcp_cap !=0,'PCP_CAP',''),if(pcp_cap!=0,', ',' '),if(spec_cost!=0,'SPEC_COST','')) as claimType from (\r\n" + 
				"select C.*, COALESCE(round(sum(rd.paid_amount - lics_paid - rep_gap_dscnt),2),0) as rx_claims from  (\r\n" + 
				"select B.*, COALESCE(round(sum(pcd.paid_amount),2),0) as prof_claims from (\r\n" + 
				"select A.*, COALESCE(round(sum(icd.paid_amount),2),0) as inst_claims from (\r\n" + 
				"select distinct dd.last_name,dd.first_name,min(dd.pcp_name) as pcpName,min(dd.pcp_location_code) as pcpLocation,\r\n" + 
				"COALESCE(max(round(dd.risk_score_partc,2)),0) as mra,dd.medicare_id,\r\n" + 
				"COALESCE(round(sum(behavioral_health + chiropractic_cap + dental_cap + hearing_cap + lab + vision_ophthamalogy + vision_optometry + otc_cap + gym_cap + podiatry_cap + transportation + dermatology),2),0) as spec_cost,\r\n" + 
				"COALESCE(round(sum(pcp_cap),2),0) as pcp_cap, COALESCE(round(count(*)*min(constant_val),2),0) as reinsurance_prem\r\n" + 
				"from demographic_detail dd where dd.pcp_name = '" +vm.getPcpName() +"' "+ conditionStr+filterStr+
				" group by dd.last_name,dd.first_name,dd.birth_date,dd.medicare_id \r\n" + 
				") A\r\n" + 
				" left join inst_claim_detail icd on icd.medicare_id = A.medicare_id\r\n" + 
				"group by A.last_name,A.first_name,A.pcpName, A.pcpLocation, A.mra, A.medicare_id, A.spec_cost, A.pcp_cap, A.reinsurance_prem order by A.last_name\r\n" + 
				") B \r\n" + 
				"left join prof_claim_detail pcd on pcd.medicare_id = B.medicare_id\r\n" + 
				"group by B.last_name,B.first_name, B.pcpName, B.pcpLocation, B.mra, B.medicare_id, B.spec_cost, B.pcp_cap, B.reinsurance_prem order by B.last_name\r\n" + 
				") C \r\n" + 
				"left join rx_detail rd  on rd.medicare_id = C.medicare_id \r\n" + 
				"group by C.last_name,C.first_name, C.pcpName, C.pcpLocation, C.mra, C.medicare_id, C.spec_cost, C.pcp_cap, C.reinsurance_prem order by C.last_name\r\n" + 
				")  D \r\n" + 
				"\r\n" +havingStr ;
				
				countQueryStr = "select count(*) from \n" + 
						"(\n" + 
						queryStr;
				
			
		
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
