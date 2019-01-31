package com.mnt.vm.reports;

public class SpecialistComparisonExpandReportVM {

	public String practiceName;
	public String specialityType;
	public String numberOfClaims;
	public String averageCostPerClaim;
	public String cost;
	
	public String getPracticeName() {
		return practiceName;
	}
	public void setPracticeName(String practiceName) {
		this.practiceName = practiceName;
	}
	public String getSpecialityType() {
		return specialityType;
	}
	public void setSpecialityType(String specialityType) {
		this.specialityType = specialityType;
	}
	public String getNumberOfClaims() {
		return numberOfClaims;
	}
	public void setNumberOfClaims(String numberOfClaims) {
		this.numberOfClaims = numberOfClaims;
	}
	public String getAverageCostPerClaim() {
		return averageCostPerClaim;
	}
	public void setAverageCostPerClaim(String averageCostPerClaim) {
		this.averageCostPerClaim = averageCostPerClaim;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
}
