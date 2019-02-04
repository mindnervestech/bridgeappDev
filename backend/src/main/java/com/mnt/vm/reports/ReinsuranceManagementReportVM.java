package com.mnt.vm.reports;

public class ReinsuranceManagementReportVM {
	
	public String hicn;
	public String planName;
	public String patientName;
	public String pcpName;
	public String termedMonth;
	public String instClaims;
	public String profClaims;
	public String totalCost;
	
	
	public String getInstClaims() {
		return instClaims;
	}
	public void setInstClaims(String instClaims) {
		this.instClaims = instClaims;
	}
	public String getProfClaims() {
		return profClaims;
	}
	public void setProfClaims(String profClaims) {
		this.profClaims = profClaims;
	}
	public String getHicn() {
		return hicn;
	}
	public void setHicn(String hicn) {
		this.hicn = hicn;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
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
	public String getTermedMonth() {
		return termedMonth;
	}
	public void setTermedMonth(String termedMonth) {
		this.termedMonth = termedMonth;
	}
	public String getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
	}
	

}
