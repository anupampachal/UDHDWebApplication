package com.scsinfinity.udhd.services.auth.dtoandconstants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scsinfinity.udhd.dao.entities.base.UserGroupEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserGroupDTO {
	@NotBlank
	@Size(min = 3, max = 200)
	private String name;

	private String id;

	@Size(min = 3, max = 2000)
	private String description;
	
	@NotNull
	private boolean status;

	@JsonIgnore
	public UserGroupEntity getNewUserGroupEntity(String randomStr) {
		return UserGroupEntity.builder().status(status).userGroupId(randomStr)
				.description(description).name(name).build();
	}

}
