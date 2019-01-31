package com.mnt.dao;

import java.util.List;

import com.mnt.domain.MonthlyTotalsData;
import com.mnt.vm.OptionsVM;

public interface MonthlyTotalsDataDao  extends BaseDao<MonthlyTotalsData> {

	public List<Object[]> getSumOfClaim(String provider, String pcpName, List<OptionsVM> locationList, String year);
	public Double getSumOfSpecialistClaim(String provider, String pcpName, List<OptionsVM> locationList, String year);
	public Double getSumOfCurrentMonth(String provider, String pcpName, List<OptionsVM> locationList, String year);
}
