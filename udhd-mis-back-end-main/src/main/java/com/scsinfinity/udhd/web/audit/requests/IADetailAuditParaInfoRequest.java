package com.scsinfinity.udhd.web.audit.requests;

import com.scsinfinity.udhd.dao.entities.audit.internalaudit.AuditParaDetailActionStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class IADetailAuditParaInfoRequest {
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
}
