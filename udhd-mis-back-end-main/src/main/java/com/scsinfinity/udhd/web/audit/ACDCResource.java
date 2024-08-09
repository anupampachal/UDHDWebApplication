package com.scsinfinity.udhd.web.audit;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scsinfinity.udhd.services.audit.acdc.dto.ACDCULBBasedDTO;
import com.scsinfinity.udhd.services.audit.acdc.interfaces.IACDCService;
import com.scsinfinity.udhd.services.common.GenericResponseObject;
import com.scsinfinity.udhd.services.common.Pagination;
import com.scsinfinity.udhd.services.settings.dto.ULBDTO;
import com.scsinfinity.udhd.services.settings.interfaces.IULBService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/audit/ac-dc")
public class ACDCResource {

	private final IACDCService acDCService;
	private final IULBService ulbService;

	public ACDCResource(IACDCService acDCService, IULBService ulbService) {
		this.acDCService = acDCService;
		this.ulbService = ulbService;
	}

	/**
	 * Find acdc using its acdc id
	 * 
	 * @pathvariable acDCId
	 * @return ACDCULBBasedDTO
	 */
	@GetMapping("/{acDCId}")
	public ResponseEntity<ACDCULBBasedDTO> getACDCById(@PathVariable Long acDCId) {
		log.info("getACDCById", acDCId);
		return ResponseEntity.ok(acDCService.findById(acDCId));
	}

	/**
	 * Load page wise ACDCULBBasedDTO data with searching, sorting and filtering
	 * 
	 * @param data
	 * @return ACDCULBBasedDTO data in paginated format
	 */
	@PostMapping("/post/info")
	public ResponseEntity<GenericResponseObject<ACDCULBBasedDTO>> getACDCByPage(
			@RequestBody Pagination<ACDCULBBasedDTO> data) {
		log.info("getUserGroupByPage", data);
		return ResponseEntity.ok(acDCService.loadACDCByPage(data));
	}

	/**
	 * create or update ACDCULBBasedDTO
	 * 
	 * @param acdc
	 * @return newly created ACDCULBBasedDTO
	 */
	@PostMapping("/post/save")
	public ResponseEntity<ACDCULBBasedDTO> createUpdateACDC(@RequestBody ACDCULBBasedDTO acdc) {
		log.info("createUpdateUserGroup", acdc);
		return ResponseEntity.ok(acDCService.createUpdateACDC(acdc));
	}

	/**
	 * Delete ACDCULBBasedDTO by id
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteACDCById(@PathVariable Long id) {
		ACDCULBBasedDTO acdc = acDCService.deactivate(id);
		return ResponseEntity.status(HttpStatus.OK).body(acdc);
	}

	/**
	 * get ulb list for crerating of acdc bill
	 */

	@GetMapping("/ulb")
	public ResponseEntity<List<ULBDTO>> getULBs() {
		return ResponseEntity.ok(ulbService.getAllActiveULBs());
	}

}