package com.mnt.vm.reports;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReinsuranceManagementReportPrintDataVM {

	@JsonProperty("SubscriberID")
	public String hicn;
	
	@JsonProperty("Plan Name")
	public String planName;
	
	@JsonProperty("Patient Name")
	public String patientName;
	
	@JsonProperty("PCP Name")
	public String pcpName;
	
	@JsonProperty("Termed Month")
	public String termedMonth;
	
	@JsonProperty("INST Claims")
	public String instClaims;
	
	@JsonProperty("PROF Claims")
	public String profClaims;
	
	
	@JsonProperty("Total Cost")
	public String totalCost;
	
	
	
	
	
	public String getInstClaims() {
		return instClaims;
	}

	public void setInstClaims(String instClaims) {
		this.instClaims = instClaims;
	}

	public String getProfClaims() {
		return profClaims;
	}

	public void setProfClaims(String profClaims) {
		this.profClaims = profClaims;
	}

	public String getHicn() {
		return hicn;
	}

	public void setHicn(String hicn) {
		this.hicn = hicn;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

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

	public String getTermedMonth() {
		return termedMonth;
	}

	public void setTermedMonth(String termedMonth) {
		this.termedMonth = termedMonth;
	}

	public String getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
	}

	
}
