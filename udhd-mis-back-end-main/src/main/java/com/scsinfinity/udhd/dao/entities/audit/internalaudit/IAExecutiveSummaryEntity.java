package com.scsinfinity.udhd.dao.entities.audit.internalaudit;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;
import com.scsinfinity.udhd.dao.entities.storage.FileEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "IA_EXECUTIVE_SUMMARY")
public class IAExecutiveSummaryEntity extends BaseIdEntity implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 9126535378582747510L;

	@NotNull
	@OneToOne
	private InternalAuditEntity internalAudit;

	@Size(min = 5, max = 255)
	private String periodCovered;

	@Size(min = 2, max = 255)
	private String executiveOfficerNameForPeriod;

	@Size(min = 2, max = 10000)
	private String overAllOpinion;

	// @NotBlank
	@Size(min = 2, max = 10000)
	private String commentFromMgt;

	// @NotBlank
	@Size(min = 2, max = 10000)
	private String acknowledgement;

	@OneToOne
	private FileEntity fileCommentFromMgt;

	@OneToMany(cascade = CascadeType.PERSIST)
	private List<IAResultsAndFindingsStrengthEntity> strengths;

	@OneToMany(cascade = CascadeType.PERSIST)
	private List<IAResultsAndFindingsWeaknessEntity> weaknesses;

	@OneToMany(cascade = CascadeType.PERSIST)
	private List<IAAuditRecommendationsEntity> recommendations;

}
