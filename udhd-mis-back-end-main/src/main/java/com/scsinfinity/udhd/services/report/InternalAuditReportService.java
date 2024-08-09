package com.scsinfinity.udhd.services.report;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.BiFunction;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;
import com.scsinfinity.udhd.configurations.security.SecuredUserInfoService;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAAdministrationEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAAdministrativeExpensesEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAAllDevelopmentWorksEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IADetailAuditEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAExecutiveSummaryEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAFinanceEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IALoanRepaymentEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAOperationAndMaintenanceEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAOtherCapitalExpenditureEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAOtherExpenditureEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARCRCFCGrantsEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARCRCentralCapitalAccountGrantsEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARCRFeesNFinesEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARCRIncomeFromInterestEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARCRLoansEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARCROctraiCompensationEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARCROtherCapitalReceiptsEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARCROtherCentralGovtTransfersEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARCROtherNonTaxRevenueEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARCROtherRevenueIncomeEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARCROtherStateGovtTransfersEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARCROtherTaxEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARCROthersEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARCRPropertyTaxEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARCRSaleOfMunicipalLandEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARCRStateAssignedReveueEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARCRStateCapitalAccountsGrantsEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARCRStateFCGrantsEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARCRUserChargesEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARevenueAndCapitalReceiptsInfoDetailsEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARevenueNCapitalExpenditureEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARevenueNCapitalReceiptsAndExpenditure;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARevenueNCapitalReceiptsEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.InternalAuditEntity;
import com.scsinfinity.udhd.dao.entities.base.UserEntity;
import com.scsinfinity.udhd.dao.entities.storage.FileEntity;
import com.scsinfinity.udhd.dao.entities.storage.FolderEntity;
import com.scsinfinity.udhd.dao.entities.storage.FolderUserGroupsEnum;
import com.scsinfinity.udhd.dao.repositories.storage.IFileRepository;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IInternalAuditService;
import com.scsinfinity.udhd.services.base.RandomGeneratorService;
import com.scsinfinity.udhd.services.base.interfaces.IFileService;
import com.scsinfinity.udhd.services.base.interfaces.IFolderService;
import com.scsinfinity.udhd.services.report.interfaces.IInternalAuditReportService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class InternalAuditReportService implements IInternalAuditReportService {

	@Value("${scs.setting.folder.reports.nickname}")
	private String reportsFolderNickName;
	private final IFileService fileService;
	private final IFileRepository fileRepository;
	private final IFolderService folderService;
	private final SecuredUserInfoService securedUser;
	private final IInternalAuditService internalAuditService;
	private final RandomGeneratorService randomGeneratorService;

	@Override
	public Resource downloadIaResource(Long iaId) {

		FolderEntity folder = getFolder();
		OutputStream outputStream;
		try {
			String fileName = "File_" + randomGeneratorService.generateRandomNumberForFilename() + ".pdf";
			outputStream = new FileOutputStream(folder.getPathToCurrentDir() + File.separator + fileName);
			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocumentFromString(parseThymeLeaf(iaId));
			renderer.layout();
			renderer.createPDF(outputStream);
			outputStream.close();

			FileEntity file = fileSave(fileName, folder);
			return fileService.getFile(file.getFileId());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Resource getFile(String fileId) {
		try {
			return fileService.getFile(fileId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private FolderEntity getFolder() {
		try {
			UserEntity currentUser = securedUser.getCurrentUserInfo();
			FolderUserGroupsEnum fodlerUserGroup = FolderUserGroupsEnum.AUTHENTICATED;
			if (currentUser == null) {
				fodlerUserGroup = FolderUserGroupsEnum.UNAUTHENTICATED;
			}
			return folderService.getFolderEntityByNickname(reportsFolderNickName, fodlerUserGroup);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	private FileEntity fileSave(String fileName, FolderEntity parentFolder) {
		FileEntity fileToSave = FileEntity.builder().fileId(randomGeneratorService.generateRandomNumberForFilename())
				.fileServerName(fileName).extension("pdf").name(fileName).parentFolder(parentFolder).build();

		return fileRepository.saveAndFlush(fileToSave);

	}

	private String parseThymeLeaf(Long iaId) {
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setPrefix("templates/thymeleaf/");
		TemplateEngine templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);

		Context context = new Context();
		InternalAuditEntity ia = internalAuditService.findInternalAuditEntityById(iaId);
		IAExecutiveSummaryEntity executiveSummary = ia.getExecutiveSummary();

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		context.setVariable("nameOfMunicipality", ia.getAuditEntity().getUlb().getName());
		context.setVariable("auditStartDate", parseToDDMMYYYY.apply(dtf, ia.getAuditEntity().getStartDate()));
		context.setVariable("auditEndDate", parseToDDMMYYYY.apply(dtf, ia.getAuditEntity().getEndDate()));
		context.setVariable("periodUnderCurrentAudit", executiveSummary.getPeriodCovered());
		context.setVariable("nameOfMunicipalOfficer", executiveSummary.getExecutiveOfficerNameForPeriod());
		context.setVariable("stengthObservedDuringAudit", executiveSummary.getStrengths());
		context.setVariable("weaknessObservedDuringAudit", executiveSummary.getWeaknesses());
		context.setVariable("overallOpinionOfAuditTeamAboutFunctionality", executiveSummary.getOverAllOpinion());
		context.setVariable("auditRecommendations", executiveSummary.getRecommendations());
		context.setVariable("commentsFromManagement", executiveSummary.getCommentFromMgt());
		context.setVariable("acknowledgement", executiveSummary.getAcknowledgement());

		IAAdministrationEntity administration = ia.getAdministration();

		context.setVariable("administration", administration);

		IADetailAuditEntity details = ia.getDetails();

		context.setVariable("details", details);

		IAFinanceEntity finance = ia.getFinance();

		context.setVariable("finance", finance);

		IARevenueNCapitalReceiptsEntity revNCapitalReceipts = finance.getRevenueNCapitalReceipts();

		IARCRPropertyTaxEntity propertyTax = revNCapitalReceipts.getPropertyTax();
		IARCROtherTaxEntity otherTax = revNCapitalReceipts.getOtherTax();
		IARCRFeesNFinesEntity feesAndFines = revNCapitalReceipts.getFeesAndFines();
		IARCRUserChargesEntity userCharges = revNCapitalReceipts.getUserCharges();
		IARCROtherNonTaxRevenueEntity otherNonTaxRevenue = revNCapitalReceipts.getOtherNonTaxRevenue();
		IARCRIncomeFromInterestEntity incomeFromInterest = revNCapitalReceipts.getIncomeFromInterest();
		IARCROtherRevenueIncomeEntity otherRevenueIncome = revNCapitalReceipts.getOtherRevenueIncome();
		IARCRStateAssignedReveueEntity stateAssignedRevenue = revNCapitalReceipts.getStateAssignedRevenue();
		IARCRStateFCGrantsEntity stateFCGrants = revNCapitalReceipts.getStateFCGrants();
		IARCROctraiCompensationEntity octraiCompensation = revNCapitalReceipts.getOctraiCompensation();
		IARCROtherStateGovtTransfersEntity otherStateGovtTransfers = revNCapitalReceipts.getOtherStateGovtTransfers();
		IARCRCFCGrantsEntity cfcGrants = revNCapitalReceipts.getCfcGrants();
		IARCROtherCentralGovtTransfersEntity otherCentralGovtTransfers = revNCapitalReceipts
				.getOtherCentralGovtTransfers();
		IARCROthersEntity others = revNCapitalReceipts.getOthers();
		IARCRSaleOfMunicipalLandEntity saleOfMunicipalLand = revNCapitalReceipts.getSaleOfMunicipalLand();
		IARCRLoansEntity loans = revNCapitalReceipts.getLoans();
		IARCRStateCapitalAccountsGrantsEntity stateCapitalAccountGrants = revNCapitalReceipts
				.getStateCapitalAccountGrants();
		IARCRCentralCapitalAccountGrantsEntity centralCapitalAccountGrants = revNCapitalReceipts
				.getCentralCapitalAccountGrants();
		IARCROtherCapitalReceiptsEntity otherCapitalReceipts = revNCapitalReceipts.getOtherCapitalReceipts();

		context.setVariable("totalReceipts",
				getTotalReceipts(propertyTax, otherTax, feesAndFines, userCharges, otherNonTaxRevenue,
						incomeFromInterest, otherRevenueIncome, stateAssignedRevenue, stateFCGrants, octraiCompensation,
						otherStateGovtTransfers, cfcGrants, otherCentralGovtTransfers, others, saleOfMunicipalLand,
						loans, stateCapitalAccountGrants, centralCapitalAccountGrants, otherCapitalReceipts));

		context.setVariable("revenueReceipts",
				getRevenueReceipts(propertyTax, otherTax, feesAndFines, userCharges, otherNonTaxRevenue,
						incomeFromInterest, otherRevenueIncome, stateAssignedRevenue, stateFCGrants, octraiCompensation,
						otherStateGovtTransfers, cfcGrants, otherCentralGovtTransfers, others));
		context.setVariable("ownRevenueReceipts",
				getOwnRevenueReceipts(propertyTax, otherTax, feesAndFines, userCharges, otherNonTaxRevenue));
		context.setVariable("taxRevenue", getTaxRevenue(propertyTax, otherTax));
		context.setVariable("nonTaxRevenue", getNonTaxRevenueReceipts(feesAndFines, userCharges, otherNonTaxRevenue));
		context.setVariable("otherRevenueReceipts",
				getOtherRevenueReceipts(incomeFromInterest, otherRevenueIncome, stateAssignedRevenue, stateFCGrants,
						octraiCompensation, otherStateGovtTransfers, cfcGrants, otherCentralGovtTransfers, others,
						saleOfMunicipalLand, loans, stateCapitalAccountGrants, centralCapitalAccountGrants,
						otherCapitalReceipts));
		context.setVariable("transfers", getTransfersGrants(stateAssignedRevenue, stateFCGrants, octraiCompensation,
				otherStateGovtTransfers, cfcGrants, otherCentralGovtTransfers, others));

		context.setVariable("capitalReceipts", getCapitalReceipts(saleOfMunicipalLand, loans, stateCapitalAccountGrants,
				centralCapitalAccountGrants, otherCapitalReceipts));

		IARevenueNCapitalExpenditureEntity expenditure = finance.getRevenueNCapitalExpenditure();
		IAAdministrativeExpensesEntity administrativeExpenses = expenditure.getAdministrativeExpenses();
		IAOperationAndMaintenanceEntity operationAndMaintenance = expenditure.getOperationAndMaintenance();
		IALoanRepaymentEntity loanRepayment = expenditure.getLoanRepayment();
		IAOtherExpenditureEntity otherExpenditure = expenditure.getOtherExpenditure();
		IAAllDevelopmentWorksEntity developmentWorks = expenditure.getDevelopmentWorks();
		IAOtherCapitalExpenditureEntity otherCapitalExpenditure = expenditure.getOtherCapitalExpenditure();

		context.setVariable("totalExpenditure", getTotalExpenditure(administrativeExpenses, operationAndMaintenance,
				loanRepayment, otherExpenditure, developmentWorks, otherCapitalExpenditure));
		context.setVariable("revenueExpenditure", getRevenueExpenditure(administrativeExpenses, operationAndMaintenance,
				loanRepayment, otherExpenditure));
		context.setVariable("capitalExpenditure", getCapitalExpenditure(developmentWorks, otherCapitalExpenditure));
		return templateEngine.process("thymeleaf_internal_audit", context);
	}

	private IARevenueNCapitalReceiptsAndExpenditure getCapitalExpenditure(IAAllDevelopmentWorksEntity developmentWorks,
			IAOtherCapitalExpenditureEntity otherCapitalExpenditure) {
		IARevenueNCapitalReceiptsAndExpenditure expense = new IARevenueNCapitalReceiptsAndExpenditure();

		expense.setFy1Amt(developmentWorks.getFy1Amt().add(otherCapitalExpenditure.getFy1Amt()));
		expense.setFy2Amt(developmentWorks.getFy2Amt().add(otherCapitalExpenditure.getFy2Amt()));
		expense.setFy3Amt(developmentWorks.getFy3Amt().add(otherCapitalExpenditure.getFy3Amt()));
		expense.setFy4Amt(developmentWorks.getFy4Amt().add(otherCapitalExpenditure.getFy4Amt()));
		expense.setFy5Amt(developmentWorks.getFy5Amt().add(otherCapitalExpenditure.getFy5Amt()));
		expense.setFy6Amt(developmentWorks.getFy6Amt().add(otherCapitalExpenditure.getFy6Amt()));
		return expense;
	}

	private IARevenueNCapitalReceiptsAndExpenditure getRevenueExpenditure(
			IAAdministrativeExpensesEntity administrativeExpenses,
			IAOperationAndMaintenanceEntity operationAndMaintenance, IALoanRepaymentEntity loanRepayment,
			IAOtherExpenditureEntity otherExpenditure) {
		IARevenueNCapitalReceiptsAndExpenditure expense = new IARevenueNCapitalReceiptsAndExpenditure();

		expense.setFy1Amt(administrativeExpenses.getFy1Amt().add(operationAndMaintenance.getFy1Amt())
				.add(loanRepayment.getFy1Amt()).add(otherExpenditure.getFy1Amt()));
		expense.setFy2Amt(administrativeExpenses.getFy2Amt().add(operationAndMaintenance.getFy2Amt())
				.add(loanRepayment.getFy2Amt()).add(otherExpenditure.getFy2Amt()));
		expense.setFy3Amt(administrativeExpenses.getFy3Amt().add(operationAndMaintenance.getFy3Amt())
				.add(loanRepayment.getFy3Amt()).add(otherExpenditure.getFy3Amt()));
		expense.setFy4Amt(administrativeExpenses.getFy4Amt().add(operationAndMaintenance.getFy4Amt())
				.add(loanRepayment.getFy4Amt()).add(otherExpenditure.getFy4Amt()));
		expense.setFy5Amt(administrativeExpenses.getFy5Amt().add(operationAndMaintenance.getFy5Amt())
				.add(loanRepayment.getFy5Amt()).add(otherExpenditure.getFy5Amt()));
		expense.setFy6Amt(administrativeExpenses.getFy6Amt().add(operationAndMaintenance.getFy6Amt())
				.add(loanRepayment.getFy6Amt()).add(otherExpenditure.getFy6Amt()));
		return expense;
	}

	private IARevenueNCapitalReceiptsAndExpenditure getTotalExpenditure(
			IAAdministrativeExpensesEntity administrativeExpenses,
			IAOperationAndMaintenanceEntity operationAndMaintenance, IALoanRepaymentEntity loanRepayment,
			IAOtherExpenditureEntity otherExpenditure, IAAllDevelopmentWorksEntity developmentWorks,
			IAOtherCapitalExpenditureEntity otherCapitalExpenditure) {
		IARevenueNCapitalReceiptsAndExpenditure expense = new IARevenueNCapitalReceiptsAndExpenditure();

		expense.setFy1Amt(administrativeExpenses.getFy1Amt().add(operationAndMaintenance.getFy1Amt())
				.add(loanRepayment.getFy1Amt()).add(otherExpenditure.getFy1Amt()).add(developmentWorks.getFy1Amt())
				.add(otherCapitalExpenditure.getFy1Amt()));
		expense.setFy2Amt(administrativeExpenses.getFy2Amt().add(operationAndMaintenance.getFy2Amt())
				.add(loanRepayment.getFy2Amt()).add(otherExpenditure.getFy2Amt()).add(developmentWorks.getFy2Amt())
				.add(otherCapitalExpenditure.getFy2Amt()));
		expense.setFy3Amt(administrativeExpenses.getFy3Amt().add(operationAndMaintenance.getFy3Amt())
				.add(loanRepayment.getFy3Amt()).add(otherExpenditure.getFy3Amt()).add(developmentWorks.getFy3Amt())
				.add(otherCapitalExpenditure.getFy3Amt()));
		expense.setFy4Amt(administrativeExpenses.getFy4Amt().add(operationAndMaintenance.getFy4Amt())
				.add(loanRepayment.getFy4Amt()).add(otherExpenditure.getFy4Amt()).add(developmentWorks.getFy4Amt())
				.add(otherCapitalExpenditure.getFy4Amt()));
		expense.setFy5Amt(administrativeExpenses.getFy5Amt().add(operationAndMaintenance.getFy5Amt())
				.add(loanRepayment.getFy5Amt()).add(otherExpenditure.getFy5Amt()).add(developmentWorks.getFy5Amt())
				.add(otherCapitalExpenditure.getFy5Amt()));
		expense.setFy6Amt(administrativeExpenses.getFy6Amt().add(operationAndMaintenance.getFy6Amt())
				.add(loanRepayment.getFy6Amt()).add(otherExpenditure.getFy6Amt()).add(developmentWorks.getFy6Amt())
				.add(otherCapitalExpenditure.getFy6Amt()));
		return expense;
	}

	private IARevenueNCapitalReceiptsAndExpenditure getCapitalReceipts(
			IARCRSaleOfMunicipalLandEntity saleOfMunicipalLand, IARCRLoansEntity loans,
			IARCRStateCapitalAccountsGrantsEntity stateCapitalAccountGrants,
			IARCRCentralCapitalAccountGrantsEntity centralCapitalAccountGrants,
			IARCROtherCapitalReceiptsEntity otherCapitalReceipts) {
		IARevenueNCapitalReceiptsAndExpenditure receipts = new IARevenueNCapitalReceiptsAndExpenditure();
		receipts.setFy1Amt(
				saleOfMunicipalLand.getFy1Amt().add(loans.getFy1Amt()).add(stateCapitalAccountGrants.getFy1Amt())
						.add(centralCapitalAccountGrants.getFy1Amt()).add(otherCapitalReceipts.getFy1Amt()));
		receipts.setFy2Amt(
				saleOfMunicipalLand.getFy2Amt().add(loans.getFy2Amt()).add(stateCapitalAccountGrants.getFy2Amt())
						.add(centralCapitalAccountGrants.getFy2Amt()).add(otherCapitalReceipts.getFy2Amt()));
		receipts.setFy3Amt(
				saleOfMunicipalLand.getFy3Amt().add(loans.getFy3Amt()).add(stateCapitalAccountGrants.getFy3Amt())
						.add(centralCapitalAccountGrants.getFy3Amt()).add(otherCapitalReceipts.getFy3Amt()));
		receipts.setFy4Amt(
				saleOfMunicipalLand.getFy4Amt().add(loans.getFy4Amt()).add(stateCapitalAccountGrants.getFy4Amt())
						.add(centralCapitalAccountGrants.getFy4Amt()).add(otherCapitalReceipts.getFy4Amt()));
		receipts.setFy5Amt(
				saleOfMunicipalLand.getFy5Amt().add(loans.getFy5Amt()).add(stateCapitalAccountGrants.getFy5Amt())
						.add(centralCapitalAccountGrants.getFy5Amt()).add(otherCapitalReceipts.getFy5Amt()));
		receipts.setFy6Amt(
				saleOfMunicipalLand.getFy6Amt().add(loans.getFy6Amt()).add(stateCapitalAccountGrants.getFy6Amt())
						.add(centralCapitalAccountGrants.getFy6Amt()).add(otherCapitalReceipts.getFy6Amt()));

		return receipts;
	}

	private IARevenueNCapitalReceiptsAndExpenditure getTransfersGrants(
			IARCRStateAssignedReveueEntity stateAssignedRevenue, IARCRStateFCGrantsEntity stateFCGrants,
			IARCROctraiCompensationEntity octraiCompensation,
			IARCROtherStateGovtTransfersEntity otherStateGovtTransfers, IARCRCFCGrantsEntity cfcGrants,
			IARCROtherCentralGovtTransfersEntity otherCentralGovtTransfers, IARCROthersEntity others) {
		IARevenueNCapitalReceiptsAndExpenditure receipts = new IARevenueNCapitalReceiptsAndExpenditure();
		receipts.setFy1Amt(stateAssignedRevenue.getFy1Amt().add(stateFCGrants.getFy1Amt())
				.add(octraiCompensation.getFy1Amt()).add(otherStateGovtTransfers.getFy1Amt()).add(cfcGrants.getFy1Amt())
				.add(otherCentralGovtTransfers.getFy1Amt()).add(others.getFy1Amt()));
		receipts.setFy2Amt(stateAssignedRevenue.getFy2Amt().add(stateFCGrants.getFy2Amt())
				.add(octraiCompensation.getFy2Amt()).add(otherStateGovtTransfers.getFy2Amt()).add(cfcGrants.getFy2Amt())
				.add(otherCentralGovtTransfers.getFy2Amt()).add(others.getFy2Amt()));
		receipts.setFy3Amt(stateAssignedRevenue.getFy3Amt().add(stateFCGrants.getFy3Amt())
				.add(octraiCompensation.getFy3Amt()).add(otherStateGovtTransfers.getFy3Amt()).add(cfcGrants.getFy3Amt())
				.add(otherCentralGovtTransfers.getFy3Amt()).add(others.getFy3Amt()));
		receipts.setFy4Amt(stateAssignedRevenue.getFy4Amt().add(stateFCGrants.getFy4Amt())
				.add(octraiCompensation.getFy4Amt()).add(otherStateGovtTransfers.getFy4Amt()).add(cfcGrants.getFy4Amt())
				.add(otherCentralGovtTransfers.getFy4Amt()).add(others.getFy4Amt()));
		receipts.setFy5Amt(stateAssignedRevenue.getFy5Amt().add(stateFCGrants.getFy5Amt())
				.add(octraiCompensation.getFy5Amt()).add(otherStateGovtTransfers.getFy5Amt()).add(cfcGrants.getFy5Amt())
				.add(otherCentralGovtTransfers.getFy5Amt()).add(others.getFy5Amt()));
		receipts.setFy6Amt(stateAssignedRevenue.getFy6Amt().add(stateFCGrants.getFy6Amt())
				.add(octraiCompensation.getFy6Amt()).add(otherStateGovtTransfers.getFy6Amt()).add(cfcGrants.getFy6Amt())
				.add(otherCentralGovtTransfers.getFy6Amt()).add(others.getFy6Amt()));

		return receipts;
	}

	private IARevenueNCapitalReceiptsAndExpenditure getTotalReceipts(IARCRPropertyTaxEntity propertyTax,
			IARCROtherTaxEntity otherTax, IARCRFeesNFinesEntity feesAndFines, IARCRUserChargesEntity userCharges,
			IARCROtherNonTaxRevenueEntity otherNonTaxRevenue, IARCRIncomeFromInterestEntity incomeFromInterest,
			IARCROtherRevenueIncomeEntity otherRevenueIncome, IARCRStateAssignedReveueEntity stateAssignedRevenue,
			IARCRStateFCGrantsEntity stateFCGrants, IARCROctraiCompensationEntity octraiCompensation,
			IARCROtherStateGovtTransfersEntity otherStateGovtTransfers, IARCRCFCGrantsEntity cfcGrants,
			IARCROtherCentralGovtTransfersEntity otherCentralGovtTransfers, IARCROthersEntity others,
			IARCRSaleOfMunicipalLandEntity saleOfMunicipalLand, IARCRLoansEntity loans,
			IARCRStateCapitalAccountsGrantsEntity stateCapitalAccountGrants,
			IARCRCentralCapitalAccountGrantsEntity centralCapitalAccountGrants,
			IARCROtherCapitalReceiptsEntity otherCapitalReceipts) {
		IARevenueNCapitalReceiptsAndExpenditure receipts = new IARevenueNCapitalReceiptsAndExpenditure();
		receipts.setFy1Amt(propertyTax.getFy1Amt().add(otherTax.getFy1Amt().add(feesAndFines.getFy1Amt())
				.add(userCharges.getFy1Amt()).add(otherNonTaxRevenue.getFy1Amt()).add(incomeFromInterest.getFy1Amt())
				.add(otherRevenueIncome.getFy1Amt()).add(stateAssignedRevenue.getFy1Amt())
				.add(stateFCGrants.getFy1Amt()).add(octraiCompensation.getFy1Amt())
				.add(otherStateGovtTransfers.getFy1Amt()).add(cfcGrants.getFy1Amt())
				.add(otherCentralGovtTransfers.getFy1Amt()).add(others.getFy1Amt()).add(saleOfMunicipalLand.getFy1Amt())
				.add(loans.getFy1Amt()).add(stateCapitalAccountGrants.getFy1Amt())
				.add(centralCapitalAccountGrants.getFy1Amt()).add(otherCapitalReceipts.getFy1Amt())));
		receipts.setFy2Amt(propertyTax.getFy2Amt().add(otherTax.getFy2Amt().add(feesAndFines.getFy2Amt())
				.add(userCharges.getFy2Amt()).add(otherNonTaxRevenue.getFy2Amt()).add(incomeFromInterest.getFy2Amt())
				.add(otherRevenueIncome.getFy2Amt()).add(stateAssignedRevenue.getFy2Amt())
				.add(stateFCGrants.getFy2Amt()).add(octraiCompensation.getFy2Amt())
				.add(otherStateGovtTransfers.getFy2Amt()).add(cfcGrants.getFy2Amt())
				.add(otherCentralGovtTransfers.getFy2Amt()).add(others.getFy2Amt()).add(saleOfMunicipalLand.getFy2Amt())
				.add(loans.getFy2Amt()).add(stateCapitalAccountGrants.getFy2Amt())
				.add(centralCapitalAccountGrants.getFy2Amt()).add(otherCapitalReceipts.getFy2Amt())));
		receipts.setFy3Amt(propertyTax.getFy3Amt().add(otherTax.getFy3Amt().add(feesAndFines.getFy3Amt())
				.add(userCharges.getFy3Amt()).add(otherNonTaxRevenue.getFy3Amt()).add(incomeFromInterest.getFy3Amt())
				.add(otherRevenueIncome.getFy3Amt()).add(stateAssignedRevenue.getFy3Amt())
				.add(stateFCGrants.getFy3Amt()).add(octraiCompensation.getFy3Amt())
				.add(otherStateGovtTransfers.getFy3Amt()).add(cfcGrants.getFy3Amt())
				.add(otherCentralGovtTransfers.getFy3Amt()).add(others.getFy3Amt()).add(saleOfMunicipalLand.getFy3Amt())
				.add(loans.getFy3Amt()).add(stateCapitalAccountGrants.getFy3Amt())
				.add(centralCapitalAccountGrants.getFy3Amt()).add(otherCapitalReceipts.getFy3Amt())));
		receipts.setFy4Amt(propertyTax.getFy4Amt().add(otherTax.getFy4Amt().add(feesAndFines.getFy4Amt())
				.add(userCharges.getFy4Amt()).add(otherNonTaxRevenue.getFy4Amt()).add(incomeFromInterest.getFy4Amt())
				.add(otherRevenueIncome.getFy4Amt()).add(stateAssignedRevenue.getFy4Amt())
				.add(stateFCGrants.getFy4Amt()).add(octraiCompensation.getFy4Amt())
				.add(otherStateGovtTransfers.getFy4Amt()).add(cfcGrants.getFy4Amt())
				.add(otherCentralGovtTransfers.getFy4Amt()).add(others.getFy4Amt()).add(saleOfMunicipalLand.getFy4Amt())
				.add(loans.getFy4Amt()).add(stateCapitalAccountGrants.getFy4Amt())
				.add(centralCapitalAccountGrants.getFy4Amt()).add(otherCapitalReceipts.getFy4Amt())));
		receipts.setFy5Amt(propertyTax.getFy5Amt().add(otherTax.getFy5Amt().add(feesAndFines.getFy5Amt())
				.add(userCharges.getFy5Amt()).add(otherNonTaxRevenue.getFy5Amt()).add(incomeFromInterest.getFy5Amt())
				.add(otherRevenueIncome.getFy5Amt()).add(stateAssignedRevenue.getFy5Amt())
				.add(stateFCGrants.getFy5Amt()).add(octraiCompensation.getFy5Amt())
				.add(otherStateGovtTransfers.getFy5Amt()).add(cfcGrants.getFy5Amt())
				.add(otherCentralGovtTransfers.getFy5Amt()).add(others.getFy5Amt()).add(saleOfMunicipalLand.getFy5Amt())
				.add(loans.getFy5Amt()).add(stateCapitalAccountGrants.getFy5Amt())
				.add(centralCapitalAccountGrants.getFy5Amt()).add(otherCapitalReceipts.getFy5Amt())));
		receipts.setFy6Amt(propertyTax.getFy6Amt().add(otherTax.getFy6Amt().add(feesAndFines.getFy6Amt())
				.add(userCharges.getFy6Amt()).add(otherNonTaxRevenue.getFy6Amt()).add(incomeFromInterest.getFy6Amt())
				.add(otherRevenueIncome.getFy6Amt()).add(stateAssignedRevenue.getFy6Amt())
				.add(stateFCGrants.getFy6Amt()).add(octraiCompensation.getFy6Amt())
				.add(otherStateGovtTransfers.getFy6Amt()).add(cfcGrants.getFy6Amt())
				.add(otherCentralGovtTransfers.getFy6Amt()).add(others.getFy6Amt()).add(saleOfMunicipalLand.getFy6Amt())
				.add(loans.getFy6Amt()).add(stateCapitalAccountGrants.getFy6Amt())
				.add(centralCapitalAccountGrants.getFy6Amt()).add(otherCapitalReceipts.getFy6Amt())));

		return receipts;
	}

	private IARevenueNCapitalReceiptsAndExpenditure getOtherRevenueReceipts(
			IARCRIncomeFromInterestEntity incomeFromInterest, IARCROtherRevenueIncomeEntity otherRevenueIncome,
			IARCRStateAssignedReveueEntity stateAssignedRevenue, IARCRStateFCGrantsEntity stateFCGrants,
			IARCROctraiCompensationEntity octraiCompensation,
			IARCROtherStateGovtTransfersEntity otherStateGovtTransfers, IARCRCFCGrantsEntity cfcGrants,
			IARCROtherCentralGovtTransfersEntity otherCentralGovtTransfers, IARCROthersEntity others,
			IARCRSaleOfMunicipalLandEntity saleOfMunicipalLand, IARCRLoansEntity loans,
			IARCRStateCapitalAccountsGrantsEntity stateCapitalAccountGrants,
			IARCRCentralCapitalAccountGrantsEntity centralCapitalAccountGrants,
			IARCROtherCapitalReceiptsEntity otherCapitalReceipts) {
		IARevenueNCapitalReceiptsAndExpenditure receipts = new IARevenueNCapitalReceiptsAndExpenditure();
		receipts.setFy1Amt(incomeFromInterest.getFy1Amt().add(otherRevenueIncome.getFy1Amt())
				.add(stateAssignedRevenue.getFy1Amt()).add(stateFCGrants.getFy1Amt())
				.add(octraiCompensation.getFy1Amt()).add(otherStateGovtTransfers.getFy1Amt()).add(cfcGrants.getFy1Amt())
				.add(otherCentralGovtTransfers.getFy1Amt()).add(others.getFy1Amt()).add(saleOfMunicipalLand.getFy1Amt())
				.add(loans.getFy1Amt()).add(stateCapitalAccountGrants.getFy1Amt())
				.add(centralCapitalAccountGrants.getFy1Amt()).add(otherCapitalReceipts.getFy1Amt()));
		receipts.setFy2Amt(incomeFromInterest.getFy2Amt().add(otherRevenueIncome.getFy2Amt())
				.add(stateAssignedRevenue.getFy2Amt()).add(stateFCGrants.getFy2Amt())
				.add(octraiCompensation.getFy2Amt()).add(otherStateGovtTransfers.getFy2Amt()).add(cfcGrants.getFy2Amt())
				.add(otherCentralGovtTransfers.getFy2Amt()).add(others.getFy2Amt()).add(saleOfMunicipalLand.getFy2Amt())
				.add(loans.getFy2Amt()).add(stateCapitalAccountGrants.getFy2Amt())
				.add(centralCapitalAccountGrants.getFy2Amt()).add(otherCapitalReceipts.getFy2Amt()));
		receipts.setFy3Amt(incomeFromInterest.getFy3Amt().add(otherRevenueIncome.getFy3Amt())
				.add(stateAssignedRevenue.getFy3Amt()).add(stateFCGrants.getFy3Amt())
				.add(octraiCompensation.getFy3Amt()).add(otherStateGovtTransfers.getFy3Amt()).add(cfcGrants.getFy3Amt())
				.add(otherCentralGovtTransfers.getFy3Amt()).add(others.getFy3Amt()).add(saleOfMunicipalLand.getFy3Amt())
				.add(loans.getFy3Amt()).add(stateCapitalAccountGrants.getFy3Amt())
				.add(centralCapitalAccountGrants.getFy3Amt()).add(otherCapitalReceipts.getFy3Amt()));
		receipts.setFy4Amt(incomeFromInterest.getFy4Amt().add(otherRevenueIncome.getFy4Amt())
				.add(stateAssignedRevenue.getFy4Amt()).add(stateFCGrants.getFy4Amt())
				.add(octraiCompensation.getFy4Amt()).add(otherStateGovtTransfers.getFy4Amt()).add(cfcGrants.getFy4Amt())
				.add(otherCentralGovtTransfers.getFy4Amt()).add(others.getFy4Amt()).add(saleOfMunicipalLand.getFy4Amt())
				.add(loans.getFy4Amt()).add(stateCapitalAccountGrants.getFy4Amt())
				.add(centralCapitalAccountGrants.getFy4Amt()).add(otherCapitalReceipts.getFy4Amt()));
		receipts.setFy5Amt(incomeFromInterest.getFy5Amt().add(otherRevenueIncome.getFy5Amt())
				.add(stateAssignedRevenue.getFy5Amt()).add(stateFCGrants.getFy5Amt())
				.add(octraiCompensation.getFy5Amt()).add(otherStateGovtTransfers.getFy5Amt()).add(cfcGrants.getFy5Amt())
				.add(otherCentralGovtTransfers.getFy5Amt()).add(others.getFy5Amt()).add(saleOfMunicipalLand.getFy5Amt())
				.add(loans.getFy5Amt()).add(stateCapitalAccountGrants.getFy5Amt())
				.add(centralCapitalAccountGrants.getFy5Amt()).add(otherCapitalReceipts.getFy5Amt()));
		receipts.setFy6Amt(incomeFromInterest.getFy6Amt().add(otherRevenueIncome.getFy6Amt())
				.add(stateAssignedRevenue.getFy6Amt()).add(stateFCGrants.getFy6Amt())
				.add(octraiCompensation.getFy6Amt()).add(otherStateGovtTransfers.getFy6Amt()).add(cfcGrants.getFy6Amt())
				.add(otherCentralGovtTransfers.getFy6Amt()).add(others.getFy6Amt()).add(saleOfMunicipalLand.getFy6Amt())
				.add(loans.getFy6Amt()).add(stateCapitalAccountGrants.getFy6Amt())
				.add(centralCapitalAccountGrants.getFy6Amt()).add(otherCapitalReceipts.getFy6Amt()));

		return receipts;
	}

	private IARevenueNCapitalReceiptsAndExpenditure getRevenueReceipts(IARCRPropertyTaxEntity propertyTax,
			IARCROtherTaxEntity otherTax, IARCRFeesNFinesEntity feesAndFines, IARCRUserChargesEntity userCharges,
			IARCROtherNonTaxRevenueEntity otherNonTaxRevenue, IARCRIncomeFromInterestEntity incomeFromInterest,
			IARCROtherRevenueIncomeEntity otherRevenueIncome, IARCRStateAssignedReveueEntity stateAssignedRevenue,
			IARCRStateFCGrantsEntity stateFCGrants, IARCROctraiCompensationEntity octraiCompensation,
			IARCROtherStateGovtTransfersEntity otherStateGovtTransfers, IARCRCFCGrantsEntity cfcGrants,
			IARCROtherCentralGovtTransfersEntity otherCentralGovtTransfers, IARCROthersEntity others) {
		IARevenueNCapitalReceiptsAndExpenditure receipts = new IARevenueNCapitalReceiptsAndExpenditure();
		receipts.setFy1Amt(propertyTax.getFy1Amt()
				.add(otherTax.getFy1Amt().add(feesAndFines.getFy1Amt()).add(userCharges.getFy1Amt())
						.add(otherNonTaxRevenue.getFy1Amt()).add(incomeFromInterest.getFy1Amt())
						.add(otherRevenueIncome.getFy1Amt()).add(stateAssignedRevenue.getFy1Amt())
						.add(stateFCGrants.getFy1Amt()).add(octraiCompensation.getFy1Amt())
						.add(otherStateGovtTransfers.getFy1Amt()).add(cfcGrants.getFy1Amt())
						.add(otherCentralGovtTransfers.getFy1Amt()).add(others.getFy1Amt())));
		receipts.setFy2Amt(propertyTax.getFy2Amt()
				.add(otherTax.getFy2Amt().add(feesAndFines.getFy2Amt()).add(userCharges.getFy2Amt())
						.add(otherNonTaxRevenue.getFy2Amt()).add(incomeFromInterest.getFy2Amt())
						.add(otherRevenueIncome.getFy2Amt()).add(stateAssignedRevenue.getFy2Amt())
						.add(stateFCGrants.getFy2Amt()).add(octraiCompensation.getFy2Amt())
						.add(otherStateGovtTransfers.getFy2Amt()).add(cfcGrants.getFy2Amt())
						.add(otherCentralGovtTransfers.getFy2Amt()).add(others.getFy2Amt())));
		receipts.setFy3Amt(propertyTax.getFy3Amt()
				.add(otherTax.getFy3Amt().add(feesAndFines.getFy3Amt()).add(userCharges.getFy3Amt())
						.add(otherNonTaxRevenue.getFy3Amt()).add(incomeFromInterest.getFy3Amt())
						.add(otherRevenueIncome.getFy3Amt()).add(stateAssignedRevenue.getFy3Amt())
						.add(stateFCGrants.getFy3Amt()).add(octraiCompensation.getFy3Amt())
						.add(otherStateGovtTransfers.getFy3Amt()).add(cfcGrants.getFy3Amt())
						.add(otherCentralGovtTransfers.getFy3Amt()).add(others.getFy3Amt())));
		receipts.setFy4Amt(propertyTax.getFy4Amt()
				.add(otherTax.getFy4Amt().add(feesAndFines.getFy4Amt()).add(userCharges.getFy4Amt())
						.add(otherNonTaxRevenue.getFy4Amt()).add(incomeFromInterest.getFy4Amt())
						.add(otherRevenueIncome.getFy4Amt()).add(stateAssignedRevenue.getFy4Amt())
						.add(stateFCGrants.getFy4Amt()).add(octraiCompensation.getFy4Amt())
						.add(otherStateGovtTransfers.getFy4Amt()).add(cfcGrants.getFy4Amt())
						.add(otherCentralGovtTransfers.getFy4Amt()).add(others.getFy4Amt())));
		receipts.setFy5Amt(propertyTax.getFy5Amt()
				.add(otherTax.getFy5Amt().add(feesAndFines.getFy5Amt()).add(userCharges.getFy5Amt())
						.add(otherNonTaxRevenue.getFy5Amt()).add(incomeFromInterest.getFy5Amt())
						.add(otherRevenueIncome.getFy5Amt()).add(stateAssignedRevenue.getFy5Amt())
						.add(stateFCGrants.getFy5Amt()).add(octraiCompensation.getFy5Amt())
						.add(otherStateGovtTransfers.getFy5Amt()).add(cfcGrants.getFy5Amt())
						.add(otherCentralGovtTransfers.getFy5Amt()).add(others.getFy5Amt())));
		receipts.setFy6Amt(propertyTax.getFy6Amt()
				.add(otherTax.getFy6Amt().add(feesAndFines.getFy6Amt()).add(userCharges.getFy6Amt())
						.add(otherNonTaxRevenue.getFy6Amt()).add(incomeFromInterest.getFy6Amt())
						.add(otherRevenueIncome.getFy6Amt()).add(stateAssignedRevenue.getFy6Amt())
						.add(stateFCGrants.getFy6Amt()).add(octraiCompensation.getFy6Amt())
						.add(otherStateGovtTransfers.getFy6Amt()).add(cfcGrants.getFy6Amt())
						.add(otherCentralGovtTransfers.getFy6Amt()).add(others.getFy6Amt())));

		return receipts;
	}

	private IARevenueNCapitalReceiptsAndExpenditure getOwnRevenueReceipts(IARCRPropertyTaxEntity propertyTax,
			IARCROtherTaxEntity otherTax, IARCRFeesNFinesEntity feesAndFines, IARCRUserChargesEntity userCharges,
			IARCROtherNonTaxRevenueEntity otherNonTaxRevenue) {
		IARevenueNCapitalReceiptsAndExpenditure receipts = new IARevenueNCapitalReceiptsAndExpenditure();
		receipts.setFy1Amt(propertyTax.getFy1Amt().add(otherTax.getFy1Amt().add(feesAndFines.getFy1Amt())
				.add(userCharges.getFy1Amt()).add(otherNonTaxRevenue.getFy1Amt())));
		receipts.setFy2Amt(propertyTax.getFy2Amt().add(otherTax.getFy2Amt().add(feesAndFines.getFy2Amt())
				.add(userCharges.getFy2Amt()).add(otherNonTaxRevenue.getFy2Amt())));
		receipts.setFy3Amt(propertyTax.getFy3Amt().add(otherTax.getFy3Amt().add(feesAndFines.getFy3Amt())
				.add(userCharges.getFy3Amt()).add(otherNonTaxRevenue.getFy3Amt())));
		receipts.setFy4Amt(propertyTax.getFy4Amt().add(otherTax.getFy4Amt().add(feesAndFines.getFy4Amt())
				.add(userCharges.getFy4Amt()).add(otherNonTaxRevenue.getFy4Amt())));
		receipts.setFy5Amt(propertyTax.getFy5Amt().add(otherTax.getFy5Amt().add(feesAndFines.getFy5Amt())
				.add(userCharges.getFy5Amt()).add(otherNonTaxRevenue.getFy5Amt())));
		receipts.setFy6Amt(propertyTax.getFy6Amt().add(otherTax.getFy6Amt().add(feesAndFines.getFy6Amt())
				.add(userCharges.getFy6Amt()).add(otherNonTaxRevenue.getFy6Amt())));

		return receipts;
	}

	private IARevenueNCapitalReceiptsAndExpenditure getNonTaxRevenueReceipts(IARCRFeesNFinesEntity feesAndFines,
			IARCRUserChargesEntity userCharges, IARCROtherNonTaxRevenueEntity otherNonTaxRevenue) {
		IARevenueNCapitalReceiptsAndExpenditure receipts = new IARevenueNCapitalReceiptsAndExpenditure();
		receipts.setFy1Amt(feesAndFines.getFy1Amt().add(userCharges.getFy1Amt()).add(otherNonTaxRevenue.getFy1Amt()));
		receipts.setFy2Amt(feesAndFines.getFy2Amt().add(userCharges.getFy2Amt()).add(otherNonTaxRevenue.getFy2Amt()));
		receipts.setFy3Amt(feesAndFines.getFy3Amt().add(userCharges.getFy3Amt()).add(otherNonTaxRevenue.getFy3Amt()));
		receipts.setFy4Amt(feesAndFines.getFy4Amt().add(userCharges.getFy4Amt()).add(otherNonTaxRevenue.getFy4Amt()));
		receipts.setFy5Amt(feesAndFines.getFy5Amt().add(userCharges.getFy5Amt()).add(otherNonTaxRevenue.getFy5Amt()));
		receipts.setFy6Amt(feesAndFines.getFy6Amt().add(userCharges.getFy6Amt()).add(otherNonTaxRevenue.getFy6Amt()));

		return receipts;
	}

	private IARevenueNCapitalReceiptsAndExpenditure getTaxRevenue(IARCRPropertyTaxEntity propertyTax,
			IARCROtherTaxEntity otherTax) {
		IARevenueNCapitalReceiptsAndExpenditure receipts = new IARevenueNCapitalReceiptsAndExpenditure();
		receipts.setFy1Amt(propertyTax.getFy1Amt().add(otherTax.getFy1Amt()));
		receipts.setFy2Amt(propertyTax.getFy2Amt().add(otherTax.getFy2Amt()));
		receipts.setFy3Amt(propertyTax.getFy3Amt().add(otherTax.getFy3Amt()));
		receipts.setFy4Amt(propertyTax.getFy4Amt().add(otherTax.getFy4Amt()));
		receipts.setFy5Amt(propertyTax.getFy5Amt().add(otherTax.getFy5Amt()));
		receipts.setFy6Amt(propertyTax.getFy6Amt().add(otherTax.getFy6Amt()));

		return receipts;
	}

	private BiFunction<DateTimeFormatter, LocalDate, String> parseToDDMMYYYY = (dtf, dateIn) -> dateIn.format(dtf);

}
