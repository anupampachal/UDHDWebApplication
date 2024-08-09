package com.scsinfinity.udhd.services.base.dto;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FileDTO {

	private Long id;
	@NotEmpty
	private String name;

	@NotEmpty
	private String fileServerName;// file real name as stored on server

	@NotEmpty
	private String extension;

	@NotEmpty
	private String fileId;

	@JsonIgnore
	private MultipartFile file;

	private String parentFolderId;

}
