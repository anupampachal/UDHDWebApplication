import { CommonModule } from '@angular/common';
import { SharedModule } from 'src/app/shared/shared.module';
import { LOCALE_ID, NgModule } from "@angular/core";
import { CAGAuditRouteModule } from "./cag-audit-routing.module";
import { CAGAuditService } from "./cag-audit.service";
import { CreateCagAuditComponent } from "./create-cag-audit/create-cag-audit.component";
import { EditCagAuditComponent } from "./edit-cag-audit/edit-cag-audit.component";
import { ListCagAuditComponent } from "./list-cag-audit/list-cag-audit.component";
import { ViewCagAuditComponent } from "./view-cag-audit/view-cag-audit.component";
import { ReactiveFormsModule } from '@angular/forms';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule, MAT_DATE_LOCALE } from '@angular/material/core';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { CreateCagAuditCriteriaComponent } from './criterias/create-cag-audit-criteria/create-cag-audit-criteria.component';

import { ListCagAuditCriteriaComponent } from './criterias/list-cag-audit-criteria/list-cag-audit-criteria.component';
import { ViewCagAuditCriteriaComponent } from './criterias/view-cag-audit-criteria/view-cag-audit-criteria.component';
import { CAGAuditCriteriaService } from './criterias/criteria.service';
import { MatExpansionModule } from '@angular/material/expansion';

import { ListCagAuditCompliancesComponent } from './criterias/compliances/list-cag-audit-compliances/list-cag-audit-compliances.component';
import { ViewCagAuditCompliancesComponent } from './criterias/compliances/view-cag-audit-compliances/view-cag-audit-compliances.component';
import { EditCagAuditCompliancesComponent } from './criterias/compliances/edit-cag-audit-compliances/edit-cag-audit-compliances.component';
import { CAGAuditComplianceService } from './criterias/compliances/compliance.service';
import { CAGAuditCommentService } from './criterias/comments/comments.service';
import { CreateCagAuditCommentsComponent } from './criterias/comments/create-cag-audit-comments/create-cag-audit-comments.component';
import { EditCagAuditCommentsComponent } from './criterias/comments/edit-cag-audit-comments/edit-cag-audit-comments.component';
import { ListCagAuditCommentsComponent } from './criterias/comments/list-cag-audit-comments/list-cag-audit-comments.component';
import { ViewCagAuditCommentsComponent } from './criterias/comments/view-cag-audit-comments/view-cag-audit-comments.component';
import { MatRadioModule } from '@angular/material/radio';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import { CagAuditSummaryComponent } from './cag-audit-summary/cag-audit-summary.component';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import { MatDialogModule } from '@angular/material/dialog';
import { ListUnassginedCagAuditComponent } from './list-unassigned-cag-audit/list-unassigned-cag-audit.component';
import { ListAssignedToMeCAGAudit } from './list-assigned-to-me-cag-audit/list-asssigned-to-me-cag-audit.component';
import { EditCagAuditCriteriaComponent } from './criterias/edit-cag-audit-criteria/edit-cag-audit-criteria.component';
import { CreateCagAuditCompliancesComponent } from './criterias/compliances/create-cag-audit-compliances/create-cag-audit-compliances.component';
@NgModule({
    declarations: [
        CreateCagAuditComponent,
        EditCagAuditComponent,
        ViewCagAuditComponent,
        ListCagAuditComponent,
        ListCagAuditCriteriaComponent,
        ViewCagAuditCriteriaComponent,
        EditCagAuditCriteriaComponent,
        CreateCagAuditCriteriaComponent,
        CreateCagAuditCompliancesComponent,
        ListCagAuditCompliancesComponent,
        ViewCagAuditCompliancesComponent,
        EditCagAuditCompliancesComponent,
        CreateCagAuditCommentsComponent,
        EditCagAuditCommentsComponent,
        ListCagAuditCommentsComponent,
        ViewCagAuditCommentsComponent,
        CagAuditSummaryComponent,
        ListUnassginedCagAuditComponent,
        ListAssignedToMeCAGAudit
    ],
    imports: [
        CAGAuditRouteModule,
        
        SharedModule,
        CommonModule,
        ReactiveFormsModule,
        MatSelectModule,
        MatNativeDateModule,
        MatDatepickerModule,
        MatFormFieldModule,
        MatInputModule,
        MatExpansionModule,
        MatRadioModule,
        MatSlideToggleModule,
        MatSnackBarModule,
        MatDialogModule
    ],
    providers: [
        CAGAuditService,
        CAGAuditCriteriaService,
        CAGAuditComplianceService,
        CAGAuditCommentService,
        { provide: MAT_DATE_LOCALE, useValue: 'en-GB' },
        { provide: LOCALE_ID, useValue: "en-GB" },
    ]
})
export class CAGAuditModuleInner { }
