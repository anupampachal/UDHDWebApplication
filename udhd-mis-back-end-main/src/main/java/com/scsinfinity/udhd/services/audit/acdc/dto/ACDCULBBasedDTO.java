package com.scsinfinity.udhd.services.audit.acdc.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.scsinfinity.udhd.dao.entities.audit.acdc.ULBWiseACDCStatusEntity;
import com.scsinfinity.udhd.services.settings.dto.ULBDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ACDCULBBasedDTO {

	private Long id;
	@NotBlank
	@Size(min = 3, max = 20)
	private String treasuryCode;

	@NotBlank
	@Size(min = 3, max = 200)
	private String treasuryName;

	@NotNull
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate financialYearStart;

	@NotNull
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate financialYearEnd;

	@NotNull
	private Boolean plannedNonPlanned;

	@NotBlank
	private String budgetCode;

	@NotNull
	private Boolean status;

	@NotNull
	private Integer billNo;

	@NotNull
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate billDate;

	@NotNull
	private Integer sanctionOrderNo;
	@NotNull
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate sanctionOrderDate;
	@NotNull
	private String billTVNo;

	@NotNull
	private BigDecimal drawn;
	@NotNull
	private BigDecimal adjusted;
	@NotNull
	private BigDecimal unadjusted;

	@NotBlank
	@Size(min = 3, max = 200)
	private String ddoCode;

	@Size(min = 3, max = 200)
	private String remarks;

	private ULBDTO ulb;
	private String ulbInfo;

	@JsonIgnore
	public ULBWiseACDCStatusEntity getEntity() {
		return ULBWiseACDCStatusEntity.builder().id(id).treasuryCode(treasuryCode).treasuryName(treasuryName)
				.financialYearStart(financialYearStart).financialYearEnd(financialYearEnd).status(status)
				.plannedNonPlanned(plannedNonPlanned).budgetCode(budgetCode).billNo(billNo).billDate(billDate)
				.sanctionOrderDate(sanctionOrderDate).sanctionOrderNo(sanctionOrderNo).billTVNo(billTVNo).drawn(drawn)
				.adjusted(adjusted).ulb(ulb.getEntity()).unadjusted(unadjusted).ddoCode(ddoCode).remarks(remarks)
				.build();
	}
}
