package com.scsinfinity.udhd.services.report.interfaces;

import org.springframework.core.io.Resource;

public interface ICAGAuditReportService {

	Resource downloadResource(Long agirId);

	public Resource getFile(String fileId);
}
