/**
 * 
 */
package com.scsinfinity.udhd.dao.entities.geography;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;
import com.scsinfinity.udhd.dao.entities.base.UserGroupEntity;
import com.scsinfinity.udhd.dao.entities.base.UserProfileEntity;
import com.scsinfinity.udhd.services.settings.dto.ULBDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * @author aditya-server
 *
 *         27-Aug-2021 -- 11:46:59 pm
 */

@Entity
@Getter
@Setter
@SuperBuilder
@ToString
@NoArgsConstructor
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "name", "code","aliasName" }) },name = "GEO_ULB")
public class ULBEntity extends BaseIdEntity implements Serializable {

	/**
	 * 
	 */
	@PrePersist
	@PreUpdate
	public void lowerTheCodeCase() {
		this.code= code==null? null: code.toLowerCase();
		this.name= name==null? null: name.toUpperCase();
	}
	private static final long serialVersionUID = 8386251805323292379L;

	@NotEmpty
	@Size(max = 50, min = 4)
	@Column(unique = true)
	private String name;

	@NotEmpty
	@Size(max = 20, min = 3)
	@Column(unique = true)
	private String code;

	@Size(max = 20, min = 4)
	@Column(unique = true)
	private String aliasName;

	@NotNull
	@Enumerated(EnumType.STRING)
	private ULBType type;

	@ManyToOne
	private GeoDistrictEntity district;

	@NotNull
	private Boolean active;

	@ManyToMany
	private List<UserGroupEntity> userGroups;

	@JsonIgnore
	public ULBDTO getDTO() {
		return ULBDTO.builder().id(id).code(code).name(name).active(active).aliasName(aliasName)
				.district(district.getDTO()).type(type).build();
	}

}
