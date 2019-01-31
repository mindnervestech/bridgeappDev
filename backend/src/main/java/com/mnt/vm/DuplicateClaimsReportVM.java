package com.mnt.vm;

public class DuplicateClaimsReportVM {

	public String subscriberId;
	public String planName;
	public String patientName;
	public String pcp;
	public String eligibleMonth;
	public String termedMonth;
	public String claimDate;
	public String duplicativeCost;
	public String claimType;
	
	public String getSubscriberId() {
		return subscriberId;
	}
	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
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
	public String getClaimType() {
		return claimType;
	}
	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}
}
