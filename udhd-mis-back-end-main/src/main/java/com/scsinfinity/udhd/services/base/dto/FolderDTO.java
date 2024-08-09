package com.scsinfinity.udhd.services.base.dto;

import javax.validation.constraints.NotBlank;

import com.scsinfinity.udhd.dao.entities.storage.FolderEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FolderDTO {

	@NotBlank
	private String name;


	@NotBlank
	private String folderId;

	private String folderNickName;


	
	public FolderEntity toEntity() {
		return FolderEntity.builder().folderId(folderId).folderNickName(folderNickName).name(name)
				.build();
	}

}
