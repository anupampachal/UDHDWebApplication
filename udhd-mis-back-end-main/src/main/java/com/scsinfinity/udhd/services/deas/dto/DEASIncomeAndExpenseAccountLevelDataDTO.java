package com.scsinfinity.udhd.services.deas.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class DEASIncomeAndExpenseAccountLevelDataDTO {
	private Long id;
	private String code;
	private String description;
	private BigDecimal amount;
}
