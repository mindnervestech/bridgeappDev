package com.mnt.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "presc_drugs_data")
public class PrescDrugsData extends BaseDomain {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "provider")
	private String provider;
	
	@Column(name = "year")
	private String year;
	
	@Column(name = "drug_names")
	private String drugNames;
	
	@Column(name = "costs")
	private String costs;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getDrugNames() {
		return drugNames;
	}

	public void setDrugNames(String drugNames) {
		this.drugNames = drugNames;
	}

	public String getCosts() {
		return costs;
	}

	public void setCosts(String costs) {
		this.costs = costs;
	}
}
