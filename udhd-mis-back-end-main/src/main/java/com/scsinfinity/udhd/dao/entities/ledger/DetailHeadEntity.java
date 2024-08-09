/**
 * 
 */
package com.scsinfinity.udhd.dao.entities.ledger;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author aditya-server
 *
 * 29-Aug-2021  --  12:55:12 am
 */
@Entity
@Getter
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name = "LEDGER_DETAIL_HEAD")
public class DetailHeadEntity extends BaseIdEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 44092725333238515L;

	@NotNull
	private String code;

	@NotBlank
	@Size(min = 3, max = 100)
	private String name;

	@Size(min = 3, max = 100)
	private String alias;
	
	@NotNull
	@ManyToOne
	private MinorHeadEntity minorHead;
}
