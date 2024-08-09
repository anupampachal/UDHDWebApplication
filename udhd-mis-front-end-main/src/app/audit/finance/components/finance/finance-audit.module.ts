import { CommonModule } from '@angular/common';
import { SharedModule } from 'src/app/shared/shared.module';
import { LOCALE_ID, NgModule } from "@angular/core";
import { FinanceAuditRouteModule } from "./finance-audit-routing.module";
import { FinanceAuditService } from "./finance-audit.service";
import { CreateFinanceAuditComponent } from "./create-finance-audit/create-finance-audit.component";
import { EditFinanceAuditComponent } from "./edit-finance-audit/edit-finance-audit.component";
import { ListFinanceAuditComponent } from "./list-finance-audit/list-finance-audit.component";
import { ViewFinanceAuditComponent } from "./view-finance-audit/view-finance-audit.component";
import { ReactiveFormsModule } from '@angular/forms';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule, MAT_DATE_LOCALE } from '@angular/material/core';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { CreateFinanceAuditCriteriaComponent } from './criterias/create-finance-audit-criteria/create-finance-audit-criteria.component';
import { EditFinanceAuditCriteriaComponent } from './criterias/edit-finance-audit-criteria/edit-finance-audit-criteria.component';
import { ListFinanceAuditCriteriaComponent } from './criterias/list-finance-audit-criteria/list-finance-audit-criteria.component';
import { ViewFinanceAuditCriteriaComponent } from './criterias/view-finance-audit-criteria/view-finance-audit-criteria.component';
import { FinanceAuditCriteriaService } from './criterias/criteria.service';
import { MatExpansionModule } from '@angular/material/expansion';
import { CreateFinanceAuditCompliancesComponent } from './criterias/compliances/create-finance-audit-compliances/create-finance-audit-compliances.component';
import { ListFinanceAuditCompliancesComponent } from './criterias/compliances/list-finance-audit-compliances/list-finance-audit-compliances.component';
import { ViewFinanceAuditCompliancesComponent } from './criterias/compliances/view-finance-audit-compliances/view-finance-audit-compliances.component';
import { EditFinanceAuditCompliancesComponent } from './criterias/compliances/edit-finance-audit-compliances/edit-finance-audit-compliances.component';
import { FinanceAuditComplianceService } from './criterias/compliances/compliance.service';
import { FinanceAuditCommentService } from './criterias/comments/comments.service';
import { CreateFinanceAuditCommentsComponent } from './criterias/comments/create-finance-audit-comments/create-finance-audit-comments.component';
import { EditFinanceAuditCommentsComponent } from './criterias/comments/edit-finance-audit-comments/edit-finance-audit-comments.component';
import { ListFinanceAuditCommentsComponent } from './criterias/comments/list-finance-audit-comments/list-finance-audit-comments.component';
import { ViewFinanceAuditCommentsComponent } from './criterias/comments/view-finance-audit-comments/view-finance-audit-comments.component';
import { MatRadioModule } from '@angular/material/radio';
import { FinanceAuditSummaryComponent } from './finance-audit-summary/finance-audit-summary.component';
@NgModule({
    declarations: [
        CreateFinanceAuditComponent,
        EditFinanceAuditComponent,
        ViewFinanceAuditComponent,
        ListFinanceAuditComponent,
        ListFinanceAuditCriteriaComponent,
        ViewFinanceAuditCriteriaComponent,
        EditFinanceAuditCriteriaComponent,
        CreateFinanceAuditCriteriaComponent,
        CreateFinanceAuditCompliancesComponent,
        ListFinanceAuditCompliancesComponent,
        ViewFinanceAuditCompliancesComponent,
        EditFinanceAuditCompliancesComponent,
        CreateFinanceAuditCommentsComponent,
        EditFinanceAuditCommentsComponent,
        ListFinanceAuditCommentsComponent,
        ViewFinanceAuditCommentsComponent,
        FinanceAuditSummaryComponent,
    ],
    imports: [
        FinanceAuditRouteModule,
        SharedModule,
        CommonModule,
        ReactiveFormsModule,
        MatSelectModule,
        MatDatepickerModule,
        MatNativeDateModule,
        MatFormFieldModule,
        MatInputModule,
        MatExpansionModule,
        MatRadioModule,

    ],
    providers: [
        FinanceAuditService,
        FinanceAuditCriteriaService,
        FinanceAuditComplianceService,
        FinanceAuditCommentService,
        { provide: MAT_DATE_LOCALE, useValue: 'en-GB' },
        { provide: LOCALE_ID, useValue: "en-GB" },
    ]
})
export class FinanceAuditModuleInner { }
