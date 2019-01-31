package com.mnt.vm;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SummaryReportPrintDataVM {

	@JsonProperty("PCP Location")
	public String pcpLocation;

	@JsonProperty("Month")
	public String month;
	
	@JsonProperty("Members")
	public String members;
	
	@JsonProperty("Ma Premium")
	public String maPremium;
	
	@JsonProperty("Part D Premium")
	public String partDPremium;
	
	@JsonProperty("Total Premium")
	public String totalPremium;
	
	@JsonProperty("IPA Premium")
	public String ipaPremium;
	
	@JsonProperty("PCP Cap")
	public String pcpCap;
	
	@JsonProperty("Spec Cost")
	public String specCost;
	
	@JsonProperty("Prof Claims")
	public String profClaims;
	
	@JsonProperty("Inst Claims")
	public String instClaims;
	
	@JsonProperty("Rx Claims")
	public String rxClaims;
	
	@JsonProperty("IBNR Dollars")
	public String ibnrDollars;
	
	@JsonProperty("Reinsurance Premium")
	public String reinsurancePremium;
	
	@JsonProperty("Spec Cap")
	public String specCap;
	
	@JsonProperty("Total Expenses")
	public String totalExpenses;
	
	@JsonProperty("Reinsurance Recovered")
	public String reinsuranceRecovered;
	
	@JsonProperty("Rx Admin")
	public String rxAdmin;
	
	@JsonProperty("Silver Sneaker Utilization")
	public String silverSneakerUtilization;
	
	@JsonProperty("PBA")
	public String pba;
	
	@JsonProperty("Humana At Home")
	public String humanaAtHome;
	
	@JsonProperty("Dental FFS")
	public String dentalFFS;

	public String getPcpLocation() {
		return pcpLocation;
	}

	public void setPcpLocation(String pcpLocation) {
		this.pcpLocation = pcpLocation;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getMembers() {
		return members;
	}

	public void setMembers(String members) {
		this.members = members;
	}

	public String getMaPremium() {
		return maPremium;
	}

	public void setMaPremium(String maPremium) {
		this.maPremium = maPremium;
	}

	public String getPartDPremium() {
		return partDPremium;
	}

	public void setPartDPremium(String partDPremium) {
		this.partDPremium = partDPremium;
	}

	public String getTotalPremium() {
		return totalPremium;
	}

	public void setTotalPremium(String totalPremium) {
		this.totalPremium = totalPremium;
	}

	public String getIpaPremium() {
		return ipaPremium;
	}

	public void setIpaPremium(String ipaPremium) {
		this.ipaPremium = ipaPremium;
	}

	public String getPcpCap() {
		return pcpCap;
	}

	public void setPcpCap(String pcpCap) {
		this.pcpCap = pcpCap;
	}

	public String getSpecCost() {
		return specCost;
	}

	public void setSpecCost(String specCost) {
		this.specCost = specCost;
	}

	public String getProfClaims() {
		return profClaims;
	}

	public void setProfClaims(String profClaims) {
		this.profClaims = profClaims;
	}

	public String getInstClaims() {
		return instClaims;
	}

	public void setInstClaims(String instClaims) {
		this.instClaims = instClaims;
	}

	public String getRxClaims() {
		return rxClaims;
	}

	public void setRxClaims(String rxClaims) {
		this.rxClaims = rxClaims;
	}

	public String getIbnrDollars() {
		return ibnrDollars;
	}

	public void setIbnrDollars(String ibnrDollars) {
		this.ibnrDollars = ibnrDollars;
	}

	public String getReinsurancePremium() {
		return reinsurancePremium;
	}

	public void setReinsurancePremium(String reinsurancePremium) {
		this.reinsurancePremium = reinsurancePremium;
	}

	public String getSpecCap() {
		return specCap;
	}

	public void setSpecCap(String specCap) {
		this.specCap = specCap;
	}

	public String getTotalExpenses() {
		return totalExpenses;
	}

	public void setTotalExpenses(String totalExpenses) {
		this.totalExpenses = totalExpenses;
	}

	public String getReinsuranceRecovered() {
		return reinsuranceRecovered;
	}

	public void setReinsuranceRecovered(String reinsuranceRecovered) {
		this.reinsuranceRecovered = reinsuranceRecovered;
	}

	public String getRxAdmin() {
		return rxAdmin;
	}

	public void setRxAdmin(String rxAdmin) {
		this.rxAdmin = rxAdmin;
	}

	public String getSilverSneakerUtilization() {
		return silverSneakerUtilization;
	}

	public void setSilverSneakerUtilization(String silverSneakerUtilization) {
		this.silverSneakerUtilization = silverSneakerUtilization;
	}

	public String getPba() {
		return pba;
	}

	public void setPba(String pba) {
		this.pba = pba;
	}

	public String getHumanaAtHome() {
		return humanaAtHome;
	}

	public void setHumanaAtHome(String humanaAtHome) {
		this.humanaAtHome = humanaAtHome;
	}

	public String getDentalFFS() {
		return dentalFFS;
	}

	public void setDentalFFS(String dentalFFS) {
		this.dentalFFS = dentalFFS;
	}
}
