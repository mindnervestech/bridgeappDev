package com.mnt.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "monthly_totals_data")
public class MonthlyTotalsData extends BaseDomain {

	private static final long serialVersionUID = 1L;
	
	@CsvBindByName(column = "MEMBERSHIP")
	@Column(name = "membership")
	private String membership;
	
	@CsvBindByName(column = "PCP_ID")
	@Column(name = "pcp_id")
	private String pcpId;
	
	@CsvBindByName(column = "PCP_NAME")
	@Column(name = "pcp_name")
	private String pcpName;
	
	@CsvBindByName(column = "PCP_LOCATION")
	@Column(name = "pcp_location")
	private String pcpLocation;
	
	@CsvBindByName(column = "RISK_SHARING")
	@Column(name = "risk_sharing")
	private String riskSharing;
	
	@CsvBindByName(column = "MONTH")
	@Column(name = "month")
	private String month;
	
	@CsvBindByName(column = "YEAR")
	@Column(name = "year")
	private String year;
	
	@CsvBindByName(column = "COUNTY")
	@Column(name = "county")
	private String county;
	
	@CsvBindByName(column = "PLAN_NAME")
	@Column(name = "plan_name")
	private String planName;
	
	@CsvBindByName(column = "IPA_ID")
	@Column(name = "ipa_id")
	private String ipaId;
	
	@CsvBindByName(column = "IPA_NAME")
	@Column(name = "ipa_name")
	private String ipaName;
	
	@CsvBindByName(column = "PREMIUM_MA")
	@Column(name = "premium_ma")
	private String premiumMa;
	
	@CsvBindByName(column = "PREMIUM_PARTD")
	@Column(name = "premium_partd")
	private String premiumPartd;
	
	@CsvBindByName(column = "TOTAL_PREMIUM")
	@Column(name = "total_premium")
	private String totalPremium;
	
	@CsvBindByName(column = "IPA_PREMIUM")
	@Column(name = "ipa_premium")
	private String ipaPremium;
	
	@CsvBindByName(column = "PCP_CAP")
	@Column(name = "pcp_cap")
	private String pcpCap;
	
	@CsvBindByName(column = "SPEC_COST")
	@Column(name = "spec_cost")
	private String specCost;
	
	@CsvBindByName(column = "PROF_CLAIMS")
	@Column(name = "prof_claims")
	private String profClaims;
	
	@CsvBindByName(column = "INST_CLAIMS")
	@Column(name = "inst_claims")
	private String instClaims;
	
	@CsvBindByName(column = "RX_CLAIMS")
	@Column(name = "rx_claims")
	private String rxClaims;
	
	@CsvBindByName(column = "IBNR_DOLLARS")
	@Column(name = "ibnr_dollars")
	private String ibntDollars;
	
	@CsvBindByName(column = "REINSURANCE")
	@Column(name = "reinsurance")
	private String reinsurance;
	
	@CsvBindByName(column = "ADJUSTMENTS")
	@Column(name = "adjustments")
	private String adjustments;
	
	@CsvBindByName(column = "TOTAL_EXPENSES")
	@Column(name = "total_expenses")
	private String totalExpenses;
	
	@CsvBindByName(column = "STOP_LOSS")
	@Column(name = "stop_loss")
	private String stopLoss;
	
	@CsvBindByName(column = "NET_PREMIUM")
	@Column(name = "net_premium")
	private String netPremium;
	
	@CsvBindByName(column = "ULTIMATE_NET_PREMIUM")
	@Column(name = "ultimate_net_premium")
	private String ultimateNetPremium;
	
	@CsvBindByName(column = "RISK_SCORE_PARTC")
	@Column(name = "risk_score_partc")
	private String riskScorePartc;
	
	@CsvBindByName(column = "RISK_SCORE_PARTD")
	@Column(name = "risk_score_partd")
	private String riskScorePartd;
	
	@CsvBindByName(column = "MFGR_RX_REBATES")
	@Column(name = "mfgr_rx_rebates")
	private String mfgrRxReabtes;
	
	@CsvBindByName(column = "Provider")
	@Column(name = "provider")
	private String provider;

	public String getMembership() {
		return membership;
	}

	public void setMembership(String membership) {
		this.membership = membership;
	}

	public String getPcpId() {
		return pcpId;
	}

	public void setPcpId(String pcpId) {
		this.pcpId = pcpId;
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

	public String getRiskSharing() {
		return riskSharing;
	}

	public void setRiskSharing(String riskSharing) {
		this.riskSharing = riskSharing;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getIpaId() {
		return ipaId;
	}

	public void setIpaId(String ipaId) {
		this.ipaId = ipaId;
	}

	public String getIpaName() {
		return ipaName;
	}

	public void setIpaName(String ipaName) {
		this.ipaName = ipaName;
	}

	public String getPremiumMa() {
		return premiumMa;
	}

	public void setPremiumMa(String premiumMa) {
		this.premiumMa = premiumMa;
	}

	public String getPremiumPartd() {
		return premiumPartd;
	}

	public void setPremiumPartd(String premiumPartd) {
		this.premiumPartd = premiumPartd;
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

	public String getIbntDollars() {
		return ibntDollars;
	}

	public void setIbntDollars(String ibntDollars) {
		this.ibntDollars = ibntDollars;
	}

	public String getReinsurance() {
		return reinsurance;
	}

	public void setReinsurance(String reinsurance) {
		this.reinsurance = reinsurance;
	}

	public String getAdjustments() {
		return adjustments;
	}

	public void setAdjustments(String adjustments) {
		this.adjustments = adjustments;
	}

	public String getTotalExpenses() {
		return totalExpenses;
	}

	public void setTotalExpenses(String totalExpenses) {
		this.totalExpenses = totalExpenses;
	}

	public String getStopLoss() {
		return stopLoss;
	}

	public void setStopLoss(String stopLoss) {
		this.stopLoss = stopLoss;
	}

	public String getNetPremium() {
		return netPremium;
	}

	public void setNetPremium(String netPremium) {
		this.netPremium = netPremium;
	}

	public String getUltimateNetPremium() {
		return ultimateNetPremium;
	}

	public void setUltimateNetPremium(String ultimateNetPremium) {
		this.ultimateNetPremium = ultimateNetPremium;
	}

	public String getRiskScorePartc() {
		return riskScorePartc;
	}

	public void setRiskScorePartc(String riskScorePartc) {
		this.riskScorePartc = riskScorePartc;
	}

	public String getRiskScorePartd() {
		return riskScorePartd;
	}

	public void setRiskScorePartd(String riskScorePartd) {
		this.riskScorePartd = riskScorePartd;
	}

	public String getMfgrRxReabtes() {
		return mfgrRxReabtes;
	}

	public void setMfgrRxReabtes(String mfgrRxReabtes) {
		this.mfgrRxReabtes = mfgrRxReabtes;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}
}
