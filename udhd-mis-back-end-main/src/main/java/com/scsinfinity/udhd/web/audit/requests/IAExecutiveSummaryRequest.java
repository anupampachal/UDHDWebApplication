package com.scsinfinity.udhd.web.audit.requests;

import com.scsinfinity.udhd.dao.entities.storage.FileEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@Getter
public class IAExecutiveSummaryRequest {

    @NotBlank
    private Long internalAuditId;

    @Size(min= 5, max = 100)
    private String periodCovered;

    @Size(min= 2, max = 100)
    private String executiveOfficerNameForPeriod;

    @Size(min = 2, max = 10000)
    private String overAllOpinion;

    @NotBlank
    @Size(min = 2, max = 10000)
    private String commentFromMgt;

    @NotBlank
    @Size(min = 2, max = 10000)
    private String acknowledgement;

    private FileEntity fileCommentFromMgt;

    private List<String> strengths;

    private List<String> weaknesses;

    private List<String> recommendations;
}
