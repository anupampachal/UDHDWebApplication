import { CommonModule } from '@angular/common';
import { SharedModule } from 'src/app/shared/shared.module';
import { LOCALE_ID, NgModule } from "@angular/core";
import { AGIRAuditRouteModule } from "./agir-audit-routing.module";
import { AGIRAuditService } from "./agir-audit.service";
import { CreateAgirAuditComponent } from "./create-agir-audit/create-agir-audit.component";
import { EditAgirAuditComponent } from "./edit-agir-audit/edit-agir-audit.component";
import { ListAgirAuditComponent } from "./list-agir-audit/list-agir-audit.component";
import { ViewAgirAuditComponent } from "./view-agir-audit/view-agir-audit.component";
import { ReactiveFormsModule } from '@angular/forms';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule, MAT_DATE_LOCALE } from '@angular/material/core';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { CreateAgirAuditCriteriaComponent } from './criterias/create-agir-audit-criteria/create-agir-audit-criteria.component';
import { EditAgirAuditCriteriaComponent } from './criterias/edit-agir-audit-criteria/edit-agir-audit-criteria.component';
import { ListAgirAuditCriteriaComponent } from './criterias/list-agir-audit-criteria/list-agir-audit-criteria.component';
import { ViewAgirAuditCriteriaComponent } from './criterias/view-agir-audit-criteria/view-agir-audit-criteria.component';
import { AGIRAuditCriteriaService } from './criterias/criteria.service';
import { MatExpansionModule } from '@angular/material/expansion';
import { CreateAgirAuditCompliancesComponent } from './criterias/compliances/create-agir-audit-compliances/create-agir-audit-compliances.component';
import { ListAgirAuditCompliancesComponent } from './criterias/compliances/list-agir-audit-compliances/list-agir-audit-compliances.component';
import { ViewAgirAuditCompliancesComponent } from './criterias/compliances/view-agir-audit-compliances/view-agir-audit-compliances.component';
import { EditAgirAuditCompliancesComponent } from './criterias/compliances/edit-agir-audit-compliances/edit-agir-audit-compliances.component';
import { AGIRAuditComplianceService } from './criterias/compliances/compliance.service';
import { AGIRAuditCommentService } from './criterias/comments/comments.service';
import { CreateAgirAuditCommentsComponent } from './criterias/comments/create-agir-audit-comments/create-agir-audit-comments.component';
import { EditAgirAuditCommentsComponent } from './criterias/comments/edit-agir-audit-comments/edit-agir-audit-comments.component';
import { ListAgirAuditCommentsComponent } from './criterias/comments/list-agir-audit-comments/list-agir-audit-comments.component';
import { ViewAgirAuditCommentsComponent } from './criterias/comments/view-agir-audit-comments/view-agir-audit-comments.component';
import { MatRadioModule } from '@angular/material/radio';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';

import {MatSnackBarModule} from '@angular/material/snack-bar';
import { MatDialogModule } from '@angular/material/dialog';
import { ListUnassginedAgirAuditComponent } from './list-unassigned-agir-audit/list-unassigned-agir-audit.component';
import { ListAssignedToMeAGIRAudit } from './list-assigned-to-me-agir-audit/list-asssigned-to-me-agir-audit.component';
import { MatTooltipModule } from '@angular/material/tooltip';
@NgModule({
    declarations: [
        CreateAgirAuditComponent,
        EditAgirAuditComponent,
        ViewAgirAuditComponent,
        ListAgirAuditComponent,
        ListAgirAuditCriteriaComponent,
        ViewAgirAuditCriteriaComponent,
        EditAgirAuditCriteriaComponent,
        CreateAgirAuditCriteriaComponent,
        CreateAgirAuditCompliancesComponent,
        ListAgirAuditCompliancesComponent,
        ViewAgirAuditCompliancesComponent,
        EditAgirAuditCompliancesComponent,
        CreateAgirAuditCommentsComponent,
        EditAgirAuditCommentsComponent,
        ListAgirAuditCommentsComponent,
        ViewAgirAuditCommentsComponent,

        ListUnassginedAgirAuditComponent,
        ListAssignedToMeAGIRAudit
    ],
    imports: [
        AGIRAuditRouteModule,
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
        MatDialogModule,
        MatTooltipModule
    ],
    providers: [
        AGIRAuditService,
        AGIRAuditCriteriaService,
        AGIRAuditComplianceService,
        AGIRAuditCommentService,
        { provide: MAT_DATE_LOCALE, useValue: 'en-GB' },
        { provide: LOCALE_ID, useValue: "en-GB" },
    ]
})
export class AGIRAuditModuleInner { }
