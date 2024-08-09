package com.scsinfinity.udhd.dao.entities.storage;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;

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
@Table(name = "FOLDER_USER_GROUP_PERMISSION")
@EqualsAndHashCode(callSuper = true)
public class FolderUserGroupPermissionEntity extends BaseIdEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 6765370356268424443L;

	@Enumerated(EnumType.STRING)
	private FolderUserGroupsEnum folderUserGroup;


	@Column(name = "permissions", nullable = true, insertable = true, updatable = true)
	@ElementCollection
	@Enumerated(EnumType.STRING)
	private List<FileFolderPermissionTypeEnum> permissions;
}
