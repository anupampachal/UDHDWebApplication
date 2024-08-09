package com.scsinfinity.udhd.services.audit.internalaudit;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAAuditObservationEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAPartCAuditObservationDTO;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAPartCAuditObservationEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.InternalAuditEntity;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIAAuditObservationRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIAPartCAuditObservationRepository;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAPartCAuditObservationEnum;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IIAAuditObservationPartCService;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IInternalAuditService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class IAAuditObservationPartCService implements IIAAuditObservationPartCService {
	private final IInternalAuditService internalAuditService;
	private final IIAAuditObservationRepository auditObservationRepository;
	private final IIAPartCAuditObservationRepository partCRepository;

	@Override
	public IAPartCAuditObservationDTO createUpdatePartC(IAPartCAuditObservationDTO partCDTO) {
		InternalAuditEntity internalAudit = internalAuditService.findInternalAuditEntityById(partCDTO.getIaId());
		IAAuditObservationEntity auditObservation = internalAudit.getAuditObservation();
		if (auditObservation == null) {
			auditObservation = auditObservationRepository
					.save(IAAuditObservationEntity.builder().internalAudit(internalAudit).build());
		}

		return createUpdateByType(internalAudit, auditObservation, partCDTO);
	}

	private IAPartCAuditObservationDTO createUpdateByType(InternalAuditEntity ia,
			IAAuditObservationEntity auditObservation, IAPartCAuditObservationDTO partCDTO) {
		IAPartCAuditObservationEntity partCDetails = auditObservation.getPartCDetails();
		if (partCDetails == null) {
			partCDetails = partCRepository.save(IAPartCAuditObservationEntity.builder().build());
		}
		auditObservation.setPartCDetails(partCDetails);
		switch (partCDTO.getPartCEnum()) {
		case AReportNonCompliance: {
			partCDetails.setAAuditorShouldReportNonCompliance(partCDTO.getComment());
			break;
		}
		case BOpenImplementationOfSAS: {
			partCDetails.setBAuditorShouldOpenImplementationOfSAS(partCDTO.getComment());
			break;
		}
		case CReportBiharMunicipalAccounting: {
			partCDetails.setCAuditorShouldReportBiharMunicipalAccounting(partCDTO.getComment());
			break;
		}
		case DReportOnComplianceOfFinancialGuidelines: {
			partCDetails.setDReportOnComplianceOfFinancialGuidelines(partCDTO.getComment());
			break;
		}
		case EReportAllMajorRevenueLoss: {
			partCDetails.setEAuditorShouldReportAllMajorRevenueLoss(partCDTO.getComment());
			break;
		}
		case FReportOnAdequacy: {
			partCDetails.setFAuditorShouldReportOnAdequacy(partCDTO.getComment());
			break;
		}
		case GReportOnProcurementThroughETendering: {
			partCDetails.setGAuditorShouldReportOnProcurementThroughETendering(partCDTO.getComment());
			break;
		}
		case HReportOnAvailabilityOfUC: {
			partCDetails.setHAuditorShouldReportOnAvailabilityOfUC(partCDTO.getComment());
			break;
		}
		case IReportOnInstanceOfLosses: {
			partCDetails.setIAuditorShouldReportOnInstanceOfLosses(partCDTO.getComment());
			break;
		}
		case JReportOnPaymentTerms: {
			partCDetails.setJAuditorShouldReportOnPaymentTerms(partCDTO.getComment());
			break;
		}
		case KReportOnEachPaymentTermsAndConditions: {
			partCDetails.setKAuditorShouldReportOnEachPaymentTermsAndConditions(partCDTO.getComment());
			break;
		}
		case LReportOnFixedDeposit: {
			partCDetails.setLAuditorShouldReportOnFixedDeposit(partCDTO.getComment());
			break;
		}
		case MReportOnMajorLossesOfULBs: {
			partCDetails.setMAuditorShouldReportOnMajorLossesOfULBs(partCDTO.getComment());
			break;
		}
		case NReportOnAllTypesOfTaxDeductions: {
			partCDetails.setNAuditorShouldReportOnAllTypesOfTaxDeductions(partCDTO.getComment());
			break;
		}
		case OInternalAuditorEnsuresAllCAGAndAGAudit: {
			partCDetails.setOInternalAuditorEnsuresAllCAGAndAGAudit(partCDTO.getComment());
			break;
		}

		}
		partCRepository.save(partCDetails);
		return IAPartCAuditObservationDTO.builder().comment(partCDTO.getComment()).iaId(ia.getId())
				.partCEnum(partCDTO.getPartCEnum()).build();
	}

	@Override
	public IAPartCAuditObservationDTO getPartCString(Long iaID, IAPartCAuditObservationEnum partCEnum) {
		InternalAuditEntity internalAudit = internalAuditService.findInternalAuditEntityById(iaID);
		IAAuditObservationEntity auditObservation = internalAudit.getAuditObservation();
		if (auditObservation == null) {
			return null;
		}
		IAPartCAuditObservationEntity partCDetails = auditObservation.getPartCDetails();
		if (partCDetails == null)
			return null;
		return fetchByType(internalAudit, partCDetails, partCEnum);
	}

	private IAPartCAuditObservationDTO fetchByType(InternalAuditEntity ia, IAPartCAuditObservationEntity partCDetails,
			IAPartCAuditObservationEnum partCEnum) {
		String text = null;
		switch (partCEnum) {
		case AReportNonCompliance: {
			text = partCDetails.getAAuditorShouldReportNonCompliance();
			break;
		}
		case BOpenImplementationOfSAS: {
			text = partCDetails.getBAuditorShouldOpenImplementationOfSAS();
			break;
		}
		case CReportBiharMunicipalAccounting: {
			text = partCDetails.getCAuditorShouldReportBiharMunicipalAccounting();
			break;
		}
		case DReportOnComplianceOfFinancialGuidelines: {
			text = partCDetails.getDReportOnComplianceOfFinancialGuidelines();
			break;
		}
		case EReportAllMajorRevenueLoss: {
			text = partCDetails.getEAuditorShouldReportAllMajorRevenueLoss();
			break;
		}
		case FReportOnAdequacy: {
			text = partCDetails.getFAuditorShouldReportOnAdequacy();
			break;
		}
		case GReportOnProcurementThroughETendering: {
			text = partCDetails.getGAuditorShouldReportOnProcurementThroughETendering();
			break;
		}
		case HReportOnAvailabilityOfUC: {
			text = partCDetails.getHAuditorShouldReportOnAvailabilityOfUC();
			break;
		}
		case IReportOnInstanceOfLosses: {
			text = partCDetails.getIAuditorShouldReportOnInstanceOfLosses();
			break;
		}
		case JReportOnPaymentTerms: {
			text = partCDetails.getJAuditorShouldReportOnPaymentTerms();
			break;
		}
		case KReportOnEachPaymentTermsAndConditions: {
			text = partCDetails.getKAuditorShouldReportOnEachPaymentTermsAndConditions();
			break;
		}
		case LReportOnFixedDeposit: {
			text = partCDetails.getLAuditorShouldReportOnFixedDeposit();
			break;
		}
		case MReportOnMajorLossesOfULBs: {
			text = partCDetails.getMAuditorShouldReportOnMajorLossesOfULBs();
			break;
		}
		case NReportOnAllTypesOfTaxDeductions: {
			text = partCDetails.getNAuditorShouldReportOnAllTypesOfTaxDeductions();
			break;
		}
		case OInternalAuditorEnsuresAllCAGAndAGAudit: {
			text = partCDetails.getOInternalAuditorEnsuresAllCAGAndAGAudit();
			break;
		}

		}
		return IAPartCAuditObservationDTO.builder().comment(text).iaId(ia.getId()).partCEnum(partCEnum).build();
	}
}
