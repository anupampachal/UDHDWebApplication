package com.scsinfinity.udhd.services.audit.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scsinfinity.udhd.dao.entities.audit.AuditEntity;
import com.scsinfinity.udhd.dao.entities.audit.AuditStatusEnum;
import com.scsinfinity.udhd.dao.entities.audit.AuditStepEnum;
import com.scsinfinity.udhd.dao.entities.audit.AuditTypeEnum;
import com.scsinfinity.udhd.dao.entities.base.UserEntity;
import com.scsinfinity.udhd.dao.entities.base.UserProfileEntity;
import com.scsinfinity.udhd.dao.entities.geography.ULBEntity;
import com.scsinfinity.udhd.services.settings.dto.ULBDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AuditDTO {
	private Long id;

	@NotBlank
	@Size(min = 10, max = 255)
	private String title;

	@Size(min = 5, max = 2000)
	private String description;

	@NotNull
	// @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "E MMM dd yyyy
	// HH:mm:ss 'GMT'z")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate startDate;

	@NotNull
	// @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "E MMM dd yyyy
	// HH:mm:ss 'GMT'z")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate endDate;

	@NotNull
	private ULBDTO ulb;

	@NotNull
	private AuditStepEnum stepEnum;
	
	@NotNull
	private AuditStatusEnum auditStatus;

	@JsonIgnore
	public AuditEntity getEntityWithULB(ULBEntity ulb) {
		return AuditEntity.builder()
				.id(id)
				.auditType(AuditTypeEnum.AG_IR)
				.auditStatus(auditStatus)
				.endDate(endDate)
				.stepEnum(AuditStepEnum.AG_ULB_ACC_DRAFT)
				.startDate(startDate)
				.title(title)
				.ulb(ulb)
				.description(description)
				.build();
	}
	
	@JsonIgnore
	public AuditEntity getEntityWithULB(ULBEntity ulb, UserProfileEntity user) {
		return AuditEntity.builder()
				.id(id)
				.auditType(AuditTypeEnum.AG_IR)
				.auditStatus(auditStatus)
				.endDate(endDate)
				.stepEnum(AuditStepEnum.AG_ULB_ACC_DRAFT)
				.startDate(startDate)
				.title(title)
				.currentStepOwner(user)
				.ulb(ulb)
				.description(description)
				.build();
	}

	@JsonIgnore
	public AuditEntity getEntity() {
		return AuditEntity.builder().id(id).auditType(AuditTypeEnum.AG_IR).auditStatus(auditStatus).endDate(endDate)
				.description(description).stepEnum(stepEnum)
				.startDate(startDate).title(title).ulb(ulb.getEntity()).build();
	}
	
}
