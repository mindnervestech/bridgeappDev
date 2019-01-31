package com.mnt.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mnt.dao.ClaimDetailsStoredResultsDao;
import com.mnt.dao.PrescDrugsDataDao;
import com.mnt.dao.ProfClaimDetailDao;
import com.mnt.dao.RxDetailDao;
import com.mnt.domain.ClaimDetailsStoredResults;
import com.mnt.domain.PrescDrugsData;

@Component
public class StoreScheduler {

	@Autowired
	RxDetailDao rxDetailDao;
	@Autowired
	ProfClaimDetailDao profClaimDetailDao;
	@Autowired
	ClaimDetailsStoredResultsDao claimDetailsStoredResultsDao;
	@Autowired
	PrescDrugsDataDao PrescDrugsDao;
	
	//@Scheduled(fixedRate = 60000*30)
	//@Scheduled(cron="0 0 0 1 * *") //execute at 1st day of every month
	public void storeClaimDetails() {
		
		/*claimDetailsStoredResultsDao.deleteAllData();
		//PrescDrugsDao.deleteAllData();
		
		List<String> providersList = rxDetailDao.getAllPlans();
		List<String> yearsList = profClaimDetailDao.getAllYears();
		List<String> pcpList = rxDetailDao.getAllPCP();
		List<String> pcpLocationList = rxDetailDao.getAllPCPLocationCode();
		
		List<String> combinationList = new ArrayList<>();
		yearsList.add("all");
		System.out.println(providersList);
		System.out.println(yearsList);
		String profClaimQry = "select round(sum(paid_amount),2) from prof_claim_detail";
		String instClaimQry = "select round(sum(paid_amount),2) from inst_claim_detail";
		String specialtyClaimQry = "select round(sum(paid_amount),2) from specialty_claim_detail";
		String prescClaimQry = "select round(sum(paid_amount),2) from (select distinct pcp_name, paid_amount from rx_detail) A";
		
		ClaimDetailsStoredResults profObj = new ClaimDetailsStoredResults();
		profObj.setProvider("all");
		profObj.setYear("all");
		profObj.setOptionName("all");
		profObj.setLocationName("all");
		profObj.setQueryResult(profClaimDetailDao.getClaimDetails(profClaimQry));
		profObj.setClaimType("prof_claim_detail");
		claimDetailsStoredResultsDao.save(profObj);
		
		ClaimDetailsStoredResults instObj = new ClaimDetailsStoredResults();
		instObj.setProvider("all");
		instObj.setYear("all");
		instObj.setOptionName("all");
		instObj.setLocationName("all");
		instObj.setQueryResult(profClaimDetailDao.getClaimDetails(instClaimQry));
		instObj.setClaimType("inst_claim_detail");
		claimDetailsStoredResultsDao.save(instObj);
		
		ClaimDetailsStoredResults specialtyObj = new ClaimDetailsStoredResults();
		specialtyObj.setProvider("all");
		specialtyObj.setYear("all");
		specialtyObj.setOptionName("all");
		specialtyObj.setLocationName("all");
		specialtyObj.setQueryResult(profClaimDetailDao.getClaimDetails(specialtyClaimQry));
		specialtyObj.setClaimType("specialty_claim_detail");
		claimDetailsStoredResultsDao.save(specialtyObj);
		
		ClaimDetailsStoredResults prescriptionObj = new ClaimDetailsStoredResults();
		prescriptionObj.setProvider("all");
		prescriptionObj.setYear("all");
		prescriptionObj.setOptionName("all");
		prescriptionObj.setLocationName("all");
		prescriptionObj.setQueryResult(profClaimDetailDao.getClaimDetails(prescClaimQry));
		prescriptionObj.setClaimType("prescription_detail");
		claimDetailsStoredResultsDao.save(prescriptionObj);
		
		for(int i = 0;i < pcpList.size();i++) {		//for option
			profClaimQry = "select round(sum(paid_amount),2) from prof_claim_detail where pcp_name="+'\''+pcpList.get(i)+'\'';
			instClaimQry = "select round(sum(paid_amount),2) from inst_claim_detail where pcp_name="+'\''+pcpList.get(i)+'\'';
			specialtyClaimQry = "select round(sum(paid_amount),2) from specialty_claim_detail where pcp_name="+'\''+pcpList.get(i)+'\'';
			prescClaimQry = "select round(sum(paid_amount),2) from (select distinct pcp_name, paid_amount from rx_detail where pcp_name="+'\''+pcpList.get(i)+'\''+") A";
			
			ClaimDetailsStoredResults profOptionObj = new ClaimDetailsStoredResults();
			profOptionObj.setProvider("all");
			profOptionObj.setYear("all");
			profOptionObj.setOptionName(pcpList.get(i));
			profOptionObj.setLocationName("all");
			profOptionObj.setQueryResult(profClaimDetailDao.getClaimDetails(profClaimQry));
			profOptionObj.setClaimType("prof_claim_detail");
			claimDetailsStoredResultsDao.save(profOptionObj);
			
			ClaimDetailsStoredResults instOptionObj = new ClaimDetailsStoredResults();
			instOptionObj.setProvider("all");
			instOptionObj.setYear("all");
			instOptionObj.setOptionName(pcpList.get(i));
			instOptionObj.setLocationName("all");
			instOptionObj.setQueryResult(profClaimDetailDao.getClaimDetails(instClaimQry));
			instOptionObj.setClaimType("inst_claim_detail");
			claimDetailsStoredResultsDao.save(instOptionObj);
			
			ClaimDetailsStoredResults specialtyOptionObj = new ClaimDetailsStoredResults();
			specialtyOptionObj.setProvider("all");
			specialtyOptionObj.setYear("all");
			specialtyOptionObj.setOptionName(pcpList.get(i));
			specialtyOptionObj.setLocationName("all");
			specialtyOptionObj.setQueryResult(profClaimDetailDao.getClaimDetails(specialtyClaimQry));
			specialtyOptionObj.setClaimType("specialty_claim_detail");
			claimDetailsStoredResultsDao.save(specialtyOptionObj);
			
			ClaimDetailsStoredResults prescriptionOptionObj = new ClaimDetailsStoredResults();
			prescriptionOptionObj.setProvider("all");
			prescriptionOptionObj.setYear("all");
			prescriptionOptionObj.setOptionName(pcpList.get(i));
			prescriptionOptionObj.setLocationName("all");
			prescriptionOptionObj.setQueryResult(profClaimDetailDao.getClaimDetails(prescClaimQry));
			prescriptionOptionObj.setClaimType("prescription_detail");
			claimDetailsStoredResultsDao.save(prescriptionOptionObj);
		}
		
		for(int j=0;j<pcpLocationList.size();j++) {	//for location
			profClaimQry = "select round(sum(paid_amount),2) from prof_claim_detail where pcp_location_code="+'\''+pcpLocationList.get(j)+'\'';
			instClaimQry = "select round(sum(paid_amount),2) from inst_claim_detail where pcp_location_code="+'\''+pcpLocationList.get(j)+'\'';
			specialtyClaimQry = "select round(sum(paid_amount),2) from specialty_claim_detail where pcp_id="+'\''+pcpLocationList.get(j)+'\'';
			prescClaimQry = "select round(sum(paid_amount),2) from (select distinct pcp_name, paid_amount from rx_detail where pcp_location_code="+'\''+pcpLocationList.get(j)+'\''+") A";
			
			ClaimDetailsStoredResults profLocationObj = new ClaimDetailsStoredResults();
			profLocationObj.setProvider("all");
			profLocationObj.setYear("all");
			profLocationObj.setOptionName("all");
			profLocationObj.setLocationName(pcpLocationList.get(j));
			profLocationObj.setQueryResult(profClaimDetailDao.getClaimDetails(profClaimQry));
			profLocationObj.setClaimType("prof_claim_detail");
			claimDetailsStoredResultsDao.save(profLocationObj);
			
			ClaimDetailsStoredResults instLocationObj = new ClaimDetailsStoredResults();
			instLocationObj.setProvider("all");
			instLocationObj.setYear("all");
			instLocationObj.setOptionName("all");
			instLocationObj.setLocationName(pcpLocationList.get(j));
			instLocationObj.setQueryResult(profClaimDetailDao.getClaimDetails(instClaimQry));
			instLocationObj.setClaimType("inst_claim_detail");
			claimDetailsStoredResultsDao.save(instLocationObj);
			
			ClaimDetailsStoredResults specialtyLocationObj = new ClaimDetailsStoredResults();
			specialtyLocationObj.setProvider("all");
			specialtyLocationObj.setYear("all");
			specialtyLocationObj.setOptionName("all");
			specialtyLocationObj.setLocationName(pcpLocationList.get(j));
			specialtyLocationObj.setQueryResult(profClaimDetailDao.getClaimDetails(specialtyClaimQry));
			specialtyLocationObj.setClaimType("specialty_claim_detail");
			claimDetailsStoredResultsDao.save(specialtyLocationObj);
			
			ClaimDetailsStoredResults prescriptionLocationObj = new ClaimDetailsStoredResults();
			prescriptionLocationObj.setProvider("all");
			prescriptionLocationObj.setYear("all");
			prescriptionLocationObj.setOptionName("all");
			prescriptionLocationObj.setLocationName(pcpLocationList.get(j));
			prescriptionLocationObj.setQueryResult(profClaimDetailDao.getClaimDetails(prescClaimQry));
			prescriptionLocationObj.setClaimType("prescription_detail");
			claimDetailsStoredResultsDao.save(prescriptionLocationObj);
		}
		
		for(int i = 0;i < pcpList.size();i++) {			//for option and location both
			for(int j=0;j<pcpLocationList.size();j++) {
				profClaimQry = "select round(sum(paid_amount),2) from prof_claim_detail where pcp_name="+'\''+pcpList.get(i)+'\''+" and pcp_location_code="+'\''+pcpLocationList.get(j)+'\'';
				instClaimQry = "select round(sum(paid_amount),2) from inst_claim_detail where pcp_name="+'\''+pcpList.get(i)+'\''+" and pcp_location_code="+'\''+pcpLocationList.get(j)+'\'';
				specialtyClaimQry = "select round(sum(paid_amount),2) from specialty_claim_detail where pcp_name="+'\''+pcpList.get(i)+'\''+" and pcp_id="+'\''+pcpLocationList.get(j)+'\'';
				prescClaimQry = "select round(sum(paid_amount),2) from (select distinct pcp_name, paid_amount from rx_detail where pcp_name="+'\''+pcpList.get(i)+'\''+" and pcp_location_code="+'\''+pcpLocationList.get(j)+'\''+") A";
				
				ClaimDetailsStoredResults profLocationObj = new ClaimDetailsStoredResults();
				profLocationObj.setProvider("all");
				profLocationObj.setYear("all");
				profLocationObj.setOptionName(pcpList.get(i));
				profLocationObj.setLocationName(pcpLocationList.get(j));
				profLocationObj.setQueryResult(profClaimDetailDao.getClaimDetails(profClaimQry));
				profLocationObj.setClaimType("prof_claim_detail");
				claimDetailsStoredResultsDao.save(profLocationObj);
				
				ClaimDetailsStoredResults instLocationObj = new ClaimDetailsStoredResults();
				instLocationObj.setProvider("all");
				instLocationObj.setYear("all");
				instLocationObj.setOptionName(pcpList.get(i));
				instLocationObj.setLocationName(pcpLocationList.get(j));
				instLocationObj.setQueryResult(profClaimDetailDao.getClaimDetails(instClaimQry));
				instLocationObj.setClaimType("inst_claim_detail");
				claimDetailsStoredResultsDao.save(instLocationObj);
				
				ClaimDetailsStoredResults specialtyLocationObj = new ClaimDetailsStoredResults();
				specialtyLocationObj.setProvider("all");
				specialtyLocationObj.setYear("all");
				specialtyLocationObj.setOptionName(pcpList.get(i));
				specialtyLocationObj.setLocationName(pcpLocationList.get(j));
				specialtyLocationObj.setQueryResult(profClaimDetailDao.getClaimDetails(specialtyClaimQry));
				specialtyLocationObj.setClaimType("specialty_claim_detail");
				claimDetailsStoredResultsDao.save(specialtyLocationObj);
				
				ClaimDetailsStoredResults prescriptionLocationObj = new ClaimDetailsStoredResults();
				prescriptionLocationObj.setProvider("all");
				prescriptionLocationObj.setYear("all");
				prescriptionLocationObj.setOptionName(pcpList.get(i));
				prescriptionLocationObj.setLocationName(pcpLocationList.get(j));
				prescriptionLocationObj.setQueryResult(profClaimDetailDao.getClaimDetails(prescClaimQry));
				prescriptionLocationObj.setClaimType("prescription_detail");
				claimDetailsStoredResultsDao.save(prescriptionLocationObj);
			}
		}
		
		combinationList = getCombinations(providersList);
		System.out.println(combinationList);
		
		for(int optionIndex=0;optionIndex<pcpList.size();optionIndex++) {		//option combinations
		
			for(int i=0;i<combinationList.size();i++) {
				
				profClaimQry = "select round(sum(paid_amount),2) from prof_claim_detail where ";
				instClaimQry = "select round(sum(paid_amount),2) from inst_claim_detail where ";
				specialtyClaimQry = "select round(sum(paid_amount),2) from specialty_claim_detail where ";
				prescClaimQry = "select round(sum(paid_amount),2) from (select distinct pcp_name, paid_amount from rx_detail where ";
				
			for(int j=0;j<yearsList.size();j++) {
					
					profClaimQry = "select round(sum(paid_amount),2) from prof_claim_detail where ";
					instClaimQry = "select round(sum(paid_amount),2) from inst_claim_detail where ";
					specialtyClaimQry = "select round(sum(paid_amount),2) from specialty_claim_detail where ";
					prescClaimQry = "select round(sum(paid_amount),2) from (select distinct pcp_name, paid_amount from rx_detail where ";
					
					profClaimQry = profClaimQry + "pcp_name="+'\''+pcpList.get(optionIndex)+'\''+" and ";
					instClaimQry = instClaimQry + "pcp_name="+'\''+pcpList.get(optionIndex)+'\''+" and ";
					specialtyClaimQry = specialtyClaimQry + "pcp_name="+'\''+pcpList.get(optionIndex)+'\''+" and ";
					prescClaimQry = prescClaimQry + "pcp_name="+'\''+pcpList.get(optionIndex)+'\''+" and ";
					
					
					String providerArr[] = combinationList.get(i).toString().split(" ");
					String providers = "";
					for(int p=0; p<providerArr.length;p++) {
						if(p == providerArr.length-1) {
							profClaimQry = profClaimQry + "provider="+'\''+providerArr[p].trim()+'\'';
							instClaimQry = instClaimQry + "provider="+'\''+providerArr[p].trim()+'\'';
							specialtyClaimQry = specialtyClaimQry + "provider="+'\''+providerArr[p].trim()+'\'';
							prescClaimQry = prescClaimQry + "provider="+'\''+providerArr[p].trim()+'\'';
							providers += providerArr[p].trim();
						} else {
							profClaimQry = profClaimQry + "provider="+'\''+providerArr[p].trim()+'\''+" or ";
							instClaimQry = instClaimQry + "provider="+'\''+providerArr[p].trim()+'\''+" or ";
							specialtyClaimQry = specialtyClaimQry + "provider="+'\''+providerArr[p].trim()+'\''+" or ";
							prescClaimQry = prescClaimQry +  "provider="+'\''+providerArr[p].trim()+'\''+" or ";
							providers += providerArr[p].trim()+",";
						}
					}
					if(!yearsList.get(j).trim().equals("all")) {
						profClaimQry += " and first_service_date like "+'\''+yearsList.get(j).trim()+"%"+'\'';
						instClaimQry += " and first_service_date like "+'\''+yearsList.get(j).trim()+"%"+'\'';
						specialtyClaimQry += " and service_date like "+'\''+yearsList.get(j).trim()+"%"+'\'';
						prescClaimQry += " and year="+yearsList.get(j).trim();
					}
					
					prescClaimQry += ") A";
					
					System.out.println(profClaimQry);
					System.out.println(instClaimQry);
					System.out.println(specialtyClaimQry);
					System.out.println(prescClaimQry);
					System.out.println();
					
					ClaimDetailsStoredResults profClaimObj = new ClaimDetailsStoredResults();
					profClaimObj.setProvider(providers);
					profClaimObj.setYear(yearsList.get(j).trim());
					profClaimObj.setOptionName(pcpList.get(optionIndex));
					profClaimObj.setLocationName("all");
					profClaimObj.setQueryResult(profClaimDetailDao.getClaimDetails(profClaimQry));
					profClaimObj.setClaimType("prof_claim_detail");
					claimDetailsStoredResultsDao.save(profClaimObj);
					
					ClaimDetailsStoredResults instClaimObj = new ClaimDetailsStoredResults();
					instClaimObj.setProvider(providers);
					instClaimObj.setYear(yearsList.get(j).trim());
					instClaimObj.setOptionName(pcpList.get(optionIndex));
					instClaimObj.setLocationName("all");
					instClaimObj.setQueryResult(profClaimDetailDao.getClaimDetails(instClaimQry));
					instClaimObj.setClaimType("inst_claim_detail");
					claimDetailsStoredResultsDao.save(instClaimObj);
					
					ClaimDetailsStoredResults specialtyClaimObj = new ClaimDetailsStoredResults();
					specialtyClaimObj.setProvider(providers);
					specialtyClaimObj.setYear(yearsList.get(j).trim());
					specialtyClaimObj.setOptionName(pcpList.get(optionIndex));
					specialtyClaimObj.setLocationName("all");
					specialtyClaimObj.setQueryResult(profClaimDetailDao.getClaimDetails(specialtyClaimQry));
					specialtyClaimObj.setClaimType("specialty_claim_detail");
					claimDetailsStoredResultsDao.save(specialtyClaimObj);
					
					ClaimDetailsStoredResults prescClaimObj = new ClaimDetailsStoredResults();
					prescClaimObj.setProvider(providers);
					prescClaimObj.setYear(yearsList.get(j).trim());
					prescClaimObj.setOptionName(pcpList.get(optionIndex));
					prescClaimObj.setLocationName("all");
					prescClaimObj.setQueryResult(profClaimDetailDao.getClaimDetails(prescClaimQry));
					prescClaimObj.setClaimType("prescription_detail");
					claimDetailsStoredResultsDao.save(prescClaimObj);
					
				}
			}
		}
		
		for(int locationIndex=0;locationIndex<pcpLocationList.size();locationIndex++) {		//location combinations
			
			for(int i=0;i<combinationList.size();i++) {
				
				profClaimQry = "select round(sum(paid_amount),2) from prof_claim_detail where ";
				instClaimQry = "select round(sum(paid_amount),2) from inst_claim_detail where ";
				specialtyClaimQry = "select round(sum(paid_amount),2) from specialty_claim_detail where ";
				prescClaimQry = "select round(sum(paid_amount),2) from (select distinct pcp_name, paid_amount from rx_detail where ";
				
			for(int j=0;j<yearsList.size();j++) {
					
					profClaimQry = "select round(sum(paid_amount),2) from prof_claim_detail where ";
					instClaimQry = "select round(sum(paid_amount),2) from inst_claim_detail where ";
					specialtyClaimQry = "select round(sum(paid_amount),2) from specialty_claim_detail where ";
					prescClaimQry = "select round(sum(paid_amount),2) from (select distinct pcp_name, paid_amount from rx_detail where ";
					
					profClaimQry = profClaimQry + "pcp_location_code="+'\''+pcpLocationList.get(locationIndex)+'\''+" and ";
					instClaimQry = instClaimQry + "pcp_location_code="+'\''+pcpLocationList.get(locationIndex)+'\''+" and ";
					specialtyClaimQry = specialtyClaimQry + "pcp_id="+'\''+pcpLocationList.get(locationIndex)+'\''+" and ";
					prescClaimQry = prescClaimQry + "pcp_location_code="+'\''+pcpLocationList.get(locationIndex)+'\''+" and ";
					
					
					String providerArr[] = combinationList.get(i).toString().split(" ");
					String providers = "";
					for(int p=0; p<providerArr.length;p++) {
						if(p == providerArr.length-1) {
							profClaimQry = profClaimQry + "provider="+'\''+providerArr[p].trim()+'\'';
							instClaimQry = instClaimQry + "provider="+'\''+providerArr[p].trim()+'\'';
							specialtyClaimQry = specialtyClaimQry + "provider="+'\''+providerArr[p].trim()+'\'';
							prescClaimQry = prescClaimQry + "provider="+'\''+providerArr[p].trim()+'\'';
							providers += providerArr[p].trim();
						} else {
							profClaimQry = profClaimQry + "provider="+'\''+providerArr[p].trim()+'\''+" or ";
							instClaimQry = instClaimQry + "provider="+'\''+providerArr[p].trim()+'\''+" or ";
							specialtyClaimQry = specialtyClaimQry + "provider="+'\''+providerArr[p].trim()+'\''+" or ";
							prescClaimQry = prescClaimQry +  "provider="+'\''+providerArr[p].trim()+'\''+" or ";
							providers += providerArr[p].trim()+",";
						}
					}
					if(!yearsList.get(j).trim().equals("all")) {
						profClaimQry += " and first_service_date like "+'\''+yearsList.get(j).trim()+"%"+'\'';
						instClaimQry += " and first_service_date like "+'\''+yearsList.get(j).trim()+"%"+'\'';
						specialtyClaimQry += " and service_date like "+'\''+yearsList.get(j).trim()+"%"+'\'';
						prescClaimQry += " and year="+yearsList.get(j).trim();
					}
					
					prescClaimQry += ") A";
					
					System.out.println(profClaimQry);
					System.out.println(instClaimQry);
					System.out.println(specialtyClaimQry);
					System.out.println(prescClaimQry);
					System.out.println();
					
					ClaimDetailsStoredResults profClaimObj = new ClaimDetailsStoredResults();
					profClaimObj.setProvider(providers);
					profClaimObj.setYear(yearsList.get(j).trim());
					profClaimObj.setOptionName("all");
					profClaimObj.setLocationName(pcpLocationList.get(locationIndex));
					profClaimObj.setQueryResult(profClaimDetailDao.getClaimDetails(profClaimQry));
					profClaimObj.setClaimType("prof_claim_detail");
					claimDetailsStoredResultsDao.save(profClaimObj);
					
					ClaimDetailsStoredResults instClaimObj = new ClaimDetailsStoredResults();
					instClaimObj.setProvider(providers);
					instClaimObj.setYear(yearsList.get(j).trim());
					instClaimObj.setOptionName("all");
					instClaimObj.setLocationName(pcpLocationList.get(locationIndex));
					instClaimObj.setQueryResult(profClaimDetailDao.getClaimDetails(instClaimQry));
					instClaimObj.setClaimType("inst_claim_detail");
					claimDetailsStoredResultsDao.save(instClaimObj);
					
					ClaimDetailsStoredResults specialtyClaimObj = new ClaimDetailsStoredResults();
					specialtyClaimObj.setProvider(providers);
					specialtyClaimObj.setYear(yearsList.get(j).trim());
					specialtyClaimObj.setOptionName("all");
					specialtyClaimObj.setLocationName(pcpLocationList.get(locationIndex));
					specialtyClaimObj.setQueryResult(profClaimDetailDao.getClaimDetails(specialtyClaimQry));
					specialtyClaimObj.setClaimType("specialty_claim_detail");
					claimDetailsStoredResultsDao.save(specialtyClaimObj);
					
					ClaimDetailsStoredResults prescClaimObj = new ClaimDetailsStoredResults();
					prescClaimObj.setProvider(providers);
					prescClaimObj.setYear(yearsList.get(j).trim());
					prescClaimObj.setOptionName("all");
					prescClaimObj.setLocationName(pcpLocationList.get(locationIndex));
					prescClaimObj.setQueryResult(profClaimDetailDao.getClaimDetails(prescClaimQry));
					prescClaimObj.setClaimType("prescription_detail");
					claimDetailsStoredResultsDao.save(prescClaimObj);
					
				}
			}
		}
		
		for(int optionIndex=0;optionIndex<pcpList.size();optionIndex++) {	//option and location combinations
			
				for(int locationIndex=0;locationIndex<pcpLocationList.size();locationIndex++) {	
					
					for(int i=0;i<combinationList.size();i++) {
						
						profClaimQry = "select round(sum(paid_amount),2) from prof_claim_detail where ";
						instClaimQry = "select round(sum(paid_amount),2) from inst_claim_detail where ";
						specialtyClaimQry = "select round(sum(paid_amount),2) from specialty_claim_detail where ";
						prescClaimQry = "select round(sum(paid_amount),2) from (select distinct pcp_name, paid_amount from rx_detail where ";
						
					for(int j=0;j<yearsList.size();j++) {
							
							profClaimQry = "select round(sum(paid_amount),2) from prof_claim_detail where ";
							instClaimQry = "select round(sum(paid_amount),2) from inst_claim_detail where ";
							specialtyClaimQry = "select round(sum(paid_amount),2) from specialty_claim_detail where ";
							prescClaimQry = "select round(sum(paid_amount),2) from (select distinct pcp_name, paid_amount from rx_detail where ";
							
							profClaimQry = profClaimQry + "pcp_name="+'\''+pcpList.get(optionIndex)+'\''+" and pcp_location_code="+'\''+pcpLocationList.get(locationIndex)+'\''+" and ";
							instClaimQry = instClaimQry + "pcp_name="+'\''+pcpList.get(optionIndex)+'\''+" and pcp_location_code="+'\''+pcpLocationList.get(locationIndex)+'\''+" and ";
							specialtyClaimQry = specialtyClaimQry + "pcp_name="+'\''+pcpList.get(optionIndex)+'\''+" and pcp_id="+'\''+pcpLocationList.get(locationIndex)+'\''+" and ";
							prescClaimQry = prescClaimQry + "pcp_name="+'\''+pcpList.get(optionIndex)+'\''+" and pcp_location_code="+'\''+pcpLocationList.get(locationIndex)+'\''+" and ";
							
							String providerArr[] = combinationList.get(i).toString().split(" ");
							String providers = "";
							for(int p=0; p<providerArr.length;p++) {
								if(p == providerArr.length-1) {
									profClaimQry = profClaimQry + "provider="+'\''+providerArr[p].trim()+'\'';
									instClaimQry = instClaimQry + "provider="+'\''+providerArr[p].trim()+'\'';
									specialtyClaimQry = specialtyClaimQry + "provider="+'\''+providerArr[p].trim()+'\'';
									prescClaimQry = prescClaimQry + "provider="+'\''+providerArr[p].trim()+'\'';
									providers += providerArr[p].trim();
								} else {
									profClaimQry = profClaimQry + "provider="+'\''+providerArr[p].trim()+'\''+" or ";
									instClaimQry = instClaimQry + "provider="+'\''+providerArr[p].trim()+'\''+" or ";
									specialtyClaimQry = specialtyClaimQry + "provider="+'\''+providerArr[p].trim()+'\''+" or ";
									prescClaimQry = prescClaimQry +  "provider="+'\''+providerArr[p].trim()+'\''+" or ";
									providers += providerArr[p].trim()+",";
								}
							}
							if(!yearsList.get(j).trim().equals("all")) {
								profClaimQry += " and first_service_date like "+'\''+yearsList.get(j).trim()+"%"+'\'';
								instClaimQry += " and first_service_date like "+'\''+yearsList.get(j).trim()+"%"+'\'';
								specialtyClaimQry += " and service_date like "+'\''+yearsList.get(j).trim()+"%"+'\'';
								prescClaimQry += " and year="+yearsList.get(j).trim();
							}
							
							
							prescClaimQry += ") A";
							
							System.out.println(profClaimQry);
							System.out.println(instClaimQry);
							System.out.println(specialtyClaimQry);
							System.out.println(prescClaimQry);
							System.out.println();
							
							ClaimDetailsStoredResults profClaimObj = new ClaimDetailsStoredResults();
							profClaimObj.setProvider(providers);
							profClaimObj.setYear(yearsList.get(j).trim());
							profClaimObj.setOptionName(pcpList.get(optionIndex));
							profClaimObj.setLocationName(pcpLocationList.get(locationIndex));
							profClaimObj.setQueryResult(profClaimDetailDao.getClaimDetails(profClaimQry));
							profClaimObj.setClaimType("prof_claim_detail");
							claimDetailsStoredResultsDao.save(profClaimObj);
							
							ClaimDetailsStoredResults instClaimObj = new ClaimDetailsStoredResults();
							instClaimObj.setProvider(providers);
							instClaimObj.setYear(yearsList.get(j).trim());
							instClaimObj.setOptionName(pcpList.get(optionIndex));
							instClaimObj.setLocationName(pcpLocationList.get(locationIndex));
							instClaimObj.setQueryResult(profClaimDetailDao.getClaimDetails(instClaimQry));
							instClaimObj.setClaimType("inst_claim_detail");
							claimDetailsStoredResultsDao.save(instClaimObj);
							
							ClaimDetailsStoredResults specialtyClaimObj = new ClaimDetailsStoredResults();
							specialtyClaimObj.setProvider(providers);
							specialtyClaimObj.setYear(yearsList.get(j).trim());
							specialtyClaimObj.setOptionName(pcpList.get(optionIndex));
							specialtyClaimObj.setLocationName(pcpLocationList.get(locationIndex));
							specialtyClaimObj.setQueryResult(profClaimDetailDao.getClaimDetails(specialtyClaimQry));
							specialtyClaimObj.setClaimType("specialty_claim_detail");
							claimDetailsStoredResultsDao.save(specialtyClaimObj);
							
							ClaimDetailsStoredResults prescClaimObj = new ClaimDetailsStoredResults();
							prescClaimObj.setProvider(providers);
							prescClaimObj.setYear(yearsList.get(j).trim());
							prescClaimObj.setOptionName(pcpList.get(optionIndex));
							prescClaimObj.setLocationName(pcpLocationList.get(locationIndex));
							prescClaimObj.setQueryResult(profClaimDetailDao.getClaimDetails(prescClaimQry));
							prescClaimObj.setClaimType("prescription_detail");
							claimDetailsStoredResultsDao.save(prescClaimObj);
							
						}
					}
				}
			}
		
		for(int optionIndex=0;optionIndex<pcpList.size();optionIndex++) {	//for option
			
			for(int j=0;j<yearsList.size();j++) {
				if(!yearsList.get(j).trim().equals("all")) {
					profClaimQry = "select round(sum(paid_amount),2) from prof_claim_detail where pcp_name="+'\''+pcpList.get(optionIndex)+'\''+" and first_service_date like "+'\''+yearsList.get(j).trim()+"%"+'\'';
					instClaimQry = "select round(sum(paid_amount),2) from inst_claim_detail where pcp_name="+'\''+pcpList.get(optionIndex)+'\''+" and first_service_date like "+'\''+yearsList.get(j).trim()+"%"+'\'';
					specialtyClaimQry = "select round(sum(paid_amount),2) from specialty_claim_detail where pcp_name="+'\''+pcpList.get(optionIndex)+'\''+" and service_date like "+'\''+yearsList.get(j).trim()+"%"+'\'';
					prescClaimQry = "select round(sum(paid_amount),2) from (select distinct pcp_name, paid_amount from rx_detail where pcp_name="+'\''+pcpList.get(optionIndex)+'\''+" and year="+yearsList.get(j).trim()+") A";
					
					ClaimDetailsStoredResults profClaimYearObj = new ClaimDetailsStoredResults();
					profClaimYearObj.setProvider("all");
					profClaimYearObj.setYear(yearsList.get(j).trim());
					profClaimYearObj.setOptionName(pcpList.get(optionIndex));
					profClaimYearObj.setLocationName("all");
					profClaimYearObj.setQueryResult(profClaimDetailDao.getClaimDetails(profClaimQry));
					profClaimYearObj.setClaimType("prof_claim_detail");
					claimDetailsStoredResultsDao.save(profClaimYearObj);
					
					ClaimDetailsStoredResults instClaimYearObj = new ClaimDetailsStoredResults();
					instClaimYearObj.setProvider("all");
					instClaimYearObj.setYear(yearsList.get(j).trim());
					instClaimYearObj.setOptionName(pcpList.get(optionIndex));
					instClaimYearObj.setLocationName("all");
					instClaimYearObj.setQueryResult(profClaimDetailDao.getClaimDetails(instClaimQry));
					instClaimYearObj.setClaimType("inst_claim_detail");
					claimDetailsStoredResultsDao.save(instClaimYearObj);
					
					ClaimDetailsStoredResults specialtyClaimYearObj = new ClaimDetailsStoredResults();
					specialtyClaimYearObj.setProvider("all");
					specialtyClaimYearObj.setYear(yearsList.get(j).trim());
					specialtyClaimYearObj.setOptionName(pcpList.get(optionIndex));
					specialtyClaimYearObj.setLocationName("all");
					specialtyClaimYearObj.setQueryResult(profClaimDetailDao.getClaimDetails(specialtyClaimQry));
					specialtyClaimYearObj.setClaimType("specialty_claim_detail");
					claimDetailsStoredResultsDao.save(specialtyClaimYearObj);
					
					ClaimDetailsStoredResults prescClaimYearObj = new ClaimDetailsStoredResults();
					prescClaimYearObj.setProvider("all");
					prescClaimYearObj.setYear(yearsList.get(j).trim());
					prescClaimYearObj.setOptionName(pcpList.get(optionIndex));
					prescClaimYearObj.setLocationName("all");
					prescClaimYearObj.setQueryResult(profClaimDetailDao.getClaimDetails(prescClaimQry));
					prescClaimYearObj.setClaimType("prescription_detail");
					claimDetailsStoredResultsDao.save(prescClaimYearObj);
				}
			}
		}
		
		for(int locationIndex=0;locationIndex<pcpLocationList.size();locationIndex++) {			//for location
			
			for(int j=0;j<yearsList.size();j++) {
				if(!yearsList.get(j).trim().equals("all")) {
					profClaimQry = "select round(sum(paid_amount),2) from prof_claim_detail where pcp_location_code="+'\''+pcpLocationList.get(locationIndex)+'\''+" and first_service_date like "+'\''+yearsList.get(j).trim()+"%"+'\'';
					instClaimQry = "select round(sum(paid_amount),2) from inst_claim_detail where pcp_location_code="+'\''+pcpLocationList.get(locationIndex)+'\''+" and first_service_date like "+'\''+yearsList.get(j).trim()+"%"+'\'';
					specialtyClaimQry = "select round(sum(paid_amount),2) from specialty_claim_detail where pcp_id="+'\''+pcpLocationList.get(locationIndex)+'\''+" and service_date like "+'\''+yearsList.get(j).trim()+"%"+'\'';
					prescClaimQry = "select round(sum(paid_amount),2) from (select distinct pcp_name, paid_amount from rx_detail where pcp_location_code="+'\''+pcpLocationList.get(locationIndex)+'\''+" and year="+yearsList.get(j).trim()+") A";
					
					ClaimDetailsStoredResults profClaimYearObj = new ClaimDetailsStoredResults();
					profClaimYearObj.setProvider("all");
					profClaimYearObj.setYear(yearsList.get(j).trim());
					profClaimYearObj.setOptionName("all");
					profClaimYearObj.setLocationName(pcpLocationList.get(locationIndex));
					profClaimYearObj.setQueryResult(profClaimDetailDao.getClaimDetails(profClaimQry));
					profClaimYearObj.setClaimType("prof_claim_detail");
					claimDetailsStoredResultsDao.save(profClaimYearObj);
					
					ClaimDetailsStoredResults instClaimYearObj = new ClaimDetailsStoredResults();
					instClaimYearObj.setProvider("all");
					instClaimYearObj.setYear(yearsList.get(j).trim());
					instClaimYearObj.setOptionName("all");
					instClaimYearObj.setLocationName(pcpLocationList.get(locationIndex));
					instClaimYearObj.setQueryResult(profClaimDetailDao.getClaimDetails(instClaimQry));
					instClaimYearObj.setClaimType("inst_claim_detail");
					claimDetailsStoredResultsDao.save(instClaimYearObj);
					
					ClaimDetailsStoredResults specialtyClaimYearObj = new ClaimDetailsStoredResults();
					specialtyClaimYearObj.setProvider("all");
					specialtyClaimYearObj.setYear(yearsList.get(j).trim());
					specialtyClaimYearObj.setOptionName("all");
					specialtyClaimYearObj.setLocationName(pcpLocationList.get(locationIndex));
					specialtyClaimYearObj.setQueryResult(profClaimDetailDao.getClaimDetails(specialtyClaimQry));
					specialtyClaimYearObj.setClaimType("specialty_claim_detail");
					claimDetailsStoredResultsDao.save(specialtyClaimYearObj);
					
					ClaimDetailsStoredResults prescClaimYearObj = new ClaimDetailsStoredResults();
					prescClaimYearObj.setProvider("all");
					prescClaimYearObj.setYear(yearsList.get(j).trim());
					prescClaimYearObj.setOptionName("all");
					prescClaimYearObj.setLocationName(pcpLocationList.get(locationIndex));
					prescClaimYearObj.setQueryResult(profClaimDetailDao.getClaimDetails(prescClaimQry));
					prescClaimYearObj.setClaimType("prescription_detail");
					claimDetailsStoredResultsDao.save(prescClaimYearObj);
				}
			}
		}
		
		for(int optionIndex=0;optionIndex<pcpList.size();optionIndex++) {	//for option and location
			
			for(int locationIndex=0;locationIndex<pcpLocationList.size();locationIndex++) {
			
				for(int j=0;j<yearsList.size();j++) {
					if(!yearsList.get(j).trim().equals("all")) {
						profClaimQry = "select round(sum(paid_amount),2) from prof_claim_detail where pcp_name="+'\''+pcpList.get(optionIndex)+'\''+" and pcp_location_code="+'\''+pcpLocationList.get(locationIndex)+'\''+" and first_service_date like "+'\''+yearsList.get(j).trim()+"%"+'\'';
						instClaimQry = "select round(sum(paid_amount),2) from inst_claim_detail where pcp_name="+'\''+pcpList.get(optionIndex)+'\''+" and pcp_location_code="+'\''+pcpLocationList.get(locationIndex)+'\''+" and first_service_date like "+'\''+yearsList.get(j).trim()+"%"+'\'';
						specialtyClaimQry = "select round(sum(paid_amount),2) from specialty_claim_detail where pcp_name="+'\''+pcpList.get(optionIndex)+'\''+" and pcp_id="+'\''+pcpLocationList.get(locationIndex)+'\''+" and service_date like "+'\''+yearsList.get(j).trim()+"%"+'\'';
						prescClaimQry = "select round(sum(paid_amount),2) from (select distinct pcp_name, paid_amount from rx_detail where pcp_name="+'\''+pcpList.get(optionIndex)+'\''+" and pcp_location_code="+'\''+pcpLocationList.get(locationIndex)+'\''+" and year="+yearsList.get(j).trim()+") A";
						
						ClaimDetailsStoredResults profClaimYearObj = new ClaimDetailsStoredResults();
						profClaimYearObj.setProvider("all");
						profClaimYearObj.setYear(yearsList.get(j).trim());
						profClaimYearObj.setOptionName(pcpList.get(optionIndex));
						profClaimYearObj.setLocationName(pcpLocationList.get(locationIndex));
						profClaimYearObj.setQueryResult(profClaimDetailDao.getClaimDetails(profClaimQry));
						profClaimYearObj.setClaimType("prof_claim_detail");
						claimDetailsStoredResultsDao.save(profClaimYearObj);
						
						ClaimDetailsStoredResults instClaimYearObj = new ClaimDetailsStoredResults();
						instClaimYearObj.setProvider("all");
						instClaimYearObj.setYear(yearsList.get(j).trim());
						instClaimYearObj.setOptionName(pcpList.get(optionIndex));
						instClaimYearObj.setLocationName(pcpLocationList.get(locationIndex));
						instClaimYearObj.setQueryResult(profClaimDetailDao.getClaimDetails(instClaimQry));
						instClaimYearObj.setClaimType("inst_claim_detail");
						claimDetailsStoredResultsDao.save(instClaimYearObj);
						
						ClaimDetailsStoredResults specialtyClaimYearObj = new ClaimDetailsStoredResults();
						specialtyClaimYearObj.setProvider("all");
						specialtyClaimYearObj.setYear(yearsList.get(j).trim());
						specialtyClaimYearObj.setOptionName(pcpList.get(optionIndex));
						specialtyClaimYearObj.setLocationName(pcpLocationList.get(locationIndex));
						specialtyClaimYearObj.setQueryResult(profClaimDetailDao.getClaimDetails(specialtyClaimQry));
						specialtyClaimYearObj.setClaimType("specialty_claim_detail");
						claimDetailsStoredResultsDao.save(specialtyClaimYearObj);
						
						ClaimDetailsStoredResults prescClaimYearObj = new ClaimDetailsStoredResults();
						prescClaimYearObj.setProvider("all");
						prescClaimYearObj.setYear(yearsList.get(j).trim());
						prescClaimYearObj.setOptionName(pcpList.get(optionIndex));
						prescClaimYearObj.setLocationName(pcpLocationList.get(locationIndex));
						prescClaimYearObj.setQueryResult(profClaimDetailDao.getClaimDetails(prescClaimQry));
						prescClaimYearObj.setClaimType("prescription_detail");
						claimDetailsStoredResultsDao.save(prescClaimYearObj);
					}
				}
			}
		}
		
		
		storeTopPrescriptionDrugs(providersList);*/
	}
	
	public void storeTopPrescriptionDrugs(List providersList) {
		List<String> combinationList = new ArrayList<>();
		String byCostQry = "select round(sum(ingredient_cost),2) as cost,drug_name from rx_detail group by drug_name order by cost desc limit 20";
		String byVolumeCostQry = "select round(sum(metric_quantity*ingredient_cost),2) as cost,drug_name from rx_detail group by drug_name order by cost desc limit 20";
		String byVolumeQry = "select sum(metric_quantity) as cost,drug_name from rx_detail group by drug_name order by cost desc limit 20";
		
		String drugNamesAll = "";
		String costListAll = "";
		List<Object[]> byCostAllList = rxDetailDao.getPrescriptionDrugs(byCostQry);
		for(int k=0;k<byCostAllList.size();k++) {
			if(k == byCostAllList.size()-1) {
				costListAll = costListAll + byCostAllList.get(k)[0].toString();
				drugNamesAll = drugNamesAll + byCostAllList.get(k)[1].toString();
			} else {
				costListAll = costListAll + byCostAllList.get(k)[0].toString()+",";
				drugNamesAll = drugNamesAll + byCostAllList.get(k)[1].toString()+",";
			}
			
		}
		PrescDrugsData byCostObjAll = new PrescDrugsData();
		byCostObjAll.setType("cost");
		byCostObjAll.setProvider("all");
		byCostObjAll.setDrugNames(drugNamesAll);
		byCostObjAll.setCosts(costListAll);
		PrescDrugsDao.save(byCostObjAll);
		
		drugNamesAll = "";
		costListAll = "";
		List<Object[]> byVolumeCostAllList = rxDetailDao.getPrescriptionDrugs(byVolumeCostQry);
		for(int k=0;k<byVolumeCostAllList.size();k++) {
			if(k == byVolumeCostAllList.size()-1) {
				costListAll = costListAll + byVolumeCostAllList.get(k)[0].toString();
				drugNamesAll = drugNamesAll + byVolumeCostAllList.get(k)[1].toString();
			} else {
				costListAll = costListAll + byVolumeCostAllList.get(k)[0].toString()+",";
				drugNamesAll = drugNamesAll + byVolumeCostAllList.get(k)[1].toString()+",";
			}
			
		}
		PrescDrugsData byVolumeCostObjAll = new PrescDrugsData();
		byVolumeCostObjAll.setType("volume cost");
		byVolumeCostObjAll.setProvider("all");
		byVolumeCostObjAll.setDrugNames(drugNamesAll);
		byVolumeCostObjAll.setCosts(costListAll);
		PrescDrugsDao.save(byVolumeCostObjAll);
		
		List<Object[]> byVolumeAllList = rxDetailDao.getPrescriptionDrugs(byVolumeQry);
		drugNamesAll = "";
		costListAll = "";
		for(int k=0;k<byVolumeAllList.size();k++) {
			if(k == byVolumeAllList.size()-1) {
				costListAll = costListAll + byVolumeAllList.get(k)[0].toString();
				drugNamesAll = drugNamesAll + byVolumeAllList.get(k)[1].toString();
			} else {
				costListAll = costListAll + byVolumeAllList.get(k)[0].toString()+",";
				drugNamesAll = drugNamesAll + byVolumeAllList.get(k)[1].toString()+",";
			}
			
		}
		PrescDrugsData byVolumeObjAll = new PrescDrugsData();
		byVolumeObjAll.setType("volume");
		byVolumeObjAll.setProvider("all");
		byVolumeObjAll.setDrugNames(drugNamesAll);
		byVolumeObjAll.setCosts(costListAll);
		PrescDrugsDao.save(byVolumeObjAll);
			
			for(int p=0; p<providersList.size();p++) {
				
				byCostQry = "select round(sum(ingredient_cost),2) as cost,drug_name from rx_detail where provider="+'\''+providersList.get(p).toString()+'\'';
				byVolumeCostQry = "select round(sum(metric_quantity*ingredient_cost),2) as cost,drug_name from rx_detail where provider="+'\''+providersList.get(p).toString()+'\'';
				byVolumeQry = "select sum(metric_quantity) as cost,drug_name from rx_detail where provider="+'\''+providersList.get(p).toString()+'\'';
			
				byCostQry = byCostQry +" group by drug_name order by cost desc limit 20";
				byVolumeCostQry = byVolumeCostQry +" group by drug_name order by cost desc limit 20";
				byVolumeQry = byVolumeQry +" group by drug_name order by cost desc limit 20";
			
				System.out.println(byCostQry);
				System.out.println(byVolumeCostQry);
				System.out.println(byVolumeQry);
			String drugNames = "";
			String costList = "";
			List<Object[]> byCostQryList = rxDetailDao.getPrescriptionDrugs(byCostQry);
			for(int k=0;k<byCostQryList.size();k++) {
				if(k == byCostQryList.size()-1) {
					costList = costList + byCostQryList.get(k)[0].toString();
					drugNames = drugNames + byCostQryList.get(k)[1].toString();
				} else {
					costList = costList + byCostQryList.get(k)[0].toString()+",";
					drugNames = drugNames + byCostQryList.get(k)[1].toString()+",";
				}
				
			}
			PrescDrugsData byCostObj = new PrescDrugsData();
			byCostObj.setType("cost");
			byCostObj.setProvider(providersList.get(p).toString());
			byCostObj.setDrugNames(drugNames);
			byCostObj.setCosts(costList);
			PrescDrugsDao.save(byCostObj);
			
			List<Object[]> byVolumeCostQryList = rxDetailDao.getPrescriptionDrugs(byVolumeCostQry);
			
			drugNames = "";
			costList = "";
			for(int k=0;k<byVolumeCostQryList.size();k++) {
				if(k == byVolumeCostQryList.size()-1) {
					costList = costList + byVolumeCostQryList.get(k)[0].toString();
					drugNames = drugNames + byVolumeCostQryList.get(k)[1].toString();
				} else {
					costList = costList + byVolumeCostQryList.get(k)[0].toString()+",";
					drugNames = drugNames + byVolumeCostQryList.get(k)[1].toString()+",";
				}
				
			}
			PrescDrugsData byVolumeCostObj = new PrescDrugsData();
			byVolumeCostObj.setType("volume cost");
			byVolumeCostObj.setProvider(providersList.get(p).toString());
			byVolumeCostObj.setDrugNames(drugNames);
			byVolumeCostObj.setCosts(costList);
			PrescDrugsDao.save(byVolumeCostObj);
			
			List<Object[]> byVolumeQryList = rxDetailDao.getPrescriptionDrugs(byVolumeQry);
			
			drugNames = "";
			costList = "";
			for(int k=0;k<byVolumeQryList.size();k++) {
				if(k == byVolumeQryList.size()-1) {
					costList = costList + byVolumeQryList.get(k)[0].toString();
					drugNames = drugNames + byVolumeQryList.get(k)[1].toString();
				} else {
					costList = costList + byVolumeQryList.get(k)[0].toString()+",";
					drugNames = drugNames + byVolumeQryList.get(k)[1].toString()+",";
				}
				
			}
			PrescDrugsData byVolumeObj = new PrescDrugsData();
			byVolumeObj.setType("volume");
			byVolumeObj.setProvider(providersList.get(p).toString());
			byVolumeObj.setDrugNames(drugNames);
			byVolumeObj.setCosts(costList);
			PrescDrugsDao.save(byVolumeObj);
		}	
		
	}
	
	
	
	List getCombinations(List providersList) {
		List<String> combinationList = new ArrayList<>();
	    int n = providersList.size();
	    int N = (int) Math.pow(2d, Double.valueOf(n));  
	    for (int i = 1; i < N; i++) {
	        String code = Integer.toBinaryString(N | i).substring(1);
	        String comb = "";
	        for (int j = 0; j < n; j++) {
	            if (code.charAt(j) == '1') {
	                System.out.print(providersList.get(j));
	                comb = comb + providersList.get(j).toString()+" ";
	            }
	            
	        }
	        combinationList.add(comb);
	        System.out.println();
	    }
	    return combinationList;
	}
	
}
