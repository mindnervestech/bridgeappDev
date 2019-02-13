package com.mnt.vm;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MembershipManagementPrintDataVM {

	@JsonProperty("Plan Name")
	public String planName;

	@JsonProperty("Medicare ID")
	public String medicareId;
	
	@JsonProperty("Insurance ID")
	public String insuranceId;
	
	@JsonProperty("Patient Name")
	public String patientName;
	
	@JsonProperty("Patient DOB")
	public String patientDob;
	
	@JsonProperty("PCP Name")
	public String assignedPcp;
	
	@JsonProperty("PCP Location")
	public String pcpLocation;
	
	@JsonProperty("IPA Effective Date")
	public String ipaEffectiveDate;
	
	@JsonProperty("MRA")
	public String mra;
	
	@JsonProperty("Total Patient Cost")
	public String totalPatientCost;

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getMedicareId() {
		return medicareId;
	}

	public void setMedicareId(String medicareId) {
		this.medicareId = medicareId;
	}

	public String getInsuranceId() {
		return insuranceId;
	}

	public void setInsuranceId(String insuranceId) {
		this.insuranceId = insuranceId;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getPatientDob() {
		return patientDob;
	}

	public void setPatientDob(String patientDob) {
		this.patientDob = patientDob;
	}

	public String getAssignedPcp() {
		return assignedPcp;
	}

	public void setAssignedPcp(String assignedPcp) {
		this.assignedPcp = assignedPcp;
	}

	public String getPcpLocation() {
		return pcpLocation;
	}

	public void setPcpLocation(String pcpLocation) {
		this.pcpLocation = pcpLocation;
	}

	public String getIpaEffectiveDate() {
		return ipaEffectiveDate;
	}

	public void setIpaEffectiveDate(String ipaEffectiveDate) {
		this.ipaEffectiveDate = ipaEffectiveDate;
	}

	public String getMra() {
		return mra;
	}

	public void setMra(String mra) {
		this.mra = mra;
	}

	public String getTotalPatientCost() {
		return totalPatientCost;
	}

	public void setTotalPatientCost(String totalPatientCost) {
		this.totalPatientCost = totalPatientCost;
	}
	
}
