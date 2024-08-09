package com.scsinfinity.udhd.dao.entities.audit.internalaudit;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;
import com.scsinfinity.udhd.dao.entities.storage.FileEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "IA_DETAIL_AUDIT_PARA_INFO")
public class IADetailAuditParaInfoEntity extends BaseIdEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -7439020803117229240L;

	@NotBlank
	@Size(min = 1, max = 255)
	private String auditParaNo;

	@NotBlank
	@Size(min = 5, max = 1000)
	private String auditParaHeading;

	@NotNull
	private BigDecimal amountInvolved;

	@NotNull
	private BigDecimal recoveryProposed;

	@NotNull
	private BigDecimal recoveryCompleted;

	@NotNull
	private AuditParaDetailActionStatusEnum actionStatus;

	@OneToOne
	private FileEntity file;
}
