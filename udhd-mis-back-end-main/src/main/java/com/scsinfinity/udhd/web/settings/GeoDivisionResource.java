/**
 * 
 */
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
import com.scsinfinity.udhd.services.settings.dto.GeoDivisionDTO;
import com.scsinfinity.udhd.services.settings.interfaces.IGeoDivisionService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author aditya-server
 *
 *         03-Sep-2021 -- 6:49:40 pm
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/settings/division")
public class GeoDivisionResource {

	private final IGeoDivisionService geoDivisionService;

	/**
	 * getGeoDivisionById
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<GeoDivisionDTO> getGeoDivisionById(@PathVariable Long id) {
		log.info("getPublisherById", id);
		return ResponseEntity.ok(geoDivisionService.findByGeoDivisionId(id));
	}

	/**
	 * ggetGeoDivisionByPage
	 * 
	 * @param data
	 * @return
	 */
	@PostMapping("/info")
	public ResponseEntity<GenericResponseObject<GeoDivisionDTO>> getGeoDivisionByPage(
			@Valid @RequestBody Pagination<GeoDivisionDTO> data) {
		log.info("getPublisherByPage", data);
		return ResponseEntity.ok(geoDivisionService.loadGeoDivisionByPage(data));
	}

	/**
	 * createUpdateGeoDivision
	 * 
	 * @param geoDivisionDTO
	 * @return
	 */
	@PostMapping("/save")
	public ResponseEntity<GeoDivisionDTO> createUpdateGeoDivision(@Valid @RequestBody GeoDivisionDTO geoDivisionDTO) {
		log.info("createUpdatePublisher", geoDivisionDTO);
		if (geoDivisionDTO.getId() == null) {
			geoDivisionDTO.setActive(true);
			return ResponseEntity.ok(geoDivisionService.createGeoDivision(geoDivisionDTO));
		}

		return ResponseEntity.ok(geoDivisionService.updateGeoDivision(geoDivisionDTO));
	}

	/**
	 * deleteGeoDivisionById
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/save/{id}")
	public ResponseEntity<GeoDivisionDTO> deleteGeoDivisionById(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(geoDivisionService.deactivate(id));
	}

}
