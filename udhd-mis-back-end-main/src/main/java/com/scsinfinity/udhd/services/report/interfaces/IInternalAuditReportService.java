package com.scsinfinity.udhd.services.report.interfaces;

import org.springframework.core.io.Resource;

public interface IInternalAuditReportService {

	Resource downloadIaResource(Long iaId);

	Resource getFile(String fileId);
}
