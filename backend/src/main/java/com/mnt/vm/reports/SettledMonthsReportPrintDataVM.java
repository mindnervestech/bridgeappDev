package com.mnt.vm.reports;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SettledMonthsReportPrintDataVM {

	@JsonProperty("PCP Location")
	public String pcpLocation;

	@JsonProperty("Month")
	public String month;
	
	@JsonProperty("Membership")
	public String membership;
	
	@JsonProperty("Ipa Premium")
	public String ipaPremium;
	
	@JsonProperty("Total Expenses")
	public String totalExpenses;
	
	@JsonProperty("StopLoss")
	public String stoploss;
	
	@JsonProperty("Net Premium")
	public String netPremium;
	
	@JsonProperty("Risk Sharing")
	public String riskSharing;
	
	@JsonProperty("Surplus/Deficit")
	public String surplusDeficit;

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

	public String getMembership() {
		return membership;
	}

	public void setMembership(String membership) {
		this.membership = membership;
	}

	public String getIpaPremium() {
		return ipaPremium;
	}

	public void setIpaPremium(String ipaPremium) {
		this.ipaPremium = ipaPremium;
	}

	public String getTotalExpenses() {
		return totalExpenses;
	}

	public void setTotalExpenses(String totalExpenses) {
		this.totalExpenses = totalExpenses;
	}

	public String getStoploss() {
		return stoploss;
	}

	public void setStoploss(String stoploss) {
		this.stoploss = stoploss;
	}

	public String getNetPremium() {
		return netPremium;
	}

	public void setNetPremium(String netPremium) {
		this.netPremium = netPremium;
	}

	public String getRiskSharing() {
		return riskSharing;
	}

	public void setRiskSharing(String riskSharing) {
		this.riskSharing = riskSharing;
	}

	public String getSurplusDeficit() {
		return surplusDeficit;
	}

	public void setSurplusDeficit(String surplusDeficit) {
		this.surplusDeficit = surplusDeficit;
	}
}
