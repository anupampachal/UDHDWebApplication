package com.scsinfinity.udhd.web.audit.internalaudit;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scsinfinity.udhd.services.audit.internalaudit.dto.AdminAuditTeamTypeEnum;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAAdministrationAuditTeamDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAAdministrationDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IIAAdministrationService;
import com.scsinfinity.udhd.web.audit.requests.IAAdministrationRequest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/audit/internal-audit/step3")
public class IAAdministrationResource {

	private final IIAAdministrationService administrationService;

	@GetMapping("/{id}")
	public ResponseEntity<IAAdministrationDTO> retrieveAdministration(@PathVariable Long id) {
		log.debug("retrieveAdministration");
		return ResponseEntity.ok(administrationService.retrieveAdministration(id));
	}

	@PostMapping
	public ResponseEntity<IAAdministrationDTO> createAdministration(
			@RequestBody IAAdministrationRequest administrationRequest) {
		log.debug("createAdministration");
		IAAdministrationDTO administrationDTO = administrationService
				.createAdministration(createAdministrationDto(administrationRequest));
		return ResponseEntity.ok(administrationDTO);
	}

	@PutMapping("/{iaId}")
	public ResponseEntity<IAAdministrationAuditTeamDTO> createAuditTeamMemberByType(@PathVariable Long iaId,
			@RequestBody IAAdministrationAuditTeamDTO teamMember) {
		return ResponseEntity.ok(administrationService.createAuditTeamMemberByType(iaId, teamMember));
	}

	@GetMapping("/member/{iaId}")
	public ResponseEntity<List<IAAdministrationAuditTeamDTO>> getListByType(@PathVariable Long iaId,
			@RequestParam AdminAuditTeamTypeEnum type) {
		return ResponseEntity.ok(administrationService.getListByType(iaId, type));
	}

	@GetMapping("/member/mem/{id}/ia/{iaId}")
	public ResponseEntity<IAAdministrationAuditTeamDTO> getMemberByTypeAndId(@PathVariable Long iaId,
			@PathVariable Long id, @RequestParam AdminAuditTeamTypeEnum type) {
		return ResponseEntity.ok(administrationService.getMemberByTypeAndId(iaId, id, type));
	}

	@DeleteMapping("/member/mem/{id}/ia/{iaId}")
	public ResponseEntity<Boolean> deleteMemberByTypeAndId(@PathVariable Long iaId, @PathVariable Long id,
			@RequestParam AdminAuditTeamTypeEnum type) {
		return ResponseEntity.ok(administrationService.deleteMemberByType(iaId, id, type));
	}

	private IAAdministrationDTO createAdministrationDto(IAAdministrationRequest administrationRequest) {

		return IAAdministrationDTO.builder().internalAuditId(administrationRequest.getInternalAuditId())
				.nameOfChairmanMayor(administrationRequest.getNameOfChairmanMayor())
				.periodOfServiceChairman(administrationRequest.getPeriodOfServiceChairman())
				.nameOfCMO(administrationRequest.getNameOfCMO())
				.periodOfServiceCMO(administrationRequest.getPeriodOfServiceCMO()).build();
	}

}
