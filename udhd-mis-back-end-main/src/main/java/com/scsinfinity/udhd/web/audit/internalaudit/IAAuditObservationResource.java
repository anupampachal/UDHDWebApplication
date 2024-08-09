package com.scsinfinity.udhd.web.audit.internalaudit;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAPartCAuditObservationDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAAuditObjectsWithMonetaryIrregularitiesDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAAuditObservationPartALineItemDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IANonComplianceOfTDSNVATDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IANonComplianceOfTDSNVATEnum;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAPartAAuditObsTypeEnum;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAPartCAuditObservationEnum;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAUtilisationOfGrantsLineItemDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.PartByTypeAuditObsEnum;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IIAAuditObservationService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/audit/internal-audit/step6")
public class IAAuditObservationResource {
	private final IIAAuditObservationService auditService;
	

	@PutMapping("/part-a")
	public ResponseEntity<IAAuditObservationPartALineItemDTO> createUpdateAuditObservationPartALineItem(
			@RequestBody IAAuditObservationPartALineItemDTO auditObservation) {
		log.debug("createUpdateAuditObservationPartALineItem");
		return ResponseEntity.ok(auditService.createUpdateAuditObservationPartALineItem(auditObservation));

	}

	@PostMapping("/part-a/{id}/ia/{iaId}")
	public ResponseEntity<IAAuditObservationPartALineItemDTO> getAuditObservationPartALineItemById(
			@PathVariable Long id, @PathVariable Long iaId, @RequestParam IAPartAAuditObsTypeEnum partAType) {
		return ResponseEntity.ok(auditService.getAuditObservationPartALineItemById(id, iaId, partAType));
	}

	@DeleteMapping("/part-a/{id}/obs/{auditObservationId}")
	public ResponseEntity<Boolean> deleteAuditObservationLineItem(@PathVariable Long id,
			@PathVariable Long auditObservationId, @RequestParam IAPartAAuditObsTypeEnum partAType) {
		return ResponseEntity.ok(auditService.deleteAuditObservationLineItem(auditObservationId, id, partAType));
	}

	@PutMapping("/part-a-file")
	public ResponseEntity<String> uploadFileForAuditObservationPartA(@RequestPart("file") MultipartFile file,
			@RequestPart("id") String id, @RequestPart("iaId") String iaId,
			@RequestParam("partAType") IAPartAAuditObsTypeEnum partAType) {
		return ResponseEntity.ok(auditService.uploadFileForAuditObservationPartA(file, Long.parseLong(id),
				Long.parseLong(iaId), partAType));
	}

	@GetMapping("/part-a-file/{id}")
	public ResponseEntity<Resource> getFilePartA(@PathVariable Long id,
			@RequestParam IAPartAAuditObsTypeEnum partAType) {
		try {
			Resource file = auditService.getFilePartA(id, partAType);
			return ResponseEntity.ok().contentLength(file.contentLength())
					.header("Content-Disposition", "attachment; filename=" + file.getFilename())
					.contentType(MediaType.APPLICATION_PDF).body(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping("/part-a-obs/{iaId}")
	public ResponseEntity<IAAuditObservationPartALineItemDTO> getAuditObservationPartALineItemByType(
			@PathVariable Long iaId, @RequestParam IAPartAAuditObsTypeEnum partAType) {
		return ResponseEntity.ok(auditService.getAuditObservationPartALineItemByType(iaId, partAType));
	}

	@GetMapping("/part-a-others/{iaId}")
	public ResponseEntity<List<IAAuditObservationPartALineItemDTO>> getOthersTypeList(@PathVariable Long iaId) {
		return ResponseEntity.ok(auditService.getOthersTypeList(iaId));
	}

	@PutMapping("/part-b/obs")
	public ResponseEntity<IAAuditObjectsWithMonetaryIrregularitiesDTO> createUpdateAuditObservationPartB(
			@RequestBody IAAuditObjectsWithMonetaryIrregularitiesDTO irregularitiesDTO) {
		return ResponseEntity.ok(auditService.createUpdateAuditObservationPartB(irregularitiesDTO));
	}

	@GetMapping("/part-b/obs/{id}")
	public ResponseEntity<IAAuditObjectsWithMonetaryIrregularitiesDTO> getAuditObsPartBById(@PathVariable Long id) {
		return ResponseEntity.ok(auditService.getAuditObsPartBById(id));
	}

	@GetMapping("/part-b/irList/{iaId}")
	public ResponseEntity<List<IAAuditObjectsWithMonetaryIrregularitiesDTO>> getAllIrregularitiesForPartB(
			@PathVariable Long iaId, @RequestParam PartByTypeAuditObsEnum type) {
		return ResponseEntity.ok(auditService.getAllIrregularitiesForPartB(iaId, type));
	}

	@DeleteMapping("/part-b/obs/{id}")
	public ResponseEntity<Boolean> deletePartBAuditObservation(@PathVariable Long id,
			@RequestParam PartByTypeAuditObsEnum type) {
		return ResponseEntity.ok(auditService.deletePartBAuditObservation(id, type));
	}

	@PutMapping("/part-b/audit-obs-file/{id}/ia/{iaId}")
	public ResponseEntity<String> uploadFileForPartBAuditObservation(@RequestPart("file") MultipartFile file,
			@PathVariable Long id, @PathVariable Long iaId) {
		return ResponseEntity.ok(auditService.uploadFileForPartBAuditObservation(file, id, iaId));
	}

	@GetMapping("/part-b-file/{id}")
	public ResponseEntity<Resource> getFilePartBAuditObs(@PathVariable Long id) {
		try {
			Resource file = auditService.getFilePartB(id);
			return ResponseEntity.ok().contentLength(file.contentLength())
					.header("Content-Disposition", "attachment; filename=" + file.getFilename())
					.contentType(MediaType.APPLICATION_PDF).body(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@PutMapping("/part-b-vat")
	public ResponseEntity<IANonComplianceOfTDSNVATDTO> createUpdateTDSVATStatute(
			@RequestBody IANonComplianceOfTDSNVATDTO compliance) {
		return ResponseEntity.ok(auditService.createUpdateTDSVATStatute(compliance));
	}

	@GetMapping("/part-b-vat/{id}/ia/{iaId}")
	public ResponseEntity<IANonComplianceOfTDSNVATDTO> getTDSVATStatuteOtherById(@PathVariable Long id,
			@PathVariable Long iaId, @RequestParam("type") IANonComplianceOfTDSNVATEnum type) {
		return ResponseEntity.ok(auditService.getTDSVATStatuteOtherById(id, iaId, type));
	}

	@GetMapping("/part-b-vat-main/{iaId}")
	public ResponseEntity<IANonComplianceOfTDSNVATDTO> getTDSVATStatuteByIaId(@PathVariable Long iaId,
			@RequestParam("type") IANonComplianceOfTDSNVATEnum type) {
		return ResponseEntity.ok(auditService.getTDSVATStatuteByIaId(iaId, type));
	}

	@GetMapping("/part-b-vat/{iaId}")
	public ResponseEntity<List<IANonComplianceOfTDSNVATDTO>> getAllTDSVATStatuteOfTypeOfTypeOthers(
			@PathVariable Long iaId) {
		return ResponseEntity.ok(auditService.getAllTDSVATStatuteOfTypeOfTypeOthers(iaId));
	}

	@DeleteMapping("/part-b-vat-main/{iaId}")
	public ResponseEntity<Boolean> deletePartBTDSVATStatute(@PathVariable Long iaId,
			@RequestParam("type") IANonComplianceOfTDSNVATEnum type) {
		return ResponseEntity.ok(auditService.deletePartBTDSVATStatute(iaId, type));
	}

	@DeleteMapping("/part-b-vat-others/id/{id}/others/{iaId}")
	public ResponseEntity<Boolean> deletePartBTDSVATStatute(@PathVariable Long iaId, @PathVariable Long id) {
		return ResponseEntity.ok(auditService.deletePartBTDSVATStatuteOthers(id, iaId));
	}

	@PutMapping("/part-b-vat/upload/{id}/ia/{iaId}")
	public ResponseEntity<String> uploadPartBTDSVATStatute(@RequestPart("file") MultipartFile file,
			@PathVariable Long id, @PathVariable Long iaId, @RequestParam("type") IANonComplianceOfTDSNVATEnum type) {
		return ResponseEntity.ok(auditService.uploadPartBTDSVATStatute(file, id, iaId, type));
	}

	@GetMapping("/part-b-vat/download/{id}")
	public ResponseEntity<Resource> getTDSVATPartB(@PathVariable String id) {
		try {
			Resource file = auditService.getTDSVATPartB(id);
			return ResponseEntity.ok().contentLength(file.contentLength())
					.header("Content-Disposition", "attachment; filename=" + file.getFilename())
					.contentType(MediaType.APPLICATION_PDF).body(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@PutMapping("/part-b-utilisation")
	public ResponseEntity<IAUtilisationOfGrantsLineItemDTO> createUpdateUtilisationOfGrantLineItem(
			@RequestBody IAUtilisationOfGrantsLineItemDTO lineItemDTO) {
		return ResponseEntity.ok(auditService.createUpdateUtilisationOfGrantLineItem(lineItemDTO));
	}

	@GetMapping("/part-b-utilisation/{id}/ia/{iaId}")
	public ResponseEntity<IAUtilisationOfGrantsLineItemDTO> getLineItemById(@PathVariable Long id, @PathVariable Long iaId) {
		return ResponseEntity.ok(auditService.getLineItemById(id,iaId));
	}

	@GetMapping("/part-b-utilisation/ia/{iaId}")
	public ResponseEntity<List<IAUtilisationOfGrantsLineItemDTO>> getAllLineItemsByIaId(@PathVariable Long iaId) {
		return ResponseEntity.ok(auditService.getAllLineItemsByIaId(iaId));
	}

	@DeleteMapping("/part-b-utilisation/{id}/ia/{iaId}")
	public ResponseEntity<Boolean> deleteLineItem(@PathVariable Long id, @PathVariable Long iaId) {
		return ResponseEntity.ok(auditService.deleteLineItem(id, iaId));
	}

	@PutMapping("/part-b-utilisation/file/{iaId}")
	public ResponseEntity<String> uploadFileUtilizationOfGrantLine(@RequestPart MultipartFile file, @PathVariable Long iaId) {
		return ResponseEntity.ok(auditService.uploadFileUtilizationOfGrantLine(file, iaId));
	}

	@GetMapping("/part-b-utilisation/file/{iaId}")
	public ResponseEntity<Resource> getUtilizationOfGrantLineItemFile(@PathVariable Long iaId) {
		try {
			Resource file = auditService.getUtilizationOfGrantLineItemFile(iaId);
			return ResponseEntity.ok().contentLength(file.contentLength())
					.header("Content-Disposition", "attachment; filename=" + file.getFilename())
					.contentType(MediaType.APPLICATION_PDF).body(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@PutMapping("/part-c")
	public ResponseEntity<IAPartCAuditObservationDTO> createUpdatePartC(
			@RequestBody IAPartCAuditObservationDTO partCDTO) {
		return ResponseEntity.ok(auditService.createUpdatePartC(partCDTO));
	}

	@GetMapping("/part-c/{iaId}")
	public ResponseEntity<IAPartCAuditObservationDTO> getPartCString(@PathVariable Long iaId,
			@RequestParam IAPartCAuditObservationEnum partCEnum) {
		return ResponseEntity.ok(auditService.getPartCString(iaId, partCEnum));
	}

}
