/**
 * 
 */
package com.scsinfinity.udhd.dao.entities.ledger;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
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
 *         29-Aug-2021 -- 12:27:59 am
 */

@Entity
@Getter
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name = "LEDGER_BASE_HEAD")
public class BaseHeadEntity extends BaseIdEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2366163805041334517L;

	@NotNull
	private Integer code;

	@NotBlank
	@Size(min = 3, max = 100)
	@Column(unique = true)
	private String name;

	@Size(min = 3, max = 50)
	@Column(unique = true)
	private String alias;


}
