package com.mnt.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "demographic_detail")
public class DemographicDetail extends BaseDomain {

	private static final long serialVersionUID = 1L;
	@CsvBindByName(column = "SUBSCRIBER_ID")
	@Column(name = "subscriber_id")
	private String subscriberId;
	
	@CsvBindByName(column = "ELIGIBLE_MONTH")
	@Column(name = "eligible_month")
	private Date eligibleMonth;
	
	@CsvBindByName(column = "BENEFIT_PLAN")
	@Column(name = "benefit_plan")
	private String benefitPlan;
	
	@CsvBindByName(column = "PLAN_NAME")
	@Column(name = "plan_name")
	private String planName;
	
	@CsvBindByName(column = "COUNTY_NAME")
	@Column(name = "county_name")
	private String countyName;
	
	@CsvBindByName(column = "PCP_ID")
	@Column(name = "pcp_id")
	private String pcpId;
	
	@CsvBindByName(column = "PCP_NAME")
	@Column(name = "pcp_name")
	private String pcpName;
	
	@CsvBindByName(column = "PCP_LOCATION_CODE")
	@Column(name = "pcp_location_code")
	private String pcpLocationCode;
	
	@CsvBindByName(column = "IPA_ID")
	@Column(name = "ipa_id")
	private String ipaId;
	
	@CsvBindByName(column = "IPA_NAME")
	@Column(name = "ipa_name")
	private String ipaName;
	
	@CsvBindByName(column = "BIRTH_DATE")
	@Column(name = "birth_date")
	private Date birthDate;
	
	@CsvBindByName(column = "MEDICARE_ID")
	@Column(name = "medicare_id")
	private String medicareId;
	
	@CsvBindByName(column = "MEDICAID_ID")
	@Column(name = "medicaid_id")
	
	private String medicaidId;
	@CsvBindByName(column = "LAST_NAME")
	@Column(name = "last_name")
	
	private String lastName;
	@CsvBindByName(column = "FIRST_NAME")
	@Column(name = "first_name")
	private String firstName;
	@CsvBindByName(column = "MEMBER_ADDR1")
	@Column(name = "member_addr1")
	private String memberAddr1;
	@CsvBindByName(column = "MEMBER_ADDR2")
	@Column(name = "member_addr2")
	private String memberAddr2;
	@CsvBindByName(column = "CITY")
	@Column(name = "city")
	private String city;
	@CsvBindByName(column = "STATE")
	@Column(name = "state")
	private String state;
	@CsvBindByName(column = "ZIP")
	@Column(name = "zip")
	private String zip;
	@CsvBindByName(column = "RISK_SCORE_PARTC")
	@Column(name = "risk_score_partc")
	private String riskScorePartc;
	@CsvBindByName(column = "RISK_SCORE_PARTD")
	@Column(name = "risk_score_partd")
	private String riskScorePartd;
	@CsvBindByName(column = "HOSPICE")
	@Column(name = "hospice")
	private String hospice;
	@CsvBindByName(column = "ESRD")
	@Column(name = "esrd")
	private String esrd;
	@CsvBindByName(column = "AGED_DISABLED_IND")
	@Column(name = "aged_disabled_ind")
	private String agedDisabledInd;
	@CsvBindByName(column = "INSTITUTIONAL")
	@Column(name = "institutional")
	private String institutional;
	@CsvBindByName(column = "NURSING_HOME_CERTIFIBLE")
	@Column(name = "nursing_home_certifible")
	private String nursingHomeCertifible;
	@CsvBindByName(column = "PCP_CAP")
	@Column(name = "pcp_cap")
	private String pcpCap;
	@CsvBindByName(column = "BEHAVIORAL_HEALTH")
	@Column(name = "behavioral_health")
	private String behavioralHealth;
	@CsvBindByName(column = "CHIROPRACTIC_CAP")
	@Column(name = "chiropractic_cap")
	private String chiropracticCap;
	@CsvBindByName(column = "DENTAL_CAP")
	@Column(name = "dental_cap")
	private String dentalCap;
	@CsvBindByName(column = "HEARING_CAP")
	@Column(name = "hearing_cap")
	private String hearingCap;
	@CsvBindByName(column = "LAB")
	@Column(name = "lab")
	private String lab;
	@CsvBindByName(column = "VISION_OPHTHAMALOGY")
	@Column(name = "vision_ophthamalogy")
	private String visionOphthamalogy;
	@CsvBindByName(column = "VISION_OPTOMETRY")
	@Column(name = "vision_optometry")
	private String visionOptometry;
	@CsvBindByName(column = "OTC_CAP")
	@Column(name = "otc_cap")
	private String otcCap;
	@CsvBindByName(column = "GYM_CAP")
	@Column(name = "gym_cap")
	private String gymCap;
	@CsvBindByName(column = "PODIATRY_CAP")
	@Column(name = "podiatry_cap")
	private String podiatryCap;
	@CsvBindByName(column = "PARTD_WITHHOLD")
	@Column(name = "partd_withhold")
	private String partdWithhold;
	@CsvBindByName(column = "CMS_ADJ_PART_C")
	@Column(name = "cms_adj_part_c")
	private String cmsAdjPartC;
	@CsvBindByName(column = "CMS_ADJ_PART_D")
	@Column(name = "cms_adj_part_d")
	private String cmsAdjPartD;
	@CsvBindByName(column = "GENDER")
	@Column(name = "gender")
	private String gender;
	@CsvBindByName(column = "DME_HH_CAP")
	@Column(name = "dme_hh_cap")
	private String dmeHhCap;
	@CsvBindByName(column = "TRANSPORTATION")
	@Column(name = "transportation")
	private String transportation;
	@CsvBindByName(column = "DERMATOLOGY")
	@Column(name = "dermatology")
	private String dermatology;
	@CsvBindByName(column = "AHCA_SNP_PAYMENTS")
	@Column(name = "ahca_snp_payments")
	private String ahcaSnpPayments;
	@CsvBindByName(column = "HIP_FEE_PART_C")
	@Column(name = "hip_fee_part_c")
	private String hipFeePartC;
	@CsvBindByName(column = "HIP_FEE_PART_D")
	@Column(name = "hip_fee_part_d")
	private String hipFeePartD;
	@CsvBindByName(column = "PD_RISK_CORRIDORS")
	@Column(name = "pd_risk_corridors")
	private String pdRiskCorridors;
	@Column(name = "year")
	private String year;
	@Column(name = "month")
	private String month;
	@Column(name = "provider")
	private String provider;
	@Column(name = "mbi")
	private String mbi;
	
	public String getSubscriberId() {
		return subscriberId;
	}
	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getBenefitPlan() {
		return benefitPlan;
	}
	public void setBenefitPlan(String benefitPlan) {
		this.benefitPlan = benefitPlan;
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
	public String getPcpLocationCode() {
		return pcpLocationCode;
	}
	public void setPcpLocationCode(String pcpLocationCode) {
		this.pcpLocationCode = pcpLocationCode;
	}
	public String getIpaId() {
		return ipaId;
	}
	public void setIpaId(String ipa_id) {
		this.ipaId = ipa_id;
	}
	public String getIpaName() {
		return ipaName;
	}
	public void setIpaName(String ipaName) {
		this.ipaName = ipaName;
	}
	public Date getEligibleMonth() {
		return eligibleMonth;
	}
	public void setEligibleMonth(Date eligibleMonth) {
		this.eligibleMonth = eligibleMonth;
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
	public String getMemberAddr1() {
		return memberAddr1;
	}
	public void setMemberAddr1(String memberAddr1) {
		this.memberAddr1 = memberAddr1;
	}
	public String getMemberAddr2() {
		return memberAddr2;
	}
	public void setMemberAddr2(String memberAddr2) {
		this.memberAddr2 = memberAddr2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
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
	public String getHospice() {
		return hospice;
	}
	public void setHospice(String hospice) {
		this.hospice = hospice;
	}
	public String getEsrd() {
		return esrd;
	}
	public void setEsrd(String esrd) {
		this.esrd = esrd;
	}
	public String getAgedDisabledInd() {
		return agedDisabledInd;
	}
	public void setAgedDisabledInd(String agedDisabledInd) {
		this.agedDisabledInd = agedDisabledInd;
	}
	public String getInstitutional() {
		return institutional;
	}
	public void setInstitutional(String institutional) {
		this.institutional = institutional;
	}
	public String getPcpCap() {
		return pcpCap;
	}
	public void setPcpCap(String pcpCap) {
		this.pcpCap = pcpCap;
	}
	public String getBehavioralHealth() {
		return behavioralHealth;
	}
	public void setBehavioralHealth(String behavioralHealth) {
		this.behavioralHealth = behavioralHealth;
	}
	public String getChiropracticCap() {
		return chiropracticCap;
	}
	public void setChiropracticCap(String chiropracticCap) {
		this.chiropracticCap = chiropracticCap;
	}
	public String getDentalCap() {
		return dentalCap;
	}
	public void setDentalCap(String dentalCap) {
		this.dentalCap = dentalCap;
	}
	public String getHearingCap() {
		return hearingCap;
	}
	public void setHearingCap(String hearingCap) {
		this.hearingCap = hearingCap;
	}
	public String getLab() {
		return lab;
	}
	public void setLab(String lab) {
		this.lab = lab;
	}
	public String getVisionOphthamalogy() {
		return visionOphthamalogy;
	}
	public void setVisionOphthamalogy(String visionOphthamalogy) {
		this.visionOphthamalogy = visionOphthamalogy;
	}
	public String getVisionOptometry() {
		return visionOptometry;
	}
	public void setVisionOptometry(String visionOptometry) {
		this.visionOptometry = visionOptometry;
	}
	public String getOtcCap() {
		return otcCap;
	}
	public void setOtcCap(String otcCap) {
		this.otcCap = otcCap;
	}
	public String getGymCap() {
		return gymCap;
	}
	public void setGymCap(String gymCap) {
		this.gymCap = gymCap;
	}
	public String getPodiatryCap() {
		return podiatryCap;
	}
	public void setPodiatryCap(String podiatryCap) {
		this.podiatryCap = podiatryCap;
	}
	public String getPartdWithhold() {
		return partdWithhold;
	}
	public void setPartdWithhold(String partdWithhold) {
		this.partdWithhold = partdWithhold;
	}
	public String getCmsAdjPartC() {
		return cmsAdjPartC;
	}
	public void setCmsAdjPartC(String cmsAdjPartC) {
		this.cmsAdjPartC = cmsAdjPartC;
	}
	public String getCmsAdjPartD() {
		return cmsAdjPartD;
	}
	public void setCmsAdjPartD(String cmsAdjPartD) {
		this.cmsAdjPartD = cmsAdjPartD;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDmeHhCap() {
		return dmeHhCap;
	}
	public void setDmeHhCap(String dmeHhCap) {
		this.dmeHhCap = dmeHhCap;
	}
	public String getTransportation() {
		return transportation;
	}
	public void setTransportation(String transportation) {
		this.transportation = transportation;
	}
	public String getDermatology() {
		return dermatology;
	}
	public void setDermatology(String dermatology) {
		this.dermatology = dermatology;
	}
	public String getAhcaSnpPayments() {
		return ahcaSnpPayments;
	}
	public void setAhcaSnpPayments(String ahcaSnpPayments) {
		this.ahcaSnpPayments = ahcaSnpPayments;
	}
	public String getHipFeePartC() {
		return hipFeePartC;
	}
	public void setHipFeePartC(String hipFeePartC) {
		this.hipFeePartC = hipFeePartC;
	}
	public String getHipFeePartD() {
		return hipFeePartD;
	}
	public void setHipFeePartD(String hipFeePartD) {
		this.hipFeePartD = hipFeePartD;
	}
	public String getPdRiskCorridors() {
		return pdRiskCorridors;
	}
	public void setPdRiskCorridors(String pdRiskCorridors) {
		this.pdRiskCorridors = pdRiskCorridors;
	}
	public String getCountyName() {
		return countyName;
	}
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	public String getNursingHomeCertifible() {
		return nursingHomeCertifible;
	}
	public void setNursingHomeCertifible(String nursingHomeCertifible) {
		this.nursingHomeCertifible = nursingHomeCertifible;
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
	public String getMbi() {
		return mbi;
	}
	public void setMbi(String mbi) {
		this.mbi = mbi;
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
