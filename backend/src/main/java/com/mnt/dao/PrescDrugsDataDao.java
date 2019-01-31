package com.mnt.dao;

import com.mnt.domain.PrescDrugsData;
import com.mnt.vm.ClaimDetailsVM;

public interface PrescDrugsDataDao extends BaseDao<PrescDrugsData> {

	public void deleteAllData();
	public PrescDrugsData getTop20PrescriptionDrugs(String type, String provider);
}
