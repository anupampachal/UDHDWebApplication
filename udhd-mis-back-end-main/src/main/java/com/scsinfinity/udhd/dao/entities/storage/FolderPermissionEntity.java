package com.scsinfinity.udhd.dao.entities.storage;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "FOLDER_PERMISSION")
@EqualsAndHashCode(callSuper = true)
public class FolderPermissionEntity extends BaseIdEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 2147478196954778797L;

	@OneToOne
	private FolderEntity folder;

	@OneToMany(cascade = CascadeType.ALL)
	private List<FolderUserGroupPermissionEntity> folderUserGroupPermissions;
}
