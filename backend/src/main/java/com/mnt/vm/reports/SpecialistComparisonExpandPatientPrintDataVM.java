package com.mnt.vm.reports;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SpecialistComparisonExpandPatientPrintDataVM {
	
	@JsonProperty("Practice Name")
	public String practiceName;
	
	@JsonProperty("Speciality Type")
	public String specialityType;
	
	@JsonProperty("Patient Name")
	public String patientName;
	
	@JsonProperty("Cost")
	public String cost;
	
	@JsonProperty("PCP Name")
	public String pcpName;

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

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getPcpName() {
		return pcpName;
	}

	public void setPcpName(String pcpName) {
		this.pcpName = pcpName;
	}
	
	
}
