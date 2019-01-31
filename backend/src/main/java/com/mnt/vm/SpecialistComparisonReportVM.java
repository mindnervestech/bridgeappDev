package com.mnt.vm;

public class SpecialistComparisonReportVM {

	public String specialityCode;
	public String numberOfClaims;
	public String numberOfPcp;
	public String numberOfBeneficiaries;
	public String costPerClaim;
	public String costPerBeneficiary;
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
