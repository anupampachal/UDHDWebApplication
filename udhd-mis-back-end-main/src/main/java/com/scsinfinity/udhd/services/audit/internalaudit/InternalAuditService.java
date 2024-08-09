package com.scsinfinity.udhd.services.audit.internalaudit;

import java.time.LocalDate;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import com.scsinfinity.udhd.dao.entities.audit.*;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.*;
import com.scsinfinity.udhd.dao.entities.base.AuthorityTypeEnum;
import com.scsinfinity.udhd.dao.repositories.audit.IAuditRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.*;
import com.scsinfinity.udhd.services.audit.dto.IAAuditCommentDTO;
import com.scsinfinity.udhd.services.common.BooleanResponseDTO;
import com.scsinfinity.udhd.services.settings.dto.ULBInfoForULBUsersDTO;
import com.scsinfinity.udhd.services.settings.interfaces.IUserMgtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.scsinfinity.udhd.configurations.security.SecuredUserInfoService;
import com.scsinfinity.udhd.dao.entities.base.UserEntity;
import com.scsinfinity.udhd.dao.entities.base.UserProfileEntity;
import com.scsinfinity.udhd.dao.entities.base.UserULBDataEntity;
import com.scsinfinity.udhd.dao.entities.geography.ULBEntity;
import com.scsinfinity.udhd.exceptions.BadRequestAlertException;
import com.scsinfinity.udhd.services.audit.dto.AuditCreationDto;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAStep1RequestDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAStep1ResponseDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IAuditService;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IInternalAuditService;
import com.scsinfinity.udhd.services.common.AuthorityConstants;
import com.scsinfinity.udhd.services.common.GenericResponseObject;
import com.scsinfinity.udhd.services.common.Pagination;
import com.scsinfinity.udhd.services.settings.dto.ULBDTO;
import com.scsinfinity.udhd.services.settings.interfaces.IPaginationInfoService;
import com.scsinfinity.udhd.services.settings.interfaces.IULBService;

import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class InternalAuditService implements IInternalAuditService {

	private final IInternalAuditRepository iaRepo;
	private final IAuditService auditService;
	private final IAuditRepository auditRepository;
	private final IULBService ulbService;
	private final IUserMgtService userMgtService;
	private final IPaginationInfoService<IAStep1ResponseDTO, InternalAuditEntity> paginationInfoService;
	private final SecuredUserInfoService securedUser;
	private final IIAExecutiveSummaryRepository executiveSummaryRepository;
	private final IIAResultsAndFindingsStrengthRepository strengthRepository;
	private final IIAResultsAndFindingsWeaknessRepository weaknessRepository;
	private final IIAAuditRecommendationsRepository recommendationsRepository;
	private final IIAAuditTeamTeamLeadersRepository teamTeamLeadersRepository;
	private final IIAAuditTeamMunicipalAuditExpertsRepository municipalAuditExpertsRepository;
	private final IIAAuditTeamMunicipalAuditAssistantsRepository auditAssistantsRepository;
	private final IIAAdministrationRepository administrationRepository;
	private final IIADetailAuditRepository detailAuditRepository;
	private final IIAStatusOfAuditObservationRepository statusOfAuditObservationRepository;
	private final IIADetailAuditParaInfoRepository iiaDetailAuditParaInfoRepository;
	private final IIAFinanceRepository financeRepository;
	private final IIABudgetaryProvisionsAndExpenditureRepository budgetaryProvisionsAndExpenditureRepository;
	private final IIAVolumeOfTransactionsRepository volumeOfTransactionsRepository;
	private final IIABankReconciliationRepository bankReconciliationRepository;
	private final IIARevenueNCapitalReceiptsRepository revenueNCapitalReceiptsRepository;
	private final IIARevenueNCapitalExpenditureRepository revenueNCapitalExpenditureRepository;
	private final IIAStatusOfImplementationOfDeasRepository deasImplementationStatusRepository;
	private final IIAAuditObservationRepository auditObservationRepository;
	private final IPaginationInfoService<IAStep1ResponseDTO, InternalAuditEntity> paginationInfoServiceForIAAudit;

	private final IIANonLevyOfPropertyHoldingTaxRepository propertyHoldingTaxRepository;
	private final IIADelayDepositTaxCollectedRepository delayDepositTaxCollectedRepository;
	private final IIAMobileTowerTaxRepository mobileTowerTaxRepository;
	private final IIARentOnMunicipalPropertiesRepository rentOnMunicipalPropertiesRepository;
	private final IIAAdvertisementTaxRepository advertisementTaxRepository;
	private final IIAExcessPaymentAgainstBillRepository excessPaymentAgainstBillRepository;
	private final IIAReportOnFindingsOfFieldRepository reportOnFindingsOfFieldRepository;
	private final IIACustomAuditObservationRepository  customAuditObservationRepository;
	private final IIAAuditObservationPartBRepository partBRepository;
	private final IIAPartCAuditObservationRepository partCDetailsRepository;
	private final IIAAuditCommentRepository iiaAuditCommentRepository;

	@Override
	@Transactional
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_INTERNAL_AUDITOR + "')")
	public IAStep1ResponseDTO createIAWithStep1(IAStep1RequestDTO step1RequestDTO) {
		UserEntity user = securedUser.getCurrentUserInfo();
		ULBEntity ulb = ulbService.findULBEntityById(step1RequestDTO.getUlbId());
		if (!isUserAllowedToCreateUpdateDeleteIA(user, ulb)) {
			throw new BadRequestAlertException("UNAUTHORISED_ACCESS", "UNAUTHORISED_ACCESS", "UNAUTHORISED_ACCESS");
		}
		AuditEntity auditEn = auditService
				.createUpdateAuditEntity(fnStep1ToAuditCreation.apply(step1RequestDTO, AuditStatusEnum.DRAFT));
		InternalAuditEntity iaE = iaRepo
				.save(InternalAuditEntity.builder().id(step1RequestDTO.getId()).auditEntity(auditEn).build());
		return fnAuditDTOToStep1Response.apply(auditEn, iaE);
	}

	@Override
	@Transactional
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_INTERNAL_AUDITOR + "')")
	public BooleanResponseDTO deleteIA(Long id) {
		UserEntity user = securedUser.getCurrentUserInfo();
		InternalAuditEntity ia = iaRepo.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Internal Audit:" + id));
		AuditEntity auditEntity = ia.getAuditEntity();
		ULBEntity ulb =auditEntity.getUlb();

		if (!auditEntity.getAuditStatus().equals(AuditStatusEnum.DRAFT)) {
			return new BooleanResponseDTO(false, "IA should only be in DRAFT status to delete.");
		}
		if (!isUserAllowedToCreateUpdateDeleteIA(user, ulb)) {
			return new BooleanResponseDTO(false, "You are not authorised to delete this Internal Audit");
		}

		IAExecutiveSummaryEntity executiveSummary= ia.getExecutiveSummary();
		if(executiveSummary!=null){
			List<IAResultsAndFindingsStrengthEntity> strengths= executiveSummary.getStrengths();
			List<IAResultsAndFindingsWeaknessEntity> weaknesses= executiveSummary.getWeaknesses();
			List<IAAuditRecommendationsEntity> recommendations= executiveSummary.getRecommendations();
			if(strengths!=null)
				strengthRepository.deleteAll(strengths);
			if(weaknesses!=null)
				weaknessRepository.deleteAll(weaknesses);
			if(recommendations!=null)
				recommendationsRepository.deleteAll(recommendations);

			executiveSummaryRepository.delete(executiveSummary);
		}
		IAAdministrationEntity administration= ia.getAdministration();
		if(administration!=null){
			List<IAAuditTeamTeamLeadersEntity> teamLeaders=administration.getTeamLeaders();
			List<IAAuditTeamMunicipalAuditExpertsEntity> municipalExperts=administration.getMunicipalExperts();
			List<IAAuditTeamMunicipalAuditAssistantsEntity> municipalAssistants=administration.getMunicipalAssistants();
			if(teamLeaders!=null)
				teamTeamLeadersRepository.deleteAll(teamLeaders);
			if(municipalExperts!=null)
				municipalAuditExpertsRepository.deleteAll(municipalExperts);
			if(municipalAssistants!=null)
				auditAssistantsRepository.deleteAll(municipalAssistants);

			administrationRepository.delete(administration);
		}

		IADetailAuditEntity detailAuditEntity=ia.getDetails();
		if(detailAuditEntity!=null){
			List<IAStatusOfAuditObservationEntity> auditStatuses=detailAuditEntity.getAuditStatuses();
			List<IADetailAuditParaInfoEntity> auditParaInfos= detailAuditEntity.getAuditParaInfos();
			if(auditStatuses!=null)
				statusOfAuditObservationRepository.deleteAll(auditStatuses);
			if(auditParaInfos!=null)
				iiaDetailAuditParaInfoRepository.deleteAll(auditParaInfos);
			detailAuditRepository.delete(detailAuditEntity);
		}

		IAFinanceEntity finance= ia.getFinance();
		if(finance!=null){
						IABudgetaryProvisionsAndExpenditureEntity budgetaryProvisions= finance.getBudgetaryProvisions();
			IAVolumeOfTransactionsEntity volumeOfTransactions=finance.getVolumeOfTransactions();
			IABankReconciliationEntity bankReconciliation=finance.getBankReconciliation();
			IARevenueNCapitalReceiptsEntity revenueNCapitalReceipts=finance.getRevenueNCapitalReceipts();
			IARevenueNCapitalExpenditureEntity revenueNCapitalExpenditure=finance.getRevenueNCapitalExpenditure();
			IAStatusOfImplementationOfDeasEntity deasImplementationStatus=finance.getDeasImplementationStatus();

			if(budgetaryProvisions!=null)
				budgetaryProvisionsAndExpenditureRepository.delete(budgetaryProvisions);
			if(volumeOfTransactions!=null)
				volumeOfTransactionsRepository.delete(volumeOfTransactions);
			if(bankReconciliation!=null)
				bankReconciliationRepository.delete(bankReconciliation);
			if(revenueNCapitalReceipts!=null)
				revenueNCapitalReceiptsRepository.delete(revenueNCapitalReceipts);
			if(revenueNCapitalExpenditure!=null)
				revenueNCapitalExpenditureRepository.delete(revenueNCapitalExpenditure);
			if(deasImplementationStatus!=null)
				deasImplementationStatusRepository.delete(deasImplementationStatus);
			financeRepository.delete(finance);
		}

		IAAuditObservationEntity auditObservation=ia.getAuditObservation();
		if(auditObservation!=null){

			IANonLevyOfPropertyHoldingTaxEntity propertyHoldingTax =auditObservation.getPropertyHoldingTax();
			if(propertyHoldingTax!=null)
				propertyHoldingTaxRepository.delete(propertyHoldingTax);

			IADelayDepositTaxCollectedEntity delayDepositTaxCollected=auditObservation.getDelayDepositTaxCollected();
			if(delayDepositTaxCollected!=null)
				delayDepositTaxCollectedRepository.delete(delayDepositTaxCollected);

			IAMobileTowerTaxEntity mobileTowerTax=auditObservation.getMobileTowerTax();
			if(mobileTowerTax!=null)
				mobileTowerTaxRepository.delete(mobileTowerTax);

			IARentOnMunicipalPropertiesEntity rentOnMunicipalProperties=auditObservation.getRentOnMunicipalProperties();
			if(rentOnMunicipalProperties!=null)
				rentOnMunicipalPropertiesRepository.delete(rentOnMunicipalProperties);

			IAAdvertisementTaxEntity advertisementTax=auditObservation.getAdvertisementTax();
			if(advertisementTax!=null)
				advertisementTaxRepository.delete(advertisementTax);

			IAExcessPaymentAgainstBillEntity excessPaymentAgainstBill=auditObservation.getExcessPaymentAgainstBill();
			if(excessPaymentAgainstBill!=null)
				excessPaymentAgainstBillRepository.delete(excessPaymentAgainstBill);

			IAReportOnFindingsOfFieldEntity reportOnFindingsOfField=auditObservation.getReportOnFindingsOfField();
			if(reportOnFindingsOfField!=null)
				reportOnFindingsOfFieldRepository.delete(reportOnFindingsOfField);

			List<IACustomAuditObservationEntity> customAuditObservation= auditObservation.getCustomAuditObservation();
			if(customAuditObservation!=null && customAuditObservation.size()!=0)
				customAuditObservationRepository.deleteAll(customAuditObservation);

			IAAuditObservationPartBEntity partB=auditObservation.getPartB();
			if(partB!=null)
				partBRepository.delete(partB);


			IAPartCAuditObservationEntity partCDetails=auditObservation.getPartCDetails();
			if(partCDetails!=null)
				partCDetailsRepository.delete(partCDetails);

			auditObservationRepository.delete(auditObservation);
		}

		iaRepo.delete(ia);
		auditService.deleteAudit(auditEntity.getId());
		return new BooleanResponseDTO(true, "Internal Audit delete successful.");
	}

	@Override
	//@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_INTERNAL_AUDITOR + "')")
	public List<ULBDTO> getULBsAllowedForIA() {
		UserEntity user = securedUser.getCurrentUserInfo();
		if (user.getUserProfile() != null && user.getUserProfile().getUserUlbInfo() != null
				&& user.getUserProfile().getUserUlbInfo().getUlbs() != null)
			return user.getUserProfile().getUserUlbInfo().getUlbs().stream().map(ulb -> ulb.getDTO())
					.collect(Collectors.toList());
		return null;
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_SLPMU_IT + "','"
			+ AuthorityConstants.ROLE_SLPMU_UC + "','" + AuthorityConstants.ROLE_UDHD_IT + "','"
			+ AuthorityConstants.ROLE_UDHD_PSEC + "','" + AuthorityConstants.ROLE_UDHD_SEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")
	public IAStep1ResponseDTO findInternalAuditById(Long id) {
		//InternalAuditEntity ia = iaRepo.findById(id)
		//		.orElseThrow(() -> new EntityNotFoundException("Internal Audit Entity"));
		InternalAuditEntity ia = iaRepo.findByAuditEntity_Id(id).orElseThrow(() -> new EntityNotFoundException("Internal Audit Entity"));
		return fnAuditDTOToStep1Response.apply(ia.getAuditEntity(), ia);
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_SLPMU_IT + "','"
			+ AuthorityConstants.ROLE_SLPMU_UC + "','" + AuthorityConstants.ROLE_UDHD_IT + "','"
			+ AuthorityConstants.ROLE_UDHD_PSEC + "','" + AuthorityConstants.ROLE_UDHD_SEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")
	public InternalAuditEntity findInternalAuditEntityById(Long id) {
		return iaRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Internal Audit Entity"));
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_SLPMU_IT + "','"
			+ AuthorityConstants.ROLE_SLPMU_UC + "','" + AuthorityConstants.ROLE_UDHD_IT + "','"
			+ AuthorityConstants.ROLE_UDHD_PSEC + "','" + AuthorityConstants.ROLE_UDHD_SEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")
	public GenericResponseObject<IAStep1ResponseDTO> loadInternalAuditByPage(Pagination<IAStep1ResponseDTO> data) {
		UserEntity user= userMgtService.getCurrentUserInfoEntity();
		if (user.getAuthority().getAuthorityType()== AuthorityTypeEnum.OTHERS || user.getAuthority().getAuthorityType()==AuthorityTypeEnum.ULB)
			return loadInternalAuditByPageForULBRelatedUsers(user,data);
		else
			return loadInternalAuditByPageForNonULBRelatedUsers(data);

	}
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "')")
	private GenericResponseObject<IAStep1ResponseDTO> loadInternalAuditByPageForULBRelatedUsers(UserEntity user,Pagination<IAStep1ResponseDTO> data){
		Pageable pageable = paginationInfoService.getPaginationRequestInfo(data);

		if(user.getUserProfile()!=null && user.getUserProfile().getUserUlbInfo() !=null && user.getUserProfile().getUserUlbInfo().getUlbs()!=null){
			Page<InternalAuditEntity> internalAuditPage = iaRepo.findByAuditEntity_UlbIn(pageable,user.getUserProfile().getUserUlbInfo().getUlbs());

			Page<IAStep1ResponseDTO> iaResponsePage = paginationInfoService.getDataAsDTO(internalAuditPage,
					en -> fnIAEntityToResp.apply(en));

			return new GenericResponseObject<IAStep1ResponseDTO>(iaResponsePage.getTotalElements(), iaResponsePage,
					data.getPageNo(), data.getPageSize());
		}
		else {
			return null;
		}
	}
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_SLPMU_IT + "','"
			+ AuthorityConstants.ROLE_SLPMU_UC + "','" + AuthorityConstants.ROLE_UDHD_IT + "','"
			+ AuthorityConstants.ROLE_UDHD_PSEC + "','" + AuthorityConstants.ROLE_UDHD_SEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")
	private GenericResponseObject<IAStep1ResponseDTO> loadInternalAuditByPageForNonULBRelatedUsers(Pagination<IAStep1ResponseDTO> data){
		Pageable pageable = paginationInfoService.getPaginationRequestInfo(data);
		Page<InternalAuditEntity> internalAuditPage = iaRepo.findAll(pageable);

		Page<IAStep1ResponseDTO> iaResponsePage = paginationInfoService.getDataAsDTO(internalAuditPage,
				en -> fnIAEntityToResp.apply(en));

		return new GenericResponseObject<IAStep1ResponseDTO>(iaResponsePage.getTotalElements(), iaResponsePage,
				data.getPageNo(), data.getPageSize());
	}

	@Override
	public Boolean isUserAllowedToCreateUpdateDeleteIA(UserEntity user, ULBEntity ulb) {
		UserProfileEntity upe = user.getUserProfile();
		UserULBDataEntity uUDe = upe.getUserUlbInfo();
		List<ULBEntity> ulbs = null;
		if (uUDe != null && uUDe.getUlbs() != null) {
			ulbs = user.getUserProfile().getUserUlbInfo().getUlbs();
		}

		if (user.getAuthority().getName().equals(AuthorityConstants.ROLE_INTERNAL_AUDITOR) && ulbs != null
				&& ulbs.contains(ulb))
			return true;
		return false;
	}

	private BiFunction<IAStep1RequestDTO, AuditStatusEnum, AuditCreationDto> fnStep1ToAuditCreation = (step1Req,
			status) -> AuditCreationDto.builder().id(step1Req.getAuditId()).auditStatus(status)
					.description(step1Req.getDescription()).endDate(step1Req.getEndDate())
					.startDate(step1Req.getStartDate()).title(step1Req.getTitle()).type(AuditTypeEnum.INTERNAL_AUDIT)
					.ulbId(step1Req.getUlbId()).build();

	private Function<InternalAuditEntity, IAStep1ResponseDTO> fnIAEntityToResp = ia -> IAStep1ResponseDTO.builder()
			.auditReportId(ia.getAuditEntity().getId()).auditStatus(ia.getAuditEntity().getAuditStatus())
			.description(ia.getAuditEntity().getDescription()).endDate(ia.getAuditEntity().getEndDate())
			.id(ia.getAuditEntity().getId()).startDate(ia.getAuditEntity().getStartDate())
			.title(ia.getAuditEntity().getTitle()).ulb(ia.getAuditEntity().getUlb().getDTO())
			.ulbName(ia.getAuditEntity().getUlb().getName()).build();

	private BiFunction<AuditEntity, InternalAuditEntity, IAStep1ResponseDTO> fnAuditDTOToStep1Response = (audit,
			iaE) -> IAStep1ResponseDTO.builder().auditReportId(audit.getId()).auditStatus(audit.getAuditStatus())
			.isAssigned(audit.getCurrentStepOwner()!=null? true:false)
					.description(audit.getDescription()).endDate(audit.getEndDate()).id(iaE.getId())
					.startDate(audit.getStartDate()).title(audit.getTitle()).ulb(audit.getUlb().getDTO()).build();

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT
			+ "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ADMIN + "','" + AuthorityConstants.ROLE_SLPMU_AUDIT + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_UC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "','" + AuthorityConstants.ROLE_UDHD_PSEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SEC + "')")
	public Long getTotalIADuring(LocalDate startDate, LocalDate endDate) {
		return iaRepo.findByAuditEntity_StartDateGreaterThanEqualAndAuditEntity_EndDateLessThanEqual(startDate, endDate)
				.stream().count();
	}

	/**Approval Workflows*/

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "')")
	public BooleanResponseDTO sendInternalAuditForReviewToSLPMU(Long id){
		log.debug("sendInternalAuditForReviewToSLPMU",id);
		InternalAuditEntity internalAuditEntity = iaRepo.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("IA audit not found"));
		ULBEntity ulb = internalAuditEntity.getAuditEntity().getUlb();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Function<ULBInfoForULBUsersDTO, Boolean> isULBUserMappedToThisULB = ulbIn -> ulbIn.getUlb().getId() == ulb.getId();
		if(internalAuditEntity.getAuditEntity()==null){
			return new BooleanResponseDTO(false, "Audit entity cannot be empty");
		}
		//@Todo:add more criterias for validation
		if (!(internalAuditEntity.getAuditEntity().getAuditStatus().equals(AuditStatusEnum.DRAFT)||internalAuditEntity.getAuditEntity().getAuditStatus().equals(AuditStatusEnum.IN_REVIEW_ULB)))
			return new BooleanResponseDTO(false,"Audit entity can only be send to review in draft stage or in ulb review stage" );

		if (isULBUserMappedToThisULB.apply(userMgtService.getUlbInfoForUlbUser(auth.getPrincipal().toString())) == false)
			return new BooleanResponseDTO(false,"User not mapped to ULB for which this audit is being send for next step" );



		AuditEntity audit= internalAuditEntity.getAuditEntity();
		audit.setCurrentStepOwner(null);
		audit.setAuditStatus(AuditStatusEnum.IN_REVIEW_SLPMU);
		return new BooleanResponseDTO(true, "IA Audit with id "+id+ " has successfully been sent to SLPMU team");
	}

	/**
	 * @param iaAuditId
	 * @return
	 */
	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_SLPMU_AUDIT +  "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR+"')")
	public IAStep1ResponseDTO assignIAAuditToMe(Long iaAuditId) {
		UserEntity user= userMgtService.getCurrentUserInfoEntity();
		if(user.getAuthority().getName().equals(AuthorityConstants.ROLE_SLPMU_AUDIT)){
			return assignIAAuditToMeSLPMUAudit(iaAuditId);
		}else if(user.getAuthority().getName().equals(AuthorityConstants.ROLE_INTERNAL_AUDITOR)){
			return assignIAAuditToMeInternalAuditor(iaAuditId);
		}else{
			throw new BadRequestAlertException("Unauthorised operation","Unauthorised operation","Unauthorised operation");
		}
	}

	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_SLPMU_AUDIT +"')")
	private IAStep1ResponseDTO assignIAAuditToMeSLPMUAudit(Long iaId) throws BadRequestAlertException{
		InternalAuditEntity internalAuditEntity=iaRepo.findById(iaId).orElseThrow(()->new EntityNotFoundException("Internal Audit entity does not exist"));
		AuditEntity auditEntity=internalAuditEntity.getAuditEntity();
		if(!(auditEntity.getAuditStatus().equals(AuditStatusEnum.IN_REVIEW_SLPMU)  &&  auditEntity.getCurrentStepOwner()==null)){
			throw new BadRequestAlertException("Either audit is not in review slpmu state, or it is already assigned to another auditor","IA AUDIT Entity","");
		}

		UserEntity user= userMgtService.getCurrentUserInfoEntity();
		UserProfileEntity userProfile=user.getUserProfile();
		auditEntity.setCurrentStepOwner(userProfile);
		auditRepository.save(auditEntity);
		return  fnAuditDTOToStep1Response.apply(auditEntity, internalAuditEntity);
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR+"')")
	public IAAuditCommentDTO addCommentsForIAAuditReviewBySLPMUAndInternalAuditor(Long iaId, String comment) {
		InternalAuditEntity internalAuditEntity=iaRepo.findById(iaId).orElseThrow(()->new EntityNotFoundException("IA Audit entity does not exist"));
		IAAuditCommentEntity commentEntity= IAAuditCommentEntity.builder().comment(comment).build();
		iiaAuditCommentRepository.save(commentEntity);
		internalAuditEntity.getIaAuditCommentEntityList().add(commentEntity);
		iaRepo.save(internalAuditEntity);
		return commentEntity.getDTO();
	}


	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR+"')")
	public List<IAAuditCommentDTO> getCommentsForIA(Long iaId) {
		InternalAuditEntity internalAuditEntity=iaRepo.findById(iaId).orElseThrow(()->new EntityNotFoundException("IA Audit entity does not exist"));
		List<IAAuditCommentEntity>commentEntities=internalAuditEntity.getIaAuditCommentEntityList();
		return commentEntities.stream().map(cmt->cmt.getDTO()).collect(Collectors.toList());
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_SLPMU_AUDIT +"')")
	public IAStep1ResponseDTO approveOrRejectBySLPMUAudit(Boolean view, Long iaId) throws BadRequestAlertException{
		InternalAuditEntity internalAuditEntity=iaRepo.findById(iaId).orElseThrow(()->new EntityNotFoundException("IA Audit entity does not exist"));
		AuditEntity auditEntity=internalAuditEntity.getAuditEntity();
		UserEntity user= userMgtService.getCurrentUserInfoEntity();
		if(!(auditEntity.getAuditStatus().equals(AuditStatusEnum.IN_REVIEW_SLPMU)  &&  auditEntity.getCurrentStepOwner().equals(user.getUserProfile()))){
			throw new BadRequestAlertException("Either audit is not in review slpmu state, or you are not the current reviewer for this audit","IA AUDIT Entity","");
		}
		auditEntity.setCurrentStepOwner(null);
		if(view){
			auditEntity.setAuditStatus(AuditStatusEnum.PUBLISHED);
		}else{
			auditEntity.setAuditStatus(AuditStatusEnum.IN_REVIEW_ULB);
		}
		auditRepository.save(auditEntity);
		return  fnAuditDTOToStep1Response.apply(auditEntity, internalAuditEntity);
	}



	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_INTERNAL_AUDITOR +"')")
	private IAStep1ResponseDTO assignIAAuditToMeInternalAuditor(Long iaId) throws BadRequestAlertException{
		InternalAuditEntity internalAuditEntity=iaRepo.findById(iaId).orElseThrow(()->new EntityNotFoundException("Internal Audit entity does not exist"));
		AuditEntity auditEntity=internalAuditEntity.getAuditEntity();
		if(!(auditEntity.getAuditStatus().equals(AuditStatusEnum.IN_REVIEW_ULB)  &&  auditEntity.getCurrentStepOwner()==null)){
			throw new BadRequestAlertException("Either audit is not in review ulb state, or it is already assigned to another internal auditor","IA AUDIT Entity","");
		}
		ULBEntity ulb = auditEntity.getUlb();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Function<ULBInfoForULBUsersDTO, Boolean> isULBUserMappedToThisULB = ulbIn -> ulbIn.getUlb().getId() == ulb.getId();

		if (isULBUserMappedToThisULB.apply(userMgtService.getUlbInfoForUlbUser(auth.getPrincipal().toString())) == false)
			throw new BadRequestAlertException("User not mapped to ULB for which this audit is being send for next step" ,"IA Audit Entity","");

		UserEntity user= userMgtService.getCurrentUserInfoEntity();
		UserProfileEntity userProfile=user.getUserProfile();
		auditEntity.setCurrentStepOwner(userProfile);
		auditRepository.save(auditEntity);
		return fnAuditDTOToStep1Response.apply(auditEntity, internalAuditEntity);
	}

	/**
	 * @param data
	 * @return
	 */
	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_SLPMU_AUDIT +  "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR+"')")
	public GenericResponseObject<IAStep1ResponseDTO> loadIAAuditUnassingedByPage(Pagination<IAStep1ResponseDTO> data) {

		Pageable pageable = paginationInfoServiceForIAAudit.getPaginationRequestInfo(data);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserEntity user= userMgtService.getCurrentUserInfoEntity();
		AuditStatusEnum status=null;
		if(user.getAuthority().getName().equals(AuthorityConstants.ROLE_SLPMU_AUDIT)){
			status=AuditStatusEnum.IN_REVIEW_SLPMU;
		} else if (user.getAuthority().getName().equals(AuthorityConstants.ROLE_INTERNAL_AUDITOR)) {
			status=AuditStatusEnum.IN_REVIEW_ULB;
		}else {
			throw new BadRequestAlertException("Unauthorised role accessing data","Unauthorised role accessing data","Unauthorised role accessing data");
		}

		Page<InternalAuditEntity> internalAuditEntityPage = null;
		if (data.getQueryString() != null) {
			internalAuditEntityPage = iaRepo
					.findAllByAuditEntity_Ulb_NameIgnoreCaseContainingOrAuditEntity_TitleIgnoreCaseContainingAndAuditEntity_AuditStatusAndAuditEntity_CurrentStepOwner(pageable,
							data.getQueryString(), data.getQueryString(),status,null);
		} else {
			internalAuditEntityPage = iaRepo.findAllByAuditEntity_AuditStatusAndAuditEntity_CurrentStepOwner(pageable,status,null);
		}

		Page<IAStep1ResponseDTO> iaStep1ResponseDTOPage = paginationInfoServiceForIAAudit.getDataAsDTO(internalAuditEntityPage,
				en -> fnAuditDTOToStep1Response.apply(en.getAuditEntity(), en));

		return new GenericResponseObject<IAStep1ResponseDTO>(iaStep1ResponseDTOPage.getTotalElements(), iaStep1ResponseDTOPage,
				data.getPageNo(), data.getPageSize());

	}



	/**
	 * @param data
	 * @return
	 */
	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_SLPMU_AUDIT +  "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR+"')")
	public GenericResponseObject<IAStep1ResponseDTO> loadIAAuditAssingedToMeByPage(Pagination<IAStep1ResponseDTO> data) {

		Pageable pageable = paginationInfoServiceForIAAudit.getPaginationRequestInfo(data);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserEntity user= userMgtService.getCurrentUserInfoEntity();
		AuditStatusEnum status=null;
		if(user.getAuthority().getName().equals(AuthorityConstants.ROLE_SLPMU_AUDIT)){
			status=AuditStatusEnum.IN_REVIEW_SLPMU;
		} else if (user.getAuthority().getName().equals(AuthorityConstants.ROLE_ULB_ACCOUNTANT)|| user.getAuthority().equals(AuthorityConstants.ROLE_FLIA)) {
			status=AuditStatusEnum.IN_REVIEW_ULB;
		}else {
			throw new BadRequestAlertException("Unauthorised role accessing data","Unauthorised role accessing data","Unauthorised role accessing data");
		}

		Page<InternalAuditEntity> iaAuditEntityPage = null;
		if (data.getQueryString() != null) {
			iaAuditEntityPage = iaRepo
					.findAllByAuditEntity_Ulb_NameIgnoreCaseContainingOrAuditEntity_TitleIgnoreCaseContainingAndAuditEntity_AuditStatusAndAuditEntity_CurrentStepOwner(pageable,
							data.getQueryString(), data.getQueryString(),status,user.getUserProfile());
		} else {
			iaAuditEntityPage = iaRepo.findAllByAuditEntity_AuditStatusAndAuditEntity_CurrentStepOwner(pageable,status,user.getUserProfile());
		}

		Page<IAStep1ResponseDTO> iaStep1ResponseDTOPage = paginationInfoServiceForIAAudit.getDataAsDTO(iaAuditEntityPage,
				en ->fnAuditDTOToStep1Response.apply(en.getAuditEntity(), en));

		return new GenericResponseObject<IAStep1ResponseDTO>(iaStep1ResponseDTOPage.getTotalElements(), iaStep1ResponseDTOPage,
				data.getPageNo(), data.getPageSize());

	}



}
