package com.scsinfinity.udhd.dao.entities.deas;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
@Table(name = "DEAS_PERIODIC_DATA")
public class DeasPeriodicDataEntity extends BaseIdEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -102634769896868317L;

	@ManyToOne
	private ULBEntity ulb;

	@NotNull
	private LocalDate yearStartDate;

	@NotNull
	private LocalDate yearEndDate;

	@ManyToOne
	private DeasAnnualDataEntity annualData;

	@OneToOne
	private TrialBalanceFileInfoEntity trialBalanceFileInfo;

	@OneToOne
	private PropertyTaxRegisterInfoEntity propertyTaxRegisterInfo;

	@OneToOne
	private FixedAssetsRegisterEntity fixedAssetsRegisterInfo;

	@OneToOne
	private BudgetUploadsInfoEntity budgetUploadInfo;

	@NotNull
	private TypeStatusEnum status;
}
