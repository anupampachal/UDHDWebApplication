package com.scsinfinity.udhd.services.audit.internalaudit.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class IAStatusOfImplementationOfDeasDTO {

	@NotNull
	private Long iaId;

	@NotNull
	private Long statusId;

	private IATallyInfoDTO tallyDetails;

	private String description;

	private IAFinanceStatementStatusDeasDTO financeStatementStatus;

	private List<IAParticularsWithFinanceStatusDTO> particularsWithFinanceStatus;

	private String statusOfMunicipalAccCommitteeFileId;

	private String statusOfMunicipalAccountsCommittee;

}
