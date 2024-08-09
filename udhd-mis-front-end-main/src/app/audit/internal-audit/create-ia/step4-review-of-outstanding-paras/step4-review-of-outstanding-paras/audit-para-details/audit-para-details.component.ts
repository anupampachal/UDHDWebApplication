import { Component, OnInit, ViewChild, Input } from '@angular/core';
import { FormGroup, FormBuilder, FormArray, FormControl, Validators } from '@angular/forms';
import { of } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';
import { Step1ReportService } from 'src/app/audit/internal-audit/services/step-1-report.service';
import { Step4Service } from 'src/app/audit/internal-audit/services/step-4.service';
@Component({
  selector: 'app-audit-para-details',
  templateUrl: './audit-para-details.component.html',
  styleUrls: ['./audit-para-details.component.css']
})
export class AuditParaDetailsComponent implements OnInit {

  auidtPartForm!: FormGroup;
  loadView: boolean = false;
  loadViewComponent: boolean = false;
  subHeadings!: FormArray;
  index!: number;
  headingIndex!: number;
  actions = ["TAKEN", "NOT_TAKEN", "NOT_REQUIRED"]
  buttonText: string = 'Save';
  selectedFile: any;
  isEdit = true;
  isFile = false;
  paraIndex: number[] = []
  isDisable = false
  @Input() reportId!: number;
  fileName: any;
  constructor(
    private fb: FormBuilder,
    private service: Step4Service,
    private service1: Step1ReportService
  ) { }

  ngOnInit() {
    this.auidtPartForm = this.fb.group({
      headingDatas: new FormArray([this.createAuditPartForm()])
    });
  }

  createAuditDetailForm(): FormGroup {
    return this.fb.group({
      'id': new FormControl(null),
      'titleForPart': new FormControl(null, [Validators.required]),
      'headingDatas': new FormArray([this.createAuditPartForm()])
    });
  }

  createAuditPartForm(): FormGroup {
    return this.fb.group({
      auditParaNo: new FormControl(null, [Validators.required]),
      amountInvolved: new FormControl(null, [Validators.required]),
      recoveryProposed: new FormControl(null, [Validators.required]),
      recoveryCompleted: new FormControl(null, [Validators.required]),
      actionStatus: new FormControl(null, [Validators.required]),
      auditParaHeading: new FormControl(null, [Validators.required]),
    });
  }

  addPartForm() {
    (<FormArray>this.auidtPartForm.get('parts')).push(this.createAuditDetailForm());
  }

  pushHeadingPartDetailForm() {
    (<FormArray>this.auidtPartForm.get('headingDatas')).push(this.createAuditPartForm());
  }

  addPartDetailForm(index: number) {
    this.pushNewPartDetailForm(index);
  }
  get auditParaDetail() {
    return this.auidtPartForm.controls["headingDatas"] as FormArray
  }
  pushNewPartDetailForm(index: number) {
    let headingDatas = this.auidtPartForm.controls['headingDatas'] as FormArray;
    headingDatas.push(this.createAuditPartForm());
  }

  onSubmit(i: number, obs: any) {
    
    this.service.createUpdateAuditPara(this.service1.step1Report.id, {
      ...obs.value,
      iaId: this.service1.step1Report.id,
      id: this.paraIndex[i] != undefined ? this.paraIndex[i] : null,

    }).pipe(
      map(res => res),
      mergeMap((res: any) => {
        if (this.selectedFile) {
          return this.service.uploadFile(this.selectedFile, res.iaId, res.id);
        }
        return of(res)
      })
    ).subscribe(res => {
      this.paraIndex[i] = res.id
      this.isDisable = true
      this.auditParaDetail.controls[i].disable()
    })
  }
  enableControl(i: number) {
    this.auditParaDetail.controls[i].enable()
    this.isDisable = false
  }

  remove(index: number) {
    let auditParaHeadings = <FormArray>this.auidtPartForm.controls['headingDatas'];
    let auditParaHeading = auditParaHeadings['controls'][index].value;

    auditParaHeadings.removeAt(index);
  }

  public selectFile(event: any) {
    this.isFile = false;
    if (event.target.files && event.target.files[0]) {
      this.isFile = true
      this.selectedFile = event.target.files[0];
      if (this.selectedFile.size > 10000 * 1024) {
        this.isFile = false;
        this.selectedFile = null;
        this.fileName = null;
        event.target.value = null;
        return;
      }

      const reader = new FileReader();
      reader.onload = (event: any) => {
        this.fileName = (<FileReader>event.target).result;
      };
      reader.readAsDataURL(event.target.files[0]);
    }
  }
  // paraInfoId = '2'
  // uploadFile() {
  //   this.service.uploadFile(this.selectedFile, this.service1.step1Report.id.toString(), this.paraInfoId)
  //     .subscribe()
  // }


}


