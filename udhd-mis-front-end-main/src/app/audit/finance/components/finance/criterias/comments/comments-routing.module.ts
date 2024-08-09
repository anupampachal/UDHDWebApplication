import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { CreateFinanceAuditCommentsComponent } from "./create-finance-audit-comments/create-finance-audit-comments.component";
import { EditFinanceAuditCommentsComponent } from "./edit-finance-audit-comments/edit-finance-audit-comments.component";
import { ListFinanceAuditCommentsComponent } from "./list-finance-audit-comments/list-finance-audit-comments.component";
import { ViewFinanceAuditCommentsComponent } from "./view-finance-audit-comments/view-finance-audit-comments.component";
const FinanceRoutes: Routes = [
    {
        path: '',
        redirectTo: 'list',
        pathMatch: 'full'
    }, {
        path: 'list',
        component: ListFinanceAuditCommentsComponent
    }, {
        path: 'create',
        component: CreateFinanceAuditCommentsComponent
    }, {
        path: 'edit',
        component: EditFinanceAuditCommentsComponent
    }, {
        path: 'view',
        component: ViewFinanceAuditCommentsComponent
    }
];
@NgModule({
    imports: [RouterModule.forChild(FinanceRoutes)],
    exports: [RouterModule]
})
export class AGCommentsRouteModule { }