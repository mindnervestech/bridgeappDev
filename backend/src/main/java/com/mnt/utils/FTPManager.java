package com.mnt.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// A wrapper class around apache's FTPClient class for 
// convenience of use specially to
// disconnect and reconnect back to the last working directory
// Caution:
// All paths passed to this should never contain a trailing or starting slash.
public class FTPManager {
	String server;
	String username;
	String password;
	String currentWorkingDirectory;
	FTPClient ftp;
	
	public FTPManager(String server, String username, String password) throws Exception {
		
		this.server = server;
		this.username = username;
		this.password = password;
		this.currentWorkingDirectory = "";
		
		this.ftp = new FTPClient();
		this.ftp.connect(server);
		
		int reply = ftp.getReplyCode();
		
		if(!FTPReply.isPositiveCompletion(reply)) {
			ftp.disconnect();
			throw new Exception("Exception in connecting to FTP Server. Server refused connection.");
		}
		
		ftp.login(username, password);
		
		// enter passive mode
		ftp.enterLocalPassiveMode();
	}
	
	public void disconnect() {
		if (this.ftp.isConnected()) {
			try {
				this.ftp.logout();
				this.ftp.disconnect();
			} catch (IOException f) {
				// do nothing as all our jobs are done.
			}
		}
	}
	
	// make sure the argument given to this function does not end in "/"
	public void changeWorkingDirectory(String pathname) throws Exception {
		if(this.ftp.changeWorkingDirectory(pathname)) {
			this.currentWorkingDirectory = this.currentWorkingDirectory + pathname + "/";
		}
		else
			throw new Exception("Cannot change working directory.");
	}
	
	// goes to the parent directory
	public void changeToParentDirectory() throws Exception {
		if(this.ftp.changeToParentDirectory()) {
			// remove the last directory from path
			int indexOfSecondLastBackslash = this.currentWorkingDirectory.lastIndexOf("/", this.currentWorkingDirectory.lastIndexOf("/")-1);
			this.currentWorkingDirectory =  this.currentWorkingDirectory.substring(0, indexOfSecondLastBackslash + 1);
			
		}
		else
			throw new Exception("Cannot change to parent directory.");
	}
	
	// reconnect to Last Working Directory
	public void reconnectToPreviousWorkingDirectory() throws Exception {
		this.ftp.connect(server);
		
		int reply = ftp.getReplyCode();
		
		if(!FTPReply.isPositiveCompletion(reply)) {
			ftp.disconnect();
			throw new Exception("Exception in connecting to FTP Server");
		}
		
		ftp.login(username, password);
		ftp.enterLocalPassiveMode();
		
		
		//changeWorkingDirectory(this.currentWorkingDirectory);
		if(!this.ftp.changeWorkingDirectory(this.currentWorkingDirectory)) {
			throw new Exception("Eception in changing to last working directory.");
		}
	}
	
	public FTPFile[] listDirectories() throws IOException {
		return this.ftp.listDirectories();
	}
	
	public FTPFile[] listFiles( ) throws IOException {
		return this.ftp.listFiles();
	}
	
	public Date getModificationTime(String fileName) throws IOException {
		String fileLastModifiedTimeString = ftp.getModificationTime(fileName);
		
		// convert lastModified time from yyyyMMddHHmmss to Date
		Date fileLastModifiedTime;
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			fileLastModifiedTime = df.parse(fileLastModifiedTimeString);
			System.out.println("\t\t\t" + fileLastModifiedTime.toString());
	    } catch (ParseException e) {
	        throw new RuntimeException("Failed to parse date: ", e);
	    }
		
		return fileLastModifiedTime;
	}
	
	public void retrieveFile(String fileName, OutputStream outputStream) throws IOException {
		this.ftp.retrieveFile(fileName, outputStream);
	}
	
	public boolean appendFile(String remoteFileName, InputStream localInputStream) throws IOException {
		return this.ftp.appendFile(remoteFileName, localInputStream);
	}
	
}
