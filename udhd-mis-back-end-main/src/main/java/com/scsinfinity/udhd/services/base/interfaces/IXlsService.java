package com.scsinfinity.udhd.services.base.interfaces;

import java.util.List;

import org.apache.poi.ss.usermodel.Row;

import com.scsinfinity.udhd.dao.entities.storage.FileEntity;

public interface IXlsService {

	List<Row> getRowListFromFileEntity(FileEntity fileE);
	
	
}
