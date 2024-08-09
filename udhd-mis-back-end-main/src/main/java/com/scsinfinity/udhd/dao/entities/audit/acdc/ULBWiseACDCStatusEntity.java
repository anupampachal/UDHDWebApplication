package com.scsinfinity.udhd.dao.entities.audit.acdc;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;
import com.scsinfinity.udhd.dao.entities.geography.ULBEntity;
import com.scsinfinity.udhd.services.audit.acdc.dto.ACDCULBBasedDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "ACDC_ULB_STATUS_DATA")
public class ULBWiseACDCStatusEntity extends BaseIdEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -1342790894700367908L;
	@NotBlank
	@Size(min = 3, max = 20)
	private String treasuryCode;

	@NotBlank
	@Size(min = 3, max = 200)
	private String treasuryName;
	

	@NotNull
	private LocalDate financialYearStart;

	@NotNull
	private LocalDate financialYearEnd;

	@NotNull
	private Boolean plannedNonPlanned;

	@NotNull
	private String budgetCode;

	@NotNull
	private Boolean status;

	@NotNull
	private Integer billNo;

	@NotNull
	private LocalDate billDate;

	@NotNull
	private Integer sanctionOrderNo;
	@NotNull
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
	@ManyToOne
	private ULBEntity ulb;

	@Builder
	public ULBWiseACDCStatusEntity(Long id, @NotBlank @Size(min = 3, max = 20) String treasuryCode,
			@NotBlank @Size(min = 3, max = 200) String treasuryName, @NotNull LocalDate financialYearStart,
			@NotNull LocalDate financialYearEnd, @NotNull Boolean plannedNonPlanned, @NotNull String budgetCode,
			@NotNull Integer billNo, @NotNull LocalDate billDate, @NotNull Integer sanctionOrderNo,
			@NotNull Boolean status, @NotNull LocalDate sanctionOrderDate, @NotNull String billTVNo,
			@NotNull BigDecimal drawn, @NotNull BigDecimal adjusted, @NotNull BigDecimal unadjusted,
			@NotBlank @Size(min = 3, max = 200) String ddoCode, @Size(min = 3, max = 200) String remarks,
			ULBEntity ulb) {
		super(id);
		this.treasuryCode = treasuryCode;
		this.treasuryName = treasuryName;
		this.financialYearStart = financialYearStart;
		this.financialYearEnd = financialYearEnd;
		this.plannedNonPlanned = plannedNonPlanned;
		this.budgetCode = budgetCode;
		this.billNo = billNo;
		this.billDate = billDate;
		this.sanctionOrderNo = sanctionOrderNo;
		this.sanctionOrderDate = sanctionOrderDate;
		this.billTVNo = billTVNo;
		this.drawn = drawn;
		this.adjusted = adjusted;
		this.status = status;
		this.unadjusted = unadjusted;
		this.ddoCode = ddoCode;
		this.remarks = remarks;
		this.ulb = ulb;
	}

	public ULBWiseACDCStatusEntity(@NotBlank @Size(min = 3, max = 20) String treasuryCode,
			@NotBlank @Size(min = 3, max = 200) String treasuryName, @NotNull LocalDate financialYearStart,
			@NotNull LocalDate financialYearEnd, @NotNull Boolean plannedNonPlanned, @NotNull String budgetCode,
			@NotNull Integer billNo, @NotNull LocalDate billDate, @NotNull Integer sanctionOrderNo,
			@NotNull LocalDate sanctionOrderDate, @NotNull String billTVNo, @NotNull BigDecimal drawn,
			@NotNull Boolean status, @NotNull BigDecimal adjusted, @NotNull BigDecimal unadjusted,
			@NotBlank @Size(min = 3, max = 200) String ddoCode, @Size(min = 3, max = 200) String remarks,
			ULBEntity ulb) {
		super();
		this.treasuryCode = treasuryCode;
		this.treasuryName = treasuryName;
		this.financialYearStart = financialYearStart;
		this.financialYearEnd = financialYearEnd;
		this.plannedNonPlanned = plannedNonPlanned;
		this.budgetCode = budgetCode;
		this.billNo = billNo;
		this.billDate = billDate;
		this.sanctionOrderNo = sanctionOrderNo;
		this.sanctionOrderDate = sanctionOrderDate;
		this.billTVNo = billTVNo;
		this.status = status;
		this.drawn = drawn;
		this.adjusted = adjusted;
		this.unadjusted = adjusted;
		this.ddoCode = ddoCode;
		this.remarks = remarks;
		this.ulb = ulb;
	}

	public ACDCULBBasedDTO getDTO() {
		return ACDCULBBasedDTO.builder().id(id).treasuryCode(treasuryCode).treasuryName(treasuryName)
				.financialYearEnd(financialYearEnd).financialYearStart(financialYearStart).status(status)
				.plannedNonPlanned(plannedNonPlanned).budgetCode(budgetCode).billNo(billNo).billDate(billDate)
				.sanctionOrderNo(sanctionOrderNo).sanctionOrderDate(sanctionOrderDate).billTVNo(billTVNo).drawn(drawn)
				.adjusted(adjusted).unadjusted(unadjusted).ddoCode(ddoCode).remarks(remarks).ulb(ulb.getDTO()).ulbInfo(ulb.getName()+"(" +ulb.getId()+")").build();

	}

}
