package com.mnt.vm;

import java.util.List;

public class ReportVM {
	
	public String year;
	public String plan;
	public String location;
	public String fromDate;
	public String toDate;
	public String gender;
	public String provider;
	public String ageRange;
	public List<String> claimType;
	public String zipCode;
	public String hcpcsCode;
	public String condition;
	public String speciality;
	public String diagnosisCodeType;
	public String costRange;
	public String hccCode;
	public String hicn;
	public List<String> statusCodeType;
	public Integer pageSize;
	public Integer page;
	public String sortedColumns;
	public String filteredColumns;
	public String patientNameAdmissionsExpand;
	public String subscriberIdAdmissionsExpand;
	public String pcpNameAdmissionsExpand;
	public String eligibleMonthAdmissionsExpand;
	public String selectedMonth;
	public String patientType;
	public String pcpName;
	public String pcpId;
	public String medicareId;
	public String serviceMonth;
	public String firstServiceDate;
	public String paidAmount;
	public String claimTypeValue;
	
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getAgeRange() {
		return ageRange;
	}
	public void setAgeRange(String ageRange) {
		this.ageRange = ageRange;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getHcpcsCode() {
		return hcpcsCode;
	}
	public void setHcpcsCode(String hcpcsCode) {
		this.hcpcsCode = hcpcsCode;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getSpeciality() {
		return speciality;
	}
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	public String getDiagnosisCodeType() {
		return diagnosisCodeType;
	}
	public void setDiagnosisCodeType(String diagnosisCodeType) {
		this.diagnosisCodeType = diagnosisCodeType;
	}
	public String getCostRange() {
		return costRange;
	}
	public void setCostRange(String costRange) {
		this.costRange = costRange;
	}
	public String getHccCode() {
		return hccCode;
	}
	public void setHccCode(String hccCode) {
		this.hccCode = hccCode;
	}
	public String getHicn() {
		return hicn;
	}
	public void setHicn(String hicn) {
		this.hicn = hicn;
	}
	public List<String> getStatusCodeType() {
		return statusCodeType;
	}
	public void setStatusCodeType(List<String> statusCodeType) {
		this.statusCodeType = statusCodeType;
	}
	public List<String> getClaimType() {
		return claimType;
	}
	public void setClaimType(List<String> claimType) {
		this.claimType = claimType;
	}
	public String getPlan() {
		return plan;
	}
	public void setPlan(String plan) {
		this.plan = plan;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public String getSortedColumns() {
		return sortedColumns;
	}
	public void setSortedColumns(String sortedColumns) {
		this.sortedColumns = sortedColumns;
	}
	public String getFilteredColumns() {
		return filteredColumns;
	}
	public void setFilteredColumns(String filteredColumns) {
		this.filteredColumns = filteredColumns;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getPatientNameAdmissionsExpand() {
		return patientNameAdmissionsExpand;
	}
	public void setPatientNameAdmissionsExpand(String patientNameAdmissionsExpand) {
		this.patientNameAdmissionsExpand = patientNameAdmissionsExpand;
	}
	public String getSubscriberIdAdmissionsExpand() {
		return subscriberIdAdmissionsExpand;
	}
	public void setSubscriberIdAdmissionsExpand(String subscriberIdAdmissionsExpand) {
		this.subscriberIdAdmissionsExpand = subscriberIdAdmissionsExpand;
	}
	public String getPcpNameAdmissionsExpand() {
		return pcpNameAdmissionsExpand;
	}
	public void setPcpNameAdmissionsExpand(String pcpNameAdmissionsExpand) {
		this.pcpNameAdmissionsExpand = pcpNameAdmissionsExpand;
	}
	public String getEligibleMonthAdmissionsExpand() {
		return eligibleMonthAdmissionsExpand;
	}
	public void setEligibleMonthAdmissionsExpand(String eligibleMonthAdmissionsExpand) {
		this.eligibleMonthAdmissionsExpand = eligibleMonthAdmissionsExpand;
	}
	public String getSelectedMonth() {
		return selectedMonth;
	}
	public void setSelectedMonth(String selectedMonth) {
		this.selectedMonth = selectedMonth;
	}
	public String getPatientType() {
		return patientType;
	}
	public void setPatientType(String patientType) {
		this.patientType = patientType;
	}
	public String getPcpName() {
		return pcpName;
	}
	public void setPcpName(String pcpName) {
		this.pcpName = pcpName;
	}
	public String getMedicareId() {
		return medicareId;
	}
	public void setMedicareId(String medicareId) {
		this.medicareId = medicareId;
	}
	public String getPcpId() {
		return pcpId;
	}
	public void setPcpId(String pcpId) {
		this.pcpId = pcpId;
	}
	public String getServiceMonth() {
		return serviceMonth;
	}
	public void setServiceMonth(String serviceMonth) {
		this.serviceMonth = serviceMonth;
	}
	public String getFirstServiceDate() {
		return firstServiceDate;
	}
	public void setFirstServiceDate(String firstServiceDate) {
		this.firstServiceDate = firstServiceDate;
	}
	public String getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(String paidAmount) {
		this.paidAmount = paidAmount;
	}
	public String getClaimTypeValue() {
		return claimTypeValue;
	}
	public void setClaimTypeValue(String claimTypeValue) {
		this.claimTypeValue = claimTypeValue;
	}
}
