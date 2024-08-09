package com.scsinfinity.udhd.services.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardDTO {

	private AGIRAuditDashboardDTO agirDashboardDTO;
	private CAGPACAuditDashboardDTO cagPacAuditDashboardDTO;
	private IAAuditDashboardDTO iaAuditDashboardDTO;
	private DEASDashboardDTO deasDashboardDTO;

}
