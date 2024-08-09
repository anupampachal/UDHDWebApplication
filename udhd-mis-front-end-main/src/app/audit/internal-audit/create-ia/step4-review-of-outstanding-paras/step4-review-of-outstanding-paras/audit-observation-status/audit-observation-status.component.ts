import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators, FormArray } from '@angular/forms';
import { Step1ReportService } from 'src/app/audit/internal-audit/services/step-1-report.service';
import { Step4Service } from 'src/app/audit/internal-audit/services/step-4.service';

@Component({
  selector: 'app-audit-observation-status',
  templateUrl: './audit-observation-status.component.html',
  styleUrls: ['./audit-observation-status.component.css']
})
export class AuditObservationStatusComponent implements OnInit {
  auditObservationStatusForm!: FormGroup;
  isDisabled: boolean = false;
  statusId!: number;
  @Input() reportId!: number;
  buttonText: string = 'Save';
  obsIndex: number[] = []
  constructor(
    private fb: FormBuilder,
    private service: Step4Service,
    private service1: Step1ReportService
    ) { }

  ngOnInit() {
    this.auditObservationStatusForm = this.fb.group({
      auditObservationStatus: new FormArray([this.createAuditObservationStatusForm()])
    });
    //this.createAuditObservationStatusForm();
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
      ...obs.value,
      id: this.obsIndex[index]!= undefined ? this.obsIndex[index]: null,
      iaId: this.service1.step1Report.id,
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
