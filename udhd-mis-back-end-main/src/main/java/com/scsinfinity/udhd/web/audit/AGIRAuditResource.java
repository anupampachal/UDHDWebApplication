package com.scsinfinity.udhd.web.audit;

import java.util.List;

import com.scsinfinity.udhd.exceptions.BadRequestAlertException;
import com.scsinfinity.udhd.services.common.BooleanResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.scsinfinity.udhd.services.audit.agir.dto.AGIRAuditDTO;
import com.scsinfinity.udhd.services.audit.agir.dto.AGIRCommentDTO;
import com.scsinfinity.udhd.services.audit.agir.dto.AGIRComplianceDTO;
import com.scsinfinity.udhd.services.audit.agir.dto.AGIRCriteriaDTO;
import com.scsinfinity.udhd.services.audit.agir.interfaces.IAGIRAuditService;
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
@RequestMapping("/api/audit/agir")
public class AGIRAuditResource {

    private final IAGIRAuditService agirAuditService;

    @GetMapping("/get-audit/{id}")
    public ResponseEntity<AGIRAuditDTO> getAGIRAuditByID(@PathVariable Long id) {
        return ResponseEntity.ok(agirAuditService.findAuditById(id));
    }

    @GetMapping("/get-criteria/{id}")
    public ResponseEntity<AuditCriteriaDTO> getAGIRAuditCriterirByID(@PathVariable Long id) {
        return ResponseEntity.ok(agirAuditService.findAuditCriteriaById(id));
    }

    @GetMapping("/get-compliance/{id}")
    public ResponseEntity<AuditComplianceDTO> getAGIRAuditComplianceByID(@PathVariable Long id) {
        return ResponseEntity.ok(agirAuditService.findAuditComplianceById(id));
    }

    @GetMapping("/get-comment/{id}")
    public ResponseEntity<AuditCommentDTO> getAGIRAuditCommentByID(@PathVariable Long id) {
        return ResponseEntity.ok(agirAuditService.findAuditCommentById(id));
    }

    @GetMapping("/get-ulb-list")
    public ResponseEntity<ULBDTO> getULBForCurrentUser() {
        return ResponseEntity.ok(agirAuditService.getULBsForCreateUpdate());
    }

    /**
     * Load page wise AGIRAudit data with searching, sorting and filtering
     *
     * @param data
     * @return AGIRAuditDTO data in paginated format
     */
    @PostMapping("/info-audit")
    public ResponseEntity<GenericResponseObject<AGIRAuditDTO>> getAGIRAuditByPage(
            @RequestBody Pagination<AGIRAuditDTO> data) {
        log.info("getAGIRAuditByPage", data);
        return ResponseEntity.ok(agirAuditService.loadAGIRAuditByPage(data));
    }

    /**
     * Load page wise AGIRAuditCriteria data with searching, sorting and filtering
     *
     * @param data
     * @return AuditCriteriaDTO data in paginated format
     */
    @PostMapping("/info-criteria")
    public ResponseEntity<GenericResponseObject<AuditCriteriaDTO>> getAGIRAuditCriteriaByPage(
            @RequestBody Pagination<AuditCriteriaDTO> data) {
        log.info("getAGIRAuditCriteriaByPage", data);
        return ResponseEntity.ok(agirAuditService.loadCriteriasByPage(data));
    }

    /**
     * Load page wise AGIRAuditCompliance data with searching, sorting and filtering
     *
     * @param data
     * @return AGIRAuditComplianceDTO data in paginated format
     */
    @PostMapping("/info-compliance")
    public ResponseEntity<GenericResponseObject<AuditComplianceDTO>> getAGIRAuditCompliancesByPage(
            @RequestBody Pagination<AuditComplianceDTO> data) {
        log.info("getAGIRAuditCompliancesByPage", data);
        return ResponseEntity.ok(agirAuditService.loadCompliancesByPage(data));
    }

    /**
     * Load page wise AGIRAuditComments data with searching, sorting and filtering
     *
     * @param data
     * @return AGIRAuditDTO data in paginated format
     */
    @PostMapping("/info-comments")
    public ResponseEntity<GenericResponseObject<AuditCommentDTO>> getAGIRAuditCommentByPage(
            @RequestBody Pagination<AuditCommentDTO> data) {
        log.info("getAGIRAuditCompliancesByPage", data);
        return ResponseEntity.ok(agirAuditService.loadCommentsByPage(data));
    }

    /**
     * createUpdateAGIRAudit
     *
     * @param agirAuditDTO
     * @return newly created AGIRAuditDTO
     */
    @PostMapping("/save")
    public ResponseEntity<AGIRAuditDTO> createUpdateAGIRAudit(@RequestBody AGIRAuditDTO agirAuditDTO) {
        log.info("createUpdateAGIRAudit", agirAuditDTO);
        return ResponseEntity.ok(agirAuditService.createUpdateBasicAGIRAudit(agirAuditDTO));
    }

    /**
     * delete agiraudit
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BooleanResponseDTO> deleteAGIRAudit(@PathVariable Long id) {
        log.info("deleteAGIRAudit", id);
        BooleanResponseDTO response = null;
        try{
           Boolean deleteResponse= agirAuditService.deleteBasicAGIRAudit(id);
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
    public ResponseEntity<AGIRCriteriaDTO> createUpdateAGIRAuditCriteria(@RequestBody AGIRCriteriaDTO criteriaDTO) {
        log.info("createUpdateAGIRAuditCriteria", criteriaDTO);
        return ResponseEntity.ok(agirAuditService.createUpdateCriteriaForAGIRAudit(criteriaDTO));
    }




    @DeleteMapping("/criteria/{id}")
    public ResponseEntity<BooleanResponseDTO> deleteAGIRAuditCriteria(@PathVariable Long id) {
        log.info("deleteAGIRAuditCriteria", id);
        log.info("deleteAGIRAudit", id);
        BooleanResponseDTO response = null;
        try{
            Boolean deleteResponse= agirAuditService.deleteAGIRAuditCriterion(id);
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
    public ResponseEntity<AGIRComplianceDTO> createUpdateAGIRAuditCompliance(
            @RequestBody AGIRComplianceDTO complianceDTO) {
        log.info("createUpdateAGIRAuditCompliance", complianceDTO);
        return ResponseEntity.ok(agirAuditService.createUpdateComplianceForAGIRAuditCriteria(complianceDTO));
    }

    /**
     * create or update complianceDTO
     *
     * @param commentDTO
     * @return newly created complianceDTO
     */
    @PostMapping("/comment")
    public ResponseEntity<AGIRCommentDTO> createUpdateAGIRAuditComment(@RequestBody AGIRCommentDTO commentDTO) {
        log.info("createUpdateAGIRAuditComment", commentDTO);
        return ResponseEntity.ok(agirAuditService.createUpdateAGIRCommentForAGIRAuditCriteria(commentDTO));
    }

    @PostMapping("/compliance-list")
    public ResponseEntity<List<AuditComplianceDTO>> getComplianceForCriteria(@RequestBody AGIRCriteriaDTO criteria) {
        log.info("getComplianceForCriteria", criteria);
        return ResponseEntity.ok(agirAuditService.getComplianceForCriteria(criteria));
    }

    @PostMapping("/comment-list")
    public ResponseEntity<List<AGIRCommentDTO>> getCommentForCriteria(@RequestBody AGIRCriteriaDTO criteria) {
        log.info("getCommentForCriteria", criteria);
        return ResponseEntity.ok(agirAuditService.getCommentsForCriteria(criteria));
    }

    @PutMapping("/send-agir-for-review-slpmu/{id}")
    public ResponseEntity<BooleanResponseDTO> sendAGIRForReviewToSLPMU(@PathVariable Long id){
        log.info("sendForReview",id);
        return ResponseEntity.ok(agirAuditService.sendAGIRAuditForReviewToSLPMU(id));
    }

    @PostMapping("/get-agir-audit-unassigned")
    public ResponseEntity<GenericResponseObject<AGIRAuditDTO>> getAGIRAuditPendingWithSLPMUUnassigned(
            @RequestBody Pagination<AGIRAuditDTO> data) {
        log.info("getAGIRAuditPendingWithSLPMUUnassigned", data);
        return ResponseEntity.ok(agirAuditService.loadAGIRAuditUnassingedByPage(data));
    }


    @PutMapping("/assign-to-me/{agirAuditId}")
    public ResponseEntity<AGIRAuditDTO> assignAGIRToMe(@PathVariable Long agirAuditId){
        log.info("assignAGIRToULB",agirAuditId);
        return ResponseEntity.ok(agirAuditService.assignAGIRAuditToMe(agirAuditId));
    }

    @PostMapping("/comment-by-slpmu-audit-for-category/{categoryId}")
    public ResponseEntity<AuditCommentDTO> createCommentAfterReviewForCriteriaForSLPMU(@PathVariable Long categoryId, @RequestBody String comment){
        log.info("createCommentAfterReviewForCriteriaForSLPMU",categoryId);
        return ResponseEntity.ok(agirAuditService.addCommentsForAGIRAuditReviewBySLPMU(categoryId,comment));
    }

    @PutMapping("/approve-reject/{id}/{view}")
    public ResponseEntity<AGIRAuditDTO> approveOrRejectAudit(@PathVariable Long id, @PathVariable Boolean view){
        log.info("approveOrRejectAudit",id);
        return ResponseEntity.ok(agirAuditService.approveOrRejectBySLPMUAudit(view,id));
    }

    @PostMapping("/get-agir-assigned-to-me")
    public ResponseEntity<GenericResponseObject<AGIRAuditDTO>> getAGIRAssignedToMe(@RequestBody Pagination<AGIRAuditDTO> data){
        log.info("getAGIRAssignedToMe",data);
        return ResponseEntity.ok(agirAuditService.loadAGIRAuditAssingedToMeByPage(data));
    }

}
