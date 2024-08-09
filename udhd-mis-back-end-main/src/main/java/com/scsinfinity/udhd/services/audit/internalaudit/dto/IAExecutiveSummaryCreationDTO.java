package com.scsinfinity.udhd.services.audit.internalaudit.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class IAExecutiveSummaryCreationDTO {
    public Long id;
    public Long internalAuditId;
    public String overAllOpinion;
    private String commentFromMgt;
    private String acknowledgement;
    private Long fileCommentFromMgtId;
    private List<Long> strengths;
    private List<Long> weaknesses;
    private List<Long> recommendations;
}
