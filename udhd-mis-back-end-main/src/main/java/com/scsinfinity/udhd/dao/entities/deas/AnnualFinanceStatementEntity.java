package com.scsinfinity.udhd.dao.entities.deas;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;
import com.scsinfinity.udhd.dao.entities.geography.ULBEntity;
import com.scsinfinity.udhd.dao.entities.storage.FileEntity;
import com.scsinfinity.udhd.services.deas.dto.AnnualFinanceStatementDTO;

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
@Table(name = "DEAS_ANNUAL_FINANCE_STATEMENT")
public class AnnualFinanceStatementEntity extends BaseIdEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -919844615303475133L;

	@ManyToOne
	private ULBEntity ulb;

	@OneToOne
	private FileEntity file;

	@NotNull
	private LocalDate yearStartDate;

	@NotNull
	private LocalDate yearEndDate;

	@NotNull
	private TypeStatusEnum status;

	@NotNull
	private Boolean overWritten;

	public AnnualFinanceStatementDTO getDto() {
		// @formatter:off
		return AnnualFinanceStatementDTO.builder()
				.endDate(yearEndDate)
				.id(id)
				.startDate(yearStartDate)
				.status(status)
				.overWritten(overWritten)
				.ulbName(ulb != null ? ulb.getName() : null)
				.ulbId(ulb != null ? ulb.getId() : null)
				.fileId(file != null ? file.getFileId() : null)
				.build();

	}

}
