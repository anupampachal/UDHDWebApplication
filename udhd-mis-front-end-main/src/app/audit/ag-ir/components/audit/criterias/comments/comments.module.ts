import { NgModule } from "@angular/core";
import { AGCommentsRouteModule } from "./comments-routing.module";
import { AGIRAuditCommentService } from "./comments.service";
// import { CreateAgirAuditCommentsComponent } from "./create-agir-audit-comments/create-agir-audit-comments.component";
// import { EditAgirAuditCommentsComponent } from "./edit-agir-audit-comments/edit-agir-audit-comments.component";
// import { ListAgirAuditCommentsComponent } from "./list-agir-audit-comments/list-agir-audit-comments.component";
// import { ViewAgirAuditCommentsComponent } from "./view-agir-audit-comments/view-agir-audit-comments.component";

@NgModule({
    declarations: [
        // CreateAgirAuditCommentsComponent,
        // EditAgirAuditCommentsComponent,
        // ListAgirAuditCommentsComponent,
        // ViewAgirAuditCommentsComponent,
    ],
    imports: [
        AGCommentsRouteModule
    ],
    providers: [
        AGIRAuditCommentService
    ]
})
export class AGIRAuditCommentsModule { }