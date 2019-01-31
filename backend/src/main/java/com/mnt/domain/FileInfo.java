package com.mnt.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "file_info")
public class FileInfo extends BaseDomain {

	private static final long serialVersionUID = 1L;
	@Column(name = "file_name")
	private String fileName;
	@Column(name = "year")
	private String year;
	@Column(name = "month")
	private String month;
	@Column(name = "provider")
	private String provider;
	@Column(name = "last_accessed")
	private Date lastAccessed;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public Date getLastAccessed() {
		return lastAccessed;
	}
	public void setLastAccessed(Date lastAccessed) {
		this.lastAccessed = lastAccessed;
	}
	
}
