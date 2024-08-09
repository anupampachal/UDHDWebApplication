package com.scsinfinity.udhd.dao.entities.storage;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;
import com.scsinfinity.udhd.services.base.dto.FolderDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "FOLDER")
//@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({ "folderNickName" })
public class FolderEntity extends BaseIdEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2734193062221435934L;

	private String name;

	private String pathToCurrentDir;

	@NotBlank
	@Column(unique = true)
	private String folderId;

	@Column(unique = true)
	@JsonProperty("folderNickName")
	private String folderNickName;


	@ManyToOne
	private FolderEntity parent;

	@JsonIgnore
	@OneToMany(orphanRemoval = true, mappedBy = "parentFolder")
	private List<FileEntity> file;

	@OneToOne(mappedBy = "folder", cascade = CascadeType.ALL)
	private FolderPermissionEntity folderPermission;

	public FolderDTO getDTO() {
		return FolderDTO.builder().name(name).folderId(folderId).folderNickName(folderNickName).build();
	}

}
