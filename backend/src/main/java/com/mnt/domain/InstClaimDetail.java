package com.mnt.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "inst_claim_detail")
public class InstClaimDetail extends BaseDomain{

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
	@CsvBindByName(column = "PERIOD_FROM")
	@Column(name = "period_from")
	private Date periodFrom;
	@CsvBindByName(column = "PERIOD_THRU")
	@Column(name = "period_thru")
	private Date periodThru;
	@CsvBindByName(column = "LOS")
	@Column(name = "los")
	private String los;
	@CsvBindByName(column = "PRINCIPAL_DIAGN_DESC")
	@Column(name = "principal_diagn_desc")
	private String principalDiagnDesc;
	@CsvBindByName(column = "PRINCIPAL_DIAGNOSIS")
	@Column(name = "principal_diagnosis")
	private String principalDiagnosis;
	@CsvBindByName(column = "DIAGNOSIS_CODE_LIST")
	@Column(name = "diagnosis_code_list")
	private String diagnosisCodeList;
	@CsvBindByName(column = "ADMITTING_DIAGN_DESC")
	@Column(name = "admitting_diagn_desc")
	private String admittingDiagnDesc;
	@CsvBindByName(column = "ADMITTING_DIAGNOSIS")
	@Column(name = "admitting_diagnosis")
	private String admittingDiagnosis;
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
	@CsvBindByName(column = "SERVICE_CODE_MODIFIER_LIST")
	@Column(name = "service_code_modifier_list")
	private String serviceCodeModifierList;
	@CsvBindByName(column = "TYPE_OF_BILL")
	@Column(name = "type_of_bill")
	private String typeOfBill;
	@CsvBindByName(column = "DRG_CODE")
	@Column(name = "drg_code")
	private String drgCode;
	@CsvBindByName(column = "DRG_DESC")
	@Column(name = "drg_desc")
	private String drgDesc;
	@CsvBindByName(column = "UNIT_TYPE")
	@Column(name = "unit_type")
	private String unitType;
	@CsvBindByName(column = "REVENUE_CODE")
	@Column(name = "revenue_code")
	private String revenueCode;
	@CsvBindByName(column = "REVENUE_CODE_DESC")
	@Column(name = "revenue_code_desc")
	private String revenueCodeDesc;
	@CsvBindByName(column = "UNITS")
	@Column(name = "units")
	private String units;
	@CsvBindByName(column = "CLAIM_SPECIALTY")
	@Column(name = "claim_specialty")
	private String claimSpecialty;
	@CsvBindByName(column = "BETOS_CAT")
	@Column(name = "betos_cat")
	private String betosCat;
	@CsvBindByName(column = "BETOS_GROUP")
	@Column(name = "betos_group")
	private String betosGroup;
	@CsvBindByName(column = "BETOS_DESC")
	@Column(name = "betos_desc")
	private String betosDesc;
	@CsvBindByName(column = "AUTHORIZATION_NUMBER")
	@Column(name = "authorization_number")
	private String authorizationNumber;
	@CsvBindByName(column = "CHECK_DATE")
	@Column(name = "check_date")
	private Date checkDate;
	@CsvBindByName(column = "TYPE_FLAG")
	@Column(name = "type_flag")
	private String typeFlag;
	@CsvBindByName(column = "SERVICE_DATE")
	@Column(name = "service_date")
	private Date serviceDate;
	@CsvBindByName(column = "PROVIDER_TAX_ID")
	@Column(name = "provider_tax_id")
	private String providerTaxId;
	@CsvBindByName(column = "PROVIDER_NPI")
	@Column(name = "provider_npi")
	private String providerNpi;
	@CsvBindByName(column = "SUBROGATION_AMT")
	@Column(name = "subrogation_amt")
	private Double subrogationAmt;
	@CsvBindByName(column = "PATIENT_STATUS")
	@Column(name = "patient_status")
	private String patientStatus;
	@CsvBindByName(column = "ADMISSION_SOURCE")
	@Column(name = "admission_source")
	private String admissionSource;
	@CsvBindByName(column = "LINE_AUTH_NUMBER")
	@Column(name = "line_auth_number")
	private String lineAuthNumber;
	@CsvBindByName(column = "ATTENDING_PHYSICIAN_ID")
	@Column(name = "attending_physician_id")
	private String attendingPhysicianId;
	@CsvBindByName(column = "ATTENDING_PHYSICIAN_NAME")
	@Column(name = "attending_physician_name")
	private String attendingPhysicianName;
	@CsvBindByName(column = "ATTENDING_PHYSICIAN_SPEC")
	@Column(name = "attending_physician_spec")
	private String attendingPhysicianSpec;
	@CsvBindByName(column = "ATTENDING_PHYS_TAX_ID")
	@Column(name = "attending_phys_tax_id")
	private String attendingPhysTaxId;
	@CsvBindByName(column = "ATTENDING_PHYSICIAN_NPI")
	@Column(name = "attending_physician_npi")
	private String attendingPhysicianNpi;
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
	public String getLos() {
		return los;
	}
	public void setLos(String los) {
		this.los = los;
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
	public Date getPeriodFrom() {
		return periodFrom;
	}
	public void setPeriodFrom(Date periodFrom) {
		this.periodFrom = periodFrom;
	}
	public Date getPeriodThru() {
		return periodThru;
	}
	public void setPeriodThru(Date periodThru) {
		this.periodThru = periodThru;
	}
	public String getPrincipalDiagnDesc() {
		return principalDiagnDesc;
	}
	public void setPrincipalDiagnDesc(String principalDiagnDesc) {
		this.principalDiagnDesc = principalDiagnDesc;
	}
	public String getPrincipalDiagnosis() {
		return principalDiagnosis;
	}
	public void setPrincipalDiagnosis(String principalDiagnosis) {
		this.principalDiagnosis = principalDiagnosis;
	}
	public String getDiagnosisCodeList() {
		return diagnosisCodeList;
	}
	public void setDiagnosisCodeList(String diagnosisCodeList) {
		this.diagnosisCodeList = diagnosisCodeList;
	}
	public String getAdmittingDiagnDesc() {
		return admittingDiagnDesc;
	}
	public void setAdmittingDiagnDesc(String admittingDiagnDesc) {
		this.admittingDiagnDesc = admittingDiagnDesc;
	}
	public String getAdmittingDiagnosis() {
		return admittingDiagnosis;
	}
	public void setAdmittingDiagnosis(String admittingDiagnosis) {
		this.admittingDiagnosis = admittingDiagnosis;
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
	public String getServiceCodeModifierList() {
		return serviceCodeModifierList;
	}
	public void setServiceCodeModifierList(String serviceCodeModifierList) {
		this.serviceCodeModifierList = serviceCodeModifierList;
	}
	public String getTypeOfBill() {
		return typeOfBill;
	}
	public void setTypeOfBill(String typeOfBill) {
		this.typeOfBill = typeOfBill;
	}
	public String getDrgCode() {
		return drgCode;
	}
	public void setDrgCode(String drgCode) {
		this.drgCode = drgCode;
	}
	public String getDrgDesc() {
		return drgDesc;
	}
	public void setDrgDesc(String drgDesc) {
		this.drgDesc = drgDesc;
	}
	public String getUnitType() {
		return unitType;
	}
	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}
	public String getRevenueCode() {
		return revenueCode;
	}
	public void setRevenueCode(String revenueCode) {
		this.revenueCode = revenueCode;
	}
	public String getRevenueCodeDesc() {
		return revenueCodeDesc;
	}
	public void setRevenueCodeDesc(String revenueCodeDesc) {
		this.revenueCodeDesc = revenueCodeDesc;
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
	public String getBetosCat() {
		return betosCat;
	}
	public void setBetosCat(String betosCat) {
		this.betosCat = betosCat;
	}
	public String getBetosGroup() {
		return betosGroup;
	}
	public void setBetosGroup(String betosGroup) {
		this.betosGroup = betosGroup;
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
	public String getTypeFlag() {
		return typeFlag;
	}
	public void setTypeFlag(String typeFlag) {
		this.typeFlag = typeFlag;
	}
	public Date getServiceDate() {
		return serviceDate;
	}
	public void setServiceDate(Date serviceDate) {
		this.serviceDate = serviceDate;
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
	public String getPatientStatus() {
		return patientStatus;
	}
	public void setPatientStatus(String patientStatus) {
		this.patientStatus = patientStatus;
	}
	public String getAdmissionSource() {
		return admissionSource;
	}
	public void setAdmissionSource(String admissionSource) {
		this.admissionSource = admissionSource;
	}
	public String getLineAuthNumber() {
		return lineAuthNumber;
	}
	public void setLineAuthNumber(String lineAuthNumber) {
		this.lineAuthNumber = lineAuthNumber;
	}
	public String getAttendingPhysicianId() {
		return attendingPhysicianId;
	}
	public void setAttendingPhysicianId(String attendingPhysicianId) {
		this.attendingPhysicianId = attendingPhysicianId;
	}
	public String getAttendingPhysicianName() {
		return attendingPhysicianName;
	}
	public void setAttendingPhysicianName(String attendingPhysicianName) {
		this.attendingPhysicianName = attendingPhysicianName;
	}
	public String getAttendingPhysicianSpec() {
		return attendingPhysicianSpec;
	}
	public void setAttendingPhysicianSpec(String attendingPhysicianSpec) {
		this.attendingPhysicianSpec = attendingPhysicianSpec;
	}
	public String getAttendingPhysTaxId() {
		return attendingPhysTaxId;
	}
	public void setAttendingPhysTaxId(String attendingPhysTaxId) {
		this.attendingPhysTaxId = attendingPhysTaxId;
	}
	public String getAttendingPhysicianNpi() {
		return attendingPhysicianNpi;
	}
	public void setAttendingPhysicianNpi(String attendingPhysicianNpi) {
		this.attendingPhysicianNpi = attendingPhysicianNpi;
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
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
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
