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
@MappedSuperclass
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class IARevenueNCapitalReceiptsAndExpenditure extends BaseIdEntity {

	private Long id;

	@NotNull
	private BigDecimal fy1Amt;
	@NotNull
	private BigDecimal fy2Amt;
	@NotNull
	private BigDecimal fy3Amt;
	@NotNull
	private BigDecimal fy4Amt;
	@NotNull
	private BigDecimal fy5Amt;
	@NotNull
	private BigDecimal fy6Amt;
}
