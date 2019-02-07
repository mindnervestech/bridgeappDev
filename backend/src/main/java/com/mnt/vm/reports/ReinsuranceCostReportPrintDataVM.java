package com.mnt.vm.reports;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReinsuranceCostReportPrintDataVM {

	@JsonProperty("Plan Name")
	public String planName;
	
	@JsonProperty("Policy Period")
	public String policyPeriod;
	
	@JsonProperty("Patient Last Name")
	public String patientLastName;
	
	@JsonProperty("Patient First Name")
	public String patientFirstName;
	
	@JsonProperty("HICN/SubscriberID")
	public String subscriberID;
	
	@JsonProperty("Effective Date")
	public String effectiveDate;
	
	@JsonProperty("Termed Month")
	public String termedMonth;
	
	@JsonProperty("DOB")
	public String dob;
	
	@JsonProperty("Status")
	public String status;
	
	@JsonProperty("Gender")
	public String gender;
	
	@JsonProperty("PCP Name")
	public String pcpName;
	
	@JsonProperty("Total Claims Cost")
	public String totalClaimsCost;

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getPolicyPeriod() {
		return policyPeriod;
	}

	public void setPolicyPeriod(String policyPeriod) {
		this.policyPeriod = policyPeriod;
	}

	public String getPatientLastName() {
		return patientLastName;
	}

	public void setPatientLastName(String patientLastName) {
		this.patientLastName = patientLastName;
	}

	public String getPatientFirstName() {
		return patientFirstName;
	}

	public void setPatientFirstName(String patientFirstName) {
		this.patientFirstName = patientFirstName;
	}

	public String getSubscriberID() {
		return subscriberID;
	}

	public void setSubscriberID(String subscriberID) {
		this.subscriberID = subscriberID;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getTermedMonth() {
		return termedMonth;
	}

	public void setTermedMonth(String termedMonth) {
		this.termedMonth = termedMonth;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPcpName() {
		return pcpName;
	}

	public void setPcpName(String pcpName) {
		this.pcpName = pcpName;
	}

	public String getTotalClaimsCost() {
		return totalClaimsCost;
	}

	public void setTotalClaimsCost(String totalClaimsCost) {
		this.totalClaimsCost = totalClaimsCost;
	}
	
	
}
