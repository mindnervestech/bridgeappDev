package com.mnt.vm;

import java.util.List;
import java.util.Map;

public class MappingVM {
	
	private List<String> fileHeaders;
	private List<String> databaseColumnNames;
	private Map<String, String> headerMappingList;
	
	public List<String> getFileHeaders() {
		return fileHeaders;
	}
	public void setFileHeaders(List<String> fileHeaders) {
		this.fileHeaders = fileHeaders;
	}
	public List<String> getDatabaseColumnNames() {
		return databaseColumnNames;
	}
	public void setDatabaseColumnNames(List<String> databaseColumnNames) {
		this.databaseColumnNames = databaseColumnNames;
	}
	public Map<String, String> getHeaderMappingList() {
		return headerMappingList;
	}
	public void setHeaderMappingList(Map<String, String> headerMappingList) {
		this.headerMappingList = headerMappingList;
	}
}
