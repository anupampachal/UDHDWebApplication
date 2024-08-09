package com.scsinfinity.udhd.services.audit.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.scsinfinity.udhd.dao.entities.audit.AuditStatusEnum;
import com.scsinfinity.udhd.dao.entities.audit.AuditTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class AuditCreationDto {
    private Long id;
    @NotBlank
	@Size(min = 10, max = 255)
    private String title;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @Size(min = 5, max = 2000)
    private String description;
    @NotNull
    private Long ulbId;
    @NotNull
    private AuditStatusEnum auditStatus;
    @NotNull
    private AuditTypeEnum type;
}
