package com.scsinfinity.udhd.dao.entities.audit.internalaudit;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

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
@Table(name = "IA_PARTICULARS_WITH_FINANCE_STATUS")
public class IAParticularsWithFinanceStatusEntity extends BaseIdEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 8672323288522253128L;


	@NotBlank
	private String particulars;

	@NotBlank
	private String value;
}
