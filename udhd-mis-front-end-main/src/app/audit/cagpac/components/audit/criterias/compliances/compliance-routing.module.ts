import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { CreateCagAuditCompliancesComponent } from "./create-cag-audit-compliances/create-cag-audit-compliances.component";
import { EditCagAuditCompliancesComponent } from "./edit-cag-audit-compliances/edit-cag-audit-compliances.component";
import { ListCagAuditCompliancesComponent } from "./list-cag-audit-compliances/list-cag-audit-compliances.component";
import { ViewCagAuditCompliancesComponent } from "./view-cag-audit-compliances/view-cag-audit-compliances.component";

const agIRRoutes: Routes = [
    {
        path: '',
        redirectTo: 'list',
        pathMatch: 'full'
    }, {
        path: 'list',
        component: ListCagAuditCompliancesComponent
    }, {
        path: 'create',
        component: CreateCagAuditCompliancesComponent
    }, {
        path: 'edit',
        component: EditCagAuditCompliancesComponent
    }, {
        path: 'view',
        component: ViewCagAuditCompliancesComponent
    }
];
@NgModule({
    imports: [RouterModule.forChild(agIRRoutes)],
    exports: [RouterModule]
})
export class AGCompliancesRouteModule { }