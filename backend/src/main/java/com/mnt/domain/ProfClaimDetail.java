package com.mnt.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "prof_claim_detail")
public class ProfClaimDetail extends BaseDomain{
	
	private static final long serialVersionUID = 1L;
	@CsvBindByName(column = "IPA_ID")
	@Column(name = "ipa_id")
	private String ipaId;
	@CsvBindByName(column = "IPA_NAME")
	@Column(name = "ipa_name")
	private String ipaName;
	@CsvBindByName(column = "SUBSCRIBER_ID")
	@Column(name = "subscriber_id")
	private String subscriberId;
	@CsvBindByName(column = "MEMBER_NAME")
	@Column(name = "member_name")
	private String memberName;
	@CsvBindByName(column = "BIRTH_DATE")
	@Column(name = "birth_date")
	private Date birthDate;
	@CsvBindByName(column = "GENDER")
	@Column(name = "gender")
	private String gender;
	@CsvBindByName(column = "MEDICAID_ID")
	@Column(name = "medicaid_id")
	private String medicaidId;
	@CsvBindByName(column = "MEDICARE_ID")
	@Column(name = "medicare_id")
	private String medicareId;
	@CsvBindByName(column = "COUNTY")
	@Column(name = "county")
	private String county;
	@CsvBindByName(column = "PBP")
	@Column(name = "pbp")
	private String pbp;
	@CsvBindByName(column = "PLAN_NAME")
	@Column(name = "plan_name")
	private String planName;
	@CsvBindByName(column = "PCP_ID")
	@Column(name = "pcp_id")
	private String pcpId;
	@CsvBindByName(column = "PCP_NAME")
	@Column(name = "pcp_name")
	private String pcpName;
	@CsvBindByName(column = "CLAIM_ID")
	@Column(name = "claim_id")
	private String claimId;
	@CsvBindByName(column = "LINE_NUMBER")
	@Column(name = "line_number")
	private String lineNumber;
	@CsvBindByName(column = "FORM_TYPE")
	@Column(name = "form_type")
	private String formType;
	@CsvBindByName(column = "CLAIM_TYPE")
	@Column(name = "claim_type")
	private String claimType;
	@CsvBindByName(column = "PRACTITIONER_ID")
	@Column(name = "practitioner_id")
	private String practitionerId;
	@CsvBindByName(column = "PRACTITIONER_NAME")
	@Column(name = "practitioner_name")
	private String practitionerName;
	@CsvBindByName(column = "PRACTITIONER_SPEC")
	@Column(name = "practitioner_spec")
	private String practitionerSpec;
	@CsvBindByName(column = "CLINIC_FACILITY_ID")
	@Column(name = "clinic_facility_id")
	private String clinicFacilityId;
	@CsvBindByName(column = "CLINIC_FACILITY_NAME")
	@Column(name = "clinic_facility_name")
	private String clinicFacilityName;
	@CsvBindByName(column = "CLINIC_FACILITY_TYPE")
	@Column(name = "clinic_facility_type")
	private String clinicFacilityType;
	@CsvBindByName(column = "FIRST_SERVICE_DATE")
	@Column(name = "first_service_date")
	private Date firstServiceDate;
	@CsvBindByName(column = "SERVICE_MONTH")
	@Column(name = "service_month")
	private Date serviceMonth;
	@CsvBindByName(column = "PRINCIPAL_DIAGNOSIS")
	@Column(name = "principal_diagnosis")
	private String principalDiagnosis;
	@CsvBindByName(column = "PRINCIPAL_DIAGN_DESC")
	@Column(name = "principal_diagn_desc")
	private String principalDiagnDesc;
	@CsvBindByName(column = "DIAGNOSIS_CODE_LIST")
	@Column(name = "diagnosis_code_list")
	private String diagnosisCodeList;
	@CsvBindByName(column = "PROVIDER_ID")
	@Column(name = "provider_id")
	private String providerId;
	@CsvBindByName(column = "PROVIDER_NAME")
	@Column(name = "provider_name")
	private String providerName;
	@CsvBindByName(column = "IN_NETWORK_FLAG")
	@Column(name = "in_network_flag")
	private String inNetworkFlag;
	@CsvBindByName(column = "COINSURANCE")
	@Column(name = "coinsurance")
	private String coinsurance;
	@CsvBindByName(column = "COPAY_AMOUNT")
	@Column(name = "copay_amount")
	private Double copayAmount;
	@CsvBindByName(column = "ALLOWED_AMOUNT")
	@Column(name = "allowed_amount")
	private Double allowedAmount;
	@CsvBindByName(column = "PAID_AMOUNT")
	@Column(name = "paid_amount")
	private Double paidAmount;
	@CsvBindByName(column = "TOTAL_CHARGES")
	@Column(name = "total_charges")
	private Double totalCharges;
	@CsvBindByName(column = "SERVICE_CODE")
	@Column(name = "service_code")
	private String serviceCode;
	@CsvBindByName(column = "SERVICE_CODE_DESC")
	@Column(name = "service_code_desc")
	private String serviceCodeDesc;
	@CsvBindByName(column = "SERVICE_MODIFIER_LIST")
	@Column(name = "service_modifier_list")
	private String serviceModifierList;
	@CsvBindByName(column = "LOCATION_CODE")
	@Column(name = "location_code")
	private String locationCode;
	@CsvBindByName(column = "LOCATION_CODE_DESC")
	@Column(name = "location_code_desc")
	private String locationCodeDesc;
	@CsvBindByName(column = "UNIT_TYPE")
	@Column(name = "unit_type")
	private String unitType;
	@CsvBindByName(column = "UNITS")
	@Column(name = "units")
	private String units;
	@CsvBindByName(column = "CLAIM_SPECIALTY")
	@Column(name = "claim_specialty")
	private String claimSpecialty;
	@CsvBindByName(column = "BETOS_GROUP")
	@Column(name = "betos_group")
	private String betosGroup;
	@CsvBindByName(column = "BETOS_CAT")
	@Column(name = "betos_cat")
	private String betosCat;
	@CsvBindByName(column = "BETOS_DESC")
	@Column(name = "betos_desc")
	private String betosDesc;
	@CsvBindByName(column = "AUTHORIZATION_NUMBER")
	@Column(name = "authorization_number")
	private String authorizationNumber;
	@CsvBindByName(column = "CHECK_DATE")
	@Column(name = "check_date")
	private Date checkDate;
	@CsvBindByName(column = "SERVICE_FROM_DATE")
	@Column(name = "service_from_date")
	private Date serviceFromDate;
	@CsvBindByName(column = "SERVICE_TO_DATE")
	@Column(name = "service_to_date")
	private Date serviceToDate;
	@CsvBindByName(column = "PRACTITIONER_TAX_ID")
	@Column(name = "practitioner_tax_id")
	private String practitionerTaxId;
	@CsvBindByName(column = "PRACTITIONER_NPI")
	@Column(name = "practitioner_npi")
	private String practitionerNpi;
	@CsvBindByName(column = "PROVIDER_TAX_ID")
	@Column(name = "provider_tax_id")
	private String providerTaxId;
	@CsvBindByName(column = "PROVIDER_NPI")
	@Column(name = "provider_npi")
	private String providerNpi;
	@CsvBindByName(column = "SUBROGATION_AMT")
	@Column(name = "subrogation_amt")
	private Double subrogationAmt;
	@CsvBindByName(column = "REF_PHY_ID")
	@Column(name = "ref_phy_id")
	private String refPhyId;
	@CsvBindByName(column = "REF_PHY_NPI")
	@Column(name = "ref_phy_npi")
	private String refPhyNpi;
	@CsvBindByName(column = "REF_PHY_NAME")
	@Column(name = "ref_phy_name")
	private String refPhyName;
	@CsvBindByName(column = "REF_PHY_SPECIALTY")
	@Column(name = "ref_phy_specialty")
	private String refPhySpecialty;
	@CsvBindByName(column = "LINE_AUTH_NUMBER")
	@Column(name = "line_auth_number")
	private String lineAuthNumber;
	@CsvBindByName(column = "ICD_VERSION_IND")
	@Column(name = "icd_version_ind")
	private String icdVersionInd;
	@CsvBindByName(column = "PCP_LOCATION_CODE")
	@Column(name = "pcp_location_code")
	private String pcpLocationCode;
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
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMedicaidId() {
		return medicaidId;
	}
	public void setMedicaidId(String medicaidId) {
		this.medicaidId = medicaidId;
	}
	public String getMedicareId() {
		return medicareId;
	}
	public void setMedicareId(String medicareId) {
		this.medicareId = medicareId;
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
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
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
	public String getClaimId() {
		return claimId;
	}
	public void setClaimId(String claimId) {
		this.claimId = claimId;
	}
	public String getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}
	public String getFormType() {
		return formType;
	}
	public void setFormType(String formType) {
		this.formType = formType;
	}
	public String getClaimType() {
		return claimType;
	}
	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}
	public String getPractitionerId() {
		return practitionerId;
	}
	public void setPractitionerId(String practitionerId) {
		this.practitionerId = practitionerId;
	}
	public String getPractitionerName() {
		return practitionerName;
	}
	public void setPractitionerName(String practitionerName) {
		this.practitionerName = practitionerName;
	}
	public String getPractitionerSpec() {
		return practitionerSpec;
	}
	public void setPractitionerSpec(String practitionerSpec) {
		this.practitionerSpec = practitionerSpec;
	}
	public String getClinicFacilityId() {
		return clinicFacilityId;
	}
	public void setClinicFacilityId(String clinicFacilityId) {
		this.clinicFacilityId = clinicFacilityId;
	}
	public String getClinicFacilityName() {
		return clinicFacilityName;
	}
	public void setClinicFacilityName(String clinicFacilityName) {
		this.clinicFacilityName = clinicFacilityName;
	}
	public String getClinicFacilityType() {
		return clinicFacilityType;
	}
	public void setClinicFacilityType(String clinicFacilityType) {
		this.clinicFacilityType = clinicFacilityType;
	}
	public String getPrincipalDiagnosis() {
		return principalDiagnosis;
	}
	public void setPrincipalDiagnosis(String principalDiagnosis) {
		this.principalDiagnosis = principalDiagnosis;
	}
	public String getPrincipalDiagnDesc() {
		return principalDiagnDesc;
	}
	public void setPrincipalDiagnDesc(String principalDiagnDesc) {
		this.principalDiagnDesc = principalDiagnDesc;
	}
	public String getDiagnosisCodeList() {
		return diagnosisCodeList;
	}
	public void setDiagnosisCodeList(String diagnosisCodeList) {
		this.diagnosisCodeList = diagnosisCodeList;
	}
	public String getProviderId() {
		return providerId;
	}
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	public String getInNetworkFlag() {
		return inNetworkFlag;
	}
	public void setInNetworkFlag(String inNetworkFlag) {
		this.inNetworkFlag = inNetworkFlag;
	}
	public String getCoinsurance() {
		return coinsurance;
	}
	public void setCoinsurance(String coinsurance) {
		this.coinsurance = coinsurance;
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
	public String getServiceModifierList() {
		return serviceModifierList;
	}
	public void setServiceModifierList(String serviceModifierList) {
		this.serviceModifierList = serviceModifierList;
	}
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public String getLocationCodeDesc() {
		return locationCodeDesc;
	}
	public void setLocationCodeDesc(String locationCodeDesc) {
		this.locationCodeDesc = locationCodeDesc;
	}
	public String getUnitType() {
		return unitType;
	}
	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}
	public String getClaimSpecialty() {
		return claimSpecialty;
	}
	public void setClaimSpecialty(String claimSpecialty) {
		this.claimSpecialty = claimSpecialty;
	}
	public String getBetosGroup() {
		return betosGroup;
	}
	public void setBetosGroup(String betosGroup) {
		this.betosGroup = betosGroup;
	}
	public String getBetosCat() {
		return betosCat;
	}
	public void setBetosCat(String betosCat) {
		this.betosCat = betosCat;
	}
	public String getBetosDesc() {
		return betosDesc;
	}
	public void setBetosDesc(String betosDesc) {
		this.betosDesc = betosDesc;
	}
	public String getAuthorizationNumber() {
		return authorizationNumber;
	}
	public void setAuthorizationNumber(String authorizationNumber) {
		this.authorizationNumber = authorizationNumber;
	}
	public String getPractitionerTaxId() {
		return practitionerTaxId;
	}
	public void setPractitionerTaxId(String practitionerTaxId) {
		this.practitionerTaxId = practitionerTaxId;
	}
	public String getPractitionerNpi() {
		return practitionerNpi;
	}
	public void setPractitionerNpi(String practitionerNpi) {
		this.practitionerNpi = practitionerNpi;
	}
	public String getProviderTaxId() {
		return providerTaxId;
	}
	public void setProviderTaxId(String providerTaxId) {
		this.providerTaxId = providerTaxId;
	}
	public String getProviderNpi() {
		return providerNpi;
	}
	public void setProviderNpi(String providerNpi) {
		this.providerNpi = providerNpi;
	}
	public String getRefPhyId() {
		return refPhyId;
	}
	public void setRefPhyId(String refPhyId) {
		this.refPhyId = refPhyId;
	}
	public String getRefPhyNpi() {
		return refPhyNpi;
	}
	public void setRefPhyNpi(String refPhyNpi) {
		this.refPhyNpi = refPhyNpi;
	}
	public String getRefPhyName() {
		return refPhyName;
	}
	public void setRefPhyName(String refPhyName) {
		this.refPhyName = refPhyName;
	}
	public String getRefPhySpecialty() {
		return refPhySpecialty;
	}
	public void setRefPhySpecialty(String refPhySpecialty) {
		this.refPhySpecialty = refPhySpecialty;
	}
	public String getLineAuthNumber() {
		return lineAuthNumber;
	}
	public void setLineAuthNumber(String lineAuthNumber) {
		this.lineAuthNumber = lineAuthNumber;
	}
	public String getIcdVersionInd() {
		return icdVersionInd;
	}
	public void setIcdVersionInd(String icdVersionInd) {
		this.icdVersionInd = icdVersionInd;
	}
	public String getPcpLocationCode() {
		return pcpLocationCode;
	}
	public void setPcpLocationCode(String pcpLocationCode) {
		this.pcpLocationCode = pcpLocationCode;
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
	public Date getFirstServiceDate() {
		return firstServiceDate;
	}
	public void setFirstServiceDate(Date firstServiceDate) {
		this.firstServiceDate = firstServiceDate;
	}
	public Date getServiceMonth() {
		return serviceMonth;
	}
	public void setServiceMonth(Date serviceMonth) {
		this.serviceMonth = serviceMonth;
	}
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	public Date getServiceFromDate() {
		return serviceFromDate;
	}
	public void setServiceFromDate(Date serviceFromDate) {
		this.serviceFromDate = serviceFromDate;
	}
	public Date getServiceToDate() {
		return serviceToDate;
	}
	public void setServiceToDate(Date serviceToDate) {
		this.serviceToDate = serviceToDate;
	}
	public Double getCopayAmount() {
		return copayAmount;
	}
	public void setCopayAmount(Double copayAmount) {
		this.copayAmount = copayAmount;
	}
	public Double getAllowedAmount() {
		return allowedAmount;
	}
	public void setAllowedAmount(Double allowedAmount) {
		this.allowedAmount = allowedAmount;
	}
	public Double getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}
	public Double getTotalCharges() {
		return totalCharges;
	}
	public void setTotalCharges(Double totalCharges) {
		this.totalCharges = totalCharges;
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
