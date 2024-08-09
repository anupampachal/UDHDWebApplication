package com.scsinfinity.udhd.services.settings.dto;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserInfoDTO {

	Long id;

	@Email
	@NotBlank
	@Size(min = 6, max = 50)
	@Column(length = 50, unique = true, nullable = false)
	private String username;

	@NotBlank
	@Size(max = 100, min = 3)
	@Column(name = "name", length = 50)
	private String name;


	@NotBlank
	@Size(max = 20, min = 8)
	@Column(name="password")
	private String password;

	@NotBlank
	private String authority;

	@Size(min = 10, max = 10)
	@Column(unique = true)
	private String mobileNo;

	@SuppressWarnings("unused")
	private MultipartFile file;

	private Long profileId;

	@Size(min = 5, max = 255)
	private String permanentAddress;

	@NotNull
	private boolean enabled;
	@NotNull
	private boolean mobileVerified;

	@NotNull
	@Column(name = "account_non_expired")
	private boolean accountNonExpired;

	@NotNull
	@Column(name = "account_non_locked")
	private boolean accountNonLocked;

	@NotNull
	@Column(name = "credentials_non_expired")
	private boolean credentialsNonExpired;

	private List<ULBDTO> ulbDetails;
	private String authorityType;


}
