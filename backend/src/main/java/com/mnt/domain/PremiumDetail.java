package com.mnt.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "premium_detail")
public class PremiumDetail extends BaseDomain{
	
	private static final long serialVersionUID = 1L;
	@CsvBindByName(column = "IPA_ID")
	@Column(name = "ipa_id")
	private String ipaId;
	@CsvBindByName(column = "IPA_NAME")
	@Column(name = "ipa_name")
	private String ipaName;
	@CsvBindByName(column = "ELIGIBLE_MONTH")
	@Column(name = "eligible_month")
	private Date eligibleMonth;
	@CsvBindByName(column = "SUBSCRIBER_ID")
	@Column(name = "subscriber_id")
	private String subscriberId;
	@CsvBindByName(column = "MEDICARE_ID")
	@Column(name = "medicare_id")
	private String medicareId;
	@CsvBindByName(column = "MEDICAID_ID")
	@Column(name = "medicaid_id")
	private String medicaidId;
	@CsvBindByName(column = "MEMBER_NAME")
	@Column(name = "member_name")
	private String memberName;
	@CsvBindByName(column = "PCP_ID")
	@Column(name = "pcp_id")
	private String pcpId;
	@CsvBindByName(column = "PCP_NAME")
	@Column(name = "pcp_name")
	private String pcpName;
	@CsvBindByName(column = "PAYMENT_MONTH")
	@Column(name = "payment_month")
	private Date paymentMonth;
	@CsvBindByName(column = "HICN")
	@Column(name = "hicn")
	private String hicn;
	@CsvBindByName(column = "BIRTH_DATE")
	@Column(name = "birth_date")
	private Date birthDate;
	@CsvBindByName(column = "AGE_GROUP")
	@Column(name = "age_group")
	private String ageGroup;
	@CsvBindByName(column = "RA_FACTOR_C")
	@Column(name = "ra_factor_c")
	private String raFactorC;
	@CsvBindByName(column = "RA_FACTOR_D")
	@Column(name = "ra_factor_d")
	private String raFactorD;
	@CsvBindByName(column = "ADJUSTMENT_REASON")
	@Column(name = "adjustment_reason")
	private String adjustmentReason;
	@CsvBindByName(column = "MONTHLY_TOTAL_MA")
	@Column(name = "monthly_total_ma")
	private Double monthlyTotalMa;
	@CsvBindByName(column = "MONTHLY_TOTAL_PARTD")
	@Column(name = "monthly_total_partd")
	private Double monthlyTotalPartd;
	@CsvBindByName(column = "MONTHLY_TOTAL")
	@Column(name = "monthly_total")
	private Double monthlyTotal;
	@CsvBindByName(column = "PAYMENT_DATE")
	@Column(name = "payment_date")
	private String paymentDate;
	@CsvBindByName(column = "REINS_SUBSIDY_PARTD")
	@Column(name = "reins_subsidy_partd")
	private String reinsSubsidyPartd;
	@CsvBindByName(column = "LICS_SUBSIDY_PARTD")
	@Column(name = "lics_subsidy_partd")
	private String licsSubsidyPartd;
	@CsvBindByName(column = "COV_GAP_SUBSIDY_PARTD")
	@Column(name = "cov_gap_subsidy_partd")
	private String covGapSubsidyPartd;
	@CsvBindByName(column = "PCP_LOCATION_CODE")
	@Column(name = "pcp_location_code")
	private String pcpLocationCode;
	@CsvBindByName(column = "PD_SUPPL_MA")
	@Column(name = "pd_suppl_ma")
	private String pdSupplMa;
	@Column(name = "year")
	private String year;
	@Column(name = "month")
	private String month;
	@Column(name = "provider")
	private String provider;
	
	
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
	public String getSubscriberId() {
		return subscriberId;
	}
	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}
	public String getMedicareId() {
		return medicareId;
	}
	public void setMedicareId(String medicareId) {
		this.medicareId = medicareId;
	}
	public String getMedicaidId() {
		return medicaidId;
	}
	public void setMedicaidId(String medicaidId) {
		this.medicaidId = medicaidId;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
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
	public String getHicn() {
		return hicn;
	}
	public void setHicn(String hicn) {
		this.hicn = hicn;
	}
	public String getAgeGroup() {
		return ageGroup;
	}
	public void setAgeGroup(String ageGroup) {
		this.ageGroup = ageGroup;
	}
	public String getRaFactorC() {
		return raFactorC;
	}
	public void setRaFactorC(String raFactorC) {
		this.raFactorC = raFactorC;
	}
	public String getRaFactorD() {
		return raFactorD;
	}
	public void setRaFactorD(String raFactorD) {
		this.raFactorD = raFactorD;
	}
	public String getAdjustmentReason() {
		return adjustmentReason;
	}
	public void setAdjustmentReason(String adjustmentReason) {
		this.adjustmentReason = adjustmentReason;
	}
	public String getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getReinsSubsidyPartd() {
		return reinsSubsidyPartd;
	}
	public void setReinsSubsidyPartd(String reinsSubsidyPartd) {
		this.reinsSubsidyPartd = reinsSubsidyPartd;
	}
	public String getLicsSubsidyPartd() {
		return licsSubsidyPartd;
	}
	public void setLicsSubsidyPartd(String licsSubsidyPartd) {
		this.licsSubsidyPartd = licsSubsidyPartd;
	}
	public String getCovGapSubsidyPartd() {
		return covGapSubsidyPartd;
	}
	public void setCovGapSubsidyPartd(String covGapSubsidyPartd) {
		this.covGapSubsidyPartd = covGapSubsidyPartd;
	}
	public String getPcpLocationCode() {
		return pcpLocationCode;
	}
	public void setPcpLocationCode(String pcpLocationCode) {
		this.pcpLocationCode = pcpLocationCode;
	}
	public String getPdSupplMa() {
		return pdSupplMa;
	}
	public void setPdSupplMa(String pdSupplMa) {
		this.pdSupplMa = pdSupplMa;
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
	public Date getPaymentMonth() {
		return paymentMonth;
	}
	public void setPaymentMonth(Date paymentMonth) {
		this.paymentMonth = paymentMonth;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public Double getMonthlyTotalMa() {
		return monthlyTotalMa;
	}
	public void setMonthlyTotalMa(Double monthlyTotalMa) {
		this.monthlyTotalMa = monthlyTotalMa;
	}
	public Double getMonthlyTotalPartd() {
		return monthlyTotalPartd;
	}
	public void setMonthlyTotalPartd(Double monthlyTotalPartd) {
		this.monthlyTotalPartd = monthlyTotalPartd;
	}
	public Double getMonthlyTotal() {
		return monthlyTotal;
	}
	public void setMonthlyTotal(Double monthlyTotal) {
		this.monthlyTotal = monthlyTotal;
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
