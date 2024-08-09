import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { CreateCagAuditCommentsComponent } from "./create-cag-audit-comments/create-cag-audit-comments.component";
import { EditCagAuditCommentsComponent } from "./edit-cag-audit-comments/edit-cag-audit-comments.component";
import { ListCagAuditCommentsComponent } from "./list-cag-audit-comments/list-cag-audit-comments.component";
import { ViewCagAuditCommentsComponent } from "./view-cag-audit-comments/view-cag-audit-comments.component";
const agIRRoutes: Routes = [
    {
        path: '',
        redirectTo: 'list',
        pathMatch: 'full'
    }, {
        path: 'list',
        component: ListCagAuditCommentsComponent
    }, {
        path: 'create',
        component: CreateCagAuditCommentsComponent
    }, {
        path: 'edit',
        component: EditCagAuditCommentsComponent
    }, {
        path: 'view',
        component: ViewCagAuditCommentsComponent
    }
];
@NgModule({
    imports: [RouterModule.forChild(agIRRoutes)],
    exports: [RouterModule]
})
export class AGCommentsRouteModule { }