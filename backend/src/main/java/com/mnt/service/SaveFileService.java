package com.mnt.service;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.mnt.vm.MappingVM;

public interface SaveFileService {	
	
	// back-end Methods
	void saveFileDetail(String tableName, File csvFile, String year, String month, String provider, Date lasModifiationTime);
	void insertIntoTable(String tablename,File csvFile, String year, String month, String provider);
	void deleteOldRecordsFromTable(String tablename, String year, String month, String provider);
	void saveMapping(String provider, String filename, String mappingJSON);
	
	// API methods for front-end
	Map<String,List<String>> getProviders();
	MappingVM getMappingData(String provider, String filename);
	Map<String,List<String>> getProvidersFTP();
}
