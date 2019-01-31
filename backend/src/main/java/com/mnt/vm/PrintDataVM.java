package com.mnt.vm;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PrintDataVM {

	@JsonProperty("Plan Name")
	public String planName;
	
	@JsonProperty("Provider Name")
	public String providerName;
	
	@JsonProperty("Medicare ID")
	public String medicareId;
	
	@JsonProperty("Patient Name")
	public String patientName;
	
	@JsonProperty("ICD9/10 Code")
	public String icdCode;
	
	@JsonProperty("HCC Codes")
	public String hccCodes;
	
	@JsonProperty("Termed Month")
	public String termedMonth;
	
	@JsonProperty("Eligible Month")
	public String eligibleMonth;
	
	@JsonProperty("Cost")
	public String cost;

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getIcdCode() {
		return icdCode;
	}

	public void setIcdCode(String icdCode) {
		this.icdCode = icdCode;
	}

	public String getHccCodes() {
		return hccCodes;
	}

	public void setHccCodes(String hccCodes) {
		this.hccCodes = hccCodes;
	}

	public String getTermedMonth() {
		return termedMonth;
	}

	public void setTermedMonth(String termedMonth) {
		this.termedMonth = termedMonth;
	}

	public String getEligibleMonth() {
		return eligibleMonth;
	}

	public void setEligibleMonth(String eligibleMonth) {
		this.eligibleMonth = eligibleMonth;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getMedicareId() {
		return medicareId;
	}

	public void setMedicareId(String medicareId) {
		this.medicareId = medicareId;
	}
}
