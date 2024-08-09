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
import com.scsinfinity.udhd.services.settings.dto.GeoDivisionDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author aditya-server
 *
 *         27-Aug-2021 -- 11:42:25 pm
 */
@Entity
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@Table(name = "GEO_DIVISION")
public class GeoDivisionEntity extends BaseIdEntity implements Serializable {

	@PrePersist
	@PreUpdate
	public void lowerTheCodeCase() {
		this.code= code==null? null: code.toLowerCase();
		this.name= name==null?null:name.toUpperCase();
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 6841513879307546891L;

	@NotBlank
	@Size(min = 3, max = 50)
	@Column(unique = true)
	private String name;

	@NotNull
	private Boolean active;

	@NotBlank
	@Size(min = 2, max = 10)
	@Column(unique = true)
	private String code;

	
	
	public GeoDivisionEntity(@NotBlank @Size(min = 3, max = 50) String name,
			@NotBlank @Size(min = 2, max = 10) String code, Boolean active) {
		this.name = name;
		this.code = code;
		this.active = active;
	}	
	
	@JsonIgnore
	public GeoDivisionDTO getDTO() {
		return GeoDivisionDTO.builder().id(id).code(code).name(name).active(active).build();
	}


}
