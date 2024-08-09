package com.scsinfinity.udhd.web.audit.internalaudit;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.scsinfinity.udhd.services.audit.internalaudit.dto.IADetailAuditDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IADetailAuditParaInfoDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAStatusOfAuditObservationDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IIADetailAuditService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/audit/internal-audit/step4")
public class IADetailAuditResource {

	private final IIADetailAuditService iaDetailAuditService;

	@GetMapping("/{iaId}")
	public ResponseEntity<IADetailAuditDTO> getDetailAuditInfo(@PathVariable Long iaId) {
		log.debug("getDetailAuditInfo");
		return ResponseEntity.ok(iaDetailAuditService.retrieveDetailedAudit(iaId));
	}

	@PutMapping("/obs/{iaId}")
	public ResponseEntity<IAStatusOfAuditObservationDTO> createUpdateAuditObservation(@PathVariable Long iaId,
			@RequestBody IAStatusOfAuditObservationDTO dto) {
		log.debug("createUpdateAuditObservation");
		return ResponseEntity.ok(iaDetailAuditService.createUpdateAuditObservation(iaId, dto));
	}

	@PutMapping("/para/{iaId}")
	public ResponseEntity<IADetailAuditParaInfoDTO> createUpdateAuditParaInfo(@PathVariable Long iaId,
			@RequestBody IADetailAuditParaInfoDTO dto) {
		log.debug("createUpdateAuditParaInfo");
		return ResponseEntity.ok(iaDetailAuditService.createUpdateAuditParaInfo(iaId, dto));
	}

	@GetMapping("/obs/{iaId}")
	public ResponseEntity<List<IAStatusOfAuditObservationDTO>> getListOfAuditObservation(@PathVariable Long iaId) {
		log.debug("getListOfAuditObservation");
		return ResponseEntity.ok(iaDetailAuditService.getListOfAuditObservation(iaId));
	}

	@GetMapping("/para/{iaId}")
	public ResponseEntity<List<IADetailAuditParaInfoDTO>> getListOfAuditPara(@PathVariable Long iaId) {
		log.debug("getListOfAuditPara");
		return ResponseEntity.ok(iaDetailAuditService.getListOfAuditPara(iaId));
	}

	@GetMapping("/obs-id/{auditObsId}/ia/{iaId}")
	public ResponseEntity<IAStatusOfAuditObservationDTO> getAuditObservationById(@PathVariable Long auditObsId,
			@PathVariable Long iaId) {
		log.debug("getAuditObservationById");
		return ResponseEntity.ok(iaDetailAuditService.getAuditObservationById(auditObsId, iaId));
	}

	@GetMapping("/para-id/{auditParaId}/ia/{iaId}")
	public ResponseEntity<IADetailAuditParaInfoDTO> getAuditParaById(@PathVariable Long auditParaId,
			@PathVariable Long iaId) {
		log.debug("getAuditParaById");
		return ResponseEntity.ok(iaDetailAuditService.getAuditParaById(auditParaId, iaId));
	}

	@PostMapping("/file/upload")
	public ResponseEntity<IADetailAuditParaInfoDTO> uploadParaInfoFile(@RequestPart("file") MultipartFile file,
			@RequestPart("paraInfoId") String paraInfoId, @RequestPart("iaId") String iaId) {
		log.debug("file being uploaded");
		return ResponseEntity
				.ok(iaDetailAuditService.uploadParaInfoFile(file, Long.parseLong(paraInfoId), Long.parseLong(iaId)));
	}

	@GetMapping("/file/{paraInfoId}")
	public ResponseEntity<Resource> getFile(@PathVariable Long paraInfoId) {
		try {
			Resource file = iaDetailAuditService.getFile(paraInfoId);
			return ResponseEntity.ok().contentLength(file.contentLength())
					.header("Content-Disposition", "attachment; filename=" + file.getFilename())
					.contentType(MediaType.APPLICATION_PDF).body(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
