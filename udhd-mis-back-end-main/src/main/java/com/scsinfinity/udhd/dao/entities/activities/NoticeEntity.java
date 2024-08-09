package com.scsinfinity.udhd.dao.entities.activities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;
import com.scsinfinity.udhd.dao.entities.storage.FileEntity;

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
@Table(name = "NOTICE")
public class NoticeEntity  extends BaseIdEntity implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -7617528556414671935L;


	@NotBlank
	@Column(name="name")
	private String header;
	
	
	@Column(name="description")
	@Size(min=2, max=1000)
	private String description;
	
	
	@NotNull
	private LocalDateTime publishDate;
	
	@NotNull
	private LocalDateTime expiryDate;
	
	@NotNull
	private NoticeTypeEnum noticeType;
	
	@OneToOne
	private FileEntity supportingImage;
}
