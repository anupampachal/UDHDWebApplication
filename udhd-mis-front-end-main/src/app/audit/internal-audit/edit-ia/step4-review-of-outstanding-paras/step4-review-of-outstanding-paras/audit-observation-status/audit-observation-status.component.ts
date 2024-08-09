import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators, FormArray } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { filter, tap, switchMap, shareReplay } from 'rxjs/operators';
import { Step1ReportService } from 'src/app/audit/internal-audit/services/step-1-report.service';
import { Step4Service } from 'src/app/audit/internal-audit/services/step-4.service';

@Component({
  selector: 'audit-observation-status',
  templateUrl: './audit-observation-status.component.html',
  styleUrls: ['./audit-observation-status.component.css']
})
export class EditAuditObservationStatusComponent implements OnInit {
  auditObservationStatusForm!: FormGroup;
  isDisabled: boolean = false;
  statusId!: number;
  @Input() reportId!: number;
  buttonText: string = 'Save';
  obsIndex: number[] = []

  constructor(
    private fb: FormBuilder,
    private service: Step4Service,
    private service1: Step1ReportService,
    private route: ActivatedRoute,

  ) { }

  ngOnInit() {
    this.auditObservationStatusForm = this.fb.group({
      auditObservationStatus: new FormArray([this.createAuditObservationStatusForm()])
    });
    this.route.params.pipe(
      filter((params) => params && params['id']),
      switchMap((param) => this.service.getAuditObsList(param['id'])),
      tap((dt) => this.setData(dt)),
      shareReplay(2)
    ).subscribe(
      () => {},
      error => {}
    )
  }

  setData(dt: any) {
    // 
    // this.auditObservationStatusForm.patchValue({
    //   internalAuditId: dt.internalAuditId,
    //   nameOfChairmanMayor: dt.nameOfChairmanMayor,
    //   nameOfCMO: dt.nameOfCMO,
    //   periodOfServiceChairman: dt.periodOfServiceChairman,
    //   periodOfServiceCMO: dt.periodOfServiceCMO,
    // })
    for (let i = 0; i < dt.length; i++) {
      this.obsIndex[i] = dt[i].id
      // 
        this.addAuditObservationStatusForm()
      this.auditObservationStatus.at(i).patchValue(dt[i])
    }
    // 
    // 
  }

  get auditObservationStatus() {
    return this.auditObservationStatusForm.controls['auditObservationStatus'] as FormArray
  }

  createAuditObservationStatusForm(): FormGroup {
    return this.fb.group({
      id: new FormControl(null),
      auditParticularsWithDate: new FormControl(null, [Validators.required]),
      totalNosOfAuditParas: new FormControl(null, [Validators.required]),
      totalAmtOfRecovery: new FormControl(null, [Validators.required]),
      totalAuditParasWithCorrectiveActionReq: new FormControl(null, [Validators.required]),
      totalAuditParasWithCashRecProposed: new FormControl(null, [Validators.required]),
      totalAuditParasWithCashRecMade: new FormControl(null, [Validators.required]),
      totalNosOfParasWithNoAction: new FormControl(null, [Validators.required]),
      nosAndDateOfComplianceReport: new FormControl(null, [Validators.required]),
    })
  }

  addAuditObservationStatusForm() {
    (<FormArray>this.auditObservationStatusForm.get('auditObservationStatus')).push(this.createAuditObservationStatusForm());
  }

  onSubmit(index: number, obs: any) {
    this.service.createUpdateAuditObs(this.service1.step1Report.id, {
      id: this.obsIndex[index] ? this.obsIndex[index] : null,
      iaId: this.service1.step1Report.id,
      ...obs.value
    }).subscribe((res:any) => {
      this.obsIndex[index] = res.id
      this.auditObservationStatus.controls[index].disable()
    })
  }
  enableControl(i: number) {
    this.auditObservationStatus.controls[i].enable()
  }
  remove(index: number) {
    let auditObservationStatuses = <FormArray>this.auditObservationStatusForm.controls['auditObservationStatus'];
    let auditObservationStatus = auditObservationStatuses['controls'][index].value;
    auditObservationStatuses.removeAt(index);
  }

}
