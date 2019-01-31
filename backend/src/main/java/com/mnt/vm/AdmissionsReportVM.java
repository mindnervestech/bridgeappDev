package com.mnt.vm;

public class AdmissionsReportVM {

	public String patientName;
	public String subscriberId;
	public String pcpName;
	public String eligibleMonth;
	public String totalNoOfAdmissions;
	public String totalCost;
	
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getSubscriberId() {
		return subscriberId;
	}
	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
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
	public String getTotalNoOfAdmissions() {
		return totalNoOfAdmissions;
	}
	public void setTotalNoOfAdmissions(String totalNoOfAdmissions) {
		this.totalNoOfAdmissions = totalNoOfAdmissions;
	}
	public String getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
	}
}
