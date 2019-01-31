package com.mnt.vm;

import java.util.List;

public class ClaimDetailsVM {

	public Double professional;
	public Double institutional;
	public Double speciality;
	public Double prescription;
	public int currentMonth;
	public String totalsType;
	public String year;
	public String type;
	public String provider;
	public String pcpName;
	public String pcpLocation;
	public Double totalExpenses[];
	public Double ipaPremiumValue[];
	List<OptionsVM> pcpList;
	
	public Double getProfessional() {
		return professional;
	}
	public void setProfessional(Double professional) {
		this.professional = professional;
	}
	public Double getInstitutional() {
		return institutional;
	}
	public void setInstitutional(Double institutional) {
		this.institutional = institutional;
	}
	public Double getSpeciality() {
		return speciality;
	}
	public void setSpeciality(Double speciality) {
		this.speciality = speciality;
	}
	public String getTotalsType() {
		return totalsType;
	}
	public void setTotalsType(String totalsType) {
		this.totalsType = totalsType;
	}
	public Double getPrescription() {
		return prescription;
	}
	public void setPrescription(Double prescription) {
		this.prescription = prescription;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getPcpName() {
		return pcpName;
	}
	public void setPcpName(String pcpName) {
		this.pcpName = pcpName;
	}
	public String getPcpLocation() {
		return pcpLocation;
	}
	public void setPcpLocation(String pcpLocation) {
		this.pcpLocation = pcpLocation;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<OptionsVM> getPcpList() {
		return pcpList;
	}
	public void setPcpList(List<OptionsVM> pcpList) {
		this.pcpList = pcpList;
	}
	public int getCurrentMonth() {
		return currentMonth;
	}
	public void setCurrentMonth(int currentMonth) {
		this.currentMonth = currentMonth;
	}
	public Double[] getTotalExpenses() {
		return totalExpenses;
	}
	public void setTotalExpenses(Double[] totalExpenses) {
		this.totalExpenses = totalExpenses;
	}
	public Double[] getIpaPremiumValue() {
		return ipaPremiumValue;
	}
	public void setIpaPremiumValue(Double[] ipaPremiumValue) {
		this.ipaPremiumValue = ipaPremiumValue;
	}
}
