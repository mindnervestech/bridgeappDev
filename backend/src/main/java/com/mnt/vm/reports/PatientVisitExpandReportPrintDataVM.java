package com.mnt.vm.reports;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PatientVisitExpandReportPrintDataVM {

	@JsonProperty("Claim ID")
	public String claimId;

	@JsonProperty("Claim Date")
	public String claimDate;
	
	@JsonProperty("Claim Type")
	public String claimType;
	
	@JsonProperty("Clinic Name")
	public String clinicName;
	
	@JsonProperty("PCP Name")
	public String pcpName;
	
	@JsonProperty("ICD Codes")
	public String icdCodes;
	
	@JsonProperty("HCC Codes")
	public String hccCodes;
	
	@JsonProperty("DRG Code")
	public String drgCode;
	
	@JsonProperty("Betos Cat")
	public String betosCat;
	
	@JsonProperty("Cost")
	public String cost;

	public String getClaimId() {
		return claimId;
	}

	public void setClaimId(String claimId) {
		this.claimId = claimId;
	}

	public String getClaimDate() {
		return claimDate;
	}

	public void setClaimDate(String claimDate) {
		this.claimDate = claimDate;
	}

	public String getClaimType() {
		return claimType;
	}

	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}

	public String getClinicName() {
		return clinicName;
	}

	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}

	public String getPcpName() {
		return pcpName;
	}

	public void setPcpName(String pcpName) {
		this.pcpName = pcpName;
	}

	public String getIcdCodes() {
		return icdCodes;
	}

	public void setIcdCodes(String icdCodes) {
		this.icdCodes = icdCodes;
	}

	public String getHccCodes() {
		return hccCodes;
	}

	public void setHccCodes(String hccCodes) {
		this.hccCodes = hccCodes;
	}

	public String getDrgCode() {
		return drgCode;
	}

	public void setDrgCode(String drgCode) {
		this.drgCode = drgCode;
	}

	public String getBetosCat() {
		return betosCat;
	}

	public void setBetosCat(String betosCat) {
		this.betosCat = betosCat;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}
	
}
