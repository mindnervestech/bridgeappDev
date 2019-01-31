package com.mnt.service;

public interface SettingsService {

	public void setMaintenanceMode(String mode);
	public String getMaintenanceMode();
	public void deleteAllData();
	public void uploadData();
}
