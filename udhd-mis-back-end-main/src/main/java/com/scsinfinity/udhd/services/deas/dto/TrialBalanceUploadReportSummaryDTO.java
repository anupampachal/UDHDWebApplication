package com.scsinfinity.udhd.services.deas.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scsinfinity.udhd.dao.entities.deas.TrialBalanceFileInfoEntity;
import com.scsinfinity.udhd.dao.entities.deas.TrialBalanceUploadReportSummaryEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrialBalanceUploadReportSummaryDTO {

	private Long id;
	@NotNull
	private Boolean error;

	private String ulbName;
	private Long ulbId;

	@NotNull
	private Boolean ulbCorrect;

	private String currentUserName;

	@NotNull
	private Boolean userAllowed;

	private String errorSummary;
	private String errorDetails;

	private String responseFileId;

	@NotNull
	private Boolean sheetSummationIssue;

	@NotNull
	private Boolean overwriteHappened;

	@NotNull
	private String trialBalanceFileId;

	private String trialBalanceFileInfoId;

	//@formatter:off
	@JsonIgnore
	public TrialBalanceUploadReportSummaryEntity getEntity(TrialBalanceFileInfoEntity tbFile) {
		return TrialBalanceUploadReportSummaryEntity.builder()
				.error(error)
				.ulbName(ulbName)
				.ulbId(ulbId)
				.ulbCorrect(ulbCorrect)
				.currentUserName(currentUserName)
				.userAllowed(userAllowed)
				.errorSummary(errorSummary)
				.errorDetails(errorDetails)
				.responseFileId(responseFileId)
				.sheetSummationIssue(sheetSummationIssue)
				.overwriteHappened(overwriteHappened)
				.trialBalanceFile(tbFile)
				.build();
	}
}
