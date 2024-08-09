package com.scsinfinity.udhd.dao.entities.deas;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;
import com.scsinfinity.udhd.dao.entities.geography.ULBEntity;
import com.scsinfinity.udhd.services.deas.dto.DeasAnnualDataDTO;

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
@Table(name = "DEAS_ANNUAL_DATA")
public class DeasAnnualDataEntity extends BaseIdEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -1479174733427991122L;

	@ManyToOne
	private ULBEntity ulb;

	@NotNull
	private LocalDate yearStartDate;

	@NotNull
	private LocalDate yearEndDate;

	@OneToMany(mappedBy = "annualData")
	private List<DeasPeriodicDataEntity> periodicDataList;

	@OneToOne
	private AnnualFinanceStatementEntity afs;

	@NotNull
	private TypeStatusEnum status;

	public DeasAnnualDataDTO getDTO() {
		return DeasAnnualDataDTO.builder().ulbId(ulb != null ? ulb.getId() : null).yearEndDate(yearEndDate)
				.yearStartDate(yearStartDate)
				.periodicDataDTOIds(periodicDataList != null
						? periodicDataList.stream().map(data -> data.getId()).collect(Collectors.toList())
						: null)
				.afsId(afs != null ? afs.getId() : null).status(status).build();
	}
}
