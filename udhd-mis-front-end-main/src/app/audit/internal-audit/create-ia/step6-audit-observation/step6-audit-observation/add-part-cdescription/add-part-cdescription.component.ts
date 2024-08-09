import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Step1ReportService } from 'src/app/audit/internal-audit/services/step-1-report.service';
import { Step6Service } from 'src/app/audit/internal-audit/services/step-6.service';

@Component({
  selector: 'app-add-part-cdescription',
  templateUrl: './add-part-cdescription.component.html',
  styleUrls: ['./add-part-cdescription.component.css']
})
export class AddPartCdescriptionComponent implements OnInit {
  partCForm!: FormGroup;

  partCList: string[] = [
    'AReportNonCompliance',
    'BOpenImplementationOfSAS',
    'CReportBiharMunicipalAccounting', 'DReportOnComplianceOfFinancialGuidelines', 'EReportAllMajorRevenueLoss',
    'FReportOnAdequacy',
    'GReportOnProcurementThroughETendering', 'HReportOnAvailabilityOfUC',
    'IReportOnInstanceOfLosses',
    'JReportOnPaymentTerms',
    'LReportOnFixedDeposit',
    'MReportOnMajorLossesOfULBs', 'NReportOnAllTypesOfTaxDeductions', 'OInternalAuditorEnsuresAllCAGAndAGAudit'
  ]

  currentType = ""
  @Input() set indx(value: number) {
    this.currentType = this.partCList[value]
  }

  constructor(private fb: FormBuilder,
     private service: Step6Service,
     private service1: Step1ReportService

     ) { }

  ngOnInit() {
    this.partCForm = this.fb.group({
      'id': new FormControl(null),
      'description': new FormControl(null, [Validators.required, Validators.minLength(2), Validators.maxLength(10000)])
    });
  }
  onSubmit() {
    this.service.createPartC({
      iaId: this.service1.step1Report.id,
      comment: this.partCForm.value.description,
      partCEnum: this.currentType
    } ).subscribe(
      () => {
        this.partCForm.disable()
      },
      err=>{}
    )
  }
  enableForm() {
    this.partCForm.enable()
  }
}
