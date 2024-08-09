package com.scsinfinity.udhd.web.audit.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class IAVolumeOfTransactionsRequest {
    private Long financeId;

    private String budgetFY;

    private String previousYearFY;

    private String correspondingPrevYearFY;

    private String currentYearFY;

    private Long openingBalanceId;

    private Long receiptsId;

    private Long netExpenditureId;
}
