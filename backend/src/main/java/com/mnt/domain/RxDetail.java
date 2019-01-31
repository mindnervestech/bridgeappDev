package com.mnt.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "rx_detail")
public class RxDetail extends BaseDomain{
	
	private static final long serialVersionUID = 1L;
	
	@CsvBindByName(column = "IPA_ID")
	@Column(name = "ipa_id")
	private String ipaId;
	@CsvBindByName(column = "IPA_NAME")
	@Column(name = "ipa_name")
	private String ipaName;
	@CsvBindByName(column = "PCP_ID")
	@Column(name = "pcp_id")
	private String pcpId;
	@CsvBindByName(column = "PCP_NAME")
	@Column(name = "pcp_name")
	private String pcpName;
	@CsvBindByName(column = "PRESCRIBER_ID")
	@Column(name = "prescriber_id")
	private String prescriberId;
	@CsvBindByName(column = "PRESCRIBER_NAME")
	@Column(name = "prescriber_name")
	private String prescriberName;
	@CsvBindByName(column = "SUBSCRIBER_ID")
	@Column(name = "subscriber_id")
	private String subscriberId;
	@CsvBindByName(column = "LAST_NAME")
	@Column(name = "last_name")
	private String lastName;
	@CsvBindByName(column = "FIRST_NAME")
	@Column(name = "first_name")
	private String firstName;
	@CsvBindByName(column = "BIRTH_DATE")
	@Column(name = "birth_date")
	private Date birthDate;
	@CsvBindByName(column = "GENDER")
	@Column(name = "gender")
	private String gender;
	@CsvBindByName(column = "MEDICARE_ID")
	@Column(name = "medicare_id")
	private String medicareId;
	@CsvBindByName(column = "MEDICAID_ID")
	@Column(name = "medicaid_id")
	private String medicaidId;
	@CsvBindByName(column = "COUNTY")
	@Column(name = "county")
	private String county;
	@CsvBindByName(column = "PBP")
	@Column(name = "pbp")
	private String pbp;
	@CsvBindByName(column = "PHARMACY_NUMBER")
	@Column(name = "pharmacy_number")
	private String pharmacyNumber;
	@CsvBindByName(column = "PRESCRIPTION_NUMBER")
	@Column(name = "prescription_number")
	private String prescriptionNumber;
	@CsvBindByName(column = "DATE_FILLED")
	@Column(name = "date_filled")
	private Date dateFilled;
	@CsvBindByName(column = "MONTH_FILLED")
	@Column(name = "month_filled")
	private Date monthFilled;
	@CsvBindByName(column = "NDC_NUMBER")
	@Column(name = "ndc_number")
	private String ndcNumber;
	@CsvBindByName(column = "DRUG_NAME")
	@Column(name = "drug_name")
	private String drugName;
	@CsvBindByName(column = "METRIC_QUANTITY")
	@Column(name = "metric_quantity")
	private String metricQuantity;
	@CsvBindByName(column = "NEW_OR_REFILL_CODE")
	@Column(name = "new_or_refill_code")
	private String newOrRefillCode;
	@CsvBindByName(column = "DAYS_SUPPLY")
	@Column(name = "days_supply")
	private String daysSupply;
	@CsvBindByName(column = "INGREDIENT_COST")
	@Column(name = "ingredient_cost")
	private Double ingredientCost;
	@CsvBindByName(column = "DISPENSING_FEE")
	@Column(name = "dispensing_fee")
	private String dispensingFee;
	@CsvBindByName(column = "CO_PAY_AMOUNT")
	@Column(name = "co_pay_amount")
	private Double coPayAmount;
	@CsvBindByName(column = "SALES_TAX")
	@Column(name = "sales_tax")
	private String salesTax;
	@CsvBindByName(column = "PAID_AMOUNT")
	@Column(name = "paid_amount")
	private Double paidAmount;
	@CsvBindByName(column = "REP_GAP_DSCNT")
	@Column(name = "rep_gap_dscnt")
	private String repGapDscnt;
	@CsvBindByName(column = "GENERIC_CODE")
	@Column(name = "generic_code")
	private String genericCode;
	@CsvBindByName(column = "PHARMACY_NAME")
	@Column(name = "pharmacy_name")
	private String pharmacyName;
	@CsvBindByName(column = "PHARMACY_ZIP")
	@Column(name = "pharmacy_zip")
	private String pharmacyZip;
	@CsvBindByName(column = "TIER")
	@Column(name = "tier")
	private String tier;
	@CsvBindByName(column = "BENEFIT_TYPE_FLAG")
	@Column(name = "benefit_type_flag")
	private String benefitTypeFlag;
	@CsvBindByName(column = "SUBROGATION_AMT")
	@Column(name = "subrogation_amt")
	private Double subrogationAmt;
	@CsvBindByName(column = "LICS_PAID")
	@Column(name = "lics_paid")
	private String licsPaid;
	@CsvBindByName(column = "GDCA")
	@Column(name = "gdca")
	private String gdca;
	@CsvBindByName(column = "GPI_NUM")
	@Column(name = "gpi_num")
	private String gpiNum;
	@CsvBindByName(column = "AHFS_ID")
	@Column(name = "ahfs_id")
	private String ahfsId;
	@CsvBindByName(column = "PRESC_DEA_ID")
	@Column(name = "presc_dea_id")
	private String prescDeaId;
	@CsvBindByName(column = "PCP_LOCATION_CODE")
	@Column(name = "pcp_location_code")
	private String pcpLocationCode;
	@CsvBindByName(column = "SUPPL_EXP")
	@Column(name = "suppl_exp")
	private String supplExp;
	@CsvBindByName(column = "CLAIM_ID")
	@Column(name = "claim_id")
	private String claimId;
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
	public String getPrescriberId() {
		return prescriberId;
	}
	public void setPrescriberId(String prescriberId) {
		this.prescriberId = prescriberId;
	}
	public String getPrescriberName() {
		return prescriberName;
	}
	public void setPrescriberName(String prescriberName) {
		this.prescriberName = prescriberName;
	}
	public String getSubscriberId() {
		return subscriberId;
	}
	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
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
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getPbp() {
		return pbp;
	}
	public void setPbp(String pbp) {
		this.pbp = pbp;
	}
	public String getPharmacyNumber() {
		return pharmacyNumber;
	}
	public void setPharmacyNumber(String pharmacyNumber) {
		this.pharmacyNumber = pharmacyNumber;
	}
	public String getPrescriptionNumber() {
		return prescriptionNumber;
	}
	public void setPrescriptionNumber(String prescriptionNumber) {
		this.prescriptionNumber = prescriptionNumber;
	}
	public String getNdcNumber() {
		return ndcNumber;
	}
	public void setNdcNumber(String ndcNumber) {
		this.ndcNumber = ndcNumber;
	}
	public String getDrugName() {
		return drugName;
	}
	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}
	public String getMetricQuantity() {
		return metricQuantity;
	}
	public void setMetricQuantity(String metricQuantity) {
		this.metricQuantity = metricQuantity;
	}
	public String getNewOrRefillCode() {
		return newOrRefillCode;
	}
	public void setNewOrRefillCode(String newOrRefillCode) {
		this.newOrRefillCode = newOrRefillCode;
	}
	public String getDaysSupply() {
		return daysSupply;
	}
	public void setDaysSupply(String daysSupply) {
		this.daysSupply = daysSupply;
	}
	public String getDispensingFee() {
		return dispensingFee;
	}
	public void setDispensingFee(String dispensingFee) {
		this.dispensingFee = dispensingFee;
	}
	public String getSalesTax() {
		return salesTax;
	}
	public void setSalesTax(String salesTax) {
		this.salesTax = salesTax;
	}
	public String getRepGapDscnt() {
		return repGapDscnt;
	}
	public void setRepGapDscnt(String repGapDscnt) {
		this.repGapDscnt = repGapDscnt;
	}
	public String getGenericCode() {
		return genericCode;
	}
	public void setGenericCode(String genericCode) {
		this.genericCode = genericCode;
	}
	public String getPharmacyName() {
		return pharmacyName;
	}
	public void setPharmacyName(String pharmacyName) {
		this.pharmacyName = pharmacyName;
	}
	public String getPharmacyZip() {
		return pharmacyZip;
	}
	public void setPharmacyZip(String pharmacyZip) {
		this.pharmacyZip = pharmacyZip;
	}
	public String getTier() {
		return tier;
	}
	public void setTier(String tier) {
		this.tier = tier;
	}
	public String getBenefitTypeFlag() {
		return benefitTypeFlag;
	}
	public void setBenefitTypeFlag(String benefitTypeFlag) {
		this.benefitTypeFlag = benefitTypeFlag;
	}
	public String getLicsPaid() {
		return licsPaid;
	}
	public void setLicsPaid(String licsPaid) {
		this.licsPaid = licsPaid;
	}
	public String getGdca() {
		return gdca;
	}
	public void setGdca(String gdca) {
		this.gdca = gdca;
	}
	public String getGpiNum() {
		return gpiNum;
	}
	public void setGpiNum(String gpiNum) {
		this.gpiNum = gpiNum;
	}
	public String getAhfsId() {
		return ahfsId;
	}
	public void setAhfsId(String ahfsId) {
		this.ahfsId = ahfsId;
	}
	public String getPrescDeaId() {
		return prescDeaId;
	}
	public void setPrescDeaId(String prescDeaId) {
		this.prescDeaId = prescDeaId;
	}
	public String getPcpLocationCode() {
		return pcpLocationCode;
	}
	public void setPcpLocationCode(String pcpLocationCode) {
		this.pcpLocationCode = pcpLocationCode;
	}
	public String getSupplExp() {
		return supplExp;
	}
	public void setSupplExp(String supplExp) {
		this.supplExp = supplExp;
	}
	public String getClaimId() {
		return claimId;
	}
	public void setClaimId(String claimId) {
		this.claimId = claimId;
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
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public Date getDateFilled() {
		return dateFilled;
	}
	public void setDateFilled(Date dateFilled) {
		this.dateFilled = dateFilled;
	}
	public Date getMonthFilled() {
		return monthFilled;
	}
	public void setMonthFilled(Date monthFilled) {
		this.monthFilled = monthFilled;
	}
	public Double getIngredientCost() {
		return ingredientCost;
	}
	public void setIngredientCost(Double ingredientCost) {
		this.ingredientCost = ingredientCost;
	}
	public Double getCoPayAmount() {
		return coPayAmount;
	}
	public void setCoPayAmount(Double coPayAmount) {
		this.coPayAmount = coPayAmount;
	}
	public Double getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}
	public Double getSubrogationAmt() {
		return subrogationAmt;
	}
	public void setSubrogationAmt(Double subrogationAmt) {
		this.subrogationAmt = subrogationAmt;
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
