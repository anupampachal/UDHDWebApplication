package com.scsinfinity.udhd.services.settings.interfaces;

import com.scsinfinity.udhd.dao.entities.base.UserEntity;
import com.scsinfinity.udhd.services.common.GenericResponseObject;
import com.scsinfinity.udhd.services.common.Pagination;
import com.scsinfinity.udhd.services.settings.dto.OtherTypeUserInfoDTO;
import com.scsinfinity.udhd.services.settings.dto.SLPMUUserInfoDTO;
import com.scsinfinity.udhd.services.settings.dto.UDHDUserInfoDTO;
import com.scsinfinity.udhd.services.settings.dto.ULBInfoForULBUsersDTO;
import com.scsinfinity.udhd.services.settings.dto.ULBUserInfoDTO;
import com.scsinfinity.udhd.services.settings.dto.ULBsInfoForOtherTypesOfUsers;
import com.scsinfinity.udhd.services.settings.dto.UserInfoDTO;

public interface IUserMgtService {

	SLPMUUserInfoDTO createUpdateSLPMUUser(SLPMUUserInfoDTO slpmuUser);

	UserInfoDTO getUserInfo(Long id);

	/**
	 * @param data
	 * @return
	 */
	GenericResponseObject<UserInfoDTO> loadUsersByPage(Pagination<UserInfoDTO> data);

	UDHDUserInfoDTO createUpdateUDHDUser(UDHDUserInfoDTO udhdUser);

	OtherTypeUserInfoDTO createUpdateOtherTypeUser(OtherTypeUserInfoDTO otherUser);

	ULBsInfoForOtherTypesOfUsers getUlBsInfoForOtherTypesOfUsers(String username);

	ULBUserInfoDTO createUpdateUlbUser(ULBUserInfoDTO ulbUserInfoDTO);

	ULBInfoForULBUsersDTO getUlbInfoForUlbUser(String username);

	UserInfoDTO getCurrentUserInfo();

	UserEntity getCurrentUserInfoEntity();

}
