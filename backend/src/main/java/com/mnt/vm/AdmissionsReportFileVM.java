package com.mnt.vm;

public class AdmissionsReportFileVM {

	public String fileQuery;
	public boolean showSubscriberId_admissions;
	public boolean showPatientName_admissions;
	public boolean showPcpName_admissions;
	public boolean showEligibleMonth_admissions;
	public boolean showTotalNoOfAdmissions_admissions;
	public boolean showTotalCost_admissions;
	
	public String getFileQuery() {
		return fileQuery;
	}
	public void setFileQuery(String fileQuery) {
		this.fileQuery = fileQuery;
	}
	public boolean isShowSubscriberId_admissions() {
		return showSubscriberId_admissions;
	}
	public void setShowSubscriberId_admissions(boolean showSubscriberId_admissions) {
		this.showSubscriberId_admissions = showSubscriberId_admissions;
	}
	public boolean isShowPatientName_admissions() {
		return showPatientName_admissions;
	}
	public void setShowPatientName_admissions(boolean showPatientName_admissions) {
		this.showPatientName_admissions = showPatientName_admissions;
	}
	public boolean isShowPcpName_admissions() {
		return showPcpName_admissions;
	}
	public void setShowPcpName_admissions(boolean showPcpName_admissions) {
		this.showPcpName_admissions = showPcpName_admissions;
	}
	public boolean isShowEligibleMonth_admissions() {
		return showEligibleMonth_admissions;
	}
	public void setShowEligibleMonth_admissions(boolean showEligibleMonth_admissions) {
		this.showEligibleMonth_admissions = showEligibleMonth_admissions;
	}
	public boolean isShowTotalNoOfAdmissions_admissions() {
		return showTotalNoOfAdmissions_admissions;
	}
	public void setShowTotalNoOfAdmissions_admissions(boolean showTotalNoOfAdmissions_admissions) {
		this.showTotalNoOfAdmissions_admissions = showTotalNoOfAdmissions_admissions;
	}
	public boolean isShowTotalCost_admissions() {
		return showTotalCost_admissions;
	}
	public void setShowTotalCost_admissions(boolean showTotalCost_admissions) {
		this.showTotalCost_admissions = showTotalCost_admissions;
	}
}
