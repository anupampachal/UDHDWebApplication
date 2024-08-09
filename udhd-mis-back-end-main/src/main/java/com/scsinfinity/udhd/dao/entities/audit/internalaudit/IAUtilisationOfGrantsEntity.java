package com.scsinfinity.udhd.dao.entities.audit.internalaudit;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;
import com.scsinfinity.udhd.dao.entities.storage.FileEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "IA_UTILISATION_OF_GRANTS")
public class IAUtilisationOfGrantsEntity extends BaseIdEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 2568575352634776045L;

	//@OneToOne(mappedBy="hUtilisationOfGrants")
	//private IAAuditObservationPartBEntity auditObservationPartB;
	
	@OneToOne
	private FileEntity file;

	@OneToMany
	private List<IAUtilisationOfGrantsLineItemEntity> utilisationLineItemList;
	
	
}
