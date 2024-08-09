package com.scsinfinity.udhd.dao.repositories.deas;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scsinfinity.udhd.dao.entities.deas.PropertyTaxRegisterInfoEntity;
import com.scsinfinity.udhd.dao.entities.geography.ULBEntity;

@Repository
public interface IPropertyTaxRegisterRepository extends JpaRepository<PropertyTaxRegisterInfoEntity, Long> {
	@Query(value = "select * from DEAS_PROPERTY_TAX_REGISTER h where h.YEAR_END_DATE>=?1 and h.YEAR_START_DATE<=?2 and h.ulb_id=?3 and status=?4", nativeQuery = true)
	List<PropertyTaxRegisterInfoEntity> findByGivenCriteria(LocalDate startDate, LocalDate endDateEq, Long ulbId,
			int status);

	Page<PropertyTaxRegisterInfoEntity> findAllByUlb_Id(Long UlbId, Pageable page);

	Page<PropertyTaxRegisterInfoEntity> findAllByUlbIn(List<ULBEntity> ulbs, Pageable page);

	Optional<PropertyTaxRegisterInfoEntity> findByFile_FileId(String fileId);
}
