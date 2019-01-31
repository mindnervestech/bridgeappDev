package com.mnt.vm;

public class PatientVisitReportVM {

	public String patientName;
	public String hicn;
	public String pcpName;
	public String termedMonth;
	public String ipaEffectiveDate;
	public String totalErVisits;
	public String totalCost;
	
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getHicn() {
		return hicn;
	}
	public void setHicn(String hicn) {
		this.hicn = hicn;
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
	public String getIpaEffectiveDate() {
		return ipaEffectiveDate;
	}
	public void setIpaEffectiveDate(String ipaEffectiveDate) {
		this.ipaEffectiveDate = ipaEffectiveDate;
	}
	public String getTotalErVisits() {
		return totalErVisits;
	}
	public void setTotalErVisits(String totalErVisits) {
		this.totalErVisits = totalErVisits;
	}
	public String getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
	}
}
