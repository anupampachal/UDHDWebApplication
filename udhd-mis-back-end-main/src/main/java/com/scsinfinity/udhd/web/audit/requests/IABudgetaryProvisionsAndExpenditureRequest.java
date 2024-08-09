package com.scsinfinity.udhd.web.audit.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class IABudgetaryProvisionsAndExpenditureRequest {
    private Long financeId;
    private String fy1;
    private String fy2;
    private String fy3;
    private Long finalBudgetDataId;
    private Long actualExpenditureDataId;
}
