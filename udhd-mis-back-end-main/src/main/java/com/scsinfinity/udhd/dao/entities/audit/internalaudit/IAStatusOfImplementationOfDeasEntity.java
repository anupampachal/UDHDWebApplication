package com.scsinfinity.udhd.dao.entities.audit.internalaudit;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;
import com.scsinfinity.udhd.dao.entities.storage.FileEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "IA_DEAS_IMPLIMENTATION_STATUS")
public class IAStatusOfImplementationOfDeasEntity extends BaseIdEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -4130565678127111327L;

	@OneToOne
	private IAFinanceEntity finance;

	@OneToOne(mappedBy = "statusOfDeas")
	private IATallyInfoEntity tallyDetails;

	//@NotBlank
	@Size(min = 3, max = 2000)
	private String description;

	@OneToOne
	private IAFinanceStatementStatusDeasEntity financeStatementStatus;

	@OneToMany
	//(mappedBy = "particularsWithFinanceStatus")
	private List<IAParticularsWithFinanceStatusEntity> particularsWithFinanceStatus;

	@OneToOne
	private FileEntity statusOfMunicipalAccCommitteeFile;
	
	//@NotBlank
	@Size(min = 3, max = 10000)
	private String statusOfMunicipalAccountsCommittee;

}
