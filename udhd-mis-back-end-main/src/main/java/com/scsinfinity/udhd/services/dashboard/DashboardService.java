package com.scsinfinity.udhd.services.dashboard;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

import com.scsinfinity.udhd.services.audit.agir.interfaces.IAGIRAuditService;
import com.scsinfinity.udhd.services.audit.cag.interfaces.ICAGAuditService;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IInternalAuditService;
import com.scsinfinity.udhd.services.dashboard.dto.AGIRAuditDashboardDTO;
import com.scsinfinity.udhd.services.dashboard.dto.CAGPACAuditDashboardDTO;
import com.scsinfinity.udhd.services.dashboard.dto.DEASDashboardDTO;
import com.scsinfinity.udhd.services.dashboard.dto.DashboardDTO;
import com.scsinfinity.udhd.services.dashboard.dto.IAAuditDashboardDTO;
import com.scsinfinity.udhd.services.dashboard.interfaces.IDashboardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService implements IDashboardService {
	private final IAGIRAuditService agirAudit;
	private final ICAGAuditService cagAudit;

	private final IInternalAuditService iaService;

	@Override
	public DashboardDTO getDashboardData() {
		LocalDate startDate = LocalDate.now().minus(1, ChronoUnit.YEARS);
		LocalDate endDate = LocalDate.now();

		LocalDate prevStartDate = LocalDate.now().minus(2, ChronoUnit.YEARS);
		LocalDate prevEndDate = LocalDate.now().minus(1, ChronoUnit.YEARS);

		return DashboardDTO.builder()
				.agirDashboardDTO(getAgirDashboardDTOData(startDate, endDate, prevStartDate, prevEndDate))
				.cagPacAuditDashboardDTO(getCAGPACDashboardDTOData(startDate, endDate, prevStartDate, prevEndDate))
				.iaAuditDashboardDTO(getIADashboardDTOData(startDate, endDate, prevStartDate, prevEndDate))
				.deasDashboardDTO(deasDashboardDTOData(startDate, endDate, prevStartDate, prevEndDate)).build();
	}

	private AGIRAuditDashboardDTO getAgirDashboardDTOData(LocalDate startDate, LocalDate endDate,
			LocalDate prevStartDate, LocalDate prevEndDate) {
		Long agirAuditBetweenDatesCurrent = agirAudit.getTotalAGIRDuring(startDate, endDate);
		Long agirAuditBetweenDatesPrevious = agirAudit.getTotalAGIRDuring(prevStartDate, prevEndDate);
		return AGIRAuditDashboardDTO.builder().currentData(agirAuditBetweenDatesCurrent)
				.currentPeriod(getLocalDateToDDMMYYYY(startDate).concat("-").concat(getLocalDateToDDMMYYYY(endDate)))
				.previousData(agirAuditBetweenDatesPrevious)
				.previousPeriod(
						getLocalDateToDDMMYYYY(prevStartDate).concat("-").concat(getLocalDateToDDMMYYYY(prevEndDate)))
				.build();
	}

	private DEASDashboardDTO deasDashboardDTOData(LocalDate startDate, LocalDate endDate, LocalDate prevStartDate,
			LocalDate prevEndDate) {
		Long agirAuditBetweenDatesCurrent = cagAudit.getTotalCAGDuring(startDate, endDate);
		Long agirAuditBetweenDatesPrevious = cagAudit.getTotalCAGDuring(prevStartDate, prevEndDate);
		return DEASDashboardDTO.builder().currentData(agirAuditBetweenDatesCurrent)
				.currentPeriod(getLocalDateToDDMMYYYY(startDate).concat("-").concat(getLocalDateToDDMMYYYY(endDate)))
				.previousData(agirAuditBetweenDatesPrevious)
				.previousPeriod(
						getLocalDateToDDMMYYYY(prevStartDate).concat("-").concat(getLocalDateToDDMMYYYY(prevEndDate)))
				.build();
	}

	private CAGPACAuditDashboardDTO getCAGPACDashboardDTOData(LocalDate startDate, LocalDate endDate,
			LocalDate prevStartDate, LocalDate prevEndDate) {
		Long agirAuditBetweenDatesCurrent = cagAudit.getTotalCAGDuring(startDate, endDate);
		Long agirAuditBetweenDatesPrevious = cagAudit.getTotalCAGDuring(prevStartDate, prevEndDate);
		return CAGPACAuditDashboardDTO.builder().currentData(agirAuditBetweenDatesCurrent)
				.currentPeriod(getLocalDateToDDMMYYYY(startDate).concat("-").concat(getLocalDateToDDMMYYYY(endDate)))
				.previousData(agirAuditBetweenDatesPrevious)
				.previousPeriod(
						getLocalDateToDDMMYYYY(prevStartDate).concat("-").concat(getLocalDateToDDMMYYYY(prevEndDate)))
				.build();
	}

	private IAAuditDashboardDTO getIADashboardDTOData(LocalDate startDate, LocalDate endDate, LocalDate prevStartDate,
			LocalDate prevEndDate) {
		Long agirAuditBetweenDatesCurrent = iaService.getTotalIADuring(startDate, endDate);
		Long agirAuditBetweenDatesPrevious = iaService.getTotalIADuring(prevStartDate, prevEndDate);
		return IAAuditDashboardDTO.builder().currentData(agirAuditBetweenDatesCurrent)
				.currentPeriod(getLocalDateToDDMMYYYY(startDate).concat("-").concat(getLocalDateToDDMMYYYY(endDate)))
				.previousData(agirAuditBetweenDatesPrevious)
				.previousPeriod(
						getLocalDateToDDMMYYYY(prevStartDate).concat("-").concat(getLocalDateToDDMMYYYY(prevEndDate)))
				.build();
	}

	private String getLocalDateToDDMMYYYY(LocalDate date) {
		return date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear();
	}

}
