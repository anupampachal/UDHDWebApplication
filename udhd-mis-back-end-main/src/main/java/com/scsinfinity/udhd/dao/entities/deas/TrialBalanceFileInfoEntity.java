package com.scsinfinity.udhd.dao.entities.deas;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;
import com.scsinfinity.udhd.dao.entities.geography.ULBEntity;
import com.scsinfinity.udhd.dao.entities.storage.FileEntity;

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
@Table(name = "DEAS_TRIAL_BALANCE_FILE_INFO")
public class TrialBalanceFileInfoEntity extends BaseIdEntity implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = -1355195396068735972L;
	
	@ManyToOne
	private ULBEntity ulb;
	
	@OneToOne
	private FileEntity file;
	
	@NotNull
	private LocalDate startDate;
	
	@NotNull
	private LocalDate endDate;

	
	@OneToMany(mappedBy="trialBalance",cascade = CascadeType.ALL, orphanRemoval = true)
	private List<LedgerTransactionEntity> transactions;
	
	@NotNull
	private TypeStatusEnum status;
}
