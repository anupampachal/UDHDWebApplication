package com.scsinfinity.udhd.dao.entities.deas;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;
import com.scsinfinity.udhd.dao.entities.storage.FileEntity;
import com.scsinfinity.udhd.services.deas.dto.TrialBalanceUploadReportSummaryDTO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name = "DEAS_TRIAL_BALANCE_REPORT_SUMMARY")
public class TrialBalanceUploadReportSummaryEntity extends BaseIdEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1648179980574833265L;

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
	private Boolean overwriteHappened;

	@NotNull
	private Boolean sheetSummationIssue;
	
	@OneToOne
	private FileEntity fileWithComments;
	
	@OneToOne
	private TrialBalanceFileInfoEntity trialBalanceFile;

	//formatter:off
	@JsonIgnore()
	public TrialBalanceUploadReportSummaryDTO getDTO() {
		return TrialBalanceUploadReportSummaryDTO.builder()
				.id(id)
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
				.trialBalanceFileInfoId(responseFileId)
				.trialBalanceFileId(trialBalanceFile.getFile().getFileId())
				.build();
	}
}
