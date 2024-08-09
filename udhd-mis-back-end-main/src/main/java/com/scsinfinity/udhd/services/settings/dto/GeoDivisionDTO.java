/**
 * 
 */
package com.scsinfinity.udhd.services.settings.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scsinfinity.udhd.dao.entities.geography.GeoDivisionEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author aditya-server
 *
 *         03-Sep-2021 -- 6:52:11 pm
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeoDivisionDTO {

	/**
	 * 
	 */

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
	private Boolean active;

	@JsonIgnore
	public GeoDivisionEntity getEntity() {
		return GeoDivisionEntity.builder().code(code).name(name).id(id).active(active).build();
	}

}
