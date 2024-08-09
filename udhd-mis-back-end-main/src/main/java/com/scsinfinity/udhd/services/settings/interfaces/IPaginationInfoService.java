package com.scsinfinity.udhd.services.settings.interfaces;

import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.scsinfinity.udhd.services.common.Pagination;

public interface IPaginationInfoService<T, Y> {
	Pageable getPaginationRequestInfo(Pagination<T> data);

	Page<T> getDataAsDTO(Page<Y> y, Function<Y, T> convertFunc);
	
	PageImpl<T> getDataAsDTOPageImpl(Page<Y> y, Function<Y, T> convertFunc);
}
