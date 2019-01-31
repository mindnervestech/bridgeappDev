package com.mnt.vm;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdmissionsReportPrintDataVM {

	@JsonProperty("Patient Name")
	public String PatientName;

	@JsonProperty("HICN/Subscriber ID")
	public String subscriberId;
	
	@JsonProperty("PCP Name")
	public String pcpName;
	
	@JsonProperty("Eligible Month")
	public String eligibleMonth;
	
	@JsonProperty("Total Number Of Admissions")
	public String totalNumberOfAdmissions;
	
	@JsonProperty("Total Cost")
	public String totalCost;

	public String getSubscriberId() {
		return subscriberId;
	}

	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}

	public String getPatientName() {
		return PatientName;
	}

	public void setPatientName(String patientName) {
		PatientName = patientName;
	}

	public String getPcpName() {
		return pcpName;
	}

	public void setPcpName(String pcpName) {
		this.pcpName = pcpName;
	}

	public String getEligibleMonth() {
		return eligibleMonth;
	}

	public void setEligibleMonth(String eligibleMonth) {
		this.eligibleMonth = eligibleMonth;
	}

	public String getTotalNumberOfAdmissions() {
		return totalNumberOfAdmissions;
	}

	public void setTotalNumberOfAdmissions(String totalNumberOfAdmissions) {
		this.totalNumberOfAdmissions = totalNumberOfAdmissions;
	}

	public String getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
	}
}
