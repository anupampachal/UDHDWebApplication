package com.scsinfinity.udhd.services.settings;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import com.scsinfinity.udhd.dao.entities.base.*;
import com.scsinfinity.udhd.dao.entities.geography.GeoDistrictEntity;
import com.scsinfinity.udhd.dao.entities.geography.GeoDivisionEntity;
import com.scsinfinity.udhd.dao.repositories.base.IUserULBDataRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scsinfinity.udhd.dao.entities.geography.ULBEntity;
import com.scsinfinity.udhd.dao.entities.storage.FileEntity;
import com.scsinfinity.udhd.dao.repositories.base.IUserGroupRepository;
import com.scsinfinity.udhd.dao.repositories.base.IUserProfileRepository;
import com.scsinfinity.udhd.dao.repositories.base.IUserRepository;
import com.scsinfinity.udhd.dao.repositories.geography.IULBRepository;
import com.scsinfinity.udhd.exceptions.BadRequestAlertException;
import com.scsinfinity.udhd.services.base.RandomGeneratorService;
import com.scsinfinity.udhd.services.common.AuthorityConstants;
import com.scsinfinity.udhd.services.common.GenericResponseObject;
import com.scsinfinity.udhd.services.common.Pagination;
import com.scsinfinity.udhd.services.settings.dto.OtherTypeUserInfoDTO;
import com.scsinfinity.udhd.services.settings.dto.SLPMUUserInfoDTO;
import com.scsinfinity.udhd.services.settings.dto.UDHDUserInfoDTO;
import com.scsinfinity.udhd.services.settings.dto.ULBDTO;
import com.scsinfinity.udhd.services.settings.dto.ULBInfoForULBUsersDTO;
import com.scsinfinity.udhd.services.settings.dto.ULBUserInfoDTO;
import com.scsinfinity.udhd.services.settings.dto.ULBsInfoForOtherTypesOfUsers;
import com.scsinfinity.udhd.services.settings.dto.UserInfoDTO;
import com.scsinfinity.udhd.services.settings.interfaces.IAuthorityService;
import com.scsinfinity.udhd.services.settings.interfaces.IPaginationInfoService;
import com.scsinfinity.udhd.services.settings.interfaces.IUserMgtService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class UserMgtService implements IUserMgtService {

	private final IAuthorityService authorityService;
	private final PasswordEncoder passwordEncoder;
	private final RandomGeneratorService randomService;
	private final IUserRepository userRepo;
	private final IUserGroupRepository userGrpRepo;
	private final IUserProfileRepository userProfileRepo;
	private final IULBRepository ulbService;

	private final IUserULBDataRepository userULBDataRepository;
	private final IPaginationInfoService<UserInfoDTO, UserEntity> paginationInfoService;

	@Override
	public UserInfoDTO getCurrentUserInfo() {
		String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		Optional<UserEntity> userE = userRepo.findByUsernameIgnoreCase(username);
		return fromEntity(userE.get());
	}

	@Override
	public GenericResponseObject<UserInfoDTO> loadUsersByPage(Pagination<UserInfoDTO> data) {
		log.info("loadUsersByPage", data);
		Pageable pageable = paginationInfoService.getPaginationRequestInfo(data);

		Page<UserEntity> userPage = null;
		if (data.getQueryString() != null) {
			userPage = userRepo
					.findAllByUsernameIgnoreCaseContainingOrNameIgnoreCaseContainingOrMobileNoIgnoreCaseContainingOrAuthority_Name(
							pageable, data.getQueryString(), data.getQueryString(), data.getQueryString(),
							data.getQueryString());
		} else {
			userPage = userRepo.findAll(pageable);
		}
		Page<UserInfoDTO> publishers = paginationInfoService.getDataAsDTO(userPage, en -> getUserInfoFromEntity(en));

		return new GenericResponseObject<UserInfoDTO>(publishers.getTotalElements(), publishers, data.getPageNo(),
				data.getPageSize());
	}

	@Override
	public UserInfoDTO getUserInfo(Long id) {
		Optional<UserEntity> userO = userRepo.findById(id);
		if (!userO.isPresent())
			throw new BadRequestAlertException("User not available", "User not availble for id->" + id,
					"Username not present");
		return getUserInfoFromEntity(userO.get());
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_SLPMU_IT + "','" + AuthorityConstants.ROLE_UDHD_IT
			+ "')")
	@Transactional
	public SLPMUUserInfoDTO createUpdateSLPMUUser(SLPMUUserInfoDTO slpmuUser) {
		// validate authority
		if (!isPermittedAuthorityInList(AuthorityTypeEnum.SLPMU, slpmuUser.getAuthority()))
			throw new BadRequestAlertException("AuthorityTypeNotPermitted", "CreateULBUser",
					"Incorrect User Authority" + slpmuUser.getAuthority());
		Optional<UserEntity> userO=null;
		// validate user
		if (slpmuUser.getId() != null) {
			userO = userRepo.findById(slpmuUser.getId());
			if (!userO.isPresent())
				throw new BadRequestAlertException("Incorrect user id", "Invalid User Id", "Invalid user id");
		}
		// proceed with user creation
		UserEntity user = createUpdateUser(slpmuUser, authorityService.findByName(slpmuUser.getAuthority()).get(),userO.get());

		UserGroupEntity userGroup = getUserGroup(user);

		// handle user profile image condition
		/**
		 * if(user.getUserProfile().getProfilePic()!=null) {
		 * 
		 * }
		 **/

		UserProfileEntity userProfile = createUpdateUserProfile(user, userGroup, slpmuUser.getPermanentAddress(), null,
				null);
		user.setUserProfile(userProfile);
		user = userRepo.save(user);
		return getSLPMUUser(user, userGroup, userProfile);
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_SLPMU_IT + "','" + AuthorityConstants.ROLE_UDHD_IT
			+ "')")
	@Transactional
	public UDHDUserInfoDTO createUpdateUDHDUser(UDHDUserInfoDTO udhdUser) {
		// validate authority
		if (!isPermittedAuthorityInList(AuthorityTypeEnum.UDHD, udhdUser.getAuthority()))
			throw new BadRequestAlertException("AuthorityTypeNotPermitted", "CreateULBUser",
					"Incorrect User Authority" + udhdUser.getAuthority());
		Optional<UserEntity> userO=null;
		// validate user
		if (udhdUser.getId() != null) {
			userO = userRepo.findById(udhdUser.getId());
			if (!userO.isPresent())
				throw new BadRequestAlertException("Incorrect user id", "Invalid User Id", "Invalid user id");
		}
		// proceed with user creation
		UserEntity user = createUpdateUser(udhdUser, authorityService.findByName(udhdUser.getAuthority()).get(),userO.get());

		UserGroupEntity userGroup = getUserGroup(user);

		// handle user profile image condition
		/**
		 * if(user.getUserProfile().getProfilePic()!=null) {
		 * 
		 * }
		 **/

		UserProfileEntity userProfile = createUpdateUserProfile(user, userGroup, udhdUser.getPermanentAddress(), null,
				null);
		user.setUserProfile(userProfile);
		user = userRepo.save(user);
		return getUDHDUser(user, userGroup, userProfile);
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_SLPMU_IT + "','" + AuthorityConstants.ROLE_UDHD_IT
			+ "')")
	@Transactional
	public OtherTypeUserInfoDTO createUpdateOtherTypeUser(OtherTypeUserInfoDTO otherUser) {
		// validate authority
		if (!isPermittedAuthorityInList(AuthorityTypeEnum.OTHERS, otherUser.getAuthority()))
			throw new BadRequestAlertException("AuthorityTypeNotPermitted", "CreateULBUser",
					"Incorrect User Authority" + otherUser.getAuthority());
		Optional<UserEntity> userO=null;
		// validate user
		if (otherUser.getId() != null) {
			userO = userRepo.findById(otherUser.getId());
			if (!userO.isPresent())
				throw new BadRequestAlertException("Incorrect user id", "Invalid User Id", "Invalid user id");
		}
		// proceed with user creation
		UserEntity user = createUpdateUser(otherUser, authorityService.findByName(otherUser.getAuthority()).get(),userO!=null?userO.get():null);

		UserGroupEntity userGroup = getUserGroup(user);

		// handle user profile image condition
		/**
		 * if(user.getUserProfile().getProfilePic()!=null) {
		 * 
		 * }
		 **/

		// ulb info
		List<ULBEntity> ulbs = ulbService.findByIdIn(otherUser.getUlbDetails().stream().map(ulbdto -> ulbdto.getId()).collect(Collectors.toList()));
		if (ulbs.size() <= 0 || ulbs.stream().filter(ulb -> ulb.getActive() != true).count() > 0)
			throw new BadRequestAlertException("ULB is either not present or inactive", "ULB ERROR",
					"Issue with one or more ULBs");

		UserProfileEntity userProfile = createUpdateUserProfile(user, userGroup, otherUser.getPermanentAddress(), null,
				ulbs);
		user.setUserProfile(userProfile);
		user = userRepo.save(user);
		return getOtherTypeUlbUser(user, userGroup, userProfile);
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_SLPMU_IT + "','" + AuthorityConstants.ROLE_UDHD_IT
			+ "')")
	@Transactional
	public ULBUserInfoDTO createUpdateUlbUser(ULBUserInfoDTO ulbUserInfoDTO) {
		// validate authority
		if (!isPermittedAuthorityInList(AuthorityTypeEnum.ULB, ulbUserInfoDTO.getAuthority()))
			throw new BadRequestAlertException("AuthorityTypeNotPermitted", "CreateULBUser",
					"Incorrect User Authority" + ulbUserInfoDTO.getAuthority());
		Optional<UserEntity> userO=null;
		// validate user
		if (ulbUserInfoDTO.getId() != null) {
			userO = userRepo.findById(ulbUserInfoDTO.getId());
			if (!userO.isPresent())
				throw new BadRequestAlertException("Incorrect user id", "Invalid User Id", "Invalid user id");
		}
		// proceed with user creation
		UserEntity user = createUpdateUser(ulbUserInfoDTO,
				authorityService.findByName(ulbUserInfoDTO.getAuthority()).get(),userO.get());

		//UserGroupEntity userGroup = getUserGroup(user);

		// handle user profile image condition
		/**
		 * if(user.getUserProfile().getProfilePic()!=null) {
		 * 
		 * }
		 **/

		// ulb info
		Optional<ULBEntity> ulbO = ulbService.findById(ulbUserInfoDTO.getUlbId());

		if (!ulbO.isPresent() || ulbO.get().getActive() != true)
			throw new BadRequestAlertException("ULB is either not present or inactive", "ULB ERROR", "Issue with ULB");
		ULBEntity ulb = ulbO.get();
		UserProfileEntity userProfile = createUpdateUserProfile(user, null, ulbUserInfoDTO.getPermanentAddress(),
				null, Arrays.asList(ulbO.get()));
		user.setUserProfile(userProfile);
	/*	List<UserProfileEntity> userProfiles = ulbO.get().getUserProfiles();
		userProfiles.add(userProfile);
		ulb.setUserProfiles(userProfiles);*/
		ulbService.save(ulb);
		user = userRepo.save(user);
		return getUlbUser(user, null, userProfile);
	}

	@Override
	public ULBsInfoForOtherTypesOfUsers getUlBsInfoForOtherTypesOfUsers(String username) {
		return ULBsInfoForOtherTypesOfUsers.builder().ulbs(getULBInfoForUsers(username)).build();
	}

	@Override
	public UserEntity getCurrentUserInfoEntity() {
		String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		Optional<UserEntity> userE = userRepo.findByUsernameIgnoreCase(username);
		return userE.get();
	}

	@Override
	public ULBInfoForULBUsersDTO getUlbInfoForUlbUser(String username) {
		List<ULBDTO> ulbs = getULBInfoForUsers(username);
		if (ulbs==null)
			return null;
		return ULBInfoForULBUsersDTO.builder().ulb(ulbs.get(0)).build();
	}

	private List<ULBDTO> getULBInfoForUsers(String username) {
		Optional<UserEntity> userO = userRepo.findByUsernameIgnoreCase(username);

		if (!userO.isPresent())
			throw new BadRequestAlertException("User not available", "Incorrect username->" + username,
					"Issue with Username for get info");
		if( userO.get().getUserProfile().getUserUlbInfo()!=null){
			return userO.get().getUserProfile().getUserUlbInfo().getUlbs().stream().map(ulb -> ulb.getDTO()).collect(Collectors.toList());
		}
		return null;
	}

	private UserInfoDTO getUserInfoFromEntity(UserEntity userE) {
		UserInfoDTO userInfoDTO= UserInfoDTO.builder().accountNonExpired(userE.isAccountNonExpired())
				.accountNonLocked(userE.isAccountNonLocked()).authority(userE.getAuthority().getName())
				.credentialsNonExpired(userE.isCredentialsNonExpired()).enabled(userE.isEnabled()).id(userE.getId())
				.mobileNo(userE.getMobileNo()).mobileVerified(userE.getMobileNoVerified()).name(userE.getName())
				.ulbDetails(userE.getUserProfile().getUserUlbInfo()!=null && userE.getUserProfile().getUserUlbInfo().getUlbs()!=null? userE.getUserProfile().getUserUlbInfo().getUlbs().stream().map(ulbEntity -> ulbEntity.getDTO()).collect(Collectors.toList()):null )
				.permanentAddress(userE.getUserProfile().getPermanentAddress()).username(userE.getUsername())
				.authorityType(userE.getAuthority().getAuthorityType().toString()).build();

		return userInfoDTO;
	}

	@Transactional
	private ULBUserInfoDTO getUlbUser(UserEntity user, UserGroupEntity userGroupEntity,
			UserProfileEntity userProfileEntity) {
		return ULBUserInfoDTO.builder().ulbId(userProfileEntity.getUserUlbInfo().getUlbs().get(0).getId())
				.accountNonExpired(user.isAccountNonExpired()).accountNonLocked(user.isAccountNonLocked())
				.authority(user.getAuthority().getName()).credentialsNonExpired(user.isCredentialsNonExpired())
				.enabled(user.isEnabled()).file(null).id(user.getId()).mobileNo(user.getMobileNo())
				.mobileVerified(user.getMobileNoVerified()).name(user.getName())
				.permanentAddress(userProfileEntity.getPermanentAddress()).username(user.getUsername()).build();

	}

	@Transactional
	private SLPMUUserInfoDTO getSLPMUUser(UserEntity user, UserGroupEntity userGroupEntity,
			UserProfileEntity userProfileEntity) {
		return SLPMUUserInfoDTO.builder().accountNonExpired(user.isAccountNonExpired())
				.accountNonLocked(user.isAccountNonLocked()).authority(user.getAuthority().getName())
				.credentialsNonExpired(user.isCredentialsNonExpired()).enabled(user.isEnabled()).file(null)
				.id(user.getId()).mobileNo(user.getMobileNo()).mobileVerified(user.getMobileNoVerified())
				.name(user.getName()).permanentAddress(userProfileEntity.getPermanentAddress())
				.username(user.getUsername()).build();

	}

	@Transactional
	private UDHDUserInfoDTO getUDHDUser(UserEntity user, UserGroupEntity userGroupEntity,
			UserProfileEntity userProfileEntity) {
		return UDHDUserInfoDTO.builder().accountNonExpired(user.isAccountNonExpired())
				.accountNonLocked(user.isAccountNonLocked()).authority(user.getAuthority().getName())
				.credentialsNonExpired(user.isCredentialsNonExpired()).enabled(user.isEnabled()).file(null)
				.id(user.getId()).mobileNo(user.getMobileNo()).mobileVerified(user.getMobileNoVerified())
				.name(user.getName()).permanentAddress(userProfileEntity.getPermanentAddress())
				.username(user.getUsername()).build();

	}

	@Transactional
	private OtherTypeUserInfoDTO getOtherTypeUlbUser(UserEntity user, UserGroupEntity userGroupEntity,
			UserProfileEntity userProfileEntity) {
		return OtherTypeUserInfoDTO.builder()
				.ulbDetails((userProfileEntity.getUserUlbInfo()!=null && userProfileEntity.getUserUlbInfo().getUlbs()!=null)?
						userProfileEntity.getUserUlbInfo().getUlbs().stream().map(ulbEntity -> ulbEntity.getDTO()).collect(Collectors.toList()):null)
				.accountNonExpired(user.isAccountNonExpired()).accountNonLocked(user.isAccountNonLocked())
				.authority(user.getAuthority().getName()).credentialsNonExpired(user.isCredentialsNonExpired())
				.enabled(user.isEnabled()).file(null).id(user.getId()).mobileNo(user.getMobileNo())
				.mobileVerified(user.getMobileNoVerified()).name(user.getName())
				.permanentAddress(userProfileEntity.getPermanentAddress()).username(user.getUsername()).build();

	}

	@Transactional
	private UserEntity createUpdateUser(UserInfoDTO ulbUserInfoDTO, AuthorityEntity authority, UserEntity existingUserE) {
		UserEntity user;
		if(existingUserE==null){
			//create user
			user = UserEntity.builder().id(ulbUserInfoDTO.getId())
					.accountNonExpired(ulbUserInfoDTO.isAccountNonExpired())
					.accountNonLocked(ulbUserInfoDTO.isAccountNonLocked()).authority(authority)
					.credentialsNonExpired(ulbUserInfoDTO.isCredentialsNonExpired()).enabled(ulbUserInfoDTO.isEnabled())
					.mobileNo(ulbUserInfoDTO.getMobileNo()).mobileNoVerified(ulbUserInfoDTO.isMobileVerified())
					.name(ulbUserInfoDTO.getName()).password(passwordEncoder.encode(ulbUserInfoDTO.getPassword()))
					.username(ulbUserInfoDTO.getUsername()).userUID(randomService.generateRandomAlphaNumericForID())
					.build();
		}else{
			user = 	UserEntity.builder()
					.id(existingUserE.getId())
					.accountNonExpired(ulbUserInfoDTO.isAccountNonExpired())
					.accountNonLocked(ulbUserInfoDTO.isAccountNonLocked())
					.credentialsNonExpired(ulbUserInfoDTO.isCredentialsNonExpired())
					.enabled(ulbUserInfoDTO.isEnabled())
					.mobileNoVerified(ulbUserInfoDTO.isMobileVerified())
					.mobileNo(ulbUserInfoDTO.getMobileNo())
					.authority(authority)
					.password(existingUserE.getPassword())
					.name(ulbUserInfoDTO.getName())
					.username(ulbUserInfoDTO.getUsername())
					.userUID(existingUserE.getUserUID())
					.build();
		}
		return userRepo.save(user);
	}

	@Transactional
	private UserGroupEntity getUserGroup(UserEntity user) {
		Optional<UserGroupEntity> grpO = userGrpRepo
				.findByNameIgnoreCaseContaining(user.getAuthority().getName() + "_user_group");
		if (!grpO.isPresent())
			throw new EntityNotFoundException("INVALIDGRPNAMEENTITYNOTFOUND");
		return grpO.get();
	}

	private UserInfoDTO fromEntity(UserEntity userE) {
		return UserInfoDTO.builder().accountNonExpired(userE.isAccountNonExpired())
				.accountNonLocked(userE.isAccountNonLocked()).authority(userE.getAuthority().getName())
				.credentialsNonExpired(userE.isCredentialsNonExpired()).enabled(userE.isEnabled()).id(userE.getId())
				.mobileNo(userE.getMobileNo()).mobileVerified(userE.getMobileNoVerified()).name(userE.getName())
				.permanentAddress(userE.getUserProfile().getPermanentAddress()).username(userE.getUsername()).build();
	}

	@Transactional
	private UserProfileEntity createUpdateUserProfile(UserEntity user, UserGroupEntity userGroup,
			String permanentAddress, FileEntity profilePic, List<ULBEntity> ulbs) {
		UserULBDataEntity userULBDataEntity= userULBDataRepository.save(UserULBDataEntity.builder().ulbs(ulbs).build());
		UserProfileEntity userProfile = UserProfileEntity.builder().permanentAddress(permanentAddress)
				.id((user.getUserProfile() != null && user.getUserProfile().getId() != null)
						? user.getUserProfile().getId()
						: null)
				.profilePic(profilePic)
				.userUlbInfo(userULBDataEntity)
				//.user(user)
				//.primaryUserGroup(userGroup)
				.build();
		userProfile= userProfileRepo.save(userProfile);
		user.setUserProfile(userProfile);
		user=userRepo.save(user);
		return userProfile;
	}

	@Transactional
	private Boolean isPermittedAuthorityInList(AuthorityTypeEnum authorityType, String authority) {
		Function<AuthorityTypeEnum, List<AuthorityEntity>> permittedAuthorities = authT -> authorityService
				.getAuthoritiesForType(authT);

		return permittedAuthorities.apply(authorityType).stream()
				.filter(auth -> auth.getName().equalsIgnoreCase(authority)).count() > 0;
	}

}
