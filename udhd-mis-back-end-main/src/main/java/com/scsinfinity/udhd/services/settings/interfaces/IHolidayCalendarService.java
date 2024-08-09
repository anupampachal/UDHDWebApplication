package com.scsinfinity.udhd.services.settings.interfaces;

import com.scsinfinity.udhd.services.common.GenericResponseObject;
import com.scsinfinity.udhd.services.common.Pagination;
import com.scsinfinity.udhd.services.settings.dto.HolidayCalendarDTO;

public interface IHolidayCalendarService {
	HolidayCalendarDTO findByHolidayId(Long id);

	GenericResponseObject<HolidayCalendarDTO> loadHolidayByPage(Pagination<HolidayCalendarDTO> data);

	HolidayCalendarDTO createUpdateHoliday(HolidayCalendarDTO holidayDTO);

	void deleteHoliday(Long id);
}
