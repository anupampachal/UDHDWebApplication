import { NgModule } from "@angular/core";
import { AGCommentsRouteModule } from "./comments-routing.module";
import { CAGAuditCommentService } from "./comments.service";
import { MatCardModule } from "@angular/material/card";
// import { CreateAgirAuditCommentsComponent } from "./create-cag-audit-comments/create-cag-audit-comments.component";
// import { EditAgirAuditCommentsComponent } from "./edit-cag-audit-comments/edit-cag-audit-comments.component";
// import { ListAgirAuditCommentsComponent } from "./list-cag-audit-comments/list-cag-audit-comments.component";
// import { ViewAgirAuditCommentsComponent } from "./view-cag-audit-comments/view-cag-audit-comments.component";

@NgModule({
    declarations: [
        // CreateAgirAuditCommentsComponent,
        // EditAgirAuditCommentsComponent,
        // ListAgirAuditCommentsComponent,
        // ViewAgirAuditCommentsComponent,
    ],
    imports: [
        AGCommentsRouteModule,¸
    ],
    providers: [
        CAGAuditCommentService
    ]
})
export class CAGAuditCommentsModule { }