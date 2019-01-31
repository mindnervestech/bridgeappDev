package com.mnt.dao;

import com.mnt.domain.SettingsTable;

public interface SettingsTableDao extends BaseDao<SettingsTable> {

	public void setMaintenanceMode(String mode);
	public String getMaintenanceMode();
	public void deleteAllData();
	public void uploadData();
}
