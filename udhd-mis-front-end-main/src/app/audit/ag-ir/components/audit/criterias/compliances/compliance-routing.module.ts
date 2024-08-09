import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { CreateAgirAuditCompliancesComponent } from "./create-agir-audit-compliances/create-agir-audit-compliances.component";
import { EditAgirAuditCompliancesComponent } from "./edit-agir-audit-compliances/edit-agir-audit-compliances.component";
import { ListAgirAuditCompliancesComponent } from "./list-agir-audit-compliances/list-agir-audit-compliances.component";
import { ViewAgirAuditCompliancesComponent } from "./view-agir-audit-compliances/view-agir-audit-compliances.component";

const agIRRoutes: Routes = [
    {
        path: '',
        redirectTo: 'list',
        pathMatch: 'full'
    }, {
        path: 'list',
        component: ListAgirAuditCompliancesComponent
    }, {
        path: 'create',
        component: CreateAgirAuditCompliancesComponent
    }, {
        path: 'edit',
        component: EditAgirAuditCompliancesComponent
    }, {
        path: 'view',
        component: ViewAgirAuditCompliancesComponent
    }
];
@NgModule({
    imports: [RouterModule.forChild(agIRRoutes)],
    exports: [RouterModule]
})
export class AGCompliancesRouteModule { }