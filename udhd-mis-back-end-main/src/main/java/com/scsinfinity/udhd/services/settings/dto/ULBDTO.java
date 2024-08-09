package com.scsinfinity.udhd.services.settings.dto;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scsinfinity.udhd.dao.entities.geography.ULBEntity;
import com.scsinfinity.udhd.dao.entities.geography.ULBType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ULBDTO {

	private Long id;

	@NotEmpty
	@Size(max = 50, min = 4)
	@Column(length = 50)
	private String name;
	

	@NotEmpty
	@Size(max = 20, min = 3)
	@Column(length = 10, unique = true)
	private String code;

	@Size(max = 20, min = 4)
	@Column(length = 20)
	private String aliasName;

	@NotNull
	@Enumerated(EnumType.STRING)
	private ULBType type;

	@NotNull
	private GeoDistrictDTO district;

	@NotNull
	private Boolean active;

	@JsonIgnore
	public ULBEntity getEntity() {
		return ULBEntity.builder().active(active).aliasName(aliasName).code(code).district(district.getEntity()).id(id)
				.name(name).type(type).build();

	}
}
