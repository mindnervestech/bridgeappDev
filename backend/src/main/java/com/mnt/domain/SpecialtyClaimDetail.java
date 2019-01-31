package com.mnt.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "specialty_claim_detail")
public class SpecialtyClaimDetail extends BaseDomain{
	
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
	@CsvBindByName(column = "SUBSCRIBER_ID")
	@Column(name = "subscriber_id")
	private String subscriberId;
	@CsvBindByName(column = "MEMBER_FIRST_NAME")
	@Column(name = "member_first_name")
	private String memberFirstName;
	@CsvBindByName(column = "MEMBER_LAST_NAME")
	@Column(name = "member_last_name")
	private String memberLastName;
	@CsvBindByName(column = "GENDER")
	@Column(name = "gender")
	private String gender;
	@CsvBindByName(column = "PBP")
	@Column(name = "pbp")
	private String pbp;
	@CsvBindByName(column = "CLAIM_NO")
	@Column(name = "claim_no")
	private String claimNo;
	@CsvBindByName(column = "MEMBER_NO")
	@Column(name = "member_no")
	private String memberNo;
	@CsvBindByName(column = "SERVICE_CODE")
	@Column(name = "service_code")
	private String serviceCode;
	@CsvBindByName(column = "SERVICE_CODE_DESC")
	@Column(name = "service_code_desc")
	private String serviceCodeDesc;
	@CsvBindByName(column = "SERVICE_DATE")
	@Column(name = "service_date")
	private Date serviceDate;
	@CsvBindByName(column = "SERVICE_MONTH")
	@Column(name = "service_month")
	private Date serviceMonth;
	@CsvBindByName(column = "VENDOR_FIRST_NAME")
	@Column(name = "vendor_first_name")
	private String vendorFirstName;
	@CsvBindByName(column = "VENDOR_LAST_NAME")
	@Column(name = "vendor_last_name")
	private String vendorLastName;
	@CsvBindByName(column = "PAID_AMOUNT")
	@Column(name = "paid_amount")
	private Double paidAmount;
	@CsvBindByName(column = "PAID_DATE")
	@Column(name = "paid_date")
	private Date paidDate;
	@CsvBindByName(column = "PAID_MONTH")
	@Column(name = "paid_month")
	private Date paidMonth;
	@CsvBindByName(column = "UNITS")
	@Column(name = "units")
	private String units;
	@CsvBindByName(column = "CLAIM_TYPE")
	@Column(name = "claim_type")
	private String claimType;
	@CsvBindByName(column = "FORM_TYPE")
	@Column(name = "form_type")
	private String formType;
	@CsvBindByName(column = "ICD9_1")
	@Column(name = "icd9_1")
	private String icd9_1;
	@CsvBindByName(column = "ICD9_2")
	@Column(name = "icd9_2")
	private String icd9_2;
	@CsvBindByName(column = "ICD9_3")
	@Column(name = "icd9_3")
	private String icd9_3;
	@CsvBindByName(column = "MEDICARE_ID")
	@Column(name = "medicare_id")
	private String medicareId;
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
	public String getSubscriberId() {
		return subscriberId;
	}
	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}
	public String getMemberFirstName() {
		return memberFirstName;
	}
	public void setMemberFirstName(String memberFirstName) {
		this.memberFirstName = memberFirstName;
	}
	public String getMemberLastName() {
		return memberLastName;
	}
	public void setMemberLastName(String memberLastName) {
		this.memberLastName = memberLastName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPbp() {
		return pbp;
	}
	public void setPbp(String pbp) {
		this.pbp = pbp;
	}
	public String getClaimNo() {
		return claimNo;
	}
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getServiceCodeDesc() {
		return serviceCodeDesc;
	}
	public void setServiceCodeDesc(String serviceCodeDesc) {
		this.serviceCodeDesc = serviceCodeDesc;
	}
	public Date getServiceDate() {
		return serviceDate;
	}
	public void setServiceDate(Date serviceDate) {
		this.serviceDate = serviceDate;
	}
	public String getVendorFirstName() {
		return vendorFirstName;
	}
	public void setVendorFirstName(String vendorFirstName) {
		this.vendorFirstName = vendorFirstName;
	}
	public String getVendorLastName() {
		return vendorLastName;
	}
	public void setVendorLastName(String vendorLastName) {
		this.vendorLastName = vendorLastName;
	}
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}
	public String getClaimType() {
		return claimType;
	}
	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}
	public String getFormType() {
		return formType;
	}
	public void setFormType(String formType) {
		this.formType = formType;
	}
	public String getIcd9_1() {
		return icd9_1;
	}
	public void setIcd9_1(String icd9_1) {
		this.icd9_1 = icd9_1;
	}
	public String getIcd9_2() {
		return icd9_2;
	}
	public void setIcd9_2(String icd9_2) {
		this.icd9_2 = icd9_2;
	}
	public String getIcd9_3() {
		return icd9_3;
	}
	public void setIcd9_3(String icd9_3) {
		this.icd9_3 = icd9_3;
	}
	public String getMedicareId() {
		return medicareId;
	}
	public void setMedicareId(String medicareId) {
		this.medicareId = medicareId;
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
	public Date getServiceMonth() {
		return serviceMonth;
	}
	public void setServiceMonth(Date serviceMonth) {
		this.serviceMonth = serviceMonth;
	}
	public Date getPaidDate() {
		return paidDate;
	}
	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}
	public Date getPaidMonth() {
		return paidMonth;
	}
	public void setPaidMonth(Date paidMonth) {
		this.paidMonth = paidMonth;
	}
	public Double getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
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
