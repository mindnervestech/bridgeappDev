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
	
	
	public String getMaintenanceMode() {
		return maintenanceMode;
	}
	public void setMaintenanceMode(String maintenanceMode) {
		this.maintenanceMode = maintenanceMode;
	}
}
