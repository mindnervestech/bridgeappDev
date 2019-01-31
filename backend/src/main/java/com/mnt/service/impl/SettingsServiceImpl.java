package com.mnt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mnt.dao.SettingsTableDao;
import com.mnt.service.SettingsService;

@Repository
public class SettingsServiceImpl implements SettingsService {

	@Autowired
	SettingsTableDao settingsTableDao;
	
	public void setMaintenanceMode(String mode) {
		settingsTableDao.setMaintenanceMode(mode);
	}
	
	public String getMaintenanceMode() {
		return settingsTableDao.getMaintenanceMode();
	}
	
	public void deleteAllData() {
		settingsTableDao.deleteAllData();
	}
	
	public void uploadData() {
		settingsTableDao.uploadData();
	}
}
