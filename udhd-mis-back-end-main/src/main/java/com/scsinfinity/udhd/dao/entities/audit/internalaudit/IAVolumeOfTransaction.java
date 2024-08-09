package com.scsinfinity.udhd.dao.entities.audit.internalaudit;

import java.math.BigDecimal;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class IAVolumeOfTransaction extends BaseIdEntity {

	@NotNull
	private BigDecimal budgetFY;

	@NotNull
	private BigDecimal previousYearFY;

	@NotNull
	private BigDecimal correspondingPrevYearFY;

	@NotNull
	private BigDecimal currentYearFY;

	@NotNull
	private BigDecimal cumulativeForCurrentPeriod;
}
