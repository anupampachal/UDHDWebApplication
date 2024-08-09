package com.scsinfinity.udhd.web.settings;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scsinfinity.udhd.services.common.GenericResponseObject;
import com.scsinfinity.udhd.services.common.Pagination;
import com.scsinfinity.udhd.services.settings.dto.HolidayCalendarDTO;
import com.scsinfinity.udhd.services.settings.interfaces.IHolidayCalendarService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/settings/holiday-calendar")
public class HolidayCalendarResource {
	private final IHolidayCalendarService holidayCalendarService;

	/**
	 * getHolidayCalendarById
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping
	public ResponseEntity<HolidayCalendarDTO> getHolidayCalendarById(@RequestParam Long id) {
		log.info("getHolidayCalendarById", id);
		return ResponseEntity.ok(holidayCalendarService.findByHolidayId(id));
	}

	/**
	 * getHolidayCalendarByPage
	 * 
	 * @param data
	 * @return
	 */
	@PostMapping("/info")
	public ResponseEntity<GenericResponseObject<HolidayCalendarDTO>> getHolidayCalendarByPage(
			@RequestBody Pagination<HolidayCalendarDTO> data) {
		log.info("getHolidayCalendarByPage", data);
		return ResponseEntity.ok(holidayCalendarService.loadHolidayByPage(data));
	}

	/**
	 * createUpdateHolidayCalendar
	 * 
	 * @param holidayCalendarDto
	 * @return
	 */
	@PostMapping("/save")
	public ResponseEntity<HolidayCalendarDTO> createUpdateHolidayCalendar(
			@RequestBody HolidayCalendarDTO holidayCalendarDto) {
		log.info("createUpdateHolidayCalendar", holidayCalendarDto);
		return ResponseEntity.ok(holidayCalendarService.createUpdateHoliday(holidayCalendarDto));
	}

	/**
	 * deleteHolidayCalendarById
	 * 
	 * @param locationid
	 * @return
	 */
	@DeleteMapping
	public ResponseEntity<Object> deleteHolidayCalendarById(@RequestParam Long id) {
		holidayCalendarService.deleteHoliday(id);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
}
