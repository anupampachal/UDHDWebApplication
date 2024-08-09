import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { CreateFinanceAuditCriteriaComponent } from "./create-finance-audit-criteria/create-finance-audit-criteria.component";
import { EditFinanceAuditCriteriaComponent } from "./edit-finance-audit-criteria/edit-finance-audit-criteria.component";
import { ListFinanceAuditCriteriaComponent } from "./list-finance-audit-criteria/list-finance-audit-criteria.component";
import { ViewFinanceAuditCriteriaComponent } from "./view-finance-audit-criteria/view-finance-audit-criteria.component";

const FinanceRoutes: Routes = [
    {
        path: '',
        redirectTo: 'list',
        pathMatch: 'full'
    }, {
        path: 'list',
        component: ListFinanceAuditCriteriaComponent
    }, {
        path: 'create',
        component: CreateFinanceAuditCriteriaComponent
    }, {
        path: 'edit',
        component: EditFinanceAuditCriteriaComponent
    }, {
        path: 'view',
        component: ViewFinanceAuditCriteriaComponent
    }
];
@NgModule({
    imports: [RouterModule.forChild(FinanceRoutes)],
    exports: [RouterModule]
})
export class AGCriteriaRouteModule { }