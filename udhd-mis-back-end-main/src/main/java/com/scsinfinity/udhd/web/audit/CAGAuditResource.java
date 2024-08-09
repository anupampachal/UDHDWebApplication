package com.scsinfinity.udhd.web.audit;

import java.util.List;

import com.scsinfinity.udhd.exceptions.BadRequestAlertException;
import com.scsinfinity.udhd.services.common.BooleanResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.scsinfinity.udhd.services.audit.cag.dto.CAGAuditDTO;
import com.scsinfinity.udhd.services.audit.cag.dto.CAGCommentDTO;
import com.scsinfinity.udhd.services.audit.cag.dto.CAGComplianceDTO;
import com.scsinfinity.udhd.services.audit.cag.dto.CAGCriteriaDTO;
import com.scsinfinity.udhd.services.audit.cag.interfaces.ICAGAuditService;
import com.scsinfinity.udhd.services.audit.dto.AuditCommentDTO;
import com.scsinfinity.udhd.services.audit.dto.AuditComplianceDTO;
import com.scsinfinity.udhd.services.audit.dto.AuditCriteriaDTO;
import com.scsinfinity.udhd.services.common.GenericResponseObject;
import com.scsinfinity.udhd.services.common.Pagination;
import com.scsinfinity.udhd.services.settings.dto.ULBDTO;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/audit/cag")
public class CAGAuditResource {

	private final ICAGAuditService cagAuditService;

	@GetMapping("/get-audit/{id}")
	public ResponseEntity<CAGAuditDTO> getCAGAuditByID(@PathVariable Long id) {
		return ResponseEntity.ok(cagAuditService.findAuditById(id));
	}

	@GetMapping("/get-criteria/{id}")
	public ResponseEntity<AuditCriteriaDTO> getCAGAuditCriterirByID(@PathVariable Long id) {
		return ResponseEntity.ok(cagAuditService.findAuditCriteriaById(id));
	}

	@GetMapping("/get-compliance/{id}")
	public ResponseEntity<AuditComplianceDTO> getCAGAuditComplianceByID(@PathVariable Long id) {
		return ResponseEntity.ok(cagAuditService.findAuditComplianceById(id));
	}

	@GetMapping("/get-comment/{id}")
	public ResponseEntity<AuditCommentDTO> getCAGAuditCommentByID(@PathVariable Long id) {
		return ResponseEntity.ok(cagAuditService.findAuditCommentById(id));
	}

	@GetMapping("/get-ulb-list")
	public ResponseEntity<ULBDTO> getULBForCurrentUser() {
		return ResponseEntity.ok(cagAuditService.getULBsForCreateUpdate());
	}

	/**
	 * Load page wise CAGAudit data with searching, sorting and filtering
	 * 
	 * @param data
	 * @return CAGAuditDTO data in paginated format
	 */
	@PostMapping("/info-audit")
	public ResponseEntity<GenericResponseObject<CAGAuditDTO>> getCAGAuditByPage(
			@RequestBody Pagination<CAGAuditDTO> data) {
		log.info("loadCAGAuditByPage", data);
		return ResponseEntity.ok(cagAuditService.loadCAGAuditByPage(data));
	}

	/**
	 * Load page wise CAGAuditCriteria data with searching, sorting and filtering
	 * 
	 * @param data
	 * @return AuditCriteriaDTO data in paginated format
	 */
	@PostMapping("/info-criteria")
	public ResponseEntity<GenericResponseObject<AuditCriteriaDTO>> getCAGAuditCriteriaByPage(
			@RequestBody Pagination<AuditCriteriaDTO> data) {
		log.info("getCAGAuditCriteriaByPage", data);
		return ResponseEntity.ok(cagAuditService.loadCriteriasByPage(data));
	}

	/**
	 * Load page wise CAGAuditCompliance data with searching, sorting and filtering
	 * 
	 * @param data
	 * @return CAGAuditComplianceDTO data in paginated format
	 */
	@PostMapping("/info-compliance")
	public ResponseEntity<GenericResponseObject<AuditComplianceDTO>> getCAGAuditCompliancesByPage(
			@RequestBody Pagination<AuditComplianceDTO> data) {
		log.info("getCAGAuditCompliancesByPage", data);
		return ResponseEntity.ok(cagAuditService.loadCompliancesByPage(data));
	}

	/**
	 * Load page wise CAGAuditComments data with searching, sorting and filtering
	 * 
	 * @param data
	 * @return CAGAuditDTO data in paginated format
	 */
	@PostMapping("/info-comments")
	public ResponseEntity<GenericResponseObject<AuditCommentDTO>> getCAGAuditCommentByPage(
			@RequestBody Pagination<AuditCommentDTO> data) {
		log.info("getCAGAuditCompliancesByPage", data);
		return ResponseEntity.ok(cagAuditService.loadCommentsByPage(data));
	}

	/**
	 * createUpdateCAGAudit
	 * 
	 * @param cagAuditDTO
	 * @return newly created CAGAuditDTO
	 */
	@PostMapping("/save")
	public ResponseEntity<CAGAuditDTO> createUpdateCAGAudit(@RequestBody CAGAuditDTO cagAuditDTO) {
		log.info("createUpdateCAGAudit", cagAuditDTO);
		return ResponseEntity.ok(cagAuditService.createUpdateBasicCAGAudit(cagAuditDTO));
	}

	/**
	 * delete cagaudit
	 * @param id
	 * @return
	 */
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<BooleanResponseDTO> deleteCAGAudit(@PathVariable Long id) {
		log.info("deleteCAGAudit", id);
		BooleanResponseDTO response = null;
		try{
			Boolean deleteResponse= cagAuditService.deleteBasicCAGAudit(id);
			response=new BooleanResponseDTO(deleteResponse,"Delete successful");
		}catch(BadRequestAlertException br){
			response=new BooleanResponseDTO(false,br.getDefaultMessage());
		}
		return ResponseEntity.ok(response);
	}


	/**
	 * create or update criteriaDTO
	 * 
	 * @param criteriaDTO
	 * @return newly created criteriaDTO
	 */
	@PostMapping("/criteria")
	public ResponseEntity<CAGCriteriaDTO> createUpdateCAGAuditCriteria(@RequestBody CAGCriteriaDTO criteriaDTO) {
		log.info("createUpdateCAGAuditCriteria", criteriaDTO);
		return ResponseEntity.ok(cagAuditService.createUpdateCriteriaForCAGAudit(criteriaDTO));
	}


	@DeleteMapping("/criteria/{id}")
	public ResponseEntity<BooleanResponseDTO> deleteCAGAuditCriteria(@PathVariable Long id) {
		log.info("deleteCAGAuditCriteria", id);

		BooleanResponseDTO response = null;
		try{
			Boolean deleteResponse= cagAuditService.deleteCAGAuditCriterion(id);
			response=new BooleanResponseDTO(deleteResponse,"Delete successful");
		}catch(BadRequestAlertException br){
			response=new BooleanResponseDTO(false,br.getDefaultMessage());
		}
		return ResponseEntity.ok(response);
	}


	/**
	 * create or update complianceDTO
	 * 
	 * @param complianceDTO
	 * @return newly created complianceDTO
	 */
	@PostMapping("/compliance")
	public ResponseEntity<CAGComplianceDTO> createUpdateCAGAuditCompliance(
			@RequestBody CAGComplianceDTO complianceDTO) {
		log.info("createUpdateCAGAuditCompliance", complianceDTO);
		return ResponseEntity.ok(cagAuditService.createUpdateComplianceForCAGAuditCriteria(complianceDTO));
	}

	/**
	 * create or update complianceDTO
	 * 
	 * @param commentDTO
	 * @return newly created complianceDTO
	 */
	@PostMapping("/comment")
	public ResponseEntity<CAGCommentDTO> createUpdateCAGAuditComment(@RequestBody CAGCommentDTO commentDTO) {
		log.info("createUpdateCAGAuditComment", commentDTO);
		return ResponseEntity.ok(cagAuditService.createUpdateCAGCommentForCAGAuditCriteria(commentDTO));
	}

	@PostMapping("/compliance-list")
	public ResponseEntity<List<AuditComplianceDTO>> getComplianceForCriteria(@RequestBody CAGCriteriaDTO criteria) {
		log.info("getComplianceForCriteria", criteria);
		return ResponseEntity.ok(cagAuditService.getComplianceForCriteria(criteria));
	}

	@PostMapping("/comment-list")
	public ResponseEntity<List<CAGCommentDTO>> getCommentForCriteria(@RequestBody CAGCriteriaDTO criteria) {
		log.info("getCommentForCriteria", criteria);
		return ResponseEntity.ok(cagAuditService.getCommentsForCriteria(criteria));
	}


	@PutMapping("/send-cag-for-review-slpmu/{id}")
	public ResponseEntity<BooleanResponseDTO> sendCAGForReviewToSLPMU(@PathVariable Long id){
		log.info("sendForReview",id);
		return ResponseEntity.ok(cagAuditService.sendCAGAuditForReviewToSLPMU(id));
	}

	@PostMapping("/get-agir-audit-unassigned")
	public ResponseEntity<GenericResponseObject<CAGAuditDTO>> getCAGAuditPendingWithSLPMUUnassigned(
			@RequestBody Pagination<CAGAuditDTO> data) {
		log.info("getCAGAuditPendingWithSLPMUUnassigned", data);
		return ResponseEntity.ok(cagAuditService.loadCAGAuditUnassingedByPage(data));
	}


	@PutMapping("/assign-to-me/{cagAuditId}")
	public ResponseEntity<CAGAuditDTO> assignCAGToMe(@PathVariable Long cagAuditId){
		log.info("assignCAGToULB",cagAuditId);
		return ResponseEntity.ok(cagAuditService.assignCAGAuditToMe(cagAuditId));
	}

	@PostMapping("/comment-by-slpmu-audit-for-category/{categoryId}")
	public ResponseEntity<AuditCommentDTO> createCommentAfterReviewForCriteriaForSLPMU(@PathVariable Long categoryId, @RequestBody String comment){
		log.info("createCommentAfterReviewForCriteriaForSLPMU",categoryId);
		return ResponseEntity.ok(cagAuditService.addCommentsForCAGAuditReviewBySLPMU(categoryId,comment));
	}

	@PutMapping("/approve-reject/{id}/{view}")
	public ResponseEntity<CAGAuditDTO> approveOrRejectAudit(@PathVariable Long id, @PathVariable Boolean view){
		log.info("approveOrRejectAudit",id);
		return ResponseEntity.ok(cagAuditService.approveOrRejectBySLPMUAudit(view,id));
	}

	@PostMapping("/get-cag-assigned-to-me")
	public ResponseEntity<GenericResponseObject<CAGAuditDTO>> getCAGAssignedToMe(@RequestBody Pagination<CAGAuditDTO> data){
		log.info("getCAGAssignedToMe",data);
		return ResponseEntity.ok(cagAuditService.loadCAGAuditAssingedToMeByPage(data));
	}



}
