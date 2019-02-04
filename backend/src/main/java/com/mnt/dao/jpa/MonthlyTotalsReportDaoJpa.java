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
				filterColumnName = "plan_name";
			}if(filterColumnName.equals("patientName")) {
				filterColumnName = "member_name";
			}if(filterColumnName.equals("pcpName")) {
				filterColumnName = "pcp_name";
			}
			if(filterColumnName.equals("instClaims")) {
				filterColumnName = "inst_claims";
			}
			if(filterColumnName.equals("profClaims")) {
				filterColumnName = "prof_claims";
			}
			
			if(filterColumnName.equals("termedMonth")) {
				filterColumnName = "service_month";
			}
			if(filterColumnName.equals("totalCost")) {
				filterColumnName = "total";
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
			if(sortColName.equals("hicn"))
				sortColName = "medicare_id";
			if(sortColName.equals("planName"))
				sortColName = "plan_name";
			if(sortColName.equals("patientName"))
				sortColName = "member_name";
			if(sortColName.equals("pcpName"))
				sortColName = "pcp_name";
			if(sortColName.equals("termedMonth"))
				sortColName = "service_month";
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
					conditionStr+" "+filterStr+" group by dd.medicare_id,memberName\r\n" + 
					") A\r\n" + 
					" left join inst_claim_detail icd on icd.medicare_id = A.medicare_id \r\n" + 
					"group by A.eligibleMonth, A.pcpName, A.medicare_id, A.memberName 	\r\n" + 
					") B \r\n" + 
					"left join prof_claim_detail pcd on pcd.medicare_id = B.medicare_id\r\n" + 
					"group by B.eligibleMonth, B.pcpName, B.medicare_id,B.memberName \r\n" +havingStr+ 
					") C having total>10000 ";
			
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
		
		String sortQryStr = " order by pcp_name limit "+start+","+end;
		String sortCountQryStr = " order by pcp_name";
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
			queryStr = "select pcp_name,sum(totalCost) as totalCost,sum(totalNumberOfMemberMonth) as totalNumberOfMemberMonth,sum(pmpm) as pmpm,sum(pmpy) as pmpy from (\n"+
					"select pcp_name,round(sum(total_expenses)+constant_val*sum(membership),0) as totalCost,sum(membership) as totalNumberOfMemberMonth,\n" + 
					"round((sum(total_expenses)+constant_val*sum(membership))/sum(membership),0) as pmpm,\n" + 
					"round(((sum(total_expenses)+constant_val*sum(membership))/sum(membership))*12,0) as pmpy,pcp_id from monthly_totals_data \n" + 
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
				filterColumnName = "concat(first_name,last_name)";
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
				filterColumnName = "";
			}
			if(filterColumnName.equals("claimType")) {
				filterColumnName = "";
			}
			
				if(!filterColumnName.equals("") && filterColumnName.equals("mra")) {
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
				sortColName = "last_name,first_name";
			if(sortColName.equals("pcpName"))
				sortColName = "pcp_name";
			if(sortColName.equals("pcpLocation"))
				sortColName = "pcp_location_code";
			if(sortColName.equals("mra"))
				sortColName = "mra";
			if(sortColName.equals("cost"))
				sortColName = "";
			if(sortColName.equals("claimType"))
				sortColName = "";
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
		
		String queryStr = "",countQueryStr = "";
		
		
		if(vm.getProvider().equals("all") && vm.getYear().equals("all")) {
			queryStr = "select distinct last_name,first_name,pcp_name,pcp_location_code,round(max(risk_score_partc),2) as mra,max(eligible_month) from demographic_detail"
					+ " where pcp_name="+'\''+vm.getPcpName()+'\''+filterStr+" group by pcp_name,first_name,last_name,pcp_location_code "+havingStr;
			
			countQueryStr = "select count(*) from \n" + 
					"(\n" + queryStr;
		} else {
			if(vm.getProvider().equals("all")) {
				queryStr = "select distinct last_name,first_name,pcp_name,pcp_location_code,round(max(risk_score_partc),2) as mra,max(eligible_month) from demographic_detail"
						+ " where eligible_month like "+'\''+vm.getYear()+"%"+'\''+" and pcp_name="+'\''+vm.getPcpName()+'\''+filterStr+" group by pcp_name,first_name,last_name,pcp_location_code "+havingStr;
				
				countQueryStr = "select count(*) from \n" + 
						"(\n" + 
						queryStr;
			}
			if(vm.getYear().equals("all")) {
				queryStr = "select distinct last_name,first_name,pcp_name,pcp_location_code,round(max(risk_score_partc),2) as mra,max(eligible_month) from demographic_detail"
						+ " where provider="+'\''+vm.getProvider()+'\''+
						" and pcp_name="+'\''+vm.getPcpName()+'\''+filterStr+" group by pcp_name,first_name,last_name,pcp_location_code "+havingStr;
				
				countQueryStr = "select count(*) from \n" + 
						"(\n" + 
						queryStr;
			}
			if(!vm.getProvider().equals("all") && !vm.getYear().equals("all")) {
				
				queryStr = "select distinct last_name,first_name,pcp_name,pcp_location_code,round(max(risk_score_partc),2) as mra,max(eligible_month) from demographic_detail"
						+ " where provider="+'\''+vm.getProvider()+'\''+" and \n" + 
						"eligible_month like "+'\''+vm.getYear()+"%"+'\''+" and pcp_name="+'\''+vm.getPcpName()+'\''+filterStr+" group by pcp_name,first_name,last_name,pcp_location_code "+havingStr;
				
				countQueryStr = "select count(*) from \n" + 
						"(\n" + 
						queryStr;
				
			}
		}
		
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
