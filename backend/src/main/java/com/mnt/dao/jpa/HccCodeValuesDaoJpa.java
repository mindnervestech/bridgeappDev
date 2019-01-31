package com.mnt.dao.jpa;

import org.springframework.stereotype.Repository;

import com.mnt.dao.HccCodeValuesDao;
import com.mnt.domain.HccCodeValues;

@Repository
public class HccCodeValuesDaoJpa extends BaseDaoJpa<HccCodeValues> implements HccCodeValuesDao {

	HccCodeValuesDaoJpa() {
		super(HccCodeValues.class, "HccCodeValues");
	}
	
	
}
