package com.mnt.vm;

public class MembershipManagementVM {

	public Integer currentPatients;
	public Integer newPatients;
	public Integer termedPatients;
	public Integer netImpact;
	
	public String currentPatientsPercent;
	public String newPatientsPercent;
	public String termedPatientsPercent;
	
	public Integer getCurrentPatients() {
		return currentPatients;
	}
	public void setCurrentPatients(Integer currentPatients) {
		this.currentPatients = currentPatients;
	}
	public Integer getNewPatients() {
		return newPatients;
	}
	public void setNewPatients(Integer newPatients) {
		this.newPatients = newPatients;
	}
	public Integer getTermedPatients() {
		return termedPatients;
	}
	public void setTermedPatients(Integer termedPatients) {
		this.termedPatients = termedPatients;
	}
	public Integer getNetImpact() {
		return netImpact;
	}
	public void setNetImpact(Integer netImpact) {
		this.netImpact = netImpact;
	}
	public String getCurrentPatientsPercent() {
		return currentPatientsPercent;
	}
	public void setCurrentPatientsPercent(String currentPatientsPercent) {
		this.currentPatientsPercent = currentPatientsPercent;
	}
	public String getNewPatientsPercent() {
		return newPatientsPercent;
	}
	public void setNewPatientsPercent(String newPatientsPercent) {
		this.newPatientsPercent = newPatientsPercent;
	}
	public String getTermedPatientsPercent() {
		return termedPatientsPercent;
	}
	public void setTermedPatientsPercent(String termedPatientsPercent) {
		this.termedPatientsPercent = termedPatientsPercent;
	}
}
