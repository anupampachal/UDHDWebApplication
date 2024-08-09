package com.scsinfinity.udhd.services.base;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import com.scsinfinity.udhd.dao.entities.storage.FileEntity;
import com.scsinfinity.udhd.services.base.interfaces.IXlsService;

@Service
public class XlsService implements IXlsService {

	@Override
	public List<Row> getRowListFromFileEntity(FileEntity fileE) {
		Resource resource = null;
		List<Row> rowList = new ArrayList<>();
		try {
			Path pathForStorage = Paths.get(fileE.getParentFolder().getPathToCurrentDir());
			Path originalPath = pathForStorage.resolve(fileE.getFileServerName());
			resource = new UrlResource(originalPath.toUri());

			FileInputStream fileStr = new FileInputStream(resource.getFile());

			XSSFWorkbook workbook = new XSSFWorkbook(fileStr);

			XSSFSheet sheet = workbook.getSheetAt(0);

			Iterator<Row> rowIterator = sheet.iterator();

			rowIterator.forEachRemaining(rowList::add);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowList;
	}

}
