/**
 * 
 */
package com.scsinfinity.udhd.configurations.dbinitializers;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * @author aditya-server
 *
 *         29-Aug-2021 -- 12:58:33 pm
 */
@Getter
@Builder
@AllArgsConstructor
public class CellValueInfoXLSX {
	private String headCode;
	private String headName;
	private Row row;
	private String type;
	
	

	public CellValueInfoXLSX computeCellValue() {

		Cell majorHeadNameCell = row.getCell(3);
		Cell minorHeadNameCell = row.getCell(6);
		Cell detailHeadNameCell = row.getCell(9);

		String majorHeadName = majorHeadNameCell.getStringCellValue();
		String minorHeadName = minorHeadNameCell.getStringCellValue();
		String detailHeadName = detailHeadNameCell.getStringCellValue();

		if (!majorHeadName.trim().equalsIgnoreCase("")) {
			// its case of majorhead
			headName = majorHeadName.trim();
			headCode = readCellValueForCode(row, "MAJOR");
			type = "MAJOR";
		} else if (majorHeadName.trim().equalsIgnoreCase("") && !minorHeadName.trim().equalsIgnoreCase("")) {
			headName = minorHeadName.trim();
			headCode = readCellValueForCode(row, "MINOR");
			type = "MINOR";
		} else if (majorHeadName.trim().equalsIgnoreCase("") && minorHeadName.trim().equalsIgnoreCase("")
				&& !detailHeadName.trim().equalsIgnoreCase("")) {
			headName = detailHeadName.trim();
			headCode = readCellValueForCode(row, "DETAIL");
			type = "DETAIL";
		}

		return this;

	}

	private String readCellValueForCode(Row row, String type) {
		Cell cell1 = row.getCell(13);
		Cell cell2 = row.getCell(14);
		Cell cell3 = row.getCell(15);
		Cell cell4 = row.getCell(16);
		Cell cell5 = row.getCell(17);
		Cell cell6 = row.getCell(18);
		Cell cell7 = row.getCell(19);

		String code = "";

		switch (type) {
		case "MAJOR":
			code = String.valueOf((int) cell1.getNumericCellValue())
					.concat(String.valueOf((int) cell2.getNumericCellValue()))
					.concat(String.valueOf((int) cell3.getNumericCellValue()));
			break;

		case "MINOR":
			code = String.valueOf((int) cell1.getNumericCellValue())
					.concat(String.valueOf((int) cell2.getNumericCellValue()))
					.concat(String.valueOf((int) cell3.getNumericCellValue()))
					.concat(String.valueOf((int) cell4.getNumericCellValue()))
					.concat(String.valueOf((int) cell5.getNumericCellValue()));

			break;
		case "DETAIL":
			code = String.valueOf((int) cell1.getNumericCellValue())
					.concat(String.valueOf((int) cell2.getNumericCellValue()))
					.concat(String.valueOf((int) cell3.getNumericCellValue()))
					.concat(String.valueOf((int) cell4.getNumericCellValue()))
					.concat(String.valueOf((int) cell5.getNumericCellValue()))
					.concat(String.valueOf((int) cell6.getNumericCellValue()))
					.concat(String.valueOf((int) cell7.getNumericCellValue()));

		}
		return code;

	}

	@Override
	public String toString() {
		return "CellValueInfoXLSX [headCode=" + headCode + ", headName=" + headName + ", type=" + type + "]";
	}

}
