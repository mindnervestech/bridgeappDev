package com.mnt.dao;

import com.mnt.domain.StoplossDetail;

public interface StoplossDetailDao extends BaseDao<StoplossDetail> {

	public void deleteOldRecords(String year, String month, String provider);
}
