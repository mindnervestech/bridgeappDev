package com.mnt.vm.reports;

public class BeneficiariesManagementByClinicReportFileVM {

	String fileQuery;
	public boolean showClinicName_beneficiariesManagementByClinic;
    public boolean showTotalCost_beneficiariesManagementByClinic;
    
	public String getFileQuery() {
		return fileQuery;
	}
	public void setFileQuery(String fileQuery) {
		this.fileQuery = fileQuery;
	}
	public boolean isShowClinicName_beneficiariesManagementByClinic() {
		return showClinicName_beneficiariesManagementByClinic;
	}
	public void setShowClinicName_beneficiariesManagementByClinic(boolean showClinicName_beneficiariesManagementByClinic) {
		this.showClinicName_beneficiariesManagementByClinic = showClinicName_beneficiariesManagementByClinic;
	}
	public boolean isShowTotalCost_beneficiariesManagementByClinic() {
		return showTotalCost_beneficiariesManagementByClinic;
	}
	public void setShowTotalCost_beneficiariesManagementByClinic(boolean showTotalCost_beneficiariesManagementByClinic) {
		this.showTotalCost_beneficiariesManagementByClinic = showTotalCost_beneficiariesManagementByClinic;
	}
}
