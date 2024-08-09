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
import com.scsinfinity.udhd.services.deas.dto.HistoricDataDTO;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author aditya-server
 *
 *         19-DEC-2021 -- 12:49:00 pm
 */

@Entity
@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name = "DEAS_HISTORICAL_DATA_ENTITY")
public class DEASHistoricalDataEntity extends BaseIdEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4437442291427820820L;

	@ManyToOne
	private ULBEntity ulb;

	@OneToOne
	private FileEntity file;

	@NotNull
	private LocalDate startDate;

	@NotNull
	private LocalDate endDate;

	@NotNull
	// @Enumerated(EnumType.STRING)
	private TypeStatusEnum status;

	@NotNull
	private Boolean overWritten;

	public HistoricDataDTO getDto() {
		return HistoricDataDTO.builder().endDate(endDate).id(id).status(status)
				.ulbName(ulb != null ? ulb.getName() : null).overWritten(overWritten).startDate(startDate)
				.ulbId(ulb != null ? ulb.getId() : null).fileId(file != null ? file.getFileId() : null).build();

	}
}
