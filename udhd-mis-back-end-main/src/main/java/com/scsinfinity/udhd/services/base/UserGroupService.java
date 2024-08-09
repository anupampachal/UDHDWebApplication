package com.scsinfinity.udhd.services.base;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.scsinfinity.udhd.dao.entities.base.UserEntity;
import com.scsinfinity.udhd.dao.entities.base.UserGroupEntity;
import com.scsinfinity.udhd.dao.entities.base.UserProfileEntity;
import com.scsinfinity.udhd.dao.repositories.base.IUserGroupRepository;
import com.scsinfinity.udhd.dao.repositories.base.IUserProfileRepository;
import com.scsinfinity.udhd.dao.repositories.base.IUserRepository;
import com.scsinfinity.udhd.services.auth.dtoandconstants.UserGroupDTO;
import com.scsinfinity.udhd.services.base.interfaces.IUserGroupService;
import com.scsinfinity.udhd.services.common.AuthorityConstants;
import com.scsinfinity.udhd.services.common.GenericResponseObject;
import com.scsinfinity.udhd.services.common.Pagination;
import com.scsinfinity.udhd.services.common.SortDirection;
import com.scsinfinity.udhd.services.settings.dto.UserInfoDTO;
import com.scsinfinity.udhd.services.settings.interfaces.IPaginationInfoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserGroupService implements IUserGroupService {

	private final IUserGroupRepository userGroupRepository;
	private final RandomGeneratorService randomGeneratorService;
	private final IPaginationInfoService<UserInfoDTO, UserProfileEntity> paginationService;
	private final IUserProfileRepository userProfileRepository;
	private final IUserRepository userRepo;

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_SLPMU_IT + "','"
			+ AuthorityConstants.ROLE_SLPMU_UC + "','" + AuthorityConstants.ROLE_UDHD_IT + "','"
			+ AuthorityConstants.ROLE_UDHD_PSEC + "','" + AuthorityConstants.ROLE_UDHD_SEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")
	public UserGroupDTO findByUserGroupId(String userGroupId) {
		log.info("find by usergroupid->", userGroupId);
		UserGroupEntity userGroupE = getEntityById(userGroupId);
		return userGroupE.toDto();
	}

	private UserGroupEntity getEntityById(String userGroupId) {
		return userGroupRepository.findByUserGroupIdIgnoreCase(userGroupId)
				.orElseThrow(() -> new EntityNotFoundException("findByUserGroupId ->" + userGroupId));
	}

	/**
	 * Create and update userGroupDTO
	 */

	@Override
	@Transactional
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_SLPMU_IT + "','" + AuthorityConstants.ROLE_UDHD_IT
			+ "')")
	public UserGroupDTO saveAndUpdate(UserGroupDTO userGroupDTO) {
		UserGroupEntity userGroupEntity = null;
		// update case
		if (userGroupDTO.getId() != null) {
			userGroupEntity = userGroupRepository.findByUserGroupIdIgnoreCase(userGroupDTO.getId())
					.orElseThrow(EntityNotFoundException::new);
			return updateUserGroup(userGroupEntity, userGroupDTO);
		}

		// create case
		return userGroupRepository
				.save(userGroupDTO.getNewUserGroupEntity(randomGeneratorService.generateRandomAlphaNumericForID()))
				.toDto();
	}

	/**
	 * Paginated, sorted and filtered data
	 */

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_SLPMU_IT + "','"
			+ AuthorityConstants.ROLE_SLPMU_UC + "','" + AuthorityConstants.ROLE_UDHD_IT + "','"
			+ AuthorityConstants.ROLE_UDHD_PSEC + "','" + AuthorityConstants.ROLE_UDHD_SEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")
	public GenericResponseObject<UserGroupDTO> loadUserGroupByPage(Pagination<UserGroupDTO> data) {
		Pageable pageable = PageRequest.of(data.getPageNo(), data.getPageSize());

		// sorting logic
		if (data.getSortedBy() != null && data.getDirectionOfSort() != null) {
			pageable = PageRequest.of(data.getPageNo(), data.getPageSize(), Sort.by(data.getSortedBy()));
			if (data.getDirectionOfSort() == SortDirection.DESCENDING.toString()) {
				pageable = PageRequest.of(data.getPageNo(), data.getPageSize(),
						Sort.by(data.getSortedBy()).descending());
			}
		} else {
			pageable = PageRequest.of(data.getPageNo(), data.getPageSize());
		}

		Page<UserGroupDTO> userGroups = null;

		// filter list and sorting
		switch (data.getCriteria().trim().toUpperCase()) {
		case "ALL":
			userGroups = userGroupRepository.findAll(pageable).map(uge -> uge.toDto());
			break;
		case "NAME":
			userGroups = userGroupRepository.findByNameIgnoreCaseContainingOrDescriptionIgnoreCaseContaining(
					data.getQueryString(), data.getQueryString(), pageable).map(uge -> uge.toDto());

		}
		return new GenericResponseObject<UserGroupDTO>(userGroups.getTotalElements(), userGroups, data.getPageNo(),
				data.getPageSize());
	}

	/**
	 * delete a usergroup
	 */
	@Override
	@Transactional
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_SLPMU_IT + "','" + AuthorityConstants.ROLE_UDHD_IT
			+ "')")
	public UserGroupDTO deleteUsergroup(String userGroupId) {
		UserGroupEntity usgr = userGroupRepository.findByUserGroupIdIgnoreCase(userGroupId)
				.orElseThrow(EntityNotFoundException::new);
		usgr.setStatus(false);
		return userGroupRepository.save(usgr).toDto();

	}

	private UserGroupDTO updateUserGroup(UserGroupEntity userGroupEntity, UserGroupDTO userGroup) {
		UserGroupEntity userGroupE = UserGroupEntity.builder().description(userGroup.getDescription())
				.name(userGroup.getName()).userGroupId(userGroup.getId()).status(userGroup.isStatus())
				.id(userGroupEntity.getId()).userProfiles(userGroupEntity.getUserProfiles()).build();

		return userGroupRepository.save(userGroupE).toDto();
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_SLPMU_IT + "','" + AuthorityConstants.ROLE_UDHD_IT
			+ "')")
	public List<UserGroupDTO> loadAllUserGroups() {
		return userGroupRepository.findAll().stream().map(userGEntity -> userGEntity.toDto())
				.collect(Collectors.toList());
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_SLPMU_IT + "','" + AuthorityConstants.ROLE_UDHD_IT
			+ "')")
	public UserGroupEntity findEntityByUserGroupId(String userGroupId) {
		return userGroupRepository.findByUserGroupIdIgnoreCase(userGroupId)
				.orElseThrow(() -> new EntityNotFoundException("findEntityByUserGroupId->" + userGroupId));

	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_SLPMU_IT + "','"
			+ AuthorityConstants.ROLE_SLPMU_UC + "','" + AuthorityConstants.ROLE_UDHD_IT + "','"
			+ AuthorityConstants.ROLE_UDHD_PSEC + "','" + AuthorityConstants.ROLE_UDHD_SEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")
	public GenericResponseObject<UserInfoDTO> getUsersByUserGroupId(String userGroupId, Pagination<UserInfoDTO> data) {
		Pageable pageable = paginationService.getPaginationRequestInfo(data);
		UserGroupEntity userGroup = userGroupRepository.findByUserGroupIdIgnoreCase(userGroupId)
				.orElseThrow(() -> new EntityNotFoundException("User group: " + userGroupId));
		List<UserProfileEntity> userProfiles = userGroup.getUserProfiles();
		Page<UserProfileEntity> userProfilesPage = searchPage(userProfiles, data.getPageNo(), data.getPageSize());
		Page<UserInfoDTO> members = paginationService.getDataAsDTO(userProfilesPage, en -> getDTOFromProfileEntity(en));
		return new GenericResponseObject<UserInfoDTO>(members.getTotalElements(), members, data.getPageNo(),
				data.getPageSize());
	}

	public Page<UserProfileEntity> searchPage(List<UserProfileEntity> upes, int page, int size) {
		Pageable paging = PageRequest.of(page, size);
		int start = Math.min((int) paging.getOffset(), upes.size());
		int end = Math.min((start + paging.getPageSize()), upes.size());

		return new PageImpl<>(upes.subList(start, end), paging, upes.size());
	}

	private UserInfoDTO getDTOFromProfileEntity(UserProfileEntity en) {
		UserEntity user = userRepo.findByUserProfile_Id(en.getId()).orElseThrow(() -> new EntityNotFoundException());
		return UserInfoDTO.builder().accountNonExpired(user.isAccountNonExpired())
				.accountNonLocked(user.isAccountNonLocked()).credentialsNonExpired(user.isCredentialsNonExpired())
				.enabled(user.getEnabled()).authority(user.getAuthority().getName()).id(user.getId())
				.mobileNo(user.getMobileNo()).mobileVerified(user.getMobileNoVerified()).name(user.getName())
				.permanentAddress(en.getPermanentAddress()).profileId(en.getId()).username(user.getUsername()).build();

	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_SLPMU_IT + "','" + AuthorityConstants.ROLE_UDHD_IT
			+ "')")
	public GenericResponseObject<UserInfoDTO> getUsersForAddByPage(String userGroupId, Pagination<UserInfoDTO> data) {
		Pageable pageable = paginationService.getPaginationRequestInfo(data);
		UserGroupEntity userGroup = getEntityById(userGroupId);

		List<Long> idsToExclude = null;
		Page<UserProfileEntity> userPage = null;
		List<UserProfileEntity> existingMembers = userGroup.getUserProfiles();
		/*
		if (existingMembers.size() != 0) {
			idsToExclude = existingMembers.stream().map(mem -> mem.getId()).collect(Collectors.toList());
			userPage = userRepo.findByIdNotInAndAccountNonExpiredAndAccountNonLockedAndCredentialsNonExpiredAndEnabled(
							idsToExclude, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, pageable)
					.stream().map(user->user.getUserProfile());
		} else {
			userPage = userProfileRepository
					.findByUser_AccountNonExpiredAndUser_AccountNonLockedAndUser_CredentialsNonExpiredAndUser_Enabled(
							Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, pageable);

		}*/
		Page<UserInfoDTO> members = paginationService.getDataAsDTO(userPage, en -> getDTOFromProfileEntity(en));
		return new GenericResponseObject<UserInfoDTO>(members.getTotalElements(), members, data.getPageNo(),
				data.getPageSize());
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_SLPMU_IT + "','" + AuthorityConstants.ROLE_UDHD_IT
			+ "')")
	public UserGroupDTO addUsersToUserGroupMembersList(String userGroupId, List<UserInfoDTO> usersToAddToMembersList) {
		UserGroupEntity userGroup = getEntityById(userGroupId);
		List<UserProfileEntity> existingMembers = userGroup.getUserProfiles();
		List<UserProfileEntity> userProfiles = userProfileRepository.findAllByIdIn(
				usersToAddToMembersList.stream().map(udt -> udt.getProfileId()).collect(Collectors.toList()));
		if (!existingMembers.isEmpty()) {
			userProfiles.removeAll(existingMembers);
			existingMembers.addAll(userProfiles);

		} else {
			existingMembers = new ArrayList<>();
			existingMembers.addAll(userProfiles);
			userGroup.setUserProfiles(userProfiles);
		}
		userGroup = userGroupRepository.save(userGroup);

		return userGroup.toDto();
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_SLPMU_IT + "','" + AuthorityConstants.ROLE_UDHD_IT
			+ "')")
	public UserGroupDTO removeUsersFromUserGroupMembersList(String userGroupId,
			List<UserInfoDTO> usersToRemoveFromMembersList) {
		UserGroupEntity userGroup = getEntityById(userGroupId);
		List<UserProfileEntity> existingMembers = userGroup.getUserProfiles();
		List<UserProfileEntity> userProfiles = userProfileRepository.findAllByIdIn(
				usersToRemoveFromMembersList.stream().map(udt -> udt.getProfileId()).collect(Collectors.toList()));
		if (existingMembers.isEmpty()) {
			return userGroup.toDto();
		}
		existingMembers.removeAll(userProfiles);
		userGroup = userGroupRepository.save(userGroup);

		return userGroup.toDto();
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_SLPMU_IT + "','" + AuthorityConstants.ROLE_UDHD_IT
			+ "')")
	public GenericResponseObject<UserInfoDTO> getUsersForAddByPageForCreate(Pagination<UserInfoDTO> data) {
		Pageable pageable = paginationService.getPaginationRequestInfo(data);
		Page<UserProfileEntity> userPage = null;
		/*userProfileRepository
				.findByUser_AccountNonExpiredAndUser_AccountNonLockedAndUser_CredentialsNonExpiredAndUser_Enabled(
						Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, pageable);*/
		Page<UserInfoDTO> members = paginationService.getDataAsDTO(userPage, en -> getDTOFromProfileEntity(en));
		return new GenericResponseObject<UserInfoDTO>(members.getTotalElements(), members, data.getPageNo(),
				data.getPageSize());

	}

}