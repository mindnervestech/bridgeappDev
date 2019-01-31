package com.mnt.vm.reports;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SettledMonthsExpandReportPrintDataVM {

	@JsonProperty("Patient Name")
	public String patientName;

	@JsonProperty("PCP Name")
	public String pcpName;
	
	@JsonProperty("PCP Location")
	public String pcpLocation;
	
	@JsonProperty("Cost")
	public String cost;
	
	@JsonProperty("Claim Type")
	public String claimType;
	
	@JsonProperty("MRA")
	public String mra;

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
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

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getClaimType() {
		return claimType;
	}

	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}

	public String getMra() {
		return mra;
	}

	public void setMra(String mra) {
		this.mra = mra;
	}
}
