package com.mnt.dao;

import java.util.List;

import com.mnt.domain.RxDetail;
import com.mnt.vm.DrugsVM;

public interface RxDetailDao extends BaseDao<RxDetail> {
	public void deleteOldRecords(String year, String month, String provider);
	public List<Object[]> getTop20PrescriptionDrugs(String type);
	public List<Object[]> getPrescriptionDrugs(String qry);
	public List<String> getAllPlans();
	public List<Object[]> getAllPCP(String provider);
	public List<String> getAllPCPLocationCode(String year);
}
