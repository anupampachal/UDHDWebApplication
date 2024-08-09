package com.scsinfinity.udhd.dao.entities.storage;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;
import com.scsinfinity.udhd.services.base.dto.FileDTO;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "FILE")
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(value = { "file" }, ignoreUnknown = false)
public class FileEntity extends BaseIdEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4103062028753499821L;

	@NotEmpty
	private String name;

	@NotEmpty
	private String fileServerName;// file real name as stored on server

	@NotEmpty
	private String fileId;
	
	@NotEmpty
	private String extension;

	@javax.persistence.Transient
	private MultipartFile file;

	@ManyToOne(fetch = FetchType.LAZY)
	private FolderEntity parentFolder;

	@JsonIgnore
	public FileDTO getDTO() {
		return FileDTO.builder().file(file).fileId(fileId).name(name).parentFolderId(parentFolder.getFolderId())
				.build();
	}

}
