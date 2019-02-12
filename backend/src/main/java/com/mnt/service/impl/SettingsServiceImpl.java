package com.mnt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mnt.dao.SettingsTableDao;
import com.mnt.service.SettingsService;
import com.mnt.vm.SettingsVM;

@Repository
public class SettingsServiceImpl implements SettingsService {

	@Autowired
	SettingsTableDao settingsTableDao;
	
	public void setMaintenanceMode(String mode) {
		settingsTableDao.setMaintenanceMode(mode);
	}
	
	public void setReinsuranceThreshold(Integer reinsuranceThreshold) {
		settingsTableDao.setReinsuranceThreshold(reinsuranceThreshold);
	}
	
	public void setReinsuranceCostThreshold(Integer reinsuranceCostThreshold) {
		settingsTableDao.setReinsuranceCostThreshold(reinsuranceCostThreshold);
	}
	
	public SettingsVM getMaintenanceMode() {
		List<Object[]> list = settingsTableDao.getMaintenanceMode();
		
		SettingsVM vm = new SettingsVM();
		if(!list.isEmpty()) {
			vm.setMaintenanceMode(list.get(0)[0].toString());
			vm.setReinsuranceThreshold(Integer.parseInt(list.get(0)[1].toString()));
			vm.setReinsuranceCostThreshold(Integer.parseInt(list.get(0)[2].toString()));
		}
		
		return vm;
	}
	
	public void deleteAllData() {
		settingsTableDao.deleteAllData();
	}
	
	public void uploadData() {
		settingsTableDao.uploadData();
	}
}
