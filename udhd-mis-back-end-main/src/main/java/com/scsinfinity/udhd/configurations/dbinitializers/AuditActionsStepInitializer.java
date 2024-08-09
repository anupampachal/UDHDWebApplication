package com.scsinfinity.udhd.configurations.dbinitializers;

import java.util.ArrayList;
import java.util.Arrays;

import javax.transaction.Transactional;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.scsinfinity.udhd.dao.entities.audit.AuditActionStepEnumLineItemEntity;
import com.scsinfinity.udhd.dao.entities.audit.AuditActionTypeEnum;
import com.scsinfinity.udhd.dao.entities.audit.AuditActionsStepEnumEntity;
import com.scsinfinity.udhd.dao.entities.audit.AuditStepEnum;
import com.scsinfinity.udhd.dao.repositories.audit.IAuditActionStepEnumLineItemRepository;
import com.scsinfinity.udhd.dao.repositories.audit.IAuditActionStepEnumRepository;

@Order(value = 9)
@Component
@Transactional
public class AuditActionsStepInitializer {
	private final IAuditActionStepEnumRepository auditActionStepsRepository;
	private final IAuditActionStepEnumLineItemRepository auditActionStepLineItemRepository;

	public AuditActionsStepInitializer(IAuditActionStepEnumRepository auditActionStepsRepository,
			IAuditActionStepEnumLineItemRepository auditActionStepLineItemRepository) {
		this.auditActionStepsRepository = auditActionStepsRepository;
		this.auditActionStepLineItemRepository = auditActionStepLineItemRepository;
	}

	public void initializer() {
		AuditActionStepEnumLineItemEntity submit = auditActionStepLineItemRepository
				.save(AuditActionStepEnumLineItemEntity.builder().actionTypeEnum(AuditActionTypeEnum.SUBMIT).build());

		AuditActionStepEnumLineItemEntity approve = auditActionStepLineItemRepository
				.save(AuditActionStepEnumLineItemEntity.builder().actionTypeEnum(AuditActionTypeEnum.APPROVE).build());

		AuditActionStepEnumLineItemEntity reject = auditActionStepLineItemRepository
				.save(AuditActionStepEnumLineItemEntity.builder().actionTypeEnum(AuditActionTypeEnum.REJECT).build());

		AuditActionStepEnumLineItemEntity reassign = auditActionStepLineItemRepository
				.save(AuditActionStepEnumLineItemEntity.builder().actionTypeEnum(AuditActionTypeEnum.REASSIGN).build());

		AuditActionStepEnumLineItemEntity publish = auditActionStepLineItemRepository
				.save(AuditActionStepEnumLineItemEntity.builder().actionTypeEnum(AuditActionTypeEnum.PUBLISH).build());

		ArrayList<AuditActionStepEnumLineItemEntity> agUlbAccDraft = new ArrayList<>(Arrays.asList(submit));

		ArrayList<AuditActionStepEnumLineItemEntity> agIaReview = new ArrayList<>(Arrays.asList(approve, reject));

		ArrayList<AuditActionStepEnumLineItemEntity> agUlbCmoReview = new ArrayList<>(Arrays.asList(approve, reject));

		ArrayList<AuditActionStepEnumLineItemEntity> agSlpmuReview = new ArrayList<>(
				Arrays.asList(approve, reject, reassign));

		ArrayList<AuditActionStepEnumLineItemEntity> agUdhdReview = new ArrayList<>(
				Arrays.asList(publish, reject, reassign));

		auditActionStepsRepository.save(AuditActionsStepEnumEntity.builder()
				.auditActionStepEnumLineItems(agUlbAccDraft).auditStepEnum(AuditStepEnum.AG_ULB_ACC_DRAFT).build());
		auditActionStepsRepository.save(AuditActionsStepEnumEntity.builder()
				.auditActionStepEnumLineItems(agIaReview).auditStepEnum(AuditStepEnum.AG_IA_REVIEW).build());
		auditActionStepsRepository.save(AuditActionsStepEnumEntity.builder()
				.auditActionStepEnumLineItems(agUlbCmoReview).auditStepEnum(AuditStepEnum.AG_ULB_CMO_REVIEW).build());
		auditActionStepsRepository.save(AuditActionsStepEnumEntity.builder()
				.auditActionStepEnumLineItems(agSlpmuReview).auditStepEnum(AuditStepEnum.AG_SLPMU_REVIEW).build());
		auditActionStepsRepository.save(AuditActionsStepEnumEntity.builder()
				.auditActionStepEnumLineItems(agUdhdReview).auditStepEnum(AuditStepEnum.AG_UDHD_REVIEW).build());/**/

	}
}
