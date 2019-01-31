package com.mnt.service.impl;


import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTPFile;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mnt.constants.FileConstants;
import com.mnt.dao.DemographicDetailDao;
import com.mnt.dao.FileInfoDao;
import com.mnt.dao.InstClaimDetailDao;
import com.mnt.dao.PremiumDetailDao;
import com.mnt.dao.ProfClaimDetailDao;
import com.mnt.dao.RxDetailDao;
import com.mnt.dao.SpecialtyClaimDetailDao;
import com.mnt.dao.StoplossDetailDao;
import com.mnt.domain.DemographicDetail;
import com.mnt.domain.FileInfo;
import com.mnt.domain.InstClaimDetail;
import com.mnt.domain.PremiumDetail;
import com.mnt.domain.ProfClaimDetail;
import com.mnt.domain.RxDetail;
import com.mnt.domain.SpecialtyClaimDetail;
import com.mnt.domain.StoplossDetail;
import com.mnt.service.SaveFileService;
import com.mnt.utils.FTPManager;
import com.mnt.utils.UserConfig;
import com.mnt.vm.MappingVM;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;


@Repository
public class SaveFileServiceImpl implements SaveFileService {

	/*
	 * 
	@Autowired
	StoplossDetailDao stoplossDetailDao;
	@Autowired
	DemographicDetailDao demographicDetailDao;
	@Autowired
	InstClaimDetailDao instClaimDetailDao;
	@Autowired
	PremiumDetailDao premiumDetailDao;
	@Autowired
	ProfClaimDetailDao profClaimDetailDao;
	@Autowired
	RxDetailDao rxDetailDao;
	@Autowired
	SpecialtyClaimDetailDao specialtyClaimDetailDao;
	*/
	
	@Autowired
	FileInfoDao fileInfoDao;
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	// REST API FUNCTIONS ##############################################
	// fetching providers from directory
	@Override
	public Map<String,List<String>> getProviders() {
		
		String localProviderHeaderDir = UserConfig.localProviderHeaderDir;
		
		Map<String,List<String>> map = new HashMap<>();
		File[] providerDirectories = new File(localProviderHeaderDir).listFiles(File::isDirectory);
		for(File provider: providerDirectories) {
			List<String> fileList = new ArrayList<>();
			File[] fileNames = provider.listFiles();
			for(File fileObj: fileNames) {
				fileList.add(fileObj.getName());
			}
			map.put(provider.getName(), fileList);
		}	
		return map;
	}
	
	public Map<String,List<String>> getProvidersFTP() {	
		// connect to server
		String server = UserConfig.server;
		String username = UserConfig.username;
		String password = UserConfig.password;
		String ftpProviderHeaderDir = UserConfig.ftpProviderHeaderDir;
		FTPManager ftp = null;
		
		Map<String,List<String>> map = new HashMap<>();
		
		try {
			ftp = new FTPManager(server, username, password);
			ftp.changeWorkingDirectory(ftpProviderHeaderDir);
			
			FTPFile[] providerDirectories = ftp.listDirectories();
			
			for(FTPFile provider: providerDirectories) {
				// go inside a specific provider directory
				ftp.changeWorkingDirectory(provider.getName());
				
				//work with files inside the providers directory
				List<String> fileList = new ArrayList<>();
				FTPFile[] fileNames = ftp.listFiles();
				for(FTPFile fileObj: fileNames) {
					fileList.add(fileObj.getName());
				}
				map.put(provider.getName(), fileList);
				
				// go back to providerDirectories
				ftp.changeToParentDirectory();	
			}
			ftp.disconnect();
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Could not get the providers and format file list.");
		}
		
		return map;
	}
	
	// save mapping from front-end to database
	@Override
	public void saveMapping(String provider, String filename, String mappingJSON) {
		
		System.out.println(provider);
		System.out.println(filename);
		System.out.println(mappingJSON);
		
		String deleteQuery = "DELETE FROM header_mapping WHERE provider='" + provider +"' AND filename='" + filename + "'" ;
		System.out.println(" >" + deleteQuery);
		jdbcTemplate.execute(deleteQuery);
		String insertQuery = "INSERT INTO header_mapping (provider, filename, mapping_json) VALUES('" + provider +"', '" + filename + "', '" + mappingJSON + "')";
		System.out.println(" >" + insertQuery);
		jdbcTemplate.execute(insertQuery);
	}
		
	// get database columns, provider file headers, saved mappings
	@Override
	public MappingVM getMappingData(String provider, String filename) {
		
		MappingVM mappingVM = new MappingVM();
		
		// provider defined headers from file
		// SECTION ***************************
		// connect to FTP server
		String server = UserConfig.server;
		String username = UserConfig.username;
		String password = UserConfig.password;
		FTPManager ftp = null;
		
		String localTempfileBaseDir = UserConfig.localTempfileBaseDir;
		String basePath = UserConfig.ftpProviderHeaderDir;
		String providerPath = basePath + "/" + provider;
		File fileHandle = null;
		
		try {
			ftp = new FTPManager(server, username, password);
			ftp.changeWorkingDirectory(providerPath);
			
			// get file from server
			// create a temporary localfile to work with
			fileHandle = new File(localTempfileBaseDir + "/" + filename);
			OutputStream outputStream = new FileOutputStream(fileHandle);
			ftp.retrieveFile(filename, outputStream);
			outputStream.close();
			// got file from server so disconnect
			ftp.disconnect();
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Could not connect to server or file is not on server.");
		}
			
		// work with file to get headers
		Scanner sc = null;
		String[] headers = null;
		String singleHeadersLine = "";
		
		File providerFile = fileHandle;
		
		try {
			sc = new Scanner(providerFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		// read first line which has headers
		if(sc.hasNextLine())
			singleHeadersLine = sc.nextLine();
		
		headers = singleHeadersLine.split(",");
		fileHandle.delete();
		// remove temporary file from system
			
		// SECTION END ************************
		
		// Column names from Database
		// SECTION ***************************
		String query = "";
		if(filename.endsWith(FileConstants.DEMO_GRAPHIC_FILE_SUFFIX))
			query = "describe demographic_detail";
		else if(filename.endsWith(FileConstants.INST_CLAIM_DETAIL_FILE_SUFFIX)) 
			query = "describe inst_claim_detail";
		else if(filename.endsWith(FileConstants.PREMIUM_DETAIL_FILE_SUFFIX))
			query = "describe premium_detail";
		else if(filename.endsWith(FileConstants.PROF_CLAIM_FILE_SUFFIX))
			query = "describe prof_claim_detail";
		else if(filename.endsWith(FileConstants.RX_FILE_SUFFIX))
			query = "describe rx_detail";
		else if(filename.endsWith(FileConstants.SPECIALTY_CLAIM_FILE_SUFFIX))
			query = "describe specialty_claim_detail";
		else if(filename.endsWith(FileConstants.STOPLOSS_FILE_SUFFIX))
			query = "describe stoploss_detail";
		
		getDatabaseColumnNames gdcn = new getDatabaseColumnNames();
		jdbcTemplate.query(query, gdcn);
		String[] databaseColumnNames = gdcn.getColumnNames();
		// SECTION END ************************
		
		// Getting stored mapping from database
		// SECTION ***************************
		getMapping mapper = new getMapping();
		String queryString = "SELECT * FROM header_mapping WHERE provider='" + provider + "' and filename='" + filename + "'";
		jdbcTemplate.query(queryString, mapper);
		
		JSONObject json = mapper.getJson();
		Map<String, String> mappingMap = null;
		if(json != null) {
			mappingMap = new HashMap<String, String>();
			Iterator<String> keysItr = json.keys();
			while(keysItr.hasNext()) {
				String key = keysItr.next();
				String value = null;
				try {
					value = json.getString(key);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mappingMap.put(key, value);
			}
		}
		
		
		// SECTION END ************************
		
		mappingVM.setFileHeaders(Arrays.asList(headers));
		mappingVM.setDatabaseColumnNames(Arrays.asList(databaseColumnNames));
		mappingVM.setHeaderMappingList(mappingMap);
		return mappingVM;
	}
	// RES API FUNCTIONS END ############################################
	
	// Helper classes for reading rows ################################
	// gets mapping of database column names and file headers as json
	// mapping is already stored in database
	// getMapping from database
	class getMapping implements RowCallbackHandler {
		
		private JSONObject jsonObj;
		
		public getMapping() {
			this.jsonObj = null;
		}
		
		public JSONObject getJson() {
			return this.jsonObj;
		}
		
		public void processRow(ResultSet rs) { 
			
			String value = null;
			// get mapping from database
			try {
				value = rs.getString(4);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			// covert into json object
			try {
				this.jsonObj = new JSONObject(value);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	// gets column names of from a table
	// get database column headers for a file
	class getDatabaseColumnNames implements RowCallbackHandler {
		
		ArrayList<String> columnNames;
		
		public getDatabaseColumnNames() {
			this.columnNames = new ArrayList<String>();
		}
		
		public String[] getColumnNames() {
			return this.columnNames.toArray(new String[this.columnNames.size()]);
		}

		@Override
		public void processRow(ResultSet rs) throws SQLException {
			String value = rs.getString(1);
			if(!value.equals("id") && !value.equals("year") && !value.equals("provider") && !value.equals("month"))
				this.columnNames.add(value);
		}
		
	}
	// HELPER CLASS END ################################################
	
	// INTERACTION WITH  DATABASE ##################################
	// insert into table data
	public void insertIntoTable(String tablename,File csvFile, String year, String month, String provider) {
		
		int totalCount, exceptionCount=0, savedCount=0;
		String[] record = null;
		String filename = csvFile.getName();
		
		getMapping mapper = new getMapping();
		String queryString = "SELECT * FROM header_mapping WHERE provider='" + provider + "' and filename='" + filename + "'";
		jdbcTemplate.query(queryString, mapper);
		
		JSONObject json = mapper.getJson();
			
		try {
			CSVReader reader = null;
			reader = new CSVReader(new FileReader(csvFile));
			
			// Read all rows from file
			List<String[]> myEntries = reader.readAll();
			String[] headers = myEntries.get(0);
			totalCount = myEntries.size()-1;
			
			// build query
			for(int j=1; j<myEntries.size(); j++) {
				
				record = myEntries.get(j);
				
				String query = "insert into "+ tablename + "(";
				try {
					
							for(int i=0; i<record.length; i++) {
								query += json.getString(headers[i]) + ",";
							}
						
						query += "year, month, provider) values(";
		
						for(int i=0; i<record.length; i++) {
							if(!record[i].equals("")) {
								String columnName = json.getString(headers[i]);
								if(tablename.equals("demographic_detail") && columnName.equals("birth_date") || columnName.equals("eligible_month")) {
									query += "STR_TO_DATE("+"\"" + record[i] + "\"" +", '%m/%d/%Y')" +",";
								} else if(tablename.equals("inst_claim_detail") && columnName.equals("birth_date") || columnName.equals("check_date") || columnName.equals("first_service_date") || columnName.equals("period_from") || columnName.equals("period_thru") || columnName.equals("service_month") || columnName.equals("service_date")) {
									query += "STR_TO_DATE("+"\"" + record[i] + "\"" +", '%m/%d/%Y')" +",";
								} else if(tablename.equals("premium_detail") && columnName.equals("birth_date") || columnName.equals("eligible_month") || columnName.equals("payment_month")) {
									query += "STR_TO_DATE("+"\"" + record[i] + "\"" +", '%m/%d/%Y')" +",";
								} else if(tablename.equals("prof_claim_detail") && columnName.equals("birth_date") || columnName.equals("check_date") || columnName.equals("first_service_date") || columnName.equals("service_from_date") || columnName.equals("service_month") || columnName.equals("service_to_date")) {
									query += "STR_TO_DATE("+"\"" + record[i] + "\"" +", '%m/%d/%Y')" +",";
								} else if(tablename.equals("rx_detail") && columnName.equals("birth_date") || columnName.equals("date_filled") || columnName.equals("month_filled")) {
									query += "STR_TO_DATE("+"\"" + record[i] + "\"" +", '%m/%d/%Y')" +",";
								} else if(tablename.equals("specialty_claim_detail") && columnName.equals("paid_date") || columnName.equals("paid_month") || columnName.equals("service_month") || columnName.equals("service_date")) {
									query += "STR_TO_DATE("+"\"" + record[i] + "\"" +", '%m/%d/%Y')" +",";
								} else if(tablename.equals("stoploss_detail") && columnName.equals("eligible_month")) {
									query += "STR_TO_DATE("+"\"" + record[i] + "\"" +", '%m/%d/%Y')" +",";
								} else {
									query += "\"" + record[i] + "\"" + ",";
								}
							}
							else {
								query +=  "NULL,";
							}
						}
						query += ( "\"" +year + "\", \"" + month + "\", \"" + provider + "\")");
						System.out.println("> " + query);					
						
						
							jdbcTemplate.execute(query);
							savedCount++;
						} catch(Exception dae) {
							exceptionCount++;
							// write missing record to log
							dae.printStackTrace();
							String exceptionMessage = dae.getMessage();
							writeMissedRecordToLogfile(record,filename, provider, exceptionMessage);
							String recordToWrite = "";
							for(int i=0; i<record.length; i++) {
								recordToWrite = recordToWrite + "\""+record[i]+"\""+",";
							}
							recordToWrite = recordToWrite + record.length;
							FileUtils.writeStringToFile(new File("MissedRecordsFile.txt"),recordToWrite+"\n"+exceptionMessage+"\n", true);
							//System.exit(1);
						}
				
			}	
			// write summary to log
			writeToLogFile(exceptionCount, totalCount, savedCount, filename, provider, year, month, "");
			reader.close();
		
		} catch (Exception e) {
			// upper exception
			// write has not been read
			// parsing error => error in mapping
			e.printStackTrace();
			writeToLogFile(0, 0, 0, filename, provider, year, month, e.getMessage());
			String recordToWrite = "";
			for(int i=0; i<record.length; i++) {
				recordToWrite = recordToWrite + "\""+record[i]+"\""+",";
			}
			recordToWrite = recordToWrite + record.length;
			try {
				FileUtils.writeStringToFile(new File("MissedRecordsFile.txt"), recordToWrite+"\n", true);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//System.exit(1);
			
		}
	}
	
	// delete old records from table
	public void deleteOldRecordsFromTable(String tableName, String year, String month, String provider) {	
					
		String query = "DELETE FROM " + tableName + " WHERE year='" + year + "' and month='" + month + "' and provider='" + provider + "'";
	
		System.out.println("> " + query);					
		
		jdbcTemplate.execute(query);
	}
	// INTERACTION WITH DATABASE END #################################
	
	// BACK END TOP LEVEL FUNCTION ###################################
	// save csv file into table
	public void saveFileDetail(String tablename,File csvFile, String year, String month, String provider, Date lasModifiationTime) {
		if(csvFile.exists() && !csvFile.isDirectory()) {
			FileInfo fileInfo = fileInfoDao.getFileInfo(csvFile.getName(), year, month, provider);
			
			if(fileInfo != null) {
				if(lasModifiationTime.after(fileInfo.getLastAccessed())) {
					deleteOldRecordsFromTable(tablename, year, month, provider);
					insertIntoTable(tablename,csvFile,year,month,provider);
					fileInfo.setLastAccessed(new Date());
					fileInfoDao.save(fileInfo);
				}
			} else {
				deleteOldRecordsFromTable(tablename, year, month, provider);
				insertIntoTable(tablename,csvFile,year,month,provider);
				FileInfo fileInfoObj = new FileInfo();
				fileInfoObj.setFileName(csvFile.getName());
				fileInfoObj.setYear(year);
				fileInfoObj.setMonth(month);
				fileInfoObj.setProvider(provider);
				fileInfoObj.setLastAccessed(new Date());
				fileInfoDao.save(fileInfoObj);
			}
		}
	}
	// BACK END TOP LEVEL FUNCTION END ###################################
	
	// writes log summary to file
	public void writeToLogFile(int exceptionCount, int totalCount, int savedCount, String fileName, String provider, String year, String month, String extraMessage) {
		
		// configure log file base path
		String logFileBasePath = UserConfig.logFileBasePath;
		String toWrite = "";
					
		if(exceptionCount == 0 && totalCount == 0 && savedCount == 0) {
			toWrite += "< Date and Time: "+ new Date() + " >\n";
			toWrite += "< For file in '" + year + "/" + month + "/" + provider + "/' >\n";
			toWrite += "Possible that the file is blank (or) Possible error in reading csv file (or) Possible error in mapping definations.\n";
			toWrite += "Extra Messages: " + extraMessage + "\n";
			toWrite += "\n";
		} else {
			toWrite += "< Date and Time: "+ new Date() + " >\n";
			toWrite += "< For file in '" + year + "/" + month + "/" + provider + "/' >\n";
			toWrite += "Total records : " + totalCount + "\n";
			toWrite += "Saved records : " + savedCount + "\n";
			toWrite += "Exceptions occured : " + exceptionCount + "\n";
			toWrite += "Extra Messages: " + extraMessage + "\n";
			toWrite += "\n";
		}
		
		// connect to server
		String server = UserConfig.server;
		String username = UserConfig.username;
		String password = UserConfig.password;
		FTPManager ftp = null;
		
		try {
			ftp = new FTPManager(server, username, password);
			ftp.changeWorkingDirectory(logFileBasePath);
			
			InputStream is = new ByteArrayInputStream( toWrite.getBytes() );
			
			ftp.appendFile(fileName, is);
			is.close();
			
			ftp.disconnect();
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Could not write to log file.");
		}
	}
	
	// writes a single missing record to the specified file
	public void writeMissedRecordToLogfile(String[] record, String fileName, String provider, String exceptionMessage) {
		
		String logFileBasePath = UserConfig.logFileBasePath;
		
		String toWrite = "";
		
		toWrite += "< Date and Time: "+ new Date() + " >\n";
		toWrite += "Exception messgage: " + exceptionMessage + "\n";
		toWrite += "[" + String.join(", ", record) + "]\n";
		toWrite += "\n";
		
		// connect to server
		String server = UserConfig.server;
		String username = UserConfig.username;
		String password = UserConfig.password;
		FTPManager ftp = null;
				
		try {
			ftp = new FTPManager(server, username, password);
			ftp.changeWorkingDirectory(logFileBasePath);
			
			InputStream is = new ByteArrayInputStream( toWrite.getBytes() );
			
			ftp.appendFile(fileName, is);
			is.close();
			
			ftp.disconnect();
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Could not write to log file.");
		}
		

	}
	
	public Reader getReaderObject(File file) {
		Reader reader = null;
		try {
			InputStream inputStream = new FileInputStream(file);
			reader = new InputStreamReader(inputStream, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reader;
	}

	
	/*
	//1
	//@Override
	public void saveDemographicDetail(File csvFile, String year, String month, String provider, Date lasModifiationTime) {
		if(csvFile.exists() && !csvFile.isDirectory()) {
			FileInfo fileInfo = fileInfoDao.getFileInfo(csvFile.getName(), year, month, provider);
			
			if(fileInfo != null) {
				if(lasModifiationTime.after(fileInfo.getLastAccessed())) {
					demographicDetailDao.deleteOldRecords(year, month, provider);
					saveDemographicDetailData(csvFile,year,month,provider);
					fileInfo.setLastAccessed(new Date());
					fileInfoDao.save(fileInfo);
				}
			} else {
				saveDemographicDetailData(csvFile,year,month,provider);
				FileInfo fileInfoObj = new FileInfo();
				fileInfoObj.setFileName(csvFile.getName());
				fileInfoObj.setYear(year);
				fileInfoObj.setMonth(month);
				fileInfoObj.setProvider(provider);
				fileInfoObj.setLastAccessed(new Date());
				fileInfoDao.save(fileInfoObj);
			}
		}
	}
 	
	public void saveDemographicDetailData(File csvFile, String year, String month, String provider) {
		
		String logfileName = "demographicDetailLog.txt";
		
		CsvToBean<DemographicDetail> csvToBean = null;
		
		try {
			csvToBean = new CsvToBeanBuilder<DemographicDetail>(getReaderObject(csvFile))
	                .withType(DemographicDetail.class)
	                .withIgnoreLeadingWhiteSpace(true)
	                .build();
		} catch (Exception e){
			e.printStackTrace();
			// Error in reading data from csv
			writeToLogFile(0, 0, 0, logfileName, provider, year, month, e.getMessage());
			return;
		}
		
		Iterator<DemographicDetail> csvUserIterator = csvToBean.iterator();
		int exceptionCount = 0,totalCount = 0;
		
		while (csvUserIterator.hasNext()) {
			totalCount++;
			DemographicDetail demographicDetail= csvUserIterator.next();
			demographicDetail.setYear(year);
			demographicDetail.setMonth(month);
			demographicDetail.setProvider(provider);
			try {
				demographicDetailDao.save(demographicDetail);
			} catch(Exception e) {
				// write missed record to logfile
				e.printStackTrace();
				writeMissedRecordToLogfile(demographicDetail, logfileName, provider, e.getMessage());
				exceptionCount++;
			}
		}
		
		// write summary of number of records written to logfile
		writeToLogFile(exceptionCount, totalCount, totalCount-exceptionCount, logfileName, provider, year, month, "none");
	}
	
	//2
	@Override
	public void saveInstClaimDetail(File csvFile, String year, String month, String provider, Date lasModifiationTime) {
		if(csvFile.exists() && !csvFile.isDirectory()) {
			FileInfo fileInfo = fileInfoDao.getFileInfo(csvFile.getName(), year, month, provider);
			
			if(fileInfo != null) {
				if(lasModifiationTime.after(fileInfo.getLastAccessed())) {
					instClaimDetailDao.deleteOldRecords(year, month, provider);
					saveInstClaimDetailData(csvFile,year,month,provider);
					fileInfo.setLastAccessed(new Date());
					fileInfoDao.save(fileInfo);
				}
			} else {
				saveInstClaimDetailData(csvFile,year,month,provider);
				FileInfo fileInfoObj = new FileInfo();
				fileInfoObj.setFileName(csvFile.getName());
				fileInfoObj.setYear(year);
				fileInfoObj.setMonth(month);
				fileInfoObj.setProvider(provider);
				fileInfoObj.setLastAccessed(new Date());
				fileInfoDao.save(fileInfoObj);
			}
		}
	}
	
	public void saveInstClaimDetailData(File csvFile, String year, String month, String provider) {
		
		String logfileName = "instClaimDetailLog.txt";
		
		CsvToBean<InstClaimDetail> csvToBean = null;
		
		try {
			csvToBean = new CsvToBeanBuilder<InstClaimDetail>(getReaderObject(csvFile))
	                .withType(InstClaimDetail.class)
	                .withIgnoreLeadingWhiteSpace(true)
	                .build();
		} catch (Exception e){
			e.printStackTrace();
			// Error in reading data from csv
			writeToLogFile(0, 0, 0, logfileName, provider, year, month, e.getMessage());
			return;
		}
		
		Iterator<InstClaimDetail> csvUserIterator = csvToBean.iterator();
		int exceptionCount = 0,totalCount = 0;
		
		while (csvUserIterator.hasNext()) {
			totalCount++;
			InstClaimDetail instClaimDetail= csvUserIterator.next();
			instClaimDetail.setYear(year);
			instClaimDetail.setMonth(month);
			instClaimDetail.setProvider(provider);
			try {
				instClaimDetailDao.save(instClaimDetail);
			} catch(Exception e) {
				// write missed record to logfile
				e.printStackTrace();
				writeMissedRecordToLogfile(instClaimDetail, logfileName, provider, e.getMessage());
				exceptionCount++;
			}
		}
		
		// write summary of number of records written to logfile
		writeToLogFile(exceptionCount, totalCount, totalCount-exceptionCount, logfileName, provider, year, month, "none");
	}
	
	//3
	@Override
	public void savePremiumDetail(File csvFile, String year, String month, String provider, Date lasModifiationTime) {
		if(csvFile.exists() && !csvFile.isDirectory()) {
			FileInfo fileInfo = fileInfoDao.getFileInfo(csvFile.getName(), year, month, provider);
			
			if(fileInfo != null) {
				if(lasModifiationTime.after(fileInfo.getLastAccessed())) {
					premiumDetailDao.deleteOldRecords(year, month, provider);
					savePremiumDetailData(csvFile,year,month,provider);
					fileInfo.setLastAccessed(new Date());
					fileInfoDao.save(fileInfo);
				}
			} else {
				savePremiumDetailData(csvFile,year,month,provider);
				FileInfo fileInfoObj = new FileInfo();
				fileInfoObj.setFileName(csvFile.getName());
				fileInfoObj.setYear(year);
				fileInfoObj.setMonth(month);
				fileInfoObj.setProvider(provider);
				fileInfoObj.setLastAccessed(new Date());
				fileInfoDao.save(fileInfoObj);
			}
		}
	}

	public void savePremiumDetailData(File csvFile, String year, String month, String provider) {
		String logfileName = "premiumDetailLog.txt";
		
		CsvToBean<PremiumDetail> csvToBean = null;
		
		try {
			csvToBean = new CsvToBeanBuilder<PremiumDetail>(getReaderObject(csvFile))
	                .withType(PremiumDetail.class)
	                .withIgnoreLeadingWhiteSpace(true)
	                .build();
		} catch (Exception e){
			e.printStackTrace();
			// Error in reading data from csv
			writeToLogFile(0, 0, 0, logfileName, provider, year, month, e.getMessage());
			return;
		}
		
		Iterator<PremiumDetail> csvUserIterator = csvToBean.iterator();
		int exceptionCount = 0,totalCount = 0;
		
		while (csvUserIterator.hasNext()) {
			totalCount++;
			PremiumDetail premiumDetail= csvUserIterator.next();
			premiumDetail.setYear(year);
			premiumDetail.setMonth(month);
			premiumDetail.setProvider(provider);
			try {
				premiumDetailDao.save(premiumDetail);
			} catch(Exception e) {
				// write missed record to logfile
				e.printStackTrace();
				writeMissedRecordToLogfile(premiumDetail, logfileName, provider, e.getMessage());
				exceptionCount++;
			}
		}
		
		// write summary of number of records written to logfile
		writeToLogFile(exceptionCount, totalCount, totalCount-exceptionCount, logfileName, provider, year, month, "none");
	}
	
	//4
	@Override
	public void saveProfClaimDetail(File csvFile, String year, String month, String provider, Date lasModifiationTime) {
		if(csvFile.exists() && !csvFile.isDirectory()) {
			FileInfo fileInfo = fileInfoDao.getFileInfo(csvFile.getName(), year, month, provider);
			
			if(fileInfo != null) {
				if(lasModifiationTime.after(fileInfo.getLastAccessed())) {
					profClaimDetailDao.deleteOldRecords(year, month, provider);
					saveProfClaimDetailData(csvFile,year,month,provider);
					fileInfo.setLastAccessed(new Date());
					fileInfoDao.save(fileInfo);
				}
			} else {
				saveProfClaimDetailData(csvFile,year,month,provider);
				FileInfo fileInfoObj = new FileInfo();
				fileInfoObj.setFileName(csvFile.getName());
				fileInfoObj.setYear(year);
				fileInfoObj.setMonth(month);
				fileInfoObj.setProvider(provider);
				fileInfoObj.setLastAccessed(new Date());
				fileInfoDao.save(fileInfoObj);
			}
		}
	}
	
	public void saveProfClaimDetailData(File csvFile, String year, String month, String provider) {
		
		String logfileName = "profClaimDetailLog.txt";
		
		CsvToBean<ProfClaimDetail> csvToBean = null;
		
		try {
			csvToBean = new CsvToBeanBuilder<ProfClaimDetail>(getReaderObject(csvFile))
	                .withType(ProfClaimDetail.class)
	                .withIgnoreLeadingWhiteSpace(true)
	                .build();
		} catch (Exception e){
			e.printStackTrace();
			// Error in reading data from csv
			writeToLogFile(0, 0, 0, logfileName, provider, year, month, e.getMessage());
			return;
		}
		
		Iterator<ProfClaimDetail> csvUserIterator = csvToBean.iterator();
		int exceptionCount = 0,totalCount = 0;
		
		while (csvUserIterator.hasNext()) {
			totalCount++;
			ProfClaimDetail profClaimDetail= csvUserIterator.next();
			profClaimDetail.setYear(year);
			profClaimDetail.setMonth(month);
			profClaimDetail.setProvider(provider);
			try {
				profClaimDetailDao.save(profClaimDetail);
			} catch(Exception e) {
				// write missed record to logfile
				e.printStackTrace();
				writeMissedRecordToLogfile(profClaimDetail, logfileName, provider, e.getMessage());
				exceptionCount++;
			}
		}
		
		// write summary of number of records written to logfile
		writeToLogFile(exceptionCount, totalCount, totalCount-exceptionCount, logfileName, provider, year, month, "none");
	}
	
	//5
	public void saveRxDetail(File csvFile, String year, String month, String provider, Date lasModifiationTime) {
		if(csvFile.exists() && !csvFile.isDirectory()) {
			FileInfo fileInfo = fileInfoDao.getFileInfo(csvFile.getName(), year, month, provider);
			
			if(fileInfo != null) {
				if(lasModifiationTime.after(fileInfo.getLastAccessed())) {
					rxDetailDao.deleteOldRecords(year, month, provider);
					saveRxDetailData(csvFile,year,month,provider);
					fileInfo.setLastAccessed(new Date());
					fileInfoDao.save(fileInfo);
				}
			} else {
				saveRxDetailData(csvFile,year,month,provider);
				FileInfo fileInfoObj = new FileInfo();
				fileInfoObj.setFileName(csvFile.getName());
				fileInfoObj.setYear(year);
				fileInfoObj.setMonth(month);
				fileInfoObj.setProvider(provider);
				fileInfoObj.setLastAccessed(new Date());
				fileInfoDao.save(fileInfoObj);
			}
		}
	}
	
	public void saveRxDetailData(File csvFile, String year, String month, String provider) {
		String logfileName = "rxDetailLog.txt";
		
		CsvToBean<RxDetail> csvToBean = null;
		
		try {
			csvToBean = new CsvToBeanBuilder<RxDetail>(getReaderObject(csvFile))
	                .withType(RxDetail.class)
	                .withIgnoreLeadingWhiteSpace(true)
	                .build();
		} catch (Exception e){
			e.printStackTrace();
			// Error in reading data from csv
			writeToLogFile(0, 0, 0, logfileName, provider, year, month, e.getMessage());
			return;
		}
		
		Iterator<RxDetail> csvUserIterator = csvToBean.iterator();
		int exceptionCount = 0,totalCount = 0;
		
		while (csvUserIterator.hasNext()) {
			totalCount++;
			RxDetail rxDetail= csvUserIterator.next();
			rxDetail.setYear(year);
			rxDetail.setMonth(month);
			rxDetail.setProvider(provider);
			try {
				rxDetailDao.save(rxDetail);
			} catch(Exception e) {
				// write missed record to logfile
				e.printStackTrace();
				writeMissedRecordToLogfile(rxDetail, logfileName, provider, e.getMessage());
				exceptionCount++;
			}
		}
		
		// write summary of number of records written to logfile
		writeToLogFile(exceptionCount, totalCount, totalCount-exceptionCount, logfileName, provider, year, month, "none");
	}
	
	//6
	public void saveSpecialtyClaimDetail(File csvFile, String year, String month, String provider, Date lasModifiationTime) {
		if(csvFile.exists() && !csvFile.isDirectory()) {
			FileInfo fileInfo = fileInfoDao.getFileInfo(csvFile.getName(), year, month, provider);
			
			if(fileInfo != null) {
				if(lasModifiationTime.after(fileInfo.getLastAccessed())) {
					specialtyClaimDetailDao.deleteOldRecords(year, month, provider);
					saveSpecialtyClaimDetailData(csvFile,year,month,provider);
					fileInfo.setLastAccessed(new Date());
					fileInfoDao.save(fileInfo);
				}
			} else {
				saveSpecialtyClaimDetailData(csvFile,year,month,provider);
				FileInfo fileInfoObj = new FileInfo();
				fileInfoObj.setFileName(csvFile.getName());
				fileInfoObj.setYear(year);
				fileInfoObj.setMonth(month);
				fileInfoObj.setProvider(provider);
				fileInfoObj.setLastAccessed(new Date());
				fileInfoDao.save(fileInfoObj);
			}
		}
	}
	
	public void saveSpecialtyClaimDetailData(File csvFile, String year, String month, String provider) {
		
		String logfileName = "speciltyClaimDetailLog.txt";
		
		CsvToBean<SpecialtyClaimDetail> csvToBean = null;
		
		try {
			csvToBean = new CsvToBeanBuilder<SpecialtyClaimDetail>(getReaderObject(csvFile))
	                .withType(SpecialtyClaimDetail.class)
	                .withIgnoreLeadingWhiteSpace(true)
	                .build();
		} catch (Exception e){
			e.printStackTrace();
			// Error in reading data from csv
			writeToLogFile(0, 0, 0, logfileName, provider, year, month, e.getMessage());
			return;
		}
		
		Iterator<SpecialtyClaimDetail> csvUserIterator = csvToBean.iterator();
		int exceptionCount = 0,totalCount = 0;
		
		while (csvUserIterator.hasNext()) {
			totalCount++;
			SpecialtyClaimDetail speciltyClaimDetail= csvUserIterator.next();
			speciltyClaimDetail.setYear(year);
			speciltyClaimDetail.setMonth(month);
			speciltyClaimDetail.setProvider(provider);
			try {
				specialtyClaimDetailDao.save(speciltyClaimDetail);
			} catch(Exception e) {
				// write missed record to logfile
				e.printStackTrace();
				writeMissedRecordToLogfile(speciltyClaimDetail, logfileName, provider, e.getMessage());
				exceptionCount++;
			}
		}
		
		// write summary of number of records written to logfile
		writeToLogFile(exceptionCount, totalCount, totalCount-exceptionCount, logfileName, provider, year, month, "none");
		
	}
	
	//7
	public void saveStoplossDetail(File csvFile, String year, String month, String provider, Date lasModifiationTime) {
		if(csvFile.exists() && !csvFile.isDirectory()) {
			FileInfo fileInfo = fileInfoDao.getFileInfo(csvFile.getName(), year, month, provider);
			
			if(fileInfo != null) {
				if(lasModifiationTime.after(fileInfo.getLastAccessed())) {
					stoplossDetailDao.deleteOldRecords(year, month, provider);
					saveStoplossDetailData(csvFile,year,month,provider);
					fileInfo.setLastAccessed(new Date());
					fileInfoDao.save(fileInfo);
				}
			} else {
				saveStoplossDetailData(csvFile,year,month,provider);
				FileInfo fileInfoObj = new FileInfo();
				fileInfoObj.setFileName(csvFile.getName());
				fileInfoObj.setYear(year);
				fileInfoObj.setMonth(month);
				fileInfoObj.setProvider(provider);
				fileInfoObj.setLastAccessed(new Date());
				fileInfoDao.save(fileInfoObj);
			}
		}
	}
	
	public void saveStoplossDetailData(File csvFile, String year, String month, String provider) {
		
		String logfileName = "stoplossDetailLog.txt";
		
		CsvToBean<StoplossDetail> csvToBean = null;
		
		try {
			csvToBean = new CsvToBeanBuilder<StoplossDetail>(getReaderObject(csvFile))
	                .withType(StoplossDetail.class)
	                .withIgnoreLeadingWhiteSpace(true)
	                .build();
		} catch (Exception e){
			e.printStackTrace();
			// Error in reading data from csv
			writeToLogFile(0, 0, 0, logfileName, provider, year, month, e.getMessage());
			return;
		}
		
		Iterator<StoplossDetail> csvUserIterator = csvToBean.iterator();
		int exceptionCount = 0,totalCount = 0;
		
		while (csvUserIterator.hasNext()) {
			totalCount++;
			StoplossDetail stoplossDetail= csvUserIterator.next();
			stoplossDetail.setYear(year);
			stoplossDetail.setMonth(month);
			stoplossDetail.setProvider(provider);
			try {
				stoplossDetailDao.save(stoplossDetail);
			} catch(Exception e) {
				// write missed record to logfile
				e.printStackTrace();
				writeMissedRecordToLogfile(stoplossDetail, logfileName, provider, e.getMessage());
				exceptionCount++;
			}
		}
		
		// write summary of number of records written to logfile
		writeToLogFile(exceptionCount, totalCount, totalCount-exceptionCount, logfileName, provider, year, month, "none");	
	}
	*/
	
}
