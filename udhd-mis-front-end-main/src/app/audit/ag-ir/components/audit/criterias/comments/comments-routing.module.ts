import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { CreateAgirAuditCommentsComponent } from "./create-agir-audit-comments/create-agir-audit-comments.component";
import { EditAgirAuditCommentsComponent } from "./edit-agir-audit-comments/edit-agir-audit-comments.component";
import { ListAgirAuditCommentsComponent } from "./list-agir-audit-comments/list-agir-audit-comments.component";
import { ViewAgirAuditCommentsComponent } from "./view-agir-audit-comments/view-agir-audit-comments.component";
const agIRRoutes: Routes = [
    {
        path: '',
        redirectTo: 'list',
        pathMatch: 'full'
    }, {
        path: 'list',
        component: ListAgirAuditCommentsComponent
    }, {
        path: 'create',
        component: CreateAgirAuditCommentsComponent
    }, {
        path: 'edit',
        component: EditAgirAuditCommentsComponent
    }, {
        path: 'view',
        component: ViewAgirAuditCommentsComponent
    }
];
@NgModule({
    imports: [RouterModule.forChild(agIRRoutes)],
    exports: [RouterModule]
})
export class AGCommentsRouteModule { }