package com.mnt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mnt.service.SettingsService;
import com.mnt.vm.SettingsVM;

@RestController
public class SettingsController {

	@Autowired
	SettingsService settingsService;
	
	@RequestMapping(value="/setMaintenanceMode",method = RequestMethod.POST)
	@ResponseBody
    public void saveMapping(@RequestParam(value="mode") String mode) {
		settingsService.setMaintenanceMode(mode);
    }
	
	@RequestMapping(value="/getMaintenanceMode",method = RequestMethod.GET)
	@ResponseBody
    public SettingsVM getAllPlanAndPCP() {
		return settingsService.getMaintenanceMode();
    }
	
	@RequestMapping(value="/setReinsuranceThreshold",method = RequestMethod.POST)
	@ResponseBody
    public void setReinsuranceThreshold(@RequestParam(value="reinsuranceThreshold") Integer reinsuranceThreshold) {
		settingsService.setReinsuranceThreshold(reinsuranceThreshold);
    }
	
	@RequestMapping(value="/setReinsuranceCostThreshold",method = RequestMethod.POST)
	@ResponseBody
    public void setReinsuranceCostThreshold(@RequestParam(value="reinsuranceCostThreshold") Integer reinsuranceCostThreshold) {
		settingsService.setReinsuranceCostThreshold(reinsuranceCostThreshold);
    }
	
	@RequestMapping(value="/deleteAllData",method = RequestMethod.POST)
	@ResponseBody
    public void deleteAllData() {
		settingsService.deleteAllData();
    }
	
	@RequestMapping(value="/uploadData",method = RequestMethod.POST)
	@ResponseBody
    public void uploadData() {
		settingsService.uploadData();
    }
	
}
