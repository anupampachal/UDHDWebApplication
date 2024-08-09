package com.scsinfinity.udhd.dao.entities.deas;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.IsoFields;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.scsinfinity.udhd.configurations.application.date.DateQuartersEnum;
import com.scsinfinity.udhd.configurations.application.date.FinYearDateDTO;
import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;
import com.scsinfinity.udhd.dao.entities.geography.ULBEntity;
import com.scsinfinity.udhd.dao.entities.storage.FileEntity;
import com.scsinfinity.udhd.services.deas.dto.BudgetUploadDTO;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name = "DEAS_BUDGET_UPLOADS_INFO")
public class BudgetUploadsInfoEntity extends BaseIdEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -4646771432994667564L;

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

	public BudgetUploadDTO getDto() {
		// @formatter:off
		FinYearDateDTO dateRange;
		try {
			dateRange = getFinDates(yearStartDate, yearEndDate);
			return BudgetUploadDTO.builder()
					.id(id)
					.endDate(dateRange.getEndDate())
					.startDate(dateRange.getStartDate())
					.inputDate(dateRange.getInputDate())
					//.quarter(dateRange.getQuarter())
					.status(status)
					.overWritten(overWritten)
					.ulbName(ulb != null ? ulb.getName() : null)
					.ulbId(ulb != null ? ulb.getId() : null)
					.fileId(file != null ? file.getFileId() : null)
					.build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				return null;
		

	}
	private FinYearDateDTO getFinDates(LocalDate startDate, LocalDate endDate) throws Exception {
		/*//int advance = (startDate.getMonthValue() < 4) ? 0 : 1;
		//startDate = startDate.plus(advance, ChronoUnit.YEARS);
		//endDate = endDate.plus(advance, ChronoUnit.YEARS);
		return new FinYearDateDTO(startDate.getYear() - 1 + "-" + (endDate.getYear() % 100),
				startDate.getYear() - 1 + "", (endDate.getYear()) + ""
				//, quarter
				);*/
		return new FinYearDateDTO(startDate.getYear()  + "-" + (endDate.getYear() % 100),startDate.toString(),endDate.toString());
	}
}
