package com.mnt.vm.reports;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BeneficiariesManagementByDoctorPrintDataVM {

	
	@JsonProperty("PCP Name")
	public String pcpName;

	@JsonProperty("PCP Location")
	public String pcpLocation;
	
	@JsonProperty("Average MRA")
	public String averageMra;
	
	@JsonProperty("Total Cost")
	public String totalCost;
	
	public String pcpId;
	public String specCost;
	public String pcpCap;
	public String reinsurancePrem;
	public String instClaims;
	public String profClaims;
	public String rxClaims;	

	public String getPcpName() {
		return pcpName;
	}

	public void setPcpName(String pcpName) {
		this.pcpName = pcpName;
	}

	public String getPcpLocation() {
		return pcpLocation;
	}

	public void setPcpLocation(String pcpLocation) {
		this.pcpLocation = pcpLocation;
	}

	public String getAverageMra() {
		return averageMra;
	}

	public void setAverageMra(String averageMra) {
		this.averageMra = averageMra;
	}

	public String getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
	}

	public String getPcpId() {
		return pcpId;
	}

	public void setPcpId(String pcpId) {
		this.pcpId = pcpId;
	}

	public String getSpecCost() {
		return specCost;
	}

	public void setSpecCost(String specCost) {
		this.specCost = specCost;
	}

	public String getPcpCap() {
		return pcpCap;
	}

	public void setPcpCap(String pcpCap) {
		this.pcpCap = pcpCap;
	}

	public String getReinsurancePrem() {
		return reinsurancePrem;
	}

	public void setReinsurancePrem(String reinsurancePrem) {
		this.reinsurancePrem = reinsurancePrem;
	}

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

	public String getRxClaims() {
		return rxClaims;
	}

	public void setRxClaims(String rxClaims) {
		this.rxClaims = rxClaims;
	}
}
