package com.scsinfinity.udhd.web.audit.internalaudit;

import java.util.List;
import javax.validation.Valid;
import com.scsinfinity.udhd.services.audit.dto.IAAuditCommentDTO;
import com.scsinfinity.udhd.services.common.BooleanResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAStep1RequestDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAStep1ResponseDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IInternalAuditService;
import com.scsinfinity.udhd.services.common.GenericResponseObject;
import com.scsinfinity.udhd.services.common.Pagination;
import com.scsinfinity.udhd.services.settings.dto.ULBDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/audit/ia/step1")
public class InternalAuditResource {

	private final IInternalAuditService internalAuditService;

	@PutMapping("/create-step1-report")
	public ResponseEntity<IAStep1ResponseDTO> createStep1(@RequestBody IAStep1RequestDTO step1RequestDTO) {
		log.debug("IAStep1ResponseDTO");
		return ResponseEntity.ok(internalAuditService.createIAWithStep1(step1RequestDTO));
	}

	@GetMapping("/ulbs")
	public ResponseEntity<List<ULBDTO>> getULBsForWhichIAIsAllowed() {
		log.debug("getULBsForWhichIAIsAllowed");
		return ResponseEntity.ok(internalAuditService.getULBsAllowedForIA());
	}

	@GetMapping("/{ia-id}")
	public ResponseEntity<IAStep1ResponseDTO> getStep1ById(@PathVariable("ia-id") Long id) {
		log.debug("getStep1ById");
		return ResponseEntity.ok(internalAuditService.findInternalAuditById(id));
	}

	@PostMapping("/info")
	public ResponseEntity<GenericResponseObject<IAStep1ResponseDTO>> getStep1ByPage(
			@Valid @RequestBody Pagination<IAStep1ResponseDTO> data) {
		log.debug("getStep1ByPage");
		return ResponseEntity.ok(internalAuditService.loadInternalAuditByPage(data));
	}

	@DeleteMapping("/{ia-id}")
	public ResponseEntity<BooleanResponseDTO> deleteIA(@PathVariable("ia-id") Long id) {
		log.debug("deleteIA");
		return ResponseEntity.ok(internalAuditService.deleteIA(id));
	}



	@PutMapping("/send-ia-for-review-slpmu/{id}")
	public ResponseEntity<BooleanResponseDTO> sendIAForReviewToSLPMU(@PathVariable Long id){
		log.info("sendForReview",id);
		return ResponseEntity.ok(internalAuditService.sendInternalAuditForReviewToSLPMU(id));
	}

	@PostMapping("/gi")
	public ResponseEntity<GenericResponseObject<IAStep1ResponseDTO>> getIAAuditPendingWithSLPMUUnassigned(
			@RequestBody Pagination<IAStep1ResponseDTO> data) {
		log.info("getIAAuditPendingWithSLPMUUnassigned", data);
		return ResponseEntity.ok(internalAuditService.loadIAAuditUnassingedByPage(data));
	}


	@PutMapping("/assign-to-me/{iaAuditId}")
	public ResponseEntity<IAStep1ResponseDTO> assignIAToMe(@PathVariable Long iaAuditId){
		log.info("assignIAToULB",iaAuditId);
		return ResponseEntity.ok(internalAuditService.assignIAAuditToMe(iaAuditId));
	}

	@PostMapping("/comment-by-slpmu-audit/{iaId}")
	public ResponseEntity<IAAuditCommentDTO> createCommentAfterReviewForSLPMU(@PathVariable Long iaId, @RequestBody String comment){
		log.info("createCommentAfterReviewForCriteriaForSLPMU",iaId);
		return ResponseEntity.ok(internalAuditService.addCommentsForIAAuditReviewBySLPMUAndInternalAuditor(iaId,comment));
	}

	@GetMapping("/comment-by-slpmu-audit/{iaId}")
	public ResponseEntity<List> getCommentsForIA(@PathVariable Long iaId){
		log.info("getCommentsForIA",iaId);
		return ResponseEntity.ok(internalAuditService.getCommentsForIA(iaId));
	}

	@PutMapping("/approve-reject/{id}/{view}")
	public ResponseEntity<IAStep1ResponseDTO> approveOrRejectAudit(@PathVariable Long id, @PathVariable Boolean view){
		log.info("approveOrRejectAudit",id);
		return ResponseEntity.ok(internalAuditService.approveOrRejectBySLPMUAudit(view,id));
	}

	@PostMapping("/get-ia-assigned-to-me")
	public ResponseEntity<GenericResponseObject<IAStep1ResponseDTO>> getIAAssignedToMe(@RequestBody Pagination<IAStep1ResponseDTO> data){
		log.info("getIAAssignedToMe",data);
		return ResponseEntity.ok(internalAuditService.loadIAAuditAssingedToMeByPage(data));
	}


}
