package com.scsinfinity.udhd.dao.entities.base;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.scsinfinity.udhd.dao.entities.storage.FileEntity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name = "USER_PROFILE")
public class UserProfileEntity extends BaseIdEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3309581792126044016L;

	/*@JsonBackReference//(value="user-and-profile")
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	//@JoinColumn(name = "user_id", referencedColumnName = "id")
	private UserEntity user;*/

	@Size(min = 2, max = 200)
	private String permanentAddress;

	@OneToOne
	private FileEntity profilePic;

	
	@OneToOne(cascade = CascadeType.PERSIST)
	private UserULBDataEntity userUlbInfo;
	//@ManyToMany( fetch = FetchType.LAZY)
	//@JsonManagedReference(value="user-profile-and-ulb")
	//private List<ULBEntity> ulbs;

}
