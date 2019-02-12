package com.mnt.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "settings_table")
public class SettingsTable extends BaseDomain {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "maintenance_mode")
	private String maintenanceMode;
	
	@Column(name = "reinsurance_threshold")
	private Integer reinsuranceThreshold;
	
	@Column(name = "reinsurance_cost_threshold")
	private Integer reinsuranceCostThreshold;
	
	public String getMaintenanceMode() {
		return maintenanceMode;
	}
	public void setMaintenanceMode(String maintenanceMode) {
		this.maintenanceMode = maintenanceMode;
	}
	public Integer getReinsuranceThreshold() {
		return reinsuranceThreshold;
	}
	public void setReinsuranceThreshold(Integer reinsuranceThreshold) {
		this.reinsuranceThreshold = reinsuranceThreshold;
	}
	public Integer getReinsuranceCostThreshold() {
		return reinsuranceCostThreshold;
	}
	public void setReinsuranceCostThreshold(Integer reinsuranceCostThreshold) {
		this.reinsuranceCostThreshold = reinsuranceCostThreshold;
	}
}
