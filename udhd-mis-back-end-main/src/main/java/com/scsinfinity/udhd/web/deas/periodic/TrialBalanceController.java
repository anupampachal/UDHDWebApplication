package com.scsinfinity.udhd.web.deas.periodic;

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

import com.scsinfinity.udhd.services.common.GenericResponseObject;
import com.scsinfinity.udhd.services.common.Pagination;
import com.scsinfinity.udhd.services.deas.dto.TrialBalanceUploadReportSummaryDTO;
import com.scsinfinity.udhd.services.deas.interfaces.ITrialBalanceService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/deas/trial-balance")
public class TrialBalanceController {

	private final ITrialBalanceService trialBalanceService;

	@PostMapping("/file/upload")
	public ResponseEntity<Boolean> uploadULBTrialBalanceFile(@RequestPart("file") MultipartFile file) {
		log.debug("file being uploaded");
		return ResponseEntity.ok(trialBalanceService.uploadTrialBalance(file));
	}

	/**
	 * getTrialBalanceFileUploadSummary
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<TrialBalanceUploadReportSummaryDTO> getTrialBalanceFileUploadSummary(@PathVariable Long id) {
		log.info("getTrialBalanceFileUploadSummary", id);
		return ResponseEntity.ok(trialBalanceService.getTrialBalanceFileUploadSummary(id));
	}

	/**
	 * getTrialBalanceFileUploadSummary
	 * 
	 * @param data
	 * @return
	 */
	@PostMapping("/info")
	public ResponseEntity<GenericResponseObject<TrialBalanceUploadReportSummaryDTO>> getTrialBalanceFileUploadSummaryPage(
			@Valid @RequestBody Pagination<TrialBalanceUploadReportSummaryDTO> data) {
		log.info("getTrialBalanceFileUploadSummaryPage", data);
		return ResponseEntity.ok(trialBalanceService.getTrialBalanceFileUploadSummaryPage(data));
	}


	@GetMapping("/file/summary/{fileId}")
	public ResponseEntity<Resource> getSummaryFile(@PathVariable String fileId) {
		try {
			Resource file = trialBalanceService.getSummaryFile(fileId);
			return ResponseEntity.ok().contentLength(file.contentLength())
					.header("Content-Disposition", "attachment; filename=" + file.getFilename())
					.contentType(MediaType.APPLICATION_OCTET_STREAM).body(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping("/file/tb/{fileId}")
	public ResponseEntity<Resource> getTBFile(@PathVariable String fileId) {
		try {
			Resource file = trialBalanceService.getTBFile(fileId);
			return ResponseEntity.ok().contentLength(file.contentLength())
					.header("Content-Disposition", "attachment; filename=" + file.getFilename())
					.contentType(MediaType.APPLICATION_OCTET_STREAM).body(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
