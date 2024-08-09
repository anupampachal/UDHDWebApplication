package com.scsinfinity.udhd.services.settings;

import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.scsinfinity.udhd.services.common.Pagination;
import com.scsinfinity.udhd.services.common.SortDirection;
import com.scsinfinity.udhd.services.settings.interfaces.IPaginationInfoService;

@Service
public class PaginationInfoService<T, Y> implements IPaginationInfoService<T, Y> {

	@Override
	public Pageable getPaginationRequestInfo(Pagination<T> data) {
		Pageable pageable = PageRequest.of(data.getPageNo(), data.getPageSize());
		// sorting logic
		if (data.getSortedBy() != null && !data.getSortedBy().trim().equalsIgnoreCase("")) {
			pageable = PageRequest.of(data.getPageNo(), data.getPageSize(), Sort.by(data.getSortedBy()).ascending());
			if (data.getDirectionOfSort() != null
					&& data.getDirectionOfSort().equalsIgnoreCase(SortDirection.DESCENDING.toString())) {
				pageable = PageRequest.of(data.getPageNo(), data.getPageSize(),
						Sort.by(data.getSortedBy()).descending());
			}
		} else {
			pageable = PageRequest.of(data.getPageNo(), data.getPageSize());
		}
		return pageable;
	}

	@Override
	public Page<T> getDataAsDTO(Page<Y> y, Function<Y, T> convertFunc) {

		Function<Page<Y>, Page<T>> convertEntityToDTOPage = entityPage -> new PageImpl<T>(
				entityPage.get().map(data -> convertFunc.apply(data)).collect(Collectors.toList()),
				entityPage.getPageable(), entityPage.getTotalElements());

		return convertEntityToDTOPage.apply(y);
	}

	@Override
	public PageImpl<T> getDataAsDTOPageImpl(Page<Y> y, Function<Y, T> convertFunc) {
		Function<Page<Y>, PageImpl<T>> convertEntityToDTOPage = entityPage -> new PageImpl<T>(
				entityPage.get().map(data -> convertFunc.apply(data)).collect(Collectors.toList()),
				entityPage.getPageable(), entityPage.getTotalElements());
		return convertEntityToDTOPage.apply(y);
	}
}
