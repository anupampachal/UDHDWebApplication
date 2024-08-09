import { NgModule } from "@angular/core";
import { AGCompliancesRouteModule } from "./compliance-routing.module";
import { CAGAuditComplianceService } from "./compliance.service";
import { CreateCagAuditCompliancesComponent } from "./create-cag-audit-compliances/create-cag-audit-compliances.component";
import { EditCagAuditCompliancesComponent } from "./edit-cag-audit-compliances/edit-cag-audit-compliances.component";
import { ListCagAuditCompliancesComponent } from "./list-cag-audit-compliances/list-cag-audit-compliances.component";
import { ViewCagAuditCompliancesComponent } from "./view-cag-audit-compliances/view-cag-audit-compliances.component";

@NgModule({
    declarations: [
        // ViewCagAuditCompliancesComponent,
        // CreateCagAuditCompliancesComponent,
        // EditCagAuditCompliancesComponent,
        // ListCagAuditCompliancesComponent
    ],
    imports: [
        AGCompliancesRouteModule
    ],
    providers: [
        CAGAuditComplianceService
    ]
})
export class CAGAuditCompliancesModule { }