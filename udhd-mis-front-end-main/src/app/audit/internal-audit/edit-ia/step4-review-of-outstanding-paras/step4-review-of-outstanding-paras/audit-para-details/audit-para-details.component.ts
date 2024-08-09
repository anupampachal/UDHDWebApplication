import { Component, OnInit, ViewChild, Input } from '@angular/core';
import { FormGroup, FormBuilder, FormArray, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { filter, map, mergeMap, shareReplay, switchMap, tap } from 'rxjs/operators';
import { Step1ReportService } from 'src/app/audit/internal-audit/services/step-1-report.service';
import { Step4Service } from 'src/app/audit/internal-audit/services/step-4.service';
@Component({
  selector: 'audit-para-details',
  templateUrl: './audit-para-details.component.html',
  styleUrls: ['./audit-para-details.component.css']
})
export class EditAuditParaDetailsComponent implements OnInit {

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
  @Input() reportId!: number;
  fileName: any;
  paraIndex: number[] = []
  isDisable = false
  constructor(
    private fb: FormBuilder,
    private service: Step4Service,
    private service1: Step1ReportService,
    private route: ActivatedRoute,

  ) { }

  ngOnInit() {
    this.auidtPartForm = this.fb.group({
      headingDatas: new FormArray([this.createAuditPartForm()])
    });
    this.route.params.pipe(
      filter((params) => params && params['id']),
      switchMap((param) => this.service.getAuditParaList(param['id'])),
      tap((dt) => this.setData(dt)),
      shareReplay(2)
    ).subscribe(
      () => {},
      error => {}
    )
  }
  setData(dt: any) {
    for (let i = 0; i < dt.length; i++) {
      this.pushHeadingPartDetailForm()
      this.paraIndex[i] = dt[i].id
      this.auditParaDetail.at(i).patchValue(dt[i])
    }
    //
    //
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
      id: new FormControl(),
      iaId: new FormControl(),
      auditParaNo: new FormControl(null, [Validators.required]),
      amountInvolved: new FormControl(null, [Validators.required]),
      recoveryProposed: new FormControl(null, [Validators.required]),
      recoveryCompleted: new FormControl(null, [Validators.required]),
      actionStatus: new FormControl(null, [Validators.required]),
      auditParaHeading: new FormControl(null, [Validators.required]),
      file: new FormControl(null)
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
      id: this.paraIndex[i] ? this.paraIndex[i] : null,

    }).pipe(
      map(res => res),
      mergeMap((res: any) => {
        if (this.selectedFile) {

          return this.service.uploadFile(this.selectedFile, res.iaId, this.paraIndex[i].toString());
        }
        return of(res)
      })
    ).subscribe(res => {
      this.isDisable = true
      this.paraIndex[i] = res.id
      this.auditParaDetail.controls[i].disable()
      this.selectedFile = null;
    })
  }
  enableControl(i: number) {
    this.isDisable = false
    this.auditParaDetail.controls[i].enable()
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
}
