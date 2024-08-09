package com.scsinfinity.udhd.services.settings.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scsinfinity.udhd.dao.entities.geography.GeoDistrictEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeoDistrictDTO {

	private Long id;

	@NotBlank
	@Size(min = 3, max = 50)
	@Column(unique = true)
	private String name;

	@NotBlank
	@Size(min = 2, max = 10)
	@Column(unique = true)
	private String code;

	@NotNull
	private GeoDivisionDTO division;

	@NotNull
	private Boolean active;

	@JsonIgnore
	public GeoDistrictEntity getEntity() {
		return GeoDistrictEntity.builder().code(code).name(name).id(id).active(active).division(division.getEntity())
				.build();
	}

}
