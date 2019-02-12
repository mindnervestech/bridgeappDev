package com.mnt.service;

import com.mnt.vm.SettingsVM;

public interface SettingsService {

	public void setMaintenanceMode(String mode);
	public void setReinsuranceThreshold(Integer reinsuranceThreshold);
	public void setReinsuranceCostThreshold(Integer reinsuranceCostThreshold);
	public SettingsVM getMaintenanceMode();
	public void deleteAllData();
	public void uploadData();
}
