package com.mnt.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "header_mapping")
public class HeaderMapping  extends BaseDomain {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "provider")
	private String provider;
	
	@Column(name = "filename")
	private String fileName;
	
	@Column(name = "mapping_json")
	private String mappingJson;

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getMappingJson() {
		return mappingJson;
	}

	public void setMappingJson(String mappingJson) {
		this.mappingJson = mappingJson;
	}
}
