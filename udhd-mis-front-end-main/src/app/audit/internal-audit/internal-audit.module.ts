import { NgModule } from "@angular/core";
import { CreateIaComponent } from "./create-ia/create-ia.component";
import { EditIaComponent } from './edit-ia/edit-ia.component';
import { ViewIaComponent } from './view-ia/view-ia.component';
import { ListIaComponent } from './list-ia/list-ia.component';
import { InternalAuditRouteModule } from "./internal-audit-routing.module";
import { InternalAuditComponent } from "./internal-audit.component";
import { MatStepperModule } from '@angular/material/stepper';
import { Step1ReportComponent } from './create-ia/step1-report/step1-report/step1-report.component';
import { Step2ExecutiveSummaryComponent } from './create-ia/step2-executive-summary/step2-executive-summary/step2-executive-summary.component';
import { Step3DetailedAuditComponent } from './create-ia/step3-detailed-audit/step3-detailed-audit/step3-detailed-audit.component';
import { Step4ReviewOfOutstandingParasComponent } from './create-ia/step4-review-of-outstanding-paras/step4-review-of-outstanding-paras/step4-review-of-outstanding-paras.component';
import { Step5FinanceComponent } from './create-ia/step5-finance/step5-finance/step5-finance.component';
import { Step6AuditObservationComponent } from './create-ia/step6-audit-observation/step6-audit-observation/step6-audit-observation.component';
import { MatCardModule } from "@angular/material/card";
import { MatIconModule } from "@angular/material/icon";
import { SharedModule } from "src/app/shared/shared.module";
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule, MAT_DATE_LOCALE } from '@angular/material/core';

import { ReactiveFormsModule } from "@angular/forms";
import { IaStep2IntroductionComponent } from './create-ia/step2-executive-summary/step2-executive-summary/ia-step2-introduction/ia-step2-introduction.component';
import { IaStep2ResultsnfindingsStrengthComponent } from './create-ia/step2-executive-summary/step2-executive-summary/ia-step2-resultsnfindings-strength/ia-step2-resultsnfindings-strength.component';
import { IaStep2ResultsnfindingsWeaknessComponent } from './create-ia/step2-executive-summary/step2-executive-summary/ia-step2-resultsnfindings-weakness/ia-step2-resultsnfindings-weakness.component';
import { IaStep2OpinionComponent } from './create-ia/step2-executive-summary/step2-executive-summary/ia-step2-opinion/ia-step2-opinion.component';
import { IaStep2AuditRecommendationComponent } from './create-ia/step2-executive-summary/step2-executive-summary/ia-step2-audit-recommendation/ia-step2-audit-recommendation.component';
import { IaStep2CommentFromManagementComponent } from './create-ia/step2-executive-summary/step2-executive-summary/ia-step2-comment-from-management/ia-step2-comment-from-management.component';
import { IaStep2AcknowledgementComponent } from './create-ia/step2-executive-summary/step2-executive-summary/ia-step2-acknowledgement/ia-step2-acknowledgement.component';
import { CommonModule } from "@angular/common";
import { BudgetaryprovisionAndExpenditureComponent } from "./create-ia/step5-finance/step5-finance/budgetaryprovision-and-expenditure/budgetaryprovision-and-expenditure.component";
import { AuditObservationStatusComponent } from "./create-ia/step4-review-of-outstanding-paras/step4-review-of-outstanding-paras/audit-observation-status/audit-observation-status.component";
import { AuditParaDetailsComponent } from "./create-ia/step4-review-of-outstanding-paras/step4-review-of-outstanding-paras/audit-para-details/audit-para-details.component";
import { VolumeOfTransactionComponent } from "./create-ia/step5-finance/step5-finance/volume-of-transaction/volume-of-transaction.component";
import { BankReconcilationComponent } from './create-ia/step5-finance/step5-finance/bank-reconcilation/bank-reconcilation.component';
import { RevenueAndCapitalReceiptsComponent } from './create-ia/step5-finance/step5-finance/revenue-and-capital-receipts/revenue-and-capital-receipts.component';
import { MunicipalAccountCommitteeStatusComponent } from './create-ia/step5-finance/step5-finance/municipal-account-committee-status/municipal-account-committee-status.component';
import { DeasImplementationStatusComponent } from './create-ia/step5-finance/step5-finance/deas-implementation-status/deas-implementation-status.component';
import { ViewStep1ReportComponent } from './view-ia/view-step1-report/view-step1-report.component';
import { ViewStep2ExecutiveSummaryComponent } from './view-ia/view-step2-executive-summary/view-step2-executive-summary.component';
import { ViewStep3DetailedAuditComponent } from './view-ia/view-step3-detailed-audit/view-step3-detailed-audit.component';
import { ViewStep4OutstandingParasReviewComponent } from './view-ia/view-step4-outstanding-paras-review/view-step4-outstanding-paras-review.component';
import { ViewStep5FinanceComponent } from './view-ia/view-step5-finance/view-step5-finance.component';
import { ViewStep6AuditObservationComponent } from './view-ia/view-step6-audit-observation/view-step6-audit-observation.component';
import { InternalAuditService } from "./internal-audit-service.service";
import { RevenueAndCapitalExpenditureComponent } from './create-ia/step5-finance/step5-finance/revenue-and-capital-expenditure/revenue-and-capital-expenditure.component';

import { EditStep5FinanceComponent } from './edit-ia/step5-finance/step5-finance/step5-finance.component';
import { EditStep1ReportComponent } from "./edit-ia/step1-report/step1-report/step1-report.component";
import { EditStep2ExecutiveSummaryComponent } from "./edit-ia/step2-executive-summary/step2-executive-summary/step2-executive-summary.component";
import { EditStep3DetailedAuditComponent } from "./edit-ia/step3-detailed-audit/step3-detailed-audit/step3-detailed-audit.component";
import { EditStep4ReviewOfOutstandingParasComponent } from "./edit-ia/step4-review-of-outstanding-paras/step4-review-of-outstanding-paras/step4-review-of-outstanding-paras.component";
import { EditBankReconcilationComponent } from "./edit-ia/step5-finance/step5-finance/bank-reconcilation/bank-reconcilation.component";
import { EditVolumeOfTransactionComponent } from "./edit-ia/step5-finance/step5-finance/volume-of-transaction/volume-of-transaction.component";
import { EditRevenueAndCapitalExpenditureComponent } from "./edit-ia/step5-finance/step5-finance/revenue-and-capital-expenditure/revenue-and-capital-expenditure.component";
import { EditRevenueAndCapitalReceiptsComponent } from "./edit-ia/step5-finance/step5-finance/revenue-and-capital-receipts/revenue-and-capital-receipts.component";
import { EditMunicipalAccountCommitteeStatusComponent } from "./edit-ia/step5-finance/step5-finance/municipal-account-committee-status/municipal-account-committee-status.component";
import { EditDeasImplementationStatusComponent } from "./edit-ia/step5-finance/step5-finance/deas-implementation-status/deas-implementation-status.component";
import { EditBudgetaryprovisionAndExpenditureComponent } from "./edit-ia/step5-finance/step5-finance/budgetaryprovision-and-expenditure/budgetaryprovision-and-expenditure.component";
import { EditIaStep2ResultsnfindingsWeaknessComponent } from "./edit-ia/step2-executive-summary/step2-executive-summary/ia-step2-resultsnfindings-weakness/ia-step2-resultsnfindings-weakness.component";
import { EditIaStep2AcknowledgementComponent } from "./edit-ia/step2-executive-summary/step2-executive-summary/ia-step2-acknowledgement/ia-step2-acknowledgement.component";
import { EditIaStep2AuditRecommendationComponent } from "./edit-ia/step2-executive-summary/step2-executive-summary/ia-step2-audit-recommendation/ia-step2-audit-recommendation.component";
import { EditIaStep2CommentFromManagementComponent } from "./edit-ia/step2-executive-summary/step2-executive-summary/ia-step2-comment-from-management/ia-step2-comment-from-management.component";
import { EditIaStep2IntroductionComponent } from "./edit-ia/step2-executive-summary/step2-executive-summary/ia-step2-introduction/ia-step2-introduction.component";
import { EditIaStep2OpinionComponent } from "./edit-ia/step2-executive-summary/step2-executive-summary/ia-step2-opinion/ia-step2-opinion.component";
import { EditIaStep2ResultsnfindingsStrengthComponent } from "./edit-ia/step2-executive-summary/step2-executive-summary/ia-step2-resultsnfindings-strength/ia-step2-resultsnfindings-strength.component";
import { EditAuditObservationStatusComponent } from "./edit-ia/step4-review-of-outstanding-paras/step4-review-of-outstanding-paras/audit-observation-status/audit-observation-status.component";
import { EditAuditParaDetailsComponent } from "./edit-ia/step4-review-of-outstanding-paras/step4-review-of-outstanding-paras/audit-para-details/audit-para-details.component";
import { EditStep6AuditObservationComponent } from "./edit-ia/step6-audit-observation/step6-audit-observation/step6-audit-observation.component";
// import { EditAddPartAObjectiveComponent } from './edit-ia/step6-audit-observation/step6-audit-observation/add-part-aobjective/add-part-aobjective.component';
import { AddPartAobjectiveComponent } from './create-ia/step6-audit-observation/step6-audit-observation/add-part-aobjective/add-part-aobjective.component';
import { AddObjectiveComponent } from './create-ia/step6-audit-observation/step6-audit-observation/add-objective/add-objective.component';
import { AddPartBDescriptionComponent } from './create-ia/step6-audit-observation/step6-audit-observation/add-part-bdescription/add-part-bdescription.component';
import { AddPartbFComponent } from './create-ia/step6-audit-observation/step6-audit-observation/add-partb-f/add-partb-f.component';
import { AddPartBHComponent } from './create-ia/step6-audit-observation/step6-audit-observation/add-part-bh/add-part-bh.component';
import { AddPartCdescriptionComponent } from './create-ia/step6-audit-observation/step6-audit-observation/add-part-cdescription/add-part-cdescription.component';
import { EditPartAobjectiveComponent } from "./edit-ia/step6-audit-observation/step6-audit-observation/edit-part-aobjective/edit-part-aobjective.component";
import { EditObjectiveComponent } from "./edit-ia/step6-audit-observation/step6-audit-observation/edit-objective/edit-objective.component";
import { EditPartBDescriptionComponent } from "./edit-ia/step6-audit-observation/step6-audit-observation/edit-part-bdescription/edit-part-bdescription.component";
import { EditPartBHComponent } from "./edit-ia/step6-audit-observation/step6-audit-observation/edit-part-bh/edit-part-bh.component";
import { EditPartCdescriptionComponent } from "./edit-ia/step6-audit-observation/step6-audit-observation/edit-part-cdescription/edit-part-cdescription.component";
import { EditPartbFComponent } from "./edit-ia/step6-audit-observation/step6-audit-observation/edit-partb-f/edit-partb-f.component";
import { Step1ReportService } from "./services/step-1-report.service";
import { Step2Service } from "./services/step-2.service";
import { Step3Service } from "./services/step-3.service";
import { Step4Service } from "./services/step-4.service";
import { Step5Service } from "./services/step-5.service";
import { Step6Service } from "./services/step-6.service";
import { ViewRevCapReceiptComponent } from './view-ia/view-step5-finance/view-rev-cap-receipt/view-rev-cap-receipt.component';
import { ViewCapExpReceiptComponent } from './view-ia/view-step5-finance/view-cap-exp-receipt/view-cap-exp-receipt.component';
import { ViewDeasComponent } from './view-ia/view-step5-finance/view-deas/view-deas.component';
import { AddObjective1BcComponent } from './create-ia/step6-audit-observation/step6-audit-observation/add-objective1-bc/add-objective1-bc.component';
import { EditObjective1BcComponent } from './edit-ia/step6-audit-observation/step6-audit-observation/edit-objective1-bc/edit-objective1-bc.component';
import { ViewPartAObjectivesComponent } from './view-ia/view-step6-audit-observation/view-part-aobjectives/view-part-aobjectives.component';
import { PartA1Component } from './view-ia/view-step6-audit-observation/part-a1/part-a1.component';
import { PartA2Component } from './view-ia/view-step6-audit-observation/part-a2/part-a2.component';
import { ViewPartCComponent } from './view-ia/view-step6-audit-observation/view-part-c/view-part-c.component';
import { ViewPartBComponent } from './view-ia/view-step6-audit-observation/view-part-b/view-part-b.component';
import { MatSnackBar, MatSnackBarModule } from "@angular/material/snack-bar";
import { AcknowledgementComponent } from './view-ia/view-step2-executive-summary/acknowledgement/acknowledgement.component';
import { CommentFromMngmntComponent } from './view-ia/view-step2-executive-summary/comment-from-mngmnt/comment-from-mngmnt.component';
import { StrengthComponent } from './view-ia/view-step2-executive-summary/strength/strength.component';
import { WeaknessComponent } from './view-ia/view-step2-executive-summary/weakness/weakness.component';
import { OpinionComponent } from './view-ia/view-step2-executive-summary/opinion/opinion.component';
import { RecommendationComponent } from './view-ia/view-step2-executive-summary/recommendation/recommendation.component';
import { IntroductionComponent } from './view-ia/view-step2-executive-summary/introduction/introduction.component';
import { ViewPartBFComponent } from './view-ia/view-step6-audit-observation/view-part-bf/view-part-bf.component';
import { ViewPartBHComponent } from './view-ia/view-step6-audit-observation/view-part-bh/view-part-bh.component';
import { ListUnassignedIaComponent } from "./list-unassigned-ia/list-unassigned-ia.component";
import { ListAssignedToMeIaComponent } from "./list-assigned-to-me-ia/list-assigned-to-me-ia.component";

@NgModule({
  declarations: [
    InternalAuditComponent,
    CreateIaComponent,
    EditIaComponent,
    ViewIaComponent,
    ListIaComponent,
    Step1ReportComponent,
    Step2ExecutiveSummaryComponent,
    Step3DetailedAuditComponent,
    Step4ReviewOfOutstandingParasComponent,
    Step5FinanceComponent,
    Step6AuditObservationComponent,
    IaStep2IntroductionComponent,
    IaStep2ResultsnfindingsStrengthComponent,
    IaStep2ResultsnfindingsWeaknessComponent,
    IaStep2OpinionComponent,
    IaStep2AuditRecommendationComponent,
    IaStep2CommentFromManagementComponent,
    IaStep2AcknowledgementComponent,
    AuditObservationStatusComponent,
    AuditParaDetailsComponent,
    BudgetaryprovisionAndExpenditureComponent,
    VolumeOfTransactionComponent,
    BankReconcilationComponent,
    RevenueAndCapitalReceiptsComponent,
    MunicipalAccountCommitteeStatusComponent,
    DeasImplementationStatusComponent,
    ViewStep1ReportComponent,
    ViewStep2ExecutiveSummaryComponent,
    ViewStep3DetailedAuditComponent,
    ViewStep4OutstandingParasReviewComponent,
    ViewStep5FinanceComponent,
    ViewStep6AuditObservationComponent,
    RevenueAndCapitalExpenditureComponent,
    EditStep1ReportComponent,
    EditStep2ExecutiveSummaryComponent,
    EditStep5FinanceComponent,
    EditStep4ReviewOfOutstandingParasComponent,
    EditBankReconcilationComponent,
    EditVolumeOfTransactionComponent,
    EditRevenueAndCapitalExpenditureComponent,
    EditRevenueAndCapitalReceiptsComponent,
    EditMunicipalAccountCommitteeStatusComponent,
    EditDeasImplementationStatusComponent,
    EditBudgetaryprovisionAndExpenditureComponent,
    EditIaStep2IntroductionComponent,
    EditIaStep2ResultsnfindingsStrengthComponent,
    EditIaStep2ResultsnfindingsWeaknessComponent,
    EditIaStep2OpinionComponent,
    EditIaStep2AuditRecommendationComponent,
    EditIaStep2CommentFromManagementComponent,
    EditIaStep2AcknowledgementComponent,
    EditStep3DetailedAuditComponent,
    EditAuditObservationStatusComponent,
    EditAuditParaDetailsComponent,
    EditStep6AuditObservationComponent,
    EditPartAobjectiveComponent,
    AddPartAobjectiveComponent,
    AddObjectiveComponent,
    AddPartBDescriptionComponent,
    AddPartbFComponent,
    AddPartBHComponent,
    AddPartCdescriptionComponent,
    EditPartAobjectiveComponent,
    EditObjectiveComponent,
    EditPartBDescriptionComponent,
    EditPartbFComponent,
    EditPartBHComponent,
    EditPartCdescriptionComponent,
    ViewRevCapReceiptComponent,
    ViewCapExpReceiptComponent,
    ViewDeasComponent,
    AddObjective1BcComponent,
    EditObjective1BcComponent,
    ViewPartAObjectivesComponent,
    PartA1Component,
    PartA2Component,
    ViewPartCComponent,
    ViewPartBComponent,
    AcknowledgementComponent,
    CommentFromMngmntComponent,
    StrengthComponent,
    WeaknessComponent,
    OpinionComponent,
    RecommendationComponent,
    IntroductionComponent,
    ViewPartBFComponent,
    ViewPartBHComponent,
    ListUnassignedIaComponent,
    ListAssignedToMeIaComponent

  ],
  imports: [
    CommonModule,
    InternalAuditRouteModule,
    MatStepperModule,
    MatCardModule,
    MatIconModule,
    SharedModule,
    ReactiveFormsModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSnackBarModule,

  ],
  exports: [
  ],

  providers: [
    InternalAuditService,
    Step1ReportService,
    Step2Service,
    Step3Service,
    Step4Service,
    Step5Service,
    Step6Service,
    MatSnackBar,
  ]
})
export class InternalAuditModule { }
