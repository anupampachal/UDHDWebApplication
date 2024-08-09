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
import com.scsinfinity.udhd.dao.entities.audit.agir.AGIRAuditEntity;
import com.scsinfinity.udhd.dao.entities.audit.cag.CAGAuditEntity;
import com.scsinfinity.udhd.dao.entities.base.UserEntity;
import com.scsinfinity.udhd.dao.entities.storage.FileEntity;
import com.scsinfinity.udhd.dao.entities.storage.FolderEntity;
import com.scsinfinity.udhd.dao.entities.storage.FolderUserGroupsEnum;
import com.scsinfinity.udhd.dao.repositories.storage.IFileRepository;
import com.scsinfinity.udhd.services.audit.agir.interfaces.IAGIRAuditService;
import com.scsinfinity.udhd.services.audit.cag.interfaces.ICAGAuditService;
import com.scsinfinity.udhd.services.base.RandomGeneratorService;
import com.scsinfinity.udhd.services.base.interfaces.IFileService;
import com.scsinfinity.udhd.services.base.interfaces.IFolderService;
import com.scsinfinity.udhd.services.report.interfaces.ICAGAuditReportService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CAGAuditReportService implements ICAGAuditReportService {
	@Value("${scs.setting.folder.reports.nickname}")
	private String reportsFolderNickName;
	private final IFileService fileService;
	private final IFileRepository fileRepository;
	private final IFolderService folderService;
	private final SecuredUserInfoService securedUser;
	private final ICAGAuditService cagAuditService;
	private final RandomGeneratorService randomGeneratorService;

	@Override
	public Resource downloadResource(Long agirId) {
		FolderEntity folder = getFolder();
		OutputStream outputStream;
		try {
			String fileName = "File_" + randomGeneratorService.generateRandomNumberForFilename() + ".pdf";
			outputStream = new FileOutputStream(folder.getPathToCurrentDir() + File.separator + fileName);
			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocumentFromString(parseThymeLeaf(agirId));
			renderer.layout();
			renderer.createPDF(outputStream);
			outputStream.close();

			FileEntity file = fileSave(fileName, folder);
			
			return fileService.getFile(file.getFileId());

		} catch (FileNotFoundException e) {
			System.out.println("**********************************");
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^*^^^^^^^^^^^^^^^^");
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
		CAGAuditEntity agir = cagAuditService.findAuditEntityById(iaId);

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		context.setVariable("nameOfMunicipality", agir.getAuditEntity().getUlb().getName());
		context.setVariable("auditStartDate", parseToDDMMYYYY.apply(dtf, agir.getAuditEntity().getStartDate()));
		context.setVariable("auditEndDate", parseToDDMMYYYY.apply(dtf, agir.getAuditEntity().getEndDate()));
		context.setVariable("auditEntity", agir.getAuditEntity());
		context.setVariable("agirAuditEntity", agir);
		return templateEngine.process("thymeleaf_cag_audit", context);
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

	private BiFunction<DateTimeFormatter, LocalDate, String> parseToDDMMYYYY = (dtf, dateIn) -> dateIn.format(dtf);

}
