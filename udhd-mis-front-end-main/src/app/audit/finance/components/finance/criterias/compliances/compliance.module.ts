import { NgModule } from "@angular/core";
import { AGCompliancesRouteModule } from "./compliance-routing.module";
import { FinanceAuditComplianceService } from "./compliance.service";
import { CreateFinanceAuditCompliancesComponent } from "./create-finance-audit-compliances/create-finance-audit-compliances.component";
import { EditFinanceAuditCompliancesComponent } from "./edit-finance-audit-compliances/edit-finance-audit-compliances.component";
import { ListFinanceAuditCompliancesComponent } from "./list-finance-audit-compliances/list-finance-audit-compliances.component";
import { ViewFinanceAuditCompliancesComponent } from "./view-finance-audit-compliances/view-finance-audit-compliances.component";

@NgModule({
    declarations: [
        // ViewFinanceAuditCompliancesComponent,
        // CreateFinanceAuditCompliancesComponent,
        // EditFinanceAuditCompliancesComponent,
        // ListFinanceAuditCompliancesComponent
    ],
    imports: [
        AGCompliancesRouteModule
    ],
    providers: [
        FinanceAuditComplianceService
    ]
})
export class FinanceAuditCompliancesModule { }