package com.scsinfinity.udhd.dao.entities.audit.internalaudit;

import java.math.BigDecimal;

import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;
import com.scsinfinity.udhd.dao.entities.storage.FileEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@MappedSuperclass
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public abstract class IANonComplianceOfTDSNVAT extends BaseIdEntity {

	@NotBlank
	private String description;

	@NotNull
	private BigDecimal amountInvolved;

	@OneToOne
	private FileEntity file;

}
