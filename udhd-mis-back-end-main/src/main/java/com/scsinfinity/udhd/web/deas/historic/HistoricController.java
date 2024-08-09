package com.scsinfinity.udhd.web.deas.historic;

import java.util.List;

import javax.validation.Valid;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.scsinfinity.udhd.configurations.application.date.DateUtil;
import com.scsinfinity.udhd.services.common.GenericResponseObject;
import com.scsinfinity.udhd.services.common.Pagination;
import com.scsinfinity.udhd.services.deas.dto.HistoricDataDTO;
import com.scsinfinity.udhd.services.deas.interfaces.IHistoricDataService;
import com.scsinfinity.udhd.services.settings.dto.ULBDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/deas/historic-upload")
public class HistoricController {

	private final IHistoricDataService historicDataService;
	private final DateUtil dateUtil;

	@PostMapping("/file/upload")
	public ResponseEntity<HistoricDataDTO> uploadHistoricData(@RequestPart("file") MultipartFile file,
			@RequestPart("startDate") String startDate, @RequestPart("endDate") String endDate,
			@RequestPart("ulbId") String ulbId) {
		log.debug("file being uploaded");
		return ResponseEntity.ok(historicDataService.uploadHistoricData(HistoricDataDTO.builder()
				.endDate(dateUtil.getDateFromPattern(endDate)).startDate(dateUtil.getDateFromPattern(startDate))
				.file(file).ulbId(Long.parseLong(ulbId)).build()));
	}

	/**
	 * getHistoricDataById
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<HistoricDataDTO> getHistoricDataById(@PathVariable Long id) {
		log.info("getHistoricDataById", id);
		return ResponseEntity.ok(historicDataService.getHistoricDataById(id));
	}

	/**
	 * getHistoricDataByPage
	 * 
	 * @param data
	 * @return
	 */
	@PostMapping("/info")
	public ResponseEntity<GenericResponseObject<HistoricDataDTO>> getHistoricDataByPage(
			@Valid @RequestBody Pagination<HistoricDataDTO> data) {
		log.info("getHistoricDataByPage", data);
		return ResponseEntity.ok(historicDataService.getHistoricDataByPage(data));
	}

	@GetMapping("/file/{fileId}")
	public ResponseEntity<Resource> getFile(@PathVariable String fileId) {
		try {
			Resource file = historicDataService.getFile(fileId);
			return ResponseEntity.ok().contentLength(file.contentLength())
					.header("Content-Disposition", "attachment; filename=" + file.getFilename())
					.contentType(MediaType.asMediaType(MimeType.valueOf("application/zip"))).body(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping("/ulb")
	public ResponseEntity<List<ULBDTO>> getMappedUlbs() {
		return ResponseEntity.ok(historicDataService.getULBsMappedToMe());
	}
}
