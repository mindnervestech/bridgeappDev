package com.mnt.dao.jpa;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.mnt.dao.FileInfoDao;
import com.mnt.domain.FileInfo;

@Repository
public class FileInfoDaoJpa extends BaseDaoJpa<FileInfo> implements FileInfoDao {

	public FileInfoDaoJpa() {
		super(FileInfo.class, "FileInfo");
	}
	
	@Override
	public FileInfo getFileInfo(String fileName, String year, String month, String provider) {
		try {
		Query query = getEntityManager().createQuery("SELECT fi FROM FileInfo AS fi WHERE fi.fileName=:fileName and fi.year=:year and fi.month=:month and fi.provider=:provider");
        query.setParameter("fileName", fileName);
        query.setParameter("year", year);
        query.setParameter("month", month);
        query.setParameter("provider", provider);
        return (FileInfo) query.getSingleResult();
		} catch(NoResultException nre) {
			return null;
		}
	}
}
