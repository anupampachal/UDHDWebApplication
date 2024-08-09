import { AGIRAuditDashboardDTO } from "./agri-chart-dto";
import { CAGPACAuditDashboardDTO } from "./cag-pac-chart-dto";
import { DEASDashboardDTO } from "./deas-chart-dto";
import { IAAuditDashboardDTO } from "./ia-chart-dto";

export class DashboardDTO {
    agirDashboardDTO!: AGIRAuditDashboardDTO;
    cagPacAuditDashboardDTO!: CAGPACAuditDashboardDTO;
    iaAuditDashboardDTO!: IAAuditDashboardDTO;
    deasDashboardDTO!: DEASDashboardDTO;
}