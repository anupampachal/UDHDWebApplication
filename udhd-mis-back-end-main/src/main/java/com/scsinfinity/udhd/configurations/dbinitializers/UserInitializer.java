package com.scsinfinity.udhd.configurations.dbinitializers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.scsinfinity.udhd.dao.entities.base.AuthorityEntity;
import com.scsinfinity.udhd.dao.entities.base.AuthorityTypeEnum;
import com.scsinfinity.udhd.dao.entities.base.UserEntity;
import com.scsinfinity.udhd.dao.entities.base.UserGroupEntity;
import com.scsinfinity.udhd.dao.entities.base.UserProfileEntity;
import com.scsinfinity.udhd.dao.entities.base.UserULBDataEntity;
import com.scsinfinity.udhd.dao.entities.geography.ULBEntity;
import com.scsinfinity.udhd.dao.repositories.base.IAuthorityRepository;
import com.scsinfinity.udhd.dao.repositories.base.IUserGroupRepository;
import com.scsinfinity.udhd.dao.repositories.base.IUserProfileRepository;
import com.scsinfinity.udhd.dao.repositories.base.IUserRepository;
import com.scsinfinity.udhd.dao.repositories.base.IUserULBDataRepository;
import com.scsinfinity.udhd.dao.repositories.geography.IULBRepository;
import com.scsinfinity.udhd.services.base.RandomGeneratorService;
import com.scsinfinity.udhd.services.common.AuthorityConstants;

import lombok.AllArgsConstructor;

@Component
@Transactional
@AllArgsConstructor
public class UserInitializer {

	private final PasswordEncoder passwordEncoder;
	private final RandomGeneratorService randomGeneratorService;
	private final IUserProfileRepository userProfileRepo;
	private final IUserRepository userRepo;
	private final IUserGroupRepository userGrpRepo;
	private final IAuthorityRepository authRepo;
	private final IULBRepository ulbRepo;
	private final IUserULBDataRepository userULBDataRepo;

	public void initializeUsers(String args) {
		AuthorityEntity slpmuadmin = authRepo.findByName(AuthorityConstants.ROLE_SLPMU_ADMIN).get();
		AuthorityEntity udhdso = authRepo.findByName(AuthorityConstants.ROLE_UDHD_SO).get();
		AuthorityEntity ulbAccountant = authRepo.findByName(AuthorityConstants.ROLE_ULB_ACCOUNTANT).get();
		AuthorityEntity slpmuit = authRepo.findByName(AuthorityConstants.ROLE_SLPMU_IT).get();
		AuthorityEntity slpmuAudit = authRepo.findByName(AuthorityConstants.ROLE_SLPMU_AUDIT).get();
		AuthorityEntity flia = authRepo.findByName(AuthorityConstants.ROLE_FLIA).get();
		AuthorityEntity ia = authRepo.findByName(AuthorityConstants.ROLE_INTERNAL_AUDITOR).get();

		createUser("slpmuadmin@scsinfinity.com", "SLPMU Admin", "9876543210", "Patna", slpmuadmin,
				AuthorityConstants.ROLE_SLPMU_ADMIN + "_user_group", "ulbJhajhaNPt");
		createUser("udhdso@scsinfinity.com", "UDHD SO", "8765432109", "Patna", udhdso,
				AuthorityConstants.ROLE_UDHD_SO + "_user_group", "ulbJhajhaNPt");
		createUser("ulbaccountant@scsinfinity.com", "ULB Accountant", "7654321098", "Patna", ulbAccountant,
				AuthorityConstants.ROLE_ULB_ACCOUNTANT + "_user_group", "ulbJhajhaNPt");
		createUser("slpmuit@scsinfinity.com", "SLPMU IT", "2345678901", "Patna", slpmuit,
				AuthorityConstants.ROLE_SLPMU_IT + "_user_group", "ulbJhajhaNPt");
		createUser("slpmuaudit@scsinfinity.com", "SLPMU Audit", "2345878901", "Patna", slpmuAudit,
				AuthorityConstants.ROLE_SLPMU_AUDIT + "_user_group", "ulbJhajhaNPt");
		createUser("flia@scsinfinity.com", "FLIA", "4774533291", "Patna", flia,
				AuthorityConstants.ROLE_FLIA + "_user_group", "ulbJhajhaNPt");
		createUser("ia@scsinfinity.com", "Internal Auditor", "4774533323", "Patna", ia,
				AuthorityConstants.ROLE_INTERNAL_AUDITOR + "_user_group", "ulbJhajhaNPt");

	}

	private UserEntity createUser(String username, String name, String contactNo, String address,
			AuthorityEntity authority, String userGroupName, String ulbCode) {

		UserProfileEntity profile = new UserProfileEntity();
		UserEntity user = UserEntity.builder().username(username).password(passwordEncoder.encode("password"))
				.name(name).userUID(randomGeneratorService.generateRandomAlphaNumericForID()).mobileNoVerified(true)
				.enabled(true).userProfile(profile).authority(authority).mobileNo(contactNo).accountNonExpired(true)
				.accountNonLocked(true).credentialsNonExpired(true).build();

		user = userRepo.save(user);
		profile = user.getUserProfile();
		// profile.setUser(user);
		profile = userProfileRepo.save(profile);

		Optional<UserGroupEntity> grpO = userGrpRepo.findByNameIgnoreCaseContaining(userGroupName);
		if (!grpO.isPresent())
			throw new EntityNotFoundException("INVALIDGRPNAMEENTITYNOTFOUND");
		UserGroupEntity ug = grpO.get();
		// profile.setPrimaryUserGroup(ug);
		/*
		 * if (profile.getUserGroups() == null) { List<UserGroupEntity> userGroups = new
		 * ArrayList<>(); userGroups.add(ug); profile.setUserGroups(userGroups); } else
		 * if (profile.getUserGroups() != null && !profile.getUserGroups().contains(ug))
		 * { profile.getUserGroups().add(ug); }
		 */
		List<UserProfileEntity> userProfilesE = ug.getUserProfiles();
		if (userProfilesE == null) {
			userProfilesE = new ArrayList<UserProfileEntity>();
		}
		userProfilesE.add(profile);
		ug.setUserProfiles(userProfilesE);

		userProfileRepo.save(profile);

		if ((authority.getAuthorityType().equals(AuthorityTypeEnum.ULB)
				|| (authority.getAuthorityType().equals(AuthorityTypeEnum.OTHERS))) && ulbCode != null) {
			Optional<ULBEntity> ulbO = ulbRepo.findByCodeIgnoreCase(ulbCode);
			List<ULBEntity> ulbs = new ArrayList<ULBEntity>();
			ulbs.add(ulbO.get());
			UserULBDataEntity ulbUserData = userULBDataRepo.save(UserULBDataEntity.builder().ulbs(ulbs).build());
			// profile.setUlbs(ulbs);
			profile.setUserUlbInfo(ulbUserData);
			profile = userProfileRepo.save(profile);
		}

		user.setUserProfile(profile);
		return userRepo.save(user);
	}
}
