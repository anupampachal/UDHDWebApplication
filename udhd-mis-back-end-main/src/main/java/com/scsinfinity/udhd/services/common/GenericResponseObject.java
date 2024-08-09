package com.scsinfinity.udhd.services.common;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponseObject<T> {
	private Long totalRecords;
	private Page<T> currentRecords;
	private int currentPage;
	private int itemsPerPage;
}
