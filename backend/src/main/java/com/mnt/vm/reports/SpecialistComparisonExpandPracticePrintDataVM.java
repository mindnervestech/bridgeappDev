package com.mnt.vm.reports;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SpecialistComparisonExpandPracticePrintDataVM {
	
	@JsonProperty("Practice Name")
	public String practiceName;
	
	@JsonProperty("Speciality Type")
	public String specialityType;
	
	@JsonProperty("Patient Name")
	public String patientName;
	
	@JsonProperty("PCP Name")
	public String pcpName;
	
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
