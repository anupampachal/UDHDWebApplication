import { NgModule } from "@angular/core";
import { AGCompliancesRouteModule } from "./compliance-routing.module";
import { AGIRAuditComplianceService } from "./compliance.service";
import { CreateAgirAuditCompliancesComponent } from "./create-agir-audit-compliances/create-agir-audit-compliances.component";
import { EditAgirAuditCompliancesComponent } from "./edit-agir-audit-compliances/edit-agir-audit-compliances.component";
import { ListAgirAuditCompliancesComponent } from "./list-agir-audit-compliances/list-agir-audit-compliances.component";
import { ViewAgirAuditCompliancesComponent } from "./view-agir-audit-compliances/view-agir-audit-compliances.component";

@NgModule({
    declarations: [
        // ViewAgirAuditCompliancesComponent,
        // CreateAgirAuditCompliancesComponent,
        // EditAgirAuditCompliancesComponent,
        // ListAgirAuditCompliancesComponent
    ],
    imports: [
        AGCompliancesRouteModule
    ],
    providers: [
        AGIRAuditComplianceService
    ]
})
export class AGIRAuditCompliancesModule { }