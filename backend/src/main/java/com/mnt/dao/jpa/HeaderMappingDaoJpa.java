package com.mnt.dao.jpa;

import org.springframework.stereotype.Repository;

import com.mnt.dao.HeaderMappingDao;
import com.mnt.domain.HeaderMapping;

@Repository
public class HeaderMappingDaoJpa  extends BaseDaoJpa<HeaderMapping> implements HeaderMappingDao {

	public HeaderMappingDaoJpa() {
		super(HeaderMapping.class, "HeaderMapping");
	}
}
