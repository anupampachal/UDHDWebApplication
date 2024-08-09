package com.scsinfinity.udhd.dao.repositories.activities;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scsinfinity.udhd.dao.entities.activities.HolidayCalendarEntity;

@Repository
public interface IHolidayCalendarRepository extends JpaRepository<HolidayCalendarEntity, Long> {
	Page<HolidayCalendarEntity> findAllByDescriptionIgnoreCaseContainingAndDayOfHolidayBetween(Pageable pageable,
			String description, LocalDateTime startDate, LocalDateTime endDate);
}
