package com.scsinfinity.udhd.services.settings;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.scsinfinity.udhd.configurations.security.SecuredUserInfoService;
import com.scsinfinity.udhd.dao.entities.base.UserEntity;
import com.scsinfinity.udhd.dao.entities.base.UserProfileEntity;
import com.scsinfinity.udhd.dao.entities.storage.FileEntity;
import com.scsinfinity.udhd.dao.repositories.base.IUserProfileRepository;
import com.scsinfinity.udhd.dao.repositories.base.IUserRepository;
import com.scsinfinity.udhd.services.base.dto.FileDTO;
import com.scsinfinity.udhd.services.base.dto.FileMultipartDTO;
import com.scsinfinity.udhd.services.base.interfaces.IFileService;
import com.scsinfinity.udhd.services.settings.dto.MyProfileRequestDTO;
import com.scsinfinity.udhd.services.settings.dto.MyProfileResponseDTO;
import com.scsinfinity.udhd.services.settings.interfaces.IMyProfileService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MyProfileService implements IMyProfileService {
	@Value("${scs.setting.folder.profile.nickname}")
	private String profileFolderNickName;

	private final SecuredUserInfoService securedUserService;
	private final IUserProfileRepository userProfileRepository;
	private final IUserRepository userRepository;
	private final IFileService fileService;
	private final PasswordEncoder passwordEncoder;

	public MyProfileService(IUserProfileRepository userProfileRepository, IUserRepository userRepository,
			IFileService fileService, PasswordEncoder passwordEncoder,
			// IFolderService folderService,
			SecuredUserInfoService securedUserService) {
		super();
		this.userProfileRepository = userProfileRepository;
		this.userRepository = userRepository;
		this.fileService = fileService;
		this.passwordEncoder = passwordEncoder;
		// this.folderService = folderService;
		this.securedUserService = securedUserService;
	}

	/**
	 * Get profile info
	 */
	@Override
	public MyProfileResponseDTO getProfileInfo() {
		UserEntity user = getUserInfo();
		List<? extends GrantedAuthority> authorityList = user.getAuthorities().stream().collect(Collectors.toList());
		return user.getMyProfile(authorityList.get(0).toString());
	}

	/**
	 * Update profile
	 */
	@Override
	@Transactional
	public MyProfileResponseDTO updateProfile(MyProfileRequestDTO profileDTO) {

		UserEntity user = getUserInfo();
		user.setName(profileDTO.getName());
		user.setMobileNo(profileDTO.getMobileNo());
		user = userRepository.save(user);

		if (profileDTO.getAddress() != null) {
			UserProfileEntity userProfile = user.getUserProfile();
			userProfile.setPermanentAddress(profileDTO.getAddress());
			userProfile = userProfileRepository.save(userProfile);
			user.setUserProfile(userProfile);
			user = userRepository.save(user);

		}
		List<? extends GrantedAuthority> authorityList = user.getAuthorities().stream().collect(Collectors.toList());
		return user.getMyProfile(authorityList.get(0).toString());
	}

	@Override
	@Transactional
	public MyProfileResponseDTO updateProfileLogo(MultipartFile file) {

		FileEntity fileM = null;
		try {
			UserEntity userE = getUserInfo();
			UserProfileEntity profileE = userE.getUserProfile();
			UserEntity user = securedUserService.getCurrentUserInfo();
			if (user == null) {
				throw new Exception("User not found");
			}
			FileMultipartDTO fileMDTO = FileMultipartDTO.builder().file(file)
					.parentFolderNickname(profileFolderNickName).build();
			fileM = fileService.saveFileWithMultipartAndParentNickNameOrIdAndEntity(fileMDTO);
			// check if user has got a logo already, if so delete old logo
			if (profileE.getProfilePic() != null) {
				Boolean oldFileDeleted = deleteOldFile(profileE.getProfilePic().getFileId());
				log.info("Deleted old logo", oldFileDeleted);
			}

			profileE.setProfilePic(fileM);

			profileE = userProfileRepository.save(profileE);
			return user.getMyProfile(user.getAuthority().getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public Resource getProfileLogo() {
		try {
			MyProfileResponseDTO profileE = getProfileInfo();
			if (profileE.getFileId() != null) {
				return fileService.getFile(profileE.getFileId());
			}
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private UserEntity getUserInfo() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getPrincipal().toString();

		return userRepository.findByUsernameIgnoreCase(username)
				.orElseThrow(() -> new EntityNotFoundException("MY_PROFILE"));
	}

	private Boolean deleteOldFile(String oldFileId) {
		try {
			FileDTO oldLogoToDelete = fileService.getFileDBInfoByID(oldFileId);
			return fileService.deleteFileById(oldLogoToDelete.getFileId());
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
