package com.mnt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mnt.service.SettingsService;

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
    public String getAllPlanAndPCP() {
		return settingsService.getMaintenanceMode();
    }
	
	@RequestMapping(value="/deleteAllData",method = RequestMethod.POST)
	@ResponseBody
    public void deleteAllData() {
		settingsService.deleteAllData();
    }
	
	@RequestMapping(value="/uploadData",method = RequestMethod.POST)
	@ResponseBody
    public void uploadData() {
		//settingsService.uploadData();
    }
	
}
