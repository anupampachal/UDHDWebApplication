package com.scsinfinity.udhd.dao.entities.audit.internalaudit;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
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
@Table(name = "IA_UTILISATION_OF_GRANTS_LINE_ITEM")
public class IAUtilisationOfGrantsLineItemEntity extends BaseIdEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -6769233510391731336L;

	@NotBlank
	private String schemeName;

	@NotNull
	private BigDecimal fundReceived;

	@NotNull
	private BigDecimal expenses;

	@NotNull
	private Long pendingUC;

	@NotNull
	private Long submittedUC;

	@NotBlank
	private String letterNoNDate;

}
