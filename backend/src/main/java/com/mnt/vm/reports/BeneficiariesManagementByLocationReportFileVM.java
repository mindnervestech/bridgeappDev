package com.mnt.vm.reports;

public class BeneficiariesManagementByLocationReportFileVM {
	
	 String fileQuery;
	 public boolean showPcpLocation_beneficiariesManagementByLocation;
     public boolean showMra_beneficiariesManagementByLocation;
     public boolean showTotalCost_beneficiariesManagementByLocation;
	  
	  
	public String getFileQuery() {
		return fileQuery;
	}
	public void setFileQuery(String fileQuery) {
		this.fileQuery = fileQuery;
	}
	public boolean isShowPcpLocation_beneficiariesManagementByLocation() {
		return showPcpLocation_beneficiariesManagementByLocation;
	}
	public void setShowPcpLocation_beneficiariesManagementByLocation(
			boolean showPcpLocation_beneficiariesManagementByLocation) {
		this.showPcpLocation_beneficiariesManagementByLocation = showPcpLocation_beneficiariesManagementByLocation;
	}
	public boolean isShowMra_beneficiariesManagementByLocation() {
		return showMra_beneficiariesManagementByLocation;
	}
	public void setShowMra_beneficiariesManagementByLocation(boolean showMra_beneficiariesManagementByLocation) {
		this.showMra_beneficiariesManagementByLocation = showMra_beneficiariesManagementByLocation;
	}
	public boolean isShowTotalCost_beneficiariesManagementByLocation() {
		return showTotalCost_beneficiariesManagementByLocation;
	}
	public void setShowTotalCost_beneficiariesManagementByLocation(
			boolean showTotalCost_beneficiariesManagementByLocation) {
		this.showTotalCost_beneficiariesManagementByLocation = showTotalCost_beneficiariesManagementByLocation;
	}
	

}
