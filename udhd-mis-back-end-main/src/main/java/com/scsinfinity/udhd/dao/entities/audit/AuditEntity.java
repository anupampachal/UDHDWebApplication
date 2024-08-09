package com.scsinfinity.udhd.dao.entities.audit;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;
import com.scsinfinity.udhd.dao.entities.base.UserProfileEntity;
import com.scsinfinity.udhd.dao.entities.geography.ULBEntity;
import com.scsinfinity.udhd.services.audit.dto.AuditDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table(name = "AUDIT")
public class AuditEntity extends BaseIdEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -6182682007861103460L;

	@NotBlank
	@Size(min = 10, max = 255)
	private String title;

	@NotNull
	private LocalDate startDate;

	@ManyToOne
	private UserProfileEntity currentStepOwner;

	@NotNull
	private LocalDate endDate;

	@NotNull
	private AuditTypeEnum auditType;

	@NotNull
	private AuditStepEnum stepEnum;


	@Size(min = 5, max = 2000)
	private String description;

	@ManyToOne
	@NotNull
	private ULBEntity ulb;

	@NotNull
	private AuditStatusEnum auditStatus;

	@JsonIgnore
	public AuditDTO getDTO() {
		return AuditDTO.builder().id(id).auditStatus(auditStatus).endDate(endDate).startDate(startDate).title(title)
				.ulb(ulb.getDTO()).stepEnum(stepEnum).description(description).build();
	}

}
