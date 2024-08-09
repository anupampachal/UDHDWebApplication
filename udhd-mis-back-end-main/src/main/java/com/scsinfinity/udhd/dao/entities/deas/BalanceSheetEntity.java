package com.scsinfinity.udhd.dao.entities.deas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;
import com.scsinfinity.udhd.dao.entities.geography.ULBEntity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name = "DEAS_BALANCE_SHEET")
public class BalanceSheetEntity extends BaseIdEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -4920986294360898144L;

	private String name;

	@NotNull
	private LocalDate dateTill;

	private BigDecimal totalAssets;

	private BigDecimal totalLiabilities;
	
	@NotNull
	private Boolean active;
	
	@ManyToOne
	private ULBEntity ulb;

	@OneToOne
	@JsonIgnore
	private TrialBalanceFileInfoEntity trialBalance;
}
