import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { CreateFinanceAuditCompliancesComponent } from "./create-finance-audit-compliances/create-finance-audit-compliances.component";
import { EditFinanceAuditCompliancesComponent } from "./edit-finance-audit-compliances/edit-finance-audit-compliances.component";
import { ListFinanceAuditCompliancesComponent } from "./list-finance-audit-compliances/list-finance-audit-compliances.component";
import { ViewFinanceAuditCompliancesComponent } from "./view-finance-audit-compliances/view-finance-audit-compliances.component";

const FinanceRoutes: Routes = [
    {
        path: '',
        redirectTo: 'list',
        pathMatch: 'full'
    }, {
        path: 'list',
        component: ListFinanceAuditCompliancesComponent
    }, {
        path: 'create',
        component: CreateFinanceAuditCompliancesComponent
    }, {
        path: 'edit',
        component: EditFinanceAuditCompliancesComponent
    }, {
        path: 'view',
        component: ViewFinanceAuditCompliancesComponent
    }
];
@NgModule({
    imports: [RouterModule.forChild(FinanceRoutes)],
    exports: [RouterModule]
})
export class AGCompliancesRouteModule { }