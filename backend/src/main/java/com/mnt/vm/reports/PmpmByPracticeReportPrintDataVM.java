package com.mnt.vm.reports;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PmpmByPracticeReportPrintDataVM {

	@JsonProperty("Facility Location Name")
	public String facilityLocationName;

	@JsonProperty("PCP Name")
	public String providerName;
	
	@JsonProperty("MRA")
	public String mra;
	
	@JsonProperty("Total Cost")
	public String totalCost;
	
	@JsonProperty("Total Number Of Member Month")
	public String totalNumberOfMemberMonth;
	
	@JsonProperty("PMPM")
	public String pmpm;
	
	@JsonProperty("PMPY")
	public String pmpy;
	
	@JsonProperty("Total Expenses")
	public String totalPremium;
	
	@JsonProperty("IPA Premium")
	public String ipaPremium;
	
	@JsonProperty("Total Expenses - IPA Premium")
	public String difference;

	public String getFacilityLocationName() {
		return facilityLocationName;
	}

	public void setFacilityLocationName(String facilityLocationName) {
		this.facilityLocationName = facilityLocationName;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
	}

	public String getTotalNumberOfMemberMonth() {
		return totalNumberOfMemberMonth;
	}

	public void setTotalNumberOfMemberMonth(String totalNumberOfMemberMonth) {
		this.totalNumberOfMemberMonth = totalNumberOfMemberMonth;
	}

	public String getPmpm() {
		return pmpm;
	}

	public void setPmpm(String pmpm) {
		this.pmpm = pmpm;
	}

	public String getPmpy() {
		return pmpy;
	}

	public void setPmpy(String pmpy) {
		this.pmpy = pmpy;
	}

	public String getTotalPremium() {
		return totalPremium;
	}

	public void setTotalPremium(String totalPremium) {
		this.totalPremium = totalPremium;
	}

	public String getIpaPremium() {
		return ipaPremium;
	}

	public void setIpaPremium(String ipaPremium) {
		this.ipaPremium = ipaPremium;
	}

	public String getDifference() {
		return difference;
	}

	public void setDifference(String difference) {
		this.difference = difference;
	}

	public String getMra() {
		return mra;
	}

	public void setMra(String mra) {
		this.mra = mra;
	}
}
