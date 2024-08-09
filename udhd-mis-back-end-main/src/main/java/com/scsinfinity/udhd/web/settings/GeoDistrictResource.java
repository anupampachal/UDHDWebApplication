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
import com.scsinfinity.udhd.services.settings.dto.GeoDistrictDTO;
import com.scsinfinity.udhd.services.settings.interfaces.IGeoDistrictService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/settings/district")
public class GeoDistrictResource {

	private final IGeoDistrictService geoDistrictService;

	/**
	 * getGeoDistrictById
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<GeoDistrictDTO> getGeoDistrictById(@PathVariable Long id) {
		log.info("getPublisherById", id);
		return ResponseEntity.ok(geoDistrictService.findByGeoDistrictId(id));
	}
	/**
	 * getGeoDistrictByPage
	 * 
	 * @param data
	 * @return
	 */
	@PostMapping("/info")
	public ResponseEntity<GenericResponseObject<GeoDistrictDTO>> getGeoDistrictByPage(
			@Valid @RequestBody Pagination<GeoDistrictDTO> data) {
		log.info("getPublisherByPage", data);
		return ResponseEntity.ok(geoDistrictService.loadGeoDistrictByPage(data));
	}

	/**
	 * createUpdateGeoDistrict
	 * 
	 * @param geoDistrictDTO
	 * @return
	 */
	@PostMapping("/save")
	public ResponseEntity<GeoDistrictDTO> createUpdateGeoDistrict(@Valid @RequestBody GeoDistrictDTO geoDistrictDTO) {
		log.info("createUpdatePublisher", geoDistrictDTO);
		if (geoDistrictDTO.getId() == null)
			geoDistrictDTO.setActive(true);
		return ResponseEntity.ok(geoDistrictService.createUpdateGeoDistrict(geoDistrictDTO));
	}

	/**
	 * deleteGeoDistrictById
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/save/{id}")
	public ResponseEntity<GeoDistrictDTO> deleteGeoDistrictById(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(geoDistrictService.deactivate(id));
	}

	/**
	 * getGeoDistrictByPageForDivision
	 * 
	 * @param data
	 * @return
	 */
	@PostMapping("/info-div")
	public ResponseEntity<GenericResponseObject<GeoDistrictDTO>> getGeoDistrictByPageForDivision(
			@Valid @RequestBody Pagination<GeoDistrictDTO> data) {
		log.info("getGeoDistrictByPageForDivision", data);
		return ResponseEntity.ok(geoDistrictService.loadGeoDistrictByPageForDivision(data));
	}

}