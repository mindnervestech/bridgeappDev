package com.mnt.vm.reports;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SpecialistComparisonExpandPrintDataVM {

	@JsonProperty("Practice Name")
	public String practiceName;
	
	@JsonProperty("Speciality Type")
	public String specialityType;

	@JsonProperty("Number Of Claims")
	public String numberOfClaims;
	
	@JsonProperty("Average Cost Per Claim")
	public String averageCostPerClaim;
	
	@JsonProperty("Cost")
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
