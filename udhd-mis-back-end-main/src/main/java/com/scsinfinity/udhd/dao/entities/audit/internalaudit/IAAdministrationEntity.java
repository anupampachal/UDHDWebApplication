package com.scsinfinity.udhd.dao.entities.audit.internalaudit;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;

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
@Table(name = "IA_ADMINISTRATION")
public class IAAdministrationEntity extends BaseIdEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 4052882552887955727L;

	@OneToOne
	@NotNull
	private InternalAuditEntity internalAudit;

	@NotBlank
	@Size(min = 5, max = 255)
	private String nameOfChairmanMayor;

	@NotBlank
	@Size(min = 5, max = 255)
	private String periodOfServiceChairman;

	@NotBlank
	@Size(min = 5, max = 255)
	private String nameOfCMO;

	@NotBlank
	@Size(min = 5, max = 255)
	private String periodOfServiceCMO;

	@OneToMany
	private List<IAAuditTeamTeamLeadersEntity> teamLeaders;

	@OneToMany
	private List<IAAuditTeamMunicipalAuditExpertsEntity> municipalExperts;

	@OneToMany
	private List<IAAuditTeamMunicipalAuditAssistantsEntity> municipalAssistants;

}
