package com.scsinfinity.udhd.services.settings;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.scsinfinity.udhd.dao.entities.activities.HolidayCalendarEntity;
import com.scsinfinity.udhd.dao.repositories.activities.IHolidayCalendarRepository;
import com.scsinfinity.udhd.exceptions.BadRequestAlertException;
import com.scsinfinity.udhd.services.common.GenericResponseObject;
import com.scsinfinity.udhd.services.common.Pagination;
import com.scsinfinity.udhd.services.settings.dto.HolidayCalendarDTO;
import com.scsinfinity.udhd.services.settings.interfaces.IHolidayCalendarService;
import com.scsinfinity.udhd.services.settings.interfaces.IPaginationInfoService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class HolidayCalendarService implements IHolidayCalendarService {

	private final IHolidayCalendarRepository holidayCalendarRepository;
	private final IPaginationInfoService<HolidayCalendarDTO, HolidayCalendarEntity> paginationInfoService;

	@Override
	public HolidayCalendarDTO findByHolidayId(Long id) {
		log.info("findByHolidayId", id);
		return holidayCalendarRepository.findById(id).map(res -> res.getDTO())
				.orElseThrow(() -> new EntityNotFoundException("Holiday"));
	}

	@Override
	public GenericResponseObject<HolidayCalendarDTO> loadHolidayByPage(Pagination<HolidayCalendarDTO> data) {
		log.info("loadVendorByPage", data);
		Pageable pageable = paginationInfoService.getPaginationRequestInfo(data);

		Page<HolidayCalendarEntity> holidayEntityByPage = null;
		// filter list and sorting
		if (data.getQueryString() != null) {
			holidayEntityByPage = holidayCalendarRepository
					.findAllByDescriptionIgnoreCaseContainingAndDayOfHolidayBetween(pageable, data.getQueryString(),
							LocalDateTime.ofInstant(data.getDateFrom().toInstant(), ZoneId.systemDefault()),
							LocalDateTime.ofInstant(data.getDateTo().toInstant(), ZoneId.systemDefault()));
		} else {
			holidayEntityByPage = holidayCalendarRepository.findAll(pageable);
		}
		Page<HolidayCalendarDTO> holidays = paginationInfoService.getDataAsDTO(holidayEntityByPage, en -> en.getDTO());

		return new GenericResponseObject<HolidayCalendarDTO>(holidays.getTotalElements(), holidays, data.getPageNo(),
				data.getPageSize());
	}

	@Override
	public HolidayCalendarDTO createUpdateHoliday(HolidayCalendarDTO holidayDTO) {
		log.info("createUpdateHoliday", holidayDTO);
		return holidayCalendarRepository.save(holidayDTO.getEntity()).getDTO();
	}

	@Override
	public void deleteHoliday(Long id) {
		log.info("deleteHoliday", id);
		Optional<HolidayCalendarEntity> holidayO = holidayCalendarRepository.findById(id);
		if (holidayO.isPresent())
			holidayCalendarRepository.delete(holidayO.get());
		else
			throw new BadRequestAlertException("Cannot delete vendor", "VENDOR", "VENDOR_IN_USE");

	}

}
