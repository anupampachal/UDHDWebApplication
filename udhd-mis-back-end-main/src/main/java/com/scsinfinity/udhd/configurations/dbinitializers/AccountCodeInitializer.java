/**
 * 
 */
package com.scsinfinity.udhd.configurations.dbinitializers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.scsinfinity.udhd.dao.entities.ledger.BaseHeadEntity;
import com.scsinfinity.udhd.dao.entities.ledger.ChartOfAccountsEntity;
import com.scsinfinity.udhd.dao.entities.ledger.DetailHeadEntity;
import com.scsinfinity.udhd.dao.entities.ledger.MajorHeadEntity;
import com.scsinfinity.udhd.dao.entities.ledger.MinorHeadEntity;
import com.scsinfinity.udhd.dao.repositories.ledger.IBaseHeadRepository;
import com.scsinfinity.udhd.dao.repositories.ledger.IChartOfAccountsRepository;
import com.scsinfinity.udhd.dao.repositories.ledger.IDetailHeadRepository;
import com.scsinfinity.udhd.dao.repositories.ledger.IMajorHeadRepository;
import com.scsinfinity.udhd.dao.repositories.ledger.IMinorHeadRepository;

/**
 * @author aditya-server
 *
 *         29-Aug-2021 -- 12:26:36 am
 */
@Order(value = 5)
@Component
@Transactional
public class AccountCodeInitializer {

	private final IBaseHeadRepository baseHeadRepo;
	private final IMajorHeadRepository majorHeadRepo;
	private final IMinorHeadRepository minorHeadRepo;
	private final IDetailHeadRepository detailHeadRepo;
	private final Environment env;
	private final IChartOfAccountsRepository chartOfAccountsRepo;

	/**
	 * @param baseHeadRepo
	 * @param majorHeadRepo
	 * @param minorHeadRepo
	 * @param detailHeadRepo
	 * @param env
	 * @param chartOfAccountsRepo
	 */
	public AccountCodeInitializer(IBaseHeadRepository baseHeadRepo, IMajorHeadRepository majorHeadRepo,
			IMinorHeadRepository minorHeadRepo, IDetailHeadRepository detailHeadRepo, Environment env,
			IChartOfAccountsRepository chartOfAccountsRepo) {
		super();
		this.baseHeadRepo = baseHeadRepo;
		this.majorHeadRepo = majorHeadRepo;
		this.minorHeadRepo = minorHeadRepo;
		this.detailHeadRepo = detailHeadRepo;
		this.env = env;
		this.chartOfAccountsRepo = chartOfAccountsRepo;
	}

	@Value("${scs.setting.file.name}")
	private String headFile;
	private Map<Integer, BaseHeadEntity> baseHeadMap = new HashMap<>();
	private Map<String, Object> headMap = new HashMap<>();

	public void initializeAccount() {
		initializeBaseHead();
		readHeadsDataFromXlsx();
	}

	private List<BaseHeadEntity> initializeBaseHead() {
		BaseHeadEntity income = saveBaseHead(1, "Income", "income");
		BaseHeadEntity expense = saveBaseHead(2, "Expense", "expense");
		BaseHeadEntity liabilities = saveBaseHead(3, "Liabilities", "liabilities");
		BaseHeadEntity assets = saveBaseHead(4, "Assets", "assets");

		List<BaseHeadEntity> bases = new ArrayList<>();
		bases.addAll(Arrays.asList(income, expense, liabilities, assets));

		baseHeadMap = bases.stream().collect(Collectors.toMap(BaseHeadEntity::getCode, Function.identity()));

		return bases;
	}

	private BaseHeadEntity saveBaseHead(Integer code, String name, String alias) {
		return baseHeadRepo.save(BaseHeadEntity.builder().code(code).name(name).alias(alias).build());
	}

	private void readHeadsDataFromXlsx() {
		ArrayList<String> activeProfiles = new ArrayList<String>(Arrays.asList(env.getActiveProfiles()));
		File file;
		try {
			file = activeProfiles.contains("prod") ? new File(headFile) : getFileFromResourceClassLoader(headFile);
			FileInputStream files = new FileInputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook(files);

			XSSFSheet incomeSheet = workbook.getSheetAt(0);
			XSSFSheet expenseSheet = workbook.getSheetAt(1);
			XSSFSheet liabilitiesSheet = workbook.getSheetAt(2);
			XSSFSheet assetsSheet = workbook.getSheetAt(3);

			sheetSave(incomeSheet);
			sheetSave(expenseSheet);
			sheetSave(liabilitiesSheet);
			sheetSave(assetsSheet);

		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void sheetSave(XSSFSheet sheet) {
		// Iterate through each rows one by one
		Iterator<Row> rowIterator = sheet.iterator();
		ArrayList<Row> rowList = new ArrayList<>();
		rowIterator.forEachRemaining(rowList::add);
		// skip first 5 rows
		rowList.subList(0, 3).clear();

		rowList.forEach(row -> {
			CellValueInfoXLSX cellInfo = CellValueInfoXLSX.builder().row(row).build();
			cellInfo = cellInfo.computeCellValue();
			if (cellInfo.getHeadCode() != null && cellInfo.getHeadName() != null) {
				setDifferentHeadsLevelFromCell(cellInfo.getHeadName(), cellInfo.getHeadCode());
				saveChartOfAccounts(cellInfo.getHeadName(), cellInfo.getHeadCode());

			}
		});
	}

	private void saveChartOfAccounts(String headName, String headCode) {
		chartOfAccountsRepo.save(ChartOfAccountsEntity.builder().code(headCode).name(headName).build());
	}

	private void setDifferentHeadsLevelFromCell(String headName, String headCode) {
		String headNameData = headName.trim();
		String accountNo = headCode.toString();
		int accountNoLength = accountNo.length();
		String baseCode = accountNo.substring(0, 1);
		BaseHeadEntity baseHead = baseHeadMap.get(Integer.parseInt(baseCode));
		MajorHeadEntity majorHead = null;
		MinorHeadEntity minorHead = null;
		@SuppressWarnings("unused")
		DetailHeadEntity detailHead = null;
		String majorCode = "";
		String minorCode = "";
		String detailCode = "";
		switch (accountNoLength) {
		case 3:
			majorCode = accountNo.length() > 2 ? accountNo.substring(accountNo.length() - 3) : accountNo;
			majorHead = getMajorHeadByCode(majorCode, headNameData, baseHead);
			break;
		case 5:
			majorCode = accountNo.length() > 4 ? accountNo.substring(0, 3) : accountNo;
			majorHead = getMajorHeadByCode(majorCode, headNameData, baseHead);
			minorCode = accountNo.length() > 4 ? accountNo.substring(accountNo.length() - 2) : accountNo;

			if (majorHead != null)
				minorHead = getMinorHeadByCode(minorCode, headNameData, majorHead);
			break;
		case 7:
			majorCode = accountNo.substring(0, 3);
			minorCode = accountNo.substring(3, 5);
			detailCode = accountNo.substring(5, 7);
			majorHead = getMajorHeadByCode(majorCode, headNameData, baseHead);
			if (majorHead != null) {
				minorHead = getMinorHeadByCode(minorCode, headNameData, majorHead);
				if (minorHead != null)
					detailHead = getDetailHeadByCode(detailCode, headNameData, majorHead, minorHead);
			}
			break;
		}
	}

	private DetailHeadEntity getDetailHeadByCode(String code, String name, MajorHeadEntity majorHead,
			MinorHeadEntity minorHead) {
		String detailHeadCode = majorHead.getCode().concat(minorHead.getCode()).concat(code);
		DetailHeadEntity detailHead = null;
		if (headMap != null)
			detailHead = (DetailHeadEntity) headMap.get(detailHeadCode);
		else
			headMap = new HashMap<>();

		if (detailHead == null) {
			detailHead = detailHeadRepo.save(new DetailHeadEntity(code, name, name.concat(code.toString()), minorHead));
			headMap.put(detailHeadCode, detailHead);
		}
		return detailHead;
	}

	private MinorHeadEntity getMinorHeadByCode(String code, String name, MajorHeadEntity majorHead) {
		String minorHeadCode = majorHead.getCode().concat(code);

		MinorHeadEntity minorHead = null;
		if (headMap != null)
			minorHead = (MinorHeadEntity) headMap.get(minorHeadCode);
		else
			headMap = new HashMap<>();

		if (minorHead == null) {
			minorHead = minorHeadRepo.save(new MinorHeadEntity(code, name, name.concat(code.toString()), majorHead));
			headMap.put(minorHeadCode, minorHead);
		}

		return minorHead;

	}

	private MajorHeadEntity getMajorHeadByCode(String code, String name, BaseHeadEntity baseHead) {
		MajorHeadEntity majorHead = null;
		if (headMap != null)
			majorHead = (MajorHeadEntity) headMap.get(code);
		else
			headMap = new HashMap<>();

		if (majorHead == null) {
			majorHead = majorHeadRepo
					.save(new MajorHeadEntity(code, name, name.concat(code.toString()), baseHead, new BigDecimal(0.0)));
			headMap.put(majorHead.getCode(), majorHead);
		}

		return majorHead;

	}

	private File getFileFromResourceClassLoader(String fileName) throws URISyntaxException {

		/*ClassLoader classLoader = getClass().getClassLoader();
		URL resource = classLoader.getResource(fileName);
		if (resource == null) {
			throw new IllegalArgumentException("file not found! " + fileName);
		} else {
			return new File(resource.toURI());
		}
		*/
		
		ClassLoader classLoader = this.getClass().getClassLoader();
		File newFile=new File(fileName);
		try {
			InputStream io=	classLoader.getResourceAsStream(fileName);
			
			FileUtils.copyInputStreamToFile(io, newFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newFile;
	}

}
