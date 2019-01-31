package com.mnt.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;

import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mnt.constants.FileConstants;
import com.mnt.service.SaveFileService;

@Component
public class Scheduler {
	
	@Autowired
	SaveFileService saveFileService;
	
	private int sessionCount = 0;
	
	// fixedDelay in milliseconds -> Delay between end of last and beginning of new task
	// initialDelay in milliseconds -> Delay before executing the task for the first time
	//@Scheduled(fixedDelayString = "${user.config.scheduler.fixedDelay.in.milliseconds}", initialDelayString="${user.config.scheduler.initialDelay.in.milliseconds}")
	public void runScheduledTask() throws Exception {
		
		System.out.println(">>>>>>>>>>>> Running scheduler session");
		
		// details to connect to server
		String server = UserConfig.server;
		String username = UserConfig.username;
		String password = UserConfig.password;
		String csvBasePath = UserConfig.csvBasePath;
		String localTempfileBaseDir = UserConfig.localTempfileBaseDir;
		
		FTPManager ftp = new FTPManager(server, username, password);
		
		//change to location of csv files
		ftp.changeWorkingDirectory(csvBasePath);
		
		
		FTPFile[] yearDirectories = ftp.listDirectories();
		for(FTPFile yearDir: yearDirectories) {
			ftp.changeWorkingDirectory(yearDir.getName());
			System.out.println("##### In year " + yearDir.getName());
			FTPFile[] monthDirectories = ftp.listDirectories();
			for(FTPFile monthDir: monthDirectories) {
				System.out.println("\t##### In month " + monthDir.getName());
				ftp.changeWorkingDirectory(monthDir.getName());
				FTPFile[] providerDirectories = ftp.listDirectories();
				for(FTPFile providerDir: providerDirectories) {
					System.out.println("\t\t##### In provider:" + providerDir.getName());
					ftp.changeWorkingDirectory(providerDir.getName());
					FTPFile[] providerFiles = ftp.listFiles();
					for(FTPFile providerFile: providerFiles) {
						
						System.out.println("\t\t\t##### File:" + providerFile.getName());
						String fileName = providerFile.getName();
						
						// if file ends with proper suffix then retrieve file from server
						if(fileName.endsWith(FileConstants.DEMO_GRAPHIC_FILE_SUFFIX) || 
							fileName.endsWith(FileConstants.INST_CLAIM_DETAIL_FILE_SUFFIX) ||
							fileName.endsWith(FileConstants.PREMIUM_DETAIL_FILE_SUFFIX)|| 
							fileName.endsWith(FileConstants.PROF_CLAIM_FILE_SUFFIX) ||
							fileName.endsWith(FileConstants.RX_FILE_SUFFIX) ||
							fileName.endsWith(FileConstants.SPECIALTY_CLAIM_FILE_SUFFIX) ||
							fileName.endsWith(FileConstants.STOPLOSS_FILE_SUFFIX)) {
							
							System.out.println("\t\t\tGetting file from server.");
							
							// get file last modified time							
							Date fileLastModifiedTime = ftp.getModificationTime(providerFile.getName());
							
							// create a temporary localfile to work with
							File fileHandle = new File(localTempfileBaseDir + "/" + providerFile.getName());
							OutputStream outputStream = new FileOutputStream(fileHandle);
							ftp.retrieveFile(providerFile.getName(), outputStream);
							outputStream.close();
							
							// disconnect from server
							System.out.println("\t\t\t>>>>>Disconnecting from server.");
							ftp.disconnect();
							
							System.out.println("\t\t\tGot file from server.");
							System.out.println("\t\t\tProcessing file.");
							
							// process the file - call service to save to db
							
							if(fileName.endsWith(FileConstants.DEMO_GRAPHIC_FILE_SUFFIX))
								saveFileService.saveFileDetail("demographic_detail",fileHandle, yearDir.getName(), monthDir.getName(), providerDir.getName(), fileLastModifiedTime);
							else if(fileName.endsWith(FileConstants.INST_CLAIM_DETAIL_FILE_SUFFIX))
								saveFileService.saveFileDetail("inst_claim_detail",fileHandle, yearDir.getName(), monthDir.getName(), providerDir.getName(), fileLastModifiedTime);
							else if(fileName.endsWith(FileConstants.PREMIUM_DETAIL_FILE_SUFFIX))
								saveFileService.saveFileDetail("premium_detail", fileHandle, yearDir.getName(), monthDir.getName(), providerDir.getName(), fileLastModifiedTime);
							else if(fileName.endsWith(FileConstants.PROF_CLAIM_FILE_SUFFIX))
								saveFileService.saveFileDetail("prof_claim_detail", fileHandle, yearDir.getName(), monthDir.getName(), providerDir.getName(), fileLastModifiedTime);
							else if(fileName.endsWith(FileConstants.RX_FILE_SUFFIX))
								saveFileService.saveFileDetail("rx_detail", fileHandle, yearDir.getName(), monthDir.getName(), providerDir.getName(), fileLastModifiedTime);
							else if(fileName.endsWith(FileConstants.SPECIALTY_CLAIM_FILE_SUFFIX))
								saveFileService.saveFileDetail("specialty_claim_detail", fileHandle, yearDir.getName(), monthDir.getName(), providerDir.getName(), fileLastModifiedTime);
							else if(fileName.endsWith(FileConstants.STOPLOSS_FILE_SUFFIX))
								saveFileService.saveFileDetail("stoploss_detail",fileHandle, yearDir.getName(), monthDir.getName(), providerDir.getName(), fileLastModifiedTime);
							
							
							// delete the temporary localfile
							fileHandle.delete();
							System.out.println("\t\t\tFinished Processing file.");
							
							// reconnect to server
							ftp.reconnectToPreviousWorkingDirectory();
							System.out.println("\t\t\t>>>>>Reconnected to server.");
							
						}						
					}
					// go back to providerDir
					ftp.changeToParentDirectory();
				}
				// go back to monthDir
				ftp.changeToParentDirectory();
			}
			// go back to yearDir
			ftp.changeToParentDirectory();
		}
		
		ftp.disconnect();
		sessionCount++;
		System.out.println(">>>>>>>>>>>> Finished scheduler session number: " + sessionCount);
		
	}
	
}
