package com.scsinfinity.udhd.dao.entities.base;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name = "LANGUAGE")
public class LanguageEntity extends BaseIdEntity implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -4557971717342877316L;
	
	@NotBlank
	private String name;

}
