
package com.scsinfinity.udhd.web.settings;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scsinfinity.udhd.services.common.GenericResponseObject;
import com.scsinfinity.udhd.services.common.Pagination;
import com.scsinfinity.udhd.services.settings.dto.ULBDTO;
import com.scsinfinity.udhd.services.settings.interfaces.IULBService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/settings/ulb")
public class 	ULBResource {
	private final IULBService ulbService;

	/**
	 * getUlbById
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<ULBDTO> getUlbById(@PathVariable Long id) {
		log.info("getPublisherById", id);
		return ResponseEntity.ok(ulbService.findByULBId(id));
	}

	/**
	 * getUlbByPage
	 * 
	 * @param data
	 * @return
	 */
	@PostMapping("/info")
	public ResponseEntity<GenericResponseObject<ULBDTO>> getUlbByPage(@Valid @RequestBody Pagination<ULBDTO> data) {
		log.info("getPublisherByPage", data);
		return ResponseEntity.ok(ulbService.loadULBByPage(data));
	}

	/**
	 * createUpdateUlb
	 * 
	 * @param geoDistrictDTO
	 * @return
	 */
	@PostMapping("/save")
	public ResponseEntity<ULBDTO> createUpdateUlb(@Valid @RequestBody ULBDTO geoDistrictDTO) {
		log.info("createUpdatePublisher", geoDistrictDTO);
		if (geoDistrictDTO.getId() == null)
			geoDistrictDTO.setActive(true);
		return ResponseEntity.ok(ulbService.createUpdateULB(geoDistrictDTO));
	}

	/**
	 * deleteUlbById
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/save/{id}")
	public ResponseEntity<ULBDTO> deleteUlbById(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(ulbService.deactivate(id));
	}

	/**
	 * getUlbByPageForDivision
	 * 
	 * @param data
	 * @return
	 */
	@PostMapping("/info-dis")
	public ResponseEntity<GenericResponseObject<ULBDTO>> getUlbByPageForDivision(@Valid @RequestBody Pagination<ULBDTO> data) {
		log.info("getUlbByPageForDivision", data);
		return ResponseEntity.ok(ulbService.loadULBByPageForDistrict(data));
	}

}
