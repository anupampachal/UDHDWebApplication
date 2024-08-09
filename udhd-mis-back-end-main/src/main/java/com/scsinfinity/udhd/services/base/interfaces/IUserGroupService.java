package com.scsinfinity.udhd.services.base.interfaces;

import java.util.List;

import com.scsinfinity.udhd.dao.entities.base.UserGroupEntity;
import com.scsinfinity.udhd.services.auth.dtoandconstants.UserGroupDTO;
import com.scsinfinity.udhd.services.common.GenericResponseObject;
import com.scsinfinity.udhd.services.common.Pagination;
import com.scsinfinity.udhd.services.settings.dto.UserInfoDTO;

public interface IUserGroupService {

	UserGroupDTO findByUserGroupId(String userGroupId);
	
	GenericResponseObject<UserInfoDTO> getUsersByUserGroupId(String userGroupId,Pagination<UserInfoDTO> data);

	UserGroupEntity findEntityByUserGroupId(String userGroupId);

	UserGroupDTO saveAndUpdate(UserGroupDTO userGroupDTO);

	List<UserGroupDTO> loadAllUserGroups();

	GenericResponseObject<UserGroupDTO> loadUserGroupByPage(Pagination<UserGroupDTO> data);

	UserGroupDTO deleteUsergroup(String userGroupId);

	GenericResponseObject<UserInfoDTO> getUsersForAddByPage(String userGroupId,Pagination<UserInfoDTO> data);

	UserGroupDTO addUsersToUserGroupMembersList(String userGroupId,List<UserInfoDTO> usersToAddToMembersList);

	UserGroupDTO removeUsersFromUserGroupMembersList(String userGroupId, List<UserInfoDTO> usersToRemoveFromMembersList);

	GenericResponseObject<UserInfoDTO> getUsersForAddByPageForCreate(Pagination<UserInfoDTO> data);
}
