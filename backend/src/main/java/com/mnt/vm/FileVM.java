package com.mnt.vm;

import java.util.List;

public class FileVM {

	public String fileQuery;
	public boolean showProviderName;
	public boolean showMedicareId;
	public boolean showPatientName;
	public boolean showICDCode;
	public boolean showHCCCodes;
	public boolean showTermedMonth;
	public boolean showEligibleMonth;
	public boolean showCost;
	public String speciality;
	public List<String> claimType; 
	
	public String getFileQuery() {
		return fileQuery;
	}
	public void setFileQuery(String fileQuery) {
		this.fileQuery = fileQuery;
	}
	public boolean isShowProviderName() {
		return showProviderName;
	}
	public void setShowProviderName(boolean showProviderName) {
		this.showProviderName = showProviderName;
	}
	public boolean isShowMedicareId() {
		return showMedicareId;
	}
	public void setShowMedicareId(boolean showMedicareId) {
		this.showMedicareId = showMedicareId;
	}
	public boolean isShowPatientName() {
		return showPatientName;
	}
	public void setShowPatientName(boolean showPatientName) {
		this.showPatientName = showPatientName;
	}
	public boolean isShowICDCode() {
		return showICDCode;
	}
	public void setShowICDCode(boolean showICDCode) {
		this.showICDCode = showICDCode;
	}
	public boolean isShowHCCCodes() {
		return showHCCCodes;
	}
	public void setShowHCCCodes(boolean showHCCCodes) {
		this.showHCCCodes = showHCCCodes;
	}
	public boolean isShowTermedMonth() {
		return showTermedMonth;
	}
	public void setShowTermedMonth(boolean showTermedMonth) {
		this.showTermedMonth = showTermedMonth;
	}
	public boolean isShowEligibleMonth() {
		return showEligibleMonth;
	}
	public void setShowEligibleMonth(boolean showEligibleMonth) {
		this.showEligibleMonth = showEligibleMonth;
	}
	public boolean isShowCost() {
		return showCost;
	}
	public void setShowCost(boolean showCost) {
		this.showCost = showCost;
	}
	public String getSpeciality() {
		return speciality;
	}
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	public List<String> getClaimType() {
		return claimType;
	}
	public void setClaimType(List<String> claimType) {
		this.claimType = claimType;
	}
}
