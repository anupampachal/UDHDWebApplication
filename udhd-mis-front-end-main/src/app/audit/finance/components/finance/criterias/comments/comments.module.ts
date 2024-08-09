import { NgModule } from "@angular/core";
import { AGCommentsRouteModule } from "./comments-routing.module";
import { FinanceAuditCommentService } from "./comments.service";
// import { CreateFinanceAuditCommentsComponent } from "./create-finance-audit-comments/create-finance-audit-comments.component";
// import { EditFinanceAuditCommentsComponent } from "./edit-finance-audit-comments/edit-finance-audit-comments.component";
// import { ListFinanceAuditCommentsComponent } from "./list-finance-audit-comments/list-finance-audit-comments.component";
// import { ViewFinanceAuditCommentsComponent } from "./view-finance-audit-comments/view-finance-audit-comments.component";

@NgModule({
    declarations: [
        // CreateFinanceAuditCommentsComponent,
        // EditFinanceAuditCommentsComponent,
        // ListFinanceAuditCommentsComponent,
        // ViewFinanceAuditCommentsComponent,
    ],
    imports: [
        AGCommentsRouteModule
    ],
    providers: [
        FinanceAuditCommentService
    ]
})
export class FinanceAuditCommentsModule { }