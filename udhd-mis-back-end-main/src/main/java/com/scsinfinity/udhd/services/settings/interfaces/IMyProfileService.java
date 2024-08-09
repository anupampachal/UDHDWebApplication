package com.scsinfinity.udhd.services.settings.interfaces;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.scsinfinity.udhd.services.settings.dto.MyProfileRequestDTO;
import com.scsinfinity.udhd.services.settings.dto.MyProfileResponseDTO;

public interface IMyProfileService {

	MyProfileResponseDTO getProfileInfo();

	MyProfileResponseDTO updateProfile(MyProfileRequestDTO libraryDTO);

	MyProfileResponseDTO updateProfileLogo(MultipartFile file);

	Resource getProfileLogo();
}
