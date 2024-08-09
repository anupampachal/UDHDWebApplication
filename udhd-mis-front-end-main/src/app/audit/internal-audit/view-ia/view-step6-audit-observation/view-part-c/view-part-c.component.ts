import { Component, Input, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { filter, switchMap, tap, shareReplay } from 'rxjs/operators';
import { Step1ReportService } from '../../../services/step-1-report.service';
import { Step6Service } from '../../../services/step-6.service';

@Component({
  selector: 'app-view-part-c',
  templateUrl: './view-part-c.component.html',
  styleUrls: ['./view-part-c.component.css']
})
export class ViewPartCComponent implements OnInit {
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
     private service1: Step1ReportService,
     private route: ActivatedRoute,

     ) { }

  ngOnInit() {
    this.partCForm = this.fb.group({
      'id': new FormControl(null),
      'description': new FormControl({ value: null, disabled: true }, [Validators.required, Validators.minLength(2), Validators.maxLength(10000)])
    });
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
  setValue(data: any) {
    
    this.partCForm.patchValue({
      id: data.id,
      description:data.comment
    })
    this.partCForm.disable()
  }
}
