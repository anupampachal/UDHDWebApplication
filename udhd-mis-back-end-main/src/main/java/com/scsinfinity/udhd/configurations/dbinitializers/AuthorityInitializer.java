package com.scsinfinity.udhd.configurations.dbinitializers;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.scsinfinity.udhd.dao.entities.base.AuthorityEntity;
import com.scsinfinity.udhd.dao.entities.base.AuthorityTypeEnum;
import com.scsinfinity.udhd.dao.entities.base.UserGroupEntity;
import com.scsinfinity.udhd.dao.repositories.base.IAuthorityRepository;
import com.scsinfinity.udhd.dao.repositories.base.IUserGroupRepository;
import com.scsinfinity.udhd.services.base.RandomGeneratorService;
import com.scsinfinity.udhd.services.common.AuthorityConstants;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
@Transactional
public class AuthorityInitializer {
	private final IAuthorityRepository authRepo;
	private final IUserGroupRepository userGroupRepository;
	private final RandomGeneratorService randomGeneratorService;

	public void initializeAuthority() {
		initializeAuthority(AuthorityConstants.ROLE_FLIA, AuthorityTypeEnum.OTHERS);
		initializeAuthority(AuthorityConstants.ROLE_INTERNAL_AUDITOR, AuthorityTypeEnum.OTHERS);
		initializeAuthority(AuthorityConstants.ROLE_ULB_ACCOUNTANT, AuthorityTypeEnum.ULB);
		initializeAuthority(AuthorityConstants.ROLE_ULB_CMO, AuthorityTypeEnum.ULB);
		initializeAuthority(AuthorityConstants.ROLE_SLPMU_ADMIN, AuthorityTypeEnum.SLPMU);
		initializeAuthority(AuthorityConstants.ROLE_SLPMU_ACCOUNT, AuthorityTypeEnum.SLPMU);
		initializeAuthority(AuthorityConstants.ROLE_SLPMU_AUDIT, AuthorityTypeEnum.SLPMU);
		initializeAuthority(AuthorityConstants.ROLE_SLPMU_UC, AuthorityTypeEnum.SLPMU);
		initializeAuthority(AuthorityConstants.ROLE_SLPMU_IT, AuthorityTypeEnum.SLPMU);
		initializeAuthority(AuthorityConstants.ROLE_UDHD_PSEC, AuthorityTypeEnum.UDHD);
		initializeAuthority(AuthorityConstants.ROLE_UDHD_SEC, AuthorityTypeEnum.UDHD);
		initializeAuthority(AuthorityConstants.ROLE_UDHD_SO, AuthorityTypeEnum.UDHD);
		initializeAuthority(AuthorityConstants.ROLE_UDHD_IT, AuthorityTypeEnum.UDHD);

	}

	private void initializeAuthority(String name, AuthorityTypeEnum type) {
		authRepo.save(new AuthorityEntity(name, type));
		initUserGroup(name + "_user_group");
	}

	private void initUserGroup(String name) {
		userGroupRepository.save(UserGroupEntity.builder().name(name).description(name).status(true)
				.userGroupId(randomGeneratorService.generateRandomAlphaNumericForID()).build());
	}

}
