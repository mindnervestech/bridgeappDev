package com.mnt.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "stoploss_detail")
public class StoplossDetail extends BaseDomain {
	
	private static final long serialVersionUID = 1L;
	@CsvBindByName(column = "IPA_ID")
	@Column(name = "ipa_id")
	private String ipaId;
	@CsvBindByName(column = "SUBSCRIBER_ID")
	@Column(name = "subscriber_id")
	private String subscriberId;
	@CsvBindByName(column = "ELIGIBLE_MONTH")
	@Column(name = "eligible_month")
	private Date eligibleMonth;
	
	@CsvBindByName(column = "MONTHLY_PROF_CLAIMS")
	@Column(name = "monthly_prof_claims")
	private String monthlyProfClaims;
	
	@CsvBindByName(column = "MBR_YEARLY_PROF_CLAIMS")
	@Column(name = "mbr_yearly_prof_claims")
	private String mbrYearlyProfClaims;
	
	@CsvBindByName(column = "MONTHLY_INST_CLAIMS")
	@Column(name = "monthly_inst_claims")
	private String monthlyInstClaims;
	
	@CsvBindByName(column = "MBR_YEARLY_INST_CLAIMS")
	@Column(name = "mbr_yearly_inst_claims")
	private String mbrYearlyInstClaims;
	
	@CsvBindByName(column = "MONTHLY_PROF_INST_CLAIMS")
	@Column(name = "monthly_prof_inst_claims")
	private String monthlyProfInstClaims;
	
	@CsvBindByName(column = "MBR_YEARLY_PROF_INST_CLAIMS")
	@Column(name = "mbr_yearly_prof_inst_claims")
	private String mbrYearlyProfInstClaims;
	
	@CsvBindByName(column = "PROF_INST_CLAIMS_ELIG_AMT")
	@Column(name = "prof_inst_claims_elig_amt")
	private Double profInstClaimsEligAmt;
	
	@CsvBindByName(column = "PROF_INST_CLAIMS_RECOVERIES")
	@Column(name = "prof_inst_claims_recoveries")
	private String profInstClaimsRecoveries;
	
	@CsvBindByName(column = "TOTAL_IPA_RECOVERIES")
	@Column(name = "total_ipa_recoveries")
	private String totalIpaRecoveries;
	
	@CsvBindByName(column = "PCP_ID")
	@Column(name = "pcp_id")
	private String pcpId;
	
	@CsvBindByName(column = "PCP_NAME")
	@Column(name = "pcp_name")
	private String pcpName;
	
	@CsvBindByName(column = "PCP_LOCATION")
	@Column(name = "pcp_location")
	private String pcpLocation;
	
	@Column(name = "year")
	private String year;
	
	@Column(name = "month")
	private String month;
	
	@Column(name = "provider")
	private String provider;
	
	@Column(name = "prof_claims_elig_amt")
	private Double profClaimsEligAmt;
	
	@Column(name = "prof_claims_recoveries")
	private String profClaimsRecoveries;
	
	@Column(name = "inst_claims_elig_amt")
	private Double instClaimsEligAmt;
	
	@Column(name = "inst_claims_recoveries")
	private String instClaimsRecoveries;
	
	public String getIpaId() {
		return ipaId;
	}
	public void setIpaId(String ipaId) {
		this.ipaId = ipaId;
	}
	public String getSubscriberId() {
		return subscriberId;
	}
	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}
	public String getMonthlyProfClaims() {
		return monthlyProfClaims;
	}
	public void setMonthlyProfClaims(String monthlyProfClaims) {
		this.monthlyProfClaims = monthlyProfClaims;
	}
	public String getMbrYearlyProfClaims() {
		return mbrYearlyProfClaims;
	}
	public void setMbrYearlyProfClaims(String mbrYearlyProfClaims) {
		this.mbrYearlyProfClaims = mbrYearlyProfClaims;
	}
	public String getMonthlyInstClaims() {
		return monthlyInstClaims;
	}
	public void setMonthlyInstClaims(String monthlyInstClaims) {
		this.monthlyInstClaims = monthlyInstClaims;
	}
	public String getMbrYearlyInstClaims() {
		return mbrYearlyInstClaims;
	}
	public void setMbrYearlyInstClaims(String mbrYearlyInstClaims) {
		this.mbrYearlyInstClaims = mbrYearlyInstClaims;
	}
	public String getMonthlyProfInstClaims() {
		return monthlyProfInstClaims;
	}
	public void setMonthlyProfInstClaims(String monthlyProfInstClaims) {
		this.monthlyProfInstClaims = monthlyProfInstClaims;
	}
	public String getMbrYearlyProfInstClaims() {
		return mbrYearlyProfInstClaims;
	}
	public void setMbrYearlyProfInstClaims(String mbrYearlyProfInstClaims) {
		this.mbrYearlyProfInstClaims = mbrYearlyProfInstClaims;
	}
	public String getProfInstClaimsRecoveries() {
		return profInstClaimsRecoveries;
	}
	public void setProfInstClaimsRecoveries(String profInstClaimsRecoveries) {
		this.profInstClaimsRecoveries = profInstClaimsRecoveries;
	}
	public String getTotalIpaRecoveries() {
		return totalIpaRecoveries;
	}
	public void setTotalIpaRecoveries(String totalIpaRecoveries) {
		this.totalIpaRecoveries = totalIpaRecoveries;
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
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public Date getEligibleMonth() {
		return eligibleMonth;
	}
	public void setEligibleMonth(Date eligibleMonth) {
		this.eligibleMonth = eligibleMonth;
	}
	public String getProfClaimsRecoveries() {
		return profClaimsRecoveries;
	}
	public void setProfClaimsRecoveries(String profClaimsRecoveries) {
		this.profClaimsRecoveries = profClaimsRecoveries;
	}
	public String getInstClaimsRecoveries() {
		return instClaimsRecoveries;
	}
	public void setInstClaimsRecoveries(String instClaimsRecoveries) {
		this.instClaimsRecoveries = instClaimsRecoveries;
	}
	public Double getProfInstClaimsEligAmt() {
		return profInstClaimsEligAmt;
	}
	public void setProfInstClaimsEligAmt(Double profInstClaimsEligAmt) {
		this.profInstClaimsEligAmt = profInstClaimsEligAmt;
	}
	public Double getProfClaimsEligAmt() {
		return profClaimsEligAmt;
	}
	public void setProfClaimsEligAmt(Double profClaimsEligAmt) {
		this.profClaimsEligAmt = profClaimsEligAmt;
	}
	public Double getInstClaimsEligAmt() {
		return instClaimsEligAmt;
	}
	public void setInstClaimsEligAmt(Double instClaimsEligAmt) {
		this.instClaimsEligAmt = instClaimsEligAmt;
	}
	@Override
	public String toString()
	{
		// using Apache commons ToStringBuilder to create string representation of the object
		// changing string from object@number[field value pairs]
		// to [field value pairs]
	    String s = ToStringBuilder.reflectionToString(this);
	    return s.substring(s.indexOf('['));
	}

}
