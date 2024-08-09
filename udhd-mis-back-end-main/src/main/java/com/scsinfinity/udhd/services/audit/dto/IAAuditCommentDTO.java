package com.scsinfinity.udhd.services.audit.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Getter
@SuperBuilder
@NoArgsConstructor
public class IAAuditCommentDTO {
    @NotBlank
    @Size(min = 3, max = 2000)
    private String comment;

    private Long id;
}
