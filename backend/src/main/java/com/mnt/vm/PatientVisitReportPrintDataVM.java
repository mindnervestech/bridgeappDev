package com.mnt.vm;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PatientVisitReportPrintDataVM {

	@JsonProperty("Patient Name")
	public String PatientName;

	@JsonProperty("HICN")
	public String hicn;
	
	@JsonProperty("PCP Name")
	public String pcpName;
	
	@JsonProperty("Termed Month")
	public String termedMonth;
	
	@JsonProperty("IPA Effective Date")
	public String ipaEffectiveDate;
	
	@JsonProperty("Total Number Of ER Visits")
	public String totalErVisits;
	
	@JsonProperty("Total Cost")
	public String totalCost;

	public String getPatientName() {
		return PatientName;
	}

	public void setPatientName(String patientName) {
		PatientName = patientName;
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
