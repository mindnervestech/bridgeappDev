package com.mnt.vm.reports;

public class SpecialistComparisonExpandPracticeReportVM {

	public String practiceName;
	public String specialityType;
	public String patientName;
	public String pcpName;
	public String numberOfClaims;
	public String averageCostPerClaim;
	public String cost;
	public String medicareId;
	
	
	

	public String getMedicareId() {
		return medicareId;
	}
	public void setMedicareId(String medicareId) {
		this.medicareId = medicareId;
	}
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
