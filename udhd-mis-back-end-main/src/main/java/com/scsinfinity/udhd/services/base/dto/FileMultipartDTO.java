package com.scsinfinity.udhd.services.base.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FileMultipartDTO {

	private String parentFolderNickname;
	private String parentFolderId;
	private MultipartFile file;
}
