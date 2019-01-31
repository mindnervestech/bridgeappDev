package com.mnt.dao;

import com.mnt.domain.FileInfo;

public interface FileInfoDao extends BaseDao<FileInfo> {

	public FileInfo getFileInfo(String fileName, String year, String month, String provider);
}
