package com.scsinfinity.udhd.dao.entities.base;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.scsinfinity.udhd.services.auth.dtoandconstants.UserGroupDTO;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER_GROUP")
public class UserGroupEntity extends BaseIdEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -1191325405456674211L;

	@NotBlank
	@Size(min = 3, max = 200)
	private String name;

	@NotNull
	@Column(unique = true)
	private String userGroupId;

	@Size(min = 3, max = 2000)
	private String description;

	@NotNull
	private Boolean status;

	@ManyToMany(fetch = FetchType.LAZY)
	// @JsonBackReference(value="user-profile-and-group")
	private List<UserProfileEntity> userProfiles;

	public UserGroupDTO toDto() {
		return UserGroupDTO.builder().description(description).name(name).id(userGroupId).status(status).build();
	}

}
