package com.scsinfinity.udhd.web.deas.periodic;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.scsinfinity.udhd.configurations.application.date.DateQuartersEnum;
import com.scsinfinity.udhd.configurations.application.date.DateUtil;
import com.scsinfinity.udhd.configurations.application.date.FinYearDateDTO;
import com.scsinfinity.udhd.services.common.GenericResponseObject;
import com.scsinfinity.udhd.services.common.Pagination;
import com.scsinfinity.udhd.services.deas.dto.FixedAssetsRegisterDTO;
import com.scsinfinity.udhd.services.deas.interfaces.IFixedAssetsRegisterService;
import com.scsinfinity.udhd.services.settings.dto.ULBDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/deas/fixed-assets")
public class FixedAssetsRegisterController {
	private final IFixedAssetsRegisterService fixedAssetsRegisterService;
	private final DateUtil dateUtil;

	@PostMapping("/file/upload")
	public ResponseEntity<FixedAssetsRegisterDTO> uploadBudget(@RequestPart("file") MultipartFile file,
			@RequestPart("startDate") String startDate, @RequestPart("endDate") String endDate,
			@RequestPart("inputDate") String inputDate, 
			//@RequestPart("quarter") String quarter,
			@RequestPart("ulbId") String ulbId) {
		log.debug("file being uploaded");
		return ResponseEntity.ok(fixedAssetsRegisterService.uploadFixedAssetsRegisterStatement(
				FixedAssetsRegisterDTO.builder().endDate(endDate).startDate(startDate).inputDate(inputDate)
						//.quarter(DateQuartersEnum.valueOf(quarter))
				.file(file).ulbId(Long.parseLong(ulbId)).build()));
	}

	/**
	 * getBudgetUploadDataById
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<FixedAssetsRegisterDTO> getBudgetUploadDataById(@PathVariable Long id) {
		log.info("getBudgetUploadDataById", id);
		return ResponseEntity.ok(fixedAssetsRegisterService.getFixedAssetsRegisterDataById(id));
	}

	/**
	 * getBudgetUploadDataByPage
	 * 
	 * @param data
	 * @return
	 */
	@PostMapping("/info")
	public ResponseEntity<GenericResponseObject<FixedAssetsRegisterDTO>> getBudgetUploadDataByPage(
			@Valid @RequestBody Pagination<FixedAssetsRegisterDTO> data) {
		log.info("getBudgetUploadDataByPage", data);
		return ResponseEntity.ok(fixedAssetsRegisterService.getFixedAssetsRegisterDataByPage(data));
	}

	@PostMapping("/fin-years")
	public ResponseEntity<List<FinYearDateDTO>> getDateList(@RequestBody Pagination<FinYearDateDTO> data) {
		log.info("get financial year list", data);
		return ResponseEntity.ok(dateUtil.getFinancialYear(LocalDate.now(), data.getPageNo(), data.getPageSize()));
	}

	@GetMapping("/file/{fileId}")
	public ResponseEntity<Resource> getFile(@PathVariable String fileId) {
		try {
			Resource file = fixedAssetsRegisterService.getFile(fileId);
			return ResponseEntity.ok().contentLength(file.contentLength())
					.header("Content-Disposition", "attachment; filename=" + file.getFilename())
					.contentType(MediaType.APPLICATION_PDF).body(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping("/ulb")
	public ResponseEntity<List<ULBDTO>> getMappedUlbs() {
		return ResponseEntity.ok(fixedAssetsRegisterService.getULBsMappedToMe());
	}
}
