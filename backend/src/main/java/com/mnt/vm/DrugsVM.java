package com.mnt.vm;

import java.util.List;

public class DrugsVM {

	public List<String> drugNames;
	public List<Double> expenditures;
	public List<OptionsVM> planList;
	public List<OptionsVM> pcpList;
	public List<OptionsVM> yearsList;
	public List<OptionsVM> specialityList;
	public List<OptionsVM> locationList;
	
	public List<String> getDrugNames() {
		return drugNames;
	}
	public void setDrugNames(List<String> drugNames) {
		this.drugNames = drugNames;
	}
	public List<Double> getExpenditures() {
		return expenditures;
	}
	public void setExpenditures(List<Double> expenditures) {
		this.expenditures = expenditures;
	}
	public List<OptionsVM> getPlanList() {
		return planList;
	}
	public void setPlanList(List<OptionsVM> planList) {
		this.planList = planList;
	}
	public List<OptionsVM> getPcpList() {
		return pcpList;
	}
	public void setPcpList(List<OptionsVM> pcpList) {
		this.pcpList = pcpList;
	}
	public List<OptionsVM> getYearsList() {
		return yearsList;
	}
	public void setYearsList(List<OptionsVM> yearsList) {
		this.yearsList = yearsList;
	}
	public List<OptionsVM> getSpecialityList() {
		return specialityList;
	}
	public void setSpecialityList(List<OptionsVM> specialityList) {
		this.specialityList = specialityList;
	}
	public List<OptionsVM> getLocationList() {
		return locationList;
	}
	public void setLocationList(List<OptionsVM> locationList) {
		this.locationList = locationList;
	}
}
