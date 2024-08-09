package com.scsinfinity.udhd.dao.entities.base;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.scsinfinity.udhd.services.settings.dto.MyProfileResponseDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity
@ToString
@Table(name = "USER")
public class UserEntity extends BaseIdEntity implements Serializable, UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5356672507968126879L;

	@Email
	@NotBlank
	@Size(min = 6, max = 50)
	@Column(length = 50, unique = true, nullable = false)
	private String username;

	@NotBlank
	@Size(min = 8, max = 200)
	@Column(name = "password_hash", length = 200)
	private String password;

	@NotBlank
	@Size(max = 100, min = 3)
	@Column(name = "name", length = 50)
	private String name;

	@NotBlank
	@Size(max = 50, min = 2)
	@Column(name = "userUId", length = 50)
	private String userUID;

	@NotNull
	private Boolean mobileNoVerified;

	@NotNull
	private Boolean enabled;// email verified

	// @OneToOne(
	// cascade = { CascadeType.REMOVE, CascadeType.PERSIST },
	// mappedBy = "user")

	
	//@JsonManagedReference//(value="user-and-profile")
	@OneToOne//(
			//mappedBy = "user", fetch=FetchType.LAZY)
	private UserProfileEntity userProfile;

	@ManyToOne
	private AuthorityEntity authority;

	@Size(min = 10, max = 10)
	@Column(unique = true)
	private String mobileNo;

	@Size(max = 8, min = 2)
	private String otp;

	private LocalDateTime otpValidTillTime;

	@NotNull
	@Column(name = "account_non_expired")
	private boolean accountNonExpired;

	@NotNull
	@Column(name = "account_non_locked")
	private boolean accountNonLocked;

	@NotNull
	@Column(name = "credentials_non_expired")
	private boolean credentialsNonExpired;

	// @Transient
	// private List<SimpleGrantedAuthority> authorities;

	public MyProfileResponseDTO getMyProfile(String role) {
		return MyProfileResponseDTO.builder().authority(role)
				.fileId(userProfile.getProfilePic() != null ? userProfile.getProfilePic().getFileId() : null)
				.mobileNo(mobileNo).name(name)
				//.userGroupId(userProfile.getPrimaryUserGroup().getUserGroupId())
				//.userGroupName(userProfile.getPrimaryUserGroup().getName())
				.userId(userUID).username(username).build();
	}

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authoritiesSet = new HashSet<>();
		authoritiesSet.add(new SimpleGrantedAuthority(authority.getName()));
		return authoritiesSet;

	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

}
