/**
 * 
 */
package com.scsinfinity.udhd.dao.entities.geography;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;
import com.scsinfinity.udhd.services.settings.dto.GeoDistrictDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author aditya-server
 *
 *         27-Aug-2021 -- 11:45:37 pm
 */

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table(name = "GEO_DISTRICT")
public class GeoDistrictEntity extends BaseIdEntity implements Serializable {
	/**
	 * 
	 */

	@PrePersist
	@PreUpdate
	public void lowerTheCodeCase() {
		this.code= code==null? null: code.toLowerCase();
		this.name= name==null?null:name.toUpperCase();
	}
	private static final long serialVersionUID = 2492926902768698296L;

	@NotBlank
	@Size(min = 3, max = 50)
	@Column(unique = true)
	private String name;

	@NotBlank
	@Size(min = 2, max = 10)
	@Column(unique = true)
	private String code;

	@NotNull
	private Boolean active;

	@ManyToOne
	private GeoDivisionEntity division;

	public GeoDistrictEntity(@NotBlank @Size(min = 3, max = 50) String name,
			@NotBlank @Size(min = 2, max = 10) String code, @NotNull Boolean active, GeoDivisionEntity division) {
		super();
		this.name = name;
		this.code = code;
		this.active = active;
		this.division = division;
	}

	@JsonIgnore
	public GeoDistrictDTO getDTO() {
		return GeoDistrictDTO.builder().id(id).code(code).name(name).active(active).division(division.getDTO()).build();
	}


}
