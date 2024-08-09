package com.scsinfinity.udhd.dao.entities.audit.internalaudit;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "IA_TALLY_INFO")
public class IATallyInfoEntity extends BaseIdEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 90565984053427616L;
	
	@OneToOne
	private IAStatusOfImplementationOfDeasEntity statusOfDeas;

	@NotNull
	private LocalDate periodFrom;

	@NotNull
	private LocalDate periodTo;

	@NotBlank
	private String tallySerialNo;

	@NotBlank
	private String tallyId;
}
