package com.scsinfinity.udhd.services.settings.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scsinfinity.udhd.dao.entities.activities.HolidayCalendarEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class HolidayCalendarDTO {

	private Long id;
	@NotNull
	private LocalDateTime dateTime;

	private String date;

	@NotBlank
	@Size(min = 3, max = 1000)
	private String description;

	@JsonIgnore
	public HolidayCalendarEntity getEntity() {
		return HolidayCalendarEntity.builder().dayOfHoliday(dateTime).description(description).id(id).build();
	}
}
