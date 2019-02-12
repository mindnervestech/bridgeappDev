package com.mnt.dao;

import java.util.List;

import com.mnt.domain.SettingsTable;

public interface SettingsTableDao extends BaseDao<SettingsTable> {

	public void setMaintenanceMode(String mode);
	public void setReinsuranceThreshold(Integer reinsuranceThreshold);
	public void setReinsuranceCostThreshold(Integer reinsuranceCostThreshold);
	public List<Object[]> getMaintenanceMode();
	public void deleteAllData();
	public void uploadData();
}
