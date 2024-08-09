/**
 * 
 */
package com.scsinfinity.udhd.dao.entities.ledger;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author aditya-server
 *
 * 29-Aug-2021  --  12:37:38 am
 */

@Entity
@Getter
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name = "LEDGER_MAJOR_HEAD")
public class MajorHeadEntity extends BaseIdEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5653699959127138266L;

	@NotNull
	private String code;

	@NotBlank
	@Size(min = 3, max = 100)
	@Column(unique = true)
	private String name;

	@Size(min = 3, max = 200)
	@Column(unique = true)
	private String alias;
	
	@NotNull
	@ManyToOne
	private BaseHeadEntity baseHead;
	
	private BigDecimal balance;

}
