package com.mnt.dao.jpa;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.dao.MonthlyTotalsDataDao;
import com.mnt.domain.MonthlyTotalsData;
import com.mnt.vm.OptionsVM;

@Repository
public class MonthlyTotalsDataDaoJpa  extends BaseDaoJpa<MonthlyTotalsData>  implements MonthlyTotalsDataDao {

	public MonthlyTotalsDataDaoJpa() {
		super(MonthlyTotalsData.class, "MonthlyTotalsData");
	}
	
	@Override
	@Transactional
	public List<Object[]> getSumOfClaim(String provider, String pcpName, List<OptionsVM> locationList, String year) {
		String str = "",conditionStr = "";
		/*if(provider.equals("all") && pcpName.equals("all") && locationName.equals("all") && year.equals("all")) {
			str = "select round(sum(prof_claims),2),round(sum(inst_claims),2),round(sum(rx_claims),2) from monthly_totals_data";
		}
		if(provider.equals("all") && pcpName.equals("all") && locationName.equals("all") && !year.equals("all")) {
			str = "select round(sum(prof_claims),2),round(sum(inst_claims),2),round(sum(rx_claims),2) from monthly_totals_data where year="+'\''+year+'\''+"";
		}
		if(provider.equals("all") && pcpName.equals("all") && !locationName.equals("all") && year.equals("all")) {
			str = "select round(sum(prof_claims),2),round(sum(inst_claims),2),round(sum(rx_claims),2) from monthly_totals_data where pcp_location="+'\''+locationName+'\''+"";
		}
		if(provider.equals("all") && pcpName.equals("all") && !locationName.equals("all") && !year.equals("all")) {
			str = "select round(sum(prof_claims),2),round(sum(inst_claims),2),round(sum(rx_claims),2) from monthly_totals_data where year="+'\''+year+'\''+" and pcp_location="+'\''+locationName+'\''+"";
		}
		if(provider.equals("all") && !pcpName.equals("all") && locationName.equals("all") && year.equals("all")) {
			str = "select round(sum(prof_claims),2),round(sum(inst_claims),2),round(sum(rx_claims),2) from monthly_totals_data where pcp_name="+'\''+pcpName+'\''+"";
		}
		if(provider.equals("all") && !pcpName.equals("all") && locationName.equals("all") && !year.equals("all")) {
			str = "select round(sum(prof_claims),2),round(sum(inst_claims),2),round(sum(rx_claims),2) from monthly_totals_data where year="+'\''+year+'\''+" and pcp_name="+'\''+pcpName+'\''+"";
		}
		if(provider.equals("all") && !pcpName.equals("all") && !locationName.equals("all") && year.equals("all")) {
			str = "select round(sum(prof_claims),2),round(sum(inst_claims),2),round(sum(rx_claims),2) from monthly_totals_data where pcp_name="+'\''+pcpName+'\''+" and pcp_location="+'\''+locationName+'\''+"";
		}
		if(provider.equals("all") && !pcpName.equals("all") && !locationName.equals("all") && !year.equals("all")) {
			str = "select round(sum(prof_claims),2),round(sum(inst_claims),2),round(sum(rx_claims),2) from monthly_totals_data where pcp_name="+'\''+pcpName+'\''+" and pcp_location="+'\''+locationName+'\''+" and year="+'\''+year+'\''+"";
		}
		if(!provider.equals("all") && pcpName.equals("all") && locationName.equals("all") && year.equals("all")) {
			str = "select round(sum(prof_claims),2),round(sum(inst_claims),2),round(sum(rx_claims),2) from monthly_totals_data where provider="+'\''+provider+'\'';
		}
		if(!provider.equals("all") && pcpName.equals("all") && locationName.equals("all") && !year.equals("all")) {
			str = "select round(sum(prof_claims),2),round(sum(inst_claims),2),round(sum(rx_claims),2) from monthly_totals_data where provider="+'\''+provider+'\''+" and year="+'\''+year+'\''+"";
		}
		if(!provider.equals("all") && pcpName.equals("all") && !locationName.equals("all") && year.equals("all")) {
			str = "select round(sum(prof_claims),2),round(sum(inst_claims),2),round(sum(rx_claims),2) from monthly_totals_data where provider="+'\''+provider+'\''+" and pcp_location="+'\''+locationName+'\'';
		}
		if(!provider.equals("all") && pcpName.equals("all") && !locationName.equals("all") && !year.equals("all")) {
			str = "select round(sum(prof_claims),2),round(sum(inst_claims),2),round(sum(rx_claims),2) from monthly_totals_data where provider="+'\''+provider+'\''+" and pcp_location="+'\''+locationName+'\''+" and year="+'\''+year+'\''+"";
		}
		if(!provider.equals("all") && !pcpName.equals("all") && locationName.equals("all") && year.equals("all")) {
			str = "select round(sum(prof_claims),2),round(sum(inst_claims),2),round(sum(rx_claims),2) from monthly_totals_data where provider="+'\''+provider+'\''+" and pcp_id="+'\''+pcpName+'\'';
		}
		if(!provider.equals("all") && !pcpName.equals("all") && locationName.equals("all") && !year.equals("all")) {
			str = "select round(sum(prof_claims),2),round(sum(inst_claims),2),round(sum(rx_claims),2) from monthly_totals_data where provider="+'\''+provider+'\''+" and pcp_id="+'\''+pcpName+'\''+" and year="+'\''+year+'\''+"";
		}
		if(!provider.equals("all") && !pcpName.equals("all") && !locationName.equals("all") && year.equals("all")) {
			str = "select round(sum(prof_claims),2),round(sum(inst_claims),2),round(sum(rx_claims),2) from monthly_totals_data where provider="+'\''+provider+'\''+" and pcp_id="+'\''+pcpName+'\''+" and pcp_location="+'\''+locationName+'\'';
		}
		if(!provider.equals("all") && !pcpName.equals("all") && !locationName.equals("all") && !year.equals("all")) {
			str = "select round(sum(prof_claims),2),round(sum(inst_claims),2),round(sum(rx_claims),2) from monthly_totals_data where provider="+'\''+provider+'\''+" and pcp_id="+'\''+pcpName+'\''+" and pcp_location="+'\''+locationName+'\''+" and year="+'\''+year+'\''+"";
		}*/
		if(!provider.equals("all")) {
			conditionStr = conditionStr + " and provider="+'\''+provider+'\'';
		}
		if(!pcpName.equals("all")) {
			if(provider.equals("all"))
				conditionStr = conditionStr + " and pcp_name="+'\''+pcpName+'\'';
			else
				conditionStr = conditionStr + " and pcp_id="+'\''+pcpName+'\'';
		}
		String locationCondition = "";
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
		
		if(!year.equals("all")) {
			conditionStr = conditionStr + " and year="+'\''+year+'\'';
		}
		
		if(!conditionStr.equals(""))
			conditionStr = " where "+conditionStr.substring(4);
		
		str = "select round(sum(prof_claims),2),round(sum(inst_claims),2),round(sum(rx_claims),2) from monthly_totals_data "+conditionStr;
		
		System.out.println("Query : \n"+str);
		Query query = getEntityManager().createNativeQuery(str);
		return query.getResultList();
	}
	
	@Override
	@Transactional
	public Double getSumOfSpecialistClaim(String provider, String pcpName, List<OptionsVM> locationList, String year) {
		String str = "",conditionStr = "";
		/*if(provider.equals("all") && pcpName.equals("all") && locationName.equals("all") && year.equals("all")) {
			str = "select round(sum(paid_amount),2) from specialty_claim_detail";
		}
		if(provider.equals("all") && pcpName.equals("all") && locationName.equals("all") && !year.equals("all")) {
			str = "select round(sum(paid_amount),2) from specialty_claim_detail where service_month like "+'\''+year+"%"+'\''+"";
		}
		if(provider.equals("all") && pcpName.equals("all") && !locationName.equals("all") && year.equals("all")) {
			str = "select round(sum(paid_amount),2) from specialty_claim_detail";
		}
		if(provider.equals("all") && pcpName.equals("all") && !locationName.equals("all") && !year.equals("all")) {
			str = "select round(sum(paid_amount),2) from specialty_claim_detail where service_month like "+'\''+year+"%"+'\'';
		}
		if(provider.equals("all") && !pcpName.equals("all") && locationName.equals("all") && year.equals("all")) {
			str = "select round(sum(paid_amount),2) from specialty_claim_detail where pcp_name="+'\''+pcpName+'\''+"";
		}
		if(provider.equals("all") && !pcpName.equals("all") && locationName.equals("all") && !year.equals("all")) {
			str = "select round(sum(paid_amount),2) from specialty_claim_detail where service_month like "+'\''+year+"%"+'\''+" and pcp_name="+'\''+pcpName+'\''+"";
		}
		if(provider.equals("all") && !pcpName.equals("all") && !locationName.equals("all") && year.equals("all")) {
			str = "select round(sum(paid_amount),2) from specialty_claim_detail where pcp_name="+'\''+pcpName+'\'';
		}
		if(provider.equals("all") && !pcpName.equals("all") && !locationName.equals("all") && !year.equals("all")) {
			str = "select round(sum(paid_amount),2) from specialty_claim_detail where pcp_name="+'\''+pcpName+'\''+" and service_month like "+'\''+year+"%"+'\''+"";
		}
		if(!provider.equals("all") && pcpName.equals("all") && locationName.equals("all") && year.equals("all")) {
			str = "select round(sum(paid_amount),2) from specialty_claim_detail where provider="+'\''+provider+'\'';
		}
		if(!provider.equals("all") && pcpName.equals("all") && locationName.equals("all") && !year.equals("all")) {
			str = "select round(sum(paid_amount),2) from specialty_claim_detail where provider="+'\''+provider+'\''+" and service_month like "+'\''+year+"%"+'\''+"";
		}
		if(!provider.equals("all") && pcpName.equals("all") && !locationName.equals("all") && year.equals("all")) {
			str = "select round(sum(paid_amount),2) from specialty_claim_detail where provider="+'\''+provider+'\'';
		}
		if(!provider.equals("all") && pcpName.equals("all") && !locationName.equals("all") && !year.equals("all")) {
			str = "select round(sum(paid_amount),2) from specialty_claim_detail where provider="+'\''+provider+'\''+" and service_month like "+'\''+year+"%"+'\''+"";
		}
		if(!provider.equals("all") && !pcpName.equals("all") && locationName.equals("all") && year.equals("all")) {
			str = "select round(sum(paid_amount),2) from specialty_claim_detail where provider="+'\''+provider+'\''+" and pcp_id="+'\''+pcpName+'\'';
		}
		if(!provider.equals("all") && !pcpName.equals("all") && locationName.equals("all") && !year.equals("all")) {
			str = "select round(sum(paid_amount),2) from specialty_claim_detail where provider="+'\''+provider+'\''+" and pcp_id="+'\''+pcpName+'\''+" and service_month like "+'\''+year+"%"+'\''+"";
		}
		if(!provider.equals("all") && !pcpName.equals("all") && !locationName.equals("all") && year.equals("all")) {
			str = "select round(sum(paid_amount),2) from specialty_claim_detail where provider="+'\''+provider+'\''+" and pcp_id="+'\''+pcpName+'\'';
		}
		if(!provider.equals("all") && !pcpName.equals("all") && !locationName.equals("all") && !year.equals("all")) {
			str = "select round(sum(paid_amount),2) from specialty_claim_detail where provider="+'\''+provider+'\''+" and pcp_id="+'\''+pcpName+'\''+" and service_month like "+'\''+year+"%"+'\''+"";
		}*/
		
		if(!provider.equals("all")) {
			conditionStr = conditionStr + " and provider="+'\''+provider+'\'';
		}
		if(!pcpName.equals("all")) {
			if(provider.equals("all"))
				conditionStr = conditionStr + " and pcp_name="+'\''+pcpName+'\'';
			else
				conditionStr = conditionStr + " and pcp_id="+'\''+pcpName+'\'';
		}
		
		if(!year.equals("all")) {
			conditionStr = conditionStr + " and service_month like "+'\''+year+"%"+'\'';
		}
		
		if(!conditionStr.equals(""))
			conditionStr = " where "+conditionStr.substring(4);
		
		str = "select round(sum(paid_amount),2) from specialty_claim_detail "+conditionStr;
		System.out.println("Specialist Query : \n"+str);
		Query query = getEntityManager().createNativeQuery(str);
		return (Double) query.getSingleResult();
	}
	
	@Override
	@Transactional
	public Double getSumOfCurrentMonth(String provider, String pcpName, List<OptionsVM> locationList, String year) {
		String str = "",conditionStr = "";
		
		int currentYear = 0,currentMonth = 0;
		
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
		}
		
		/*Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		currentYear = cal.get(Calendar.YEAR);
		currentMonth = cal.get(Calendar.MONTH);*/
		String currentMonthStr = "";
		
		if(year.equals(currentYear+"")) {
			
			if(currentMonth > 9) {
				currentMonthStr = currentYear+"/"+currentMonth+"%";
			} else {
				currentMonthStr = currentYear+"/0"+currentMonth+"%";
			}
		} else {
			currentMonthStr = year+"/"+12+"%";
		}
		
		/*if(provider.equals("all") && pcpName.equals("all") && locationName.equals("all") && year.equals("all")) {
			str = "select sum(membership) from monthly_totals_data";
		}
		if(provider.equals("all") && pcpName.equals("all") && locationName.equals("all") && !year.equals("all")) {
			str = "select sum(membership) from monthly_totals_data where month like "+'\''+currentMonthStr+'\''+"";
		}
		if(provider.equals("all") && pcpName.equals("all") && !locationName.equals("all") && year.equals("all")) {
			str = "select sum(membership) from monthly_totals_data where pcp_location="+'\''+locationName+'\''+"";
		}
		if(provider.equals("all") && pcpName.equals("all") && !locationName.equals("all") && !year.equals("all")) {
			str = "select sum(membership) from monthly_totals_data where pcp_location="+'\''+locationName+'\''+" and month like "+'\''+currentMonthStr+'\''+"";
		}
		if(provider.equals("all") && !pcpName.equals("all") && locationName.equals("all") && year.equals("all")) {
			str = "select sum(membership) from monthly_totals_data where pcp_name="+'\''+pcpName+'\'';
		}
		if(provider.equals("all") && !pcpName.equals("all") && locationName.equals("all") && !year.equals("all")) {
			str = "select sum(membership) from monthly_totals_data where pcp_name="+'\''+pcpName+'\''+" and month like "+'\''+currentMonthStr+'\''+"";
		}
		if(provider.equals("all") && !pcpName.equals("all") && !locationName.equals("all") && year.equals("all")) {
			str = "select sum(membership) from monthly_totals_data where pcp_name="+'\''+pcpName+'\''+" and pcp_location="+'\''+locationName+'\'';
		}
		if(provider.equals("all") && !pcpName.equals("all") && !locationName.equals("all") && !year.equals("all")) {
			str = "select sum(membership) from monthly_totals_data where pcp_name="+'\''+pcpName+'\''+" and pcp_location="+'\''+locationName+'\''+" and month like "+'\''+currentMonthStr+'\''+"";
		}
		if(!provider.equals("all") && pcpName.equals("all") && locationName.equals("all") && year.equals("all")) {
			str = "select sum(membership) from monthly_totals_data where provider="+'\''+provider+'\'';
		}
		if(!provider.equals("all") && pcpName.equals("all") && locationName.equals("all") && !year.equals("all")) {
			str = "select sum(membership) from monthly_totals_data where provider="+'\''+provider+'\''+" and month like "+'\''+currentMonthStr+'\''+"";
		}
		if(!provider.equals("all") && pcpName.equals("all") && !locationName.equals("all") && year.equals("all")) {
			str = "select sum(membership) from monthly_totals_data where provider="+'\''+provider+'\''+" and pcp_location="+'\''+locationName+'\'';
		}
		if(!provider.equals("all") && pcpName.equals("all") && !locationName.equals("all") && !year.equals("all")) {
			str = "select sum(membership) from monthly_totals_data where provider="+'\''+provider+'\''+" and pcp_location="+'\''+locationName+'\''+" and month like "+'\''+currentMonthStr+'\''+"";
		}
		if(!provider.equals("all") && !pcpName.equals("all") && locationName.equals("all") && year.equals("all")) {
			str = "select sum(membership) from monthly_totals_data where provider="+'\''+provider+'\''+" and pcp_id="+'\''+pcpName+'\'';
		}
		if(!provider.equals("all") && !pcpName.equals("all") && locationName.equals("all") && !year.equals("all")) {
			str = "select sum(membership) from monthly_totals_data where provider="+'\''+provider+'\''+" and pcp_id="+'\''+pcpName+'\''+" and month like "+'\''+currentMonthStr+'\''+"";
		}
		if(!provider.equals("all") && !pcpName.equals("all") && !locationName.equals("all") && year.equals("all")) {
			str = "select sum(membership) from monthly_totals_data where provider="+'\''+provider+'\''+" and pcp_id="+'\''+pcpName+'\''+" and pcp_location="+'\''+locationName+'\'';
		}
		if(!provider.equals("all") && !pcpName.equals("all") && !locationName.equals("all") && !year.equals("all")) {
			str = "select sum(membership) from monthly_totals_data where provider="+'\''+provider+'\''+" and pcp_id="+'\''+pcpName+'\''+" and pcp_location="+'\''+locationName+'\''+" and month like "+'\''+currentMonthStr+'\''+"";
		}*/
		
		if(!provider.equals("all")) {
			conditionStr = conditionStr + " and provider="+'\''+provider+'\'';
		}
		if(!pcpName.equals("all")) {
			if(provider.equals("all"))
				conditionStr = conditionStr + " and pcp_name="+'\''+pcpName+'\'';
			else
				conditionStr = conditionStr + " and pcp_id="+'\''+pcpName+'\'';
		}
		String locationCondition = "";
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
		
		if(!year.equals("all")) {
			conditionStr = conditionStr + " and month like "+'\''+currentMonthStr+'\'';
		}
		
		if(!conditionStr.equals(""))
			conditionStr = " where "+conditionStr.substring(4);
		
		str = "select sum(membership) from monthly_totals_data "+conditionStr;
		
		System.out.println("Current Month Query : \n"+str);
		Query query = getEntityManager().createNativeQuery(str);
		return query.getSingleResult() == null ? 0.0 : (Double)query.getSingleResult();
	}
	
}
