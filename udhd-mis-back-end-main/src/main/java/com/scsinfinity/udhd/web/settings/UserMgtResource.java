package com.scsinfinity.udhd.web.settings;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scsinfinity.udhd.dao.entities.base.AuthorityEntity;
import com.scsinfinity.udhd.dao.entities.base.AuthorityTypeEnum;
import com.scsinfinity.udhd.dao.entities.geography.ULBType;
import com.scsinfinity.udhd.services.common.GenericResponseObject;
import com.scsinfinity.udhd.services.common.Pagination;
import com.scsinfinity.udhd.services.settings.dto.GeoDistrictDTO;
import com.scsinfinity.udhd.services.settings.dto.GeoDivisionDTO;
import com.scsinfinity.udhd.services.settings.dto.OtherTypeUserInfoDTO;
import com.scsinfinity.udhd.services.settings.dto.SLPMUUserInfoDTO;
import com.scsinfinity.udhd.services.settings.dto.UDHDUserInfoDTO;
import com.scsinfinity.udhd.services.settings.dto.ULBDTO;
import com.scsinfinity.udhd.services.settings.dto.ULBInfoForULBUsersDTO;
import com.scsinfinity.udhd.services.settings.dto.ULBUserInfoDTO;
import com.scsinfinity.udhd.services.settings.dto.ULBsInfoForOtherTypesOfUsers;
import com.scsinfinity.udhd.services.settings.dto.UserInfoDTO;
import com.scsinfinity.udhd.services.settings.interfaces.IAuthorityService;
import com.scsinfinity.udhd.services.settings.interfaces.IGeoDistrictService;
import com.scsinfinity.udhd.services.settings.interfaces.IGeoDivisionService;
import com.scsinfinity.udhd.services.settings.interfaces.IULBService;
import com.scsinfinity.udhd.services.settings.interfaces.IUserMgtService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.scsinfinity.udhd.services.common.AuthorityConstants.ROLE_FLIA;
import static com.scsinfinity.udhd.services.common.AuthorityConstants.ROLE_INTERNAL_AUDITOR;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/settings/user-mgt")
public class UserMgtResource {

	private final IUserMgtService userMgtService;
	private final IULBService ulbService;
	private final IGeoDivisionService geoDivisionService;
	private final IGeoDistrictService geoDistrictService;
	private final IAuthorityService authorityService;

	@GetMapping("/{id}")
	public ResponseEntity<UserInfoDTO> findUserInfoById(@PathVariable Long id) {
		log.debug("findUserInfoById ", id);
		return ResponseEntity.ok(userMgtService.getUserInfo(id));
	}

	@GetMapping("/ulb/{username}")
	public ResponseEntity<ULBInfoForULBUsersDTO> getULBForULBUserInfo(@PathVariable String username) {
		log.debug("getULBUserInfo for ", username);
		return ResponseEntity.ok(userMgtService.getUlbInfoForUlbUser(username));
	}

	@GetMapping("/other/{username}")
	public ResponseEntity<ULBsInfoForOtherTypesOfUsers> getULBsForOtherUserInfo(@PathVariable String username) {
		log.debug("getULBUserInfo for ", username);
		return ResponseEntity.ok(userMgtService.getUlBsInfoForOtherTypesOfUsers(username));
	}

	/**
	 * getUserInfoByPage
	 * 
	 * @param data
	 * @return
	 */
	@PostMapping("/info")
	public ResponseEntity<GenericResponseObject<UserInfoDTO>> getUserInfoByPage(
			@Valid @RequestBody Pagination<UserInfoDTO> data) {
		log.info("getUserInfoByPage", data);
		return ResponseEntity.ok(userMgtService.loadUsersByPage(data));
	}

	/**
	 * 
	 * @param ulbUserInfoDTO
	 * @return
	 */
	@PostMapping("/ulb")
	public ResponseEntity<ULBUserInfoDTO> createUpdateULBUser(@RequestBody ULBUserInfoDTO ulbUserInfoDTO) {
		return ResponseEntity.ok(userMgtService.createUpdateUlbUser(ulbUserInfoDTO));
	}

	/**
	 * 
	 * @param slpmuUserInfoDTO
	 * @return SLPMUUserInfoDTO
	 */
	@PostMapping("/slpmu")
	public ResponseEntity<SLPMUUserInfoDTO> createUpdateSLPMUUser(@RequestBody SLPMUUserInfoDTO slpmuUserInfoDTO) {
		return ResponseEntity.ok(userMgtService.createUpdateSLPMUUser(slpmuUserInfoDTO));
	}

	/**
	 * 
	 * @param udhdUserInfoDTO
	 * @return UDHDUserInfoDTO
	 */
	@PostMapping("/udhd")
	public ResponseEntity<UDHDUserInfoDTO> createUpdateUDHDUser(@RequestBody UDHDUserInfoDTO udhdUserInfoDTO) {
		return ResponseEntity.ok(userMgtService.createUpdateUDHDUser(udhdUserInfoDTO));
	}

	/**
	 * 
	 * @param otherTypeUserInfoDTO
	 * @return OtherTypeUserInfoDTO
	 */
	@PostMapping("/others")
	public ResponseEntity<OtherTypeUserInfoDTO> createUpdateOthersUser(
			@RequestBody OtherTypeUserInfoDTO otherTypeUserInfoDTO) {
		return ResponseEntity.ok(userMgtService.createUpdateOtherTypeUser(otherTypeUserInfoDTO));
	}

	@GetMapping("/division")
	public ResponseEntity<List<GeoDivisionDTO>> getDivisionList() {
		return ResponseEntity.ok(geoDivisionService.getAllActiveGeoDivision());
	}

	@GetMapping("/district/{divisionId}")
	public ResponseEntity<List<GeoDistrictDTO>> getDistrictList(@PathVariable Long divisionId) {
		return ResponseEntity.ok(geoDistrictService.getGeoDistrictForDivision(divisionId));
	}

	@GetMapping("/ulb-dis/{geoDistrictId}/type/{ulbType}")
	public ResponseEntity<List<ULBDTO>> getULBsForDistrict(@PathVariable Long geoDistrictId,
			@PathVariable ULBType ulbType) {
		return ResponseEntity.ok(ulbService.getULBsForDistrictAndULBType(geoDistrictId, ulbType));
	}
/*
	@GetMapping("/auth/others")
	public ResponseEntity<List<String>> getOthersAuthorityType(){
		List<String> authorityList= new ArrayList<>();
		authorityList.add(ROLE_FLIA);
		authorityList.add(ROLE_INTERNAL_AUDITOR);
		return ResponseEntity.ok(authorityList);
	}
*/
	/**
	 * get ulb list for crerating of acdc bill
	 */

	@GetMapping("/ulb-list")
	public ResponseEntity<List<ULBDTO>> getULBs() {
		return ResponseEntity.ok(ulbService.getAllActiveULBs());
	}

	@GetMapping("/auth/{authType}")
	public ResponseEntity<List<AuthorityEntity>> getAuthorityInfo(@PathVariable AuthorityTypeEnum authType) {
		return ResponseEntity.ok(authorityService.getAuthoritiesForType(authType));
	}

}
