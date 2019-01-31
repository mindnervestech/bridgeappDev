package com.mnt.vm;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SpecialistComparisonPrintDataVM {

	@JsonProperty("Speciality Code")
	public String specialityCode;

	@JsonProperty("Number Of PCP")
	public String numberOfPcp;
	
	@JsonProperty("Number Of Claims")
	public String numberOfClaims;
	
	@JsonProperty("Number Of Beneficiaries")
	public String numberOfBeneficiaries;
	
	@JsonProperty("Cost Per Claim")
	public String costPerClaim;
	
	@JsonProperty("Cost Per Beneficiary")
	public String costPerBeneficiary;
	
	@JsonProperty("Total Cost")
	public String totalCost;

	public String getSpecialityCode() {
		return specialityCode;
	}

	public void setSpecialityCode(String specialityCode) {
		this.specialityCode = specialityCode;
	}

	public String getNumberOfClaims() {
		return numberOfClaims;
	}

	public void setNumberOfClaims(String numberOfClaims) {
		this.numberOfClaims = numberOfClaims;
	}

	public String getNumberOfBeneficiaries() {
		return numberOfBeneficiaries;
	}

	public void setNumberOfBeneficiaries(String numberOfBeneficiaries) {
		this.numberOfBeneficiaries = numberOfBeneficiaries;
	}

	public String getCostPerClaim() {
		return costPerClaim;
	}

	public void setCostPerClaim(String costPerClaim) {
		this.costPerClaim = costPerClaim;
	}

	public String getCostPerBeneficiary() {
		return costPerBeneficiary;
	}

	public void setCostPerBeneficiary(String costPerBeneficiary) {
		this.costPerBeneficiary = costPerBeneficiary;
	}

	public String getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
	}

	public String getNumberOfPcp() {
		return numberOfPcp;
	}

	public void setNumberOfPcp(String numberOfPcp) {
		this.numberOfPcp = numberOfPcp;
	}
}
