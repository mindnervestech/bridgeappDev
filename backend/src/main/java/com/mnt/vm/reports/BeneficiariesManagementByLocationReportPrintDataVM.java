package com.mnt.vm.reports;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BeneficiariesManagementByLocationReportPrintDataVM {
	
	@JsonProperty("PCP Location")
	String pcpLocation;
	
	@JsonProperty("Average MRA")
	String mra;
	
	@JsonProperty("Total Cost")
	String totalCost;

	public String getPcpLocation() {
		return pcpLocation;
	}

	public void setPcpLocation(String pcpLocation) {
		this.pcpLocation = pcpLocation;
	}

	public String getMra() {
		return mra;
	}

	public void setMra(String mra) {
		this.mra = mra;
	}

	public String getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
	}

}
