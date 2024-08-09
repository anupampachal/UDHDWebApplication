package com.scsinfinity.udhd.web.audit.internalaudit;

import java.util.List;

import com.scsinfinity.udhd.services.common.BooleanResponseDTO;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAExSumCommentOpinionAcknowledgementDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAExecSummaryStrengthWeaknessOrRecommendationInputDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAExecutiveSummaryDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAExecutiveSummaryIntroductionRequestDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAExecutiveSummarySWOREnum;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAStengthWeaknessOrRecommendationOutputDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IIAExecutiveSummaryService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/audit/internal-audit/step2")
public class IAExecutiveSummaryResource {

	private final IIAExecutiveSummaryService iaExecutiveSummaryService;

	@PostMapping("/introduction")
	public ResponseEntity<IAExecutiveSummaryDTO> createUpdateIntroduction(
			@RequestBody IAExecutiveSummaryIntroductionRequestDTO dto) {
		log.debug("createUpdateIntroduction");
		return ResponseEntity.ok(iaExecutiveSummaryService.createUpdateIntroductionForExecutiveSummary(dto));

	}

	@PostMapping("/swor")
	public ResponseEntity<IAStengthWeaknessOrRecommendationOutputDTO> createUpdateSWOR(
			@RequestBody IAExecSummaryStrengthWeaknessOrRecommendationInputDTO swrDTO) {
		log.debug("createUpdateSWOR");
		return ResponseEntity.ok(iaExecutiveSummaryService.createUpdateStrengthWeaknessRecommendation(swrDTO));
	}

	@PostMapping("/swor-del")
	public ResponseEntity<BooleanResponseDTO> deleteSWOR(
			@RequestBody IAExecSummaryStrengthWeaknessOrRecommendationInputDTO swrDTO) {
		log.debug("deleteSWOR");
		return ResponseEntity.ok(iaExecutiveSummaryService.deleteStrengthWeaknessRecommendation(swrDTO));
	}

	@GetMapping("/swor/{iaId}")
	public ResponseEntity<List<IAStengthWeaknessOrRecommendationOutputDTO>> getSWORList(@PathVariable Long iaId,
			@RequestParam("type") IAExecutiveSummarySWOREnum type) {
		return ResponseEntity.ok(iaExecutiveSummaryService.getListOfStrengthsWeaknessesOrRecommendations(iaId, type));
	}

	@PostMapping("/swor/info")
	public ResponseEntity<IAStengthWeaknessOrRecommendationOutputDTO> getStengthWeaknessOrRecommendation(
			@RequestBody IAExecSummaryStrengthWeaknessOrRecommendationInputDTO swrDTO) {
		log.debug("getStengthWeaknessOrRecommendation");
		return ResponseEntity.ok(iaExecutiveSummaryService.getSworById(swrDTO));
	}

	@PostMapping("/coack")
	public ResponseEntity<IAExecutiveSummaryDTO> createUpdateCommentOpinionAcknowledgement(
			@RequestBody IAExSumCommentOpinionAcknowledgementDTO inputDTO) {
		log.debug("createUpdateCommentOpinionAcknowledgement");
		return ResponseEntity.ok(iaExecutiveSummaryService.createUpdateCommentOpinionAcknowledgement(inputDTO));
	}

	@GetMapping("/{id}")
	public ResponseEntity<IAExecutiveSummaryDTO> getExecutiveSummary(@PathVariable Long id) {
		log.debug("getExecutiveSummary");
		return ResponseEntity.ok(iaExecutiveSummaryService.getExecutiveSummaryById(id));
	}

	@PostMapping("/coack/info")
	public ResponseEntity<String> getCOACKString(@RequestBody IAExSumCommentOpinionAcknowledgementDTO dto) {
		log.debug("getCOACKString");
		return ResponseEntity.ok(iaExecutiveSummaryService.getCommentOpinionAcknowledgement(dto));
	}

	@PostMapping("/file/upload")
	public ResponseEntity<IAExecutiveSummaryDTO> uploadBudget(@RequestPart("file") MultipartFile file,
			@RequestPart("ia") String iaId) {
		log.debug("file being uploaded");
		return ResponseEntity.ok(iaExecutiveSummaryService.uploadExSumCommentFile(file, Long.parseLong(iaId)));
	}

	@GetMapping("/file/{iaId}")
	public ResponseEntity<Resource> getFile(@PathVariable Long iaId ) {
		try {
			Resource file = iaExecutiveSummaryService.getFile(iaId);
			return ResponseEntity.ok().contentLength(file.contentLength())
					.header("Content-Disposition", "attachment; filename=" + file.getFilename())
					.contentType(MediaType.APPLICATION_PDF).body(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
