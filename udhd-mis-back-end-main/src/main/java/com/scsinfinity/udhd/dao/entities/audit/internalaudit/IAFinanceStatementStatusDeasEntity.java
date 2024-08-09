package com.scsinfinity.udhd.dao.entities.audit.internalaudit;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "IA_FINANCE_STATEMENT_STATUS")
public class IAFinanceStatementStatusDeasEntity extends BaseIdEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 8347136140859987741L;

	
	@NotBlank
	private String particulars;
	@NotBlank
	private String propertyTaxRegister;
	@NotBlank
	private String fixedAssetsRegister;
	@NotBlank
	private String openingBalanceRegister;
	@NotBlank
	private String annualFinanceStatement;

}
