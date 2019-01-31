package com.mnt.vm.reports;

public class SettledMonthsExpandReportVM {

	public String patientName;
	public String pcpName;
	public String pcpLocation;
	public String cost;
	public String claimType;
	public String mra;
	
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
	public String getPcpLocation() {
		return pcpLocation;
	}
	public void setPcpLocation(String pcpLocation) {
		this.pcpLocation = pcpLocation;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getClaimType() {
		return claimType;
	}
	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}
	public String getMra() {
		return mra;
	}
	public void setMra(String mra) {
		this.mra = mra;
	}
}
