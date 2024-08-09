/**
 * 
 */
package com.scsinfinity.udhd.dao.entities.ledger;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author aditya-server
 *
 *         29-Aug-2021 -- 12:31:46 am
 */

@Entity
@Getter
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name = "LEDGER_CHART_OF_ACCOUNTS")
public class ChartOfAccountsEntity extends BaseIdEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8178795218832541324L;

	@NotBlank
	private String name;

	@NotBlank
	@Column(unique = true)
	private String code;

}
