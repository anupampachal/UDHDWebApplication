package com.scsinfinity.udhd.dao.entities.activities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;
import com.scsinfinity.udhd.services.settings.dto.HolidayCalendarDTO;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name = "HOLIDAY_CALENDAR")
public class HolidayCalendarEntity extends BaseIdEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 2293603537149135583L;

	@NotNull
	private LocalDateTime dayOfHoliday;

	@NotBlank
	@Size(min = 3, max = 1000)
	private String description;

	public HolidayCalendarDTO getDTO() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
		return HolidayCalendarDTO.builder().dateTime(dayOfHoliday)
				.date(formatter.format(Date.from(dayOfHoliday.toInstant(ZoneOffset.UTC))))
				.description(description).id(id).build();
	}

	@Builder
	public HolidayCalendarEntity(Long id, @NotNull LocalDateTime dayOfHoliday,
			@NotBlank @Size(min = 3, max = 1000) String description) {
		super(id);
		this.dayOfHoliday = dayOfHoliday;
		this.description = description;
	}

}
