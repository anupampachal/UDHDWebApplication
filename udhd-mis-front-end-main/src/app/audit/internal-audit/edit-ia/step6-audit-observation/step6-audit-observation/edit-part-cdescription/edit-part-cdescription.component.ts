import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { filter, switchMap, tap, shareReplay } from 'rxjs/operators';
import { Step1ReportService } from 'src/app/audit/internal-audit/services/step-1-report.service';
import { Step6Service } from 'src/app/audit/internal-audit/services/step-6.service';

@Component({
  selector: 'app-edit-part-cdescription',
  templateUrl: './edit-part-cdescription.component.html',
  styleUrls: ['./edit-part-cdescription.component.css']
})
export class EditPartCdescriptionComponent implements OnInit {
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
    this.route.params.pipe(
      filter((params) => params && params['id']),
      switchMap((param) =>
        this.service.getPartC(param['id'], this.currentType)),
      tap((dt) => this.setValue(dt)),
      shareReplay(2)).subscribe(
        () => { },
        error => { }
      )
  }

  constructor(private fb: FormBuilder,
    private service: Step6Service,
    private service1: Step1ReportService,
    private route: ActivatedRoute,

  ) { }

  ngOnInit() {
    this.partCForm = this.fb.group({
      'id': new FormControl(null),
      'description': new FormControl(null, [Validators.required, Validators.minLength(2), Validators.maxLength(10000)])
    });

  }
  setValue(data: any) {

    this.partCForm.patchValue({
      id: data.id,
      description: data.comment
    })
  }
  onSubmit() {
    this.service.createPartC({
      iaId: this.service1.step1Report.id,
      comment: this.partCForm.value.description,
      partCEnum: this.currentType
    }).subscribe(
      () => {
        this.partCForm.disable()
      },
      err => { }
    )
  }
  enableForm() {
    this.partCForm.enable()
  }
}
