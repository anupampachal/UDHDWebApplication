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
@Table(name = "IA_RCR_DETAILS")
public class IARevenueAndCapitalReceiptsInfoDetailsEntity extends BaseIdEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7074426830077746990L;

	@OneToOne
	private IARevenueNCapitalReceiptsEntity revenueAndCapitalReceipts;

	@OneToOne
	private IARevenueNCapitalExpenditureEntity revenueAndCapitalExpenditure;

	@NotBlank
	private String fy1;
	@NotBlank
	private String fy2;
	@NotBlank
	private String fy3;
	@NotBlank
	private String fy4;
	@NotBlank
	private String fy5;
	@NotBlank
	private String fy6;
}
