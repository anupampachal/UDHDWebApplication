package com.scsinfinity.udhd.web.audit.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class IAStatusOfAuditObservationRequest {
    @NotBlank
    @Size(min = 3, max = 255)
    private String auditParticularsWithDate;

    @NotNull
    private Long totalNosOfAuditParas;

    @NotNull
    private Long totalAuditParasWithCorrectiveActionReq;

    @NotNull
    private Long totalAuditParasWithCashRecProposed;

    @NotNull
    private Long totalAuditParasWithCashRecMade;

    @NotNull
    private BigDecimal totalAmtOfRecovery;

    @NotNull
    private Long totalNosOfParasWithNoAction;

    @NotBlank
    private String nosAndDateOfComplianceReport;
}
