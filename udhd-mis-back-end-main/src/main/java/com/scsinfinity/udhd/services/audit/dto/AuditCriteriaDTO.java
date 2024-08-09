package com.scsinfinity.udhd.services.audit.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scsinfinity.udhd.dao.entities.audit.AuditCriteriaEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class AuditCriteriaDTO {

	private Long id;

	@NotBlank
	@Size(min = 3, max = 512)
	private String title;

	@NotBlank
	@Size(min = 3, max = 2000)
	private String description;

	@NotBlank
	@Size(min = 3, max = 255)
	private String auditPara;

	@NotNull
	private Boolean status;

	@NotBlank
	@Size(min = 3, max = 255)
	private String associatedRisk;

	@NotNull
	private BigDecimal amount;
	
	private Long typeAuditId;

	@JsonIgnore
	public AuditCriteriaEntity getEntity() {
		return AuditCriteriaEntity.builder().id(id).amount(amount).associatedRisk(associatedRisk).auditPara(auditPara)
				.description(description).title(title).status(status).build();
	}

}
