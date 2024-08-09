package com.scsinfinity.udhd.web.settings;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.scsinfinity.udhd.services.settings.dto.MyProfileRequestDTO;
import com.scsinfinity.udhd.services.settings.dto.MyProfileResponseDTO;
import com.scsinfinity.udhd.services.settings.interfaces.IMyProfileService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/settings/profile")
public class MyProfileResource {

	private final IMyProfileService profileService;
	private final HttpServletRequest request;

	@GetMapping
	public ResponseEntity<MyProfileResponseDTO> getProfileInfo() {
		return ResponseEntity.ok(profileService.getProfileInfo());
	}

	@PutMapping
	public ResponseEntity<MyProfileResponseDTO> updateProfileProfile(@RequestBody MyProfileRequestDTO profileDTO) {
		return ResponseEntity.ok(profileService.updateProfile(profileDTO));
	}

	@PatchMapping
	public ResponseEntity<MyProfileResponseDTO> updateProfileLogo(@Valid @RequestPart MultipartFile file) throws URISyntaxException {
		return ResponseEntity.ok(profileService.updateProfileLogo(file));

	}

	@GetMapping("/logo")
	public ResponseEntity<Resource> getFile() {
		// return ResponseEntity.ok(profileService.getProfileLogo());
		Resource file = profileService.getProfileLogo();
		String contentType = null;
		if (file == null) {
			return ResponseEntity.ok(null);
		}
		try {
			contentType = request.getServletContext().getMimeType(file.getFile().getAbsolutePath());
		} catch (IOException ex) {
			log.info("Could not determine file type.");
		}

		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getFilename() + ";")

				.body(file);

	}
}