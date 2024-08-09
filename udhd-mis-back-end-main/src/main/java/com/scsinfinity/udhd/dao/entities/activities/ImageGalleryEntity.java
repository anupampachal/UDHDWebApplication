package com.scsinfinity.udhd.dao.entities.activities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;
import com.scsinfinity.udhd.dao.entities.storage.FolderEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name = "IMAGE_GALLERY")
public class ImageGalleryEntity extends BaseIdEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4581179637654219125L;
	
	@NotBlank
	@Size(min = 3, max = 50)
	private String name;
	
	@OneToOne
	private FolderEntity folder;

	// if true, member and admin both can access. if false, only admin can access.
	@NotNull
	private Boolean accessibleToAll;
}
