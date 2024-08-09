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
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class IABudgetaryProvisionAndExpenditure extends BaseIdEntity{

	@NotNull
	private BigDecimal fy1;

	@NotNull
	private BigDecimal fy2;

	@NotNull
	private BigDecimal fy3;
}
