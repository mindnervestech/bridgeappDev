package com.mnt.vm;

public class SettingsVM {

	public String maintenanceMode;
	public int reinsuranceThreshold;
	public int reinsuranceCostThreshold;
	
	public int getReinsuranceThreshold() {
		return reinsuranceThreshold;
	}
	public void setReinsuranceThreshold(int reinsuranceThreshold) {
		this.reinsuranceThreshold = reinsuranceThreshold;
	}
	public int getReinsuranceCostThreshold() {
		return reinsuranceCostThreshold;
	}
	public void setReinsuranceCostThreshold(int reinsuranceCostThreshold) {
		this.reinsuranceCostThreshold = reinsuranceCostThreshold;
	}
	public String getMaintenanceMode() {
		return maintenanceMode;
	}
	public void setMaintenanceMode(String maintenanceMode) {
		this.maintenanceMode = maintenanceMode;
	}
}
