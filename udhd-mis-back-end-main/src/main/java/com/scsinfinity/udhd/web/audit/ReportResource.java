package com.scsinfinity.udhd.web.audit;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scsinfinity.udhd.services.report.interfaces.IAGIRAuditReportService;
import com.scsinfinity.udhd.services.report.interfaces.ICAGAuditReportService;
import com.scsinfinity.udhd.services.report.interfaces.IInternalAuditReportService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/audit/report")
public class ReportResource {

	private final IInternalAuditReportService internalAuditReportService;
	private final IAGIRAuditReportService agirReportService;
	private final ICAGAuditReportService cagAuditReportService;

	@GetMapping("/ia/{iaId}")
	public ResponseEntity<Resource> getIaPDFReportDownload(@PathVariable Long iaId) {
		log.debug("getIaPDFReportDownload :", iaId);
		Resource file = internalAuditReportService.downloadIaResource(iaId);
		try {
			return ResponseEntity.ok().contentLength(file.contentLength())
					.header("Content-Disposition", "attachment; filename=" + file.getFilename())
					.contentType(MediaType.APPLICATION_OCTET_STREAM).body(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping("/agir/{id}")
	public ResponseEntity<Resource> getAgirPDFReportDownload(@PathVariable Long id) {
		log.debug("agirReportService :", id);
		Resource file = agirReportService.downloadResource(id);
		try {
			return ResponseEntity.ok().contentLength(file.contentLength())
					.header("Content-Disposition", "attachment; filename=" + file.getFilename())
					.contentType(MediaType.APPLICATION_OCTET_STREAM).body(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping("/agir/file/{fileId}")
	public ResponseEntity<Resource> getAgirPDFReportDownloadFile(@PathVariable String fileId) {
		log.debug("agirReportService :", fileId);
		Resource file = agirReportService.getFile(fileId);
		try {
			/*ResponseEntity.ok().contentLength(file.contentLength())
					.header("Content-Disposition", "attachment; filename=" + file.getFilename())
					.contentType(MediaType.APPLICATION_PDF).body(file);*/
			return ResponseEntity.ok().contentLength(file.contentLength())
					.header("Content-Disposition", "attachment; filename=" + file.getFilename())
					.contentType(MediaType.APPLICATION_OCTET_STREAM).body(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping("/cag/{id}")
	public ResponseEntity<Resource> getCAGPDFReportDownload(@PathVariable Long id) {
		log.debug("agirReportService :", id);
		Resource file = cagAuditReportService.downloadResource(id);
		try {
			return ResponseEntity.ok().contentLength(file.contentLength())
					.header("Content-Disposition", "attachment; filename=" + file.getFilename())
					.contentType(MediaType.APPLICATION_OCTET_STREAM).body(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping("/cag/file/{fileId}")
	public ResponseEntity<Resource> getCAGPDFReportDownloadFile(@PathVariable String fileId) {
		log.debug("agirReportService :", fileId);
		Resource file = cagAuditReportService.getFile(fileId);
		try {
			/*ResponseEntity.ok().contentLength(file.contentLength())
					.header("Content-Disposition", "attachment; filename=" + file.getFilename())
					.contentType(MediaType.APPLICATION_PDF).body(file);*/
			return ResponseEntity.ok().contentLength(file.contentLength())
					.header("Content-Disposition", "attachment; filename=" + file.getFilename())
					.contentType(MediaType.APPLICATION_OCTET_STREAM).body(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
