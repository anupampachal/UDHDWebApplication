package com.scsinfinity.udhd.services.deas.dto;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.scsinfinity.udhd.dao.entities.deas.TypeStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class DeasAnnualDataDTO {

	private Long ulbId;

	@NotNull
	private LocalDate yearStartDate;

	@NotNull
	private LocalDate yearEndDate;

	private List<Long> periodicDataDTOIds;

	private Long afsId;

	@NotNull
	private TypeStatusEnum status;
}
