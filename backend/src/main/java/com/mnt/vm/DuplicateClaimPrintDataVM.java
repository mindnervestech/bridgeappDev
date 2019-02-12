package com.mnt.vm;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DuplicateClaimPrintDataVM {

	@JsonProperty("HICN/Subscriber ID")
	public String subscriberId;
	
	@JsonProperty("Plan Name")
	public String PlanName;
	
	@JsonProperty("Patient Name")
	public String PatientName;
	
	@JsonProperty("PCP Name")
	public String pcp;
	
	@JsonProperty("Eligible Month")
	public String eligibleMonth;
	
	@JsonProperty("Termed Month")
	public String termedMonth;
	
	@JsonProperty("Claim Date")
	public String claimDate;
	
	@JsonProperty("Duplicative Cost")
	public String duplicativeCost;

	public String getSubscriberId() {
		return subscriberId;
	}

	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}

	public String getPlanName() {
		return PlanName;
	}

	public void setPlanName(String planName) {
		PlanName = planName;
	}

	public String getPatientName() {
		return PatientName;
	}

	public void setPatientName(String patientName) {
		PatientName = patientName;
	}

	public String getPcp() {
		return pcp;
	}

	public void setPcp(String pcp) {
		this.pcp = pcp;
	}

	public String getEligibleMonth() {
		return eligibleMonth;
	}

	public void setEligibleMonth(String eligibleMonth) {
		this.eligibleMonth = eligibleMonth;
	}

	public String getTermedMonth() {
		return termedMonth;
	}

	public void setTermedMonth(String termedMonth) {
		this.termedMonth = termedMonth;
	}

	public String getClaimDate() {
		return claimDate;
	}

	public void setClaimDate(String claimDate) {
		this.claimDate = claimDate;
	}

	public String getDuplicativeCost() {
		return duplicativeCost;
	}

	public void setDuplicativeCost(String duplicativeCost) {
		this.duplicativeCost = duplicativeCost;
	}
}
