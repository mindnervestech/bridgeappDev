package com.mnt.vm;

import java.util.List;

public class ReportGridVM {

	public List<ReportDataVM> reportDataList;
	public Integer pages;
	public Integer totalCount;
	public String fileQuery;
	
	public List<ReportDataVM> getReportDataList() {
		return reportDataList;
	}
	public void setReportDataList(List<ReportDataVM> reportDataList) {
		this.reportDataList = reportDataList;
	}
	public Integer getPages() {
		return pages;
	}
	public void setPages(Integer pages) {
		this.pages = pages;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public String getFileQuery() {
		return fileQuery;
	}
	public void setFileQuery(String fileQuery) {
		this.fileQuery = fileQuery;
	}
}
