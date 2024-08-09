package com.scsinfinity.udhd.services.audit.internalaudit.dto;

import java.util.List;

import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAAuditRecommendationsEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAResultsAndFindingsStrengthEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAResultsAndFindingsWeaknessEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class IAExecutiveSummaryDTO {
	private Long id;
	private Long internalAuditId;
	private String periodCovered;
	private String executiveOfficerNameForPeriod;
	private String overAllOpinion;
	private String commentFromMgt;
	private String acknowledgement;
	private String fileCommentFromMgt;
	private String ulbName;
	private List<IAResultsAndFindingsStrengthEntity> strengths;
	private List<IAResultsAndFindingsWeaknessEntity> weaknesses;
	private List<IAAuditRecommendationsEntity> recommendations;
}