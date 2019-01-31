package com.mnt.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "claim_details_stored_results")
public class ClaimDetailsStoredResults extends BaseDomain {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "provider")
	private String provider;
	
	@Column(name = "year")
	private String year;
	
	@Column(name = "query_result")
	private Double queryResult;

	@Column(name = "claim_type")
	private String claimType;
	
	@Column(name = "option_name")
	private String optionName;

	@Column(name = "location_name")
	private String locationName;
	
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

	public String getClaimType() {
		return claimType;
	}

	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}

	public Double getQueryResult() {
		return queryResult;
	}

	public void setQueryResult(Double queryResult) {
		this.queryResult = queryResult;
	}

	public String getOptionName() {
		return optionName;
	}

	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	
}
