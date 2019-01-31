package com.mnt.vm.reports;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BeneficiariesManagementByClinicReportPrintDataVM {

	@JsonProperty("Clinic Name")
	String clinicName;
	
	@JsonProperty("Total Cost")
	String totalCost;

	public String getClinicName() {
		return clinicName;
	}

	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}

	public String getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
	}
}
