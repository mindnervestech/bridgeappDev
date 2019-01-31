package com.mnt.vm.reports;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BeneficiariesManagementReportPrintDataVM {

	@JsonProperty("Plan Name")
	public String planName;

	@JsonProperty("HICN/ Subscriber ID")
	public String hicn;
	
	@JsonProperty("Patient Name")
	public String patientName;
	
	@JsonProperty("DOB")
	public String dob;
	
	@JsonProperty("Eligible Month")
	public String eligibleMonth;
	
	@JsonProperty("Termed Month")
	public String termedMonth;
	
	@JsonProperty("PCP Name")
	public String pcpName;
	
	@JsonProperty("PCP Location")
	public String pcpLocation;
	
	@JsonProperty("MRA")
	public String mra;
	
	@JsonProperty("Total Cost")
	public String totalCost;
	
	@JsonProperty("Address")
	public String address;
	
	@JsonProperty("Recent Appointment Date")
	public String recentAppointmentDate;
	
	@JsonProperty("Next Appointment Date")
	public String nextAppointmentDate;
	
	@JsonProperty("Facility Location")
	public String facilityLocation;
	
	@JsonProperty("Phone Number")
	public String phoneNumber;
	
	@JsonProperty("Last Claims Date")
	public String lastClaimsDate;
	
	@JsonProperty("ICD 9/10 Code")
	public String icdCode;

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getHicn() {
		return hicn;
	}

	public void setHicn(String hicn) {
		this.hicn = hicn;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getEligibleMonth() {
		return eligibleMonth;
	}

	public void setEligibleMonth(String eligibleMonth) {
		this.eligibleMonth = eligibleMonth;
	}

	public String getTermedMonth() {
		return termedMonth;
	}

	public void setTermedMonth(String termedMonth) {
		this.termedMonth = termedMonth;
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

	public String getMra() {
		return mra;
	}

	public void setMra(String mra) {
		this.mra = mra;
	}

	public String getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRecentAppointmentDate() {
		return recentAppointmentDate;
	}

	public void setRecentAppointmentDate(String recentAppointmentDate) {
		this.recentAppointmentDate = recentAppointmentDate;
	}

	public String getNextAppointmentDate() {
		return nextAppointmentDate;
	}

	public void setNextAppointmentDate(String nextAppointmentDate) {
		this.nextAppointmentDate = nextAppointmentDate;
	}

	public String getFacilityLocation() {
		return facilityLocation;
	}

	public void setFacilityLocation(String facilityLocation) {
		this.facilityLocation = facilityLocation;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getLastClaimsDate() {
		return lastClaimsDate;
	}

	public void setLastClaimsDate(String lastClaimsDate) {
		this.lastClaimsDate = lastClaimsDate;
	}

	public String getIcdCode() {
		return icdCode;
	}

	public void setIcdCode(String icdCode) {
		this.icdCode = icdCode;
	}
}
