package com.scsinfinity.udhd.services.common;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Builder
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Pagination<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8762779258869561431L;
	private int pageNo;
	private int pageSize;
	private int total;
	private String queryString;
	private String sortedBy;
	private String directionOfSort;
	private Long code;
	private Long id;
	private Date dateFrom;
	private Date dateTo;
	private String dateType;
	private String status;
	private String criteria;

	private T dateTypeToFilter;
}
