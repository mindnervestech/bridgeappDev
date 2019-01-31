package com.mnt.vm;

import java.util.List;

public class ReportResponseVM {

	public List<Object[]> dataList;
	public Integer noOfPages;
	public Integer totalCount;
	public String fileQuery;
	public String prevMonth;
	
	public List<Object[]> getDataList() {
		return dataList;
	}
	public void setDataList(List<Object[]> dataList) {
		this.dataList = dataList;
	}
	public Integer getNoOfPages() {
		return noOfPages;
	}
	public void setNoOfPages(Integer noOfPages) {
		this.noOfPages = noOfPages;
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
	public String getPrevMonth() {
		return prevMonth;
	}
	public void setPrevMonth(String prevMonth) {
		this.prevMonth = prevMonth;
	}
}
