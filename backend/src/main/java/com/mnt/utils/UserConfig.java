package com.mnt.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserConfig {
	
	// FTP Connection settings
	public static String server;
	public static String username;
	public static String password;
	
	@Value("${user.config.ftp.server.ip}")
    public void setServer(String userConfigServername) {
        server = userConfigServername;
    }
	
	@Value("${user.config.ftp.username}")
	public void setUsername(String userConfigUsername) {
		username = userConfigUsername;
	}
	
	@Value("${user.config.ftp.password}")
	public void setPassword(String userConfigPassword) {
		password = userConfigPassword;
	}
	
	// Please define csvBasePath with respect to login directory of the user
	// csvBase Path is the the path where csv files are stored
	// structure of csv files should be as follows
	// csvBasePath
	//  \--- Year Directories
	//        \--- Month Directories
	//               \--- Provider Directories
	//                       \--- Provider Files
	// Do not put a slash in front of the path
	// DO *NOT* put a trailing slash in the path
	public static String csvBasePath;
	@Value("${user.config.ftp.csv-dir}")
	public void setCsvBasePath(String userConfigCsvBasePath) {
		csvBasePath = userConfigCsvBasePath;
	}
	
	// Please define ftpProviderHeaderDir with respect to login directory of the user
	// The location where files with table headers of providers will be stored
	// For mapping - i.e using the front-end this will be read
	// DO *NOT* put a trailing slash  in the path
	public static String ftpProviderHeaderDir;
	@Value("${user.config.ftp.provider-header-dir}")
	public void setFtpProviderHeaderDir(String userConfigFtpProviderHeaderDir) {
		ftpProviderHeaderDir = userConfigFtpProviderHeaderDir;
	}
	
	// Please define logFileBasePath with respect to login directory of the user
	// logFileBasePath Path is the the path where logs are stored on the server
	// Do not put a slash in front of the path
	// DO *NOT* put a trailing slash in the path
	public static String logFileBasePath;
	@Value("${user.config.ftp.log-dir}")
	public void setLogFileBasePath(String userConfigLogFileBasePath) {
		logFileBasePath = userConfigLogFileBasePath;
	}
	
	// The location where files downloaded from server will be stored temporarily locally 
	// For processing - i.e. storing files into the database
	// DO *NOT* put a trailing slash  in the path
	public static String localTempfileBaseDir;
	@Value("${user.config.local.temp-dir}")
	public void setLocalTempFileBaseDir(String userConfigLocalTempFileBaseDir) {
		localTempfileBaseDir = userConfigLocalTempFileBaseDir;
	}
	
	// The location where files with table headers of providers will be stored
	// For mapping - i.e using the front-end this will be read
	// DO *NOT* put a trailing slash  in the path
	// IN FUTURE: need to put on FTP
	public static String localProviderHeaderDir = "/home/anmol/Testing/Providers";
}
