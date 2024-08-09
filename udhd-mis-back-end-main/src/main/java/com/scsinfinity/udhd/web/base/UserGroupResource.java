package com.scsinfinity.udhd.web.base;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scsinfinity.udhd.services.auth.dtoandconstants.UserGroupDTO;
import com.scsinfinity.udhd.services.base.interfaces.IUserGroupService;
import com.scsinfinity.udhd.services.common.GenericResponseObject;
import com.scsinfinity.udhd.services.common.Pagination;
import com.scsinfinity.udhd.services.settings.dto.UserInfoDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/settings/user-group")
public class UserGroupResource {

	private final IUserGroupService userGroupService;

	public UserGroupResource(IUserGroupService userGroupService) {
		super();
		this.userGroupService = userGroupService;
	}

	/**
	 * Find usergroup using its userGroup id
	 * 
	 * @pathvariable userGroupId
	 * @return UserGroupDTO
	 */
	@GetMapping("/{userGroupId}")
	public ResponseEntity<UserGroupDTO> getUserGroupById(@PathVariable String userGroupId) {
		log.info("getUserGroupById", userGroupId);
		return ResponseEntity.ok(userGroupService.findByUserGroupId(userGroupId));
	}

	/**
	 * Find users that are present in user group and are active using its userGroup
	 * id
	 * 
	 * @pathvariable userGroupId
	 * @return paginated list of members
	 */
	@PostMapping("/members/{userGroupId}")
	public ResponseEntity<GenericResponseObject<UserInfoDTO>> getUsersByUserGroupId(@PathVariable String userGroupId,
			@RequestBody Pagination<UserInfoDTO> data) {
		log.info("getUserGroupById", userGroupId);
		return ResponseEntity.ok(userGroupService.getUsersByUserGroupId(userGroupId, data));
	}

	@PostMapping("/members/all/{userGroupId}")
	public ResponseEntity<GenericResponseObject<UserInfoDTO>> getAllUsersToAddToMembersList(
			@PathVariable String userGroupId,@RequestBody Pagination<UserInfoDTO> data) {
		return ResponseEntity.ok(userGroupService.getUsersForAddByPage(userGroupId,data));
	}
	
	@PostMapping("/members/add")
	public ResponseEntity<GenericResponseObject<UserInfoDTO>>getAllUsersToAdd(@RequestBody Pagination<UserInfoDTO> data){
		return ResponseEntity.ok(userGroupService.getUsersForAddByPageForCreate(data));
	}

	@PutMapping("/members/add/{userGroupId}")
	public ResponseEntity<UserGroupDTO> addUsersToUserGroup(@PathVariable String userGroupId,
			@RequestBody List<UserInfoDTO> usersToAddToMembersList) {
		return ResponseEntity.ok(userGroupService.addUsersToUserGroupMembersList(userGroupId, usersToAddToMembersList));
	}

	@DeleteMapping("/members/remove/{userGroupId}")
	public ResponseEntity<UserGroupDTO> removeUsersFromUserGroup(@PathVariable String userGroupId,
			@RequestBody List<UserInfoDTO> usersToRemoveFromMembersList) {
		return ResponseEntity
				.ok(userGroupService.removeUsersFromUserGroupMembersList(userGroupId, usersToRemoveFromMembersList));
	}

	/**
	 * Load page wise userGroup data with searching, sorting and filtering
	 * 
	 * @param data
	 * @return UserGroup data in paginated format
	 */
	@PostMapping("/post/info")
	public ResponseEntity<GenericResponseObject<UserGroupDTO>> getUserGroupByPage(
			@RequestBody Pagination<UserGroupDTO> data) {
		log.info("getUserGroupByPage", data);
		return ResponseEntity.ok(userGroupService.loadUserGroupByPage(data));
	}

	/**
	 * get all usergroups for user creation
	 * 
	 * @return list of usergroupdto
	 */
	@GetMapping("/post/info")
	public ResponseEntity<List<UserGroupDTO>> getAllUserGroups() {
		log.info("getAllUserGroups");
		return ResponseEntity.ok(userGroupService.loadAllUserGroups());
	}

	/**
	 * create or update usergroup
	 * 
	 * @param userGroup
	 * @return newly created usergroup
	 */
	@PostMapping("/post/save")
	public ResponseEntity<UserGroupDTO> createUpdateUserGroup(@RequestBody UserGroupDTO userGroup) {
		log.info("createUpdateUserGroup", userGroup);
		return ResponseEntity.ok(userGroupService.saveAndUpdate(userGroup));
	}

	/**
	 * Delete user group by id
	 * 
	 * @param userGroupId
	 * @return
	 */
	@DeleteMapping("/post/save/{userGroupId}")
	public ResponseEntity<Object> deleteUserGroupById(@PathVariable String userGroupId) {
		UserGroupDTO usrG = userGroupService.deleteUsergroup(userGroupId);
		return ResponseEntity.status(HttpStatus.OK).body(usrG);
	}
}
