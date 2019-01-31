package com.mnt.controller;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mnt.service.PermissionService;
import com.mnt.service.SaveFileService;
import com.mnt.service.UserService;
import com.mnt.vm.GroupVM;
import com.mnt.vm.MappingVM;
import com.mnt.vm.UserVM;

@RestController
public class HomeController {

	@Autowired
	SaveFileService saveFileService;
	
	@Autowired
	PermissionService permissionService;
	
	@RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot! It's working";
    }
	
	@RequestMapping(value="/savemapping",method = RequestMethod.POST)
	@ResponseBody
    public void saveMapping(@RequestParam(value="provider") String provider,
    		@RequestParam(value="filename") String filename, @RequestParam(value="mapping") String mappingJSON) {
		saveFileService.saveMapping(provider, filename, mappingJSON);
    }
	
	@RequestMapping(value="/testing",method = RequestMethod.GET)
	@ResponseBody
    public void test() {
		//saveFileService.insertIntoTable("stoploss_detail", new File("/home/anmol/csvreader_files/OPT_test/OPTAEGC_STOPLOSS_DETAIL.txt"), "2018", "june","OPT");
		//saveFileService.deleteFromTable("stoploss_detail", "2018", "june", "OPT");
		saveFileService.saveFileDetail("specialty_claim_detail", new File("/home/anmol/csvreader_files/OPT_test/OPTAEGC_SPECIALTY_CLAIM_DETAIL.txt"), "2018", "june", "OPT", new Date());
	}
	
	@RequestMapping(value="/getProviders",method = RequestMethod.GET)
	@ResponseBody
    public Map<String,List<String>> getProviders() {
        return saveFileService.getProviders();
    }
	
	@RequestMapping(value="/getProvidersFTP",method = RequestMethod.GET)
	@ResponseBody
    public Map<String,List<String>> getProvidersFTP() {
        return saveFileService.getProvidersFTP();
    }
	
	@RequestMapping(value="/getAllPermissions",method = RequestMethod.GET)
	@ResponseBody
    public Map<String,List<String>> getAllPermissions() {
		return permissionService.getAllPermissions();
    }
	
	@RequestMapping(value="/getAllGroups",method = RequestMethod.GET)
	@ResponseBody
    public List<GroupVM> getAllGroups() {
		return permissionService.getAllGroups();
    }
	
	@RequestMapping(value="/getGroupById/{id}",method = RequestMethod.GET)
	@ResponseBody
    public GroupVM getGroupById(@PathVariable("id") Long id) {
		return permissionService.getGroupById(id);
    }
	
	@RequestMapping(value="/saveGroupPermissions",method = RequestMethod.POST)
	@ResponseBody
    public void saveGroupPermissions(@RequestParam(value="groupName") String groupName,
    		@RequestParam(value="groupDescription") String groupDescription, 
    		@RequestParam(value="permissionArray") List<String> permissionArray) {
		permissionService.saveGroupPermissions(groupName, groupDescription, permissionArray);
    }
	
	@RequestMapping(value="/updateGroupPermissions/{id}",method = RequestMethod.POST)
	@ResponseBody
    public void updateGroupPermissions(@PathVariable("id") Long id, 
    		@RequestParam(value="groupName") String groupName,
    		@RequestParam(value="groupDescription") String groupDescription, 
    		@RequestParam(value="permissionArray") List<String> permissionArray) {
		permissionService.updateGroupById(id, groupName, groupDescription, permissionArray);
    }
	
	@RequestMapping(value="/getMappingData",method = RequestMethod.GET)
	@ResponseBody
    public MappingVM getMappingData(@RequestParam(value="provider") String provider,
    		@RequestParam(value="filename") String filename) {
        return saveFileService.getMappingData(provider, filename);
    }
	
	/*
	@RequestMapping(value="/saveDemographicDetails",method = RequestMethod.POST)
	@ResponseBody
    public String saveDemographicDetails() {
		//saveFileService.saveFileDetail("demographic_detail",fileHandle, yearDir.getName(), monthDir.getName(), providerDir.getName(), fileLastModifiedTime);
        return "success";
    }
	
	@RequestMapping(value="/saveInstClaimDetails",method = RequestMethod.POST)
	@ResponseBody
    public String saveInstClaimDetails() {
		//saveFileService.saveFileDetail("inst_claim_detail",fileHandle, yearDir.getName(), monthDir.getName(), providerDir.getName(), fileLastModifiedTime);
        return "success";
    }
	
	@RequestMapping(value="/savePremiumDetails",method = RequestMethod.POST)
	@ResponseBody
    public String savePremiumDetails() {
		//saveFileService.saveFileDetail("premium_detail", fileHandle, yearDir.getName(), monthDir.getName(), providerDir.getName(), fileLastModifiedTime);
        return "success";
    }
	
	@RequestMapping(value="/saveProfClaimDetails",method = RequestMethod.POST)
	@ResponseBody
    public String saveProfClaimDetails() {
		//saveFileService.saveFileDetail("prof_claim_detail", fileHandle, yearDir.getName(), monthDir.getName(), providerDir.getName(), fileLastModifiedTime);
        return "success";
    }
	
	@RequestMapping(value="/saveRxDetails",method = RequestMethod.POST)
	@ResponseBody
    public String saveRxDetails() {
		//saveFileService.saveFileDetail("rx_detail", fileHandle, yearDir.getName(), monthDir.getName(), providerDir.getName(), fileLastModifiedTime);
        return "success";
    }
	
	@RequestMapping(value="/saveSpecialtyClaimDetails",method = RequestMethod.POST)
	@ResponseBody
    public String saveSpecialtyClaimDetails() {
		//saveFileService.saveFileDetail("specialty_claim_detail", fileHandle, yearDir.getName(), monthDir.getName(), providerDir.getName(), fileLastModifiedTime);
        return "success";
    }
	
	@RequestMapping(value="/saveStoplossDetails",method = RequestMethod.POST)
	@ResponseBody
    public String saveStoplossDetails() {
		//saveFileService.saveFileDetail("stoploss_detail",fileHandle, yearDir.getName(), monthDir.getName(), providerDir.getName(), fileLastModifiedTime);
        return "success";
    }
	*/
}
